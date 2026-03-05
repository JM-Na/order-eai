package com.jmna.order_eai.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderSequenceRepository {
    private final JdbcTemplate jdbcTemplate;

    public long getNextSequence() {
        return jdbcTemplate.queryForObject(
                "SELECT ORDER_SEQ.NEXTVAL FROM DUAL",
                Long.class
        );
    }
}
