package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class AbstractGrammarMapperTest {

    private static String DEFAULT_GRAMMAR;
    private static StubMapper DEFAULT_MAPPER;
    private static String TEST_GRAMMAR;
    private static String TEST_GRAMMAR_2;

    @BeforeClass
    public static void setUpClass() {
        DEFAULT_GRAMMAR = GrammarFiles.getDefaultGrammarPath();
        TEST_GRAMMAR = GrammarFiles.getGrammarPathFromWorkingDirectory("src" + File.separator + "test" + File.separator + "resources" + File.separator + "testgrammar.bnf");
        TEST_GRAMMAR_2 = GrammarFiles.getGrammarPathFromWorkingDirectory("src" + File.separator + "test" + File.separator + "resources" + File.separator + "testgrammar2.bnf");
        try {
            DEFAULT_MAPPER = new StubMapper(DEFAULT_GRAMMAR);
        } catch (IOException ex) {
            Logger.getLogger(AbstractGrammarMapperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AbstractGrammarMapperTest() {
    }

    @Test
    public void constructorTest() throws IOException {
        StubMapper mapper = new StubMapper();
        assertNull(mapper.rootNode);
    }

    @Test
    public void constructorTest2() throws IOException {
        StubMapper mapper = new StubMapper(DEFAULT_GRAMMAR);
        assertNotNull(mapper.rootNode);
    }

    @Test
    public void constructorTest3() throws IOException {
        StubMapper mapper = new StubMapper(new File(DEFAULT_GRAMMAR));
        assertNotNull(mapper.rootNode);
    }

    @Test(expected = IOException.class)
    public void constructorTest4() throws IOException {
        new StubMapper("");
    }

    @Test
    public void loadGrammarTest() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        assertEquals(25, nodes.size());
        assertNotNull(nodes.get(NonTerminalRuleType.STRATEGY));
        assertNotNull(nodes.get(NonTerminalRuleType.DEFAULT_OPERATION));
        assertNotNull(nodes.get(NonTerminalRuleType.SELECTION_TYPE));
        assertNotNull(nodes.get(NonTerminalRuleType.SORTING_DIRECTION));
        assertNotNull(nodes.get(NonTerminalRuleType.QUANTITY));
        assertNotNull(nodes.get(NonTerminalRuleType.PERCENTAGE));
        assertNotNull(nodes.get(NonTerminalRuleType.OPERATOR_OPERATION));
        assertNotNull(nodes.get(NonTerminalRuleType.SELECT_OPERATORS));
        assertNotNull(nodes.get(NonTerminalRuleType.OPERATOR_SELECTION_TYPE));
        assertNotNull(nodes.get(NonTerminalRuleType.OPERATOR_SORTING));
        assertNotNull(nodes.get(NonTerminalRuleType.OPERATOR_GROUP_SELECTION_TYPE));
        assertNotNull(nodes.get(NonTerminalRuleType.OPERATOR_GROUPING));
        assertNotNull(nodes.get(NonTerminalRuleType.OPERATOR_GROUP_SORTING));
        assertNotNull(nodes.get(NonTerminalRuleType.OPERATOR_ATTRIBUTE));
        assertNotNull(nodes.get(NonTerminalRuleType.OPERATOR_GROUP_ATTRIBUTE));
        assertNotNull(nodes.get(NonTerminalRuleType.OPERATOR_EXECUTION_TYPE));
        assertNotNull(nodes.get(NonTerminalRuleType.SELECT_MUTANTS));
        assertNotNull(nodes.get(NonTerminalRuleType.MUTANT_OPERATION));
        assertNotNull(nodes.get(NonTerminalRuleType.SELECT_MUTANTS));
        assertNotNull(nodes.get(NonTerminalRuleType.MUTANT_SELECTION_TYPE));
        assertNotNull(nodes.get(NonTerminalRuleType.MUTANT_SORTING));
        assertNotNull(nodes.get(NonTerminalRuleType.MUTANT_GROUP_SELECTION_TYPE));
        assertNotNull(nodes.get(NonTerminalRuleType.MUTANT_GROUPING));
        assertNotNull(nodes.get(NonTerminalRuleType.MUTANT_GROUP_SORTING));
        assertNotNull(nodes.get(NonTerminalRuleType.MUTANT_ATTRIBUTE));
        assertNotNull(nodes.get(NonTerminalRuleType.MUTANT_GROUP_ATTRIBUTE));
    }

    @Test
    public void loadGrammarTest10() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.OPERATOR_SELECTION_TYPE);
        assertEquals(2, node.getOptions().size());
        assertEquals(3, node.getOption(0).getRules().size());
        assertEquals(3, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest11() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.OPERATOR_SORTING);
        assertEquals(2, node.getOptions().size());
        assertEquals(2, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest13() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.OPERATOR_GROUP_SELECTION_TYPE);
        assertEquals(1, node.getOptions().size());
        assertEquals(3, node.getOption(0).getRules().size());
    }

    @Test
    public void loadGrammarTest14() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.OPERATOR_GROUPING);
        assertEquals(1, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
    }

    @Test
    public void loadGrammarTest15() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.OPERATOR_GROUP_SORTING);
        assertEquals(2, node.getOptions().size());
        assertEquals(2, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest16() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.OPERATOR_ATTRIBUTE);
        assertEquals(2, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest18() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.OPERATOR_GROUP_ATTRIBUTE);
        assertEquals(2, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest19() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.OPERATOR_EXECUTION_TYPE);
        assertEquals(1, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
    }

    @Test
    public void loadGrammarTest2() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.STRATEGY);
        assertEquals(1, node.getOptions().size());
        assertEquals(2, node.getOption(0).getRules().size());
    }

    @Test
    public void loadGrammarTest20() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.MUTANT_OPERATION);
        assertEquals(2, node.getOptions().size());
        assertEquals(2, node.getOption(0).getRules().size());
        assertEquals(2, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest21() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.SELECT_MUTANTS);
        assertEquals(2, node.getOptions().size());
        assertEquals(2, node.getOption(0).getRules().size());
        assertEquals(2, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest22() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.MUTANT_SELECTION_TYPE);
        assertEquals(2, node.getOptions().size());
        assertEquals(3, node.getOption(0).getRules().size());
        assertEquals(3, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest23() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.MUTANT_SORTING);
        assertEquals(2, node.getOptions().size());
        assertEquals(2, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest25() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.MUTANT_GROUP_SELECTION_TYPE);
        assertEquals(1, node.getOptions().size());
        assertEquals(3, node.getOption(0).getRules().size());
    }

    @Test
    public void loadGrammarTest26() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.MUTANT_GROUPING);
        assertEquals(1, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
    }

    @Test
    public void loadGrammarTest27() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.MUTANT_GROUP_SORTING);
        assertEquals(2, node.getOptions().size());
        assertEquals(2, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest28() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.MUTANT_ATTRIBUTE);
        assertEquals(2, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest29() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.MUTANT_GROUP_ATTRIBUTE);
        assertEquals(1, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
    }

    @Test
    public void loadGrammarTest3() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.DEFAULT_OPERATION);
        assertEquals(4, node.getOptions().size());
        assertEquals(2, node.getOption(0).getRules().size());
        assertEquals(2, node.getOption(1).getRules().size());
        assertEquals(3, node.getOption(2).getRules().size());
        assertEquals(1, node.getOption(3).getRules().size());
    }

    @Test
    public void loadGrammarTest4() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.SELECTION_TYPE);
        assertEquals(3, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
        assertEquals(1, node.getOption(2).getRules().size());
    }

    @Test
    public void loadGrammarTest5() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.SORTING_DIRECTION);
        assertEquals(2, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
    }

    @Test
    public void loadGrammarTest6() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.QUANTITY);
        assertEquals(10, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
        assertEquals(1, node.getOption(2).getRules().size());
        assertEquals(1, node.getOption(3).getRules().size());
        assertEquals(1, node.getOption(4).getRules().size());
        assertEquals(1, node.getOption(5).getRules().size());
        assertEquals(1, node.getOption(6).getRules().size());
        assertEquals(1, node.getOption(7).getRules().size());
        assertEquals(1, node.getOption(8).getRules().size());
        assertEquals(1, node.getOption(9).getRules().size());
    }

    @Test
    public void loadGrammarTest7() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.PERCENTAGE);
        assertEquals(10, node.getOptions().size());
        assertEquals(1, node.getOption(0).getRules().size());
        assertEquals(1, node.getOption(1).getRules().size());
        assertEquals(1, node.getOption(2).getRules().size());
        assertEquals(1, node.getOption(3).getRules().size());
        assertEquals(1, node.getOption(4).getRules().size());
        assertEquals(1, node.getOption(5).getRules().size());
        assertEquals(1, node.getOption(6).getRules().size());
        assertEquals(1, node.getOption(7).getRules().size());
        assertEquals(1, node.getOption(8).getRules().size());
        assertEquals(1, node.getOption(9).getRules().size());
    }

    @Test
    public void loadGrammarTest8() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.OPERATOR_OPERATION);
        assertEquals(3, node.getOptions().size());
        assertEquals(2, node.getOption(0).getRules().size());
        assertEquals(2, node.getOption(1).getRules().size());
        assertEquals(3, node.getOption(2).getRules().size());
    }

    @Test
    public void loadGrammarTest9() throws IOException {
        HashMap<String, Rule> nodes = DEFAULT_MAPPER.nonTerminalNodes;
        Rule node = nodes.get(NonTerminalRuleType.SELECT_OPERATORS);
        assertEquals(2, node.getOptions().size());
        assertEquals(2, node.getOption(0).getRules().size());
        assertEquals(2, node.getOption(1).getRules().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void loadGrammarTestIllegal() throws IOException {
        StubMapper stubMapper = new StubMapper(TEST_GRAMMAR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void loadGrammarTestIllegal2() throws IOException {
        StubMapper stubMapper = new StubMapper(TEST_GRAMMAR_2);
    }

    @Test
    public void testGetRootNode() {
        assertNotNull(DEFAULT_MAPPER.getRootNode());
        assertEquals("<strategy>", DEFAULT_MAPPER.getRootNode().toString());
    }

    private static class StubMapper extends AbstractGrammarMapper<String> {

        public StubMapper() {
        }

        public StubMapper(String file) throws IOException {
            super(file);
        }

        public StubMapper(File file) throws IOException {
            super(file);
        }

        @Override
        protected String hookInterpret(Iterator integerIterable) {
            return "This does nothing!";
        }
    }
}
