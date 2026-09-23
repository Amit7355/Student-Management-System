package com.sampark.student.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sampark.student.model.StudentDetails;


public interface StudentDetailsRepository
        extends JpaRepository<StudentDetails, String> {

    @Query(value = "SELECT * FROM students_detail", nativeQuery = true)
    List<StudentDetails> getAllStudentDetails();

    @Query(value=
    		"SELECT * FROM students_detail WHERE roll=:roll",
    		nativeQuery=true)
	StudentDetails getStudentDetailsByRoll(@Param("roll") String roll);
//    
    @Modifying
    @Query(value = """
        INSERT INTO students_detail
        (roll, address, email, mobile, fees, course)
        VALUES (:roll,:address, :email, :mobile, :fees, :course)
        """, nativeQuery = true)
    int saveStudentDetails(
    	@Param("roll") String roll,
        @Param("address") String address,
        @Param("email") String email,
        @Param("mobile") String mobile,
        @Param("fees") int fees,
        @Param("course") String course
    );
    
    @Modifying
    @Query(value=
    		"DELETE FROM students_detail WHERE roll=:roll",
    		nativeQuery=true)
	int deleteStudentDetailsRoll(@Param("roll")String roll);

    @Modifying
    @Query(value="""
    		UPDATE students_detail SET address=:address, email=:email,  mobile=:mobile, fees=:fees, course=:course WHERE roll=:roll 
    		""",nativeQuery=true)
	int updateStudentDetails(
			@Param("roll") String roll, 
			@Param("address")String address,
			@Param("email")String email,
			@Param("mobile")String mobile,
			@Param("fees")int fees,
			@Param("course")String course);

    @Modifying
    @Query(value=
    "UPDATE students_detail SET fees=:fees WHERE roll=:roll",
    nativeQuery=true)
	int updatePartialStudentDetails(@Param("roll")String roll,
			                @Param("fees")int fees);

    
    @Query(value = "SELECT COUNT(*) FROM students_detail WHERE course = :course",
    	       nativeQuery = true)
    	int countByCourse(@Param("course") String course);
   
}