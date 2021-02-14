package de.cj.jchess.service.mapper;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessPiece;
import de.cj.jchess.entity.MongoChessConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Set;

@Mapper(componentModel = "spring", uses = ChessPieceMapper.class)
public interface ChessConfigurationMapper {

    MongoChessConfiguration chessConfigurationToMongo(ChessConfiguration configuration);

    ChessConfiguration mongoToChessConfiguration(MongoChessConfiguration configuration);

    void copy(ChessConfiguration source, @MappingTarget ChessConfiguration target);

    ChessConfiguration copy(ChessConfiguration source);

    Set<ChessPiece> copy(Set<ChessPiece> source);

}
