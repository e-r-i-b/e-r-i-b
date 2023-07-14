-- Номер ревизии: 58994
-- Номер версии: 1.18
-- Комментарий: BUG068155: Переделать логирование в OFFER_NOTIFICATIONS_LOG
create table OFFER_NOTIFICATIONS_LOG 
(
   ID                   INTEGER              not null,
   NOTIFICATION_ID      INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   DISPLAY_DATE         TIMESTAMP            not null,
   TYPE                 VARCHAR2(12)         not null,
   NAME                 VARCHAR2(20)         not null
)
partition by range
 (DISPLAY_DATE)  interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition    P_START     values less than (to_date('01-01-2014','DD-MM-YYYY')))
go

create sequence S_OFFER_NOTIFICATIONS_LOG
go

create index I_OFFER_NOTIF_LOG on OFFER_NOTIFICATIONS_LOG (
   TYPE ASC,
   DISPLAY_DATE ASC,
   ID ASC
)
local
go

-- Номер ревизии: 59325
-- Номер версии: 1.18
-- Комментарий: ENH067289: Добавить информацию в CODLOG.
alter table CODLOG add THREAD_INFO INTEGER
go
alter table CSA_CODLOG add THREAD_INFO INTEGER
go
alter table USERLOG add THREAD_INFO INTEGER
go

-- Номер ревизии: 60483
-- Номер версии: 1.18
-- Комментарий:  BUG070976: Не отображается баннер по предодобренной карте\кредиту 
alter table OFFER_NOTIFICATIONS_LOG modify NAME varchar2(40)
go


-- Номер ревизии: 60927
-- Номер версии: 1.18
-- Комментарий: Отчет по автоматической перекатегоризации 

create table ALF_RECATEGORIZATION_LOG 
(
   ID                   INTEGER              not null,
   LOG_DATE             TIMESTAMP            not null,
   DESCRIPTION          VARCHAR2(100)        not null,
   MCC_CODE             INTEGER              not null,
   ORIGINAL_CATEGORY    VARCHAR2(100)        not null,
   NEW_CATEGORY         VARCHAR2(100)        not null,
   OPERATION_TYPE       VARCHAR2(6)          not null,
   OPERATIONS_COUNT     INTEGER
)
partition by range
 (LOG_DATE)  interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition P_START values less than (to_date('01-01-2014','DD-MM-YYYY')))
go

create sequence S_ALF_RECATEGORIZATION_LOG
go

create index I_ALF_RECATEGORIZATION_LOG on ALF_RECATEGORIZATION_LOG (
    LOG_DATE
)
local
go


-- Номер ревизии: 62031
-- Номер версии: 1.18
-- Комментарий: CHG072320: Секционировать ADVERTISINGS_LOG 

drop table ADVERTISINGS_LOG cascade constraints
go

create table ADVERTISINGS_LOG 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   TYPE                 VARCHAR2(20)         not null
)
partition by range
 (START_DATE)  interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition P_START values less than (to_date('01-06-2014','DD-MM-YYYY')))
go

create index I_ADVERTISING_LOG on ADVERTISINGS_LOG (
   START_DATE ASC
)
local
go

create index DXFK_ADVERTISINGS_LOG on ADVERTISINGS_LOG (
   ADVERTISING_ID ASC
)
local
go



-- Номер ревизии: 62755
-- Номер версии: 1.18
-- Комментарий: Синхронизация адресной книги: отчет "Оповещения о превышении порога обращения к сервису"
create table CONTACT_SYNC_COUNT_EXCEED_LOG 
(
   LOGIN_ID             INTEGER              not null,
   NAME                 VARCHAR2(256)        not null,
   DOCUMENT             VARCHAR2(64)         not null,
   BIRTHDAY             TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   SYNC_DATE            TIMESTAMP            not null,
   MESSAGE              CLOB                 not null
)
partition by range
 (SYNC_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-06-2014','DD-MM-YYYY')))
go

create index I_CONTACT_SYNC_EXCEED_DATE on CONTACT_SYNC_COUNT_EXCEED_LOG (
   SYNC_DATE ASC
)
local
go



-- Номер ревизии: 67273
-- Номер версии: 1.18
-- Комментарий: Уменьшение объема информации при логировании. Новые поля для таблицы MESSAGE_TRANSLATE
alter table MESSAGE_TRANSLATE add (
    IS_NEW char(1) default '0' not null,
    LOG_TYPE varchar2(20)
)
go

-- Номер ревизии: 67408 
-- Номер версии: 1.18
-- Комментарий: Уменьшение объема информации при логировании. Изменение логирования
create table FINANCIAL_CODLOG 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(40)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(80)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   LOGIN_ID             INTEGER,
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   OPERATION_UID        VARCHAR2(32),
   NODE_ID              INTEGER,
   TB                   VARCHAR2(4),
   OSB                  VARCHAR2(4),
   VSP                  VARCHAR2(7),
   THREAD_INFO          INTEGER
)
partition by range("START_DATE") interval ( numtoyminterval(1,'MONTH') ) subpartition by hash("ID") subpartitions 16
(
      partition "P_START" values less than (timestamp' 2014-01-01 00:00:00')
)
go

create sequence S_FINANCIAL_CODLOG
go

create index FCL_APP_DATE_INDEX on FINANCIAL_CODLOG (
   APPLICATION ASC,
   START_DATE DESC
)
local
go

create index FCL_APP_SYST_DATE_INDEX on FINANCIAL_CODLOG (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
)
local
go

create index FCL_OUID_INDEX on FINANCIAL_CODLOG (
   OPERATION_UID ASC
)
local
go

create index FCL_SESSION_DATE_INDEX on FINANCIAL_CODLOG (
   SESSION_ID ASC,
   START_DATE DESC
)
local
go

create index FCL_MESSAGE_DEMAND_ID_INDEX on FINANCIAL_CODLOG (
   MESSAGE_DEMAND_ID ASC
)
local
go

create index FCL_DI_DATE_INDEX on FINANCIAL_CODLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
local
go

create index FCL_LOGIN_DATE_INDEX on FINANCIAL_CODLOG (
   LOGIN_ID ASC,
   START_DATE DESC
)
local
go

create index FCL_FIO_DATE_INDEX on FINANCIAL_CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
go

create index FCL_PK on FINANCIAL_CODLOG (
   ID ASC
)
local
go


-- Номер ревизии: 67924
-- Номер версии: 1.18
-- Комментарий: Stand-In. Журналы логов.

create index CL_FIO_BIRTHDAY_DATE_INDEX on CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE DESC
)
local
go

create index FCL_FIO_BIRTHDAY_DATE_INDEX on FINANCIAL_CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE DESC
)
local
go

create index SL_FIO_BIRTHDAY_DATE_INDEX on SYSTEMLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE DESC
)
local
go

create index UL_FIO_BIRTHDAY_DATE_INDEX on USERLOG (
    UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
    BIRTHDAY ASC,
    START_DATE DESC
)
local
go

