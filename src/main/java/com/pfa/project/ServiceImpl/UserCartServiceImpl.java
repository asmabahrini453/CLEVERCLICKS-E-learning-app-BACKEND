package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.AddCourseInCartDto;
import com.pfa.project.Dto.CartItemDto;
import com.pfa.project.Dto.OrderDto;
import com.pfa.project.Dto.PlaceOrderDto;
import com.pfa.project.Entities.CartItems;
import com.pfa.project.Entities.Course;
import com.pfa.project.Entities.Enum.OrderStatus;
import com.pfa.project.Entities.Order;
import com.pfa.project.Entities.User;
import com.pfa.project.Repository.CartItemRepository;
import com.pfa.project.Repository.CourseRepository;
import com.pfa.project.Repository.OrderRepository;
import com.pfa.project.Repository.UserRepository;
import com.pfa.project.Service.UserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCartServiceImpl implements UserCartService {
    @Autowired
    private OrderRepository orderRepository ;
    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private CartItemRepository cartItemRepository ;
    @Autowired
    private CourseRepository courseRepository ;

    public Long addCourseToCart(AddCourseInCartDto addCourseToCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addCourseToCartDto.getUserId(), OrderStatus.Pending);

        if (activeOrder == null) {
            return 0L;
        }

        Optional<CartItems> optionalCartItems = cartItemRepository.findByCourseIdAndOrderIdAndUserId(
                addCourseToCartDto.getCourseId(), activeOrder.getId(), addCourseToCartDto.getUserId());

        if (optionalCartItems.isPresent()) {
            return 0L;
        } else {
            Optional<Course> optionalCourse = courseRepository.findById(addCourseToCartDto.getCourseId());
            Optional<User> optionalUser = userRepository.findById(addCourseToCartDto.getUserId());

            if (optionalCourse.isPresent() && optionalUser.isPresent()) {
                CartItems cart = new CartItems();
                cart.setCourse(optionalCourse.get());
                cart.setPrice(optionalCourse.get().getPrice());
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updatedCart = cartItemRepository.save(cart);

                Long totalAmount = activeOrder.getCartItems().stream().mapToLong(CartItems::getPrice).sum();
                activeOrder.setTotalAmount(totalAmount);

                orderRepository.save(activeOrder);

                return cart.getId();
            } else {
                return 0L;
            }
        }
    }

    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);

        if (activeOrder == null) {
            System.out.println("No active order found for user ID " + userId);
            return null;
        }

        if (activeOrder.getCartItems() == null || activeOrder.getCartItems().isEmpty()) {
            System.out.println("No cart items found for order ID " + activeOrder.getId());
            return null;
        }

        List<CartItemDto> cartItemDtoList = activeOrder.getCartItems().stream()
                .map(CartItems::getCartDto)
                .collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemDtoList);
        orderDto.setCreatedAt(activeOrder.getCreatedAt());

        if (activeOrder.getUser() != null) {
            orderDto.setUsername(activeOrder.getUser().getName());
        }

        return orderDto;
    }





    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser =userRepository.findById(placeOrderDto.getUserId());
        if(optionalUser.isPresent()){
            activeOrder.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(activeOrder);
            Order order = new Order();
            order.setTotalAmount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            return activeOrder.getOrderDto();


        }

        return null;
    }

    public  List<OrderDto> getMyPlacedOrders(Long userId){
        return orderRepository.findByUserIdAndOrderStatusIn(userId,List.of(OrderStatus.Pending,
                OrderStatus.Approved,OrderStatus.Refused)).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }






}
