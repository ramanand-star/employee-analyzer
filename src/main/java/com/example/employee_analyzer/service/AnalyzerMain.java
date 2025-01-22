package com.example.employee_analyzer.service;

import java.io.IOException;

public class AnalyzerMain {
    public static void main(String[] args) {
        EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
        try {
            analyzer.readCSV("/Users/rkuma820/Downloads/employee-analyzer/employees.csv");
            analyzer.analyzeSalaries();
            analyzer.analyzeReportingLines();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
