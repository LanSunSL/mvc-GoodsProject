DROP DATABASE goods ;
CREATE DATABASE goods CHARACTER SET UTF8 ;
USE goods ;
-- 创建数据表
CREATE TABLE item(
	iid			INT AUTO_INCREMENT ,
	title		VARCHAR(50) ,
	CONSTRAINT pk_iid PRIMARY KEY(iid)
) engine="innodb" ;
CREATE TABLE tag(
	tid			INT AUTO_INCREMENT ,
	title		VARCHAR(50) ,
	CONSTRAINT pk_tid PRIMARY KEY(tid)
) engine="innodb"  ;
CREATE TABLE goods (
	gid			INT ,
	name		VARCHAR(50) ,
	price		DOUBLE ,
	photo		VARCHAR(100) ,
	iid			INT ,
	CONSTRAINT pk_gid PRIMARY KEY(gid) ,
	CONSTRAINT fk_iid FOREIGN KEY(iid) REFERENCES item(iid)
) engine="innodb"  ;
CREATE TABLE goods_tag(
	gid			INT , 
	tid			INT ,
	CONSTRAINT fk_gid FOREIGN KEY(gid) REFERENCES goods(gid) ON DELETE CASCADE ,
	CONSTRAINT fk_tid FOREIGN KEY(tid) REFERENCES tag(tid)
) ;
-- 测试数据
INSERT INTO item(iid,title) VALUES (1,'图书音像') ;
INSERT INTO item(iid,title) VALUES (2,'办公用品') ;
INSERT INTO item(iid,title) VALUES (3,'家居生活') ;
INSERT INTO item(iid,title) VALUES (4,'厨房家电') ;
INSERT INTO item(iid,title) VALUES (5,'电子设备') ;

INSERT INTO tag(tid,title) VALUES (1,'高端') ;
INSERT INTO tag(tid,title) VALUES (2,'奢华') ;
INSERT INTO tag(tid,title) VALUES (3,'性价比高') ;
INSERT INTO tag(tid,title) VALUES (4,'免费') ;
INSERT INTO tag(tid,title) VALUES (5,'耐用') ;

-- 提交事务
COMMIT ;

