package at.questionbank.qustion_bank.logic;


import at.questionbank.qustion_bank.persistence.domain.Question;
import at.questionbank.qustion_bank.persistence.repository.QuestionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@Service
@RequiredArgsConstructor
@Setter
@Getter
public class QuestionManger {
    private final QuestionRepository questionRepository;

//    public List<Question> getQusetion(String sprache, String category, String tag, String type, int difficulty){

//        return questionRepository.findAllBySpracheIgnoreCase(sprache).stream()
//                .filter(e -> e.category.equals(category))
//                .filter(e -> e.tag.equals(tag))
//                .filter(e -> e.type.equals(type))
//                .filter(e -> e.difficulty == difficulty)
//                .collect(Collectors.toList());
//    }
    public List<Question> getQuestions(String sprache, String category, String tag, Integer type, Integer difficulty,
                                       Integer status) {
        return questionRepository.findAll().stream()
                .filter(e -> sprache == null || e.getSprache().equalsIgnoreCase(sprache))
                .filter(e -> category == null || e.getCategory().equalsIgnoreCase(category))
                .filter(e -> tag == null || e.getTag().equalsIgnoreCase(tag))
                .filter(e -> type == null || e.getType() == type)
                .filter(e -> difficulty == null || e.getDifficulty() == difficulty)
                .filter(e -> status == null || e.getStatus() == status)
                .collect(Collectors.toList());
    }


    public Question save(Question question) {
        questionRepository.save(question);
        return question;
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }

    public void deleteById(Integer id) {
        questionRepository.deleteById(id);
    }

    public void saveAll(Question[] questions) {
        questionRepository.saveAll(Arrays.asList(questions));
    }

    public Question updatedQuestion(Question uQuestion) {
        Question existingQuestion = questionRepository.findById(uQuestion.getId());

        if (existingQuestion == null || uQuestion.getId() == 0) {
            throw new NoSuchElementException("Question not found with ID: " + uQuestion.getId());
        }
        updateIfNotNull(existingQuestion::setQuestion, uQuestion.getQuestion());
        updateIfNotNull(existingQuestion::setAnswer_1, uQuestion.getAnswer_1());
        updateIfNotNull(existingQuestion::setAnswer_2, uQuestion.getAnswer_2());
        updateIfNotNull(existingQuestion::setAnswer_3, uQuestion.getAnswer_3());
        updateIfNotNull(existingQuestion::setAnswer_4, uQuestion.getAnswer_4());
        updateIfNotNull(existingQuestion::setImageUrl_1, uQuestion.getImageUrl_1());
        updateIfNotNull(existingQuestion::setImageUrl_2, uQuestion.getImageUrl_2());
        updateIfNotNull(existingQuestion::setImageUrl_3, uQuestion.getImageUrl_3());
        updateIfNotNull(existingQuestion::setImageUrl_4, uQuestion.getImageUrl_4());
        updateIfNotNull(existingQuestion::setSprache, uQuestion.getSprache());
        updateIfNotNull(existingQuestion::setCorrect_answer, uQuestion.getCorrect_answer());
        updateIfNotNull(existingQuestion::setCategory, uQuestion.getCategory());
        updateIfNotNull(existingQuestion::setDifficulty, uQuestion.getDifficulty());
        updateIfNotNull(existingQuestion::setTag, uQuestion.getTag());
        updateIfNotNull(existingQuestion::setType, uQuestion.getType());
        updateIfNotNull(existingQuestion::setTip, uQuestion.getTip());
        updateIfNotNull(existingQuestion::setSource, uQuestion.getSource());
        updateIfNotNull(existingQuestion::setTimeLimit, uQuestion.getTimeLimit());
        updateIfNotNull(existingQuestion::setStatus, uQuestion.getStatus());
        this.save(existingQuestion);
        return existingQuestion;
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

    public void dellAll() {
        questionRepository.deleteAll();
    }
}




