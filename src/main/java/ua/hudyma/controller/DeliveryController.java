package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.hudyma.dto.DeliveryReqDto;
import ua.hudyma.dto.DeliveryRespDto;
import ua.hudyma.enums.DeliveryTrackDto;
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

    @PatchMapping("/refuse")
    public ResponseEntity<String> refuseDelivery (@RequestParam String ttn){
        deliveryService.refuseDelivery(ttn);
        return ResponseEntity.ok("Delivery "+ ttn + " HAS been REFUSED");
    }
    @PatchMapping("/redirect")
    public ResponseEntity<String> redirectDelivery (@RequestParam String ttn,
                                                    @RequestParam String newDigitalAddress){
        deliveryService.redirectDelivery(ttn, newDigitalAddress);
        return ResponseEntity.ok("Delivery "+ ttn + " HAS been REDIRECTED");
    }

    @GetMapping("/track")
    public ResponseEntity<DeliveryTrackDto> trackDelivery (
            @RequestParam String ttn){
        return ResponseEntity.ok(deliveryService
                .trackDelivery (ttn));
    }


}
