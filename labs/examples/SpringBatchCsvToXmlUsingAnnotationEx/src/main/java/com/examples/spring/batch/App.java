package com.examples.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.examples.spring.batch.config.ContextConfig;
import com.examples.spring.batch.config.JobConfig;

public class App {
	public static void main(String[] args) {

		// Spring Java config
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(ContextConfig.class);
		context.register(JobConfig.class);
		context.refresh();	

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("csvToXmlBatchJob");
		System.out.println("Starting the batch job");
		try {
			JobExecution execution = jobLauncher.run(job, new JobParameters());
			System.out.println("Job Status : " + execution.getStatus());
			System.out.println("Job completed");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Job failed");
		}
		
		// graceful shutdown of container
		context.close();
	}
}
