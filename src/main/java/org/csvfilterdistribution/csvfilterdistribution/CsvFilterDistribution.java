package org.csvfilterdistribution.csvfilterdistribution;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.csvfilterdistribution.cli.CliArgsParser;

public class CsvFilterDistribution {
	
	/*
	 * Properties ...
	 */
	CliArgsParser cliArgsParser = null;
	ConfigFilterDistribution configFilterDistribution = new ConfigFilterDistribution();
	Integer countInputFile = null;
	

	/*
	 * Constructor 
	 */
	public CsvFilterDistribution(CliArgsParser cliArgsParser) throws Exception {
		super();
		
		// Setter cliArgsParser ...
		this.cliArgsParser = cliArgsParser;
		
	}
	
	
	/*
	 * executeFilterDistribution() 
	 */
	public void executeFilterDistribution() throws Exception {
				
		// Read config filter distribution file ...
		configFilterDistribution.readConfigFromFile(cliArgsParser.getConfigCsvFile());
		
		// Count input file lines ...
		readInputFileCount(cliArgsParser.getInputCsvFile());
		
		// Calculate distribution ...
		configFilterDistribution.calculateDistribution(this.getCountInputFile() - 1 /* Header */);
		
		// Read, Filter and Distribute input file ...
		readFilterDistribute(cliArgsParser.getInputCsvFile(), cliArgsParser.getOutputCsvFile());
		
		// Summary distribution ...
		configFilterDistribution.summaryDistribution();

	}
	
	
	/*
	 * Integer getCountInputFile()
	 */
	public Integer getCountInputFile() {
		return countInputFile;
	}


	/*
	 * setCountInputFile(countInputFile)
	 */
	public void setCountInputFile(Integer countInputFile) {
		this.countInputFile = countInputFile;
	}


	/*
	 * readInputFileCount(inputFile) - Read file to count number of rows ...
	 */
	public void readInputFileCount(String inputFile) throws Exception {
		System.out.println("Counting '%s' ...".replaceFirst("%s", inputFile));
		BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
		String csvLine;
		int currentLineNumber = 0;
		while((csvLine = bufferedReader.readLine()) != null) {
			 currentLineNumber++;
			 if ( 
						(currentLineNumber < 10)
						|| (currentLineNumber >= 10 && currentLineNumber < 100 && currentLineNumber % 10 == 0)
						|| (currentLineNumber >= 100 && currentLineNumber < 1000 && currentLineNumber % 100 == 0)
						|| (currentLineNumber >= 1000 && currentLineNumber < 10000 && currentLineNumber % 1000 == 0)
						|| (currentLineNumber >= 10000 && currentLineNumber < 100000 && currentLineNumber % 5000 == 0)
						|| (currentLineNumber >= 100000 && currentLineNumber % 10000 == 0)
					) {
					System.out.print("\rCounting - row: %s".replaceFirst("%s", String.valueOf(currentLineNumber) ));
			}
		}
		bufferedReader.close();
		System.out.print("\rCounting - row: %s".replaceFirst("%s", String.valueOf(currentLineNumber) ));
		System.out.println("");
		setCountInputFile(currentLineNumber);
	}
	
	
	/*
	 * readFilterDistribute() - Read, filter and distribute to output ...
	 */
	@SuppressWarnings("unused")
	public void readFilterDistribute(String inputFile, String outputFile) throws Exception {
		System.out.println("Reading, filtering and distributing '%s' ...".replaceFirst("%s", inputFile));
		BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(outputFile));
		String csvLine;
		int currentLineNumber = 0;
		while((csvLine = bufferedReader.readLine()) != null) {
			currentLineNumber++;
			if ( 
				(currentLineNumber < 10)
					|| (currentLineNumber >= 10 && currentLineNumber < 100 && currentLineNumber % 10 == 0)
					|| (currentLineNumber >= 100 && currentLineNumber < 1000 && currentLineNumber % 100 == 0)
					|| (currentLineNumber >= 1000 && currentLineNumber < 10000 && currentLineNumber % 1000 == 0)
					|| (currentLineNumber >= 10000 && currentLineNumber < 100000 && currentLineNumber % 5000 == 0)
					|| (currentLineNumber >= 100000 && currentLineNumber % 10000 == 0)
				) {
				System.out.print("\rReading, filtering and distributing - row: %s".replaceFirst("%s", String.valueOf(currentLineNumber) ));
			}
			if (currentLineNumber == 1 /* header*/ ) {
				// Write output (header) ...
				bufferedWriter.write(csvLine + "\n");
			} else {
				if (configFilterDistribution.isFilterDistribution(csvLine)) {
					// Write output data filtred distribution ...
					bufferedWriter.write(csvLine + "\n");
				}
			}
		}
		bufferedReader.close();
		bufferedWriter.close();
		System.out.print("\rReading, filtering and distributing - row: %s".replaceFirst("%s", String.valueOf(currentLineNumber) ));
		System.out.println("");
	}
	
		
}
