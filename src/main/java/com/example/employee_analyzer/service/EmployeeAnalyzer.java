package com.example.employee_analyzer.service;

import com.example.employee_analyzer.model.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeAnalyzer {
    private Map<String, Employee> employees = new HashMap<>();

    public void readCSV(String filePath) throws IOException {
        try (Reader in = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("Id", "firstName", "lastName", "salary", "managerId")
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                Employee employee = new Employee(
                        record.get("Id"),
                        record.get("firstName"),
                        record.get("lastName"),
                        Double.parseDouble(record.get("salary")),
                        record.get("managerId")
                );
                employees.put(employee.getId(), employee);
            }
        }
    }

    // Other methods for analysis

    public void analyzeSalaries() {
        for (Employee manager : employees.values()) {
            List<Employee> subordinates = getSubordinates(manager.getId());
            if (!subordinates.isEmpty()) {
                double averageSalary = subordinates.stream().mapToDouble(Employee::getSalary).average().orElse(0);
                double minSalary = averageSalary * 1.2;
                double maxSalary = averageSalary * 1.5;
                System.out.printf("Manager: %s %s, Average Salary: %.2f, Min Salary: %.2f, Max Salary: %.2f%n",
                        manager.getFirstName(), manager.getLastName(), averageSalary, minSalary, maxSalary);
                if (manager.getSalary() < minSalary) {
                    System.out.printf("Manager %s %s earns less than they should by %.2f%n",
                            manager.getFirstName(), manager.getLastName(), minSalary - manager.getSalary());
                } else if (manager.getSalary() > maxSalary) {
                    System.out.printf("Manager %s %s earns more than they should by %.2f%n",
                            manager.getFirstName(), manager.getLastName(), manager.getSalary() - maxSalary);
                }
            }
        }
    }

    public void analyzeReportingLines() {
        for (Employee employee : employees.values()) {
            int reportingLineLength = getReportingLineLength(employee.getId());
            if (reportingLineLength > 4) {
                System.out.printf("Employee %s %s has a reporting line too long by %d levels%n", employee.getFirstName(), employee.getLastName(), reportingLineLength - 4);
            }
        }
    }

    private List<Employee> getSubordinates(String managerId) {
        List<Employee> subordinates = new ArrayList<>();
        for (Employee employee : employees.values()) {
            if (managerId.equals(employee.getManagerId())) {
                subordinates.add(employee);
            }
        }
        return subordinates;
    }

    private int getReportingLineLength(String employeeId) {
        int length = 0;
        Employee current = employees.get(employeeId);
        while (current != null && current.getManagerId() != null) {
            length++;
            current = employees.get(current.getManagerId());
        }
        return length;
    }
}