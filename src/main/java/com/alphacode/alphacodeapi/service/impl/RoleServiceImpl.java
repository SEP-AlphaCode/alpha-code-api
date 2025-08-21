package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RoleDto;
import com.alphacode.alphacodeapi.entity.Role;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.RoleMapper;
import com.alphacode.alphacodeapi.repository.RoleRepository;
import com.alphacode.alphacodeapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public PagedResult<RoleDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Role> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(RoleMapper::toDto));
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
    public RoleDto patchUpdate(UUID id, RoleDto roleDto) {
        var existingRole = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        if (repository.findByName(roleDto.getName()) != null &&
                !existingRole.getName().equals(roleDto.getName())) {
            throw new IllegalArgumentException("Role with this name already exists");
        }

        if (roleDto.getName() != null)
            existingRole.setName(roleDto.getName());

        var updatedRole = repository.save(existingRole);
        return RoleMapper.toDto(updatedRole);
    }

    @Override
    public String delete(UUID id) {
        try {
            var role = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

            if (role.getAccounts() != null && !role.getAccounts().isEmpty()) {
                throw new IllegalArgumentException("Cannot delete role with associated accounts");
            }

//        repository.delete(role);
            role.setStatus(0);
            repository.save(role);
            return "Role deleted successfully with ID: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting role", e);
        }

    }
}
