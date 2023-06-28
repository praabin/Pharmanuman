package com.pharmanuman.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharmanuman.entities.PlaceOrder;

public interface PlaceOrderRepository extends JpaRepository<PlaceOrder, Integer> {

	@Query("from PlaceOrder as p where p.user.id =:uid")
	public List<PlaceOrder> findPlaceOrderById(@Param("uid") int uid);

	@Query("FROM PlaceOrder AS p WHERE p.poid = :poid")
	public PlaceOrder findPlaceOrderById(@Param("poid") String poid);

	@Query("FROM PlaceOrder AS p WHERE p.stockist = :stockist")
	public List<PlaceOrder> findPlaceOrderByOrderStockist(@Param("stockist") String stockist);

	@Query("FROM PlaceOrder AS p WHERE p.pharmacyName = :pharmacyName")
	public List<PlaceOrder> findPlaceOrderByPharmacyName(@Param("pharmacyName") String pharmacyName);

}
