package jpa.schedule.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;



@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

	@Id @GeneratedValue
	@Column(name = "comment_id")
	private Long commentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	private String content;

	@CreatedDate
	private LocalDateTime writtenTime;

	@LastModifiedDate
	private LocalDateTime updatedTime;

	@Builder
	public Comment(Long commentId, User user, Schedule schedule, String content) {
		this.commentId = commentId;
		this.user = user;
		this.schedule = schedule;
		this.content = content;
	}

	public void update(String content) {
		this.content = content != null ? content : this.content;
	}

}
