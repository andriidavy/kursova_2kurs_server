package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMyIsamRepository extends JpaRepository<CustomerMyISAM, Integer> {

}
