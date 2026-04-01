package com.synonyms.task.service;


import com.synonyms.task.StringUtils;
import com.synonyms.task.dto.SynonymResponse;
import com.synonyms.task.entity.Word;
import com.synonyms.task.exception.BadRequestException;
import com.synonyms.task.exception.NotFoundException;
import com.synonyms.task.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WordService {

    private final WordRepository wordRepository;

    public SynonymResponse getSynonymsByWord(String text) {
        validateText(text);
        Word word = findWordByText(text);
        List<String> allSynonyms = wordRepository.findAllSynonymsExceptSourceWord(word.getGroupId(), word.getWord());
        return new SynonymResponse(word.getWord(), allSynonyms);
    }

    private static void validateText(String text) {
        boolean textIsNullOrEmpty = StringUtils.textIsNullOrEmpty(text);
        if (textIsNullOrEmpty) {
            throw new BadRequestException("Text cannot be null or empty");
        }
    }

    private @NonNull Word findWordByText(String text) {
        return wordRepository.findWordIgnoreCase(text).orElseThrow(() -> new NotFoundException("Word not found: " + text));
    }


}
