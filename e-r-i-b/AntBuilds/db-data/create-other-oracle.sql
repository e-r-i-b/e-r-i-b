/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

DROP SEQUENCE @dbserver.shadow.username@.S_ADDRESS
go

DROP SEQUENCE @dbserver.shadow.username@.S_USERS
go

DROP SEQUENCE @dbserver.shadow.username@.S_DOCUMENTS
go

DROP SEQUENCE @dbserver.shadow.username@.S_ACCESSSCHEMES
go

DROP SEQUENCE @dbserver.shadow.username@.S_LOGINS
go

DROP SEQUENCE @dbserver.shadow.username@.S_AUTHENTICATION_MODES
go

DROP SEQUENCE @dbserver.shadow.username@.S_SCHEMEOWNS
go

DROP SEQUENCE @dbserver.shadow.username@.S_CARD_LINKS
go

DROP SEQUENCE @dbserver.shadow.username@.S_PAYMENTS_SYSTEM_LINKS
go

DROP SEQUENCE @dbserver.shadow.username@.S_RECEIVERS
go

DROP SEQUENCE @dbserver.shadow.username@.S_ACCOUNT_LINKS
go

DROP SEQUENCE @dbserver.shadow.username@.S_UNLOAD_PRODUCT
go

DROP SEQUENCE @dbserver.shadow.username@.S_UNLOAD_VIRTUAL_CARD_CLAIM
go

CREATE SYNONYM @dbserver.shadow.username@.S_ADDRESS FOR @dbserver.username@.S_ADDRESS
go

CREATE SYNONYM @dbserver.shadow.username@.S_USERS FOR @dbserver.username@.S_USERS
go

CREATE SYNONYM @dbserver.shadow.username@.S_DOCUMENTS FOR @dbserver.username@.S_DOCUMENTS
go

CREATE SYNONYM @dbserver.shadow.username@.S_ACCESSSCHEMES FOR @dbserver.username@.S_ACCESSSCHEMES
go

CREATE SYNONYM @dbserver.shadow.username@.S_LOGINS FOR @dbserver.username@.S_LOGINS
go

CREATE SYNONYM @dbserver.shadow.username@.S_AUTHENTICATION_MODES FOR @dbserver.username@.S_AUTHENTICATION_MODES
go

CREATE SYNONYM @dbserver.shadow.username@.S_SCHEMEOWNS FOR @dbserver.username@.S_SCHEMEOWNS
go

CREATE SYNONYM @dbserver.shadow.username@.S_CARD_LINKS FOR @dbserver.username@.S_CARD_LINKS
go

CREATE SYNONYM @dbserver.shadow.username@.S_PAYMENTS_SYSTEM_LINKS FOR @dbserver.username@.S_PAYMENTS_SYSTEM_LINKS
go

CREATE SYNONYM @dbserver.shadow.username@.S_RECEIVERS FOR @dbserver.username@.S_RECEIVERS
go

CREATE SYNONYM @dbserver.shadow.username@.S_ACCOUNT_LINKS FOR @dbserver.username@.S_ACCOUNT_LINKS
go 

CREATE SYNONYM @dbserver.shadow.username@.S_UNLOAD_PRODUCT FOR @dbserver.username@.S_UNLOAD_PRODUCT
go 

CREATE SYNONYM @dbserver.shadow.username@.S_UNLOAD_VIRTUAL_CARD_CLAIM FOR @dbserver.username@.S_UNLOAD_VIRTUAL_CARD_CLAIM
go

GRANT SELECT ON @dbserver.username@.S_ADDRESS TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_USERS TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_DOCUMENTS TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_ACCESSSCHEMES TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_LOGINS TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_AUTHENTICATION_MODES TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_SCHEMEOWNS TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_CARD_LINKS TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_PAYMENTS_SYSTEM_LINKS TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_RECEIVERS TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_ACCOUNT_LINKS TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_UNLOAD_PRODUCT TO @dbserver.shadow.username@
go

GRANT SELECT ON @dbserver.username@.S_UNLOAD_VIRTUAL_CARD_CLAIM TO @dbserver.shadow.username@
go