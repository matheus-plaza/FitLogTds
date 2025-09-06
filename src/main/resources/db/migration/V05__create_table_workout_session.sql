--registro de que um treino foi executado em um dia expecifico

CREATE TABLE t_workout_session (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   session_date DATE NOT NULL,
                                   duration_in_minutes INT,
                                   notes TEXT,
                                   user_id BIGINT NOT NULL,

                                   FOREIGN KEY (user_id) REFERENCES t_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Inserindo uma sessão de treino completa para o usuário Matheus (id=2)
-- Supondo que o treino foi ontem
INSERT INTO t_workout_session (session_date, duration_in_minutes, notes, user_id)
VALUES (CURDATE() - INTERVAL 1 DAY, 60, 'Treino foi bom, senti bastante o peito.', 2);