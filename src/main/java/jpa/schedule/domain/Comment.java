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
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	private String content;
	private LocalDateTime writtenTime;
	private LocalDateTime updatedTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	@Builder
	public Comment(String content, User user, Schedule schedule) {
		this.content = content;
		this.user = user;
		this.schedule = schedule;
		this.writtenTime = LocalDateTime.now();
		this.updatedTime = this.writtenTime;
	}

	public void update(String content) {
		this.content = content;
		this.updatedTime = LocalDateTime.now();
	}

}
