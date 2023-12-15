package com.rewe.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/emails")
@RestController
public class EmailsController {

    @Operation(summary = "Gets a list of repositories with optional filters")
    @GetMapping(path = "/hello", produces = {"application/json"})
    public String index() {
        return "<b>Greetings from Spring Boot!<b>";
    }
}
