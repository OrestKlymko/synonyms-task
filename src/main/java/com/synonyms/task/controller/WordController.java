package com.synonyms.task.controller;

import com.synonyms.task.dto.CursorPageResponse;
import com.synonyms.task.dto.SynonymResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequestMapping("/words")
@Validated
@Tag(name = "Words", description = "Operations for retrieving words and their synonyms")
public interface WordController {

    @Operation(
            summary = "Get all words with synonyms",
            description = "Returns a cursor-paginated list of words with their synonyms"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
    })
    @GetMapping(value = "/synonyms")
    CursorPageResponse<SynonymResponse> getAllWordsWithSynonyms(

            @Parameter(description = "Cursor for pagination")
            @RequestParam(required = false) UUID cursor,

            @Parameter(description = "Max number of items", example = "20")
            @RequestParam(defaultValue = "20")
            @Min(1)
            @Max(100)
            int limit
    );


    @Operation(
            summary = "Get synonyms by word",
            description = "Returns all synonyms for the given word"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Word not found"),
    })
    @GetMapping("/{word}/synonyms")
    SynonymResponse getSynonymsByWord(
            @Parameter(description = "Word to search", example = "happy")
            @PathVariable String word
    );
}
