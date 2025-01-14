package com.admiral.app.conf;

import com.admiral.app.analyze.FilterPoint;

import java.util.List;

public class Config {
    public static final List<FilterPoint> FILTER_POINTS = List.of(
            new FilterPoint("auth","Не определена информация о пользователе", false),
            new FilterPoint("npe", "NullPointerException", true)
    );
    
    public static final String COMMON_PATH = "/home/andrey/";
    public static final String FILE_PATH = "/home/andrey/filtered_report.txt";
    public static final String HTTP_FILE_PATH = "/home/andrey/http_remains_report.txt";
    public static final String PROBLEM_FILE_PATH = "/home/andrey/http_problem_report.txt";
    
    public static final String HOST = "http://localhost:8080/PO.Insurance/";
}
