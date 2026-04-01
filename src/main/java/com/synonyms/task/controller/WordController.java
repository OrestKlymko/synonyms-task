package com.synonyms.task.controller;

import com.synonyms.task.service.WordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WordController {

    private final WordService synonymService;


}
