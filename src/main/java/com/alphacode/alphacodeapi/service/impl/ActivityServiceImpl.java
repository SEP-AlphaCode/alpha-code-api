package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Activity;
import com.alphacode.alphacodeapi.entity.QRCode;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.ActivityMapper;
import com.alphacode.alphacodeapi.repository.ActivityRepository;
import com.alphacode.alphacodeapi.repository.QRCodeRepository;
import com.alphacode.alphacodeapi.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private static final String ACTIVITY_NOT_FOUND = "Activity not found";
    private final ActivityRepository repository;
    private final QRCodeRepository qrCodeRepository;
    private final ActivityMapper mapper;
    private final StringRedisTemplate redisTemplate;

    private void increaseUsage(UUID activityId) {
        String activityKey = activityId.toString();

        // Key theo ngày
        String keyDay = "activities:day:" + LocalDate.now();
        redisTemplate.opsForZSet().incrementScore(keyDay, activityKey, 1);

        // Key theo tuần
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = LocalDate.now().get(weekFields.weekOfWeekBasedYear());
        String keyWeek = "activities:week:" + LocalDate.now().getYear() + "-" + weekNumber;
        redisTemplate.opsForZSet().incrementScore(keyWeek, activityKey, 1);

        // Key theo tháng
        String keyMonth = "activities:month:" + YearMonth.now();
        redisTemplate.opsForZSet().incrementScore(keyMonth, activityKey, 1);
    }

    @Override
    @Cacheable(value = "activities_list", key = "{#page, #size, #status}")
    public PagedResult<ActivityDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Activity> pageResult = (status != null) ?
                repository.findAllByStatus(status, pageable) :
                repository.findAll(pageable);

        return new PagedResult<>(pageResult.map(mapper::toDto));
    }

    @Override
    @Cacheable(value = "activities", key = "#id")
    public ActivityDto getById(UUID id) {
        Activity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));

        increaseUsage(entity.getId());
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"activities", "activities_list"}, allEntries = true)
    public ActivityDto create(ActivityDto dto) {
        Activity entity = mapper.toEntity(dto);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdate(LocalDateTime.now());
        if (entity.getStatus() == null) entity.setStatus(1);
        Activity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    @CachePut(value = "activities", key = "#id")
    @CacheEvict(value = {"activities_list"}, allEntries = true)
    public ActivityDto update(UUID id, ActivityDto dto) {
        Activity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));

        existing = mapper.updateEntity(dto, existing);
        existing.setLastUpdate(LocalDateTime.now());

        Activity updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    @CachePut(value = "activities", key = "#id")
    @CacheEvict(value = {"activities_list"}, allEntries = true)
    public ActivityDto patchUpdate(UUID id, ActivityDto dto) {
        Activity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));

        existing = mapper.updateEntity(dto, existing);
        existing.setLastUpdate(LocalDateTime.now());

        Activity updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"activities", "activities_list"}, key = "#id", allEntries = true)
    public String delete(UUID id) {
        Activity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));

        existing.setStatus(0);
        existing.setLastUpdate(LocalDateTime.now());
        repository.save(existing);
        return "Deleted successfully";
    }

    @Override
    @Cacheable(value = "activities_qr", key = "#qrCode")
    public ActivityDto getByQRCode(String qrCode) {
        Optional<QRCode> code = qrCodeRepository.findQRCodeByQrCode(qrCode);
        if (code.isPresent()) {
            UUID activityId = code.get().getActivityId();
            Activity activity = repository.findById(activityId)
                    .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));
            return mapper.toDto(activity);
        }
        return null;
    }

    @Override
    @Transactional
    @CachePut(value = "activities", key = "#id")
    @CacheEvict(value = {"activities_list"}, allEntries = true)
    public ActivityDto changeActivityStatus(UUID id, Integer status) {
        Activity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));
        existing.setStatus(status);
        existing.setLastUpdate(LocalDateTime.now());
        Activity updated = repository.save(existing);
        return mapper.toDto(updated);
    }
}
