package com.sampark.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sampark.student.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query(value = "SELECT * FROM student", nativeQuery = true)
    List<Student> getAllStudents();
    
    // Fetch By Id
    @Query(value=
    		"SELECT * FROM student WHERE id=:id AND status=true" ,
    		nativeQuery=true)
	Student getStudentById(@Param("id") int id);
    
    @Modifying
    @Query(value = """
        INSERT INTO student
        (name, roll, class_name, status)
        VALUES
        (:name, :roll, :className, :status)
        """, nativeQuery = true)
    int saveStudent(
            @Param("name") String name,
            @Param("roll") String roll,
            @Param("className") String className,
            @Param("status") boolean status
    );

   


    @Modifying
    @Query(value=
    		"DELETE FROM student WHERE id=:id",
    		nativeQuery=true)
    int deleteStudentById(@Param("id")int id);


	@Modifying
	@Query(value="""
			UPDATE student SET name=:name,  roll=:roll,  class_name=:className ,status=:status WHERE id=:id
			""",nativeQuery=true)
	int updateStudent(@Param("id") int id,@Param("name")String name,@Param("roll")String roll,@Param("className")String className,@Param("status") boolean status);

    @Modifying
    @Query(value=
    "UPDATE  student SET status=:status WHERE id=:id",
    nativeQuery=true)
	int deleteSoftly(@Param("id") int id, 
			         @Param("status") boolean status);
    
    }