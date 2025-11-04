package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.enums.EntityType;
import ua.hudyma.service.AddresseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/addressees")
public class AddresseeController {
    private final AddresseService addresseService;

    @GetMapping
    public ResponseEntity<String> createBatchAddressees (
            @RequestParam Integer quantity, @RequestParam EntityType type){
        addresseService.createAddressee(quantity, type);
        return ResponseEntity.ok().build();
    }
}
