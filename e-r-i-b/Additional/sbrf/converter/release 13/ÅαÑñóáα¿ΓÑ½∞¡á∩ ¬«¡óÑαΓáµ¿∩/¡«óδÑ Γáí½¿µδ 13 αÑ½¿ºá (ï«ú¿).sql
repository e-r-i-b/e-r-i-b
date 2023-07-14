--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

-- Номер ревизии: 57412 
-- Комментарий: Добавление нового дизайна самостоятельной регистрации
create table REGISTRATION_FAILURE_REASONS 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   ADDITION_DATE        TIMESTAMP            not null,
   REASON               VARCHAR(250)         not null,
   constraint PK_REGISTRATION_FAILURE_REASON primary key (ID) using index (
      create unique index PK_REGISTRATION_FAILURE_REASON on REGISTRATION_FAILURE_REASONS(ID) tablespace ERIBLOGINDEXES  
   )  
) tablespace ERIBLOG 
/

create sequence S_REGISTRATION_FAILURE_REASONS
/

create table OFFER_NOTIFICATIONS_LOG 
(
   ID                   INTEGER              not null,
   NOTIFICATION_ID      INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   DISPLAY_DATE         TIMESTAMP            not null,
   TYPE                 VARCHAR2(12)         not null,
   NAME                 VARCHAR2(20)         not null
)
partition by range (DISPLAY_DATE)  interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition    P_START     values less than (to_date('01-01-2014','DD-MM-YYYY')))
/

create sequence S_OFFER_NOTIFICATIONS_LOG
/

create index I_OFFER_NOTIF_LOG on OFFER_NOTIFICATIONS_LOG (
   TYPE ASC,
   DISPLAY_DATE ASC,
   ID ASC
)
local
/
