CREATE TABLE IF NOT EXISTS `schedule_your_beauty_database`.`schedules` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `client_id` INT NOT NULL,
  `production_id` INT NOT NULL,
  `event_type` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_fk_production_id_idx` (`production_id` ASC) VISIBLE,
  INDEX `idx_fk_client_id_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `idx_fk_production_id`
    FOREIGN KEY (`production_id`)
    REFERENCES `schedule_your_beauty_database`.`productions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idx_fk_client_id`
    FOREIGN KEY (`client_id`)
    REFERENCES `schedule_your_beauty_database`.`clients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;