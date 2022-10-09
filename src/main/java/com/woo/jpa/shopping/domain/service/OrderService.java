package com.woo.jpa.shopping.domain.service;

import com.woo.jpa.shopping.domain.entity.Customer;
import com.woo.jpa.shopping.domain.entity.Item;
import com.woo.jpa.shopping.domain.entity.Order;
import com.woo.jpa.shopping.domain.entity.Product;
import com.woo.jpa.shopping.domain.repository.CustomerRepository;
import com.woo.jpa.shopping.domain.repository.OrderRepository;
import com.woo.jpa.shopping.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final ProductRepository productRepository;

    //주문 등록
    public Order insertOrder(Long customerId, Long productId, int count) {
        Customer customer = customerRepository.findById(customerId).get();
        Product product = productRepository.findById(productId).get();

        Item item = new Item(product, count);
        Order order = new Order(customer, item);

        orderRepository.save(order);

        return order;

    }

    //주문 상세 검색
    public Item getItem(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        return order.getItemList().stream().findFirst().get();
    }

    //주문 취소
    public void orderCancel(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.orderCancel();
    }

    //주문 검색
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    //주문 목록 검색
    public List<Order> getOrderList(Order order) {
        return orderRepository.findByJPQL(order.getSearchCustomerName());
    }
}
