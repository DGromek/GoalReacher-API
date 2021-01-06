INSERT INTO app_user(email, first_name, last_name, password) VALUES
('a.kowalski@gmail.com', 'Adam', 'Kowalski', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('b.nowak@gmail.com', 'Bartosz', 'Nowak', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('c.ebula@gmail.com', 'Celina', 'Ebula', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('j.wick@gmail.com', 'John', 'Wick', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('m.wazowski@gmail.com', 'Mike', 'Wazowski', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('s.sullivan@gmail.com', 'Sully', 'Sulivan', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('qq@gmail.com', 'q', 'q', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('ww@gmail.com', 'w', 'w', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('ee@gmail.com', 'e', 'e', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('rr@gmail.com', 'r', 'r', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('tt@gmail.com', 't', 't', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('yy@gmail.com', 'y', 'y', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('uu@gmail.com', 'u', 'u', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('ii@gmail.com', 'i', 'i', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('oo@gmail.com', 'o', 'o', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq'),
('pp@gmail.com', 'p', 'p', '$2a$10$UMHEGIuKed.l0tTbZDGExe0Q6lywnCC0VMgjbOBa9H2WuDKbpwPuq');


INSERT INTO app_group(description, guid, name, is_locked) VALUES
('Grupa testowa A', 'AAAAAA', 'A Grupa', false),
('Grupa testowa B', 'BBBBBB', 'B Grupa', false),
('Grupa testowa C', 'CCCCCC', 'C Grupa', false),
('Grupa testowa D', 'DDDDDD', 'D Grupa', false),
('Grupa testowa F', 'FFFFFF', 'F Grupa', false),
('Grupa testowa G', 'GGGGGG', 'G Grupa', false);


INSERT INTO user_group(google_calendar, role, group_id, user_id) VALUES
(false, 0, 1, 1),
(false, 0, 2, 2),
(false, 0, 3, 3),
(false, 0, 4, 4),
(false, 0, 5, 5),
(false, 0, 6, 6),
(false, 1, 2, 1),
(false, 2, 3, 2),
(false, 1, 4, 2),
(false, 2, 5, 3),
(false, 1, 6, 4),
(false, 2, 1, 5),
(false, 1, 2, 5),
(false, 2, 3, 5),
(false, 1, 4, 5),
(false, 3, 6, 5);
(false, 3, 5, 7),
(false, 3, 5, 8),
(false, 3, 5, 9),
(false, 3, 5, 10),
(false, 3, 5, 11),
(false, 3, 5, 12),
(false, 3, 5, 13),
(false, 3, 5, 14),
(false, 3, 5, 15),
(false, 3, 5, 16);

INSERT INTO invitation(group_id, invited_id, inviting_id) VALUES
(5, 1, 5);

INSERT INTO event(datetime, description, name, group_id) values
('2020-12-2 04:00:00', 'Event na 2 grudnia', 'Event 2', 5),
('2020-12-4 12:00:00', 'Event na 4 grudnia', 'Event 4', 5),
('2020-12-3 11:00:00', 'Event na 3 grudnia', 'Event 3', 5),
('2020-12-5 18:00:00', 'Event na 5 grudnia', 'Event 5', 5),
('2020-12-6 09:00:00', 'Event na 6 grudnia', 'Event 6', 5);

INSERT INTO note(title, content, author_id, group_id) VALUES
('Notatka wazowskiego', 'Tresc notatki wazowskiego', 5, 5),
('Notatka ebuli', 'Tresc notatki ebuli', 3, 5);