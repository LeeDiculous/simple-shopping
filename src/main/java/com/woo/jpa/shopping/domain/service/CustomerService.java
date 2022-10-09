package com.woo.jpa.shopping.domain.service;

import com.woo.jpa.shopping.domain.entity.Customer;
import com.woo.jpa.shopping.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    //회원 등록
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    //회원 상세 조회
    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    //회원 목록 검색
    public List<Customer> getCustomerList() {
        return (List<Customer>)customerRepository.findAll();
    }

    //회원 로그인
    public Customer login(String loginId, String password) {
        return customerRepository.findByLogin(loginId, password);
    }
}
