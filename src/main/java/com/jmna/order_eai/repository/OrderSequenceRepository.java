package com.jmna.order_eai.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderSequenceRepository {
    private final JdbcTemplate jdbcTemplate;
    
    // 시퀀스 값을 가져옴
    public Long getNextSequence() {
        Long sequence = jdbcTemplate.queryForObject(
                "SELECT ORDER_SEQ.NEXTVAL FROM DUAL",
                Long.class);
        if (sequence == null) {
            log.error("DB 시퀀스를 가져오는 데 오류가 발생했습니다.");
            throw new RuntimeException("DB 시퀀스를 가져오는 데 오류가 발생했습니다.");
        }
        return sequence;
    }
}
