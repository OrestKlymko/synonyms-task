package com.synonyms.task.service;

import com.synonyms.task.dto.CursorPageResponse;
import com.synonyms.task.dto.SynonymResponse;

import java.util.UUID;

public interface WordService {

    CursorPageResponse<SynonymResponse> getAllWordsWithSynonyms(UUID cursor, int limit);

    SynonymResponse getSynonymsByWord(String text);
}
