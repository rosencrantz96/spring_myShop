package com.myshop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.myshop.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="orders") 
@Getter
@Setter
@ToString
public class Order {
	@Id
	@Column(name="order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	private LocalDateTime orderDate; // 주문일
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus; // 주문 상태 
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // OrderItem에 있는 order로 관리가 된다 . (연관관계의 주인은 OrderItem의 order인거... mappedBy로 주인 설정한 것)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this); // ★ 양방향 참조관계일 때는 orderItem객체에도 order객체를 세팅한다. 
	}
	
	// order 객체를 생성해준다. 
	public static Order createOrder(Member member, List<OrderItem> orderItemList) {
		Order order = new Order();
		order.setMember(member);
		
		for(OrderItem orderItem : orderItemList) {
			order.addOrderItem(orderItem);
		}
		
		order.setOrderStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		
		return order;
	}
	
	// 총 주문금액 
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		
		return totalPrice;
	}
}
