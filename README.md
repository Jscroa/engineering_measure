# engineering_measure
> mysql create脚本

```sql
CREATE USER 'measure'@'%' IDENTIFIED BY '123456';

GRANT all ON measure.* TO 'measure'@'%';

flush privileges;

CREATE DATABASE `measure` DEFAULT CHARACTER SET utf8;

use measure;

CREATE TABLE `t_pit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(200) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_workbench` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(200) NOT NULL,
  `pit_id` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `rfid` varchar(200) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

CREATE TABLE `t_work_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(200) NOT NULL,
  `workbench_id` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `measure_type` int(11) DEFAULT NULL,
  `measure_count` int(11) DEFAULT NULL,
  `deviation_percent` int(11) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_measure_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(200) NOT NULL,
  `point_id` int(11) DEFAULT NULL,
  `data` double DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
