package com.pfa.project.Service;

import com.pfa.project.Dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    boolean deleteFeedback(Long id);
    FeedbackDto createFeedback(FeedbackDto feedbackDto);
    List<FeedbackDto> getAllFeedbacks();
}
