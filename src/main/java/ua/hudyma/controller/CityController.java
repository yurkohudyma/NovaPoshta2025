package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.hudyma.enums.EntityType;
import ua.hudyma.service.AddresseeService;
import ua.hudyma.service.CityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<String> createCitiesBatch (@RequestBody String[] cityNames){
        cityService.createBatchCities(cityNames);
        return ResponseEntity.ok().build();
    }
}
