package com.springboot.app.test;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest
public class UserTest {

	// Sample test files in
	// springboot-app\\target\\test-classes\\com\\springboot\a\pp\\test\\test_data_file
	final String pathOfTheCurrentClass = this.getClass().getResource(".").getPath() + "test_data_file/";
	@Autowired
	private WebApplicationContext userController;

	@Test
	@Order(1)
	/*
	 * Test Case 01 Description: To test on a good test data file. Expected Result:
	 * Id e0001 and e0002 records inserted into database correctly.
	 */
	public void uploadUserFileGoodTestData() throws Exception {
		// upload file and insert records to database
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		String fileName = "01_test_data.csv";
		
		
		FileInputStream fis = new FileInputStream(pathOfTheCurrentClass + fileName);
		
		
		
		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("true"));

		// get id e0001
		String getId = "e0001";
		mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + getId))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.id", is(getId)));

		// get id e0002
		String getId2 = "e0002";
		mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + getId2))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.id", is(getId2)));

	}

	@Test
	@Order(2)
	/*
	 * Test Case 02 Description: To test on updating existing records. Expected
	 * Result: Records updated into database correctly. With id e0001 update salary
	 * from 1000.0 to 1001.0 and e0002 updated salary from 2000 to 2001.0.
	 */
	public void uploadUserFileUpdateExistingRecord() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String fileName = "02_test_data.csv";
		FileInputStream fis = new FileInputStream(pathOfTheCurrentClass + fileName);

		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("true"));

		String getId = "e0001";
		mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + getId))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.salary", is(1001.0)));

		String getId2 = "e0002";
		mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + getId2))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.salary", is(2001.0)));

	}

	@Test
	@Order(3)
	/*
	 * Test Case 03 Description: To test on uploading empty file. Expected Result:
	 * No record will be added to database. Return response with 'false'.
	 */
	public void uploadUserEmptyFile() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String fileName = "03_test_data.csv";
		FileInputStream fis = new FileInputStream(pathOfTheCurrentClass + fileName);

		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("false"));

	}

	@Test
	@Order(4)
	/*
	 * Test Case 04 Description: To test on data with more than 4 coloumns. Expected
	 * Result: No record will be added to database. Return response with 'false'.
	 */
	public void uploadUserMoreThan4Columns() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String fileName = "04_test_data.csv";
		FileInputStream fis = new FileInputStream(pathOfTheCurrentClass + fileName);

		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("false"));

	}

	@Test
	@Order(5)
	/*
	 * Test Case 04 Description: To test on data with less than 4 coloumns. Expected
	 * Result: No record will be added to database. Return response with 'false'.
	 */
	public void uploadUserLessThan4Columns() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String fileName = "05_test_data.csv";
		FileInputStream fis = new FileInputStream(pathOfTheCurrentClass + fileName);

		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("false"));

	}

	@Test
	@Order(6)
	/*
	 * Test Case 06 Description: To test on one data with invalid salary and not all
	 * rows. (More than 2 decimal places) Expected Result: No record will be added
	 * to database. Return response with 'false'.
	 */
	public void uploadUserSalaryMoreThan2DecimalPlaces() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String fileName = "06_test_data.csv";
		FileInputStream fis = new FileInputStream(pathOfTheCurrentClass + fileName);

		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("false"));

	}

	@Test
	@Order(7)
	/*
	 * Test Case 07 Description: To test on one data with invalid salary and not all
	 * rows. (Only 1 decimal place) Expected Result: No record will be added to
	 * database. Return response with 'false'.
	 */
	public void uploadUserSalaryOneDecimalPlaces() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String fileName = "07_test_data.csv";
		FileInputStream fis = new FileInputStream(pathOfTheCurrentClass + fileName);

		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("false"));

	}

	@Test
	@Order(8)
	/*
	 * Test Case 08 Description: To test on one data with invalid salary and not all
	 * rows. (Negative salary value E.g. <0.0 ) Expected Result: No record will be
	 * added to database. Return response with 'false'.
	 */
	public void uploadUserNegativeSalaryValue() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String fileName = "08_test_data.csv";
		FileInputStream fis = new FileInputStream(pathOfTheCurrentClass + fileName);

		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("false"));

	}

	@Test
	@Order(9)
	/*
	 * Test Case 09 Description: To test on one data with invalid salary and not all
	 * rows. (Invalid salary value E.g. non-numeric values) Expected Result: No
	 * record will be added to database. Return response with 'false'.
	 */
	public void uploadUserInvalidSalaryValue() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String fileName = "09_test_data.csv";
		FileInputStream fis = new FileInputStream(pathOfTheCurrentClass + fileName);

		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("false"));

	}

	@Test
	@Order(10)
	/*
	 * Test Case 10 Description: To test on non-English characters. (UTF-8, E.g β)
	 * Expected Result: Record will be added to database.
	 */
	public void uploadUserNonEnglishCharacters() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String fileName = "10_test_data.csv";
		FileInputStream fis = new FileInputStream(pathOfTheCurrentClass + fileName);

		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("true"));

		String getId = "e0003";
		mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + getId))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.name", is("Severus Snapeβ")));
	}

}
