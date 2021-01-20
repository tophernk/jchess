package de.cj.jchess.rest;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.service.ChessFenService;
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
        return "fenin: OK";
    }

    @GetMapping("/fenout")
    public @ResponseBody String fenOut(@RequestParam String id) {
        ChessConfiguration configuration = fenService.findConfigurationById(id);
        return configuration != null ? fenService.exportFen(configuration) : "fenout: not found";
    }

}
