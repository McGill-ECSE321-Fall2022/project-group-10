-- Insert small rooms
INSERT INTO room (room_type, id, name, capacity) VALUES ('SMALL', 1, 'STONE AGE GALLERY', 200);
INSERT INTO room (room_type, id, name, capacity) VALUES ('SMALL', 2, 'FRENCH REVOLUTION GALLERY', 200);
INSERT INTO room (room_type, id, name, capacity) VALUES ('SMALL', 3, 'DINOSAUR BONES DISPLAY ROOM', 200);
INSERT INTO room (room_type, id, name, capacity) VALUES ('SMALL', 4, 'RENAISSANCE PAINTINGS GALLERY', 200);
INSERT INTO room (room_type, id, name, capacity) VALUES ('SMALL', 5, 'PITS OF TARTARUS GALLERY', 200);

-- Insert large rooms
INSERT INTO room (room_type, id, name, capacity) VALUES ('LARGE', 6, 'F*MBOYS ART GALLERY', 300);
INSERT INTO room (room_type, id, name, capacity) VALUES ('LARGE', 7, 'MCDONALD ATWATER MARTYR GALLERY', 300);
INSERT INTO room (room_type, id, name, capacity) VALUES ('LARGE', 8, 'RANDOM ROCKS THAT LOOK NICE GALLERY', 300);
INSERT INTO room (room_type, id, name, capacity) VALUES ('LARGE', 9, 'CREWMATE FOSSILS GALLERY', 300);
INSERT INTO room (room_type, id, name, capacity) VALUES ('LARGE', 10, 'MUSEUM BACKROOMS', 300);

-- Insert storage room
INSERT INTO room (room_type, id, name, capacity) VALUES ('STORAGE', 11, 'STORAGE', 65536);

-- Insert owner account
-- Note: I filled in the fields first and last name for the owner with placeholder values, in case we
--  are planning to @NOTNULL them later
INSERT INTO person(person_type, id, email, first_name, last_name, password, is_active, salary, shopping_cart_id) VALUES ('OWNER', 1, 'admin@mail.museum.com', 'ADMIN_FIRST_NAME', 'ADMIN_LAST_NAME', 'admin', TRUE, NULL, NULL);

-- Insert Calendar
INSERT INTO calendar (id, is_museum_open) VALUES (1, TRUE);