-- CREATE OR REPLACE FUNCTION random_between(low INT ,high INT)
--     RETURNS INT AS
-- $$
-- BEGIN
--     RETURN floor(random()* (high-low + 1) + low);
-- END;
-- $$ language 'plpgsql' STRICT;

INSERT INTO publisher (id, name, website)
SELECT seq, 'publisher_' || seq, 'www.' || MD5(random()::text) || '.com'
FROM generate_series(1, 10) AS seq;

INSERT INTO theme (id, name, short_desc)
SELECT seq, 'theme_' || seq, 'This is a short desc...' || MD5(random()::text)
FROM generate_series(11, 20) AS seq;

INSERT INTO board_game(id, description, min_age, name, fk_publisher)
SELECT seq,
       'This is a desc...' || MD5(random()::text),
       random()::int,
       'title' || seq || MD5(random()::text),
       random_between(1,10)
FROM generate_series(21, 1000) AS seq;

INSERT INTO board_game_theme(fk_game, fk_theme)
SELECT generate_series(21, 1000), random_between(11 ,15);

INSERT INTO board_game_theme(fk_game, fk_theme)
SELECT generate_series(21, 1000), random_between(16 ,20);