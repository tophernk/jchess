package de.cj.jchess;

import org.springframework.data.repository.CrudRepository;

public interface JpaChessPieceRepository extends CrudRepository<JpaChessPiece, Integer> {
}
