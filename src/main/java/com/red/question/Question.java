package com.red.question;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Question {

	private ObjectId id;

	@Indexed
	private int questionId;
	private String text;
	private List<String> options;
	
	@Indexed
	private String category;     /** EEE, EC, CS, Mechanical, Civil */
	private String answer;
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", questionId=" + questionId + ", text=" + text + ", options=" + options
				+ ", category=" + category + ", answer=" + answer + "]";
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
