package com.pfa.project.Entities;

import com.pfa.project.Dto.CartItemDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.time.Instant;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  Long price ;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "course_id" , nullable = false )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course ;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "user_id" , nullable = false )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public CartItemDto getCartDto (){
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(id);
        cartItemDto.setPrice(price);
        cartItemDto.setCourseId(course.getId());
        cartItemDto.setUserId(user.getId());
        cartItemDto.setCourseName(course.getTitle());
        cartItemDto.setReturnedImg(course.getImg());

        return cartItemDto ;

    }

}
