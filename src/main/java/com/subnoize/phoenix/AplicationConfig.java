package com.subnoize.phoenix;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * mDNS, Async scheduler and password via BCrypt
 * 
 * @author youca
 *
 */
@Configuration
public class AplicationConfig {

	@Bean
	public Executor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

}
