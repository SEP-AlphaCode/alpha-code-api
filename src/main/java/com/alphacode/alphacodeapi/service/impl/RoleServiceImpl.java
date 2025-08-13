package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.dto.RoleDto;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.RoleMapper;
import com.alphacode.alphacodeapi.repository.RoleRepository;
import com.alphacode.alphacodeapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public List<RoleDto> getAll() {
        return repository.findAll().stream()
                .map(RoleMapper::toDto)
                .toList();
    }

    @Override
    public RoleDto getById(UUID id) {
        var role = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        return RoleMapper.toDto(role);
    }

    @Override
    public RoleDto create(RoleDto roleDto) {
        if (repository.findByName(roleDto.getName()) != null) {
            throw new IllegalArgumentException("Role with this name already exists");
        }

        var role = RoleMapper.toEntity(roleDto);
        role.setStatus(1);

        var savedRole = repository.save(role);
        return RoleMapper.toDto(savedRole);
    }

    @Override
    public RoleDto update(UUID id, RoleDto roleDto) {
        var existingRole = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        if (repository.findByName(roleDto.getName()) != null &&
            !existingRole.getName().equals(roleDto.getName())) {
            throw new IllegalArgumentException("Role with this name already exists");
        }

        existingRole.setName(roleDto.getName());

        var updatedRole = repository.save(existingRole);
        return RoleMapper.toDto(updatedRole);
    }

    @Override
    public void delete(UUID id) {
        var role = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        if (role.getAccounts() != null && !role.getAccounts().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete role with associated accounts");
        }

//        repository.delete(role);
        role.setStatus(0);
        repository.save(role);
    }
}
