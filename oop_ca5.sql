DROP DATABASE IF EXISTS oop_ca5_richard_collins;
CREATE DATABASE oop_ca5_richard_collins;
USE oop_ca5_richard_collins;
Drop TABLE IF EXISTS course, student, student_courses;

CREATE TABLE `course` (
  `courseid` varchar(10) NOT NULL,
  `level` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `institution` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `student` (
  `caoNumber` int(8) NOT NULL,
  `dateOfBirth` varchar(10) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `student_courses` (
  `caoNumber` int(8) NOT NULL,
  `courseid` varchar(10) NOT NULL,
  `order` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `course`
  ADD PRIMARY KEY (`courseid`);


ALTER TABLE `student`
  ADD PRIMARY KEY (`caoNumber`);
COMMIT;


INSERT into student values
(12345678, "2001-07-12", "password123"),
(87654321, "2000-08-22", "superduper222"),
(43215678, "1999-01-01", "yomamayo3"),
(56784321, "2001-06-30", "hjsdajdb123");

INSERT INTO course VALUES
("DK700", 7, "Computer Science", "DkIT"),
("NUIM321", 8, "Primary School Teaching", "NUIM"),
("NUIG621", 8, "Maths", "NUIG"),
("DK821", 8, "Computer Science", "DkIT"),
("DCU542", 8, "Business", "DCU");

INSERT INTO student_courses VALUES
("12345678", "DK821", 2),
("12345678", "DK700", 3),
("12345678", "NUIM321", 1),
("87654321", "DK821", 1),
("87654321", "DCU542", 2),
("87654321", "NUIM321", 3),
("43215678", "DK821", 2),
("43215678", "NUIG621", 3),
("43215678", "NUIM321", 1),
("56784321", "DCU542", 1),
("56784321", "NUIG621", 2),
("56784321", "DK821", 3);


