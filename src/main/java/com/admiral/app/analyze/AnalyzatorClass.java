package com.admiral.app.analyze;

import com.admiral.app.utils.FileUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public class AnalyzatorClass {

    private final String pathToProject;
    private final List<String> classList = new ArrayList<>();
    private final String[] containsArray;

    public void analyze(List<String> classNamesList) throws IOException {
        for (String className : classNamesList) {
            findFile(className)
                    .ifPresent(path -> {
                        if (FileUtil.findContent(path, containsArray)) {
                            classList.add(className);
                        }
                    });
        }
    }

    private Optional<Path> findFile(String className) throws IOException {
        String javaFileName = className.substring(className.lastIndexOf(".") + 1) + ".java";
        try (Stream<Path> stream = Files.walk(Path.of(pathToProject))) {
            List<Path> paths = stream
                    .filter(this::excludePaths)
                    .filter(path -> checkPath(path, className))
                    .toList();

            return paths.stream()
                    .filter(Files::isRegularFile)
                    .filter(this::isJavaFile)
                    .filter(path -> path.getFileName().toString().equals(javaFileName))
                    .findFirst();
        }
    }


    private boolean isJavaFile(Path path) {
        return path.getFileName().toString().endsWith(".java");
    }

    private boolean checkPath(Path path, String className) {
        String prefix = className.substring(0, className.lastIndexOf("."));
        String pathPrefix = prefix.replaceAll("\\.", "/");
        return path.toString().contains(pathPrefix);
//        return path.endsWith(pathPrefix);
    }

    private boolean excludePaths(Path path) {
        return !path.toString().contains("/build/classes") &&
                !path.toString().contains("/build/resources");
    }
}
