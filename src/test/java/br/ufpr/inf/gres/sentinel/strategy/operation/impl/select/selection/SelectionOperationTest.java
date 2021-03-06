package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.LastToFirstSelection;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.type.impl.SequentialSelection;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.OperatorTypeComparator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Giovani Guizzo
 */
public class SelectionOperationTest {

    public SelectionOperationTest() {
    }

    @Test
    public void testDoOperation() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setQuantity(1);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type3");
        Operator operator4 = new Operator("Operator4", "Type4");

        List<Operator> group1 = new ArrayList<>();
        group1.add(operator1);
        group1.add(operator2);
        group1.add(operator3);
        group1.add(operator4);

        Collection<Operator> result = selectionOp.doOperation(group1, null);
        assertEquals(1, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator1, iterator.next());
    }

    @Test
    public void testDoOperation10() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(1);

        List<Operator> group1 = new ArrayList<>();

        Collection<Operator> result = selectionOp.doOperation(group1, null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testDoOperation2() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setQuantity(1);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type0");
        Operator operator4 = new Operator("Operator4", "Type4");

        List<Operator> group1 = new ArrayList<>();
        group1.add(operator1);
        group1.add(operator2);
        group1.add(operator3);
        group1.add(operator4);

        Collection<Operator> result = selectionOp.doOperation(group1, null);
        assertEquals(1, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator3, iterator.next());
    }

    @Test
    public void testDoOperation3() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setQuantity(5);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type3");
        Operator operator4 = new Operator("Operator4", "Type4");

        List<Operator> group1 = new ArrayList<>();
        group1.add(operator1);
        group1.add(operator2);
        group1.add(operator3);
        group1.add(operator4);

        Collection<Operator> result = selectionOp.doOperation(group1, null);
        assertEquals(4, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator1, iterator.next());
        assertEquals(operator2, iterator.next());
        assertEquals(operator3, iterator.next());
        assertEquals(operator4, iterator.next());
    }

    @Test(expected = Exception.class)
    public void testDoOperation4() throws Exception {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setPercentage(0.0);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type3");
        Operator operator4 = new Operator("Operator4", "Type4");

        List<Operator> group1 = new ArrayList<>();
        group1.add(operator1);
        group1.add(operator2);
        group1.add(operator3);
        group1.add(operator4);

        Collection<Operator> result = selectionOp.doOperation(group1, null);
    }

    @Test
    public void testDoOperation5() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setPercentage(1.0);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type3");
        Operator operator4 = new Operator("Operator4", "Type4");

        List<Operator> group1 = new ArrayList<>();
        group1.add(operator1);
        group1.add(operator2);
        group1.add(operator3);
        group1.add(operator4);

        Collection<Operator> result = selectionOp.doOperation(group1, null);
        assertEquals(4, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator1, iterator.next());
        assertEquals(operator2, iterator.next());
        assertEquals(operator3, iterator.next());
        assertEquals(operator4, iterator.next());
    }

    @Test
    public void testDoOperation6() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setPercentage(0.5);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type3");
        Operator operator4 = new Operator("Operator4", "Type4");

        List<Operator> group1 = new ArrayList<>();
        group1.add(operator1);
        group1.add(operator2);
        group1.add(operator3);
        group1.add(operator4);

        Collection<Operator> result = selectionOp.doOperation(group1, null);
        assertEquals(2, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator1, iterator.next());
        assertEquals(operator2, iterator.next());
    }

    @Test
    public void testDoOperation7() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setPercentage(0.74);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type3");
        Operator operator4 = new Operator("Operator4", "Type4");

        List<Operator> group1 = new ArrayList<>();
        group1.add(operator1);
        group1.add(operator2);
        group1.add(operator3);
        group1.add(operator4);

        Collection<Operator> result = selectionOp.doOperation(group1, null);
        assertEquals(2, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator1, iterator.next());
        assertEquals(operator2, iterator.next());
    }

    @Test
    public void testDoOperation8() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new LastToFirstSelection());
        selectionOp.setSorter(new OperatorTypeComparator());
        selectionOp.setPercentage(0.99);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type3");
        Operator operator4 = new Operator("Operator4", "Type4");

        List<Operator> group1 = new ArrayList<>();
        group1.add(operator1);
        group1.add(operator2);
        group1.add(operator3);
        group1.add(operator4);

        Collection<Operator> result = selectionOp.doOperation(group1, null);
        assertEquals(3, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator1, iterator.next());
        assertEquals(operator4, iterator.next());
        assertEquals(operator2, iterator.next());
    }

    @Test
    public void testDoOperation9() {
        SelectionOperation<Operator> selectionOp = new SelectionOperation<>();
        selectionOp.setSelectionType(new SequentialSelection());
        selectionOp.setSorter(null);
        selectionOp.setQuantity(1);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type2");
        Operator operator3 = new Operator("Operator3", "Type0");
        Operator operator4 = new Operator("Operator4", "Type4");

        List<Operator> group1 = new ArrayList<>();
        group1.add(operator1);
        group1.add(operator2);
        group1.add(operator3);
        group1.add(operator4);

        Collection<Operator> result = selectionOp.doOperation(group1, null);
        assertEquals(1, result.size());

        Iterator<Operator> iterator = result.iterator();
        assertEquals(operator1, iterator.next());
    }

}
