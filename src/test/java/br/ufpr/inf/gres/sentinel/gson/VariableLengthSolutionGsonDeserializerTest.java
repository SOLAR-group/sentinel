package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeFactory;
import br.ufpr.inf.gres.sentinel.util.TestPrograms;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class VariableLengthSolutionGsonDeserializerTest {

    private static IntegrationFacade facade;
    private static IntegrationFacade oldFacade;
    private static MutationStrategyGenerationProblem problem;

    @BeforeClass
    public static void setUpClass() throws IOException {
        facade = IntegrationFacadeFactory.createIntegrationFacade("PIT",
                System.getProperty("user.dir") + File.separator + "training");
        oldFacade = IntegrationFacade.getIntegrationFacade();
        IntegrationFacade.setIntegrationFacade(facade);

        problem = new MutationStrategyGenerationProblem(GrammarFiles.getGrammarPath(GrammarFiles.DEFAULT_GRAMMAR),
                15,
                100,
                0,
                179,
                10,
                5,
                1,
                Lists.newArrayList(facade.instantiateProgram(TestPrograms.TRIANGLE)),
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
    }

    @AfterClass
    public static void tearDownClass() {
        IntegrationFacade.setIntegrationFacade(oldFacade);
    }

    @Test
    public void testDeserialize() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(VariableLengthSolution.class, new VariableLengthSolutionGsonDeserializer(problem))
                .create();

        VariableLengthSolution<Integer> solution = gson.fromJson("{\"" + ObjectiveFunction.AVERAGE_CPU_TIME + "\":10.0,\"" + ObjectiveFunction.AVERAGE_QUANTITY + "\":10.0,\"" + ObjectiveFunction.AVERAGE_SCORE + "\":10.0,\"name\":\"TestName\",\"objectives\":[1000.0,2000.0],\"consumedItemsCount\":2,\"evaluation\":100,\"variables\":[0,2,1,0,0,1,9,3],\"strategy\":{\"firstOperation\":{\"name\":\"TestOperation\",\"successor\":{\"name\":\"TestOperation2\",\"successor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"TestOperation3\"},\"secondSuccessor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"TestOperation5\"},\"secondSuccessor\":{\"name\":\"TestOperation6\"}},\"secondSuccessor\":{\"name\":\"TestOperation4\"}}}}}}}", VariableLengthSolution.class);

        Assert.assertArrayEquals(new Object[]{0, 2, 1, 0, 0, 1, 9, 3}, solution.getVariablesCopy().toArray());
        Assert.assertEquals(1000, solution.getObjective(0), 0.00001);
        Assert.assertEquals(2000, solution.getObjective(1), 0.00001);
        Assert.assertEquals(100, (int) solution.getAttribute("Evaluation Found"));
        Assert.assertEquals(2, (int) solution.getAttribute("Consumed Items Count"));
        Assert.assertEquals("TestName", solution.getAttribute("Name"));
        Assert.assertEquals(10D, (double) solution.getAttribute(ObjectiveFunction.AVERAGE_CPU_TIME), 0.0001);
        Assert.assertEquals(10D, (double) solution.getAttribute(ObjectiveFunction.AVERAGE_QUANTITY), 0.0001);
        Assert.assertEquals(10D, (double) solution.getAttribute(ObjectiveFunction.AVERAGE_SCORE), 0.0001);
    }

    @Test
    public void testDeserialize2() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(VariableLengthSolution.class, new VariableLengthSolutionGsonDeserializer(problem))
                .create();

        VariableLengthSolution<Integer> solution = gson.fromJson("{\"" + ObjectiveFunction.AVERAGE_QUANTITY + "\":10.0,\"" + ObjectiveFunction.AVERAGE_SCORE + "\":10.0,\"objectives\":[1000.0,2000.0],\"name\":\"TestName\",\"consumedItemsCount\":2,\"evaluation\":100,\"variables\":[0,2,1,0,0,1,9,3],\"strategy\":{\"firstOperation\":{\"name\":\"TestOperation\",\"successor\":{\"name\":\"TestOperation2\",\"successor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"TestOperation3\"},\"secondSuccessor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"New Branch\",\"successor\":{\"name\":\"TestOperation5\"},\"secondSuccessor\":{\"name\":\"TestOperation6\"}},\"secondSuccessor\":{\"name\":\"TestOperation4\"}}}}}}}", VariableLengthSolution.class);

        Assert.assertArrayEquals(new Object[]{0, 2, 1, 0, 0, 1, 9, 3}, solution.getVariablesCopy().toArray());
        Assert.assertEquals(1000, solution.getObjective(0), 0.00001);
        Assert.assertEquals(2000, solution.getObjective(1), 0.00001);
        Assert.assertEquals(100, (int) solution.getAttribute("Evaluation Found"));
        Assert.assertEquals("TestName", solution.getAttribute("Name"));
        Assert.assertEquals(2, (int) solution.getAttribute("Consumed Items Count"));
        Assert.assertNull(solution.getAttribute(ObjectiveFunction.AVERAGE_CPU_TIME));
        Assert.assertEquals(10D, (double) solution.getAttribute(ObjectiveFunction.AVERAGE_QUANTITY), 0.0001);
        Assert.assertEquals(10D, (double) solution.getAttribute(ObjectiveFunction.AVERAGE_SCORE), 0.0001);
    }

}
