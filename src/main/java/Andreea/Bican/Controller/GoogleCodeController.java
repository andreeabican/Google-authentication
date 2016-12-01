package Andreea.Bican.Controller;

import Andreea.Bican.Service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by andre on 01.12.2016.
 */
@Controller
public class GoogleCodeController {

    @Autowired
    AccessTokenService accessTokenService;

    @RequestMapping("/code")
    public String GoogleCode(@RequestParam(value = "code", required = false, defaultValue = "World")String code, Model model) throws IOException {
        model.addAttribute("accessToken", accessTokenService.getGoogleAccessToken(code));
        return "token";
    }
}
