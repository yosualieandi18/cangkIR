-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 18 Des 2023 pada 05.41
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cangkir`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `cart`
--

CREATE TABLE `cart` (
  `UserID` char(5) NOT NULL,
  `CupID` char(5) NOT NULL,
  `Quantity` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `cart`
--

INSERT INTO `cart` (`UserID`, `CupID`, `Quantity`) VALUES
('US012', 'CU004', 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `mscourier`
--

CREATE TABLE `mscourier` (
  `CourierID` char(5) NOT NULL,
  `CourierName` varchar(30) NOT NULL,
  `CourierPrice` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `mscourier`
--

INSERT INTO `mscourier` (`CourierID`, `CourierName`, `CourierPrice`) VALUES
('CO001', 'JNA', 20000),
('CO002', 'TAKA', 19000),
('CO003', 'LoinParcel', 22000),
('CO004', 'IRX', 30000),
('CO005', 'JINJA', 150000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `mscup`
--

CREATE TABLE `mscup` (
  `CupID` char(5) NOT NULL,
  `CupName` varchar(30) NOT NULL,
  `CupPrice` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `mscup`
--

INSERT INTO `mscup` (`CupID`, `CupName`, `CupPrice`) VALUES
('CU001', 'Porcelain small cup', 15000),
('CU003', 'Glass jug', 35000),
('CU004', 'Wooden cup', 8000),
('CU005', 'Ceramic tea cup set', 280000),
('CU006', 'Plastic Jug', 20000),
('CU007', 'Plastic small cup', 12000),
('CU008', 'Plastic normal cup', 17000),
('CU009', 'Japanese tea cup', 100000),
('CU011', 'hahaha cup', 67000),
('CU013', 'hohoho cup', 80000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `msuser`
--

CREATE TABLE `msuser` (
  `UserID` char(5) NOT NULL,
  `Username` varchar(30) NOT NULL,
  `UserEmail` varchar(50) NOT NULL,
  `UserPassword` varchar(20) NOT NULL,
  `UserGender` varchar(10) NOT NULL,
  `UserRole` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `msuser`
--

INSERT INTO `msuser` (`UserID`, `Username`, `UserEmail`, `UserPassword`, `UserGender`, `UserRole`) VALUES
('US001', 'swiftee', 'swiftee@gmail.com', 'swiftee123', 'Male', 'Admin'),
('US002', 'efren', 'efren@gmail.com', 'efrnnthnl12', 'Male', 'Admin'),
('US003', 'vncnt', 'vincent@gmail.com', 'njnjnjnj33', 'Male', 'User'),
('US004', 'obrt', 'obort@gmail.com', 'makanIk4n', 'Male', 'Admin'),
('US005', 'feryf', 'nandi@gmail.com', 'frryndi22', 'Male', 'User'),
('US006', 'tester', 'tester@gmail.com', 'tester123', 'Female', 'User'),
('US007', 'admintester', 'admin@gmail.com', 'admin123', 'Male', 'Admin'),
('US008', 'cladmin', 'cl@gmail.com', 'cl123456', 'Male', 'Admin'),
('US009', 'jingyuansayang', 'starrail@gmail.com', 'jingyuan123', 'Male', 'User'),
('US010', 'stevebauadmin', 'steve@gmail.com', 'steve123', 'Male', 'Admin'),
('US011', 'yosua', 'yosua@gmail.com', 'yosua123', 'Male', 'User'),
('US012', 'bernut', 'bernut@gmail.com', 'bernut123', 'Male', 'User'),
('US013', 'allyssa', 'allyssa@gmail.com', 'allyssa1', 'Female', 'User'),
('US015', 'bearnut', 'PDIP@gmail.com', 'abcde12345', 'Male', 'User'),
('US016', 'hihihi', '17000@gmail.com', 'hahahahah123', 'Male', 'User');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `TransactionID` char(5) NOT NULL,
  `CupID` char(5) NOT NULL,
  `Quantity` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transactiondetail`
--

