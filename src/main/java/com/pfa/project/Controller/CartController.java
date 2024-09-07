package com.pfa.project.Controller;

import com.pfa.project.Dto.AddCourseInCartDto;
import com.pfa.project.Dto.OrderDto;
import com.pfa.project.Dto.PlaceOrderDto;
import com.pfa.project.Service.AdminService;
import com.pfa.project.Service.UserCartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pfa/cart")
@AllArgsConstructor
public class CartController {
    @Autowired
    private UserCartService userCartService;

   @PostMapping("")
    public Long addCourseToCart (@RequestBody AddCourseInCartDto addCourseInCartDto){
        return userCartService.addCourseToCart(addCourseInCartDto);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        OrderDto orderDto = userCartService.getCartByUserId(userId);
        if (orderDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(orderDto);
        } else {
            // Log the user ID and any other relevant information
            System.out.println("User ID " + userId + " has no cart items or order.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user.");
        }
    }



    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDto> placeOrder (@RequestBody PlaceOrderDto placeOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userCartService.placeOrder(placeOrderDto));
    }

    @GetMapping("/myOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getMyPlacedOrders (@PathVariable Long userId){
        return ResponseEntity.ok(userCartService.getMyPlacedOrders(userId));
    }


}
