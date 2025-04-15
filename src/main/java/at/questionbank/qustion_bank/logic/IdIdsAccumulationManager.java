package at.questionbank.qustion_bank.logic;

import at.questionbank.qustion_bank.persistence.domain.IdsAccumulation;
import at.questionbank.qustion_bank.persistence.repository.IdsAccumulationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
@RequiredArgsConstructor
@Setter
@Getter
public class IdIdsAccumulationManager {
    private final IdsAccumulationRepository idAccumulationRepository;

    public List<IdsAccumulation> getIdsAccumulationAll() {
        return idAccumulationRepository.findAll();
    }

    public IdsAccumulation getIdsAccumulationById() {
        return idAccumulationRepository.findById(1);
    }

    public IdsAccumulation save(IdsAccumulation idsAccumulation) {
        idAccumulationRepository.save(idsAccumulation);
        return idsAccumulation;
    }

    public int getIdsAccumulationByType(String type) {
        int result = 0;
        switch (type) {
            case "buch":
                result = idAccumulationRepository.findById(1).buchIdAcc;
                break;
            case "blog":
                result = idAccumulationRepository.findById(1).blogIdAcc;
                break;
            case "author":
                result = idAccumulationRepository.findById(1).authorIdAcc;
                break;
            case "synaxarium":
                result = idAccumulationRepository.findById(1).synaxariumIdAcc;
                break;
            case "question":
                result = idAccumulationRepository.findById(1).questionIdAcc;
                break;
        }
        return result;
    }

//    public void addNewIdsAccumulation(IdsAccumulation idsAccumulation, String type) {
//
//        if (type.equals("buch")) {
////            idsAccumulation.buchIdAcc = idsAccumulation.buchIdAcc++;
//            idsAccumulation.setBuchIdAcc(idsAccumulation.buchIdAcc++);
//            idAccumulationRepository.save(idsAccumulation);
//        } else if (type.equals("blog")) {
//
//            idsAccumulation.setBlogIdAcc(idsAccumulation.blogIdAcc++);
//            idAccumulationRepository.save(idsAccumulation);
//        }
//       }

    public IdsAccumulation updateIdsAccumulation(String type) {
        IdsAccumulation idsAccumulation = idAccumulationRepository.findById(1);
        switch (type) {
            case "buch":
                idsAccumulation.setBuchIdAcc(idsAccumulation.buchIdAcc + 1);
                break;
            case "blog":
                idsAccumulation.setBlogIdAcc(idsAccumulation.blogIdAcc + 1);
                break;
            case "author":
                idsAccumulation.setAuthorIdAcc(idsAccumulation.authorIdAcc + 1);
                break;
            case "synaxarium":
                idsAccumulation.setSynaxariumIdAcc(idsAccumulation.synaxariumIdAcc + 1);
                break;
            case "question":
                idsAccumulation.setQuestionIdAcc(idsAccumulation.questionIdAcc + 1);
                break;
        }
        idAccumulationRepository.save(idsAccumulation);
        return idsAccumulation;
    }

    public IdsAccumulation updateIdsAccumulationAll(IdsAccumulation newIdsAccumulation) {
        idAccumulationRepository.save(newIdsAccumulation);
        return newIdsAccumulation;
    }

    public void deleteIdsAccumulation() {
        idAccumulationRepository.deleteAll();
    }
}