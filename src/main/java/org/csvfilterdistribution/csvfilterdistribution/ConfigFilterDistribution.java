
package org.csvfilterdistribution.csvfilterdistribution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import java.util.regex.*;  

public class ConfigFilterDistribution {
	
	/*
	 * Properties ...
	 */
	private HashMap<String,Integer> mapCsvHeaderExpected = new HashMap<String,Integer>();
	private List<DistributionRecord> listDistribution = new ArrayList<DistributionRecord>();

	
	/*
	 * Constants ...
	 */
	private final String CSV_HEADER_EXPECTED_CONFIG_NAME = new String("config-name");
	private final String CSV_HEADER_EXPECTED_DISTRIBUTION = new String("distribution");
	private final String CSV_HEADER_EXPECTED_COL_NUMBER_LIST = new String("col-number-list");
	private final String CSV_HEADER_EXPECTED_REGULAR_EXPRESSION = new String("regular-expression");
	private final String CSV_HEADER_EXPECTED[] = {CSV_HEADER_EXPECTED_CONFIG_NAME, CSV_HEADER_EXPECTED_DISTRIBUTION, CSV_HEADER_EXPECTED_COL_NUMBER_LIST, CSV_HEADER_EXPECTED_REGULAR_EXPRESSION};
	private final String MSG_ERROR_CONFIG_CSV_HEADER_EXPECTED = new String("Erro: Arquivo de configuracao nao contem coluna obrigatoria no cabecalho '%s'");
	private final String MSG_ERROR_CONFIG_CSV_DATA_INVALID = new String("Erro: Arquivo de configuracao - linha %s, coluna '%s' contem valor invalido! %s");
	private final String MSG_ERROR_DATA_VALUENOTFOUND = new String("valor nao encontrado entre separador ';'");
	private final String MSG_ERROR_DATA_VALUEISEMPTY = new String("valor vazio ou nulo");
	private final String MSG_ERROR_DATA_VALUEDISTRIB = new String("valor '%s' invalido para percentual distribuicao");
	
	
	/*
	 * readConfigFromFile(csvConfigFile) - Reads configuration from file
	 */
	public void readConfigFromFile(String csvConfigFile) throws Exception {
		System.out.println("Reading '%s' ...".replaceFirst("%s", csvConfigFile));
		BufferedReader bufferedReader = new BufferedReader(new FileReader(csvConfigFile));
		String csvLine;
		int currentLineNumber = 0;
		while((csvLine = bufferedReader.readLine()) != null)
		{
			currentLineNumber++;
			if (currentLineNumber==1) {
				 checkCsvHeaderExpected(csvLine);
			} else {
				 extractCsvData(csvLine, currentLineNumber);
			}
		}
		bufferedReader.close();
	}

	
	/*
	 * checkCsvHeaderExpected(csvHeader) - Is (.csv) header complete
	 */
	private void checkCsvHeaderExpected(String csvHeader) throws Exception {
		String[] csvHeaderParts = csvHeader.split(";");
		for(int i=0;i<CSV_HEADER_EXPECTED.length;i++) {
			boolean missing = true;
			for (int j=0;j<csvHeaderParts.length;j++) {
				if (csvHeaderParts[j]!=null) {
					if (csvHeaderParts[j].equals(CSV_HEADER_EXPECTED[i])) {
						missing = false;
						mapCsvHeaderExpected.put(CSV_HEADER_EXPECTED[i], j);
						break; // j
					}
				}
			}
			if (missing) {
				// Raise error message
				throw new Exception(MSG_ERROR_CONFIG_CSV_HEADER_EXPECTED.replaceFirst("%s", CSV_HEADER_EXPECTED[i]));
			}
		}
	}
	
	
	/*
	 * extractCsvData(csvData) - Extract (.csv) data
	 */
	private void extractCsvData(String csvData, int rowNumber) throws Exception {
		
		// Split line into parts separated by ";" ...
		String[] csvDataParts = csvData.split(";");
		
		// Record ...
		DistributionRecord distributionRecord = new DistributionRecord(); 

		
		// CSV_HEADER_EXPECTED_CONFIG_NAME
		if (mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_CONFIG_NAME)<csvDataParts.length) {
			if (!StringUtils.isEmpty(csvDataParts[mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_CONFIG_NAME)])) {
				distributionRecord.setConfigName(csvDataParts[mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_CONFIG_NAME)]);
			} else {
				distributionRecord.setConfigName("row #" + Integer.toString(rowNumber));
			}
		} else {
			// Raise error message
			throw new Exception(MSG_ERROR_CONFIG_CSV_DATA_INVALID.replaceFirst("%s", Integer.toString(rowNumber)).replaceFirst("%s", CSV_HEADER_EXPECTED_CONFIG_NAME).replaceFirst("%s", MSG_ERROR_DATA_VALUENOTFOUND));
		}
		
		// CSV_HEADER_EXPECTED_DISTRIBUTION
		if (mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_DISTRIBUTION)<csvDataParts.length) {
			distributionRecord.setDistribution(csvDataParts[mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_DISTRIBUTION)]);
		} else {
			// Raise error message
			throw new Exception(MSG_ERROR_CONFIG_CSV_DATA_INVALID.replaceFirst("%s", Integer.toString(rowNumber)).replaceFirst("%s", CSV_HEADER_EXPECTED_DISTRIBUTION).replaceFirst("%s", MSG_ERROR_DATA_VALUENOTFOUND));
		}
		if (StringUtils.isEmpty(csvDataParts[mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_DISTRIBUTION)])) {
			// Raise error message
			throw new Exception(MSG_ERROR_CONFIG_CSV_DATA_INVALID.replaceFirst("%s", Integer.toString(rowNumber)).replaceFirst("%s", CSV_HEADER_EXPECTED_DISTRIBUTION).replaceFirst("%s", MSG_ERROR_DATA_VALUEISEMPTY));
		}
		try {
			double value = Double.parseDouble(csvDataParts[mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_DISTRIBUTION)]);
		} catch (Exception e) {
			// Raise error message
			throw new Exception(MSG_ERROR_CONFIG_CSV_DATA_INVALID.replaceFirst("%s", Integer.toString(rowNumber)).replaceFirst("%s", CSV_HEADER_EXPECTED_DISTRIBUTION).replaceFirst("%s",MSG_ERROR_DATA_VALUEDISTRIB).replaceFirst("%", csvDataParts[mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_DISTRIBUTION)]));
		}
		
		// CSV_HEADER_EXPECTED_COL_NUMBER_LIST
		if (mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_COL_NUMBER_LIST)<csvDataParts.length) {
			distributionRecord.setColNumberList(csvDataParts[mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_COL_NUMBER_LIST)]);
		} else {
			// Raise error message
			throw new Exception(MSG_ERROR_CONFIG_CSV_DATA_INVALID.replaceFirst("%s", Integer.toString(rowNumber)).replaceFirst("%s", CSV_HEADER_EXPECTED_COL_NUMBER_LIST).replaceFirst("%s", MSG_ERROR_DATA_VALUENOTFOUND));
		}
		if (StringUtils.isEmpty(csvDataParts[mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_COL_NUMBER_LIST)])) {
			// Raise error message
			throw new Exception(MSG_ERROR_CONFIG_CSV_DATA_INVALID.replaceFirst("%s", Integer.toString(rowNumber)).replaceFirst("%s", CSV_HEADER_EXPECTED_COL_NUMBER_LIST).replaceFirst("%s", MSG_ERROR_DATA_VALUEISEMPTY));
		}
		
		// CSV_HEADER_EXPECTED_REGULAR_EXPRESSION
		if (mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_REGULAR_EXPRESSION)<csvDataParts.length) {
			distributionRecord.setRegularExpression(csvDataParts[mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_REGULAR_EXPRESSION)]);
		} else {
			// Raise error message
			throw new Exception(MSG_ERROR_CONFIG_CSV_DATA_INVALID.replaceFirst("%s", Integer.toString(rowNumber)).replaceFirst("%s", CSV_HEADER_EXPECTED_REGULAR_EXPRESSION).replaceFirst("%s", MSG_ERROR_DATA_VALUENOTFOUND));
		}
		if (StringUtils.isEmpty(csvDataParts[mapCsvHeaderExpected.get(CSV_HEADER_EXPECTED_REGULAR_EXPRESSION)])) {
			// Raise error message
			throw new Exception(MSG_ERROR_CONFIG_CSV_DATA_INVALID.replaceFirst("%s", Integer.toString(rowNumber)).replaceFirst("%s", CSV_HEADER_EXPECTED_REGULAR_EXPRESSION).replaceFirst("%s", MSG_ERROR_DATA_VALUEISEMPTY));
		}
		
		// add to array list ...
		listDistribution.add(distributionRecord);
				
	}


	/*
	 * calculateDistribution() - Calculate distribution
	 */
	public void calculateDistribution(Integer rowsCountFile) {
		System.out.println("Calculating distribution ...");
		for(DistributionRecord item : listDistribution) {
			double distribution = Double.parseDouble(item.getDistribution());
			String fmtDistribution = (new DecimalFormat("###.#####").format(distribution*100)).replaceAll(",",".");
			int countDistributionPlanned = (int) Math.round(distribution * rowsCountFile);
			countDistributionPlanned = (countDistributionPlanned==0) ? 1 : countDistributionPlanned;
			item.setCountDistributionPlanned(countDistributionPlanned);
			String fmtRowsCountDistribution = Integer.toString(countDistributionPlanned);
		}
	}


	/*
	 * summaryDistribution
	 */
	public void summaryDistribution() {
		System.out.println("Summary filter distribution ...");
		System.out.println("  ConfigName                     %          Planned    Distributed");
		System.out.println("  ------------------------------ ---------- ---------- ------------");
		for(DistributionRecord item : listDistribution) {
			double distribution = Double.parseDouble(item.getDistribution());
			String fmtDistribution = (new DecimalFormat("###.#####").format(distribution*100)).replaceAll(",",".");
			String fmtCountDistributionPlanned = Integer.toString( item.getCountDistributionPlanned() );
			String fmtCountDistributed = Integer.toString( item.getCountDistributed() );
			System.out.println("  " 
					+ item.getConfigName() + StringUtils.repeat(" ", 30 - item.getConfigName().length())
					+ " " + fmtDistribution + "%" + StringUtils.repeat(" ", 10 - 1 - fmtDistribution.length())
					+ " " + fmtCountDistributionPlanned + StringUtils.repeat(" ", 10 - fmtCountDistributionPlanned.length())
					+ " " + fmtCountDistributed + StringUtils.repeat(" ", 10 - fmtCountDistributed.length())
			);
		}
		
	}


	/*
	 * isFilterDistribution(csvLine)
	 */
	public boolean isFilterDistribution(String csvLine) {
		boolean bRet = false;
		String[] csvLineParts = csvLine.split(";");
		for(DistributionRecord item : listDistribution) {
			// Get col-number-list and build value to compare ...
			String[] colNumberListParts = item.getColNumberList().split("-");
			String value = new String("");
			for (int i=0;i<colNumberListParts.length;i++) {
				if (colNumberListParts[i]!=null) {
					if ( Integer.parseInt(colNumberListParts[i]) -1 < csvLineParts.length) {
						value = value.concat(csvLineParts[Integer.parseInt(colNumberListParts[i]) -1]);
					}
				}
			}
			// Get regular expression ...
			String regularExpression = item.getRegularExpression();
			if (!StringUtils.isEmpty(regularExpression)) {
				// Test if Matches regular expression pattern ...
				if (Pattern.compile(regularExpression).matcher(value).matches()) {
					int countDistributionPlanned = item.getCountDistributionPlanned();
					int countDistributed = item.getCountDistributed();
					if (countDistributed < countDistributionPlanned) {
						bRet = true;
						countDistributed ++;
						item.setCountDistributed(countDistributed); // countDistributed ++
					}
				}
			}
		}
		return bRet;
	}
	

	
}
