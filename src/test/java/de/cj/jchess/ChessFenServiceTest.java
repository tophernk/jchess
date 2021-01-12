package de.cj.jchess;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ChessFenServiceTest {

    @Autowired
    private ChessFenService fenService;

    @Test
    void createConfiguration() {
        ChessConfiguration configuration = fenService.importFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 0");

        assertNotNull(configuration);
    }

    @Test
    void exportFen() {
        ChessConfiguration configuration = new ChessConfiguration();
        String fen = fenService.exportFen(configuration);

        assertNotNull(fen);
    }

    @Test
    void findConfigurationById() {
        ChessConfiguration inConfiguration = fenService.importFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 0");
        ChessConfiguration outConfiguration = fenService.findConfigurationById(inConfiguration.getId());
        Assertions.assertThat(inConfiguration).isEqualToComparingFieldByFieldRecursively(outConfiguration);
    }
}