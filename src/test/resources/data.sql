DELETE FROM PUBLIC.USERS;

INSERT INTO PUBLIC.USERS(NAME, EMAIL)
VALUES ('user1', 'user1@mail.ru');
INSERT INTO PUBLIC.USERS(NAME, EMAIL)
VALUES ('user2', 'user2@mail.ru');

INSERT INTO PUBLIC.ITEMS(name, description, is_available, owner_id, request_id)
 VALUES('item1', 'description1', true, 1, null);
INSERT INTO PUBLIC.ITEMS(name, description, is_available, owner_id, request_id)
VALUES('item2', 'description2', true, 1, null);
INSERT INTO PUBLIC.ITEMS(name, description, is_available, owner_id, request_id)
VALUES('item3', 'description3', false, 1, null);
INSERT INTO PUBLIC.ITEMS(name, description, is_available, owner_id, request_id)
VALUES('item4', 'description4', true, 2, null);

-- INSERT INTO PUBLIC.COMMENTS(text, item_id, author_id, created)
-- VALUES ('comment', 1, 2, CURRENT_DATE);