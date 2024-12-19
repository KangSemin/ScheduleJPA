package jpa.schedule.domain;

import jakarta.persistence.*;
import jpa.schedule.dto.ScheduleRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity @Getter @Table(name = "schedules")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long scheduleId;

	private String title;
	private String task;

	@CreatedDate
	private LocalDateTime postedTime;
	@LastModifiedDate
	private LocalDateTime updatedTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;


	@Builder
	public Schedule(String title, String task, User user) {
		this.title = title;
		this.task = task;
		this.user = user;
	}

	public void update(String title, String task) {
		this.title = title;
		this.task = task;
	}

}
