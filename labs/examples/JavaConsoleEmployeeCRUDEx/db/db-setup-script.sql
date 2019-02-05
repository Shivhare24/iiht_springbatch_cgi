CREATE SCHEMA spring_training;

USE spring_training;

CREATE TABLE employee (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  age int(11) DEFAULT NULL,
  department varchar(255) DEFAULT NULL,
  designation varchar(255) DEFAULT NULL,
  country varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
);

DESCRIBE employee;

SELECT * FROM employee;