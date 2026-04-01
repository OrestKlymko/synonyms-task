CREATE TABLE PUBLIC.WORDS
(
    ID   UUID PRIMARY KEY ,
    WORD VARCHAR(255) NOT NULL UNIQUE,
    SYNONYM_GROUP_ID UUID NOT NULL
);


CREATE INDEX IDX_WORDS_GROUP_ID ON PUBLIC.WORDS (SYNONYM_GROUP_ID);
-- CREATE INDEX IDX_WORDS_UPPER_WORD ON PUBLIC.WORDS (UPPER(WORD)); -- H2 nie podporuje funkcjiu UPPER ale napr pre Postgre mozeme to pouzit

INSERT INTO PUBLIC.WORDS (ID, WORD, SYNONYM_GROUP_ID) VALUES
-- group 1
(RANDOM_UUID(), 'happy', '11111111-1111-1111-1111-111111111111'),
(RANDOM_UUID(), 'joyful', '11111111-1111-1111-1111-111111111111'),
(RANDOM_UUID(), 'elated', '11111111-1111-1111-1111-111111111111'),
(RANDOM_UUID(), 'cheerful', '11111111-1111-1111-1111-111111111111'),

-- group 2
(RANDOM_UUID(), 'sad', '22222222-2222-2222-2222-222222222222'),
(RANDOM_UUID(), 'unhappy', '22222222-2222-2222-2222-222222222222'),
(RANDOM_UUID(), 'sorrowful', '22222222-2222-2222-2222-222222222222'),

-- group 3
(RANDOM_UUID(), 'cold', '33333333-3333-3333-3333-333333333333'),
(RANDOM_UUID(), 'chilly', '33333333-3333-3333-3333-333333333333'),
(RANDOM_UUID(), 'frigid', '33333333-3333-3333-3333-333333333333'),

-- group 4
(RANDOM_UUID(), 'hot', '44444444-4444-4444-4444-444444444444'),
(RANDOM_UUID(), 'warm', '44444444-4444-4444-4444-444444444444'),
(RANDOM_UUID(), 'heated', '44444444-4444-4444-4444-444444444444'),

-- group 5
(RANDOM_UUID(), 'fast', '55555555-5555-5555-5555-555555555555'),
(RANDOM_UUID(), 'quick', '55555555-5555-5555-5555-555555555555'),
(RANDOM_UUID(), 'rapid', '55555555-5555-5555-5555-555555555555'),
(RANDOM_UUID(), 'swift', '55555555-5555-5555-5555-555555555555'),

-- group 6
(RANDOM_UUID(), 'slow', '66666666-6666-6666-6666-666666666666'),
(RANDOM_UUID(), 'sluggish', '66666666-6666-6666-6666-666666666666'),
(RANDOM_UUID(), 'lethargic', '66666666-6666-6666-6666-666666666666'),

-- group 7
(RANDOM_UUID(), 'big', '77777777-7777-7777-7777-777777777777'),
(RANDOM_UUID(), 'large', '77777777-7777-7777-7777-777777777777'),
(RANDOM_UUID(), 'huge', '77777777-7777-7777-7777-777777777777'),
(RANDOM_UUID(), 'massive', '77777777-7777-7777-7777-777777777777'),

-- group 8
(RANDOM_UUID(), 'small', '88888888-8888-8888-8888-888888888888'),
(RANDOM_UUID(), 'tiny', '88888888-8888-8888-8888-888888888888'),
(RANDOM_UUID(), 'little', '88888888-8888-8888-8888-888888888888'),

-- group 9
(RANDOM_UUID(), 'smart', '99999999-9999-9999-9999-999999999999'),
(RANDOM_UUID(), 'clever', '99999999-9999-9999-9999-999999999999'),
(RANDOM_UUID(), 'intelligent', '99999999-9999-9999-9999-999999999999'),

-- group 10
(RANDOM_UUID(), 'stupid', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
(RANDOM_UUID(), 'dumb', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
(RANDOM_UUID(), 'foolish', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),

-- group 11
(RANDOM_UUID(), 'strong', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
(RANDOM_UUID(), 'powerful', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
(RANDOM_UUID(), 'robust', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),

-- group 12
(RANDOM_UUID(), 'weak', 'cccccccc-cccc-cccc-cccc-cccccccccccc'),
(RANDOM_UUID(), 'fragile', 'cccccccc-cccc-cccc-cccc-cccccccccccc'),
(RANDOM_UUID(), 'feeble', 'cccccccc-cccc-cccc-cccc-cccccccccccc'),

-- group 13
(RANDOM_UUID(), 'rich', 'dddddddd-dddd-dddd-dddd-dddddddddddd'),
(RANDOM_UUID(), 'wealthy', 'dddddddd-dddd-dddd-dddd-dddddddddddd'),
(RANDOM_UUID(), 'affluent', 'dddddddd-dddd-dddd-dddd-dddddddddddd'),

-- group 14
(RANDOM_UUID(), 'poor', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'),
(RANDOM_UUID(), 'needy', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'),
(RANDOM_UUID(), 'destitute', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'),

-- group 15
(RANDOM_UUID(), 'beautiful', 'ffffffff-ffff-ffff-ffff-ffffffffffff'),
(RANDOM_UUID(), 'pretty', 'ffffffff-ffff-ffff-ffff-ffffffffffff'),
(RANDOM_UUID(), 'attractive', 'ffffffff-ffff-ffff-ffff-ffffffffffff'),

-- group 16 (single word)
(RANDOM_UUID(), 'unique', '12121212-1212-1212-1212-121212121212'),

-- group 17 (single word)
(RANDOM_UUID(), 'solo', '13131313-1313-1313-1313-131313131313'),

-- group 18
(RANDOM_UUID(), 'angry', '14141414-1414-1414-1414-141414141414'),
(RANDOM_UUID(), 'mad', '14141414-1414-1414-1414-141414141414'),
(RANDOM_UUID(), 'furious', '14141414-1414-1414-1414-141414141414'),

-- group 19
(RANDOM_UUID(), 'calm', '15151515-1515-1515-1515-151515151515'),
(RANDOM_UUID(), 'peaceful', '15151515-1515-1515-1515-151515151515'),
(RANDOM_UUID(), 'quiet', '15151515-1515-1515-1515-151515151515'),

-- group 20
(RANDOM_UUID(), 'start', '16161616-1616-1616-1616-161616161616'),
(RANDOM_UUID(), 'begin', '16161616-1616-1616-1616-161616161616'),
(RANDOM_UUID(), 'commence', '16161616-1616-1616-1616-161616161616');