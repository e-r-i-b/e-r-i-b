/* Убрано, в связи с пересозданием таблицы CSA_OPERATIONS
-- Номер ревизии:  48129
-- Комментарий: Изменить поиск операции в БД ЦСА 
alter table CSA_OPERATIONS modify CREATION_DATE timestamp default sysdate
/

-- Номер ревизии:  48538
-- Комментарий: Интеграция с деловой средой

alter table CSA_OPERATIONS add (
	CONFIRM_TYPE             varchar2(32),
	CONFIRM_SID              varchar2(32),
	CONFIRM_PASSWORD_NUMBER  varchar2(32),
	CONFIRM_RECEIPT_NUMBER   varchar2(32),
	CONFIRM_PASSWORD_LEFT    integer,
	CONFIRM_LAST_ATEMPTS     integer
)
/
drop index CSA_OPERATIONS_USERID_INDEX
/
*/
-- Номер ревизии: 48682
-- Комментарий: правила блокировки
alter table BLOCKINGRULES add (
	FROM_PUBLISH_DATE     timestamp,
	TO_PUBLISH_DATE       timestamp,
	FROM_RESTRICTION_DATE timestamp,
	TO_RESTRICTION_DATE   timestamp
)
/
create index IND_BLOCKRULE_F_PUBLISH_DATE on BLOCKINGRULES(FROM_PUBLISH_DATE)
/
create index IND_BLOCKRULE_T_PUBLISH_DATE on BLOCKINGRULES(TO_PUBLISH_DATE)
/

/* Убрано, в связи с пересозданием таблиц логов
alter table CSA_CODLOG set unused column MESSAGE_ANSWER_TYPE
/
alter table CSA_CODLOG set unused column DIRECTION
/
alter table CSA_CODLOG set unused column LINK
/
alter table CSA_SYSTEMLOG set unused column PERSON_ID
/
*/

--Новая структура CSA_OPERATIONS
alter table CSA_OPERATIONS rename to CSA_OPERATIONS_OLD
/
alter table CSA_OPERATIONS_OLD rename constraint PK_CSA_OPERATIONS to PK_OLD_CSA_OPERATIONS
/
alter index PK_CSA_OPERATIONS rename to PK_OLD_CSA_OPERATIONS
/
alter index CSA_OPERATIONS_PTSC rename to CSA_OLD_OPERATIONS_PTSC
/

/*==============================================================*/
/* Table: CSA_OPERATIONS                                        */
/*==============================================================*/
create table CSA_OPERATIONS  (
   OUID                 VARCHAR2(32)                    not null,
   FIRST_NAME           VARCHAR2(100)                   not null,
   SUR_NAME             VARCHAR2(100)                   not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)                   not null,
   BIRTHDATE            TIMESTAMP                       not null,
   CB_CODE              VARCHAR2(20)                    not null,
   USER_ID              VARCHAR2(10),
   TYPE                 VARCHAR2(40)                    not null,
   STATE                VARCHAR2(40)                    not null,
   CREATION_DATE        TIMESTAMP       default SYSDATE not null,
   PROFILE_ID           INTEGER                         not null,
   PARAMS               CLOB,
   CONFIRM_TYPE         VARCHAR2(32),
   CONFIRM_CODE_HASH    VARCHAR2(40),
   CONFIRM_CODE_SALT    VARCHAR2(32),
   CONFIRM_CODE_CREATION_DATE TIMESTAMP,
   CONFIRM_ERRORS       INTEGER,
   CONFIRM_SID          VARCHAR2(32),
   CONFIRM_PASSWORD_NUMBER VARCHAR2(32),
   CONFIRM_RECEIPT_NUMBER VARCHAR2(32),
   CONFIRM_PASSWORD_LEFT INTEGER,
   CONFIRM_LAST_ATEMPTS INTEGER,
   INFO                 CLOB,
   IP_ADDRESS           VARCHAR2(15)                    not null,
   constraint PK_CSA_OPERATIONS primary key (OUID)
)
partition by range (CREATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH')) 
subpartition by hash (PROFILE_ID) subpartitions 16
(
	partition P_2013_07 values less than (timestamp '2013-08-01 00:00:00')
)
/

/*==============================================================*/
/* Index: CSA_OPERATIONS_PTSC                                   */
/*==============================================================*/
create index CSA_OPERATIONS_PTSC on CSA_OPERATIONS (
   PROFILE_ID ASC,
   TYPE ASC,
   STATE ASC,
   CREATION_DATE ASC
)
  local
/


