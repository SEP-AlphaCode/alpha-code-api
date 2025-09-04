package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ActionDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/actions")
@RequiredArgsConstructor
@Tag(name = "Actions")
public class ActionController {

    private final ActionService actionService;

    @GetMapping

    @Operation(summary = "Get all actions")
    public PagedResult<ActionDto> getAllActions(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search) {
        return actionService.getAllActions(page, size, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get action by id")
    public ActionDto getActionById(@PathVariable UUID id) {
        return actionService.getActionById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new action")
    public ActionDto createAction(@Valid @RequestBody ActionDto actionDto) {
        return actionService.createAction(actionDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Update action")
    public ActionDto updateAction(@PathVariable UUID id, @Valid @RequestBody ActionDto actionDto) {
        return actionService.updateAction(id, actionDto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Patch update action")
    public ActionDto patchUpdate(@PathVariable UUID id, @Valid @RequestBody ActionDto dto) {
        ActionDto existing = actionService.getActionById(id);
        ActionDto updated = actionService.updateAction(id, dto); // service đã có update
        return updated;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Delete action")
    public void deleteAction(@PathVariable UUID id) {
        actionService.deleteAction(id);
    }
}
