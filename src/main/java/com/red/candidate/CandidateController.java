package com.red.candidate;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.red.SessionExpiredException;
import com.red.question.QuestionService;

import static com.red.MainClass.EEE;
import static com.red.MainClass.CS;
import static com.red.MainClass.EC;
import static com.red.MainClass.CIVIL;
import static com.red.MainClass.MECH;

@Controller
@RequestMapping("/")
public class CandidateController {
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private QuestionService questionService;
	
	private static final Logger log = LoggerFactory.getLogger(CandidateController.class);
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(HttpSession session) throws SessionExpiredException{
		
		candidateService.verifySession(session);
		
		return "redirect:/exam/1";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String viewRegisterPage(Candidate candidate, ModelMap model){
		
		Map<String, String> streams = new HashMap<>();
		streams.put(EC, "E&C");
		streams.put(CS, "CS");
		streams.put(EEE, "EEE");
		streams.put(CIVIL, "Civil");
		streams.put(MECH, "Mechanical");
		
		model.put("streams", streams);
		
		return "register";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(HttpSession session, 
			Candidate candidate, RedirectAttributes redirModel){
		
		System.out.println(candidate);
		
		// check if already a candidate exist with 
		// matching info
		// 
		String name = candidate.getName();
		String cId = candidate.getCandidateId();
		
		List<Candidate> candidates = candidateRepository
									.findByNameIgnoreCaseAndCandidateId(name, cId);
		
		if (candidates.size() > 0) {
			
			log.info("User already exist. Not adding a new user. Loggin in.");
			String msg = "User found in db. Recovering previous progress.";
			redirModel.addFlashAttribute("msg", msg);
		} else {
			
			log.info("User is new. Adding new user to db.");
			
			// generate new questionSequence [Map<Integer, Integer> qSeq]
			LinkedHashMap<Integer, Integer> qSeq = questionService.getQuestionSequence(candidate.getStream());
			
			candidate.setActiveStartTime(new Date());
			candidate.setQuestionSequence(qSeq);
			
			candidateRepository.save(candidate);
		}
		
		session.setAttribute("candidateName", candidate.getName());
		session.setAttribute("candidateId", candidate.getCandidateId());
		session.setAttribute("candidateStream", candidate.getStream());
		
		return "redirect:/exam/1";
	}
	
	@RequestMapping("/logout")
	public String logout(){
		
		return "redirect:/";
	}
}
