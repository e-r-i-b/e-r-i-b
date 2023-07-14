-- Номер ревизии: 59325
-- Номер версии: 1.18
-- Комментарий: ENH067289: Добавить информацию в CODLOG.
alter table CODLOG add THREAD_INFO INTEGER
/
alter table CSA_CODLOG add THREAD_INFO INTEGER
/
alter table USERLOG add THREAD_INFO INTEGER
/

-- Номер ревизии: 60483
-- Номер версии: 1.18
-- Комментарий:  BUG070976: Не отображается баннер по предодобренной карте\кредиту 
alter table OFFER_NOTIFICATIONS_LOG modify NAME varchar2(40)
/


-- Номер ревизии: 62031
-- Номер версии: 1.18
-- Комментарий: CHG072320: Секционировать ADVERTISINGS_LOG 
alter table ADVERTISINGS_LOG rename to ADVERTISINGS_LOG_O
/
alter table ADVERTISINGS_LOG drop constraint PK_ADVERTISINGS_LOG
/
alter index I_ADVERTISING_LOG rename to I_ADVERTISING_LOG_O
/
alter index DXFK_ADVERTISINGS_LOG rename to DXFK_ADVERTISINGS_LOG_O
/

create table ADVERTISINGS_LOG 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   TYPE                 VARCHAR2(20)         not null
)
partition by range (START_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_START values less than (to_date('01-06-2014','DD-MM-YYYY'))
)
/
create index I_ADVERTISING_LOG on ADVERTISINGS_LOG (
   START_DATE ASC
) local
/
create index DXFK_ADVERTISINGS_LOG on ADVERTISINGS_LOG (
   ADVERTISING_ID ASC
) local
/