package com.mongodb.springmongodb;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.springmongodb.Model.Student;
import com.mongodb.springmongodb.Repository.StudentRepository;
import com.mongodb.springmongodb.Resource.StudentController;
import com.mongodb.springmongodb.Service.StudentService;


//Disclaimer:half assed implementation below
@Configuration
@ComponentScan(basePackages= {"com.mongodb.springmongodb.Repository","com.mongodb.springmongodb.Resource","com.mongodb.springmongodb.Service"})
class SpringContext
{
	
}
//only with controller methods
@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = SpringContext.class)
public class ResourceTest {
	Student st1;
	Student st2;
	Student st3;
	private MockMvc mockMvc;
	@InjectMocks
	StudentService sts;
	@InjectMocks
    private StudentController studentController;
	@Mock
	StudentRepository str;
//	@BeforeClass
//	public static void setupController()
//	{
//		//will be called once
//		studentController.setStudentService(sts);
//		studentController.setStudentServiceRepository(str);
//	}
	@Before
	public void setup()
	{
		st1=new Student();
		st2=new Student();
		st3=new Student();
		mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
		studentController.setStudentService(sts);
		studentController.setStudentServiceRepository(str);
		MockitoAnnotations.initMocks(this);
		
		
	}
	@Test
	public void testAddStudent() throws Exception
	{	
//		Student count = new Student();
//        count.setId("0");
//        count.setStName("0");
//        when(str.insert(count)).thenReturn(count); //"Init successful! \n" + "Kindly retry entering data"
        when(str.count()).thenReturn((long)1);
		st2.setId("A0001");
		st2.setStName("0");
		st2.setStStream("CSE");
		
		Optional<Student> opt = Optional.of( st2 );
		when(str.findById("0")).thenReturn(opt);
//		when(str.insert(st2)).thenReturn(st2);
		when(str.save(st2)).thenReturn(st2);
		String expected="Added Student-\n" + "ID :" + String.valueOf(st2.getId()) + "\nName :" + String.valueOf(st2.getStName());
		assertEquals(expected,studentController.saveStudent(st2).toString());
		ObjectMapper mapper = new ObjectMapper();
	      String jsonString = mapper.writeValueAsString(st2);
		mockMvc.perform(MockMvcRequestBuilders
			      .post("/addStudent")
			      .content(jsonString)
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))		      
		.andExpect(status().isOk());
		verify(str,times(2)).count();
		verify(str,times(2)).save(st2);
//		verify(str,times(1)).insert(st2);   //do not uncomment this anywhere,there is some problem with this.
//		verify(str,atLeast(1)).insert(st2);
	}
	@Test
	public void testUpdateStudent() throws Exception
	{	
		st1.setId("A001");
		st1.setStName("Taran");
		st1.setStStream("CSE");
		st2.setId("A001");
		st2.setStName("Rishit");
		st2.setStStream("CSEB");
		String expected=st2.getId(); // we need same id with different attr's 
		Optional<Student> opt = Optional.of( st1 );
		when(str.findById(st1.getId())).thenReturn(opt);
//		when(str.insert(st2)).thenReturn(st2);
		assertEquals("A001",studentController.updateStudent(st2,"A001"));
		ObjectMapper mapper = new ObjectMapper();
	      String jsonString = mapper.writeValueAsString(st1);
		mockMvc.perform(MockMvcRequestBuilders
			      .put("/updateStudent/{id}",st1.getId())
			      .content(jsonString)
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))		      
		.andExpect(status().isOk());
		verify(str,times(2)).deleteById(st1.getId());
//		verify(str,times(1)).insert(st2);
	}
	//check for dup primary keys
	@Test()
	public void testUpdateStudent_dup_test() throws Exception
	{	
		
//		java.lang.Exception: Unexpected exception, expected<java.lang.Exception> but was<org.mockito.exceptions.verification.junit.ArgumentsAreDifferent>
	

		st1.setId("A001");
		st1.setStName("Taran");     //existing data
		st1.setStStream("CSE");
		st2.setId("A002");			// u gave this for update
		st2.setStName("Rishit");
		st2.setStStream("CSEB");
		st3.setId("A002");			//another one already exists
		st3.setStName("Undru");
		st3.setStStream("cse");
		
		List<Student> as=new ArrayList<Student>();
		as.add(st1);
		as.add(st2);
		as.add(st3);
		
//		when(str.findById(st1.getId())).thenReturn(st1);
		Optional<Student> opt = Optional.of( st1 );
		when(str.findById(st1.getId())).thenReturn(opt);
		
		when(str.insert(st3)).thenReturn(st3);
//		when(str.insert(st2)).thenReturn(st2);
		Student expected=str.insert(st3);
		
		assertNotEquals(expected.getId().toString(),studentController.updateStudent(st2,"A001"));
		ObjectMapper mapper = new ObjectMapper();
	      String jsonString = mapper.writeValueAsString(st1);
		mockMvc.perform(MockMvcRequestBuilders
			      .put("/updateStudent/{id}",st1.getId())
			      .content(jsonString)
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))		      
		.andExpect(status().isOk());
//		verify(str,times(1)).insert(st2);
	}
	@Test
	public void testGetStudent() throws Exception
	{
		Student st0=new Student();
		st0.setId("0");
		st0.setStName("0");;
		st0.setStStream("");
		st1.setId("A0001");
		st1.setStName("Taran");
		st1.setStStream("CSE");
		st2.setId("A0002");
		st2.setStName("Rishit");
		st2.setStStream("CSEB");
		List<Student> ls=new ArrayList<Student>();
		ls.add(st0);
		ls.add(st1);
		ls.add(st2);
		
		when(str.findAll()).thenReturn(ls);
//		studentController.getStudents();
		assertEquals(ls,studentController.getStudents());
		mockMvc.perform(MockMvcRequestBuilders
			      .get("/findAllStudents"))		      
		.andExpect(status().isOk());
		verify(str,times(2)).findAll();
	}
	@Test
	public void testDeleteStudent() throws Exception
	{
		st1.setId("A0001");
		st1.setStName("Taran");
		st1.setStStream("CSE");
		when(str.existsById(st1.getId())).thenReturn(true);
//		when(str.deleteById(st1.getId())).thenReturn(st1);
		assertEquals("Student deleted : " + st1.getId(),studentController.deleteStudent(st1.getId()));
		ObjectMapper mapper = new ObjectMapper();
		 String jsonString = mapper.writeValueAsString(st1);
		mockMvc.perform(MockMvcRequestBuilders
			      .delete("/deleteStudent/{id}",st1.getId())
			      .content(jsonString)
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))		      
		.andExpect(status().isOk());      //makes it two times...
		verify(str,times(2)).existsById(st1.getId());
		verify(str,times(2)).deleteById(st1.getId());
		
	}
	@Test
	public void testFindById() throws Exception
	{
		st1.setId("A0001");
		st1.setStName("Taran");
		st1.setStStream("CSE");
		Optional opt=Optional.of(st1);
		
		when(str.findById(st1.getId())).thenReturn(opt);
		assertEquals(st1.toString(),studentController.getStudent(st1.getId()).get().toString());
		ObjectMapper mapper = new ObjectMapper();
	      String jsonString = mapper.writeValueAsString(st1);
		mockMvc.perform(MockMvcRequestBuilders
			      .post("/addStudent")
			      .content(jsonString)
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))		      
		.andExpect(status().isOk());
		
		
		
	}
//	
	
	
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
	

}
