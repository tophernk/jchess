package de.cj.jchess.service.mapper;

import de.cj.jchess.entity.ChessPiece;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChessPieceMapper {

    ChessPiece copy(ChessPiece source);

}
