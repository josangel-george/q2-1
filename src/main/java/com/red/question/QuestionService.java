package com.red.question;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class QuestionService {

	/** 
	 * Get Question Sequence for new Candidate.
	 *  
	 * QuestionNo vs Question
	 * */
	public LinkedHashMap<Integer, Integer> getQuestionSequence(String string){
		
		// get the questions from DB
		Map<String, List<Question>> qPerCat = QuestionCache.getQuestionPerStream();
		
		List<Question> aptitudeQuestions = qPerCat.get("Aptitude");
		List<Question> gkQuestions = qPerCat.get("GK");
		List<Question> streamQuestions = qPerCat.get(string);
		
		LinkedHashMap<Integer, Integer> questionSequence = new LinkedHashMap<>();
		
		int qnCount = 1;
		for(Question q: aptitudeQuestions){
			
			questionSequence.put(qnCount, q.getQuestionId());
			qnCount++;
		}
		
		for(Question q: gkQuestions){
			
			questionSequence.put(qnCount, q.getQuestionId());
			qnCount++;
		}
		
		for(Question q: streamQuestions){
			
			questionSequence.put(qnCount, q.getQuestionId());
			qnCount++;
		}
		
		return questionSequence;
	}

	public Question findByQuestionId(Integer qId) {

		return QuestionCache.getQuestionById().get(qId);
	}
}
