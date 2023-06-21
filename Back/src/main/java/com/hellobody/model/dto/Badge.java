package com.hellobody.model.dto;

public class Badge {

	private String bName;
	private String bDesc;
	private String bGrade;
	private String bImg;

	public Badge() {
	}

	public Badge(String bName, String bDesc, String bGrade, String bImg) {
		this.bName = bName;
		this.bDesc = bDesc;
		this.bGrade = bGrade;
		this.bImg = bImg;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getbDesc() {
		return bDesc;
	}

	public void setbDesc(String bDesc) {
		this.bDesc = bDesc;
	}

	public String getbGrade() {
		return bGrade;
	}

	public void setbGrade(String bGrade) {
		this.bGrade = bGrade;
	}

	public String getbImg() {
		return bImg;
	}

	public void setbImg(String bImg) {
		this.bImg = bImg;
	}

	@Override
	public String toString() {
		return "Badge [bName=" + bName + ", bDesc=" + bDesc + ", bGrade=" + bGrade + ", bImg=" + bImg + "]";
	}

}
