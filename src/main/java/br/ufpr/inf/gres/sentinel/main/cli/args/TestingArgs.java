package br.ufpr.inf.gres.sentinel.main.cli.args;

import br.ufpr.inf.gres.sentinel.main.cli.converter.SeparatorConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author Giovani Guizzo
 */
@Parameters(commandDescription = "If Sentinel must be executed for executing strategies (testing).",
        commandNames = "test")
public class TestingArgs {

    @Parameter(names = {"--help", "-h"}, description = "Shows this message.")
    public boolean help = false;

    @Parameter(names = {
        "--facade", "--integrationFacade", "--tool", "--mutationTool", "-m"
    },
            description = "The tool used to effectively generate the mutants. Available options: \"PIT\", \"muJava\", \"HG4HOM\" (same as muJava).")
    public String facade = "PIT";

    @Parameter(names = {"--workingDirectory", "-w"},
            description = "The working directory of Sentinel.",
            converter = SeparatorConverter.class)
    public String workingDirectory = System.getProperty("user.dir");

    @Parameter(names = {
        "--grammar", "--grammarFile", "-g"
    },
            description = "The grammar file path (relative to the working directory) used to interpret the strategies.",
            converter = SeparatorConverter.class)
    public String grammarFilePath = "grammars/default_grammar_no_homs.bnf";

}