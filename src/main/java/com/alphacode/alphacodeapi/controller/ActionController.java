package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ActionDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/actions")
@RequiredArgsConstructor
public class ActionController {

    private final ActionService actionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResult<ActionDto> getAllActions(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) {
        return actionService.getAllActions(page, size, status);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ActionDto getActionById(@PathVariable UUID id) {
        return actionService.getActionById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActionDto createAction(@RequestBody ActionDto actionDto) {
        return actionService.createAction(actionDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ActionDto updateAction(@PathVariable UUID id, @RequestBody ActionDto actionDto) {
        return actionService.updateAction(id, actionDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAction(@PathVariable UUID id) {
        actionService.deleteAction(id);
    }
}
