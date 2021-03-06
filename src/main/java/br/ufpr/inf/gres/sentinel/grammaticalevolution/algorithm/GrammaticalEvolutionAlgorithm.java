package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.duplicate.DuplicateOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.PruneOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.VariableLengthProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.List;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

/**
 * @author Giovani Guizzo
 * @param <T>
 */
public class GrammaticalEvolutionAlgorithm<T> extends NSGAII<VariableLengthSolution<T>> {

    private final DuplicateOperator<VariableLengthSolution<T>> duplicateOperator;
    private final PruneOperator<VariableLengthSolution<T>> pruneOperator;

    /**
     * Constructor
     *
     * @param problem
     * @param maxEvaluations
     * @param populationSize
     * @param duplicateOperator
     * @param pruneOperator
     * @param crossoverOperator
     * @param mutationOperator
     * @param selectionOperator
     * @param evaluator
     */
    public GrammaticalEvolutionAlgorithm(VariableLengthProblem<T> problem,
            int maxEvaluations,
            int populationSize,
            DuplicateOperator<VariableLengthSolution<T>> duplicateOperator,
            PruneOperator<VariableLengthSolution<T>> pruneOperator,
            CrossoverOperator<VariableLengthSolution<T>> crossoverOperator,
            MutationOperator<VariableLengthSolution<T>> mutationOperator,
            SelectionOperator<List<VariableLengthSolution<T>>, VariableLengthSolution<T>> selectionOperator,
            SolutionListEvaluator<VariableLengthSolution<T>> evaluator) {
        super(problem,
                maxEvaluations,
                populationSize,
                crossoverOperator,
                mutationOperator,
                selectionOperator,
                evaluator);
        this.pruneOperator = pruneOperator;
        this.duplicateOperator = duplicateOperator;
    }

    private List<VariableLengthSolution<T>> duplicate(List<VariableLengthSolution<T>> population) {
        for (VariableLengthSolution<T> solution : population) {
            this.duplicateOperator.execute(solution);
        }
        return population;
    }

    @Override
    public String getDescription() {
        return "Multi-Objective Grammatical Evolution Algorithm based on NSGA-II";
    }

    @Override
    public String getName() {
        return "GE-NSGA-II";
    }

    private List<VariableLengthSolution<T>> prune(List<VariableLengthSolution<T>> population) {
        for (VariableLengthSolution<T> solution : population) {
            this.pruneOperator.execute(solution);
        }
        return population;
    }

    @Override
    public void run() {
        this.setPopulation(this.createInitialPopulation());
        this.setPopulation(this.evaluatePopulation(this.getPopulation()));
        this.initProgress();

        while (!this.isStoppingConditionReached()) {
            List<VariableLengthSolution<T>> matingPopulation = this.selection(this.getPopulation());
            List<VariableLengthSolution<T>> offspringPopulation = this.reproduction(matingPopulation);
            offspringPopulation = this.duplicate(offspringPopulation);
            offspringPopulation = this.evaluatePopulation(offspringPopulation);
            offspringPopulation = this.prune(offspringPopulation);
            this.setPopulation(this.replacement(this.getPopulation(), offspringPopulation));
            this.updateProgress();
        }
    }

}
