package com.admiral.app.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.admiral.app.conf.Config.*;

public class FileUtil {


    public static List<String> getClassList() throws IOException {
        return Files.readAllLines(Path.of(FILE_PATH));
    }

    public static void exportRemainsToFile(List<String> classList) throws IOException {
        String content = String.join("\n", classList);
        Files.writeString(Path.of(HTTP_FILE_PATH), content);
    }

    public static void exportToFile(String fileName, List<String> classList) throws IOException {
        String content = String.join("\n", classList);
        Files.writeString(Path.of(COMMON_PATH, fileName), content);
    }

    public static void exportProblemsToFile(List<String[]> list) throws IOException {
        String content = list.stream().map(array -> array[0] + "\n" + array[1]).collect(Collectors.joining("\n"));
        Files.writeString(Path.of(PROBLEM_FILE_PATH), content);
    }

    public static boolean findContent(Path file, String[] contents) {
        try (Stream<String> lines = Files.lines(file)) {

            return lines
                    .anyMatch(line -> Arrays.stream(contents)
                            .anyMatch(line::contains));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
