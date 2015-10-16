package com.red.question;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, ObjectId>{

	public Question findByQuestionId(int questionId);
	public List<Question> findByCategory(String category);
}
