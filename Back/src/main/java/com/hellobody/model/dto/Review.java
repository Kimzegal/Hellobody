package com.hellobody.model.dto;

public class Review {
	

	private int rId;
	private int cId;
	private String uId;
	private String rDate;
	private String rContent;

	public Review() {
	}

	public Review(int rId, int cId, String uId, String rDate, String rContent) {
		this.rId = rId;
		this.cId = cId;
		this.uId = uId;
		this.rDate = rDate;
		this.rContent = rContent;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getrDate() {
		return rDate;
	}

	public void setrDate(String rDate) {
		this.rDate = rDate;
	}

	public String getrContent() {
		return rContent;
	}

	public void setrContent(String rContent) {
		this.rContent = rContent;
	}

	@Override
	public String toString() {
		return "Review [rId=" + rId + ", cId=" + cId + ", uId=" + uId + ", rDate=" + rDate + ", rContent=" + rContent
				+ "]";
	}

}
