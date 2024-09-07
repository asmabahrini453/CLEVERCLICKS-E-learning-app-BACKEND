package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.AnalyticsResponse;
import com.pfa.project.Dto.OrderDto;
import com.pfa.project.Entities.Enum.OrderStatus;
import com.pfa.project.Entities.Order;
import com.pfa.project.Repository.AdminRepository;
import com.pfa.project.Repository.OrderRepository;
import com.pfa.project.Service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDto> getAllPlacedOrders() {
        List<Order> orderList = orderRepository
                .findAllByOrderStatusIn(Arrays.asList(OrderStatus.Approved, OrderStatus.Refused, OrderStatus.Pending));
        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto changeOrderStatus(Long id, String orderStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (Objects.equals(orderStatus, "Approved")) {
                order.setOrderStatus(OrderStatus.Approved);
            } else if (Objects.equals(orderStatus, "Refused")) {
                order.setOrderStatus(OrderStatus.Refused);
            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }

    @Override
    public AnalyticsResponse calculateAnalytics() {
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);

        Long currentMonthOrders = getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthOrders = getTotalOrdersForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());
        Long currentMonthEarnings = getTotalEarningsForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthEarnings = getTotalEarningsForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long approved = orderRepository.countByOrderStatus(OrderStatus.Approved);
        Long refused = orderRepository.countByOrderStatus(OrderStatus.Refused);
        Long pending = orderRepository.countByOrderStatus(OrderStatus.Pending);

        return new AnalyticsResponse(approved, refused, pending, currentMonthOrders, previousMonthOrders, currentMonthEarnings, previousMonthEarnings);
    }

    public Long getTotalOrdersForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Approved);
        return (long) orders.size();
    }

    public Long getTotalEarningsForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Approved);
        return orders.stream().mapToLong(Order::getTotalAmount).sum();
    }
}