-- Номер ревизии: 69557
-- Номер версии: 1.18
-- Комментарий: Подбор номеров. Логирование.
create table REQUEST_CARD_BY_PHONE_LOG 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   EVENT_DATE           TIMESTAMP            not null,
   BLOCK_ID             INTEGER              not null,
   FIO                  VARCHAR2(360)        not null,
   DOC_TYPE             VARCHAR2(32)         not null,
   DOC_NUMBER           VARCHAR2(32)         not null,
   BIRTHDAY             TIMESTAMP
)
partition by range  (EVENT_DATE)     interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition P_START         values less than (to_date('01-01-2014','DD-MM-YYYY')))
go

create sequence S_REQUEST_CARD_BY_PHONE_LOG
go


create index I_REQUEST_CARD_BY_PHONE_LOG on REQUEST_CARD_BY_PHONE_LOG (
   BLOCK_ID ASC,
   EVENT_DATE ASC
)
local
go

create index I_REQ_CARD_BY_PHONE_LOG_LOGIN on REQUEST_CARD_BY_PHONE_LOG (
   BLOCK_ID ASC,
   LOGIN_ID ASC,
   EVENT_DATE ASC
)
local
go

-- Номер ревизии: 73487
-- Номер версии: 1.18
-- Комментарий: Доработка механизма логирования.
alter table systemlog add (add_info varchar2(128))
go
alter table userlog add (add_info varchar2(128))
go
alter table codlog add (add_info varchar2(128))
go
alter table financial_codlog add (add_info varchar2(128))
go
alter table csa_codlog add (add_info varchar2(128))
go
alter table csa_systemlog add (add_info varchar2(128))
go


-- Номер ревизии: 74130
-- Номер версии: 1.18
-- Комментарий: Гостевой вход. Systemlog
/*==============================================================*/
/* Table: GUEST_CSA_SYSTEMLOG                                   */
/*==============================================================*/
create table GUEST_CSA_SYSTEMLOG 
(
   ID                   INTEGER              not null,
   MSG_LEVEL            CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE              CLOB                 not null,
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   MESSAGE_SOURCE       VARCHAR2(16)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(50),
   DOC_SERIES           VARCHAR2(50),
   BIRTHDATE            TIMESTAMP,
   DEPARTMENT_CODE      VARCHAR2(4),
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   THREAD_INFO          INTEGER              not null,
   LOG_UID              VARCHAR2(32),
   ADD_INFO             VARCHAR2(128)
)
partition by range
 (START_DATE)
    interval (numtoyminterval(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('1-01-2015','DD-MM-YYYY')))
go

