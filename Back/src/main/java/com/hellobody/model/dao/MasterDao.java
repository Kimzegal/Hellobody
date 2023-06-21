package com.hellobody.model.dao;

import com.hellobody.model.dto.Master;
import com.hellobody.model.dto.User;

public interface MasterDao {

	public Master selectMaster(int mId);

	public int insertMaster(Master master);

	public int updateMaster(Master master);

}
