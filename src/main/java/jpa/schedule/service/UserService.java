package jpa.schedule.service;

import jpa.schedule.config.PasswordEncoder;
import jpa.schedule.domain.User;
import jpa.schedule.dto.LoginRequestDto;
import jpa.schedule.dto.UserRequestDto;
import jpa.schedule.dto.UserResponseDto;
import jpa.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public Long createUser(UserRequestDto requestDto) {

		if (userRepository.existsByEmail(requestDto.getEmail())) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}

		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
		
		User user = User.builder()
				.email(requestDto.getEmail())
				.password(encodedPassword)
				.username(requestDto.getUsername())
				.build();

		return userRepository.save(user).getUserId();
	}

	public User login(LoginRequestDto requestDto) {
		User user = userRepository.findByEmail(requestDto.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("등록된지 않은 이메일입니다."));

		// 비밀번호 검증
		if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		return user;
	}

	public UserResponseDto getUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		return new UserResponseDto(user);
	}

	public void updateUser(Long id, UserRequestDto requestDto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		String password = requestDto.getPassword() != null ? 
				passwordEncoder.encode(requestDto.getPassword()) : 
				user.getPassword();

		user.update(requestDto.getEmail(), 
				   password,
				   requestDto.getUsername());
	}

	public void deleteUser(Long id) {
		 userRepository.findById(id).get().markAsDeleted();
	}
}