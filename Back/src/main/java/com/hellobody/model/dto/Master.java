package com.hellobody.model.dto;

public class Master {

	private int mId;		
	private String career;
	private String job;
	private String adCom;

	public Master() {
	}

	public Master(int mId, String career, String job, String adCom) {
		this.mId = mId;
		this.career = career;
		this.job = job;
		this.adCom = adCom;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getAdCom() {
		return adCom;
	}

	public void setAdCom(String adCom) {
		this.adCom = adCom;
	}

	@Override
	public String toString() {
		return "Master [mId=" + mId + ", career=" + career + ", job=" + job + ", adCom=" + adCom + "]";
	}

}
