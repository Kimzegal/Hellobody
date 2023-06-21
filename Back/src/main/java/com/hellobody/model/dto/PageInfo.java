package com.hellobody.model.dto;

import java.util.List;

public class PageInfo {
	private User user;
	private List<Record> recordList;
	private List<Record> maxRecord;
	
	PageInfo(){}

	public PageInfo(User user, List<Record> recordList, List<Record> maxRecord) {
		this.user = user;
		this.recordList = recordList;
		this.maxRecord = maxRecord;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Record> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<Record> recordList) {
		this.recordList = recordList;
	}

	public List<Record> getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(List<Record> maxRecord) {
		this.maxRecord = maxRecord;
	}

	@Override
	public String toString() {
		return "PageInfo [user=" + user + ", recordList=" + recordList + ", maxRecord=" + maxRecord + "]";
	};
	
	
}