create sequence S_GUEST_CSA_SYSTEMLOG
cache 15000
go
/*==============================================================*/
/* Index: I_G_CSA_SL_DI_DATE                                    */
/*==============================================================*/
create index I_G_CSA_SL_DI_DATE on GUEST_CSA_SYSTEMLOG (
   replace(DOC_NUMBER,' ','') ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CSA_SL_IP_DATE                                    */
/*==============================================================*/
create index I_G_CSA_SL_IP_DATE on GUEST_CSA_SYSTEMLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CSA_SL_LOGIN_DATE                                 */
/*==============================================================*/
create index I_G_CSA_SL_LOGIN_DATE on GUEST_CSA_SYSTEMLOG (
   LOGIN ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CSA_SL_PHONE_DATE                                 */
/*==============================================================*/
create index I_G_CSA_SL_PHONE_DATE on GUEST_CSA_SYSTEMLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CSA_SL_FIO                                        */
/*==============================================================*/
create index I_G_CSA_SL_FIO on GUEST_CSA_SYSTEMLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Table: GUEST_SYSTEMLOG                                       */
/*==============================================================*/
create table GUEST_SYSTEMLOG 
(
   ID                   INTEGER              not null,
   MSG_LEVEL            CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE              CLOB                 not null,
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   MESSAGE_SOURCE       VARCHAR2(16)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(50),
   DOC_SERIES           VARCHAR2(50),
   BIRTHDATE            TIMESTAMP,
   TB                   VARCHAR2(4),
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   THREAD_INFO          INTEGER              not null,
   NODE_ID              INTEGER              not null,
   DEPARTMENT_NAME      VARCHAR2(256)
)
partition by range
 (START_DATE)
    interval (numtoyminterval(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('1-01-2015','DD-MM-YYYY')))
go

create sequence S_GUEST_SYSTEMLOG
cache 15000
go

/*==============================================================*/
/* Index: I_G_SL_DI_DATE                                        */
/*==============================================================*/
create index I_G_SL_DI_DATE on GUEST_SYSTEMLOG (
   replace(DOC_NUMBER,' ','') ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_SL_PHONE_DATE                                     */
/*==============================================================*/
create index I_G_SL_PHONE_DATE on GUEST_SYSTEMLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_SL_FIO_DATE                                       */
/*==============================================================*/
create index I_G_SL_FIO_DATE on GUEST_SYSTEMLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
local
go

-- Номер ревизии: 74323
-- Номер версии: 1.18
-- Комментарий: Логирование гостевого входа(журнал сообщений Модель БД)

create sequence S_GUEST_CODLOG
cache 15000
go

/*==============================================================*/
/* Table: GUEST_CODLOG                                          */
/*==============================================================*/
create table GUEST_CODLOG 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(80)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   GUEST_CODE           INTEGER              not null,
   SESSION_ID           VARCHAR2(64)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   TB                   VARCHAR2(4),
   BIRTHDATE            TIMESTAMP,
   NODE_ID              INTEGER              not null,
   THREAD_INFO          INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_LOGIN          VARCHAR2(50)
)
partition by range
 (START_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-01-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: I_G_CL_PHONE_DATE                                     */
/*==============================================================*/
create index I_G_CL_PHONE_DATE on GUEST_CODLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CL_DI_DATE                                        */
/*==============================================================*/
create index I_G_CL_DI_DATE on GUEST_CODLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CL_FIO_DATE                                       */
/*==============================================================*/
create index I_G_CL_FIO_DATE on GUEST_CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CL_PK                                             */
/*==============================================================*/
create index I_G_CL_PK on GUEST_CODLOG (
   ID ASC
)
reverse
go


-- Номер ревизии: 74373
-- Номер версии: 1.18
-- Комментарий: Логирование гостевого входа(журнал действий гостя)

create sequence S_GUEST_USERLOG
cache 15000
go

/*==============================================================*/
/* Table: GUEST_USERLOG                                         */
/*==============================================================*/
create table GUEST_USERLOG 
(
   ID                   INTEGER              not null,
   DESCRIPTION          VARCHAR2(160),
   DESCRIPTION_KEY      VARCHAR2(160),
   SUCCESS              CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   OPERATION_KEY        VARCHAR2(300),
   PARAMETERS           VARCHAR2(4000),
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   EXECUTION_TIME       INTEGER,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   BIRTHDATE            TIMESTAMP,
   NODE_ID              INTEGER              not null,
   TB                   VARCHAR2(4),
   THREAD_INFO          INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_LOGIN          VARCHAR2(50)
)
partition by range
 (START_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-01-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: I_G_UL_DI_DATE                                        */
/*==============================================================*/
create index I_G_UL_DI_DATE on GUEST_USERLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_UL_PHONE_DATE                                     */
/*==============================================================*/
create index I_G_UL_PHONE_DATE on GUEST_USERLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_UL_PK                                             */
/*==============================================================*/
create index I_G_UL_PK on GUEST_USERLOG (
   ID ASC
)
reverse
go

/*==============================================================*/
/* Index: I_G_UL_FIO_DATE                                       */
/*==============================================================*/
create index I_G_UL_FIO_DATE on GUEST_USERLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
go

-- Номер ревизии: 74474
-- Номер версии: 1.18
-- Комментарий: Логирование гостевого входа(журнал сообщенй ЦСА гостя)

create sequence S_GUEST_CSA_CODLOG
cache 15000
go

/*==============================================================*/
/* Table: GUEST_CSA_CODLOG                                      */
/*==============================================================*/
create table GUEST_CSA_CODLOG 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(80)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   BIRTHDATE            TIMESTAMP,
   DEPARTMENT_CODE      VARCHAR2(4),
   OPERATION_UID        VARCHAR2(32)         not null,
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_CODE           INTEGER              not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   ERROR_CODE           VARCHAR2(10),
   THREAD_INFO          INTEGER              not null,
   LOG_UID              VARCHAR2(32),
   ADD_INFO             VARCHAR2(128)
)
partition by range
 (START_DATE)
    interval (numtoyminterval(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('1-01-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: G_CSA_CL_PK                                           */
/*==============================================================*/
create unique index G_CSA_CL_PK on GUEST_CSA_CODLOG (
   ID ASC
)
reverse
go

/*==============================================================*/
/* Index: I_G_CSA_CL_DI_DATE                                    */
/*==============================================================*/
create index I_G_CSA_CL_DI_DATE on GUEST_CSA_CODLOG (
   replace(DOC_NUMBER,' ','') ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CSA_CL_FIO                                        */
/*==============================================================*/
create index I_G_CSA_CL_FIO on GUEST_CSA_CODLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CSA_CL_IP_DATA                                    */
/*==============================================================*/
create index I_G_CSA_CL_IP_DATA on GUEST_CSA_CODLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CSA_CL_LOGIN_DATE                                 */
/*==============================================================*/
create index I_G_CSA_CL_LOGIN_DATE on GUEST_CSA_CODLOG (
   LOGIN ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CSA_CL_PHONE_DATE                                 */
/*==============================================================*/
create index I_G_CSA_CL_PHONE_DATE on GUEST_CSA_CODLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

-- Номер ревизии: 74698
-- Номер версии: 1.18
-- Комментарий: Журнал сообщений гостя(АРМ сотрудника)

drop index I_G_CL_DI_DATE;

-- Номер ревизии: 74725
-- Номер версии: 1.18
-- Комментарий: Гостевой журнал системных действий, гостевой журнал действий пользователя.

drop index I_G_SL_DI_DATE
go

drop table GUEST_USERLOG
go

/*==============================================================*/
/* Table: GUEST_USERLOG                                         */
/*==============================================================*/
create table GUEST_USERLOG 
(
   ID                   INTEGER              not null,
   DESCRIPTION          VARCHAR2(160),
   DESCRIPTION_KEY      VARCHAR2(160),
   SUCCESS              CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   OPERATION_KEY        VARCHAR2(300),
   PARAMETERS           VARCHAR2(4000),
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   EXECUTION_TIME       INTEGER,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   BIRTHDATE            TIMESTAMP,
   NODE_ID              INTEGER              not null,
   TB                   VARCHAR2(4),
   THREAD_INFO          INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_LOGIN          VARCHAR2(50)
)
partition by range
 (START_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
         subpartition by hash ("ID") subpartitions 16
 (partition
         P_START
        values less than (to_date('01-01-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: I_G_UL_PHONE_DATE                                     */
/*==============================================================*/
create index I_G_UL_PHONE_DATE on GUEST_USERLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_UL_PK                                             */
/*==============================================================*/
create index I_G_UL_PK on GUEST_USERLOG (
   ID ASC
)
local
go

/*==============================================================*/
/* Index: I_G_UL_FIO_DATE                                       */
/*==============================================================*/
create index I_G_UL_FIO_DATE on GUEST_USERLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
go

-- Номер ревизии: 74768
-- Номер версии: 1.18
-- Комментарий: Гостевой журнал системных действий CSA

drop index I_G_CSA_SL_LOGIN_DATE
go

drop index I_G_CSA_SL_IP_DATE
go

drop index I_G_CSA_SL_DI_DATE
go

-- Номер ревизии: 74785
-- Номер версии: 1.18
-- Комментарий: Журнал сообщений ЦСА гостя(АРМ сотрудника)

drop index I_G_CSA_CL_IP_DATA;
drop index  I_G_CSA_CL_LOGIN_DATE;
drop index I_G_CSA_CL_DI_DATE;

-- Номер ревизии: 75255
-- Номер версии: 1.18
-- Комментарий: Мониторинг бизнес операций
create table MONITORING_BUSINESS_OPERATION
( 
   START_DATE TIMESTAMP not null, 
   CREATION_DATE TIMESTAMP not null, 
   PROVIDER_UUID VARCHAR2(32), 
   OPERATION_TYPE VARCHAR2(30) not null, 
   ACCOUNT_TYPE VARCHAR2(20), 
   AMOUNT NUMBER(15,4), 
   AMOUNT_CUR CHAR(3), 
   APPLICATION VARCHAR2(20) not null, 
   TB VARCHAR2(4), 
   PLATFORM VARCHAR2(20), 
   STATE_CODE VARCHAR2(20) not null, 
   NODE_ID INTEGER not null 
) 
partition by range 
    (START_DATE) 
interval (NUMTODSINTERVAL(1,'DAY')) 
    (partition P_START values less than (to_date('01-03-2015','DD-MM-YYYY'))) 
go

create table MONITORING_USER_LOGIN
( 
   START_DATE TIMESTAMP not null,   
   APPLICATION VARCHAR2(20) not null, 
   TB VARCHAR2(4), 
   PLATFORM VARCHAR2(20),  
   NODE_ID INTEGER not null 
) 
partition by range 
    (START_DATE) 
interval (NUMTODSINTERVAL(1,'DAY')) 
    (partition P_START values less than (to_date('01-03-2015','DD-MM-YYYY'))) 
go

-- Номер ревизии: 76113
-- Номер версии: 1.18
-- Комментарий: Мониторинг бизнес операций (ч2)

create table BUS_DOCUMENTS_MONITORING_STATE( 
    REPORT_DATE TIMESTAMP not null, 
    STATE VARCHAR2(10) not null, 
    STATE_DESCR VARCHAR2(200) 
) 
go

create table AGREGATE_BUS_DOC_MONITORING (
    REPORT_DATE TIMESTAMP not null, 
    PROVIDER_UUID VARCHAR2(32), 
    DOCUMENT_TYPE VARCHAR2(30) not null, 
    ACCOUNT_TYPE VARCHAR2(20), 
    AMOUNT NUMBER(15,4), 
    AMOUNT_CUR CHAR(3), 
    APPLICATION VARCHAR2(20) not null, 
    TB VARCHAR2(4), 
    PLATFORM VARCHAR2(20), 
    STATE_CODE VARCHAR2(20) not null, 
    NODE_ID INTEGER not null, 
    COUNT INTEGER not null
)
partition by range 
(REPORT_DATE) 
interval (NUMTOYMINTERVAL(1,'MONTH'))
(partition P_START values less than (to_date('01-04-2015','DD-MM-YYYY'))) 
go 

-- Номер ревизии: 76647
-- Номер версии: 1.18
-- Комментарий: Мониторинг бизнес операций (ч9)
create table PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_KEY         VARCHAR2(256)        not null,
   PROPERTY_VALUE       VARCHAR2(500)        not null,
   CATEGORY             VARCHAR2(80),
   constraint PK_PROPERTIES primary key (ID)
)
go

create sequence S_PROPERTIES
go

create unique index PROPERTIES_UNIQ on PROPERTIES (
   PROPERTY_KEY ASC,
   CATEGORY ASC
)
go

CREATE OR REPLACE PACKAGE LOG_SERVICES_PKG AS
	--Агрегация данных для мониторинга по бизнес операциям. 
	--Используется в джобе: AGREGATE_OPERATION_JOB, запускается раз в сутки.
	PROCEDURE AGREGATEOPERATIONMONITORING;
END LOG_SERVICES_PKG;
go

CREATE OR REPLACE PACKAGE BODY LOG_SERVICES_PKG AS
	PROCEDURE AGREGATEOPERATIONMONITORING AS
	   err_num NUMBER;
	   err_msg VARCHAR2(100);
	   needCreate VARCHAR2(10);
	BEGIN 
	   select PROPERTY_VALUE into needCreate from PROPERTIES where PROPERTY_KEY='reports.business.aggregate.on';
	   IF needCreate = 'true' THEN
		  INSERT INTO AGREGATE_BUS_DOC_MONITORING (
			 REPORT_DATE, 
			 PROVIDER_UUID, 
			 DOCUMENT_TYPE, 
			 ACCOUNT_TYPE, 
			 AMOUNT, 
			 AMOUNT_CUR, 
			 APPLICATION, 
			 TB, 
			 PLATFORM, 
			 STATE_CODE, 
			 NODE_ID, 
			 "COUNT"
		  ) SELECT
			 MAX(START_DATE) AS REPORT_DATE,
			 MIN(PROVIDER_UUID) AS PROVIDER_UUID,
			 MIN(OPERATION_TYPE) AS DOCUMENT_TYPE,
			 MIN(ACCOUNT_TYPE) AS ACCOUNT_TYPE,
			 SUM(AMOUNT) AS AMOUNT,
			 MIN(AMOUNT_CUR) AS AMOUNT_CUR,
			 MIN(APPLICATION) AS APPLICATION,
			 MIN(TB) AS TB,
			 MIN(PLATFORM) AS PLATFORM,
			 MIN(STATE_CODE) AS STATE_CODE,
			 MIN(NODE_ID) AS NODE_ID,
			 COUNT(1) AS "COUNT"
		  FROM
			 MONITORING_BUSINESS_OPERATION 
		  WHERE
			 (AMOUNT_CUR IS NULL OR AMOUNT_CUR = 'RUB' OR AMOUNT_CUR = 'RUR') AND
			 START_DATE BETWEEN (TRUNC(sysdate - 1)) AND (TRUNC(sysdate)) 
		  GROUP BY
			 OPERATION_TYPE,
			 PROVIDER_UUID,
			 ACCOUNT_TYPE,
			 TB,
			 PLATFORM,
			 STATE_CODE,
			 NODE_ID,
			 AMOUNT_CUR;
				
		  INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE) values (sysdate, 'SUCCESS');
	   END IF;
	EXCEPTION
	   WHEN OTHERS THEN 
		  err_num := SQLCODE;
		  err_msg := SUBSTR(SQLERRM, 1, 100);
		  INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE, STATE_DESCR) values (sysdate, 'ERROR', TO_CHAR(err_num) || ':' || err_msg);
	end;
END LOG_SERVICES_PKG;
go

begin
DBMS_SCHEDULER.CREATE_JOB (
   job_name                 =>  'AGREGATE_OPERATION_JOB', 
   job_type                 =>  'PLSQL_BLOCK',
   job_action               =>  'BEGIN LOG_SERVICES_PKG.AGREGATEOPERATIONMONITORING; END;',
   start_date               =>  to_date('2015-03-23 01:00','YYYY-MM-DD HH24:MI'),
   repeat_interval          =>  'FREQ=DAILY',
   enabled => TRUE
   );
end;
go

-- Номер ревизии: 77156
-- Номер версии: 1.18
-- Комментарий: Маппинг ошибок. Не забыть заменить %линк к основной БД% !!!
/*==============================================================*/
/* Table: EXCEPTIONS_LOG                                        */
/*==============================================================*/
create table EXCEPTIONS_LOG 
(
   CREATION_DATE        TIMESTAMP            not null,
   HASH                 VARCHAR2(277)        not null
)
partition by range
 (CREATION_DATE)
    interval (NUMTODSINTERVAL(1,'day'))
 (partition
         P_FIRST
        values less than (to_date('1-11-2013','DD-MM-YYYY')))
go

/*==============================================================*/
/* Table: EXCEPTION_COUNTERS                                    */
/*==============================================================*/
create table EXCEPTION_COUNTERS 
(
   EXCEPTION_HASH       VARCHAR2(277)        not null,
   EXCEPTION_DATE       TIMESTAMP            not null,
   EXCEPTION_COUNT      INTEGER              not null,
   constraint PK_EXCEPTION_COUNTERS primary key (EXCEPTION_HASH, EXCEPTION_DATE)
)
go

/*==============================================================*/
/* Table: EXCEPTION_ENTRY                                       */
/*==============================================================*/
create table EXCEPTION_ENTRY 
(
   ID                   INTEGER              not null,
   KIND                 CHAR(1)              not null,
   HASH                 VARCHAR2(277)        not null,
   OPERATION            VARCHAR2(160),
   APPLICATION          VARCHAR2(20),
   DETAIL               CLOB                 not null,
   SYSTEM               VARCHAR2(64),
   ERROR_CODE           VARCHAR2(20),
   constraint PK_EXCEPTION_ENTRY primary key (ID)
)

go

create sequence S_EXCEPTION_ENTRY
go

/*==============================================================*/
/* Index: IND_EXCEPTION_ENTRY_HASH                              */
/*==============================================================*/
create unique index IND_EXCEPTION_ENTRY_HASH on EXCEPTION_ENTRY (
   HASH ASC
)
go

create or replace package LOG_SERVICES_PKG as
   procedure AGREGATEOPERATIONMONITORING;
   procedure aggregateExceptionInformation;
end LOG_SERVICES_PKG;
go

create or replace package body LOG_SERVICES_PKG as
   procedure AGREGATEOPERATIONMONITORING as
      err_num NUMBER;
      err_msg VARCHAR2(100);
      needCreate VARCHAR2(10);
   begin
      select PROPERTY_VALUE into needCreate from PROPERTIES where PROPERTY_KEY='reports.business.aggregate.on';
      IF needCreate = 'true' THEN
         INSERT INTO AGREGATE_BUS_DOC_MONITORING (
            REPORT_DATE, 
            PROVIDER_UUID, 
            DOCUMENT_TYPE, 
            ACCOUNT_TYPE, 
            AMOUNT, 
            AMOUNT_CUR, 
            APPLICATION, 
            TB, 
            PLATFORM, 
            STATE_CODE, 
            NODE_ID, 
            "COUNT"
         ) SELECT
            MAX(START_DATE) AS REPORT_DATE,
            MIN(PROVIDER_UUID) AS PROVIDER_UUID,
            MIN(OPERATION_TYPE) AS DOCUMENT_TYPE,
            MIN(ACCOUNT_TYPE) AS ACCOUNT_TYPE,
            SUM(AMOUNT) AS AMOUNT,
            MIN(AMOUNT_CUR) AS AMOUNT_CUR,
            MIN(APPLICATION) AS APPLICATION,
            MIN(TB) AS TB,
            MIN(PLATFORM) AS PLATFORM,
            MIN(STATE_CODE) AS STATE_CODE,
            MIN(NODE_ID) AS NODE_ID,
            COUNT(1) AS "COUNT"
         FROM
            MONITORING_BUSINESS_OPERATION 
         WHERE
            (AMOUNT_CUR IS NULL OR AMOUNT_CUR = 'RUB' OR AMOUNT_CUR = 'RUR') AND
            START_DATE BETWEEN (TRUNC(sysdate - 1)) AND (TRUNC(sysdate)) 
         GROUP BY
            OPERATION_TYPE,
            PROVIDER_UUID,
            ACCOUNT_TYPE,
            TB,
            PLATFORM,
            STATE_CODE,
            NODE_ID,
            AMOUNT_CUR;
   				
         INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE) values (sysdate, 'SUCCESS');
      END IF;
   EXCEPTION
      WHEN OTHERS THEN 
         err_num := SQLCODE;
         err_msg := SUBSTR(SQLERRM, 1, 100);
         INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE, STATE_DESCR) values (sysdate, 'ERROR', TO_CHAR(err_num) || ':' || err_msg);
   end;
   procedure aggregateExceptionInformation as
   CREATE OR REPLACE PROCEDURE aggregateExceptionInformation(aggregationDate TIMESTAMP) as
   BEGIN
    execute immediate 'INSERT INTO EXCEPTION_COUNTERS (EXCEPTION_HASH, EXCEPTION_DATE, EXCEPTION_COUNT) '||
       'SELECT HASH, to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' ), count(1) FROM EXCEPTIONS_LOG '||
           'PARTITION FOR (to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' )) GROUP BY HASH';
   END;
   go;
end LOG_SERVICES_PKG;
go

begin
DBMS_SCHEDULER.CREATE_JOB (
   job_name                 =>  'AGGREGATE_EXC_INFO', 
   job_type                 =>  'PLSQL_BLOCK',
   job_action               =>  'BEGIN LOG_SERVICES_PKG.aggregateExceptionInformation(sysdate-1); END;',
   start_date               =>  to_date('2015-03-01 02:25','YYYY-MM-DD HH24:MI'),
   repeat_interval          =>  'FREQ=DAILY',
   enabled => TRUE
   );
end;
/
go

insert into EXCEPTION_ENTRY (ID, KIND, HASH, APPLICATION, OPERATION, DETAIL, SYSTEM, ERROR_CODE) 
    select 
        S_EXCEPTION_ENTRY.nextval, 
        exceptions.Kind, exceptions.Hash, 
        exceptions.Application, 
        exceptions.Operation, 
        exceptions.Detail, 
        exceptions.System, 
        exceptions.Error_Code 
    from EXCEPTION_ENTRY@%линк к основной БД% exceptions
    where not exists (
        select 1 from EXCEPTION_ENTRY entry
        where entry.HASH = exceptions.Hash
)
go


-- Номер ревизии: 77204
-- Номер версии: 1.18
-- Комментарий: Маппинг ошибок. ИОК
create or replace package body LOG_SERVICES_PKG as
   procedure AGREGATEOPERATIONMONITORING as
      err_num NUMBER;
      err_msg VARCHAR2(100);
      needCreate VARCHAR2(10);
   begin
      select PROPERTY_VALUE into needCreate from PROPERTIES where PROPERTY_KEY='reports.business.aggregate.on';
      IF needCreate = 'true' THEN
         INSERT INTO AGREGATE_BUS_DOC_MONITORING (
            REPORT_DATE, 
            PROVIDER_UUID, 
            DOCUMENT_TYPE, 
            ACCOUNT_TYPE, 
            AMOUNT, 
            AMOUNT_CUR, 
            APPLICATION, 
            TB, 
            PLATFORM, 
            STATE_CODE, 
            NODE_ID, 
            "COUNT"
         ) SELECT
            MAX(START_DATE) AS REPORT_DATE,
            MIN(PROVIDER_UUID) AS PROVIDER_UUID,
            MIN(OPERATION_TYPE) AS DOCUMENT_TYPE,
            MIN(ACCOUNT_TYPE) AS ACCOUNT_TYPE,
            SUM(AMOUNT) AS AMOUNT,
            MIN(AMOUNT_CUR) AS AMOUNT_CUR,
            MIN(APPLICATION) AS APPLICATION,
            MIN(TB) AS TB,
            MIN(PLATFORM) AS PLATFORM,
            MIN(STATE_CODE) AS STATE_CODE,
            MIN(NODE_ID) AS NODE_ID,
            COUNT(1) AS "COUNT"
         FROM
            MONITORING_BUSINESS_OPERATION 
         WHERE
            (AMOUNT_CUR IS NULL OR AMOUNT_CUR = 'RUB' OR AMOUNT_CUR = 'RUR') AND
            START_DATE BETWEEN (TRUNC(sysdate - 1)) AND (TRUNC(sysdate)) 
         GROUP BY
            OPERATION_TYPE,
            PROVIDER_UUID,
            ACCOUNT_TYPE,
            TB,
            PLATFORM,
            STATE_CODE,
            NODE_ID,
            AMOUNT_CUR;
   				
         INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE) values (sysdate, 'SUCCESS');
      END IF;
   EXCEPTION
      WHEN OTHERS THEN 
         err_num := SQLCODE;
         err_msg := SUBSTR(SQLERRM, 1, 100);
         INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE, STATE_DESCR) values (sysdate, 'ERROR', TO_CHAR(err_num) || ':' || err_msg);
   end;
   procedure aggregateExceptionInformation as
       aggregationDate TIMESTAMP;
   BEGIN
   execute immediate 'INSERT INTO EXCEPTION_COUNTERS (EXCEPTION_HASH, EXCEPTION_DATE, EXCEPTION_COUNT) '||
      'SELECT HASH, to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' ), count(1) FROM EXCEPTIONS_LOG '||
          'PARTITION FOR (to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' )) GROUP BY HASH';
   END;
