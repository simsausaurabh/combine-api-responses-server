-- liquibase formatted sql
-- changeset Saurabh:setup_db.sql

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE IF NOT EXISTS predict;

USE predict;

DROP TABLE IF EXISTS predict.url;

CREATE TABLE `url` (
    -- auto-generated primary key
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `url1` varchar(1000) NOT NULL,
    `url2` varchar(1000) NOT NULL,
    `url3` varchar(1000) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE(url1, url2, url3)
);

SET FOREIGN_KEY_CHECKS=1;
