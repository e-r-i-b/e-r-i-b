DROP TABLE @dbserver.spoobk_table_name@ CASCADE CONSTRAINTS;
DROP SEQUENCE S_@dbserver.spoobk_table_name@;
DROP DATABASE LINK WAYSPBK;

CREATE TABLE @dbserver.spoobk_table_name@
(
  ID        INTEGER      NOT NULL,
  TER_CODE  VARCHAR2(32) NOT NULL,
  OSB       VARCHAR2(32),
  FOSB      VARCHAR2(32),
  DESPATCH  VARCHAR2(11),
  DATE_SUC  TIMESTAMP,

  CONSTRAINT @dbserver.spoobk_table_name@_PK PRIMARY KEY (ID)
);

CREATE SEQUENCE S_@dbserver.spoobk_table_name@;

CREATE DATABASE LINK WAYSPBK
  CONNECT TO @dbserver.username@ IDENTIFIED BY @dbserver.password@
    using '(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=@dbserver.name@)(PORT=@dbserver.port@))(CONNECT_DATA=(SID=@dbserver.database@)))';