end LOG_SERVICES_PKG;
go

-- Номер ревизии: 78538
-- Номер версии: 1.18
-- Комментарий:  BUG088437: Ошибка при регистрации по неактивной карте

ALTER TABLE CSA_ACTION_LOG MODIFY ( "PATR_NAME" VARCHAR2(100) NULL )
GO

-- Номер ревизии: 78897
-- Номер версии: 1.18
-- Комментарий:  Мониторинг БО. Подсчет результатов для отчета по переводам(Модель БД)
create index I_AGREGATE_BUS_DOC_MONITORING on AGREGATE_BUS_DOC_MONITORING (
   DOCUMENT_TYPE ASC,
   REPORT_DATE ASC
)
local
go

-- Номер ревизии: 80347
-- Номер версии: 1.18
-- Комментарий:  BUG089660: [Гостевой][СБНКД] ошибка заполнении заявки СБНКД при выборе документа отличного от паспорта РФ 
alter table GUEST_CODLOG modify (DOC_SERIES varchar2(25))
go
alter table GUEST_CODLOG modify (DOC_NUMBER varchar2(25))
go
alter table GUEST_USERLOG modify (DOC_SERIES varchar2(25))
go
alter table GUEST_USERLOG modify (DOC_NUMBER varchar2(25))
go
alter table GUEST_SYSTEMLOG modify (DOC_SERIES varchar2(25))
go
alter table GUEST_SYSTEMLOG modify (DOC_NUMBER varchar2(25))
go
alter table GUEST_CSA_CODLOG modify (DOC_SERIES varchar2(25))
go
alter table GUEST_CSA_CODLOG modify (DOC_NUMBER varchar2(25))
go
alter table GUEST_CSA_SYSTEMLOG modify (DOC_SERIES varchar2(25))
go
alter table GUEST_CSA_SYSTEMLOG modify (DOC_NUMBER varchar2(25))
go




