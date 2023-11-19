package com.example.WarehouseDatabaseJava.InnoDB.model.order;

import com.example.WarehouseDatabaseJava.dto.custom.CustomProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomProductRepository extends JpaRepository<CustomProduct, Integer> {
    @Query("SELECT new com.example.WarehouseDatabaseJava.dto.custom.CustomProductDTO(cp.productId, p.name, cp.quantity) FROM CustomProduct cp JOIN Product p ON cp.productId = p.id WHERE cp.customId = :custom_id")
    List<CustomProductDTO> getCustomProductDTOListByCustomId(@Param("custom_id") int customId);
}
