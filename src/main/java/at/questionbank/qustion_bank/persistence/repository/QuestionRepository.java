package at.questionbank.qustion_bank.persistence.repository;

import at.questionbank.qustion_bank.persistence.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findAllBySpracheIgnoreCase(String sprache);

    Question findById(Integer searchId);

    void deleteById(Integer id);
}
