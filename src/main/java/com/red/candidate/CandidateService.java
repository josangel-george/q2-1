package com.red.candidate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.question.Question;
import com.red.question.QuestionRepository;

@Service
public class CandidateService {
	
	 public static final long SESSION_EXPIRY_TIME = 50 * 60 * 1_000;  // 50 min in ms

	@Autowired
	private CandidateRepository candidateRepository; 
	
	@Autowired
	private QuestionRepository questionRepository;
	
	// get questions assigned to user
	// get the answers given by user along with corresponding questions
	// get the number of correct answers given by user
	// get the correct or incorrect answers given by user against category/apptempted?
	// 
	
	public List<Question> userQuestions(Map<Integer, Integer> qSeq){
		
		List<Question> questionsForUser = new ArrayList<Question>();
		
		for(Map.Entry<Integer, Integer> q: qSeq.entrySet()){
			
			Question question = questionRepository.findByQuestionId(q.getKey());
			questionsForUser.add(question);
		}
		
		return questionsForUser;
	}
	
	/** Get questionId for given questionNo for given candidate */
	public Integer getQuestionId(int questionNo, Candidate candidate){
		
		return candidate.getQuestionSequence().get(questionNo);
	}
	

	/**
	 * @return Currently Logged in candidate.
	 * @throws Exception if no valid session exist
	 * */
	public String verifySession(HttpSession session){
	
		String candidateId = (String) session.getAttribute("candidateId");
		
		// if no user in session
		if(candidateId == null){
			return "NULL";
		}
		
		// If no user in DB which matches that in session!
		List<Candidate> candidates = candidateRepository.findByCandidateId(candidateId);
		
		if(candidates.size() <= 0){

			return "NOT_FOUND";
		}
		
		Candidate candidate = candidates.get(0);
		Long startTime = candidate.getActiveStartTime().getTime();
		
		// If user session expired
		if(new Date().getTime() - startTime > SESSION_EXPIRY_TIME){	// 50 mins

			return "SESSION_EXPIRED";
		} 

		// If user has already finalized his session
		if(candidate.isCompleted()){
			
			return "ALREADY_SUBMITTED";
		}
		
		return candidate.getCandidateId();
	}
}
