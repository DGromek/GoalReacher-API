INSERT INTO app_user(email, first_name, last_name, password) VALUES
('a.kowalski@gmail.com', 'Adam', 'Kowalski', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('b.nowak@gmail.com', 'Bartosz', 'Nowak', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('c.ebula@gmail.com', 'Celina', 'Ebula', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('j.wick@gmail.com', 'John', 'Wick', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('m.wazowski@gmail.com', 'Mike', 'Wazowski', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('s.sullivan@gmail.com', 'Sully', 'Sulivan', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq');

INSERT INTO app_group(description, guid, name) VALUES
('Grupa testowa A', 'AAAAAA', 'A Grupa'),
('Grupa testowa B', 'BBBBBB', 'B Grupa'),
('Grupa testowa C', 'CCCCCC', 'C Grupa'),
('Grupa testowa D', 'DDDDDD', 'D Grupa'),
('Grupa testowa F', 'FFFFFF', 'F Grupa'),
('Grupa testowa G', 'GGGGGG', 'G Grupa');

INSERT INTO user_group(google_calendar, status, group_id, user_id) VALUES
(false, 0, 1, 1),
(false, 0, 2, 2),
(false, 0, 3, 3),
(false, 0, 4, 4),
(false, 0, 5, 5),
(false, 0, 6, 6),
(false, 1, 2, 1),
(false, 2, 3, 2),
(false, 1, 4, 2),
(false, 3, 5, 3),
(false, 1, 6, 4),
(false, 2, 1, 5),
(false, 1, 2, 5),
(false, 2, 3, 5),
(false, 1, 4, 5),
(false, 3, 6, 5);

