CREATE SCHEMA IF NOT EXISTS sbatch_training;

USE sbatch_training;

CREATE TABLE IF NOT EXISTS transaction (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(255) DEFAULT NULL,
  userId int(11) DEFAULT NULL,
  transactionDate date DEFAULT NULL,
  amount double DEFAULT NULL,
  PRIMARY KEY (id)
);

DESCRIBE transaction;

SELECT * FROM transaction;

#TRUNCATE transaction;