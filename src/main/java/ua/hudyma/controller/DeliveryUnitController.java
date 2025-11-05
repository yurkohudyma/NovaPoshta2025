package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.dto.DeliveryReqDto;
import ua.hudyma.enums.DeliveryUnitReqDto;
import ua.hudyma.service.DeliveryService;
import ua.hudyma.service.DeliveryUnitService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/units")
public class DeliveryUnitController {
    private final DeliveryUnitService deliveryUnitService;

    @PostMapping
    public ResponseEntity<String> createDeliveryUnit (@RequestBody DeliveryUnitReqDto dto){
        deliveryUnitService.createDeliveryUnit (dto);
        return ResponseEntity.ok().build();
    }
}
