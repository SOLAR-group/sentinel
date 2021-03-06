package br.ufpr.inf.gres.sentinel.integration.pit;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.cache.CachedFacadeTest;
import br.ufpr.inf.gres.sentinel.util.TestPrograms;
import com.google.common.base.Stopwatch;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class PITFacadeTest {
    
    private static final Logger LOGGER = LogManager.getLogger(PITFacadeTest.class);

    private static PITFacade facade;
    private static Program programUnderTest;

    @BeforeClass
    public static void setUpClass() {
        facade = new PITFacade(System.getProperty("user.dir"));
        programUnderTest = facade.instantiateProgram(TestPrograms.TRIANGLE);
    }

    @Test
    @Ignore
    public void testJodaExecution() {
        LOGGER.debug("Testing method: testJodaExecution");
        PITFacade facade = new PITFacade("training");
        Program programUnderTest = facade.instantiateProgram("joda-time-2.8;src/main/java;org.joda.time.*;org.joda.time.TestAll;target/classes;target/test-classes;joda-convert-1.2.jar");
        IntegrationFacade.setIntegrationFacade(facade);

        Stopwatch stopwatch = Stopwatch.createStarted();
        Collection<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        facade.executeMutants(mutants, programUnderTest);
        stopwatch.stop();

        System.out.println("Time: " + stopwatch.elapsed(TimeUnit.MINUTES) + "m" + (stopwatch.elapsed(TimeUnit.SECONDS) % 60) + "s");
        System.out.println("Size: " + mutants.size());
        System.out.println("Dead: " + mutants.stream().filter(Mutant::isDead).count());
        System.out.println("Alive: " + mutants.stream().filter(Mutant::isAlive).count());
    }

    @Test
    public void testInitializeProgram() {
        LOGGER.debug("Testing method: testInitializeProgram");
        PITFacade facade = new PITFacade(System.getProperty("user.dir"));
        IntegrationFacade.setIntegrationFacade(facade);

        facade.initializeConventionalStrategy(programUnderTest, 1);
        int size = facade.generatedMutants.get(programUnderTest).keySet().size();
        assertEquals(50, size);
        assertEquals(50, facade.generatedMutants.get(programUnderTest).keySet().stream().filter(Mutant::isDead).count());
        assertEquals(1, facade.unitToMutants.get(programUnderTest).keySet().size());
        assertEquals(1, facade.unitToMutants.get(programUnderTest).values().size());
        assertEquals(50, facade.unitToMutants.get(programUnderTest).values().iterator().next().stream().filter(Mutant::isDead).count());
    }

    @Test
    public void testExecuteMutant() {
        LOGGER.debug("Testing method: testExecuteMutant");
        IntegrationFacade.setIntegrationFacade(facade);

        List<Mutant> mutants = new ArrayList<>(facade.executeOperators(facade.getAllOperators(), programUnderTest));
        assertNotNull(mutants);

        facade.executeMutant(mutants.get(0), programUnderTest);
        assertTrue(mutants.get(0).isDead());

        facade.executeMutant(mutants.get(1), programUnderTest);
        assertTrue(mutants.get(1).isDead());

        facade.executeMutant(mutants.get(2), programUnderTest);
        assertTrue(mutants.get(2).isDead());

        facade.executeMutant(null, programUnderTest);
    }

    @Test
    public void testExecuteMutants() {
        LOGGER.debug("Testing method: testExecuteMutants");
        IntegrationFacade.setIntegrationFacade(facade);

        Collection<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);

        facade.executeMutants(mutants, programUnderTest);
        assertEquals(50, mutants.stream().filter(Mutant::isDead).count());
        assertEquals(0, mutants.stream().filter(Mutant::isAlive).count());
    }

    @Test
    public void testExecuteOperator() {
        LOGGER.debug("Testing method: testExecuteOperator");
        IntegrationFacade.setIntegrationFacade(facade);
        List<Operator> allOperators = new ArrayList<>(facade.getAllOperators());

        Collection<Mutant> mutants = facade.executeOperator(allOperators.get(0), programUnderTest);
        assertNotNull(mutants);
        assertEquals(13, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
        assertTrue(mutants.stream().allMatch((t) -> t.getOperator().equals(allOperators.get(0))));
        assertTrue(allOperators.get(0).getGeneratedMutants().containsAll(mutants));
    }

    @Test
    public void testExecuteOperator2() {
        LOGGER.debug("Testing method: testExecuteOperator2");
        IntegrationFacade.setIntegrationFacade(facade);

        Collection<Mutant> mutants = facade.executeOperator(new ArrayList<>(facade.getAllOperators()).get(6), programUnderTest);
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testExecuteOperators() {
        LOGGER.debug("Testing method: testExecuteOperators");
        IntegrationFacade.setIntegrationFacade(facade);

        Collection<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);
        assertEquals(50, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
    }

    @Test
    public void testExecuteOperators2() {
        LOGGER.debug("Testing method: testExecuteOperators2");
        IntegrationFacade.setIntegrationFacade(facade);

        Collection<Mutant> mutants = facade.executeOperators(new ArrayList<>(), programUnderTest);
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testExecuteOperators3() {
        LOGGER.debug("Testing method: testExecuteOperators3");
        IntegrationFacade.setIntegrationFacade(facade);

        Collection<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);
        assertEquals(50, mutants.size());
        assertTrue(mutants.stream().allMatch(Mutant::isAlive));
    }

    @Test(expected = Exception.class)
    public void testExecuteOperators4() {
        LOGGER.debug("Testing method: testExecuteOperators4");
        IntegrationFacade.setIntegrationFacade(facade);
        Program programUnderTest = new Program("unknown.Program", new File("unknown" + File.separator + "Program.java"));

        Collection<Mutant> mutants = facade.executeOperators(facade.getAllOperators(), programUnderTest);
        assertNotNull(mutants);
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void testGetAllOperators() {
        LOGGER.debug("Testing method: testGetAllOperators");
        Collection<Operator> allOperators = facade.getAllOperators();
        assertNotNull(allOperators);
        assertEquals(7, allOperators.size());
    }

    @Test
    public void testInstantiateProgram() {
        LOGGER.debug("Testing method: testInstantiateProgram");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;;br");
        assertNotNull(program);
        assertEquals("Triangle", program.getName());
        assertEquals(System.getProperty("user.dir"), program.getSourceFile().getAbsolutePath());
        assertEquals("br.ufpr.inf.gres.TriTyp*", program.getAttribute("targetClassesGlob"));
        assertEquals("br.ufpr.inf.gres.TriTypTest*", program.getAttribute("targetTestsGlob"));
        assertEquals("", program.getAttribute("excludedClassesGlob"));
        assertArrayEquals(new Object[]{System.getProperty("user.dir") + File.separator + "br"}, ((List) program.getAttribute("classPath")).toArray());
    }

    @Test
    public void testInstantiateProgram2() {
        LOGGER.debug("Testing method: testInstantiateProgram2");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;test.**.testagain,exclude.**.TestingClass,test");
        assertNotNull(program);
        assertEquals("Triangle", program.getName());
        assertEquals(System.getProperty("user.dir"), program.getSourceFile().getAbsolutePath());
        assertEquals("br.ufpr.inf.gres.TriTyp*", program.getAttribute("targetClassesGlob"));
        assertEquals("br.ufpr.inf.gres.TriTypTest*", program.getAttribute("targetTestsGlob"));
        assertEquals("test.**.testagain,exclude.**.TestingClass,test", program.getAttribute("excludedClassesGlob"));
        assertTrue(((List) program.getAttribute("classPath")).isEmpty());
    }

    @Test
    public void testInstantiateProgram5() {
        LOGGER.debug("Testing method: testInstantiateProgram5");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;test.**.testagain,exclude.**.TestingClass,test;src/test/resources");
        assertNotNull(program);
        assertEquals("Triangle", program.getName());
        assertEquals(System.getProperty("user.dir"), program.getSourceFile().getAbsolutePath());
        assertEquals("br.ufpr.inf.gres.TriTyp*", program.getAttribute("targetClassesGlob"));
        assertEquals("br.ufpr.inf.gres.TriTypTest*", program.getAttribute("targetTestsGlob"));
        assertEquals("test.**.testagain,exclude.**.TestingClass,test", program.getAttribute("excludedClassesGlob"));
        assertArrayEquals(new Object[]{
            System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "testjar.jar",
            System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "testzip.zip",
            System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources"},
                ((List) program.getAttribute("classPath")).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateProgram3() {
        LOGGER.debug("Testing method: testInstantiateProgram3");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstantiateProgram4() {
        LOGGER.debug("Testing method: testInstantiateProgram4");
        Program program = facade.instantiateProgram("Triangle;;br.ufpr.inf.gres.TriTyp*;test");
    }

}
