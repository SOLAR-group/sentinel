package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant.GroupMutantsByOperator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.mutant.GroupMutantsByOperatorType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator.GroupOperatorsByMutantQuantity;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.impl.operator.GroupOperatorsByType;
import com.google.common.base.Preconditions;
import java.util.Iterator;

/**
 * @author Giovani Guizzo
 */
public class GroupingFactory implements Factory<Option> {

    /**
     *
     * @return
     */
    public static GroupingFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private GroupingFactory() {
    }

    /**
     *
     * @param node
     * @param integerIterator
     * @return
     */
    @Override
    public Operation createOperation(Option node, Iterator<Integer> integerIterator) {
        Iterator<Rule> rules = node.getRules().iterator();

        Preconditions.checkArgument(rules.hasNext(), "Malformed grammar option: " + node.toString());
        Rule rule = rules.next();
        rule = rule.getOption(integerIterator).getRules().get(0);

        AbstractGroupingOperation mainOperation;
        switch (rule.getName()) {
            case TerminalRuleType.TYPE:
                mainOperation = new GroupOperatorsByType();
                break;
            case TerminalRuleType.MUTANT_QUANTITY:
                mainOperation = new GroupOperatorsByMutantQuantity();
                break;
            case TerminalRuleType.OPERATOR_TYPE:
                mainOperation = new GroupMutantsByOperatorType();
                break;
            case TerminalRuleType.OPERATOR:
                mainOperation = new GroupMutantsByOperator();
                break;
            default:
                throw new IllegalArgumentException("Malformed grammar option: " + node.toString());
        }

        return mainOperation;
    }

    private static class SingletonHolder {

        private static final GroupingFactory INSTANCE = new GroupingFactory();
    }

}
