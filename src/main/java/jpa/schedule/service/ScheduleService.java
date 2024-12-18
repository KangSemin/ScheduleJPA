package jpa.schedule.service;

import jpa.schedule.dto.ScheduleListResponseDto;
import jpa.schedule.dto.ScheduleRequestDto;
import jpa.schedule.dto.ScheduleResponseDto;
import jpa.schedule.domain.Schedule;
import jpa.schedule.repository.ScheduleRepository;
import jpa.schedule.repository.UserRepository;
import jpa.schedule.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	private static final int DEFAULT_PAGE_SIZE = 10;

	public Schedule findSchedule(Long id) {
		return scheduleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));
	}

	public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		Schedule schedule = new Schedule(requestDto.getTask(),requestDto.getTitle(), user);
		scheduleRepository.save(schedule);
		return new ScheduleResponseDto(schedule);
	}

	public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto, Long userId) {
		Schedule schedule = findSchedule(id);
		if (!schedule.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("수정 권한이 없습니다.");
		}
		schedule.update(requestDto.getTitle(),requestDto.getTask());
		return new ScheduleResponseDto(schedule);
	}

	public void deleteSchedule(Long id, Long userId) {
		Schedule schedule = findSchedule(id);
		if (!schedule.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("삭제 권한이 없습니다.");
		}
		scheduleRepository.delete(schedule);
	}

	public ScheduleResponseDto getScheduleById(Long id) {
		Schedule schedule = findSchedule(id);
		return new ScheduleResponseDto(schedule);
	}

	public Page<ScheduleListResponseDto> getScheduleList(int page, Integer size) {
		PageRequest pageRequest = PageRequest.of(
			page, 
			size != null ? size : DEFAULT_PAGE_SIZE,
			Sort.by(Sort.Direction.DESC, "updatedTime")
		);
		
		return scheduleRepository.findAllWithCommentCount(pageRequest);
	}
}
