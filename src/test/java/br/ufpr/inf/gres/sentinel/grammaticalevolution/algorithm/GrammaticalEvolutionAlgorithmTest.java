package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.crossover.impl.SinglePointVariableCrossover;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.duplicate.impl.SimpleDuplicateOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.mutation.impl.SimpleRandomVariableMutation;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl.SimplePruneOperator;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.hg4hom.HG4HOMFacade;
import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class GrammaticalEvolutionAlgorithmTest {

	private static ProblemStub problem;
	private static GrammaticalEvolutionAlgorithm<Integer> algorithm;

	@BeforeClass
	public static void setUpClass() throws IOException {
		IntegrationFacade.setIntegrationFacade(new HG4HOMFacade(System.getProperty("user.dir") +
																File.separator +
																"src/test/resources/testfiles"));

		Program
				programUnderTest =
				new Program("br.ufpr.inf.gres.TriTyp",
							new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		problem =
				new ProblemStub(GrammarFiles.getDefaultGrammarPath(),
								15,
								100,
								0,
								179,
								0,
								1,
								Lists.newArrayList(programUnderTest));

		algorithm =
				new GrammaticalEvolutionAlgorithm<>(problem,
													100,
													50,
													new SimpleDuplicateOperator<>(0.1, 100),
													new SimplePruneOperator<>(0.1, 10),
													new SinglePointVariableCrossover<>(1.0),
													new SimpleRandomVariableMutation(0.01, 0, 179),
													new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>()),
													new SequentialSolutionListEvaluator<>());
	}

	@Test
	public void run() throws Exception {
		algorithm.run();
		List<VariableLengthSolution<Integer>> result = algorithm.getResult();
		assertEquals(50, result.size());
	}

	@Test
	public void getName() throws Exception {
		assertEquals("GE-NSGA-II", algorithm.getName());
	}

	@Test
	public void getDescription() throws Exception {
		assertEquals("Multi-Objective Grammatical Evolution Algorithm based on NSGA-II", algorithm.getDescription());
	}

	public static class ProblemStub extends MutationStrategyGenerationProblem {

		public ProblemStub(String grammarFile,
						   int minLength,
						   int maxLength,
						   int lowerVariableBound,
						   int upperVariableBound,
						   int maxWraps,
						   int numberOfStrategyRuns,
						   List<Program> testPrograms) throws IOException {
			super(grammarFile,
				  minLength,
				  maxLength,
				  lowerVariableBound,
				  upperVariableBound,
				  maxWraps,
				  numberOfStrategyRuns,
				  testPrograms);
		}

		@Override
		public void evaluate(VariableLengthSolution<Integer> solution) {
			solution.setObjective(0, 0.5);
			solution.setObjective(1, 0.5);
			solution.setObjective(2, 0.5);
		}
	}
}