package com.myshop.repository;

//import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myshop.constant.ItemSellStatus;
import com.myshop.entity.Item;

// JpaRepository: 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의가 되어있다. 
// JpaRepository<사용할 엔티티 클래스, 사용할 엔티티 클래스의 기본키 타입>
public interface ItemRepository extends JpaRepository<Item, Long> { 
	// select * from item where item_nm = ?
	List<Item> findByItemNm(String itemNm);
	
	// select * from item where item_nm =? or item_detail = ?
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	
	// select * from item where price < ?
	List<Item> findByPriceLessThan(Integer price);
	
	// select * from item where price < ? order by price desc
	List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
	
	/*
	// 퀴즈 
	// 1. and
	List <Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus sell);
	// 2. ~사이: between
	List <Item> findByPriceBetween(Integer price, Integer price2);
	// 3. 이후: after
	List <Item> findByRegTimeAfter(LocalDateTime regTime);
	// 4. null이 아닌 레코드: not null
	List <Item> findByItemSellStatusIsNotNull();
	// 5. ~로 끝나는: like or endingWith
	List <Item> findByItemDetailEndingWith(String itemDetail);
	*/
	
//	JPQL 수업
	//파라미터 받는 방법 1 (쿼리문 봤을 때 이 방식이 더 명확하게 구분이 가능해서 더 많이 쓴다)
//	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc ") // from 뒤에는 엔티티 클래스 명, 별칭을 줘야함(i) ★엔티티 클래스가 기준이 되어야 함!
////	어노테이션 Param은 itemDetail이라는 파라미터 이름으로 쓰겠다는 의미
//	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail); //itemDetail에 검색할 데이터를 넣어주면 된다
	
	//파라미터 받는 방법 2 (snippet 참고)
	@Query("select i from Item i where i.itemDetail like %?1% order by i.price desc ") // from 뒤에는 엔티티 클래스 명, 별칭을 줘야함(i) ★엔티티 클래스가 기준이 되어야 함!
	List<Item> findByItemDetail(String itemDetail); //itemDetail에 검색할 데이터를 넣어주면 된다
	
	//nativeQuery (DB에 맞춰서 쿼리를 작성하면 된다) (컬럼명을 엔티티 클래스에 맞추면 안 되고 DB 테이블,컬럼 명으로 적어줘야 한다 DB확인 필수)
	@Query(value ="select * from item i where i.item_detail like %:itemDetail% order by i.price desc ", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
	
	/*
	//퀴즈
	// 1.price가 10005 이상인 레코드
	@Query("select i from Item i where i.price >= ?1 ")
	List<Item> findByPriceGreaterThan(Integer price);
	
	// 2. itemNm이 "테스트 상품1"이고 ItemSellStatus가 sell인 레코드 (네이티브 쿼리로 하니까 안 됨) 
//	@Query(value = "select * from item i where i.item_nm = :itemNm and i.item_sell_status = :sell", nativeQuery = true)
//	List<Item> findByItemNmAndItemSellStatusByNative(@Param("itemNm") String itemNm, @Param("sell") ItemSellStatus sell);
	
	// 2. 그냥 쿼리로 하니까 됨 미친새끼
	@Query("select i from Item i where i.itemNm = ?1 and i.itemSellStatus = ?2")
	List<Item> findByItemNmAndItemSellStatusByNative(String itemNm, ItemSellStatus sell);
	*/
	
}
	