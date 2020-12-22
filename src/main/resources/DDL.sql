DROP TABLE IF EXISTS 'users';

CREATE TABLE 'users' (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
