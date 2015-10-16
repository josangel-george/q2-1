package com.red.exam;

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

import com.red.SessionExpiredException;
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
			HttpSession session) throws SessionExpiredException{
		
		// find the user from session
		Candidate candidate = candidateService.verifySession(session);
		
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
		
		// send questions to user
		model.put("questions", subQns);
		model.put("currentPage", pageNo);
		model.put("attemptSlice", attemptSlice);
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
					HttpSession session) throws SessionExpiredException{
		
		// find the user from session
		Candidate candidate = candidateService.verifySession(session);
		
		// Integer qId = candidateService.getQuestionId(questionNo, candidate);
		// Question q = questionService.findByQuestionId(qId);
		
		candidate.getAttempts().put(questionNo, option);
		// do lot of other things
		// - update correctAnswer#
		// - update correctAnswerPerCategory;
		// - update attempts
		
		String msg = "Option update: " + candidate.getCandidateId();
		msg += " q:" + questionNo + " o" + option;
		msg += "User: " + candidate.getCandidateId();
		
		log.info(msg);
		
		candidateRepository.save(candidate);
		// user.getAttempts.get(questionNo).set(option);
		
		return new ResponseEntity<>(HttpStatus.OK);
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