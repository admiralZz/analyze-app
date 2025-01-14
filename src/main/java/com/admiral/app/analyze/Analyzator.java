package com.admiral.app.analyze;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Analyzator {

    private final List<FilterPoint> filterPointList;

    public void analyze(Map.Entry<String, Response> entry,
                                List<String[]> problemClasses) {
        // Проверяем успешность ответа
        String className = entry.getKey();
        Response response = entry.getValue();
        if (response.isSuccessful() && response.body() != null) {
            try (ResponseBody body = response.body()) {
                String responseBody = body.string();

                for(FilterPoint filterPoint : filterPointList) {
                    boolean contains = responseBody.contains(filterPoint.getMessage());
                    if (contains) {
                        System.out.println(filterPoint.getMessage() + ": " + className);
                        filterPoint.getClassList().add(className);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Request failed with code: " + response.code());
            problemClasses.add(new String[]{className, "response code: " + response.code() +
                    ", message: " + response.message()});
        }
    }
}
