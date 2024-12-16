package jpa.schedule.dto;

import jpa.schedule.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
	private Long commentId;
	private String content;
	private String username;
	private LocalDateTime writtenTime;
	private LocalDateTime updatedTime;

	public CommentResponseDto(Comment comment) {
		this.commentId = comment.getCommentId();
		this.content = comment.getContent();
		this.username = comment.getUser().getUsername();
		this.writtenTime = comment.getWrittenTime();
		this.updatedTime = comment.getUpdatedTime();
	}
}
