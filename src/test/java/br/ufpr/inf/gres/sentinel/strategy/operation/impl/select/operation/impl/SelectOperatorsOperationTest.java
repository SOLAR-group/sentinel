package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class SelectOperatorsOperationTest {

    public SelectOperatorsOperationTest() {
    }

    @Test
    public void testConstructor() {
        SelectionOperation<Operator> selectionOperation = new SelectionOperation<>();
        SelectOperatorsOperation operation = new SelectOperatorsOperation(selectionOperation);
        assertEquals(selectionOperation, operation.getSelection());
    }

    @Test
    public void testObtainList() {
        Solution solution = new Solution();
        SelectOperatorsOperation operation = new SelectOperatorsOperation();
        assertEquals(solution.getOperators(), operation.obtainList(solution));
    }

}
