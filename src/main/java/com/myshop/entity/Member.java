package com.myshop.entity;

import javax.persistence.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.myshop.constant.Role;
import com.myshop.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
	@Id // pk
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO) // 생성전략 자동
	private Long id;
	
	private String name;

	@Column(unique = true) 
	private String email;

	private String password;

	private String address;

	@Enumerated(EnumType.STRING) // 이넘클래스 데이터를 넣어주기 
	private Role role;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		// 일단 멤버 객체 생성 
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setAddress(memberFormDto.getAddress());
		// 패스워드는 암호화가 되어야 하기 때문에 한 번 처리를 해주어야 한다. 
		String password = passwordEncoder.encode(memberFormDto.getPassword()); // 비밀번호 암호화 
		member.setPassword(password); // 암호화 한 것을 저장해야 한다! 
		
		member.setRole(Role.USER);
		
		return member;
	}
}
