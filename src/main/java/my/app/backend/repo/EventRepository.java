package my.app.backend.repo;

import my.app.backend.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e where " +
            "(:title is null or :title='' or lower(e.title) like lower(concat('%', :title,'%'))) and" +
            "(:categoryId is null or e.categoryId=:categoryId) and " + // учитываем, что параметр может быть null или пустым
            "(" +
            "(cast(:dateFrom as timestamp) is null or e.date>=:dateFrom) and " +
            "(cast(:dateTo as timestamp) is null or e.date<=:dateTo)" +
            ") and " +
            "(:userId is null or e.userId=:userId)" // показывать задачи только определенного пользователя, а не все
    )
        // искать по всем переданным параметрам (пустые параметры учитываться не будут)
    Page<Event> findByParams(@Param("title") String title,
                             @Param("userId") Long userId,
                             @Param("categoryId") Long categoryId,
                             @Param("dateFrom") Date dateFrom,
                             @Param("dateTo") Date dateTo,
                             Pageable pageable
    );
    List<Event> findAllByUserId(Long userId);
}
