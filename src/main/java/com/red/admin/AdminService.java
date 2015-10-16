package com.red.admin;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

	public static final String ADMIN = "Admin";
	
	public boolean verifyAdmin(HttpSession session) {

		String adminUser = (String) session.getAttribute("adminUser");
		
		if(adminUser == null){
			return false;
		}
		
		return adminUser.equals(ADMIN);
	}
}
