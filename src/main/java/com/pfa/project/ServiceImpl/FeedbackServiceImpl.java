package com.pfa.project.ServiceImpl;

import com.pfa.project.Dto.FeedbackDto;
import com.pfa.project.Dto.RequestCourse;
import com.pfa.project.Entities.*;
import com.pfa.project.Repository.*;
import com.pfa.project.Service.FeedbackService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final AdminRepository adminRepository ;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    @Override
    public List<FeedbackDto> getAllFeedbacks() {
        List<Feedback> f = feedbackRepository.findAll();
        return f.stream().map(Feedback::getDto).collect(Collectors.toList());
    }


    @Override
    public FeedbackDto createFeedback(FeedbackDto feedbackDto)  {

        Feedback f = new Feedback();
        f.setComment(feedbackDto.getComment());

        User u  = userRepository.findById(feedbackDto.getUserId()).orElseThrow();
        f.setUser(u);

        Admin admin = adminRepository.findById(1L).orElseThrow();
        f.setAdmin(admin);

        return feedbackRepository.save(f).getDto();
    }


    @Override
    public boolean deleteFeedback(Long id) {
        Optional<Feedback> Feedback = feedbackRepository.findById(id);
        if (Feedback.isPresent()) {
            feedbackRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
