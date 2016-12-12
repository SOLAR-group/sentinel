package br.ufpr.inf.gres.sentinel.strategy.operation.impl.group;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Giovani Guizzo
 */
public abstract class AbstractGroupingFunction<T> extends Operation<List<T>, List<List<T>>> {

    public AbstractGroupingFunction(String name) {
        super(name);
    }

    @Override
    public List<List<T>> doOperation(List<T> input) {
        Map<?, List<T>> collect = input
                .stream()
                .collect(Collectors.groupingBy(createGrouperFunction()));
        return new ArrayList<>(collect.values());
    }

    protected abstract Function<T, ?> createGrouperFunction();

}