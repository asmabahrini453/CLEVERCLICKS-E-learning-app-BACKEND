package com.pfa.project.Controller;

import com.pfa.project.Dto.AnalyticsResponse;
import com.pfa.project.Dto.OrderDto;
import com.pfa.project.Dto.RequestCourse;
import com.pfa.project.Service.AdminOrderService;
import com.pfa.project.Service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/pfa/admin/order")
@AllArgsConstructor
public class AdminOrderController {
    @Autowired
    private AdminOrderService adminOrderService;


    @GetMapping("")
    public ResponseEntity<List<OrderDto>> getAllPlacedOrders(){
        List<OrderDto> orders = adminOrderService.getAllPlacedOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{id}/{orderStatus}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long id,@PathVariable String orderStatus){
        OrderDto orderDto = adminOrderService.changeOrderStatus(id,orderStatus);
        if(orderDto == null){
            return new ResponseEntity<>("Something went wrong!" , HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/analytics")
    public ResponseEntity<AnalyticsResponse> getAnalytics(){
        return ResponseEntity.ok(adminOrderService.calculateAnalytics());
    }
}
