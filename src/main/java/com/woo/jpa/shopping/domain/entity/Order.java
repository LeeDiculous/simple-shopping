package com.woo.jpa.shopping.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "S_ORDER")
@NoArgsConstructor
public class Order {

    //주문 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    //회원 참조
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    //회원 설정 시에 회원 쪽에도 양방향 참조 설정
    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getOrderList().add(this);
    }

    //주문 상태
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //주문 날짜
    private String orderDate;

    //날짜 적용
    public String newDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return now.format(dateTimeFormatter);
    }

    /**
     * 검색 관련 정보
     */
    //검색할 회원 이름
    @Transient
    private String searchCustomerName;

    //검색할 주문 상태
    @Transient
    private OrderStatus searchOrderStatus;

    //주문내역 목록
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> itemList = new ArrayList<>();

    //주문상품 설정 시에 주문상품 쪽에도 양방향 설정
    public void addItem(Item item) {
        itemList.add(item);
        item.setOrder(this);
    }

    //주문 취소 처리
    public void orderCancel() {
        this.setStatus(OrderStatus.CANCEL);
        for (Item item : itemList) {
            item.restoreStock();
        }
    }

    //주문 생성자 : 회원과 주문 내역 객체를 이용하여 주문을 구성한다.
    public Order(Customer customer, Item item) {
        setCustomer(customer);
        addItem(item);
        this.status = OrderStatus.ORDER; //주문 생성 시 상태를 ORDER
        this.orderDate = newDate();
    }
}
