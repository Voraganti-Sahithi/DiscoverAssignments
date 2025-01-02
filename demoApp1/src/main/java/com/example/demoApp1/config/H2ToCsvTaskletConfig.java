//package com.example.demoApp1.config;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import com.example.demoApp1.dao.EmployeeRepository;
//
//import jakarta.persistence.EntityManagerFactory;
//
//@Configuration
//public class H2ToCsvTaskletConfig {
//	
//	private final JobRepository jobRepository;
//	  private final PlatformTransactionManager transactionManager;
//	  private final EmployeeRepository empRepo;
//	  private final EntityManagerFactory entityManagerFactory;
//
//		public H2ToCsvTaskletConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
//				EmployeeRepository empRepo, EntityManagerFactory entityManagerFactory) {
//			super();
//			this.jobRepository = jobRepository;
//			this.transactionManager = transactionManager;
//			this.empRepo = empRepo;
//			this.entityManagerFactory = entityManagerFactory;
//		}
//	 
//		
//	    @Autowired
//	    private H2ToCsvTasklet h2ToCsvTasklet;
//
//	 
//	    @Bean
//	    public Step step2() {
//	        return new StepBuilder("step2",jobRepository)
//	                .tasklet(h2ToCsvTasklet)
//	                .transactionManager(transactionManager) 
//	                .build();
//	    }
//	 
//	    @Bean
//	    public Job job(JobRepository jobRepository,Step step2) {
//	        return new JobBuilder("H2ToCsvTasklet",jobRepository)
//	                .start(step2())
//	                .build();
//	    }
//
//}
