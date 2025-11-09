package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.dto.SenderContactsDto;
import ua.hudyma.service.SenderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/senders")
public class SenderController {
    private final SenderService senderService;

    @GetMapping
    public ResponseEntity<String> createBatchSenders (
            @RequestParam Integer quantity){
        senderService.createSender(quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<SenderContactsDto>> fetchSenderContacts (
            @RequestParam String senderCode){
        return ResponseEntity.ok(senderService
                .fetchSenderContacts (senderCode));
    }
}
