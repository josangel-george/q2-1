package com.red.admin;

import static com.red.MainClass.APTI;
import static com.red.MainClass.GK;
import static com.red.admin.AdminService.ADMIN;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.red.candidate.Candidate;
import com.red.candidate.CandidateRepository;
import com.red.question.QuestionService;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private QuestionService questionService;
	
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
		
		if(userId.equals("red") && pass.equals("ilayathalapathi")){
			
			session.setAttribute("adminUser", ADMIN);
			log.info("Login Success.");
			return "redirect:/admin/home";
		} else {
			
			log.info("Login Failure.");
			return "redirect:/admin/login";
		}
	}

	@RequestMapping(value = "home")
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

			sb.append(addBasicStats(c)).append("<br>");
		}
		
		model.put("stats", sb.toString());
		return "admin/home";
	}
	
	
	@RequestMapping(value ="stats")
	public String adminHome(HttpSession session, ModelMap model){
		
		if(!adminService.verifyAdmin(session)){
			log.info("Not Logged in, redirecting to login");
			return "redirect:/admin/login";
		}
		
		List<Candidate> candidates = candidateRepository.findAll();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Candidate ID,Name,DOB[yyyy-mm-dd],Stream,")
			.append("Correct Answers,APTI,GK,Stream,")
			.append("Total Attempt,Apti Attemp,GK Attempt,Stream Attempt,Answers [qId vs T/F]");
		
		sb.append("<br>");
		
		for(Candidate c: candidates){

			sb.append(addBasicStats(c))
				.append(addAdvancedStats(c))
				.append("<br>");
		}
		
		model.put("stats", sb.toString());
		return "admin/stats";
	}

	private String addBasicStats(Candidate c){
		
		StringBuilder sb = new StringBuilder();
		
		int noOfAttmpt = c.getAttempts().size();
		int aptiAttempt = 0;
		int gkAttempt = 0;
		int streamAttempt = 0;
		
		Set<Integer> attempts = c.getAttempts().keySet();
		
		for(Integer i: attempts){
			if(i >= 1 && i <= 25){
				aptiAttempt++;
			} else if(i >= 26 && i <= 30){
				gkAttempt++;
			} else if(i >= 31){
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
			.append(streamAttempt);
		
		return sb.toString();
	}
	
	private String addAdvancedStats(Candidate c) {
		
		StringBuilder sb = new StringBuilder().append(",");
		
		c.getQuestionSequence();
		for(Integer qNo: c.getAttempts().keySet()){
			System.out.println(qNo);
			
			int questionId = c.getQuestionSequence().get(qNo); 
			String answer = getStrOption(c.getAttempts().get(qNo));
			String correctAns = questionService.getCorrectAnswer(questionId);
			boolean ansCorrect = correctAns.equals(answer);

			sb.append(" [").append(questionId).append("-")
				.append(correctAns).append("-")
				.append(ansCorrect).append("] ");
		}
		
		return sb.toString();
	}
	
	@RequestMapping("download")
	public ResponseEntity<Void> downloadStats(HttpSession session,
			HttpServletResponse response){
		
		if(!adminService.verifyAdmin(session)){
			log.info("Not Logged in, redirecting to login");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		List<Candidate> candidates = candidateRepository.findAll();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Candidate ID,Name,DOB[yyyy-mm-dd],Stream,")
			.append("Correct Answers,APTI,GK,Stream,")
			.append("Total Attempt,Apti Attemp,GK Attempt,Stream Attempt,Answers [qId vs T/F]");
		
		sb.append("\r\n");
		
		for(Candidate c: candidates){

			sb.append(addBasicStats(c))
				.append(addAdvancedStats(c))
				.append("\r\n");
		}
		
		String data = sb.toString();
		
        // Send file
		String date = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss").format(new Date());
		String fileName = "Stats-" + date + ".csv";
		
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        
        response.setContentType("text/csv");
        response.setHeader(headerKey, headerValue);
		
        try {
			FileCopyUtils.copy(data.getBytes(), response.getOutputStream());
		} catch (IOException e) {
			System.err.println("File download failed. Bkup: " + data);
			log.error("File download failed. Bkup: " + data);
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping("logout")
	public String adminLogout(HttpSession session){
		
		session.removeAttribute("adminUser");
		return "redirect:/admin/login";
	}
	
	// ------------------------ AUX
	/**
	 * Convert Numeric Options to String options
	 * */
	private String getStrOption(String option){
		
		if(option == null){
			return "-";
		}
		
		if(option.equals("1")){
			return "A";
		} else if(option.equals("2")){
			return "B";
		} else if(option.equals("3")){
			return "C";
		} else if(option.equals("4")){
			return "D";
		} else if(option.equals("5")){
			return "E";
		} else {
			return "-";
		}
	}
}
