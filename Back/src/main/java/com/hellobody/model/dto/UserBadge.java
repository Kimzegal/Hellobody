package com.hellobody.model.dto;

public class UserBadge {
	private int bId;
	private String uId;
	private String bName;
	private String achDate;

	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getAchDate() {
		return achDate;
	}

	public void setAchDate(String achDate) {
		this.achDate = achDate;
	}

	public UserBadge(int bId, String uId, String bName, String achDate) {
		this.bId = bId;
		this.uId = uId;
		this.bName = bName;
		this.achDate = achDate;
	}

	public UserBadge() {
	}

	@Override
	public String toString() {
		return "UserBadge [bId=" + bId + ", uId=" + uId + ", bName=" + bName + ", achDate=" + achDate + "]";
	}

}