-- Номер ревизии: 80708
-- Номер версии: 1.18
-- Комментарий: BUG089698: [Отчеты по БО] Не работает разовая выгрузка отчета
create or replace package body LOG_SERVICES_PKG as
   procedure AGREGATEOPERATIONMONITORING as
      err_num NUMBER;
      err_msg VARCHAR2(100);
      needCreate VARCHAR2(10);
   begin
      select PROPERTY_VALUE into needCreate from PROPERTIES where PROPERTY_KEY='reports.business.aggregate.on';
      IF needCreate = 'true' THEN
         INSERT INTO AGREGATE_BUS_DOC_MONITORING (
            REPORT_DATE, 
            PROVIDER_UUID, 
            DOCUMENT_TYPE, 
            ACCOUNT_TYPE, 
            AMOUNT, 
            AMOUNT_CUR, 
            APPLICATION, 
            TB, 
            PLATFORM, 
            STATE_CODE, 
            NODE_ID, 
            "COUNT"
         ) SELECT
            MAX(START_DATE) AS REPORT_DATE,
            MIN(PROVIDER_UUID) AS PROVIDER_UUID,
            MIN(OPERATION_TYPE) AS DOCUMENT_TYPE,
            MIN(ACCOUNT_TYPE) AS ACCOUNT_TYPE,
            SUM(AMOUNT) AS AMOUNT,
            MIN(AMOUNT_CUR) AS AMOUNT_CUR,
            MIN(APPLICATION) AS APPLICATION,
            MIN(TB) AS TB,
            MIN(PLATFORM) AS PLATFORM,
            MIN(STATE_CODE) AS STATE_CODE,
            MIN(NODE_ID) AS NODE_ID,
            COUNT(1) AS "COUNT"
         FROM
            MONITORING_BUSINESS_OPERATION 
         WHERE
            (OPERATION_TYPE not in ('AOC', 'AOC_ALF') or STATE_CODE ='EXECUTED') AND
            (AMOUNT_CUR IS NULL OR AMOUNT_CUR = 'RUB' OR AMOUNT_CUR = 'RUR') AND
            START_DATE BETWEEN (TRUNC(sysdate - 1)) AND (TRUNC(sysdate)) 
         GROUP BY
            OPERATION_TYPE,
            PROVIDER_UUID,
            ACCOUNT_TYPE,
            TB,
            PLATFORM,
            STATE_CODE,
            NODE_ID,
            AMOUNT_CUR;
   				
         INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE) values (sysdate, 'SUCCESS');
      END IF;
   EXCEPTION
      WHEN OTHERS THEN 
         err_num := SQLCODE;
         err_msg := SUBSTR(SQLERRM, 1, 100);
         INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE, STATE_DESCR) values (sysdate, 'ERROR', TO_CHAR(err_num) || ':' || err_msg);
   end;
   procedure aggregateExceptionInformation as
       aggregationDate TIMESTAMP;
   BEGIN
   execute immediate 'INSERT INTO EXCEPTION_COUNTERS (EXCEPTION_HASH, EXCEPTION_DATE, EXCEPTION_COUNT) '||
      'SELECT HASH, to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' ), count(1) FROM EXCEPTIONS_LOG '||
          'PARTITION FOR (to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' )) GROUP BY HASH';
   END;
