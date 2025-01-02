//package com.example.demoApp1.config;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JobRunner {
//
//	private final JobLauncher jobLauncher;
//    private final Job job2;
//
//    // Constructor with @Qualifier to specify which job to inject
//    public JobRunner(JobLauncher jobLauncher, @Qualifier("csvToH2Job") Job job2) {
//        this.jobLauncher = jobLauncher;
//        this.job2 = job2;
//    }
//
//    public void runJob() {
//        try {
//            jobLauncher.run(job2, new JobParameters());
//            System.out.println("Job executed successfully");
//        } catch (Exception e) {
//            System.err.println("Job execution failed: " + e.getMessage());
//        }
//    }
//}
//
