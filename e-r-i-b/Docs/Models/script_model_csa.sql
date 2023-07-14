-- Номер ревизии: 40510
-- Номер версии: 1.18
-- Комментарий: Просмотр списка новостей

create sequence S_NEWS;

create table NEWS  (
   ID                   INTEGER                         not null,
   NEWS_DATE            TIMESTAMP                       not null,
   TITLE                VARCHAR2(100)                   not null,
   TEXT                 CLOB                            not null,
   IMPORTANT            VARCHAR2(10)                    not null,
   SHORT_TEXT           VARCHAR2(150)                   not null,
   STATE                VARCHAR2(15),
   TYPE                 VARCHAR2(25),
   AUTOMATIC_PUBLISH_DATE TIMESTAMP,
   CANCEL_PUBLISH_DATE  TIMESTAMP,
   constraint PK_NEWS primary key (ID)
);

-- Номер ревизии: 40560
-- Номер версии: 1.18
-- Комментарий: Ограничение входа ЦСА

create table BLOCKINGRULES  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR(100)                    not null,
   DEPARTMENTS          VARCHAR(100)                    not null,
   STATE                VARCHAR(9)                      not null,
   MESSAGE              VARCHAR(200)                    not null,
   constraint PK_BLOCKINGRULES primary key (ID)
);

create sequence S_BLOCKINGRULES
;

-- Номер ревизии: 42057
-- Номер версии: 1.18
-- Комментарий: ЦСА. пронос изменений из 9.0(Модель БД)

create table CSA_CONNECTORS  (
   ID                   INTEGER                         not null,
   GUID                 VARCHAR2(32)                    not null,
   CREATION_DATE        TIMESTAMP                       not null,
   STATE                VARCHAR2(40)                    not null,
   TYPE                 VARCHAR2(40)                    not null,
   USER_ID              VARCHAR2(10)                    not null,
   LOGIN                VARCHAR2(32),
   CB_CODE              VARCHAR2(20)                    not null,
   CARD_NUMBER          VARCHAR2(20)                    not null,
   BLOCK_REASON         VARCHAR2(256),
   BLOCKED_UNTIL        TIMESTAMP,
   AUTH_ERRORS          INTEGER                         not null,
   PROFILE_ID           INTEGER                         not null,
   DEVICE_INFO          VARCHAR2(100),
   DEVICE_STATE         VARCHAR2(100),
   LAST_SESSION_ID      VARCHAR2(32),
   LAST_SESSION_DATE    TIMESTAMP,
   constraint PK_CSA_CONNECTORS primary key (ID)
)

go

create sequence S_CSA_CONNECTORS
go

create unique index CSA_CONNECTORS_U_GUID on CSA_CONNECTORS (
   GUID ASC
)
go

/*==============================================================*/
/* Index: CSA_CONNECTORS_U_LOGIN                                */
/*==============================================================*/
create unique index CSA_CONNECTORS_U_LOGIN on CSA_CONNECTORS (
   LOGIN ASC
)
go

/*==============================================================*/
/* Index: CSA_CONNECTORS_LTS                                    */
/*==============================================================*/
create index CSA_CONNECTORS_LTS on CSA_CONNECTORS (
   LOGIN ASC,
   TYPE ASC,
   STATE ASC
)
go

/*==============================================================*/
/* Index: CSA_CONNECTORS_UTS                                    */
/*==============================================================*/
create index CSA_CONNECTORS_UTS on CSA_CONNECTORS (
   USER_ID ASC,
   TYPE ASC,
   STATE ASC
)
go

/*==============================================================*/
/* Index: CSA_CONNECTORS_PTS                                    */
/*==============================================================*/
create index CSA_CONNECTORS_PTS on CSA_CONNECTORS (
   PROFILE_ID ASC,
   TYPE ASC,
   STATE ASC
)
go

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
   USER_ID              VARCHAR2(10)                    not null,
   TYPE                 VARCHAR2(40)                    not null,
   STATE                VARCHAR2(40)                    not null,
   CREATION_DATE        TIMESTAMP                       not null,
   PROFILE_ID           INTEGER                         not null,
   PARAMS               CLOB,
   CONFIRM_CODE_HASH    VARCHAR2(40),
   CONFIRM_CODE_SALT    VARCHAR2(32),
   CONFIRM_CODE_CREATION_DATE TIMESTAMP,
   CONFIRM_ERRORS       INTEGER,
   INFO                 CLOB,
   IP_ADDRESS           VARCHAR2(15)                    not null,
   constraint PK_CSA_OPERATIONS primary key (OUID)
)
  partition by range
 ( CREATION_DATE )
 ( partition
         P_MAXVALUE
        values less than ( maxvalue ) )

go

create sequence S_CSA_OPERATIONS
go

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
go

/*==============================================================*/
/* Table: CSA_PASSWORDS                                         */
/*==============================================================*/
create table CSA_PASSWORDS  (
   ID                   INTEGER                         not null,
   VALUE                VARCHAR2(40)                    not null,
   SALT                 VARCHAR2(32)                    not null,
   CREATION_DATE        TIMESTAMP                       not null,
   ACTIVE               CHAR                            not null,
   CONNECTOR_ID         INTEGER                         not null,
   constraint PK_CSA_PASSWORDS primary key (ID)
)
  partition by range
 ( CREATION_DATE )
 ( partition
         P_MAXVALUE
        values less than ( maxvalue ) )

go

create sequence S_CSA_PASSWORDS
go

/*==============================================================*/
/* Table: CSA_PROFILES                                          */
/*==============================================================*/
create table CSA_PROFILES  (
   ID                   INTEGER                         not null,
   FIRST_NAME           VARCHAR2(100)                   not null,
   SUR_NAME             VARCHAR2(100)                   not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)                   not null,
   BIRTHDATE            TIMESTAMP                       not null,
   TB                   VARCHAR2(4)                     not null,
   MAPI_PASSWORD_VALUE  VARCHAR2(40),
   MAPI_PASSWORD_SALT   VARCHAR2(32),
   MAPI_PASSWORD_CREATION_DATE TIMESTAMP,
   constraint PK_CSA_PROFILES primary key (ID)
)

go

create sequence S_CSA_PROFILES
go

/*==============================================================*/
/* Table: CSA_SESSIONS                                          */
/*==============================================================*/
create table CSA_SESSIONS  (
   SID                  VARCHAR2(32)                    not null,
   CREATION_DATE        TIMESTAMP                       not null,
   CLOSE_DATE           TIMESTAMP,
   STATE                VARCHAR2(40)                    not null,
   CONNECTOR            VARCHAR2(32)                    not null,
   PREV_SESSION_ID      VARCHAR2(32),
   PREV_SESSION_DATE    TIMESTAMP,
   constraint PK_CSA_SESSIONS primary key (SID)
)
  partition by range
 ( CREATION_DATE )
 ( partition
         P_MAXVALUE
        values less than ( maxvalue ) )

go

create sequence S_CSA_SESSIONS
go

create index "DXFK_CSA_CONN_FK_CSA_CO_CSA_PR" on CSA_CONNECTORS
(
   PROFILE_ID
)
go

alter table CSA_CONNECTORS
   add constraint FK_CSA_CONN_FK_CSA_CO_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go


create index "DXFK_CSA_PASS_FK_CSA_PA_CSA_CO" on CSA_PASSWORDS
(
   CONNECTOR_ID
)
go

alter table CSA_PASSWORDS
   add constraint FK_CSA_PASS_FK_CSA_PA_CSA_CONN foreign key (CONNECTOR_ID)
      references CSA_CONNECTORS (ID)
go

-- Номер ревизии: 42160
-- Номер версии: 1.18
-- Комментарий: Исправление ошибки

alter table BLOCKINGRULES modify(
   NAME                 VARCHAR2(100),                   
   DEPARTMENTS          VARCHAR2(100),                    
   STATE                VARCHAR2(9),
   MESSAGE              VARCHAR2(200)   
);

-- Номер ревизии: 42465
-- Номер версии: 1.18
-- Комментарий: ЦСА. Индексы.

create index CSA_SESSIONS_CSD on CSA_SESSIONS (
   CONNECTOR ASC,
   STATE ASC,
   CREATION_DATE ASC
)
  local
go

create unique index CSA_PROFILES_UNIVERSAL_ID on CSA_PROFILES (
   UPPER(FIRST_NAME) ASC,
   UPPER(SUR_NAME) ASC,
   UPPER(PATR_NAME) ASC,
   UPPER(PASSPORT) ASC,
   BIRTHDATE ASC,
   TB ASC
)
go

-- Номер ревизии:  42595
-- Номер версии: 1.18
-- Комментарий: ЦСА. Работа с коннекторами в клиенте и анкете клиента. Хранимка в БД ЦСА.
/*Пакет для работы с CSA*/
create or replace package CSA_CONNECTORS_PKG is
       /*Получение подключенных мобильных приложений клиента по параметрам. Используется в двух случаях:
       - получение списка подключенных приложений клиента по ФИО, паспорту(серия+номер), дате рождения;
       - получение конкретного подключенного мобильного приложения или списка по ID*/
       procedure GetConnectedMobileDevices(pSurName in varchar2,        /*фамилия клиента*/
                                           pFirstName in varchar2,      /*имя*/
                                           pPatrName in varchar2,       /*отчество*/
                                           pPassport in varchar2,       /*серия и номер паспорта*/
                                           pBirthdate in timestamp,     /*дата рождения*/
                                           pTB in varchar2,             /*номер тербанка*/
                                           pAppId in varchar2,          /*ID подключенного приложения*/
                                           pOutCursor out sys_refcursor /*подключенные приложения*/);
end CSA_CONNECTORS_PKG;
/
create or replace package body CSA_CONNECTORS_PKG is
   /*Получение подключенных мобильных приложений клиента по параметрам. Используется в двух случаях:
   - получение списка подключенных приложений клиента по ФИО, паспорту(серия+номер), дате рождения;
   - получение конкретного подключенного мобильного приложения или списка по ID*/
   procedure GetConnectedMobileDevices(pSurName in varchar2,        /*фамилия клиента*/
                                       pFirstName in varchar2,      /*имя*/
                                       pPatrName in varchar2,       /*отчество*/
                                       pPassport in varchar2,       /*серия и номер паспорта*/
                                       pBirthdate in timestamp,     /*дата рождения*/
                                       pTB in varchar2,             /*номер тербанка*/
                                       pAppId in varchar2,          /*ID подключенного приложения*/
                                       pOutCursor out sys_refcursor /*подключенные приложения*/) is   
   begin
     if pAppId is not null then /*необходимо получить приложения по списку ID*/
       open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) = 'ACTIVE'/*активный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
     else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения*/
       /*Контроль входных данных*/
       if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
       end if;
       
       open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
            from csa_connectors con, csa_profiles pr
           where upper(pr.sur_name) = upper(pSurName)
             and upper(pr.first_name) = upper(pFirstName)
             and (upper(pr.patr_name) = upper(pPatrName) or coalesce(pr.patr_name,pPatrName) is null)
             and pr.passport = pPassport
             and pr.birthdate = pBirthdate
             and lpad(pr.tb,4,'0') = lpad(pTB,4,'0')
             and con.profile_id = pr.id
             and upper(con.state) = 'ACTIVE'/*активный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
     end if;  
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии:  42737
-- Номер версии: 1.18
-- Комментарий: BUG046561: При поиске мобильных подключений сравнивать ТБ аналогично входу клиента.
/*==============================================================*/
/* Database package: CSA_CONNECTORS_PKG                         */
/*==============================================================*/
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) = 'ACTIVE'/*активный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
            from csa_connectors con, csa_profiles pr
           where upper(pr.sur_name) = upper(pSurName)
             and upper(pr.first_name) = upper(pFirstName)
             and (upper(pr.patr_name) = upper(pPatrName) or coalesce(pr.patr_name,pPatrName) is null)
             and pr.passport = pPassport
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
                                      CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) = 'ACTIVE'/*активный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/;
      end if;
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии:  42789
-- Номер версии: 1.18
-- Комментарий: ENH046604: Отображать у сотрудника и клиента временно заблокированные мобильные приложений.
/*==============================================================*/
/* Database package: CSA_CONNECTORS_PKG                         */
/*==============================================================*/
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
            from csa_connectors con, csa_profiles pr
           where upper(pr.sur_name) = upper(pSurName)
             and upper(pr.first_name) = upper(pFirstName)
             and (upper(pr.patr_name) = upper(pPatrName) or coalesce(pr.patr_name,pPatrName) is null)
             and pr.passport = pPassport
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/;
      end if;
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии: 43125 
-- Номер версии: 1.18
-- Комментарий: Логирование в ЦСА + журнал сообщений ЦСА + журнал системных действий ЦСА

/*==============================================================*/
/* Table: CSA_CODLOG                                            */
/*==============================================================*/
create table CSA_CODLOG  (
   ID                   INTEGER                         not null,
   START_DATE           TIMESTAMP                       not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)                    not null,
   MESSAGE_TYPE         VARCHAR2(80)                    not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)                    not null,
   MESSAGE_DEMAND       CLOB                            not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   DIRECTION            VARCHAR2(10)                    not null,
   LINK                 VARCHAR2(32),
   SYST                 VARCHAR2(10)                    not null,
   MESSAGE_ANSWER_TYPE  VARCHAR2(80),
   LOGIN_ID             INTEGER,
   DEPARTMENT_ID        NUMBER(22,0),
   SESSION_ID           VARCHAR2(64),
   PERSON_ID            NUMBER(22,0),
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   OPERATION_UID        VARCHAR2(32),
   LOGIN                VARCHAR2(32),
   DEPARTMENT_CODE      VARCHAR2(4),
   constraint PK_CSA_CODLOG primary key (ID)
)
  partition by range
 ( START_DATE )
 ( partition
         P_MAXVALUE
        values less than ( maxvalue ) )
;

create sequence S_CSA_CODLOG;

create index CSA_CL_APP_DATE_INDEX on CSA_CODLOG (
   APPLICATION ASC,
   START_DATE DESC
)
  local
;

create index CSA_CL_APP_SYST_DATE_INDEX on CSA_CODLOG (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
)
  local
;

create index CSA_CL_OUID_INDEX on CSA_CODLOG (
   OPERATION_UID ASC
)
;


create index CSA_CL_MESSAGE_DEMAND_ID_INDEX on CSA_CODLOG (
   MESSAGE_DEMAND_ID ASC
)
;


create index CSA_CL_DI_DATE_INDEX on CSA_CODLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
  Local;

create index CSA_CL_LOGIN_DATE_INDEX on CSA_CODLOG (
   START_DATE DESC,
   LOGIN ASC
);

/*==============================================================*/
/* Table: CSA_MESSAGE_TRANSLATE                                 */
/*==============================================================*/
create table CSA_MESSAGE_TRANSLATE  (
   ID                   INTEGER                         not null,
   CODE                 VARCHAR2(255)                   not null,
   TRANSLATE            VARCHAR2(255)                   not null,
   TYPE                 CHAR(1)                         not null,
   constraint PK_CSA_MESSAGE_TRANSLATE primary key (ID),
   constraint AK_CODE_CSA_MESS unique (CODE)
);
create sequence S_CSA_MESSAGE_TRANSLATE;

/*==============================================================*/
/* Table: CSA_SYSTEMLOG                                         */
/*==============================================================*/
create table CSA_SYSTEMLOG  (
   ID                   INTEGER                         not null,
   MSG_LEVEL            CHAR(1)                         not null,
   START_DATE           TIMESTAMP                       not null,
   LOGIN_ID             INTEGER                         not null,
   APPLICATION          VARCHAR2(20)                    not null,
   MESSAGE              CLOB,
   SESSION_ID           VARCHAR2(64),
   IP_ADDRESS           VARCHAR2(15),
   MESSAGE_SOURCE       VARCHAR2(16)                    not null,
   DEPARTMENT_ID        INTEGER,
   PERSON_ID            NUMBER(22,0),
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   THREAD_INFO          INTEGER,
   LOGIN                VARCHAR2(32),
   DEPARTMENT_CODE      VARCHAR2(4),
   constraint PK_CSA_SYSTEMLOG primary key (ID)
)
  partition by range
 ( START_DATE )
 ( partition
         P_MAXVALUE
        values less than ( maxvalue ) )

;

create sequence S_CSA_SYSTEMLOG;

