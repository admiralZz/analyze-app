package com.admiral.app.analyze;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class FilterPoint {
    private final String name;
    private final String message;
    private final Boolean exportToFile;

    private final List<String> classList = new ArrayList<>();
}
