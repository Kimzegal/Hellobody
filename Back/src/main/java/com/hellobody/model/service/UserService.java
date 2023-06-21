package com.hellobody.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellobody.model.dao.UserDao;
import com.hellobody.model.dto.User;
import com.hellobody.util.JwtUtil;

@Service
public class UserService {
	private UserDao uDao;

	public UserService(@Autowired UserDao uDao) {
		this.uDao = uDao;
	}

	// 이메일 주소로 유저 정보 얻기
	public User getUserByUid(String uId) {
		return uDao.selectByUid(uId);
	}

	// 닉네임 주소로 유저 정보 얻기
	public User getUserByNick(String nick) {
		return uDao.selectByNick(nick);
	}

	// 로그인한 유저에 대해서 토큰을 발급하기 위해 정보를 만듬
	public Map<String, Object> loginUser(String uId, String pw) {
		Map<String, Object> userMap = new HashMap<>();
		// 아이디가 없거나, 패스워드가 잘못되었다면 null을 반환
		try {
			User user = getUserByUid(uId);
			if (user == null || !user.getPw().contentEquals(pw)) {
				return null;
			} else {
				uDao.updateDate(uId);
				userMap = JwtUtil.makeMap(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userMap;
	}

	// 회원 가입시 유저 정보를 데이터베이스에 등록합니다.
	public int registUser(User user) {
		return uDao.insert(user);
	}

	// 유저의 목록을 가져옵니다.
	public List<User> getUsers() {
		return uDao.selectAll();
	}

	// ------------------------------------- 미사용 ---------------------

	// 개인 정보 수정 시 유저 정보를 변경합니다.
	public int updateUser(User user) {
		return uDao.update(user);
	}

	// 유저 탈퇴 시 유저 정보를 삭제합니다.
	public int deleteUser(String uId) {
		return uDao.delete(uId);
	}
}
