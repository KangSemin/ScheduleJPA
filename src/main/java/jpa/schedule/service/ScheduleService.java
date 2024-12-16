package jpa.schedule.service;

import jpa.schedule.dto.ScheduleRequestDto;
import jpa.schedule.dto.ScheduleResponseDto;
import jpa.schedule.domain.Schedule;
import jpa.schedule.repository.ScheduleRepository;
import jpa.schedule.repository.UserRepository;
import jpa.schedule.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;
	private final UserRepository userRepository;

	public Schedule findSchedule(Long id) {
		return scheduleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));
	}

	public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		Schedule schedule = new Schedule(requestDto, user);
		scheduleRepository.save(schedule);
		return new ScheduleResponseDto(schedule);
	}

	public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto, Long userId) {
		Schedule schedule = findSchedule(id);
		if (!schedule.getUser().getId().equals(userId)) {
			throw new IllegalArgumentException("수정 권한이 없습니다.");
		}
		schedule.update(requestDto);
		return new ScheduleResponseDto(schedule);
	}

	public void deleteSchedule(Long id, Long userId) {
		Schedule schedule = findSchedule(id);
		if (!schedule.getUser().getId().equals(userId)) {
			throw new IllegalArgumentException("삭제 권한이 없습니다.");
		}
		scheduleRepository.delete(schedule);
	}

	public List<ScheduleResponseDto> getSchedules() {
		return scheduleRepository.findAllByOrderByPostedTimeDesc()
				.stream()
				.map(ScheduleResponseDto::new)
				.collect(Collectors.toList());
	}

	public ScheduleResponseDto getScheduleById(Long id) {
		Schedule schedule = findSchedule(id);
		return new ScheduleResponseDto(schedule);
	}
}
