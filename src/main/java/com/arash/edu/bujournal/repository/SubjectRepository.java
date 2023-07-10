package com.arash.edu.bujournal.repository;

import com.arash.edu.bujournal.domain.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface SubjectRepository extends MongoRepository<Subject, UUID> {

    List<Subject> findAllByGroupId(UUID groupId);
}