end LOG_SERVICES_PKG;
go

-- Номер ревизии: 83053
-- Номер версии: 1.18
-- Комментарий: BUG091172: Мапинг ошибок : Некорректно считается статистика 
create or replace package LOG_SERVICES_PKG as
   procedure AGREGATEOPERATIONMONITORING;
   procedure aggregateExceptionInformation(aggregationDate TIMESTAMP);
   procedure updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in clob, exceptionSystem in varchar, exceptionErrorCode in varchar);
end LOG_SERVICES_PKG;
go

create or replace package body LOG_SERVICES_PKG as
   procedure AGREGATEOPERATIONMONITORING as
      err_num NUMBER;
      err_msg VARCHAR2(100);
      needCreate VARCHAR2(10);
   begin
      select PROPERTY_VALUE into needCreate from PROPERTIES where PROPERTY_KEY='reports.business.aggregate.on';
      IF needCreate = 'true' THEN
         INSERT INTO AGREGATE_BUS_DOC_MONITORING (
            REPORT_DATE, 
            PROVIDER_UUID, 
            DOCUMENT_TYPE, 
            ACCOUNT_TYPE, 
            AMOUNT, 
            AMOUNT_CUR, 
            APPLICATION, 
            TB, 
            PLATFORM, 
            STATE_CODE, 
            NODE_ID, 
            "COUNT"
         ) SELECT
            MAX(START_DATE) AS REPORT_DATE,
            MIN(PROVIDER_UUID) AS PROVIDER_UUID,
            MIN(OPERATION_TYPE) AS DOCUMENT_TYPE,
            MIN(ACCOUNT_TYPE) AS ACCOUNT_TYPE,
            SUM(AMOUNT) AS AMOUNT,
            MIN(AMOUNT_CUR) AS AMOUNT_CUR,
            MIN(APPLICATION) AS APPLICATION,
            MIN(TB) AS TB,
            MIN(PLATFORM) AS PLATFORM,
            MIN(STATE_CODE) AS STATE_CODE,
            MIN(NODE_ID) AS NODE_ID,
            COUNT(1) AS "COUNT"
         FROM
            MONITORING_BUSINESS_OPERATION 
         WHERE
            (OPERATION_TYPE not in ('AOC', 'AOC_ALF') or STATE_CODE ='EXECUTED') AND
            (AMOUNT_CUR IS NULL OR AMOUNT_CUR = 'RUB' OR AMOUNT_CUR = 'RUR') AND
            START_DATE BETWEEN (TRUNC(sysdate - 1)) AND (TRUNC(sysdate)) 
         GROUP BY
            OPERATION_TYPE,
            PROVIDER_UUID,
            ACCOUNT_TYPE,
            TB,
            PLATFORM,
            STATE_CODE,
            NODE_ID,
            AMOUNT_CUR;
   				
         INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE) values (sysdate, 'SUCCESS');
      END IF;
   EXCEPTION
      WHEN OTHERS THEN 
         err_num := SQLCODE;
         err_msg := SUBSTR(SQLERRM, 1, 100);
         INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE, STATE_DESCR) values (sysdate, 'ERROR', TO_CHAR(err_num) || ':' || err_msg);
   end;
   procedure aggregateExceptionInformation(aggregationDate TIMESTAMP) as
   BEGIN
   execute immediate 'INSERT INTO EXCEPTION_COUNTERS (EXCEPTION_HASH, EXCEPTION_DATE, EXCEPTION_COUNT) '||
     'SELECT HASH, to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' ), count(1) FROM EXCEPTIONS_LOG '||
         'PARTITION FOR (to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' )) GROUP BY HASH';
   END;
   procedure updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in clob, exceptionSystem in varchar, exceptionErrorCode in varchar) as
   begin
      insert into EXCEPTIONS_LOG(CREATION_DATE, HASH) values (SYSDATE, exceptionHash);
   
      insert into EXCEPTION_ENTRY (ID, KIND, HASH, OPERATION, APPLICATION, DETAIL, SYSTEM, ERROR_CODE) 
          select S_EXCEPTION_ENTRY.nextval, exceptionKind, exceptionHash, exceptionOperation, exceptionApplication, exceptionDetail, exceptionSystem, exceptionErrorCode from DUAL
          where not exists (select 1 from EXCEPTION_ENTRY where EXCEPTION_ENTRY.HASH = exceptionHash);
   
      commit;
   end;
