package com.red.candidate;

import static com.red.MainClass.CIVIL;
import static com.red.MainClass.CS;
import static com.red.MainClass.EC;
import static com.red.MainClass.EEE;
import static com.red.MainClass.EI;
import static com.red.MainClass.IT;
import static com.red.MainClass.MECH;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.red.question.QuestionService;

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
	public String home(HttpSession session, RedirectAttributes redir){
		
		String candidateId = candidateService.verifySession(session);
		if(!verifyReturn(candidateId)){
			return "redirect:/register";
		}
		
		return "redirect:/exam/1";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String viewRegisterPage(Candidate candidate, ModelMap model){
		
		Map<String, String> streams = new HashMap<>();
		streams.put(EC, "E&C");
		streams.put(CS, "CS");
		streams.put(EEE, "EEE");
		streams.put(CIVIL, "Civil");
		streams.put(MECH, "Mech");
		streams.put(EI,"E&I");
		streams.put(IT, "IT");
		
		model.put("streams", streams);
		
		return "register";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(HttpSession session, 
			Candidate candidate, RedirectAttributes redirModel,
			HttpServletRequest request){
		
		if(candidate.getOriginalStream().equals(IT)){
			candidate.setStream(CS);
		} 
		else if(candidate.getOriginalStream().equals(EI)){
			candidate.setStream(EC);
		}
		else {
			candidate.setStream(candidate.getOriginalStream());
		}
		
		String loginIp = request.getRemoteAddr();
		
		// check if already a candidate exist with matching info 
		String name = candidate.getName();
		String cId = candidate.getCandidateId();
		
		// check if same id and diff dob
		List<Candidate> c2 = candidateRepository.findByCandidateId(cId);

		if(c2.size() > 0){
			Candidate c3 = c2.get(0);
			if(!c3.getName().equals(name)){
				
				log.info("Invalid Login. User exist, name not matching: " 
															+ candidate.getCandidateId()
															+ "-" + candidate.getName());
				
				String msg = "Id already exist, But name not matching. unable to recover previous progress";
				redirModel.addFlashAttribute("msg", msg);
				redirModel.addFlashAttribute("candidate", candidate);
				return "redirect:/register";
			}
		}

		List<Candidate> candidates = candidateRepository
									.findByNameIgnoreCaseAndCandidateId(name, cId);
		
		if (candidates.size() > 0) {
			
			log.info("User already exist. Loggin in." + candidate.getCandidateId() + " - " + candidate.getName());
			String msg = "User found in DB. Recovering previous progress.";
			redirModel.addFlashAttribute("msg", msg);
			
			Candidate c = candidates.get(0);
			
			// update login timestamps and IP addresses
			List<Date> times = c.getLoginTimes();
			if(times != null){
				times = new ArrayList<>();
			}
			times.add(new Date());
			c.setLoginTimes(times);
			
			List<String> ips = c.getCandidateIPs();
			if(ips != null){
				ips = new ArrayList<>();
			}
			ips.add(loginIp);
			c.setCandidateIPs(ips);
			
			candidateRepository.save(c);
			
		} else {
			
			log.info("New User. Adding to db. " + candidate);
			
			// generate new questionSequence [Map<Integer, Integer> qSeq]
			LinkedHashMap<Integer, Integer> qSeq = questionService.getQuestionSequence(candidate.getStream());
			
			candidate.setActiveStartTime(new Date());
			candidate.setQuestionSequence(qSeq);
			
			// update login timestamps and ip addresses
			List<Date> times = new ArrayList<>();
			times.add(new Date());
			
			List<String> ips = new ArrayList<>();
			ips.add(loginIp);
			
			candidate.setLoginTimes(times);
			candidate.setCandidateIPs(ips);
			
			candidateRepository.save(candidate);
		}
		
		session.setAttribute("candidateName", candidate.getName());
		session.setAttribute("candidateId", candidate.getCandidateId());
		session.setAttribute("candidateStream", candidate.getOriginalStream());
		
		return "redirect:/exam/1";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session, 
			RedirectAttributes redir){
		
		session.removeAttribute("candidateName");
		session.removeAttribute("candidateId");
		session.removeAttribute("candidateStream");
		
		redir.addFlashAttribute("msg", "User Logged out.");
		return "redirect:/";
	}
	
	@RequestMapping("finalize")
	public String finalizePage(HttpSession session,
			RedirectAttributes redir){
		
		String candidateId = candidateService.verifySession(session);
		if(!verifyReturn(candidateId)){
			
			if(candidateId.equals("SESSION_EXPIRED")){
				// allow user through, if user exist and is only invalid due to expiry
			} else {
				redir.addFlashAttribute("msg", "User not valid. Please register. " + candidateId);
				return "redirect:/register";
			}
		}
		
		return "finalize/finalize-confirm";
	}
	
	@RequestMapping(value = "finalize", method = RequestMethod.POST)
	public String finalize(HttpSession session, RedirectAttributes redir,
				@RequestParam String feedback){
		
		System.err.println(feedback);
		String candidateId = (String) session.getAttribute("candidateId");
		if(candidateId == null){
			
			redir.addFlashAttribute("msg", "Invalid User. Please register.");
			return "redirect:/register";
		}
		
		Candidate c = candidateRepository.findByCandidateId(candidateId).get(0);
		
		// get the candidate
		Candidate candidate = candidateRepository.findByCandidateId(c.getCandidateId()).get(0);
		
		candidate.setCompleted(true);
		candidate.setFeedback(feedback);
		
		candidateRepository.save(candidate);
		
		session.removeAttribute("candidateName");
		session.removeAttribute("candidateId");
		session.removeAttribute("candidateStream");
		
		return "finalize/finalized";
	}
	
	/** true if user valid else false */
	private boolean verifyReturn(String msg){
		
		if(msg.equals("NOT_LOGGED_IN") || msg.equals("NOT_FOUND") || msg.equals("SESSION_EXPIRED") 
					|| msg.equals("ALREADY_SUBMITTED")){
			return false;
		} else {
			return true;
		}
	}
}
