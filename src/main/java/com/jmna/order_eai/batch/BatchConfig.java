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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory emf;

    private final int CHUNK_SIZE = 10;

    @Bean
    public JpaCursorItemReader<Order> orderReader() {

        JpaCursorItemReader<Order> reader = new JpaCursorItemReader<>();

        reader.setName("orderReader");
        reader.setEntityManagerFactory(emf);
        reader.setQueryString("SELECT o FROM Order o WHERE o.status = 'N'");

        return reader;
    }

    @Bean
    public Step orderStep(ItemReader<Order> reader, ItemProcessor<Order, Shipment> processor, ItemWriter<Shipment> writer) {

        return new StepBuilder("orderStep", jobRepository)
                .<Order, Shipment>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job orderJob(Step orderStep) {

        return new JobBuilder("orderJob", jobRepository)
                .start(orderStep)
                .build();
    }
}
