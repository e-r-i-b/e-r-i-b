/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

drop user @dbserver.documents.offline.username@ cascade
go
create user @dbserver.documents.offline.username@ profile "DEFAULT" identified by @dbserver.documents.offline.password@ account unlock
	default tablespace "USERS"
	temporary tablespace "TEMP"
	quota unlimited on "USERS"
go
grant connect, resource, create view, create sequence to @dbserver.documents.offline.username@
go
