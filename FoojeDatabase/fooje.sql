-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sty 29, 2024 at 02:55 AM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fooje`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `cart`
--

CREATE TABLE `cart` (
  `CART_ID` bigint(20) NOT NULL,
  `CLIENT_ID` int(11) NOT NULL,
  `PRODUCT_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `client`
--

CREATE TABLE `client` (
  `CLIENT_ID` int(11) NOT NULL,
  `NAME` text NOT NULL,
  `LASTNAME` text NOT NULL,
  `EMAIL` text NOT NULL,
  `PASSWORD` text NOT NULL,
  `PHONE_NUMBER` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`CLIENT_ID`, `NAME`, `LASTNAME`, `EMAIL`, `PASSWORD`, `PHONE_NUMBER`) VALUES
(1, 'Karol', 'Bagiński', 'karolb98@gmail.com', 'q', '785586648'),
(2, 'Mateusz', 'Szulc', 'mateuszSzulc2001@gmail.com', 'w', '692175662'),
(4, 'Tomasz', 'Baginski', 'tmsbag@gmail.com', 'r', '123456789'),
(5, 'Wiktor', 'Syska', 'wiktorSyska@gmail.com', 'a', '987654321'),
(6, 'Szymon', 'Jański', 'szymi@gmail.com', 't', '632236632');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `favorites`
--

