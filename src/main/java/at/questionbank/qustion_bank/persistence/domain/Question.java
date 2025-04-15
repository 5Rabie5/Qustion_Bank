package at.questionbank.qustion_bank.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    public int id;
    public String question;
    public String answer_1;
    public String answer_2;
    public String answer_3;
    public String answer_4;
    public String imageUrl_1;
    public String imageUrl_2;
    public String imageUrl_3;
    public String imageUrl_4;
    public int correct_answer;
    public int difficulty;
    public String tag;
    public String category;
    public int type;
    public String sprache;
    public int timeLimit;
    public String source;
    public String tip;

    public int status;
}


//{
//        "id": 0,
//        "question": "",
//        "answer_1": "",
//        "answer_2": "",
//        "answer_3": "",
//        "answer_4": "",
//        "image_url_1": "",
//        "image_url_2": "",
//        "image_url_3": "",
//        "image_url_4": "",
//        "correct_answer": 0,
//        "difficulty": 0,
//        "tag": "",
//        "category": "",
//        "type": 0,
//        "sprache": "",
//        "time_limit": 0,
//        "source": "",
//        "tip": "",
//        "status": "",
//        }
