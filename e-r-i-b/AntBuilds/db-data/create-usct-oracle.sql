/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

drop user @dbserver.usct.username@ cascade
go
create user @dbserver.usct.username@ profile "DEFAULT" identified by @dbserver.usct.password@ account unlock
	default tablespace "USERS"
	temporary tablespace "TEMP"
	quota unlimited on "USERS"
go
grant connect, resource, create view, create sequence, change notification to @dbserver.usct.username@
go