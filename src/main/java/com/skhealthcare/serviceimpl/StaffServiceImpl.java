package com.skhealthcare.serviceimpl;

import com.skhealthcare.entity.Staff;
import com.skhealthcare.repository.StaffRepository;
import com.skhealthcare.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Override
    public List<Staff> getAllDoctors(String designation) {
        return staffRepository.findAllByDesignation(designation);
    }

    @Override
    public Staff checkStaff(String text, String userName, String password) {
        Staff staff = staffRepository.findByUserNameAndPasswordAndDesignation(userName, password, text);
//        Staff staff = staffRepository.findByUserName(userName);
//        if(staff!=null && staff.getPassword().equals(password) && staff.getDesignation().equals(text)){
//            return staff;
//        }
        return staff;

    }

}
