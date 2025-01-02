//package com.example.demoApp1.config;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import com.example.demoApp1.vo.EmployeeVO;
//
//@Configuration
//public class H2ToCsvTasklet implements Tasklet{
//
//	// Inject JdbcTemplate to access the database
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
// 
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        // Query the database to fetch data
//        String sql = "SELECT * FROM EMPLOYEES"; // Replace with your table and query
//        List<EmployeeVO> employees = jdbcTemplate.query(sql, (rs, rowNum) -> {
//            EmployeeVO emp = new EmployeeVO();
//            emp.setId(rs.getLong("id"));
//            emp.setName(rs.getString("name"));
//            emp.setAge(rs.getInt("age"));
//            return emp;
//        });
// 
//        // Write data to CSV
//        try (FileWriter fileWriter = new FileWriter("result/h2ToCsvTasklet.csv")) {
//            // Write CSV header
//            fileWriter.append("id, name, age\n");
// 
//            // Write data rows
//            for (EmployeeVO emp : employees) {
//                fileWriter.append(emp.getId() + ", ");
//                fileWriter.append(emp.getName() + ", ");
//                fileWriter.append(emp.getAge() + "\n ");
//
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Error writing to CSV", e);
//        }
// 
//        return RepeatStatus.FINISHED; // Indicate that the tasklet has finished
//    }
//}