end LOG_SERVICES_PKG;
go


-- Номер ревизии: 83158
-- Номер версии: 1.18
-- Комментарий: MDM
create table MDM_PROFILES 
(
   ID                   INTEGER              not null,
   MDM_ID               VARCHAR2(10)         not null,
   LAST_NAME            VARCHAR2(320)        not null,
   FIRST_NAME           VARCHAR2(320)        not null,
   MIDDLE_NAME          VARCHAR2(320),
   GENDER               VARCHAR2(6),
   BIRTHDAY             TIMESTAMP,
   BIRTH_PLACE          VARCHAR2(510),
   TAX_ID               VARCHAR2(20),
   RESIDENT             CHAR(1)              not null,
   EMPLOYEE             CHAR(1)              not null,
   SHAREHOLDER          CHAR(1)              not null,
   INSIDER              CHAR(1)              not null,
   CITIZENSHIP          VARCHAR2(120),
   LITERACY             CHAR(1)              not null,
   TARIFF_PLAN_NAME     VARCHAR2(50),
   TARIFF_PLAN_CODE     VARCHAR2(50),
   constraint PK_MDM_PROFILES primary key (ID),
   constraint AK_MDM_PROFILE_ID unique (MDM_ID)
)

go

create sequence S_MDM_PROFILES
go

/*==============================================================*/
/* Index: I_MDM_PROFILE_ID                                      */
/*==============================================================*/
create unique index I_MDM_PROFILE_ID on MDM_PROFILES (
   MDM_ID ASC
)
go

/*==============================================================*/
/* Table: MDM_PROFILE_ACCOUNTS                                  */
/*==============================================================*/
create table MDM_PROFILE_ACCOUNTS 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   PRODUCT_NUMBER       VARCHAR2(255)        not null,
   START_DATE           TIMESTAMP,
   CLOSING_DATE         TIMESTAMP,
   constraint PK_MDM_PROFILE_ACCOUNTS primary key (ID)
)

go

create sequence S_MDM_PROFILE_ACCOUNTS
go

/*==============================================================*/
/* Table: MDM_PROFILE_CARDS                                     */
/*==============================================================*/
create table MDM_PROFILE_CARDS 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   PRODUCT_NUMBER       VARCHAR2(32)         not null,
   START_DATE           TIMESTAMP,
   EXPIRED_DATE         TIMESTAMP,
   constraint PK_MDM_PROFILE_CARDS primary key (ID)
)

go

create sequence S_MDM_PROFILE_CARDS
go

/*==============================================================*/
/* Table: MDM_PROFILE_DEPO_ACCOUNTS                             */
/*==============================================================*/
create table MDM_PROFILE_DEPO_ACCOUNTS 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   PRODUCT_NUMBER       VARCHAR2(255)        not null,
   constraint PK_MDM_PROFILE_DEPO_ACCOUNTS primary key (ID)
)

go

create sequence S_MDM_PROFILE_DEPO_ACCOUNTS
go

/*==============================================================*/
/* Table: MDM_PROFILE_DOCUMENTS                                 */
/*==============================================================*/
create table MDM_PROFILE_DOCUMENTS 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER,
   TYPE                 VARCHAR2(80)         not null,
   DOC_SERIES           VARCHAR2(20),
   DOC_NUMBER           VARCHAR2(255)        not null,
   ISSUED_BY            VARCHAR2(510),
   ISSUED_DATE          TIMESTAMP,
   constraint PK_MDM_PROFILE_DOCUMENTS primary key (ID)
)

go

create sequence S_MDM_PROFILE_DOCUMENTS
go

/*==============================================================*/
/* Table: MDM_PROFILE_INNER_IDS                                 */
/*==============================================================*/
create table MDM_PROFILE_INNER_IDS 
(
   MDM_ID               VARCHAR2(10)         not null,
   CSA_PROFILE_ID       INTEGER              not null
)
go

/*==============================================================*/
/* Index: I_MDM_PROFILE_IDS_ID                                  */
/*==============================================================*/
create unique index I_MDM_PROFILE_IDS_ID on MDM_PROFILE_INNER_IDS (
   CSA_PROFILE_ID ASC
)
go

