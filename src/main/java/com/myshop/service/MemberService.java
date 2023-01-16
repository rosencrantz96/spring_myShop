package com.myshop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.entity.Member;
import com.myshop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;


@Service // 서비스 클래스라는 의미의 어노테이션 (service 클래스의 역할)
@Transactional // 서비스 계층 클래스에 있는 로직을 처리하다가 에러가 발생하면 로직을 수행하기 이전 상태로 되돌려 준다. (필드로 의존성 주입)\\
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member); // member 테이블에 insert
	}
	
	// 이메일 중복체크 메소드 
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail()); // 이메일이 있는지 없는지 select 해주는 것 
		if (findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
 	}
}