create index CSA_SL_APP_DATE_INDEX on CSA_SYSTEMLOG (
   APPLICATION ASC,
   START_DATE DESC
)
  local
;

create index CSA_SL_DI_DATE_INDEX on CSA_SYSTEMLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
  local
;

create index CSA_SL_IP_DATE_INDEX on CSA_SYSTEMLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
  local
;

create index CSA_SL_LOGIN_DATE_INDEX on CSA_SYSTEMLOG (
   START_DATE DESC,
   LOGIN ASC
)
;


-- Номер ревизии:  43567
-- Номер версии: 1.18
-- Комментарий:  CHG046532 Вынос настроек CSABackApp в БД 

/*==============================================================*/
/* Table: PROPERTIES                                            */
/*==============================================================*/
create table PROPERTIES  (
   ID                   INTEGER                         not null,
   PROPERTY_KEY         VARCHAR2(256)                   not null,
   PROPERTY_VALUE       VARCHAR2(500),
   CATEGORY             VARCHAR2(80),
   constraint PK_PROPERTIES primary key (ID)
)

go

create sequence S_PROPERTIES
go

-- Номер ревизии:  43567
-- Номер версии: 1.18
-- Комментарий:  CHG046532 Вынос настроек CSABackApp в БД(Скрипты для добавляения настроек из csa-back.properties и log.properties в таблицу PROPERTIES, если настройка не будет добавлена, она будут браться из файлов properties)

-- log.propertiers

insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.systemLog.level.Core', '4', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.systemLog.level.Gate', '4', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.systemLog.level.Scheduler', '4', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.systemLog.level.Cache', '4', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.systemLog.level.Web', '4', 'csa-back.log.properties');

insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.writers.SystemLogWriter', 'com.rssl.phizic.logging.system.DatabaseSystemLogWriter', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName', 'CSA2Log', 'csa-back.log.properties');

insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.writers.MessageLogWriter', 'com.rssl.phizic.logging.system.DatabaseSystemLogWriter', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName', 'CSA2Log', 'csa-back.log.properties');

insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.messagesLog.level.jdbc', 'on', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.messagesLog.level.iPas', 'on', 'csa-back.log.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.phizic.logging.messagesLog.level.mobile', 'on', 'csa-back.log.properties');


-- csa-back.properties

insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.integration.ipas.url', 'http://localhost:8888/CSA/services/IPASWSSoap_Impl', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.common.confirmation.timeout', '600', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.common.confirmation.code.length', '5', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.common.confirmation.code.allowed-chars', '0123456789', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.common.session.timeout', '24', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.registration.user.deny-multiple', 'true', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.registration.timeout', '1800', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.authentication.blocking.timeout', '1800', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.authentication.failed.limit', '3', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.password-restoration.timeout', '1800', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.mobile.registration.timeout', '1800', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.mobile.registration.max.connectors', '5', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.mobile.registration.request.limit', '10', 'csa-back.properties');
insert into PROPERTIES(ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.NEXTVAL, 'com.rssl.auth.csa.back.config.mobile.registration.request.limit.check.interval', '600', 'csa-back.properties');

-- Номер ревизии: 43939 
-- Номер версии: 1.18
-- Комментарий:  Индексы для логирвоания ЦСА


create index CSA_CL_FIO_INDEX on CSA_CODLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC
)
go

create index CSA_OPERATIONS_USERID_INDEX on CSA_OPERATIONS (
   USER_ID ASC,
   CREATION_DATE DESC
)
go

create index CSA_OPERATIONS_TYPE on CSA_OPERATIONS (
   TYPE ASC,
   CREATION_DATE DESC
)
go

create index CSA_OPERATIONS_STATE on CSA_OPERATIONS (
   STATE ASC,
   CREATION_DATE DESC
)
go

create index CSA_PROFILES_DUL_BIRTH_INDEX on CSA_PROFILES (
   PASSPORT ASC,
   BIRTHDATE DESC
)
go

create index CSA_PROFILES_TB_INDEX on CSA_PROFILES (
   TB ASC
)
go

create index CSA_PROFILES_FIO_INDEX on CSA_PROFILES (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC
)
go

create index CSA_SL_FIO_INDEX on CSA_SYSTEMLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC
)
go

-- Номер ревизии: 43974 
-- Номер версии: 1.18
-- Комментарий:  Индексы для логирвоания ЦСА(индексы для секцонированых таблиц+удаление не селективных)

drop index CSA_CL_LOGIN_DATE_INDEX;
create index CSA_CL_LOGIN_DATE_INDEX on CSA_CODLOG (
   LOGIN ASC,
   START_DATE DESC
)
  local
;

drop index CSA_CL_FIO_INDEX;
create index CSA_CL_FIO_INDEX on CSA_CODLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
  local
;

drop index CSA_OPERATIONS_USERID_INDEX;
create index CSA_OPERATIONS_USERID_INDEX on CSA_OPERATIONS (
   USER_ID ASC,
   CREATION_DATE DESC
)
  local
;

drop index CSA_OPERATIONS_TYPE;
drop index CSA_OPERATIONS_STATE;
drop index CSA_PROFILES_TB_INDEX;

drop index CSA_SL_LOGIN_DATE_INDEX; 
create index CSA_SL_LOGIN_DATE_INDEX on CSA_SYSTEMLOG (
   LOGIN ASC,
   START_DATE DESC
)
  local
;

drop index CSA_SL_FIO_INDEX;
create index CSA_SL_FIO_INDEX on CSA_SYSTEMLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
  local
;

-- Номер ревизии:  44396
-- Номер версии: 10.0
-- Комментарий:  ЦСА.Актуализация индексов для 10 релиза.

drop index CSA_CONNECTORS_U_LOGIN 
go

create unique index CSA_CONNECTORS_U_LOGIN on CSA_CONNECTORS (
   UPPER(LOGIN) ASC
)
go

create index CSA_CONNECTORS_CTS on CSA_CONNECTORS (
   CARD_NUMBER ASC,
   TYPE ASC,
   STATE ASC
)
go

-- Номер ревизии:  44412
-- Номер версии: 10.0
-- Комментарий:  Ограничение входа клиентов

alter table BLOCKINGRULES add (RESUMING_TIME timestamp not null);

alter table BLOCKINGRULES modify(MESSAGE varchar2(1024)); 


-- Номер ревизии:  44578
-- Номер версии: 10.0
-- Комментарий:  Динамическое заполнение текстовок для вкладок на главной странице

create table CSA_TABS  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(32),
   TEXT                 VARCHAR2(500),
   constraint PK_CSA_TABS primary key (ID)
);

create sequence S_CSA_TABS;


-- Номер ревизии:  44593
-- Номер версии: 10.0
-- Комментарий:  ЦСА работа с паролями логинами ч6. история паролей.
create index CSA_PASSWORDS_CC on CSA_PASSWORDS (
   CONNECTOR_ID ASC,
   CREATION_DATE ASC
)
  local
;
-- Номер ревизии:  44910
-- Номер версии: 10.0
-- Комментарий:  ЦСА Промоутеры.
create table CSA_PROMOCLIENT_LOG  (
   ID                   INTEGER                         not null,
   PROMO_SESSION_ID     INTEGER                         not null,
   PROFILE_ID           INTEGER                         not null,
   CREATION_DATE        TIMESTAMP                       not null,
   BEFORE_CREATION_DATE TIMESTAMP,
   constraint PK_CSA_PROMOCLIENT_LOG primary key (ID)
);

create sequence S_CSA_PROMOCLIENT_LOG;
comment on table CSA_PROMOCLIENT_LOG is 'Таблица регистрации входов клиентов, привлеченных промоутерами';
comment on column CSA_PROMOCLIENT_LOG.ID is 'ID записи';
comment on column CSA_PROMOCLIENT_LOG.PROMO_SESSION_ID is 'ID смены промоутера';
comment on column CSA_PROMOCLIENT_LOG.PROFILE_ID is 'ID профиля клиента';
comment on column CSA_PROMOCLIENT_LOG.CREATION_DATE is 'Дата входа клиента';
comment on column CSA_PROMOCLIENT_LOG.BEFORE_CREATION_DATE is 'Дата последнего входа перед данным';

create table CSA_PROMOTER_SESSIONS  (
   SESSION_ID           INTEGER                         not null,
   CREATION_DATE        TIMESTAMP                       not null,
   CLOSE_DATE           TIMESTAMP,
   CHANNEL              VARCHAR2(10)                    not null,
   TB                   VARCHAR2(4)                     not null,
   OSB                  VARCHAR2(4)                     not null,
   OFFICE               VARCHAR(7),
   PROMOTER             VARCHAR2(100)                   not null,
   constraint PK_CSA_PROMOTER_SESSIONS primary key (SESSION_ID)
);

create sequence S_CSA_PROMOTER_SESSIONS;
comment on table CSA_PROMOTER_SESSIONS is 'Таблица хранит историю открытия/закрытия смен промоутеров';
comment on column CSA_PROMOTER_SESSIONS.SESSION_ID is 'Идентификатор смены промотера';
comment on column CSA_PROMOTER_SESSIONS.CREATION_DATE is 'Время начала смены';
comment on column CSA_PROMOTER_SESSIONS.CLOSE_DATE is 'Время окончания смены';
comment on column CSA_PROMOTER_SESSIONS.CHANNEL is 'Канал';
comment on column CSA_PROMOTER_SESSIONS.TB is 'Террбанк';
comment on column CSA_PROMOTER_SESSIONS.OSB is 'ОСБ';
comment on column CSA_PROMOTER_SESSIONS.OFFICE is 'ВСП';
comment on column CSA_PROMOTER_SESSIONS.PROMOTER is 'Идентификатор промоутера';
create index CSA_PROMOTER_SESSIONS_INDEX on CSA_PROMOTER_SESSIONS (TB ASC, OSB ASC, OFFICE ASC);

-- Номер ревизии:  44977
-- Номер версии: 10.0
-- Комментарий:  ЦСА Промоутеры. Отчет об активности промоутеров.
create index CSA_PROMCLNT_PROM_SESID_INDX on CSA_PROMOCLIENT_LOG (
   PROMO_SESSION_ID ASC
);
create index CSA_PROMO_SES_CREATE_DATE_INDX on CSA_PROMOTER_SESSIONS (
   CREATION_DATE ASC
);

-- Номер ревизии:  45198
-- Номер версии: 10.0
-- Комментарий:  Реализация хранимой процедуры в ЦСА для получения алиасов
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
            from csa_connectors con, csa_profiles pr
           where upper(pr.sur_name) = upper(pSurName)
             and upper(pr.first_name) = upper(pFirstName)
             and (upper(pr.patr_name) = upper(pPatrName) or coalesce(pr.patr_name,pPatrName) is null)
             and pr.passport = pPassport
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/;
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии:  45332
-- Номер версии: 10.0
-- Комментарий:  ЦСА. Запрет регистрации доп карт
ALTER TABLE CSA_OPERATIONS MODIFY ( "USER_ID" VARCHAR2(10) NULL );




-- Номер ревизии:  45574
-- Номер версии: 10.0
-- Комментарий:  CHG040738: Вынести параметры из конфигурационных файлов в АРМ.  Уникальный индекс к полям PROPERTY_KEY и CATEGORY таблицы PROPERTIES
create unique index PROPERTIES_UNIQ on PROPERTIES (
   PROPERTY_KEY ASC,
   CATEGORY ASC
)
go

-- Номер ревизии:  45582
-- Номер версии: 10.0
-- Комментарий:  CHG050203: Фиксировать вход через машину промоутера на стороне CSAFront 
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
            from csa_connectors con, csa_profiles pr
           where upper(pr.sur_name) = upper(pSurName)
             and upper(pr.first_name) = upper(pFirstName)
             and (upper(pr.patr_name) = upper(pPatrName) or coalesce(pr.patr_name,pPatrName) is null)
             and pr.passport = pPassport
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/;
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
      if null in (pConnectorGUID, pPromoterSessionId) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове SetPromoClientSessionLog');
      end if;
   
      select max(con.profile_id), max(con.last_session_date)
        into l_Profile_id, l_before_creation_date
        from csa_connectors con
       where con.guid = pConnectorGUID;
   
      if l_Profile_id is null then
        RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
      end if;
          
      insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
           values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
      returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии:  45648
-- Номер версии: 10.0
-- Комментарий:  BUG047685: ЦСА - не учитывать пробелы в документе клиента при поиске устройств
drop index CSA_PROFILES_UNIVERSAL_ID;

create unique index CSA_PROFILES_UNIVERSAL_ID on CSA_PROFILES (
   UPPER(FIRST_NAME) ASC,
   UPPER(SUR_NAME) ASC,
   UPPER(PATR_NAME) ASC,
   BIRTHDATE ASC,
   TB ASC,
   UPPER(PASSPORT) ASC
);

/*==============================================================*/
/* Database package: CSA_CONNECTORS_PKG                         */
/*==============================================================*/
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
/

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
            from csa_connectors con, csa_profiles pr
           where upper(pr.sur_name) = upper(pSurName)
             and upper(pr.first_name) = upper(pFirstName)
             and (upper(pr.patr_name) = upper(pPatrName) or coalesce(pr.patr_name,pPatrName) is null)
             and replace(pr.passport,'') = replace(pPassport,'')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   								   CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/;
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
      if null in (pConnectorGUID, pPromoterSessionId) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове SetPromoClientSessionLog');
      end if;
   
      select max(con.profile_id), max(con.last_session_date)
        into l_Profile_id, l_before_creation_date
        from csa_connectors con
       where con.guid = pConnectorGUID;
   
      if l_Profile_id is null then
        RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
      end if;
          
      insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
           values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
      returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии:  46049
-- Номер версии: 10.0
-- Комментарий:  BUG051014 Невозможно сменить пароль клиента по идентификатору через АРМ Сотрудника(ч1 - модель БД)

drop index CSA_PROFILES_UNIVERSAL_ID
go

create unique index CSA_PROFILES_UNIVERSAL_ID on CSA_PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT,' ','')  ASC
)
go

drop package CSA_CONNECTORS_PKG
go

/*==============================================================*/
/* Database package: CSA_CONNECTORS_PKG                         */
/*==============================================================*/
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))          and replace(pr.passport,'') = replace(pPassport,'')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрацияя мобильного устройства*/;
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
      if null in (pConnectorGUID, pPromoterSessionId) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове SetPromoClientSessionLog');
      end if;
   
      select max(con.profile_id), max(con.last_session_date)
        into l_Profile_id, l_before_creation_date
        from csa_connectors con
       where con.guid = pConnectorGUID;
   
      if l_Profile_id is null then
        RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
      end if;
          
      insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
           values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
      returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии:  46082
-- Номер версии: 1.18
-- Комментарий:  Журнал собщений ЦСА(поля журнала действий)

alter table CSA_CODLOG add (PROMOTER_ID varchar2(100) null);
alter table CSA_CODLOG add (MGUID varchar2(32) null);
alter table CSA_CODLOG add (IP_ADDRESS varchar2(15) null);
alter table CSA_CODLOG add(ERROR_CODE varchar2(10) null);
alter table CSA_CODLOG drop column PERSON_ID;
alter table CSA_CODLOG drop column DEPARTMENT_NAME;
alter table CSA_CODLOG drop column DEPARTMENT_ID;
alter table CSA_CODLOG drop column LOGIN_ID;

create index CSA_CL_IP_DATA_INDEX on CSA_CODLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
  local
;

create index CSA_CL_MGUID_DATE_INDEX on CSA_CODLOG (
   MGUID ASC,
   START_DATE DESC
)
  local
;

create index CSA_CL_PROMO_ID_DATE_INDEX on CSA_CODLOG (
   PROMOTER_ID ASC,
   START_DATE DESC
)
  local
;

-- Номер ревизии:  46216
-- Номер версии: 1.18
-- Комментарий:  увеличение размера столдбца TEXT до 1000

alter table CSA_TABS modify (TEXT varchar2(1000));

-- Номер ревизии:  46433
-- Номер версии: 1.18
-- Комментарий:  BUG047685: ЦСА - не учитывать пробелы в документе клиента при поиске устройств 
/*==============================================================*/
/* Database package: CSA_CONNECTORS_PKG                         */
/*==============================================================*/
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in varchar2,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/;
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
      if null in (pConnectorGUID, pPromoterSessionId) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове SetPromoClientSessionLog');
      end if;
   
      select max(con.profile_id), max(con.last_session_date)
        into l_Profile_id, l_before_creation_date
        from csa_connectors con
       where con.guid = pConnectorGUID;
   
      if l_Profile_id is null then
        RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
      end if;
          
      insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
           values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
      returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии:  46447
