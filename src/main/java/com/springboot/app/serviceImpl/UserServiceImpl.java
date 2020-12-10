package com.springboot.app.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.domain.User;
import com.springboot.app.repository.UserRepository;
import com.springboot.app.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public synchronized boolean uploadFile(MultipartFile file) {
		
		List<User> userList = new ArrayList<User>();
		boolean checkFile = false;
		if(!file.isEmpty()) {
			BufferedReader br;
			try {

			     String line;
			     InputStream is = file.getInputStream();
			     br = new BufferedReader(new InputStreamReader(is));
			     int totalLine = 1;
			     while ((line = br.readLine()) != null) {
			    	 checkFile = false;
			    	 if(totalLine != 1) {
			    		 char firstChar = line.charAt(0); 
				    	 if(firstChar != '#') {
				    		 if(checkRecord(line)) {
				    			 String[] stringRecordArray = line.split(",");
				    			 User userEntity = new User(stringRecordArray[0],stringRecordArray[1],stringRecordArray[2],Double.parseDouble(stringRecordArray[3]));
				    			 userList.add(userEntity);
				    			 checkFile = true;
				    			 
				    		 }else {
				    			 break;
				    		 }
				    	 }
			    	 }
			    	 
			         totalLine++;
			     }
			     if(checkFile&&!userList.isEmpty()) {
			    	 for(User u: userList) {
			    		userRepo.save(u);
			    	 }
			     }

			  } catch (IOException e) {
			    System.err.println(e.getMessage());       
			  }
		}
		
		
		return checkFile;
	}
	
	public boolean checkRecord(String record) {
		boolean result = false;
		String[] stringRecordArray = record.split(",");
		//Count total tokens
		if(stringRecordArray.length == 4) {
			//Check Salary
			try {
				String[] salaryStringArray = stringRecordArray[3].split("\\.");
				if(salaryStringArray[1].length() == 2) {
					double salary = Double.parseDouble(stringRecordArray[3]);
					if(salary >= 0) {
						result = true;
					}
				}
			}catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		
		return result;
	}

}
