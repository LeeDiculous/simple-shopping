package com.woo.jpa.shopping.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {

    //도시
    private String city;

    //도로명
    private String roadName;

    //우편번호
    private String postCode;
}
