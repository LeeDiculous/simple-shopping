package com.woo.jpa.shopping.domain.repository;

import com.woo.jpa.shopping.domain.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    // <----- 로그인 ID와 비밀번호로 회원 검색 ----->
    @Query(value = "SELECT customer FROM Customer customer WHERE customer.login.loginId= :loginId AND customer.login.password= :password")
    Customer findByLogin(@Param("loginId") String loginId, @Param("password") String password);


}