-- Номер версии: 1.18
-- Комментарий:  BUG051861: Не отображается список подключенных мобильных устройств
/*==============================================================*/
/* Database package: CSA_CONNECTORS_PKG                         */
/*==============================================================*/
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/;
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
      if null in (pConnectorGUID, pPromoterSessionId) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове SetPromoClientSessionLog');
      end if;
   
      select max(con.profile_id), max(con.last_session_date)
        into l_Profile_id, l_before_creation_date
        from csa_connectors con
       where con.guid = pConnectorGUID;
   
      if l_Profile_id is null then
        RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
      end if;
          
      insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
           values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
      returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии:  46625
-- Номер версии: 1.18
-- Комментарий:  BUG049947: Не полностью логируются действия клиента через промоутера. 
/*==============================================================*/
/* Database package: CSA_CONNECTORS_PKG                         */
/*==============================================================*/
create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/;
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
     if null in (nvl(pConnectorGUID, pOperationUID), pPromoterSessionId) then
       RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове SetPromoClientSessionLog');
     end if;
   
     if (pConnectorGUID is not null) then
       select max(con.profile_id), max(con.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_connectors con
        where con.guid = pConnectorGUID;
     else
       select max(op.profile_id), max(c.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_operations op
            , csa_connectors c
        where op.ouid = pOperationUID
          and c.profile_id = op.profile_id
          and c.state = 'ACTIVE';
     end if;
     
     if l_Profile_id is null then
       RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
     end if;
     
     insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
          values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
     returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер версии: 1.18
-- Комментарий:  CHG052430: Русские названия для сообщений журнала ЦСА.

delete from CSA_MESSAGE_TRANSLATE;
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'mb_WWW_GetClientByLogin','Получение информации о пользователе по логину (userId)', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'mb_WWW_GetClientByCardNumber','Получение информации о пользователе по номеру карты', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'mb_WWW_SendPassByCardNumber','Генерация и отправка пароля по карте в iPas', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'mb_ESB_SendSMS','Отправка SMS на номер телефона, зарегистрированный в Мобильном банке', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'mb_IMSI_SendSMS','Отправка сообщения с проверкой IMSI', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'mb_IMSI_GetValidationResult','Выполнение проверки IMSI', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'mb_WWW_GetRegistrations','Получение информации о регистрации клиента в Мобильном банке по номеру карты', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'mb_WWW_GetRegistrations2','Получение информации о регистрации клиента в Мобильном банке по номеру карты 2  (в случае получения пустого результата по запросу mb_WWW_GetRegistrations)', 'R');

insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'SetPromoClientSessionLog','Логирование информации о входе клиента через страницу промоутера', 'R');

insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'ns0:VerifyPasswordRq','Проверка логина и пароля в iРas', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'actualizeLogonInfoRq','Получение актуальной информации о входе клиента (удаление лишних подключений)', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'cancelMobileRegistrationRq','Отказ в регистрации мобильного приложения', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'changeLoginRq','Смена логина клиента', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'changePasswordRq','Смена пароля клиента', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'checkPasswordRq','Проверка пароля клиента', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'checkSessionRq','Проверка валидности сессии (sid идентификатор сессии)', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'closeSessionRq','Закрытие сессии клиента', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'confirmOperationRq','Подтверждение операции', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'createMobileSessionRq','Открытие сессии для мобильного приложения', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'finishCreateSessionRq','Аутентификация пользователя', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'finishMobileRegistrationRq','Завершение регистрации мобильного приложения', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'finishRestorePasswordRq','Окончание восстановления пароля', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'finishUserRegistrationRq','Завершение регистрации пользователя', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'generatePassword2Rq','Генерация пароля для подключений через ЦСА всего профиля', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'generatePasswordRq','Генерация пароля для клиента', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'startCreateSessionRq','Вход пользователя', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'startMobileRegistrationRq','Регистрация мобильного приложения', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'startRestorePasswordRq','Восстановление пароля клиента', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'startUserRegistrationRq','Регистрация клиента в системе', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'validateLoginRq','Проверка логина клиента', 'R');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'validatePasswordRq','Проверка безопасности пароля', 'R');

insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'ns0:VerifyPasswordRs','Проверка логина и пароля в iРas', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'actualizeLogonInfoRs','Получение актуальной информации о входе клиента (удаление лишних подключений)', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'cancelMobileRegistrationRs','Отказ в регистрации мобильного приложения', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'changeLoginRs','Смена логина клиента', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'changePasswordRs','Смена пароля клиента', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'checkPasswordRs','Проверка пароля клиента', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'checkSessionRs','Проверка валидности сессии (sid идентификатор сессии)', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'closeSessionRs','Закрытие сессии клиента', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'confirmOperationRs','Подтверждение операции', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'createMobileSessionRs','Открытие сессии для мобильного приложения', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'finishCreateSessionRs','Аутентификация пользователя', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'finishMobileRegistrationRs','Завершение регистрации мобильного приложения', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'finishRestorePasswordRs','Окончание восстановления пароля', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'finishUserRegistrationRs','Завершение регистрации пользователя', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'generatePassword2Rs','Генерация пароля для подключений через ЦСА всего профиля', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'generatePasswordRs','Генерация пароля для клиента', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'startCreateSessionRs','Вход пользователя', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'startMobileRegistrationRs','Регистрация мобильного приложения', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'startRestorePasswordRs','Восстановление пароля клиента', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'startUserRegistrationRs','Регистрация клиента в системе', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'validateLoginRs','Проверка логина клиента', 'A');
insert into CSA_MESSAGE_TRANSLATE values(S_CSA_MESSAGE_TRANSLATE.nextval, 'validatePasswordRs','Проверка безопасности пароля', 'A');

-- Номер ревизии:  48060
-- Номер версии: 1.18
-- Комментарий: BUG053330: Изменить поиск операции в БД ЦСА 
alter table CSA_OPERATIONS MODIFY CREATION_DATE TIMESTAMP default SYSTIMESTAMP


-- Номер ревизии:  48100
-- Номер версии: 1.18
-- Комментарий: Выбор региона на странице ЦСА(БД) 

create table REGIONS  (
   ID                   INTEGER                         not null,
   CODE                 VARCHAR2(20)                    not null,
   NAME                 NVARCHAR2(128)                  not null,
   CODE_TB              VARCHAR2(2),
   PARENT_ID            INTEGER,
   constraint PK_REGIONS primary key (ID)
)
go

create sequence S_REGIONS
go

create unique index INDEX_CODE on REGIONS(CODE)
go


create table USERS_REGIONS(
   ID                   INTEGER                         not null,
   CODE                 VARCHAR2(20)                    not null,
   COOKIE               VARCHAR2(32)                    not null,
   CSA_USER_ID          VARCHAR2(10),
   LAST_USE_DATE        TIMESTAMP,
   constraint PK_USERS_REGIONS primary key (ID)   
)
go

create sequence S_USERS_REGIONS
go

create unique index INDEX_COOKIE on USERS_REGIONS(COOKIE)
go

create index INDEX_CSA_USER_ID on USERS_REGIONS(CSA_USER_ID)
go


-- Номер ревизии:  48129
-- Номер версии: 1.18
-- Комментарий: BUG053330: Изменить поиск операции в БД ЦСА 
alter table CSA_OPERATIONS MODIFY CREATION_DATE TIMESTAMP default SYSDATE

-- Номер ревизии:  48431
-- Номер версии: 1.18
-- Комментарий: Синхронизация обновлений региона клиента в ЕРИБ-е и ЦСА
alter table USERS_REGIONS modify CODE null;
alter table USERS_REGIONS modify CSA_USER_ID not null;


-- Номер ревизии:  48483
-- Номер версии: 1.18
-- Комментарий: Интеграция с деловой средой
ALTER TABLE CSA_PROFILES ADD PREFERRED_CONFIRM_TYPE VARCHAR2(32);

-- Номер ревизии:  48519
-- Номер версии: 1.18
-- Комментарий: Управление текстами смс в ЕРИБ. CSA. Модель БД.
create table SMS_RESOURCES  (
   ID                   INTEGER                         not null,
   KEY                  VARCHAR2(255)                   not null,
   TEXT                 CLOB                            not null,
   DESCRIPTION          VARCHAR2(255),
   constraint PK_SMS_RESOURCES primary key (ID)
);

create sequence S_SMS_RESOURCES;

create unique index INDEX_SMS_KEY on SMS_RESOURCES (
   KEY ASC
);



-- Номер ревизии:  48529
-- Номер версии: 1.18
-- Комментарий: новости для страницы ЦСА.

create table NEWS_DEPARTMENT  (
   NEWS_ID              INTEGER                         not null,
   DEPARTMENT_ID        INTEGER                         not null,
   TB                   VARCHAR2(2),
   NAME                 VARCHAR2(256),
   constraint PK_NEWS_DEPARTMENT primary key (NEWS_ID, DEPARTMENT_ID)
);

-- Номер ревизии:  48538
-- Номер версии: 1.18
-- Комментарий: Интеграция с деловой средой

alter table CSA_OPERATIONS ADD CONFIRM_TYPE VARCHAR2(32)
go

alter table CSA_OPERATIONS ADD CONFIRM_SID VARCHAR2(32)
go

alter table CSA_OPERATIONS ADD CONFIRM_PASSWORD_NUMBER VARCHAR2(32)
go

alter table CSA_OPERATIONS ADD CONFIRM_RECEIPT_NUMBER VARCHAR2(32)
go

alter table CSA_OPERATIONS ADD CONFIRM_PASSWORD_LEFT INTEGER
go

alter table CSA_OPERATIONS ADD CONFIRM_LAST_ATEMPTS INTEGER
go

-- Номер ревизии: 48615
-- Номер версии: 1.18
-- Комментарий: Управление текстами смс в ЕРИБ. CSA.

delete from SMS_RESOURCES;
alter table SMS_RESOURCES add (SMS_TYPE varchar2(13) not null);

-- Номер ревизии: 48635
-- Номер версии: 1.18
-- Комментарий: Интеграция с деловой средой
alter table CSA_PROFILES drop column PREFERRED_CONFIRM_TYPE
go

-- Номер ревизии: 48682
-- Номер версии: 1.18
-- Комментарий: правила блокировки
alter table BLOCKINGRULES add FROM_PUBLISH_DATE TIMESTAMP
go
alter table BLOCKINGRULES add TO_PUBLISH_DATE TIMESTAMP
go
alter table BLOCKINGRULES add FROM_RESTRICTION_DATE TIMESTAMP
go
alter table BLOCKINGRULES add TO_RESTRICTION_DATE TIMESTAMP
go
create index IND_BLOCKRULE_F_PUBLISH_DATE on BLOCKINGRULES(FROM_PUBLISH_DATE)
go
create index IND_BLOCKRULE_T_PUBLISH_DATE on BLOCKINGRULES(TO_PUBLISH_DATE)
go 

-- Номер ревизии: 49300
-- Номер версии: 1.18
-- Комментарий: Реализация процедуры синхронизации текстов смс в базе ПРОМа и SVN. CSA. Модель БД

alter table sms_resources add LAST_MODIFIED 	timestamp;
alter table sms_resources add PREVIOUS_TEXT 	clob;
alter table sms_resources add EMPLOYEE_LOGIN_ID integer;

-- Номер ревизии: 49471
-- Номер версии: 1.18
-- Комментарий: Удаление неиспользуемых полей из журналов

ALTER TABLE CSA_CODLOG DROP COLUMN MESSAGE_ANSWER_TYPE;
ALTER TABLE CSA_CODLOG DROP COLUMN DIRECTION;
ALTER TABLE CSA_CODLOG DROP COLUMN LINK;
ALTER TABLE CSA_SYSTEMLOG DROP COLUMN PERSON_ID;

-- Номер ревизии: 49471
-- Номер версии: 1.18
-- Комментарий:  ENH055049: Реализовать возможность расширенного логирования по клиенту и уменьшения логируемой информации 

create table LIST_PROPERTIES (
   ID              INTEGER     not null,
   PROPERTY_ID     INTEGER     not null,
   VALUE              VARCHAR2(200),
   constraint PK_LIST_PROPERTIES primary key (ID)
)
go

create sequence S_LIST_PROPERTIES
go

create index "DXFK_LIST_PROPERTIES" on LIST_PROPERTIES
(
   PROPERTY_ID
)
go

alter table LIST_PROPERTIES
   add constraint FK_LIST_PROPERTIES foreign key (PROPERTY_ID)
      references PROPERTIES (ID)
      on delete cascade
go

-- Номер ревизии: 49760
-- Номер версии: 1.18
-- Комментарий:  BUG054814: Нет проверки корректности написания переменных при сохранении шаблона СМС

alter table SMS_RESOURCES     add (VARIABLES VARCHAR2(4000));

-- Номер ревизии: 50127
-- Номер версии: 1.18
-- Комментарий:  Удаление индекса CSA_OPERATIONS_USERID_INDEX

drop index CSA_OPERATIONS_USERID_INDEX;

-- Номер ревизии: 51356
-- Номер версии: 1.18
-- Комментарий:   CHG057619: Разрешить длинные текстовки на закладках страницы входа ЦСА 

alter table CSA_TABS add TEXT_CLOB CLOB
go
update CSA_TABS set TEXT_CLOB = TEXT
go
alter table CSA_TABS drop column TEXT
go
alter table CSA_TABS rename column TEXT_CLOB to TEXT
go

-- Номер ревизии: 51709
-- Номер версии: 1.18_release_11.1
-- Комментарий:  BUG057006: Клиент не привязывается к среднему уровню безопасности  

alter table CSA_CONNECTORS add (REGISTRATION_TYPE varchar2(8))
go
update CSA_CONNECTORS set REGISTRATION_TYPE = 'REMOTE' where (CREATION_DATE < to_date('30.06.2013', 'dd.mm.yyyy') and TYPE like 'CSA') or TYPE like 'TERMINAL'
go
update CSA_CONNECTORS set REGISTRATION_TYPE = 'SELF' where (CREATION_DATE >= to_date('30.06.2013', 'dd.mm.yyyy') and TYPE like 'CSA') or TYPE like 'MAPI'
go
alter table CSA_CONNECTORS modify (REGISTRATION_TYPE not null)
go

-- Номер ревизии: 52125
-- Номер версии: 1.18
-- Комментарий:  Пронос идентификатора устройства в ЦСА, сохранение в коннекторе.

ALTER TABLE CSA_CONNECTORS ADD (DEVICE_ID VARCHAR2(40 BYTE));
CREATE INDEX IDX_CSA_CONNECTORS_DEVICE_ID ON CSA_CONNECTORS (DEVICE_ID);

-- Номер ревизии: 52211
-- Номер версии: 1.18
-- Комментарий:  Добавить в коннектор признак подключения push-уведомлений. .

ALTER TABLE CSA_CONNECTORS
ADD (PUSH_SUPPORTED CHAR(1 BYTE) DEFAULT 0 NOT NULL);

-- Номер ревизии: 52223
-- Номер версии: 1.18
-- Комментарий:  Добавить признаки в id устройства и признак подключения push-уведомлений ClientConnectedMobileApplication

