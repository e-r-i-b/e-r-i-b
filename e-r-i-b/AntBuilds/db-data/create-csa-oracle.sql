/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

drop user @dbserver.csa.username@ cascade
go
create user @dbserver.csa.username@ profile "DEFAULT" identified by @dbserver.csa.password@ account unlock
	default tablespace "USERS"
	temporary tablespace "TEMP"
	quota unlimited on "USERS"
go
grant connect, resource, create view, create sequence, change notification to @dbserver.csa.username@
go
