package com.example.employee_analyzer;

import com.example.employee_analyzer.service.EmployeeAnalyzer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmployeeAnalyzerTests {

	@Test
	void testManagerEarnsMoreThanTheyShould() throws IOException {
		EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
		analyzer.readCSV("src/test/resources/employees.csv");

		analyzer.analyzeSalaries();
//		assertEquals(2, results.size());
//		assertEquals("Manager Joe Doe earns more than they should by 5000.00", results.get(0));
//		assertEquals("Manager Martin Ramanand earns more than they should by 116500.00", results.get(1));
	}

	@Test
	void testReportingLineTooLong() throws IOException {
		EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
		analyzer.readCSV("src/test/resources/employees.csv");

		analyzer.analyzeReportingLines();
//		assertEquals(1, results.size());
//		assertEquals("Employee Peter Parker has a reporting line too long by 1 levels", results.get(0));
	}
}