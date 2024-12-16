package jpa.schedule.dto;

import jpa.schedule.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
	private final Long id;
	private final String username;
	private final String email;
	private final LocalDateTime registerTime;

	public UserResponseDto(User user) {
		this.id = user.getUserId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.registerTime = user.getRegisterTime();
	}
}
