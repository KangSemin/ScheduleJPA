package jpa.schedule.service;

import jpa.schedule.domain.User;
import jpa.schedule.dto.LoginRequestDto;
import jpa.schedule.dto.UserRequestDto;
import jpa.schedule.dto.UserResponseDto;
import jpa.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;


	public Long createUser(UserRequestDto requestDto) {
		// 이메일 중복 검증
		if (userRepository.existsByEmail(requestDto.getEmail())) {
			throw new IllegalArgumentException("이미 가입된 이메일입니다.");
		}

		User user = new User(requestDto);
		User savedUser = userRepository.save(user);
		return savedUser.getId();
	}

	public UserResponseDto getUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
		return new UserResponseDto(user);
	}

	public void updateUser(Long id, UserRequestDto requestDto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
		user.update(requestDto);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public User login(LoginRequestDto requestDto) {
		User user = userRepository.findByEmail(requestDto.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

		if (!requestDto.getPassword().equals(user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		return user;
	}
}