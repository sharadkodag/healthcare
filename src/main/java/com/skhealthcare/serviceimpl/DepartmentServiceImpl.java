package com.skhealthcare.serviceimpl;

import com.skhealthcare.entity.Department;
import com.skhealthcare.repository.DepartmentRepository;
import com.skhealthcare.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

}
