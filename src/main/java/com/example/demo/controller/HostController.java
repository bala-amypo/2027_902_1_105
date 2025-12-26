package com.example.demo.controller;

import com.example.demo.model.Host;
import com.example.demo.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hosts")
public class HostController {

    @Autowired
    private HostService hostService;

    @PostMapping
    public ResponseEntity<Host> createHost(@RequestBody Host host) {
        return ResponseEntity.ok(hostService.createHost(host));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Host> getHost(@PathVariable Long id) {
        return ResponseEntity.ok(hostService.getHost(id));
    }

    @GetMapping
    public ResponseEntity<List<Host>> getAllHosts() {
        return ResponseEntity.ok(hostService.getAllHosts());
    }
}
