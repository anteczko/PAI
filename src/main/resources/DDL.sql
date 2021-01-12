CREATE DATABASE stm_db;

CREATE USER IF NOT EXISTS 'stm_user'@'localhost' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON * . * TO 'stm_user'@'localhost';


