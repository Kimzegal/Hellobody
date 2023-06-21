package com.hellobody.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hellobody.model.dto.Record;
import com.hellobody.model.service.CertificationService;
import com.hellobody.model.service.LeagueService;
import com.hellobody.model.service.RecordService;

@RestController
@RequestMapping("/api/league")
public class LeagueController {
	private LeagueService lService;
	private RecordService rService;
	private CertificationService cService;
	
	public LeagueController(@Autowired LeagueService lService, @Autowired RecordService rService, 
			@Autowired CertificationService cService) {
		this.lService = lService;
		this.rService = rService;
		this.cService = cService;
	}
	
	// 리그 정보 확인 메서드
	@GetMapping("")
	public ResponseEntity<?> regist() {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpStatus status = null;
		System.out.println("리그 데이터를 불러옵니다.");
		
		try {
			// 대분류, 소분류로 분류된 리그 데이터를 Map으로 만듬
			Map<String, List<String>> leagueList = lService.getLeagueData();	
			if (leagueList != null) {
				System.out.println(leagueList.toString());
				result.put("leagues", leagueList);
				status = HttpStatus.OK;
			} else {
				System.out.println("DB에 리그 데이터가 존재하지 않습니다.");
				status = HttpStatus.NO_CONTENT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	
	@Transactional
	@GetMapping("/records")
	public ResponseEntity<?> getRecords(String region, String area, String exName) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpStatus status = null;
		String lId = region + " " + area;
		System.out.println(lId + "의 " + exName + "관련 레코드를 반환합니다.");
		try {
			// 대분류, 소분류로 분류된 리그 데이터를 Map으로 만듬
			List<Record> list = rService.getLeagueRecords(lId, exName);
			System.out.println("레코드 입니다." + list.toString());
			if (list == null || list.size() == 0)
				status = HttpStatus.BAD_REQUEST;
			else {
				status = HttpStatus.OK;
				result.put("records", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(result, status);
	}
}
