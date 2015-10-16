package com.red.candidate;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandidateRepository extends MongoRepository<Candidate, ObjectId>{

	public List<Candidate> findByNameIgnoreCase(String name);
	public List<Candidate> findByNameIgnoreCaseAndCandidateId(String name, String candidateId);
	
	public List<Candidate> findByCandidateId(String candidateId);
}
