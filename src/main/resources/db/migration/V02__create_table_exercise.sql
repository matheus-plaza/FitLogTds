CREATE TABLE t_exercise (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(100) NOT NULL,
                            muscle_group VARCHAR(50),
                            created_by_user_id BIGINT,

                            FOREIGN KEY (created_by_user_id) REFERENCES t_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Exercícios Globais (created_by_user_id é NULL)
INSERT INTO t_exercise (name, muscle_group) VALUES ('Supino Reto com Barra', 'Peito');
INSERT INTO t_exercise (name, muscle_group) VALUES ('Agachamento Livre', 'Pernas');
INSERT INTO t_exercise (name, muscle_group) VALUES ('Levantamento Terra', 'Costas');
INSERT INTO t_exercise (name, muscle_group) VALUES ('Desenvolvimento com Halteres', 'Ombros');