package com.tjoeun.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tjoeun.contant.UsersRole;
import com.tjoeun.entity.Users;
import com.tjoeun.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersSecurityService  implements UserDetailsService {

	private final UsersRepository usersRepository;

	//loadUserByUsername 자동완성
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	/*
		Users users = usersRepository.findByusername(username)
															.orElseThrow(EntityNotFoundException::new);
															이렇게 사용하거나 밑에껄로 사용하거나
	*/
	
		//DB에서 가져옴
		Optional<Users> tmp_users = usersRepository.findByusername(username);
		
		//못 가지고 왔을 때, 로그인 하려는 아이디가 아직 없을 경우
		if(tmp_users.isEmpty()) {
			throw new UsernameNotFoundException("없는 회원입니다.");
		}
		//로그인 하려는 아이디가 있을 경우
		Users users = tmp_users.get();
		
		// ADMIN인지, USER인지 확인
		// GrantedAuthority 스프링프레임워크가 자동으로 설정
		List<GrantedAuthority> authorities = new ArrayList();
		
		if("admin".equals(username)) {
			//admin이 맞다면 ADMIN 권환을 부여
			authorities.add(new SimpleGrantedAuthority(UsersRole.ADMIN.getValue()));
	}else //그게 아니라면 USER권환부여
		authorities.add(new SimpleGrantedAuthority(UsersRole.USER.getValue()));
		
		//DB에 있는 값 꺼내오기 USER users = tmp_user를 가져온 거
		UserDetails user = new User(users.getUsername(),users.getPassword(), authorities);
		return user;
		
		// 이렇게 바로 작성도 가능
		// return new User(users.getUsername(),users.getPassword(), authorities);
	}

	
	
	
}
