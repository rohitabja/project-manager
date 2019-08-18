CREATE TABLE IF NOT EXISTS user
(
    id_user int(11) NOT NULL AUTO_INCREMENT,
    nm_first varchar(45) NOT NULL,
    nm_last varchar(45) NOT NULL,
    id_employee int(11) NOT NULL,
    deleted char(1) NOT NULL,
    dt_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_user)
);

CREATE TABLE IF NOT EXISTS project
(
    id_project int(11) NOT NULL AUTO_INCREMENT,
    nm_project varchar(45) NOT NULL,
    dt_start DATE,
    dt_end DATE,
    priority int(11),
    id_user int(11),
    dt_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_project)
);

CREATE TABLE IF NOT EXISTS task
(
    id_task int(11) NOT NULL AUTO_INCREMENT,
    nm_task varchar(45) NOT NULL,
    id_parent_task int(11),
    dt_start DATE,
    dt_end DATE,
    priority int(11),
    is_completed char(1),
    id_project int(11),
    id_user int(11),
    dt_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_task)
);

CREATE TABLE IF NOT EXISTS parent_task
(
    id_parent_task int(11) NOT NULL AUTO_INCREMENT,
    nm_parent_task varchar(45) NOT NULL,
    PRIMARY KEY (id_parent_task)
);