CREATE TABLE `favorites` (
  `FAVORITES_ID` int(11) NOT NULL,
  `CLIENT_ID` int(11) NOT NULL,
  `RESTAURANT_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `favorites`
--

INSERT INTO `favorites` (`FAVORITES_ID`, `CLIENT_ID`, `RESTAURANT_ID`) VALUES
(10, 4, 3),
(21, 4, 97);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `food_type`
--

CREATE TABLE `food_type` (
  `FOOD_TYPE_ID` int(11) NOT NULL,
  `NAME` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `food_type`
--

INSERT INTO `food_type` (`FOOD_TYPE_ID`, `NAME`) VALUES
(1, 'Pizza'),
(2, 'Kebab'),
(3, 'Burger'),
(4, 'Makaron'),
(5, 'Pierogi');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `orders`
--

CREATE TABLE `orders` (
  `ORDERS_ID` int(11) NOT NULL,
  `CLIENT_ID` int(11) NOT NULL,
  `RESTAURANT_ID` int(11) NOT NULL,
  `STATE` text NOT NULL,
  `ORDER_DATE` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`ORDERS_ID`, `CLIENT_ID`, `RESTAURANT_ID`, `STATE`, `ORDER_DATE`) VALUES
(15, 4, 97, 'Anulowany', '14:47 22,01,2024'),
(16, 4, 97, 'złożone', '15:07 22,01,2024');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `orders_details`
--

CREATE TABLE `orders_details` (
  `ORDERS_DETAILS_ID` int(11) NOT NULL,
  `ORDERS_ID` int(11) NOT NULL,
  `PRODUCT_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders_details`
--

INSERT INTO `orders_details` (`ORDERS_DETAILS_ID`, `ORDERS_ID`, `PRODUCT_ID`) VALUES
(23, 15, 9),
(24, 15, 10),
(25, 15, 9),
(26, 16, 10);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `products`
--

CREATE TABLE `products` (
  `PRODUCT_ID` int(11) NOT NULL,
  `RESTAURANT_ID` int(11) NOT NULL,
  `NAME` text NOT NULL,
  `DESCRIPTION` text NOT NULL,
  `WEIGHT` text NOT NULL,
  `PRICE` text NOT NULL,
  `INGREDIENTS` text NOT NULL,
  `FAT` text NOT NULL,
  `CARBON` text NOT NULL,
  `SUGAR` text NOT NULL,
  `PROTEIN` text NOT NULL,
  `SALT` text NOT NULL,
  `FIBER` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`PRODUCT_ID`, `RESTAURANT_ID`, `NAME`, `DESCRIPTION`, `WEIGHT`, `PRICE`, `INGREDIENTS`, `FAT`, `CARBON`, `SUGAR`, `PROTEIN`, `SALT`, `FIBER`) VALUES
(1, 1, 'KING KEBAB MEGA SAMO MIĘSO', 'Tak bardzo przez wszystkich uwielbiany kebab w naszym wykonaninu. W środku znajduje się samo mięso z sosem czosnkowym. SMACZNEGO. ', '2.000 kg', '25.00 zł', 'Tortilla pełnoziarnista, mięso baranie 30%, mięso wołowe 70%, sos czosnkowy. ', '70g', '80g', '15g', '190g', '15g', '13g'),
(2, 1, 'Burger wędkarza', 'Burger niby drwala, niby wędkarza. Zamiast roztopionego sera zaaplikowaliśmy do środka kawałek świerzego pstrąga wyłowionego przez naszego doświadczonego wędkarza.', '600g', '33zł', 'Bułka pszenna, 100g filet z pstrąga, 150g wołowina, pomidor, sałata, boczek, cebula.', '40g', '120g', '10g', '40g', '7g', '5g'),
(3, 2, 'Pizza z salami w stylu neapolitańskim', 'Długo wyrastające ciasto pieczona w kamiennym piecu (420 stopni celc.) wraz z rzemieślniczym sosem pomidorowym z pomidorów sanmarcano. Salami pochodzi prosto od świni z ubojni.  ', '700g', '24.00zł', 'Mąka pszenna 00, pomidory, oliwa, mozzarella, salami', '30g', '100g', '20g', '20g', '17g', '4g'),
(4, 2, 'Pizza neapolitańska z ślimakiem', 'Byliśmy we francji i 8 osób z nas mówiło że slimak super a mi nie smakowało, dlatego też dodaliśmy tą pozycję do menu', '650g', '30zł', 'Mąka pszenna 00, pomidory, oliwa, mozzarella, ślimak', '40g', '150g', '15g', '30g', '15g', '7g'),
(8, 3, 'picka', 'pyszna picka kochani', '750g kg', '25.50zł', 'tajemnica', '45g', '120g', '15g', '35g', '7g', '24g'),
(9, 97, 'Cheesburger bbq', 'Hamburger z dodanym żółtym serem i sosem bbq', '400g', '10.00zł', 'Bułka pszenna, sos bbq, 150g wołowiny, ser cheddar, cebula, ogórek', '30g', '80g', '5g', '15g', '3g', '3g'),
(10, 97, 'Burger na wypasie', 'Burger z dwoma warstwami wołowiny serem, pomidorem i sałatom', '900g', '23.00zł', 'Bułka maślana, wołowina 400g, cebula, bekon, sałata, pomidor, sosy, ser cheddar', '40g', '120g', '8g', '30g', '6g', '4g');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `restaurant`
--

CREATE TABLE `restaurant` (
  `RESTAURANT_ID` int(11) NOT NULL,
  `RESTAURANT_NAME` text NOT NULL,
  `NAME` text NOT NULL,
  `SURNAME` text NOT NULL,
  `EMAIL` text NOT NULL,
  `PASSWORD` text NOT NULL,
  `PHONE_NUMBER` text NOT NULL,
  `STREET_ADDRESS` text NOT NULL,
  `CITY` text NOT NULL,
  `WEB_PAGE` text DEFAULT NULL,
  `FOOD_TYPE` int(11) NOT NULL,
  `ANIMAL_INFO` tinyint(1) NOT NULL,
  `TOILET_INFO` tinyint(1) NOT NULL,
  `CAR_INFO` tinyint(1) NOT NULL,
  `HOURS_MON_INFO` text NOT NULL,
  `HOURS_TUE_INFO` text NOT NULL,
  `HOURS_WEN_INFO` text NOT NULL,
  `HOURS_THU_INFO` text NOT NULL,
  `HOURS_FRI_INFO` text NOT NULL,
  `HOURS_SAT_INFO` text NOT NULL,
  `HOURS_SUN_INFO` text NOT NULL,
  `LATITUDE` double NOT NULL,
  `LONGITUDE` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `restaurant`
--

INSERT INTO `restaurant` (`RESTAURANT_ID`, `RESTAURANT_NAME`, `NAME`, `SURNAME`, `EMAIL`, `PASSWORD`, `PHONE_NUMBER`, `STREET_ADDRESS`, `CITY`, `WEB_PAGE`, `FOOD_TYPE`, `ANIMAL_INFO`, `TOILET_INFO`, `CAR_INFO`, `HOURS_MON_INFO`, `HOURS_TUE_INFO`, `HOURS_WEN_INFO`, `HOURS_THU_INFO`, `HOURS_FRI_INFO`, `HOURS_SAT_INFO`, `HOURS_SUN_INFO`, `LATITUDE`, `LONGITUDE`) VALUES
(1, 'Pasibrzuch', 'Michał', 'Bagiński', 'mbansk@gamail.com', 'e', '721652568', 'Komuny paryskiej 81', 'Wrocław', 'https://github.com/KarBagi', 2, 1, 1, 0, '9:30 - 22:00', '9:30 - 22:00', '9:30 - 22:00', '9:30 - 22:00', '9:30 - 23:00', '9:30 - 23:00', '15:00 - 19:30', 51.128546, 17.027069),
(2, 'Monstera', 'Marcin', 'Bagiński', 'mrcn@gmail.com', 'r', '755643578', 'Kleczkowska 49A', 'Wrocław', 'https://www.radcabaginski.pl', 1, 1, 1, 1, '10:00 - 18:00', '10:00 - 18:00', '10:00 - 18:00', '10:00 - 18:00', '10:00 - 21:00', '10:00 - 21:00', 'Zamknięte', 51.127248, 17.025592),
(3, 'Pizza cap', 'Remigiusz', 'Górecki', 'remik224@gmail.com', 'r', '786698765', 'Kaszubska 4', 'Wrocław', 'https://www.google.com/maps/place/51°07\'12.7\"N+17°01\'47.2\"E/@51.120186,17.0272047,17z/data=!3m1!4b1!4m4!3m3!8m2!3d51.120186!4d17.029785?hl=pl-PL&entry=ttu', 1, 1, 0, 1, '14:00-00:00', '14:00-00:00', '14:00-00:00', '14:00-00:00', '14:00-00:00', '14:00-00:00', '14:00-00:00', 51.114434, 17.035039),
(4, 'Jacobi-Feil', 'Zak', 'Kennelly', 'zkennelly0@mysql.com', 'ghj', '4536687534', 'Stang', 'Wrocław', 'diigo.com', 3, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.106127725, 17.0061614438),
(5, 'Block LLC', 'Alanson', 'Matyukon', 'amatyukon1@sitemeter.com', 'ghj', '7818876919', 'Towne', 'Wrocław', 'usnews.com', 5, 0, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1206582399, 17.0482649527),
(6, 'Tromp Group', 'Rudolph', 'Hoffman', 'rhoffman2@com.com', 'ghj', '9373834814', 'Dorton', 'Wrocław', 'qq.com', 1, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.0987570038, 17.0514663),
(7, 'Legros LLC', 'Sherlock', 'Ginger', 'sginger3@reverbnation.com', 'ghj', '9364428257', 'Talisman', 'Wrocław', 'ucla.edu', 3, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.0990794068, 17.0349317807),
(8, 'Lakin, Keebler and Zulauf', 'Halsey', 'Gainsford', 'hgainsford4@mediafire.com', 'ghj', '2787241706', 'East', 'Wrocław', 'unesco.org', 4, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1206834057, 17.0161096597),
(9, 'Romaguera, Kovacek and Erdman', 'Ulysses', 'Liddon', 'uliddon5@networkadvertising.org', 'ghj', '3683774313', 'Jana', 'Wrocław', 'netvibes.com', 1, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.116997972, 17.0250696527),
(10, 'Kohler, Turner and Wunsch', 'Vincents', 'Kuhnwald', 'vkuhnwald6@macromedia.com', 'ghj', '5653560497', 'Acker', 'Wrocław', 'printfriendly.com', 4, 0, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1073442269, 17.0115635182),
(11, 'Padberg-Gusikowski', 'Sandro', 'Victor', 'svictor7@technorati.com', 'ghj', '5777014480', 'Sunbrook', 'Wrocław', 'ucla.edu', 4, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1182802123, 17.0421303646),
(12, 'Raynor-Muller', 'Noak', 'Purse', 'npurse8@hhs.gov', 'ghj', '2994087054', 'Weeping Birch', 'Wrocław', 'scribd.com', 4, 0, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1175351731, 17.0428424014),
(13, 'Abernathy Inc', 'Leslie', 'Fenna', 'lfenna9@yale.edu', 'ghj', '3399660728', 'Waywood', 'Wrocław', 'elegantthemes.com', 1, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1190139193, 17.0113764442),
(14, 'Luettgen-Schulist', 'Shadow', 'Maybury', 'smayburya@nhs.uk', 'ghj', '3912422635', 'Eggendart', 'Wrocław', 'ifeng.com', 3, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.0974760738, 17.0181413903),
(15, 'Daugherty, Lindgren and Swaniawski', 'Gareth', 'Faulkener', 'gfaulkenerb@fotki.com', 'ghj', '4986038989', 'Waxwing', 'Wrocław', 'skyrock.com', 2, 0, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1200276405, 17.0083104057),
(16, 'Casper, Rippin and Moore', 'Joaquin', 'Mattiello', 'jmattielloc@paypal.com', 'ghj', '7494028112', 'Nancy', 'Wrocław', 'hugedomains.com', 2, 0, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1156392786, 17.0248421256),
(17, 'Cummerata, Hand and Nolan', 'Gun', 'Beatson', 'gbeatsond@wiley.com', 'ghj', '3129191185', 'Jay', 'Wrocław', 'shutterfly.com', 1, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1079965862, 17.044806336),
(18, 'Reilly, Altenwerth and Rau', 'Marve', 'Scotsbrook', 'mscotsbrooke@msu.edu', 'ghj', '4792433210', 'Erie', 'Wrocław', 'blinklist.com', 4, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1056759595, 17.0438610456),
(19, 'Wilkinson, Waters and Greenholt', 'Gasparo', 'Spalding', 'gspaldingf@indiegogo.com', 'ghj', '5841880558', 'Michigan', 'Wrocław', 'ebay.co.uk', 3, 0, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.115297477, 17.0261460888),
(20, 'Daugherty LLC', 'Morty', 'Chellenham', 'mchellenhamg@blinklist.com', 'ghj', '5122153919', 'Evergreen', 'Wrocław', 'issuu.com', 4, 0, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1023543337, 17.0284556175),
(21, 'Thompson, Bailey and Lynch', 'Kristoforo', 'St Pierre', 'kstpierreh@senate.gov', 'ghj', '7321400656', 'Crest Line', 'Wrocław', 'canalblog.com', 3, 0, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1143804474, 17.0397329473),
(22, 'Gutkowski-Russel', 'Brantley', 'Heild', 'bheildi@php.net', 'ghj', '5871886654', 'Sheridan', 'Wrocław', 'fastcompany.com', 1, 0, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1138050868, 17.031280052),
(23, 'Collins, Howe and Bradtke', 'Kimble', 'Higbin', 'khigbinj@google.it', 'ghj', '5495717186', 'Westridge', 'Wrocław', 'globo.com', 5, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1177328779, 17.0070411365),
(24, 'Schinner and Sons', 'Tobe', 'Lashley', 'tlashleyk@topsy.com', 'ghj', '8437753686', 'Fuller', 'Wrocław', 'dyndns.org', 3, 1, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1069740123, 17.0221091263),
(25, 'Smith, Ryan and Leffler', 'Washington', 'Stribling', 'wstriblingl@comsenz.com', 'ghj', '6405594052', 'Dennis', 'Wrocław', 'cisco.com', 5, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1076916234, 17.0242688358),
(26, 'Runolfsson, Mueller and Homenick', 'Dean', 'Sharville', 'dsharvillem@facebook.com', 'ghj', '6576104243', 'Prairie Rose', 'Wrocław', 'mashable.com', 3, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1076626949, 17.0417522719),
(27, 'Padberg-Schuster', 'Winnie', 'Lease', 'wleasen@ehow.com', 'ghj', '5697000426', 'Utah', 'Wrocław', 'jigsy.com', 5, 0, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.103923273, 17.0052838419),
(28, 'Walsh-Walker', 'Andres', 'Rentenbeck', 'arentenbecko@gnu.org', 'ghj', '1477578328', 'Darwin', 'Wrocław', 'newyorker.com', 3, 1, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1055104468, 17.0063353837),
(29, 'Parker-Reichert', 'Lionello', 'Kippins', 'lkippinsp@ustream.tv', 'ghj', '1738796318', 'Rutledge', 'Wrocław', 'dyndns.org', 5, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1078997813, 17.0103651533),
(30, 'Emard, Bradtke and Hirthe', 'Felice', 'Lanyon', 'flanyonq@studiopress.com', 'ghj', '3936382013', 'Vidon', 'Wrocław', 'tripod.com', 1, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1032482782, 17.0289132092),
(31, 'Daniel LLC', 'Alleyn', 'Comins', 'acominsr@ebay.co.uk', 'ghj', '8125213057', 'Di Loreto', 'Wrocław', 'pagesperso-orange.fr', 5, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1042819809, 17.0534829736),
(32, 'Marvin, Herman and Cartwright', 'Orlan', 'Postlewhite', 'opostlewhites@opera.com', 'ghj', '8429983033', 'Clyde Gallagher', 'Wrocław', 'blogger.com', 2, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1052022946, 17.032781661),
(33, 'Ward-Cruickshank', 'Rube', 'Aldersea', 'ralderseat@yelp.com', 'ghj', '8743406391', 'American', 'Wrocław', 'simplemachines.org', 2, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1081897264, 17.0411867457),
(34, 'Trantow, Reilly and Grady', 'Tabby', 'Arnolds', 'tarnoldsu@github.com', 'ghj', '6228326036', 'Nova', 'Wrocław', 'examiner.com', 2, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1092630318, 17.0504494652),
(35, 'McLaughlin, Greenholt and Nader', 'Lanny', 'Spillman', 'lspillmanv@apple.com', 'ghj', '5941177624', 'Dryden', 'Wrocław', 'hp.com', 3, 0, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.0980417048, 17.0243802405),
(36, 'Friesen, Kunze and Aufderhar', 'Wolfy', 'Gillison', 'wgillisonw@webnode.com', 'ghj', '3133160620', 'Algoma', 'Wrocław', 'toplist.cz', 2, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1208522371, 17.0057607212),
(37, 'Klein-Botsford', 'Warner', 'Stanion', 'wstanionx@skype.com', 'ghj', '9707427458', 'Northfield', 'Wrocław', 'businessweek.com', 5, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.118826346, 17.0194210659),
(38, 'Renner LLC', 'Jorge', 'O\'Dyvoie', 'jodyvoiez@woothemes.com', 'ghj', '2567262761', 'Cherokee', 'Wrocław', 'godaddy.com', 5, 0, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1069983842, 17.0121208771),
(39, 'Zboncak, Kessler and Beatty', 'Bryant', 'Blincowe', 'bblincowe10@vimeo.com', 'ghj', '7072108723', 'Sommers', 'Wrocław', 'blogtalkradio.com', 2, 0, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1020849823, 17.0395202827),
(40, 'Hand Group', 'Tanney', 'Obern', 'tobern11@epa.gov', 'ghj', '9602322311', '8th', 'Wrocław', 'globo.com', 5, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1072755854, 17.0391196644),
(41, 'Cremin-Cruickshank', 'Paddie', 'de Quesne', 'pdequesne12@nifty.com', 'ghj', '1561220146', 'Dakota', 'Wrocław', 'nbcnews.com', 3, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1041882176, 17.026795319),
(42, 'Stamm-Osinski', 'Armstrong', 'Dienes', 'adienes13@mapy.cz', 'ghj', '7423095673', 'Bartelt', 'Wrocław', 'businesswire.com', 3, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1186045458, 17.0477953639),
(43, 'Stokes Group', 'Gottfried', 'Zanussii', 'gzanussii14@mapy.cz', 'ghj', '7574881414', 'Gerald', 'Wrocław', 'homestead.com', 3, 0, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1039045338, 17.0311483052),
(44, 'Dach Inc', 'Carolus', 'Sinfield', 'csinfield15@amazon.com', 'ghj', '1487909895', 'Jay', 'Wrocław', 'canalblog.com', 5, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1192569975, 17.0173443898),
(45, 'Satterfield, Schimmel and Gutmann', 'Garrett', 'Grunwall', 'ggrunwall16@abc.net.au', 'ghj', '6801419172', 'Ruskin', 'Wrocław', 'hao123.com', 4, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1140697746, 17.0238784439),
(46, 'Aufderhar, Tillman and Veum', 'Andris', 'Buttrey', 'abuttrey17@multiply.com', 'ghj', '2857974099', 'Karstens', 'Wrocław', 'house.gov', 3, 0, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1113018443, 17.0466530005),
(47, 'Jast-Jacobson', 'Raynard', 'Davidman', 'rdavidman18@redcross.org', 'ghj', '3902347140', 'Dahle', 'Wrocław', 'linkedin.com', 1, 0, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1201209624, 17.0421895633),
(48, 'Raynor, Purdy and Gusikowski', 'Clevie', 'Jest', 'cjest19@dell.com', 'ghj', '7137535191', 'Kinsman', 'Wrocław', 'nbcnews.com', 5, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1218365197, 17.0523750311),
(49, 'Mitchell-Strosin', 'Georges', 'Pimblott', 'gpimblott1a@businesswire.com', 'ghj', '1174620911', 'Independence', 'Wrocław', 'amazon.co.uk', 3, 1, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1139556595, 17.0409689606),
(50, 'Considine Group', 'Lucien', 'Harback', 'lharback1b@sourceforge.net', 'ghj', '9532499359', 'Forest Dale', 'Wrocław', 'businesswire.com', 5, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.110205183, 17.0500823354),
(51, 'Hand and Sons', 'Finley', 'Brandreth', 'fbrandreth1c@weather.com', 'ghj', '4441091586', 'Graedel', 'Wrocław', 'histats.com', 1, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.107104566, 17.0240670179),
(52, 'Mueller-McKenzie', 'Byrann', 'Wardrop', 'bwardrop1d@nba.com', 'ghj', '7821911877', 'Schlimgen', 'Wrocław', 'trellian.com', 3, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1057597518, 17.0318882316),
(53, 'Beer LLC', 'Hinze', 'Skeels', 'hskeels1e@oakley.com', 'ghj', '5017557767', 'Moland', 'Wrocław', 'taobao.com', 4, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1126692076, 17.0200788899),
(54, 'Graham-Huel', 'Franky', 'Bolderstone', 'fbolderstone1f@ezinearticles.com', 'ghj', '8072289133', 'North', 'Wrocław', 'si.edu', 4, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1084239861, 17.0245754568),
(55, 'Schumm-Marks', 'Allen', 'Godart', 'agodart1g@cnn.com', 'ghj', '3613559300', 'Fulton', 'Wrocław', 'parallels.com', 2, 0, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1144748265, 17.0403813876),
(56, 'Haley LLC', 'Weidar', 'Wistance', 'wwistance1h@dion.ne.jp', 'ghj', '7765826412', 'Bobwhite', 'Wrocław', 'naver.com', 5, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1103790068, 17.0352209289),
(57, 'Price, Konopelski and Crona', 'Luis', 'Tewnion', 'ltewnion1i@qq.com', 'ghj', '2764973147', 'Jenna', 'Wrocław', 'istockphoto.com', 5, 0, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1042271177, 17.03146295),
(58, 'D\'Amore LLC', 'Elwood', 'Clout', 'eclout1j@un.org', 'ghj', '5452994498', 'Moose', 'Wrocław', 'google.es', 3, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1005518313, 17.0053476268),
(59, 'Waelchi, Harber and Veum', 'Sinclair', 'Livesley', 'slivesley1k@omniture.com', 'ghj', '9565597724', 'Sutteridge', 'Wrocław', 'mlb.com', 3, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.0984473924, 17.0352714167),
(60, 'Welch Inc', 'Sigismundo', 'Gare', 'sgare1l@washingtonpost.com', 'ghj', '4608879046', 'Pearson', 'Wrocław', 'archive.org', 4, 1, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1015954187, 17.049594421),
(61, 'Wolff-Windler', 'Myron', 'Waldock', 'mwaldock1m@nyu.edu', 'ghj', '8646607620', 'Brentwood', 'Wrocław', 'vistaprint.com', 1, 0, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1170121165, 17.0086235537),
(62, 'Oberbrunner-Rohan', 'Dillon', 'Lambdean', 'dlambdean1n@prlog.org', 'ghj', '4252331460', 'Ronald Regan', 'Wrocław', 'ucoz.ru', 4, 0, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.119855251, 17.0479237232),
(63, 'Graham and Sons', 'Micheil', 'Isoldi', 'misoldi1o@fastcompany.com', 'ghj', '3933933983', 'American', 'Wrocław', 'imgur.com', 2, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1044839862, 17.0201676187),
(64, 'Hilll-Johns', 'Rainer', 'Blaksley', 'rblaksley1p@stanford.edu', 'ghj', '2558346671', 'Kensington', 'Wrocław', 'booking.com', 4, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1198193748, 17.0532329716),
(65, 'Von, Howell and Dibbert', 'Marve', 'Girvan', 'mgirvan1q@live.com', 'ghj', '7758089332', 'Browning', 'Wrocław', 'nyu.edu', 5, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.0993902824, 17.0171070565),
(66, 'Heathcote Group', 'Nigel', 'Zapatero', 'nzapatero1r@bravesites.com', 'ghj', '7661172053', 'Texas', 'Wrocław', 'weather.com', 5, 1, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1182522888, 17.0101170341),
(67, 'Treutel, Leuschke and Reichel', 'Emmanuel', 'Renzo', 'erenzo1s@xrea.com', 'ghj', '3101250228', 'Lyons', 'Wrocław', 'discovery.com', 2, 0, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.0977535267, 17.0075228019),
(68, 'Carroll LLC', 'Shepperd', 'Rosendall', 'srosendall1t@aol.com', 'ghj', '5148185601', 'Dwight', 'Wrocław', 'ted.com', 5, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.0991824248, 17.0169019305),
(69, 'Mante Inc', 'Wait', 'Surgison', 'wsurgison1u@aboutads.info', 'ghj', '8914865251', 'Ilene', 'Wrocław', 'wsj.com', 1, 0, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1114460926, 17.0352920541),
(70, 'Botsford, Gerlach and Schmeler', 'Von', 'Blose', 'vblose1v@businessinsider.com', 'ghj', '5826430845', 'Delaware', 'Wrocław', 'cornell.edu', 3, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1130470441, 17.0106164798),
(71, 'Price, Braun and Leannon', 'Grant', 'Fance', 'gfance1w@wp.com', 'ghj', '8305358112', 'Talisman', 'Wrocław', 'noaa.gov', 1, 1, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1008756845, 17.0157164817),
(72, 'Torp, Turner and Mante', 'Prescott', 'Rembaud', 'prembaud1x@youku.com', 'ghj', '7446647262', 'Bobwhite', 'Wrocław', 'istockphoto.com', 3, 1, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1126373616, 17.0521883568),
(73, 'McCullough-Osinski', 'Basilius', 'McLorinan', 'bmclorinan1y@linkedin.com', 'ghj', '3731581331', 'Ludington', 'Wrocław', 'nature.com', 4, 0, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1150289883, 17.0223668121),
(74, 'Collins Inc', 'Carlie', 'Burdus', 'cburdus1z@ocn.ne.jp', 'ghj', '4686995807', 'Oxford', 'Wrocław', 'altervista.org', 5, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1050709962, 17.0426724105),
(75, 'Parisian, Kshlerin and Farrell', 'Derrek', 'Forsey', 'dforsey20@altervista.org', 'ghj', '1697322925', 'Pepper Wood', 'Wrocław', 'paypal.com', 2, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1213025593, 17.0460358294),
(76, 'Grimes, Waelchi and Hand', 'Drud', 'Kunath', 'dkunath21@intel.com', 'ghj', '3714023488', 'Golf View', 'Wrocław', 'telegraph.co.uk', 3, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1183878785, 17.0090429204),
(77, 'Feeney-Turner', 'Garek', 'Fleckno', 'gfleckno22@army.mil', 'ghj', '7144323484', 'Valley Edge', 'Wrocław', 'bigcartel.com', 4, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1205425313, 17.0135150654),
(78, 'Considine-Bogan', 'Derick', 'Londer', 'dlonder23@last.fm', 'ghj', '3769609437', 'Eastwood', 'Wrocław', 'dagondesign.com', 3, 0, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1104122669, 17.052791585),
(79, 'Reinger-Rogahn', 'Maje', 'Zavattero', 'mzavattero24@arizona.edu', 'ghj', '6292747838', 'Bunting', 'Wrocław', 'nhs.uk', 1, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1040132567, 17.0265645793),
(80, 'Mohr-Towne', 'Barth', 'Gonnely', 'bgonnely25@woothemes.com', 'ghj', '8424610062', 'Leroy', 'Wrocław', 'google.fr', 5, 0, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1155363663, 17.0516369684),
(81, 'Ondricka-McKenzie', 'Rodolph', 'Kendrew', 'rkendrew26@bloglovin.com', 'ghj', '5932151813', 'Summit', 'Wrocław', 'google.co.uk', 1, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.116702363, 17.0065688538),
(82, 'Marvin and Sons', 'Cameron', 'Matteuzzi', 'cmatteuzzi27@delicious.com', 'ghj', '1955887915', 'Kenwood', 'Wrocław', '360.cn', 1, 0, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1184550258, 17.0546688028),
(83, 'Bauch, Thiel and Koss', 'Aluin', 'Benaine', 'abenaine28@tumblr.com', 'ghj', '9282550814', 'Mcguire', 'Wrocław', 'amazonaws.com', 2, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1007142252, 17.0450864086),
(84, 'Mueller LLC', 'Igor', 'Alyukin', 'ialyukin29@nydailynews.com', 'ghj', '7343146115', 'Dennis', 'Wrocław', 'dell.com', 1, 0, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1157200545, 17.0295006853),
(85, 'Larson Inc', 'Germain', 'Ditchfield', 'gditchfield2a@un.org', 'ghj', '5038833883', 'Havey', 'Wrocław', 'nba.com', 3, 0, 0, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1000448164, 17.031203578),
(86, 'Walsh and Sons', 'Hodge', 'Hallahan', 'hhallahan2b@house.gov', 'ghj', '6409110634', 'Meadow Valley', 'Wrocław', 'webs.com', 4, 0, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1187688177, 17.0251754034),
(87, 'Kuhn-Koch', 'Tibold', 'Sterrick', 'tsterrick2c@chronoengine.com', 'ghj', '2271359390', 'Northview', 'Wrocław', 'seesaa.net', 2, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1002502545, 17.0335368904),
(88, 'Swaniawski Group', 'Russell', 'Binner', 'rbinner2d@deliciousdays.com', 'ghj', '7366589438', 'Raven', 'Wrocław', 'house.gov', 4, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1198406689, 17.0053500777),
(89, 'Trantow and Sons', 'Way', 'Aldwich', 'waldwich2e@si.edu', 'ghj', '9979060344', 'Old Shore', 'Wrocław', 'parallels.com', 1, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.0979744776, 17.0280301106),
(90, 'Hintz, Russel and Blick', 'Kingston', 'Hopewell', 'khopewell2f@chron.com', 'ghj', '7563326789', 'Cody', 'Wrocław', 'miibeian.gov.cn', 4, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.104922032, 17.0551645529),
(91, 'Cartwright-Dibbert', 'Nikki', 'Vedeneev', 'nvedeneev2g@sciencedirect.com', 'ghj', '8623303584', 'Fallview', 'Wrocław', 'myspace.com', 5, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1069432733, 17.0444448684),
(92, 'Boehm Group', 'Yulma', 'Griss', 'ygriss2h@example.com', 'ghj', '1834530413', 'Village Green', 'Wrocław', 'amazon.co.uk', 1, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1141939832, 17.0552222091),
(93, 'Ratke, Bradtke and Carter', 'Michel', 'Gullberg', 'mgullberg2i@example.com', 'ghj', '6875052518', 'Lukken', 'Wrocław', 'si.edu', 1, 1, 1, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1185583284, 17.0382744115),
(94, 'Kerluke-Leannon', 'Tannie', 'Lowden', 'tlowden2j@addthis.com', 'ghj', '6711652911', 'Drewry', 'Wrocław', 'java.com', 3, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1152828483, 17.0473357416),
(95, 'Schinner-Smith', 'Andie', 'Blasi', 'ablasi2k@myspace.com', 'ghj', '8332667562', 'Mallard', 'Wrocław', 'angelfire.com', 5, 1, 1, 1, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1222734188, 17.0206813818),
(96, 'Gerlach, Mosciski and Ruecker', 'Ade', 'Cadden', 'acadden2l@blogtalkradio.com', 'ghj', '6824779319', 'Ilene', 'Wrocław', 'discovery.com', 4, 1, 0, 0, '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', '9:00 - 21:00', 51.1147918453, 17.0216179719),
(97, 'Burger Hat', 'Kacper', 'Chwaliński', 'chwalu@gmail.com', 'r', '867754678', 'Prądzyńskiego 24', 'Wrocław', 'www.burgerhat.pl', 3, 1, 1, 0, '9:00 - 20:30', '9:00 - 20:30', '9:00 - 20:30', '9:00 - 20:30', '9:00 - 22:30', '9:00 - 22:30', '12:00 - 19:00', 51.099467, 17.047901);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`CART_ID`),
  ADD KEY `client_cart_fooje` (`CLIENT_ID`),
  ADD KEY `product_cart_fooje` (`PRODUCT_ID`);

--
-- Indeksy dla tabeli `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`CLIENT_ID`);

