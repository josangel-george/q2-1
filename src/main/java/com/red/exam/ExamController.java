package com.red.exam;

import java.util.LinkedHashMap;
import java.util.Map;

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
		
		int firstQn = (pageNo - 1) * qnPerPage + 1;
		firstQn = firstQn > qnSize ? qnSize : firstQn;
		
		int lastQn = (pageNo * qnPerPage);
		lastQn = lastQn > qnSize ? qnSize : lastQn;
		
		// slice the questions based on page requested
		for(int i = firstQn; i <= lastQn; i++){
			if(allQns.get(i) != null){
				Question q = questionService.findByQuestionId(allQns.get(i));
				subQns.put(i, q);
			}
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
		model.put("pageCount", 10);		// No of pagination pages to be shown
		
		return "main";
	}
	
	// ===================================================================
	// =========================  REST  ==================================
	// ===================================================================
	
	@RequestMapping("1/saveProgress/{questionNo}/{option}")
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
		
		log.info("Option update: " + candidate.getCandidateId() + " q:" + questionNo + " o" + option);
		
		candidateRepository.save(candidate);
		// user.getAttempts.get(questionNo).set(option);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
