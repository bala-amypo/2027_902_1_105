package com.example.demo.service;

import com.example.demo.dto.HostDTO;
import java.util.List;

public interface HostService {
    HostDTO createHost(HostDTO hostDTO);
    HostDTO getHost(Long id);
    List<HostDTO> getAllHosts();
    HostDTO getHostByEmail(String email);
}
