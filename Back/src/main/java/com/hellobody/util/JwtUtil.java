package com.hellobody.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hellobody.model.dto.User;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil{
	private static final String SALT = "YJGLSH";
	
	public String createToken(Map<String,Object> claims) throws UnsupportedEncodingException {
		JwtBuilder jb = Jwts.builder().setHeaderParam("alg", "HS256").setHeaderParam("typ", "JWT").setClaims(claims);
		return jb.setExpiration(new Date(System.currentTimeMillis()+(100000000))) //유효기간(30분)
				.signWith(SignatureAlgorithm.HS256, SALT.getBytes("UTF-8")) //서명완료
				.compact();
	}
	
	public static Map<String, Object> makeMap (User user) throws Exception{
		Map<String, Object> userMap = new HashMap<>();
		
		try {
			// user의 모든 정보를 필드 배열로 변환
			Field[] fields = user.getClass().getDeclaredFields();
			// 반복을 도는 동안, userMap에 필드값 집어넣음.
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(user);
				userMap.put(field.getName(), value);
			}
			userMap.remove("pw");
			userMap.remove("name");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return userMap;
	}
	
	public static void valid(String token) throws Exception {
		Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(token);
	}
}
