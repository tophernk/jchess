package de.cj.jchess.service.mapper;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.MongoChessConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChessConfigurationMapper {

    ChessConfigurationMapper INSTANCE = Mappers.getMapper(ChessConfigurationMapper.class);

    MongoChessConfiguration chessConfigurationToMongo(ChessConfiguration configuration);
    ChessConfiguration mongoToChessConfiguration(MongoChessConfiguration configuration);
}
