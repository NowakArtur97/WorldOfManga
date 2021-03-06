DROP DATABASE IF EXISTS `world_of_manga`;

CREATE DATABASE `world_of_manga`;

USE `world_of_manga`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    `user_id` INT(11) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(40) NOT NULL,
    `password` varchar(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `first_name` VARCHAR(40),
    `last_name` VARCHAR(40),
    `is_enabled` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `USER_USER_NAME_UNIQUE` (`username`),
    UNIQUE KEY `USER_EMAIL_UNIQUE` (`email`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
    `role_id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE `ROLE_NAME_UNIQUE` (`name`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role`(
	`user_id` INT(11) NOT NULL,
    `role_id` INT(11) NOT NULL,
    PRIMARY KEY(`user_id`, `role_id`),
	CONSTRAINT `FK_USER_ROLE` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `FK_ROLE_USER` FOREIGN KEY (`role_id`) REFERENCES `role`(`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `language`;

CREATE TABLE `language` (
    `language_id` INT(11) NOT NULL AUTO_INCREMENT,
    `locale` VARCHAR(6) NOT NULL,
    PRIMARY KEY (`language_id`),
    UNIQUE `LANGUAGE_LOCALE_UNIQUE` (`locale`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `manga`;

CREATE TABLE `manga` (
    `manga_id` INT(11) NOT NULL AUTO_INCREMENT,
    `image` MEDIUMBLOB NOT NULL,
    PRIMARY KEY (`manga_id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `author`;

CREATE TABLE `author` (
    `author_id` INT(11) NOT NULL AUTO_INCREMENT,
    `full_name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`author_id`),
    UNIQUE `AUTHOR_FULL_NAME_UNIQUE` (`full_name`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `manga_author`;

CREATE TABLE `manga_author` (
    `manga_id` INT(11) NOT NULL,
    `author_id` INT(11) NOT NULL,
    PRIMARY KEY (`manga_id` , `author_id`),
    CONSTRAINT `FK_MANGA_AUTHOR` FOREIGN KEY (`manga_id`)
        REFERENCES `manga` (`manga_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `FK_AUTHOR_MANGA` FOREIGN KEY (`author_id`)
        REFERENCES `author` (`author_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `manga_translation`;

CREATE TABLE `manga_translation` (
    `manga_translation_id` INT(11) NOT NULL AUTO_INCREMENT,
    `manga_id` INT(11),
	`language_id` INT(11),
    `title` VARCHAR(50) NOT NULL,
    `description` VARCHAR(1000) NOT NULL,
    PRIMARY KEY (`manga_translation_id`),
    CONSTRAINT `FK_MANGA_TRANSLATION` FOREIGN KEY (`manga_id`)
        REFERENCES `manga` (`manga_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `FK_TRANSLATION_LANGUAGE` FOREIGN KEY (`language_id`)
        REFERENCES `language` (`language_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `manga_rating`;

CREATE TABLE `manga_rating` (
    `manga_id` INT(11),
    `user_id` INT(11),
    `rating` INT(1),
    PRIMARY KEY (`manga_id` , `user_id`),
    CONSTRAINT `FK_RATING_MANGA_USER` FOREIGN KEY (`manga_id`)
        REFERENCES `manga` (`manga_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `FK_RATING_USER_MANGA` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `CHK_MANGA_RATING_MAX_VALUE` CHECK (`rating` <= 5)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `favourite_manga`;

CREATE TABLE `favourite_manga` (
    `manga_id` INT(11),
    `user_id` INT(11),
    PRIMARY KEY (`manga_id` , `user_id`),
    CONSTRAINT `FK_FAVOURITE_MANGA_USER` FOREIGN KEY (`manga_id`)
        REFERENCES `manga` (`manga_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `FK_FAVOURITE_USER_MANGA` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `manga_list`;

CREATE TABLE `manga_list` (
    `manga_id` INT(11),
    `user_id` INT(11),
    `status` VARCHAR(20),
    PRIMARY KEY (`manga_id` , `user_id`),
    CONSTRAINT `FK_LIST_MANGA_USER` FOREIGN KEY (`manga_id`)
        REFERENCES `manga` (`manga_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `FK_LIST_USER_MANGA` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `genre`;

CREATE TABLE `genre` (
    `genre_id` INT(11) NOT NULL AUTO_INCREMENT,
    `en_translation` VARCHAR(50) NOT NULL,
    `pl_translation` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`genre_id`),
    UNIQUE `GENRE_EN_UNIQUE` (`en_translation`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `manga_genre`;

CREATE TABLE `manga_genre` (
    `manga_id` INT(11) NOT NULL,
    `genre_id` INT(11) NOT NULL,
    PRIMARY KEY (`manga_id`, `genre_id`),
    CONSTRAINT `FK_MANGA_GENRE` FOREIGN KEY (`manga_id`)
        REFERENCES `manga` (`manga_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `FK_GENRE_MANGA` FOREIGN KEY (`genre_id`)
        REFERENCES `genre` (`genre_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;
