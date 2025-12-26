package com.example.demo.service;

import com.example.demo.entity.Host;
import java.util.List;

public interface HostService {
    Host createHost(Host host);
    Host getHost(Long id);
    List<Host> getAllHosts();
    Host getHostByEmail(String email);
}