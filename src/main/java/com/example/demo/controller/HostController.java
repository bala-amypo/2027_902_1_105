package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Host;
import com.example.demo.service.HostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hosts")
@Tag(name = "Hosts", description = "Host/Employee management")
@SecurityRequirement(name = "Bearer Authentication")
public class HostController {

    private final HostService hostService;

    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    @PostMapping
    @Operation(summary = "Create a new host")
    public ResponseEntity<ApiResponse<Host>> createHost(@Valid @RequestBody Host host) {
        Host createdHost = hostService.createHost(host);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Host created successfully", createdHost));
    }

    @GetMapping
    @Operation(summary = "Get all hosts")
    public ResponseEntity<ApiResponse<List<Host>>> getAllHosts() {
        List<Host> hosts = hostService.getAllHosts();
        return ResponseEntity.ok(new ApiResponse<>(true, "Hosts retrieved successfully", hosts));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get host by ID")
    public ResponseEntity<ApiResponse<Host>> getHost(@PathVariable Long id) {
        Host host = hostService.getHost(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Host retrieved successfully", host));
    }
}