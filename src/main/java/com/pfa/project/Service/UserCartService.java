package com.pfa.project.Service;

import com.pfa.project.Dto.AddCourseInCartDto;
import com.pfa.project.Dto.OrderDto;
import com.pfa.project.Dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserCartService {
    Long addCourseToCart (AddCourseInCartDto addCourseToCartDto);
    OrderDto getCartByUserId(Long userId) ;
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
    List<OrderDto> getMyPlacedOrders(Long userId);
}
