package de.cj.jchess.service.mapper;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.MongoChessConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChessConfigurationMapper {

    MongoChessConfiguration chessConfigurationToMongo(ChessConfiguration configuration);

    ChessConfiguration mongoToChessConfiguration(MongoChessConfiguration configuration);

    void copy(ChessConfiguration source, @MappingTarget ChessConfiguration target);

}