CREATE OR REPLACE PACKAGE BODY CSA_CONNECTORS_PKG is
  /*Получение подключенных мобильных приложений клиента по параметрам. Используется в двух случаях:
   - получение списка подключенных приложений клиента по ФИО, паспорту(серия+номер), дате рождения и тербанку;
   - получение конкретного подключенного мобильного приложения или списка по ID*/
   procedure GetConnectedMobileDevices(pSurName in varchar2,        /*фамилия клиента*/
                                       pFirstName in varchar2,      /*имя*/
                                       pPatrName in varchar2,       /*отчество*/
                                       pPassport in varchar2,       /*серия и номер паспорта*/
                                       pBirthdate in timestamp,     /*дата рождения*/
                                       pTB in varchar2,             /*номер тербанка с синонимами*/
                                       pAppId in varchar2,          /*ID подключенного приложения*/
                                       pOutCursor out sys_refcursor /*подключенные приложения*/) is   
   begin
     if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
                , con.device_id  device_id           /*ID устройства*/
                , con.push_supported  push_supported /*Признак подключения push-уведомления*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/;
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;

        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
               , con.device_id  device_id           /*ID устройства*/
               , con.push_supported  push_supported /*Признак подключения push-уведомления*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
                                            CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрацияя мобильного устройства*/;
      end if;
   end;
   
   /*Получение коннектора клиента по его алиасу*/   
   procedure GetClientConnectorByAlias(pAlias in varchar2,          /*алиас клиента*/
                                       pOutCursor out sys_refcursor /*все данные коннектора*/) is
   begin
     if (pAlias is null) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
     end if;
       
     open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   
   /*Логирование входа клиента с участием промоутера*/
   procedure SetPromoClientSessionLog(pConnectorGUID in varchar2,     /*GUID коннектора клиента*/
                                      pOperationUID in varchar2,      /*OUID операции клиента*/
                                      pPromoterSessionId in varchar2, /*ID активной сессии промоутера*/
                                      pPromoClientLogId out varchar2  /*ID добавленной записи лога*/) is
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
      if null in (nvl(pConnectorGUID, pOperationUID), pPromoterSessionId) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове SetPromoClientSessionLog');
      end if;
   
      if (pConnectorGUID is not null) then
        select max(con.profile_id), max(con.last_session_date)
          into l_Profile_id, l_before_creation_date
          from csa_connectors con
         where con.guid = pConnectorGUID;
      else
        select max(op.profile_id), max(c.last_session_date)
          into l_Profile_id, l_before_creation_date
          from csa_operations op
             , csa_connectors c
         where op.ouid = pOperationUID
           and c.profile_id = op.profile_id
           and c.state = 'ACTIVE';
      end if;
      
      if l_Profile_id is null then
        RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
      end if;
      
      insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
           values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
      returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;
/


-- Номер ревизии: 52300
-- Номер версии: 1.18
-- Комментарий:  ЦСА. История изменений идентификационных данных клиента
create table CSA_PROFILES_HISTORY  (
   ID                   INTEGER                         not null,
   FIRST_NAME           VARCHAR2(100)                   not null,
   SUR_NAME             VARCHAR2(100)                   not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)                   not null,
   BIRTHDATE            TIMESTAMP                       not null,
   TB                   VARCHAR2(4)                     not null,
   EXPIRE_DATE          TIMESTAMP                      default SYSDATE not null,
   PROFILE_ID           INTEGER                         not null,
   constraint PK_CSA_PROFILES_HISTORY primary key (ID)
)

go

create sequence S_CSA_PROFILES_HISTORY
go

/*==============================================================*/
/* Index: CSA_PROFILES_HISTORY_UID                              */
/*==============================================================*/
create index CSA_PROFILES_HISTORY_UID on CSA_PROFILES_HISTORY (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT,' ','') ASC
)
go

create index "DXFK_CSA_PROF_REFERENCE_CSA_PR" on CSA_PROFILES_HISTORY
(
   PROFILE_ID
)
go

alter table CSA_PROFILES_HISTORY
   add constraint FK_CSA_PROF_FK_CSA_PR_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go

-- Номер ревизии: 52479
-- Номер версии: 1.18
-- Комментарий:  ЦСА. Блокировки профиля (БД)
/*==============================================================*/
/* Table: CSA_PROFILES_LOCK                                     */
/*==============================================================*/
create table CSA_PROFILES_LOCK  (
   ID                   INTEGER                         not null,
   PROFILE_ID           INTEGER                         not null,
   REASON               VARCHAR2(1024)                  not null,
   DATE_FROM            TIMESTAMP                       not null,
   DATE_TO              TIMESTAMP,
   LOCKER_FIO           VARCHAR2(100)                   not null,
   CREATION_DATE        TIMESTAMP                      default SYSDATE not null,
   constraint PK_CSA_PROFILES_LOCK primary key (ID)
)

go

create sequence S_CSA_PROFILES_LOCK
go

create index "DXREFERENCE_5" on CSA_PROFILES_LOCK
(
   PROFILE_ID
)
go

alter table CSA_PROFILES_LOCK
   add constraint FK_CSA_PROF_REFERENCE_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go

-- Номер ревизии: 52491
-- Номер версии: 1.18
-- Комментарий:  BUG058731: Ошибка при входе клиента,при объединении профилей в CSA.(констрейн БД)
create index "DXFK_CSA_OPERATION_TO_PROFILE" on CSA_OPERATIONS
(
   PROFILE_ID
)
go

alter table CSA_OPERATIONS
   add constraint FK_CSA_OPER_FK_CSA_OP_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go

-- Номер ревизии: 52506
-- Номер версии: 1.18
-- Комментарий:  перевод моделей бд под 16 ПД.
drop index "DXFK_CSA_OPERATION_TO_PROFILE"
go

-- Номер ревизии: 52547
-- Номер версии: 1.18
-- Комментарий:  Актуализация модели БД ЦСА с промом
drop sequence S_CSA_SESSIONS
go

drop sequence S_NEWS_DEPARTMENT
go

drop sequence S_CSA_OPERATIONS
go

drop table CSA_OPERATIONS
go

create table CSA_OPERATIONS 
(
   OUID                 VARCHAR2(32)         not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   CB_CODE              VARCHAR2(20)         not null,
   USER_ID              VARCHAR2(10),
   TYPE                 VARCHAR2(40)         not null,
   STATE                VARCHAR2(40)         not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   PROFILE_ID           INTEGER              not null,
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
   IP_ADDRESS           VARCHAR2(15)         not null,
   constraint PK_CSA_OPERATIONS primary key (OUID)
)
partition by range (CREATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH')) 
subpartition by hash (PROFILE_ID) subpartitions 16
(
       partition P_2013_07 values less than (timestamp '2013-08-01 00:00:00')
)
go

create index CSA_OPERATIONS_PTSC on CSA_OPERATIONS (
   PROFILE_ID ASC,
   TYPE ASC,
   STATE ASC,
   CREATION_DATE ASC
)
local
go

alter table CSA_OPERATIONS
   add constraint FK_CSA_OPER_FK_CSA_OP_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go

-- Номер ревизии: 52582
-- Номер версии: 1.18
-- Комментарий:  Актуализация модели БД ЦСА с промом
drop sequence S_CSA_PROMOTER_SESSION
go

-- Номер ревизии: 52827
-- Номер версии: 1.18
-- Комментарий:  ЦСА. вход клиента в многоблочной архитектуре (БД)
/*==============================================================*/
/* Table: CSA_NODES                                             */
/*==============================================================*/
create table CSA_NODES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   HOSTNAME             VARCHAR2(100)        not null,
   EXISTING_USERS_ALLOWED CHAR(1)              not null,
   NEW_USERS_ALLOWED    CHAR(1)              not null,
   TEMPORARY_USERS_ALLOWED CHAR(1)              not null,
   USERS_TRANSFER_ALLOWED CHAR(1)              not null,
   constraint PK_CSA_NODES primary key (ID)
)

go

create sequence S_CSA_NODES
go

/*==============================================================*/
/* Table: CSA_PROFILE_NODES                                     */
/*==============================================================*/
create table CSA_PROFILE_NODES 
(
   ID                   INTEGER              not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   STATE                VARCHAR2(20)         not null,
   PROFILE_TYPE         VARCHAR2(20)         not null,
   PROFILE_ID           INTEGER              not null,
   NODE_ID              INTEGER              not null,
   constraint PK_CSA_PROFILE_NODES primary key (ID)
)

go

create sequence S_CSA_PROFILE_NODES
go

create index "DXFK_CSA_PROFILENODES_TO_NODE" on CSA_PROFILE_NODES
(
   NODE_ID
)
go

alter table CSA_PROFILE_NODES
   add constraint FK_CSA_PROFILENODES_TO_NODE foreign key (NODE_ID)
      references CSA_NODES (ID)
go


create index "DXFK_CSA_PROFILENODES_TO_PROFI" on CSA_PROFILE_NODES
(
   PROFILE_ID
)
go

alter table CSA_PROFILE_NODES
   add constraint FK_CSA_PROFILENODES_TO_PROFILE foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go

-- Номер ревизии: 52841
-- Номер версии: 1.18
-- Комментарий: ЦСА. вход клиента в многоблочной архитектуре (ч2. фронтальная часть ЦСА.)
-- вместо localhost:8888 указываем реальное имя хоста, на который следует выполнить редирект после аутентификации в ЦСА.
insert into CSA_NODES values (S_CSA_NODES.nextval, 'основной', 'localhost:8888', 1, 1, 0, 0)
go
-- Номер ревизии: 52855
-- Номер версии: 1.18
-- Комментарий: Актуализация можели БД ЦСА с промом(ч6 row movement)
alter table CSA_OPERATIONS enable row movement
go

-- Номер ревизии: 52978
-- Номер версии: 1.18
-- Комментарий: CHG060024: Отображение списка подключений к МП при выборе уведомлений по средством Push 

create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2, pFlag in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;

go

CREATE OR REPLACE PACKAGE BODY CSA_CONNECTORS_PKG is
   /*Получение подключенных мобильных приложений клиента по параметрам. Используется в двух случаях:
   - получение списка подключенных приложений клиента по ФИО, паспорту(серия+номер), дате рождения и тербанку;
   - получение конкретного подключенного мобильного приложения или списка по ID*/
   procedure GetConnectedMobileDevices(pSurName in varchar2,        /*фамилия клиента*/
                                       pFirstName in varchar2,      /*имя*/
                                       pPatrName in varchar2,       /*отчество*/
                                       pPassport in varchar2,       /*серия и номер паспорта*/
                                       pBirthdate in timestamp,     /*дата рождения*/
                                       pTB in varchar2,             /*номер тербанка с синонимами*/
                                       pAppId in varchar2,          /*ID подключенного приложения*/
                                       pFlag in varchar2,           /*использовать дополнительные параметры поиска*/
                                       pOutCursor out sys_refcursor /*подключенные приложения*/) is   
   begin
     if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
                , con.device_id  device_id           /*ID устройства*/
                , con.push_supported  push_supported /*Признак подключения push-уведомления*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/
              and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;

        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
               , con.device_id  device_id           /*ID устройства*/
               , con.push_supported  push_supported /*Признак подключения push-уведомления*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
                                            CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрацияя мобильного устройства*/
             and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      end if;
   end;
   
   /*Получение коннектора клиента по его алиасу*/   
   procedure GetClientConnectorByAlias(pAlias in varchar2,          /*алиас клиента*/
                                       pOutCursor out sys_refcursor /*все данные коннектора*/) is
   begin
     if (pAlias is null) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
     end if;
       
     open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   
   /*Логирование входа клиента с участием промоутера*/
   procedure SetPromoClientSessionLog(pConnectorGUID in varchar2,     /*GUID коннектора клиента*/
                                      pOperationUID in varchar2,      /*OUID операции клиента*/
                                      pPromoterSessionId in varchar2, /*ID активной сессии промоутера*/
                                      pPromoClientLogId out varchar2  /*ID добавленной записи лога*/) is
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
      if null in (nvl(pConnectorGUID, pOperationUID), pPromoterSessionId) then
        RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове SetPromoClientSessionLog');
      end if;
   
      if (pConnectorGUID is not null) then
        select max(con.profile_id), max(con.last_session_date)
          into l_Profile_id, l_before_creation_date
          from csa_connectors con
         where con.guid = pConnectorGUID;
      else
        select max(op.profile_id), max(c.last_session_date)
          into l_Profile_id, l_before_creation_date
          from csa_operations op
             , csa_connectors c
         where op.ouid = pOperationUID
           and c.profile_id = op.profile_id
           and c.state = 'ACTIVE';
      end if;
      
      if l_Profile_id is null then
        RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
      end if;
      
      insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
           values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
      returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;

-- Номер ревизии: 53465
-- Номер версии: 1.18
-- Комментарий: ЦСА. прихранивание паролей (ч1. модель БД)
/*==============================================================*/
/* Table: CSA_STORED_PASSWORDS                                  */
/*==============================================================*/
create table CSA_STORED_PASSWORDS 
(
   ID                   INTEGER              not null,
   LOGIN                VARCHAR2(10)         not null,
   HASH                 VARCHAR2(64)         not null,
   SALT                 VARCHAR2(32)         not null,
   CHANGED              TIMESTAMP            not null,
   constraint PK_CSA_STORED_PASSWORDS primary key (ID)
)

go

create sequence S_CSA_STORED_PASSWORDS
go

/*==============================================================*/
/* Index: CSA_STORED_PASSWORD_U_LOGIN                           */
/*==============================================================*/
create unique index CSA_STORED_PASSWORD_U_LOGIN on CSA_STORED_PASSWORDS (
   LOGIN ASC
)
go

-- Номер ревизии: 53700
-- Номер версии: 1.18
-- Комментарий: Перенос аутентификации ЕРМБ в ЦСА.


alter table CSA_CONNECTORS add (PHONE_NUMBER varchar2(20))
go
create unique index CONNECTORS_PHONE_INDEX on CSA_CONNECTORS
(
    PHONE_NUMBER
)
go
alter table CSA_CONNECTORS modify (CARD_NUMBER null)
go
alter table CSA_CONNECTORS modify (USER_ID null)
go

-- Номер ревизии: 53807
-- Номер версии: 1.18
-- Комментарий: Перенос аутентификации ЕРМБ в ЦСА. Информация об очередях блоков.

delete from CSA_PROFILE_NODES
go
delete from CSA_NODES
go
alter table CSA_NODES add (SMS_QUEUE_NAME varchar2(64) not null)
go
alter table CSA_NODES add (SMS_FACTORY_NAME varchar2(64) not null)
go
alter table CSA_NODES add (TRANSPORT_QUEUE_NAME varchar2(64) not null)
go
alter table CSA_NODES add (TRANSPORT_FACTORY_NAME varchar2(64) not null)
go
-- вместо localhost:8888 указываем реальное имя хоста, на который следует выполнить редирект после аутентификации в ЦСА.
insert into CSA_NODES values (S_CSA_NODES.nextval, 'основной', 'localhost:8888', 1, 1, 0, 0, 'jms/ermb/sms/SmsQCF', 'jms/ermb/sms/SmsQueue', 'jms/ermb/transport/CheckIMSIRsQCF', 'jms/ermb/transport/CheckIMSIRsQueue')
go

-- Номер ревизии: 53928
-- Номер версии: 1.18
-- Комментарий: ЦСА БЭК. userId удален из операции и контекста идентификации.

alter table CSA_OPERATIONS drop column USER_ID
go

-- Номер ревизии: 54300
-- Номер версии: 1.18
-- Комментарий: Перенос CONNECTOR_INFO в ЦСА

create table CONNECTORS_INFO  (
   ID                   INTEGER                         not null,
   GUID                 VARCHAR2(35)                    not null,
   LOGIN_TYPE           VARCHAR2(10)                    not null,
   constraint PK_CONNECTORS_INFO primary key (ID)
)

go

create sequence S_CONNECTORS_INFO
go

create unique index CONNECTORS_INFO_INDEX on CONNECTORS_INFO (GUID)
go

-- Номер ревизии: 54470
-- Номер версии: 1.18
-- Комментарий: реализация технологических перерывов в разрезе канала

alter table BLOCKINGRULES add (ERIB char)
go
alter table BLOCKINGRULES add (ERIB_MESSAGE varchar2(1024))
go
alter table BLOCKINGRULES add (MAPI char)
go
alter table BLOCKINGRULES add (MAPI_MESSAGE varchar2(1024))
go
alter table BLOCKINGRULES add (ATM char)
go
alter table BLOCKINGRULES add (ATM_MESSAGE varchar2(1024))
go
alter table BLOCKINGRULES add (ERMB char)
go
alter table BLOCKINGRULES add (ERMB_MESSAGE varchar2(1024))
go
update BLOCKINGRULES set ERIB_MESSAGE = MESSAGE 
go
update BLOCKINGRULES set ERIB = 1
go
alter table BLOCKINGRULES drop column MESSAGE 
go

-- Номер ревизии: 54491
-- Номер версии: 1.18
-- Комментарий: BUG060888: mAPI. CSA. Не устанавливается AuthenticationContext

alter table CSA_CONNECTORS add (API_VERSION VARCHAR2(4) null);

