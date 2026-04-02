    package com.synonyms.task.controller;

    import com.synonyms.task.dto.CursorPageResponse;
    import com.synonyms.task.dto.SynonymResponse;
    import com.synonyms.task.service.WordService;
    import jakarta.validation.constraints.Max;
    import jakarta.validation.constraints.Min;
    import lombok.AllArgsConstructor;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;

    import java.util.UUID;

    @RestController
    @AllArgsConstructor
    @RequestMapping("/words")
    @Validated
    public class WordController {

        private final WordService wordService;


        @GetMapping("/synonyms")
        public CursorPageResponse<SynonymResponse> getAllWordsWithSynonyms(
                @RequestParam(required = false) UUID cursor,
                @RequestParam(required = false, defaultValue = "20") @Min(1) @Max(100) int limit
        ) {
            return wordService.getAllWordsWithSynonyms(cursor, limit);
        }


        @GetMapping("/{word}/synonyms")
        public SynonymResponse getSynonymsByWord(@PathVariable String word) {
            return wordService.getSynonymsByWord(word);
        }


    }
