package jpa.schedule.dto;

import jpa.schedule.domain.Schedule;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ScheduleListResponseDto {
	private Long scheduleId;
	private String title;
	private String task;
	private Long commentCount;
	private LocalDateTime postedTime;
	private LocalDateTime updatedTime;
	private String username;

	public ScheduleListResponseDto(Schedule schedule, Long commentCount) {
		this.scheduleId = schedule.getScheduleId();
		this.title = schedule.getTitle();
		this.task = schedule.getTask();
		this.commentCount = commentCount;
		this.postedTime = schedule.getPostedTime();
		this.updatedTime = schedule.getUpdatedTime();
		this.username = schedule.getUser().getDisplayName();
	}
}
