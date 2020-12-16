package com.springboot.app.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.domain.User;
import com.springboot.app.repository.UserRepository;
import com.springboot.app.service.UserService;
import com.springboot.app.utils.EnhancedPagable;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public synchronized boolean uploadFile(MultipartFile file) {
		
		List<User> userList = new ArrayList<User>();
   	 	ArrayList<String> tempLoginList = new ArrayList<String>();
   	 	ArrayList<String> tempIdList = new ArrayList<String>();
		boolean checkFile = false;
		boolean duplicatedLogin = false;
		boolean duplicatedId = false;
		boolean result = false;
		if(!file.isEmpty()) {
			
			BufferedReader br;
			try {
			     String line;
			     InputStream is = file.getInputStream();
			     br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));
			     int totalLine = 1;
			     while ((line = br.readLine()) != null) {
			    	 if(totalLine != 1) {
			    		 char firstChar = line.charAt(0);
				    	 if(firstChar != '#') {
				    		 if(checkRecord(line)) {
				    			 String[] stringRecordArray = line.split(",");
				    			 User userEntity = new User(stringRecordArray[0],stringRecordArray[1],stringRecordArray[2],Double.parseDouble(stringRecordArray[3]));
				    			 userList.add(userEntity);
				    			 tempLoginList.add(stringRecordArray[0]);
				    			 tempIdList.add(stringRecordArray[1]);
				    			 checkFile = true;
				    		 }else {
				    			 checkFile = false;
				    			 break;
				    		 }
				    		 
				    	 }
			    	 }
			    	 
			         totalLine++;
			     }
			     
			     Set<String> tempLoginSet = new HashSet<String>(tempLoginList);
			     Set<String> tempIdSet = new HashSet<String>(tempIdList);
			     
			     //check duplicate Login
			     if(tempLoginSet.size() != userList.size()) {
			    	 duplicatedLogin = true;
			     }
			     
			     //check duplicate Id
			     if(tempIdSet.size() != userList.size()) {
			    	 duplicatedId = true;
			     }
			     
			     if(!duplicatedLogin&&!duplicatedId&&checkFile&&!userList.isEmpty()) {
			    		 for(User u: userList) {
			    			 userRepo.save(u);
				    	 }
			    		 result = true;
			    	 
			     }
			  } catch (IOException e) {
			    System.err.println(e.getMessage());       
			  }
		}
		
		
		return result;
	}
	
	public boolean checkRecord(String record) {
		boolean result = false;
		boolean validSalary =false;
		String[] stringRecordArray = record.split(",");
		//Count total tokens
		if(stringRecordArray.length == 4) {
			//Check Salary
			try {
				if(stringRecordArray[3].contains(".")) {
					String[] salaryStringArray = stringRecordArray[3].split("\\.");
					if(salaryStringArray[1].length() <= 2) {
						validSalary = true;
					}
				}else {
					validSalary = true;
				}
				
				if(validSalary) {
					double salary = Double.parseDouble(stringRecordArray[3]);
					if(salary >= 0) {
						result = checkLogin(stringRecordArray[1],stringRecordArray[0]);
					}
				}
				
			}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
		
		return result;
	}
	
	public boolean checkLogin(String loginId,String id) {
		boolean validLogin = false;
		
		User user = userRepo.findBylogin(loginId);
		if(user == null) {
			validLogin = true;
		}else if (user.getId().equals(id)) {
			validLogin = true;
		}
		
		return validLogin;
	}

	@Override
	public List<User> getUsers(Double minSalary, Double maxSalary, Integer offset, Integer limit, String column, int ascii) {
		
		List<User> result = new ArrayList<User>();
		
		
		if(ascii == 43) {
			//Pageable sortedByAsc = 
					  //PageRequest.of(offset, limit, Sort.by(column).ascending());
			Pageable sortedByAsc = new EnhancedPagable(offset.intValue(),limit.intValue(),Sort.by(column).ascending());
			
			result= userRepo.findAllBySalaryGreaterThanEqualAndSalaryLessThanEqual(minSalary,maxSalary,sortedByAsc);
		}else {
			Pageable sortedByDesc = new EnhancedPagable(offset.intValue(),limit.intValue(),Sort.by(column).descending());
			result= userRepo.findAllBySalaryGreaterThanEqualAndSalaryLessThanEqual(minSalary,maxSalary,sortedByDesc);
		}
		
		
		return result;
	}

}
