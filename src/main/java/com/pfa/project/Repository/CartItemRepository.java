package com.pfa.project.Repository;

import com.pfa.project.Entities.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItems , Long> {
    Optional<CartItems> findByCourseIdAndOrderIdAndUserId(Long courseId , Long OrderId , Long userId);
}
