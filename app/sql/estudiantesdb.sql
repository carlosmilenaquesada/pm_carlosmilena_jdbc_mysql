-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema estudiantesdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema estudiantesdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `estudiantesdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish2_ci ;
USE `estudiantesdb` ;

-- -----------------------------------------------------
-- Table `estudiantesdb`.`cursos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estudiantesdb`.`cursos` (
  `curso` VARCHAR(50) NOT NULL,
  `descripcion` VARCHAR(100) NULL,
  PRIMARY KEY (`curso`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estudiantesdb`.`estudiantes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estudiantesdb`.`estudiantes` (
  `dni` VARCHAR(50) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `fechan` VARCHAR(45) NOT NULL,
  `horap` VARCHAR(45) NOT NULL,
  `curso` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`dni`),
  INDEX `fk_estudiantes_cursos_idx` (`curso` ASC) VISIBLE,
  CONSTRAINT `fk_estudiantes_cursos`
    FOREIGN KEY (`curso`)
    REFERENCES `estudiantesdb`.`cursos` (`curso`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