--
-- Indeksy dla tabeli `favorites`
--
ALTER TABLE `favorites`
  ADD PRIMARY KEY (`FAVORITES_ID`),
  ADD KEY `client_favorites_fooje` (`CLIENT_ID`),
  ADD KEY `restauration_favorite_fooje` (`RESTAURANT_ID`);

--
-- Indeksy dla tabeli `food_type`
--
ALTER TABLE `food_type`
  ADD PRIMARY KEY (`FOOD_TYPE_ID`);

--
-- Indeksy dla tabeli `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`ORDERS_ID`),
  ADD KEY `orders_client_fooje` (`CLIENT_ID`),
  ADD KEY `orders_restauration_fooje` (`RESTAURANT_ID`);

--
-- Indeksy dla tabeli `orders_details`
--
ALTER TABLE `orders_details`
  ADD PRIMARY KEY (`ORDERS_DETAILS_ID`),
  ADD KEY `orders_details_product_fooje` (`PRODUCT_ID`),
  ADD KEY `orders_details_orders_fooje` (`ORDERS_ID`);

--
-- Indeksy dla tabeli `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`PRODUCT_ID`),
  ADD KEY `product_restaurant_fooje` (`RESTAURANT_ID`);

--
-- Indeksy dla tabeli `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`RESTAURANT_ID`),
  ADD KEY `restaurant_food_type_fooje` (`FOOD_TYPE`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `CART_ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `CLIENT_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `favorites`
--
ALTER TABLE `favorites`
  MODIFY `FAVORITES_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `food_type`
