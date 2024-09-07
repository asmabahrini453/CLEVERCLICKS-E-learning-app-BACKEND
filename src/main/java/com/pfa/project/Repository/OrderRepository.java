package com.pfa.project.Repository;

import com.pfa.project.Entities.Enum.OrderStatus;
import com.pfa.project.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
 Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
List<Order> findByUserIdAndOrderStatusIn(Long userId ,List<OrderStatus> orderStatusList);
 List<Order> findAllByOrderStatusIn(List<OrderStatus> statuses);
 List<Order> findByDateBetweenAndOrderStatus(Date startDate, Date endDate, OrderStatus status);
 Long countByOrderStatus(OrderStatus status);
}
