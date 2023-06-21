package com.hellobody.model.dao;

import java.util.List;
import java.util.Map;

import com.hellobody.model.dto.Record;

public interface RecordDao {
	// 모든 record를 가져옵니다.
	public List<Record> selectAll();
	
	// lid를 통해 레코드를 가져옴
    public List<Record> selectBylId(Map<String, String> params);
	
    public List<Record> select(Record record);
	
	// uId에 따라 record를 가져옵니다.
	public List<Record> selectByUid(String uId);
	
	// 자신의 각 운동별 최고 record를 가져옵니다.
	public List<Record> selectMyMax(String uId);
	
	// 레코드를 추가합니다.
	public int insertRecord(Record record);
	
	// -----------------------------------------------------master와 관련된 영역
	// mId에 따라 record를 가져옵니다.
	public List<Record> searchByMId(String mId);
	//master가 인증을 완료한 record입니다.
	public int certificationComplete(Record record);
	
	// -----------------------------------------------------review와 관련된 영역
	// respect를 올려줍니다.
	public int increaseRespect(Record record);

}
