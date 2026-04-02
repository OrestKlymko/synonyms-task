package com.synonyms.task.dto;

import java.util.List;
import java.util.UUID;

public record CursorPageResponse<T>(
        List<T> data,
        UUID nextCursor,
        boolean hasNext
) {
}
