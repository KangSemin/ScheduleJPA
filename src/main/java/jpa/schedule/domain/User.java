package jpa.schedule.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jpa.schedule.dto.UserRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(unique = true)
	private String email;

	private String password;
	private String username;

	@CreatedDate
	private LocalDateTime registerTime;

	@OneToMany(mappedBy = "user")
	private List<Schedule> schedules = new ArrayList<>();

	@Builder
	public User(String email, String password, String username) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.registerTime = LocalDateTime.now();
	}

	public void update(String email, String password, String username) {
		this.email = email != null ? email : this.email;
		this.password = password != null ? password : this.password;
		this.username = username != null ? username : this.username;
	}
}
