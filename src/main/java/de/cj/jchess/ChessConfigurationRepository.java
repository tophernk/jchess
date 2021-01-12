package de.cj.jchess;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface ChessConfigurationRepository extends MongoRepository<ChessConfiguration, String> {

    public ChessConfiguration findChessConfigurationById(@Param("id") String id);
}
