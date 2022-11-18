package ca.mcgill.ecse321.museum.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.mcgill.ecse321.museum.model.ScheduleBlock;

public interface ScheduleBlockRepository extends JpaRepository<ScheduleBlock, Long> {
    public List<ScheduleBlock> findScheduleBlockByStartDateGreaterThanEqualAndEndDateLessThanEqual(Date startDate, Date endDate);
    public List<ScheduleBlock> findScheduleBlockByStartDateGreaterThanEqual(Date startDate);
    public List<ScheduleBlock> findScheduleBlockByEndDateLessThanEqual(Date endDate);
}
