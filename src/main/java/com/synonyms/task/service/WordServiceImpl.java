package com.synonyms.task.service;


import com.synonyms.task.dto.CursorPageResponse;
import com.synonyms.task.utils.StringUtils;
import com.synonyms.task.dto.SynonymResponse;
import com.synonyms.task.entity.Word;
import com.synonyms.task.exception.model.BadRequestException;
import com.synonyms.task.exception.model.NotFoundException;
import com.synonyms.task.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    @Override
    public CursorPageResponse<SynonymResponse> getAllWordsWithSynonyms(UUID cursor, int limit) {

        List<Word> words = wordRepository.getWords(cursor, limit + 1);

        if (CollectionUtils.isEmpty(words)) {
            return new CursorPageResponse<>(List.of(), null, false);
        }

        List<Word> pageWords = words.stream().limit(limit).toList();
        Set<UUID> wordsGroupIds = pageWords.stream().map(Word::getGroupId).collect(Collectors.toSet());
        List<SynonymResponse> data = getSynonymResponseList(wordsGroupIds, pageWords);

        boolean hasNext = words.size() > limit;
        UUID nextCursor = hasNext ? pageWords.getLast().getId() : null;

        return new CursorPageResponse<>(data, nextCursor, hasNext);

    }

    @Override
    public SynonymResponse getSynonymsByWord(String text) {
        validateText(text);
        Word word = findWordByText(text);
        List<String> allSynonyms = wordRepository.findAllSynonymsExceptSourceWord(word.getGroupId(), word.getWord());
        return new SynonymResponse(word.getWord(), allSynonyms);
    }

    private @NonNull List<SynonymResponse> getSynonymResponseList(Set<UUID> groupIds, List<Word> pageWords) {
        List<Word> synonyms = wordRepository.getSynonymsByGroupIds(groupIds);

        Map<UUID, List<String>> synonymsByGroup =
                synonyms.stream()
                        .collect(Collectors.groupingBy(
                                Word::getGroupId,
                                Collectors.mapping(Word::getWord, Collectors.toList())
                        ));


        return pageWords.stream()
                .map(word -> new SynonymResponse(
                        word.getWord(),
                        synonymsByGroup.getOrDefault(word.getGroupId(), List.of())
                                .stream()
                                .filter(s -> !s.equalsIgnoreCase(word.getWord()))
                                .toList()
                ))
                .toList();
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
