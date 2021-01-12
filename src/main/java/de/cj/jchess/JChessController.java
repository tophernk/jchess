package de.cj.jchess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JChessController {

    @Autowired
    private ChessFenService fenService;

    @RequestMapping("/fenout")
    public @ResponseBody String fenOut() {
        return fenService.exportFen(new ChessConfiguration());
    }

}
