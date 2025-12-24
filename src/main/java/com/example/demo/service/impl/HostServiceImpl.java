package com.example.apiproject.service.impl;

import com.example.apiproject.dto.HostDTO;
import com.example.apiproject.model.Host;
import com.example.apiproject.exception.ResourceNotFoundException;
import com.example.apiproject.repository.HostRepository;
import com.example.apiproject.service.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;

    public HostServiceImpl(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    @Override
    public HostDTO createHost(HostDTO dto) {
        Host host = dtoTomodel(dto);
        Host saved = hostRepository.save(host);
        return modelToDto(saved);
    }

    @Override
    public HostDTO getHost(Long id) {
        Host host = hostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found"));
        return modelToDto(host);
    }

    @Override
    public List<HostDTO> getAllHosts() {
        return hostRepository.findAll().stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HostDTO getHostByEmail(String email) {
        Host host = hostRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found"));
        return modelToDto(host);
    }

    private Host dtoTomodel(HostDTO dto) {
        Host host = new Host();
        host.setId(dto.getId());
        host.setHostName(dto.getHostName());
        host.setFullname(dto.getFullname());
        host.setEmail(dto.getEmail());
        host.setDepartment(dto.getDepartment());
        host.setPhone(dto.getPhone());
        return host;
    }

    private HostDTO modelToDto(Host host) {
        return new HostDTO(
                host.getId(),
                host.getHostName(),
                host.getFullname(),
                host.getEmail(),
                host.getDepartment(),
                host.getPhone()
        );
    }
}