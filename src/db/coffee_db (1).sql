-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 24, 2025 at 09:35 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `coffee_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_accounts`
--

CREATE TABLE `tbl_accounts` (
  `u_id` int(11) NOT NULL,
  `u_username` varchar(255) NOT NULL,
  `u_pass` varchar(255) NOT NULL,
  `u_accType` varchar(255) NOT NULL,
  `u_email` varchar(255) NOT NULL,
  `u_status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_accounts`
--

INSERT INTO `tbl_accounts` (`u_id`, `u_username`, `u_pass`, `u_accType`, `u_email`, `u_status`) VALUES
(1, 'JohnClarence', '12345678', 'Admin', 'johnclarence@gmail.com', 'Active'),
(2, 'test', '12345678', 'Employee', 'test@gmail.com', 'Active'),
(3, 'testing2', 'test12345678', 'Employee', 'testing2@gmail.com', 'Pending'),
(4, 'JohnClarence', '123456789', 'Employee', 'jjohnclarence@gmail.com', 'Pending'),
(5, 'JohnClarence1', '12345678', 'Employee', 'johnclarence1001@gmail.com', 'Pending'),
(6, 'Jerry', '12345678', 'Employee', 'testing@gmail.com', 'Pending'),
(7, 'clarence', 'pro090909', 'Employee', 'scc@gmail.com', 'Active');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_accounts`
--
ALTER TABLE `tbl_accounts`
  ADD PRIMARY KEY (`u_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_accounts`
--
ALTER TABLE `tbl_accounts`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
