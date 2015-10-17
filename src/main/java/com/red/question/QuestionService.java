package com.red.question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class QuestionService {

	private static final int APTI_QN_COUNT = 25;
	private static final int GK_QN_COUNT = 5;
	private static final int STREAM_QN_COUNT = 20;
	/** 
	 * Get Question Sequence for new Candidate.
	 *  
	 * QuestionNo vs Question
	 * */
	public LinkedHashMap<Integer, Integer> getQuestionSequence(String string){
		
		// get the questions from DB
		Map<String, List<Question>> qPerCat = QuestionCache.getQuestionPerStream();
		
		List<Question> aptitudeQuestions = new ArrayList<>(qPerCat.get("Aptitude"));
		List<Question> gkQuestions = new ArrayList<>(qPerCat.get("GK"));
		List<Question> streamQuestions = new ArrayList<>(qPerCat.get(string));

		Collections.shuffle(aptitudeQuestions);
		Collections.shuffle(gkQuestions);
		Collections.shuffle(streamQuestions);
		
		LinkedHashMap<Integer, Integer> questionSequence = new LinkedHashMap<>();
		
		int qnCount = 1;
		for(int i = 0; i < APTI_QN_COUNT; i++){
			
			if(i >= aptitudeQuestions.size()) {break;}
			Question q = aptitudeQuestions.get(i);
			
			questionSequence.put(qnCount, q.getQuestionId());
			qnCount++;
		}
		
		for(int i= 0; i < GK_QN_COUNT; i++){
			
			if(i >= gkQuestions.size()) {break;}
			Question q = gkQuestions.get(i);
			
			questionSequence.put(qnCount, q.getQuestionId());
			qnCount++;
		}
		
		for(int i = 0; i < STREAM_QN_COUNT; i++){
			
			if(i >= streamQuestions.size()) {break;}
			Question q = streamQuestions.get(i);
			
			questionSequence.put(qnCount, q.getQuestionId());
			qnCount++;
		}
		
		System.out.println("User created: stream: " + string + " -" + questionSequence);
		
		return questionSequence;
	}

	public Question findByQuestionId(Integer qId) {

		return QuestionCache.getQuestionById().get(qId);
	}

	public String getCorrectAnswer(int questionId) {

		return QuestionCache.getQuestionById().get(questionId).getAnswer();
	}
}