-- Номер ревизии: 54563
-- Номер версии: 1.18
-- Комментарий: реализация технологических перерывов в разрезе канала, добавил not null

update BLOCKINGRULES set MAPI = 0 where MAPI is null
go
update BLOCKINGRULES set ERIB = 0 where ERIB is null
go
update BLOCKINGRULES set ATM = 0 where ATM is null
go
update BLOCKINGRULES set ERMB = 0 where ERMB is null
go
alter table BLOCKINGRULES modify (ERIB default 0 not null)
go
alter table BLOCKINGRULES modify (MAPI default 0 not null)
go
alter table BLOCKINGRULES modify (ATM default 0 not null)
go
alter table BLOCKINGRULES modify (ERMB default 0 not null)
go

-- Номер ревизии: 54595
-- Номер версии: 1.18
-- Комментарий: Добавил информацию об очереди служебного канала ЕРМБ. Удалил информацию о транспортном канале.

delete from CSA_PROFILE_NODES
go
delete from CSA_NODES
go
alter table CSA_NODES add (SERVICE_QUEUE_NAME varchar2(64) not null)
go
alter table CSA_NODES add (SERVICE_FACTORY_NAME varchar2(64) not null)
go
alter table CSA_NODES drop column TRANSPORT_QUEUE_NAME
go
alter table CSA_NODES drop column TRANSPORT_FACTORY_NAME
go
insert into CSA_NODES values (S_CSA_NODES.nextval, 'основной', 'localhost:8888', 1, 1, 0, 0, 'jms/ermb/sms/SmsQueue', 'jms/ermb/sms/SmsQCF', 'jms/ermb/auxiliary/ConfirmProfileQueue', 'jms/ermb/auxiliary/ConfirmProfileQCF')
go

-- Номер ревизии: 54635
-- Номер версии: 1.18
-- Комментарий: Сохранение токена безопасности

ALTER TABLE CSA_CONNECTORS
	ADD ( DEVICE_SECURITY_TOKEN VARCHAR2(4000) NULL ) 
GO

-- Номер ревизии: 54712
-- Номер версии: 1.18
-- Комментарий: Настройка приватности в клиентском приложении ЕРИБ

ALTER TABLE CSA_PROFILES ADD (INCOGNITO CHAR(1 BYTE) DEFAULT 0 NOT NULL);

create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
   procedure GetClientIncognito (pConnectorGUID in varchar2,pOutCursor out sys_refcursor);
end CSA_CONNECTORS_PKG;

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
                , con.device_id  device_id           /*ID устройства*/
                , con.push_supported  push_supported /*Признак подключения push-уведомления*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/
              and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
               , con.device_id  device_id           /*ID устройства*/
               , con.push_supported  push_supported /*Признак подключения push-уведомления*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/
             and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
     if null in (nvl(pConnectorGUID, pOperationUID), pPromoterSessionId) then
       RAISE_APPLICATION_ERROR(-20900,'Ошибка входных дaнных при вызове SetPromoClientSessionLog');
     end if;
   
     if (pConnectorGUID is not null) then
       select max(con.profile_id), max(con.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_connectors con
        where con.guid = pConnectorGUID;
     else
       select max(op.profile_id), max(c.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_operations op
            , csa_connectors c
        where op.ouid = pOperationUID
          and c.profile_id = op.profile_id
          and c.state = 'ACTIVE';
     end if;
     
     if l_Profile_id is null then
       RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
     end if;
     
     insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
          values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
     returning id into pPromoClientLogId;
   end;
   procedure GetClientIncognito (pConnectorGUID in varchar2,pOutCursor out sys_refcursor) as
   begin
        if (pConnectorGUID is null) then
           RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientIncognito');
        end if;
          
        open pOutCursor for
             select profiles.incognito client_incognito
               from csa_profiles profiles,
                    csa_connectors con
             where CON.GUID = pConnectorGUID
               and PROFILES.ID = CON.PROFILE_ID;
   end;
end CSA_CONNECTORS_PKG;


-- Номер ревизии: 54876
-- Номер версии: 1.18
-- Комментарий: Реализовать фильтрацию списка ЦСА клиентов.

ALTER TABLE CSA_PROFILES ADD AGREEMENT_NUMBER VARCHAR2(16)
go

ALTER TABLE CSA_PROFILES ADD CREATION_TYPE VARCHAR2(25)
go

ALTER TABLE CSA_PROFILES ADD PHONE VARCHAR2(16)
go

create index CSA_PROFILES_AGREEMENT_NUM_I on CSA_PROFILES (
   AGREEMENT_NUMBER ASC
)
go

create index CSA_PROFILES_PHONE_INDEX on CSA_PROFILES (
   PHONE ASC
)
go

-- Номер ревизии: 54886
-- Номер версии: 1.18
-- Комментарий: Справочник телефонов "инкогнито". Джоб для обновления справочника.

create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
   procedure GetClientIncognito (pConnectorGUID in varchar2,pOutCursor out sys_refcursor);
   procedure GetIncognitoClientList (pOutCursor out sys_refcursor);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
                , con.device_id  device_id           /*ID устройства*/
                , con.push_supported  push_supported /*Признак подключения push-уведомления*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/
              and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if null in (pSurName, pFirstName, pPassport, pBirthdate, pTB) then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
               , con.device_id  device_id           /*ID устройства*/
               , con.push_supported  push_supported /*Признак подключения push-уведомления*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/
             and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
     if null in (nvl(pConnectorGUID, pOperationUID), pPromoterSessionId) then
       RAISE_APPLICATION_ERROR(-20900,'Ошибка входных дaнных при вызове SetPromoClientSessionLog');
     end if;
   
     if (pConnectorGUID is not null) then
       select max(con.profile_id), max(con.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_connectors con
        where con.guid = pConnectorGUID;
     else
       select max(op.profile_id), max(c.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_operations op
            , csa_connectors c
        where op.ouid = pOperationUID
          and c.profile_id = op.profile_id
          and c.state = 'ACTIVE';
     end if;
     
     if l_Profile_id is null then
       RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
     end if;
     
     insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
          values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
     returning id into pPromoClientLogId;
   end;
   procedure GetClientIncognito (pConnectorGUID in varchar2,pOutCursor out sys_refcursor) as
   begin
        if (pConnectorGUID is null) then
           RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientIncognito');
        end if;
          
        open pOutCursor for
             select profiles.incognito client_incognito
               from csa_profiles profiles,
                    csa_connectors con
             where CON.GUID = pConnectorGUID
               and PROFILES.ID = CON.PROFILE_ID;
   end;
   procedure GetIncognitoClientList (pOutCursor out sys_refcursor) as
   begin
		open pOutCursor for
			select distinct con.USER_ID profile_id
			  from csa_profiles profiles,
				   csa_connectors con
			where PROFILES.INCOGNITO = '1'
			  and PROFILES.ID = CON.PROFILE_ID;
   end;
end CSA_CONNECTORS_PKG;


-- Номер ревизии: 54890
-- Номер версии: 1.18
-- Комментарий: Определение региона с сайта www.sberbank.ru

alter table REGIONS add EN_NAME varchar2(128)
go

update REGIONS set EN_NAME='altaikrai' where CODE='01000'
go

update REGIONS set EN_NAME='primorskykrai' where CODE='05000'
go

update REGIONS set EN_NAME='stavropol' where CODE='07000'
go

update REGIONS set EN_NAME='khabarovsk' where CODE='08000'
go

update REGIONS set EN_NAME='volgograd' where CODE='18000'
go

update REGIONS set EN_NAME='samara' where CODE='36000'
go

update REGIONS set EN_NAME='adygea' where CODE='79000'
go

update REGIONS set EN_NAME='buryatia' where CODE='81000'
go

update REGIONS set EN_NAME='dagestan' where CODE='82000'
go

update REGIONS set EN_NAME='ingushetia' where CODE='26000'
go

update REGIONS set EN_NAME='kabardinobalkaria' where CODE='83000'
go

update REGIONS set EN_NAME='mariel' where CODE='88000'
go

update REGIONS set EN_NAME='mordovia' where CODE='89000'
go

update REGIONS set EN_NAME='tuva' where CODE='93000'
go

update REGIONS set EN_NAME='moscow' where CODE='45000'
go

update REGIONS set EN_NAME='karelia' where CODE='86000'
go

update REGIONS set EN_NAME='khakassia' where CODE='95000'
go

update REGIONS set EN_NAME='chuvashia' where CODE='97000'
go

update REGIONS set EN_NAME='zabaykalskykrai' where CODE='76000'
go

update REGIONS set EN_NAME='krasnodar' where CODE='03000'
go

update REGIONS set EN_NAME='krasnoyarsk' where CODE='04000'
go

update REGIONS set EN_NAME='perm' where CODE='57000'
go

update REGIONS set EN_NAME='amur' where CODE='10000'
go

update REGIONS set EN_NAME='arkhangelsk' where CODE='11000'
go

update REGIONS set EN_NAME='astrakhan' where CODE='12000'
go

update REGIONS set EN_NAME='belgorod' where CODE='14000'
go

update REGIONS set EN_NAME='bryansk' where CODE='15000'
go

update REGIONS set EN_NAME='vologda' where CODE='19000'
go

update REGIONS set EN_NAME='voronezh' where CODE='20000'
go

update REGIONS set EN_NAME='ivanovo' where CODE='24000'
go

update REGIONS set EN_NAME='irkutsk' where CODE='25000'
go

update REGIONS set EN_NAME='kaliningrad' where CODE='27000'
go

update REGIONS set EN_NAME='kaluga' where CODE='29000'
go

update REGIONS set EN_NAME='kirov' where CODE='33000'
go

update REGIONS set EN_NAME='kostroma' where CODE='34000'
go

update REGIONS set EN_NAME='lipetsk' where CODE='42000'
go

update REGIONS set EN_NAME='magadan' where CODE='44000'
go

update REGIONS set EN_NAME='murmansk' where CODE='47000'
go

update REGIONS set EN_NAME='nizhnynovgorod' where CODE='22000'
go

update REGIONS set EN_NAME='novgorod' where CODE='49000'
go

update REGIONS set EN_NAME='pskov' where CODE='58000'
go

update REGIONS set EN_NAME='rostov' where CODE='60000'
go

update REGIONS set EN_NAME='ryazan' where CODE='61000'
go

update REGIONS set EN_NAME='smolensk' where CODE='66000'
go

update REGIONS set EN_NAME='tambov' where CODE='68000'
go

update REGIONS set EN_NAME='tver' where CODE='28000'
go

update REGIONS set EN_NAME='tula' where CODE='70000'
go

update REGIONS set EN_NAME='chelyabinsk' where CODE='75000'
go

update REGIONS set EN_NAME='yaroslavl' where CODE='78000'
go

update REGIONS set EN_NAME='saintpetersburg' where CODE='40000'
go

update REGIONS set EN_NAME='penza' where CODE='56000'
go

update REGIONS set EN_NAME='saratov' where CODE='63000'
go

update REGIONS set EN_NAME='vladimir' where CODE='17000'
go

update REGIONS set EN_NAME='kemerovo' where CODE='32000'
go

update REGIONS set EN_NAME='kurgan' where CODE='37000'
go

update REGIONS set EN_NAME='ulyanovsk' where CODE='73000'
go

update REGIONS set EN_NAME='novosibirsk' where CODE='50000'
go

update REGIONS set EN_NAME='omsk' where CODE='52000'
go

update REGIONS set EN_NAME='sverdlovsk' where CODE='65000'
go

update REGIONS set EN_NAME='tomsk' where CODE='69000'
go

update REGIONS set EN_NAME='tyumen' where CODE='71000'
go

update REGIONS set EN_NAME='bashkortostan' where CODE='80000'
go

update REGIONS set EN_NAME='komi' where CODE='87000'
go

update REGIONS set EN_NAME='tatarstan' where CODE='92000'
go

update REGIONS set EN_NAME='udmurtia' where CODE='94000'
go

update REGIONS set EN_NAME='kursk' where CODE='38000'
go

update REGIONS set EN_NAME='oryol' where CODE='54000'
go

update REGIONS set EN_NAME='sakha' where CODE='98000'
go

update REGIONS set EN_NAME='moscowoblast' where CODE='46000'
go

update REGIONS set EN_NAME='orenburg' where CODE='53000'
go

-- Номер ревизии: 54918
-- Номер версии: 1.18
-- Комментарий: Обновление шинных справочников в многоблочной архитектуре. JMS.


alter table CSA_NODES add (DICTIONARY_QUEUE_NAME varchar2(64) default 'jms/dictionary/SynchDictionaryQueue' not null)
go
alter table CSA_NODES add (DICTIONARY_FACTORY_NAME varchar2(64) default 'jms/dictionary/SynchDictionaryQCF' not null)
go

-- Номер ревизии: 54953
-- Номер версии: 1.18
-- Комментарий: BUG063052: Некорректное использование оператора in в CSA_CONNECTORS_PKG 

create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
   procedure GetClientIncognito (pConnectorGUID in varchar2,pOutCursor out sys_refcursor);
   procedure GetIncognitoClientList (pOutCursor out sys_refcursor);
   procedure GetIncognitoClietns (pOutCursor out sys_refcursor);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
                , con.device_id  device_id           /*ID устройства*/
                , con.push_supported  push_supported /*Признак подключения push-уведомления*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/
              and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if pSurName is null or pFirstName is null or pPassport is null or pBirthdate is null or pTB is null then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
               , con.device_id  device_id           /*ID устройства*/
               , con.push_supported  push_supported /*Признак подключения push-уведомления*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/
             and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
     if nvl(pConnectorGUID, pOperationUID) is null or pPromoterSessionId is null then
       RAISE_APPLICATION_ERROR(-20900,'Ошибка входных дaнных при вызове SetPromoClientSessionLog');
     end if;
   
     if (pConnectorGUID is not null) then
       select max(con.profile_id), max(con.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_connectors con
        where con.guid = pConnectorGUID;
     else
       select max(op.profile_id), max(c.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_operations op
            , csa_connectors c
        where op.ouid = pOperationUID
          and c.profile_id = op.profile_id
          and c.state = 'ACTIVE';
     end if;
     
     if l_Profile_id is null then
       RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
     end if;
     
     insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
          values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
     returning id into pPromoClientLogId;
   end;
   procedure GetClientIncognito (pConnectorGUID in varchar2,pOutCursor out sys_refcursor) as
   begin
        if (pConnectorGUID is null) then
           RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientIncognito');
        end if;
          
        open pOutCursor for
             select profiles.incognito client_incognito
               from csa_profiles profiles,
                    csa_connectors con
             where CON.GUID = pConnectorGUID
               and PROFILES.ID = CON.PROFILE_ID;
   end;
   procedure GetIncognitoClientList (pOutCursor out sys_refcursor) as
   begin
   open pOutCursor for
                select distinct con.USER_ID profile_id
                  from csa_profiles profiles,
                       csa_connectors con
                where PROFILES.INCOGNITO = '1'
                  and PROFILES.ID = CON.PROFILE_ID;
   end;
   procedure GetIncognitoClietns (pOutCursor out sys_refcursor) as
   begin
      open pOutCursor for
        select profiles.FIRST_NAME  first_name, 
               profiles.SUR_NAME  sur_name, 
               profiles.PATR_NAME  patr_name, 
               profiles.PASSPORT passport, 
               profiles.BIRTHDATE birthdate, 
               profiles.TB tb
          from CSA_PROFILES profiles 
         where incognito = '1';
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии: 54980
-- Номер версии: 1.18
-- Комментарий: Реализация журнала входов в ЦСА

/*==============================================================*/
/* Table: CSA_ACTION_LOG                                        */
/*==============================================================*/
create table CSA_ACTION_LOG 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   OPERATION_TYPE       VARCHAR2(40)         not null,
   IDENTIFICATION_TYPE  VARCHAR2(15)         not null,
   IDENTIFICATION_PARAM VARCHAR2(350),
   CARD_NUMBER          VARCHAR2(20),
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100)        not null,
   PASSPORT             VARCHAR2(100)        not null,
   IP_ADDRESS           VARCHAR2(15),
   BIRTHDATE            TIMESTAMP            not null,
   ERROR_MESSAGE        CLOB,
   CONFIRMATION_TYPE    VARCHAR2(35),
   EMPLOYEE_FIO         VARCHAR2(300),
   EMPLOYEE_LOGIN       VARCHAR2(50),
   TB                   VARCHAR2(4)          not null,
   LOG_UID              VARCHAR2(32)         not null,
   constraint PK_CSA_ACTION_LOG primary key (ID)
)
partition by range
 (START_DATE)
 (partition
         P_MAXVALUE
        values less than (maxvalue))
