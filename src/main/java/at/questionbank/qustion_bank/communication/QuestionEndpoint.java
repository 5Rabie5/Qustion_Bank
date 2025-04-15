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

//    @GetMapping("/{lang}")
//    List<Question> getQuestion(@PathVariable String sprache,@PathVariable String category,@PathVariable String tag,
//                               @PathVariable String type,@PathVariable  int difficulty) {
//        return questionManger.getQusetion(sprache, category, tag, type, difficulty);
//    }

    @GetMapping("/questions")
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
    void post(@RequestBody Question[] questions) {
        questionManger.saveAll(questions);
    }

    @DeleteMapping("/{id}")
    void deleteQuestion(@PathVariable Integer id) {
        questionManger.deleteById(id);
    }

    @PostMapping
    void post(@RequestBody Question question) {
        question.id = idsAccumulationManager.getIdsAccumulationById().questionIdAcc;
        questionManger.save(question);
    }

    @PutMapping("/{id}")
    Question updateBlog(@RequestBody Question newQuestion, @PathVariable Integer id) {
        newQuestion.setId(id);
        return questionManger.updatedQuestion(newQuestion);
    }

    @DeleteMapping("/all/kma")
    void deletall() {
        questionManger.dellAll();
    }
}
