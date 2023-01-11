package com.myshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.type.LocalDateType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import com.myshop.constant.ItemSellStatus;
import com.myshop.entity.Item;
import com.myshop.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.thymeleaf.util.StringUtils; 

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

	private static final ItemSellStatus SELL = null;
	@Autowired
	ItemRepository itemRepository;
	
	@PersistenceContext // 영속성 컨텍스트를 사용하기 위해 선언
	EntityManager em; // 엔티티 매니저
	

//	@Test
//	@DisplayName("상품 저장 테스트")
//	public void createItemTest() {
//		Item item = new Item();
//		item.setItemNm("테스트 상품");
//		item.setPrice(10000);
//		item.setItemDetail("테스트 상품 상세 설명");
//		item.setItemSellStatus(ItemSellStatus.SELL);
//		item.setStockNumber(100);
//		item.setRegTime(LocalDateTime.now());
//		item.setUpdateTime(LocalDateTime.now());
//		
//		Item savedItem = itemRepository.save(item); // 데이터 insert 
//		
//		System.out.println(savedItem.toString());
//	}

	public void createItemTest() {
		for (int i = 1; i <= 10; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepository.save(item); // 데이터 insert
		}

	}
	
	public void createItemTest2() {
		for (int i = 1; i <= 5; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepository.save(item); // 데이터 insert
		}
		
		for (int i = 6; i <= 10; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
			item.setStockNumber(0);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepository.save(item); // 데이터 insert
		}

	}

