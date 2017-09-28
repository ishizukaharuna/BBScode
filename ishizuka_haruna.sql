CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL,
  `password` varchar(225) NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `position_id` int(11) DEFAULT NULL,
  `is_working` tinyint(1) DEFAULT NULL,
  `insert_date` timestamp NOT NULL 
  `update_date` timestamp NOT NULL 
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_UNIQUE` (`account`),
  UNIQUE KEY `password_UNIQUE` (`password`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `title` varchar(30) NOT NULL,
  `text` text NOT NULL,
  `category` varchar(10) NOT NULL,
  `branch_id` int(11) NOT NULL,
  `position_id` int(11) NOT NULL,
  `insert_date` timestamp NOT NULL ,
  `update_date` timestamp NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `message_id` int(11) NOT NULL,
  `text` text NOT NULL,
  `branch_id` int(11) NOT NULL,
  `position_id` int(11) NOT NULL,
  `insert_date` timestamp NOT NULL ,
  `update_date` timestamp NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `branches` (
  `id` int(11) NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `positions` (
  `id` int(11) NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`
 SQL SECURITY DEFINER VIEW `user_messages` AS
  select `messages`.`id` AS `id`,
  `messages`. `title` AS `title`,
  `messages`.`text` AS `text`,
  `messages`.`category` AS `category`,
  `messages`.`insert_date` AS `insert_date`,`users`.
  `id` AS `user_id`,
  `users`.`branch_id` AS `branch_id`,
  `users`.`position_id` AS `position_id`,
  `users`.`name` AS `name` 
from (`users` join `messages`) where (`users`.`id` = `messages`.`user_id`)

CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `user_comments` AS
    select 
        `comments`.`id` AS `id`,
        `comments`.`text` AS `text`,
        `comments`.`message_id` AS `message_id`,
        `comments`.`user_id` AS `user_id`,
        `users`.`branch_id` AS `branch_id`,
        `users`.`position_id` AS `position_id`,
        `comments`.`insert_date` AS `insert_date`,
        `users`.`name` AS `name`
    from
        (`users`
        join `comments`)