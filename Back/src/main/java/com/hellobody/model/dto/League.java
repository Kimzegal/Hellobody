package com.hellobody.model.dto;

public class League {

	private String region;
	private String area;

	public League() {
	}

	public League(String region, String area) {
		this.region = region;
		this.area = area;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "League [region=" + region + ", area=" + area + "]";
	}

}
