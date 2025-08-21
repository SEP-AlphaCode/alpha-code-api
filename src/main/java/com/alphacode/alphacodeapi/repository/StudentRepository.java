package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Role;
import com.alphacode.alphacodeapi.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Page<Student> findAllByStatus(Integer status, Pageable pageable);
    Page<Student> findAllByStatusAndClassId(Integer status, UUID classId, Pageable pageable);
    Page<Student> findAllByStatusAndFullName(Integer status, String fullName, Pageable pageable);
    Page<Student> findAllByStatusAndClassIdAndFullName(Integer status, UUID classId, String fullName, Pageable pageable);
}
