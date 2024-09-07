package com.pfa.project.Dto;

import com.pfa.project.Entities.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestChapter {
    private Long id;
    private String title;
    private List<String> contents;
    private Status status;
    private List<Long> imageIds;
    private Long courseId;
    private String courseTitle;
}
