package at.questionbank.qustion_bank.logic;

import at.questionbank.qustion_bank.persistence.domain.Question;
import at.questionbank.qustion_bank.persistence.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionManger {

    private final QuestionRepository questionRepository;

    public List<Question> getQuestions(
            String sprache,
            String category,
            String tag,
            Integer type,
            Integer difficulty,
            Integer timeLimit,
            Boolean shortOnly) {

        System.out.println("Filter received -> sprache=" + sprache + ", category=" + category + ", type=" + type + ", difficulty=" + difficulty);

        List<Question> allQuestions = questionRepository.findAll();
        List<Question> filtered = new ArrayList<>();

        for (Question q : allQuestions) {
            boolean match = true;

            if (sprache != null && !q.getSprache().equalsIgnoreCase(sprache)) {
                match = false;
            }
            if (category != null && !q.getCategory().equalsIgnoreCase(category)) {
                match = false;
            }
            if (tag != null && (q.getTag() == null || !q.getTag().equalsIgnoreCase(tag))) {
                match = false;
            }
            if (type != null && q.getType() != type) {
                match = false;
            }
            if (difficulty != null && q.getDifficulty() != difficulty) {
                match = false;
            }
            if (timeLimit != null && q.getTimeLimit() != timeLimit) {
                match = false;
            }
            if (shortOnly != null && shortOnly) {
                if (q.getQuestion() == null || q.getQuestion().split("\\n").length > 2) {
                    match = false;
                }
            }

            if (match) {
                filtered.add(q);
            }
        }

        return filtered;
    }




    public Question save(Question question) {
        return questionRepository.save(question);
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
        Question existingQuestion = questionRepository.findById(uQuestion.getId())
                .orElseThrow(() -> new NoSuchElementException("Question not found with ID: " + uQuestion.getId()));

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

        return this.save(existingQuestion);
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) setter.accept(value);
    }
}
