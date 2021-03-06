package br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults;

import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest.IntegrationFacadeStub;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Giovani Guizzo
 */
public class AddAllOperatorsOperationTest {

    @BeforeClass
    public static void setUpClass() {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeStub());
    }

    public AddAllOperatorsOperationTest() {
    }

    @Test
    public void testDoOperation() {
        Solution solution = new Solution();
        AddAllOperatorsOperation operation = new AddAllOperatorsOperation();
        operation.doOperation(solution, null);
        assertEquals(4, solution.getOperators().size());
    }

    @Test
    public void testIsSpecific() {
        AddAllOperatorsOperation operation = new AddAllOperatorsOperation();
        assertFalse(operation.isSpecific());
    }

}
