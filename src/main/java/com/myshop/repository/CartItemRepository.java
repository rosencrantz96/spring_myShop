package com.myshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myshop.dto.CartDetailDto;
import com.myshop.entity.CartItem;

public interface CartItemRepository {

	CartItem findByCartIdAndItemId(Long cartId, Long itemId);

	@Query("select new com.haseoshop.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) "
			+ "from CartItem ci, ItemImg im " + "join ci.item i " + "where ci.cart.id = :cartId "
			+ "and im.item.id = ci.item.id " + "and im.repimgYn = 'Y' " + "order by ci.regTime desc")
	List<CartDetailDto> findCartDetailDtoList(@Param("cartId") Long cartId);

}
