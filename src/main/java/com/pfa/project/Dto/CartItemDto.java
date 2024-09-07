package com.pfa.project.Dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id ;
    private Long price ;
    private Long courseId ;
    private Long orderId ;
    private Long userId ;
    private String courseName ;
    private byte[] returnedImg ;

}
