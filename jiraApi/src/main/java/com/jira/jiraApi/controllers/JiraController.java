package com.jira.jiraApi.controllers;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JiraController {
    @GetMapping("/errores")
    public Map<String, Object> erroresJira()
            throws ClientProtocolException, IOException, JSONException {
        String url = "https://test-mesa-ayuda.atlassian.net/rest/api/2/search?jql=project=Test";
        String email = "salvador243gm@gmail.com";
        String apiToken = "ATATT3xFfGF0GXZwCyWRdszxjYv9vx1r4gRAI809wk7Q4foo95T9LXuaC6s6bdtIjLzczqexQ0aZ7NK8VVk7HgWg4nyqP5bkua6BP8eJWju2Laktr9vpzMuqJwNMdgYcphh_yzz6ykDhaWVFshmzpM7G9EUFV4FW0aUr3UjQupMVqHqz5lZms6U=B14FC959";

        // Crear la conexión HTTP
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Configurar el método HTTP y las cabeceras
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "Basic " + getAuthString(email, apiToken));

        // Obtener la respuesta
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Map<String, Object> dict = new HashMap<String, Object>();
        dict.put("codigo", responseCode);
        dict.put("errores", response.toString());
        return dict;
    }

    private static String getAuthString(String email, String apiToken) {
        String auth = email + ":" + apiToken;
        return java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }
}