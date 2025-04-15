package at.questionbank.qustion_bank.communication;

import at.questionbank.qustion_bank.logic.IdIdsAccumulationManager;
import at.questionbank.qustion_bank.persistence.domain.IdsAccumulation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/idsAccumulation")
@RequiredArgsConstructor
public class IdsAccumulationEndpoint {
    private final IdIdsAccumulationManager idsAccumulationManager;

    @GetMapping()
    List<IdsAccumulation> getIdsAccumulationByType() {
        return idsAccumulationManager.getIdsAccumulationAll();
    }

    @GetMapping("/{type}")
    int getIdsAccumulationByType(@PathVariable String type) {
        return idsAccumulationManager.getIdsAccumulationByType(type);
    }

    @PostMapping
    void post(@RequestBody IdsAccumulation idsAccumulation) {
        idsAccumulationManager.save(idsAccumulation);
    }

    @PutMapping("/{type}")
    IdsAccumulation updateIdsAccumulation(@PathVariable String type) {
        return idsAccumulationManager.updateIdsAccumulation(type);
    }

//    @PutMapping("/{all}")
//    IdsAccumulation updateIdsAccumulationAll(@RequestBody IdsAccumulation idsAccumulation) {
//        return idsAccumulationManager.updateIdsAccumulationAll(idsAccumulation);
//    }
    @DeleteMapping("/1na1")
    void deleteIdsAccumulation() {
        idsAccumulationManager.deleteIdsAccumulation();
    }


}
// {
//         "id":1,
//         "buchIdAcc":0,
//         "blogIdAcc":0,
//         "authorIdAcc":0,
//         "synaxariumIdAcc":0,
//         "questionIdAcc":0
//         }
