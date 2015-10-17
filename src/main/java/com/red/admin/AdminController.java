package com.red.admin;

import static com.red.MainClass.APTI;
import static com.red.MainClass.GK;
import static com.red.MainClass.MAX_QNS;
import static com.red.admin.AdminService.ADMIN;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.red.candidate.Candidate;
import com.red.candidate.CandidateRepository;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private AdminService adminService;
	
	private static final Logger log = LoggerFactory.getLogger(CandidateRepository.class);

	@RequestMapping(value = "login")
	public String adminLoginPage(Admin admin){
		
		return "admin/login";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String adminLogin(Admin admin, HttpSession session){
		
		String userId = admin.getUserId();
		String pass = admin.getPass();
		
		log.info("Login Attempt: " + userId + "-" + pass.length());
		
		if(userId.equals("admin") && pass.equals("pass")){
			
			session.setAttribute("adminUser", ADMIN);
			log.info("Login Success.");
			return "redirect:/admin/home";
		} else {
			
			log.info("Login Failure.");
			return "redirect:/admin/login";
		}
	}
	
	@RequestMapping(value ="home")
	public String adminHome(HttpSession session){
		
		if(!adminService.verifyAdmin(session)){
			log.info("Not Logged in, redirecting to login");
			return "redirect:/admin/login";
		}
		
		return "admin/home";
	}
	

	@RequestMapping(value = "stats")
	public String getStats(HttpSession session, ModelMap model){
		
		if(!adminService.verifyAdmin(session)){
			log.info("Not Logged in, redirecting to login");
			return "redirect:/admin/login";
		}
		
		List<Candidate> candidates = candidateRepository.findAll();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Candidate ID,Name,DOB[yyyy-mm-dd],Stream,")
			.append("Correct Answers,APTI,GK,Stream,")
			.append("Total Attempt,Apti Attemp,GK Attempt,Stream Attempt")
			.append("<br>");
		
		for(Candidate c: candidates){

			int noOfAttmpt = c.getAttempts().size();
			int aptiAttempt = 0;
			int gkAttempt = 0;
			int streamAttempt = 0;
			
			Set<Integer> attempts = c.getAttempts().keySet();
			
			for(Integer i: attempts){
				if(i > 1 && i <= 25){
					aptiAttempt++;
				} else if(i > 26 && i <= 30){
					gkAttempt++;
				} else if(i > 31){
					streamAttempt++;
				}
			}
			
			sb.append(c.getCandidateId()).append(",")
				.append(c.getName()).append(",")
				.append(c.getDob()).append(",")
				.append(c.getStream()).append(",")
				.append(c.getCorrectAnswers()).append(",")
				.append(c.getCorrectAnswerPerCategory().get(APTI)).append(",")
				.append(c.getCorrectAnswerPerCategory().get(GK)).append(",")
				.append(c.getCorrectAnswerPerCategory().get(c.getStream())).append(",")
				.append(noOfAttmpt).append(",")
				.append(aptiAttempt).append(",")
				.append(gkAttempt).append(",")
				.append(streamAttempt)
				.append("<br>");
				
		}
		
		model.put("stats", sb.toString());
		return "admin/stats";
	}
	
	@RequestMapping("logout")
	public String adminLogout(HttpSession session){
		
		session.removeAttribute("adminUser");
		return "redirect:/admin/login";
	}
}
