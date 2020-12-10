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
		boolean duplicatedLogin = false;
		boolean result = false;
		if(!file.isEmpty()) {
			BufferedReader br;
			try {

			     String line;
			     InputStream is = file.getInputStream();
			     br = new BufferedReader(new InputStreamReader(is));
			     int totalLine = 1;
			     while ((line = br.readLine()) != null) {
			    	 if(totalLine != 1) {
			    		 char firstChar = line.charAt(0); 
				    	 if(firstChar != '#') {
				    		 if(checkRecord(line)) {
				    			 String[] stringRecordArray = line.split(",");
				    			 User userEntity = new User(stringRecordArray[0],stringRecordArray[1],stringRecordArray[2],Double.parseDouble(stringRecordArray[3]));
				    			 userList.add(userEntity);
				    			 checkFile = true;
				    			 
				    		 }else {
				    			 checkFile = false;
				    			 break;
				    		 }
				    	 }
			    	 }
			    	 
			         totalLine++;
			     }
			     if(checkFile&&!userList.isEmpty()) {
			    	 
			    	 for(User u: userList) {
			    		 for(User u2: userList) {
			    			 if(u.getLogin().equals(u2.getLogin())) {
			    				 if(!u.getId().equals(u2.getId())) {
			    					 duplicatedLogin = true;
			    					 break;
			    				 }
			    			 }
					    }
			    	 }
			    	 if(!duplicatedLogin) {
			    		 for(User u: userList) {
			    			 userRepo.save(u);
				    	 }
			    		 result = true;
			    	 }
			     }
			  } catch (IOException e) {
			    System.err.println(e.getMessage());       
			  }
		}
		
		
		return result;
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
						User user = userRepo.findBylogin(stringRecordArray[1]);
						if(user == null) {
							result = true;
						}else if (user.getId().equals(stringRecordArray[0])) {
							result = true;
						}
					}
				}
			}catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		
		return result;
	}

}
