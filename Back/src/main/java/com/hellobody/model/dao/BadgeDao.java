package com.hellobody.model.dao;

import com.hellobody.model.dto.Badge;

public interface BadgeDao {
	
	public Badge selectBadge(String bName);
	
	public int insertBadge(Badge badge);
	
}