go

create sequence S_CSA_ACTION_LOG
go

/*==============================================================*/
/* Index: CSA_ACTIONLOG_UNIVERSAL_ID                            */
/*==============================================================*/
create index CSA_ACTIONLOG_UNIVERSAL_ID on CSA_ACTION_LOG (
   START_DATE ASC,
   FIRST_NAME ASC,
   SUR_NAME ASC,
   PATR_NAME ASC,
   PASSPORT ASC,
   BIRTHDATE ASC,
   TB ASC
)
local
go

alter table CSA_CODLOG add (LOG_UID varchar2(32));

/*==============================================================*/
/* Index: CSA_CL_LOG_UID_INDEX                                  */
/*==============================================================*/
create index CSA_CL_LOG_UID_INDEX on CSA_CODLOG (
   LOG_UID ASC
)
go

alter table CSA_SYSTEMLOG add (LOG_UID varchar2(32))

/* Index: CSA_SL_LOG_UID_INDEX                                  */
/*==============================================================*/
create index CSA_SL_LOG_UID_INDEX on CSA_SYSTEMLOG (
   LOG_UID ASC
)
go

-- Номер ревизии: 55176
-- Номер версии: 1.18
-- Комментарий: Очереди и фабрики служебного канала ЕРМБ

alter table CSA_NODES drop column SERVICE_QUEUE_NAME
go
alter table CSA_NODES drop column SERVICE_FACTORY_NAME
go
alter table CSA_NODES add (SERVICE_PROFILE_QUEUE_NAME varchar2(64) default 'jms/ermb/auxiliary/ConfirmProfileQueue' not null)
go
alter table CSA_NODES add (SERVICE_PROFILE_FACTORY_NAME varchar2(64) default 'jms/ermb/auxiliary/ConfirmProfileQCF' not null)
go
alter table CSA_NODES add (SERVICE_CLIENT_QUEUE_NAME varchar2(64) default 'jms/ermb/auxiliary/UpdateClientQueue' not null)
go
alter table CSA_NODES add (SERVICE_CLIENT_FACTORY_NAME varchar2(64) default 'jms/ermb/auxiliary/UpdateClientQCF' not null)
go
alter table CSA_NODES add (SERVICE_RESOURCE_QUEUE_NAME varchar2(64) default 'jms/ermb/auxiliary/UpdateResourceQueue' not null)
go
alter table CSA_NODES add (SERVICE_RESOURCE_FACTORY_NAME varchar2(64) default 'jms/ermb/auxiliary/UpdateResourceQCF' not null)
go


-- Номер ревизии: 55245
-- Номер версии: 1.18
-- Комментарий: Реализация журнала входов в ЦСА(индексы)
alter table CSA_ACTION_LOG drop column ID;

create unique index CSA_ACTIONLOG_U_LOGUID on CSA_ACTION_LOG (
   LOG_UID ASC
);

drop index CSA_ACTIONLOG_UNIVERSAL_ID;

create index CSA_ACTIONLOG_UNIVERSAL_ID on CSA_ACTION_LOG 
(
PASSPORT ASC,
START_DATE DESC
)
local
;

-- Номер ревизии: 55254
-- Номер версии: 1.18
-- Комментарий: Реализация журнала входов в ЦСА(избавлене от избыточночности)

drop index CSA_ACTIONLOG_U_LOGUID;

-- Номер ревизии: 55432
-- Номер версии: 1.18
-- Комментарий: BUG063489: Доработать джоб актуализации справочника "Инкогнито" для пакетной обработки клиентов.

create or replace package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
   procedure GetClientIncognito (pConnectorGUID in varchar2,pOutCursor out sys_refcursor);
   procedure GetIncognitoClietns (rowPosition in integer,rate in integer,pOutCursor out sys_refcursor);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
                , con.device_id  device_id           /*ID устройства*/
                , con.push_supported  push_supported /*Признак подключения push-уведомления*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/
              and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if pSurName is null or pFirstName is null or pPassport is null or pBirthdate is null or pTB is null then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
               , con.device_id  device_id           /*ID устройства*/
               , con.push_supported  push_supported /*Признак подключения push-уведомления*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
   											CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/
             and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
     if nvl(pConnectorGUID, pOperationUID) is null or pPromoterSessionId is null then
       RAISE_APPLICATION_ERROR(-20900,'Ошибка входных дaнных при вызове SetPromoClientSessionLog');
     end if;
   
     if (pConnectorGUID is not null) then
       select max(con.profile_id), max(con.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_connectors con
        where con.guid = pConnectorGUID;
     else
       select max(op.profile_id), max(c.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_operations op
            , csa_connectors c
        where op.ouid = pOperationUID
          and c.profile_id = op.profile_id
          and c.state = 'ACTIVE';
     end if;
     
     if l_Profile_id is null then
       RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
     end if;
     
     insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
          values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
     returning id into pPromoClientLogId;
   end;
   procedure GetClientIncognito (pConnectorGUID in varchar2,pOutCursor out sys_refcursor) as
   begin
        if (pConnectorGUID is null) then
           RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientIncognito');
        end if;
          
        open pOutCursor for
             select profiles.incognito client_incognito
               from csa_profiles profiles,
                    csa_connectors con
             where CON.GUID = pConnectorGUID
               and PROFILES.ID = CON.PROFILE_ID;
   end;
   procedure GetIncognitoClietns (rowPosition in integer,rate in integer,pOutCursor out sys_refcursor) as
   begin
      if (rowPosition is null or rate is null) then
           open pOutCursor for
               select profiles.FIRST_NAME  first_name,
                      profiles.SUR_NAME  sur_name, 
                      profiles.PATR_NAME  patr_name, 
                      profiles.PASSPORT passport, 
                      profiles.BIRTHDATE birthdate, 
                      profiles.TB tb
                                              
                      from CSA_PROFILES profiles 
                      where incognito = '1';
         
      else
           open pOutCursor for
               select res.* from
                   (select rownum rn, profiles.FIRST_NAME  first_name, 
                                       profiles.SUR_NAME  sur_name, 
                                       profiles.PATR_NAME  patr_name, 
                                       profiles.PASSPORT passport, 
                                       profiles.BIRTHDATE birthdate, 
                                       profiles.TB tb
                                       
                                       from CSA_PROFILES profiles 
                                       where incognito = '1' and rownum < rowPosition*rate
                                       )res 
                                       where res.rn >= (rowPosition*rate-rate);
      
      
      end if;
   end;
end CSA_CONNECTORS_PKG;
/
-- Номер ревизии: 55756
-- Номер версии: 1.18
-- Комментарий: Доработки по журналу входов ЦСА

drop index CSA_CL_DI_DATE_INDEX
go
/*==============================================================*/
/* Index: CSA_CL_DI_DATE_INDEX                                  */
/*==============================================================*/
create index CSA_CL_DI_DATE_INDEX on CSA_CODLOG (
   replace(DOC_NUMBER,' ','') ASC,
   START_DATE DESC
)
local
go
drop index CSA_CL_FIO_BIRT_DATE_IND 	(удалил из модели, на пром его нет)
go

drop index CSA_SL_DI_DATE_INDEX
go
/*==============================================================*/
/* Index: CSA_SL_DI_DATE_INDEX                                  */
/*==============================================================*/
create index CSA_SL_DI_DATE_INDEX on CSA_SYSTEMLOG (
   replace(DOC_NUMBER,' ','') ASC,
   START_DATE DESC
)
local
go
drop index CSA_SL_FIO_BIRT_DATE_IND 	(удалил из модели, на пром его нет)
go

-- Номер ревизии: 56210
-- Номер версии: 1.18
-- Комментарий: BUG064676: Добавить признак «Статус приватности» в профиль клиента в АРМ-сотрудника

CREATE OR REPLACE package CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor);
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor);
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
   procedure GetIncognitoClietns (rowPosition in integer,rate in integer,pOutCursor out sys_refcursor);
   procedure GetClientIncognito (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2, pConnectorGUID in varchar2,pOutCursor out sys_refcursor);
end CSA_CONNECTORS_PKG;
go

