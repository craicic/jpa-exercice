INSERT INTO publisher (id, name, website)
VALUES (3, 'Intrafin', 'intrafin.eu');
INSERT INTO publisher (id, name, website)
VALUES (4, 'Edge', 'edgeent.fr');

INSERT INTO board_game (id, description, min_age, name, fk_publisher)
VALUES (1, 'A strategy game...', 12, 'Terraforming Mars', 3);
INSERT INTO board_game (id, description, min_age, name, fk_publisher)
VALUES (2, 'A dungeon game...', 8, 'Munchkin', 4);

INSERT INTO theme(id, name, short_desc)
VALUES (5, 'Space', 'This is a useless description');
INSERT INTO theme(id, name, short_desc)
VALUES (6, 'Fantasy', 'This is a useless description');
INSERT INTO theme(id, name, short_desc)
VALUES (7, 'Medieval', 'This is a useless description');

INSERT INTO board_game_theme(fk_game, fk_theme)
VALUES (1, 5);
INSERT INTO board_game_theme(fk_game, fk_theme)
VALUES (2, 6);
INSERT INTO board_game_theme(fk_game, fk_theme)
VALUES (2, 7);