package jpa.schedule.controller;

import jpa.schedule.dto.CommentRequestDto;
import jpa.schedule.dto.CommentResponseDto;
import jpa.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @RequestBody @Valid CommentRequestDto requestDto,
            HttpServletRequest request) {
        Long userId = getUserId(request);
        return ResponseEntity.ok(commentService.createComment(scheduleId, requestDto, userId));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getScheduleComments(
            @PathVariable Long scheduleId) {
        return ResponseEntity.ok(commentService.getScheduleComments(scheduleId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto requestDto,
            HttpServletRequest request) {
        Long userId = getUserId(request);
        return ResponseEntity.ok(commentService.updateComment(commentId, requestDto, userId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            HttpServletRequest request) {
        Long userId = getUserId(request);
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok().build();
    }


    private Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        return (Long) session.getAttribute("userId");
    }
}
