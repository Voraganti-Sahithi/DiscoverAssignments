//package com.example.demoApp1.config;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.database.JpaItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.FileSystemResource;
//
//import org.springframework.transaction.PlatformTransactionManager;
//
//
//import com.example.demoApp1.vo.EmployeeVO;
//import jakarta.persistence.EntityManagerFactory;
//
//
//@Configuration
//public class CsvToH2DbEmployee {
//	
//	
//	private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final EntityManagerFactory entityManagerFactory;
//
//    public CsvToH2DbEmployee(JobRepository jobRepository, PlatformTransactionManager transactionManager, EntityManagerFactory entityManagerFactory) {
//        this.jobRepository = jobRepository;
//        this.transactionManager = transactionManager;
//        this.entityManagerFactory = entityManagerFactory;
//    }
//
//    // 1. Reader - Reads data from the CSV file
//    @Bean(name = "csvReader")
////    @Primary
//    public FlatFileItemReader<EmployeeVO> csvReader() {
//        FlatFileItemReader<EmployeeVO> reader = new FlatFileItemReader<>();
//        reader.setResource(new FileSystemResource("employees.csv")); // Update the path
//        reader.setLinesToSkip(1); // Skip header
//        reader.setLineMapper(new DefaultLineMapper<>() {{
//            setLineTokenizer(new DelimitedLineTokenizer() {{
//                setDelimiter(",");
//                setNames("name", "age");
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
//                setTargetType(EmployeeVO.class);
//            }});
//        }});
//        return reader;
//    }
//
//    // 2. Processor - Transform the input if necessary
//    @Bean
////    @Primary
//    public ItemProcessor<EmployeeVO, EmployeeVO> csvProcessor() {
//    	return item -> {
//            // Convert the name field to uppercase
//            item.setName(item.getName().toUpperCase());
//            return item; // Return the transformed item
//        };    
//    }
//
//    // 3. Writer - Write to the H2 Database
//    @Bean(name = "csvJpaWriter")
////    @Primary
//    public JpaItemWriter<EmployeeVO> csvJpaWriter() {
//        JpaItemWriter<EmployeeVO> writer = new JpaItemWriter<>();
//        writer.setEntityManagerFactory(entityManagerFactory); // Set EntityManagerFactory for database operations
//        return writer;
//    }
//
//    // 4. Step - Defines a chunk-oriented step
//    @Bean(name = "csvToH2Step")
////    @Primary
//    public Step csvToH2Step() {
//        return new StepBuilder("csvToH2Step", jobRepository)
//                .<EmployeeVO, EmployeeVO>chunk(10, transactionManager) // Process 10 items at a time
//                .reader(csvReader())
//                .processor(csvProcessor())
//                .writer(csvJpaWriter())
//                .build();
//    }
//
//    // 5. Job - Assemble the step into a Job
//    @Bean(name = "csvToH2Job")
////    @Primary
//    public Job csvToH2Job() {
//        return new JobBuilder("csvToH2Job", jobRepository)
//                .start(csvToH2Step())
//                .build();
//    }
//}
//
