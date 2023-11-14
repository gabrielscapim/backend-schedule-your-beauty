CREATE TABLE IF NOT EXISTS `schedule_your_beauty_database`.`scheduling_date_times` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date_time` DATETIME NOT NULL,
  `last_schedule_time_day` TINYINT NOT NULL,
  `available` TINYINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;