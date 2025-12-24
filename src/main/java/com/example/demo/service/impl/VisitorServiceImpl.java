package com.example.apiproject.service.impl;

import com.example.apiproject.dto.VisitorDTO;
import com.example.apiproject.model.Visitor;
import com.example.apiproject.exception.ResourceNotFoundException;
import com.example.apiproject.repository.VisitorRepository;
import com.example.apiproject.service.VisitorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorServiceImpl(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public VisitorDTO createVisitor(VisitorDTO dto) {
        Visitor visitor = dtoTomodel(dto);
        Visitor saved = visitorRepository.save(visitor);
        return modelToDto(saved);
    }

    @Override
    public VisitorDTO getVisitor(Long id) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));
        return modelToDto(visitor);
    }

    @Override
    public List<VisitorDTO> getAllVisitors() {
        return visitorRepository.findAll().stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private Visitor dtoTomodel(VisitorDTO dto) {
        Visitor visitor = new Visitor();
        visitor.setId(dto.getId());
        visitor.setFullName(dto.getFullName());
        visitor.setEmail(dto.getEmail());
        visitor.setPhone(dto.getPhone());
        visitor.setIdProofNumber(dto.getIdProofNumber());
        return visitor;
    }

    private VisitorDTO modelToDto(Visitor visitor) {
        return new VisitorDTO(
                visitor.getId(),
                visitor.getFullName(),
                visitor.getEmail(),
                visitor.getPhone(),
                visitor.getIdProofNumber()
        );
    }
}
