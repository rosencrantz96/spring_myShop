package com.myshop.repository;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myshop.dto.ItemSearchDto;
import com.myshop.entity.Item;
import com.myshop.entity.QItem;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);  
	}
	
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		QueryResults<Item> results = queryFactory
				.selectFrom(QItem.item) // select * from item
				.where(itemSearchDto.getSearchDateType(),
						itemSearchDto.getSearchSellStatus(),
						itemSearchDto.getSearchBy());
		
		return null;
	}

}
