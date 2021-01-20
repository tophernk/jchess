package de.cj.jchess;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class JchessApplication {

    public static void main(String[] args) {
        SpringApplication.run(JchessApplication.class, args);
    }

    @Bean
    public CommandLineRunner testJPAPersistence(JpaChessPieceRepository repository) {
        return (args) -> {
            JpaChessPiece chessPiece = new JpaChessPiece(ChessPieceType.PAWN, ChessPieceColor.WHITE);
            repository.save(chessPiece);
            Optional<JpaChessPiece> managedPiece = repository.findById(chessPiece.getId());
        };
    }

}
