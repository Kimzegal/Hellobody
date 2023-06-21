package com.hellobody.model.dao;

import java.util.List;

import com.hellobody.model.dto.Badge;

public interface UserBadgeDao {

	public List<Badge> selectAllBadge(String uId);

	public int insertBadgeToUser(Badge badge, String uId);

	
}
