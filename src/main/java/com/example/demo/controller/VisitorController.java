package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Visitor;
import com.example.demo.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@Tag(name = "Visitors", description = "Visitor management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping
    @Operation(summary = "Create a new visitor")
    public ResponseEntity<ApiResponse<Visitor>> createVisitor(@Valid @RequestBody Visitor visitor) {
        Visitor createdVisitor = visitorService.createVisitor(visitor);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Visitor created successfully", createdVisitor));
    }

    @GetMapping
    @Operation(summary = "Get all visitors")
    public ResponseEntity<ApiResponse<List<Visitor>>> getAllVisitors() {
        List<Visitor> visitors = visitorService.getAllVisitors();
        return ResponseEntity.ok(new ApiResponse<>(true, "Visitors retrieved successfully", visitors));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get visitor by ID")
    public ResponseEntity<ApiResponse<Visitor>> getVisitor(@PathVariable Long id) {
        Visitor visitor = visitorService.getVisitor(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Visitor retrieved successfully", visitor));
    }
}