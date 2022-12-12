package com.skhealthcare.serviceimpl;

import com.skhealthcare.entity.Staff;
import com.skhealthcare.repository.StaffRepository;
import com.skhealthcare.service.StaffService;
import org.hibernate.loader.custom.ScalarReturn;
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
        return staffRepository.findByUserNameAndPasswordAndDesignation(userName, password, text);
    }

    @Override
    public Boolean checkUsername(String userName) {
        return staffRepository.findByUserName(userName) != null;
    }

    @Override
    public void addStaff(Staff staff) {
        staffRepository.save(staff);
    }

    @Override
    public Staff getStaffById(Integer id) {
        return staffRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteStaff(Staff staff) {
        staffRepository.delete(staff);
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

}
