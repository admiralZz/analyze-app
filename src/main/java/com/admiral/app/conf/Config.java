package com.admiral.app.conf;

import com.admiral.app.analyze.FilterPoint;

import java.util.List;

public class Config {
    // Фильтры куда отбраются классы по сообщению из ответа
    public static final List<FilterPoint> FILTER_POINTS = List.of(
            new FilterPoint("auth", "Не определена информация о пользователе", false),
            new FilterPoint("npe", "NullPointerException", true)
    );
    // Основной рабочий каталог
    public static final String COMMON_PATH = "/home/andrey/analyze";
    // Файл откуда берутся названия классов
    public static final String FILE_PATH = "/home/andrey/filtered1_report.txt";
    // Файл куда помещаются классы которые не попали не в один из фильтров
    public static final String HTTP_FILE_PATH = COMMON_PATH + "/http_remains_report.txt";
    // Файл куда попали классы, при запросе на которые произошла ошибка(исключение)
    public static final String PROBLEM_FILE_PATH = COMMON_PATH + "/http_problem_report.txt";
    // Основной хост
    public static final String HOST = "http://localhost:8080/PO.Insurance/";

    public static final String[] PATHS_FOR_ANALYZE_CLASSES = {
            "/home/andrey/packages/jx-core",
            "/home/andrey/packages/vita"
    };
    public static final String[] CONTAINS_IN_CLASS = {
            "RPDSessionGetter.INST.получитьСессиюРПД"
    };
}
