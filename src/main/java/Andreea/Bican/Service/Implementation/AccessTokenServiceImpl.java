package Andreea.Bican.Service.Implementation;

import Andreea.Bican.ClientAppDetails;
import Andreea.Bican.Service.AccessTokenService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 01.12.2016.
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private final String USER_AGENT = "Mozilla/5.0";

    private final String URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=";

    @Override
    public String getGoogleAccessToken(String code) throws IOException{

        List<String> scopes = new ArrayList<String>();
        scopes.add("email");
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory,
                ClientAppDetails.getGoogleClientId(), ClientAppDetails.getGoogleClientSecret(), scopes).build();
        GoogleTokenResponse res = flow.newTokenRequest(code).setRedirectUri("http://localhost:8181/code").execute();
        res.getRefreshToken();
        String accessToken = res.getAccessToken();

        return accessToken;
    }

    @Override
    public void openBrowser(String uri) {
        System.setProperty("java.awt.headless", "false");
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(URI.create(uri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getGoogleAuthorizationCode() throws Exception {
        List<String> scopes = new ArrayList<String>();
        scopes.add("email");

        HttpTransport transport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory,
                ClientAppDetails.getGoogleClientId(),
                ClientAppDetails.getGoogleClientSecret(), scopes).build();
        GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        url.setRedirectUri("http://localhost:8181/code");
        url.setApprovalPrompt("force");
        url.setAccessType("offline");

        String authorize_url = url.build();

        // paste into browser to get code
        return authorize_url;
    }

    @Override
    public String getEmailFromGoogleAccessToken(String token) throws IOException, ParseException, org.json.simple.parser.ParseException {

        URL obj = new URL(URL + token);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        con.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        JSONParser parser = new JSONParser();
        Object object = parser.parse(String.valueOf(sb));
        JSONObject jsonObject = (JSONObject) object;
        String email = (String)jsonObject.get("email");

        return email;
    }

}
