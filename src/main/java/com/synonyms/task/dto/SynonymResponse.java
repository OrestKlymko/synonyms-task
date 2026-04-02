package com.synonyms.task.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response containing a word and its synonyms")
public record SynonymResponse(

        @Schema(
                description = "Source word",
                example = "happy"
        )
        String word,

        @Schema(
                description = "List of synonyms for the word",
                example = "[\"joyful\", \"cheerful\"]"
        )
        List<String> synonyms
) {}