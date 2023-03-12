package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import com.itextpdf.text.Document;  
import com.itextpdf.text.DocumentException;  
import com.itextpdf.text.Paragraph;  
import com.itextpdf.text.pdf.PdfWriter;  
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;

@RestController
@RequestMapping("/emp")
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	@GetMapping
	public String sayHello() {
		return "Welcome to Spring boot!!";
	}
	
	@PostMapping("/registerStudent")
	public StudentEntity saveStudent(@RequestBody StudentEntity studentEntity) {
		try {
		studentRepository.save(studentEntity);
		studentEntity.setMessage("Details are saved successfully!!");
		}catch(Exception e) {
			studentEntity.setMessage("Details are Not saved!!");
		}
		return studentEntity;
		
	}
	
	@PutMapping("/updateStudData")
	public StudentEntity updateStudData(@RequestBody StudentEntity studentEntity) {
		try {
		studentRepository.save(studentEntity);
		studentEntity.setMessage("Details are updated successfully!!");
		}catch(Exception e) {
			studentEntity.setMessage("Details are Not updated!!");
		}
		return studentEntity;
		
	}
	
	@DeleteMapping("/deleteStudentrecord")
	public String deleteStudentrecord(@RequestParam Integer id) {
		String message="";
		try {
		studentRepository.deleteById(id);
		message="Details are deleted successfully!!";
		}catch(Exception e) {
			message="Details are Not deleted!!";
		}
		return message;
		
	}
	@GetMapping("/getAllStudData")
	public List<StudentEntity> getAllStud(){
		List<StudentEntity> studentList=studentRepository.findAll();
		///created PDF document instance   
		Document doc = new Document();  
		try  
		{  
		//generate a PDF at the specified location  
		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("D:\\SpringBootPractice\\StudentData.pdf"));  
		System.out.println("PDF created.");  
		//opens the PDF  
		doc.open();  
		//adds paragraph to the PDF file  
		doc.add(new Paragraph("This is Student Details Student Id "+studentList.get(0).getId()+" Student name "+studentList.get(0).getName()+" Company "+studentList.get(0).getCompanyName()));   
		//close the PDF file  
		doc.close();  
		//closes the writer  
		writer.close();  
		}   
		catch (DocumentException e)  
		{  
		e.printStackTrace();  
		}   
		catch (FileNotFoundException e)  
		{  
		e.printStackTrace();  
		}  
		return studentList;
	}

	@GetMapping("/getStudData")
	public StudentEntity getStudData(@RequestParam Integer id){
		
		StudentEntity student=studentRepository.findById(id).orElse(null);
		if(student==null) {
			String message="Details not available for given Input";
			System.out.println(message);
			StudentEntity studente=new StudentEntity();
			studente.setMessage(message);
			return studente;
		}else {
			System.out.println("Detaild was "+student.getName());
		}
		return student;
	}
	
	@DeleteMapping("/deleteAllByStudentrecord")
	public String deleteAllByStudentrecord(@RequestParam List<Integer> id) {
		String message="";
		try {
		studentRepository.deleteAllById(id);
		message="Details are deleted successfully!!";
		}catch(Exception e) {
			message="Details are Not deleted!!";
		}
		return message;
		
	}
}
