package jpa.schedule.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jpa.schedule.dto.UserRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;

	private String username;

	@Email
	private String email;
	private String password;

	private LocalDateTime registerTime;

	@OneToMany(mappedBy = "user")
	private List<Schedule> schedules = new ArrayList<>();

	public User(UserRequestDto requestDto) {
		username = requestDto.getUsername();
		email =requestDto.getEmail();
		password = requestDto.getPassword();
		registerTime = LocalDateTime.now();
	}

	public void update(UserRequestDto requestDto) {
		this.username = requestDto.getUsername();
		this.password = requestDto.getPassword();
	}
}
