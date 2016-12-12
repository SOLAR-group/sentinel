package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.Iterator;

/**
 * Created by Giovani Guizzo on 24/10/2016.
 */
public class NonTerminalFactory implements Factory<Rule> {

    private NonTerminalFactory() {
    }

    @Override
    public Operation createOperation(Rule rule, Iterator<Integer> cyclicIterator) {
        Factory factory = FactoryFlyweight.getFactory(rule.getName());
        return factory.createOperation(rule.getOption(cyclicIterator), cyclicIterator);
    }

    public static NonTerminalFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        private static final NonTerminalFactory INSTANCE = new NonTerminalFactory();
    }

}