package de.cj.jchess;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.cj.jchess.dao.JpaChessPieceRepository;
import de.cj.jchess.entity.ChessPieceColor;
import de.cj.jchess.entity.ChessPieceType;
import de.cj.jchess.entity.JpaChessPiece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class JchessApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(JchessApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JchessApplication.class, args);
    }

    @Bean
    public CommandLineRunner testJPAPersistence(JpaChessPieceRepository repository) {
        return args -> {
            JpaChessPiece chessPiece = new JpaChessPiece();
            chessPiece.setPieceType(ChessPieceType.PAWN);
            chessPiece.setPieceColor(ChessPieceColor.WHITE);
            repository.save(chessPiece);
            Optional<JpaChessPiece> managedPiece = repository.findById(chessPiece.getId());
            managedPiece.ifPresent(p -> LOGGER.info(p.toString()));
        };
    }

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://" + username + ":" + password + "@localhost:27017");
    }
}
