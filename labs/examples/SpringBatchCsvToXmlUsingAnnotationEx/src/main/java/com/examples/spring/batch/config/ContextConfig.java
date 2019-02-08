package com.examples.spring.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class ContextConfig {

	// @EnableBatchProcessing Creates instances (default) of JobRepository,
	// JobLauncher, JobRegistry, PlatformTransactionManager, JobBuilderFactory,
	// StepBuilderFactory beans and registers into IoC container with bean id as
	// jobRepository, jobLauncher, jobRegistry, transactionManager, jobs and steps
	// which can be autowired directly.

	// If in case needs to override the default behaviour of those beans can be
	// achieved by simply implementing the getter methods of those beans as shown
	// below. These methods are part of BatchConfigurer interface which we are
	// implementing (with @EnableBatchProcessing or implements BatchConfigurer)

	private JobRepository getJobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setTransactionManager(getTransactionManager());
		factory.afterPropertiesSet();
		return (JobRepository) factory.getObject();
	}

	private PlatformTransactionManager getTransactionManager() {
		return new ResourcelessTransactionManager();
	}

	public JobLauncher getJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(getJobRepository());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
}
