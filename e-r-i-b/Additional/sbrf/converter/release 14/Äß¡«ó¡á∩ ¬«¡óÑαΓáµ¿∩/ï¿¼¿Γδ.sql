-- Номер ревизии: 61772
-- Номер версии: 1.18
-- Комментарий: Создать сущности для хранения данных профиля клиента. 
create table PROFILE_INFORMATION(
    PROFILE_ID integer not null,
    INFORMATION_TYPE varchar2(128) not null,
    DATA clob,
    constraint PK_PROFILE_INFORMATION primary key(PROFILE_ID, INFORMATION_TYPE)   
)
/
alter table PROFILE_INFORMATION
   add constraint FK_PROFILE_INF_TO_PROFILES foreign key (PROFILE_ID)
      references PROFILES (ID)
      on delete cascade
/

-- Номер ревизии: 62233
-- Номер версии: 1.18
-- Комментарий: Индекс по ФИО ДУЛ ДР ТБ
create unique index INDEX_PROFILES_UID on PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT,' ','') ASC
)
/
