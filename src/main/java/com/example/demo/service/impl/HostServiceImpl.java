package com.example.demo.service.impl;

import com.example.demo.entity.Host;
import com.example.demo.repository.HostRepository;
import com.example.demo.service.HostService;

import java.util.List;

public class HostServiceImpl implements HostService {

    private HostRepository hostRepository;

    public HostServiceImpl() {}

    @Override
    public Host createHost(Host host) {
        return hostRepository.save(host);
    }

    @Override
    public Host getHost(Long id) {
        return hostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Host not found"));
    }

    @Override
    public List<Host> getAllHosts() {
        return hostRepository.findAll();
    }
}
