-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 27, 2025 at 10:08 AM
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
  `u_pass` varchar(50) NOT NULL,
  `u_accType` varchar(255) NOT NULL,
  `u_email` varchar(255) NOT NULL,
  `u_image` varchar(255) NOT NULL,
  `u_status` varchar(255) NOT NULL,
  `security_question` varchar(255) NOT NULL,
  `security_answer` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_accounts`
--

INSERT INTO `tbl_accounts` (`u_id`, `u_username`, `u_pass`, `u_accType`, `u_email`, `u_image`, `u_status`, `security_question`, `security_answer`) VALUES
(1, 'JohnClarence', '73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8=', 'Admin', 'johnclarence@gmail.com', '', 'Active', '', ''),
(2, 'test', 'FeKw08M4keuw8e9gnsQZQgwg4yDOlMZfvIwzEkSOsiU=', 'Employee', 'test@gmail.com', '', 'Active', 'What\'s your favorite Color?', 'test'),
(3, 'test2', '73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8=', 'Admin', 'testing2@gmail.com', '', 'Active', '', ''),
(4, 'Johnparker', '73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8=', 'Admin', 'Johnparker@gmail.com', '', 'Active', '', ''),
(5, 'dannyboy', '73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8=', 'Employee', 'johnclarence1001@gmail.com', '', 'Active', '', ''),
(7, 'clarence', '73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8=', 'Employee', 'scc@gmail.com', '', 'Active', '', ''),
(11, 'h', '73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8=', 'Employee', 'h@gmail.com', '', 'Deleted', '', ''),
(12, 'test3', '73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8=', 'Employee', 'test3@gmail.com', '', 'Pending', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_logs`
--

