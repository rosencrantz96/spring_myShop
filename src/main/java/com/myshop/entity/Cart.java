package com.myshop.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart") 
@Getter
@Setter
@ToString
public class Cart {
	
	@Id
	@Column(name="cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY) // OneToOne: 참조하는 자식 테이블에 적기 
	@JoinColumn(name = "member_id")
	private Member member;

	public static Cart createCart(Member member2) {
		// TODO Auto-generated method stub
		return null;
	}
}
