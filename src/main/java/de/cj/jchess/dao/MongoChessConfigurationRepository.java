package de.cj.jchess.dao;

import de.cj.jchess.entity.MongoChessConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface MongoChessConfigurationRepository extends MongoRepository<MongoChessConfiguration, String> {

    public MongoChessConfiguration findChessConfigurationById(@Param("id") String id);
}
