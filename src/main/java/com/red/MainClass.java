package com.red;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.red.candidate.CandidateRepository;
import com.red.question.Question;
import com.red.question.QuestionCache;
import com.red.question.QuestionRepository;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages="com.red")
@EnableMongoRepositories("com.red")
public class MainClass {
	
	public static final String APTI = "Aptitude";	// 1000
	public static final String GK = "GK";			// 2000
	public static final String CS = "CS";			// 3000
	public static final String EC = "EC";			// 4000
	public static final String EEE = "EEE";			// 5000
	public static final String CIVIL = "Civil";		// 6000
	public static final String MECH = "Mechanical";	// 7000
	public static final String IT = "IT";		// Alias of CS - 3000
	public static final String EI = "EI";		// Alias of EC - 4000
	
	public static final int MAX_QNS = 50;
	public static final long SESSION_EXPIRY_TIME = 50 * 60 * 1_000;  // 50 min in ms

    public static void main(String[] args) {
        ApplicationContext ctx = 
        			SpringApplication.run(MainClass.class, args);
        
        QuestionRepository qRepo = ctx.getBean("questionRepository", QuestionRepository.class);
        
        /** Populate the allQuestions field in Question Cache */
        populateQuestionCache(qRepo);
        
        CandidateRepository cRepo = ctx.getBean("candidateRepository", CandidateRepository.class);
        System.err.println("\nCandidate Count: " + cRepo.findAll().size());
        
        Scanner in = new Scanner(System.in);
        
        boolean status = true;
        while(status){
        	if(in.nextLine().equals("C")){
        		populateQuestionCache(qRepo);
        		System.err.println("\nQuestions re-added to cache.");
        	}
        }
        in.close();
    }
    
    private static void populateQuestionCache(QuestionRepository qRepo) {
    	
    	QuestionCache.setCacheLock(true);

    	List<Question> allQuestions = qRepo.findAll();
    	
    	// ------------------- Questions per category
    	 /** EEE, EC, CS, Mechanical, Civil */
    	Map<String, List<Question>> questionPerStream = new HashMap<>();
    	
    	questionPerStream.put(APTI, qRepo.findByCategory(APTI));
    	questionPerStream.put(GK, qRepo.findByCategory(GK));
    	questionPerStream.put(EEE, qRepo.findByCategory(EEE));
    	questionPerStream.put(EC, qRepo.findByCategory(EC));
    	questionPerStream.put(CS, qRepo.findByCategory(CS));
    	questionPerStream.put(MECH, qRepo.findByCategory(MECH));
    	questionPerStream.put(CIVIL, qRepo.findByCategory(CIVIL));
    	
    	System.err.println("Aptitude Questions: " + questionPerStream.get(APTI).size());
    	System.err.println("GK Questions: " + questionPerStream.get(GK).size());
    	System.err.println("EC Questions: " + questionPerStream.get(EC).size());
    	System.err.println("CS Questions: " + questionPerStream.get(CS).size());
    	System.err.println("EEE Questions: " + questionPerStream.get(EEE).size());
    	System.err.println("Civil Questions: " + questionPerStream.get(CIVIL).size());
    	System.err.println("Mechanical Questions: " + questionPerStream.get(MECH).size());
    	
    	// ------------------- Question By Id
    	Map<Integer, Question> questionById = new HashMap<>();
    	for(Question q: allQuestions){
    		questionById.put(q.getQuestionId(), q);
    	}
    	
    	QuestionCache.setAllQuestions(allQuestions);
    	QuestionCache.setQuestionPerStream(questionPerStream);
    	QuestionCache.setQuestionById(questionById);
    	
    	QuestionCache.setCacheLock(false);
	}
    
	public static void populateTestQuestions(QuestionRepository qRepo){
    	
		if(qRepo.findAll().size() > 0){
			return;
		}
		
    	for(int i=1; i <= 25; i++){
    		
        	Question q = new Question();
        	q.setText(i + " Which amoung is a bird.");
        	q.setOptions(Arrays.asList("Man", "Deer", "Horse", "Hen"));
        	q.setAnswer("D");
        	q.setImage("");
        	
    		q.setQuestionId(i);
    		q.setCategory(APTI);
    		qRepo.save(q);
    	}
    	
    	for(int i=26; i <= 30; i++){
    		
        	Question q = new Question();
        	q.setText(i + " Which amoung is a bird.");
        	q.setOptions(Arrays.asList("Man", "Deer", "Horse", "Hen"));
        	q.setAnswer("D");
        	q.setImage("");
        	
    		q.setQuestionId(i);
    		q.setCategory(GK);
    		qRepo.save(q);
    	}
    	
    	for(int i=31; i <= 50; i++){
    		
        	Question q = new Question();
        	q.setText(i + " > Which <pre>amoung</pre> is a bird.");
        	q.setOptions(Arrays.asList("Man", "Deer", "Horse", "Hen"));
        	q.setAnswer("D");
        	q.setImage("");
        	
    		q.setQuestionId(i);
    		q.setCategory(CS);
    		qRepo.save(q);
    	}
    	
    	System.err.println("Added items to question db: " + qRepo.findAll().size());
    }
}
