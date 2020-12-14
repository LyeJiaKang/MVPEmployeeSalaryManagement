package com.springboot.app.test;


import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.FileInputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.app.domain.User;

@SpringBootTest
public class User_Story_2_UserTest {

	// Change to your Sample test files directory and assign to inputFileDir
	// (absolute directory)
	final String inputFileDir = this.getClass().getResource(".").getPath() + "test_data_file/";
	@Autowired
	private WebApplicationContext userController;

	@Test
	@Order(1)
	/*
	 * Description: To insert records to database for initial setup
	 */
	public void setupInitialDataToDB() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String fileName = "Sample_test_data_for_user_story_test_2.csv";

		FileInputStream fis = new FileInputStream(inputFileDir + fileName);

		MockMultipartFile file = new MockMultipartFile("file", fis);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/user/upload").file(file)).andExpect(ok)
				.andExpect(MockMvcResultMatchers.content().string("true"));
	}
	
	@Test
	/*
	 * Test Case 01 Description: To test on null value for minSalary
	 * Expected Result: Error 400 Bad Request
	 */
	public void getUsersNullMinSalary() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = null;
		Double maxSalary = 10000.00;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 02 Description: To test on null value for maxSalary
	 * Expected Result: Error 400 Bad Request
	 */
	public void getUsersNullMaxSalary() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = 0.0;
		Double maxSalary = null;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 03 Description: To test on null value for offset
	 * Expected Result: Error 400 Bad Request
	 */
	public void getUsersNullOffset() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = 0.0;
		Double maxSalary = 10000.00;
		Integer offset = null;
		Integer limit = 30;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 04 Description: To test on null value for Limit
	 * Expected Result: Error 400 Bad Request
	 */
	public void getUsersNullLimit() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = 0.0;
		Double maxSalary = 10000.00;
		Integer offset = 0;
		Integer limit = null;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 05 Description: To test on null value for sorting String
	 * Expected Result: Error 400 Bad Request
	 */
	public void getUsersNullSort() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = 0.0;
		Double maxSalary = 10000.00;
		Integer offset = 0;
		Integer limit = 30;
		String sort = null;
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 06 Description: To test on negative value for minSalary
	 * Expected Result: Error 400 Bad Request
	 */
	public void getUsersNegativeMinSalary() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = -1.0;
		Double maxSalary = 10000.00;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 07 Description: To test on negative value for maxSalary
	 * Expected Result: Error 400 Bad Request
	 */
	public void getUsersNegativeMaxSalary() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = 0.0;
		Double maxSalary = -1.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 07 Description: To test on negative value for offset
	 * Expected Result: Error 400 Bad Request
	 */
	public void getUsersNegativeOffset() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = -1;
		Integer limit = 30;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 08 Description: To test on negative value for Limit
	 * Expected Result: Error 400 Bad Request
	 */
	public void getUsersNegativeLimit() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = -1;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 09 Description: To test on minSalary is more than maxSalary
	 * Expected Result: Error 400 Bad Request
	 */
	public void getUsersMinSalaryMoreThanMaxSalary() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = 5000.0;
		Double maxSalary = 4999.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 10 Description: To test on minSalary is equals than maxSalary
	 * Expected Result: HTTP 200 OK
	 */
	public void getUsersMinSalaryEqualsMaxSalary() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		Double minSalary = 0.0;
		Double maxSalary = 5000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(ok);
	}
	
	@Test
	/*
	 * Test Case 11 Description: To test on sorting order sign that is not '+' or '-'
	 * Expected Result: HTTP 200 OK
	 */
	public void getUsersInvalidSortingOrderSign() throws Exception {
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();

		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "!name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))
				.andExpect(badRequest);
	}
	
	@Test
	/*
	 * Test Case 12 Description: To test on getting users with salary within minSalary and maxSalary range
	 * Expected Result: HTTP 200 OK
	 */
	public void getUsersSalaryWithinMinSalaryAndMaxSalaryRange() throws Exception {
		setupInitialDataToDB();
		ObjectMapper objectMapper = new ObjectMapper();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});
		
		//check for salary is within range
		for(User u : userList) {
			assertTrue(minSalary <= u.getSalary() && u.getSalary() <= maxSalary);
		}
	}
	
	@Test
	/*
	 * Test Case 13 Description: To test on getting users with correct offset range of value zero
	 * Expected Result: HTTP 200 OK
	 * For offset = 0, the first id will be 'e0001' to the last id of 'e0030'
	 */
	public void getUsersWithCorrectOffsetRangeOfValueZero() throws Exception {
		setupInitialDataToDB();
		ObjectMapper objectMapper = new ObjectMapper();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+id";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});
		
		//offset = 0, the first id will be 'e0001' to the last id of 'e0030'
		assertTrue(userList.get(0).getId().equals("e0001")&& userList.get(userList.size()-1).getId().equals("e0030"));
	}
	
	@Test
	/*
	 * Test Case 14 Description: To test on getting users with correct offset range of value one
	 * Expected Result: HTTP 200 OK
	 * For offset = 1, the first id will be 'e0002' to the last id of 'e0031'
	 */
	public void getUsersWithCorrectOffsetRangeOfValueOne() throws Exception {
		setupInitialDataToDB();
		ObjectMapper objectMapper = new ObjectMapper();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 1;
		Integer limit = 30;
		String sort = "+id";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});
		
		//offset = 1, the first id will be 'e0002' to the last id of 'e0031'
		assertTrue(userList.get(0).getId().equals("e0002")&& userList.get(userList.size()-1).getId().equals("e0031"));
	}
	
	@Test
	/*
	 * Test Case 15 Description: To test on getting users with correct limit range of value 30
	 * Expected Result: HTTP 200 OK
	 * For offset = 0, the first id will be 'e0001' to the last id of 'e0030'
	 */
	public void getUsersWithCorrectLimitRangeOfValue30() throws Exception {
		setupInitialDataToDB();
		ObjectMapper objectMapper = new ObjectMapper();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+id";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});
		
		//limit = 30, the first id will be 'e0002' to the last id of 'e0031'
		assertTrue(userList.get(0).getId().equals("e0001")&& userList.get(userList.size()-1).getId().equals("e0030"));
		
		assertTrue(userList.size()==30);
	}
	
	@Test
	/*
	 * Test Case 16 Description: To test on getting users with correct limit range of value 10
	 * Expected Result: HTTP 200 OK
	 * For offset = 0, the first id will be 'e0001' to the last id of 'e0010'
	 */
	public void getUsersWithCorrectLimitRangeOfValue10() throws Exception {
		setupInitialDataToDB();
		ObjectMapper objectMapper = new ObjectMapper();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 10;
		String sort = "+id";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});
		
		//limit = 30, the first id will be 'e0002' to the last id of 'e0031'
		assertTrue(userList.get(0).getId().equals("e0001")&& userList.get(userList.size()-1).getId().equals("e0010"));
		
		assertTrue(userList.size()==10);
	}
	
	@Test
	/*
	 * Test Case 17 Description: To test on getting users with invalid sorting column name
	 * Expected Result: HTTP 400 Bad Request
	 */
	public void getUsersWithInvalidSortingColumnName() throws Exception {
		setupInitialDataToDB();
		ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 10;
		String sort = "+firstname";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(badRequest);
		
	}
	
	@Test
	/*
	 * Test Case 18 Description: To test on getting users with valid sorting column name (id) ascending order
	 * Expected Result: HTTP 200 ok
	 * With sorting column of 'id' in ascending order, the first id will be 'e0001' and the last id will be 'e0030'
	 */
	public void getUsersWithValidSortingColumnIdAsc() throws Exception {
		setupInitialDataToDB();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		ObjectMapper objectMapper = new ObjectMapper();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+id";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});;
		
		assertTrue(userList.get(0).getId().equals("e0001")&& userList.get(userList.size()-1).getId().equals("e0030"));
		
	}
	
	@Test
	/*
	 * Test Case 18 Description: To test on getting users with valid sorting column name (id) descending order
	 * Expected Result: HTTP 200 ok
	 * With sorting column of 'id' in descending order, the first id will be 'e0042' and the last id will be 'e0013'
	 */
	public void getUsersWithValidSortingColumnIdDesc() throws Exception {
		setupInitialDataToDB();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		ObjectMapper objectMapper = new ObjectMapper();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "-id";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});;
		
		
		assertTrue(userList.get(0).getId().equals("e0042")&& userList.get(userList.size()-1).getId().equals("e0013"));
		
	}
	
	@Test
	/*
	 * Test Case 19 Description: To test on getting users with valid sorting column name (login) ascending order
	 * Expected Result: HTTP 200 ok
	 * With sorting column of 'login' in ascending order, the first login will be 'carl' and the last login will be 'mickey_2'
	 */
	public void getUsersWithValidSortingColumnLoginAsc() throws Exception {
		setupInitialDataToDB();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		ObjectMapper objectMapper = new ObjectMapper();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+login";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});;
		
		assertTrue(userList.get(0).getLogin().equals("carl")&& userList.get(userList.size()-1).getLogin().equals("mickey_2"));
		
	}
	
	@Test
	/*
	 * Test Case 20 Description: To test on getting users with valid sorting column name (login)  descending order
	 * Expected Result: HTTP 200 ok
	 * With sorting column of 'login' in descending order, the first login will be 'tom_3' and the last login will be 'feivel_2'
	 */
	public void getUsersWithValidSortingColumnLoginDesc() throws Exception {
		setupInitialDataToDB();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		ObjectMapper objectMapper = new ObjectMapper();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "-login";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});;

		
		assertTrue(userList.get(0).getLogin().equals("tom_3")&& userList.get(userList.size()-1).getLogin().equals("feivel_2"));
		
	}
	
	@Test
	/*
	 * Test Case 21 Description: To test on getting users with valid sorting column name (name)  ascending order
	 * Expected Result: HTTP 200 ok
	 * With sorting column of 'name' in ascending order, the first name will be 'Carl' and the last name will be 'Mickey_2'
	 */
	public void getUsersWithValidSortingColumnNameAsc() throws Exception {
		setupInitialDataToDB();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		ObjectMapper objectMapper = new ObjectMapper();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});;
		
		
		assertTrue(userList.get(0).getName().equals("Carl")&& userList.get(userList.size()-1).getName().equals("Mickey_2"));
		
	}
	
	@Test
	/*
	 * Test Case 22 Description: To test on getting users with valid sorting column name (name)  descending order
	 * Expected Result: HTTP 200 ok
	 * With sorting column of 'name' in descending order, the first name will be 'Tom_3' and the last name will be 'Feivel_2'
	 */
	public void getUsersWithValidSortingColumnNameDesc() throws Exception {
		setupInitialDataToDB();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		ObjectMapper objectMapper = new ObjectMapper();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "-name";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});
		
		assertTrue(userList.get(0).getName().equals("Tom_3")&& userList.get(userList.size()-1).getName().equals("Feivel_2"));
		
	}
	
	@Test
	/*
	 * Test Case 23 Description: To test on getting users with valid sorting column name (salary)  ascending order
	 * Expected Result: HTTP 200 ok
	 * With sorting column of 'name' in ascending order, the first salary will be '1000.01' and the last salary will be '6902.86'
	 */
	public void getUsersWithValidSortingColumnSalaryAsc() throws Exception {
		setupInitialDataToDB();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		ObjectMapper objectMapper = new ObjectMapper();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "+salary";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});
		
		assertTrue(userList.get(0).getSalary() ==1000.01 && userList.get(userList.size()-1).getSalary() == 6902.86);
		
	}
	
	@Test
	/*
	 * Test Case 24 Description: To test on getting users with valid sorting column name (salary)  descending order
	 * Expected Result: HTTP 200 ok
	 * With sorting column of 'name' in descending order, the first salary will be '9231.79' and the last salary will be '3075.32'
	 */
	public void getUsersWithValidSortingColumnSalaryDesc() throws Exception {
		setupInitialDataToDB();
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		ObjectMapper objectMapper = new ObjectMapper();
		Double minSalary = 0.0;
		Double maxSalary = 10000.0;
		Integer offset = 0;
		Integer limit = 30;
		String sort = "-salary";
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(userController).build();
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary="+minSalary+"&maxSalary="+maxSalary+"&offset="+offset+"&limit="+limit+"&sort="+sort))		
		.andExpect(ok).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		List<User> userList = objectMapper.readValue(contentAsString, new TypeReference<List<User>>(){});
		
		assertTrue(userList.get(0).getSalary() ==9231.79 && userList.get(userList.size()-1).getSalary() == 3075.32);
		
	}
}
