package de.cj.jchess.dao;

import de.cj.jchess.entity.JpaChessPiece;
import org.springframework.data.repository.CrudRepository;

public interface JpaChessPieceRepository extends CrudRepository<JpaChessPiece, Integer> {
}
