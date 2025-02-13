package com.example.demoApp1.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="Employees")
@Data
public class EmployeeVO implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique Id of the Employee", example = "1", required = true)
    private Long id;

	@NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters")
    @Column(nullable = false)
	@Schema(description = "Name of the Employee", example = "Sahithi", required = true)
    private String name;

	@NotNull(message = "Age is mandatory")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age must be at most 65")
    @Column(nullable = false)
	@Schema(description = "Age of the Employee", example = "20", required = true)
    private Integer age;

	public EmployeeVO() {
	}

	public EmployeeVO(Long id,
			@NotBlank(message = "Name is mandatory") @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters") String name,
			@NotNull(message = "Age is mandatory") @Min(value = 18, message = "Age must be at least 18") @Max(value = 65, message = "Age must be at most 65") Integer age , Long Version) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
//		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
    
    
}
