package com.myshop.entity;

import javax.persistence.*;

import com.myshop.constant.ItemSellStatus;
import com.myshop.dto.ItemDto;
import com.myshop.dto.ItemFormDto;

import lombok.*;

@Entity
@Table(name="item") // 테이블명 설정(설정 안해주면 클래스명으로 알아서 해줌) 
@Getter
@Setter
@ToString
public class Item extends BaseEntity { // Item 테이블의 각각의 컬럼들
	// not null이 아닐 때는 필드 타입을 객체(예 int - Integer)로 지정해야 한다.
	
	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 상품코드 
	 
	@Column(nullable = false, length = 50) // not null, 길이 50
	private String itemNm; // 상품명
	
	@Column(nullable = false, name="price")
	private int price; // 가격
	
	@Column(nullable = false)
	private int stockNumber; // 재고수량
	
	@Lob // 데이터 타입이 크다 
	@Column(nullable = false)
	private String itemDetail; // 상품 상세 설명 
	
	@Enumerated(EnumType.STRING) // 열거형 타입! 
	private ItemSellStatus itemSellStatus; // 상품 판매 상태 

	public void upadateItem(ItemFormDto itemFormDto) {
		this.itemNm = itemFormDto.getItemNm();
		this.price = itemFormDto.getPrice();
		this.stockNumber = itemFormDto.getStockNumber();
		this.itemDetail = itemFormDto.getItemDetail();
		this.itemSellStatus = itemFormDto.getItemSellStatus();
		
	}
}
