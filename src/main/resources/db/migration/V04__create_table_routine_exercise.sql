-- tablea de relacionamento para a rotinas(treino a, b, ...) e exercicios(supino, desenvolvimento), n x n

CREATE TABLE t_routine_exercise (
                                    routine_id BIGINT NOT NULL,
                                    exercise_id BIGINT NOT NULL,

                                    PRIMARY KEY (routine_id, exercise_id),
                                    FOREIGN KEY (routine_id) REFERENCES t_workout_routine(id),
                                    FOREIGN KEY (exercise_id) REFERENCES t_exercise(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- (id=1) Matheus
-- Supino Reto (id=1) Desenvolvimento (id=4)
INSERT INTO t_routine_exercise (routine_id, exercise_id) VALUES (1, 1);
INSERT INTO t_routine_exercise (routine_id, exercise_id) VALUES (1, 4);