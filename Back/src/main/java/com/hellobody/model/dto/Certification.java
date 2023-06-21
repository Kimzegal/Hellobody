package com.hellobody.model.dto;

public class Certification {

	private String exName;
	private String exUnit;
	private String score;
	private double cRate;
	private String exDef;
	private String exStd;
	
	
	public Certification() {
	}
	
	public Certification(String exName, String exUnit, String score, double cRate, String exDef, String exStd) {
		this.exName = exName;
		this.exUnit = exUnit;
		this.score = score;
		this.cRate = cRate;
		this.exDef = exDef;
		this.exStd = exStd;
	}

	public String getExName() {
		return exName;
	}

	public void setExName(String exName) {
		this.exName = exName;
	}

	public String getExUnit() {
		return exUnit;
	}

	public void setExUnit(String exUnit) {
		this.exUnit = exUnit;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public double getcRate() {
		return cRate;
	}

	public void setcRate(double cRate) {
		this.cRate = cRate;
	}

	public String getExDef() {
		return exDef;
	}

	public void setExDef(String exDef) {
		this.exDef = exDef;
	}

	public String getExStd() {
		return exStd;
	}

	public void setExStd(String exStd) {
		this.exStd = exStd;
	}

	@Override
	public String toString() {
		return "Certification [exName=" + exName + ", exUnit=" + exUnit + ", score=" + score + ", cRate=" + cRate
				+ ", exDef=" + exDef + ", exStd=" + exStd + "]";
	}

}
