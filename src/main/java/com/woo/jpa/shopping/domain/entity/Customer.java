package com.woo.jpa.shopping.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "S_CUSTOMER")
@NoArgsConstructor
public class Customer {

    //회원 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //회원 이름
    private String name;

    //회원 전화번호
    private String phone;

    //회원 특징
    private String comment;

    //회원 주소
    @Embedded
    private Address address;

    //회원 로그인
    @Embedded
    private Login login;

    //주문 목록
    @OneToMany(mappedBy = "customer")
    private List<Order> orderList = new ArrayList<>();

    //회원 생성자
    public Customer(String name, String phone, String comment,
                    String city, String roadName, String postCode,
                    String loginId, String password) {
        this.name = name;
        this.phone = phone;
        this.comment = comment;

        this.address = new Address();
        address.setCity(city);
        address.setRoadName(roadName);
        address.setPostCode(postCode);

        this.login = new Login();
        login.setLoginId(loginId);
        login.setPassword(password);
    }
}
