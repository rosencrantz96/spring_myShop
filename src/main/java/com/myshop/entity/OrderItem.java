package com.myshop.entity;


import javax.persistence.*;

import lombok.*;

@Entity
@Table(name="order_item") 
@Getter
@Setter
@ToString
public class OrderItem extends BaseEntity {
	@Id
	@Column(name="order_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@ManyToOne(fetch = FetchType.LAZY) // 자신을 기준으로 하는데 n:1이니까 ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY) // (fetch = FetchType.LAZY): 지연로딩 
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Column(name = "order_price")
	private int orderPrice;  // 주문 가격 
	
	private int count; // 주문 수량
}
	