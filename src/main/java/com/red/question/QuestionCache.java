package com.red.question;

import java.util.List;
import java.util.Map;

public class QuestionCache {

	private static List<Question> allQuestions;
	
	/** Category vs List<Question> */
	private static Map<String, List<Question>> questionPerStream;
	
	/** questionId vs Question*/
	private static Map<Integer, Question> questionById;
	
	
	public static List<Question> getAllQuestions(){
		
		return allQuestions;
	}
	
	public static void setAllQuestions(List<Question> allQuestions){
		QuestionCache.allQuestions = allQuestions;
	}

	public static Map<String, List<Question>> getQuestionPerStream() {
		return questionPerStream;
	}

	public static void setQuestionPerStream(Map<String, List<Question>> questionPerStream) {
		QuestionCache.questionPerStream = questionPerStream;
	}

	public static Map<Integer, Question> getQuestionById() {
		return questionById;
	}

	public static void setQuestionById(Map<Integer, Question> questionById) {
		QuestionCache.questionById = questionById;
	}
}
