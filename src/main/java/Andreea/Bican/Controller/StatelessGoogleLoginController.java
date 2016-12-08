package Andreea.Bican.Controller;

import Andreea.Bican.Service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by andre on 08.12.2016.
 */
@Controller
public class StatelessGoogleLoginController {

    @Autowired
    AccessTokenService accessTokenService;

    @RequestMapping("/authorize")
    @ResponseBody
    public String login() throws Exception {
        String googleCodeURI = accessTokenService.getGoogleAuthorizationCode();
        accessTokenService.openBrowser(googleCodeURI);
        return "Login in browser";
    }
}
