package com.red.candidate;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Candidate {

	private ObjectId id;
	
	@Indexed
	private String candidateId;
	
	@Indexed
	private String name;
	
	private String dob;
	private String stream;
	
	private Date activeStartTime;

	private int correctAnswers = 0;
	private Map<String, Integer> correctAnswerPerCategory = new HashMap<>();
	
	/** TreeMap of [questionNo vs questionId]*/
	private LinkedHashMap<Integer, Integer> questionSequence;
	
	private boolean completed;
	
	private List<String> candidateIPs;
	private List<Date> loginTimes;
	
	/** Candidate QuestionNo vs Option Selected*/
	private Map<Integer, String> attempts = new HashMap<>();
	
	@Override
	public String toString() {
		return "Candidate [id=" + id + ", candidateId=" + candidateId + ", name=" + name + ", dob=" + dob + ", stream="
				+ stream + ", activeStartTime=" + activeStartTime + ", correctAnswers=" + correctAnswers
				+ ", correctAnswerPerCategory=" + correctAnswerPerCategory + ", questionSequence=" + questionSequence
				+ ", completed=" + completed + ", candidateIPs=" + candidateIPs + ", loginTimes=" + loginTimes
				+ ", attempts=" + attempts + "]";
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public Map<String, Integer> getCorrectAnswerPerCategory() {
		return correctAnswerPerCategory;
	}

	public void setCorrectAnswerPerCategory(Map<String, Integer> correctAnswerPerCategory) {
		this.correctAnswerPerCategory = correctAnswerPerCategory;
	}

	public LinkedHashMap<Integer, Integer> getQuestionSequence() {
		return questionSequence;
	}

	public void setQuestionSequence(LinkedHashMap<Integer, Integer> questionSequence) {
		this.questionSequence = questionSequence;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Map<Integer, String> getAttempts() {
		return attempts;
	}

	public void setAttempts(Map<Integer, String> attempts) {
		this.attempts = attempts;
	}

	public Date getActiveStartTime() {
		return activeStartTime;
	}

	public void setActiveStartTime(Date activeStartTime) {
		this.activeStartTime = activeStartTime;
	}

	public List<String> getCandidateIPs() {
		return candidateIPs;
	}

	public void setCandidateIPs(List<String> candidateIPs) {
		this.candidateIPs = candidateIPs;
	}

	public List<Date> getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(List<Date> loginTimes) {
		this.loginTimes = loginTimes;
	}
}
