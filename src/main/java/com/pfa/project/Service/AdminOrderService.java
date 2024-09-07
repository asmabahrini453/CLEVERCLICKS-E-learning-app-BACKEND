package com.pfa.project.Service;

import com.pfa.project.Dto.AnalyticsResponse;
import com.pfa.project.Dto.OrderDto;

import java.util.List;

public interface AdminOrderService {
    List<OrderDto> getAllPlacedOrders();
    OrderDto changeOrderStatus(Long id , String orderStatus);
     AnalyticsResponse calculateAnalytics();

}
