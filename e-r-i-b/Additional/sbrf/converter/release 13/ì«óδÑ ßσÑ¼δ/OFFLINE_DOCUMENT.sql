create table OFFLINE_DOCUMENT_INFO 
(
	ID                   INTEGER              not null,
	EXTERNAL_ID          VARCHAR2(80)         not null,
	BLOCK_NUMBER         INTEGER              not null,
	ADAPTER_UUID         VARCHAR2(64)         not null,
	DOC_TYPE             VARCHAR2(150),
	STATE_CODE           VARCHAR2(25),
	STATE_DESCRIPTION    VARCHAR2(265),
	ADDIT_INFO           CLOB,
	DOCUMENT_DATE        DATE default sysdate not null               
) 
partition by range (DOCUMENT_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH')) 
(
  partition P_FIRST values less than (to_date('01.01.2014', 'dd.mm.yyyy'))
)
/
create sequence S_OFFLINE_DOCUMENT_INFO cache 2000
/

create unique index I_OFFLINE_DOCUMENT_INFO on OFFLINE_DOCUMENT_INFO (
   EXTERNAL_ID ASC
) global tablespace OFFLINE_DOC_IDX
/