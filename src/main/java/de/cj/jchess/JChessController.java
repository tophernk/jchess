package de.cj.jchess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class JChessController {

    @Autowired
    private ChessFenService fenService;

    @PostMapping("/fenin")
    public @ResponseBody String fenIn(@RequestParam String fen) {
        ChessConfiguration chessConfiguration = fenService.importFen(fen);
        return chessConfiguration.getId();
    }

    @GetMapping("/fenout")
    public @ResponseBody String fenOut(@RequestParam String id) {
        ChessConfiguration configuration = fenService.findConfigurationById(id);
        return configuration != null ? fenService.exportFen(new ChessConfiguration()) : "configuration not found";
    }

}