/*==============================================================*/
/* Table: MDM_PROFILE_LOANS                                     */
/*==============================================================*/
create table MDM_PROFILE_LOANS 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   PRODUCT_NUMBER       VARCHAR2(64)         not null,
   ADDITIONAL_NUMBER    VARCHAR2(64),
   LEGAL_NUMBER         VARCHAR2(200),
   LEGAL_NAME           VARCHAR2(255),
   START_DATE           TIMESTAMP,
   CLOSING_DATE         TIMESTAMP,
   constraint PK_MDM_PROFILE_LOANS primary key (ID)
)

go

create sequence S_MDM_PROFILE_LOANS
go

/*==============================================================*/
/* Table: MDM_PROFILE_SERVICES                                  */
/*==============================================================*/
create table MDM_PROFILE_SERVICES 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   TYPE                 VARCHAR2(80)         not null,
   START_DATE           TIMESTAMP,
   END_DATE             TIMESTAMP,
   constraint PK_MDM_PROFILE_SERVICES primary key (ID)
)

go

create sequence S_MDM_PROFILE_SERVICES
go


create index "DXFK_MDM_ACCOUNT_TO_POFILE" on MDM_PROFILE_ACCOUNTS
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_ACCOUNTS
   add constraint FK_MDM_ACCOUNT_TO_POFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


create index "DXFK_MDM_CARD_TO_PROFILE" on MDM_PROFILE_CARDS
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_CARDS
   add constraint FK_MDM_CARD_TO_PROFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


create index "DXFK_MDM_DEPO_TO_POFILE" on MDM_PROFILE_DEPO_ACCOUNTS
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_DEPO_ACCOUNTS
   add constraint FK_MDM_DEPO_TO_POFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


create index "DXFK_MDM_DOCUMENT_TO_POFILE" on MDM_PROFILE_DOCUMENTS
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_DOCUMENTS
   add constraint FK_MDM_DOCUMENT_TO_POFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


create index "DXFK_MDM_IDS_TO_PROFILES" on MDM_PROFILE_INNER_IDS
(
   MDM_ID
)
go

alter table MDM_PROFILE_INNER_IDS
   add constraint FK_MDM_IDS_TO_PROFILES foreign key (MDM_ID)
      references MDM_PROFILES (MDM_ID)
      on delete cascade
go


create index "DXFK_MDM_LOAN_TO_POFILE" on MDM_PROFILE_LOANS
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_LOANS
   add constraint FK_MDM_LOAN_TO_POFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


create index "DXFK_MDM_SERVICE_TO_POFILE" on MDM_PROFILE_SERVICES
(
   PROFILE_ID
)
go

alter table MDM_PROFILE_SERVICES
   add constraint FK_MDM_SERVICE_TO_POFILE foreign key (PROFILE_ID)
      references MDM_PROFILES (ID)
      on delete cascade
go


-- Номер ревизии: 83158
-- Номер версии: 1.18
-- Комментарий: MDM
alter table MDM_PROFILES modify MDM_ID varchar2(64)
go

alter table MDM_PROFILE_INNER_IDS modify MDM_ID varchar2(64)
go


-- Номер ревизии: 83587
-- Номер версии: 1.18
-- Комментарий: MDM
alter table MDM_PROFILES drop column TARIFF_PLAN_NAME
go

alter table MDM_PROFILES drop column TARIFF_PLAN_CODE
go

-- Номер ревизии: 83723
-- Номер версии: 1.18
-- Комментарий: [Гостевой вход] Логгирование ЦСА-входа гостя. Создание таблицы записей о гостевом входе.
create table CSA_GUEST_ACTION_LOG 
(
   START_DATE           TIMESTAMP            not null,
   OPERATION_TYPE       VARCHAR2(40)         not null,
   IDENTIFICATION_TYPE  VARCHAR2(15)         not null,
   IDENTIFICATION_PARAM VARCHAR2(350),
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   IP_ADDRESS           VARCHAR2(15),
   BIRTHDATE            TIMESTAMP            not null,
   ERROR_MESSAGE        CLOB,
   CONFIRMATION_TYPE    VARCHAR2(35),
   TB                   VARCHAR2(4)          not null,
   LOG_UID              VARCHAR2(32)         not null
)
partition by range(START_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
     partition P_START values less than (to_date('1-07-2015', 'DD-MM-YYYY'))
)
go
create index CSA_GUEST_ACTIONLOG_UID on CSA_GUEST_ACTION_LOG (
   PASSPORT ASC,
   START_DATE DESC
)
local
go

-- Номер ревизии: 84021
-- Номер версии: 1.18
-- Комментарий: [Гостевой вход] Создать возможность просмотра журнала в АРМ сотрудника

ALTER TABLE CSA_GUEST_ACTION_LOG
  ADD ( "PHONE_NUMBER" VARCHAR2(20) NOT NULL ) 
go

drop index CSA_GUEST_ACTIONLOG_UID
go

create index CSA_GAL_FIO_DATE_IDX  on CSA_GUEST_ACTION_LOG (
    UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
    START_DATE DESC
)  local
go

create index CSA_GAL_PHONE_IDX  on CSA_GUEST_ACTION_LOG (
    PHONE_NUMBER,
    START_DATE DESC
)  local
go

-- Номер ревизии: 84021
-- Номер версии: 1.18
-- Комментарий: [Гостевой вход] Создать возможность просмотра журнала в АРМ сотрудника

ALTER TABLE "CSA_GUEST_ACTION_LOG" MODIFY ( "FIRST_NAME" VARCHAR2(100) NULL )
GO
ALTER TABLE "CSA_GUEST_ACTION_LOG" MODIFY ( "SUR_NAME" VARCHAR2(100) NULL )
GO
ALTER TABLE "CSA_GUEST_ACTION_LOG" MODIFY ( "PASSPORT" VARCHAR2(100) NULL )
GO
ALTER TABLE "CSA_GUEST_ACTION_LOG" MODIFY ( "BIRTHDATE" TIMESTAMP(6) NULL )
GO
ALTER TABLE "CSA_GUEST_ACTION_LOG" MODIFY ( "TB" VARCHAR2(4) NULL )
GO
ALTER TABLE "CSA_GUEST_ACTION_LOG" MODIFY ( "PHONE_NUMBER" VARCHAR2(20) NULL )
GO


-- Номер ревизии: 83587
-- Номер версии: 1.18
-- Комментарий: MDM

drop index I_MDM_PROFILE_ID
go

alter table MDM_PROFILES
   drop unique (MDM_ID) cascade
go

alter table MDM_PROFILES
   add constraint AK_MDM_PROFILE_ID unique (MDM_ID)
      using index (create unique index I_MDM_PROFILE_ID on MDM_PROFILES (
   MDM_ID ASC
))
go

alter table MDM_PROFILE_INNER_IDS
   add constraint FK_MDM_IDS_TO_PROFILES foreign key (MDM_ID)
      references MDM_PROFILES (MDM_ID)
      on delete cascade
go


