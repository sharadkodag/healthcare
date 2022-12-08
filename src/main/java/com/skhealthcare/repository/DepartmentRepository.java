package com.skhealthcare.repository;

import com.skhealthcare.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    public Department findByDeptName(String deptName);

}
