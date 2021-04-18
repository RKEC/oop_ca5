-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 18, 2021 at 05:59 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `oop_ca5_richard_collins`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `courseid` varchar(10) NOT NULL,
  `level` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `institution` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`courseid`, `level`, `title`, `institution`) VALUES
('DCU542', 8, 'Business', 'DCU'),
('DK700', 7, 'Computer Science', 'DkIT'),
('DK821', 8, 'Computer Science', 'DkIT'),
('NUIG621', 8, 'Maths', 'NUIG'),
('NUIM321', 8, 'Primary School Teaching', 'NUIM');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `caoNumber` int(8) NOT NULL,
  `dateOfBirth` varchar(10) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`caoNumber`, `dateOfBirth`, `password`) VALUES
(12345678, '2001-07-12', 'password123'),
(43215678, '1999-01-01', 'yomamayo3'),
(56784321, '2001-06-30', 'hjsdajdb123'),
(87654321, '2000-08-22', 'superduper222');

-- --------------------------------------------------------

--
-- Table structure for table `student_courses`
--

CREATE TABLE `student_courses` (
  `caoNumber` int(8) NOT NULL,
  `courseid` varchar(10) NOT NULL,
  `order` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_courses`
--

INSERT INTO `student_courses` (`caoNumber`, `courseid`, `order`) VALUES
(12345678, 'DK821', 2),
(12345678, 'DK700', 3),
(12345678, 'NUIM321', 1),
(87654321, 'DK821', 1),
(87654321, 'DCU542', 2),
(87654321, 'NUIM321', 3),
(43215678, 'DK821', 2),
(43215678, 'NUIG621', 3),
(43215678, 'NUIM321', 1),
(56784321, 'DCU542', 1),
(56784321, 'NUIG621', 2),
(56784321, 'DK821', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`courseid`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`caoNumber`);

--
-- Indexes for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD KEY `caoNumber` (`caoNumber`),
  ADD KEY `courseid` (`courseid`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD CONSTRAINT `student_courses_ibfk_1` FOREIGN KEY (`caoNumber`) REFERENCES `student` (`caoNumber`),
  ADD CONSTRAINT `student_courses_ibfk_2` FOREIGN KEY (`courseid`) REFERENCES `course` (`courseid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
