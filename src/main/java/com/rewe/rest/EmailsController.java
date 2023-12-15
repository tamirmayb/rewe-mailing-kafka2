package com.rewe.rest;

import com.rewe.services.EmailSenderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/emails")
@RestController
@AllArgsConstructor
public class EmailsController {

    @Operation(summary = "Generates emails randomly")
    @PostMapping(path = "/generate", produces = {"application/json"})
    public void generate(@RequestParam(defaultValue = "30") Integer generate) {
        senderService.execute(generate);
    }

    private final EmailSenderService senderService;
}

