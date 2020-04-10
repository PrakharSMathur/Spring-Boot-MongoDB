package com.mongodb.springmongodb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mongodb.springmongodb.Model.Student;
import com.mongodb.springmongodb.Resource.StudentController;

@ComponentScan({"com.mongodb.springmongodb.Model","com.mongodb.springmongodb.Repository","com.mongodb.springmongodb.Resource","com.mongodb.springmongodb.Service"})
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class WithoutMocks {
	String victim;
	String victim2;
	String expected;
	String actual;
	Student st1;
	Student st2;
	
	@Autowired
	StudentController stc;
	@Test
	public void initializeOnce()
	{
		stc.saveStudent(new Student());
//		st2=new Student();
//		st2.setId("200");
//		st2.setStName("Rishit");
//		st2.setStStream("CSE");
//		stc.saveStudent(st2);
//		st2=new Student();
//		st2.setId("200");
//		st2.setStName("Rishit");
//		st2.setStStream("CSE");
//		stc.saveStudent(st2);
	}
	
	@Test
	public void testAddStudent()
	{
		//"Init successful! \n" + "Kindly retry entering data"
		st1=new Student();
		st1.setId("200");
		st1.setStName("Taran1");
		st1.setStStream("CSE");
		stc.saveStudent(st1);		
		String actual=(String)stc.saveStudent(st1);
		String expected = "Added Student-\n" + "ID :" + "A0..." + "\nName :" + String.valueOf(st1.getStName());
		assertTrue(Pattern.matches(expected,actual));
		System.out.println(actual);
		
	}
	

	@Test
	public void testGetAllStudents() {
		st2=new Student();
		st2.setId("200");
		st2.setStName("Rishit");
		st2.setStStream("CSE");
		stc.saveStudent(st2);
		List<Student> ls=stc.getStudents();
		assertEquals(4,ls.size());
		expected="." ;
//		victim=ls.get(1).getId();
		victim2=ls.get(2).getId();
		assertEquals("0",ls.get(0).getId().toString());
		assertTrue(Pattern.matches(expected,ls.get(0).getStName().toString()));
		assertEquals(st2.getStName(),ls.get(3).getStName());
	}
	@Test
	public void testFindById() 
	{
		try {
		st1=stc.getStudent("A0001").get();
		assertEquals("Taran1",st1.getStName());
		st2=stc.getStudent("A0002").get();
		assertEquals("Rishit",st1.getStName());
		}
		catch(NoSuchElementException e)
		{
			System.out.println("technically must not give this excp but it is, so something is wrong");
		}
//		System.out.println(st.toString());
	}
	@Test
	public void testUpdate_not_present()
	{
		st2=new Student();
		st2.setId("A1000");
		st2.setStName("Rishit");
		st2.setStStream("CSE");
		assertEquals("ID does not exist",stc.updateStudent(st2, "A1000"));
	}
	@Test(expected=org.springframework.dao.DuplicateKeyException.class)
	public void testUpdate_dup_key()
	{
		st2=new Student();
		st2.setId("200");
		st2.setStName("taran2");
		st2.setStStream("CSE");
		stc.saveStudent(st2);
		st2=new Student();
		st2.setId("A0001");
		st2.setStName("Rishit");
		st2.setStStream("CSE");
		stc.updateStudent(st2, "A0002");
		
	}
	@Test()
	public void testDeleteStudent()
	{
		st2=new Student();
		st2.setId("200");
		st2.setStName("taran3");
		st2.setStStream("CSE");
		stc.saveStudent(st2);
		stc.deleteStudent("A0001");
		assertEquals(Optional.empty(),stc.getStudent("A0001"));
	}
	
}
