package com.hellobody.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellobody.model.dao.LeagueDao;
import com.hellobody.model.dto.League;

@Service
public class LeagueService {
	private LeagueDao lDao;
	
	public LeagueService(@Autowired LeagueDao lDao) {
		this.lDao = lDao;
	}
	
	// 유저의 목록을 가져옵니다.
	public Map<String, List<String>> getLeagueData() {
		Map<String, List<String>> map = new HashMap<>();
		List<League> list = lDao.select();
		
		for(League league : list) {
			List<String> str = map.getOrDefault(league.getRegion(), new ArrayList<>());
			str.add(league.getArea());
			map.put(league.getRegion(),str);
		}
		return map;
	}
}
