package com.woo.jpa.shopping.domain.service;

import com.woo.jpa.shopping.domain.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Test
    @Transactional
    void OrderTest() {

        //회원 정보 생성 및 등록
        Customer customer = new Customer();
        customer.setName("홍길동");
        Address address = new Address();
        address.setCity("서울");
        address.setRoadName("행당로 82");
        address.setPostCode("123-123");
        customer.setAddress(address);
        customer.setPhone("010-xxxx-xxxx");
        customer.setComment("반품 요청이 많은 회원임");

        customerService.insertCustomer(customer);


        Customer customer1 = new Customer();
        customer1.setName("이재우");
        Address address1 = new Address();
        address1.setCity("서울");
        address1.setRoadName("한글비석로 82");
        address1.setPostCode("123-123");
        customer1.setAddress(address1);
        customer1.setPhone("010-xxxx-xxxx");
        customer1.setComment("잘생긴 회원임");

        customerService.insertCustomer(customer1);


        //두 개의 상품 정보 생성 및 등록
        Product product1 = new Product();
        product1.setName("MacBookAir M2");
        product1.setPrice(2000000);
        product1.setQuantity(10);
        productService.insertProduct(product1);

        Product product2 = new Product();
        product2.setName("Iphone14 pro max");
        product2.setPrice(1600000);
        product2.setQuantity(5);
        productService.insertProduct(product2);


        //주문 정보 생성
        orderService.insertOrder(customer.getId(), product2.getId(), 2);
        orderService.insertOrder(customer.getId(), product1.getId(), 1);
        orderService.insertOrder(customer1.getId(), product2.getId(), 3);

        selectData(customerService);

    }


    private static void selectData(CustomerService customerService) {
        Customer customer = customerService.getCustomer(1L);

        List<Order> customerOrderList = customer.getOrderList();
        for (Order order : customerOrderList) {
            System.out.println(order.getId() + " " + order.getStatus() + " " + order.getOrderDate());
            List<Item> itemList = order.getItemList();
            for (Item item : itemList) {
                System.out.println(item.getProduct().getName());
                System.out.println(item.getCount());
            }
        }
    }

}