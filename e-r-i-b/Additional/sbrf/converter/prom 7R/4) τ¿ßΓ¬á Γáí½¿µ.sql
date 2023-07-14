drop table A 
/
drop table ACCOUNT_LINKS_BAD
/
drop table BAD_DOC
/
drop table BF
/
drop table BRYANSK_CONVERTED_DOCS
/
drop table CALENDARPAYMENTFORM 
/
drop table CARD_CLIENTS_DUBLI 
/
drop table EMP_TST 
/
drop table EXTERNAL_SYSTEMS 
/
drop table EXTERNAL_SYSTEMS_LINKS
/
drop table HT_RECEIVERS
/
drop table HT_SUBSCRIPTION_PARAMETERS
/
drop table KBK_TMP 
/
drop table MAGADAN_CONVERT_TEMP
/
drop table OFFICES 
/
drop table RUSBANKS_BACKUP
/
drop table RUSBANKS_BACKUP_2
/
drop table RUSBANKS_TTT 
/
drop table SHOW_CLOSED_PRODUCT 
/
drop table SP_CONVERT 
/
drop table TEMP_SERVICES_MAP  
/
drop table TEMP_USERS_AGREEMENTS
/
drop table UDBO_CLIENTS_DUBLI
/
drop table VVB 
/

--чистка структур

	drop index BUSINESS_DOCUMENTS_I_CHANGED 
	/*
		CREATE INDEX BUSINESS_DOCUMENTS_I_CHANGED
		ON BUSINESS_DOCUMENTS(CHANGED)
	*/
	
	
