package com.skhealthcare.service;

import com.skhealthcare.entity.Staff;

import java.util.List;


public interface StaffService {

    public List<Staff> getAllDoctors(String designation);

    public Staff checkStaff(String text, String username, String password);
}
