package com.hellobody.model.dao;

import java.util.List;

import com.hellobody.model.dto.League;

public interface LeagueDao {

	public List<League> select();

	public int insertLeague(League league);

}
