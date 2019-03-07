package org.csvfilterdistribution.csvfilterdistribution;

public class DistributionRecord {

	/*
	 * Properties ...
	 */
	private String configName = null;
	private String distribution = null;
	private String colNumberList = null;
	private String regularExpression = null;
	private Integer countDistributionPlanned = 0;
	private Integer countDistributed = 0;
	
	
	/*
	 * Getters and Setters ...
	 */
	
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getDistribution() {
		return distribution;
	}
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
	public String getColNumberList() {
		return colNumberList;
	}
	public void setColNumberList(String colNumberList) {
		this.colNumberList = colNumberList;
	}
	public String getRegularExpression() {
		return regularExpression;
	}
	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}
	public Integer getCountDistributionPlanned() {
		return countDistributionPlanned;
	}
	public void setCountDistributionPlanned(Integer countDistributionPlanned) {
		this.countDistributionPlanned = countDistributionPlanned;
	}
	public Integer getCountDistributed() {
		return countDistributed;
	}
	public void setCountDistributed(Integer countDistributed) {
		this.countDistributed = countDistributed;
	}

}
