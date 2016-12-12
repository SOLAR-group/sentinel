package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.impl.GroupSelectionOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.impl.SelectionOperation;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class OperatorSelectionTypeFactoryTest {

    private static Rule testingRule;

    @BeforeClass
    public static void setUpClass() {
        try {
            StrategyMapper strategyMapper = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
            testingRule = strategyMapper.getNonTerminalRule(NonTerminalRuleType.OPERATOR_SELECTION_TYPE);
        } catch (IOException ex) {
            Logger.getLogger(OperatorSelectionTypeFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OperatorSelectionTypeFactoryTest() {
    }

    @Test
    public void testCreateOperation() {
        Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof SelectionOperation);
        assertNotNull(((SelectionOperation) operation).getSelectionType());
        assertNotNull(((SelectionOperation) operation).getSorter());
    }

    @Test
    public void testCreateOperation2() {
        Iterator<Integer> iterator = Lists.newArrayList(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).iterator();
        Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
        assertNotNull(operation);
        assertTrue(operation instanceof GroupSelectionOperation);
        assertNotNull(((GroupSelectionOperation) operation).getSelectionType());
        assertNotNull(((GroupSelectionOperation) operation).getSorter());
        assertNotNull(((GroupSelectionOperation) operation).getGroupingFunction());
        assertNotNull(((GroupSelectionOperation) operation).getSelectionOperation());
    }

}