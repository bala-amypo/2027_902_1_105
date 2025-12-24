package com.example.demo.controller;

import com.example.demo.dto.HostDTO;
import com.example.demo.dto.ApiResponse;
import com.example.demo.service.HostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hosts", description = "Host/Employee management")
@RestController
@RequestMapping("/api/hosts")
@SecurityRequirement(name = "bearerAuth")
public class HostController {

    private final HostService hostService;

    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    @Operation(summary = "Create new host")
    @PostMapping
    public ResponseEntity<ApiResponse> createHost(@Valid @RequestBody HostDTO hostDTO) {
        HostDTO created = hostService.createHost(hostDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Host created successfully", created));
    }

    @Operation(summary = "Get all hosts")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllHosts() {
        List<HostDTO> hosts = hostService.getAllHosts();
        return ResponseEntity.ok(new ApiResponse(true, "Hosts retrieved", hosts));
    }

    @Operation(summary = "Get host by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getHost(
            @Parameter(description = "Host ID") @PathVariable Long id) {
        HostDTO host = hostService.getHost(id);
        return ResponseEntity.ok(new ApiResponse(true, "Host found", host));
    }
}