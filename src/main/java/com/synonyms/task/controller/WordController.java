package com.synonyms.task.controller;

import com.synonyms.task.dto.CursorPageResponse;
import com.synonyms.task.dto.SynonymResponse;
import com.synonyms.task.service.WordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/words")
public class WordController {

    private final WordService wordService;


    @GetMapping("/synonyms")
    public CursorPageResponse<SynonymResponse> getAllWordsWithSynonyms(
            @RequestParam(required = false) UUID cursor,
            @RequestParam(required = false, defaultValue = "20") int limit
    ) {
        return wordService.getAllWordsWithSynonyms(cursor, limit);
    }


    @GetMapping("/{word}/synonyms")
    public SynonymResponse getSynonymsByWord(@PathVariable String word) {
        return wordService.getSynonymsByWord(word);
    }


}
