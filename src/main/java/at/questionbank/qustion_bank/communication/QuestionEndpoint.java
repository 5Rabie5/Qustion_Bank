package at.questionbank.qustion_bank.communication;

import at.questionbank.qustion_bank.logic.IdIdsAccumulationManager;
import at.questionbank.qustion_bank.logic.QuestionManger;
import at.questionbank.qustion_bank.persistence.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionEndpoint {

    private final QuestionManger questionManger;
    private final IdIdsAccumulationManager idsAccumulationManager;


    @GetMapping
    public List<Question> getQuestions(
            @RequestParam(required = false) String sprache,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) Integer status) {
        return questionManger.getQuestions(sprache, category, tag, type, difficulty, status);
    }
    @PostMapping("/all")
    public void saveAll(@RequestBody Question[] questions) {
        questionManger.saveAll(questions);
    }

    @PostMapping("/single")
    public void saveSingle(@RequestBody Question question) {
        question.setId(idsAccumulationManager.getIdsAccumulationById().getQuestionIdAcc());
        questionManger.save(question);
    }

    @PutMapping("/{id}")
    public Question updateQuestion(@RequestBody Question updated, @PathVariable Integer id) {
        updated.setId(id);
        return questionManger.updatedQuestion(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Integer id) {
        questionManger.deleteById(id);
    }

    @DeleteMapping("/all/kma")
    public void deleteAll() {
        questionManger.deleteAll();
    }
}
