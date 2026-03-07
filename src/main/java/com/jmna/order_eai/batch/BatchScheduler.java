package com.jmna.order_eai.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job job;

    // 5분 간격으로 실행
    @Scheduled(cron = "0 0/5 * * * *")
    public void runJob() throws Exception {
        log.info("5분 단위 스케줄러 실행: 운송 DB로 주문 정보 전달");
        
        // JobParameter가 겹치지 않도록 함.
        JobParameters parameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(job, parameters);
    }
}
