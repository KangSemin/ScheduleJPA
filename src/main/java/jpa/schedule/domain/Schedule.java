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
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long scheduleId;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String task;

	@Column(name = "posted_time")
	private LocalDateTime postedTime;

	@Column(name = "updated_time")
	private LocalDateTime updatedTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Schedule(ScheduleRequestDto requestDto, User user) {
		this.title = requestDto.getTitle();
		this.task = requestDto.getTask();
		this.user = user;
		this.postedTime = LocalDateTime.now();
		this.updatedTime = LocalDateTime.now();
	}

	public void update(ScheduleRequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.task = requestDto.getTask();
		this.updatedTime = LocalDateTime.now();
	}

	public boolean isWriter(Long userId) {
		return this.user.getId() == userId;
	}
}
