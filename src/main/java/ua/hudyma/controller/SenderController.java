package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.enums.EntityType;
import ua.hudyma.service.SenderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/senders")
public class SenderController {
    private final SenderService senderService;

    @GetMapping
    public ResponseEntity<String> createBatchSenders (
            @RequestParam Integer quantity, @RequestParam EntityType type){
        senderService.createSender(quantity, type);
        return ResponseEntity.ok().build();
    }
}
