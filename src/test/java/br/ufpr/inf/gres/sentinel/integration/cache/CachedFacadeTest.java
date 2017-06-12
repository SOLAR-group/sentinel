package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Giovani Guizzo
 */
public class CachedFacadeTest {

    private static final Logger LOGGER = LogManager.getLogger(CachedFacadeTest.class);

    private static CachedFacade facade;
    private static Program programUnderTest;

    public CachedFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        LOGGER.debug("Initializing CachedFacadeTest.");
        String directory = System.getProperty("user.dir") + File.separator + "training";
        facade = new CachedFacade(new PITFacade(directory), directory, directory);
        programUnderTest = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;");
        LOGGER.debug("Initializing program.");
        facade.initializeConventionalStrategy(programUnderTest, 1);
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            String cacheFolderPath = System.getProperty("user.dir") + File.separator + "training" + File.separator + ".cache";
            LOGGER.debug("Deleting cache folder at " + cacheFolderPath);
            FileUtils.deleteDirectory(new File(cacheFolderPath));
        } catch (IOException ex) {
            LOGGER.error("Couldn't delete the cache folder.", ex);
        }
    }

    @Test
    public void testGetAllOperators() {
        LOGGER.debug("Testing method: testGetAllOperators");
        List<Operator> allOperators = facade.getAllOperators();
        assertNotNull(allOperators);
        assertEquals(17, allOperators.size());
    }

    @Test
    public void testExecuteOperator() {
        LOGGER.debug("Testing method: testExecuteOperator");
        IntegrationFacade.setIntegrationFacade(facade);

        List<Operator> allOperators = facade.getAllOperators();

        Operator operator1 = allOperators.get(0);
        List<Mutant> mutants = facade.executeOperator(operator1, programUnderTest);
        assertNotNull(mutants);
        assertEquals(13, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
        assertTrue(mutants.stream().allMatch((t) -> t.getOperator().equals(operator1)));
        assertTrue(operator1.getGeneratedMutants().containsAll(mutants));
        assertTrue(operator1.getCpuTime() > 0);
        assertTrue(operator1.getExecutionTime() > 0);

        facade.initializeConventionalStrategy(programUnderTest, 1);

        List<Operator> allOperators2 = facade.getAllOperators();

        Operator operator2 = allOperators2.get(0);
        List<Mutant> mutants2 = facade.executeOperator(operator2, programUnderTest);
        assertNotNull(mutants2);
        assertEquals(13, mutants2.size());
        assertTrue(mutants2.stream().allMatch(Mutant::isAlive));
        assertTrue(mutants2.stream().allMatch((t) -> t.getOperator().equals(operator2)));
        assertTrue(operator2.getGeneratedMutants().containsAll(mutants2));
        assertTrue(operator2.getCpuTime() > 0);
        assertTrue(operator2.getExecutionTime() > 0);

        assertEquals(operator1.getCpuTime(), operator2.getCpuTime(), 0.0001);
        assertEquals(operator1.getExecutionTime(), operator2.getExecutionTime(), 0.0001);
        assertArrayEquals(operator1.getGeneratedMutants().toArray(), operator2.getGeneratedMutants().toArray());
    }

    @Test
    public void testExecuteOperators() {
        LOGGER.debug("Testing method: testExecuteOperators");
        String directory = System.getProperty("user.dir") + File.separator + "training";
        IntegrationFacade facade = new CachedFacade(new PITFacade(directory), directory, directory);
        IntegrationFacade.setIntegrationFacade(facade);

        List<Operator> allOperators = facade.getAllOperators();

        List<Mutant> mutants = facade.executeOperators(allOperators, programUnderTest);
        assertNotNull(mutants);
        assertEquals(122, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));

        List<Operator> allOperators2 = facade.getAllOperators();

        List<Mutant> mutants2 = facade.executeOperators(allOperators2, programUnderTest);
        assertNotNull(mutants2);
        assertEquals(122, mutants2.size());
        assertTrue(mutants2.stream().allMatch(Mutant::isAlive));

        for (int i = 0; i < allOperators.size(); i++) {
            Operator operator1 = allOperators.get(i);
            Operator operator2 = allOperators2.get(i);

            assertEquals(operator1.getCpuTime(), operator2.getCpuTime(), 0.0001);
            assertEquals(operator1.getExecutionTime(), operator2.getExecutionTime(), 0.0001);
            assertArrayEquals(operator1.getGeneratedMutants().toArray(), operator2.getGeneratedMutants().toArray());
        }
        IntegrationFacade.setIntegrationFacade(this.facade);
    }

    @Test
    public void testExecuteOperators2() {
        LOGGER.debug("Testing method: testExecuteOperators2");
        IntegrationFacade.setIntegrationFacade(facade);

        List<Mutant> mutants = facade.executeOperators(new ArrayList<>(), programUnderTest);
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test(expected = Exception.class)
    public void testExecuteOperators4() {
        LOGGER.debug("Testing method: testExecuteOperators4");
        IntegrationFacade.setIntegrationFacade(facade);
        Program programUnderTest = new Program("unknown.Program", new File("unknown" + File.separator + "Program.java"));

        List<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testExecuteMutant() {
        LOGGER.debug("Testing method: testExecuteMutant");
        IntegrationFacade.setIntegrationFacade(facade);

        List<Operator> allOperators = facade.getAllOperators();

        List<Mutant> mutants = facade.executeOperators(allOperators, programUnderTest);
        assertNotNull(mutants);
        assertEquals(122, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));

        List<Operator> allOperators2 = facade.getAllOperators();

        List<Mutant> mutants2 = facade.executeOperators(allOperators2, programUnderTest);
        assertNotNull(mutants2);
        assertEquals(122, mutants2.size());
        assertTrue(mutants2.stream().allMatch(Mutant::isAlive));

        facade.executeMutant(mutants.get(0), programUnderTest);
        assertTrue(mutants.get(0).isDead());

        facade.executeMutant(mutants2.get(0), programUnderTest);
        assertTrue(mutants2.get(0).isDead());

        assertEquals(mutants.get(0).getCpuTime(), mutants.get(0).getCpuTime(), 0.001);
        assertEquals(mutants.get(0).getExecutionTime(), mutants.get(0).getExecutionTime(), 0.001);
        assertArrayEquals(mutants.get(0).getKillingTestCases().toArray(), mutants2.get(0).getKillingTestCases().toArray());

        facade.executeMutant(null, programUnderTest);
    }

    @Test
    public void testExecuteMutants() {
        LOGGER.debug("Testing method: testExecuteMutants");
        IntegrationFacade.setIntegrationFacade(facade);

        List<Operator> allOperators = facade.getAllOperators();

        List<Mutant> mutants = facade.executeOperators(allOperators, programUnderTest);
        assertNotNull(mutants);
        assertEquals(122, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));

        List<Operator> allOperators2 = facade.getAllOperators();

        List<Mutant> mutants2 = facade.executeOperators(allOperators2, programUnderTest);
        assertNotNull(mutants2);
        assertEquals(122, mutants2.size());
        assertTrue(mutants2.stream().allMatch(Mutant::isAlive));

        facade.executeMutants(mutants, programUnderTest);
        assertEquals(119, mutants.stream().filter(Mutant::isDead).count());
        assertEquals(3, mutants.stream().filter(Mutant::isAlive).count());

        facade.executeMutants(mutants2, programUnderTest);
        assertEquals(119, mutants2.stream().filter(Mutant::isDead).count());
        assertEquals(3, mutants2.stream().filter(Mutant::isAlive).count());

        for (int i = 0; i < mutants.size(); i++) {
            Mutant mutant1 = mutants.get(i);
            Mutant mutant2 = mutants2.get(i);

            assertEquals(mutant1.getCpuTime(), mutant2.getCpuTime(), 0.0001);
            assertEquals(mutant1.getExecutionTime(), mutant2.getExecutionTime(), 0.0001);
            assertArrayEquals(mutant1.getKillingTestCases().toArray(), mutant2.getKillingTestCases().toArray());
        }
    }

    @Test
    public void testInstantiateProgram() {
        LOGGER.debug("Testing method: testInstantiateProgram");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;br");
        assertNotNull(program);
        assertEquals("Triangle", program.getName());
        assertEquals(System.getProperty("user.dir") + File.separator + "training", program.getSourceFile().getAbsolutePath());
        assertEquals("br.ufpr.inf.gres.TriTyp*", program.getAttribute("targetClassesGlob"));
        assertEquals("br.ufpr.inf.gres.TriTypTest*", program.getAttribute("targetTestsGlob"));
        assertArrayEquals(new Object[]{System.getProperty("user.dir") + File.separator + "training" + File.separator + "br"}, ((List) program.getAttribute("classPath")).toArray());
    }

    @Test
    public void testInstantiateProgram2() {
        LOGGER.debug("Testing method: testInstantiateProgram2");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*");
        assertNotNull(program);
        assertEquals("Triangle", program.getName());
        assertEquals(System.getProperty("user.dir") + File.separator + "training", program.getSourceFile().getAbsolutePath());
        assertEquals("br.ufpr.inf.gres.TriTyp*", program.getAttribute("targetClassesGlob"));
        assertEquals("br.ufpr.inf.gres.TriTypTest*", program.getAttribute("targetTestsGlob"));
        assertTrue(((List) program.getAttribute("classPath")).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateProgram3() {
        LOGGER.debug("Testing method: testInstantiateProgram3");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*");
    }

}