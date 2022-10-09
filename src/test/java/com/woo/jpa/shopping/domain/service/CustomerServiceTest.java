package com.woo.jpa.shopping.domain.service;

import com.woo.jpa.shopping.domain.entity.Address;
import com.woo.jpa.shopping.domain.entity.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void insert() {
        //given
        Customer customer = new Customer();
        customer.setName("이재우");
        customer.setComment("VIP");
        customer.setPhone("010-xxxx-xxxx");
        Address address = new Address();
        address.setCity("노원구");
        address.setRoadName("한글비석로");
        address.setPostCode("1241551");
        customer.setAddress(address);

        customerService.insertCustomer(customer);

        //when
        Customer findCustomer = customerService.getCustomer(customer.getId());
        List<Customer> customerList = customerService.getCustomerList();

        //then
        Assertions.assertThat(findCustomer.getName()).isEqualTo("이재우");
        Assertions.assertThat(customerList.size()).isEqualTo(1);
    }

}