package jpa.schedule.service;

import jpa.schedule.domain.Comment;
import jpa.schedule.domain.Schedule;
import jpa.schedule.domain.User;
import jpa.schedule.dto.CommentRequestDto;
import jpa.schedule.dto.CommentResponseDto;
import jpa.schedule.repository.CommentRepository;
import jpa.schedule.repository.ScheduleRepository;
import jpa.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto, Long userId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .schedule(schedule)
                .user(user)
                .build();

        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
        }

        comment.update(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    public List<CommentResponseDto> getScheduleComments(Long scheduleId) {
        return commentRepository.findBySchedule_ScheduleIdOrderByWrittenTimeDesc(scheduleId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
