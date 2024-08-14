package com.gs.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //keep alive thread
        executor.setCorePoolSize(5000);
        //max thread in pool
        executor.setMaxPoolSize(5000);
        //number of queue used to hold task

        // Set thread keep-alive time
        executor.setKeepAliveSeconds(40); // Adjust based on expected task durations
        executor.setThreadNamePrefix("async-thread - ");
        executor.initialize();
        return executor;
    }
}
