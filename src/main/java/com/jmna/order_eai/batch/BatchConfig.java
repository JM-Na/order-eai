package com.jmna.order_eai.batch;

import com.jmna.order_eai.entity.Order;
import com.jmna.order_eai.entity.Shipment;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final int PAGE_SIZE = 10;
    private final int CHUNK_SIZE = 10;

    // DB에서 status = 'N'을 조회하는 reader
    @Bean
    public JpaCursorItemReader<Order> orderReader(EntityManagerFactory emf) {
        JpaCursorItemReader<Order> reader = new JpaCursorItemReader<>();

        reader.setName("orderReader");
        reader.setEntityManagerFactory(emf);

        reader.setQueryString("SELECT o FROM Order o WHERE o.status = 'N'");

        return reader;
    }

    @Bean
    public Step orderToShipmentStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<Order> reader,
                                    ItemProcessor<Order, Shipment> processor,
                                    ItemWriter<Shipment> writer) {
        return new StepBuilder("orderToShipmentStep", jobRepository)
                .<Order, Shipment>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job orderToShipmentJob(JobRepository jobRepository,
                                  Step orderToShipmentStep){
        return new JobBuilder("orderToShipmentJob", jobRepository)
                .start(orderToShipmentStep)
                .build();
    }
}
