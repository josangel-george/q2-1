package com.red.exam;

import static com.red.candidate.CandidateService.SESSION_EXPIRY_TIME;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.red.candidate.Candidate;
import com.red.candidate.CandidateController;
import com.red.candidate.CandidateRepository;
import com.red.candidate.CandidateService;
import com.red.question.Question;
import com.red.question.QuestionService;

@Controller
@RequestMapping("/exam/")
public class ExamController {

	private static final int qnPerPage = 5;
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private QuestionService questionService;
	
	private static final Logger log = LoggerFactory.getLogger(CandidateController.class);

	@RequestMapping("{pageNo}")
	public String examMain(ModelMap model, @PathVariable int pageNo,
			HttpSession session, RedirectAttributes redir){
		
		// find the user from session
		String candidateId = candidateService.verifySession(session);
		if(candidateId.equals("SESSION_EXPIRED")){
			redir.addFlashAttribute("expiryMessage", 
					"Your Session has expired. All your progress have been saved.");
			return "redirect:/finalize";
		}
		if(!verifyReturn(candidateId)){
			redir.addFlashAttribute("msg", "User not valid. Please register. "  + candidateId);
			return "redirect:/register";
		}
		
		Candidate candidate = candidateRepository.findByCandidateId(candidateId).get(0);
		
		// read the questions from session scope
		Map<Integer, Integer> allQns = candidate.getQuestionSequence();
		Map<Integer, Question> subQns = new LinkedHashMap<>();
		
		int qnSize = allQns.size();
		
		// first question in requested page
		int firstQn = (pageNo - 1) * qnPerPage + 1;
		firstQn = firstQn > qnSize ? qnSize : firstQn;
		
		// last question in requested page
		int lastQn = (pageNo * qnPerPage);
		lastQn = lastQn > qnSize ? qnSize : lastQn;
		
		// slice the questions based on page requested
		for(int i = firstQn; i <= lastQn; i++){
			if(allQns.get(i) != null){
				Question q = questionService.findByQuestionId(allQns.get(i));
				subQns.put(i, q);
			}
		}
		
		// loop to find attempt summary
		Map<Integer, String> attemptMap = candidate.getAttempts();
		Map<Integer, String> attemptSlice = new TreeMap<>();
		for(int i = firstQn; i <= lastQn; i++){
			
			String ans = attemptMap.get(i);
			attemptSlice.put(i, getStrOption(ans));
		}
		
		String msg = new StringBuilder().append("Inside ").append(pageNo)
										.append(" page. Candidate: ")
										.append(candidate.getCandidateId())
										.append("Showing ").append(firstQn)
										.append(" to ").append(lastQn)
										.append("of").append(allQns.size())
										.toString();
		log.info(msg);
		
		Instant expirtyInstant = candidate.getActiveStartTime().toInstant().plusMillis(SESSION_EXPIRY_TIME);
		Date expiryDate = Date.from(expirtyInstant);
		String expirtyTime = new SimpleDateFormat("HH:mm:ss").format(expiryDate);
		String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
		
		// send questions to user
		model.put("questions", subQns);
		model.put("currentPage", pageNo);
		model.put("attemptSlice", attemptSlice);
		model.put("expiryTime", expirtyTime);
		model.put("currentTime", currentTime);
		model.put("optionSelected", candidate.getAttempts());
		model.put("pageCount", 10);		// No of pagination pages to be shown
		
		return "main";
	}
	
	// ===================================================================
	// =========================  REST  ==================================
	// ===================================================================
	
	@RequestMapping("/saveProgress/{questionNo}/{option}")
	public ResponseEntity<Void> saveProgreess(@PathVariable int questionNo,
					@PathVariable String option,
					HttpSession session){
		
		// find the user from session
		String candidateId = candidateService.verifySession(session);
		if(!verifyReturn(candidateId)){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Candidate candidate = candidateRepository.findByCandidateId(candidateId).get(0);
		
		// Integer qId = candidateService.getQuestionId(questionNo, candidate);
		// Question q = questionService.findByQuestionId(qId);
		
		int questionId = candidateService.getQuestionId(questionNo, candidate);
		Question q = questionService.findByQuestionId(questionId);
		
		Candidate resultCandidate = evaluateStats(candidate, questionNo, option, q);
		
		resultCandidate.getAttempts().put(questionNo, option);
		
		String msg = "Option update: " + resultCandidate.getCandidateId();
		msg += " q:" + questionNo + " o: " + option;
		msg += " correctAns:" + resultCandidate.getCorrectAnswers(); 
		msg += " correctPerCat" + resultCandidate.getCorrectAnswerPerCategory();		
		log.trace(msg);
		
		candidateRepository.save(resultCandidate);
		// user.getAttempts.get(questionNo).set(option);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private Candidate evaluateStats(Candidate candidate, int questionNo, String option, Question q) {

		String prevAns = getStrOption(candidate.getAttempts().get(questionNo)); 
		String newAns = getStrOption(option);
		String correctAns = q.getAnswer();
		String category = q.getCategory();
		
		log.trace(prevAns + "-" + newAns + "-" + correctAns + "-" +  category);
		
		if(prevAns == null & newAns.equals(correctAns)){
			// increment
			candidate.setCorrectAnswers(candidate.getCorrectAnswers() + 1);
			
			Integer prevCount = candidate.getCorrectAnswerPerCategory().get(category);
			if(prevCount == null){prevCount = 0;}
			candidate.getCorrectAnswerPerCategory()
				.put(category, prevCount + 1);
			
		} else if(prevAns == null && !newAns.equals(correctAns)){
			// do nothing
		} else if(prevAns != null && newAns.equals(correctAns)){
			if(prevAns.equals(correctAns)){
				//do nothing
			} else {
				// increment
				candidate.setCorrectAnswers(candidate.getCorrectAnswers() + 1);
				
				Integer prevCount = candidate.getCorrectAnswerPerCategory().get(category);
				if(prevCount == null){prevCount = 0;}
				candidate.getCorrectAnswerPerCategory()
					.put(category, prevCount + 1);
				
			}
		} else if(prevAns != null && !newAns.equals(correctAns)){
			
			if(prevAns.equals(correctAns)){
				// decrement
				candidate.setCorrectAnswers(candidate.getCorrectAnswers() - 1);
				
				Integer prevCount = candidate.getCorrectAnswerPerCategory().get(category);
				if(prevCount == null){prevCount = 0;}
				candidate.getCorrectAnswerPerCategory()
					.put(category, prevCount - 1);
				
			} else {
				// do nothing
			}
		}
		
		return candidate;
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
	
	private boolean verifyReturn(String msg){
		
		if(msg.equals("NULL") || msg.equals("NOT_FOUND") || msg.equals("SESSION_EXPIRED") 
					|| msg.equals("ALREADY_SUBMITTED")){
			return false;
		} else {
			return true;
		}
	}
}
