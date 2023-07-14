/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

drop user @dbserver.einvoicing.username@ cascade
go
create user @dbserver.einvoicing.username@ profile "DEFAULT" identified by @dbserver.einvoicing.password@ account unlock
	default tablespace "USERS"
	temporary tablespace "TEMP"
	quota unlimited on "USERS"
go
grant connect, resource, create view, create sequence, change notification to @dbserver.einvoicing.username@
go
