package jpa.schedule.dto;


import jpa.schedule.domain.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

	private final String title;
	private final String task;
	private final String username;
	private final LocalDateTime postedTime;
	private final LocalDateTime updatedTime;

	public ScheduleResponseDto(Schedule schedule) {
		this.title = schedule.getTitle();
		this.task = schedule.getTask();
		this.username = schedule.getUser().getUsername();
		this.postedTime = schedule.getPostedTime();
		this.updatedTime = schedule.getUpdatedTime();
	}
}
