package com.example.apiproject.controller;

import com.example.apiproject.dto.VisitorDTO;
import com.example.apiproject.dto.ApiResponse;
import com.example.apiproject.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Visitors", description = "Visitor management endpoints")
@RestController
@RequestMapping("/api/visitors")
@SecurityRequirement(name = "bearerAuth")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @Operation(summary = "Create new visitor")
    @PostMapping
    public ResponseEntity<ApiResponse> createVisitor(@Valid @RequestBody VisitorDTO visitorDTO) {
        VisitorDTO created = visitorService.createVisitor(visitorDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Visitor created successfully", created));
    }

    @Operation(summary = "Get all visitors")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllVisitors() {
        List<VisitorDTO> visitors = visitorService.getAllVisitors();
        return ResponseEntity.ok(new ApiResponse(true, "Visitors retrieved", visitors));
    }

    @Operation(summary = "Get visitor by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getVisitor(
            @Parameter(description = "Visitor ID") @PathVariable Long id) {
        VisitorDTO visitor = visitorService.getVisitor(id);
        return ResponseEntity.ok(new ApiResponse(true, "Visitor found", visitor));
    }
}
