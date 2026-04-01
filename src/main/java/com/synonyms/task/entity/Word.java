package com.synonyms.task.entity;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "WORDS")
@Getter
@EqualsAndHashCode
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "WORD", nullable = false, unique = true)
    private String word;

    @Column(name = "SYNONYM_GROUP_ID",nullable = false)
    private UUID groupId;

}
