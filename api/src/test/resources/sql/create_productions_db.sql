CREATE TABLE IF NOT EXISTS `schedule_your_beauty_database`.`productions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` SMALLINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;