package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.hudyma.dto.ScheduleDto;
import ua.hudyma.service.WorkScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class WorkScheduleController {
    private final WorkScheduleService workScheduleService;

    @PostMapping("/{unitId}")
    public ResponseEntity<String> createOrUpdateSchedule (@PathVariable Long unitId,
                                                  @RequestBody List<ScheduleDto> dtoList){
        workScheduleService.createOrUpdateSchedule(dtoList, unitId);
        return ResponseEntity.ok().build();
    }
}
