package Andreea.Bican.Service;

import org.springframework.expression.ParseException;

import java.io.IOException;

/**
 * Created by andre on 01.12.2016.
 */
public interface AccessTokenService {
    String getGoogleAuthorizationCode() throws Exception;
    String getGoogleAccessToken(String code) throws IOException;
    void openBrowser(String uri);
    String getEmailFromGoogleAccessToken(String token) throws IOException, ParseException, org.json.simple.parser.ParseException;
}
