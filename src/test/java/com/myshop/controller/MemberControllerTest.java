package com.myshop.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.dto.MemberFormDto;
import com.myshop.entity.Member;
import com.myshop.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc // 실제 객체와 비슷하지만 테스트에 필요한 기능만 제공하는 가짜 객체를 만든다. -> 웹브라우저에서 요청을 하는 것처럼 작성 가능
@Transactional	
@TestPropertySource(locations="classpath:application-test.properties")
class MemberControllerTest {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public Member createMember(String email, String password) {
		MemberFormDto member = new MemberFormDto();
		member.setName("권수경");
		member.setEmail(email);
		member.setAddress("서울시 관악구 청림동");
		member.setPassword(password); // 암호화 한 것을 저장해야 한다!

		Member member2 = Member.createMember(member, passwordEncoder);
		
		return memberService.saveMember(member2);
	}
	
	@Test
	@DisplayName("로그인 성공 테스트")
	public void loginSuccessTest() throws Exception {
		String email = "test@email.com";
		String password = "12345678";
		this.createMember(email, password);
		mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login") // 회원가입 메소드를 실행 후에 회원정보로 로그인이 되는지 테스트를 진행(해당 URL로 로그인 요청)
                .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); // 로그인이 성공해서 인증되면 테스트 코드를 통과시킨다. 
	}
	
	@Test
	@DisplayName("로그인 실패 테스트")
	public void loginFailTest() throws Exception {
		String email = "test@email.com";
		String password = "12345678";
		this.createMember(email, password);
		mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login") // 회원가입 메소드를 실행 후에 회원정보로 로그인이 되는지 테스트를 진행(해당 URL로 로그인 요청)
                .user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated()); // 로그인이 성공해서 인증되면 테스트 코드를 통과시킨다. 
	}
}
