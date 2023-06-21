package com.hellobody.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hellobody.model.dto.Certification;
import com.hellobody.model.dto.Record;
import com.hellobody.model.dto.Review;
import com.hellobody.model.dto.User;
import com.hellobody.model.service.CertificationService;
import com.hellobody.model.service.RecordService;
import com.hellobody.model.service.UserService;
import com.hellobody.util.DownUtil;
import com.hellobody.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class PageController {
	// 운동 기록과 관련된 URL을 매핑하는 컨트롤러
	private RecordService rService;
	private UserService uService;
	private CertificationService cService;
	private JwtUtil jUtil;
	
	// 운동 인증 영상을 담을 수 있는 경로
	private String path = DownUtil.getPath("video");
	
	public PageController(@Autowired RecordService rService, @Autowired UserService uService, @Autowired CertificationService cService, @Autowired JwtUtil jUtil) {
		this.rService = rService;
		this.uService = uService;
		this.cService = cService;
		this.jUtil = jUtil;
	}
	
	// 개인 페이지에서 요청이 올 경우
	@Transactional
	@GetMapping("/page/{nick}")
	public ResponseEntity<?> getMaxRecords(@PathVariable("nick") String nick) {
		HttpStatus status = null;
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 유저 정보 얻어 uId얻고, 비밀번호와 이름 정보 없애기
			User user = uService.getUserByNick(nick);
			user.setPw("0"); user.setName("0");
			// 유저의 최고 운동 기록 얻기
			List<Record> list = rService.getPageMaxRecord(user.getuId());
			result.put("user", user);
			result.put("list", list);
			status = HttpStatus.OK;	
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	
	 @Transactional
	    @GetMapping("/allrecords")
	    public ResponseEntity<?> getAllRecords() {
	    	  HttpStatus status = null;
	        List<Record> list = rService.getAllRecords();
	        System.out.println("레코드 입니다."+list.toString());
	        if(list == null || list.size() == 0) status = HttpStatus.BAD_REQUEST;
	        else status = HttpStatus.OK;

	        return new ResponseEntity<List<Record>>(list, status);
	    }
	
	@Transactional
	@GetMapping("/certInfo")
	public ResponseEntity<?> getCertData(String exName) {
		HttpStatus status = null;
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> jwtBuilder = new HashMap<String, Object>();
		
		// 운동 조건 가져오기
		Certification cert = cService.selectCertByExName(exName);
		jwtBuilder.put("cert", cert);
		try {
			result.put("info-token", jUtil.createToken(jwtBuilder));
			result.put("message", "success");
			status = HttpStatus.OK;	
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	
		@Transactional
		@GetMapping("/myRecordInfo")
		public ResponseEntity<?> getAllRecords(Record record) {
			HttpStatus status = null;
			Map<String, Object> result = new HashMap<String, Object>();
			Map<String, Object> jwtBuilder = new HashMap<String, Object>();
			System.out.println(record.toString());
			// 유저의 모든 운동기록 가져오기
			List<Record> list = rService.getAllRecord(record);
			jwtBuilder.put("list", list);
			try {
				result.put("info-token", jUtil.createToken(jwtBuilder));
				result.put("message", "success");
				status = HttpStatus.OK;	
			} catch (Exception e) {
				e.printStackTrace();
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			return new ResponseEntity<Map<String, Object>>(result, status);
		}
	
	@Transactional
	@PostMapping("/uploadRecord")
	public ResponseEntity<?> uploadRecord(Record record, MultipartHttpServletRequest req) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpStatus status = null;
		try {
			String newFileName; // 새로운 파일명
			Iterator<String> iterator = req.getFileNames();
			String fileName = null; // 업로드된 파일명
			while(iterator.hasNext()) {
				fileName = iterator.next();
			}
		
			MultipartFile movieFile = req.getFile(fileName); // 멀티파트 파일 객체
			File file = new File(path);
			if(file.exists() == false) {
				file.mkdirs(); // 경로만들기
			}
			
			if(movieFile.isEmpty() == false) {
				
				newFileName = System.nanoTime()+movieFile.getOriginalFilename(); //시간을 더해서 중복 방지
	
				file = new File(path + "/" + newFileName);
				movieFile.transferTo(file);
				record.setcVideo(newFileName);
				System.out.println(record.toString());
			} else {
				result.put("message", "영상 등록에 실패하셨습니다.");
				status = HttpStatus.BAD_REQUEST;
				return new ResponseEntity<Map<String,Object>>(result, status);
			}
		} catch(Exception e) {
			result.put("message", "알 수 없는 이유로 레코드 등록에 실패하셨습니다.");
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<Map<String,Object>>(result, status);
		}
		
		int success = rService.insertPersonalRecord(record);
		if(success == 1){
			result.put("message", "레코드 등록 성공");
			status = HttpStatus.OK;
		} else {
			result.put("message", "알 수 없는 이유로 레코드 등록에 실패하셨습니다.");
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Map<String,Object>>(result, status);
	}
	
	@GetMapping("/record/comments/{cId}")
    public ResponseEntity<List<Review>> getComments(@PathVariable int cId) {
		List<Review> commentList = null;
		System.out.println("번호받아라~ " +cId);
    	try {
        commentList = rService.getReviewInRecord(cId);
        System.out.println("번호받아라~ " +cId);
        if (commentList == null || commentList.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }}catch(Exception e) {
        	e.printStackTrace();
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        	
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }
    
    @DeleteMapping("/record/comments/{rId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int rId){
    	int result = rService.deleteComment(rId);
    	if(result !=1) {
    		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }

    
    @PostMapping("/records/comments")
    public ResponseEntity<Void> insertComment(Review review){
    	System.out.println(review.toString());
    	int result;
    	try {
    	result = rService.insertComment(review);
    	if(result != 1) {
    		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    	}}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return new ResponseEntity<Void>(HttpStatus.OK);	
    }
    
    @PutMapping("/records/comments")
    public ResponseEntity<Review> updatComment(Review review){
    	int result;
    	try {
    	result = rService.updateComment(review);
    	if(result !=1) {
    		return new ResponseEntity<Review>(HttpStatus.BAD_REQUEST);
    	}}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return new ResponseEntity<Review>(review,HttpStatus.OK);
    }
    
    @PutMapping("/records/cert")
    public ResponseEntity<Record> updateRecord(Record record){
    	System.out.println("레코드 인증 시작");
    	try {
    	int result = rService.updateRecord(record);
    	if(result !=1) {
    		return new ResponseEntity<Record>(HttpStatus.BAD_REQUEST);
    	}}catch(Exception e){
    		e.printStackTrace();
    		return new ResponseEntity<Record>(HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<Record>(record,HttpStatus.OK);
    }
	
	
}
