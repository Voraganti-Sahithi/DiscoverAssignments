//package com.example.demoApp1.controller;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequestMapping("/batch")
//public class BatchController {
//
//	@Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private Job job;
//    
//    @GetMapping("/start")
//    public ResponseEntity<String> startBatchJob() {
//        try {
//            jobLauncher.run(job, new JobParameters());
//            return ResponseEntity.ok("Batch job has been triggered successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Batch job failed: " + e.getMessage());
//        }
//    }
//}
