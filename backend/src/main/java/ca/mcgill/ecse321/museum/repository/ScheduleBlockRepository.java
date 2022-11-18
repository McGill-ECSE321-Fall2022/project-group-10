/* (C)2022 */
package ca.mcgill.ecse321.museum.repository;

import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.model.ScheduleBlock.ScheduleEvent;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleBlockRepository extends JpaRepository<ScheduleBlock, Long> {
    public List<ScheduleBlock> findScheduleBlockByStartDateGreaterThanEqualAndEndDateLessThanEqual(
            Date startDate, Date endDate);

    public List<ScheduleBlock> findScheduleBlockByStartDateGreaterThanEqual(Date startDate);

    public List<ScheduleBlock> findScheduleBlockByEndDateLessThanEqual(Date endDate);

    public List<ScheduleBlock>
            findScheduleBlockByStartDateGreaterThanEqualAndEndDateLessThanEqualAndEvent(
                    Date startDate, Date endDate, ScheduleEvent event);
}
