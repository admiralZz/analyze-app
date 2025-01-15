package com.admiral.app;

import com.admiral.app.analyze.Analyzator;
import com.admiral.app.analyze.AnalyzatorClass;
import com.admiral.app.analyze.FilterPoint;
import com.admiral.app.conf.Config;
import com.admiral.app.utils.FileUtil;
import com.admiral.app.http.HttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    private static final HttpClient HTTP_CLIENT = new HttpClient(Config.HOST);
    private static final Analyzator ANALYZATOR = new Analyzator(Config.FILTER_POINTS);
    private static final AnalyzatorClass ANALYZATOR_CLASS_CORE = new AnalyzatorClass(Config.PATHS_FOR_ANALYZE_CLASSES[0], Config.CONTAINS_IN_CLASS);
    private static final AnalyzatorClass ANALYZATOR_CLASS_PROJECT = new AnalyzatorClass(Config.PATHS_FOR_ANALYZE_CLASSES[1], Config.CONTAINS_IN_CLASS);

    public static void main(String[] args) throws IOException {
        runWithAnalyze();
//        runSimple("ru.lois.web.poinsurance.conf.service.action.CalcPolicyAction");
    }

    private static void runSimple(String className) {
        Map.Entry<String, Response> stringResponseEntry = HTTP_CLIENT.execCall(className);
        Response response = stringResponseEntry.getValue();
        System.out.println(response);
        try (ResponseBody body = response.body()) {
            System.out.println(new String(body.string().getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runWithAnalyze() throws IOException {
        List<String> classList = FileUtil.getClassList();

        List<String[]> problemClasses = new ArrayList<>();
        for (String className : classList) {
            Map.Entry<String, Response> stringResponseEntry = HTTP_CLIENT.execCall(className);
            ANALYZATOR.analyze(stringResponseEntry, problemClasses);
        }

        for (FilterPoint filterPoint : ANALYZATOR.getFilterPointList()) {
            classList.removeAll(filterPoint.getClassList());
            System.out.println(filterPoint.getMessage() + ": size = " + filterPoint.getClassList().size());
            if (filterPoint.getExportToFile()) {
                FileUtil.exportToFile(filterPoint.getName() + "_filtered_report.txt", filterPoint.getClassList());
            }
        }

        ANALYZATOR_CLASS_CORE.analyze(classList);
        ANALYZATOR_CLASS_PROJECT.analyze(classList);
        System.out.println("(Core)Class analyze of getting rpdSession: size = " + ANALYZATOR_CLASS_CORE.getClassList().size());
        System.out.println("(Project)Class analyze of getting rpdSession: size = " + ANALYZATOR_CLASS_PROJECT.getClassList().size());
        FileUtil.exportToFile( "core_contains_rpd_report.txt", ANALYZATOR_CLASS_CORE.getClassList());
        FileUtil.exportToFile( "proj_contains_rpd_report.txt", ANALYZATOR_CLASS_PROJECT.getClassList());
        classList.removeAll(ANALYZATOR_CLASS_CORE.getClassList());
        classList.removeAll(ANALYZATOR_CLASS_PROJECT.getClassList());

        System.out.println("Problem classes: size = " + problemClasses.size());
        classList.removeAll(problemClasses.stream().map(array -> array[0]).toList());
        System.out.println("Remain classes: size = " + classList.size());
        classList.forEach(System.out::println);

        FileUtil.exportRemainsToFile(classList);
        FileUtil.exportProblemsToFile(problemClasses);
    }
}
