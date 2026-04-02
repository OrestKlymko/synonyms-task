# 📖 Synonyms API

![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?logo=springboot)
![H2](https://img.shields.io/badge/Database-H2-lightgrey)
![Flyway](https://img.shields.io/badge/Migrations-Flyway-red)
![OpenAPI](https://img.shields.io/badge/Docs-OpenAPI%2FSwagger-orange)

---

## Overview

A REST API for retrieving words and their synonyms.

**Supported operations:**

- 🔍 Get synonyms for a specific word
- 📄 Get a cursor-paginated list of words with their synonyms

The application is built with **Spring Boot** and uses a relational database model where each word belongs to a synonym group.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Persistence | Spring Data JPA / Hibernate |
| Database | H2 |
| Migrations | Flyway |
| Validation | Bean Validation |
| API Docs | OpenAPI / Swagger |

---

## How to Run

The application starts on **http://localhost:8080**

| Resource | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI Docs | http://localhost:8080/v3/api-docs |

---

## API Endpoints

### 🔍 Get synonyms for a specific word

```
GET /words/{word}/synonyms
```

**Example request:**
```
GET /words/happy/synonyms
```

**Example response:**
```json
{
  "word": "happy",
  "synonyms": ["joyful", "elated", "cheerful"]
}
```

**Behavior:**

| Case | HTTP Status |
|---|---|
| Word exists with synonyms | `200 OK` |
| Word exists but has no synonyms | `200 OK` with empty list |
| Word does not exist | `404 Not Found` |
| Invalid input | `400 Bad Request` |

---

### 📄 Get all words with synonyms

```
GET /words/synonyms
```

**Query parameters:**

| Parameter | Type | Required | Default | Max | Description |
|---|---|---|---|---|---|
| `cursor` | UUID | No | — | — | Keyset pagination cursor |
| `limit` | Integer | No | `20` | `100` | Page size |

**Example request:**
```
GET /words/synonyms?limit=2
```

**Example response:**
```json
{
  "data": [
    {
      "word": "happy",
      "synonyms": ["joyful", "elated", "cheerful"]
    },
    {
      "word": "cold",
      "synonyms": ["chilly", "frigid"]
    }
  ],
  "nextCursor": "6f1f8d8b-2f3f-4f8d-8d61-7fd5f1d7d2a2",
  "hasNext": true
}
```

---

## Pagination

The collection endpoint uses **cursor-based (keyset) pagination**.

**Flow:**

1. Send the first request **without** a cursor
2. Use the `nextCursor` from the response in the next request
3. Repeat until `hasNext` is `false`

**Implementation details:**

- The query fetches `limit + 1` rows
- `hasNext` is `true` if more than `limit` rows were returned
- `nextCursor` is the ID of the last returned word

> ℹ️ Cursor-based pagination was chosen over offset pagination because it is more stable and performs better on large datasets.

---

## Error Handling

All errors are handled centrally via a global exception handler.

**Error response format:**
```json
{
  "status": 400,
  "message": "must be less than or equal to 100"
}
```

**Handled cases:**

| HTTP Status | Cause |
|---|---|
| `400 Bad Request` | Invalid input / Bean Validation failure |
| `404 Not Found` | Word not found |

---

## Validation

Validation is applied to all request parameters.

| Parameter | Rule |
|---|---|
| `limit` | Must be a positive integer |
| `limit` | Must not exceed the configured maximum (`100`) |

Violations return a structured `400 Bad Request` response.

---

## Architecture

The application follows a standard layered structure:

```
controller   →   HTTP endpoints (thin layer, no business logic)
service      →   Business logic
repository   →   Database access
exception    →   Centralized error handling
dto          →   API response models
```

---

## Data Model

Each word is stored alongside a **synonym group identifier**.

| Property | Description |
|---|---|
| Each word is unique | No duplicate word entries |
| Each word belongs to exactly one group | `groupId` |
| All words in the same group are synonyms | Group-based lookup |

> This avoids the complexity of bidirectional pairwise relation tables.

---

## Main Design Decisions

### 1. Group-based synonym model

Instead of pairwise synonym relations, the solution uses a **synonym group ID**.

**Reasons:**
- Simpler data model
- Efficient lookup of all synonyms for a word
- Avoids duplicate symmetric relations (A→B and B→A)

---

### 2. Cursor-based pagination

The collection endpoint uses **keyset pagination**.

**Reasons:**
- More stable than offset pagination
- Better suited for larger datasets
- Avoids performance degradation on high offsets

---

### 3. In-memory grouping of synonyms

For paginated responses, the service:

1. Fetches the page of words
2. Fetches all words belonging to the page's synonym groups
3. Groups them in Java by `groupId`

**Reasons:**
- Avoids N+1 database queries
- Keeps the SQL simple and portable
- Avoids database-specific aggregation functions

---


