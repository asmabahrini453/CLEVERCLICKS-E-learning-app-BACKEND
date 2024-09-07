package com.pfa.project.Dto;

import java.util.List;

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
public class GetChapterDto {
    private Long id;
    private String title;
    private List<String> contents;
    private Status status;
    private List<Long> imageIds;
    private Long courseId;
    private List<ImageData> images;
}
