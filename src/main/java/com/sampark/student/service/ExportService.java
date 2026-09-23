package com.sampark.student.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.sampark.student.model.Student;
import com.sampark.student.model.StudentDetails;
import com.sampark.student.repository.StudentRepository;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Font;

@Service
public class ExportService {
	
	private StudentRepository studentRepo; 
	
	public ExportService(StudentRepository studentRepo)
	{
		this.studentRepo=studentRepo;
	}

		
		public ByteArrayInputStream exportStudents() throws IOException {

	        // Get data from database
	        List<Student> students = studentRepo.getAllStudents();

	        // Create Excel workbook
	        Workbook workbook = new XSSFWorkbook();


	        // =========================
	        // HEADER STYLE
	        // =========================

	        CellStyle headerStyle = workbook.createCellStyle();

	        headerStyle.setAlignment(HorizontalAlignment.CENTER);
	        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

	        Font headerFont = workbook.createFont();
	        headerFont.setBold(true);
	        headerFont.setFontHeightInPoints((short) 12);

	        headerStyle.setFont(headerFont);

	        // =========================
	        // DATA STYLE
	        // =========================

	        CellStyle dataStyle = workbook.createCellStyle();

	        dataStyle.setAlignment(HorizontalAlignment.CENTER);
	        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

	        // Create sheet
	        Sheet sheet = workbook.createSheet("Students");

	        // =========================
	        // HEADER
	        // =========================

	        Row header = sheet.createRow(0);

	        header.createCell(0).setCellValue("ID");
	        header.createCell(1).setCellValue("Name");
	        header.createCell(2).setCellValue("Class Name");
	        header.createCell(3).setCellValue("Roll");
	        header.createCell(4).setCellValue("Address");
	        header.createCell(5).setCellValue("Email");
	        header.createCell(6).setCellValue("Mobile");
	        header.createCell(7).setCellValue("Fees");
	        header.createCell(8).setCellValue("Course");
	        header.createCell(9).setCellValue("Status");

	        // =========================
	        // DATA
	        // =========================

	        int rowNumber = 1;

	        for (Student student : students) {

	            Row row = sheet.createRow(rowNumber++);

	            // Student table data
	            row.createCell(0).setCellValue(student.getId());
	            row.createCell(1).setCellValue(student.getName());
	            row.createCell(2).setCellValue(student.getClassName());


	            // StudentDetails data
	            StudentDetails details = student.getStudentDetails();

	            if (details != null) {

	                row.createCell(3)
	                        .setCellValue(details.getRoll());

	                row.createCell(4)
	                        .setCellValue(details.getAddress());

	                row.createCell(5)
	                        .setCellValue(details.getEmail());

	                row.createCell(6)
	                        .setCellValue(details.getMobile());

	                row.createCell(7)
	                        .setCellValue(details.getFees());

	                row.createCell(8)
	                        .setCellValue(details.getCourse());

	                row.createCell(9)
	                        .setCellValue(student.isStatus());
	            }
	        }

	        // Adjust column width
	        for (int i = 0; i < 10; i++) 
	        {
	            sheet.autoSizeColumn(i);
	        }

	        // Convert Excel into byte array
	        ByteArrayOutputStream outputStream =
	                new ByteArrayOutputStream();

	        workbook.write(outputStream);

	        workbook.close();

	        return new ByteArrayInputStream(
	                outputStream.toByteArray()
	        );
		
	}

}
