package com.rewe.rest;

import com.rewe.models.Statistics;
import com.rewe.repository.StatisticsRepository;
import com.rewe.services.EmailSenderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/emails")
@RestController
@AllArgsConstructor
public class EmailsController {

    private final StatisticsRepository statisticsRepository;

    private final EmailSenderService senderService;

    @Operation(summary = "Generates emails randomly")
    @PostMapping(path = "/generate", produces = {"application/json"})
    public void generate(@RequestParam(defaultValue = "30") Integer generate) {
        senderService.execute(generate);
    }

    @Operation(summary = "Email statistics")
    @GetMapping(path = "/", produces = {"application/json"})
    public List<Statistics> statistics() {
        return statisticsRepository.findAll();
    }
}

