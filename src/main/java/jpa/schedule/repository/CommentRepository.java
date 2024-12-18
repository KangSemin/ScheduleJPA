package jpa.schedule.repository;

import jpa.schedule.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.schedule.scheduleId = :scheduleId ORDER BY c.writtenTime DESC")
    List<Comment> findBySchedule_ScheduleIdOrderByWrittenTimeDesc(@Param("scheduleId") Long scheduleId);
}
