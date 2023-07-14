/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

drop user @etsm.docs.backup.dbserver.username@ cascade
go
create user @etsm.docs.backup.dbserver.username@ profile "DEFAULT" identified by @etsm.docs.backup.dbserver.password@ account unlock
	default tablespace "USERS"
	temporary tablespace "TEMP"
	quota unlimited on "USERS"
go
grant connect, resource, create view, create sequence, change notification to @etsm.docs.backup.dbserver.username@
go
