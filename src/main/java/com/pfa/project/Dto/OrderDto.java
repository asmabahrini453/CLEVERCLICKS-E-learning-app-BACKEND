package com.pfa.project.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pfa.project.Entities.Admin;
import com.pfa.project.Entities.Enum.OrderStatus;
import com.pfa.project.Entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
     Long id;
     Long totalAmount ;
     OrderStatus orderStatus ;
     Instant createdAt ;
     String username;

     List<CartItemDto> cartItems ;


}
