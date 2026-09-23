package com.sampark.student.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sampark.student.model.Student;
import com.sampark.student.model.StudentRequest;
import com.sampark.student.service.ExportService;
import com.sampark.student.service.ImportService;
import com.sampark.student.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	
	private ExportService excelService;
	private StudentService service;
	private ImportService importService;
	public StudentController(StudentService service,ExportService excelService,ImportService importService)
	{
		this.service=service;
		this.excelService=excelService;
		this.importService=importService;
	}
	
	@GetMapping("/fetchAll")
	public List<Student> getAllStudents()
	{
		return service.getAllStudents();
	}
	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable int id)
	{
		return service.getStudentById(id);
	}
	@PostMapping("insert")
	public String saveStudent(@RequestBody StudentRequest  studentRequest)
	{
		
		return service.saveStudent(studentRequest);
	}
	
	@DeleteMapping("/{id}")
	public String deleteStudentById(@PathVariable int id)
	{
		return service.deleteStudentById(id);
	}
	
	@PutMapping("/{id}")
	public String updateStudent(@PathVariable int id,@RequestBody  StudentRequest studentRequest)
	{
		return service.updateStudent(id,studentRequest);
		
	}
	// here i used @RequesgtParm for read query parameter and pass parameter 
	@PatchMapping("/fees/{roll}")
	public String updatePartialStudent(@PathVariable String roll,@RequestParam int fees)
	{	
		return service.updatePartialStudent(roll,fees);
	}
	
	@PatchMapping("/status/{id}")
	public String isActiveOrNot(@PathVariable int id, @RequestParam boolean status)
	{
		return service.deleteSoftly(id,status);
		
	}
	
	
	@PutMapping("allDataUpdate")
	public String updateAllStudentTogether(@RequestBody List<StudentRequest> studentRequest)
	{
		
		
		return service.updateAllStudentTogether(studentRequest);
		
	}
	
	@GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportStudents() throws IOException {

        ByteArrayInputStream excelFile =excelService.exportStudents();

        InputStreamResource resource =new InputStreamResource(excelFile);

        String headerKey="CONTENT_DISPOSITION";
        String headerValue="attachment; filename=students.xlsx";
        HttpHeaders headers=new HttpHeaders();
        headers.add(headerKey, headerValue);
        
        return ResponseEntity.ok()
//                .header(
//                        HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=students.xlsx"
//                )
        		.header(headerKey,headerValue)
                .contentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
	}
	
	@PostMapping("/import")
	public String importStudents(
	        @RequestParam("file") MultipartFile file,StudentRequest studentRequest)
	        throws IOException {

	    return importService.importStudents(file,studentRequest);
	}
}