INSERT INTO `transactiondetail` (`TransactionID`, `CupID`, `Quantity`) VALUES
('TR001', 'CU001', 3),
('TR002', 'CU008', 2),
('TR003', 'CU005', 1),
('TR004', 'CU007', 10),
('TR004', 'CU008', 2),
('TR006', 'CU001', 5),
('TR007', 'CU008', 4),
('TR008', 'CU009', 2),
('TR009', 'CU008', 7),
('TR010', 'CU009', 2),
('TR012', 'CU008', 1),
('TR013', 'CU006', 1),
('TR014', 'CU003', 1),
('TR014', 'CU008', 1),
('TR015', 'CU006', 1),
('TR015', 'CU009', 4),
('TR016', 'CU009', 1),
('TR017', 'CU009', 2),
('TR018', 'CU006', 1),
('TR018', 'CU007', 1),
('TR018', 'CU009', 1),
('TR019', 'CU003', 6),
('TR019', 'CU005', 3),
('TR019', 'CU007', 1),
('TR019', 'CU009', 1),
('TR020', 'CU004', 4),
('TR020', 'CU006', 3),
('TR021', 'CU003', 18),
('TR021', 'CU005', 20),
('TR022', 'CU001', 20),
('TR022', 'CU003', 20),
('TR022', 'CU004', 20),
('TR022', 'CU005', 20),
('TR022', 'CU006', 20),
('TR022', 'CU007', 20),
('TR022', 'CU008', 20),
('TR022', 'CU009', 20),
('TR024', 'CU005', 1),
('TR025', 'CU009', 1),
('TR025', 'CU011', 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transactionheader`
--

CREATE TABLE `transactionheader` (
  `TransactionID` char(5) NOT NULL,
  `UserID` char(5) NOT NULL,
  `CourierID` char(5) NOT NULL,
  `TransactionDate` date NOT NULL,
  `UseDeliveryInsurance` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transactionheader`
--

INSERT INTO `transactionheader` (`TransactionID`, `UserID`, `CourierID`, `TransactionDate`, `UseDeliveryInsurance`) VALUES
('TR001', 'US001', 'CO001', '2023-01-19', 0),
('TR002', 'US001', 'CO002', '2023-01-20', 1),
('TR003', 'US001', 'CO002', '2023-02-19', 1),
('TR004', 'US002', 'CO003', '2022-01-19', 0),
('TR005', 'US003', 'CO004', '2021-01-19', 0),
('TR006', 'US004', 'CO001', '2022-05-19', 1),
('TR007', 'US004', 'CO002', '2022-03-02', 1),
('TR008', 'US004', 'CO002', '2023-04-25', 1),
('TR009', 'US005', 'CO005', '2023-04-10', 0),
('TR010', 'US005', 'CO001', '2022-04-29', 0),
('TR011', 'US001', 'CO004', '2023-05-19', 0),
('TR012', 'US001', 'CO005', '2023-05-19', 0),
('TR013', 'US001', 'CO004', '2023-05-19', 0),
('TR014', 'US001', 'CO001', '2023-05-19', 1),
('TR015', 'US001', 'CO004', '2023-05-20', 1),
('TR016', 'US009', 'CO004', '2023-05-20', 1),
('TR017', 'US010', 'CO001', '2023-05-20', 0),
('TR018', 'US011', 'CO001', '2023-12-13', 0),
('TR019', 'US011', 'CO001', '2023-12-13', 0),
('TR020', 'US011', 'CO005', '2023-12-13', 1),
('TR021', 'US015', 'CO005', '2023-12-15', 1),
('TR022', 'US015', 'CO001', '2023-12-15', 1),
('TR024', 'US011', 'CO005', '2023-12-16', 0),
('TR025', 'US011', 'CO002', '2023-12-16', 1);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`UserID`,`CupID`),
  ADD KEY `CupID` (`CupID`) USING BTREE,
  ADD KEY `UserID` (`UserID`) USING BTREE;

--
-- Indeks untuk tabel `mscourier`
--
ALTER TABLE `mscourier`
  ADD PRIMARY KEY (`CourierID`);

--
-- Indeks untuk tabel `mscup`
--
ALTER TABLE `mscup`
  ADD PRIMARY KEY (`CupID`);

--
-- Indeks untuk tabel `msuser`
--
ALTER TABLE `msuser`
  ADD PRIMARY KEY (`UserID`);

--
-- Indeks untuk tabel `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD PRIMARY KEY (`TransactionID`,`CupID`),
  ADD KEY `transactiondetail_ibfk_3` (`CupID`);

--
-- Indeks untuk tabel `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `transactionheader_ibfk_1` (`UserID`),
  ADD KEY `transactionheader_ibfk_2` (`CourierID`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `msuser` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`CupID`) REFERENCES `mscup` (`CupID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD CONSTRAINT `FK_detailheader` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transactiondetail_ibfk_3` FOREIGN KEY (`CupID`) REFERENCES `mscup` (`CupID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD CONSTRAINT `transactionheader_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `msuser` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transactionheader_ibfk_2` FOREIGN KEY (`CourierID`) REFERENCES `mscourier` (`CourierID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
