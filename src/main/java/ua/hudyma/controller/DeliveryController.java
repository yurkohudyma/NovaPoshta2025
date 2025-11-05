package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.hudyma.dto.DeliveryReqDto;
import ua.hudyma.enums.DeliveryRespDto;
import ua.hudyma.service.DeliveryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<String> createDelivery (
            @RequestBody DeliveryReqDto dto){
        deliveryService.createDelivery (dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DeliveryRespDto>> fetchUserDeliveries (
            @RequestParam String userCode){
        return ResponseEntity.ok(deliveryService
                .getAllDeliveriesByUserCode (userCode));
    }
}
