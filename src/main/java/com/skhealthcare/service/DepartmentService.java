package com.skhealthcare.service;

import com.skhealthcare.entity.Department;

import java.util.List;

public interface DepartmentService {

    public List<Department> getAllDepartment();
    public Department getDepartmentByName(String deptName);


}
