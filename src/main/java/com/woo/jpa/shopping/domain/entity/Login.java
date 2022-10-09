package com.woo.jpa.shopping.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
public class Login {

    @Column(name = "LOGIN_ID", unique = true, nullable = false)
    private String loginId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
}
