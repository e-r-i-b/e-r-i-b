/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

drop user @dbserver.csaadmin.username@ cascade
go
create user @dbserver.csaadmin.username@ profile "DEFAULT" identified by @dbserver.csaadmin.password@ account unlock
	default tablespace "USERS"
	temporary tablespace "TEMP"
	quota unlimited on "USERS"
go
grant connect, resource, create view, create sequence to @dbserver.csaadmin.username@
go
