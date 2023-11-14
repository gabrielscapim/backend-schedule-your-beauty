CREATE TABLE IF NOT EXISTS `schedule_your_beauty_database`.`scheduled_date_times` (
  `schedule_id` INT NOT NULL,
  `scheduling_date_time_id` INT NOT NULL,
  PRIMARY KEY (`schedule_id`, `scheduling_date_time_id`),
  INDEX `idx_fk_scheduling_time_id_idx` (`scheduling_date_time_id` ASC) VISIBLE,
  CONSTRAINT `idx_fk_schedule_id`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `schedule_your_beauty_database`.`schedules` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idx_fk_scheduling_time_id`
    FOREIGN KEY (`scheduling_date_time_id`)
    REFERENCES `schedule_your_beauty_database`.`scheduling_date_times` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;