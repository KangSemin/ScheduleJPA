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
    @Query("SELECT new jpa.schedule.dto.ScheduleListResponseDto(s, COUNT(c)) " +
           "FROM Schedule s " +
           "LEFT JOIN s.comments c " +
           "GROUP BY s " +
           "ORDER BY s.updatedTime DESC")
    Page<ScheduleListResponseDto> findAllWithCommentCount(Pageable pageable);
}
