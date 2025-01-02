//package com.example.demoApp1.config;
//
//
//import java.util.Collections;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.data.RepositoryItemReader;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
//import org.springframework.batch.item.support.PassThroughItemProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.data.domain.Sort;
//import org.springframework.transaction.PlatformTransactionManager;
//import com.example.demoApp1.vo.EmployeeVO;
//import com.example.demoApp1.dao.EmployeeRepository;
//
//@Configuration
//public class EmployeeConfig {
//	
//	private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final EmployeeRepository employeeRepository;
// 
//    public EmployeeConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, EmployeeRepository employeeRepository) {
//        this.jobRepository = jobRepository;
//        this.transactionManager = transactionManager;
//        this.employeeRepository = employeeRepository;
//    }
// 
//    // 1. Reader - RepositoryItemReader to fetch data from the database
//    @Bean
//    @Primary
//    public RepositoryItemReader<EmployeeVO> reader() {
//        RepositoryItemReader<EmployeeVO> reader = new RepositoryItemReader<>();
//        reader.setRepository(employeeRepository);
//        reader.setMethodName("findAll");
//        reader.setPageSize(10);
//        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
//        return reader;
//    }
// 
//    // 2. Processor - PassThroughItemProcessor (no transformation in this case)
//    @Bean
//    @Primary
//    public PassThroughItemProcessor<EmployeeVO> processor() {
//        return new PassThroughItemProcessor<>();
//    }
// 
//    // 3. Writer - FlatFileItemWriter to write data to a CSV file
//    @Bean
//    @Primary
//    public FlatFileItemWriter<EmployeeVO> writer() {
//        return new FlatFileItemWriterBuilder<EmployeeVO>()
//                .name("csvWriter")
//                .resource(new FileSystemResource("result/H2ToCsv.csv"))
//                .delimited()
//                .delimiter(",")
//                .names("id", "name", "age")
//                .headerCallback(writer -> writer.write("ID,Name,Age")) 
//                .build();
//    }
// 
//    // 4. Step - Defines a chunk-oriented step
//    @Bean
//    @Primary
//    public Step step() {
//        return new StepBuilder("step", jobRepository)
//                .<EmployeeVO, EmployeeVO>chunk(10, transactionManager)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//    }
// 
//    // 5. Job - Assembles the step into a Job
//    @Bean
//    @Primary
//    public Job job() {
//        return new JobBuilder("H2ToCsvJob", jobRepository)
//                .start(step())
//                .build();
//    }
//}
//
