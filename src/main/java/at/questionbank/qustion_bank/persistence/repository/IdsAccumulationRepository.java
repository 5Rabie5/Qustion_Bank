package at.questionbank.qustion_bank.persistence.repository;

import at.questionbank.qustion_bank.persistence.domain.IdsAccumulation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IdsAccumulationRepository extends MongoRepository<IdsAccumulation,String> {
    IdsAccumulation findById(Integer searchId);

    List<IdsAccumulation> findAll();

    void deleteById(int id);

    @Override
    void deleteAll();
}
