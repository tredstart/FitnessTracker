INSERT INTO users (first_name, last_name, email, birthdate) VALUES ('Jan', 'Kowalski', 'jan.kowalski@example.com', '1990-01-15');
INSERT INTO users (first_name, last_name, email, birthdate) VALUES ('Anna', 'Nowak', 'anna.nowak@example.com', '1985-06-20');

INSERT INTO trainings (user_id, start_time, end_time, activity_type, distance, average_speed) VALUES (1, '2024-01-10 08:00:00', '2024-01-10 09:00:00', 'RUNNING', 10.5, 10.5);
INSERT INTO trainings (user_id, start_time, end_time, activity_type, distance, average_speed) VALUES (2, '2024-01-11 07:00:00', '2024-01-11 08:30:00', 'CYCLING', 25.0, 16.7);

INSERT INTO statistics (user_id, total_trainings, total_distance, total_calories_burned) VALUES (1, 5, 42.0, 1200);
INSERT INTO statistics (user_id, total_trainings, total_distance, total_calories_burned) VALUES (2, 3, 75.0, 900);

INSERT INTO health_metrics (user_id, date, weight, height, heart_rate) VALUES (1, '2024-01-10', 75.5, 180.0, 72.0);
INSERT INTO health_metrics (user_id, date, weight, height, heart_rate) VALUES (2, '2024-01-11', 62.0, 165.0, 68.0);