package com.example.WarehouseDatabaseJava.MyISAM.model.order;

import com.example.WarehouseDatabaseJava.dto.custom.CustomProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomProductMyIsamRepository extends JpaRepository <CustomProductMyISAM, Integer> {

    @Query("SELECT new com.example.WarehouseDatabaseJava.dto.custom.CustomProductDTO(cp.productId, p.name, cp.quantity) FROM CustomProductMyISAM cp JOIN ProductMyISAM p ON cp.productId = p.id WHERE cp.customId = :custom_id")
    List<CustomProductDTO> getCustomProductDTOListByCustomId(@Param("custom_id") int customId);
}
