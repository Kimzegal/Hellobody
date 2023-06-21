package com.hellobody.model.dto;

public class Record {
	private int cId;
	private String uId;
	private String mId;
	private String mCom;
	private String exName;
	private String exScore;
	private int pof;
	private int respect;
	private String cVideo;
	private String uDate;
	private String cDate;
	
	public Record() {}

	public Record(int cId, String uId, String mId, String mCom, String exName, String exScore, int pof, int respect,
			String cVideo, String uDate, String cDate) {
		this.cId = cId;
		this.uId = uId;
		this.mId = mId;
		this.mCom = mCom;
		this.exName = exName;
		this.exScore = exScore;
		this.pof = pof;
		this.respect = respect;
		this.cVideo = cVideo;
		this.uDate = uDate;
		this.cDate = cDate;
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

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmCom() {
		return mCom;
	}

	public void setmCom(String mCom) {
		this.mCom = mCom;
	}

	public String getExName() {
		return exName;
	}

	public void setExName(String exName) {
		this.exName = exName;
	}

	public String getExScore() {
		return exScore;
	}

	public void setExScore(String exScore) {
		this.exScore = exScore;
	}

	public int getPof() {
		return pof;
	}

	public void setPof(int pof) {
		this.pof = pof;
	}

	public int getRespect() {
		return respect;
	}

	public void setRespect(int respect) {
		this.respect = respect;
	}

	public String getcVideo() {
		return cVideo;
	}

	public void setcVideo(String cVideo) {
		this.cVideo = cVideo;
	}

	public String getuDate() {
		return uDate;
	}

	public void setuDate(String uDate) {
		this.uDate = uDate;
	}

	public String getcDate() {
		return cDate;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
	}

	@Override
	public String toString() {
		return "Record [cId=" + cId + ", uId=" + uId + ", mId=" + mId + ", mCom=" + mCom + ", exName=" + exName
				+ ", exScore=" + exScore + ", pof=" + pof + ", respect=" + respect + ", cVideo=" + cVideo + ", uDate="
				+ uDate + ", cDate=" + cDate + "]";
	}


}
