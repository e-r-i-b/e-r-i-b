/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

drop user @dbserver.asyncsearch.username@ cascade
go
create user @dbserver.asyncsearch.username@ profile "DEFAULT" identified by @dbserver.asyncsearch.password@ account unlock
	default tablespace "USERS"
	temporary tablespace "TEMP"
	quota unlimited on "USERS"
go
grant connect, resource, create view, create sequence, change notification to @dbserver.asyncsearch.username@
go
