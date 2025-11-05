package ua.hudyma.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.WorkSchedule;
import ua.hudyma.dto.ScheduleDto;
import ua.hudyma.exception.DtoObligatoryFieldsAreMissingException;
import ua.hudyma.repository.DeliveryUnitRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class WorkScheduleService {
    private final WorkScheduleRepository workScheduleRepository;
    private final DeliveryUnitRepository deliveryUnitRepository;

    @Transactional
    public void createOrUpdateSchedule(List<ScheduleDto> dtoList, Long unitId) {
        if (dtoList == null || dtoList.isEmpty() || unitId == null){
            throw new DtoObligatoryFieldsAreMissingException
                    ("Schedule Dto List is NULL or empty or unitId not provided");
        }
        var schedule = workScheduleRepository.findByDeliveryUnitId(unitId);
        if (schedule.isEmpty()){
            var newSchedule = new WorkSchedule();
            var unit = deliveryUnitRepository
                    .findById(unitId).orElseThrow(() ->
                            new EntityNotFoundException("Unit " + unitId + " NOT FOUND"));
            newSchedule.setScheduleDtoList(dtoList);
            newSchedule.setDeliveryUnit(unit);
            workScheduleRepository.save(newSchedule);
            log.info("::: Schedule {} CREATED", newSchedule.getId());
            unit.setWorkSchedule(newSchedule);
        }
        else {
            var scheduleOpt = schedule.get();
            scheduleOpt.setScheduleDtoList(dtoList);
            log.info("::: Schedule {} UPDATED", scheduleOpt.getId());
        }
    }

    public List<ScheduleDto> fetchScheduleDtoListForUnit(String digitalAddress) {
        return workScheduleRepository
                .findByDeliveryUnitId_DigitalAddress(digitalAddress)
                .orElseThrow( () ->
                        new EntityNotFoundException(
                                "::: Unit " + digitalAddress + " NOT FOUND"))
                .getScheduleDtoList();
    }
}