--
ALTER TABLE `food_type`
  MODIFY `FOOD_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `ORDERS_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `orders_details`
--
ALTER TABLE `orders_details`
  MODIFY `ORDERS_DETAILS_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `RESTAURANT_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `client_cart_fooje` FOREIGN KEY (`CLIENT_ID`) REFERENCES `client` (`CLIENT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `product_cart_fooje` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `products` (`PRODUCT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `favorites`
--
ALTER TABLE `favorites`
  ADD CONSTRAINT `client_favorites_fooje` FOREIGN KEY (`CLIENT_ID`) REFERENCES `client` (`CLIENT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `restauration_favorite_fooje` FOREIGN KEY (`RESTAURANT_ID`) REFERENCES `restaurant` (`RESTAURANT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_client_fooje` FOREIGN KEY (`CLIENT_ID`) REFERENCES `client` (`CLIENT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `orders_restauration_fooje` FOREIGN KEY (`RESTAURANT_ID`) REFERENCES `restaurant` (`RESTAURANT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `orders_details`
--
ALTER TABLE `orders_details`
  ADD CONSTRAINT `orders_details_orders_fooje` FOREIGN KEY (`ORDERS_ID`) REFERENCES `orders` (`ORDERS_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `orders_details_product_fooje` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `products` (`PRODUCT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `product_restaurant_fooje` FOREIGN KEY (`RESTAURANT_ID`) REFERENCES `restaurant` (`RESTAURANT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `restaurant`
--
ALTER TABLE `restaurant`
  ADD CONSTRAINT `restaurant_food_type_fooje` FOREIGN KEY (`FOOD_TYPE`) REFERENCES `food_type` (`FOOD_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
