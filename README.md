# engineering_measure
> mysql create脚本

```sql
CREATE DATABASE `measure` DEFAULT CHARACTER SET utf8;

CREATE TABLE `t_pit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_workbench` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pit_id` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `rfid` varchar(200) DEFAULT NULL,
  `longitude` decimal(10,0) DEFAULT NULL,
  `latitude` decimal(10,0) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_work_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  `point_id` int(11) DEFAULT NULL,
  `data` decimal(10,0) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
