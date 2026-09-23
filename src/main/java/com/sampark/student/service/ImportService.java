package com.sampark.student.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sampark.student.model.StudentRequest;
import com.sampark.student.repository.StudentDetailsRepository;
import com.sampark.student.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class ImportService {

    private final StudentRepository studentRepo;
    private final StudentDetailsRepository studentDetailsRepo;

    public ImportService(
            StudentRepository studentRepo,
            StudentDetailsRepository studentDetailsRepo) {

        this.studentRepo = studentRepo;
        this.studentDetailsRepo = studentDetailsRepo;
    }

    @Transactional
    public String importStudents(MultipartFile file,StudentRequest studentRequest) throws IOException {

    	
    	
    	InputStream resource=file.getInputStream();
        Workbook workbook = WorkbookFactory.create(resource);

        Sheet sheet = workbook.getSheetAt(0); // first sheet take

        // for unique print
        Set<String> mobileSet=new HashSet<>();
        Set<String> emailSet=new HashSet<>();
        
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // getLastRowNum tak jayega last row tak jayega ka excel sheet ke 

            Row row = sheet.getRow(i);

            if (row == null) { //agar row me data nhi to skip kardo
                continue;
            }

            // Read Student data
            DataFormatter formatter = new DataFormatter();

           
            
            String name = formatter.formatCellValue(row.getCell(1)).trim();
            
            String className = row.getCell(2).getStringCellValue();
           // String roll = row.getCell(3).getStringCellValue();

            // Read StudentDetails data
            String address = row.getCell(4).getStringCellValue();
            String email = row.getCell(5).getStringCellValue();
            String mobile = formatter.formatCellValue(row.getCell(6));

            int fees =(int) row.getCell(7).getNumericCellValue();

            String course = row.getCell(8).getStringCellValue();

            //boolean status = row.getCell(9).getBooleanCellValue();

            // className Not null
            if(className.isEmpty())
            {
            	System.out.println("class Name should not be empty");
            	continue;
            	//throw new IllegalArgumentException("class Name should not be empty"+row.getRowNum()+1);	
            }
            
            //check name is empty or not
            if(name.isEmpty()) // isEmpty given by default true
            {
            
            	System.out.println("Name should not be empty");
            	continue;
            	     
            	 //throw  new IllegalArgumentException("Name cannot be empty at row: "+row.getRowNum()+1); // row number start from 0 so we add 1  to give  correct  location where is empty
            		    
            }
            
            //check email contains @
            if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") || email.isBlank()) // email contain nhi karrha hai @ so throw exception
            {
                System.out.println(" Email should not be blank and must be unique at row");
                continue;
            	//throw new IllegalArgumentException("Invalid email and Email should not be blank and must be unique at row: "+row.getRowNum());
            	
            }
            
            // Check phone number must me 10 digit and must be unique and not blank
            if(!mobile.matches("\\d{10}") || mobile.isBlank() ) // d-> means digit contains(0-9) and 10 means 10 digit contains > It does not accept any alphabet or special character inside phone number
            {
            	throw new IllegalArgumentException("Invalid mobile and mobile must be unique and not blank at row: "+row.getRowNum());
                  	
            }
            
            
             //mobile unique
            if(!mobileSet.add(mobile))
            {
            	throw new  IllegalArgumentException("Duplicate mobile number at Row: "+row.getRowNum()+1);
            	
            }
            
            // email must be unique
            if(!emailSet.add(email))
            {
            	throw new IllegalArgumentException("Duplicate email  at Row:"+row.getRowNum()+1);
            }
            
            
            
            // generate roll number automatically
            
            String a=course+String.format("%03d", i);
            
            // First insert StudentDetails
            studentDetailsRepo.saveStudentDetails(
                    a,
                    address,
                    email,
                    mobile,
                    fees,
                    course
            );

            // Then insert Student
            studentRepo.saveStudent(
                    name,
                    a,
                    className,
                    studentRequest.isStatus()
            );
        }

        workbook.close();

        return "Excel data inserted successfully";
    }
}