CREATE OR REPLACE package body CSA_CONNECTORS_PKG as
   procedure GetConnectedMobileDevices (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2,pAppId in varchar2,pFlag in varchar2,pOutCursor out sys_refcursor) as
   begin
      if pAppId is not null then /*необходимо получить приложения по списку ID*/
        open pOutCursor for
           select con.id connector_id                 /*ID коннектора*/
                , con.guid                            /*GUID коннектора*/
                , con.creation_date connection_date   /*дата подключения*/
                , con.card_number                     /*Номер карты*/
                , con.device_info  mobile_app_type    /*Тип приложения*/
                , con.device_id  device_id           /*ID устройства*/
                , con.push_supported  push_supported /*Признак подключения push-уведомления*/
             from csa_connectors con
            where con.id in (SELECT regexp_substr(pAppId,'[^,]+',1,level) FROM dual t
                             CONNECT BY instr(pAppId, ',', 1, level-1) > 0)
              and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
              and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистрации мобильного устройства*/
              and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      else /*необходим список приложений по ФИО, паспорту(серия+номер), дате рождения и тербанку*/
        /*Контроль входных данных*/
        if pSurName is null or pFirstName is null or pPassport is null or pBirthdate is null or pTB is null then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetConnectedMobileDevices');
        end if;
       
        open pOutCursor for
          select con.id connector_id                /*ID коннектора*/
               , con.guid                           /*GUID коннектора*/
               , con.creation_date connection_date  /*дата подключения*/
               , con.card_number                    /*Номер карты*/
               , con.device_info  mobile_app_type   /*Тип коннектора*/
               , con.device_id  device_id           /*ID устройства*/
               , con.push_supported  push_supported /*Признак подключения push-уведомления*/
            from csa_connectors con, csa_profiles pr
           where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
                                               CONNECT BY instr(pTB, ',', 1, level-1) > 0)
             and con.profile_id = pr.id
             and upper(con.state) in ('ACTIVE', 'BLOCKED') /*активный и временно заблокированный коннектор*/
             and upper(con.type) = 'MAPI'/*коннектор на доступ к mAPI, созданный при регистраци?? мобильного устройства*/
             and (pFlag <> 'true' or (con.device_id is not null and con.push_supported = 1));
      end if;
   end;
   procedure GetClientConnectorByAlias (pAlias in varchar2,pOutCursor out sys_refcursor) as
   begin
       if (pAlias is null) then
         RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientConnectorByAlias');
       end if;
       
       open pOutCursor for
          select con.* 
            from csa_connectors con
           where upper(con.login) = upper(pAlias);
   end;
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
     if nvl(pConnectorGUID, pOperationUID) is null or pPromoterSessionId is null then
       RAISE_APPLICATION_ERROR(-20900,'Ошибка входных дaнных при вызове SetPromoClientSessionLog');
     end if;
   
     if (pConnectorGUID is not null) then
       select max(con.profile_id), max(con.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_connectors con
        where con.guid = pConnectorGUID;
     else
       select max(op.profile_id), max(c.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_operations op
            , csa_connectors c
        where op.ouid = pOperationUID
          and c.profile_id = op.profile_id
          and c.state = 'ACTIVE';
     end if;
     
     if l_Profile_id is null then
       RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
     end if;
     
     insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
          values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
     returning id into pPromoClientLogId;
   end;
   procedure GetIncognitoClietns (rowPosition in integer,rate in integer,pOutCursor out sys_refcursor) as
   begin
      if (rowPosition is null or rate is null) then
           open pOutCursor for
               select profiles.FIRST_NAME  first_name,
                      profiles.SUR_NAME  sur_name, 
                      profiles.PATR_NAME  patr_name, 
                      profiles.PASSPORT passport, 
                      profiles.BIRTHDATE birthdate, 
                      profiles.TB tb
                                              
                      from CSA_PROFILES profiles 
                      where incognito = '1';
         
      else
           open pOutCursor for
               select res.* from
                   (select rownum rn, profiles.FIRST_NAME  first_name, 
                                       profiles.SUR_NAME  sur_name, 
                                       profiles.PATR_NAME  patr_name, 
                                       profiles.PASSPORT passport, 
                                       profiles.BIRTHDATE birthdate, 
                                       profiles.TB tb
                                       
                                       from CSA_PROFILES profiles 
                                       where incognito = '1' and rownum < rowPosition*rate
                                       )res 
                                       where res.rn >= (rowPosition*rate-rate);
      
      
      end if;
   end;
   procedure GetClientIncognito (pSurName in varchar2,pFirstName in varchar2,pPatrName in varchar2,pPassport in varchar2,pBirthdate in timestamp,pTB in varchar2, pConnectorGUID in varchar2,pOutCursor out sys_refcursor) as
   begin
        if pConnectorGUID is not null then 
        open pOutCursor for
             select profiles.incognito client_incognito
               from csa_profiles profiles,
                    csa_connectors con
             where CON.GUID = pConnectorGUID
               and PROFILES.ID = CON.PROFILE_ID;
      else 
        
        if pSurName is null or pFirstName is null or pPassport is null or pBirthdate is null or pTB is null then
          RAISE_APPLICATION_ERROR(-20900,'Ошибка входных данных при вызове GetClientIncognitoByParams');
        end if;
       
        open pOutCursor for        
            select pr.incognito client_incognito
               from csa_profiles pr
            where 
             UPPER(TRIM(REGEXP_REPLACE(pr.sur_name||' '||pr.first_name||' '||pr.patr_name,'( )+',' '))) = UPPER(TRIM(REGEXP_REPLACE(pSurName||' '||pFirstName||' '||pPatrName,'( )+',' ')))
             and replace(pr.passport,' ') = replace(pPassport,' ')
             and pr.birthdate = pBirthdate
             and ltrim(pr.tb,'0') in (SELECT regexp_substr(pTB,'[^,]+',1,level) FROM dual t
                                               CONNECT BY instr(pTB, ',', 1, level-1) > 0);
      end if;
   end;
end CSA_CONNECTORS_PKG;
go

-- Номер ревизии: 56495
-- Номер версии: 1.18
-- Комментарий: CHG065516: Переделать привязку новостей к департаментам
 
alter table NEWS_DEPARTMENT drop constraint PK_NEWS_DEPARTMENT
go
alter table NEWS_DEPARTMENT drop column DEPARTMENT_ID 
go
alter table NEWS_DEPARTMENT modify TB varchar2(4)
go
alter table NEWS_DEPARTMENT add constraint PK_NEWS_DEPARTMENT primary key (NEWS_ID, TB)
go
alter table NEWS_DEPARTMENT drop column NAME
go


-- Номер ревизии: 57150
-- Номер версии: 1.18
-- Комментарий: Переделать привязку логов к департаментам

alter table CSA_SYSTEMLOG drop column DEPARTMENT_ID
GO

-- Номер ревизии: 57206
-- Номер версии: 1.18
-- Комментарий: CHG066358: Разобраться с регионами в ЦСА

truncate table USERS_REGIONS
/ 
drop sequence S_USERS_REGIONS
/
alter table USERS_REGIONS drop constraint PK_USERS_REGIONS
/
alter table USERS_REGIONS add  constraint PK_USERS_REGIONS primary key(CSA_USER_ID)
/
alter table USERS_REGIONS drop column id
/
alter table USERS_REGIONS drop column LAST_USE_DATE
/

-- Номер ревизии: 57526
-- Номер версии: 1.18
-- Комментарий: CHG066358: Разобраться с регионами в ЦСА
truncate table USERS_REGIONS
/ 
alter table USERS_REGIONS add PROFILE_ID NUMBER not null
/
alter table USERS_REGIONS drop constraint PK_USERS_REGIONS
/
alter table USERS_REGIONS add constraint PK_USERS_REGIONS primary key(PROFILE_ID)
/
drop index INDEX_CSA_USER_ID
/
alter table USERS_REGIONS drop column CSA_USER_ID
/


-- Номер ревизии: 57710
-- Номер версии: 1.18
-- Комментарий: Перенос уровня безопасности в ЦСА. Часть 1

alter table CSA_CONNECTORS add (SECURITY_TYPE varchar2(28))
go
alter table CSA_PROFILES add (SECURITY_TYPE varchar2(28))
go
update CSA_CONNECTORS set SECURITY_TYPE = 'LOW'
    where TYPE in ('ATM','DISPOSABLE','TERMINAL')
go

-- Номер ревизии: 57777
-- Номер версии: 1.18
-- Комментарий: Доработки работы с резервными блоками. Признак "частичного" входа.

update CSA_PROFILE_NODES SET STATE = 'PROCESS_MIGRATION' WHERE STATE = 'MIGRATION'
go

-- Номер ревизии: 58064
-- Номер версии: 1.18
-- Комментарий: ENH067465: Многоблочность - настройка для деловой среды в ЦСА Бэк 

UPDATE PROPERTIES 
SET PROPERTY_VALUE = SUBSTR(PROPERTY_VALUE, 0, INSTR(PROPERTY_VALUE, ':')) || '//%s/WSGateListener/services/CSABackRefServiceImpl'
WHERE PROPERTY_KEY = 'com.rssl.auth.csa.back.config.integration.erib.url'


-- Номер ревизии: 58784
-- Номер версии: 1.18
-- Комментарий: BUG023097: Переделать автоматическую публикацию новостей.
alter table NEWS RENAME COLUMN AUTOMATIC_PUBLISH_DATE TO START_PUBLISH_DATE
go

alter table NEWS RENAME COLUMN CANCEL_PUBLISH_DATE TO END_PUBLISH_DATE
go


create index I_NEWS_DATE  ON NEWS(
	START_PUBLISH_DATE, 
	END_PUBLISH_DATE
)
go

update NEWS set START_PUBLISH_DATE = current_date where START_PUBLISH_DATE is null and STATE = 'PUBLISHED'
go

alter table NEWS drop column STATE
go


-- Номер ревизии: 58842
-- Номер версии: 1.18
-- Комментарий: BUG069393: [ЕРМБ] необходимо реализовать CSATransportSmsResponseListener 

alter table CSA_NODES add (SMS_WITH_IMSI_QUEUE_NAME varchar2(64))
go
alter table CSA_NODES add (SMS_WITH_IMSI_FACTORY_NAME varchar2(64))
go
update CSA_NODES set SMS_WITH_IMSI_QUEUE_NAME = 'jms/ermb/transport/CheckIMSIRsQueue'
go
update CSA_NODES set SMS_WITH_IMSI_FACTORY_NAME = 'jms/ermb/transport/CheckIMSIRsQCF'
go
alter table CSA_NODES modify (SMS_WITH_IMSI_QUEUE_NAME not null)
go
alter table CSA_NODES modify (SMS_WITH_IMSI_FACTORY_NAME not null)
go
alter table CSA_NODES modify (SERVICE_PROFILE_QUEUE_NAME default null)
go
alter table CSA_NODES modify (SERVICE_PROFILE_FACTORY_NAME default null)
go
alter table CSA_NODES modify (SERVICE_CLIENT_QUEUE_NAME default null)
go
alter table CSA_NODES modify (SERVICE_CLIENT_FACTORY_NAME default null)
go
alter table CSA_NODES modify (SERVICE_RESOURCE_QUEUE_NAME default null)
go
alter table CSA_NODES modify (SERVICE_RESOURCE_FACTORY_NAME default null)
go

-- Номер ревизии: 59146
-- Номер версии: 1.18
-- Комментарий: ENH067465: Многоблочность - настройка для деловой среды в ЦСА Бэк 

alter table CSA_NODES ADD LISTENER_HOSTNAME VARCHAR2(100) 
go

update CSA_NODES SET LISTENER_HOSTNAME = HOSTNAME
go

alter table CSA_NODES MODIFY LISTENER_HOSTNAME NOT NULL
go

-- Номер ревизии: 61072
-- Номер версии: 1.18
-- Комментарий: CHG071536: Изменить работу кнопки блокировки в списке клиентов ЦСА 
create table CSA_PROFILES_LOCK_CHG071536 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   REASON               VARCHAR2(1024)       not null,
   DATE_FROM            TIMESTAMP            not null,
   DATE_TO              TIMESTAMP,
   LOCKER_FIO           VARCHAR2(100)        not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   constraint PK_CSA_PROFILES_LOCK_CHG071536 primary key (ID)
)
go

create sequence S_CSA_PROFILES_LOCK_CHG071536
go

create index CSA_CHG071536_PROFILE_ID on CSA_PROFILES_LOCK_CHG071536 (
   PROFILE_ID ASC
)
go

-- Номер ревизии: 61711
-- Номер версии: 1.18
-- Комментарий: CHG071536: Перевод ФОС на многоблочный режим.
alter table CSA_NODES add (MULTI_NODE_DATA_QUEUE_NAME varchar2(64))
go
alter table CSA_NODES add (MULTI_NODE_DATA_FACTORY_NAME varchar2(64))
go
update CSA_NODES set MULTI_NODE_DATA_QUEUE_NAME = 'jms/resources/MultiNodeResourcesQueue'
go
update CSA_NODES set MULTI_NODE_DATA_FACTORY_NAME = 'jms/resources/MultiNodeResourcesQCF'
go
alter table CSA_NODES modify (MULTI_NODE_DATA_QUEUE_NAME not null)
go
alter table CSA_NODES modify (MULTI_NODE_DATA_FACTORY_NAME not null)
go

-- Номер ревизии: 62235
-- Номер версии: 1.18
-- Комментарий: Доработки ЕРМБ. Абонентская плата. Реализация многоблочности (ч2)
alter table CSA_NODES add (SERVICE_FEE_RES_QUEUE_NAME varchar2(64))
go
alter table CSA_NODES add (SERVICE_FEE_RES_FACTORY_NAME varchar2(64))
go
update CSA_NODES set SERVICE_FEE_RES_QUEUE_NAME = 'jms/ermb/transport/ServiceFeeResultRqQueue'
go
update CSA_NODES set SERVICE_FEE_RES_FACTORY_NAME = 'jms/ermb/transport/ServiceFeeResultRqQCF'
go
alter table CSA_NODES modify (SERVICE_FEE_RES_QUEUE_NAME not null)
go
alter table CSA_NODES modify (SERVICE_FEE_RES_FACTORY_NAME not null)
go

-- Номер ревизии: 62760
-- Номер версии: 1.18
-- Комментарий: ENH070461: Уйти от использования ConnectorInfo в ЕРИБ и ЦСА 

alter table CSA_CONNECTORS add (REGISTRATION_LOGIN_TYPE varchar2(20))
go
update CSA_CONNECTORS connector set connector.REGISTRATION_LOGIN_TYPE = (select info.LOGIN_TYPE from CONNECTORS_INFO info where info.GUID = connector.GUID) where connector.TYPE = 'MAPI'
go
drop table CONNECTORS_INFO
go
drop sequence S_CONNECTORS_INFO
go
drop index CONNECTORS_INFO_INDEX
go

-- Номер ревизии: 62764
-- Номер версии: 1.18
-- Комментарий: CHG070268: Сделать ip-адрес для запросов в цса необязательным 

alter table CSA_OPERATIONS modify (IP_ADDRESS null)
go

-- Номер ревизии: 62972
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. ФПП. Доработка ЦСА. (фабрика/очередь) 
alter table CSA_NODES add (MBK_REGISTRATION_QUEUE_NAME varchar2(64))
go
alter table CSA_NODES add (MBK_REGISTRATION_FACTORY_NAME varchar2(64))
go
update CSA_NODES set MBK_REGISTRATION_QUEUE_NAME = 'jms/ermb/mbk/RegistrationRqQueue'
go
update CSA_NODES set MBK_REGISTRATION_FACTORY_NAME = 'jms/ermb/mbk/RegistrationRqQCF'
go
alter table CSA_NODES modify (MBK_REGISTRATION_QUEUE_NAME not null)
go
alter table CSA_NODES modify (MBK_REGISTRATION_FACTORY_NAME not null)
go

-- Номер ревизии: 63282
-- Номер версии: 1.18
-- Комментарий: BUG071121: полное сканирование таблицы CSA_PROFILES 

create index CSA_PROFILES_INCOGNITO_INDEX on CSA_PROFILES (
   decode(INCOGNITO, '1', ID, null) ASC
)
go

-- Номер ревизии: 64386
-- Номер версии: 1.18
-- Комментарий: CHG074609 	Использование CSA_PROFILES_UNIVERSAL_ID

DROP INDEX CSA_PROFILES_UNIVERSAL_ID;

CREATE UNIQUE INDEX CSA_PROFILES_UNIVERSAL_ID
   ON CSA_PROFILES (
      UPPER (
         TRIM (
            REGEXP_REPLACE (
               SUR_NAME || ' ' || FIRST_NAME || ' ' || PATR_NAME,
               '( )+',
               ' '))) ASC,
      BIRTHDATE ASC,
      REPLACE (PASSPORT, ' ', '') ASC,
      TB ASC);

-- Номер ревизии: 64472
-- Номер версии: 1.18
-- Комментарий: BUG074237: Запрос получения списка клиентов ЦСА.

drop index CSA_PROFILES_DUL_BIRTH_INDEX;

create index CSA_PROFILES_DUL_BIRTH_INDEX on CSA_PROFILES (
   REPLACE(REPLACE(PASSPORT,' ',''),'-','') ASC,
   BIRTHDATE DESC
);


-- Номер ревизии: 65586
-- Номер версии: 1.18
-- Комментарий: BUG076155: АРМ сотрудника : Поиск клиентов : Не ищется клиент без Creation_Type 

-- для потенциальных клиентов в блоках (USERS.CREATION_TYPE == 'POTENTIAL') в профилях цса необходимо заполнить поле CREATION_TYPE (CSA_PROFILES.CREATION_TYPE = 'POTENTIAL')

-- Номер ревизии: 66448
-- Номер версии: 1.18
-- Комментарий: BUG069682 Не закрываются курсоры в базе ЦСА(Удалены неиспользуемые хранимки)

create or replace package CSA_CONNECTORS_PKG as
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
     if nvl(pConnectorGUID, pOperationUID) is null or pPromoterSessionId is null then
       RAISE_APPLICATION_ERROR(-20900,'Ошибка входных дaнных при вызове SetPromoClientSessionLog');
     end if;
   
     if (pConnectorGUID is not null) then
       select max(con.profile_id), max(con.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_connectors con
        where con.guid = pConnectorGUID;
     else
       select max(op.profile_id), max(c.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_operations op
            , csa_connectors c
        where op.ouid = pOperationUID
          and c.profile_id = op.profile_id
          and c.state = 'ACTIVE';
     end if;
     
     if l_Profile_id is null then
       RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
     end if;
     
     insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
          values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
     returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;
/

-- Номер ревизии: 67348
-- Номер версии: 1.18
-- Комментарий: Push III: Добавить в БД ЦСА таблицу PUSH_PARAMS
create table PUSH_PARAMS 
(
   ID                   INTEGER              not null,
   TYPE                 VARCHAR(20)          not null,
   KEY                  VARCHAR(255)         not null,
   TEXT                 CLOB                 not null,
   SHORT_TEXT           VARCHAR(255)         not null,
   DESCRIPTION          VARCHAR(255),
   PRIORITY             INTEGER,
   CODE                 NUMBER(2)            not null,
   PRIVACY_TYPE         VARCHAR(20)          not null,
   PUBLICITY_TYPE       VARCHAR(20)          not null,
   SMS_BACKUP           CHAR(1)              not null,
   VARIABLES            VARCHAR(4000),
   LAST_MODIFIED        TIMESTAMP,
   PREVIOUS_TEXT        CLOB,
   EMPLOYEE_LOGIN_ID    INTEGER,
   constraint PK_PUSH_PARAMS primary key (ID)
)
go
create sequence S_PUSH_PARAMS
go
create unique index I_PUSH_PARAMS_KEY on PUSH_PARAMS (
   KEY ASC
)
go


-- Номер ревизии: 67299
-- Номер версии: 1.18
-- Комментарий: Проверки уникальности телефонов и карт в МБК и ЕРМБ. Модель.
alter table ERMB_CLIENT_PHONE add CREATION_DATE timestamp
go
update ERMB_CLIENT_PHONE set CREATION_DATE = sysdate
go
alter table ERMB_CLIENT_PHONE modify CREATION_DATE not null
go
alter table CARD_LINKS add CREATION_DATE timestamp
go
update CARD_LINKS set CREATION_DATE = sysdate
go
alter table CARD_LINKS modify CREATION_DATE not null
go

-- Номер ревизии: 67419
-- Номер версии: 1.18
-- Комментарий: Реализовать выгрузку xml файла сообщений.
create index I_ERIB_STATIC_MESSAGE_BK on ERIB_STATIC_MESSAGE (
   BUNDLE ASC,
   KEY ASC
)
go
create index I_ERIB_STATIC_MESSAGE_LI on ERIB_STATIC_MESSAGE (
   LOCALE_ID ASC,
   ID ASC
)
go

-- Номер ревизии: 67466
-- Номер версии: 1.18
-- Комментарий: Тех-перерывы МБК - Реализация кэша хранимых процедур МБК
/*==============================================================*/
/* Table: MBK_CACHE_CARDS_BY_PHONE                              */
/*==============================================================*/
create table MBK_CACHE_CARDS_BY_PHONE 
(
   PHONE_NUMBER         VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   RESULT_SET           CLOB,
   constraint PK_MBK_CACHE_CARDS_BY_PHONE primary key (PHONE_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_CLIENT_BY_CARD                              */
/*==============================================================*/
create table MBK_CACHE_CLIENT_BY_CARD 
(
   CARD_NUMBER          VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   FATHERS_NAME         VARCHAR2(100),
   LAST_NAME            VARCHAR2(100)        not null,
   REG_NUMBER           VARCHAR2(20)         not null,
   BIRTH_DATE           TIMESTAMP            not null,
   CB_CODE              VARCHAR2(20)         not null,
   AUTH_IDT             VARCHAR2(10)         not null,
   CONTR_STATUS         INTEGER              not null,
   ADD_INFO_CN          INTEGER              not null,
   constraint PK_MBK_CACHE_CLIENT_BY_CARD primary key (CARD_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_CLIENT_BY_LOGIN                             */
/*==============================================================*/
create table MBK_CACHE_CLIENT_BY_LOGIN 
(
   AUTH_IDT             VARCHAR2(10)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   FATHERS_NAME         VARCHAR2(100),
   LAST_NAME            VARCHAR2(100)        not null,
   REG_NUMBER           VARCHAR2(20)         not null,
   BIRTH_DATE           TIMESTAMP            not null,
   CB_CODE              VARCHAR2(20)         not null,
   CARD_NUMBER          VARCHAR2(20)         not null,
   CONTR_STATUS         INTEGER              not null,
   ADD_INFO_CN          INTEGER              not null,
   constraint PK_MBK_CACHE_CLIENT_BY_LOGIN primary key (AUTH_IDT)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_IMSI_CHECK_RESULT                           */
/*==============================================================*/
create table MBK_CACHE_IMSI_CHECK_RESULT 
(
   PHONE_NUMBER         VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   MESSAGE_ID           INTEGER              not null,
   VALIDATION_RESULT    INTEGER,
   constraint PK_MBK_CACHE_IMSI_CHECK_RESULT primary key (PHONE_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_REGISTRATIONS                               */
/*==============================================================*/
create table MBK_CACHE_REGISTRATIONS 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS primary key (STR_CARD_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_REGISTRATIONS2                              */
/*==============================================================*/
create table MBK_CACHE_REGISTRATIONS2 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS2 primary key (STR_CARD_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_REGISTRATIONS3                              */
/*==============================================================*/
create table MBK_CACHE_REGISTRATIONS3 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS3 primary key (STR_CARD_NUMBER)
)
go


-- Номер ревизии: 67729
-- Номер версии: 1.18
-- Комментарий: Stand-In. форма состояния миграции
create index I_CSA_PROFILE_NODES_STATE ON CSA_PROFILE_NODES (
    STATE
)
go


-- Номер ревизии: 66769
-- Номер версии: 1.18
-- Комментарий: Разделить коннекторы mAPI и socialAPI в ЦСА
update csa_connectors c set c.type = 'SOCIAL' where upper(c.device_info) in ('FACEBOOK', 'VKONTAKTE', 'ODNOKLAS')


-- Номер ревизии: 68273
-- Номер версии: 1.18
-- Комментарий: Stand-In. Доработка функционала определения блока сотрудника

alter table CSA_NODES add ADMIN_AVAILABLE char(1)
go

update CSA_NODES set ADMIN_AVAILABLE = '1'
go

alter table CSA_NODES modify ADMIN_AVAILABLE not null
go

-- Номер ревизии: 68316
-- Номер версии: 1.18
-- Комментарий: BUG079428 Заголовок: Исключение при выполнении LoadCSA_PushCSABackResources

alter table PUSH_PARAMS add (ATTRIBUTES clob)
go

-- Номер ревизии: 69382
-- Номер версии: 1.18
-- Комментарий: Добавление механизма технологических перерывов в ЦСА

/*==============================================================*/
/* Table: TECHNOBREAKS                                          */
/*==============================================================*/
create table TECHNOBREAKS 
(
   ID                   INTEGER              not null,
   ADAPTER_UUID         VARCHAR2(64)         not null,
   FROM_DATE            TIMESTAMP            not null,
   TO_DATE              TIMESTAMP            not null,
   PERIODIC             VARCHAR2(15)         not null,
   IS_DEFAULT_MESSAGE   CHAR(1)              default '1' not null,
   MESSAGE              VARCHAR2(200)        not null,
   STATUS               VARCHAR2(10)         not null,
   IS_AUTO_ENABLED      char(1)              default '0' not null,
   IS_ALLOWED_OFFLINE_PAY CHAR(1)              default '0' not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_TECHNOBREAKS primary key (ID)
)

go

create sequence S_TECHNOBREAKS
go

/*==============================================================*/
/* Index: I_TECHNOBREAKS                                        */
/*==============================================================*/
create index I_TECHNOBREAKS on TECHNOBREAKS (
   ADAPTER_UUID ASC,
   STATUS ASC,
   TO_DATE ASC
)
go

/*==============================================================*/
/* Index: UI_TECHNOBREAKS                                       */
/*==============================================================*/
create unique index UI_TECHNOBREAKS on TECHNOBREAKS (
   UUID ASC
)
go

-- Номер ревизии: 69762
-- Номер версии: 1.18
-- Комментарий: Обработка обратного потока way4(модель БД).

/*==============================================================*/
/* Table: WAY4_NOTIFICATION_JOURNAL                             */
/*==============================================================*/
create table WAY4_NOTIFICATION_JOURNAL 
(
   ID                   INTEGER              not null,
   CLIENTID             VARCHAR2(100)        not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   CB_CODE              VARCHAR2(20)         not null,
   BIRTHDATE            TIMESTAMP            not null,
   AMND_DATE            TIMESTAMP            not null
)
partition by range (AMND_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
subpartition by hash (CLIENTID) subpartitions 64
(
    partition P_FIRST values less than (to_date('01-01-2014','DD-MM-YYYY'))
)
go

create sequence S_WAY4_NOTIFICATION_JOURNAL cache 10000 
go

/*==============================================================*/
/* Index: WAY4_NOTIF_CLIENTID_DATE_INDEX                        */
/*==============================================================*/
create index WAY4_NOTIF_CLIENTID_DATE_INDEX on WAY4_NOTIFICATION_JOURNAL (
   CLIENTID ASC,
   AMND_DATE DESC
)
local
go

/*==============================================================*/
/* Table: CLIENT_IDS                                            */
/*==============================================================*/
create table CLIENT_IDS 
(
   PROFILE_ID           INTEGER              not null,
   CLIENTID             VARCHAR2(100)        not null,
   AMND_DATE            TIMESTAMP            not null
)

go

create sequence S_CLIENT_IDS
go

/*==============================================================*/
/* Index: CLIENT_IDS_PROFILE_ID_INDEX                           */
/*==============================================================*/
create index CLIENT_IDS_PROFILE_ID_INDEX on CLIENT_IDS (
   PROFILE_ID ASC
)
go

/*==============================================================*/
/* Index: CLIENT_IDS_CLIENT_ID_INDEX                            */
/*==============================================================*/
create index CLIENT_IDS_CLIENT_ID_INDEX on CLIENT_IDS (
   CLIENTID ASC
)
go

alter table CLIENT_IDS
   add constraint FK_CLIENT_IDS_TO_PROFILES foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go

-- Номер ревизии: 72813
-- Номер версии: 1.18
-- Комментарий: Уменьшение количества jndi-имен для ЕРМБ

alter table CSA_NODES drop (SMS_WITH_IMSI_QUEUE_NAME, SMS_WITH_IMSI_FACTORY_NAME, SERVICE_PROFILE_QUEUE_NAME, SERVICE_PROFILE_FACTORY_NAME, SERVICE_CLIENT_QUEUE_NAME, SERVICE_CLIENT_FACTORY_NAME,
SERVICE_RESOURCE_QUEUE_NAME, SERVICE_RESOURCE_FACTORY_NAME, SERVICE_FEE_RES_QUEUE_NAME, SERVICE_FEE_RES_FACTORY_NAME);

alter table CSA_NODES modify SMS_FACTORY_NAME default 'jms/ermb/ErmbQCF';
alter table CSA_NODES add (ERMB_QUEUE_NAME varchar2(64) default 'jms/ermb/ErmbQueue' not null);
alter table CSA_NODES add (ERMB_FACTORY_NAME varchar2(64) default 'jms/ermb/ErmbQCF' not null);


-- Номер ревизии: 73357
-- Номер версии: 1.18
-- Комментарий: Гостевой вход.Доработка бэка.Реализация схемы данных(ч1)

create sequence S_GUEST_CODE_PROFILES
cache 10000
go


/*==============================================================*/
/* Table: GUEST_OPERATIONS                                      */
/*==============================================================*/
create table GUEST_OPERATIONS 
(
   OUID                 VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   PHONE                VARCHAR2(16)         not null,
   TYPE                 VARCHAR2(40)         not null,
   STATE                VARCHAR2(40)         not null,
   AUTH_CODE            VARCHAR2(40),
   AUTH_ERRORS          INTEGER,
   IP_ADDRESS           VARCHAR2(15),
   INFO                 VARCHAR2(4000),
   PARAMS               VARCHAR2(4000)
)
partition by range (CREATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH')) 
subpartition by hash (PHONE) subpartitions 64
(
       partition P_2015_02 values less than (timestamp '2015-02-01 00:00:00')
)
go

/*==============================================================*/
/* Index: IDX_GUEST_OPERATIONS_OOUD                             */
/*==============================================================*/
create unique index IDX_GUEST_OPERATIONS_OOUD on GUEST_OPERATIONS (
   OUID ASC
)
global partition by hash
 (OUID)
 partitions 64
go

/*==============================================================*/
/* Table: GUEST_PROFILES                                        */
/*==============================================================*/
create table GUEST_PROFILES 
(
   ID                   INTEGER              not null,
   PHONE                VARCHAR2(16)         not null,
   CODE                 INTEGER,
   BLOCKED_UNTIL        TIMESTAMP,
   AUTH_ERRORS          INTEGER,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100),
   BIRTHDATE            TIMESTAMP,
   TB                   VARCHAR2(4),
   constraint PK_GUEST_PROFILES primary key (ID)
)
partition by hash
 (PHONE)
 partitions 64
go

create sequence S_GUEST_PROFILES
cache 1000
go

/*==============================================================*/
/* Index: IDX_GUEST_PROFILES_PHONE                              */
/*==============================================================*/
create unique index IDX_GUEST_PROFILES_PHONE on GUEST_PROFILES (
   PHONE ASC
)
local
go

-- Номер ревизии: 73539
-- Номер версии: 1.18
-- Комментарий: Гостевой вход.Доработка бэка.Реализация схемы данных(ч2) корретировки (Внимание!!! Таблица CSA_LOGINS и связанные с ней констрейнты и индесы будут изменены ниже.)

/*==============================================================*/
/* Table: CSA_LOGINS                                            */
/*==============================================================*/
create table CSA_LOGINS 
(
   LOGIN                VARCHAR2(32)         not null,
   CONNECTOR_ID         INTEGER,
   GUEST_ID             INTEGER
)
partition by hash
 (LOGIN)
 partitions 64
go

/*==============================================================*/
/* Index: DXFK_CSA_LOGINS_TO_CONNECTORS                         */
/*==============================================================*/
create index DXFK_CSA_LOGINS_TO_CONNECTORS on CSA_LOGINS (
   CONNECTOR_ID ASC
)
global partition by hash
 (CONNECTOR_ID)
 partitions 64
go

/*==============================================================*/
/* Index: DXFK_CSA_LOGINS_TO_GUEST_PROF                         */
/*==============================================================*/
create index DXFK_CSA_LOGINS_TO_GUEST_PROF on CSA_LOGINS (
   GUEST_ID ASC
)
global partition by hash
 (GUEST_ID)
 partitions 64
go

/*==============================================================*/
/* Index: IDX_CSA_LOGINS                                        */
/*==============================================================*/
create unique index IDX_CSA_LOGINS on CSA_LOGINS (
   LOGIN ASC
)
local
go


/*==============================================================*/
/* Table: GUEST_PASSWORDS                                       */
/*==============================================================*/
create table GUEST_PASSWORDS 
(
   ID                   INTEGER              not null,
   VALUE                VARCHAR2(40)         not null,
   SALT                 VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            not null,
   ACTIVE               CHAR(1)              not null,
   GUEST_ID             INTEGER              not null,
   constraint PK_GUEST_PASSWORDS primary key (ID)
)
partition by range
 (CREATION_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-02-2015','DD-MM-YYYY')))
go

create sequence S_GUEST_PASSWORDS
go

/*==============================================================*/
/* Index: IDX_GUEST_DATE_PASSWORDS                              */
/*==============================================================*/
create index IDX_GUEST_DATE_PASSWORDS on GUEST_PASSWORDS (
   GUEST_ID ASC,
   CREATION_DATE ASC
)
go

alter table CSA_LOGINS
   add constraint FK_CSA_LOGINS_TO_CONNECTORS foreign key (CONNECTOR_ID)
      references CSA_CONNECTORS (ID)
go

alter table CSA_LOGINS
   add constraint FK_CSA_LOGINS_TO_GUEST foreign key (GUEST_ID)
      references GUEST_PROFILES (ID)
go


alter table GUEST_PASSWORDS
   add constraint FK_GUEST_PASS_TO_PROFILES foreign key (GUEST_ID)
      references GUEST_PROFILES (ID)
go

-- Номер ревизии: 73670 
-- Номер версии: 1.18
-- Комментарий: Гостевой вход. Миграция

alter table CSA_NODES add GUEST_AVAILABLE char(1)
go

update CSA_NODES set GUEST_AVAILABLE = '0'
go

alter table CSA_NODES modify GUEST_AVAILABLE not null
go

-- Номер ревизии: 73926 
-- Номер версии: 1.18
-- Комментарий: Гостевой вход. Реализация схемы данных(ч3)

drop table CSA_LOGINS
go

create table CSA_LOGINS 
(
   LOGIN             VARCHAR2(32)not null,
   CONNECTOR_ID      INTEGER,
   GUEST_ID          INTEGER
)
go

alter table CSA_LOGINS
   add constraint FK_CSA_LOGINS_TO_CONNECTORS foreign key (CONNECTOR_ID)
      references CSA_CONNECTORS (ID)
go

alter table CSA_LOGINS
   add constraint FK_CSA_LOGINS_TO_GUEST foreign key (GUEST_ID)
      references GUEST_PROFILES (ID)
go

create index "DXFK_CSA_LOGINS_TO_CONNECTORS" on CSA_LOGINS
(
   CONNECTOR_ID
)
go

create index "DXFK_CSA_LOGINS_TO_GUEST_PROF" on CSA_LOGINS
(
   GUEST_ID
)
go

create unique index IDX_CSA_LOGINS on CSA_LOGINS (
   UPPER(LOGIN) ASC
)
go

insert into CSA_LOGINS (LOGIN, CONNECTOR_ID, GUEST_ID) 
(select LOGIN as LOGIN, ID as CONNECTOR_ID, null as GUEST_ID 
	from CSA_CONNECTORS where STATE = 'ACTIVE' and TYPE in ('CSA', 'TERMINAL') and LOGIN is not null)
go

-- Номер ревизии: 74199
-- Номер версии: 1.18
-- Комментарий:  CHG084758: [ISUP] [Деловая среда] ошибка при подтверждении верификации чековым паролем 

alter table csa_operations modify CONFIRM_SID VARCHAR2(64)
go


-- Номер ревизии: 79854
-- Номер версии: 1.18
-- Комментарий:  BUG080895: [Многоязычность] Добавить кнопку "Заполнить язык"(Ч5 ограничение входа(mapi).)(БД)

create table BLOCKINGRULES_RES 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   ERIB_MESSAGE         VARCHAR2(1024),
   MAPI_MESSAGE         VARCHAR2(1024),
   ATM_MESSAGE          VARCHAR2(1024),
   ERMB_MESSAGE         VARCHAR2(1024),
   constraint PK_BLOCKINGRULES_RES primary key (ID, LOCALE_ID)
)
go

create table ERIB_LOCALES 
(
   ID                   varchar2(30)         not null,
   NAME                 VARCHAR2(100)        not null,
   IMAGE_ID             INTEGER              not null,
   ERIB_AVAILABLE       CHAR(1)              not null,
   MAPI_AVAILABLE       CHAR(1)              not null,
   ATMAPI_AVAILABLE     CHAR(1)              not null,
   WEBAPI_AVAILABLE     CHAR(1)              not null,
   ERMB_AVAILABLE       CHAR(1)              not null,
   STATE                VARCHAR2(10)         not null,
   ACTUAL_DATE          TIMESTAMP            not null,
   constraint PK_ERIB_LOCALES primary key (ID)
)
go

create table ERIB_STATIC_MESSAGE 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   BUNDLE               VARCHAR2(40)         not null,
   KEY                  VARCHAR2(200)        not null,
   MESSAGE              VARCHAR2(2048)       not null,
   constraint PK_ERIB_STATIC_MESSAGE primary key (ID)
)

go

create sequence S_ERIB_STATIC_MESSAGE
go


create index I_ERIB_STATIC_MESSAGE_LBK on ERIB_STATIC_MESSAGE (
   LOCALE_ID ASC,
   BUNDLE ASC,
   KEY ASC
)
go


-- Номер ревизии: 81425
-- Номер версии: 1.18
-- Комментарий: BUG090050: Многоязычность : Локализация справочника текстов СМС

CREATE TABLE SMS_RESOURCES_RES  
( 
    ID       	INTEGER NOT NULL,
    LOCALE_ID	VARCHAR2(30) NOT NULL,
    TEXT     	CLOB NOT NULL,
    CONSTRAINT PK_SMS_RESOURCES_RES PRIMARY KEY(ID,LOCALE_ID)
)
GO


-- Номер ревизии: 83464
-- Номер версии: 1.18
-- Комментарий: mdm ч.17. Доработка интеграции с ЦСА

create table PROFILE_MDM_IDS 
(
   CSA_PROFILE_ID       INTEGER              not null,
   MDM_ID               VARCHAR2(64)         not null   
)
go

create unique index U_CSA_PROFILE_ID on PROFILE_MDM_IDS
(
    CSA_PROFILE_ID
)
go

alter table PROFILE_MDM_IDS
   add constraint FK_P_MDM_TO_PROFILE foreign key (CSA_PROFILE_ID)
      references CSA_PROFILES (ID)
      on delete cascade
go

-- Номер ревизии 85289
-- Номер Версии: v1.18
-- Комментарий: Пакетирование вызова mb_WWW_GetRegistrations.
create table MBK_CACHE_REGISTRATIONS_PACK
(
  STR_CARDS VARCHAR2(2000) not null,
  REQUEST_TIME    TIMESTAMP(6) not null,
  STR_RET_STR     CLOB
);
alter table MBK_CACHE_REGISTRATIONS_PACK
  add constraint PK_MBK_CACHE_REG_PACK primary key (STR_CARDS);
  
create table MBK_CACHE_REGISTRATIONS_PACK3
(
  STR_CARDS VARCHAR2(2000) not null,
  REQUEST_TIME    TIMESTAMP(6) not null,
  STR_RET_STR     CLOB
);
alter table MBK_CACHE_REGISTRATIONS_PACK3
  add constraint PK_MBK_CACHE_REG_PACK3 primary key (STR_CARDS);