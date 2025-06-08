-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2025 at 10:26 PM
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
-- Database: `parkir_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `tariffs`
--

CREATE TABLE `tariffs` (
  `id` int(11) NOT NULL,
  `type` enum('mobil','motor') NOT NULL,
  `rate_per_hour` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tariffs`
--

INSERT INTO `tariffs` (`id`, `type`, `rate_per_hour`) VALUES
(1, 'mobil', 10000),
(2, 'motor', 5000);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` enum('admin','petugas') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'admin1', 'admin123', 'admin'),
(2, 'petugas1', 'petugas123', 'petugas'),
(10, 'petugas2', 'petugas123', 'petugas'),
(14, 'petugas10', 'petugas123', 'petugas');

-- --------------------------------------------------------

--
-- Table structure for table `vehicles`
--

CREATE TABLE `vehicles` (
  `id` int(11) NOT NULL,
  `plate_number` varchar(20) NOT NULL,
  `type` enum('mobil','motor') NOT NULL,
  `checkin_time` datetime NOT NULL,
  `checkout_time` datetime DEFAULT NULL,
  `fee` int(11) DEFAULT NULL,
  `handled_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicles`
--

INSERT INTO `vehicles` (`id`, `plate_number`, `type`, `checkin_time`, `checkout_time`, `fee`, `handled_by`) VALUES
(1, 'B1234ABC', 'mobil', '2025-06-08 08:00:00', '2025-06-08 10:00:00', 10000, 2),
(2, 'D5678XYZ', 'motor', '2025-06-08 09:00:00', '2025-06-08 11:15:00', 6000, NULL),
(3, 'F9876GHI', 'mobil', '2025-06-07 13:00:00', '2025-06-07 15:30:00', 15000, 2),
(8, 'D222HHH', 'mobil', '2025-06-08 13:29:19', '2025-06-08 13:35:22', 5000, 2),
(9, 'A111HHH', 'motor', '2025-06-08 16:42:51', '2025-06-08 16:43:00', 0, 2),
(10, 'A222HHH', 'motor', '2025-06-08 16:44:01', '2025-06-08 16:46:30', 5000, 2),
(11, 'B222JJJ', 'mobil', '2025-06-08 16:44:13', '2025-06-08 16:46:43', 10000, 2),
(12, 'A111XXX', 'motor', '2025-06-08 17:24:45', '2025-06-08 17:28:38', 5000, 2),
(13, 'A222NNN', 'mobil', '2025-06-08 17:24:57', '2025-06-09 01:42:33', 90000, 2),
(14, 'AZZZVVV', 'motor', '2025-06-08 17:28:53', NULL, NULL, 2),
(15, 'A222TTT', 'motor', '2025-06-08 22:51:43', NULL, NULL, 2),
(16, 'B999MMM', 'motor', '2025-06-08 22:51:59', NULL, NULL, 2),
(17, 'A222GGG', 'motor', '2025-06-09 00:23:16', NULL, NULL, 2),
(18, 'A444ZZZ', 'motor', '2025-06-09 01:42:10', NULL, NULL, 2),
(19, 'B555CCC', 'mobil', '2025-06-09 01:42:18', NULL, NULL, 2),
(20, 'A777HHH', 'motor', '2025-06-09 02:44:52', '2025-06-09 02:45:55', 5000, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tariffs`
--
ALTER TABLE `tariffs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `vehicles`
--
ALTER TABLE `vehicles`
  ADD PRIMARY KEY (`id`),
  ADD KEY `handled_by` (`handled_by`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tariffs`
--
ALTER TABLE `tariffs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `vehicles`
--
ALTER TABLE `vehicles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `vehicles`
--
ALTER TABLE `vehicles`
  ADD CONSTRAINT `vehicles_ibfk_1` FOREIGN KEY (`handled_by`) REFERENCES `users` (`id`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
