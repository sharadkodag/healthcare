package com.skhealthcare.repository;

import com.skhealthcare.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface StaffRepository extends JpaRepository<Staff, Integer> {

    public Staff findByUserNameAndPasswordAndDesignation(String userName, String password, String text);
    public Staff findByUserName(String userName);

}
