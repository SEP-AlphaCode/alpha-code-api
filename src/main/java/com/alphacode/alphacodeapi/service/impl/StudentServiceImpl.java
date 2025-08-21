package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.StudentDto;
import com.alphacode.alphacodeapi.entity.Role;
import com.alphacode.alphacodeapi.entity.Student;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.RoleMapper;
import com.alphacode.alphacodeapi.mapper.StudentMapper;
import com.alphacode.alphacodeapi.repository.StudentRepository;
import com.alphacode.alphacodeapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final S3ServiceImpl s3Service;

    @Override
    public PagedResult<StudentDto> getAll(int page, int size, Integer status, String fullName, UUID classId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Student> pageResult;

        if (status != null && classId != null && fullName != null) {
           pageResult = studentRepository.findAllByStatusAndClassIdAndFullName(status, classId, fullName, pageable);
        } else if (status != null && classId != null) {
            pageResult = studentRepository.findAllByStatusAndClassId(status, classId, pageable);
        } else if (status != null && fullName != null) {
            pageResult = studentRepository.findAllByStatusAndFullName(status, fullName, pageable);
        } else if (status != null) {
            pageResult = studentRepository.findAllByStatus(status, pageable);
        } else {
            pageResult = studentRepository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(StudentMapper::toDto));
    }

    @Override
    public StudentDto getById(UUID id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return StudentMapper.toDto(student);
    }

    @Override
    public StudentDto create(StudentDto dto,  MultipartFile image) {
        try {
            var student = StudentMapper.toEntity(dto);

            student.setCreateDate(LocalDateTime.now());
            student.setFacialRecordData("");

            if (image != null && !image.isEmpty()) {
                String fileKey = "avatars/" + System.currentTimeMillis() + "_" + image.getOriginalFilename();
                String avatarUrl = s3Service.uploadBytes(image.getBytes(), fileKey, image.getContentType());
                student.setImage(avatarUrl);
            }

            var saved = studentRepository.save(student);
            return StudentMapper.toDto(saved);
        }
        catch (Exception e) {
            throw new RuntimeException("Error creating Student", e);
        }

    }

    @Override
    public StudentDto update(UUID id, StudentDto dto) {
        var existed = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        existed.setFullName(dto.getFullName());
        existed.setClassId(dto.getClassId());
        existed.setFacialRecordData(dto.getFacialRecordData());
        existed.setShortName(dto.getShortName());
        existed.setNickname(dto.getNickname());
        existed.setDateOfBirth(dto.getDateOfBirth());
        existed.setGender(dto.getGender());
        existed.setStatus(dto.getStatus());
        existed.setLastUpdate(LocalDateTime.now());

        var updated = studentRepository.save(existed);
        return StudentMapper.toDto(updated);
    }

    @Override
    public StudentDto patchUpdate(UUID id, StudentDto dto) {
        var existed = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (dto.getFullName() != null) {
            existed.setFullName(dto.getFullName());
        }
        if (dto.getClassId() != null) {
            existed.setClassId(dto.getClassId());
        }
        if (dto.getFacialRecordData() != null) {
            existed.setFacialRecordData(dto.getFacialRecordData());
        }
        if (dto.getShortName() != null) {
            existed.setShortName(dto.getShortName());
        }
        if (dto.getNickname() != null) {
            existed.setNickname(dto.getNickname());
        }
        if (dto.getDateOfBirth() != null) {
            existed.setDateOfBirth(dto.getDateOfBirth());
        }
        if (dto.getGender() != null) {
            existed.setGender(dto.getGender());
        }
        if (dto.getStatus() != null) {
            existed.setStatus(dto.getStatus());
        }
        existed.setLastUpdate(LocalDateTime.now());

        var updated = studentRepository.save(existed);
        return StudentMapper.toDto(updated);
    }

    @Override
    public String delete(UUID id) {
        try{
            var existed = studentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
            existed.setStatus(0);
            existed.setLastUpdate(LocalDateTime.now());
            studentRepository.save(existed);
            return "Deleted Student with ID: " + id;
        }
        catch (Exception e) {
            throw new RuntimeException("Error deleting Student", e);
        }
    }
}
