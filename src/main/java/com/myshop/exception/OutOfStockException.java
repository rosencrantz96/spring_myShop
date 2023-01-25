package com.myshop.exception;

public class OutOfStockException extends RuntimeException {
	
	// 상품 주문 수량보다 
	public OutOfStockException(String message) {
		super(message);
	}
	
	
}
