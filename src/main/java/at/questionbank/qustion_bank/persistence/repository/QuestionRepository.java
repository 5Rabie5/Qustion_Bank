package at.questionbank.qustion_bank.persistence.repository;

import at.questionbank.qustion_bank.persistence.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findAllBySpracheIgnoreCase(String sprache);

    Optional<Question> findById(Integer id); // 👈 this is important!


    void deleteById(Integer id);
}
