# Analyze app

Приложение для анализа **gwt**-приложения, путем вызова классов приложения(из списка) по **http**

## Принцип работы

После запуска, приложение берет названия классов из списка(**Config.FILE_PATH**), делает http-запросы
и анализирует ответы на основе фильтров(**Config.FILTER_POINTS**). После запросов и анализа, формирует файлы
со списками, из каждого фильтра.

## Config

Тут перечислены основные настройки приложения:

```java
public class Config {
    // Фильтры куда отбраются классы по сообщению из ответа
    public static final List<FilterPoint> FILTER_POINTS = List.of(
            new FilterPoint("auth","Не определена информация о пользователе", false),
            new FilterPoint("npe", "NullPointerException", true)
    );
    // Основной рабочий каталог
    public static final String COMMON_PATH = "/home/andrey/";
    // Файл откуда берутся названия классов
    public static final String FILE_PATH = "/home/andrey/filtered_report.txt";
    // Файл куда помещаются классы которые не попали не в один из фильтров
    public static final String HTTP_FILE_PATH = "/home/andrey/http_remains_report.txt";
    // Файл куда попали классы, при запросе на которые произошла ошибка(исключение)
    public static final String PROBLEM_FILE_PATH = "/home/andrey/http_problem_report.txt";
    // Основной хост
    public static final String HOST = "http://localhost:8080/PO.Insurance/";
}
```

