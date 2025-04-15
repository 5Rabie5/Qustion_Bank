package at.questionbank.qustion_bank.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdsAccumulation {
    public int id;
    public int buchIdAcc;
    public int blogIdAcc;
    public int authorIdAcc;
    public int synaxariumIdAcc;
    public int questionIdAcc;
}