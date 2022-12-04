-- Insert storage room
INSERT INTO room (room_type, id, name, capacity) VALUES ('StorageRoom', 1, 'Storage', 65536);

-- Insert small rooms
INSERT INTO room (room_type, id, name, capacity) VALUES ('ExhibitRoom', 2, 'Gas Station Gallery', 200);
INSERT INTO room (room_type, id, name, capacity) VALUES ('ExhibitRoom', 3, 'Art by Kindergarteners Gallery', 200);
INSERT INTO room (room_type, id, name, capacity) VALUES ('ExhibitRoom', 4, 'NFT Gallery', 200);
INSERT INTO room (room_type, id, name, capacity) VALUES ('ExhibitRoom', 5, 'AI Generated Art Gallery', 200);
INSERT INTO room (room_type, id, name, capacity) VALUES ('ExhibitRoom', 6, 'Pits of Tartarus Gallery', 200);

-- Insert large rooms
INSERT INTO room (room_type, id, name, capacity) VALUES ('ExhibitRoom', 7, 'Cowboys Art Gallery', 300);
INSERT INTO room (room_type, id, name, capacity) VALUES ('ExhibitRoom', 8, 'McDonald Atwater Martyr Gallery', 300);
INSERT INTO room (room_type, id, name, capacity) VALUES ('ExhibitRoom', 9, 'Random Rocks that Look Nice Gallery', 300);
INSERT INTO room (room_type, id, name, capacity) VALUES ('ExhibitRoom', 10, 'Crewmate Fossils Gallery', 300);
INSERT INTO room (room_type, id, name, capacity) VALUES ('ExhibitRoom', 11, 'Museum Backrooms', 300);

-- Insert owner account
-- Note: I filled in the fields first and last name for the owner with placeholder values, in case we
--  are planning to @NOTNULL them later
INSERT INTO person(person_type, id, email, first_name, last_name, password, is_active, salary) VALUES ('Owner', 0, 'admin@mail.museum.com', 'ADMIN_FIRST_NAME', 'ADMIN_LAST_NAME', '$2a$10$xAWrdY3R8dgB8..iwouXu.wseFsJLumW49VfXVahgwFQSl7nabxjm', TRUE, NULL);