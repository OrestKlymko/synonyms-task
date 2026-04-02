package com.synonyms.task.controller;

import com.synonyms.task.dto.CursorPageResponse;
import com.synonyms.task.dto.SynonymResponse;
import com.synonyms.task.service.WordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class WordControllerImpl implements WordController {

    private final WordService wordService;

    @Override
    public CursorPageResponse<SynonymResponse> getAllWordsWithSynonyms(UUID cursor, int limit) {
        return wordService.getAllWordsWithSynonyms(cursor, limit);
    }

    @Override
    public SynonymResponse getSynonymsByWord(String word) {
        return wordService.getSynonymsByWord(word);
    }
}