CREATE TABLE `tbl_logs` (
  `log_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `u_username` varchar(255) NOT NULL,
  `action_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `log_action` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_logs`
--

INSERT INTO `tbl_logs` (`log_id`, `u_id`, `u_username`, `action_time`, `log_action`) VALUES
(1, 2, 'test', '2025-04-07 04:22:06', 'User Changed Their Password'),
(9, 2, 'test', '2025-04-07 06:26:03', 'Logged as Employee'),
(10, 3, 'test2', '2025-04-07 06:32:25', 'Logged as Admin'),
(11, 2, 'test', '2025-04-10 07:36:25', 'Logged as Employee'),
(12, 2, 'test', '2025-04-10 07:36:44', 'User Changed Their Details'),
(13, 2, 'test', '2025-04-10 08:12:18', 'Forgot, and changed their password'),
(14, 2, 'test', '2025-04-10 08:14:17', 'Forgot, and changed their password'),
(15, 2, 'test', '2025-04-10 08:15:32', 'Forgot, and changed their password'),
(16, 2, 'test', '2025-04-10 08:19:13', 'Forgot, and changed their password'),
(17, 2, 'test', '2025-04-10 08:20:52', 'Forgot, and changed their password'),
(18, 2, 'test', '2025-04-10 08:21:44', 'Forgot, and changed their password'),
(19, 2, 'test', '2025-04-24 02:42:32', 'Logged as Employee'),
(20, 2, 'test', '2025-04-24 02:43:00', 'User Changed Their Details'),
(21, 2, 'test', '2025-05-05 13:06:58', 'Logged as Employee'),
(22, 2, 'test', '2025-05-05 13:07:07', 'Logged Out'),
(23, 3, 'test2', '2025-05-05 13:07:30', 'Logged as Admin'),
(24, 12, 'test3', '2025-05-05 13:20:27', 'Registered for the first time'),
(25, 2, 'test', '2025-05-05 14:05:11', 'Logged as Employee'),
(26, 2, 'test', '2025-05-05 14:05:15', 'Logged Out'),
(27, 3, 'test2', '2025-05-05 14:05:25', 'Logged as Admin'),
(28, 2, 'test', '2025-05-05 14:11:55', 'Logged as Employee'),
(29, 3, 'test2', '2025-05-05 14:14:07', 'Logged as Admin'),
(30, 3, 'test2', '2025-05-05 14:17:31', 'Logged as Admin'),
(31, 3, 'test2', '2025-05-05 14:18:31', 'Logged as Admin'),
(32, 3, 'test2', '2025-05-05 14:20:12', 'Logged as Admin'),
(33, 3, 'test2', '2025-05-05 14:20:38', 'Admin Updated Account: Johnparker'),
(34, 4, 'Johnparker', '2025-05-05 14:31:02', 'Logged as Employee'),
(35, 4, 'Johnparker', '2025-05-05 14:38:03', 'Logged as Admin'),
(36, 4, 'Johnparker', '2025-05-05 14:39:12', 'Logged as Admin'),
(37, 4, 'Johnparker', '2025-05-05 14:39:28', 'Admin Updated Account: dannyboy'),
(38, 4, 'Johnparker', '2025-05-05 14:48:28', 'Logged as Admin'),
(39, 4, 'Johnparker', '2025-05-05 14:52:35', 'Logged as Admin'),
(40, 4, 'Johnparker', '2025-05-05 14:52:48', 'Admin Added The Movie: Caramel'),
(41, 4, 'Johnparker', '2025-05-05 14:52:57', 'Logged Out'),
(42, 5, 'dannyboy', '2025-05-05 14:53:07', 'Logged as Employee'),
(43, 5, 'dannyboy', '2025-05-05 14:53:30', 'User made transaction ID: Caramel'),
(44, 4, 'Johnparker', '2025-05-26 08:45:53', 'Logged as Admin'),
(45, 4, 'Johnparker', '2025-05-26 08:46:11', 'Logged Out'),
(46, 5, 'dannyboy', '2025-05-26 08:46:22', 'Logged as Employee'),
(47, 5, 'dannyboy', '2025-05-26 09:08:43', 'Logged as Employee');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_orders`
--

CREATE TABLE `tbl_orders` (
  `o_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `p_id` int(11) NOT NULL,
  `quantity` int(50) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` varchar(255) NOT NULL,
  `o_total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_orders`
--

INSERT INTO `tbl_orders` (`o_id`, `u_id`, `p_id`, `quantity`, `date`, `status`, `o_total`) VALUES
(1, 5, 1, 1, '2025-05-05 14:53:28', 'Succesful', 30.00);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_products`
--

CREATE TABLE `tbl_products` (
  `p_id` int(20) NOT NULL,
  `p_name` varchar(50) NOT NULL,
  `p_price` decimal(10,2) NOT NULL,
  `p_status` varchar(50) NOT NULL,
  `p_image` varchar(50) NOT NULL,
  `p_sold` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_products`
--

INSERT INTO `tbl_products` (`p_id`, `p_name`, `p_price`, `p_status`, `p_image`, `p_sold`) VALUES
(1, 'Caramel', 30.00, 'Available', '', '1');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_temporary_orders`
--

CREATE TABLE `tbl_temporary_orders` (
  `to_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `p_id` int(11) NOT NULL,
  `quantity` int(50) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `o_total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_accounts`
--
ALTER TABLE `tbl_accounts`
  ADD PRIMARY KEY (`u_id`);

--
-- Indexes for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `fk_log_user_id` (`u_id`);

--
-- Indexes for table `tbl_orders`
--
ALTER TABLE `tbl_orders`
  ADD PRIMARY KEY (`o_id`),
  ADD KEY `pid` (`p_id`),
  ADD KEY `uid` (`u_id`);

--
-- Indexes for table `tbl_products`
--
ALTER TABLE `tbl_products`
  ADD PRIMARY KEY (`p_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_accounts`
--
ALTER TABLE `tbl_accounts`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT for table `tbl_orders`
--
ALTER TABLE `tbl_orders`
  MODIFY `o_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_products`
--
ALTER TABLE `tbl_products`
  MODIFY `p_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_logs`
--
ALTER TABLE `tbl_logs`
  ADD CONSTRAINT `fk_log_user_id` FOREIGN KEY (`u_id`) REFERENCES `tbl_accounts` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_orders`
--
ALTER TABLE `tbl_orders`
  ADD CONSTRAINT `pid` FOREIGN KEY (`p_id`) REFERENCES `tbl_products` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `uid` FOREIGN KEY (`u_id`) REFERENCES `tbl_accounts` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
