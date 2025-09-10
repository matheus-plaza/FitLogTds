CREATE TABLE t_logged_exercise (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   session_id BIGINT NOT NULL,
                                   exercise_id BIGINT NOT NULL,

                                   FOREIGN KEY (session_id) REFERENCES t_workout_session(id),
                                   FOREIGN KEY (exercise_id) REFERENCES t_exercise(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Na sessão de treino de ontem (id=1), o Matheus registrou 2 exercícios:
-- Supino Reto (id=1 do catálogo de exercícios)
-- Agachamento Livre (id=2 do catálogo de exercícios)
INSERT INTO t_logged_exercise (session_id, exercise_id) VALUES (1, 1);
INSERT INTO t_logged_exercise (session_id, exercise_id) VALUES (1, 2);