package com.demo.kafka2kafka.controller;

import com.demo.kafka2kafka.dto.MessageDto;
import com.demo.kafka2kafka.event.MessageEvent;
import com.demo.kafka2kafka.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BaseController {

    private final MessageService messageService;

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody MessageDto message) {
        MessageEvent event = messageService.add(message);
        return ResponseEntity.ok("success");
    }
}
