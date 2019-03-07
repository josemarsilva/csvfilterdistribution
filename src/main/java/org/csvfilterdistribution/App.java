package org.csvfilterdistribution;

import org.csvfilterdistribution.cli.CliArgsParser;
import org.csvfilterdistribution.csvfilterdistribution.CsvFilterDistribution;

/**
 * App static main()
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	
		// Parser Command Line Arguments ...
		CliArgsParser cliArgsParser = new CliArgsParser(args);
		
		// Create CsvFilterDistribution instance ...
		CsvFilterDistribution csvFilterDistribution = new CsvFilterDistribution(cliArgsParser);

		// Execute filter distribution ...
		csvFilterDistribution.executeFilterDistribution();

    }
}
