DROP TABLE IF EXISTS answer;
CREATE TABLE `answer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `review_num` int(11) DEFAULT '0',
  `content` text,
  `comment_count` int(11) DEFAULT '0',
  `support_count` int(11) DEFAULT '0',
  `unsupport_count` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;