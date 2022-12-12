package com.skhealthcare.service;

import com.skhealthcare.entity.Staff;

import java.util.List;


public interface StaffService {

    public List<Staff> getAllDoctors(String designation);

    public Staff checkStaff(String text, String username, String password);

    public Boolean checkUsername(String userName);

    public void addStaff(Staff staff);

    public Staff getStaffById(Integer id);

    public void deleteStaff(Staff staff);

    public List<Staff> getAllStaff();
}
