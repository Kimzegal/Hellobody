package com.hellobody.model.dao;

import java.util.List;

import com.hellobody.model.dto.User;

public interface UserDao {
	// 유저 목록 가져오기
	List<User> selectAll();
	
	// 유저 가져오기
	User selectByUid(String uId);
	
	// 유저 가져오기
	User selectByNick(String nick);
	
	// 유저 등록하기
	int insert(User user);
	
	// 방문 날짜 변경
	int updateDate(String uId);
	
	// 유저 수정하기
	int update(User user);
	
	// 유저 삭제하기
	int delete(String uId);
}
