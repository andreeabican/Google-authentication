package Andreea.Bican.Controller;

import Andreea.Bican.Service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by andre on 01.12.2016.
 */
@Controller
public class GoogleLoginController {

    @Autowired
    AccessTokenService accessTokenService;

    @RequestMapping("/login/Google")
    public String login(HttpServletResponse httpServletResponse) throws Exception {
        String googleCodeURI = accessTokenService.getGoogleAuthorizationCode();
        accessTokenService.openBrowser(googleCodeURI);
        return "greeting";
    }

}