//	@Test
	@DisplayName("상품명 조회 테스트")
	public void findByItemNmTest() {
		this.createItemTest(); // item 테이블에 insert
		List<Item> itemList = itemRepository.findByItemNm("테스트 상품 1");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

//	@Test
	@DisplayName("상품명, 상품상세설명 or 테스트")
	public void findByItemNmOrItemDetailTest() {
		this.createItemTest(); // item 테이블에 insert
		List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

//	@Test
	@DisplayName("가격 LessThan 테스트")
	public void findByPriceLessThanTest() {
		this.createItemTest(); // item 테이블에 insert
		List<Item> itemList = itemRepository.findByPriceLessThan(10005);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

//	@Test
	@DisplayName("가격 내림차순 조회 테스트")
	public void findByPriceLessThanOrderByPriceDescTest() {
		this.createItemTest(); // item 테이블에 insert
		List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	/*
	 * @Test
	 * 
	 * @DisplayName("and 테스트") public void findByItemNmAndItemSellStatusTest() {
	 * this.createItemTest(); List <Item> itemList =
	 * itemRepository.findByItemNmAndItemSellStatus("테스트 상품1", ItemSellStatus.SELL);
	 * for (Item item : itemList) { System.out.println(item.toString()); } }
	 * 
	 * @Test
	 * 
	 * @DisplayName("두 값 사이의 가격 조회 테스트") public void findByPriceBetweenTest() {
	 * this.createItemTest(); List <Item> itemList =
	 * itemRepository.findByPriceBetween(10004, 10008); for (Item item : itemList) {
	 * System.out.println(item.toString()); } }
	 * 
	 * @Test
	 * 
	 * @DisplayName("이후의 레코드 조회 테스트") public void findByRegTimeAfterTest() {
	 * this.createItemTest(); // 변수를 따로 선언해서 해주는 것이 코드가 더 깔끔한 것 같다. List <Item>
	 * itemList = itemRepository.findByRegTimeAfter(LocalDateTime.of(2023, 1, 1, 12,
	 * 12, 44)); for (Item item : itemList) { System.out.println(item.toString()); }
	 * }
	 * 
	 * @Test
	 * 
	 * @DisplayName("not null 레코드 구하는 테스트") public void
	 * findByItemSellStatusIsNotNullTest() { this.createItemTest(); List <Item>
	 * itemList = itemRepository.findByItemSellStatusIsNotNull(); for (Item item :
	 * itemList) { System.out.println(item.toString()); } }
	 * 
	 * @Test
	 * 
	 * @DisplayName("~로 끝나는 레코드 구하는 테스트") public void
	 * findByItemDetailEndingWithTest() { this.createItemTest(); List <Item>
	 * itemList = itemRepository.findByItemDetailEndingWith("설명1"); for (Item item :
	 * itemList) { System.out.println(item.toString()); } }
	 */

//	JPQL 실행
//	@Test
	@DisplayName("@Query를 이용한 상품 조회 테스트")
	public void findByItemDetailTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

//	@Test
	@DisplayName("@native Query를 이용한 상품 조회 테스트")
	public void findByItemDetailByNativeTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

	/*
	//퀴즈1
	@Test
	@DisplayName("@Query를 이용한 상품 조회 테스트 퀴즈 1")
	public void findByPriceGreaterThanTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceGreaterThan(10005);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//퀴즈2
	@Test
	@DisplayName("@nativeQuery를 이용한 상품 조회 테스트 퀴즈 2")
	public void findByItemNmAndItemSellStatusByNativeTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmAndItemSellStatusByNative("테스트 상품1", ItemSellStatus.SELL);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	*/
	
//	@Test
	@DisplayName("queryds1 조회 테스트")
	public void queryDslTest() {
		this.createItemTest();
		JPAQueryFactory qf = new JPAQueryFactory(em); // 쿼리를 동적으로 생성하기 위한 객체 
		QItem qItem = QItem.item;
		// JPAQuery: 쿼리를 실행시킬 수 있는 객체 
		// selectFrom메소드를 통해 쿼리 실현 (=select * from item where itemSellStatus = 'SELL' and itemDetail like %테스트 상품 상세 설명% order by price desc → 함수로 표현한 것)
		JPAQuery<Item> query = qf.selectFrom(qItem)
				.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
				.where(qItem.itemDetail.like("%테스트 상품 상세 설명%"))
				.orderBy(qItem.price.desc()); // 가격을 내림차순으로 정렬
		
		List<Item> itemList = query.fetch();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
//	@Test
	@DisplayName("queryds1 조회 테스트2")
	public void queryDslTest2() {
		this.createItemTest2();
		
		JPAQueryFactory qf = new JPAQueryFactory(em); // 쿼리를 동적으로 생성하기 위한 객체 
		QItem qItem = QItem.item;
		Pageable page = PageRequest.of(0, 2);
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
				.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
				.where(qItem.itemDetail.like("%테스트 상품 상세 설명%"))
				.where(qItem.price.gt(10003))
				// offset, limit를 통해서 페이징을 할 수 있음 
				.offset(page.getOffset())
				.limit(page.getPageSize());
		
		
		List<Item> itemList = query.fetch();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}

	//퀴즈 
	@Test
	@DisplayName("퀴즈1 쿼리 조건절 and")
	public void quiz1() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); 
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
				.where(qItem.itemNm.eq("테스트 상품1"))
				.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL));

		List<Item> itemList = query.fetch();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
	@Test
	@DisplayName("퀴즈2 between")
	public void quiz2() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); 
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
				.where(qItem.price.between(10004, 10008));

		List<Item> itemList = query.fetch();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
	@Test
	@DisplayName("퀴즈3 after")
	public void quiz3() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); 
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
				.where(qItem.regTime.after(LocalDateTime.of(2023, 1, 1, 12, 12, 44)));

		List<Item> itemList = query.fetch();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
	@Test
	@DisplayName("퀴즈4 not null")
	public void quiz4() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); 
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
				.where(qItem.itemSellStatus.isNotNull());

		List<Item> itemList = query.fetch();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
	@Test
	@DisplayName("퀴즈5 endsWith")
	public void quiz5() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); 
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
				.where(qItem.itemDetail.endsWith("설명1"));

		List<Item> itemList = query.fetch();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	
	@Test
	@DisplayName("퀴즈5-1 endsWith 대신 like")
	public void quiz5_1() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); 
		QItem qItem = QItem.item;
		
		JPAQuery<Item> query = qf.selectFrom(qItem)
				.where(qItem.itemDetail.like("%설명1"));

		List<Item> itemList = query.fetch();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
		
	}
	

}
