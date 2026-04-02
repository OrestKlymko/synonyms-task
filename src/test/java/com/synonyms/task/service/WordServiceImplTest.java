package com.synonyms.task.service;

import com.synonyms.task.dto.CursorPageResponse;
import com.synonyms.task.dto.SynonymResponse;
import com.synonyms.task.entity.Word;
import com.synonyms.task.exception.model.BadRequestException;
import com.synonyms.task.exception.model.NotFoundException;
import com.synonyms.task.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordServiceImplTest {

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private WordServiceImpl wordService;

    @Test
    void shouldReturnEmptyPageWhenNoWordsFound() {
        int limit = 2;
        when(wordRepository.getWords(null, limit + 1)).thenReturn(List.of());

        CursorPageResponse<SynonymResponse> response = wordService.getAllWordsWithSynonyms(null, limit);

        assertTrue(response.data().isEmpty());
        assertNull(response.nextCursor());
        assertFalse(response.hasNext());

        verify(wordRepository).getWords(null, limit + 1);
        verify(wordRepository, never()).getSynonymsByGroupIds(any());
    }

    @Test
    void shouldReturnPageAndNextCursorWhenMoreWordsExist() {
        int limit = 2;
        UUID group1 = UUID.randomUUID();
        UUID group2 = UUID.randomUUID();

        Word first = createWord(UUID.fromString("00000000-0000-0000-0000-000000000001"), "Happy", group1);
        Word second = createWord(UUID.fromString("00000000-0000-0000-0000-000000000002"), "Cold", group2);
        Word third = createWord(UUID.fromString("00000000-0000-0000-0000-000000000003"), "Warm", UUID.randomUUID());

        when(wordRepository.getWords(null, limit + 1)).thenReturn(List.of(first, second, third));
        when(wordRepository.getSynonymsByGroupIds(Set.of(group1, group2))).thenReturn(List.of(
                createWord(UUID.randomUUID(), "happy", group1),
                createWord(UUID.randomUUID(), "Joyful", group1),
                createWord(UUID.randomUUID(), "COLD", group2),
                createWord(UUID.randomUUID(), "Chilly", group2)
        ));

        CursorPageResponse<SynonymResponse> response = wordService.getAllWordsWithSynonyms(null, limit);

        assertTrue(response.hasNext());
        assertEquals(second.getId(), response.nextCursor());
        assertEquals(2, response.data().size());

        SynonymResponse firstResponse = response.data().getFirst();
        assertEquals("Happy", firstResponse.word());
        assertEquals(List.of("Joyful"), firstResponse.synonyms());

        SynonymResponse secondResponse = response.data().get(1);
        assertEquals("Cold", secondResponse.word());
        assertEquals(List.of("Chilly"), secondResponse.synonyms());

        verify(wordRepository).getWords(null, limit + 1);
        verify(wordRepository).getSynonymsByGroupIds(Set.of(group1, group2));
    }

    @Test
    void shouldReturnPageWithoutNextCursorWhenNoMoreWordsExist() {
        int limit = 3;
        UUID group = UUID.randomUUID();
        Word onlyWord = createWord(UUID.randomUUID(), "Light", group);

        when(wordRepository.getWords(null, limit + 1)).thenReturn(List.of(onlyWord));
        when(wordRepository.getSynonymsByGroupIds(Set.of(group))).thenReturn(List.of(
                createWord(UUID.randomUUID(), "Bright", group),
                createWord(UUID.randomUUID(), "LIGHT", group)
        ));

        CursorPageResponse<SynonymResponse> response = wordService.getAllWordsWithSynonyms(null, limit);

        assertFalse(response.hasNext());
        assertNull(response.nextCursor());
        assertEquals(1, response.data().size());
        assertEquals(List.of("Bright"), response.data().getFirst().synonyms());

        verify(wordRepository).getWords(null, limit + 1);
        verify(wordRepository).getSynonymsByGroupIds(Set.of(group));
    }

    @Test
    void shouldReturnSynonymsByWord() {
        String sourceText = "happy";
        UUID groupId = UUID.randomUUID();
        Word source = createWord(UUID.randomUUID(), "happy", groupId);

        when(wordRepository.findWordIgnoreCase(sourceText)).thenReturn(Optional.of(source));
        when(wordRepository.findAllSynonymsExceptSourceWord(groupId, "happy")).thenReturn(List.of("joyful", "cheerful"));

        SynonymResponse response = wordService.getSynonymsByWord(sourceText);

        assertEquals("happy", response.word());
        assertEquals(List.of("joyful", "cheerful"), response.synonyms());

        verify(wordRepository).findWordIgnoreCase(sourceText);
        verify(wordRepository).findAllSynonymsExceptSourceWord(groupId, "happy");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void shouldThrowBadRequestWhenTextIsNullOrBlank(String text) {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> wordService.getSynonymsByWord(text));

        assertEquals("Text cannot be null or empty", exception.getMessage());
        verifyNoInteractions(wordRepository);
    }

    @Test
    void shouldThrowNotFoundWhenWordDoesNotExist() {
        String text = "missing";
        when(wordRepository.findWordIgnoreCase(text)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> wordService.getSynonymsByWord(text));

        assertEquals("Word not found: missing", exception.getMessage());
        verify(wordRepository).findWordIgnoreCase(eq(text));
        verify(wordRepository, never()).findAllSynonymsExceptSourceWord(any(), any());
    }

    private static Word createWord(UUID id, String text, UUID groupId) {
        Word word = new Word();
        ReflectionTestUtils.setField(word, "id", id);
        ReflectionTestUtils.setField(word, "word", text);
        ReflectionTestUtils.setField(word, "groupId", groupId);
        return word;
    }
}

