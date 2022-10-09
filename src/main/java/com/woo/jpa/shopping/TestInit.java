package com.woo.jpa.shopping;

import com.woo.jpa.shopping.domain.entity.Customer;
import com.woo.jpa.shopping.domain.entity.Product;
import com.woo.jpa.shopping.domain.service.CustomerService;
import com.woo.jpa.shopping.domain.service.OrderService;
import com.woo.jpa.shopping.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestInit {

    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final OrderService orderService;


    /**
     * TEST DATA
     */
    @PostConstruct
    public void init() {

        Customer customer = new Customer("홍길동", "010-1234-1234", "친구를 통해서", "서울", "종로구 사직로 161", "12345", "hello", "qwer1234");
        customerService.insertCustomer(customer);

        //두 개의 상품 정보 생성 및 등록
        Product product1 = new Product();
        product1.setName("MacBook Air M2");
        product1.setPrice(2000000);
        product1.setQuantity(20);
        productService.insertProduct(product1);

        Product product2 = new Product();
        product2.setName("Iphone14 pro max");
        product2.setPrice(1600000);
        product2.setQuantity(15);
        productService.insertProduct(product2);

        Product product3 = new Product();
        product3.setName("Mac");
        product3.setPrice(1850000);
        product3.setQuantity(30);
        productService.insertProduct(product3);

        //주문 정보 생성
        orderService.insertOrder(customer.getId(), product2.getId(), 2);
        orderService.insertOrder(customer.getId(), product1.getId(), 1);
        orderService.insertOrder(customer.getId(), product3.getId(), 1);


    }
}
