package jpa.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jpa.schedule.dto.ScheduleRequestDto;
import jpa.schedule.dto.ScheduleResponseDto;
import jpa.schedule.dto.ScheduleListResponseDto;
import jpa.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

	private final ScheduleService scheduleService;

	@PostMapping
	public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto,
	                                                          HttpServletRequest request) {
		Long userId = getUserId(request);
		return ResponseEntity.ok(scheduleService.createSchedule(requestDto, userId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
		return ResponseEntity.ok(scheduleService.getScheduleById(id));
	}

	@GetMapping
	public ResponseEntity<Page<ScheduleListResponseDto>> getScheduleList(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) Integer size) {
		return ResponseEntity.ok(scheduleService.getScheduleList(page, size));
	}
//	@GetMapping
//	public String list(Model model,
//	                   @RequestParam(defaultValue = "0") int page,
//	                   @RequestParam(defaultValue = "10") int size) {
//
//		Page<ScheduleListResponseDto> schedules = scheduleService.getScheduleList(page, size);
//		model.addAttribute("schedules", schedules);
//		return "schedule/list";
//	}

	@PutMapping("/{id}")
	public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id,
	                                                          @RequestBody ScheduleRequestDto requestDto,
	                                                          HttpServletRequest request) {
		Long userId = getUserId(request);
		return ResponseEntity.ok(scheduleService.updateSchedule(id, requestDto, userId));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSchedule(@PathVariable Long id,
	                                           HttpServletRequest request) {
		Long userId = getUserId(request);
		scheduleService.deleteSchedule(id, userId);
		return ResponseEntity.ok().build();
	}

	private Long getUserId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			throw new IllegalArgumentException("로그인이 필요합니다.");
		}
		return (Long) session.getAttribute("userId");
	}
}


