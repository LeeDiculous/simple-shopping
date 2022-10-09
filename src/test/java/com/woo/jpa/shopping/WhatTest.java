package com.woo.jpa.shopping;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class WhatTest {

    @Test
    public void test() {

        //현재 날짜 구하기
        LocalDateTime now = LocalDateTime.now(); // now.get 메소드로 다양한 시간 관련 정보 추출 가능.

        //포맷 정의
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //포맷 적용
        String format = now.format(dateTimeFormatter);

        //결과 출력
        System.out.println(format);
    }
}
