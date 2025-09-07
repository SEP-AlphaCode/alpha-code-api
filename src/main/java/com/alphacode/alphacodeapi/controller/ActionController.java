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
import org.springframework.web.bind.annotation.*;

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
        return actionService.patchUpdateAction(id, dto);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Change action status")
    public ActionDto changeActionStatus(@PathVariable UUID id, @RequestParam Integer status) {
        return actionService.changeActionStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Delete action")
    public void deleteAction(@PathVariable UUID id) {
        actionService.deleteAction(id);
    }
}
