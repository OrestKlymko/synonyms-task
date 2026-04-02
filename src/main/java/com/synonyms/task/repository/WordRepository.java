package com.synonyms.task.repository;

import com.synonyms.task.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface WordRepository extends JpaRepository<Word, UUID> {

    @Query("FROM Word WHERE UPPER(word) = UPPER(:text)")
    Optional<Word> findWordIgnoreCase(String text);


    @Query("SELECT w.word FROM Word w WHERE w.groupId = :groupId AND UPPER(w.word) <> UPPER(:sourceWord)")
    List<String> findAllSynonymsExceptSourceWord(UUID groupId, String sourceWord);


    @Query(value = """
            SELECT * FROM WORDS W
            WHERE (:cursor IS NULL OR W.ID > :cursor)
            ORDER BY W.ID
            LIMIT :limit
            """, nativeQuery = true)
    List<Word> getWords(UUID cursor, int limit);

    @Query(value = """
            SELECT * FROM WORDS W
                        WHERE W.SYNONYM_GROUP_ID IN (:groupIds)
            """, nativeQuery = true)
    List<Word> getSynonymsByGroupIds(Set<UUID> groupIds);
}
