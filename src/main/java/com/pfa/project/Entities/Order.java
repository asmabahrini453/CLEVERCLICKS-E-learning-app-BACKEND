package com.pfa.project.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pfa.project.Dto.OrderDto;
import com.pfa.project.Dto.RequestCourse;
import com.pfa.project.Entities.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long totalAmount;
    private OrderStatus orderStatus;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItems> cartItems;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setTotalAmount(totalAmount);
        orderDto.setCreatedAt(createdAt);
        orderDto.setCartItems(cartItems.stream().map(CartItems::getCartDto).collect(Collectors.toList()));
        if (user != null) {
            orderDto.setUsername(user.getName());
        }
        return orderDto;
    }
}
