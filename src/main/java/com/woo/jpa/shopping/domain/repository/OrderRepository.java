package com.woo.jpa.shopping.domain.repository;


import com.woo.jpa.shopping.domain.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    // <----- 해당 customer의 주문 목록 ----->
    @Query(value = "SELECT order FROM Order order WHERE customer.name like %:name%")
    List<Order> findByJPQL(@Param("name") String name);
}
