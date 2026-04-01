package com.synonyms.task.dto;

import java.util.List;

public record SynonymResponse(
        String word,
        List<String> synonyms
) {
}
