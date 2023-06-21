package com.hellobody.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hellobody.model.dto.User;
import com.hellobody.model.service.UserService;
import com.hellobody.util.DownUtil;
import com.hellobody.util.JwtUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private UserService uService;

	// JWT Token과 static 요소 접근 관련 필드
	private JwtUtil jUtil;
	private String path = DownUtil.getPath("img");

	public UserController(@Autowired UserService uService, @Autowired JwtUtil jUtil) {
		this.uService = uService;
		this.jUtil = jUtil;
	}

	// 이메일 중복 확인 메서드
	@GetMapping("/{uId}")
	public ResponseEntity<?> getConfirm(@PathVariable("uId") String uId) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpStatus status = null;
		System.out.println(uId+"이메일 에 대한 중복 여부를 확인합니다.");
		
		// 유저 정보가 없다면 confirm 키에 true를, 아니라면 false를
		try {
			// 이메일 주소로 유저의 정보를 얻음
			User user = uService.getUserByUid(uId);
			
			if (user == null) {
				result.put("confirm", true);
				status = HttpStatus.OK;
			} else {
				result.put("confirm", false);
				status = HttpStatus.OK;
			}

		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	
	// 이메일 중복 확인 메서드
		@GetMapping("/nick/{nick}")
		public ResponseEntity<?> nickConfirm(@PathVariable("nick") String nick) {
			Map<String, Object> result = new HashMap<String, Object>();
			HttpStatus status = null;
			System.out.println(nick+"닉네임에 대한 중복 여부를 확인합니다.");
			
			// 유저 정보가 없다면 confirm 키에 true를, 아니라면 false를
			try {
				// 닉네임으로 유저의 정보를 얻음
				User user = uService.getUserByNick(nick);
				
				if (user == null) {
					result.put("confirm", true);
					status = HttpStatus.OK;
				} else {
					result.put("confirm", false);
					status = HttpStatus.OK;
				}

			} catch (Exception e) {
				e.printStackTrace();
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
			return new ResponseEntity<Map<String, Object>>(result, status);
		}
	
	// 회원 가입 메서드
	@PostMapping("/")
	public ResponseEntity<?> regist(User user, MultipartHttpServletRequest req){
		Map<String, Object> result = new HashMap<String, Object>();
		HttpStatus status = null;

		String newFileName;
		// 첨부파일은 한 개이지만, 일단 iterator를 돌려 fileName을 추출

		try {
			Iterator<String> iterator = req.getFileNames();
			String fileName = null;
			while (iterator.hasNext()) {
				fileName = iterator.next();
			}

			// fileName이 없으면 default.png로 바꿈
			if (fileName != null) {
				MultipartFile imgFile = req.getFile(fileName);
				
				// 디렉토리 만들기
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				// 중복방지용 시간 더하기
				newFileName = System.currentTimeMillis() + imgFile.getOriginalFilename();
				
				file = new File(path + "/" + newFileName);
				System.out.println("아래의 파일을 만듭니다.");
				System.out.println(file.getAbsolutePath());
				
				user.setrImg(newFileName);
				// 파일 실제 송출
				imgFile.transferTo(file);
			} else user.setrImg("default.png");
			
			//registUser 메서드를 통해 user를 등록하고, 1을 반환하면 성공한다.
			int success = uService.registUser(user);
			if (success == 1) {
				result.put("file", user.getrImg());
				System.out.println(user.toString());
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	
	// 로그인 메서드
	@PostMapping("/{uId}")
	public ResponseEntity<?> login(@PathVariable("uId") String uId, String pw) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpStatus status = null;
		System.out.println(uId);
		try {
			// uId와 pw로 로그인한 유저를 파악
			Map<String, Object> logStatus = uService.loginUser(uId, pw);
			System.out.println("토큰에 대한 정보입니다.");
			System.out.println(logStatus.toString());
			result.put("login-token", jUtil.createToken(logStatus));
			result.put("logged", true);
			status = HttpStatus.OK;
		} catch (Exception e) {
			// 오류가 발생하면 여기서 예외가 발생한다.
			e.printStackTrace();
			result.put("logged", false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		System.out.println(status);
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	
	// 토큰 최신화
	@PostMapping("/token")
	public ResponseEntity<?> giveToken(User user) {
		Map<String, Object> token = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		HttpStatus status = null;
		
		try {
			// 토큰화 시키기
			token = JwtUtil.makeMap(user);
			System.out.println("토큰에 대한 정보입니다.");
			System.out.println(token.toString());
			result.put("login-token", jUtil.createToken(token));
			status = HttpStatus.OK;
		} catch (Exception e) {
			// 오류가 발생하면 여기서 예외가 발생한다.
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		System.out.println(status);
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	// ------------------------------------- 미사용 ---------------------
//	@GetMapping("/")
//	public ResponseEntity<?> getUser(String nick) {
//		System.out.println(nick);
//		Map<String, Object> result = new HashMap<String, Object>();
//		HttpStatus status = null;
//		User user = uService.getUserByNick(nick);
//		System.out.println(user.toString());
//		try {
//			if (user != null) {
//				result.put("userInfo", user);
//				result.put("message", "success");
//				status = HttpStatus.OK;
//			} else {
//				result.put("message", "fail");
//				status = HttpStatus.OK;
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//		}
//		return new ResponseEntity<Map<String, Object>>(result, status);
//	}
//	


	

}
