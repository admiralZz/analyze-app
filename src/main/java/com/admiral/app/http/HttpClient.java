package com.admiral.app.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class HttpClient {
    private static final OkHttpClient CLIENT = new OkHttpClient();

    private final String host;

    public Map.Entry<String, Response> execCall(String className) {
        String url = host + className + ".call";

        // Создаем запрос
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            // Отправляем запрос и получаем ответ
            Response response = CLIENT.newCall(request).execute();
            return Map.entry(className, response);
        } catch (IOException e) {
            e.printStackTrace();
            return Map.entry(className, new Response.Builder()
                    .protocol(Protocol.HTTP_1_1)
                    .code(0)
                    .request(request)
                    .message(e.getMessage())
                    .build());
        }
    }
}
