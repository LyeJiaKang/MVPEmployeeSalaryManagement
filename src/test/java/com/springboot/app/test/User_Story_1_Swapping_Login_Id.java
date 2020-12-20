package com.springboot.app.test;

import java.io.FileInputStream;

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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class User_Story_1_Swapping_Login_Id {

	
	// Change to your Sample test files directory and assign to inputFileDir (absolute directory)
	final String inputFileDir = "C:\\<youruser>\\<youruser>\\<dir>\\springboot-app\\src\\test\\resources\\test_case_files\\";
	@Autowired
	private WebApplicationContext userController;
		
	@Test
	/*
	 * Test Case 11 Description: To test on swapping of login ID Expected Result:
	 * Record will be updated to database accordingly with 3 uploads.
	 */
	public void uploadUserSwappingLoginId() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String initialFileName = "01_test_data.csv";
		FileInputStream initialfis = new FileInputStream(inputFileDir + initialFileName);
		String fileName1 = "11_test_data_1.csv";
		String fileName2 = "11_test_data_2.csv";
		String fileName3 = "11_test_data_3.csv";
		FileInputStream fis = new FileInputStream(inputFileDir + fileName1);
		
		//Insert default records
		MockMultipartFile intialFile = new MockMultipartFile("file", initialfis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(intialFile)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("true"));

		// get id e0001
		String getInitialId = "e0001";
		mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + getInitialId))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.login", is("hpotter")));

		// get id e0002
		String getInitialId2 = "e0002";
		mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + getInitialId2))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.login", is("rwesley")));
		
		
		//First upload to set 1st Id to 'tempid'
		MockMultipartFile file = new MockMultipartFile("file", fis);
		mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("true"));

		String getId = "e0001";
		mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + getId))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.login", is("tempid")));
		
		//Second upload to set Second user Id to first user Id
		FileInputStream fis2 = new FileInputStream(inputFileDir + fileName2);
		MockMultipartFile file2 = new MockMultipartFile("file", fis2);
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file2)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("true"));
		String getId2 = "e0002";
		mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + getId2))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.login", is("hpotter")));

		//Third upload to set first user Id to Second user Id
		FileInputStream fis3 = new FileInputStream(inputFileDir + fileName3);
		MockMultipartFile file3 = new MockMultipartFile("file", fis3);
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file3)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("true"));

		mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + getId))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.login", is("rwesley")));
	}
}
