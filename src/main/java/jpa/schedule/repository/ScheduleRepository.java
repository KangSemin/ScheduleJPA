package jpa.schedule.repository;

import jpa.schedule.domain.Schedule;
import jpa.schedule.dto.ScheduleListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT new jpa.schedule.dto.ScheduleListResponseDto(s, (SELECT COUNT(c) FROM Comment c WHERE c.schedule = s)) " +
           "FROM Schedule s " +
           "ORDER BY s.updatedTime DESC")
    Page<ScheduleListResponseDto> findAllWithCommentCount(Pageable pageable);
}
