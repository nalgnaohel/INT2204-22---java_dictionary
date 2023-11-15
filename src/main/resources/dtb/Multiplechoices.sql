-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 15, 2023 at 05:37 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Dictionary`
--

-- --------------------------------------------------------

--
-- Table structure for table `Multiplechoices`
--

CREATE TABLE `Multiplechoices` (
  `ID` int(11) NOT NULL,
  `Question` text NOT NULL,
  `A` text NOT NULL,
  `B` text NOT NULL,
  `C` text NOT NULL,
  `D` text NOT NULL,
  `Answer` text NOT NULL,
  `Explaination` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Multiplechoices`
--

INSERT INTO `Multiplechoices` (`ID`, `Question`, `A`, `B`, `C`, `D`, `Answer`, `Explaination`) VALUES
(1, 'Choose the best answer to complete each sentence.\r\nWe are able to advise people what their legal ___________ are.', 'conscience', 'entitlement', 'decision', 'judge', 'B', 'conscience(n): lương tâm\r\nentitlement(n): quyền, được phép làm\r\ndecision(n): quyết định\r\njudge(n): quan toà, thẩm phán\r\n=> We are able to advise people what their legal.\r\nTạm dịch: Chúng ta có thể đưa ra lời khuyên cho mọi người về quyền lợi hợp pháp của họ là gì.'),
(2, 'Choose the best answer to complete each sentence.\r\nMany school clubs are _______ efforts to attract more students to join.', 'doing', 'giving', 'cutting', 'making', 'D', 'Cụm từ: make efforts/ make an effort (nỗ lực)\r\nMany school clubs are making efforts to attract more students to join.\r\nTạm dịch: Nhiều câu lạc bộ trong trường đang nỗ lực để thu hút nhiều sinh viên tham gia.'),
(3, 'Choose the best answer to complete each sentence.\r\nWhenever problems come up, we discuss them frankly and find solutions quickly.', 'happen', 'encounter', 'arrive', 'clean', 'A', 'come up (v): xuất hiện, phát sinh.\r\nLời giải chi tiết :\r\ncome up (v): xảy ra (một cách đột ngột)\r\nhappen (v): xảy ra\r\narrive (v): đến\r\nencounter (v): đối mặt\r\nclean (v): lau dọn</p>\r\n\r\n=> come up = happen\r\nWhenever problems happen, we discuss them frankly and find solutions quickly.\r\nTạm dịch: Bất cứ khi nào xảy ra vấn đề, chúng tôi đều thảo luận một cách thẳng thắn và tìm hướng giải quyết nhanh chóng.'),
(4, 'Choose the best answer to complete each sentence.\r\nPeter tried his best and passed the driving test at the first _______.', 'try', 'attempt', 'doing', 'aim', 'B', 'try (n): cố gắng\r\ndoing (v-ing): làm \r\nattempt (n) nỗ lực, thử\r\naim (n): mục tiêu\r\ncụm từ: at the first attempt: lần thử đầu tiên (= on the first try)\r\nTạm dịch: Peter cố gắng hết sức vượt qua kỳ thi lái xe bằng lần cố gắng đầu tiên của anh ấy.\r\n'),
(5, 'Choose the best answer to complete each sentence.\r\nThey ________ their time and money to their children.', 'assumed', 'devoted', 'committed', 'embarked', 'B', 'assume (v): giả sử, giả định\r\ncommit (v): cam kết\r\ndevote (v): cống hiến, dành hết cho\r\nembark (v): tham gia\r\nCụm từ: devote something to somebody: cống hiến/dành cái gì cho ai.\r\n=> They devoted their time and money to their children.\r\nTạm dịch: Họ đã dành hết thời gian và tiền bạc cho con cái của mình.'),
(6, 'Choose the best answer to complete each sentence.\r\nHe remarried after a ______ from his first wife, Kate.', 'rebellion', 'legacy', 'privilege', 'divorce', 'D', 'rebellion (n): cuộc nổi loạn\r\nprivilege (n): đặc ân, đặc quyền\r\nlegacy (n): gia tài, di sản\r\ndivorce (n): sự ly hôn\r\n=> He remarried after a divorce from his first wife, Kate.\r\nTạm dịch: Anh ấy đã tái hôn sau khi li hôn với người vợ đầu là Kate.'),
(7, 'Choose the best answer to complete each sentence.\r\nHe\'s always ___________ to his teachers.', 'confident', 'supportive', 'obedient', 'expressive', 'C', 'confident (adj): tự tin\r\nsupportive (adj): khuyến khích\r\nobedient (adj): vâng lời\r\nexpressive (adj): biểu cảm\r\n=> He\'s always obedient to his teachers.\r\nTạm dịch: Anh ấy luôn vâng lời của các giáo viên của mình.'),
(8, 'Choose the best answer to complete each sentence.\r\nWe are a very __________ family and support each other through any crises.', 'old-established', 'well-to-do', 'hard-up', 'close-knit', 'D', 'old-established (adj): xưa cũ\r\nwell-to-do (adj): giàu có, thịnh vượng\r\nhard-up (adj): cạn túi, hết tiền\r\nclose-knit(adj): khăng khít, gắn bó\r\n=> We are a very close-knit family and support each other through any crises.\r\nTạm dịch: Chúng tôi là 1 gia đình gắn bó khăng khít và luôn giúp đỡ nhau vượt qua mọi khó khăn khủng hoảng.'),
(9, 'Choose the best answer to complete each sentence.\r\nShe earned extra money last year ________ several young children.', 'bringing into', 'caring for', 'taking on', 'bearing up', 'B', 'bring into (v): mang vào (nhà, địa điểm nào đó)\r\ncare for (v): chăm sóc, nuôi nấng, trông nom\r\ntake on (v): đảm nhận, nhận lấy\r\nbear up (v): ủng hộ, chống đỡ\r\n=> She earned extra money last year caring for several young children.\r\nTạm dịch: Năm ngoái, cô ấy đã kiếm thêm tiền để nuôi dạy những đứa con nhỏ của mình.'),
(10, 'Choose the best answer to complete each sentence.\r\nWe must come to a _____ about what to do tomorrow.', 'decision', 'driving test', 'silence', 'experiment', 'A', 'decision (n): sự quyết định\r\nsilence (n): sự im lặng\r\ndriving test (n): kỳ thi lái xe\r\nexperiment (n): thí nghiệm\r\n- Cụm từ \"come to a decision\" = \"arrive at a decision\": đi tới một quyết định, đưa ra một quyết định\r\n=> We must come to a decision about what to do tomorrow.\r\nTạm dịch: Chúng ta cần phải quyết định về những gì sẽ làm vào ngày mai.');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
