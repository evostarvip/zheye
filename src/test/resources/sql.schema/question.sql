DROP TABLE IF EXISTS question;
CREATE TABLE `question` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `content` text,
    `user_id` int(11) NOT NULL,
    `created_date` datetime NOT NULL,
    `comment_count` int(11) NOT NULL,
    `support_count` int(11) DEFAULT '0',
    `unsupport_count` int(11) DEFAULT '0',
    `review_num` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `date_index` (`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `question` (`id`, `title`, `content`, `user_id`, `created_date`, `comment_count`, `support_count`, `unsupport_count`)
VALUES
	(1, '今天您核酸了吗？', '一加一在什么情况下等于三？答，在算错的情况下的等于三！', 1, '2020-07-01 18:10:28', 0, 0, 0),
	(2, '今天您核酸了吗？', '一加一在什么情况下等于三？答，在算错的情况下的等于三！', 2, '2020-07-01 18:10:28', 0, 0, 0);