//package com.example.demoApp1.config;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
// 
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.demoApp1.vo.EmployeeVO;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//
//@Configuration
//public class CsvToH2Tasklet {
//	private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public CsvToH2Tasklet(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        this.jobRepository = jobRepository;
//        this.transactionManager = transactionManager;
//    }
//
//    // Shared data storage for steps
//    private static final List<EmployeeVO> userData = new ArrayList<>();
//
//    @Bean
//    public Tasklet itemReaderTasklet() {
//        return (contribution, chunkContext) -> {
//            Files.lines(Paths.get("employees.csv"))
//                    .skip(1) // Skip header row
//                    .map(line -> {
//                        String[] fields = line.split(",");
//                        EmployeeVO emp = new EmployeeVO();
//                        emp.setName(fields[0].trim());
//                        emp.setAge(Integer.parseInt(fields[1].trim()));
//                        return emp;
//                    })
//                    .forEach(userData::add);
//            System.out.println("Read Data: " + userData);
//            return RepeatStatus.FINISHED;
//        };
//    }
//
//    @Bean
//    public Tasklet itemProcessorTasklet() {
//        return (contribution, chunkContext) -> {
//            userData.forEach(emp -> emp.setName("Tasklet-" + emp.getName().toUpperCase()));
//            System.out.println("Processed Data: " + userData);
//            return RepeatStatus.FINISHED;
//        };
//    }
//
//    @Bean
//    @Transactional
//    public Tasklet itemWriterTasklet() {
//        return (contribution, chunkContext) -> {
//            userData.forEach(entityManager::persist);
//            System.out.println("Data written to H2 DB");
//            return RepeatStatus.FINISHED;
//        };
//    }
//
//    @Bean
//    public Step readerStep() {
//        return new StepBuilder("readerStep", jobRepository)
//                .tasklet(itemReaderTasklet(), transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step processorStep() {
//        return new StepBuilder("processorStep", jobRepository)
//                .tasklet(itemProcessorTasklet(), transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step writerStep() {
//        return new StepBuilder("writerStep", jobRepository)
//                .tasklet(itemWriterTasklet(), transactionManager)
//                .build();
//    }
//    @Bean
//    public Job csvToH2TaskletJob() {
//        return new JobBuilder("csvToH2Tasklet", jobRepository)
//                .start(readerStep())
//                .next(processorStep())
//                .next(writerStep())
//                .build();
//    }
//}
