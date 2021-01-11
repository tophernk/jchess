package de.cj.jchess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ChessConfigurationServiceTest {

    @Autowired
    private ChessConfigurationService chessConfigurationService;

    @Test
    void createConfiguration() {
        ChessConfiguration configuration = chessConfigurationService.createConfiguration("test");

        assertNotNull(configuration);
    }

    @Test
    void exportFen() {
        ChessConfiguration configuration = new ChessConfiguration("test");
        String fen = chessConfigurationService.exportFen(configuration);

        assertNotNull(fen);
    }
}