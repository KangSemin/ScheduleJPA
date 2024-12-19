package jpa.schedule.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity @Table(name = "users") @Getter
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

	@LastModifiedDate
	private LocalDateTime updatedTime;

	private boolean deleted = false;
	private LocalDateTime deletedAt;

	@Builder
	public User(String email, String password, String username) {
		this.email = email;
		this.password = password;
		this.username = username;
	}

	public void update(String email, String password, String username) {
		this.email = email != null ? email : this.email;
		this.password = password != null ? password : this.password;
		this.username = username != null ? username : this.username;
	}

	public void markAsDeleted() {
		this.deleted = true;
		this.deletedAt = LocalDateTime.now();
		this.username = "[삭제된 사용자]";
		this.email = "deleted_" + this.userId + "@deleted.com";
	}

	public String getDisplayName() {
		return deleted ? "[삭제된 사용자]" : username;
	}
}
