INSERT INTO appuser(email, first_name, last_name, password) VALUES
('a.kowalski@gmail.com', 'Adam', 'Kowalski', '$2a$10$DE2NOZq3QQux4acj944fq.kGo.NjwNeCsCZyCjlMaerkpPsfHoLyq'),
('b.nowak@gmail.com', 'Bartosz', 'Nowak', '$2a$10$DE2NOZq3QQux4acj944fq.kGo.NjwNeCsCZyCjlMaerkpPsfHoLyq'),
('c.ebula@gmail.com', 'Celina', 'Ebula', '$2a$10$DE2NOZq3QQux4acj944fq.kGo.NjwNeCsCZyCjlMaerkpPsfHoLyq');

INSERT INTO appgroup(description, guid, name) VALUES
('Grupa testowa A', 'AAAAAA', 'Grupa A'),
('Grupa testowa B', 'BBBBBB', 'Grupa B'),
('Grupa testowa C', 'CCCCCC', 'Grupa C');

INSERT INTO user_group(google_calendar, status, group_id, user_id) VALUES
(false, 1, 1, 1),
(false, 2, 2, 2),
(false, 3, 1, 3),
(false, 1, 2, 2),
(false, 3, 2, 1),
(false, 1, 3, 3),
(false, 3, 3, 1);


