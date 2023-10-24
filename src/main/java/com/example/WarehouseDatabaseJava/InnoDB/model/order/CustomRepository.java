package com.example.WarehouseDatabaseJava.InnoDB.model.order;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRepository extends JpaRepository<Custom, Integer> {
    @Query("SELECT cm FROM Custom cm JOIN cm.department d JOIN d.managerDepartmentList md JOIN md.manager m WHERE cm.status = :status AND m.id = :managerId")
    List<Custom> findAllByStatusAndManagerId(@Param("status") Custom.Status status, @Param("managerId") int managerId);
}

//@Query("SELECT cm FROM Custom cm WHERE cm.status = :status AND cm.department.managerDepartmentList.manager.id = :managerId")
//similar query but the path to manager.id is complex, which causes an error.
// To eliminate it, you need to break the path into pieces and combine it using JOIN operator
