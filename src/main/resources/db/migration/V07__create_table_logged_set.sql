CREATE TABLE t_logged_set (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              set_number INT NOT NULL,
                              repetitions INT,
                              weight DECIMAL(10, 2),
                              notes VARCHAR(255),
                              logged_exercise_id BIGINT NOT NULL,

                              FOREIGN KEY (logged_exercise_id) REFERENCES t_logged_exercise(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Adicionando as séries para os exercícios registrados
-- Para o Supino Reto (logged_exercise_id = 1)
INSERT INTO t_logged_set (set_number, repetitions, weight, logged_exercise_id) VALUES (1, 12, 80.0, 1);
INSERT INTO t_logged_set (set_number, repetitions, weight, logged_exercise_id) VALUES (2, 10, 85.0, 1);
INSERT INTO t_logged_set (set_number, repetitions, weight, logged_exercise_id) VALUES (3, 8, 85.0, 1);

-- Para o Agachamento Livre (logged_exercise_id = 2)
INSERT INTO t_logged_set (set_number, repetitions, weight, logged_exercise_id) VALUES (1, 12, 100.0, 2);
INSERT INTO t_logged_set (set_number, repetitions, weight, logged_exercise_id) VALUES (2, 12, 100.0, 2);
INSERT INTO t_logged_set (set_number, repetitions, weight, logged_exercise_id) VALUES (3, 10, 110.0, 2);