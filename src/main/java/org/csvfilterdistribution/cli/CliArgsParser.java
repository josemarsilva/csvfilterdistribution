package org.csvfilterdistribution.cli;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;


/*
 * CliArgsParser class is responsible for extract, compile and check consistency
 * for command line arguments passed as parameters in command line
 * 
 * @author josemarsilva
 * @date   2019-03-05
 * 
 */

public class CliArgsParser {

	// Constants ...
	public final static String APP_NAME = new String("csvfilterdistribution");
	public final static String APP_VERSION = new String("v.2019.03.07");
	public final static String APP_USAGE = new String(APP_NAME + " [<args-options-list>] - "+ APP_VERSION);

	// Constants defaults ...

	// Constants Error Messages ...
	public final static String MSG_ERROR_FILE_NOT_FOUND = new String("Erro: arquivo '%s' não existe !");
	
	// Properties ...
    private String inputCsvFile = new String("");
    private String configCsvFile = new String("");
    private String outputCsvFile = new String("");


    /*
     * CliArgsParser(args) - Constructor ...
     */
	public CliArgsParser( String[] args ) {

		// Options creating ...
		Options options = new Options();
		
		
		// Options configuring  ...
        Option helpOption = Option.builder("h") 
        		.longOpt("help") 
        		.required(false) 
        		.desc("shows usage help message. See more https://github.com/josemarsilva/csvfilterdistribution") 
        		.build(); 
        Option inputCsvFileOption = Option.builder("i")
        		.longOpt("input-csv-file") 
        		.required(true) 
        		.desc("Nome do arquivo (.csv) de entrada do processo de filtro e distribuicao. Ex: input.csv")
        		.hasArg()
        		.build();
        Option configCsvFileOption = Option.builder("c")
        		.longOpt("config-csv-file") 
        		.required(true) 
        		.desc("Nome do arquivo (.csv) de configuracao do filtro de distribuicao. Ex: config.csv")
        		.hasArg()
        		.build();
        Option outputCsvFileOption = Option.builder("o")
        		.longOpt("output-csv-file") 
        		.required(true) 
        		.desc("Nome do arquivo (.csv) de configuracao do filtro de distribuicao. Ex: config.csv")
        		.hasArg()
        		.build();
        
		// Options adding configuration ...
        options.addOption(helpOption);
        options.addOption(inputCsvFileOption);
        options.addOption(configCsvFileOption);
        options.addOption(outputCsvFileOption);
        
        
        // CommandLineParser ...
        CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmdLine = parser.parse(options, args);
			
	        if (cmdLine.hasOption("help")) { 
	            HelpFormatter formatter = new HelpFormatter();
	            formatter.printHelp(APP_USAGE, options);
	            System.exit(0);
	        } else {
	        	
	        	// Set properties from Options ...
	        	this.setInputCsvFile( cmdLine.getOptionValue("input-csv-file", "") );
	        	this.setConfigCsvFile( cmdLine.getOptionValue("config-csv-file", "") );
	        	this.setOutputCsvFile( cmdLine.getOptionValue("output-csv-file", "") );
	        	
	        	// Check arguments Options ...
	        	try {
	        		checkArgumentOptions();
	        	} catch (Exception e) {
	    			System.err.println(e.getMessage());
	    			System.exit(-1);
	        	}
	        	
	        }
			
		} catch (ParseException e) {
			System.err.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter(); 
            formatter.printHelp(APP_USAGE, options); 
			System.exit(-1);
		} 
        
	}

	//
	private void checkArgumentOptions() throws Exception {
		
		// Check input file arguments must exists ...
		if (!(new File(inputCsvFile).exists())) {
			throw new Exception(MSG_ERROR_FILE_NOT_FOUND.replaceFirst("%s", inputCsvFile));
		} else if (!(new File(configCsvFile).exists())) {
			throw new Exception(MSG_ERROR_FILE_NOT_FOUND.replaceFirst("%s", configCsvFile));
		}
		
	}

	
	// Generate Getters and Setters - Begin ...

	public String getInputCsvFile() {
		return inputCsvFile;
	}

	public void setInputCsvFile(String inputCsvFile) {
		this.inputCsvFile = inputCsvFile;
	}

	public String getConfigCsvFile() {
		return configCsvFile;
	}

	public void setConfigCsvFile(String configCsvFile) {
		this.configCsvFile = configCsvFile;
	}

	public String getOutputCsvFile() {
		return outputCsvFile;
	}

	public void setOutputCsvFile(String outputCsvFile) {
		this.outputCsvFile = outputCsvFile;
	}
	
	// Generate Getters and Setters - End.


}
