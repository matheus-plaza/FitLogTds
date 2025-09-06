CREATE TABLE t_user (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        `password` VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_user (name, email, `password`) VALUES ('Admin', 'admin@fitlog.com', '123');
INSERT INTO t_user (name, email, `password`) VALUES ('Matheus Plaza', 'matheus@email.com', '123');