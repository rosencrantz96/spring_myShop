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
	
	// 주문할 상품과 주문 수량을 통해 orderItem 객체를 만듬
	public static OrderItem createOrderItem(Item item, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setCount(count);
		orderItem.setOrderPrice(item.getPrice());
		
		item.removeStock(count);
		
		return orderItem;
		
	}
	
	// 주문한 총 가격 
	public int getTotalPrice() {
		
		return orderPrice*count;
	}
}
	