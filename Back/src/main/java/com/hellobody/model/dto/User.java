package com.hellobody.model.dto;

public class User {
	private String uId;
	private String pw;
	private String name;
	private String nick;
	private String lId;
	private int mId;
	private int aId;
	private String rDate;
	private String vDate;
	private String rBadge;
	private String rExercise;
	private String rImg;
	private String adCom;
	
	public User() { }

	public String getrImg() {
		return rImg;
	}

	public void setrImg(String rImg) {
		this.rImg = rImg;
	}

	public User(String uId, String pw, String name, String nick, String lId, int mId, int aId, String rDate,
			String vDate, String rBadge, String rExercise, String rImg, String adCom) {
		this.uId = uId;
		this.pw = pw;
		this.name = name;
		this.nick = nick;
		this.lId = lId;
		this.mId = mId;
		this.aId = aId;
		this.rDate = rDate;
		this.vDate = vDate;
		this.rBadge = rBadge;
		this.rExercise = rExercise;
		this.rImg = rImg;
		this.adCom = adCom;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getlId() {
		return lId;
	}

	public void setlId(String lId) {
		this.lId = lId;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

	public String getrDate() {
		return rDate;
	}

	public void setrDate(String rDate) {
		this.rDate = rDate;
	}

	public String getvDate() {
		return vDate;
	}

	public void setvDate(String vDate) {
		this.vDate = vDate;
	}

	public String getrBadge() {
		return rBadge;
	}

	public void setrBadge(String rBadge) {
		this.rBadge = rBadge;
	}

	public String getrExercise() {
		return rExercise;
	}

	public void setrExercise(String rExercise) {
		this.rExercise = rExercise;
	}

	public String getAdCom() {
		return adCom;
	}

	public void setAdCom(String adCom) {
		this.adCom = adCom;
	}

	@Override
	public String toString() {
		return "User [uId=" + uId + ", pw=" + pw + ", name=" + name + ", nick=" + nick + ", lId=" + lId + ", mId=" + mId
				+ ", aId=" + aId + ", rDate=" + rDate + ", vDate=" + vDate + ", rBadge=" + rBadge + ", rExercise="
				+ rExercise + ", rImg=" + rImg + ", adCom=" + adCom + "]";
	}

}
