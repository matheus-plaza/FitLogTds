--treino a ou treino b ...

CREATE TABLE t_workout_routine (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   name VARCHAR(100) NOT NULL,
                                   description TEXT,
                                   user_id BIGINT NOT NULL,

                                   FOREIGN KEY (user_id) REFERENCES t_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- usuário Matheus (id=2)
INSERT INTO t_workout_routine (name, description, user_id) VALUES ('Treino A - Peitoral e Tríceps', 'Foco em força e hipertrofia', 2);