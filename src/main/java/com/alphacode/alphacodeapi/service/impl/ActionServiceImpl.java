package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ActionDto;
import com.alphacode.alphacodeapi.entity.Action;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.ActionMapper;
import com.alphacode.alphacodeapi.repository.ActionRepository;
import com.alphacode.alphacodeapi.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ActionDto> getAllActions() {
        return actionRepository.findAll().stream()
                .map(ActionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ActionDto getActionById(UUID id) {
        Action action = actionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + id));
        return ActionMapper.toDto(action);
    }

    @Override
    @Transactional
    public ActionDto createAction(ActionDto actionDto) {
        Action action = ActionMapper.toEntity(actionDto);
        action.setCreateDate(LocalDateTime.now());
        action.setLastUpdate(LocalDateTime.now());
        Action savedAction = actionRepository.save(action);
        return ActionMapper.toDto(savedAction);
    }

    @Override
    @Transactional
    public ActionDto updateAction(UUID id, ActionDto actionDto) {
        Action existingAction = actionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + id));
        
        ActionMapper.updateEntity(actionDto, existingAction);
        existingAction.setLastUpdate(LocalDateTime.now());
        
        Action updatedAction = actionRepository.save(existingAction);
        return ActionMapper.toDto(updatedAction);
    }

    @Override
    @Transactional
    public void deleteAction(UUID id) {
        if (!actionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Action not found with id: " + id);
        }
        actionRepository.deleteById(id);
    }
}
