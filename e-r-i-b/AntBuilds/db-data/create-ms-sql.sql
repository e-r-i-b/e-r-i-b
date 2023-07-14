/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

if exists (select name from master.dbo.sysdatabases where name = N'@dbserver.database@')
  drop database [@dbserver.database@]
go

create database [@dbserver.database@]
collate Cyrillic_General_CI_AS
go

use [@dbserver.database@]
go

if not exists (select * from master.dbo.syslogins where loginname = N'@dbserver.username@')
  exec sp_addlogin N'@dbserver.username@', N'@dbserver.password@', N'@dbserver.database@'
go

if not exists (select * from dbo.sysusers where name = N'@dbserver.username@' and uid < 16382)
  exec sp_grantdbaccess N'@dbserver.username@', N'@dbserver.username@'
go

exec sp_addrolemember N'db_datareader', N'@dbserver.username@'
go

exec sp_addrolemember N'db_datawriter', N'@dbserver.username@'
go
