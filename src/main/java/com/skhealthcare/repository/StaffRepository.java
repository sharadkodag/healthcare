package com.skhealthcare.repository;

import com.skhealthcare.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface StaffRepository extends JpaRepository<Staff, Integer> {

    public Staff findByUserNameAndPasswordAndDesignation(String userName, String password, String text);

    public List<Staff> findAllByDesignation(String designation);


}
