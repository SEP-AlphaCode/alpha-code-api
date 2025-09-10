package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ActionDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Action;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.ActionMapper;
import com.alphacode.alphacodeapi.repository.ActionRepository;
import com.alphacode.alphacodeapi.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "actions_list", key = "{#page, #size, #status}")
    public PagedResult<ActionDto> getAllActions(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Action> pageResult;

        if (search != null) {
            pageResult = actionRepository.findAllByNameOrDescriptionContaining(search, pageable);
        } else {
            pageResult = actionRepository.findAllActiveActions(pageable);
        }

        return new PagedResult<>(pageResult.map(ActionMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "actions", key = "#id")
    public ActionDto getActionById(UUID id) {
        Action action = actionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + id));
        return ActionMapper.toDto(action);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"actions_list"}, allEntries = true)
    public ActionDto createAction(ActionDto actionDto) {
        Action action = ActionMapper.toEntity(actionDto);
        action.setCreatedDate(LocalDateTime.now());
        action.setLastUpdate(LocalDateTime.now());
        Action savedAction = actionRepository.save(action);
        return ActionMapper.toDto(savedAction);
    }

    @Override
    @Transactional
    @CachePut(value = "actions", key = "#id")
    @CacheEvict(value = {"actions_list"}, allEntries = true)
    public ActionDto updateAction(UUID id, ActionDto actionDto) {
        Action existingAction = actionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + id));

        ActionMapper.updateEntity(actionDto, existingAction);
        existingAction.setLastUpdate(LocalDateTime.now());

        Action updatedAction = actionRepository.save(existingAction);
        return ActionMapper.toDto(updatedAction);
    }

    @Override
    public ActionDto patchUpdateAction(UUID id, ActionDto actionDto) {
        Action existingAction = actionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + id));

        if (actionDto.getName() != null) {
            existingAction.setName(actionDto.getName());
        }
        if (actionDto.getDescription() != null) {
            existingAction.setDescription(actionDto.getDescription());
        }
        if (actionDto.getStatus() != null) {
            existingAction.setStatus(actionDto.getStatus());
        }
        if (actionDto.getDuration() != null) {
            existingAction.setDuration(actionDto.getDuration());
        }
        if (actionDto.getCanInterrupt() != null) {
            existingAction.setCanInterrupt(actionDto.getCanInterrupt());
        }
        existingAction.setLastUpdate(LocalDateTime.now());

        Action updatedAction = actionRepository.save(existingAction);
        return ActionMapper.toDto(updatedAction);

    }

    @Override
    @Transactional
    @CachePut(value = "actions", key = "#id")
    @CacheEvict(value = {"actions_list"}, allEntries = true)
    public ActionDto changeActionStatus(UUID id, Integer status) {
        Action existingAction = actionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + id));
        existingAction.setStatus(status);
        existingAction.setLastUpdate(LocalDateTime.now());
        Action updatedAction = actionRepository.save(existingAction);
        return ActionMapper.toDto(updatedAction);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"actions", "actions_list"}, key = "#id", allEntries = true)
    public void deleteAction(UUID id) {
        if (!actionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Action not found with id: " + id);
        }
        actionRepository.softDeleteById(id);
    }
}
