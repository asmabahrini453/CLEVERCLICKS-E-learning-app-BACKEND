package com.pfa.project.Dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDto {
    Long id ;
    String comment;
    Long adminId;
    Long userId;
}
