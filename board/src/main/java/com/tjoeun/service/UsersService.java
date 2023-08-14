package com.tjoeun.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tjoeun.entity.Users;
import com.tjoeun.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //Autowired 하는 거
public class UsersService {

	private final UsersRepository usersRepository;
	
	private final PasswordEncoder passwordEncoder;
	

	public Users createUsers(String usersname, String password, String email) {
				
		//DB에 저장하기 전에 비밀번호 암호화
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String encodedPassword = passwordEncoder.encode(password);
		
		
		Users users = new Users();

		users.setUsername(usersname);
		users.setPassword(encodedPassword); // 인코딩한 패스워드를 넣어서 사용
		users.setEmail(email);																				
									
		//Controller에서 전달받은 값으로 설정됨
		//users레포에 users로 저장 (Users Entity를 DB에 저장 (insert)
		usersRepository.save(users);
		
		return users;
	}
	//회원정보 가져와서 수정하기
	public Users getUsers(String username) {
		
		Users users = usersRepository.findByusername(username)
													   .orElseThrow(() -> new EntityNotFoundException("해당 회원이 없습니다."));
														   					   
	return users;
	}
	
	
}
