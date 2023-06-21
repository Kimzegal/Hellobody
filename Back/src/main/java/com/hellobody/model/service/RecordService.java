package com.hellobody.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellobody.model.dao.CertificationDao;
import com.hellobody.model.dao.RecordDao;
import com.hellobody.model.dao.ReviewDao;
import com.hellobody.model.dto.Record;
import com.hellobody.model.dto.Review;

@Service
public class RecordService {
    private RecordDao rDao;
    private CertificationDao cDao;
    private ReviewDao rvDao;
    
    public RecordService(@Autowired RecordDao rDao, @Autowired CertificationDao cDao,@Autowired ReviewDao rvDao) {
        this.rDao = rDao;
        this.cDao = cDao;
        this.rvDao = rvDao;
    }
	
	// 개인 페이지의 모든 운동 목록을 가져옵니다.
	public List<Record> getAllRecord(Record record){
		return rDao.select(record);
	}
	
	// 모든 운동 목록을 가져옵니다.
	public List<Record> getAllRecords(){
		return rDao.selectAll();
	}
	
	// lId와 exNam으로 리그의 레코드를 가져온다.
    public List<Record> getLeagueRecords(String lId,String exName){
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("lId", lId);
    	params.put("exName", exName);
    	List<Record> list = rDao.selectBylId(params);
    	return list;
    }
	
	
	// 유저 최고 운동 목록을 가져옵니다.
	public List<Record> getPageMaxRecord(String uId) {
		return rDao.selectMyMax(uId);
	}
	
	// 유저의 레코드를 추가 (환산점수 없이 원점수를 레코드에 추가)
	// 레코드에 대한 유효성 검사(신규 레코드는 이전의 레코드 기록보다 높아야 함)는 프론트에서 진행
	public int insertPersonalRecord(Record record) {
		System.out.println(record.toString());
		return rDao.insertRecord(record); 
	}
	
	 public List<Review> getReviewInRecord(int cId) {
		 	System.out.println("시작이다.");
	    	List<Review> list = rvDao.getReviewInRecord(cId);
	    	System.out.println("이거왜이래"+list.get(0));
	        return list;
	    }
	    
	    public int deleteComment(int rId) {
	    	return rvDao.deleteReview(rId);
	    }
	    
	    public int updateComment(Review review) {
	    	return rvDao.updateReview(review);
	    }
	    
	    public int insertComment(Review review) {
	    	return rvDao.insertReview(review);
	    }
	    
	    public int updateRecord(Record record) {
	    	return rDao.certificationComplete(record);
	    }
}
