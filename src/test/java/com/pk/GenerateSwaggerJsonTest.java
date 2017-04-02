package com.pk;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Generate swagger.json so it can be checked in with the project
 *
 * Created by pallav.kothari on 3/28/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GenerateSwaggerJsonTest {
    private static final String API_DOCS = "http://localhost:%d/swagger/api-docs";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @LocalServerPort
    int localPort;

    @Test
    public void genSwagger() throws Exception {
        Request req = new Request.Builder().url(String.format(API_DOCS, localPort)).build();
        Response response = client.newCall(req).execute();
        try (ResponseBody body = response.body()) {
            JsonObject json = new JsonParser().parse(body.string()).getAsJsonObject();
            Files.write(Paths.get("src/main/resources/swagger.json"), gson.toJson(json).getBytes(Charsets.UTF_8));
        }
    }
}
