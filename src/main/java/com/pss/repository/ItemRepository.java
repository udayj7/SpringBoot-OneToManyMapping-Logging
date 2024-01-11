package com.pss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pss.entity.Items;

public interface ItemRepository extends JpaRepository<Items, Long> {

	  List<Items> findByOrder_Id(Long orderId);

}
