package com.jmna.order_eai.entity;

/**
 * OrderId(ex: A001)를 생성하는 클래스
 */
public class OrderIdGenerator {

    public static String generate(long seq) {
        int letterIndex = (int) (seq / 1000);
        int number = (int) (seq % 1000);

        char letter = (char) ('A' + letterIndex);

        return letter + String.format("%03d", number);
    }
}
