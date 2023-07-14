/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

drop user @dbserver.limits.username@ cascade
go
create user @dbserver.limits.username@ profile "DEFAULT" identified by @dbserver.limits.password@ account unlock
	default tablespace "USERS"
	temporary tablespace "TEMP"
	quota unlimited on "USERS"
go
grant connect, resource, create view, create sequence, change notification to @dbserver.limits.username@
go
