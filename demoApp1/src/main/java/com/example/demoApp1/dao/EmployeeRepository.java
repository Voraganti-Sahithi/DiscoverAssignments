package com.example.demoApp1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demoApp1.vo.EmployeeVO;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeVO, Long>{

	List<EmployeeVO> findByName(String name);
}
