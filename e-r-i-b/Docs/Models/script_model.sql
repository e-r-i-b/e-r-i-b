-- Номер ревизии: 37469
-- Номер версии: 8.0
-- Комментарий: CHG040654: Установка группы риска по переводам частному лицу 

create table PAYMENTS_GROUP_RISK  (
   ID                   INTEGER                         not null,
   PAYMENT_TYPE         VARCHAR2(50)                    not null,
   GROUP_RISK           VARCHAR2(10)                    not null,
   DEPARTMENT_ID        INTEGER                         not null,
   constraint PK_PAYMENTS_GROUP_RISK primary key (ID)
)
;
create sequence S_PAYMENTS_GROUP_RISK
;
create index DXFK_PAYMENT_GROUP_RISK_TO_DEP on PAYMENTS_GROUP_RISK
(
   DEPARTMENT_ID
)
;
alter table PAYMENTS_GROUP_RISK
   add constraint FK_PAYMENTS_FK_PAYMEN_DEPARTME foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
      on delete cascade;



-- Номер ревизии: 37626
-- Номер версии: 8.0.1
-- Комментарий: CHG041044 Ограничить количество получений информации о ФИО 

create table USER_COUNTERS  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   VALUE                INTEGER                         not null,
   CHANGE_DATE          TIMESTAMP                       not null,
   constraint PK_USER_COUNTERS primary key (ID)
)
;

create sequence S_USER_COUNTERS
;
create index "DXFK_USER_COUNTERS_TO_LOGIN" on USER_COUNTERS
(
   LOGIN_ID
)
;

alter table USER_COUNTERS
   add constraint FK_USER_COUNTERS_TO_LOGIN foreign key (LOGIN_ID)
      references LOGINS (ID)
;


-- Номер ревизии: 37689
-- Номер версии: 1.18
-- Комментарий: CHG041044 Ограничить количество получений информации о ФИО 

alter table  USER_COUNTERS add COUNTER_TYPE VARCHAR2(25) not null;

ALTER TABLE USER_COUNTERS
add CONSTRAINT UNIQUE_LOGIN_ID_COUNTER_TYPE UNIQUE (LOGIN_ID, COUNTER_TYPE);



-- Номер ревизии: 37713
-- Номер версии: 1.18
-- Комментарий: ENH041187: Переделать получение СКС в шлюзе КСШ  

ALTER TABLE CARD_LINKS
ADD (KIND INTEGER);

ALTER TABLE CARD_LINKS
ADD (SUB_KIND INTEGER);



-- Номер ревизии: 38369
-- Номер версии: 1.18
-- Комментарий: ENH041795: Добавить возможность указывать несколько ТБ в настройках внешней системы.

create table EXTERNAL_SYSTEM_DEPARTMENTS (
   EXT_SYS_ID integer not null,
   TB_ID integer not null,
   LIST_INDEX integer not null,

   constraint PK_EXT_SYS_DEP primary key (EXT_SYS_ID, TB_ID)
)
go

insert into EXTERNAL_SYSTEM_DEPARTMENTS 
   select ID as EXT_SYS_ID, TB_ID, 0 from EXTERNAL_SYSTEM
go

alter table EXTERNAL_SYSTEM drop constraint FK_EXT_SYS_TO_DEPARTMENTS
go

alter table EXTERNAL_SYSTEM drop column TB_ID
go

create index "DXFK_EXT_SYS_LINK" on EXTERNAL_SYSTEM_DEPARTMENTS
(
   EXT_SYS_ID
)
go

alter table EXTERNAL_SYSTEM_DEPARTMENTS
   add constraint FK_EXT_SYS_LINK foreign key (EXT_SYS_ID)
      references EXTERNAL_SYSTEM (ID)
go


create index "DXFK_SYS_TB_LINK_ID" on EXTERNAL_SYSTEM_DEPARTMENTS
(
   TB_ID
)
go

alter table EXTERNAL_SYSTEM_DEPARTMENTS
   add constraint FK_SYS_TB_LINK_ID foreign key (TB_ID)
      references DEPARTMENTS (ID)
go

-- Номер ревизии: 38404
-- Номер версии: 1.18
-- Комментарий: BUG042398: Убрать ссылку сохранить шаблон для поставщика аэрофлота.

alter table SERVICE_PROVIDERS add (IS_TEMPLATE_SUPPORTED CHAR(1) default '1' not null);

-- Номер ревизии: 38478
-- Номер версии: 1.18
-- Комментарий: CHG041256: Реализовать фильтрацию URL для баннеров

create table WHITE_LIST_URL  (
   ID                   INTEGER                         not null,
   URL                  VARCHAR2(256)                   not null,
   constraint PK_WHITE_LIST_URL primary key (ID),
   constraint UK_UNIQUE_URL unique (URL)
)
go

create sequence S_WHITE_LIST_URL
go


-- Номер ревизии: 38568
-- Номер версии: 1.18
-- Комментарий: ENH040530: Сортировка поставщиков.
alter table SERVICE_PROVIDERS
   add SORT_PRIORITY integer
go

update SERVICE_PROVIDERS set SORT_PRIORITY = 0
go

alter table SERVICE_PROVIDERS
   modify SORT_PRIORITY not null
go


-- Номер ревизии: 38618
-- Номер версии: 1.18
-- Комментарий: BUG042457: Распадается связка услуг ПУ при прикреплении логотипа. 
--              Скрипт ДОЛЖЕН работать в два этапа. На первом этапе мы добавляем столбец md5 и индекс на него.
--              Далее необходимо выполнить ant install.UpdateImagesHashKey
--              Затем необходимо выполнить вторую часть скрипта, обновление таблиц и удаление дублирующихся картинок.
--              Скрипт неактуален, актуальный скрипт приведен в ревизии 38882.
-- Первый этап:
alter table IMAGES add MD5 varchar(32)
go

create index IDX_IMAGES_MD5 on IMAGES
(
    MD5 asc
)
go

-- Второй этап. Необходимо убедиться, что все md5 в таблице IMAGES не пусты перед выполнением второй части скрипта.
update IMAGES_MESSAGES set IMAGE_ID =
    (select ii.ID from IMAGES i, (
        select max(ID) ID, img.MD5 MD5 from IMAGES img
        group by MD5) ii
     where i.md5 = ii.md5 and i.id = IMAGE_ID)
where not (
        IMAGE_ID in (
            select max(ID) ID from IMAGES
            group by MD5
        )
    )
go

update PAYMENT_SERVICES set IMAGE_ID =
    (select ii.ID from IMAGES i, (
        select max(ID) ID, img.MD5 MD5 from IMAGES img
        group by MD5) ii
     where i.md5 = ii.md5 and i.id = IMAGE_ID)
where not (
        IMAGE_ID in (
            select max(ID) ID from IMAGES
            group by MD5
        )
    )
go  

update SERVICE_PROVIDERS set IMAGE_ID =
    (select ii.ID from IMAGES i, (
        select max(ID) ID, img.MD5 MD5 from IMAGES img
        group by MD5) ii
     where i.md5 = ii.md5 and i.id = IMAGE_ID)
where not (
        IMAGE_ID in (
            select max(ID) ID from IMAGES
            group by MD5
        )
    )
go

alter table IMAGES_MESSAGES
   drop constraint FK_IMG_MSGS_FK_IMAGES
go

alter table IMAGES_MESSAGES
   add constraint FK_IMG_MSGS_FK_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
go

alter table PAYMENT_SERVICES
   drop constraint FK_P_SERVICE_TO_IMAGES
go

alter table PAYMENT_SERVICES
   add constraint FK_P_SERVICE_TO_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
go

alter table SERVICE_PROVIDERS
   drop constraint FK_S_PROVIDERS_TO_IMAGES
go

alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_TO_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
go

delete from IMAGES
    where not (ID in (
            select max(ID) ID from IMAGES
            group by MD5
        )
    )
go

drop index IDX_IMAGES_MD5
go

create unique index IDX_IMAGES_MD5 on IMAGES
(
    MD5 asc
)
go

-- Номер ревизии: 38886
-- Номер версии: 1.18
-- Комментарий: Выбор получателей для панели быстрой оплаты сотовой связи в разрезе ТБ (релиз 8.2)
create table QUICK_PAYMENT_PANELS(
    ID          INTEGER       not null,
    STATE       VARCHAR2(16)  not null,
    NAME        VARCHAR2(100) not null,
    START_DATE  TIMESTAMP,
    END_DATE    TIMESTAMP,   

constraint PK_QUICK_PAYMENT_PANELS primary key (ID)  
);

create table PANEL_BLOCKS(
    ID             INTEGER       not null,
    Q_P_PANEL_ID   INTEGER       not null,
    PROVIDER_ID    INTEGER       not null,    
    SHOW           CHAR(1)       not null,
    ORDER_INDEX    INTEGER       not null,     

constraint PANEL_BLOCKS primary key (ID),

constraint FK_PAN_BLCKS_TO_Q_P_PANELS foreign key (Q_P_PANEL_ID)
           references QUICK_PAYMENT_PANELS(ID) on delete cascade 
);

create table Q_P_PANELS_DEPARTMENTS(
    Q_P_PANEL_ID   INTEGER not null,
    DEPARTMENT_ID  INTEGER not null,

constraint PK_Q_P_PANELS_DEPARTMENTS primary key (Q_P_PANEL_ID, DEPARTMENT_ID),

constraint FK_Q_P_PANELS_DEP_TO_DEP foreign key (DEPARTMENT_ID)
           references DEPARTMENTS (ID) on delete cascade,
constraint FK_Q_P_PAN_DEP_TO_Q_P_PAN foreign key (Q_P_PANEL_ID)
      references QUICK_PAYMENT_PANELS (ID) on delete cascade
);

create sequence S_QUICK_PAYMENT_PANELS;
create sequence S_PANEL_BLOCKS;

create index "DXFK_PAN_BLCKS_TO_Q_P_PANELS" on PANEL_BLOCKS
(
   Q_P_PANEL_ID
);

create index "DXFK_Q_P_PANELS_DEP_TO_DEP" on Q_P_PANELS_DEPARTMENTS
(
   DEPARTMENT_ID
);

create index "DXFK_Q_P_PAN_DEP_TO_Q_P_PAN" on Q_P_PANELS_DEPARTMENTS
(
   Q_P_PANEL_ID
);


-- Номер ревизии: 38882
-- Номер версии: 1.18
-- Комментарий: BUG042457: Распадается связка услуг ПУ при прикреплении логотипа. 
--              На первом этапе мы добавляем столбец md5 и индекс на него.
--              Далее необходимо выполнить таск install.UpdateImagesHashKey
alter table IMAGES add MD5 varchar(32)
go

create index IDX_IMAGES_MD5 on IMAGES
(
    MD5 asc
)
go

alter table IMAGES_MESSAGES
   drop constraint FK_IMG_MSGS_FK_IMAGES
go

alter table IMAGES_MESSAGES
   add constraint FK_IMG_MSGS_FK_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
go

alter table PAYMENT_SERVICES
   drop constraint FK_P_SERVICE_TO_IMAGES
go

alter table PAYMENT_SERVICES
   add constraint FK_P_SERVICE_TO_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
go

alter table SERVICE_PROVIDERS
   drop constraint FK_S_PROVIDERS_TO_IMAGES
go

alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_TO_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
go


-- Номер ревизии: 38910
-- Номер версии: 1.18
-- Комментарий: добавление АТМ. 
ALTER TABLE PAYMENTFORMS
 ADD ATM_TRANSFORMATION CLOB;


-- Номер ревизии: 38984,38985
-- Номер версии: 1.18
-- Комментарий: CHG038646: Отказаться от создания новых адаптеров при заведении шинного биллинга.


alter table ADAPTERS add IS_ESB CHAR(1)   DEFAULT  '0'  NOT NULL;

alter table BILLINGS MODIFY EXTERNAL_ID varchar(115);

drop table EXTERNAL_SYSTEM_DEPARTMENTS;

drop sequence S_EXTERNAL_SYSTEM_DEPARTMENTS;

delete from TECHNOBREAKS;

alter table TECHNOBREAKS drop constraint FK_TECHNOBR_FK_TECHNO_EXTERNAL;

alter table TECHNOBREAKS
   add constraint FK_TECHNOBREAKS_TO_ADAPTERS foreign key (EXTERNAL_SYSTEM_ID)
      references ADAPTERS (ID);

alter table TECHNOBREAKS rename column EXTERNAL_SYSTEM_ID to ADAPTER_ID;

DECLARE
new_id_adapter INTEGER; -- новый идентификатор адаптера
id_adapter INTEGER; -- идентификатор адаптера
CURSOR adapterRows(adapterCode VARCHAR2) IS SELECT ID FROM ADAPTERS WHERE UUID = adapterCode; -- курсор для поиска адаптеров с указанным идентификатором
BEGIN
-- перебираем все записи в EXTERNAL_SYSTEM
FOR externalSystemRow IN (SELECT ID, NAME, ADAPTER_CODE, INTERACTION, SYSTEM_ID FROM EXTERNAL_SYSTEM) LOOP
IF(externalSystemRow.ADAPTER_CODE is not NULL) THEN
    -- ищем адаптер с таким кодом
    OPEN adapterRows(externalSystemRow.ADAPTER_CODE);
    FETCH adapterRows INTO id_adapter;
    -- если не нашли адаптер создаем
    IF(adapterRows%NOTFOUND) THEN
        SELECT S_ADAPTERS.nextval INTO new_id_adapter FROM DUAL;
        -- вставляем новый адаптер
        INSERT INTO ADAPTERS(ID, UUID, NAME, IS_ESB) VALUES(new_id_adapter, externalSystemRow.ADAPTER_CODE, externalSystemRow.NAME, '0');
        DBMS_OUTPUT.PUT_LINE('Создан новый адаптер с id='||new_id_adapter||' UUID='||externalSystemRow.ADAPTER_CODE||' и isESB=0');
    END IF;
    CLOSE adapterRows;
END IF;
IF(externalSystemRow.SYSTEM_ID is not NULL) THEN
    -- ищем адаптер с таким кодом
    OPEN adapterRows(externalSystemRow.SYSTEM_ID);
    FETCH adapterRows INTO id_adapter;
    -- если не нашли адаптер создаем
    IF(adapterRows%NOTFOUND) THEN
        SELECT S_ADAPTERS.nextval INTO new_id_adapter FROM DUAL; 
        -- вставляем новый адаптер
        INSERT INTO ADAPTERS(ID, UUID, NAME, IS_ESB) VALUES(new_id_adapter, externalSystemRow.SYSTEM_ID, externalSystemRow.NAME, '1');
        DBMS_OUTPUT.PUT_LINE('Создан новый адаптер с id='||new_id_adapter||' UUID='||externalSystemRow.SYSTEM_ID||' и isESB=1');
    END IF;    
    CLOSE adapterRows;
END IF;
END LOOP;
END;



drop table EXTERNAL_SYSTEM;

drop sequence S_EXTERNAL_SYSTEM;


-- Номер ревизии: 39114
-- Номер версии: 1.18
-- Комментарий: Выбор получателей для панели быстрой оплаты сотовой связи в разрезе ТБ (релиз 8.2)

alter table Panel_Blocks add PROVIDER_NAME varchar(25) not null;

ALTER TABLE Q_P_PANELS_DEPARTMENTS
ADD CONSTRAINT  AK_DEPARTMENT_ID  UNIQUE (DEPARTMENT_ID);

alter table QUICK_PAYMENT_PANELS 
add constraint AK_NAME_UNIQUE UNIQUE (NAME);



-- Номер ревизии: 39171
-- Номер версии: 1.18
-- Комментарий: Выбор получателей для панели быстрой оплаты сотовой связи в разрезе ТБ (релиз 8.2)

alter table Panel_Blocks modify PROVIDER_NAME varchar2(25);


-- Номер ревизии: 39182
-- Номер версии: 1.18
-- Комментарий: BUG043166: Оплата по старым шаблонам требует подтверждения смс
delete from DOCUMENT_EXTENDED_FIELDS def where
    exists (
        select 1 from BUSINESS_DOCUMENTS bd 
        where def.PAYMENT_ID = bd.ID and bd.KIND = 'P'
    ) and
    def.NAME = 'receiver-kpp' and def.VALUE is null
go
    
-- Номер ревизии: 39183
-- Номер версии: 1.18
-- Комментарий: CHG042451: Запретить более одной сессии одного и того же клиента 
alter table LOGINS add LOGON_SESSION_ID VARCHAR2(64)
go

-- Номер ревизии: 39224
-- Номер версии: 1.18
-- Комментарий: Выбор получателей для панели быстрой оплаты сотовой связи в разрезе ТБ (релиз 8.2)

alter table panel_blocks modify Q_P_PANEL_ID null;

-- Номер ревизии: 39271
-- Номер версии: 1.18
-- Комментарий: CHG042451: Запретить более одной сессии одного и того же клиента 
alter table LOGINS drop column LOGON_SESSION_ID
go

create table LOGINS_SESSION_IDS (
   LOGIN_ID               INTEGER        not null,
   APPLICATION_TYPE VARCHAR2(16) not null,
   SESSION_ID  VARCHAR2(64) not null,
   constraint PK_LOGINS_SESSION_IDS primary key (LOGIN_ID, APPLICATION_TYPE)
)
go

-- Номер ревизии: 39270
-- Номер версии: 1.18
-- CHG043223: Добавить поставщикам услуг признак возможности совершения оплаты через УС  
ALTER TABLE SERVICE_PROVIDERS
ADD (ATM_AVAILABLE CHAR(1) DEFAULT 0 NOT NULL)
go

-- Номер ревизии: 39352
-- Номер версии: 1.18
-- BUG043368: Ошибка при входе клиента.  
ALTER TABLE USERS MODIFY(CLIENT_ID VARCHAR2(100))
go

-- Номер ревизии: 39480
-- Номер версии: 1.18
-- CHG043282: Изменение текста всплывающей подсказки статуса по таймаутному платежу.
alter table BUSINESS_DOCUMENTS modify STATE_DESCRIPTION varchar2(265)
;

-- Номер ревизии: 39477
-- Номер версии: 1.18
-- Доработка подразделений: поле возможности заказа омс в офисе
ALTER TABLE  DEPARTMENTS
ADD (IS_OPEN_IMA_OFFICE CHAR(1) DEFAULT 0 NOT NULL);

-- Номер ревизии: 39549
-- Номер версии: 1.18
-- Доработка подразделений: Доработка справочника продуктов в ПФП
ALTER TABLE PFP_INSURANCE_PRODUCTS ADD MIN_INCOME NUMBER(7,2);
ALTER TABLE PFP_INSURANCE_PRODUCTS ADD MAX_INCOME NUMBER(7,2);
ALTER TABLE PFP_INSURANCE_PRODUCTS ADD DEFAULT_INCOME NUMBER(7,2);

ALTER TABLE PFP_PRODUCTS ADD MIN_INCOME NUMBER(7,2);
ALTER TABLE PFP_PRODUCTS ADD MAX_INCOME NUMBER(7,2);
ALTER TABLE PFP_PRODUCTS ADD DEFAULT_INCOME NUMBER(7,2);

-- Номер ревизии: 39532
-- Номер версии: 1.18
-- Комментарий: Доработка входа клиента: поиск клиента по карте Сбера
ALTER TABLE LOGINS ADD LAST_LOGON_VISITING_MODE VARCHAR2(40);

-- Номер ревизии: 39678
-- Номер версии: 1.18
-- Комментарий: Справочник ОМС (для открытия)
create table IMAPRODUCT  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(255)                   not null,
   TYPE                 INTEGER                         not null,
   SUBTYPE              INTEGER                         not null,
   CURRENCY             CHAR(3)                         not null,
   CONTRACT_TEMPLATE    CLOB                            not null,
   constraint PK_IMAPRODUCT primary key (ID)
);

create sequence S_IMAPRODUCT;


-- Номер ревизии: 39480
-- Номер версии: 1.18
-- Комментарий: Вторая часть скприпта, обновляющая статусы старых докуметов, для запроса CHG043282 Изменение текста всплывающей подсказки статуса по таймаутному платежу
UPDATE BUSINESS_DOCUMENTS SET STATE_DESCRIPTION = 'Статус документа не определен. Пожалуйста, обратитесь в контактный центр банка.' WHERE IS_LONG_OFFER = '1' AND STATE_CODE = 'UNKNOW';

UPDATE BUSINESS_DOCUMENTS SET STATE_DESCRIPTION = 'Статус документа не определен. Пожалуйста, проверьте списание суммы со счета или с карты по данной операции. Если средства не списаны, то при необходимости повторите операцию. Если средства списаны, обратитесь в Контактный центр банка для уточнения информации.' WHERE IS_LONG_OFFER = '0' AND STATE_CODE = 'UNKNOW';



-- Номер ревизии: 39845
-- Номер версии: 1.18
-- Комментарий: задача Visa money transfer и MasterCard money send. Тип CardsTransfer разделен на два ExternalCardsTransfer и InternalCardsTransfer, поэтому требуется добавить запись в PAYMENTS_GROUP_RISK
DECLARE
new_id_paymentGroupRisk INTEGER; -- новый идентификатор
CURSOR adapterRows(adapterCode VARCHAR2) IS SELECT ID FROM ADAPTERS WHERE UUID = adapterCode; -- курсор для поиска адаптеров с указанным идентификатором
BEGIN
FOR paymentGroupRiskRow IN (SELECT ID, PAYMENT_TYPE, GROUP_RISK, DEPARTMENT_ID FROM PAYMENTS_GROUP_RISK WHERE PAYMENT_TYPE = 'CardsTransfer') LOOP
    SELECT S_PAYMENTS_GROUP_RISK.nextval INTO new_id_paymentGroupRisk FROM DUAL;
    -- обновляем тип платежа у существующей записи
    UPDATE PAYMENTS_GROUP_RISK SET PAYMENT_TYPE = 'InternalCardsTransfer' WHERE ID = paymentGroupRiskRow.ID;
    -- вставляем запись с новым типом и самой низкой группой риска
    INSERT INTO PAYMENTS_GROUP_RISK(ID, PAYMENT_TYPE, GROUP_RISK, DEPARTMENT_ID) VALUES(new_id_paymentGroupRisk, 'ExternalCardsTransfer', 'LOWEST', paymentGroupRiskRow.DEPARTMENT_ID);
END LOOP;
END;

-- Номер ревизии: 39864
-- Номер версии: 1.18
-- Комментарий: задача Создание функционала групп риска
create sequence S_GROUPS_RISK;

create table GROUPS_RISK
(
ID INTEGER                          not null,
NAME VARCHAR(100)                   not null,
DEPARTMENT_ID    INTEGER            not null,
constraint PK_ID primary key (ID),
constraint UNIQUE_NAME unique (NAME),

constraint FK_GROUPS_RISK_FK_DEPARTMENT foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)


);


-- Номер ревизии: 31944
-- Номер версии: 1.18
-- Комментарий: Номер ревизии - Реализация новой схемы создания билинговых АП.Конвертация для платежей созданных до этой ревизии.
BUG043381: Клиент не может войти во вкладку платежи и операции.

update BUSINESS_DOCUMENTS set ACCESS_AUTOPAY_SCHEMES = '<entity-list name="auto-sub-payment-types"></entity-list>' where KIND = 'P' and ACCESS_AUTOPAY_SCHEMES IS NULL ;


-- Номер ревизии: 39899
-- Номер версии: 1.18
-- Комментарий: Добавить притвязку иконок целей на форму "Цели и средства". 
ALTER TABLE PERSON_TARGET ADD IMAGE_ID INTEGER
go

ALTER TABLE PERSON_TARGET  
ADD CONSTRAINT FK_PERSON_TARGET_TO_IMAGES FOREIGN KEY (IMAGE_ID)
    REFERENCES  IMAGES(ID) 
go


-- Номер ревизии: 40017
-- Номер версии: 1.18
-- Комментарий: Добавить поле IS_BAR_SUPPORTED в SERVICE_PROVIDERS
ALTER TABLE SERVICE_PROVIDERS
    ADD ( IS_BAR_SUPPORTED CHAR(1) DEFAULT '0' NOT NULL ) 

-- Номер ревизии: 40026
-- Номер версии: 1.18
-- Комментарий: Отображение номера телефона на панели быстрой оплаты
alter table PANEL_BLOCKS add FIELD_NAME varchar2(40);
alter table PANEL_BLOCKS modify PROVIDER_NAME varchar2(25) null;

-- Номер ревизии: 40142
-- Номер версии: 1.18
-- Комментарий: Доработка функционала суточных кумулятивных лимитов по группам риска
-- СКРИПТ НЕ АКТУАЛЕН. ЕГО ЗАМЕНЯЕТ СКРИПТ ДЛЯ РЕВИИЗИИ 40258

alter table LIMITS drop column GROUP_RISK;
alter table LIMITS drop column TYPE;


alter table LIMITS add (GROUP_RISK_ID integer);
alter table LIMITS add constraint FK_LIMITS_TO_GROUPS_RISK foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID) on delete cascade 

alter table LIMITS add (TYPE varchar2(12) not null);
alter table LIMITS add (OPERATION_TYPE varchar2(30) not null);
alter table LIMITS add (RESTRICTION_TYPE varchar2(16) not null);
alter table LIMITS add (OPERATION_COUNT integer);


alter table LIMITS MODIFY AMOUNT null;
alter table LIMITS MODIFY CURRENCY null;


alter table SERVICE_PROVIDERS drop column GENERAL_GROUP_RISK;
alter table SERVICE_PROVIDERS drop column MOBILE_GROUP_RISK;

alter table SERVICE_PROVIDERS add (GROUP_RISK_ID integer);
alter table SERVICE_PROVIDERS add constraint FK_S_PROVIDERS_TO_GROUPS_RISK foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID);

-- Номер ревизии: 40108
-- Номер версии: 1.18
-- Комментарий: Отображение номера телефона на панели быстрой оплаты(Доработки: Поле сумма)(БД)

alter table PANEL_BLOCKS add FIELD_SUMM_NAME varchar2(40);
alter table PANEL_BLOCKS add SUMM NUMBER(15,4);

--Номер ревизии: 40173
--Номер версии: 1.18
--Комментарий: Новые поля в профиле клиента ( код сегмента, к которому относят клиента; код тарифного плана; дата подключения тарифного плана; номер менеджера)
alter table USERS add SEGMENT_CODE VARCHAR2(20);
alter table USERS add TARIF_PLAN_CODE VARCHAR2(20);
alter table USERS add TARIF_PLAN_CONNECT_DATE DATE;
alter table USERS add MANAGER_ID INTEGER;

--Номер ревизии: 40204
--Номер версии: 1.18
--Комментарий: Добавление доходности продукта на форму добавление продукта
ALTER TABLE PFP_BASE_PRODUCT ADD PRODUCT_INCOME NUMBER(7,2)
go

--Номер ревизии: 40227
--Номер версии: 1.18
--Комментарий: заявка на подключение к МБ
/*==============================================================*/
/* Table: MB_REGISTRATION_CLAIMS                                */
/*==============================================================*/
create table MB_REGISTRATION_CLAIMS  (
   ID                   INTEGER                         not null,
   USER_ID              INTEGER,
   "DATE"               TIMESTAMP                       not null,
   TARIFF               CHAR                            not null,
   DEPARTMENT_TB        VARCHAR2(4)                     not null,
   PHONE_NUMBER         VARCHAR2(32)                    not null,
   CARD_NUMBER          VARCHAR2(32)                    not null,
   constraint PK_MB_REGISTRATION_CLAIMS primary key (ID)
)

;

create sequence S_MB_REGISTRATION_CLAIMS
;

comment on column MB_REGISTRATION_CLAIMS.USER_ID is
'Идентификатор клиента, оставившего заявку'
;

comment on column MB_REGISTRATION_CLAIMS."DATE" is
'Дата подключения'
;

comment on column MB_REGISTRATION_CLAIMS.DEPARTMENT_TB is
'ТБ клиента, оставившего заявку на подключение'
;

comment on column MB_REGISTRATION_CLAIMS.PHONE_NUMBER is
'Номер телефона в заявке на подключение'
;

comment on column MB_REGISTRATION_CLAIMS.CARD_NUMBER is
'Номер карты в заявке на подключение'
;

/*==============================================================*/
/* Index: IDX_MB_REGCLAIM_DATE                                 */
/*==============================================================*/
create index IDX_MB_REGCLAIM_DATE on MB_REGISTRATION_CLAIMS (
   "DATE" ASC
)
;

create index "DXFK_MB_REGCLAIM_USER" on MB_REGISTRATION_CLAIMS
(
   USER_ID
)
;

alter table MB_REGISTRATION_CLAIMS
   add constraint FK_MB_REGCLAIM_USER foreign key (USER_ID)
      references USERS (ID)
      on delete set null
;


-- Номер ревизии: 40258
-- Номер версии: 1.18
-- Комментарий: Доработка функционала суточных кумулятивных лимитов по группам риска
-- ЗАМЕНЯЕТ СКРИПТ ДЛЯ РЕВИИЗИИ 40142

--добавление новых связей
alter table LIMITS add (GROUP_RISK_ID integer);
alter table LIMITS add constraint FK_LIMITS_TO_GROUPS_RISK foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID) on delete cascade 

alter table SERVICE_PROVIDERS add (GROUP_RISK_ID integer);
alter table SERVICE_PROVIDERS add constraint FK_S_PROVIDERS_TO_GROUPS_RISK foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID);

-- создание групп риска
DECLARE
new_id_groupRisk INTEGER; -- новый идентификатор
num_group_risk INTEGER;
BEGIN
num_group_risk :=0;
FOR departmentRow IN (SELECT ID, NAME, PARENT_DEPARTMENT  FROM DEPARTMENTS WHERE PARENT_DEPARTMENT is null) LOOP
    
    --создаем высокую группу риска
        num_group_risk := num_group_risk + 1;
        SELECT S_GROUPS_RISK.nextval INTO new_id_groupRisk FROM DUAL;
        -- вставляем запись с новой группой риска
        INSERT INTO GROUPS_RISK(ID, NAME , DEPARTMENT_ID) VALUES(new_id_groupRisk,CONCAT(CONCAT('Группа ',num_group_risk),' (высокая группа риска)'), departmentRow.ID);
    
        -- обновляем поставщиков услуг
        UPDATE SERVICE_PROVIDERS SET GROUP_RISK_ID = new_id_groupRisk WHERE GENERAL_GROUP_RISK = 'HIGH' AND departmentRow.ID in (
            SELECT dep.id id
            FROM DEPARTMENTS dep
            START WITH dep.ID = DEPARTMENT_ID 
            CONNECT BY PRIOR dep.parent_department = dep.id);
        -- обновляем лимиты
        UPDATE LIMITS SET GROUP_RISK_ID = new_id_groupRisk WHERE GROUP_RISK = 'HIGH' AND DEPARTMENT_ID = departmentRow.ID;


    --создаем среднюю группу риска
        num_group_risk := num_group_risk + 1;
        SELECT S_GROUPS_RISK.nextval INTO new_id_groupRisk FROM DUAL;
        -- вставляем запись с новой группой риска
        INSERT INTO GROUPS_RISK(ID, NAME , DEPARTMENT_ID) VALUES(new_id_groupRisk, CONCAT(CONCAT('Группа ',num_group_risk),' (средняя группа риска)'), departmentRow.ID);
    
        -- обновляем поставщиков услуг
        UPDATE SERVICE_PROVIDERS SET GROUP_RISK_ID = new_id_groupRisk WHERE GENERAL_GROUP_RISK = 'MEDIUM' AND departmentRow.ID in (
            SELECT dep.id id
            FROM DEPARTMENTS dep
            START WITH dep.ID = DEPARTMENT_ID 
            CONNECT BY PRIOR dep.parent_department = dep.id);
        -- обновляем лимиты
        UPDATE LIMITS SET GROUP_RISK_ID = new_id_groupRisk WHERE GROUP_RISK = 'MEDIUM' AND DEPARTMENT_ID = departmentRow.ID;


    --создаем низкую группу риска
        num_group_risk := num_group_risk + 1;
        SELECT S_GROUPS_RISK.nextval INTO new_id_groupRisk FROM DUAL;
        -- вставляем запись с новой группой риска
        INSERT INTO GROUPS_RISK(ID, NAME , DEPARTMENT_ID) VALUES(new_id_groupRisk, CONCAT(CONCAT('Группа ',num_group_risk),' (низкая группа риска)'), departmentRow.ID);
    
        -- обновляем поставщиков услуг
        UPDATE SERVICE_PROVIDERS SET GROUP_RISK_ID = new_id_groupRisk WHERE GENERAL_GROUP_RISK = 'LOW' AND departmentRow.ID in (
            SELECT dep.id id
            FROM DEPARTMENTS dep
            START WITH dep.ID = DEPARTMENT_ID 
            CONNECT BY PRIOR dep.parent_department = dep.id);
        -- обновляем лимиты
        UPDATE LIMITS SET GROUP_RISK_ID = new_id_groupRisk WHERE GROUP_RISK = 'LOW' AND DEPARTMENT_ID = departmentRow.ID;

    --создаем самую низкую группу риска
        num_group_risk := num_group_risk + 1;
        SELECT S_GROUPS_RISK.nextval INTO new_id_groupRisk FROM DUAL;
        -- вставляем запись с новой группой риска
        INSERT INTO GROUPS_RISK(ID, NAME , DEPARTMENT_ID) VALUES(new_id_groupRisk, CONCAT(CONCAT('Группа ',num_group_risk),' (самая низкая группа риска)'), departmentRow.ID);
    
        -- обновляем поставщиков услуг
        UPDATE SERVICE_PROVIDERS SET GROUP_RISK_ID = new_id_groupRisk WHERE GENERAL_GROUP_RISK = 'LOWEST' AND departmentRow.ID in (
            SELECT dep.id id
            FROM DEPARTMENTS dep
            START WITH dep.ID = DEPARTMENT_ID 
            CONNECT BY PRIOR dep.parent_department = dep.id);
        -- обновляем лимиты
        UPDATE LIMITS SET GROUP_RISK_ID = new_id_groupRisk WHERE GROUP_RISK = 'LOWEST' AND DEPARTMENT_ID = departmentRow.ID;


    --создаем группу риска "СЧЕТ - Социальная Карта"
        num_group_risk := num_group_risk + 1;
        SELECT S_GROUPS_RISK.nextval INTO new_id_groupRisk FROM DUAL;
        -- вставляем запись с новой группой риска
        INSERT INTO GROUPS_RISK(ID, NAME , DEPARTMENT_ID) VALUES(new_id_groupRisk, CONCAT(CONCAT('Группа ',num_group_risk),' (для операций "счет - социальная карта")'), departmentRow.ID);
    
        -- обновляем лимиты
        UPDATE LIMITS SET GROUP_RISK_ID = new_id_groupRisk WHERE GROUP_RISK = 'SOCCARD' AND DEPARTMENT_ID = departmentRow.ID;
END LOOP;
END;


--добавление новых
alter table LIMITS add (OPERATION_TYPE varchar2(30) null);
alter table LIMITS add (RESTRICTION_TYPE varchar2(16) null);
alter table LIMITS add (OPERATION_COUNT integer null);


--изменение
alter table LIMITS MODIFY AMOUNT null;
alter table LIMITS MODIFY CURRENCY null;
alter table LIMITS MODIFY TYPE varchar2(12) not null;

--заполнение данных по лимитам
UPDATE LIMITS SET TYPE = 'GROUP_RISK'  WHERE GROUP_RISK = 'SOCCARD' OR GROUP_RISK = 'LOWEST' OR GROUP_RISK = 'LOW' OR GROUP_RISK = 'MEDIUM' OR GROUP_RISK = 'HIGH';
UPDATE LIMITS SET TYPE = 'OBSTRUCTION'  WHERE GROUP_RISK = 'GENERAL';
UPDATE LIMITS SET TYPE = 'IMSI'  WHERE GROUP_RISK = 'IMSI';

UPDATE LIMITS SET OPERATION_TYPE = 'NEED_ADDITIONAL_CONFIRN'  WHERE TYPE = 'GROUP_RISK';
UPDATE LIMITS SET OPERATION_TYPE = 'READ_SIM'  WHERE TYPE = 'IMSI';
UPDATE LIMITS SET OPERATION_TYPE = 'IMPOSSIBLE_PERFORM_OPERATION'  WHERE TYPE = 'OBSTRUCTION' OR GROUP_RISK = 'SOCCARD';

UPDATE LIMITS SET RESTRICTION_TYPE = 'AMOUNT';

-- создание дополнительного лимита для лимитов со "СЧЕТ - Социальная Карта". нельзя одновременно на количество операций и сумму
DECLARE
new_id_limit INTEGER; -- новый идентификатор
BEGIN
FOR limitRow IN (SELECT ID, CREATION_DATE, START_DATE, GROUP_RISK_ID, TYPE,OPERATION_TYPE, DEPARTMENT_ID,GROUP_RISK FROM LIMITS WHERE GROUP_RISK = 'SOCCARD') LOOP 

    SELECT S_LIMITS.nextval INTO new_id_limit FROM DUAL;
        -- вставляем запись с дополнительным лимитом
    INSERT INTO LIMITS(ID, CREATION_DATE, START_DATE, GROUP_RISK_ID, OPERATION_TYPE, TYPE, DEPARTMENT_ID,GROUP_RISK, OPERATION_COUNT ,RESTRICTION_TYPE) 
    VALUES(new_id_limit,limitRow.CREATION_DATE, limitRow.START_DATE, limitRow.GROUP_RISK_ID, limitRow.OPERATION_TYPE, limitRow.TYPE, limitRow.DEPARTMENT_ID,'SOCCARD_DOP', '2','OPERATION_COUNT'); 
END LOOP;
END;

alter table LIMITS MODIFY OPERATION_TYPE not null;
alter table LIMITS MODIFY RESTRICTION_TYPE not null;
alter table SERVICE_PROVIDERS MODIFY GROUP_RISK_ID not null;

--удаление старых записей
alter table LIMITS drop column GROUP_RISK;

alter table SERVICE_PROVIDERS drop column GENERAL_GROUP_RISK;
alter table SERVICE_PROVIDERS drop column MOBILE_GROUP_RISK;



-- Номер ревизии: 40324
-- Номер версии: 1.18
-- Комментарий: Реализовать загрузку файла «Описание расчетов» в АРМ сотрудника

create table FILES(
    ID INTEGER not null,
    FILE_NAME  varchar2(256),
    DATA BLOB,
    constraint PK_FILES primary key (ID)    
);

create sequence S_FILES;

--Номер ревизии: 40242
--Номер версии: 1.18
--Комментарий:  линк для хранения информации о программе лояльности
/*==============================================================*/
/* Table: LOYALTY_PROGRAM_LINKS                                 */
/*==============================================================*/
create table LOYALTY_PROGRAM_LINKS  (
   ID                   INTEGER                         not null,
   EXTERNAL_ID          VARCHAR2(80)                    not null,
   LOGIN_ID             INTEGER                         not null,
   SHOW_IN_MAIN         CHAR(1)                         not null,
   SHOW_OPERATIONS      CHAR(1)                         not null,
   constraint PK_LOYALTY_PROGRAM_LINKS primary key (ID),
   constraint AK_LOYALTY_LOGIN unique (LOGIN_ID)
);

create sequence S_LOYALTY_PROGRAM_LINKS;

--Номер ревизии: 40333
--Номер версии: 1.18
--Комментарий:  линк для хранения информации о программе лояльности(докоммит)
alter table LOYALTY_PROGRAM_LINKS add STATE VARCHAR2(10) not null;


--Номер ревизии: 40435
--Номер версии: 1.18
--Комментарий: журнал операций для лимитов
create table TRANSACTIONS_JOURNAL  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER,
   JOURNAL_TYPE         VARCHAR(32),
   APPLICATION          VARCHAR(32),
   OPERATION_ID         INTEGER,
   OPERATION_ALLOWED    VARCHAR(32),
   OP_AMOUNT            NUMBER(10,2),
   OP_AMOUNT_CURRENCY   CHAR(3),
   TOTAL_AMOUNT         NUMBER(10,2),
   TOTAL_AMOUNT_CURRENCY CHAR(3),
   OPERATION_TYPE       VARCHAR(32),
   constraint PK_TRANSACTIONS_JOURNAL primary key (ID)
);

create sequence S_TRANSACTIONS_JOURNAL;


--Номер ревизии: 40526
--Номер версии: 1.18
--Комментарий: удаляем зависимость групп риска от подразделений

alter table groups_risk drop constraint FK_GROUPS_RISK_FK_DEPARTMENT;
alter table groups_risk drop column DEPARTMENT_ID;


--Номер ревизии: 40588
--Номер версии: 1.18
--Комментарий: Реализовать настройку отображения продуктов в зависимости от сегмента клиента
/*==============================================================*/
/* Table: PFP_SP_TARGET_GROUPS_BUNDLE                           */
/*==============================================================*/
create table PFP_SP_TARGET_GROUPS_BUNDLE  (
   PRODUCT_ID           INTEGER                         not null,
   SEGMENT_CODE         VARCHAR2(20)                    not null,
   constraint PK_PFP_SP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)

go

/*==============================================================*/
/* Table: PFP_CP_TARGET_GROUPS_BUNDLE                           */
/*==============================================================*/
create table PFP_CP_TARGET_GROUPS_BUNDLE  (
   PRODUCT_ID           INTEGER                         not null,
   SEGMENT_CODE         VARCHAR2(20)                    not null,
   constraint PK_PFP_CP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)

go

/*==============================================================*/
/* Table: PFP_IP_TARGET_GROUPS_BUNDLE                           */
/*==============================================================*/
create table PFP_IP_TARGET_GROUPS_BUNDLE  (
   PRODUCT_ID           INTEGER                         not null,
   SEGMENT_CODE         VARCHAR2(20)                    not null,
   constraint PK_PFP_IP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)

go

--Номер ревизии: 40723
--Номер версии: 1.18
--Комментарий: делаем возможность задавать поставщикам пустую группу риска

alter table service_providers modify (GROUP_RISK_ID null);


--Номер ревизии: 40866
--Номер версии: 1.18
--Комментарий: добавляем всем тербанкам лимит - мобильный кошелек

declare
new_id_limit INTEGER; --новый идентификатор
begin
for departmentRow in 
(
            select id from departments where PARENT_DEPARTMENT is null 
            minus
            select id from departments where ID in 
            (
            select DEPARTMENT_ID from LIMITS where TYPE = 'USER_POUCH'
            )
)
loop
            select S_LIMITS.nextval INTO new_id_limit from dual;
            insert into limits (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, TYPE, DEPARTMENT_ID) values (new_id_limit, sysdate, sysdate,0,'RUB','AMOUNT','IMPOSSIBLE_PERFORM_OPERATION','USER_POUCH',departmentRow.ID);
end loop;
end ;


--Номер ревизии: 40934
--Номер версии: 1.18
--Комментарий: Доработка стратегий подтверждения (лимиты)
delete from PAYMENTS_GROUP_RISK
go

alter table PAYMENTS_GROUP_RISK drop column PAYMENT_TYPE
go

alter table PAYMENTS_GROUP_RISK drop column GROUP_RISK
go

alter table PAYMENTS_GROUP_RISK add KIND varchar2(3) not null
go

alter table PAYMENTS_GROUP_RISK add GROUP_RISK_ID integer not null
go

create index "DXFK_TO_PAY_GR_RSK_GROUP_RISK" on PAYMENTS_GROUP_RISK
(
   GROUP_RISK_ID
)
go

alter table PAYMENTS_GROUP_RISK add constraint FK_TO_PAY_GR_RSK_GROUP_RISK foreign key (GROUP_RISK_ID) references GROUPS_RISK(ID)
go

alter table TRANSACTIONS_JOURNAL add CREATION_DATE date
go

alter table TRANSACTIONS_JOURNAL drop column TOTAL_AMOUNT
go

alter table TRANSACTIONS_JOURNAL drop column TOTAL_AMOUNT_CURRENCY
go

alter table TRANSACTIONS_JOURNAL drop column OPERATION_TYPE
go


--Номер ревизии: 40947
--Номер версии: 1.18
--Комментарий: CHG044693: Реализовать настройку графической подсказки на форме оплаты ПУ 
ALTER TABLE SERVICE_PROVIDERS add IMAGE_HELP_ID INTEGER
go

alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_HELP_TO_IMAGES foreign key (IMAGE_HELP_ID)
      references IMAGES (ID)
go

alter table IMAGES modify IMAGE_HELP varchar2(100)
go

create index "DXFK_S_PROVIDERS_HELP_TO_IMAGE" on SERVICE_PROVIDERS
(
   IMAGE_HELP_ID
)
go


--Номер ревизии: 40969
--Номер версии: 1.18
--Комментарий: BUG044681: Исключение при создании суточных лимитов
alter table LIMITS modify TYPE varchar2(33)
go

--Номер ревизии: 40995
--Номер версии: 1.18
--Комментарий: BUG044973: Очищается поле "Желаемая стоимость портфеля" на шаге "Финансовый прогноз" (ПФП) 
alter table PERSONAL_FINANCE_PROFILE add PLAN_DATE TIMESTAMP
go

alter table PERSONAL_FINANCE_PROFILE add PLAN_MONEY_AMOUNT NUMBER(19,4)
go

alter table PERSONAL_FINANCE_PROFILE add PLAN_MONEY_CURRENCY CHAR(3)
go



--Номер ревизии: 41109
--Номер версии: 1.18
--Комментарий: Доработка анкеты сотрудника: ввод данных персонального менеджера(ПФП) 

alter table EMPLOYEES add MANAGER_PHONE varchar2(20);
alter table EMPLOYEES add MANAGER_EMAIL varchar2(40);
alter table EMPLOYEES add MANAGER_LEAD_EMAIL varchar2(40);



--Номер ревизии: 41194
--Номер версии: 1.18
--Комментарий: Обеспечение поддержки старых фин. планирований в ПФП-2
-- удаляем незавершенные планирования
DELETE FROM PFP_BASE_PRODUCT WHERE PORTFOLIO_PRODUCT_ID IN (SELECT ID FROM PORTFOLIO_PRODUCT WHERE PORTFOLIO_ID IN (SELECT ID FROM PERSON_PORTFOLIO WHERE PERSON_FINANCE_PROFILE_ID IN (SELECT ID FROM PERSONAL_FINANCE_PROFILE WHERE STATE_CODE <> 'COMPLITE')))
go

DELETE FROM PORTFOLIO_PRODUCT WHERE PORTFOLIO_ID IN (SELECT ID FROM PERSON_PORTFOLIO WHERE PERSON_FINANCE_PROFILE_ID IN (SELECT ID FROM PERSONAL_FINANCE_PROFILE WHERE STATE_CODE <> 'COMPLITE'))
go

DELETE FROM PERSON_PORTFOLIO WHERE PERSON_FINANCE_PROFILE_ID IN (SELECT ID FROM PERSONAL_FINANCE_PROFILE WHERE STATE_CODE <> 'COMPLITE')
go

DELETE FROM PERSON_TARGET WHERE PERSON_FINANCE_PROFILE_ID IN (SELECT ID FROM PERSONAL_FINANCE_PROFILE WHERE STATE_CODE <> 'COMPLITE')
go

DELETE FROM PERSON_PROFILE_RP_QUESTION WHERE PROFILE_ID in (SELECT ID FROM PERSONAL_FINANCE_PROFILE WHERE STATE_CODE <> 'COMPLITE')
go

DELETE FROM PERSONAL_FINANCE_PROFILE WHERE STATE_CODE <> 'COMPLITE'
go

-- обновляем доходность для неизвестных продуктов
update PFP_PRODUCTS set DEFAULT_INCOME=5 WHERE DEFAULT_INCOME is null
go 

update PFP_INSURANCE_PRODUCTS set DEFAULT_INCOME=5 WHERE DEFAULT_INCOME is null
go

-- обновляем доходность для продуктов, добавленных в портфели
UPDATE PFP_BASE_PRODUCT clientProduct 
SET clientProduct.PRODUCT_INCOME = (SELECT incomes.income 
                                      FROM (SELECT  ID id, 
                                                  DEFAULT_INCOME income, 
                                                  CASE
                                                        WHEN TYPE = 'A' THEN 'account'
                                                        WHEN TYPE = 'F' THEN 'fund'
                                                        WHEN TYPE = 'M' THEN 'IMA'
                                                   END type
                                            FROM PFP_PRODUCTS
                                            UNION ALL
                                            SELECT ID id, DEFAULT_INCOME income, 'insurance' type
                                            FROM PFP_INSURANCE_PRODUCTS) incomes
                                       WHERE incomes.id = clientProduct.DICTIONARY_PRODUCT_ID and incomes.type = clientProduct.PRODUCT_TYPE)
WHERE clientProduct.PRODUCT_INCOME is NULL
go
 
UPDATE PFP_BASE_PRODUCT
SET PRODUCT_INCOME = 5
WHERE PRODUCT_INCOME is NULL
go
 
-- меняем статус пройденных планирований
UPDATE PERSONAL_FINANCE_PROFILE
SET STATE_CODE = 'COMPLITE_OLD'
WHERE STATE_CODE = 'COMPLITE'
go
 
-- задаем дату формирования плана
UPDATE PERSONAL_FINANCE_PROFILE SET PLAN_DATE = (TO_DATE('01'||to_char(ceil(to_number(TO_CHAR(EXECUTION_DATE, 'MM')) / 3 - 1) * 3 + 1, '09') || to_char(to_number(TO_CHAR(EXECUTION_DATE, 'YY')) + 5, '9999') || ' 00:00:00', 'DDMMYY HH24:MI:SS'))
WHERE PLAN_DATE IS NULL AND EXECUTION_DATE IS NOT NULL
go

--Номер ревизии: 41223
--Номер версии: 1.18
--Комментарий: Подключение услуги "Мобильный Банк": статус заявки
ALTER TABLE MB_REGISTRATION_CLAIMS
ADD (COMPLETED CHAR(1) DEFAULT '0');




--Номер ревизии 41245
--Номер версии: 1.18
--Комментарий:  Новые поля в профиле клиента (меняем тип поля "номер менеджера" с integer на varchar)
update users set manager_id = null;
alter table users modify MANAGER_ID varchar2(13);




--Номер ревизии: 41275
--Номер версии: 1.18
--Комментарий: Подключение услуги "Мобильный Банк": заявка: ссылка_на_пользователя -> ссылка_на_логин
DELETE FROM MB_REGISTRATION_CLAIMS
;

ALTER TABLE MB_REGISTRATION_CLAIMS
DROP CONSTRAINT FK_MB_REGCLAIM_USER
;

DROP INDEX DXFK_MB_REGCLAIM_USER
;

ALTER TABLE MB_REGISTRATION_CLAIMS
RENAME COLUMN USER_ID TO LOGIN_ID
;

ALTER TABLE MB_REGISTRATION_CLAIMS
MODIFY(LOGIN_ID  NOT NULL);

COMMENT ON COLUMN MB_REGISTRATION_CLAIMS.LOGIN_ID IS
'Идентификатор логина пользователя, оставившего заявку'
;

CREATE INDEX "DXFK_MB_REGCLAIM_LOGIN" on MB_REGISTRATION_CLAIMS
(
   LOGIN_ID
)
;

ALTER TABLE MB_REGISTRATION_CLAIMS
   ADD CONSTRAINT FK_MB_REGCLAIM_LOGIN FOREIGN KEY (LOGIN_ID)
      REFERENCES LOGINS (ID)
;

ALTER TABLE MB_REGISTRATION_CLAIMS
MODIFY(COMPLETED  NOT NULL)
;
ALTER TABLE MB_REGISTRATION_CLAIMS
   ADD CONSTRAINT FK_MB_REGCLAIM_LOGIN FOREIGN KEY (LOGIN_ID)
      REFERENCES LOGINS (ID)
;

--Номер ревизии: 41278
--Номер версии: 1.18
--Комментарий: ПФП: Отображение рекомендаций по распределению средств. Ч3(изменение доходности портфелей) 
alter table PERSON_PORTFOLIO add WANTED_INCOME NUMBER(6,1)
go

--Номер ревизии: 41304
--Номер версии: 1.18
--Комментарий: Программа лояльности: Удаляем пункт верхнего меню "Мои бонусы"
delete from MENU_LINKS where LINK_ID=7;

--Номер ревизии: 41457
--Номер версии: 1.18
--Комментарий: ENH044719: mAPI 5. Реализовать версионность платежных форм.
/*==============================================================*/
/* Table: PAYMENT_FORM_TRANSFORMATIONS                          */
/*==============================================================*/
create table PAYMENT_FORM_TRANSFORMATIONS  (
   ID                   INTEGER                         not null,
   FORM_ID              INTEGER                         not null,
   TYPE                 VARCHAR2(8)                     not null,
   TRANSFORMATION       CLOB                            not null,
   constraint PK_PAYMENT_FORM_TRANSFORMATION primary key (ID)
);

create sequence S_PAYMENT_FORM_TRANSFORMATIONS;

create index "DXFK_TRANSFORM_TO_FORM" on PAYMENT_FORM_TRANSFORMATIONS
(
   FORM_ID
);

alter table PAYMENT_FORM_TRANSFORMATIONS
   add constraint FK_TRANSFORM_TO_FORM foreign key (FORM_ID)
      references PAYMENTFORMS (ID);

ALTER TABLE PAYMENTFORMS DROP COLUMN HTML_TRANSFORMATION;
ALTER TABLE PAYMENTFORMS DROP COLUMN PRINT_TRANSFORMATION;
ALTER TABLE PAYMENTFORMS DROP COLUMN MOBILE_TRANSFORMATION;
ALTER TABLE PAYMENTFORMS DROP COLUMN ATM_TRANSFORMATION;


--Номер ревизии: 41509
--Номер версии: 1.18
--Комментарий: Аунтентификация в ЕРИБе с поддержкой старой ЦСА
alter table LOGINS add TRASTED char(1)
go

update LOGINS set TRASTED = '1'
go

alter table LOGINS modify TRASTED not null
go

alter table LOGINS add LOGIN_TYPE varchar2(10)
go

update LOGINS set LOGIN_TYPE = 'OLD_CSA'
go

alter table LOGINS modify LOGIN_TYPE not null
go


--Номер ревизии: 41726
--Номер версии: 1.18
--Комментарий: CHG045376: Изменить названия операций на форме "Группа риска для операций"
alter table PAYMENTS_GROUP_RISK modify GROUP_RISK_ID null
go

update PAYMENTS_GROUP_RISK set KIND = 'SP' where KIND = 'S'
go

update PAYMENTS_GROUP_RISK set KIND = 'EAP' where KIND = 'EP'
go

insert into PAYMENTS_GROUP_RISK (ID, KIND, GROUP_RISK_ID, DEPARTMENT_ID)
   select S_PAYMENTS_GROUP_RISK.nextval as ID, 'ECP' as KIND, GROUP_RISK_ID, DEPARTMENT_ID 
      from PAYMENTS_GROUP_RISK where KIND = 'EAP'
go

update PAYMENTS_GROUP_RISK set KIND = 'JP' where KIND = 'J'
go

insert into PAYMENTS_GROUP_RISK (ID, KIND, GROUP_RISK_ID, DEPARTMENT_ID)
    select 
        S_PAYMENTS_GROUP_RISK.nextval as ID, 
        'CP' as KIND, 
        t.GROUP_RISK_ID as GROUP_RISK_ID, 
        t.DEPARTMENT_ID as DEPARTMENT_ID
    from (
        select 
            min(GROUP_RISK_ID) as GROUP_RISK_ID,
            min(DEPARTMENT_ID) as DEPARTMENT_ID 
        from PAYMENTS_GROUP_RISK
            where KIND in ('JC', 'EPC', 'IPC')
           group by DEPARTMENT_ID
        ) t
go

delete from PAYMENTS_GROUP_RISK where KIND in ('JC', 'EPC', 'IPC')
go

--Номер ревизии: 41739
--Номер версии: 1.18
--Комментарий: заменяем старые типы лимита на новые

alter table limits modify (RESTRICTION_TYPE varchar2(30));
update limits set RESTRICTION_TYPE = 'DESCENDING' where TYPE = 'USER_POUCH';
update limits set RESTRICTION_TYPE = 'AMOUNT_IN_DAY' where RESTRICTION_TYPE = 'AMOUNT';
update limits set RESTRICTION_TYPE = 'OPERATION_COUNT_IN_DAY' where RESTRICTION_TYPE = 'OPERATION_COUNT';


--Номер ревизии: 41778
--Номер версии: 1.18
--Комментарий: CHG045193: Загруpузка иконок для панели быстрой оплаты вручную на форме настройки ПБО.

alter table PANEL_BLOCKS add IMAGE_ID integer;
alter table PANEL_BLOCKS add SHOW_PROVIDER_NAME char(1) default '1' not null;

--Номер ревизии: 41835
--Номер версии: 1.18
--Комментарий:  CHG045455: Группа риска по умолчанию 

ALTER TABLE GROUPS_RISK ADD IS_DEFAULT CHAR(1); 
UPDATE GROUPS_RISK SET IS_DEFAULT = '0';
ALTER TABLE GROUPS_RISK MODIFY IS_DEFAULT CHAR(1) NOT NULL;

--Номер ревизии: 42194
--Номер версии: 1.18
--Комментарий:  CHG046026: Ограничить длину названия поставщика в ПБО 

update PANEL_BLOCKS set PROVIDER_NAME = substr( PROVIDER_NAME, 0, 14 );
alter table PANEL_BLOCKS modify PROVIDER_NAME varchar2(14);


--Номер ревизии: 42185
--Номер версии: 1.18
--Комментарий: ЕРМБ. Разработка сущностей + модель бд.

create table ERMB_TARIF (
     ID                          INTEGER                     not null,
     NAME                        VARCHAR2(128)               not null,
     CONNECT_COST_AMOUNT         INTEGER                             ,
     CONNECT_COST_CURRENCY       CHAR(3)                             ,
     CHARGE_PERIOD               INTEGER                     not null,
     GRACE_PERIOD                INTEGER                     not null, 
     GRACE_PERIOD_COST_AMOUNT    INTEGER                             ,
     GRACE_PERIOD_COST_CURRENCY  CHAR(3)                             ,
     CLASS_GRACE_AMOUNT          INTEGER                             ,
     CLASS_GRACE_CURRENCY        CHAR(3)                             ,
     CLASS_PREMIUM_AMOUNT        INTEGER                             ,
     CLASS_PREMIUM_CURRENCY      CHAR(3)                             ,
     CLASS_SOCIAL_AMOUNT         INTEGER                             ,
     CLASS_SOCIAL_CURRENCY       CHAR(3)                             ,
     CLASS_STANDARD_AMOUNT       INTEGER                             ,
     CLASS_STANDARD_CURRENCY     CHAR(3)                             ,
     OP_NOTICE_CONS_INCOM        CHAR(1)                     not null,    
     OP_NOTICE_LOAN_ARREAR       CHAR(1)                     not null,    
     OP_GET_CARD_LIMIT_INFO      CHAR(1)                     not nulL,       
     OP_GET_CARD_HISTORY_INFO    CHAR(1)                     not null, 
     OP_ALL_OTHER_CLIENT_REQUESTS CHAR(1)                    not null, 
     STATUS                      CHAR(1)                     not null,
     constraint PK_ERMB_TARIF primary key (ID)
);

create sequence S_ERMB_TARIF;

create table ERMB_PROFILE (
    ID                        INTEGER                   not null,
    PERSON_ID                 INTEGER                   not null,
    OLD_FIRST_NAME            VARCHAR2(42)                      ,
    OLD_SUR_NAME              VARCHAR2(42)                      ,  
    OLD_PATR_NAME             VARCHAR2(42)                      ,  
    OLD_BIRTHDAY              TIMESTAMP                         ,
    OLD_DOCUMENT_ID           INTEGER                           ,
    SERVICE_STATUS            CHAR(1)                   not null,
    END_SERVICE_DATE          TIMESTAMP                         ,
    FOREG_PRODUCT_ID          INTEGER                           ,
    CONNECTION_DATE           TIMESTAMP                         ,
    NEW_PRODUCT_NOTIFICATION  CHAR(1)                   not null,
    DAYS_OF_WEEK              VARCHAR2(28)                      ,
    TIME_START                VARCHAR2(10)                      , 
    TIME_END                  VARCHAR2(10)                      , 
    TIME_ZONE                 INTEGER                           ,
    ERMB_TARIF_ID             INTEGER                   not null,
    constraint PK_ERMB_PROFILE primary key (ID),
    constraint FK_ERMB_PROFILE_USERS foreign key (PERSON_ID)   references USERS (ID),
    constraint FK_ERMB_PROFILE_OLD_DOCUMENT foreign key (OLD_DOCUMENT_ID)   references DOCUMENTS (ID),
    constraint FK_ERMB_PROFILE_TARIF foreign key (ERMB_TARIF_ID)   references ERMB_TARIF (ID)
);


create sequence S_ERMB_PROFILE;

create index "DXFK_ERMB_PROFILE_OLD_DOCUMENT" on ERMB_PROFILE
(
   OLD_DOCUMENT_ID
);

create index "DXFK_ERMB_PROFILE_TARIF" on ERMB_PROFILE
(
   ERMB_TARIF_ID
);

create index "DXFK_ERMB_PROFILE_USERS" on ERMB_PROFILE
(
   PERSON_ID
);

create table ERMB_CLIENT_PHONE (
     ID                          INTEGER                     not null,
     PROFILE_ID                  INTEGER                     not null,
     IS_MAIN                     CHAR(1)                     not null,
     SEND_NOTIFICATION           CHAR(1)                     not null,
     PHONE_NUMBER                      VARCHAR2(20)          not null,
     constraint PK_CLIENT_PHONE primary key (ID),
     constraint FK_ERMB_CLIENT_PHONE_PROFILE foreign key (PROFILE_ID)  references ERMB_PROFILE(ID)
);

create sequence S_ERMB_CLIENT_PHONE;

create index "DXFK_ERMB_CLIENT_PHONE_PROFILE" on ERMB_CLIENT_PHONE
(
   PROFILE_ID
);

    
alter table CARD_LINKS add (ERMB_NOTIFICATION char(1));

alter table ACCOUNT_LINKS add (ERMB_NOTIFICATION char(1));

--Номер ревизии: 42302
--Номер версии: 1.18
--Комментарий: Видимость шаблонов  в мобильном приложении

ALTER TABLE BUSINESS_DOCUMENTS_RES ADD SHOW_IN_MOBILE CHAR(1);

UPDATE BUSINESS_DOCUMENTS_RES SET SHOW_IN_MOBILE = '1';

ALTER TABLE BUSINESS_DOCUMENTS_RES MODIFY SHOW_IN_MOBILE CHAR(1) NOT NULL;

--Номер ревизии: 42332
--Номер версии: 1.18
--Комментарий:  merge: 41974, 41975. Регистрация и аутентификация мобильного приложения в mAPI 5 (Общие доработки.)

ALTER TABLE AUTHENTICATION_MODES
    MODIFY (ACCESS_TYPE VARCHAR2(15));
    
ALTER TABLE SCHEMEOWNS
    MODIFY (ACCESS_TYPE VARCHAR2(15));
    
DECLARE

PROPERTY VARCHAR2(440);

BEGIN

    SELECT RAWTOHEX('<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"><properties><entry key="simple-auth-choice">lp</entry><entry key="secure-confirm-choice">nc</entry></properties>') INTO PROPERTY FROM dual;

    DELETE FROM AUTHENTICATION_MODES
        WHERE ACCESS_TYPE = 'mobileLimited';

    INSERT INTO AUTHENTICATION_MODES(ID, LOGIN_ID, ACCESS_TYPE, PROPERTIES, MODE_KEY) 
        VALUES(S_AUTHENTICATION_MODES.NEXTVAL, null, 'mobileLimited', PROPERTY, null);
        
    INSERT INTO AUTHENTICATION_MODES(ID, LOGIN_ID, ACCESS_TYPE, PROPERTIES, MODE_KEY) 
        SELECT S_AUTHENTICATION_MODES.NEXTVAL, LOGINS.ID, 'mobileLimited', PROPERTY, null
            FROM LOGINS WHERE KIND = 'C' AND DELETED = '0';
        
END;

-- Эту часть скрипта выполнять после загрузки операций
DECLARE

SCHEMEID INTEGER;

BEGIN

    SELECT MAX(ACCESSSCHEMES.ID) INTO SCHEMEID FROM ACCESSSCHEMES WHERE SCHEME_KEY = 'mobile-limited-scheme';

    IF NOT(SCHEMEID IS NULL) THEN
        BEGIN    
            DELETE FROM SCHEMEOWNS
                WHERE ACCESS_TYPE = 'mobileLimited';
            
            INSERT INTO SCHEMEOWNS(ID, SCHEME_ID, LOGIN_ID, ACCESS_TYPE) 
                SELECT S_SCHEMEOWNS.NEXTVAL, SCHEMEID, LOGINS.ID, 'mobileLimited'
                    FROM LOGINS WHERE KIND = 'C' AND DELETED = '0';
        END;    
    END IF;     
        
END;

--Номер ревизии: 42384
--Номер версии: 1.18
--Комментарий: Реализация управления видимостью продуктов для канала УС

alter table account_links
add   SHOW_IN_ATM          CHAR(1)                        default '0' not null;

update account_links
set SHOW_IN_ATM = show_in_es
where show_in_es is not null;

alter table card_links
add   SHOW_IN_ATM          CHAR(1)                        default '0' not null;

alter table loan_links
add   SHOW_IN_ATM          CHAR(1)                        default '0' not null;

alter table imaccount_links
add   SHOW_IN_ATM          CHAR(1)                        default '0' not null;

--Номер ревизии: 42523
--Номер версии: 1.18
--Комментарий: BUG044190: Исключение при поиске карточного клиента в автоплатежах. 

alter table USERS modify (DISPLAY_CLIENT_ID VARCHAR2(100));

--Номер ревизии: 42690
--Номер версии: 1.18
--Комментарий:ERMB. Изменения в  модели + сервис для работы с телефонами 

alter table ERMB_PROFILE ADD (CLIENT_CATEGORY CHAR(1));
alter table ERMB_PROFILE ADD (LAST_REQUEST_TIME TIMESTAMP);
alter table ERMB_PROFILE ADD (FAST_SERVICE CHAR(1) DEFAULT 0 NOT NULL);
alter table ERMB_PROFILE ADD (ADVERTISING CHAR(1) DEFAULT 1 NOT NULL);
alter table ERMB_PROFILE modify  (NEW_PRODUCT_NOTIFICATION DEFAULT 0);
alter table ERMB_CLIENT_PHONE modify (IS_MAIN DEFAULT 0);   
create unique index "UNIQ_ERMB_PHONE_NUMBER" on ERMB_CLIENT_PHONE (
   PHONE_NUMBER ASC
);




--Номер ревизии: 42785
--Номер версии: 1.18
--Комментарий:CHG046573: Необходимо реализовать проверки на форме «Информация о персонально менеджере»
alter table EMPLOYEES add constraint AK_MANAGER_ID_UNIQUE UNIQUE (MANAGER_ID);

--Номер ревизии: 42840
--Номер версии: 1.18
--Комментарий:фиксации в истории операций типа канала и номер УС (без изменения модели БД)
alter table business_documents add CODE_ATM varchar2(255);

--Номер ревизии: 42858
--Номер версии: 1.18
--Комментарий:ЕРМБ. Доработка интерфейса АРМ сотрудника (Подключение профиля) 
alter table ERMB_CLIENT_PHONE
   drop constraint FK_ERMB_CLIENT_PHONE_PROFILE; 

alter table ERMB_CLIENT_PHONE
   add constraint FK_ERMB_CLIENT_PHONE_PROFILE foreign key (PROFILE_ID)
      references ERMB_PROFILE(ID)
      on delete cascade;

alter table ERMB_CLIENT_PHONE
    drop column SEND_NOTIFICATION;

update LOAN_LINKS set ERMB_NOTIFICATION = 1;
alter table LOAN_LINKS modify (ERMB_NOTIFICATION default 1 not null);
update CARD_LINKS set ERMB_NOTIFICATION = 1;
alter table CARD_LINKS modify  (ERMB_NOTIFICATION default 1 not null);
update ACCOUNT_LINKS set ERMB_NOTIFICATION = 1;
alter table ACCOUNT_LINKS modify (ERMB_NOTIFICATION default 1 not null);
                                                                             

--Номер ревизии: 42877
--Номер версии: 1.18
--Комментарий: ЕРМБ. Работа над ошибками
alter table ERMB_PROFILE modify SERVICE_STATUS varchar(13);

alter table ERMB_TARIF modify STATUS varchar(10);

alter table  ERMB_TARIF modify OP_NOTICE_CONS_INCOM varchar(24);
alter table  ERMB_TARIF modify OP_NOTICE_LOAN_ARREAR varchar(24);
alter table  ERMB_TARIF modify OP_GET_CARD_LIMIT_INFO varchar(24);
alter table  ERMB_TARIF modify OP_GET_CARD_HISTORY_INFO varchar(24);
alter table  ERMB_TARIF modify OP_ALL_OTHER_CLIENT_REQUESTS varchar(24);

update ERMB_TARIF
set OP_NOTICE_CONS_INCOM = 'PROVIDED',
    OP_NOTICE_LOAN_ARREAR = 'PROVIDED',
    OP_GET_CARD_LIMIT_INFO = 'PROVIDED',
    OP_GET_CARD_HISTORY_INFO = 'PROVIDED',
    OP_ALL_OTHER_CLIENT_REQUESTS = 'PROVIDED',
    STATUS = 'ACTIVE';

update ERMB_PROFILE
set SERVICE_STATUS = 'ACTIVE';

--Номер ревизии: 42947
--Номер версии: 1.18
--Комментарий: BUG045348: Ошибка при входе клиента
alter table LOYALTY_PROGRAM_LINKS modify EXTERNAL_ID null;
alter table LOYALTY_PROGRAM_LINKS modify STATE varchar2(15);
alter table LOYALTY_PROGRAM_LINKS drop column  SHOW_IN_MAIN;
alter table LOYALTY_PROGRAM_LINKS drop column  SHOW_OPERATIONS;

--Номер ревизии: 42965
--Номер версии: 1.18
--Комментарий: BUG044507: Заведение в ЕРИБ сотрудников call центров - права доступа .
alter table services add CA_ADMIN_SERVICE char(1) default 0 not null;

--Номер ревизии: 43250
--Номер версии: 1.18
--Комментарий: BUG043926: Ошибка при просмотре списка ПБО
alter table PANEL_BLOCKS
   add constraint FK_PANEL_BLOCKS_TO_PROVIDERS foreign key (PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade;

create index "DXFK_PANEL_BLOCKS_TO_PROVIDERS" on PANEL_BLOCKS
(
   PROVIDER_ID
)
;

--Номер ревизии: 43361
--Номер версии: 1.18
--Комментарий: CHG045573: Дошагивание биллинговых операций
alter table BUSINESS_DOCUMENTS add OPERATION_UUID varchar2(50) null
go


--Номер ревизии: 43384
--Номер версии: 1.18
--Комментарий:  BUG046136: Ошибка при переходе с сайта ФНС 
-- fix: поправил constraint для заказов ФНС и ОЗОН
alter table FNS_FIELDS
drop constraint FK_FNS_FIEL_FK_FNS_FI_ORDERS
;

alter table FNS_FIELDS
   add constraint FK_FNS_FIEL_FK_FNS_FI_ORDERS foreign key (ORDER_ID)
      references ORDERS (ID)
      on delete cascade
;

alter table SHOP_FIELDS
drop constraint FK_SHOP_FIE_REFERENCE_ORDERS
;

alter table SHOP_FIELDS
   add constraint FK_SHOP_FIE_REFERENCE_ORDERS foreign key (ORDER_ID)
      references ORDERS (ID)
      on delete cascade
;

--Номер ревизии: 43410
--Номер версии: 1.18
--Комментарий: BUG046072 Не учитываются суммы в рамках старых лимитов

DROP TABLE USERS_LIMITS_JOURNAL;                                  

/*==============================================================*/
/* Table: USERS_LIMITS_JOURNAL                                  */
/*==============================================================*/
create table USERS_LIMITS_JOURNAL  (
   ID                   INTEGER                         not null,
   CREATION_DATE        TIMESTAMP                       not null,
   LOGIN_ID             INTEGER                         not null,
   LIMIT_ID             INTEGER,
   DOCUMENT_ID          INTEGER,
   AMOUNT               NUMBER(10,2),
   AMOUNT_CURRENCY      CHAR(3),
   STATE                VARCHAR2(20)                    not null,
   LIMIT_TYPE           VARCHAR2(40)                    not null,
   CHANNEL_TYPE         VARCHAR2(25)                    not null,
   RESTRICTION_TYPE     VARCHAR2(40)                    not null,
   GROUP_RISK_ID        INTEGER,
   constraint PK_USERS_LIMITS_JOURNAL primary key (ID)
);

create sequence S_USERS_LIMITS_JOURNAL;

/*==============================================================*/
/* Index: USR_LIMITS_IND                                        */
/*==============================================================*/
create index USR_LIMITS_IND on USERS_LIMITS_JOURNAL (
  CREATION_DATE ASC,
   LOGIN_ID ASC
);

create index "DXFK_USR_LIMIT_TO_DOCS" on USERS_LIMITS_JOURNAL
(
   DOCUMENT_ID
);

alter table USERS_LIMITS_JOURNAL
   add constraint FK_USR_LIMIT_TO_DOCS foreign key (DOCUMENT_ID)
      references BUSINESS_DOCUMENTS (ID)
      on delete cascade;


create index "DXFK_USR_LIMIT_TO_LIMIT" on USERS_LIMITS_JOURNAL
(
   LIMIT_ID
);

alter table USERS_LIMITS_JOURNAL
   add constraint FK_USR_LIMIT_TO_LIMIT foreign key (LIMIT_ID)
      references LIMITS (ID);


create index "DXFK_USR_LIMIT_TO_LOGIN" on USERS_LIMITS_JOURNAL
(
   LOGIN_ID
);

alter table USERS_LIMITS_JOURNAL
   add constraint FK_USR_LIMIT_TO_LOGIN foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade;


--Номер ревизии: 43434
--Номер версии: 1.18
--Комментарий:  CHG046302: АЛФ. Доработать справочник MCC кодов. 
--              CHG046541: Доработка АЛФ: обработка неизвестных MCC кодов. 

alter table CARD_OPERATION_CATEGORIES add IS_DEFAULT char(1) default '0';

alter table CARD_OPERATIONS drop constraint FK_CARDOP_MCC_CODE;

delete from MERCHANT_CATEGORY_CODES;

alter table MERCHANT_CATEGORY_CODES drop column CARD_OPERATION_CATEGORY_ID;
alter table MERCHANT_CATEGORY_CODES add INCOME_OPERATION_CATEGORY_ID INTEGER;
alter table MERCHANT_CATEGORY_CODES add OUTCOME_OPERATION_CATEGORY_ID INTEGER;

--Номер ревизии: 43598 
--Номер версии: 1.18
--Комментарий:  BUG047171: Ошибка на главной странице. 
create index "DXFK_NEWS_DEP_TO_DEPARTMENTS" on NEWS_DEPARTMENT
(
   DEPARTMENT_ID
)
go

alter table NEWS_DEPARTMENT
   add constraint FK_NEWS_DEP_TO_DEPARTMENTS foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
      on delete cascade
go


--Номер ревизии: 43645 
--Номер версии: 1.18
--Комментарий:  BUG047411: Ошибка при входе под клиентом с внешней ссылки 
-- fix: добавил "on delete cascade" 
alter table SHOP_ADDITIONAL_FIELDS
drop constraint FK_SHOP_ADD_REFERENCE_SHOP_FIE
;

alter table SHOP_ADDITIONAL_FIELDS
  add constraint FK_SHOP_ADD_REFERENCE_SHOP_FIE foreign key (SHOP_ORDER_ID)
    references SHOP_FIELDS (ID)
    on delete cascade
;

--Номер ревизии: 43727
--Номер версии: 1.18
--Комментарий:  CHG046655: Перенос настроек из конфигурационного файла в АРМ сотрудника
create table BUSINESS_PROPERTIES  (
   ID                   INTEGER                         not null,
   KEY                  VARCHAR2(256),
   KIND                 CHAR(1)                         not null,
   FROM_TIME            VARCHAR2(10),
   TO_TIME              VARCHAR2(10),
   DEPARTMENT_ID        INTEGER,
   constraint PK_BUSINESS_PROPERTIES primary key (ID)
);

create sequence S_BUSINESS_PROPERTIES;

create index "DXFK_BUS_PROP_TO_DEPARTMENTS" on BUSINESS_PROPERTIES
(
   DEPARTMENT_ID
);

alter table BUSINESS_PROPERTIES
   add constraint FK_BUSINESS_FK_BUS_PR_DEPARTME foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID);
      
--Номер ревизии: 43755
--Номер версии: 1.18
--Комментарий: BUG047378: Нет проверки на ввод значения поля сумма превышающего допустимое при создании\редактировании АП 
  
alter table USERS_LIMITS_JOURNAL modify (AMOUNT number(19,4));


--Номер ревизии 43821
--Номер версии 1.18
--Комментарий: CHG046835: Изменение алгоритма учета ИМСИ лимита для платежей\переводов
create table IMSI_ERROR_FOR_LOGIN
(
   ID integer not null,
   OPERATION_DATE date not null,
   LOGIN_ID integer not null,
   GOOD_IMSI char(1) not null,

   constraint FK_IMSI_FOR_LOGIN_TO_LOGIN foreign key (LOGIN_ID) references LOGINS(ID),
   constraint PK_IMSI_ERROR_FOR_LOGIN primary key (ID)
)
go

create index "DXFK_IMSI_FOR_LOGIN_TO_LOGIN" on IMSI_ERROR_FOR_LOGIN
(
   LOGIN_ID
)
go

create sequence S_IMSI_ERROR_FOR_LOGIN
go

create index IDX_INSI_LOGIN_LOGINID_DATE on IMSI_ERROR_FOR_LOGIN
(
   LOGIN_ID asc,
   OPERATION_DATE desc
)
go

--Номер ревизии: 43851
--Номер версии: 1.18
--Комментарий: BUG046838: Ошибка при платеже по прямой интеграции с ЦОД.(сам скрипт находится в файле create-other-oracle.sql) 
create or replace procedure create_sequence(sequenceName VARCHAR, maxval INTEGER) is
begin
    IF regexp_like(sequenceName,'SC_.+_\d{6}') -- если этот сиквенс - обновляемый
    then    -- ставим ему кэш 2000
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle cache 2000';
    else
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle';
    end if;
end;
go

--Номер ревизии: 44104
--Номер версии: 1.18
--Комментарий: CHG044555: Изменить текст смс при изменении моб. кошелька. 

alter table PROFILE add (MOBILE_WALLET_AMOUNT number(19,4),
                        MOBILE_WALLET_CURRENCY char(3));

--Номер ревизии: 44208
--Номер версии: 1.18
--Комментарий: Добавление таблицы WEB_PAGES

create table WEB_PAGES  (
   ID                   INTEGER                         not null,
   CLASSNAME            VARCHAR2(64)                    not null,
   LAYOUT               CLOB                            not null,
   WIDGETS              CLOB                            not null,
   PROFILE_ID           INTEGER                         not null,
   LOCATION             VARCHAR(16)                     not null,
   constraint PK_WEB_PAGES primary key (ID)
);

create sequence S_WEB_PAGES;

create index "DXPROFILE_TO_WEB_PAGES" on WEB_PAGES
(
   PROFILE_ID
);

alter table WEB_PAGES
   add constraint FK_PROFILE_TO_WEB_PAGES foreign key (PROFILE_ID)
      references PROFILE (ID)

                        
--Номер ревизии: 44269
--Номер версии: 1.18
--Комментарий: CHG047998: mAPI. Необходимо расширить поле в Business_documents
alter table BUSINESS_DOCUMENTS
modify(EXTERNAL_OWNER_ID varchar2(100))
;


--Номер ревизии: 44276
--Номер версии: 1.18
--Комментарий: CHG046196: Привязка рабочих календарей к подразделениям банка

alter table CALENDARS add DEPARTMENT_ID INTEGER;

create index "DXFK_CALENDARS_TO_DEPARTMENTS" on CALENDARS
(
   DEPARTMENT_ID
);

alter table CALENDARS
   add constraint FK_CALENDARS_TO_DEPARTMENTS foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
      on delete set NULL;


--Номер ревизии: 44606
--Номер версии: 1.18
--Комментарий: CHG047137: Отчет по статистике ПБО

create sequence S_QUICK_PAYMENT_PANELS_LOG;

create table QUICK_PAYMENT_PANELS_LOG(
    ID             INTEGER      not null,
    PANEL_ID       INTEGER      not null,
    TB             VARCAR2(4)   not null,
    TYPE           varchar2(16) not null,
    START_DATE     TIMESTAMP    not null,
    AMOUNT         NUMBER(15,4),   

constraint PK_QUICK_PAYMENT_PANELS_LOG primary key (ID)  
);


--Номер ревизии: 44681
--Номер версии: 1.18
--Комментарий: Погодный виджет: справочник городов

create table CITIES  (
    CODE  VARCHAR2(20) not null,
    REGION_CODE   VARCHAR2(20),
    NAME          VARCHAR2(128),
    constraint PK_CITIES primary key (CODE)
)
go


--Номер ревизии: 44737
--Номер версии: 1.18
--Комментарий: BUG048554: проблема при загрузке курсов валют
-- Скрипт переворачивает обратно инвертированные курсы Рубль (643) -> XXX
update RATE R
set R.FROM_CURRENCY = R.TO_CURRENCY,
    R.FROM_VALUE = R.TO_VALUE,
    R.RATE_TYPE = 4
where R.FROM_CURRENCY='643'
   and R.RATE_TYPE = 3
   and exists (
       -- для перевёрнутого курса д.б. брат по RATE_TYPE 
       select 1 from RATE RR
       where RR.FROM_CURRENCY = R.TO_CURRENCY
          and RR.TO_CURRENCY = R.FROM_CURRENCY
          and RR.RATE_TYPE = R.RATE_TYPE
          and RR.EFF_DATE = R.EFF_DATE
          and NVL(RR.ORDER_NUMBER, '0') = NVL(R.ORDER_NUMBER, '0')
          and RR.DEPARTMENT_ID = R.DEPARTMENT_ID
          and RR.ID <> R.ID
);

update RATE R
set R.FROM_CURRENCY = R.TO_CURRENCY,
    R.FROM_VALUE = R.TO_VALUE,
    R.RATE_TYPE = 0
where R.FROM_CURRENCY='643'
   and R.RATE_TYPE = 0
   and exists (
       -- для перевёрнутого курса д.б. брат по RATE_TYPE 
       select 1 from RATE RR
       where RR.FROM_CURRENCY = R.TO_CURRENCY
          and RR.TO_CURRENCY = R.FROM_CURRENCY
          and RR.RATE_TYPE = R.RATE_TYPE
          and RR.EFF_DATE = R.EFF_DATE
          and NVL(RR.ORDER_NUMBER, '0') = NVL(R.ORDER_NUMBER, '0')
          and RR.DEPARTMENT_ID = R.DEPARTMENT_ID
          and RR.ID <> R.ID
);


--Номер ревизии: 44745
--Номер версии: 1.18
--Комментарий: ЕРИБ-Качество
-- создание таблицы настроек мониторинга
create table MONITORING_GATE_CONFIGS  (
   SERVICE              VARCHAR2(50)                    not null,
   STATE                VARCHAR2(15)                    not null,
   DEGRADATION_USE      CHAR(1)                         not null,
   DEGRADATION_TIMEOUT  NUMBER                          not null,
   DEGRADATION_TIME     NUMBER                          not null,
   DEGRADATION_COUNT    NUMBER                          not null,
   INACCESSIBLE_USE     CHAR(1)                         not null,
   INACCESSIBLE_TIMEOUT NUMBER                          not null,
   INACCESSIBLE_TIME    NUMBER                          not null,
   INACCESSIBLE_COUNT   NUMBER                          not null,
   constraint PK_MONITORING_GATE_CONFIGS primary key (SERVICE)
)


--Номер ревизии: 44778
--Номер версии: 1.18
--Комментарий: создание таблицы реквизитов поля
create table FIELD_REQUISITE_TYPES  (
   FIELD_ID             INTEGER                         not null,
   REQUISITE_TYPE       VARCHAR2(32)                    not null,
   LIST_INDEX           INTEGER                         not null,
   constraint PK_FIELD_REQUISITE_TYPES primary key (FIELD_ID, LIST_INDEX)
);

create sequence S_FIELD_REQUISITE_TYPES;

--Номер ревизии: 44777
--Номер версии: 1.18
--Комментарий: оффлайн продукты

/*==============================================================*/
/* Table: STORED_DEPO_ACCOUNT                                                     */
/*==============================================================*/
create table STORED_DEPO_ACCOUNT  (
   ID                   INTEGER                         not null,
   DEPARTMENT_ID        INTEGER,
   STATE                VARCHAR2(20),
   AGREEMENT_NUMBER     VARCHAR2(25),
   AGREEMENT_DATE       TIMESTAMP,
   IS_OPERATION_ALLOWED CHAR(1),
   ENTITY_UPDATE_TIME   TIMESTAMP,
   HASH                 INTEGER,
   DEBT_AMOUNT          NUMBER(15,4),
   DEBT_CURRENCY        CHAR(3),
   RESOURCE_ID          INTEGER,
   constraint PK_STORED_DEPO_ACCOUNT primary key (ID)
)
go

/*==============================================================*/
/* Table: STORED_LOAN                                                                     */
/*==============================================================*/
create table STORED_LOAN  (
   ID                   INTEGER                         not null,
   RESOURCE_ID          INTEGER            not null,
   DEPARTMENT_ID        INTEGER        not null,
   IS_ANNUITY           CHAR(1),
   STATE                VARCHAR2(20),
   DESCRIPTION          VARCHAR2(255),
   ENTITY_UPDATE_TIME   TIMESTAMP,
   AGREEMENT_NUMBER     VARCHAR2(25),
   NEXT_PAYMENT_DATE    TIMESTAMP,
   TERM_START           TIMESTAMP,
   TERM_END             TIMESTAMP,
   TERM_DURATION        VARCHAR2(15),
   RATE                 NUMBER(15,4),
   LAST_PAYMENT_DATE    TIMESTAMP,
   HASH                 INTEGER,
   PAST_DUE_AMOUNT      NUMBER(15,4),
   PAST_DUE_AMOUNT_CURRENCY CHAR(3),
   NEXT_PAYMENT_AMOUNT  NUMBER(15,4),
   NEXT_PAYMENT_AMOUNT_CURRENCY CHAR(3),
   LAST_PAYMENT_AMOUNT  NUMBER(15,4),
   LAST_PAYMENT_AMOUNT_CURRENCY CHAR(3),
   LOAN_AMOUNT          NUMBER(15,4),
   LOAN_AMOUNT_CURRENCY CHAR(3),
   BALANCE_AMOUNT       NUMBER(15,4),
   BALANCE_AMOUNT_CURRENCY CHAR(3),
   constraint PK_STORED_LOAN primary key (ID)
)
go

/*==============================================================*/
/* Table: STORED_ACCOUNT                                                                  */
/*==============================================================*/
create table STORED_ACCOUNT  (
   ID                   INTEGER                         not null,
   RESOURCE_ID          INTEGER                         not null,
   OPEN_DATE            TIMESTAMP,
   PROLONGATION_ALLOWED CHAR(3),
   INTEREST_RATE        NUMBER(15,4),
   KIND                 INTEGER,
   SUB_KIND             INTEGER,
   INTEREST_TRANSFER_ACCOUNT VARCHAR2(25),
   INTEREST_TRANSFER_CARD VARCHAR2(25),
   DEMAND               CHAR(1),
   PERIOD               VARCHAR2(15),
   CLOSE_DATE           TIMESTAMP,
   CREDIT_CROSS_AGENCY  CHAR(1),
   DEBIT_CROSS_AGENCY   CHAR(1),
   ENTITY_UPDATE_TIME   TIMESTAMP,
   ENTITY_UPDATE_INFO_TIME TIMESTAMP,
   HASH                 INTEGER,
   HASH_INFO            INTEGER,
   ACCOUNT_STATE        VARCHAR2(15),
   PASSBOOK             CHAR(1),
   CREDIT_ALLOWED       CHAR(1),
   DEBIT_ALLOWED        CHAR(1),
   BALANCE_AMOUNT       NUMBER(15,4),
   BALANCE_AMOUNT_CURRENCY CHAR(3),
   MAX_SUM_AMOUNT       NUMBER(15,4),
   MAX_SUM_AMOUNT_CURRENCY CHAR(3),
   MIN_BALANCE_AMOUNT   NUMBER(15,4),
   MIN_BALANCE_AMOUNT_CURRENCY CHAR(3),
   DEPARTMENT_ID        INTEGER                         not null,
   constraint PK_STORED_ACCOUNT primary key (ID)
)
go

/*==============================================================*/
/* Table: STORED_CARD                                                                     */
/*==============================================================*/
create table STORED_CARD  (
   ID                   INTEGER                         not null,
   RESOURCE_ID          INTEGER                         not null,
   DEPARTMENT_ID        INTEGER                         not null,
   ENTITY_UPDATE_TIME   TIMESTAMP,
   ENTITY_UPDATE_INFO_TIME TIMESTAMP,
   EXTERNAL_STATUS_CODE VARCHAR2(20),
   ISSUE_DATE           TIMESTAMP,
   DISPLAYED_EXPIRE_DATE VARCHAR2(20),
   CARD_TYPE            VARCHAR2(20),
   VIRTUAL              CHAR(1),
   LIMIT_AMOUNT         NUMBER(15,4),
   LIMIT_AMOUNT_CURRENCY CHAR(3),
   ADDITIONAL_CARD_TYPE VARCHAR2(20),
   STATUS_DESCRIPTION   VARCHAR2(255),
   CASH_LIMIT_AMOUNT    NUMBER(15,4),
   CASH_LIMIT_AMOUNT_CURRENCY CHAR(3),
   PURCHASE_LIMIT_AMOUNT NUMBER(15,4),
   PURCHASE_LIMIT_AMOUNT_CURRENCY CHAR(3),
   CARD_STATE           VARCHAR2(20),
   HOLDER_NAME          VARCHAR2(255),
   HASH                 INTEGER,
   HASH_INFO            INTEGER,
   constraint PK_STORED_CARD primary key (ID)
)
go

/*==============================================================*/
/* Table: STORED_IMACCOUNT                                                            */
/*==============================================================*/
create table STORED_IMACCOUNT  (
   ID                   INTEGER                         not null,
   RESOURCE_ID          INTEGER                         not null,
   ENTITY_UPDATE_TIME   TIMESTAMP,
   DEPARTMENT_ID        INTEGER                         not null,
   NAME                 VARCHAR2(255),
   AGREEMENT_NUMBER     VARCHAR2(25),
   OPEN_DATE            TIMESTAMP,
   STATE                VARCHAR2(20),
   CLOSING_DATE         TIMESTAMP,
   BALANCE_AMOUNT       NUMBER(15,4),
   BALANCE_AMOUNT_CURRENCY CHAR(3),
   MAX_SUM_AMOUNT       NUMBER(15,4),
   MAX_SUM_AMOUNT_CURRENCY CHAR(3),
   HASH                 INTEGER,
   constraint PK_STORED_IMACCOUNT primary key (ID)
)
go

create sequence S_STORED_ACCOUNT
go

create sequence S_STORED_CARD
go

create sequence S_STORED_IMACCOUNT
go

create sequence S_STORED_DEPO_ACCOUNT
go

create sequence S_STORED_LOAN
go

alter table STORED_ACCOUNT
   add constraint FK_STORED_A_TO_DEPARTMENT_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
go

alter table STORED_ACCOUNT
   add constraint FK_STORED_A_TO_LINK_REF foreign key (RESOURCE_ID)
      references ACCOUNT_LINKS (ID)
go

alter table STORED_IMACCOUNT
   add constraint FK_STORED_I_TO_LINK_REF foreign key (RESOURCE_ID)
      references IMACCOUNT_LINKS (ID)
go

alter table STORED_IMACCOUNT
   add constraint FK_STORED_I_TO_DEPARTMENTS_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
go

alter table STORED_CARD
   add constraint FK_STORED_C_TO_LINK_REF foreign key (RESOURCE_ID)
      references CARD_LINKS (ID)
go

alter table STORED_CARD
   add constraint FK_STORED_C_TO_DEPARTMENT_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
go

alter table STORED_DEPO_ACCOUNT
   add constraint STORED_DA_TO_DEPARTMENTS_REF foreign key (DEPARTMENT_ID)
      references DEPO_ACCOUNT_LINKS (ID)
go

alter table STORED_DEPO_ACCOUNT
   add constraint STORED_DA_TO_LINK_REF foreign key (RESOURCE_ID)
      references DEPO_ACCOUNT_LINKS (ID)
go

alter table STORED_LOAN
   add constraint STORED_L_TO_DEPARTMENTS_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
go

alter table STORED_LOAN
   add constraint STORED_L_TO_LINK_REF foreign key (RESOURCE_ID)
      references LOAN_LINKS (ID)
go

create index "DXSTORED_DA_TO_DEPARTMENTS" on STORED_DEPO_ACCOUNT
(
   DEPARTMENT_ID
)
go

create index "DXSTORED_DA_TO_LINK" on STORED_DEPO_ACCOUNT
(
   RESOURCE_ID
)
go

create index "DXSTORED_L_TO_DEPARTMENTS" on STORED_LOAN
(
   DEPARTMENT_ID
)
go

create index "DXSTORED_L_TO_LINK" on STORED_LOAN
(
   RESOURCE_ID
)
go

create index "DXSTORED_A_TO_DEPARTMENTS" on STORED_ACCOUNT
(
   DEPARTMENT_ID
)
go

create index "DXSTORED_A_TO_LINK" on STORED_ACCOUNT
(
   RESOURCE_ID
)
go

create index "DXSTORED_C_TO_DEPARTMENTS" on STORED_CARD
(
   DEPARTMENT_ID
)
go

create index "DXSTORED_C_TO_LINK" on STORED_CARD
(
   RESOURCE_ID
)
go

create index "DXSTORED_IMA_TO_DEPARTMENTS" on STORED_IMACCOUNT
(
   DEPARTMENT_ID
)
go

create index "DXSTORED_IMA_TO_LINK" on STORED_IMACCOUNT
(
   RESOURCE_ID
)
go



--Номер ревизии: 44804
--Номер версии: 1.18
--Комментарий: Погодный виджет: справочник городов (добавлено ограничение на NAME)

alter table CITIES modify NAME NOT NULL
go

--Номер ревизии: 44817
--Номер версии: 1.18
--Комментарий: Оффлайн продукты: исправлены внешние ключи

alter table STORED_DEPO_ACCOUNT
   drop constraint STORED_DA_TO_DEPARTMENTS_REF
go

alter table STORED_DEPO_ACCOUNT
   add constraint STORED_DA_TO_DEPARTMENTS_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
go

--Номер ревизии: 44819
--Номер версии: 1.18
--Комментарий: Перенос описания виджетов в базу

create table WIDGET_DEFINITIONS  (
   CODENAME             VARCHAR2(25)                    not null,
   USERNAME             VARCHAR2(30),
   DESCRIPTION          VARCHAR2(100),
   LOAD_MODE            VARCHAR2(5)                     not null,
   CLASS_NAME           VARCHAR2(25)                    not null,
   URL_PATH             VARCHAR2(100)                   not null,
   OPERATION            VARCHAR2(35)                    not null,
   WIDGET_CLASS         VARCHAR2(55),
   PICTURE              VARCHAR2(100),
   INITIAL_SIZE         VARCHAR2(10)                    not null,
   SIZEABLE             CHAR(1)                        default '0' not null,
   constraint PK_WIDGET_DEFINITIONS primary key (CODENAME)
)

go

create sequence S_WIDGET_DEFINITIONS
go

--Номер ревизии: 44848
--Номер версии: 1.18
--Комментарий: Управление каталогом виджетов из АРМ сотрудника(1)

alter table WIDGET_DEFINITIONS add (
    INDEX_NUMBER  number default '0' not null, 
    AVAILABILITY  char(1) default '1' not null
);

--Номер ревизии: 44859
--Номер версии: 1.18
--Комментарий: добавление в таблицу WIDGET_DEFINITIONS поля ADDING_DATE

delete from WIDGET_DEFINITIONS;
alter table WIDGET_DEFINITIONS add ADDING_DATE TIMESTAMP not null;


--Номер ревизии: 44865
--Номер версии: 1.18
--Комментарий: изменен enum com.rssl.phizic.common.types.RequisiteType

update FIELD_REQUISITE_TYPES set REQUISITE_TYPE = 'IS_LIST_OF_CHARGES' where REQUISITE_TYPE = 'IsListOfCharges';

update FIELD_REQUISITE_TYPES set REQUISITE_TYPE = 'IS_PERIOD' where REQUISITE_TYPE = 'IsPeriod';


--Номер ревизии: 44866
--Номер версии: 1.18
--Комментарий: добавление вторичного ключа FIELD_REQUISITE_TYPES

create index "DXFK_F_REQUISITE_TYPE_TO_FIELD" on FIELD_REQUISITE_TYPES
(
   FIELD_ID
);

alter table FIELD_REQUISITE_TYPES
   add constraint FK_F_REQUISITE_TYPE_TO_FIELD foreign key (FIELD_ID)
      references FIELD_DESCRIPTIONS (ID)
      on delete cascade;
      
--Номер ревизии: 44902
--Номер версии: 1.18
--Комментарий: Подтверждение операций чековым паролем.

alter table CARD_LINKS add(MB_USER_ID varchar2(30));

--Номер ревизии: 44942
--Номер версии: 1.18
--Комментарий: Автоматическое определение недоступности внешних систем

create table OFFLINE_EXT_SYSTEM_EVENT  (
   ID                   integer                         not null,
   ADAPTER_ID           integer                         not null,
   SYSTEM_TYPE          varchar2(4),
   EVENT_TIME           TIMESTAMP,
   constraint PK_OFFLINE_EXT_SYSTEM_EVENT primary key (ID)
);

create sequence S_OFFLINE_EXT_SYSTEM_EVENT;

create index DX_OFFLINE_ADAPTER_TIME on OFFLINE_EXT_SYSTEM_EVENT (
   ADAPTER_ID ASC,
   SYSTEM_TYPE ASC,
   EVENT_TIME ASC
);

alter table OFFLINE_EXT_SYSTEM_EVENT
   add constraint FK_OFFLINE_SYSTEM_TO_ADAPTER foreign key (ADAPTER_ID)
      references ADAPTERS (ID)
      on delete cascade;

alter table TECHNOBREAKS
    add IS_AUTO_ENABLED CHAR(1) default '0' not null;

--Номер ревизии: 44981
--Номер версии: 1.18
--Комментарий: ENH048874: Реализовать поддержку отдельной БД для PhizICLog
alter table USERLOG add USER_ID VARCHAR2(50);

--Номер ревизии: 45070
--Номер версии: 1.18
--Комментарий: ENH048874: Реализовать поддержку отдельной БД для PhizICLog
alter table SYSTEMLOG add USER_ID VARCHAR2(50);


--Номер ревизии: 45126
--Номер версии: 1.18
--Комментарий: chg: виджеты разнесены на виджеты и виджет-контейнеры

DELETE FROM WEB_PAGES
;

DROP TABLE WIDGETS
;

CREATE TABLE WIDGETS
(
  CODENAME             VARCHAR2(80)             NOT NULL,
  DEFINITION_CODENAME  VARCHAR2(25)             NOT NULL,
  WEB_PAGE_ID          INTEGER                  NOT NULL,
  BODY                 CLOB                     NOT NULL,
  
  CONSTRAINT PK_WIDGETS PRIMARY KEY(CODENAME)
)
;

CREATE INDEX DXWIDGETS_R01 ON WIDGETS (WEB_PAGE_ID)
;

ALTER TABLE WIDGETS ADD 
CONSTRAINT WIDGETS_R01
 FOREIGN KEY (WEB_PAGE_ID)
 REFERENCES WEB_PAGES (ID)
 ON DELETE CASCADE
;

ALTER TABLE WEB_PAGES
DROP COLUMN WIDGETS
;

--Номер ревизии: 45153
--Номер версии: 1.18
--Комментарий: Обеспечение возможности оплаты услуг при недоступности IQWave / шины (модель БД)

ALTER TABLE SERVICE_PROVIDERS
    ADD (IS_OFFLINE_AVAILABLE CHAR(1) DEFAULT '0' NOT NULL);

--Номер ревизии: 45159
--Номер версии: 1.18
--Комментарий: Доработака формы "Редактирование узла"

ALTER TABLE NODES ADD (TYPE varchar2(20) null); 
UPDATE NODES SET TYPE = 'COD';
ALTER TABLE NODES MODIFY TYPE varchar2(20) NOT NULL;


--Номер ревизии: 45176
--Номер версии: 1.18
--Комментарий: ЕРМБ тариф. Добавление операций. 

ALTER TABLE ERMB_TARIF DROP COLUMN OP_NOTICE_CONS_INCOM;
ALTER TABLE ERMB_TARIF DROP COLUMN OP_NOTICE_LOAN_ARREAR;
ALTER TABLE ERMB_TARIF DROP COLUMN OP_GET_CARD_LIMIT_INFO;
ALTER TABLE ERMB_TARIF DROP COLUMN OP_GET_CARD_HISTORY_INFO;
ALTER TABLE ERMB_TARIF DROP COLUMN OP_ALL_OTHER_CLIENT_REQUESTS;

ALTER TABLE ERMB_TARIF ADD  (
OP_NOTICE_CONS_INCOM_CARD varchar(24)  DEFAULT 'PROVIDED' NOT NULL,
OP_NOTICE_CONS_INCOM_ACC varchar(24)  DEFAULT 'PROVIDED' NOT NULL,
OP_CARD_INFO varchar(24)  DEFAULT 'PROVIDED' NOT NULL,
OP_ACC_INFO varchar(24)  DEFAULT 'PROVIDED' NOT NULL,
OP_CARD_MINI_INFO varchar(24)  DEFAULT 'PROVIDED' NOT NULL,
OP_ACC_MINI_INFO varchar(24)  DEFAULT 'PROVIDED' NOT NULL,
OP_RE_ISSUE_CARD varchar(24)  DEFAULT 'PROVIDED' NOT NULL,
OP_JUR_PAYMENT varchar(24)  DEFAULT 'PROVIDED' NOT NULL,
OP_TRANSFERT_THIRD_PARTIES varchar(24)  DEFAULT 'PROVIDED' NOT NULL
);

--Номер ревизии: 45234
--Номер версии: 1.18
--Комментарий: Управление механизмом виджетов в профиле клиента
alter table profile add SHOW_WIDGET  char(1) default '1' not null;

--Номер ревизии: 45269
--Номер версии: 1.18
--Комментарий: merge 43837: BUG046072 Не учитываются суммы в рамках старых лимитов (модель БД докоммит) + перевернул индекс USR_LIMITS_IND
--в конвертер 10го релиза данные изменения включать не нужно, на базе прома они должны быть, изменения пронесены для синхронизации
alter table limits rename column TYPE to LIMIT_TYPE;
alter table limits add (CHANNEL_TYPE varchar2(20) default 'INTERNET_CLIENT' not null);
drop index USR_LIMITS_IND;
create index USR_LIMITS_IND on USERS_LIMITS_JOURNAL (
   LOGIN_ID ASC,
   CREATION_DATE ASC
);

--Номер ревизии: 45247
--Номер версии: 1.18
--Комментарий: Синхронизация мобильных контактов
/*==============================================================*/
/* Table: CONTACTS                                              */
/*==============================================================*/
create table CONTACTS  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   NAME                 VARCHAR2(50)                    not null,
   PHONE                VARCHAR2(11)                    not null,
   constraint PK_CONTACTS primary key (ID)
);
create sequence S_CONTACTS;

/*==============================================================*/
/* Table: CONTACTS_SYNC                                         */
/*==============================================================*/
create table CONTACTS_SYNC  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   SYNC_COUNT           INTEGER                         not null,
   LAST_SYNC_DATE       TIMESTAMP                       not null,
   constraint PK_CONTACTS_SYNC primary key (ID)
);
create sequence S_CONTACTS_SYNC;

create index "DXFK_CONTACTS_TO_LOGINS" on CONTACTS
(
   LOGIN_ID
);

alter table CONTACTS
   add constraint FK_CONTACTS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID);


create index "DXFK_CONTACTS_SYNC_TO_LOGINS" on CONTACTS_SYNC
(
   LOGIN_ID
);

alter table CONTACTS_SYNC
   add constraint FK_CONTACTS_SYNC_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID);

--Номер ревизии: 45328
--Номер версии: 1.18
--Комментарий:  Табличка для конфигов     
create table CONFIGS  (
   CODENAME             VARCHAR2(32)                    not null,
   DATA                 CLOB                            not null,
   constraint PK_CONFIGS primary key (CODENAME)
)
;

create sequence S_CONFIGS
;

comment on table CONFIGS is
'Конфиги в сериализованном виде (xml, json и т.д.)'
;
                        
--Номер ревизии: 45346
--Номер версии: 1.18
--Комментарий:  BUG049514: MANAGER_ID 14 символов     
alter table users modify manager_id varchar2(20);

--Номер ревизии: 45365
--Номер версии: 1.18
--Комментарий: доработка обмена с АС Автоплатежи

alter table BUSINESS_DOCUMENTS add (CONFIRMED_EMPLOYEE_LOGIN_ID integer);

alter table BUSINESS_DOCUMENTS add (CREATED_EMPLOYEE_LOGIN_ID integer);

update BUSINESS_DOCUMENTS set CREATED_EMPLOYEE_LOGIN_ID = EMPLOYEE_LOGIN_ID;

alter table BUSINESS_DOCUMENTS drop column EMPLOYEE_DEPARTMENT_ID;

alter table BUSINESS_DOCUMENTS drop column EMPLOYEE_LOGIN_ID;

alter table BUSINESS_DOCUMENTS drop column EMPLOYEE_FIO;

--Номер ревизии: 45374
--Номер версии: 1.18
--Комментарий:   CHG050095: Добавление кода для выгрузки файла в СПООБК. 

ALTER TABLE CREDIT_CARD_PRODUCTS ADD(CARD_TYPE_CODE NUMBER);

--Номер ревизии: 45396
--Номер версии: 1.18
--Комментарий: Доработан справочник городов (добавлено новое поле)

alter table CITIES add (EN_NAME varchar2(128) NOT NULL);

--Номер ревизии: 45426
--Номер версии: 1.18
--Комментарий: Подтверждение введенных лимитов (ЗНИ 152412)

alter table limits add STATE varchar2(10)  default 'CONFIRMED' not null;

--Номер ревизии: 45431
--Номер версии: 1.18
--Комментарий: Редактирование бизнес настроек

ALTER TABLE BUSINESS_PROPERTIES ADD (VALUE varchar2(256) null); 
ALTER TABLE BUSINESS_PROPERTIES ADD constraint UNIQUE_KEY unique (KEY,KIND);

insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'limitInfoPersonPayment','C','10');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'bankLoanLink','C','http://sberbank.ru/ru/person/credits/');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'bankCardLink','C','http://sberbank.ru/moscow/ru/person/bank_cards/special/');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'changeSimSmsMessage','C','Уважаемый клиент! Зафиксирована замена Вашей SIM-карты. В целях безопасности Ваших средств операции в Сбербанк Онлайн приостановлены. Для разблокировки обратитесь в Контактный центр.');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'accountsKindsForbiddenClosing','C','');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'newsCount','C','3');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'clearOrderDays','C','15');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'orderNotificationCount','C','20');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'clearNotConfirmDocumentsPeriod','C','30');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'clearWaitConfirmDocumentsPeriod','C','30');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'RUB','D','3000');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'RUR','D','3000');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'EUR','D','100');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'USD','D','100');

--Номер ревизии: 45526
--Номер версии: 1.18
--Комментарий: Виджеты. Стратегия доступности widgetDefinition-а. (Модель БД)
ALTER TABLE WIDGET_DEFINITIONS
ADD (ACCESS_STRATEGY VARCHAR2(100 BYTE));


-- Номер ревизии:  45574
-- Номер версии: 10.0
-- Комментарий:  CHG040738: Вынести параметры из конфигурационных файлов в АРМ.  Уникальный индекс к полям PROPERTY_KEY и CATEGORY таблицы PROPERTIES
create unique index PROPERTIES_UNIQ on PROPERTIES (
   PROPERTY_KEY ASC,
   CATEGORY ASC
)
go

-- Номер ревизии:  45638
-- Номер версии: 1.18
-- Комментарий:  Интеграция с АС "Филиал СБ". Реализация интерфейса UPDATE_PROFILE(1)
alter table ERMB_TARIF add (code varchar2(24))
go
alter table ERMB_TARIF add constraint UNIQUE_CODE unique (code)
go

-- Номер ревизии: 45642
-- Номер версии:  1.18
-- Комментарий:  BUG050488: необходимо изменить механизм выгрузки файла "Описание расчетов" для ПФП.
drop table FILES
;
drop sequence S_FILES
;

-- Номер ревизии: 45654
-- Номер версии:  1.18
-- Комментарий:  CHG050492: Возможность отключения режима оффлайновости 
ALTER TABLE TECHNOBREAKS ADD (IS_ALLOWED_OFFLINE_PAY CHAR(1) DEFAULT '0' NOT NULL);

-- Номер ревизии:  45678
-- Номер версии: 1.18
-- Комментарий: Редактирование настроек адаптерова София-ВМС 
ALTER TABLE NODES ADD (PREFIX varchar2(128) null); 


-- Номер ревизии: 45706
-- Номер версии:  1.18
-- Комментарий: Увеличен размер столбца, в соответствии с размером поля поставщика

alter table FIELD_DESCRIPTIONS modify (INITIAL_VALUE nvarchar2 (1024));

-- Номер ревизии: 45798
-- Номер версии:  1.18
-- Комментарий: BUG050883: Нельзя создать ДП на погашение кредита. Модель данных

ALTER TABLE STORED_CARD MODIFY(DEPARTMENT_ID  NULL);
ALTER TABLE STORED_ACCOUNT MODIFY(DEPARTMENT_ID  NULL);
ALTER TABLE STORED_LOAN MODIFY(DEPARTMENT_ID  NULL);
ALTER TABLE STORED_IMACCOUNT MODIFY(DEPARTMENT_ID  NULL);
ALTER TABLE STORED_CARD ADD (OFFICE_OSB  VARCHAR2(5));
ALTER TABLE STORED_CARD ADD (OFFICE_TB    VARCHAR2(5));
ALTER TABLE STORED_CARD ADD (OFFICE_VSP  VARCHAR2(5));
ALTER TABLE STORED_LOAN ADD (OFFICE_OSB  VARCHAR2(5));
ALTER TABLE STORED_LOAN ADD (OFFICE_TB    VARCHAR2(5));
ALTER TABLE STORED_LOAN ADD (OFFICE_VSP  VARCHAR2(5));
ALTER TABLE STORED_ACCOUNT ADD (OFFICE_OSB  VARCHAR2(5));
ALTER TABLE STORED_ACCOUNT ADD (OFFICE_TB    VARCHAR2(5));
ALTER TABLE STORED_ACCOUNT ADD (OFFICE_VSP  VARCHAR2(5));
ALTER TABLE STORED_IMACCOUNT ADD (OFFICE_OSB  VARCHAR2(5));
ALTER TABLE STORED_IMACCOUNT ADD (OFFICE_TB    VARCHAR2(5));
ALTER TABLE STORED_IMACCOUNT ADD (OFFICE_VSP  VARCHAR2(5));
ALTER TABLE STORED_DEPO_ACCOUNT ADD (OFFICE_OSB  VARCHAR2(5));
ALTER TABLE STORED_DEPO_ACCOUNT ADD (OFFICE_TB    VARCHAR2(5));
ALTER TABLE STORED_DEPO_ACCOUNT ADD (OFFICE_VSP  VARCHAR2(5));

-- Номер ревизии: 45805
-- Номер версии:  1.18
-- Комментарий: Интеграция с АС "Филиал СБ". Реализация queryProfile. Добавление создания пользователя. Модель БД.

drop index FIO_PERSON_INDEX;

ALTER TABLE USERS MODIFY (FIRST_NAME VARCHAR2(120));
ALTER TABLE USERS MODIFY (SUR_NAME VARCHAR2(120));
ALTER TABLE USERS MODIFY (PATR_NAME VARCHAR2(120));

CREATE INDEX FIO_PERSON_INDEX ON USERS
(upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME),' ',''),'-','')));

ALTER TABLE DOCUMENTS MODIFY ( "DOC_ISSUE_BY" VARCHAR2(255) );

-- Номер ревизии: 45904
-- Номер версии:  1.18
-- Комментарий: Виджеты. Добавление инициализатора виджета, уточнение длин и обязательности полей

DELETE FROM WIDGET_DEFINITIONS
;

ALTER TABLE WIDGET_DEFINITIONS
ADD (INITIALIZER VARCHAR2(256) NOT NULL)
;

ALTER TABLE WIDGET_DEFINITIONS
MODIFY (ACCESS_STRATEGY VARCHAR2(256) NOT NULL)
;

ALTER TABLE WIDGET_DEFINITIONS
RENAME COLUMN ACCESS_STRATEGY TO ACCESSOR
;

ALTER TABLE WIDGET_DEFINITIONS
DROP COLUMN CLASS_NAME
;

ALTER TABLE WIDGET_DEFINITIONS
MODIFY (WIDGET_CLASS VARCHAR2(256) NOT NULL)
;

ALTER TABLE WIDGET_DEFINITIONS
MODIFY (USERNAME VARCHAR2(50) NOT NULL)
;

ALTER TABLE WIDGET_DEFINITIONS
MODIFY (DESCRIPTION VARCHAR2(256) NOT NULL)
;

-- Номер ревизии: 458877, 45886 
-- Номер версии:  1.18
-- Комментарий: ограничение входа клиента
/*==============================================================*/
/* Table: CONNECTOR_INFO                                        */
/*==============================================================*/
create table CONNECTOR_INFO  (
   ID                      INTEGER                         not null,
   GUID                  VARCHAR(35)                 not null,
   TRUSTED            CHAR(1)                         not null,
   constraint PK_CONNECTOR_INFO primary key (ID)
);

create sequence S_CONNECTOR_INFO;

/*==============================================================*/
/* Index: CON_INFO_GUID_UN_IND                                  */
/*==============================================================*/
create unique index CON_INFO_GUID_UN_IND on CONNECTOR_INFO (
   GUID ASC
);
ALTER TABLE LOGINS DROP (LOGIN_TYPE);
ALTER TABLE LOGINS DROP (TRASTED);

Если у кого-то в таблице LOGINS было добавлено поле CSA_TYPE, то необходимо его удалить
ALTER TABLE LOGINS DROP (CSA_TYPE);

-- Номер ревизии: 45935 
-- Номер версии:  1.18
-- Комментарий: ограничение входа клиента
ALTER TABLE BUSINESS_DOCUMENTS ADD (CSA_GUID VARCHAR2(35));

-- Номер ревизии: 45954
-- Номер версии:  1.18
-- Комментарий: BUG051037: Срок действия карты при открытии ОМС 
ALTER TABLE CARD_LINKS  ADD (GFL_TB VARCHAR2(4));

ALTER TABLE CARD_LINKS  ADD (GFL_OSB VARCHAR2(4));

ALTER TABLE CARD_LINKS  ADD (GFL_VSP VARCHAR2(7));

-- Номер ревизии: 46013
-- Номер версии:  1.18
-- Комментарий: BUG050982: Неверное время окончания ПФП 

alter table PERSONAL_FINANCE_PROFILE add (START_PLANING_DATE TIMESTAMP );
update PERSONAL_FINANCE_PROFILE set START_PLANING_DATE = EXECUTION_DATE;

-- Номер ревизии: 46065
-- Номер версии:  1.18
-- Комментарий: ENH043835: привязывать gate-овые сообщения в журнале сообщений к login_id клиента. 
alter table CODLOG add (DOCUMENT_ID number);

-- Номер ревизии: 46189
-- Номер версии:  1.18
-- Комментарий: ENH043566: Отсутствие поиска по мере набора текста в поисковых полях (возможность поиска по отдельной БД).
/*==============================================================*/
/* Table: CHANGES_FOR_ASYNCH_SEARCH                             */
/*==============================================================*/
create table CHANGES_FOR_ASYNCH_SEARCH  (
   ID                   INTEGER                         not null,
   OBJECT_CLASS_NAME    VARCHAR2(250),
   OBJECT_KEY           VARCHAR2(100),
   OBJECT_STATE         VARCHAR2(100),
   constraint PK_CHANGES_FOR_ASYNCH_SEARCH primary key (ID)
);

create sequence S_CHANGES_FOR_ASYNCH_SEARCH;

-- Номер ревизии: 46210
-- Номер версии:  1.18
-- Комментарий: Добавление версии профиля ЕРМБ

ALTER TABLE ERMB_PROFILE ADD (VERSION INTEGER DEFAULT 1);
ALTER TABLE ERMB_PROFILE ADD (CONFIRM_VERSION INTEGER DEFAULT 0);

update ERMB_PROFILE set VERSION = 1;
update ERMB_PROFILE set CONFIRM_VERSION = 0;

ALTER TABLE ERMB_PROFILE MODIFY(VERSION  NOT NULL);
ALTER TABLE ERMB_PROFILE MODIFY(CONFIRM_VERSION  NOT NULL);

-- Номер ревизии: 46335
-- Номер версии:  1.18
-- Комментарий: BUG051513: Добавить настройку времени восстановления сервиса IQWave 

ALTER TABLE MONITORING_GATE_CONFIGS ADD DETERIORATION_TIME TIMESTAMP
go

ALTER TABLE MONITORING_GATE_CONFIGS ADD DEGRADATION_MESSAGE  VARCHAR2(500)
go

ALTER TABLE MONITORING_GATE_CONFIGS ADD DEGRADATION_RECOVERY_TIME NUMBER
go

ALTER TABLE MONITORING_GATE_CONFIGS ADD INACCESSIBLE_MESSAGE VARCHAR2(500)
go

ALTER TABLE MONITORING_GATE_CONFIGS ADD INACCESSIBLE_RECOVERY_TIME NUMBER
go

-- Номер ревизии: 46402
-- Номер версии:  1.18
-- Комментарий: BUG051513: Добавить настройку времени восстановления сервиса IQWave 

ALTER TABLE MONITORING_GATE_CONFIGS DROP COLUMN DETERIORATION_TIME
go

ALTER TABLE MONITORING_GATE_CONFIGS ADD DEGRADATION_DATE TIMESTAMP
go

ALTER TABLE MONITORING_GATE_CONFIGS ADD INACCESSIBLE_DATE TIMESTAMP
go

-- Номер ревизии: 46288
-- Номер версии:  1.18
-- Комментарий:  BUG051076: Ошибка в файле выгрузки заявки на предодобренную кредитную карту  

update DEPARTMENTS
set IS_CREDIT_CARD_OFFICE = '0'
where IS_CREDIT_CARD_OFFICE = '1'
and OFFICE is null or OFFICE = ''
go

-- Номер ревизии: 46660
-- Номер версии:  1.18
-- Комментарий:   CHG051444 Изменить режим ограниченной функциональности  

drop table CONNECTOR_INFO;
alter table USERS add (TRUSTED char(1) default 1);

-- Номер ревизии: 46684
-- Номер версии:  1.18
-- Комментарий:  Доработка по ЕРМБ(доработка xsd, веб сервисы, изменения в бизнес логике ч1.) 

alter table ERMB_CLIENT_PHONE
add (MOBILE_PHONE_OPERATOR varchar2(100) default '0' not null)
go
-- Номер ревизии: 46679
-- Номер версии:  1.18
-- Комментарий:   CHG051444 Изменить режим ограниченной функциональности  

alter table BUSINESS_DOCUMENTS drop (CSA_GUID);
alter table BUSINESS_DOCUMENTS add(LOGIN_TYPE varchar2(10));

-- Номер ревизии: 46721
-- Номер версии:  1.18
-- Комментарий:   Обеспечение доступа к информации по продуктам при недоступности внешней системы. Длительные поручения. Модель БД.

create table STORED_LONG_OFFER  (
   ID                   INTEGER                         not null,
   DEPARTMENT_ID        INTEGER,
   RESOURCE_ID          INTEGER,
   START_DATE           TIMESTAMP,
   END_DATE             TIMESTAMP,
   EXECUTION_EVENT_TYPE VARCHAR2(255),
   PAY_DAY              INTEGER,
   PERCENT              NUMBER(15,4),
   PRIORITY             INTEGER,
   SUM_TYPE             VARCHAR2(255),
   FRIENDLY_NAME        VARCHAR2(255),
   TYPE                 VARCHAR(100),
   HASH                 INTEGER,
   ENTITY_UPDATE_TIME   TIMESTAMP,
   OFFICE_OSB           VARCHAR2(5),
   OFFICE_TB            VARCHAR2(5),
   OFFICE_VSP           VARCHAR2(5),
   AMOUNT               NUMBER(15,4),
   AMOUNT_CURRENCY      CHAR(3),
   FLOOR_LIMIT_AMOUNT   NUMBER(15,4),
   FLOOR_LIMIT_AMOUNT_CURRENCY CHAR(3),
   constraint PK_STORED_LONG_OFFER primary key (ID)
)
go

create sequence S_STORED_LONG_OFFER;

create index "DXSTORED_LO_TO_DEPARTMENTS" on STORED_LONG_OFFER
(
   DEPARTMENT_ID
)
go

alter table STORED_LONG_OFFER
   add constraint FK_STORED_LO_DEPARTMENTS_REF foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
go


create index "DXSTORED_LO_TO_LINK" on STORED_LONG_OFFER
(
   RESOURCE_ID
)
go

alter table STORED_LONG_OFFER
   add constraint FK_STORED_LO_TO_LINK_REF foreign key (RESOURCE_ID)
      references LONG_OFFER_LINKS (ID)
go

-- Номер ревизии: 46879
-- Номер версии:  1.18
-- Комментарий:   BUG051662: Невозможно открыть форму оплаты (любую) 
ALTER TABLE STORED_ACCOUNT DROP COLUMN HASH;

ALTER TABLE STORED_ACCOUNT DROP COLUMN HASH_INFO;

ALTER TABLE STORED_CARD DROP COLUMN HASH;

ALTER TABLE STORED_CARD DROP COLUMN HASH_INFO;

ALTER TABLE STORED_DEPO_ACCOUNT DROP COLUMN HASH;

ALTER TABLE STORED_IMACCOUNT DROP COLUMN HASH;

ALTER TABLE STORED_LOAN DROP COLUMN HASH;

ALTER TABLE STORED_LONG_OFFER DROP COLUMN HASH;


-- Номер ревизии: 46966
-- Номер версии: 1.18
-- Комментарий: BUG052363: Использование смс-пароля для входа

alter table SMSPASSWORDS add (SESSION_ID varchar2(64))
go

create index SMSPASSWORDS_I_SESSION_ID on SMSPASSWORDS(SESSION_ID)
go

-- Номер ревизии: 46980
-- Номер версии: 1.18
-- Комментарий: ENH037025: Запрос статуса операции IQWave. 

create table PAYMENT_EXECUTION_RECORDS  (
   ID                   INTEGER                         not null,
   DOCUMENT_ID          INTEGER                         not null,
   JOB_NAME             VARCHAR2(128)                   not null,
   ADDING_DATE          TIMESTAMP,
   constraint PK_PAYMENT_EXECUTION_RECORDS primary key (ID)
)
go

create sequence S_PAYMENT_EXECUTION_RECORDS
go


-- Номер ревизии: 46986
-- Номер версии: 1.18
-- Комментарий: Интеграция с АС "Филиал СБ". Смена держателя номера мобильного телефона.
create table ERMB_TMP_PHONE  (
   ID                     INTEGER                         not null,
   PHONE_NUMBER           VARCHAR2(20)                    not null,
   MOBILE_PHONE_OPERATOR  VARCHAR2(100)                   not null,
   constraint PK_ERMB_TMP_PHONE primary key (ID)
)
go

create sequence S_ERMB_TMP_PHONE
go

create unique index ERMB_TMP_PHONE_UNIQUE_NUMBER on ERMB_TMP_PHONE (
    PHONE_NUMBER
)
go


-- Номер ревизии: 47024
-- Номер версии: 1.18
-- Комментарий: BUG052436: Исключение при редактировании биллинговой системы
create unique index NODE_ADAPTERS_ADAPTER_ID_INDX on NODE_ADAPTERS (
   ADAPTER_ID ASC
)
go

-- Номер ревизии: 47037
-- Номер версии: 1.18
-- Комментарий: BUG051371: Изменить значение времени ответа на запрос по умолчанию при проверке недоступности IQWave 
DELETE FROM MONITORING_GATE_CONFIGS
go

ALTER TABLE MONITORING_GATE_CONFIGS ADD DEGRADATION_AVAILABLE CHAR(1) NOT NULL
go

ALTER TABLE MONITORING_GATE_CONFIGS ADD INACCESSIBLE_AVAILABLE CHAR(1) NOT NULL
go

-- Номер ревизии: 47169
-- Номер версии: 1.18
-- Комментарий: BUG051371: Изменить значение времени ответа на запрос по умолчанию при проверке недоступности IQWave 
drop table MONITORING_GATE_CONFIGS
go

create table MONITORING_SERVICE_CONFIGS  (
   SERVICE              VARCHAR2(50)                    not null,
   STATE                VARCHAR2(15)                    not null,
   DEGRADATION_CONFIG_ID INTEGER                         not null,
   INACCESSIBLE_CONFIG_ID INTEGER                         not null,
   constraint PK_MONITORING_SERVICE_CONFIGS primary key (SERVICE)
)

go

create sequence S_MONITORING_SERVICE_CONFIGS
go

/*==============================================================*/
/* Table: MONITORING_STATE_CONFIGS                              */
/*==============================================================*/
create table MONITORING_STATE_CONFIGS  (
   ID                   INTEGER                         not null,
   AVAILABLE            CHAR(1)                         not null,
   USE                  CHAR(1)                         not null,
   TIMEOUT              NUMBER,
   MONITORING_TIME      NUMBER,
   MONITORING_COUNT     NUMBER,
   DETERIORATION_TIME   TIMESTAMP,
   MESSAGE              VARCHAR2(500),
   RECOVERY_TIME        NUMBER,
   AVAILABLE_CHANGE_INACTIVE_TYPE CHAR(1)                         not null,
   INACTIVE_TYPE        VARCHAR2(8),
   constraint PK_MONITORING_STATE_CONFIGS primary key (ID)
)

go

create sequence S_MONITORING_STATE_CONFIGS
go

create index "DXFK_MONITORING_GATE_D_CONFIGS" on MONITORING_SERVICE_CONFIGS
(
   DEGRADATION_CONFIG_ID
)
go

alter table MONITORING_SERVICE_CONFIGS
   add constraint FK_MONITORING_GATE_D_CONFIGS foreign key (DEGRADATION_CONFIG_ID)
      references MONITORING_STATE_CONFIGS (ID)
go


create index "DXFK_MONITORING_GATE_I_CONFIGS" on MONITORING_SERVICE_CONFIGS
(
   INACCESSIBLE_CONFIG_ID
)
go

alter table MONITORING_SERVICE_CONFIGS
   add constraint FK_MONITORING_GATE_I_CONFIGS foreign key (INACCESSIBLE_CONFIG_ID)
      references MONITORING_STATE_CONFIGS (ID)
go

-- Номер ревизии: 47250
-- Номер версии: 1.18
-- Комментарий: CHG052429: Оптимизировать механизм проводки оффлайн документов 
create table JOB_EXECUTION_MARKER(
   ID                   INTEGER              not null,   
   JOB_NAME             VARCHAR2(128)        not null,
   ACTUAL_DATE          TIMESTAMP            not null,
   constraint PK_JOB_EXECUTION_MARKER primary key (ID)
)
go

create sequence S_JOB_EXECUTION_MARKER
go
-- Номер ревизии: 47260 
-- Номер версии: 1.18
-- Комментарий: BUG052469: Исключение при сохранении настроек кредитов.
ALTER TABLE SETTING_LOAN MODIFY(DIRECTORY_AUTO NULL)
go
ALTER TABLE SETTING_LOAN MODIFY(FILE_NAME_AUTO  NULL)
go

-- Номер ревизии: 47444 
-- Номер версии: 1.18
-- Комментарий: BUG052756: Ошибка на странице "Шаблоны и платежи" 
alter table STORED_ACCOUNT drop constraint FK_STORED_A_TO_LINK_REF;
alter table STORED_CARD drop constraint FK_STORED_C_TO_LINK_REF;
alter table STORED_DEPO_ACCOUNT drop constraint STORED_DA_TO_LINK_REF;
alter table STORED_IMACCOUNT drop constraint FK_STORED_I_TO_LINK_REF;
alter table STORED_LOAN drop constraint STORED_L_TO_LINK_REF;
alter table STORED_LONG_OFFER drop constraint FK_STORED_LO_TO_LINK_REF;

alter table STORED_ACCOUNT
   add constraint FK_STORED_A_TO_LINK_REF foreign key (RESOURCE_ID)
      references ACCOUNT_LINKS (ID)
      on delete cascade;
      
alter table STORED_CARD
   add constraint FK_STORED_C_TO_LINK_REF foreign key (RESOURCE_ID)
      references CARD_LINKS (ID)
      on delete cascade;
      
alter table STORED_DEPO_ACCOUNT
   add constraint STORED_DA_TO_LINK_REF foreign key (RESOURCE_ID)
      references DEPO_ACCOUNT_LINKS (ID)
      on delete cascade;
      
alter table STORED_IMACCOUNT
   add constraint FK_STORED_I_TO_LINK_REF foreign key (RESOURCE_ID)
      references IMACCOUNT_LINKS (ID)
      on delete cascade;
      
alter table STORED_LOAN
   add constraint STORED_L_TO_LINK_REF foreign key (RESOURCE_ID)
      references LOAN_LINKS (ID)
      on delete cascade;
      
alter table STORED_LONG_OFFER
   add constraint FK_STORED_LO_TO_LINK_REF foreign key (RESOURCE_ID)
      references LONG_OFFER_LINKS (ID)
      on delete cascade;


-- Номер ревизии: 47422 
-- Номер версии: 1.18
-- Комментарий: BUG052419 Не действует режим ограниченной функциональности в mAPI
/*==============================================================*/
/* Table: CONNECTORS_INFO                                       */
/*==============================================================*/
create table CONNECTORS_INFO  (
   ID                   INTEGER                          not null,
   GUID                 VARCHAR2(35)                    not null,
   LOGIN_TYPE           VARCHAR2(10)                    not null,
   constraint PK_CONNECTORS_INFO primary key (ID)
)

go

create sequence S_CONNECTORS_INFO
go

/*==============================================================*/
/* Index: USR_CON_INFO_UN_IND                                   */
/*==============================================================*/
create unique index USR_CON_INFO_UN_IND on CONNECTORS_INFO (
   GUID ASC
)
go

-- Номер ревизии: 47500
-- Номер версии: 1.18
-- Комментарий: ЕРМБ: Доработка функциональности "Настройка подключения" + доработка листнера АС "Филиал СБ". Модель БД (ч1) 

alter table ERMB_PROFILE
    add (DEPOSITS_TRANSFER CHAR(1) default '1' not null) 
go

-- Номер ревизии: 47621
-- Номер версии: 1.18
-- Комментарий: CHG052853: Изменить текст смс-оповещения при превышении лимита ИМСИ. 

alter table BUSINESS_PROPERTIES modify (VALUE varchar2(1024));

-- Номер ревизии: 47754
-- Номер версии: 1.18
-- Комментарий: ENH044725: mAPI 5. Вынести стандартные шаблоны форм платежа в отдельный файл.
/*==============================================================*/
/* Table: PAYMENT_FORM_IMPORTS                                  */
/*==============================================================*/
create table PAYMENT_FORM_IMPORTS  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(256)                   not null,
   BODY                 CLOB                            not null,
   constraint PK_PAYMENT_FORM_IMPORTS primary key (ID),
   constraint AK_FORM_IMPORT_NAME unique (NAME)
);
create sequence S_PAYMENT_FORM_IMPORTS;


-- Номер ревизии: 47856
-- Номер версии: 1.18
-- Комментарий: Скрипт не актуален, актуальный скрипт приведен в ревизии 49478. (Не актуален только в плане конвертации поэтому ревизия такая же + в новом скрипте присутствует удаление ненужных полей поставщика). Лимиты по автоплатежам(Доработка АРМ сотрудника банка)

create table AUTOPAY_SETTINGS(
   ID                   INTEGER                         not null,
   TYPE                 VARCHAR2(20)                    not null,
   RECIPIENT_ID         INTEGER                         not null,
   PARAMETERS           CLOB                            not null,
   constraint PK_AUTOPAY_SETTING primary key (ID)
)
go

create sequence S_AUTOPAY_SETTINGS
go

alter table AUTOPAY_SETTINGS
   add constraint FK_AUTOPAY_SETTING_TO_PROV foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go

create index "DXFK_AUTOPAY_SETTING_TO_PROV" on AUTOPAY_SETTINGS
(
   RECIPIENT_ID
)
go

DECLARE
autopayParams CLOB; -- параметры автоплатежа
BEGIN
FOR serviceProvidersRow IN 
(SELECT ID,
IS_THRESHOSD_AUTOPAY_SUPPORTED, 
MIN_SUMMA_THRESHOLD, 
MAX_SUMMA_THRESHOLD, 
IS_INTERVAL_THRESHOLD, 
MIN_VALUE_THRESHOLD, 
MAX_VALUE_THRESHOLD, 
DISCRETE_VALUE_THRESHOLD,
CLIENT_HINT_THRESHOLD,
IS_ALWAYS_AUTOPAY_SUPPORTED,
MIN_SUMMA_ALWAYS,
MAX_SUMMA_ALWAYS,
CLIENT_HINT_ALWAYS,
IS_INVOICE_AUTOPAY_SUPPORTED,
CLIENT_HINT_INVOICE
FROM SERVICE_PROVIDERS WHERE IS_AUTOPAYMENT_SUPPORTED = '1') LOOP
    IF(serviceProvidersRow.IS_THRESHOSD_AUTOPAY_SUPPORTED = '1') THEN   
        -- формируем параметры
        autopayParams := 
                '<entity key="THRESHOLD">'
                    ||'<field name="clientHint">'||serviceProvidersRow.CLIENT_HINT_THRESHOLD||'</field>'
                    ||'<field name="maxSumThreshold">'||serviceProvidersRow.MAX_SUMMA_THRESHOLD||'</field>'
                    ||'<field name="minSumThreshold">'||serviceProvidersRow.MIN_SUMMA_THRESHOLD||'</field>'
                    ||'<field name="maxValueThreshold">'||serviceProvidersRow.MAX_VALUE_THRESHOLD||'</field>'
                    ||'<field name="minValueThreshold">'||serviceProvidersRow.MIN_VALUE_THRESHOLD||'</field>'
                    ||'<field name="accessTotalMaxSum">false</field>'
                    ||'<field name="totalMaxSumERIB"></field>'
                    ||'<field name="totalMaxSumMAPI"></field>'
                    ||'<field name="totalMaxSumATM"></field>';
        IF(serviceProvidersRow.IS_INTERVAL_THRESHOLD = '1') THEN
            autopayParams := autopayParams
                    ||'<field name="interval">true</field>';
        ELSE     
            autopayParams := autopayParams
                    ||'<field name="interval">false</field>';     
        END IF;
        autopayParams := autopayParams
                    ||'<field name="discreteValues">'||serviceProvidersRow.DISCRETE_VALUE_THRESHOLD||'</field>'
                    ||'</entity>';
        -- вставляем запись
        insert into AUTOPAY_SETTINGS(ID, TYPE, RECIPIENT_ID, PARAMETERS) values (S_AUTOPAY_SETTINGS.nextval, 'THRESHOLD', serviceProvidersRow.ID, autopayParams);
    END IF;
    IF(serviceProvidersRow.IS_ALWAYS_AUTOPAY_SUPPORTED = '1') THEN   
        -- формируем параметры
        autopayParams := 
                '<entity key="ALWAYS">'
                    ||'<field name="clientHint">'||serviceProvidersRow.CLIENT_HINT_ALWAYS||'</field>'
                    ||'<field name="minSumAlways">'||serviceProvidersRow.MIN_SUMMA_ALWAYS||'</field>'
                    ||'<field name="maxSumAlways">'||serviceProvidersRow.MAX_SUMMA_ALWAYS||'</field>'
                    ||'</entity>';
        -- вставляем запись            
        insert into AUTOPAY_SETTINGS(ID, TYPE, RECIPIENT_ID, PARAMETERS) values (S_AUTOPAY_SETTINGS.nextval, 'ALWAYS', serviceProvidersRow.ID, autopayParams);            
    END IF;
    IF(serviceProvidersRow.IS_INVOICE_AUTOPAY_SUPPORTED = '1') THEN   
        -- формируем параметры
        autopayParams := 
                '<entity key="INVOICE">'
                    ||'<field name="clientHint">'||serviceProvidersRow.CLIENT_HINT_INVOICE||'</field>'
                    ||'</entity>';
        -- вставляем запись            
        insert into AUTOPAY_SETTINGS(ID, TYPE, RECIPIENT_ID, PARAMETERS) values (S_AUTOPAY_SETTINGS.nextval, 'INVOICE', serviceProvidersRow.ID, autopayParams);                        
    END IF;   
END LOOP;
END;
go



-- Номер ревизии: 47642
-- Номер версии: 1.18
-- Комментарий: Добавил индекс в QUICK_PAYMENT_PANELS_LOG по TB, START_DATE.
create index QUICK_PAYMENT_STATISTICS on QUICK_PAYMENT_PANELS_LOG (
   TB ASC,
   START_DATE ASC
)
go

-- Номер ревизии: 47889
-- Номер версии: 1.18
-- Комментарий:  Изменения старых данных клиента в профиле ч.1
create table PERSON_OLD_IDENTITY  (
   ID                   INTEGER                         not null,
   FIRST_NAME           VARCHAR2(120)                   not null,
   SUR_NAME             VARCHAR2(120)                   not null,
   PATR_NAME            VARCHAR2(120),
   BIRTHDAY             TIMESTAMP(6)                    not null,
   DOC_TYPE             VARCHAR2(32)                    not null,
   DOC_SERIES           VARCHAR2(16),
   DOC_NUMBER           VARCHAR2(16),
   PERSON_ID            INTEGER                         not null,
   EMPLOYEE_ID          INTEGER,
   DATE_CHANGE          TIMESTAMP(6),
   constraint PK_PERSON_OLD_IDENTITY primary key (ID)
)

go

create sequence S_PERSON_OLD_IDENTITY
go

create unique index UNIQ_PERSON_OLD_IDENTITY on PERSON_OLD_IDENTITY (
   FIRST_NAME ASC,
   SUR_NAME ASC,
   PATR_NAME ASC,
   BIRTHDAY ASC,
   DOC_TYPE ASC,
   DOC_SERIES ASC,
   DOC_NUMBER ASC
)
go


create index "DXFK_PERSON_OLD_IDENTITY_USERS" on PERSON_OLD_IDENTITY
(
   PERSON_ID
)
go

alter table PERSON_OLD_IDENTITY
   add constraint FK_PERSON_OLD_IDENTITY_USERS foreign key (PERSON_ID)
      references USERS (ID)
      on delete cascade
go


create index "DXFK_PERS_OLD_IDEN_EMPLOYEE" on PERSON_OLD_IDENTITY
(
   EMPLOYEE_ID
)
go

alter table PERSON_OLD_IDENTITY
   add constraint FK_PERS_OLD_IDEN_EMPLOYEE foreign key (EMPLOYEE_ID)
      references LOGINS (ID)
go

-- Номер ревизии: 47913
-- Номер версии: 1.18
-- Комментарий: BUG051608: Некорректно отображается виджет «Twitter» после перетаскивания.
alter table WIDGET_DEFINITIONS
add MAX_COUNT NUMBER default 5 not null;

-- Номер ревизии: 47938
-- Номер версии: 1.18
-- Комментарий: АЛФ Планирование: Реализована настройка доступности вкладов для открытия целей в АРМ Сотрудника

alter table DEPOSITDESCRIPTIONS
    add (ALLOWED_TARGET CHAR(1) default '0' not null) 
go


-- Номер ревизии: 47948
-- Номер версии: 1.18
-- Комментарий: АЛФ Планирование: Создать сущность "Цель", сервис и тест.

create table ACCOUNT_TARGETS  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   NAME                 VARCHAR2(35)                    not null,
   NAME_COMMENT         VARCHAR2(100),
   PLANED_DATE          TIMESTAMP                       not null,
   AMOUNT               NUMBER(20,4)                    not null,
   AMOUNT_CURRENCY      CHAR(3)                         not null,
   START_AMOUNT         NUMBER(20,4)                    not null,
   START_AMOUNT_CURRENCY CHAR(3)                         not null,
   DEPOSIT_ID           INTEGER                         not null,
   ACCOUNT_NUM          VARCHAR2(25),
   constraint PK_ACCOUNT_TARGETS primary key (ID)
)

go

create sequence S_ACCOUNT_TARGETS
go

create index "DXFK_A_TARGETS_FK_LOGINS" on ACCOUNT_TARGETS
(
   LOGIN_ID
)
go

alter table ACCOUNT_TARGETS
   add constraint FK_A_TARGETS_FK_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
go

-- Номер ревизии: 47959
-- Номер версии: 1.18
-- Комментарий: Страховые и НПФ продукты клиента
create table INSURANCE_LINKS  (
   ID                   INTEGER                         not null,
   EXTERNAL_ID          VARCHAR(70)                     not null,
   LOGIN_ID             INTEGER,
   REFERENCE            VARCHAR(32)                     not null,
   INSURANCE_NAME       VARCHAR(50)                     not null,
   SHOW_IN_SYSTEM       CHAR(1)                         not null,
   BUSINESS_PROCESS     VARCHAR(12)                     not null,
   constraint PK_INSURANCE_LINKS primary key (ID)
)

go

create sequence S_INSURANCE_LINKS
go

create unique index UNIQ_INS_NUMBER on INSURANCE_LINKS (
   REFERENCE ASC,
   LOGIN_ID ASC
)
go

-- Номер ревизии: 47990
-- Номер версии: 1.18
-- Комментарий: Изменения старых данных клиента в профиле ч.2


delete from  PERSON_OLD_IDENTITY
go

alter table PERSON_OLD_IDENTITY
    rename column PERSON_ID to ERMB_PROFILE_ID
go

alter table PERSON_OLD_IDENTITY
    drop constraint FK_PERSON_OLD_IDENTITY_USERS
go


alter table PERSON_OLD_IDENTITY
   add constraint FK_PERS_OLD_IDEN_ERMB_PROFILE foreign key (ERMB_PROFILE_ID)
      references  ERMB_PROFILE(ID)
      on delete cascade
GO


create index "DXFK_PERS_OLD_IDEN_ERMB_PROFIL" on PERSON_OLD_IDENTITY
(
   ERMB_PROFILE_ID
)
go

-- Номер ревизии: 48011
-- Номер версии: 1.18
-- Комментарий: Оповещение ЕРИБ об изменении данных клиента (ЕРМБ->ЕРИБ)

ALTER TABLE ERMB_PROFILE
    MODIFY(TIME_ZONE  NOT NULL)
go
    
delete from PERSON_OLD_IDENTITY
go

ALTER TABLE PERSON_OLD_IDENTITY
    MODIFY(DATE_CHANGE  NOT NULL)
go

DROP INDEX UNIQ_PERSON_OLD_IDENTITY
go

ALTER TABLE PERSON_OLD_IDENTITY DROP COLUMN DOC_TYPE
go

ALTER TABLE PERSON_OLD_IDENTITY DROP COLUMN DOC_SERIES
go

ALTER TABLE PERSON_OLD_IDENTITY DROP COLUMN DOC_NUMBER
go

ALTER TABLE PERSON_OLD_IDENTITY
    ADD (DOCUMENT_ID  INTEGER NOT NULL)
go

ALTER TABLE PERSON_OLD_IDENTITY
    ADD CONSTRAINT FK_PERS_OLD_IDEN_DOCUMENTS FOREIGN KEY (DOCUMENT_ID) REFERENCES DOCUMENTS (ID)
go

-- Номер ревизии: 48022
-- Номер версии: 1.18
-- Комментарий: Оповещение ЕРИБ об изменении данных клиента (ЕРМБ->ЕРИБ) (докоммит)

create index "DXFK_PERS_OLD_IDEN_DOCUMENTS" on PERSON_OLD_IDENTITY(DOCUMENT_ID)
go


-- Номер ревизии: 48032
-- Номер версии: 1.18
-- Комментарий: АЛФ Планирование: Реализовать отображение страницы «Просмотр списка целей»

alter table ACCOUNT_TARGETS add DICTIONARY_TARGET VARCHAR2(15) not null
go

-- Номер ревизии: 48104
-- Номер версии: 1.18
-- Комментарий: ЕРМБ.Изменения старых данных клиента в профила(ч.1)

delete from  PERSON_OLD_IDENTITY
go
alter table PERSON_OLD_IDENTITY
    add (STATUS VARCHAR2(12) not null)
go


-- Номер ревизии: 48116
-- Номер версии: 1.18
-- Комментарий: Обновление списка продуктов клиента для СМС-канала ЕРМБ

/*==============================================================*/
/* Table: USER_TIMINGS                                          */
/*==============================================================*/
create table USER_TIMINGS  (
   USER_ID              INTEGER                         not null,
   ACCOUNT_LIST_LAST_UPDATE TIMESTAMP,
   CARD_LIST_LAST_UPDATE TIMESTAMP,
   LOAN_LIST_LAST_UPDATE TIMESTAMP,
   constraint PK_USER_TIMINGS primary key (USER_ID)
)

;

create sequence S_USER_TIMINGS
;

comment on column USER_TIMINGS.USER_ID is
'ID клиента'
;

comment on column USER_TIMINGS.ACCOUNT_LIST_LAST_UPDATE is
'Время последнего обновления списка счетов клиента данными из КСШ'
;

comment on column USER_TIMINGS.CARD_LIST_LAST_UPDATE is
'Время последнего обновления списка карт клиента данными из КСШ'
;

comment on column USER_TIMINGS.LOAN_LIST_LAST_UPDATE is
'Время последнего обновления списка кредитов клиента данными из КСШ'
;

alter table USER_TIMINGS
   add constraint FK_USER_TIMINGS foreign key (USER_ID)
      references USERS (ID)
      on delete cascade
;


-- Номер ревизии: 48189
-- Номер версии: 1.18
-- Комментарий: АЛФ Планирование: Реализовать отображение страницы «Ввод параметров цели» (часть 1)

alter table ACCOUNT_TARGETS modify START_AMOUNT null
go

alter table ACCOUNT_TARGETS modify START_AMOUNT_CURRENCY null
go

alter table ACCOUNT_TARGETS add ACCOUNT_LINK INTEGER
go

create index "DXFK_A_TARGETS_FK_ACCOUNT_LINK" on ACCOUNT_TARGETS
(
   ACCOUNT_LINK
)
go

alter table ACCOUNT_TARGETS
   add constraint FK_A_TARGETS_FK_ACCOUNT_LINK foreign key (ACCOUNT_LINK)
      references ACCOUNT_LINKS (ID)
      on delete cascade
go


-- Номер ревизии: 48195
-- Номер версии: 1.18
-- Комментарий: АЛФ Планирование: Откатить настройку доступности вкладов для открытия целей в АРМ Сотрудника

alter table ACCOUNT_TARGETS drop column DEPOSIT_ID
go

alter table DEPOSITDESCRIPTIONS drop column ALLOWED_TARGET 
go

-- Номер ревизии: 48243
-- Номер версии: 1.18
-- Комментарий: Перевыпуск карт не по месту ведения

alter table DEPARTMENTS add REISSUED_CARD char(1) null
go

-- Номер ревизии: 48338
-- Номер версии: 1.18
-- Комментарий: Управление текстами смс в ЕРИБ. Модель БД.

/*==============================================================*/
/* Table: SMS_TEMPLATE                                          */
/*==============================================================*/
create table SMS_TEMPLATE  (
   ID                   INTEGER                         not null,
   SMS_TYPE             VARCHAR2(13)                    not null,
   KEY                  VARCHAR2(255)                   not null,
   TEXT                 CLOB                            not null,
   CUSTOM               CHAR(1),
   CHANNEL              VARCHAR2(20),
   DESCRIPTION          VARCHAR2(255),
   constraint PK_SMS_TEMPLATE primary key (ID)
);

create sequence S_SMS_TEMPLATE;

/*==============================================================*/
/* Index: INDEX_SMS_TYPE                                        */
/*==============================================================*/
create index INDEX_SMS_TYPE on SMS_TEMPLATE (
   SMS_TYPE ASC
);

/*==============================================================*/
/* Index: INDEX_SMS_CHANNEL_KEY                                 */
/*==============================================================*/
create index INDEX_SMS_CHANNEL_KEY on SMS_TEMPLATE (
   KEY ASC,
   CHANNEL ASC
);

-- Номер ревизии: 48342
-- Номер версии: 1.18
-- Комментарий: Управление текстами смс в ЕРИБ. Модель БД. Fix

DROP INDEX INDEX_SMS_CHANNEL_KEY;
DROP INDEX INDEX_SMS_TYPE;

DROP SEQUENCE S_SMS_TEMPLATE; 

ALTER TABLE SMS_TEMPLATE
  RENAME TO SMS_RESOURCES;
  
create sequence S_SMS_RESOURCES;

create index INDEX_SMS_TYPE on SMS_RESOURCES (
   SMS_TYPE ASC
);

create index INDEX_SMS_CHANNEL_KEY on SMS_RESOURCES (
   KEY ASC,
   CHANNEL ASC
);


-- Номер ревизии: 48377
-- Номер версии: 1.18
-- Комментарий: автоматический СМС-алиас для счёт-линков, кард-линков и кредит-линков

ALTER TABLE ACCOUNT_LINKS
ADD SMS_AUTO_ALIAS VARCHAR2(20)
;

ALTER TABLE CARD_LINKS
ADD SMS_AUTO_ALIAS VARCHAR2(20)
;

ALTER TABLE LOAN_LINKS
ADD SMS_AUTO_ALIAS VARCHAR2(20)
;

-- Номер ревизии: 48443
-- Номер версии: 1.18
-- Индивидуальные курсы
ALTER TABLE RATE ADD TARIF_PLAN_CODE VARCHAR2(20);

-- Номер ревизии: 48473
-- Номер версии: 1.18
-- Комментарий: BUG053687: Некорректное сообщение при повторном добавлении виджета Twitter.
alter table WIDGET_DEFINITIONS
add MAX_COUNT_MESSAGE VARCHAR2(256) default 'Вы не можете добавить более 5 одинаковых виджетов.' not null;


-- Номер ревизии: 48518
-- Номер версии: 1.18
-- Комментарий: удаление столбца OPERATION_UUID в рамках исправления ошибок кодирования.

update BUSINESS_DOCUMENTS set OPERATION_UID = OPERATION_UUID where OPERATION_UUID is not null and KIND = 'P'
go

alter table BUSINESS_DOCUMENTS drop column OPERATION_UUID
go


-- Номер ревизии: 48516
-- Номер версии: 1.18
-- Комментарий: логирование кода подтверждения в журнале подтвреждений(БД)
ALTER TABLE OPERATION_CONFIRM_LOG ADD CONFIRM_CODE VARCHAR2(32)
go

-- Номер ревизии: 48430, 48466 
-- Номер версии: 1.18
-- Комментарий: 1. ЕРМБ. PersonOldIdentity должен быть связан с USER-ом, а не с профилем ЕРМБ; 2. ЕРМБ. PersonOldIdentity должен хранить в себе поля документа, вместо внешего ключа на DOCUMENTS
delete from  PERSON_OLD_IDENTITY;
alter table PERSON_OLD_IDENTITY drop column ERMB_PROFILE_ID;
alter table PERSON_OLD_IDENTITY add (PERSON_ID  integer not null);
alter table PERSON_OLD_IDENTITY add constraint FK_PERS_OLD_IDEN_USERS foreign key (PERSON_ID)
      references USERS (ID) on delete cascade;
create index "DXFK_PERS_OLD_IDEN_USERS" on PERSON_OLD_IDENTITY
(
   PERSON_ID
);

alter table PERSON_OLD_IDENTITY drop column DOCUMENT_ID;
alter table PERSON_OLD_IDENTITY add (DOC_TYPE  VARCHAR2(32));
alter table PERSON_OLD_IDENTITY add (DOC_NAME  VARCHAR2(128));
alter table PERSON_OLD_IDENTITY add (DOC_NUMBER  VARCHAR2(16));
alter table PERSON_OLD_IDENTITY add (DOC_SERIES  VARCHAR2(16));
alter table PERSON_OLD_IDENTITY add (DOC_ISSUE_DATE  TIMESTAMP);
alter table PERSON_OLD_IDENTITY add (DOC_ISSUE_BY  VARCHAR2(255));
alter table PERSON_OLD_IDENTITY add (DOC_ISSUE_BY_CODE  VARCHAR2(16));
alter table PERSON_OLD_IDENTITY add (DOC_MAIN  VARCHAR2(1));
alter table PERSON_OLD_IDENTITY add (DOC_TIME_UP_DATE  TIMESTAMP);
alter table PERSON_OLD_IDENTITY add (DOC_IDENTIFY  VARCHAR2(1));

-- Номер ревизии: 48577
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. ErmbProfile: PK = FK на USER.ID
drop table ERMB_CLIENT_PHONE;
drop table ERMB_PROFILE;

create table ERMB_PROFILE  (
   PERSON_ID            INTEGER                         not null,
   OLD_FIRST_NAME       VARCHAR2(42),
   OLD_SUR_NAME         VARCHAR2(42),
   OLD_PATR_NAME        VARCHAR2(42),
   OLD_BIRTHDAY         TIMESTAMP,
   OLD_DOCUMENT_ID      INTEGER,
   SERVICE_STATUS       VARCHAR2(13)                    not null,
   END_SERVICE_DATE     TIMESTAMP,
   FOREG_PRODUCT_ID     INTEGER,
   CONNECTION_DATE      TIMESTAMP,
   NEW_PRODUCT_NOTIFICATION CHAR(1)                        default '0' not null,
   DAYS_OF_WEEK         VARCHAR2(28),
   TIME_START           VARCHAR2(10),
   TIME_END             VARCHAR2(10),
   TIME_ZONE            INTEGER                         not null,
   ERMB_TARIF_ID        INTEGER                         not null,
   CLIENT_CATEGORY      CHAR(1),
   LAST_REQUEST_TIME    TIMESTAMP,
   FAST_SERVICE         CHAR(1)                        default '0' not null,
   ADVERTISING          CHAR(1)                        default '1' not null,
   VERSION              INTEGER                        default 1 not null,
   CONFIRM_VERSION      INTEGER                        default 0 not null,
   DEPOSITS_TRANSFER    CHAR(1)                        default '1' not null,
   constraint PK_ERMB_PROFILE primary key (PERSON_ID)
);

create table ERMB_CLIENT_PHONE  (
   ID                   INTEGER                         not null,
   PROFILE_ID           INTEGER                         not null,
   IS_MAIN              CHAR(1)                        default '0' not null,
   PHONE_NUMBER         VARCHAR2(20)                    not null,
   MOBILE_PHONE_OPERATOR VARCHAR2(100)                  default '0' not null,
   constraint PK_ERMB_CLIENT_PHONE primary key (ID)
);

create unique index UNIQ_ERMB_PHONE_NUMBER on ERMB_CLIENT_PHONE (
   PHONE_NUMBER ASC
);

create index "DXFK_ERMB_CLIENT_PHONE_PROFILE" on ERMB_CLIENT_PHONE
(
   PROFILE_ID
);

alter table ERMB_CLIENT_PHONE
   add constraint FK_ERMB_CLIENT_PHONE_PROFILE foreign key (PROFILE_ID)
      references ERMB_PROFILE (PERSON_ID)
      on delete cascade;

create index "DXFK_ERMB_PROFILE_OLD_DOCUMENT" on ERMB_PROFILE
(
   OLD_DOCUMENT_ID
);

alter table ERMB_PROFILE
   add constraint FK_ERMB_PROFILE_OLD_DOCUMENT foreign key (OLD_DOCUMENT_ID)
      references DOCUMENTS (ID);

create index "DXFK_ERMB_PROFILE_TARIF" on ERMB_PROFILE
(
   ERMB_TARIF_ID
);

alter table ERMB_PROFILE
   add constraint FK_ERMB_PROFILE_TARIF foreign key (ERMB_TARIF_ID)
      references ERMB_TARIF (ID);

alter table ERMB_PROFILE
   add constraint FK_ERMB_PROFILE_USERS foreign key (PERSON_ID)
      references USERS (ID);



-- Номер ревизии: 48599
-- Номер версии: 1.18
-- Комментарий: АЛФ Планирование: привязка цели к заявке на открытие вклада 


alter table ACCOUNT_TARGETS drop COLUMN START_AMOUNT 
go

alter table ACCOUNT_TARGETS drop COLUMN START_AMOUNT_CURRENCY 
go

alter table ACCOUNT_TARGETS add CLAIM_ID INTEGER
go

alter table ACCOUNT_TARGETS drop constraint FK_A_TARGETS_FK_ACCOUNT_LINK
go

alter table ACCOUNT_TARGETS
   add constraint FK_A_TARGETS_FK_ACCOUNT_LINK foreign key (ACCOUNT_LINK)
      references ACCOUNT_LINKS (ID)
go

-- Номер ревизии: 48608
-- Номер версии: 1.18
-- Комментарий: Редактирование поставщика: добавление блока "настройка видимости в каналах"

ALTER TABLE SERVICE_PROVIDERS
ADD ( VISIBLE_PAYMENTS_FOR_IB CHAR(1) DEFAULT '0' NOT NULL );

ALTER TABLE SERVICE_PROVIDERS
ADD ( VISIBLE_PAYMENTS_FOR_M_API CHAR(1) DEFAULT '0' NOT NULL );

ALTER TABLE SERVICE_PROVIDERS
ADD ( VISIBLE_PAYMENTS_FOR_ATM_API CHAR(1) DEFAULT '0' NOT NULL );

-- Номер ревизии: 48616
-- Номер версии: 1.18
-- Комментарий: Редактирование группы услуг
alter table PAYMENT_SERVICES add IS_CATEGORY CHAR(1) default '0' NOT NULL ;
alter table PAYMENT_SERVICES add SHOW_IN_API CHAR(1) default '1' NOT NULL ;
alter table PAYMENT_SERVICES add SHOW_IN_ATM CHAR(1) default '1' NOT NULL ;
alter table PAYMENT_SERVICES add SHOW_IN_SYSTEM CHAR(1) default '1' NOT NULL ;

-- Номер ревизии: 48630
-- Номер версии: 1.18
-- Комментарий: ЕРМБ (Транспортный канал): прием результата проверки IMSI
create table ERMB_CHECK_IMSI_RESULTS  (
   SMS_UID              VARCHAR(32)                     not null,
   RESULT               CHAR(1),
   constraint PK_ERMB_CHECK_IMSI_RESULTS primary key (SMS_UID)
);

-- Номер ревизии: 48639
-- Номер версии: 1.18
-- Комментарий: CHG054318: Убрать поиск по подстроке в журналах (в части аудита и ДУЛ).
create index DUL_INDEX on DOCUMENTS (
   REPLACE("DOC_SERIES"||"DOC_NUMBER",' ','') ASC
)
go

-- Номер ревизии: 48678
-- Номер версии: 1.18
-- Комментарий: Управление текстами смс в ЕРИБ. Модель БД. Ошибки кодирования

alter table MESSAGE_TEMPLATES add KEY VARCHAR2(255);
alter table MESSAGE_TEMPLATES add DESCRIPTION VARCHAR2(255);

-- Номер ревизии: 48689
-- Номер версии: 1.18
-- Комментарий: ERMB. Удаление мусора из ErmbProfileImpl. Модель БД.
alter table ERMB_PROFILE drop column OLD_FIRST_NAME;
alter table ERMB_PROFILE drop column OLD_SUR_NAME;
alter table ERMB_PROFILE drop column OLD_PATR_NAME;
alter table ERMB_PROFILE drop column OLD_BIRTHDAY;
alter table ERMB_PROFILE drop column OLD_DOCUMENT_ID;


-- Номер ревизии: 48710
-- Номер версии: 1.18
-- Комментарий: Маппинг ошибок(часть 1. Создание сущностей, запись ошибок за стратсом).
create table EXCEPTION_ENTRY  (
   ID                   INTEGER                         not null,
   HASH                 VARCHAR2(40)                    not null,
   OPERATION            VARCHAR2(56),
   APPLICATION          VARCHAR2(20)                    not null,
   MESSAGE              CLOB                            not null,
   constraint PK_EXCEPTION_ENTRY primary key (ID)
)
go

create sequence S_EXCEPTION_ENTRY
go

-- Номер ревизии: 48726
-- Номер версии: 1.18
-- Комментарий: BUG054350: Не работает проверка на обновление курсов валют при подтверждении платежа
alter table PROFILE add TARIF_PLAN_CODE VARCHAR2(20);

-- Номер ревизии: 48730
-- Номер версии: 1.18
-- Комментарий: Степень риска для групп риска
alter table GROUPS_RISK add (RANK varchar2(5)  default 'HIGH' not null);

-- Номер ревизии: 48763
-- Номер версии: 1.18
-- Комментарий: Маппинг ошибок(часть 2. Запись ошибок jsp)
alter table EXCEPTION_ENTRY modify (OPERATION VARCHAR2(160))
go

create unique index IND_EXCEPTION_ENTRY_HASH on EXCEPTION_ENTRY (HASH ASC)
go

-- Номер ревизии: 48768
-- Номер версии: 1.18
-- Комментарий: Создать справочник классификации внешних ошибок
ALTER TABLE EXCEPTION_ENTRY RENAME COLUMN MESSAGE TO DETAIL;

ALTER TABLE EXCEPTION_ENTRY ADD KIND CHAR(1);

UPDATE EXCEPTION_ENTRY SET KIND = 'I';

ALTER TABLE EXCEPTION_ENTRY MODIFY KIND NOT NULL;

ALTER TABLE EXCEPTION_ENTRY ADD MESSAGE VARCHAR2(2000);

ALTER TABLE EXCEPTION_ENTRY ADD SYSTEM VARCHAR2(16);

-- Номер ревизии: 48771
-- Номер версии: 1.18
-- Комментарий: Редактирование группы услуг. ч.2
create table PAYMENT_SERV_PARENTS  (
   SERVICE_ID           INTEGER                         not null,
   PARENT_ID            INTEGER                         not null,
   constraint PK_PAYMENT_SERV_PARENTS primary key (SERVICE_ID, PARENT_ID)
);
create sequence S_PAYMENT_SERV_PARENTS;
create index "DXFK_PAY_SER_TO_PARENT" on PAYMENT_SERV_PARENTS
(
   PARENT_ID
);
alter table PAYMENT_SERV_PARENTS
   add constraint FK_PAY_SER_TO_PARENT foreign key (PARENT_ID)
      references PAYMENT_SERVICES (ID);
create index "DXFK_PAY_SER_TO_SERV" on PAYMENT_SERV_PARENTS
(
   SERVICE_ID
);
alter table PAYMENT_SERV_PARENTS
   add constraint FK_PAY_SER_TO_SERV foreign key (SERVICE_ID)
      references PAYMENT_SERVICES (ID);
--Если справочник услуг заполнен 'руками', а не загружен из справочника ЦАС НСИ необходимо выполнить скрипты:
INSERT INTO PAYMENT_SERVICES(ID, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM)
  VALUES(s_PAYMENT_SERVICES.nextval, '05', N'Налоги и штрафы, ГИБДД', NULL, NULL, '0', 'Налоги и штрафы, ГИБДД', '0', NULL, '0', '/payment_service/nalogi_gibdd.png', '1', '0', '0', '0');
INSERT INTO PAYMENT_SERVICES(ID, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM)
  VALUES(s_PAYMENT_SERVICES.nextval, '06', N'Образование', NULL, NULL, '0', 'Образование', '0', NULL, '0', '/payment_service/obrazovanie.png', '1', '0', '0', '0');
INSERT INTO PAYMENT_SERVICES(ID, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM)
  VALUES(s_PAYMENT_SERVICES.nextval, '07', N'Пенсионные фонды', NULL, NULL, '0', 'Пенсионные фонды', '0', NULL, '0', '/payment_service/pfr.png', '1', '0', '0', '0');
INSERT INTO PAYMENT_SERVICES(ID, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM)
  VALUES(s_PAYMENT_SERVICES.nextval, '18', N'Оплата товаров и услуг', NULL, NULL, '0', 'Оплата товаров и услуг', '0', NULL, '0', '/payment_service/oplata_uslug.png', '1', '0', '0', '0');
INSERT INTO PAYMENT_SERVICES(ID, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM)
  VALUES(s_PAYMENT_SERVICES.nextval, '99', N'Прочие', NULL, NULL, '0', 'Прочие', '0', NULL, '0', '/payment_service/prochie.png', '1', '0', '0', '0');
INSERT INTO PAYMENT_SERVICES(ID, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM)
  VALUES(s_PAYMENT_SERVICES.nextval, '01', N'Связь, Интернет и телевидение', NULL, NULL, '0', 'Связь, Интернет и телевидение', '0', NULL, '0', '/payment_service/swyaz.png', '1', '0', '0', '0');
INSERT INTO PAYMENT_SERVICES(ID, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM)
  VALUES(s_PAYMENT_SERVICES.nextval, '02', N'Переводы и обмен валют', NULL, NULL, '0', 'Переводы и обмен валют', '0', NULL, '0', '/payment_service/perevod.png', '1', '0', '0', '0');
--скрипты для привязки услуг в соответсвии с новой структурой
alter table PAYMENT_SERVICE_CATEGORIES add code varchar2(3);
alter table PAYMENT_SERVICE_CATEGORIES add service_id INTEGER;
update PAYMENT_SERVICE_CATEGORIES set code = '01'  where CATEGORY = 'COMMUNICATION';
update PAYMENT_SERVICE_CATEGORIES set code = '02'  where CATEGORY = 'TRANSFER';
update PAYMENT_SERVICE_CATEGORIES set code = '05'  where CATEGORY = 'TAX_PAYMENT';
update PAYMENT_SERVICE_CATEGORIES set code = '06'  where CATEGORY = 'EDUCATION';
update PAYMENT_SERVICE_CATEGORIES set code = '07'  where CATEGORY = 'PFR';
update PAYMENT_SERVICE_CATEGORIES set code = '18'  where CATEGORY = 'SERVICE_PAYMENT';
update PAYMENT_SERVICE_CATEGORIES set code = '99'  where CATEGORY = 'OTHER';
update PAYMENT_SERVICE_CATEGORIES set service_id = (select id from PAYMENT_SERVICES where CODE = PAYMENT_SERVICE_CATEGORIES.code);
insert into PAYMENT_SERV_PARENTS select id, parent_id from PAYMENT_SERVICES where not(PARENT_ID is null );
insert into PAYMENT_SERV_PARENTS  select PAYMENT_SERVICES_ID, service_id from PAYMENT_SERVICE_CATEGORIES  where not(service_id is null);
update PAYMENT_SERVICES set  IS_CATEGORY = '1' where id not in(select PAYMENT_SERVICES_ID from PAYMENT_SERVICE_CATEGORIES) and PARENT_ID is null ;
alter table  PAYMENT_SERVICE_CATEGORIES drop column code;
alter table PAYMENT_SERVICE_CATEGORIES drop column service_id;

-- Номер ревизии: 48851
-- Номер версии: 1.18
-- Комментарий: Редактирование поставщика: блок "настройка видимости в каналах"
ALTER TABLE SERVICE_PROVIDERS
ADD ( AVAILABLE_PAYMENTS_FOR_IB CHAR(1) DEFAULT '1' NOT NULL );

ALTER TABLE SERVICE_PROVIDERS
ADD ( AVAILABLE_PAYMENTS_FOR_M_API CHAR(1) DEFAULT '1' NOT NULL );

ALTER TABLE SERVICE_PROVIDERS
ADD ( AVAILABLE_PAYMENTS_FOR_ATM_API CHAR(1) DEFAULT '1' NOT NULL );

ALTER TABLE SERVICE_PROVIDERS
ADD ( AVAILABLE_PAYMENTS_FOR_ERMB CHAR(1) DEFAULT '1' NOT NULL );

-- Номер ревизии: 48892
-- Номер версии: 1.18
-- Комментарий: Реализовать механизм автоматической архивации отчетов об ошибках (сущность каунтера)
create table EXCEPTION_COUNTERS  (
   EXCEPTION_HASH       VARCHAR2(40)                    not null,
   EXCEPTION_DATE       TIMESTAMP                       not null,
   EXCEPTION_COUNT      INTEGER                         not null,
   constraint PK_EXCEPTION_COUNTERS primary key (EXCEPTION_HASH, EXCEPTION_DATE)
)
go

create sequence S_EXCEPTION_COUNTERS
go

-- Номер ревизии: 48906
-- Номер версии: 1.18
-- Комментарий: Создать справочник классификации внешних ошибок
alter table EXCEPTION_ENTRY add ERROR_CODE VARCHAR2(20);

-- Номер ревизии: 48930
-- Номер версии: 1.18
-- Комментарий: CHG054568: Добавление признаков "Сотрудник ВСП" и "Доступно сотрудникам ВСП".
alter table ACCESSSCHEMES add VSP_EMPLOYEE_SCHEME char default '0' not null
go
alter table EMPLOYEES add VSP_EMPLOYEE char default '0' not null 
go

--Номер ревизии: 48937
--Номер версии: 1.18
--Комментарий:  BUG049514: MANAGER_ID 14 символов     
alter table employees modify manager_id varchar2(14);
alter table users modify manager_id varchar2(14);
alter table personal_finance_profile modify manager_id varchar2(14);

--Номер ревизии: 49012
--Номер версии: 1.18
--Комментарий:  Маппинг ошибок. часть 5. Настройка сообщений об ошибках для клиента и сотрудника.
alter table STATIC_MESSAGES modify (KEY varchar2(64))
go
update STATIC_MESSAGES set KEY = concat('com.rssl.iccs.',KEY)
go
alter table IMAGES_MESSAGES modify (MESSAGE_KEY varchar2(64))
go
update IMAGES_MESSAGES set MESSAGE_KEY = concat('com.rssl.iccs.',MESSAGE_KEY)
go

--Номер ревизии: 49031
--Номер версии: 1.18
--Комментарий:  Перекодировка справочника подразделений из СПОБК. Модель БД
create table DEPARTMENTS_RECORDING  (
   ID                   INTEGER                         not null,
   TB_ERIB              VARCHAR2(3)                     not null,
   OSB_ERIB             VARCHAR2(4),
   OFFICE_ERIB          VARCHAR2(7),
   TB_SPOOBK2           VARCHAR2(3)                     not null,
   OSB_SPOOBK2          VARCHAR2(4),
   OFFICE_SPOOBK2       VARCHAR2(7),
   constraint PK_DEPARTMENTS_RECORDING primary key (ID)
);

create sequence S_DEPARTMENTS_RECORDING;

create table DEPARTMENTS_RECORDING_TMP  (
   ID                   INTEGER                         not null,
   TB_ERIB              VARCHAR2(3)                     not null,
   OSB_ERIB             VARCHAR2(4),
   OFFICE_ERIB          VARCHAR2(7),
   TB_SPOOBK2           VARCHAR2(3)                     not null,
   OSB_SPOOBK2          VARCHAR2(4),
   OFFICE_SPOOBK2       VARCHAR2(7),
   constraint PK_DEPARTMENTS_RECORDING_TMP primary key (ID)
);

--Номер ревизии: 49038
--Номер версии: 1.18
--Комментарий:  Доработать гейтовый интерфейс Field(добавить поле mask) 

alter table FIELD_DESCRIPTIONS add (MASK varchar2(1024) null);

--Номер ревизии: 49046
--Номер версии: 1.18
--Комментарий: ПиП. "Переименование" существующих признаков доступности ПУ в каналах: mAPI (MOBILE_SERVICE -> AVAILABLE_PAYMENTS_FOR_M_API CHAR), atmAPI (ATM_AVAILABLE -> AVAILABLE_PAYMENTS_FOR_ATM_API).
update service_providers set AVAILABLE_PAYMENTS_FOR_M_API = MOBILE_SERVICE;
update service_providers set AVAILABLE_PAYMENTS_FOR_ATM_API = ATM_AVAILABLE;
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN MOBILE_SERVICE;
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN ATM_AVAILABLE;


--Номер ревизии: 49136
--Номер версии: 1.18
--Комментарий: BUG055100: Исключение при добавлении ошибки в справочник маппинга 
DELETE FROM EXCEPTION_COUNTERS WHERE EXCEPTION_HASH IN (SELECT HASH FROM EXCEPTION_ENTRY WHERE KIND = 'E' AND ERROR_CODE is null)
go

DELETE FROM EXCEPTION_ENTRY WHERE KIND = 'E' AND ERROR_CODE is null
go

--Номер ревизии: 49174
--Номер версии: 1.18
--Комментарий: Доработка формы настройки автоплатежа.
alter table SERVICE_PROVIDERS add (IS_AUTOPAYMENT_SUPPORTED_API CHAR(1) default '0' not null);
alter table SERVICE_PROVIDERS add (IS_AUTOPAYMENT_SUPPORTED_ATM CHAR(1) default '0' not null);
alter table SERVICE_PROVIDERS add (IS_AUTOPAYMENT_SUPPORTED_ERMB CHAR(1) default '0' not null);

--Номер ревизии: 49180
--Номер версии: 1.18
--Комментарий: Добавлен функциональный индекс ФИО+ДУЛ+ДР на историю идентификационных данных клиентов
create index POI_FIO_DUL_DR on PERSON_OLD_IDENTITY
(
    upper(replace(SUR_NAME,' ','') || replace(FIRST_NAME,' ','') || replace(PATR_NAME,' ','')),
    replace(DOC_SERIES,' ','') || replace(DOC_NUMBER,' ',''),
    BIRTHDAY
);



--Номер ревизии: 49215
--Номер версии: 1.18
--Комментарий:  Бюджетирование

create table CLIENTS_BUDGET  (
   LOGIN_ID             INTEGER                         not null,
   CATEGORY_ID          INTEGER                         not null,
   BUDGET               NUMBER(15,4),
   constraint PK_CLIENTS_BUDGET primary key (LOGIN_ID, CATEGORY_ID)
)

go

create sequence S_CLIENTS_BUDGET
go


create index "DXFK_CLIENTS_BUDGET_TO_CATEGOR" on CLIENTS_BUDGET
(
   CATEGORY_ID
)
go

alter table CLIENTS_BUDGET
   add constraint FK_CLIENTS__FK_CLIENT_CARD_OPE foreign key (CATEGORY_ID)
      references CARD_OPERATION_CATEGORIES (ID)
      on delete cascade
go


create index "DXFK_CLIENTS_BUDGET_TO_LOGINS" on CLIENTS_BUDGET
(
   LOGIN_ID
)
go

alter table CLIENTS_BUDGET
   add constraint FK_CLIENTS__FK_CLIENT_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go 

--Номер ревизии: 49220
--Номер версии: 1.18
--Комментарий:  Аэроэкспресс: Создать внутреннюю ссылку для поля “я согласен”(Также вставка разметки для правил Аэроэкспресса)
/*==============================================================*/
/* Table: EXTENDED_DESCRIPTION_DATA                             */
/*==============================================================*/
create table EXTENDED_DESCRIPTION_DATA  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(50)                    not null,
   CONTENT              CLOB                            not null,
   constraint PK_EXTENDED_DESCRIPTION_DATA primary key (ID)
)

go

create sequence S_EXTENDED_DESCRIPTION_DATA
go

/*==============================================================*/
/* Index: UNQ_NAME_EXT_DESC                                     */
/*==============================================================*/
create unique index UNQ_NAME_EXT_DESC on EXTENDED_DESCRIPTION_DATA (
   NAME ASC
)
go

insert into EXTENDED_DESCRIPTION_DATA(ID, NAME, CONTENT) values (S_EXTENDED_DESCRIPTION_DATA.nextval, 'aeroexpress-rule', EMPTY_CLOB())
go

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h1>
                                Правила оказания услуг по перевозкам пассажиров, ручной клади и багажа в электропоездах ООО «Аэроэкспресс»
                            </h1>
                            <br/>
                            <h2>
                                <b>1. Общие положения</b>
                            </h2>
                            1.1 Настоящие Правила разработаны в соответствии с требованиями следующих нормативных документов:
                            &mdash; Федерального закона РФ от 10.01.2003 года № 18-ФЗ «Устав железнодорожного транспорта РФ»;<br/>
                            &mdash; Правил оказания услуг по перевозкам на железнодорожном транспорте пассажиров, а также грузов, багажа и грузобагажа для личных, семейных, домашних и иных нужд, не связанных с осуществлением предпринимательской деятельности, утвержденных постановлением Правительства РФ от 2 марта 2005 года № 111;<br/>
                            &mdash; Правил перевозок пассажиров, багажа и грузобагажа на федеральном железнодорожном транспорте, утвержденных Приказом МПС РФ от 26 июля 2002 года N 30;<br/>
                            &mdash; Иных нормативно-правовых актов Российской Федерации.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
go

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>2. Перевозка пассажиров</b>
                            </h2>
                            2.1 Для проезда в электропоезде ООО «Аэроэкспресс» пассажир обязан:<br/>
                            2.2 Приобрести билет для разовой поездки, либо абонементный билет в установленной форме;<br/>
                            2.3 Сохранять приобретённый проездной документ (билет) в течение всего пути следования электропоезда до момента выхода через пункт контроля проездных документов (билетов). Пассажир, не предъявивший проездной документ (билет) при выходе через пункт контроля, считается безбилетным и обязан оплатить стоимость проезда;<br/>
                            2.4 Предъявлять проездной документ (билет) лицам, осуществляющим контроль – разъездным билетным кассирам (бортпроводникам);<br/>
                            2.5 При проверке проездных документов (билетов) в электропоезде, предъявить разъездному билетному кассиру (бортпроводнику) документы, удостоверяющие право на льготы, (если пассажир имеет на них право).<br/>
                            2.6 Пассажир вправе приобрести проездной документ (билет) в пути следования (при наличии такой услуги на направлении перевозки) с оплатой стоимости услуги по оформлению проездных документов (билетов) в электропоездах ООО «Аэроэкспресс» согласно действующему тарифу.<br/>
                            2.7 Проездной документ (билет) для разовой поездки действителен на одну поездку в одном направлении в соответствии с датой, указанной в нём. Если срок действия проездного документа (билета) заканчивается в момент нахождения пассажира в пути, проездной документ (билет) является действительным до прибытия пассажира в пункт назначения.<br/>
                            2.8 Пассажир имеет право провозить бесплатно детей в возрасте не старше 5 (пяти) лет. При следовании с пассажиром детей в возрасте от 5 (пяти) до 7 (семи) лет непосредственно в день поездки приобретаются детские билеты. При возникновении сомнения относительно возраста детей, провозимых бесплатно или по детским билетам, разъездные билетные кассиры (бортпроводники) вправе потребовать предъявления соответствующих документов, подтверждающих возраст ребенка.<br/>
                            2.9 Разовые и абонементные билеты (в т.ч. абонементный билет «Выходного дня»), оформленные иными перевозчиками пригородного сообщения, в электропоездах  ООО «Аэроэкспресс» не действительны. Пассажир, предъявивший такие билеты, считается безбилетным и с него взимается полная стоимость проезда, согласно установленному тарифу для данного вида перевозок.<br/>
                            2.10 На один билет пассажир имеет право занять только одно место. При отсутствии свободных сидячих мест в вагоне допускается проезд пассажиров стоя, при этом стоимость проезда не изменяется. <br/>
                            2.11 Пассажир может получить полную стоимость проезда в случае незапланированного перерыва в движении электропоездов более чем на 1 (один) час. При этом в других случаях возврат средств по неиспользованным билетам для разовой поездки не производится. Возврат средств по неиспользованным абонементным билетам производится в порядке и случаях, предусмотренных «Правилами перевозок пассажиров, багажа и грузобагажа на федеральном железнодорожном транспорте»,  утвержденными Приказом МПС РФ от 26 июля 2002 года N 30.<br/>
                            2.12 Оформление проездного документа (билета) в электропоезд лицу, имеющему право оплаты стоимости проезда со скидкой или бесплатного проезда, производится в Порядке, разработанном в соответствии с требованиями действующего законодательства Российской Федерации.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
go

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>3. Перевозка ручной клади и багажа</b>
                            </h2>
                            3.1 Каждый пассажир имеет право бесплатно провозить с собой на один проездной документ (билет) полный или детский ручную кладь весом не более 36 кг, размер которой в сумме 3-х измерений не должен превышать 180 см. Указанная ручная кладь независимо от рода и вида упаковки должна быть размещена на багажных полках вагона электропоезда. Дополнительно к установленной норме разрешается провоз до 50 кг ручной клади за дополнительную плату, согласно установленному тарифу.<br/>
                            3.2 За провоз мелких домашних животных, собак и птиц, одного велосипеда в не разобранном виде (перевозка в тамбуре поезда) взимается дополнительная плата, согласно установленному тарифу.<br/>
                            3.3 Мелкие домашние животные, собаки и птицы должны перевозиться в ящиках, клетках, контейнерах и помещаться на местах, предназначенных для размещения ручной клади. Собаки крупных пород, в том числе служебные перевозятся в тамбуре поезда (не более двух собак) в намордниках и с поводком под наблюдением их владельцев или сопровождающих, которые должны обеспечить соблюдение санитарно-гигиенического режима в вагоне поезда. Перевозка мелких домашних животных, собак и птиц допускается сверх установленной нормы провоза ручной клади при наличии ветеринарной справки.<br/>
                            3.4 Слепые пассажиры провозят при себе собак-проводников бесплатно.<br/>
                            3.5 К перевозке ручной кладью в счет установленной нормы провоза  допускаются растения, саженцы и другие посадочные материалы с обвязанной кроной и упакованными корневищами, не превышающими по высоте 180 см.<br/>
                            3.6 Разрешается перевозка за дополнительную плату электронной, бытовой, видео- и аудиотехники, которая по сумме 3-х измерений превышает 180 см, независимо от наличия у пассажира ручной клади, не более одного предмета на проездной документ (билет). Плата за провоз указанных предметов, независимо от веса взимается согласно установленному тарифу.<br/>
                            3.7 Пассажиру разрешается бесплатно провозить с собой сверх установленной нормы провоза ручной клади портфель, дамскую сумочку, бинокль, лыжи и палки к ним, удочки, фотоаппарат, зонт, а также другие мелкие вещи, размер которых по сумме 3-х измерений не превышает 100 см.<br/>
                            3.8 Обеспечение целостности и сохранности ручной клади, перевозимой пассажиром, является обязанностью пассажира.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
go

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>4.   Пассажирам запрещено</b>
                            </h2>
                            4.1 В электропоездах ООО «Аэроэкспресс» запрещается:<br/>
                            4.2 Находиться с багажом, в одежде, с предметами, продуктами, которые могут испачкать пассажиров, вагоны, сооружения и устройства станций.<br/>
                            4.3 Размещать багаж или ручную кладь на пассажирских креслах.<br/>
                            4.4 Перевозить вещи (предметы), которые могут повредить или загрязнить вагон, а также зловонные, огнеопасные, отравляющие, легковоспламеняющиеся, взрывчатые и другие опасные вещества. Огнестрельное оружие при перевозке в качестве ручной клади должно находиться в чехле, кобуре или специальном футляре в разряженном состоянии отдельно от патронов.<br/>
                            4.5 Распивать спиртные напитки и находиться в нетрезвом состоянии.<br/>
                            4.6 Задерживать открытие или закрытие автоматических дверей на остановках, открывать двери во время движения электропоезда.<br/>
                            4.7 Повреждать внутривагонное оборудование, стекла локомотивов и вагонов.<br/>
                            4.8 Нарушать спокойствие других пассажиров, играть в азартные игры, сорить.<br/>
                            4.9 Останавливать без надобности поезд стоп-краном.<br/>
                            4.10 Курить в вагонах и тамбурах.<br/>
                            4.11 Пассажир может быть удален из электропоезда:<br/>
                            4.12 Работниками ОВД и сотрудниками охраны, сопровождающими состав, если он при посадке в поезд или в пути следования нарушает правила проезда, общественный порядок и мешает спокойствию других пассажиров;<br/>
                            4.13 Разъездными билетными кассирами (бортпроводниками), если пассажир проезжает без проездного документа (билета) или по билету, оформленному на обычный пригородный поезд и отказывается оплатить стоимость проезда согласно установленного порядка;<br/>
                            4.14 Медицинскими работниками - в случае болезни пассажира, препятствующей возможности его дальнейшей поездки или угрожающей здоровью других пассажиров, если нет возможности поместить его отдельно;<br/>
                            4.15 В иных случаях, установленных законодательством Российской Федерации.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
go

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>5.   Права билетных кассиров (бортпроводников)</b>
                            </h2>
                            5.1 Разъездные билетные кассиры (бортпроводники) имеют право:<br/>
                            &mdash; Взыскивать с пассажиров, проезжающих без проездных документов (билетов), стоимость проезда согласно установленному тарифу и сбор за оказание услуги по оформлению проездных документов (билетов).<br/>
                            &mdash; Взыскивать с пассажиров стоимость проезда в установленных размерах за провоз без билета детей в возрасте старше 5 (пяти) лет, за провоз излишнего веса багажа и негабаритной ручной клади, а также сбор за оказание услуги по продаже билетов.<br/>
                            &mdash; При отказе пассажира от уплаты проезда, принимать меры к высадке пассажира из электропоезда.<br/>' where NAME = 'aeroexpress-rule'
go

alter table SERVICE_PROVIDERS add (IS_EDIT_PAYMENT_SUPPORTED CHAR(1) default '1' not null);
go

--Номер ревизии: 49265
--Номер версии: 1.18
--Комментарий:  уровень безопасности лимитов

alter table LIMITS add (SECURITY_TYPE varchar2(8));

--Номер ревизии: 49298
--Номер версии: 1.18
--Комментарий:  Реализация процедуры синхронизации текстов смс в базе ПРОМа и SVN. Модель БД.

alter table sms_resources     add LAST_MODIFIED timestamp;
alter table sms_resources     add PREVIOUS_TEXT clob;
alter table sms_resources     add EMPLOYEE_LOGIN_ID   integer;
alter table message_templates add LAST_MODIFIED timestamp;
alter table message_templates add PREVIOUS_TEXT clob;
alter table message_templates add EMPLOYEE_LOGIN_ID   integer;

-- Номер ревизии: 49314
-- Номер версии: 1.18
-- Комментарий: CHG054606: Перекодировка подразделений для перевыпуска карт 

ALTER TABLE DEPARTMENTS DROP COLUMN REISSUED_CARD;


-- Номер ревизии: 49288
-- Номер версии: 1.18
-- Комментарий: confirm-бины 
create table CONFIRM_BEANS  (
   LOGIN_ID             INTEGER                         not null,
   CONFIRM_CODE         VARCHAR2(32)                    not null,
   EXPIRE_TIME          TIMESTAMP                       not null,
   CONFIRMABLE_TASK_CLASS VARCHAR2(256)                 not null,
   CONFIRMABLE_TASK_BODY VARCHAR2(1024)                 not null,
   constraint PK_CONFIRM_BEANS primary key (LOGIN_ID, CONFIRM_CODE)
)
;

-- Номер ревизии: 49336
-- Номер версии: 1.18
-- Комментарий: настройки безопасности для клиента
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityStartDate','C',''); 
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityDaysNumber','C',''); 
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInATM','C',''); 
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInERIB','C',''); 
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInERMB','C','');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInMAPI','C','');


-- Номер ревизии: 49352
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. Защита от дублирования входящих СМС.

create table ERMB_SMS_INBOX  (
   RQ_UID               VARCHAR2(32)                    not null,
   RQ_TIME              TIMESTAMP                       not null,
   PHONE                VARCHAR2(20)                    not null,
   TEXT                 VARCHAR2(100)                   not null,
   constraint PK_ERMB_SMS_INBOX primary key (RQ_UID)
)
;

create sequence S_ERMB_SMS_INBOX
;

create unique index UI_ERMB_SMS_INBOX on ERMB_SMS_INBOX (
   PHONE ASC,
   TEXT ASC
)
;

create index IDX_RQ_TIME on ERMB_SMS_INBOX (
   RQ_TIME ASC
)
;

-- Номер ревизии: 49388
-- Номер версии: 1.18
-- Комментарий: Подсказки для вкладки "Расходы" (мои финансы): бюджетирование

alter table PROFILE add (HINT_READ CHAR(1) default '0' not null)
go

-- Номер ревизии: 49391
-- Номер версии: 1.18
-- Комментарий: ENH054717: Реализовать автоматический докат биллинговых операций в статусе "неизвестен"

create index DOC_ID_JOB_NAME_PER_IDX on PAYMENT_EXECUTION_RECORDS
(
    DOCUMENT_ID asc,
    JOB_NAME asc
)
go

-- Номер ревизии: 49404
-- Номер версии: 1.18
-- Комментарий:  BUG055004: Привести форму редактирования поставщика в соответствие макету 

ALTER TABLE SERVICE_PROVIDERS  DROP COLUMN NOT_VISIBLE_IN_HIERARCHY;

-- Номер ревизии: 49412
-- Номер версии: 1.18
-- Комментарий:  ENH052671: Оптимизировать загрузку предложений по предодоберенным кредитным картам (ч.2)

delete from LOAN_CARD_OFFER
go
alter table LOAN_CARD_OFFER  drop column  DOCUMENT_SERIES 
go
alter table LOAN_CARD_OFFER  drop column  DOCUMENT_NUMBER 
go
alter table LOAN_CARD_OFFER add SERIES_NUMBER varchar2(19)
go

drop index DXFK_LOAN_CARD_OFFER
go
create index LCO_FIO_DUL_TB_V_DR on LOAN_CARD_OFFER
(
   upper(replace(SUR_NAME,' ','') || replace(FIRST_NAME,' ','') || replace(PATR_NAME,' ','')),
   upper(replace(SERIES_NUMBER, ' ', '')),
   TB,
   IS_VIEWED,
   BIRTHDAY
)
go


-- Номер ревизии: 49455
-- Номер версии: 1.18
-- Комментарий:  ИОК от 17.05.2013. Увеличение длины поля.

alter table ERMB_SMS_INBOX MODIFY TEXT varchar2(1024);

-- Номер ревизии: 49471
-- Номер версии: 1.18
-- Комментарий:  Удаление неиспользуемых полей из журналов

ALTER TABLE CODLOG DROP COLUMN MESSAGE_ANSWER_TYPE;
ALTER TABLE CODLOG DROP COLUMN DIRECTION;
ALTER TABLE CODLOG DROP COLUMN LINK;
ALTER TABLE CODLOG DROP COLUMN PERSON_ID;
ALTER TABLE USERLOG DROP COLUMN PERSON_ID;
ALTER TABLE SYSTEMLOG DROP COLUMN PERSON_ID;

-- Номер ревизии: 49471
-- Номер версии: 1.18
-- Комментарий:  ENH055049: Реализовать возможность расширенного логирования по клиенту и уменьшения логируемой информации 

create table CLIENT_EXTENDED_LOGGING  (
   LOGIN_ID             INTEGER not null,
   START_DATE           TIMESTAMP                       not null,
   END_DATE           TIMESTAMP,
   constraint PK_CLIENT_EXTENDED_LOGGING primary key (LOGIN_ID)
)
go

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

-- Номер ревизии: 49505
-- Номер версии: 1.18
-- Комментарий:  добавление уровня безопасности в таблицу USERS. Скрипт, заполняющий уровень безопасности существующих клиентов.

alter table users add (SECURITY_TYPE varchar2(8));
update USERS set SECURITY_TYPE = 'LOW' where TRUSTED = '1';
update USERS set SECURITY_TYPE = 'HIGHT' where TRUSTED = '0' and round(months_between(sysdate, BIRTHDAY)/12) >= 60;
update USERS set SECURITY_TYPE = 'MIDDLE' where TRUSTED = '0' and round(months_between(sysdate, BIRTHDAY)/12) < 60;

-- Номер ревизии: 49564
-- Номер версии: 1.18
-- Комментарий: Изменены настройки уровня безопасности

update BUSINESS_PROPERTIES set KEY = 'securityInCC' where key = 'securityInMAPI';
update BUSINESS_PROPERTIES set KEY = 'securityInVSP' where key = 'securityInERMB';


-- Номер ревизии: 49478
-- Номер версии: 1.18
-- Комментарий: Лимиты по автоплатежам(Доработка АРМ сотрудника банка)

create table AUTOPAY_SETTINGS(
   ID                   INTEGER                         not null,
   TYPE                 VARCHAR2(20)                    not null,
   RECIPIENT_ID         INTEGER                         not null,
   PARAMETERS           CLOB                            not null,
   constraint PK_AUTOPAY_SETTING primary key (ID)
)
go

create sequence S_AUTOPAY_SETTINGS
go

alter table AUTOPAY_SETTINGS
   add constraint FK_AUTOPAY_SETTING_TO_PROV foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go

create index "DXFK_AUTOPAY_SETTING_TO_PROV" on AUTOPAY_SETTINGS
(
   RECIPIENT_ID
)
go

DECLARE
autopayParams CLOB; -- параметры автоплатежа
BEGIN
FOR serviceProvidersRow IN 
(SELECT ID,
IS_THRESHOSD_AUTOPAY_SUPPORTED, 
MIN_SUMMA_THRESHOLD, 
MAX_SUMMA_THRESHOLD, 
IS_INTERVAL_THRESHOLD, 
MIN_VALUE_THRESHOLD, 
MAX_VALUE_THRESHOLD, 
DISCRETE_VALUE_THRESHOLD,
CLIENT_HINT_THRESHOLD,
IS_ALWAYS_AUTOPAY_SUPPORTED,
MIN_SUMMA_ALWAYS,
MAX_SUMMA_ALWAYS,
CLIENT_HINT_ALWAYS,
IS_INVOICE_AUTOPAY_SUPPORTED,
CLIENT_HINT_INVOICE
FROM SERVICE_PROVIDERS WHERE IS_AUTOPAYMENT_SUPPORTED = '1') LOOP
    IF(serviceProvidersRow.IS_THRESHOSD_AUTOPAY_SUPPORTED = '1') THEN   
        -- формируем параметры
        autopayParams := '<entity key="THRESHOLD">';
        IF(serviceProvidersRow.CLIENT_HINT_THRESHOLD is not null) THEN
            autopayParams := autopayParams || '<field name="clientHint">'||serviceProvidersRow.CLIENT_HINT_THRESHOLD||'</field>';
        END IF;    
        IF(serviceProvidersRow.MAX_SUMMA_THRESHOLD is not null) THEN
            autopayParams := autopayParams || '<field name="maxSumThreshold">'||serviceProvidersRow.MAX_SUMMA_THRESHOLD||'</field>';
        END IF;    
        IF(serviceProvidersRow.MIN_SUMMA_THRESHOLD is not null) THEN
            autopayParams := autopayParams || '<field name="minSumThreshold">'||serviceProvidersRow.MIN_SUMMA_THRESHOLD||'</field>';
        END IF;    
        IF(serviceProvidersRow.MAX_VALUE_THRESHOLD is not null) THEN
            autopayParams := autopayParams || '<field name="maxValueThreshold">'||serviceProvidersRow.MAX_VALUE_THRESHOLD||'</field>';
        END IF;    
        IF(serviceProvidersRow.MIN_VALUE_THRESHOLD is not null) THEN
            autopayParams := autopayParams || '<field name="minValueThreshold">'||serviceProvidersRow.MIN_VALUE_THRESHOLD||'</field>'; 
        END IF;    
        IF(serviceProvidersRow.DISCRETE_VALUE_THRESHOLD is not null) THEN
            autopayParams := autopayParams ||'<field name="discreteValues">'||serviceProvidersRow.DISCRETE_VALUE_THRESHOLD||'</field>';     
        END IF;    
        IF(serviceProvidersRow.IS_INTERVAL_THRESHOLD = '1') THEN
            autopayParams := autopayParams || '<field name="interval">true</field>';
        ELSE     
            autopayParams := autopayParams ||'<field name="interval">false</field>';     
        END IF;
        autopayParams := autopayParams ||'</entity>';
        -- вставляем запись
        insert into AUTOPAY_SETTINGS(ID, TYPE, RECIPIENT_ID, PARAMETERS) values (S_AUTOPAY_SETTINGS.nextval, 'THRESHOLD', serviceProvidersRow.ID, autopayParams);
    END IF;
    IF(serviceProvidersRow.IS_ALWAYS_AUTOPAY_SUPPORTED = '1') THEN   
        -- формируем параметры
        autopayParams := '<entity key="ALWAYS">'; 
        IF(serviceProvidersRow.CLIENT_HINT_ALWAYS is not null) THEN
            autopayParams := autopayParams ||'<field name="clientHint">'||serviceProvidersRow.CLIENT_HINT_ALWAYS||'</field>';
        END IF;    
        IF(serviceProvidersRow.MIN_SUMMA_ALWAYS is not null) THEN
            autopayParams := autopayParams ||'<field name="minSumAlways">'||serviceProvidersRow.MIN_SUMMA_ALWAYS||'</field>';
        END IF;    
        IF(serviceProvidersRow.MAX_SUMMA_ALWAYS is not null) THEN
            autopayParams := autopayParams ||'<field name="maxSumAlways">'||serviceProvidersRow.MAX_SUMMA_ALWAYS||'</field>';
        END IF;    
        autopayParams := autopayParams ||'</entity>';                                          
        -- вставляем запись            
        insert into AUTOPAY_SETTINGS(ID, TYPE, RECIPIENT_ID, PARAMETERS) values (S_AUTOPAY_SETTINGS.nextval, 'ALWAYS', serviceProvidersRow.ID, autopayParams);            
    END IF;
    IF(serviceProvidersRow.IS_INVOICE_AUTOPAY_SUPPORTED = '1') THEN   
        -- формируем параметры
        autopayParams := '<entity key="INVOICE">'; 
        IF(serviceProvidersRow.CLIENT_HINT_INVOICE is not null) THEN
            autopayParams := autopayParams ||'<field name="clientHint">'||serviceProvidersRow.CLIENT_HINT_INVOICE||'</field>';
        END IF;    
        autopayParams := autopayParams ||'</entity>';
        -- вставляем запись            
        insert into AUTOPAY_SETTINGS(ID, TYPE, RECIPIENT_ID, PARAMETERS) values (S_AUTOPAY_SETTINGS.nextval, 'INVOICE', serviceProvidersRow.ID, autopayParams);                        
    END IF;   
END LOOP;
END;
go

ALTER TABLE SERVICE_PROVIDERS DROP COLUMN IS_THRESHOSD_AUTOPAY_SUPPORTED
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN MIN_SUMMA_THRESHOLD
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN MAX_SUMMA_THRESHOLD
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN IS_INTERVAL_THRESHOLD
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN MIN_VALUE_THRESHOLD
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN MAX_VALUE_THRESHOLD
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN DISCRETE_VALUE_THRESHOLD
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN CLIENT_HINT_THRESHOLD
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN IS_ALWAYS_AUTOPAY_SUPPORTED
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN MIN_SUMMA_ALWAYS
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN MAX_SUMMA_ALWAYS
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN CLIENT_HINT_ALWAYS
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN IS_INVOICE_AUTOPAY_SUPPORTED
go
ALTER TABLE SERVICE_PROVIDERS DROP COLUMN CLIENT_HINT_INVOICE
go

-- Номер ревизии: 49659
-- Номер версии: 1.18
-- Комментарий: ENH055699: Реализовать "видимость" для автоплатежей в настройках ПУ

ALTER TABLE SERVICE_PROVIDERS ADD ( VISIBLE_AUTOPAYMENTS_FOR_IB CHAR(1) DEFAULT '0' NOT NULL )
go
ALTER TABLE SERVICE_PROVIDERS ADD ( VISIBLE_AUTOPAYMENTS_FOR_API CHAR(1) DEFAULT '0' NOT NULL )
go
ALTER TABLE SERVICE_PROVIDERS ADD ( VISIBLE_AUTOPAYMENTS_FOR_ATM CHAR(1) DEFAULT '0' NOT NULL )
go
ALTER TABLE SERVICE_PROVIDERS ADD ( VISIBLE_AUTOPAYMENTS_FOR_ERMB CHAR(1) DEFAULT '0' NOT NULL )
go

-- Номер ревизии: 49666
-- Номер версии: 1.18
-- Комментарий: CHG055491: Преобразование старых кодов ВСП при загрузки справочника перекодировки

ALTER TABLE LOAN_CARD_OFFER ADD (ORIGINAL_TB VARCHAR2(3));


-- Номер ревизии: 49641
-- Номер версии: 1.18
-- Комментарий: CHG054768: Ввести признак для поставщиков услуг - "запрещен платеж с кредитных карт"

alter table SERVICE_PROVIDERS add (IS_CREDIT_CARD_SUPPORTED CHAR(1) default '1' not null)
go


-- Номер ревизии: 49686
-- Номер версии: 1.18
-- Комментарий: BUG054563: Учитывать время ввода в действие курсов валют

alter table RATE add EXPIRE_DATE TIMESTAMP
go

-- Номер ревизии: 49663
-- Номер версии: 1.18
-- Комментарий: Учет полной версии mAPI в анкете ПУ
update service_providers set version_api = 400 where version_api is not null and version_api < 5;
update service_providers set version_api = 500 where version_api is not null and version_api = 5;

-- Номер ревизии: 49471
-- Номер версии: 1.18
-- Комментарий: Удаление неиспользуемых полей из журналов
ALTER TABLE CODLOG DROP COLUMN DOCUMENT_ID;

-- Номер ревизии: 49721
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. Доработка Анкеты поставщика(ч.2)
delete PROVIDER_SMS_ALIAS_FIELD
go
delete PROVIDER_SMS_ALIAS
go
alter table PROVIDER_SMS_ALIAS_FIELD add IS_LIST char(1) not null
go 

-- Номер ревизии: 49730
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. Доработка Анкеты поставщика(ч.2) fix
alter table PROVIDER_SMS_ALIAS_FIELD drop column IS_LIST
go 

-- Номер ревизии: 49757
-- Номер версии: 1.18
-- Комментарий: BUG054814: Нет проверки корректности написания переменных при сохранении шаблона СМС.

alter table SMS_RESOURCES     add (VARIABLES VARCHAR2(4000));
alter table MESSAGE_TEMPLATES add (VARIABLES VARCHAR2(4000));

-- Номер ревизии: 49782
-- Номер версии: 1.18
-- Комментарий: ЕСУШ
/*==============================================================*/
/* Table: PAYMENTS_DOCUMENTS                                    */
/*==============================================================*/
create table PAYMENTS_DOCUMENTS  (
   ID                   INTEGER                         not null,
   KIND                 CHAR(1)                         not null,
   CHANGED              TIMESTAMP                       not null,
   EXTERNAL_ID          VARCHAR(80),
   DOCUMENT_NUMBER      INTEGER,
   CLIENT_CREATION_DATE TIMESTAMP                       not null,
   CLIENT_CREATION_CHANNEL CHAR(1)                         not null,
   CLIENT_OPERATION_DATE TIMESTAMP,
   CLIENT_OPERATION_CHANNEL CHAR(1),
   EMPLOYEE_OPERATION_DATE TIMESTAMP,
   EMPLOYEE_OPERATION_CHANNEL CHAR(1),
   CLIENT_GUID          VARCHAR(24)                     not null,
   CREATED_EMPLOYEE_GUID VARCHAR(24),
   CREATED_EMPLOYEE_FULL_NAME VARCHAR2(250),
   CONFIRMED_EMPLOYEE_GUID VARCHAR(24),
   CONFIRMED_EMPLOYEE_FULL_NAME VARCHAR2(250),
   OPERATION_UID        VARCHAR2(32),
   STATE_CODE           VARCHAR2(25)                    not null,
   STATE_DESCRIPTION    VARCHAR2(265),
   FORM_TYPE            VARCHAR(100)                    not null,
   CHARGEOFF_RESOURCE   VARCHAR(30),
   DESTINATION_RESOURCE VARCHAR(30),
   GROUND               VARCHAR2(1024),
   CHARGEOFF_AMOUNT     NUMBER(19,4),
   CHARGEOFF_CURRENCY   CHAR(3),
   DESTINATION_AMOUNT   NUMBER(19,4),
   DESTINATION_CURRENCY CHAR(3),
   SUMM_TYPE            VARCHAR2(50),
   RECEIVER_NAME        VARCHAR2(256),
   INTERNAL_RECEIVER_ID INTEGER,
   RECEIVER_POINT_CODE  VARCHAR2(128),
   EXTENDED_FIELDS      CLOB,
   REGION_ID            VARCHAR2(4),
   AGENCY_ID            VARCHAR2(4),
   BRANCH_ID            VARCHAR2(6),
   CLASS_TYPE           VARCHAR2(200),
   TEMPLATE_NAME        VARCHAR2(128)                   not null,
   TEMPLATE_USE_IN_MAPI CHAR(1)                         not null,
   TEMPLATE_USE_IN_ATM  CHAR(1)                         not null,
   TEMPLATE_USE_IN_ERMB CHAR(1)                         not null,
   TEMPLATE_USE_IN_ERIB CHAR(1)                         not null,
   TEMPLATE_IS_VISIBLE  CHAR(1)                         not null,
   TEMPLATE_ORDER_IND   INTEGER                         not null,
   TEMPLATE_STATE_CODE  VARCHAR2(50)                    not null,
   TEMPLATE_STATE_DESCRIPTION VARCHAR2(128),
   constraint PK_PAYMENTS_DOCUMENTS primary key (ID)
)

go

create sequence S_PAYMENTS_DOCUMENTS
go

/*==============================================================*/
/* Index: IND_TEMPLATE_OWNER                                    */
/*==============================================================*/
create index IND_TEMPLATE_OWNER on PAYMENTS_DOCUMENTS (
   CLIENT_GUID ASC,
   CLIENT_CREATION_DATE ASC
)
go

/*==============================================================*/
/* Index: IND_U_TEMPLATE_NAME                                   */
/*==============================================================*/
create unique index IND_U_TEMPLATE_NAME on PAYMENTS_DOCUMENTS (
   CLIENT_GUID ASC,
   TEMPLATE_NAME ASC
)
go

/*==============================================================*/
/* Table: PAYMENTS_DOCUMENTS_EXT                                */
/*==============================================================*/
create table PAYMENTS_DOCUMENTS_EXT  (
   ID                   INTEGER                         not null,
   KIND                 VARCHAR(50)                     not null,
   NAME                 VARCHAR2(64)                    not null,
   VALUE                VARCHAR2(4000),
   PAYMENT_ID           INTEGER                         not null,
   constraint PK_PAYMENTS_DOCUMENTS_EXT primary key (ID)
)
go

create sequence S_PAYMENTS_DOCUMENTS_EXT
go

create index "DXFK_TEMPLATE_EXT_TO_TEMPLATE" on PAYMENTS_DOCUMENTS_EXT
(
   PAYMENT_ID
)
go

alter table PAYMENTS_DOCUMENTS_EXT
   add constraint FK_TEMPLATE_EXT_TO_TEMPLATE foreign key (PAYMENT_ID)
      references PAYMENTS_DOCUMENTS (ID)
      on delete cascade
go

-- Номер ревизии: 49792
-- Номер версии: 1.18
-- Комментарий: BUG055823: Необходимо реализовать рассинхронизацию старого и нового каталога услуг

create table PAYMENT_SERVICES_OLD  (
   ID                   INTEGER                         not null,
   CODE                 VARCHAR2(50)                    not null,
   NAME                 nvarchar2(128)                  not null,
   PARENT_ID            INTEGER,
   IMAGE_ID             INTEGER,
   POPULAR              CHAR(1)                         not null,
   DESCRIPTION          VARCHAR2(512),
   SYSTEM               CHAR(1)                         not null,
   PRIORITY             INTEGER,
   VISIBLE_IN_SYSTEM    CHAR(1)                         not null,
   IMAGE_NAME           VARCHAR2(128)                   not null,
   constraint PK_PAYMENT_SERVICES_OLD primary key (ID)
)

go

create sequence S_PAYMENT_SERVICES_OLD
go
create table SERV_PROVIDER_PAYMENT_SERV_OLD  (
   PAYMENT_SERVICE_ID   INTEGER                         not null,
   SERVICE_PROVIDER_ID  INTEGER                         not null,
   LIST_INDEX           INTEGER,
   constraint PK_SERV_PROVIDER_PAYMENT_SERV_ primary key (PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID)
)

go
create index "DXFK_P_SERVICE_TO_P_SERVICE_OL" on PAYMENT_SERVICES_OLD
(
   PARENT_ID
)
go

alter table PAYMENT_SERVICES_OLD
   add constraint FK_P_SERVICE_TO_P_SERVICE_OLD foreign key (PARENT_ID)
      references PAYMENT_SERVICES_OLD (ID)
go

create index "DXFK_PROV_PAY_SER_TO_PAY_OLD" on SERV_PROVIDER_PAYMENT_SERV_OLD
(
   PAYMENT_SERVICE_ID
)
go

alter table SERV_PROVIDER_PAYMENT_SERV_OLD
   add constraint FK_PROV_PAY_SER_TO_PAY_OLD foreign key (PAYMENT_SERVICE_ID)
      references PAYMENT_SERVICES_OLD (ID)
go


create index "DXFK_PROV_PAY_SER_TO_PROV_OLD" on SERV_PROVIDER_PAYMENT_SERV_OLD
(
   SERVICE_PROVIDER_ID
)
go


alter table SERV_PROVIDER_PAYMENT_SERV_OLD
   add constraint FK_PROV_PAY_SER_TO_PROV_OLD foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
go

insert into  PAYMENT_SERVICES_OLD select id, code, name, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION,SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME from PAYMENT_SERVICES
go
insert into SERV_PROVIDER_PAYMENT_SERV_OLD select PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID, LIST_INDEX   from SERV_PROVIDER_PAYMENT_SERV
go

alter table PAYMENT_SERVICE_CATEGORIES drop constraint FK_CATEGORY_TO_P_SERV
go
alter table PAYMENT_SERVICE_CATEGORIES
   add constraint FK_CATEGORY_TO_P_SERV foreign key (PAYMENT_SERVICES_ID)
      references PAYMENT_SERVICES_OLD (ID)
go

-- Номер ревизии: 49797
-- Номер версии: 1.18
-- Комментарий: ENH043574: Доработать фильтр истории операций

update business_documents bd
   set bd.receiver_name = 'Сбербанк России'
 where bd.receiver_name is null
   and bd.form_id in (select pf.id from paymentforms pf
                       where pf.name in ('VirtualCardClaim','LoanCardProduct','LoanCardOffer',
                                         'LoanProduct','LoanOffer','LoyaltyProgramRegistrationClaim',
                                         'LoanPayment'));
update business_documents bd
   set bd.receiver_name = 'Пенсионный фонд РФ'
 where bd.receiver_name is null
   and bd.form_id = (select pf.id from paymentforms pf where pf.name = 'PFRStatementClaim');

update business_documents bd
   set bd.receiver_name = trim((select max(def.value) from document_extended_fields def 
                                 where def.name = 'to-account-name' and def.payment_id = bd.id))
 where bd.receiver_name is null
   and bd.form_id in (select pf.id from paymentforms pf where pf.name in ('IMAPayment','InternalPayment'));
   
update business_documents bd
   set bd.receiver_name = trim((select max(def.value) from document_extended_fields def 
                                 where def.name = 'deposit-name' and def.payment_id = bd.id))
 where bd.receiver_name is null
   and bd.form_id in (select pf.id from paymentforms pf where pf.name in ('AccountOpeningClaim','AccountOpeningClaimWithClose'));
   
update business_documents bd
   set bd.receiver_name = trim((select max(def.value) from document_extended_fields def 
                                 where def.name = 'buyIMAProduct' and def.payment_id = bd.id))
 where bd.receiver_name is null
   and bd.form_id = (select pf.id from paymentforms pf where pf.name = 'IMAOpeningClaim');


-- Номер ревизии: 49824
-- Номер версии: 1.18
-- Комментарий: BUG055918: Ошибка при перезагрузке вкладов.

alter table ACCOUNT_TARGETS drop constraint FK_A_TARGETS_FK_ACCOUNT_LINK
go

-- Номер ревизии: 49849
-- Номер версии: 1.18
-- Комментарий: new: 2 кода подтверждения на одну подтверждаемую операцию

DROP TABLE CONFIRM_BEANS;

CREATE TABLE CONFIRM_BEANS  (
   ID                   INTEGER                         NOT NULL,
   LOGIN_ID             INTEGER                         NOT NULL,
   PRIMARY_CONFIRM_CODE VARCHAR2(1024)                  NOT NULL,
   SECONDARY_CONFIRM_CODE VARCHAR2(1024),
   EXPIRE_TIME          TIMESTAMP                       NOT NULL,
   CONFIRMABLE_TASK_CLASS VARCHAR2(256)                   NOT NULL,
   CONFIRMABLE_TASK_BODY VARCHAR2(1024)                  NOT NULL,
   CONSTRAINT PK_CONFIRM_BEANS PRIMARY KEY (ID)
);

CREATE SEQUENCE S_CONFIRM_BEANS
;


CREATE INDEX IDX_CONFIRM_EXPIRE ON CONFIRM_BEANS (
   EXPIRE_TIME ASC
)
;

CREATE UNIQUE INDEX UNIQ_CONFIRM_CODE ON CONFIRM_BEANS (
   LOGIN_ID,
   (GREATEST(PRIMARY_CONFIRM_CODE, SECONDARY_CONFIRM_CODE) || LEAST(PRIMARY_CONFIRM_CODE, SECONDARY_CONFIRM_CODE))
)
;

-- Номер ревизии: 49890
-- Номер версии: 1.18
-- Комментарий: ENH055949: Перенести запрос к СПООБК2 из загрузки предложений на вход

ALTER TABLE LOAN_CARD_OFFER DROP COLUMN ORIGINAL_TB;

create index SPOOBK2_TB_OSB on DEPARTMENTS_RECORDING (
   TB_SPOOBK2 ASC,
   LTRIM(OSB_SPOOBK2, '0') ASC
);

-- Номер ревизии: 49936
-- Номер версии: 1.18
-- Комментарий: BUG055823: Необходимо реализовать рассинхронизацию старого и нового каталога услуг(иок)
create table SERV_PROV_PAYM_SERV_OLD  (
   PAYMENT_SERVICE_ID   INTEGER                         not null,
   SERVICE_PROVIDER_ID  INTEGER                         not null,
   LIST_INDEX           INTEGER,
   constraint PK_SERV_PROV_PAYM_SERV_OLD primary key (PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID)
)
go

create sequence S_SERV_PROV_PAYM_SERV_OLD
go

insert into SERV_PROV_PAYM_SERV_OLD select PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID, LIST_INDEX   from SERV_PROVIDER_PAYMENT_SERV_OLD
go

drop table SERV_PROVIDER_PAYMENT_SERV_OLD cascade constraints
go

create index "DXFK_PROV_PAY_SER_TO_PAY_OLD" on SERV_PROV_PAYM_SERV_OLD
(
   PAYMENT_SERVICE_ID
)
go

alter table SERV_PROV_PAYM_SERV_OLD
   add constraint FK_PROV_PAY_SER_TO_PAY_OLD foreign key (PAYMENT_SERVICE_ID)
      references PAYMENT_SERVICES_OLD (ID)
go


create index "DXFK_PROV_PAY_SER_TO_PROV_OLD" on SERV_PROV_PAYM_SERV_OLD
(
   SERVICE_PROVIDER_ID
)
go

alter table SERV_PROV_PAYM_SERV_OLD
   add constraint FK_PROV_PAY_SER_TO_PROV_OLD foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
go

-- Номер ревизии: 50089
-- Номер версии: 1.18
-- Комментарий: CHG055441: Необходимо подтверждать смс- паролем открытие вклада при создании цели
create unique index I_ACCOUNT_TARGETS_CLAIM on ACCOUNT_TARGETS (
   LOGIN_ID ASC,
   CLAIM_ID ASC
)
go

-- Номер ревизии: 50124
-- Номер версии: 1.18
-- Комментарий: CHG055452: Необходимо удалить constraint'ы. 
alter table STORED_LONG_OFFER drop constraint FK_STORED_LO_DEPARTMENTS_REF;
alter table STORED_DEPO_ACCOUNT drop constraint STORED_DA_TO_DEPARTMENTS_REF;
alter table STORED_IMACCOUNT drop constraint FK_STORED_I_TO_DEPARTMENTS_REF;
alter table STORED_LOAN drop constraint STORED_L_TO_DEPARTMENTS_REF;
alter table STORED_CARD drop constraint FK_STORED_C_TO_DEPARTMENT_REF;
alter table STORED_ACCOUNT drop constraint FK_STORED_A_TO_DEPARTMENT_REF;

-- Номер ревизии: 50208
-- Номер версии: 1.18
-- Комментарий:  CHG053830: Доступность банковских продуктов для АТМ (по умолчанию)
alter table account_links drop column show_in_es;
alter table account_links modify show_in_atm default '1';
alter table card_links modify show_in_atm default '1';
alter table loan_links modify show_in_atm default '1';
alter table imaccount_links modify show_in_atm default '1';

-- Номер ревизии: 50251
-- Номер версии: 11.1
-- Комментарий: Оплата закозов интернет магазинов.

alter table SHOP_FIELDS add CANCELED char(1) default '0' not null
go

create index ORDERS_USER_ID_DATE on ORDERS 
(
    USER_ID asc,
    ORDER_DATE desc
)
go

-- Номер ревизии: 50237
-- Номер версии: 1.18
-- Комментарий: BUG056498: Ошибка удаления ПУ при репликации
alter table SERV_PROV_PAYM_SERV_OLD drop constraint FK_PROV_PAY_SER_TO_PROV_OLD
go
alter table SERV_PROV_PAYM_SERV_OLD
   add constraint FK_PROV_PAY_SER_TO_PROV_OLD foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
	  on delete cascade
go

-- Номер ревизии: 50297
-- Номер версии: 1.18
-- Комментарий: BUG055001: Невозможно подтвердить операцию в КЦ. 

alter table BUSINESS_DOCUMENTS modify (CONFIRM_EMPLOYEE     VARCHAR2(256))

-- Номер ревизии: 50320
-- Номер версии: 1.18
-- Комментарий: Доработка процедуры входа для оплаты поручений из УЭК

ALTER TABLE ORDERS
MODIFY(EXTENDED_ID  NULL);

drop index ORDERS_3;

CREATE INDEX ORDERS_3 ON ORDERS (EXTENDED_ID, ORDER_TYPE, SYSTEM_NAME);

-- Номер ревизии: 50428
-- Номер версии: 1.18
-- Комментарий: BUG056518: Избавится от лишних запросов во ВС при очистке кеша по ресурсам. 

alter table STORED_CARD drop column    ADDITIONAL_CARD_TYPE;

alter table CARD_LINKS add ADDITIONAL_CARD_TYPE VARCHAR2(20);

alter table ACCOUNT_LINKS add OFFICE_TB   VARCHAR2(5);
alter table ACCOUNT_LINKS add OFFICE_OSB  VARCHAR2(5);
alter table ACCOUNT_LINKS add OFFICE_VSP  VARCHAR2(5);

-- Номер ревизии: 50440
-- Номер версии: 1.18
-- Комментарий: BUG056662: Ошибка при входе на главную страницу

UPDATE ACCOUNT_TARGETS target SET ACCOUNT_LINK = null where not EXISTS (select * from ACCOUNT_LINKS links WHERE links.ID = target.ACCOUNT_LINK)
go

create index "DXFK_A_TARGETS_FK_ACCOUNT_LINK" on ACCOUNT_TARGETS
(
   ACCOUNT_LINK
)
go

alter table ACCOUNT_TARGETS
   add constraint FK_ACCOUNT__FK_A_TARG_ACCOUNT_ foreign key (ACCOUNT_LINK)
      references ACCOUNT_LINKS (ID)
      on delete set null
go

-- Номер ревизии: 50447
-- Номер версии: 1.18
-- Комментарий: Доработка процедуры приёма и сохранения платёжных поручений

alter table ORDERS add(ADDITIONAL_FIELDS CLOB);

-- Номер ревизии: 50440
-- Номер версии: 1.18
-- Комментарий: BUG056662: Ошибка при входе на главную страницу (кривое имя сгенерилось)

ALTER TABLE ACCOUNT_TARGETS RENAME CONSTRAINT FK_ACCOUNT__FK_A_TARG_ACCOUNT_ TO FK_A_TARGETS_FK_ACCOUNT_LINK
go


-- Номер ревизии: 50474
-- Номер версии: 1.18
-- Комментарий: актуализация индексов по таблице ORDERS согласно запросам

DROP INDEX ORDERS_3;

DROP INDEX ORDERS_USER_ID_DATE;

CREATE INDEX ORDERS_EXTENDED_ID ON ORDERS (EXTENDED_ID ASC);

CREATE INDEX ORDERS_DATE ON ORDERS (ORDER_DATE ASC);

CREATE INDEX ORDERS_NOTIFY_TIME ON ORDERS (NOTIFICATION_TIME ASC);

CREATE INDEX ORDERS_PERSON ON ORDERS (USER_ID ASC);

-- Номер ревизии: 50504
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. Подключение/настройка услуги дла клиента ч.1

alter table  ERMB_PROFILE add LOCK_DESCRIPTION varchar2(256)
go
alter table  ERMB_PROFILE add LOCK_TIME timestamp
go

-- Номер ревизии: 50474
-- Номер версии: 1.18
-- Комментарий:
-- переименовал ExternalPaymentDataJob -> WebShopNotifyTrigger, UECNotifyTrigger,
-- удалил ErmbProfilesUpdateJob (из базы PhizIC)

DELETE FROM QRTZ_SIMPLE_TRIGGERS
WHERE TRIGGER_NAME IN ('ExternalPaymentDataTrigger', 'ErmbProfilesUpdateTrigger')
;

DELETE FROM QRTZ_TRIGGERS
WHERE TRIGGER_NAME IN ('ExternalPaymentDataTrigger', 'ErmbProfilesUpdateTrigger')
;

DELETE FROM QRTZ_JOB_DETAILS
WHERE JOB_NAME IN ('ExternalPaymentDataJob', 'ErmbProfilesUpdateJob')
;

-- Номер ревизии: 50757
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. Подключение/настройка услуги дла клиента ч.2

alter table ERMB_TMP_PHONE drop column MOBILE_PHONE_OPERATOR
GO
alter table ERMB_CLIENT_PHONE drop column MOBILE_PHONE_OPERATOR
GO

-- Номер ревизии: 50799
-- Номер версии: 1.18
-- Комментарий:   ENH047420: Проассоциировать иконки форм с самими формами
update PAYMENT_SERVICES set  IMAGE_NAME = REPLACE(IMAGE_NAME,'.png','.jpg')
go
update PAYMENT_SERVICES_OLD set  IMAGE_NAME = REPLACE(IMAGE_NAME,'.png','.jpg')
go

-- Номер ревизии: 50827
-- Номер версии: 1.18
-- Комментарий:   BUG055422: АС Филиал - в профиле старый идентификатор карты 

DECLARE 
    card INTEGER;
BEGIN

    FOR ermbProfiles IN (SELECT FOREG_PRODUCT_ID FROM ERMB_PROFILE where (FOREG_PRODUCT_ID IS NOT NULL)) LOOP
        select count(*)  into card  from CARD_LINKS where id = ermbProfiles.FOREG_PRODUCT_ID;
        if (card = 0) then
            update ERMB_PROFILE set FOREG_PRODUCT_ID = null where ERMB_PROFILE.FOREG_PRODUCT_ID = ermbProfiles.FOREG_PRODUCT_ID;
        end if;
     END LOOP;      
END;    
go

create index "DXFK_FOREG_PRODUCT" on ERMB_PROFILE
(
   FOREG_PRODUCT_ID
)
go

alter table ERMB_PROFILE
   add constraint FK_FOREG_PRODUCT foreign key (FOREG_PRODUCT_ID)
      references CARD_LINKS (ID)
      on delete cascade
go

-- Номер ревизии: 50834
-- Номер версии: 1.18
-- Комментарий: убрал лишнюю таблицу, починил уникальный индекс по ORDER_UUID для заказов, добавил ORDER_UUID в платежи

DROP TABLE ORDER_INFO;

DROP INDEX ORDERS_UUID;

CREATE UNIQUE INDEX ORDERS_UUID ON ORDERS(UUID);

ALTER TABLE BUSINESS_DOCUMENTS
ADD ORDER_UUID VARCHAR2(255)
;

UPDATE BUSINESS_DOCUMENTS doc
SET doc.ORDER_UUID=(SELECT UUID FROM ORDERS WHERE doc.ID=PAYMENT_ID)
;

-- Номер ревизии: 50841
-- Номер версии: 1.18
-- Комментарий: E-INVOICING (РЕЛИЗ 11.1): Доработки для МБК: Регистрация оферты в МБК

alter table SHOP_FIELDS add MOBILE_CHECKOUT_STATE varchar(20) 
go
alter table SHOP_FIELDS add MOBILE_CHECKOUT_PHONE varchar(30)
go
create index MOB_CHCKOUT_STTE_IDX on SHOP_FIELDS 
(
   MOBILE_CHECKOUT_STATE asc 
)
go
alter table SERVICE_PROVIDERS add MOBILE_CHECKOUT_AVAILABLE char(1) default '0' not null
go

-- Номер ревизии: 50847
-- Номер версии: 1.18
-- Комментарий: доработки справочников пфп (разбиение минимальной суммы продуктов по портфелям)

create table PFP_PRODUCT_PARAMETERS  (
   PRODUCT_ID  INTEGER NOT NULL,
   KEY_PARAMETER      VARCHAR2(16) NOT NULL,
   MIN_SUM NUMBER NOT NULL,
   constraint PK_PFP_PRODUCT_PARAMETERS primary key (PRODUCT_ID, KEY_PARAMETER)
)
go

create sequence S_PFP_PRODUCT_PARAMETERS
go

insert into PFP_PRODUCT_PARAMETERS (PRODUCT_ID, KEY_PARAMETER, MIN_SUM) select id, 'START_CAPITAL', MIN_SUM from PFP_PRODUCTS
go

insert into PFP_PRODUCT_PARAMETERS (PRODUCT_ID, KEY_PARAMETER, MIN_SUM) select id, 'QUARTERLY_INVEST', MIN_SUM from PFP_PRODUCTS
go

ALTER TABLE PFP_PRODUCTS DROP COLUMN MIN_SUM
go

create table PFP_C_PRODUCT_PARAMETERS  (
   PRODUCT_ID  INTEGER NOT NULL,
   KEY_PARAMETER      VARCHAR2(16) NOT NULL,
   MIN_SUM NUMBER NOT NULL,
   constraint PK_PFP_C_PRODUCT_PARAMETERS primary key (PRODUCT_ID, KEY_PARAMETER)
)
go

create sequence S_PFP_C_PRODUCT_PARAMETERS
go

ALTER TABLE PFP_COMPLEX_PRODUCTS MODIFY MIN_SUM NULL
go

insert into PFP_C_PRODUCT_PARAMETERS (PRODUCT_ID, KEY_PARAMETER, MIN_SUM) select id, 'START_CAPITAL', MIN_SUM from PFP_COMPLEX_PRODUCTS WHERE TYPE <> 'I'
go

insert into PFP_C_PRODUCT_PARAMETERS (PRODUCT_ID, KEY_PARAMETER, MIN_SUM) select id, 'QUARTERLY_INVEST', MIN_SUM from PFP_COMPLEX_PRODUCTS WHERE TYPE <> 'I'
go

UPDATE PFP_COMPLEX_PRODUCTS SET MIN_SUM = null WHERE TYPE <> 'I'
go

-- Номер ревизии: 50865
-- Номер версии: 1.18
-- Комментарий: установка типа формы платежа "IMA_PAYMENT" шаблонам по "покупка\продажа металлов"

update PAYMENTS_DOCUMENTS set FORM_TYPE = 'IMA_PAYMENT' where FORM_TYPE = 'INTERNAL_TRANSFER' and (DESTINATION_CURRENCY = 'A99' or DESTINATION_CURRENCY = 'A98' or DESTINATION_CURRENCY = 'A76' or DESTINATION_CURRENCY = 'A33' or CHARGEOFF_CURRENCY = 'A99' or CHARGEOFF_CURRENCY = 'A98' or CHARGEOFF_CURRENCY = 'A76' or CHARGEOFF_CURRENCY = 'A33')
go


-- Номер ревизии: 51011
-- Номер версии: 1.18
-- Комментарий: Cохранение риск профиля, заданного клиентом. 

create table PFP_PERSON_RISK_PROFILES  (
   PROFILE_ID           INTEGER                         not null,
   PRODUCT              VARCHAR2(10)                    not null,
   WEIGHT               INTEGER,
   constraint PK_PFP_PERSON_RISK_PROFILES primary key (PROFILE_ID, PRODUCT)
)
go

insert into PFP_PERSON_RISK_PROFILES (PROFILE_ID, PRODUCT, WEIGHT) select pfp.ID, ppw.PRODUCT, ppw.WEIGHT from PERSONAL_FINANCE_PROFILE pfp
join PFP_PRODUCTS_WEIGHTS ppw on pfp.RISK_PROFILE_ID = ppw.ID
go


-- Номер ревизии: 51023
-- Номер версии: 1.18
-- Комментарий: Добавление таблицы отложенных команд
create table ERMB_DELAYED_COMMANDS  (
   ID                   INTEGER                         not null,
   USER_ID              INTEGER                         not null,
   COMMAND_CLASS        VARCHAR2(256)                   not null,
   COMMAND_BODY         VARCHAR2(1024)                  not null,
   constraint PK_ERMB_DELAYED_COMMANDS primary key (ID)
)
;

create sequence S_ERMB_DELAYED_COMMANDS
;

-- Номер ревизии: 51110
-- Номер версии: 11.1
-- Комментарий: ENH057431: Добавить Информация о заказе в чек оплаты заказа.
alter table ORDERS add PRINT_DESC varchar(200)

-- Номер ревизии: 51115
-- Номер версии: 1.18
-- Комментарий: CHG054318: Убрать поиск по подстроке в журналах(поправка индекса)

drop index DUL_INDEX
go

create index DUL_INDEX on DOCUMENTS (
   REPLACE("DOC_SERIES"||"DOC_NUMBER",' ') ASC
)
go

-- Номер ревизии: 51131
-- Номер версии: 1.18
-- Комментарий: Создание справочника продуктов

create table PFP_PRODUCT_TYPE_PARAMETERS  (
   ID                   INTEGER                         not null,
   DICTIONARY_TYPE      VARCHAR2(100)                   not null,
   NAME                 VARCHAR2(100)                   not null,
   USE                  CHAR(1)                         not null,
   IMAGE_ID             INTEGER                         not null,
   DESCRIPTION          VARCHAR2(500)                   not null,
   USE_ON_DIAGRAM       CHAR(1)                         not null,
   USE_ON_TABLE         CHAR(1)                         not null,
   AXIS_USE_ZERO        CHAR(1),
   X_AXIS_NAME          VARCHAR2(50),
   X_AXIS_USE_STEPS     CHAR(1),
   Y_AXIS_NAME          VARCHAR2(50),
   LINK_NAME            VARCHAR2(100)                   not null,
   LINK_HINT            VARCHAR2(500)                   not null,
   LINK_IMAGE_ID        INTEGER                         not null,
   constraint PK_PFP_PRODUCT_TYPE_PARAMETERS primary key (ID)
)

go

create sequence S_PFP_PRODUCT_TYPE_PARAMETERS
go

create table PFP_PT_TARGET_GROUPS  (
   PRODUCT_ID           INTEGER                         not null,
   SEGMENT_CODE         VARCHAR2(16)                    not null,
   constraint PK_PFP_PT_TARGET_GROUPS primary key (PRODUCT_ID, SEGMENT_CODE)
)

go

create sequence S_PFP_PT_TARGET_GROUPS
go

create table PFP_DIAGRAM_STEPS  (
   PFP_TYPE_PARAMETERS_ID INTEGER                         not null,
   LIST_INDEX           INTEGER                         not null,
   STEP_FROM            INTEGER                         not null,
   STEP_TO              INTEGER                         not null,
   STEP_NAME            VARCHAR2(50)                    not null,
   constraint PK_PFP_DIAGRAM_STEPS primary key (PFP_TYPE_PARAMETERS_ID, LIST_INDEX)
)

go

create sequence S_PFP_DIAGRAM_STEPS
go

create table PFP_TABLE_COLUMNS  (
   PFP_TYPE_PARAMETERS_ID INTEGER                         not null,
   LIST_INDEX           INTEGER                         not null,
   COLUMN_NAME          VARCHAR2(50)                    not null,
   constraint PK_PFP_TABLE_COLUMNS primary key (PFP_TYPE_PARAMETERS_ID, LIST_INDEX)
)

go

create sequence S_PFP_TABLE_COLUMNS
go

create index "DXFK_PFP_SEGMENT_TO_PT" on PFP_PT_TARGET_GROUPS
(
   PRODUCT_ID
)
go

alter table PFP_PT_TARGET_GROUPS
   add constraint FK_PFP_SEGMENT_TO_PT foreign key (PRODUCT_ID)
      references PFP_PRODUCT_TYPE_PARAMETERS (ID)
      on delete cascade
go

create index "DXFK_PFP_AXIS_TO_PT" on PFP_DIAGRAM_STEPS
(
   PFP_TYPE_PARAMETERS_ID
)
go

alter table PFP_DIAGRAM_STEPS
   add constraint FK_PFP_AXIS_TO_PT foreign key (PFP_TYPE_PARAMETERS_ID)
      references PFP_PRODUCT_TYPE_PARAMETERS (ID)
      on delete cascade
go

create index "DXFK_PFP_TABLE_COLUMNS_TO_PT" on PFP_TABLE_COLUMNS
(
   PFP_TYPE_PARAMETERS_ID
)
go

alter table PFP_TABLE_COLUMNS
   add constraint FK_PFP_TABLE_COLUMNS_TO_PT foreign key (PFP_TYPE_PARAMETERS_ID)
      references PFP_PRODUCT_TYPE_PARAMETERS (ID)
go

-- Номер ревизии: 51198
-- Номер версии: 1.18
-- Комментарий: вынос настроек ПФП в отдельный файл
update PROPERTIES 
set CATEGORY = 'pfp.properties' 
where PROPERTY_KEY in ('com.rssl.iccs.settings.pfp.target.count','com.rssl.iccs.pfp.configure.period.default',
                       'com.rssl.iccs.pfp.configure.segment.available.mvcpotential','com.rssl.iccs.pfp.configure.segment.available.vip','com.rssl.iccs.pfp.configure.segment.available.mvc')
go

-- Номер ревизии: 51221
-- Номер версии: 1.18
-- Комментарий: вынос настроек ПФП в отдельный файл
update PROPERTIES 
set CATEGORY = 'pfpDictionary.properties' 
where PROPERTY_KEY in ('com.rssl.iccs.settings.pfp.target.count','com.rssl.iccs.pfp.configure.period.default',
                       'com.rssl.iccs.pfp.configure.segment.available.mvcpotential','com.rssl.iccs.pfp.configure.segment.available.vip','com.rssl.iccs.pfp.configure.segment.available.mvc')
go


-- Номер ревизии: 51261
-- Номер версии: 1.18
-- Комментарий: Создание справочника кредитных карт (ПФП)
create table PFP_CARDS  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(250)                   not null,
   PROGRAMM_TYPE        VARCHAR2(50)                    not null,
   INPUTS               NUMBER,
   BONUS                NUMBER,
   CLAUSE               VARCHAR2(250),
   CARD_ICON_ID         INTEGER                         not null,
   PROGRAMM_ICON_ID     INTEGER,
   DESCRIPTION          VARCHAR2(500)                   not null,
   RECOMMENDATION       VARCHAR2(500),
   DIAGRAM_USE_IMAGE    CHAR(1)                         not null,
   DIAGRAM_COLOR        VARCHAR2(7),
   DIAGRAM_ICON_ID      INTEGER,
   DIAGRAM_USE_NET      CHAR(1)                         not null,
   DIAGRAM_DESCRIPTION  VARCHAR2(500),
   constraint PK_PFP_CARDS primary key (ID)
)

go

create sequence S_PFP_CARDS
go

-- Номер ревизии: 51313
-- Номер версии: 1.18
-- Комментарий:  ENH057555: Доступность ПУ для создания СМС-шаблонов. 
ALTER TABLE SERVICE_PROVIDERS
ADD ( AVAILABLE_SMS_TEMPLATES CHAR(1) DEFAULT '0' NOT NULL );

-- Номер ревизии: 51385
-- Номер версии: 1.18
-- Комментарий:  Создание справочника кредитных карт (ПФП)
create unique index I_PFP_CARDS_NAME on PFP_CARDS (
   NAME ASC
)
go

ALTER TABLE PFP_CARDS modify DIAGRAM_COLOR        VARCHAR2(16)
go


-- Номер ревизии: 51465
-- Номер версии: 1.18
-- Комментарий:  Доработка справочника целей в АРМ сотрудника (ПФП)

ALTER TABLE PFP_TARGETS ADD (ONLY_ONE CHAR(1) DEFAULT '0' NOT NULL)
go

ALTER TABLE PFP_TARGETS ADD (LATER_ALL CHAR(1) DEFAULT '0' NOT NULL)
go

ALTER TABLE PFP_TARGETS ADD (LATER_LOANS CHAR(1) DEFAULT '0' NOT NULL)
go

-- Номер ревизии: 51482
-- Номер версии: 1.18
-- Комментарий:  Дополнения отчета о статистике прохождений финансового планирования

ALTER TABLE PERSONAL_FINANCE_PROFILE ADD (CREDIT_CARD_TYPE VARCHAR2(100))
go

-- Номер ревизии: 51509
-- Номер версии: 1.18
-- Комментарий:  Дополнения отчета о статистике прохождений финансового планирования

ALTER TABLE PERSONAL_FINANCE_PROFILE ADD MANAGER_OSB VARCHAR2(4)
go

ALTER TABLE PERSONAL_FINANCE_PROFILE ADD MANAGER_VSP VARCHAR2(7)
go

-- Номер ревизии: 51513
-- Номер версии: 1.18
-- Комментарий: Изменение механизма добавления целей в механизме проведения ПФП

drop table PFP_TARGET_LOAN_KINDS
go

alter table PERSON_TARGET drop column LOAN_NAME
go

-- Номер ревизии: 51532
-- Номер версии: 1.18
-- Комментарий: Добавлены иконки к страховым компаниям, отображение иконки компаний при прохождении ПФП

alter table PFP_INSURANCE_COMPANIES add IMAGE_ID INTEGER
go

-- Номер ревизии: 51534
-- Номер версии: 1.18
-- Комментарий: CHG057902: Реализовать в схеме прав администратора настройки (отдельные) отключения режимов частичной и полной репликации поставщиков услуг 

alter table PROVIDER_REPLICA_TASKS add PROPERTIES BLOB
go

-- Номер ревизии: 51550
-- Номер версии: 1.18
-- Комментарий: ПФП. Сохранение выбранной кредитной карты. 
alter table PERSONAL_FINANCE_PROFILE add (CREDIT_CARD_ID INTEGER)


-- Номер ревизии: 51570
-- Номер версии: 1.18
-- Комментарий: ПФП. Создание справочника "Справочника рисков"
create table PFP_RISKS  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(50)                    not null,
   constraint PK_PFP_RISKS primary key (ID)
)
go

create sequence S_PFP_RISKS
go

create unique index I_PFP_RISK_NAME on PFP_RISKS (
   NAME ASC
)
go

-- Номер ревизии: 51605
-- Номер версии: 1.18
-- Комментарий: ПФП. Создание справочника "Справочника горизонтов инвестирования"

create table PFP_INVESTMENT_PERIODS  (
   ID                   INTEGER                         not null,
   PERIOD               VARCHAR2(50)                    not null,
   constraint PK_PFP_INVESTMENT_PERIODS primary key (ID)
)

go

create sequence S_PFP_INVESTMENT_PERIODS
go

-- Номер ревизии: 51625
-- Номер версии: 1.18
-- Комментарий: Реализовать связку вклада/ОМС ПФП с вкладом/ОМС ЕРИБ

alter table PFP_PRODUCTS add SBOL_PRODUCT_ID INTEGER
go

-- Номер ревизии: 51652
-- Номер версии: 1.18
-- Комментарий: Для продуктов: вклады, ОМС, ПИФ параметры отображения на графике(координаты) и в таблице(данные для полей)(БД)
 
ALTER TABLE PFP_TABLE_COLUMNS RENAME COLUMN LIST_INDEX TO ORDER_INDEX
go
alter TABLE PFP_TABLE_COLUMNS add ID INTEGER not null
go
alter table PFP_TABLE_COLUMNS drop CONSTRAINT PK_PFP_TABLE_COLUMNS
go
alter table PFP_TABLE_COLUMNS add CONSTRAINT PK_PFP_TABLE_COLUMNS PRIMARY KEY (ID)
go
alter table PFP_TABLE_COLUMNS modify PFP_TYPE_PARAMETERS_ID null
go
-- Номер ревизии: 51604
-- Номер версии: 1.18
-- Комментарий: ENH057379 Доработки по шаблонам документов (Модель БД)
ALTER TABLE BUSINESS_DOCUMENTS MODIFY (ADDITION_CONFIRM VARCHAR2(2));
UPDATE BUSINESS_DOCUMENTS SET ADDITION_CONFIRM = '1' where ADDITION_CONFIRM = 'CC';
UPDATE BUSINESS_DOCUMENTS SET ADDITION_CONFIRM = '4' where ADDITION_CONFIRM = 'SD';
ALTER TABLE BUSINESS_DOCUMENTS MODIFY (ADDITION_CONFIRM CHAR(1));
DROP TABLE BUSINESS_DOCUMENTS_RES;

-- Номер ревизии: 51662
-- Номер версии: 1.18
-- Комментарий: ПФП. Изменение формы «Создание кредитного продукта». 
delete from PFP_LOAN_KINDS
go
alter table PFP_LOAN_KINDS add FROM_AMOUNT NUMBER not null
go
alter table PFP_LOAN_KINDS add TO_AMOUNT NUMBER not null
go
alter table PFP_LOAN_KINDS add FROM_PERIOD INTEGER not null
go
alter table PFP_LOAN_KINDS add TO_PERIOD INTEGER not null
go
alter table PFP_LOAN_KINDS add DEFAULT_PERIOD INTEGER not null
go
alter table PFP_LOAN_KINDS add FROM_RATE NUMBER not null
go
alter table PFP_LOAN_KINDS add TO_RATE NUMBER not null
go
alter table PFP_LOAN_KINDS add DEFAULT_RATE NUMBER not null
go
alter table PFP_LOAN_KINDS add IMAGE_ID INTEGER not null
go

-- Номер ревизии: 51676
-- Номер версии: 1.18
-- Комментарий: ПФП. Создание справочника "Доверительное управление"
alter table PFP_PRODUCTS add RISK_ID INTEGER
go

alter table PFP_PRODUCTS add INVESTMENT_PERIOD_ID INTEGER
go

alter table PFP_PRODUCTS modify FOR_COMPLEX null
go

create index "DXFK_PFP_RISK_TO_PRODUCT" on PFP_PRODUCTS
(
   RISK_ID
)
go

alter table PFP_PRODUCTS
   add constraint FK_PFP_RISK_TO_PRODUCT foreign key (RISK_ID)
      references PFP_RISKS (ID)
go

create index "DXFK_PFP_INV_PERIOD_TO_PRODUCT" on PFP_PRODUCTS
(
   INVESTMENT_PERIOD_ID
)
go

alter table PFP_PRODUCTS
   add constraint FK_PFP_INV_PERIOD_TO_PRODUCT foreign key (INVESTMENT_PERIOD_ID)
      references PFP_INVESTMENT_PERIODS (ID)
go

-- Номер ревизии: 51676
-- Номер версии: 1.18
-- Комментарий: ПФП. Изменение формы "Создание риск-профиля". 
alter table PFP_PRODUCTS_WEIGHTS modify PRODUCT VARCHAR2(15)
go

alter table PFP_PERSON_RISK_PROFILES modify PRODUCT VARCHAR2(15)
go

insert into PFP_PRODUCTS_WEIGHTS (ID, PRODUCT,WEIGHT) 
	select id, 'trustManaging', '0' 
		from PFP_PRODUCTS_WEIGHTS group by id 
go

-- Номер ревизии: 51697
-- Номер версии: 1.18
-- Комментарий: ПФП. Создание справочника "Типы пенсионных продуктов"
create table PFP_PENSION_FUND  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(50)                    not null,
   ICON_ID              INTEGER,
   constraint PK_PFP_PENSION_FUND primary key (ID)
)
go

create sequence S_PFP_PENSION_FUND
go


-- Номер ревизии: 51756
-- Номер версии: 1.18
-- Комментарий: ПФП. Для продуктов: вклады, ОМС, ПИФ параметры отображения на графике(координаты) и в таблице(данные для полей)

create table PFP_SP_TABLE_VIEW_PARAMETERS(
    PRODUCT_ID INTEGER not null,
    TABLE_COLUMN_ID INTEGER not NULL,
    VALUE varchar2(100) not null,
    constraint PK_PFP_SP_TBL_VW_PARAMETERS primary key (PRODUCT_ID, TABLE_COLUMN_ID)
)
go
alter table PFP_SP_TABLE_VIEW_PARAMETERS
   add constraint FK_PFP_SP_T_FK_TABLE_PFP_TABL foreign key (TABLE_COLUMN_ID)
      references PFP_TABLE_COLUMNS (ID)
      on delete cascade
go
create index "DXFK_PFP_SP_T_FK_TABLE_PFP_TAB" on PFP_SP_TABLE_VIEW_PARAMETERS
(
   TABLE_COLUMN_ID
)
go
alter table PFP_PRODUCTS add USE_ICON char(1) default '0' not null 
go
alter table PFP_PRODUCTS add AXIS_X INTEGER
go
alter table PFP_PRODUCTS add AXIS_Y INTEGER
go

-- Номер ревизии: 51760
-- Номер версии: 1.18
-- Комментарий: ПФП. Добавление справочника Пенсионных продуктов

create table PFP_PRODUCT_BASE  (
   ID                   INTEGER                         not null,
   TYPE                 CHAR(1)                         not null,
   NAME                 VARCHAR2(250)                   not null,
   DESCRIPTION          VARCHAR2(500),
   IMAGE_ID             INTEGER,
   MIN_INCOME           NUMBER(7,2),
   MAX_INCOME           NUMBER(7,2),
   DEFAULT_INCOME       NUMBER(7,2),
   constraint PK_PFP_PRODUCT_BASE primary key (ID)
)
go

create sequence S_PFP_PRODUCT_BASE
go

create table PFP_PENSION_PRODUCT  (
   PRODUCT_BASE_ID      INTEGER                         not null,
   PENSION_FUND_ID      INTEGER                         not null,
   ENTRY_FEE            NUMBER                          not null,
   QUARTERLY_FEE        NUMBER                          not null,
   MIN_PERIOD           INTEGER                         not null,
   MAX_PERIOD           INTEGER                         not null,
   DEFAULT_PERIOD       INTEGER                         not null,
   constraint PK_PFP_PENSION_PRODUCT primary key (PRODUCT_BASE_ID)
)
go

create sequence S_PFP_PENSION_PRODUCT
go

create table PFP_PRODUCT_TARGET_GROUPS  (
   PRODUCT_ID           INTEGER                         not null,
   SEGMENT_CODE         VARCHAR2(20)                    not null,
   constraint PK_PFP_PRODUCT_TARGET_GROUPS primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

create sequence S_PFP_PRODUCT_TARGET_GROUPS
go

create index "DXFK_PFP_PENSION_PRODUCT_TO_FU" on PFP_PENSION_PRODUCT
(
   PENSION_FUND_ID
)
go

alter table PFP_PENSION_PRODUCT                               
   add constraint FK_PFP_PENSION_PRODUCT_TO_FUND foreign key (PENSION_FUND_ID)
      references PFP_PENSION_FUND (ID)
go               

alter table PFP_PENSION_PRODUCT
   add constraint FK_PFP_PENSION_TO_PRODUCT foreign key (PRODUCT_BASE_ID)
      references PFP_PRODUCT_BASE (ID)
      on delete cascade
go

create index "DXFK_PFP_TARGET_GR_TO_PRODUCT" on PFP_PRODUCT_TARGET_GROUPS
(
   PRODUCT_ID
)
go

alter table PFP_PRODUCT_TARGET_GROUPS
   add constraint FK_PFP_TARGET_GR_TO_PRODUCT foreign key (PRODUCT_ID)
      references PFP_PRODUCT_BASE (ID)
      on delete cascade
go


-- Номер ревизии: 51777
-- Номер версии: 1.18
-- Комментарий: ПФП. Изменения формы «Условия отображения рекомендаций»

delete from PROPERTIES where PROPERTY_KEY='period.achieve.required.amount'
GO

-- Номер ревизии: 51806
-- Номер версии: 1.18
-- Комментарий: ПФП. Доработка справочника вопросов

ALTER TABLE PFP_QUESTIONS ADD SEGMENT VARCHAR2(16)
go

-- Номер ревизии: 51812
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. Добавление индексов к таблице отложенных команд.
create index DXFK_DELAYED_TO_USERS on ERMB_DELAYED_COMMANDS
(
   USER_ID
)
;

alter table ERMB_DELAYED_COMMANDS
   add constraint FK_DELAYED_TO_USERS foreign key (USER_ID)
      references USERS (ID)
      on delete cascade
;


-- Номер ревизии: 51841
-- Номер версии: 1.18
-- Комментарий: Добавление признака «Универсальный продукт».

alter table PFP_PRODUCTS add UNIVERSAL char(1) default '0' not null
go
alter table PFP_PRODUCT_BASE add  UNIVERSAL char(1) default '0' not null
go
alter table PFP_INSURANCE_PRODUCTS add UNIVERSAL char(1) default '0' not null
go
alter table PFP_COMPLEX_PRODUCTS add UNIVERSAL char(1) default '0' not null
go
create unique index I_PFP_PRODUCTS_UNIVERSAL_P on PFP_PRODUCTS(
     decode(UNIVERSAL, '0', null, '1'), 
     decode(UNIVERSAL,'1', TYPE, null)
)
go
create unique index I_PFP_PRODUCTS_UNIVERSAL_CP on PFP_COMPLEX_PRODUCTS
(
    decode(UNIVERSAL, '0', null, '1'), 
    decode(UNIVERSAL,'1', TYPE, null)
)
go
create unique index I_PFP_PRODUCTS_UNIVERSAL_PB on PFP_PRODUCT_BASE
(
    decode(UNIVERSAL, '0', null, '1'), 
    decode(UNIVERSAL,'1', TYPE, null)
)
go
create unique index I_PFP_PRODUCTS_UNIVERSAL_IP on PFP_INSURANCE_PRODUCTS 
(
    decode(UNIVERSAL,'0', null, '1')
)
go

-- Номер ревизии: 51851
-- Номер версии: 1.18
-- Комментарий: ПФП. Доработка справочника риск-профилей 
ALTER TABLE PFP_RISK_PROFILES ADD SEGMENT VARCHAR2(16)
go

-- Номер ревизии: 51870
-- Номер версии: 1.18
-- Комментарий: Доработка репликации подразделений : Подготовка к реализации основного алгоритма (изменение формы редактирования подразделения, базы)
alter table OPERATIONDESCRIPTORS modify OPERATIONKEY VARCHAR2(50)
go

alter table DEPARTMENTS add AUTOMATION_TYPE varchar2(15)
go

-- Номер ревизии: 51812
-- Номер версии: 1.18
-- Комментарий: Доработка репликации подразделений : Создание формы создание фоновой задачи, списка фоновых задач для репликации

create table DEPARTMENTS_REPLICA_TASKS  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   CREATION_DATE        TIMESTAMP                       not null,
   STATE_CODE           VARCHAR2(20)                    not null,
   REPLICATION_MODE     VARCHAR2(10)                    not null,
   REPORT_START_DATE    TIMESTAMP,
   REPORT_END_DATE      TIMESTAMP,
   CONTENT              BLOB,
   DEPARTMENTS          VARCHAR2(200),
   DETAILED_REPORT      CLOB,
   SOURCE_ERRORS        NUMBER(22,0),
   SOURCE_SUCCESS       NUMBER(22,0),
   DEST_INSERED         NUMBER(22,0),
   DEST_UPDATED         NUMBER(22,0),
   DEST_DELETED         NUMBER(22,0),
   constraint PK_DEPARTMENTS_REPLICA_TASKS primary key (ID)
)
go

create sequence S_DEPARTMENTS_REPLICA_TASKS
go

create index "DXFK_DEPARTMENTS_REPL_LOGINS" on DEPARTMENTS_REPLICA_TASKS
(
   LOGIN_ID
)
go

alter table DEPARTMENTS_REPLICA_TASKS
   add constraint FK_DEPARTMENTS_REPL_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go

-- Номер ревизии: 51883
-- Номер версии: 1.18
-- Комментарий:  Добавить видимость продукта при проведении ПФП

alter table PFP_PRODUCTS add ENABLED char(1) default '1' not null
go
alter table PFP_PRODUCT_BASE add  ENABLED char(1) default '1' not null
go
alter table PFP_INSURANCE_PRODUCTS add ENABLED char(1) default '1' not null
go


-- Номер ревизии: 51901
-- Номер версии: 1.18
-- Комментарий:  ПФП. Финаносвый прогноз. Добавление кредитов. Часть 1.

create table PFP_PERSON_LOAN  (
   ID                   INTEGER                         not null,
   PERSON_FINANCE_PROFILE_ID INTEGER,
   DICTIONARY_LOAN_ID   INTEGER                         not null,
   NAME                 VARCHAR2(120)                   not null,
   USER_COMMENT         VARCHAR2(100),
   START_DATE           TIMESTAMP                       not null,
   END_DATE             TIMESTAMP                       not null,
   RATE                 NUMBER(7,2)                     not null,
   AMOUNT               NUMBER(19,4)                    not null,
   CURRENCY             CHAR(3)                         not null,
   LIST_INDEX           INTEGER,
   constraint PK_PFP_PERSON_LOAN primary key (ID)
)
go

create sequence S_PFP_PERSON_LOAN
go

create index "DXFK_LOAN_TO_PROFILE" on PFP_PERSON_LOAN
(
   PERSON_FINANCE_PROFILE_ID
)
go

alter table PFP_PERSON_LOAN
   add constraint FK_PFP_PERS_FK_LOAN_T_PERSONAL foreign key (PERSON_FINANCE_PROFILE_ID)
      references PERSONAL_FINANCE_PROFILE (ID)
      on delete cascade
go

-- Номер ревизии: 51918
-- Номер версии: 1.18
-- Комментарий:   BUG055422: АС Филиал - в профиле старый идентификатор карты 

alter table ERMB_PROFILE drop constraint FK_FOREG_PRODUCT
go

alter table ERMB_PROFILE
   add constraint FK_FOREG_PRODUCT foreign key (FOREG_PRODUCT_ID)
      references CARD_LINKS (ID)
      on delete set null
go

-- Номер ревизии: 51930
-- Номер версии: 1.18
-- Комментарий: ПФП. Доработка справочника риск-профилей

UPDATE PFP_QUESTIONS SET SEGMENT = 'NOTEXISTS' WHERE SEGMENT IS NULL
go

ALTER TABLE PFP_QUESTIONS MODIFY SEGMENT NOT NULL
go

UPDATE PFP_RISK_PROFILES SET SEGMENT = 'NOTEXISTS' WHERE SEGMENT IS NULL
go

ALTER TABLE PFP_RISK_PROFILES MODIFY SEGMENT NOT NULL
go

INSERT INTO PFP_PT_TARGET_GROUPS (PRODUCT_ID, SEGMENT_CODE)
SELECT ID, 'MVCPOTENTIAL' 
FROM PFP_PRODUCT_TYPE_PARAMETERS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_PT_TARGET_GROUPS)
UNION
SELECT ID, 'VIP' 
FROM PFP_PRODUCT_TYPE_PARAMETERS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_PT_TARGET_GROUPS)
UNION
SELECT ID, 'MVC' 
FROM PFP_PRODUCT_TYPE_PARAMETERS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_PT_TARGET_GROUPS)
UNION
SELECT ID, 'NOTEXISTS' 
FROM PFP_PRODUCT_TYPE_PARAMETERS
go

INSERT INTO PFP_SP_TARGET_GROUPS_BUNDLE (PRODUCT_ID, SEGMENT_CODE)
SELECT ID, 'MVCPOTENTIAL' 
FROM PFP_PRODUCTS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_SP_TARGET_GROUPS_BUNDLE)
UNION
SELECT ID, 'VIP' 
FROM PFP_PRODUCTS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_SP_TARGET_GROUPS_BUNDLE)
UNION
SELECT ID, 'MVC' 
FROM PFP_PRODUCTS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_SP_TARGET_GROUPS_BUNDLE)
UNION
SELECT ID, 'NOTEXISTS' 
FROM PFP_PRODUCTS
go

INSERT INTO PFP_PRODUCT_TARGET_GROUPS (PRODUCT_ID, SEGMENT_CODE)
SELECT ID, 'MVCPOTENTIAL' 
FROM PFP_PRODUCT_BASE 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_PRODUCT_TARGET_GROUPS)
UNION
SELECT ID, 'VIP' 
FROM PFP_PRODUCT_BASE 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_PRODUCT_TARGET_GROUPS)
UNION
SELECT ID, 'MVC' 
FROM PFP_PRODUCT_BASE 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_PRODUCT_TARGET_GROUPS)
UNION
SELECT ID, 'NOTEXISTS' 
FROM PFP_PRODUCT_BASE
go

INSERT INTO PFP_CP_TARGET_GROUPS_BUNDLE (PRODUCT_ID, SEGMENT_CODE)
SELECT ID, 'MVCPOTENTIAL' 
FROM PFP_COMPLEX_PRODUCTS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_CP_TARGET_GROUPS_BUNDLE)
UNION
SELECT ID, 'VIP' 
FROM PFP_COMPLEX_PRODUCTS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_CP_TARGET_GROUPS_BUNDLE)
UNION
SELECT ID, 'MVC' 
FROM PFP_COMPLEX_PRODUCTS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_CP_TARGET_GROUPS_BUNDLE)
UNION
SELECT ID, 'NOTEXISTS' 
FROM PFP_COMPLEX_PRODUCTS
go

INSERT INTO PFP_IP_TARGET_GROUPS_BUNDLE (PRODUCT_ID, SEGMENT_CODE)
SELECT ID, 'MVCPOTENTIAL' 
FROM PFP_INSURANCE_PRODUCTS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_IP_TARGET_GROUPS_BUNDLE)
UNION
SELECT ID, 'VIP' 
FROM PFP_INSURANCE_PRODUCTS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_IP_TARGET_GROUPS_BUNDLE)
UNION
SELECT ID, 'MVC' 
FROM PFP_INSURANCE_PRODUCTS 
WHERE ID NOT IN (SELECT PRODUCT_ID FROM PFP_IP_TARGET_GROUPS_BUNDLE)
UNION
SELECT ID, 'NOTEXISTS' 
FROM PFP_INSURANCE_PRODUCTS
go

-- Номер ревизии: 51963
-- Номер версии: 1.18
-- Комментарий: ENH058124: Запрос курсов валют.
create index RATE_IDX_FROMCUR_DEP_RATETYPE on RATE (
   FROM_CURRENCY ASC,
   DEPARTMENT_ID ASC,
   RATE_TYPE ASC
)
go

-- Номер ревизии: 51996
-- Номер версии: 1.18
-- Комментарий: BUG058547: Не добавляются страховые и НПФ продукты в БД 
ALTER TABLE INSURANCE_LINKS MODIFY INSURANCE_NAME VARCHAR(256)
go

-- Номер ревизии: 52011
-- Номер версии: 1.18
-- Комментарий: ЕРМБ: перенести смс-текстовки в БД
ALTER TABLE SMS_RESOURCES MODIFY TEXT NULL
go

-- Номер ревизии: 52031
-- Номер версии: 1.18
-- Комментарий: работа над ошибками
update ERMB_PROFILE set DAYS_OF_WEEK = 'SAT,FRI,WED,TUE,MON,SUN,THU' where DAYS_OF_WEEK IS NULL
go
update ERMB_PROFILE set TIME_START = '00:00:00' where TIME_START IS NULL
go
update ERMB_PROFILE set TIME_END = '23:59:59' where TIME_END IS NULL
go
alter table ERMB_PROFILE modify TIME_START default('00:00:00') not null
go
alter table ERMB_PROFILE modify TIME_END default('23:59:59') not null
go
alter table ERMB_PROFILE modify DAYS_OF_WEEK default('SAT,FRI,WED,TUE,MON,SUN,THU') not null
go

-- Номер ревизии: 52117 
-- Номер версии: 1.18
-- Комментарий: Справочник доверенных получателей дляы режимов Ligh и Про
alter table FIELD_DESCRIPTIONS add (BUSINESS_SUB_TYPE VARCHAR2(10));
alter table SERVICE_PROVIDERS add (SUB_TYPE VARCHAR2(10));

create table RECENTLY_FILLED_FIELD_DATA  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   RECEIVER_FIRST_NAME  VARCHAR2(120),
   RECEIVER_SUR_NAME    VARCHAR2(120),
   RECEIVER_PATR_NAME    VARCHAR2(120),
   PHONE_NUMBER         VARCHAR2(20),
   OUR_CARD_NUMBER      VARCHAR2(20),
   PROVIDER_GUID        INTEGER,
   IS_SELF_PHONE_NUMBER CHAR(1)                         not null,
   IWALLET_NUMBER       VARCHAR2(20),
   EXTERNAL_ACCOUNT_NUMBER VARCHAR2(25),
   RECEIVER_BANK_BIC    VARCHAR2(9),
   RECEIVER_BANK_NAME   VARCHAR2(128),
   OUR_ACCOUNT_NUMBER   VARCHAR2(25),
   VISA_CARD_NUMBER     VARCHAR2(20),
   MASTERCARD_CARD_NUMBER VARCHAR2(20),
   POPULARITY           INTEGER                         not null,
   constraint PK_RECENTLY_FILLED_FIELD_DATA primary key (ID)
);

create sequence S_RECENTLY_FILLED_FIELD_DATA;

create unique index U_LOGIN_PHONE_CARD_FIO on RECENTLY_FILLED_FIELD_DATA (
   LOGIN_ID ASC,
   RECEIVER_FIRST_NAME ASC,
   RECEIVER_SUR_NAME ASC,
   RECEIVER_PATR_NAME ASC,
   PHONE_NUMBER ASC,
   OUR_CARD_NUMBER ASC
);

create unique index U_LOGIN_IWALLET_PROVIDER on RECENTLY_FILLED_FIELD_DATA (
   LOGIN_ID ASC,
   PROVIDER_GUID ASC,
   IWALLET_NUMBER ASC
);

create unique index U_LOGIN_VISA on RECENTLY_FILLED_FIELD_DATA (
   LOGIN_ID ASC,
   VISA_CARD_NUMBER ASC
);

create unique index U_LOGIN_MASTERCARD on RECENTLY_FILLED_FIELD_DATA (
   LOGIN_ID ASC,
   MASTERCARD_CARD_NUMBER ASC
);

create unique index U_LOGIN_EXTERNAL_ACCOUNT_BIC on RECENTLY_FILLED_FIELD_DATA (
   LOGIN_ID ASC,
   EXTERNAL_ACCOUNT_NUMBER ASC,
   RECEIVER_BANK_BIC ASC
);

create unique index U_LOGIN_OUR_ACCOUNT_BIC on RECENTLY_FILLED_FIELD_DATA (
   LOGIN_ID ASC,
   RECEIVER_BANK_BIC ASC,
   OUR_ACCOUNT_NUMBER ASC
);


-- Номер ревизии: 52198
-- Номер версии: 1.18
-- Комментарий: BUG058827: Ошибка в маппинге системных ошибок 
delete from EXCEPTION_ENTRY
where KIND = 'I' and
APPLICATION not in ('PhizIA', 'PhizIC', 'mobile', 'mobile2', 'mobile3', 'mobile4', 'mobile5', 'mobile6', 'atm', 'Gate', 'Scheduler')
/

-- Номер ревизии: 52283
-- Номер версии: 1.18
-- Комментарий: BUG057746: Лишние запросы курсов валют 
alter table RATE add DYNAMIC_EXCHANGE varchar2(20);

-- Номер ревизии: 52346
-- Номер версии: 1.18
-- Комментарий:  ENH058567 	Выборка данных из таблицы DEPARTMENTS
drop index I_DEPARTMENTS_TB
go
drop index I_DEPARTMENTS_OSB
go
drop index I_DEPARTMENTS_OFFICE 
go
create unique index I_DEPARTMENTS_BANK_INFO on DEPARTMENTS (
TB,			
nvl(OSB, 'NULL'),
nvl(OFFICE, 'NULL')
)
go

-- Номер ревизии:  52366
-- Номер версии: 1.18
-- Комментарий:  Настройка мобильных платформ
create table MOBILE_PLATFORMS
(
  ID                 INTEGER not null,
  PLATFORM_ID        VARCHAR2(10) not null,
  PLATFORM_NAME      VARCHAR2(100) not null,
  VERSION            VARCHAR2(100) not null,
  ERROR_TEXT         VARCHAR2(500) not null,
  PLATFORM_ICON      INTEGER,
  DOWNLOAD_FROM_SBRF CHAR(1) not null,
  BANK_URL           VARCHAR2(100),
  EXTERNAL_URL       VARCHAR2(100),
  USE_QR             CHAR(1) not null,
  QR_NAME            VARCHAR2(100),
  IS_LIGHT_SCHEME    CHAR(1) not null
);

alter table MOBILE_PLATFORMS
  add constraint PK_MOBILE_PLATFORMS primary key (ID);
alter table MOBILE_PLATFORMS
  add constraint UK_UNIQUE_PLATFORM_ID unique (PLATFORM_ID);

create sequence S_MOBILE_PLATFORMS;

-- Номер ревизии:  52380
-- Номер версии: 1.18
-- Комментарий:  Подарочные карты iTunes(АРМ клиента)
alter table FIELD_DESCRIPTIONS add EXTENDED_DESC_DATA_ID varchar2(50)
go
alter table FIELD_DESCRIPTIONS modify NAME NVARCHAR2(60)
go

alter table SERVICE_PROVIDERS add PAYMENT_FORM_INFO_WEB CLOB
go
alter table SERVICE_PROVIDERS add PAYMENT_FORM_INFO_MAPI CLOB
go


-- Номер ревизии: 52395
-- Номер версии: 1.18
-- Комментарий: 
--              ПФП. Реализовать привязку сотрудников к каналам.
--              CHG058657: На форму «Журнал проведения ПФП» необходимо добавить поле "Канал" 
--              CHG059249: При удалении канала из справочника каналов необходимо помечать удаленный канал специальным 

create table PFP_CHANNELS(
    ID INTEGER not null,
    NAME VARCHAR2(100) not null,
    DELETED CHAR(1)    not null,
    constraint PK_PFP_CHANNELS primary key (ID) 
)
go

create unique index I_PFP_CHANNELS_NAME on PFP_CHANNELS (NAME)
go

create sequence S_PFP_CHANNELS
go 

 
alter table EMPLOYEES add CHANNEL_ID INTEGER
go

alter table PERSONAL_FINANCE_PROFILE add CHANNEL_ID INTEGER
go


-- Номер ревизии: 52434
-- Номер версии: 1.18
-- Комментарий: CHG059185: С формы «Редактирование раздела» необходимо убрать поле «Иконка» 

alter table PFP_PRODUCT_TYPE_PARAMETERS drop column LINK_IMAGE_ID
go

-- Номер ревизии:  52520
-- Номер версии: 1.18
-- Комментарий:  Доработка статистики обработки сообщений

alter table MAIL add RESPONSE_TIME integer
go

-- Номер ревизии:  52609
-- Номер версии: 1.18
-- Комментарий:  Изменения формы «Цели и средства»
ALTER TABLE PERSONAL_FINANCE_PROFILE RENAME COLUMN SHORT_TERM_ASSETS_AMOUNT TO SBRF_ACCOUNTS_AMOUNT
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE RENAME COLUMN SHORT_TERM_ASSETS_CURRENCY TO SBRF_ACCOUNTS_CURRENCY
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE RENAME COLUMN MEDIUM_TERM_ASSETS_AMOUNT TO INVESTMENTS_OTHER_AMOUNT
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE RENAME COLUMN MEDIUM_TERM_ASSETS_CURRENCY TO INVESTMENTS_OTHER_CURRENCY
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE ADD OTHER_BANKS_ACCOUNTS_AMOUNT NUMBER default null
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE ADD OTHER_BANKS_ACCOUNTS_CURRENCY CHAR(3) default null
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE ADD CASH_AMOUNT NUMBER default null
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE ADD CASH_CURRENCY CHAR(3) default null
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE ADD INVESTMENTS_FUNDS_AMOUNT NUMBER default null
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE ADD INVESTMENTS_FUNDS_CURRENCY CHAR(3) default null
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE ADD INVESTMENTS_IMA_AMOUNT NUMBER default null
GO
ALTER TABLE PERSONAL_FINANCE_PROFILE ADD INVESTMENTS_IMA_CURRENCY CHAR(3) default null
GO

-- Номер ревизии:  52574
-- Номер версии: 1.18
-- Комментарий: Подарочные карты iTunes(АРМ клиента). Добавление соглашения iTunes. 
insert into EXTENDED_DESCRIPTION_DATA(ID, NAME, CONTENT) values 
(S_EXTENDED_DESCRIPTION_DATA.nextval, 'itunes-agreement', 
'<h2>Приобретайте музыку, фильмы, игры, приложения и многое другое.</h2>
<div>
    Действуют только в магазине iTunes в РФ, оператором которого является iTunes S.a.r.l. Требуется наличие учетной записи iTunes и предварительное согласие с условиями лицензии и использования. Для создания учетной записи Вы должны быть старше 13 лет и находиться в РФ. Требуется наличие совместимого программного обеспечения, аппаратного обеспечения, а также доступ к Интернету. Не подлежат обмену на наличные денежные средства; возврат или обмен не осуществляются (за исключением случаев, предусмотренных законом). Пин-код не может использоваться для приобретения подарочных сертификатов, карт iTunes, каких-либо других товаров, получения скидок или подарков в магазине iTunes. Пин-код не может использоваться для совершения покупок в интернет магазинах Apple и в розничных магазинах Apple. Неиспользованные средства по пин-коду не подлежат передаче. Cбор и использование данных осуществляется в соответствии с Политикой конфиденциальности для клиентов Apple, см. www.apple.com/privacy, если не указано иное. Риск утраты и право использования пин-кода переходят к покупателю в момент передачи. Стоимость пин-кода полностью зачисляется на учетную запись. Пин-коды выпускаются, реализуются и находятся под управлением компании iTunes S.A.R.L. iTunes S.A.R.L. не несет ответственность за какой-либо убыток или ущерб в результате утраты или кражи кода, или его использования без разрешения. Компания iTunes S.A.R.L., а также ее лицензиаты, аффилированные лица и лицензиары не дают никаких гарантий, прямых или подразумеваемых, в отношении пин-кодов или магазина iTunes, и отказываются от всех гарантий в максимально возможной степени. Указанные ограничения могут не распространяться на Вас. Недействительны в тех случаях, когда действует запрет. Не предназначены для перепродажи. Действуют полные условия, которые могут периодически изменяться, см. apple.com/legal/itunes/appstore/ru/terms.html. Контент и цены определяются с учетом доступности на момент фактической загрузки. Контент, приобретаемый в магазине iTunes, предназначается только для законного личного использования. Не крадите музыку. © 2013 г., iTunes S.A.R.L. Все права защищены.
</div>')
go

-- Номер ревизии:  52615
-- Номер версии: 1.18
-- Комментарий: BUG059566: Исключение в логах при совершении перевода частному лицу 
drop index U_LOGIN_PHONE_CARD_FIO;
drop index U_LOGIN_IWALLET_PROVIDER;
drop index U_LOGIN_VISA;
drop index U_LOGIN_MASTERCARD;
drop index U_LOGIN_EXTERNAL_ACCOUNT_BIC;
drop index U_LOGIN_OUR_ACCOUNT_BIC;

-- Номер ревизии: 52697
-- Номер версии: 1.18
-- Комментарий: CHG059689: Убрать галку в настройках ПУ "Поставщик МБ"
alter table SERVICE_PROVIDERS drop column AVAILABLE_SMS_TEMPLATES
go

-- Номер ревизии: 52731
-- Номер версии: 1.18
-- Комментарий: доработка загрузки справочников ПФП

update PFP_RISK_PROFILES set IS_DEFAULT = '0'
where NAME != 'DEFAULT_PROFILE' and DESCRIPTION != 'DEFAULT_PROFILE_DESCRIPTION' and IS_DELETED != '1'
/

-- Номер ревизии: 52767
-- Номер версии: 1.18
-- Комментарий: Добавление таблицы Регистрация клиентов ЕРМБ

create table ERMB_REGISTRATION  (
   ID                   INTEGER                         not null,
   PROFILE_ID           INTEGER                         not null,
   PHONE_NUMBER         VARCHAR2(20)                    not null,
   constraint PK_ERMB_REGISTRATION primary key (ID)
)
;

create sequence S_ERMB_REGISTRATION
;

create unique index UI_ERMB_REGISTRATION on ERMB_REGISTRATION (
   PHONE_NUMBER ASC
)
;

create index "DXFK_PROFILE_TO_REGISTRATION" on ERMB_REGISTRATION
(
   PROFILE_ID
)
;

alter table ERMB_REGISTRATION
   add constraint FK_ERMB_REG_FK_PROFIL_ERMB_PRO foreign key (PROFILE_ID)
      references ERMB_PROFILE (PERSON_ID)
;

-- Номер ревизии: 52794
-- Номер версии: 1.18
-- Комментарий: BUG059344: Запретить создавать одинаковые записи в справочнике
create unique index I_PFP_INV_PERIODS_PERIOD on PFP_INVESTMENT_PERIODS (
   PERIOD ASC
)
go

create unique index I_PFP_PENSION_FUND_NAME on PFP_PENSION_FUND (
   NAME ASC
)
go

-- Номер ревизии: 53014
-- Номер версии: 1.18
-- Комментарий: Расширение состава данных, передаваемых в запросах, в комплексном типе данных "Информация о карте"(модель БД)

alter table STORED_CARD add (CARD_BONUS_SIGN varchar2(5));
alter table STORED_CARD add (CARD_LEVEL varchar2(5));


-- Номер ревизии: 53055
-- Номер версии: 1.18
-- Комментарий: Доработка репликации подразделений(Создание джоба для выполнения фоновых задач)
DELETE FROM QRTZ_SIMPLE_TRIGGERS	WHERE TRIGGER_NAME = 'PerformBackgroundTasksTrigger' AND TRIGGER_GROUP = 'DEFAULT'
go
DELETE FROM QRTZ_TRIGGERS 		WHERE TRIGGER_NAME = 'PerformBackgroundTasksTrigger' AND TRIGGER_GROUP = 'DEFAULT'
go
DELETE FROM QRTZ_JOB_DETAILS 		WHERE JOB_NAME = 'PerformBackgroundTasksJob' AND JOB_GROUP = 'DEFAULT'
go

-- Номер ревизии: 53053
-- Номер версии: 11.2
-- Комментарий: BUG060006: Реализовать проверку на уникальность координат продукта. 
create unique index I_PFP_PRODUCTS_POINT_P on PFP_PRODUCTS (
   decode(AXIS_X,null,null,TYPE) ASC,
   AXIS_X ASC,
   AXIS_Y ASC
)
go


-- Номер ревизии: 530712
-- Номер версии: 11.2
-- Комментарий: CHG059712: Сохранять состояние чекбоксов и шкал Вклад, Бонусная программа
alter table PERSONAL_FINANCE_PROFILE add USE_ACCOUNT CHAR(1) DEFAULT '1' NOT NULL
/
alter table PERSONAL_FINANCE_PROFILE add ACCOUNT_VALUE NUMBER NULL
/
alter table PERSONAL_FINANCE_PROFILE add USE_THANKS CHAR(1) DEFAULT '1' NOT NULL
/
alter table PERSONAL_FINANCE_PROFILE add THANKS_VALUE NUMBER NULL
/

-- Номер ревизии: 53201
-- Номер версии: 1.18
-- Комментарий: merge 53199: ПФП. Добавление пенсионных продуктов в портфель клиента.
update PFP_BASE_PRODUCT set PRODUCT_TYPE = 'ACCOUNT' where PRODUCT_TYPE = 'account'
/
update PFP_BASE_PRODUCT set PRODUCT_TYPE = 'FUND' where PRODUCT_TYPE = 'fund'
/
update PFP_BASE_PRODUCT set PRODUCT_TYPE = 'INSURANCE' where PRODUCT_TYPE = 'insurance'
/
update PFP_BASE_PRODUCT set PRODUCT_TYPE = 'TRUST_MANAGING' where PRODUCT_TYPE = 'trustManaging'
/

-- Номер ревизии: 53224
-- Номер версии: 1.18
-- Комментарий: merge 53214: CHG059555: Доработать настройку блоков Параметры отображения в таблице/на графике 
create table PFP_CP_TABLE_VIEW_PARAMETERS(
    PRODUCT_ID INTEGER not null,
    TABLE_COLUMN_ID INTEGER not NULL,
    VALUE varchar2(100) not null,
    constraint PK_PFP_CP_TBL_VW_PARAMETERS primary key (PRODUCT_ID, TABLE_COLUMN_ID)
)
go

create index "DXFK_PFP_CP_T_FK_TABLE_PFP_TAB" on PFP_CP_TABLE_VIEW_PARAMETERS
(
   TABLE_COLUMN_ID
)
go

alter table PFP_CP_TABLE_VIEW_PARAMETERS
   add constraint FK_PFP_CP_T_FK_TABLE_PFP_TABL foreign key (TABLE_COLUMN_ID)
      references PFP_TABLE_COLUMNS (ID)
go

alter table PFP_COMPLEX_PRODUCTS add USE_ICON char(1) default '0' not null 
go

create sequence S_PFP_CP_TABLE_VIEW_PARAMETERS
go


-- Номер ревизии: 53222
-- Номер версии: 1.18
-- Комментарий: Доработка справочника вкладов.

ALTER TABLE DEPOSITDESCRIPTIONS ADD (CAPITALIZATION CHAR(1 BYTE) DEFAULT 0 NOT NULL);
ALTER TABLE DEPOSITDESCRIPTIONS ADD (MINIMUM_BALANCE CHAR(1 BYTE) DEFAULT 0 NOT NULL);

-- Номер ревизии: 53232
-- Номер версии: 1.18
-- Комментарий: Актуализация модели БД
ALTER TABLE "SMS_RESOURCES"
	ADD ( "PRIORITY" INTEGER NULL ) 
GO

-- Номер ревизии: 53338
-- Номер версии: 1.18
-- Комментарий: BUG060485: Отображение списка целей на форме финансового прогноза

alter table PERSON_TARGET add VERY_LAST char(1) default '0' not null 
go

update PERSON_TARGET set VERY_LAST=1 where DICTIONARY_TARGET_ID in (select ID from PFP_TARGETS where LATER_ALL=1 and ONLY_ONE=1)
go

-- Номер ревизии: 53385
-- Номер версии: 1.18
-- Комментарий: mAPI 7.0. "Избранное" в мобильном приложении
ALTER TABLE PROFILE ADD (STASH VARCHAR2(500));

-- Номер ревизии: 53459
-- Номер версии: 1.18
-- Комментарий: Доработка АРМ сотрудника при работе со справочником вкладов: просмотр и редактирование информации по вкладу

ALTER TABLE DEPOSITGLOBALS ADD (VISIBILITY_TRANSFORMATION CLOB);


-- Номер ревизии: 53504
-- Номер версии: 1.18
-- Комментарий: CHG060309: В случае смены сегмента необходимо давать клиенту завершить прерванное планирование со старым сегментом 

create table PFP_AVAILABLE_PRODUCTS  (
   PROFILE_ID INTEGER      not null,
   PRODUCT    VARCHAR2(24) not null,
   constraint PK_PFP_AVAILABLE_PRODUCTS primary key (PROFILE_ID, PRODUCT)
)
go

create sequence S_PFP_AVAILABLE_PRODUCTS
go

create index "DXFK_PFP_P_AVAILABLE_TO_PROFIL" on PFP_AVAILABLE_PRODUCTS
(
   PROFILE_ID
)
go

alter table PFP_AVAILABLE_PRODUCTS
   add constraint FK_PFP_P_AVAILABLE_TO_PROFILE foreign key (PROFILE_ID)
      references PERSONAL_FINANCE_PROFILE (ID)
      on delete cascade
go

insert into PFP_AVAILABLE_PRODUCTS (PROFILE_ID, PRODUCT) select id, 'INSURANCE' from PERSONAL_FINANCE_PROFILE
go
insert into PFP_AVAILABLE_PRODUCTS (PROFILE_ID, PRODUCT) select id, 'COMPLEX_INSURANCE' from PERSONAL_FINANCE_PROFILE
go
insert into PFP_AVAILABLE_PRODUCTS (PROFILE_ID, PRODUCT) select id, 'COMPLEX_INVESTMENT' from PERSONAL_FINANCE_PROFILE
go
insert into PFP_AVAILABLE_PRODUCTS (PROFILE_ID, PRODUCT) select id, 'ACCOUNT' from PERSONAL_FINANCE_PROFILE
go
insert into PFP_AVAILABLE_PRODUCTS (PROFILE_ID, PRODUCT) select id, 'FUND' from PERSONAL_FINANCE_PROFILE
go
insert into PFP_AVAILABLE_PRODUCTS (PROFILE_ID, PRODUCT) select id, 'IMA' from PERSONAL_FINANCE_PROFILE
go


-- Номер ревизии: 53649
-- Номер версии: 1.18
-- Комментарий: ENH060775: lob поля в таблице SERVICE_PROVIDERS.

/*==============================================================*/
/* Table: SERVICE_PROVIDER_INFO                                 */
/*==============================================================*/
create table SERVICE_PROVIDER_INFO 
(
   PROVIDER_ID          INTEGER              not null,
   PAYMENT_FORM_INFO_WEB CLOB,
   PAYMENT_FORM_INFO_MAPI CLOB,
   constraint PK_SERVICE_PROVIDER_INFO primary key (PROVIDER_ID)
)
go

alter table SERVICE_PROVIDER_INFO
   add constraint FK_PROVIDER_INFO_TO_PROVIDER foreign key (PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
go

insert into SERVICE_PROVIDER_INFO(PROVIDER_ID, PAYMENT_FORM_INFO_WEB, PAYMENT_FORM_INFO_MAPI) 
    select provider.ID, provider.PAYMENT_FORM_INFO_WEB , provider.PAYMENT_FORM_INFO_MAPI 
    from SERVICE_PROVIDERS provider where provider.PAYMENT_FORM_INFO_WEB is not null or provider.PAYMENT_FORM_INFO_MAPI is not null
go

alter table SERVICE_PROVIDERS DROP (PAYMENT_FORM_INFO_WEB, PAYMENT_FORM_INFO_MAPI)
go

-- Номер ревизии: 53668
-- Номер версии: 1.18
-- Комментарий: BUG060877: ПФП: Справочник каналов Не создается канал с именем удаленного канала 

drop index I_PFP_CHANNELS_NAME
go
create unique index I_PFP_CHANNELS_NAME on PFP_CHANNELS
(
    decode(DELETED, '1', null, NAME)
)
go

-- Номер ревизии: 53694
-- Номер версии: 1.18
-- Комментарий: Сберегательные сертификаты(ч.1)

create table SECURITY_ACCOUNT_LINKS
(
   ID                   INTEGER              not null,
   EXTERNAL_ID          VARCHAR2(100)        not null,
   LOGIN_ID             INTEGER              not null,
   SERIAL_NUMBER        VARCHAR2(64)         not null,
   SECURITY_NAME        VARCHAR2(256)        not null,
   SHOW_IN_SYSTEM       CHAR(1)              not null,
   constraint PK_SECURITY_ACCOUNT_LINKS primary key (ID)
)

go

create sequence S_SECURITY_ACCOUNT_LINKS
go

create unique index UNIQ_SEC_NUMBER on SECURITY_ACCOUNT_LINKS (
   LOGIN_ID ASC,
   SERIAL_NUMBER ASC
)
go


-- Номер ревизии: 53735
-- Номер версии: 1.18
-- Комментарий: Добавление приоритета в таблицу MESSAGE_TEMPLATES

ALTER TABLE "MESSAGE_TEMPLATES"
	ADD ( "PRIORITY" INTEGER NULL ) 
GO

-- Номер ревизии: 53745
-- Номер версии: 1.18
-- Комментарий: BUG061005: В отчете по ПФП пропадает информация по продуктам после удаления их из справочника.
ALTER TABLE PFP_PRODUCT_FIELDS MODIFY VALUE VARCHAR2(512)
go

-- Номер ревизии: 53768 
-- Номер версии: 1.18
-- Комментарий: CHG060329: Чек-бокс «Универсальный продукт» не добавлен отображаться на форме создания комплексных страховых продуктов.
DROP INDEX I_PFP_PRODUCTS_UNIVERSAL_CP
go

ALTER TABLE PFP_COMPLEX_PRODUCTS DROP COLUMN UNIVERSAL
go

-- Номер ревизии: 53821 
-- Номер версии: 1.18
-- Комментарий: АС ФСБ: доработка согласно спецификации 1.07 (ч.1)
alter table CARD_LINKS add (SHOW_IN_SMS  char(1) default 1 not null)
go
alter table ACCOUNT_LINKS add (SHOW_IN_SMS  char(1) default 1 not null)
go
alter table LOAN_LINKS add (SHOW_IN_SMS  char(1) default 1 not null)
go

update CARD_LINKS set SHOW_IN_SMS = SHOW_IN_SYSTEM
go
update ACCOUNT_LINKS set SHOW_IN_SMS = SHOW_IN_SYSTEM
go
update LOAN_LINKS set SHOW_IN_SMS = SHOW_IN_SYSTEM
go


-- Номер ревизии: 53943 
-- Номер версии: 1.18
-- Комментарий: Доработка репликации подразделений(Реализация алгоритма репликации)

create table DEPARTMENTS_TASKS_CONTENT(
    REPLICA_TASKS_ID INTEGER not null,
    CONTENT              BLOB,
    constraint PK_DEPARTMENTS_TASKS_CONTENT primary key (REPLICA_TASKS_ID)
)
go

alter table DEPARTMENTS_TASKS_CONTENT
   add constraint FK_CONTENT_TO_DEP_TASKS foreign key (REPLICA_TASKS_ID)
      references DEPARTMENTS_REPLICA_TASKS (ID)
go

alter table DEPARTMENTS_REPLICA_TASKS drop column CONTENT
go
alter table DEPARTMENTS_REPLICA_TASKS drop column DEST_DELETED
go
alter table DEPARTMENTS_REPLICA_TASKS add TOTAL_RECORDS NUMBER(22,0)
go
alter table DEPARTMENTS_REPLICA_TASKS add DEST_INSERED_REPORT CLOB 
go
alter table DEPARTMENTS_REPLICA_TASKS add DEST_INSERED_DECENTR_REPORT CLOB 
go
alter table DEPARTMENTS_REPLICA_TASKS add DEST_UPDATED_REPORT CLOB
go
alter table DEPARTMENTS_REPLICA_TASKS add ERROR_FORMAT_REPORT CLOB
go
alter table DEPARTMENTS_REPLICA_TASKS add ERROR_PARENT_REPORT CLOB
go

-- Номер ревизии: 54044 
-- Номер версии: 1.18
-- Комментарий: Печать реквизитов банка для вкладов (модель БД)
ALTER TABLE RUSBANKS ADD
(
   PARTICIPANT_CODE VARCHAR2(8),
   INN VARCHAR2(12),
   KPP VARCHAR2(12)
);

CREATE TABLE TB_DETAILS 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(4)          not null,
   NAME                 VARCHAR2(256)        not null,
   BIC                  VARCHAR2(16),
   OKPO                 VARCHAR2(16),
   ADDRESS              VARCHAR2(256),
   constraint PK_TB_DETAILS primary key (ID)
);

create sequence S_TB_DETAILS;


-- Номер ревизии: 54092
-- Номер версии: 11.2
-- Комментарий: BUG061459: Неверные значения в графике использования кредитной карты с программой лояльности. 

update PFP_CARDS set INPUTS = null, BONUS = 0.3 where PROGRAMM_TYPE = 'beneficent'
go

-- Номер ревизии: 54107
-- Номер версии: 1.18
-- Комментарий: CHG060999: На странице с выбором кредитной карты необходимо при первом входе указать карту "подари жизнь"  

alter table PFP_CARDS add DEFAULT_CARD char(1) default '0'
go

create unique index I_PFP_CARDS_DEFAULT_CARD on PFP_CARDS(
    decode(DEFAULT_CARD, '0', null, DEFAULT_CARD)
) 
Go


-- Номер ревизии: 54155
-- Номер версии: 1.18
-- Комментарий: ENH061480: Доработка страницы настройки платформы приложения.

alter table MOBILE_PLATFORMS add SHOW_IN_M_APPS CHAR(1) default 1 not null;

-- Номер ревизии: 54209
-- Номер версии: 1.18
-- Комментарий: Сберегательные сертификаты(тех. перерывы)
create table STORED_SECURITY_ACCOUNT
(
   ID                   INTEGER              not null,
   RESOURCE_ID          INTEGER              not null,
   BANK_ID              VARCHAR2(32),
   BANK_NAME            VARCHAR2(300),
   BANK_ADDR            VARCHAR2(500),
   ISS_BANK_ID          VARCHAR2(32),
   ISS_BANK_NAME        VARCHAR2(300),
   COMPOSE_DATE         TIMESTAMP,
   DOC_NUMBER           VARCHAR2(100),
   DOC_DATE             TIMESTAMP,
   INCOME_RATE          NUMBER(15,4),
   START_DATE           TIMESTAMP,
   ENTITY_UPDATE_TIME   TIMESTAMP,
   NOMINAL_AMOUNT       NUMBER(15,4),
   NOMINAL_CURRENCY     CHAR(3),
   INCOME_AMOUNT        NUMBER(15,4),
   INCOME_CURRENCY      CHAR(3),
   constraint PK_STORED_SECURITY_ACCOUNT primary key (ID)
)

go

create sequence S_STORED_SECURITY_ACCOUNT
go

create index "DXSTORED_S_A_TO_LINK" on STORED_SECURITY_ACCOUNT
(
   RESOURCE_ID
)
go

alter table STORED_SECURITY_ACCOUNT
   add constraint FK_STORED_S_SECURITY foreign key (RESOURCE_ID)
      references SECURITY_ACCOUNT_LINKS (ID)
      on delete cascade
go

alter table SECURITY_ACCOUNT_LINKS add ON_STORAGE_IN_BANK CHAR(1) default 1 not null
go

-- Номер ревизии: 54209
-- Номер версии: 1.18
-- Комментарий: CHG061747: Упорядочить цели в хронологическом порядке
ALTER TABLE PERSON_TARGET DROP COLUMN LIST_INDEX
go

-- Номер ревизии: 54279
-- Номер версии: 1.18
-- Комментарий: ИОК от 13.09.2013
alter table ERMB_REGISTRATION add CONNECTION_DATE TIMESTAMP
;

update ERMB_REGISTRATION
set CONNECTION_DATE =
    (select profile.CONNECTION_DATE from ERMB_PROFILE profile
     where ERMB_REGISTRATION.PROFILE_ID = profile.PERSON_ID
    )
;

alter table ERMB_REGISTRATION
modify CONNECTION_DATE not null
;

drop index DXFK_PROFILE_TO_REGISTRATION
;

alter table ERMB_REGISTRATION drop constraint FK_ERMB_REG_FK_PROFIL_ERMB_PRO
;

drop index UI_ERMB_REGISTRATION
;

create index IDX_ERMB_REGISTRATION on ERMB_REGISTRATION
(
   PHONE_NUMBER ASC
)
;


-- Номер ревизии: 54287
-- Номер версии: 1.18
-- Комментарий: CHG058649: Необходимо внести изменения на форму «Создание кредитной карты» 

ALTER TABLE PFP_CARDS DROP COLUMN DIAGRAM_DESCRIPTION
go

-- Номер ревизии: 54363
-- Номер версии: 1.18
-- Комментарий: CSAAdmin. Работа со схемами прав

ALTER TABLE ACCESSSCHEMES ADD EXTERNAL_ID INTEGER
go

-- Номер ревизии: 54365
-- Номер версии: 1.18
-- Комментарий: Форма редактирования и списка площадок КЦ

create table CONTACT_CENTER_AREAS(
    ID INTEGER not null,
    AREA_NAME varchar2(50) not null,
    constraint PK_CONTACT_CENTER_AREAS primary key (ID)   
)
go

create table C_CENTER_AREAS_DEPARTMENTS(
    C_C_AREA_ID INTEGER not null,
    DEPARTMENT_ID INTEGER not null,
    constraint PK_C_CENTER_AREAS_DEPARTMENTS primary key (C_C_AREA_ID, DEPARTMENT_ID),
    
    constraint FK_C_C_AREA_DEP_TO_DEP foreign key (DEPARTMENT_ID)
        references DEPARTMENTS (ID) on delete cascade,
    constraint FK_C_C_AREA_DEP_TO_C_C_AREA foreign key (C_C_AREA_ID)
        references CONTACT_CENTER_AREAS (ID) on delete cascade
)
go

create unique index I_C_CENTER_AREAS_DEPARTMENTS on C_CENTER_AREAS_DEPARTMENTS (
   DEPARTMENT_ID
)
go

create unique index I_CONTACT_CENTER_AREAS_NAME on CONTACT_CENTER_AREAS (
   AREA_NAME
)
go

create sequence S_CONTACT_CENTER_AREAS
go

create index "DXFK_C_C_AREA_DEP_TO_C_C_AREA" on C_CENTER_AREAS_DEPARTMENTS
(
   C_C_AREA_ID
)
go

-- Номер ревизии: 54356
-- Номер версии: 1.18
-- Комментарий: BUG061025: Доработать отображение таблицы Мои кредиты

alter table PFP_PERSON_LOAN add IMAGE_ID integer null
/

-- Номер ревизии: 54378
-- Номер версии: 1.18
-- Комментарий: Доработка интерфейсов по продуктам (сортировка). Доработка сущности линков.

ALTER TABLE ACCOUNT_LINKS ADD (POSITION_NUMBER INTEGER);
ALTER TABLE CARD_LINKS ADD (POSITION_NUMBER INTEGER);
ALTER TABLE DEPO_ACCOUNT_LINKS ADD (POSITION_NUMBER INTEGER);
ALTER TABLE IMACCOUNT_LINKS ADD (POSITION_NUMBER INTEGER);
ALTER TABLE LOAN_LINKS ADD (POSITION_NUMBER INTEGER);

-- Номер ревизии: 54421
-- Номер версии: 1.18
-- Комментарий: ENH062018: Настройка отображения окна саморегистрации

alter table PROFILE add REG_WINDOW_SHOW_COUNT INTEGER;

-- Номер ревизии: 54471
-- Номер версии: 1.18
-- Комментарий: ENH060229: Привести страницы со списками шаблонов и автоплатежей к макетам

create table AUTOPAYMENT_LINKS_ORDER 
(
   ID                   INTEGER        not null,
   LINK_ID              VARCHAR2(130)  not null,
   LOGIN_ID             INTEGER        not null,
   ORDER_IND            INTEGER        not null,
   constraint PK_AUTOPAYMENT_ORDER primary key (ID)
)

go

alter table AUTOPAYMENT_LINKS_ORDER
   add constraint FK_AUTOPAYMENT_ORDER_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)

go

create sequence S_AUTOPAYMENT_LINKS_ORDER

go


create index IND_AUTOPAYMENT_ORDER_LOGIN_ID on AUTOPAYMENT_LINKS_ORDER (
   LOGIN_ID
)

-- Номер ревизии: 54486
-- Номер версии: 1.18
-- Комментарий: BUG062286: Значения ВСП и ОСБ в запросе на открытие вклада в ВСП КМ

ALTER TABLE USERS ADD
(
    MANAGER_TB VARCHAR2(8),
    MANAGER_OSB VARCHAR2(8),
    MANAGER_VSP VARCHAR2(8)
)

-- Номер ревизии: 54515
-- Номер версии: 1.18
-- Комментарий: Удаление контактных данных из класса Person,
-- замена их использования на использования аналогичных полей из PersonSubscriptionData,
-- добавление в PersonSubscriptionData поля mailFormat.


ALTER TABLE USERS
	DROP ( "E_MAIL", "MOBILE_PHONE", "SMS_FORMAT" ) CASCADE CONSTRAINT 
GO

ALTER TABLE PERSONAL_SUBSCRIPTION_DATA
	ADD ( "MAIL_FORMAT" VARCHAR2(16) NULL ) 
GO


-- Номер ревизии: 54522
-- Номер версии: 1.18
-- Комментарий: доработка отправки уведомлений

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

-- Номер ревизии: 54525
-- Номер версии: 1.18
-- Комментарий: ENH061998: Переделать логику получения бонусных баллов
alter table LOYALTY_PROGRAM_LINKS  add BALANCE NUMBER(19,2)
go
alter table LOYALTY_PROGRAM_LINKS  add UPDATE_TIME TIMESTAMP
go


-- Номер ревизии: 54547
-- Номер версии: 1.18
-- Комментарий: Переназначение обращений на других сотрудников

create table REASSIGN_MAIL_HISTORY(
    ID INTEGER not null,
    MAIL_ID integer,
    REASSIGN_DATE TIMESTAMP,
    EMPLOYEE_FIO varchar2(92),
    REASSIGN_REASON varchar2(500),
    constraint PK_REASSIGN_MAIL_HISTORY primary key (ID)
)
go

create index "DXFK_R_MAIL_HISTORY_TO_MAIL" on REASSIGN_MAIL_HISTORY
(
   MAIL_ID
)
go

alter table REASSIGN_MAIL_HISTORY
   add constraint FK_R_MAIL_HISTORY_TO_MAIL foreign key (MAIL_ID)
      references MAIL (ID) on delete cascade
go

create sequence S_REASSIGN_MAIL_HISTORY
go
-- Номер ревизии: 54561
-- Номер версии: 1.18
-- Комментарий: Создание таблицы для хранения email-шаблонов
create table EMAIL_RESOURCES
(
   ID                   INTEGER              not null,
   KEY                   VARCHAR2(255)       not null,
   THEME                 VARCHAR2(255),
   DESCRIPTION           VARCHAR2(255),
   VARIABLES             VARCHAR2(4000),
   PLAIN_TEXT           CLOB,
   HTML_TEXT            CLOB,
   PREVIOUS_PLAIN_TEXT  CLOB,
   PREVIOUS_HTML_TEXT   CLOB,
   LAST_MODIFIED        TIMESTAMP,
   EMPLOYEE_LOGIN_ID    INTEGER,
   constraint PK_EMAIL_RESOURCES primary key (ID)
)
go

create sequence S_EMAIL_RESOURCES
go

create index INDEX_EMAIL_KEY on EMAIL_RESOURCES (
   KEY ASC
)
go

-- Номер ревизии: 54584
-- Номер версии: 1.18
-- Комментарий: Реализовать справочник категорий операций в АРМ сотрудника
alter table CARD_OPERATION_CATEGORIES add VISIBLE char(1) 
/


-- Номер ревизии: 54638
-- Номер версии: 1.18
-- Комментарий: Реализовать алгоритм выделения операций в категорию «Переводы между своими картами»

alter table CARD_OPERATION_CATEGORIES add FOR_INTERNAL_OPERATIONS CHAR(1) default '0' not null
go

-- Номер ревизии: 54670
-- Номер версии: 1.18
-- Комментарий: Реализовать механизм периодической очистки старых операций в АЛФ

create index IDX_OPERATION_DATE on CARD_OPERATIONS (
   OPERATION_DATE ASC
)
go
-- Номер ревизии: 54694
-- Номер версии: 12.0
-- Комментарий: BUG061100 	Лимиты при подтверждении операции в другом канале
ALTER TABLE BUSINESS_DOCUMENTS ADD (CLIENT_OPERATION_TYPE CHAR(1))
GO

-- Номер ревизии: 54715
-- Номер версии: 1.18
-- Комментарий: АЛФ. Админ. Создание и редактирование категорий карточных операций

alter table CARD_OPERATION_CATEGORIES drop column VISIBLE
/
alter table CARD_OPERATION_CATEGORIES add VISIBLE char(1) DEFAULT '1' NOT NULL
/
alter table CARD_OPERATION_CATEGORIES add EXTERNALIDT VARCHAR2(100) 
/
alter table MERCHANT_CATEGORY_CODES add INCOME_OPERATION_CATEGORY_IDT VARCHAR2(100) 
/
alter table MERCHANT_CATEGORY_CODES add OUTCOME_OPERATION_CATEGORY_IDT VARCHAR2(100) 
/
update CARD_OPERATION_CATEGORIES set EXTERNALIDT = EXTERNALID 
/
update MERCHANT_CATEGORY_CODES set INCOME_OPERATION_CATEGORY_IDT = INCOME_OPERATION_CATEGORY_ID 
/
update MERCHANT_CATEGORY_CODES set OUTCOME_OPERATION_CATEGORY_IDT = OUTCOME_OPERATION_CATEGORY_ID 
/
alter table MERCHANT_CATEGORY_CODES drop column INCOME_OPERATION_CATEGORY_ID
/
alter table MERCHANT_CATEGORY_CODES drop column OUTCOME_OPERATION_CATEGORY_ID
/
alter table CARD_OPERATION_CATEGORIES drop column EXTERNALID
/
alter table MERCHANT_CATEGORY_CODES rename column INCOME_OPERATION_CATEGORY_IDT to INCOME_OPERATION_CATEGORY_ID
/
alter table MERCHANT_CATEGORY_CODES rename column OUTCOME_OPERATION_CATEGORY_IDT to OUTCOME_OPERATION_CATEGORY_ID
/
alter table CARD_OPERATION_CATEGORIES rename column EXTERNALIDT to EXTERNALID 
/ 

-- Номер ревизии: 54736
-- Номер версии: 1.18
-- Комментарий: ENH060014: Реализовать страницу со списком "избранного"

alter table FAVOURITE_LINKS add TYPE_LINK CHAR(1) default 'U' not null

-- Номер ревизии: 54747
-- Номер версии: 1.18
-- Комментарий: Изменение джоба уточнения статуса документов.
alter table PAYMENT_EXECUTION_RECORDS add (NEXT_PROCESS_DATE TIMESTAMP)

-- Номер ревизии: 54788
-- Номер версии: 1.18
-- Комментарий: Реализовать ограничение на количество попыток выполнить одну заявку.

alter table CARD_OPERATION_CLAIMS add EXECUTION_ATTEMPT_NUM NUMBER default '0' not null
go

-- Номер ревизии: 54809
-- Номер версии: 1.18
-- Комментарий: Реализовать создание адаптера для ИПС

alter table ADAPTERS add ADAPTER_TYPE varchar2(8)
go
update ADAPTERS set ADAPTER_TYPE='NONE'
go
update ADAPTERS set ADAPTER_TYPE='ESB' where IS_ESB ='1'
go
alter table ADAPTERS modify ADAPTER_TYPE not null
go
alter table ADAPTERS drop column IS_ESB
go 


-- Номер ревизии: 54814
-- Номер версии: 1.18 (база с логами)
-- Комментарий: Реализовать сохранение логов при изменении клиентом категории

create table CHANGE_CARD_OP_CATEGORY_LOG 
(
   ID  			INTEGER              not null,
   CHANGE_DATE          TIMESTAMP          not null,
   TB_NAME              	VARCHAR2(4)       not null,
   VSP                  	VARCHAR2(7),
   FIO                 	VARCHAR2(100)    not null,
   OPERATION_NAME     VARCHAR2(100),
   MCC_CODE             	INTEGER,
   AMOUNT               	NUMBER(15,4)      not null,
   PARENT_CATEGORY   VARCHAR2(100)   not null,
   NEW_CATEGORIES     VARCHAR2(100)   not null,
   NEW_CATEGORIES_COUNT INTEGER     not null,
   constraint PK_CHANGE_CARD_OP_CATEGORY_LOG primary key (ID)
)
go

create sequence S_CHANGE_CARD_OP_CATEGORY_LOG 
go

-- Номер ревизии: 54856
-- Номер версии: 1.18
-- Комментарий: Доработка настройки оповещений клиентом

ALTER TABLE PROFILE
	ADD ( BANK_NEWS_NOTIFICATION_TYPE VARCHAR2(16) NULL ) 
GO

-- Номер ревизии: 54873
-- Номер версии: 1.18
-- Комментарий: Сбор статистики по использованию АЛФ.Отчет «Подключение АЛФ».

alter table PROFILE 
add START_USING_PERSONAL_FINANCE DATE
/
update PROFILE 
set 
    START_USING_PERSONAL_FINANCE = trunc (SYSDATE)
where 
    SHOW_PERSONAL_FINANCE = '1'
/
create or replace view DATES_ADD_PERSONAL_FINANCE as 
select 
    START_USING_PERSONAL_FINANCE, TB, count(*) cnt 
from PROFILE profile 
    join USERS users on profile.LOGIN_ID = users.LOGIN_ID 
    join DEPARTMENTS departments on departments.ID = users.DEPARTMENT_ID
where SHOW_PERSONAL_FINANCE = '1'
group by START_USING_PERSONAL_FINANCE, TB
/

-- Номер ревизии: 54886
-- Номер версии: 1.18
-- Комментарий: Справочник телефонов "инкогнито".

create table INCOGNITO_PHONES 
(
   ID                   INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(20 BYTE)    not null,
   constraint PK_INCOGNITO_PHONES primary key (ID)
)
go

create sequence S_INCOGNITO_PHONES
go

-- Номер ревизии: 54894
-- Номер версии: 1.18
-- Комментарий: Доработка механизма рассылки оповещений
create table DISTRIBUTION_CHANNEL
(
   ID                   INTEGER              not null,
   DISTRIBUTION_ID      INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   CHANNEL              VARCHAR2(20),
   constraint PK_DISTRIBUTION_CHANNEL primary key (ID)
)

go

create sequence S_DISTRIBUTION_CHANNEL
go

create unique index CHANNEL_UK on DISTRIBUTION_CHANNEL (
   DISTRIBUTION_ID ASC,
   CHANNEL ASC
)
go
create index "DXCHANNEL_TO_DISTRIBUTION" on DISTRIBUTION_CHANNEL
(
   DISTRIBUTION_ID
)
go

insert into DISTRIBUTION_CHANNEL (select id, DISTRIBUTION_ID, CHANNEL,LIST_INDEX from MESSAGE_TEMPLATES)
go

alter table MESSAGE_TEMPLATES drop constraint TEMPLATE_TO_DISTRIBUTION
go

alter table SUBSCRIPTION_TEMPLATES drop constraint FK_SUBTEM_TO_TEMPLATE
go
drop  sequence S_MESSAGE_TEMPLATES
go
drop  index CHANNEL_UK
go
drop table  MESSAGE_TEMPLATES
go

alter table DISTRIBUTION_CHANNEL
   add constraint TEMPLATE_TO_DISTRIBUTION foreign key (DISTRIBUTION_ID)
      references DISTRIBUTIONS (ID)
go

alter table SUBSCRIPTION_TEMPLATES
   add constraint FK_SUBTEM_TO_TEMPLATE foreign key (TEMPLATE_ID)
      references DISTRIBUTION_CHANNEL (ID)
go


-- Номер ревизии: 54904
-- Номер версии: 1.18
-- Комментарий: Добавление таблиц для рассылки оповещений о новостях
create table NEWS_DISTRIBUTIONS
(
   ID                   INTEGER              not null,
   EMPLOYEE_LOGIN_ID    INTEGER              not null,
   DATE_CREATED         TIMESTAMP            not null,
   TYPE                 VARCHAR2(32)         not null,
   NEWS_ID              INTEGER              not null,
   MAIL_COUNT           INTEGER              not null,
   TIMEOUT              INTEGER              not null,
   STATE                VARCHAR2(16)         not null,
   TITLE                VARCHAR2(100)        not null,
   TEXT                 CLOB                 not null,
   constraint PK_NEWS_DISTRIBUTIONS primary key (ID)
)

go

create sequence S_NEWS_DISTRIBUTIONS
go

create table NEWS_DISTR_DEPARTMENTS
(
   ID                   INTEGER              not null,
   NEWS_DISTRIBUTIONS_ID INTEGER              not null,
   DEPARTMENT_ID        INTEGER,
   LAST_PROCESSED_USER_ID INTEGER,
   LAST_DISTRIBUTION_DATE TIMESTAMP,
   constraint PK_NEWS_DISTR_DEPARTMENTS primary key (ID)
)

go

create sequence S_NEWS_DISTR_DEPARTMENTS
go

create index "DXFK_NEWS_DISTR_TO_DEPARTMENTS" on NEWS_DISTR_DEPARTMENTS
(
   NEWS_DISTRIBUTIONS_ID
)
go

alter table NEWS_DISTR_DEPARTMENTS
   add constraint FK_NEWS_DISTR_TO_DEPARTMENTS foreign key (NEWS_DISTRIBUTIONS_ID)
      references NEWS_DISTRIBUTIONS (ID)
      on delete cascade
go


-- Номер ревизии: 54912
-- Номер версии: 1.18 (база с логами)
-- Комментарий: Реализовать сохранение логов при изменении клиентом категории (доработки)

ALTER table CHANGE_CARD_OP_CATEGORY_LOG rename column TB_NAME to TB
go

alter table CHANGE_CARD_OP_CATEGORY_LOG modify NEW_CATEGORIES VARCHAR2(100) null
go

-- Номер ревизии: 54914
-- Номер версии: 1.18 (база с логами)
-- Комментарий: Логирование отправленных клиентам push-сообщений

create table SENT_PUSH_MESSAGES_LOG 
(
   ID                   number(20)           not null,
   CREATION_DATE        DATE                 not null,
   EVENT_ID             VARCHAR2(64)         not null,
   LOGIN_ID             number(20)           not null,
   TYPE_CODE            VARCHAR2(32)         not null,
   constraint PK_SENT_PUSH_MESSAGES_LOG primary key (ID)
)
go

create sequence S_SENT_PUSH_MESSAGES_LOG 
go

create table PUSH_DEVICES_STATES_LOG 
(
   ID                   number(20)           not null,
   CREATION_DATE        DATE                 not null,
   CLIENT_ID            varchar2(64)         not null,
   MGUID                varchar2(64)         not null,
   TYPE                 varchar2(1)          not null,
   constraint PK_PUSH_DEVICES_STATES_LOG primary key (ID)
)
go

create sequence S_PUSH_DEVICES_STATES_LOG 
go

-- Номер ревизии: 54701
-- Номер версии: 1.18
-- Комментарий: Новые поля в справочнике СПООБК

ALTER TABLE DEPARTMENTS_RECORDING_TMP
ADD (DATE_SUC  TIMESTAMP);

ALTER TABLE DEPARTMENTS_RECORDING_TMP
ADD (DESPATCH  VARCHAR2(11));

ALTER TABLE DEPARTMENTS_RECORDING
ADD (DATE_SUC  TIMESTAMP);

ALTER TABLE DEPARTMENTS_RECORDING
ADD (DESPATCH  VARCHAR2(11));

create table OFFICES_NOT_ISSUING_CARDS 
(
   ID                   INTEGER              not null,
   TB                   VARCHAR2(3)          not null,
   OSB                  VARCHAR2(4),
   OFFICE               VARCHAR2(7),
   constraint PK_OFFICES_NOT_ISSUING_CARDS primary key (ID)
);

create sequence S_OFFICES_NOT_ISSUING_CARDS;

-- Номер ревизии: 54935
-- Номер версии: 1.18
-- Комментарий: Сбор статистики. Сущности.

create table USER_NOTIFICATION_LOG 
(
   ID                   INTEGER              not null,
   "DATE"               TIMESTAMP            not null,
   TYPE                 VARCHAR2(25)         not null,
   VALUE                VARCHAR2(20),
   constraint PK_USER_NOTIFICATION_LOG primary key (ID)
)

go

create sequence S_USER_NOTIFICATION_LOG
go
create table USER_MESSAGES_LOG 
(
   ID                   INTEGER              not null,
   LOGIN_ID				INTEGER				 not null,
   "DATE"               TIMESTAMP            not null,
   TYPE                 VARCHAR2(25)         not null,
   MESSAGE_ID           INTEGER              not null,
   constraint PK_USER_MESSAGES_LOG primary key (ID)
)

go

create sequence S_USER_MESSAGES_LOG
go

-- Номер ревизии: 55113
-- Номер версии: 1.18
-- Комментарий: Доработать ограничения на перебор логинов (ЗНИ 189437)
alter table USERS add (CHECK_LOGIN_COUNT integer)
go

alter table USERS add (LAST_FAILURE_LOGIN_CHECK date)
go

-- Номер ревизии: 54973
-- Номер версии: 1.18
-- Комментарий: Сбор статистики. Сущности.

ALTER TABLE USER_NOTIFICATION_LOG RENAME COLUMN "DATE" TO "ADDITION_DATE"
GO
ALTER TABLE USER_MESSAGES_LOG RENAME COLUMN "DATE" TO "ADDITION_DATE"
GO

-- Номер ревизии: 54985
-- Номер версии: 1.18
-- Комментарий: Реализация журнала входов в ЦСА

alter table LOGINS add (CSA_USER_ALIAS varchar2(32));

-- Номер ревизии: 55033
-- Номер версии: 1.18
-- Комментарий: CHG058580: Несоответствие данных в Расходах. 

ALTER TABLE CARD_OPERATIONS ADD LOAD_DATE TIMESTAMP DEFAULT SYSDATE NOT NULL 
go

create index "IDX_CARDOP_LOAD_DATE" on CARD_OPERATIONS
(
    LOAD_DATE
)
go

-- Номер ревизии: 55025
-- Номер версии: 1.18
-- Комментарий: Архитектура. Доработки по E-Invoicing

create index IDX_BUS_DOC_ORDERUUID_CR_DATE on BUSINESS_DOCUMENTS (
   CREATION_DATE asc,
   ORDER_UUID asc
) 
LOCAL

-- Номер ревизии: 55045
-- Номер версии: 1.18
-- Комментарий: Добавлен индекс на таблицу с логами (изменение категории у карточной операции) 

create index "IDX_CHANGE_CARD_OP_LOG_DATE" on CHANGE_CARD_OP_CATEGORY_LOG
(
    CHANGE_DATE
)
go

-- Номер ревизии: 55052
-- Номер версии: 1.18
-- Комментарий: CHG062715: Реализовать механизм управления контентом промо-блока Автоплатежей, отображаемого в клиентском приложении ЕРИБ
alter table SERVICE_PROVIDER_REGIONS  add SHOW_IN_PROMO_BLOCK  CHAR(1) default '0' not null
go

-- Номер ревизии: 55084
-- Номер версии: 1.18
-- Комментарий: Отчет «Популярные цели»

ALTER TABLE ACCOUNT_TARGETS ADD CREATION_DATE TIMESTAMP DEFAULT SYSDATE NOT NULL 
go

create index "IDX_ACCOUNT_TARGETS_DATE" on ACCOUNT_TARGETS
(
    CREATION_DATE DESC
)
go

create or replace view DATES_ADD_ACCOUNT_TARGETS as 
select 
    trunc(targets.CREATION_DATE) target_date, departments.TB tb, targets.LOGIN_ID client, targets.NAME target_name 
from ACCOUNT_TARGETS targets 
    join USERS users on targets.LOGIN_ID = users.LOGIN_ID 
    join DEPARTMENTS departments on departments.ID = users.DEPARTMENT_ID
go

-- Номер ревизии: 55092
-- Номер версии: 1.18
-- Комментарий: Отчет «Популярные отчеты по расходам в АЛФ».
create table FILTER_OUTCOME_PERIOD_LOG
(
    ID                   INTEGER            NOT NULL,
    FILTER_DATE          TIMESTAMP          not null,
    TB                   VARCHAR2(4)        not null,
    PERIOD_TYPE          VARCHAR2(30)       not null,
    IS_DEFAULT         	 CHAR(1)        not null,
    primary key(ID)
)
/
create sequence  S_FILTER_OUTCOME_PERIOD_LOG
/
create index FILTER_OUTCOME_PERIOD_INDEX on FILTER_OUTCOME_PERIOD_LOG
(
   FILTER_DATE asc,
   PERIOD_TYPE,
   IS_DEFAULT
)
/

-- Номер ревизии: 55128
-- Номер версии: 1.18
-- Комментарий: Сбор статистики. Сущности
ALTER TABLE USER_MESSAGES_LOG
	ADD ( "LOGIN_ID" INTEGER NOT NULL ) 
GO
ALTER TABLE USER_MESSAGES_LOG MODIFY ( "MESSAGE_ID" NUMBER NULL )
GO

-- Номер ревизии: 55138
-- Номер версии: 1.18
-- Комментарий: Доработка логики подтверждения операций
create unique index I_PUSH_PARAMS_KEY on PUSH_PARAMS (
   KEY ASC
)
go

-- Номер ревизии: 55147
-- Номер версии: 1.18
-- Комментарий: CHG063276: Изменение алгоритма определения БИКа и корр. счета для печати реквизитов счета.
ALTER TABLE TB_DETAILS ADD OFF_CODE VARCHAR2(4);
-- Номер ревизии: 55121
-- Номер версии: 1.18
-- Комментарий: ENH061673 Хранить номер блока в таблицах логирования
alter table CODLOG add (NODE_ID INTEGER)
go

alter table SYSTEMLOG add (NODE_ID INTEGER)
go

alter table USERLOG add (NODE_ID INTEGER)
go

-- Номер ревизии: 55150
-- Номер версии: 1.18
-- Комментарий: ЦСА Админ. Актуализация доступа к подразделениям в блоке, на основе данных из ЦСа Админ.
create global temporary table ALLOWED_DEPARTMENT_TEMPORARY 
(
   TB                   VARCHAR2(4)          not null,
   OSB                  VARCHAR2(4)          not null,
   VSP                  VARCHAR2(7)          not null
)
on commit delete rows
go

-- Номер ревизии: 55205
-- Номер версии: 1.18
-- Комментарий: Маршрутизация обратного потока. БД
ALTER TABLE ADAPTERS ADD (LISTENER_URL VARCHAR2(128))


-- Номер ревизии: 55210
-- Номер версии: 1.18
-- Комментарий: BUG063730: Некорректно выполняется скрипт «Отчет по изменению клиентом категории»

drop index IDX_CHANGE_CARD_OP_LOG_DATE
go

create index IDX_CHANGE_CARD_OP_LOG on CHANGE_CARD_OP_CATEGORY_LOG (
   CHANGE_DATE DESC,
   TB ASC,
   VSP ASC
)
go


-- Номер ревизии: 55233
-- Номер версии: 1.18
-- Комментарий: BUG061367: Ошибка в маппинге внешних ошибок.
DELETE FROM EXCEPTION_COUNTERS 
WHERE EXCEPTION_HASH IN (SELECT HASH 
                         FROM EXCEPTION_ENTRY 
                         WHERE APPLICATION NOT IN ('PhizIA','PhizIC','mobile5','mobile6','mobile7','atm','Gate','Scheduler'))
go

DELETE FROM EXCEPTION_ENTRY 
WHERE APPLICATION NOT IN ('PhizIA','PhizIC','mobile5','mobile6','mobile7','atm','Gate','Scheduler')
go

-- Номер ревизии: 55271
-- Номер версии: 1.18
-- Комментарий: Доработки по E-Invoicing

drop index ORDERS_DATE
go

drop index ORDERS_PERSON
go

create index ORDERS_PERSON_DATE on ORDERS (
   USER_ID ASC,
   ORDER_DATE DESC
)
go

-- Номер ревизии: 55280
-- Номер версии: 1.18
-- Комментарий: BUG062142: Некорректная работа RemoveOldNotConfirmedDocumentsJob

delete FROM QRTZ_CRON_TRIGGERS  WHERE TRIGGER_NAME = 'RemoveOldNotConfirmedDocumentsTrigger'
go
delete  FROM QRTZ_TRIGGERS       WHERE TRIGGER_NAME = 'RemoveOldNotConfirmedDocumentsTrigger'
go
delete  FROM QRTZ_JOB_DETAILS  WHERE JOB_NAME ='RemoveOldNotConfirmedDocumentsJob'
go

-- Номер ревизии: 55284
-- Номер версии: 1.18
-- Комментарий: BUG057896: Ошибка при отказе шуделлером перевода между своими счетами. 
CREATE OR REPLACE PROCEDURE updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in varchar, exceptionSystem in varchar, exceptionErrorCode in varchar) as
   existRows NUMBER;
BEGIN
    SELECT count(ID) into existRows FROM EXCEPTION_ENTRY WHERE EXCEPTION_ENTRY.HASH = exceptionHash;
    
    IF existRows > 0 THEN
        UPDATE EXCEPTION_COUNTERS SET EXCEPTION_COUNT = EXCEPTION_COUNT + 1 WHERE EXCEPTION_HASH = exceptionHash AND EXCEPTION_DATE=trunc(SYSDATE);
    ELSE 
        BEGIN
            INSERT INTO EXCEPTION_ENTRY (ID, KIND, HASH, APPLICATION, OPERATION, DETAIL, MESSAGE, SYSTEM, ERROR_CODE)
                VALUES (S_EXCEPTION_ENTRY.NEXTVAL, exceptionKind, exceptionHash, exceptionApplication, exceptionOperation, exceptionDetail, null, exceptionSystem, exceptionErrorCode);
            INSERT INTO EXCEPTION_COUNTERS (EXCEPTION_HASH, EXCEPTION_DATE, EXCEPTION_COUNT)
                VALUES (exceptionHash, trunc(SYSDATE), 1);
            EXCEPTION WHEN DUP_VAL_ON_INDEX THEN UPDATE EXCEPTION_COUNTERS SET EXCEPTION_COUNT = EXCEPTION_COUNT + 1 WHERE EXCEPTION_HASH = exceptionHash AND EXCEPTION_DATE=trunc(SYSDATE);
        END;
    END IF;
END;
go

-- Номер ревизии: 55299
-- Номер версии: 1.18
-- Комментарий: CHG058213: Сообщение при фильтрации по неключевым данным ДР и статусу операции(бредовый индекс в БД)
drop index BIRTHDAY_INDEX
go

-- Номер ревизии: 55305
-- Номер версии: 1.18
-- Комментарий: CHG058213: Сообщение при фильтрации по неключевым данным ДР и статусу операции(ДР в индекс)
drop index FIO_PERSON_INDEX
go
/*==============================================================*/
/* Index: FIO_BD_PERSON_INDEX                                   */
/*==============================================================*/
create index FIO_BD_PERSON_INDEX on USERS (
   upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME),' ',''),'-','')) ASC,
   BIRTHDAY ASC
)
go
-- Номер ревизии: 55317
-- Номер версии: 1.18
-- Комментарий: Задача "Лимит на получателя ЕРМБ"
ALTER TABLE USERS_LIMITS_JOURNAL  ADD (EXTERNAL_CARD        VARCHAR2(18))
go

ALTER TABLE USERS_LIMITS_JOURNAL  ADD (EXTERNAL_PHONE       VARCHAR2(16))
go

/*==============================================================*/
/* Index: USR_LIMITS_CARD_IND                                   */
/*==============================================================*/
create index USR_LIMITS_CARD_IND on USERS_LIMITS_JOURNAL (
   EXTERNAL_CARD ASC,
   CREATION_DATE ASC
)
go

/*==============================================================*/
/* Index: USR_LIMITS_PHONE_IND                                  */
/*==============================================================*/
create index USR_LIMITS_PHONE_IND on USERS_LIMITS_JOURNAL (
   EXTERNAL_PHONE ASC,
   CREATION_DATE ASC
)
go

-- Номер ревизии: 55284
-- Номер версии: 1.18
-- Комментарий: BUG057896: Ошибка при отказе шуделлером перевода между своими счетами. 
CREATE OR REPLACE PROCEDURE updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in varchar, exceptionSystem in varchar, exceptionErrorCode in varchar) as
   existExceptions NUMBER;
   existCounters NUMBER;
   currentDate TIMESTAMP;
BEGIN
    SELECT trunc(SYSDATE) into currentDate FROM DUAL;
    SELECT count(ID) into existExceptions FROM EXCEPTION_ENTRY WHERE EXCEPTION_ENTRY.HASH = exceptionHash;

    IF existExceptions > 0 THEN
        SELECT count(EXCEPTION_HASH) into existCounters FROM EXCEPTION_COUNTERS WHERE EXCEPTION_HASH = exceptionHash AND EXCEPTION_DATE=currentDate;
    ELSE 
        INSERT INTO EXCEPTION_ENTRY (ID, KIND, HASH, APPLICATION, OPERATION, DETAIL, MESSAGE, SYSTEM, ERROR_CODE) VALUES (S_EXCEPTION_ENTRY.NEXTVAL, exceptionKind, exceptionHash, exceptionApplication, exceptionOperation, exceptionDetail, null, exceptionSystem, exceptionErrorCode);
        existCounters := 0;
    END IF;

    IF existCounters > 0 THEN
        UPDATE EXCEPTION_COUNTERS SET EXCEPTION_COUNT = EXCEPTION_COUNT + 1 WHERE EXCEPTION_HASH = exceptionHash AND EXCEPTION_DATE=currentDate;
    ELSE
        INSERT INTO EXCEPTION_COUNTERS (EXCEPTION_HASH, EXCEPTION_DATE, EXCEPTION_COUNT) VALUES (exceptionHash, trunc(SYSDATE), 1);
    END IF;

    EXCEPTION WHEN DUP_VAL_ON_INDEX THEN UPDATE EXCEPTION_COUNTERS SET EXCEPTION_COUNT = EXCEPTION_COUNT + 1 WHERE EXCEPTION_HASH = exceptionHash AND EXCEPTION_DATE=currentDate;
END;
go

-- Номер ревизии: 55352
-- Номер версии: 1.18
-- Комментарий: CHG063922: Удалить таблицу MB_SMS_ID.
rename S_MB_SMS_ID to SC_MB_SMS_ID
go
drop table MB_SMS_ID
go

-- Номер ревизии: 55364
-- Номер версии: 1.18
-- Комментарий: BUG061589: Исключение в Маппинге системных ошибок 
DELETE FROM EXCEPTION_COUNTERS 
WHERE EXCEPTION_HASH IN (SELECT HASH 
                         FROM EXCEPTION_ENTRY 
                         WHERE APPLICATION  = 'Gate')
go

DELETE FROM EXCEPTION_ENTRY 
WHERE APPLICATION ='Gate'
go
-- Номер ревизии: 55440
-- Номер версии: 1.18
-- Комментарий: ENH061752: Обработка доступа ко всем подразделениям в схемах прав сотрудников при работе с подразделениями(ч1 - список тербанков)

drop index I_DEPARTMENTS_BANK_INFO
go

/*==============================================================*/
/* Index: I_DEPARTMENTS_BANK_INFO                               */
/*==============================================================*/
create unique index I_DEPARTMENTS_BANK_INFO on DEPARTMENTS (
   TB ASC,
   nvl(OSB, 'NULL') ASC,
   nvl(OFFICE, 'NULL') ASC,
   ID ASC
)
go

-- Номер ревизии: 55455
-- Номер версии: 1.18
-- Комментарий: CHG063854: Сбор статисики для push и email. 

drop table SENT_PUSH_MESSAGES_LOG
go
drop sequence S_SENT_PUSH_MESSAGES_LOG
go

drop table USER_MESSAGES_LOG
go

create table USER_MESSAGES_LOG 
(
   ID                   INTEGER              not null,
   ADDITION_DATE        TIMESTAMP            not null,
   TYPE                 VARCHAR2(25)         not null,
   MESSAGE_ID           VARCHAR2(64),
   LOGIN_ID             INTEGER              not null,
   TYPE_CODE            VARCHAR2(32),
   constraint PK_USER_MESSAGES_LOG primary key (ID)
)
partition by range (ADDITION_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
    --Создаем 1 начальную партицию до 1 декабря
    partition P_2013_11 values less than (to_date('1-12-2013','DD-MM-YYYY'))
)
go

create index I_USER_MESSAGES_LOG_DATE_TYPE on USER_MESSAGES_LOG (
   ADDITION_DATE ASC,
   TYPE_CODE ASC
)
go

create index I_USER_NOTIFICATION_LOG_DATE on USER_NOTIFICATION_LOG (
   ADDITION_DATE ASC,
   TYPE ASC
)
go

create index I_PUSH_DEVICES_STATES_LOG_DATE on PUSH_DEVICES_STATES_LOG (
   CREATION_DATE ASC,
   TYPE ASC
)
go

-- Номер ревизии: 55501
-- Номер версии: 1.18
-- Комментарий: ENH058779: Переместить блок "Спасибо" в общий список продуктов
alter table LOYALTY_PROGRAM_LINKS add  SHOW_IN_MAIN char(1) default '1' not null
go

-- Номер ревизии: 55507
-- Номер версии: 1.18
-- Комментарий: BUG061367: Ошибка в маппинге внешних ошибок. 
DELETE FROM EXCEPTION_COUNTERS WHERE EXCEPTION_HASH IN (SELECT HASH FROM EXCEPTION_ENTRY WHERE KIND='E' AND SYSTEM IS NULL)
go
DELETE FROM EXCEPTION_ENTRY WHERE KIND='E' AND SYSTEM IS NULL
go


-- Номер ревизии: 55521
-- Номер версии: 1.18
-- Комментарий: Новостная рассылка. Часть II
ALTER TABLE PROFILE DROP COLUMN BANK_NEWS_NOTIFICATION_TYPE
GO

CREATE TABLE NEWS_SUBSCRIPTIONS(
   ID         INTEGER     not null,
   LOGIN_ID   INTEGER     not null,
   TB         varchar2(2) not null
)
GO

CREATE SEQUENCE S_NEWS_SUBSCRIPTIONS
GO

create index NEWS_SUBSCRIPTIONS_TB_INDEX on NEWS_SUBSCRIPTIONS (
   TB ASC
)
GO

DROP SEQUENCE s_NEWS_DISTR_DEPARTMENTS
GO

DROP TABLE NEWS_DISTR_DEPARTMENTS
GO

ALTER TABLE NEWS_DISTRIBUTIONS ADD (TERBANKS VARCHAR2(60) NOT NULL)
GO

ALTER TABLE NEWS_DISTRIBUTIONS ADD (LAST_PROCESSED_ID INTEGER)
GO

-- Номер ревизии: 55536
-- Номер версии: 1.18
-- Комментарий: CHG063854: Сбор статисики для push и email. 

drop index I_USER_MESSAGES_LOG_DATE_TYPE
go
drop index I_USER_NOTIFICATION_LOG_DATE
go

create index I_USER_MESSAGES_LOG_DATE_TYPE on USER_MESSAGES_LOG (
   ADDITION_DATE ASC,
   TYPE ASC
)
local
go

create index I_USER_NOTIFICATION_LOG_DATE on USER_NOTIFICATION_LOG (
   ADDITION_DATE ASC,
   VALUE ASC
)
go

-- Номер ревизии: 55562
-- Номер версии: 1.18
-- Комментарий: BUG062167: Уменьшить допустимое колличество символов в названии сертификата.

ALTER TABLE SECURITY_ACCOUNT_LINKS
   MODIFY ( SECURITY_NAME VARCHAR2(100) )
GO

-- Номер ревизии: 55565
-- Номер версии: 1.18
-- Комментарий:   BUG063770: Исключение при удалении фоновой задачи репликации подразделений 

alter table DEPARTMENTS_TASKS_CONTENT drop constraint FK_CONTENT_TO_DEP_TASKS
go
alter table DEPARTMENTS_TASKS_CONTENT
   add constraint FK_CONTENT_TO_DEP_TASKS foreign key (REPLICA_TASKS_ID)
      references DEPARTMENTS_REPLICA_TASKS (ID)
      on delete cascade
go

-- Номер ревизии: 55572
-- Номер версии: 1.18
-- Комментарий:   CHG064088: Поместить ссылку на бонусную программу вниз Избранного
alter table FAVOURITE_LINKS add ONCLICK_FUNCTION varchar2(300)
go
update FAVOURITE_LINKS set ONCLICK_FUNCTION='openBusinessmanRegistrationWindow(event);'  where NAME='Стань предпринимателем'
go


-- Номер ревизии: актуально для любой ревизии
-- Номер версии: 1.18
-- Комментарий: BUG061035: Исключение при добавлении кредитного продукта
ALTER TABLE PFP_PERSON_LOAN MODIFY NAME VARCHAR2(256)
go

-- Номер ревизии: 55693
-- Номер версии: 1.18
-- Комментарий: BUG057697: Некорректный фильтр справочника маппинга внешних ошибок.
ALTER TABLE EXCEPTION_ENTRY MODIFY SYSTEM varchar2(64)
go

ALTER TABLE EXCEPTION_ENTRY MODIFY HASH varchar2(256)
go

ALTER TABLE EXCEPTION_COUNTERS MODIFY EXCEPTION_HASH varchar2(256)
go

-- Номер ревизии: 55693
-- Номер версии: 1.18
-- Комментарий: BUG064494: Не работает репликация по большому количеству ОСБ 

alter table DEPARTMENTS_REPLICA_TASKS modify (DEPARTMENTS varchar2(3000))
go

-- Номер ревизии: 55727
-- Номер версии: 1.18
-- Комментарий: CHG063851 Механизм мапинга ошибок(ч1. работаем в 1 коннекте)
CREATE OR REPLACE PROCEDURE updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in varchar, exceptionSystem in varchar, exceptionErrorCode in varchar) as
   PRAGMA AUTONOMOUS_TRANSACTION;
   existExceptions NUMBER;
   existCounters NUMBER;
   currentDate TIMESTAMP;
   updateExceptionInformationOFF NUMBER;
BEGIN
    SELECT count(1) into updateExceptionInformationOFF from PROPERTIES where PROPERTY_KEY='com.rssl.phizic.business.exception.updateExceptionInformation' AND PROPERTY_VALUE = 'OFF' AND CATEGORY='iccs.properties';

    IF updateExceptionInformationOFF > 0 THEN
        ROLLBACK;
        RETURN;
    END IF;

    SELECT trunc(SYSDATE) into currentDate FROM DUAL;
    SELECT count(ID) into existExceptions FROM EXCEPTION_ENTRY WHERE EXCEPTION_ENTRY.HASH = exceptionHash;

    IF existExceptions > 0 THEN
        SELECT count(EXCEPTION_HASH) into existCounters FROM EXCEPTION_COUNTERS WHERE EXCEPTION_HASH = exceptionHash AND EXCEPTION_DATE=currentDate;
    ELSE 
        INSERT INTO EXCEPTION_ENTRY (ID, KIND, HASH, APPLICATION, OPERATION, DETAIL, MESSAGE, SYSTEM, ERROR_CODE) VALUES (S_EXCEPTION_ENTRY.NEXTVAL, exceptionKind, exceptionHash, exceptionApplication, exceptionOperation, exceptionDetail, null, exceptionSystem, exceptionErrorCode);
        existCounters := 0;
    END IF;

    IF existCounters > 0 THEN
        UPDATE EXCEPTION_COUNTERS SET EXCEPTION_COUNT = EXCEPTION_COUNT + 1 WHERE EXCEPTION_HASH = exceptionHash AND EXCEPTION_DATE=currentDate;
    ELSE
        INSERT INTO EXCEPTION_COUNTERS (EXCEPTION_HASH, EXCEPTION_DATE, EXCEPTION_COUNT) VALUES (exceptionHash, trunc(SYSDATE), 1);
    END IF;
    COMMIT;

    EXCEPTION WHEN DUP_VAL_ON_INDEX THEN UPDATE EXCEPTION_COUNTERS SET EXCEPTION_COUNT = EXCEPTION_COUNT + 1 WHERE EXCEPTION_HASH = exceptionHash AND EXCEPTION_DATE=currentDate;
    
    COMMIT;
END;
go

drop sequence  S_EXCEPTION_COUNTERS
go

-- Номер ревизии: 55748
-- Номер версии: 1.18
-- Комментарий: BUG064354: Неверно отображаются статусы интернет-заказов после конвертации
create table BUSINESS_DOCUMENTS_TO_ORDERS (
   BUSINESS_DOCUMENT_ID integer not null,
   ORDER_UUID varchar2(32) not null,
   constraint PK_BUSINESS_DOCUMENTS_TO_ORDER primary key (BUSINESS_DOCUMENT_ID, ORDER_UUID)
)
go

insert into BUSINESS_DOCUMENTS_TO_ORDERS (BUSINESS_DOCUMENT_ID, ORDER_UUID) 
select bd.ID, bd.ORDER_UUID from BUSINESS_DOCUMENTS bd, PAYMENTFORMS frm where bd.ORDER_UUID is not null and bd.FORM_ID = frm.ID and frm.NAME in ('AirlineReservationPayment', 'ExternalProviderPayment')
go

create index BUS_DOC_ORD_UUID_IDX on BUSINESS_DOCUMENTS_TO_ORDERS
(
   ORDER_UUID asc 
)
go

create index "DXREFERENCE_12" on BUSINESS_DOCUMENTS_TO_ORDERS
(
   BUSINESS_DOCUMENT_ID
)
go

alter table BUSINESS_DOCUMENTS_TO_ORDERS
   add constraint FK_BUSINESS_REFERENCE_BUSINESS foreign key (BUSINESS_DOCUMENT_ID)
      references BUSINESS_DOCUMENTS (ID)
go

alter table BUSINESS_DOCUMENTS drop column ORDER_UUID
go

alter table ORDERS drop column PAYMENT_ID 
go

-- Номер ревизии: 55793
-- Номер версии: 1.18
-- Комментарий: ENH046236: Убрать "девочку" из АРМ-клиента.

alter table PROFILE drop column SHOW_ASSISTANT
go

-- Номер ревизии: 55808
-- Номер версии: 1.18
-- Комментарий: BUG057697: Некорректный фильтр справочника маппинга внешних ошибок. 

truncate table EXCEPTION_COUNTERS
go
truncate table EXCEPTION_ENTRY
go


-- Номер ревизии: 55812
-- Номер версии: 1.18
-- Комментарий: BUG059752: Доработать отображение кред. карт на шаге Эффект использования кредитной карты 

update PFP_CARDS
set NAME = SUBSTR(NAME, 0, 80)  
where LENGTH(NAME) > 80
go

update PFP_CARDS
set DESCRIPTION = SUBSTR(DESCRIPTION, 0, 170)  
where LENGTH(DESCRIPTION) > 170
go

alter table PFP_CARDS modify (NAME varchar2(80))
go

alter table PFP_CARDS modify (DESCRIPTION varchar2(170))
go 

-- Номер ревизии: 55819
-- Номер версии: 1.18
-- Комментарий: CHG063851 Механизм мапинга ошибок(ч3. вставка и агрегация отдельно)
/*==============================================================*/
/* Table: EXCEPTIONS_LOG                                        */
/*==============================================================*/
create table EXCEPTIONS_LOG 
(
   CREATION_DATE        TIMESTAMP            not null,
   HASH                 VARCHAR2(256)        not null
)
PARTITION BY RANGE (CREATION_DATE) INTERVAL (NUMTODSINTERVAL(1,'day'))
(
    partition P_FIRST values less than (to_date('1-11-2013','DD-MM-YYYY'))
)
go

CREATE OR REPLACE PROCEDURE aggregateExceptionInformation(aggregationDate TIMESTAMP) as
BEGIN
 execute immediate 'INSERT INTO EXCEPTION_COUNTERS (EXCEPTION_HASH, EXCEPTION_DATE, EXCEPTION_COUNT) '||
    'SELECT HASH, to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' ), count(1) FROM EXCEPTIONS_LOG '||
        'PARTITION FOR (to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' )) GROUP BY HASH';

 execute immediate 'alter table EXCEPTIONS_LOG drop partition for (to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' ))';
END;
go

CREATE OR REPLACE PROCEDURE updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in varchar, exceptionSystem in varchar, exceptionErrorCode in varchar) as
   PRAGMA AUTONOMOUS_TRANSACTION;
   updateExceptionInformationOFF NUMBER;
BEGIN
    SELECT count(1) into updateExceptionInformationOFF from PROPERTIES where PROPERTY_KEY='com.rssl.phizic.business.exception.updateExceptionInformation' AND PROPERTY_VALUE = 'OFF' AND CATEGORY='iccs.properties';

    IF updateExceptionInformationOFF > 0 THEN
        ROLLBACK;
        RETURN;
    END IF;

    INSERT INTO EXCEPTIONS_LOG(CREATION_DATE, HASH) values (SYSDATE, exceptionHash);

    INSERT INTO EXCEPTION_ENTRY (ID, KIND, HASH, APPLICATION, OPERATION, DETAIL, MESSAGE, SYSTEM, ERROR_CODE) 
        SELECT S_EXCEPTION_ENTRY.nextval, exceptionKind, exceptionHash, exceptionApplication, exceptionOperation, exceptionDetail, null, exceptionSystem, exceptionErrorCode FROM DUAL
        WHERE NOT EXISTS (SELECT 1 FROM EXCEPTION_ENTRY WHERE EXCEPTION_ENTRY.HASH = exceptionHash);

    COMMIT;
END;
go

BEGIN
DBMS_SCHEDULER.CREATE_JOB (
   job_name                 =>  'AGGREGATE_EXC_INFO', 
   job_type                 =>  'PLSQL_BLOCK',
   job_action               =>  'BEGIN <ИМЯ_СХЕМЫ>.aggregateExceptionInformation(sysdate-1); END;',
   start_date               =>  to_date('2013-11-26 02:25','YYYY-MM-DD HH24:MI'),
   repeat_interval          =>  'FREQ=DAILY',
   enabled => TRUE
   );
END;
go

-- Номер ревизии: 55819
-- Номер версии: 1.18
-- Комментарий: иок (поправил тип параметра)
CREATE OR REPLACE PROCEDURE updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in clob, exceptionSystem in varchar, exceptionErrorCode in varchar) as
   PRAGMA AUTONOMOUS_TRANSACTION;
   updateExceptionInformationOFF NUMBER;
BEGIN
    SELECT count(1) into updateExceptionInformationOFF from PROPERTIES where PROPERTY_KEY='com.rssl.phizic.business.exception.updateExceptionInformation' AND PROPERTY_VALUE = 'OFF' AND CATEGORY='iccs.properties';

    IF updateExceptionInformationOFF > 0 THEN
        ROLLBACK;
        RETURN;
    END IF;

    INSERT INTO EXCEPTIONS_LOG(CREATION_DATE, HASH) values (SYSDATE, exceptionHash);

    INSERT INTO EXCEPTION_ENTRY (ID, KIND, HASH, APPLICATION, OPERATION, DETAIL, MESSAGE, SYSTEM, ERROR_CODE) 
        SELECT S_EXCEPTION_ENTRY.nextval, exceptionKind, exceptionHash, exceptionApplication, exceptionOperation, exceptionDetail, null, exceptionSystem, exceptionErrorCode FROM DUAL
        WHERE NOT EXISTS (SELECT 1 FROM EXCEPTION_ENTRY WHERE EXCEPTION_ENTRY.HASH = exceptionHash);

    COMMIT;
END;
go

-- Номер ревизии: 55915
-- Номер версии: 1.18
-- Комментарий: BUG064107: Некорректное отображение динамики курсов. 
update rate set DYNAMIC_EXCHANGE = 'TEMP_VAR' where DYNAMIC_EXCHANGE = 'DOWN'
go
update rate set DYNAMIC_EXCHANGE = 'DOWN' where DYNAMIC_EXCHANGE = 'UP'
go
update rate set DYNAMIC_EXCHANGE = 'UP' where DYNAMIC_EXCHANGE = 'TEMP_VAR'
go

-- Номер ревизии: 55979
-- Номер версии: 1.18
-- Комментарий: WebAPI. 
create table WEBAPI_SESSIONS 
(
   SID                  varchar2(32)         not null,
   LOGIN_ID             INTEGER              not null,
   OPEN_DATE            TIMESTAMP(6)         not null,
   CLOSE_DATE           TIMESTAMP(6)         not null,
   PARAMS               CLOB,
   constraint PK_WEBAPI_SESSIONS primary key (SID)
)
go


-- Номер ревизии: 56122
-- Номер версии: 1.18
-- Комментарий: BUG065012: Новости в клиентском приложении  
drop index INDEX_DATE_6
go

create index NEWS_INDEX on NEWS (
   STATE ASC,
   NEWS_DATE ASC
)
go

-- Номер ревизии: 56127
-- Номер версии: 1.18
-- Комментарий: BUG065010: АЛФ Получение списка категорий и суммы операций в национальной валюте 
DROP INDEX IDX_OPERATION_DATE
go

DROP INDEX IDX_CARDOP_FILTER
go

DROP INDEX IDX_CARDOP_LOAD_DATE
GO


CREATE INDEX I_CARDOP_LCO ON CARD_OPERATIONS 
(
    LOGIN_ID,
    CARD_NUMBER,
    OPERATION_DATE
)
go

drop index IDX_CARDOP_CATEGORY_NVL_OWNER
go

drop index IDX_NAME_LOGIN_INCOME
go

create unique index IDX_NAME_LOGIN_INCOME on CARD_OPERATION_CATEGORIES (
   (nvl(LOGIN_ID, -1)) ASC,
   INCOME ASC,
   NAME ASC
)
go

-- Номер ревизии: 56135
-- Номер версии: 1.18
-- Комментарий:  Сохранение оферты МБК. 
ALTER TABLE CONFIRM_BEANS MODIFY (CONFIRMABLE_TASK_CLASS NULL)
GO
ALTER TABLE CONFIRM_BEANS MODIFY (CONFIRMABLE_TASK_BODY NULL)
GO

-- Номер ревизии: 56127
-- Номер версии: 1.18
-- Комментарий: BUG065010: АЛФ Получение списка категорий и суммы операций в национальной валюте 
drop table CARD_OPERATIONS
go

create table CARD_OPERATIONS 
(
   ID                   INTEGER              not null,
   EXTERNAL_ID          VARCHAR2(64),
   OPERATION_DATE       TIMESTAMP            not null,
   CARD_NUMBER          VARCHAR2(20)         not null,
   DESCRIPTION          VARCHAR2(100),
   NATIONAL_SUMM        NUMBER(15,4)         not null,
   CARD_SUMM            NUMBER(15,4)         not null,
   CASH                 CHAR(1)              not null,
   DEVICE_NUMBER        VARCHAR2(10),
   LOGIN_ID             INTEGER              not null,
   CATEGORY_ID          INTEGER              not null,
   MCC_CODE             INTEGER,
   LOAD_DATE            TIMESTAMP            not null
)
partition by range (OPERATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
    partition P_FIRST values less than (to_date('01-01-2012','DD-MM-YYYY'))
)
go

create index I_CARD_OPERATIONS_ID ON CARD_OPERATIONS
(
    ID
)
local
go

create index IDX_EXTERNAL_ID ON CARD_OPERATIONS
(
    EXTERNAL_ID
)
global
go

create index I_CARDOP_LCO on CARD_OPERATIONS (
   LOGIN_ID ASC,
   CARD_NUMBER ASC,
   OPERATION_DATE ASC
)
local
go

create index "DXFK_CARDOP_CATEGORY" on CARD_OPERATIONS
(
   CATEGORY_ID
)
go

alter table CARD_OPERATIONS
   add constraint FK_CARDOP_CATEGORY foreign key (CATEGORY_ID)
      references CARD_OPERATION_CATEGORIES (ID)
go

--если таблица CARD_OPERATIONS копироваласть, то удалять не нужно
DELETE FROM CARD_OPERATION_CLAIMS
go

-- Номер ревизии: 56280
-- Номер версии: 1.18
-- Комментарий: BUG065314: [Служебный канал ЕРМБ] Не передаётся ТБ профиля ЕРМБ

alter table PERSON_OLD_IDENTITY add (TB varchar2(4))
go

update PERSON_OLD_IDENTITY
set TB =
    (select TB from DEPARTMENTS
     where id = 
        (
            select DEPARTMENT_ID from USERS
            where id = PERSON_OLD_IDENTITY.PERSON_ID 
        )
     )
go

alter table PERSON_OLD_IDENTITY modify (TB not null)
go

-- Номер ревизии: 56336
-- Номер версии: 1.18
-- Комментарий: BUG059903: Не отображается сообщение о незаполненных персональных данных (ч.2 обновление данных редактируемого сотрудника)
alter table LOGINS add LAST_SYNCHRONIZATION_DATE TIMESTAMP(6)
go

update LOGINS set LAST_SYNCHRONIZATION_DATE = LAST_LOGON_DATE WHERE DELETED ='0' AND  KIND='E'
go

-- Номер ревизии: 56492
-- Номер версии: 1.18
-- Комментарий: ENH064911: Создание дефолтного администратора в csaAdmin. (ч.2 вход дефолтного админа)
UPDATE LOGINS SET CSA_USER_ID = 'admin' where USER_ID = 'admin'
go


-- Номер ревизии: 56495
-- Номер версии: 1.18
-- Комментарий: CHG065516: Переделать привязку новостей к департаментам 

alter table NEWS_DEPARTMENT add TB varchar2(4)
go
update NEWS_DEPARTMENT  nd set TB = (select TB from DEPARTMENTS d where d.ID = nd.DEPARTMENT_ID)
go
alter table NEWS_DEPARTMENT modify TB not null
go
alter table NEWS_DEPARTMENT drop constraint FK_NEWS_DEP_TO_DEPARTMENTS
go
alter table NEWS_DEPARTMENT drop constraint PK_NEWS_DEPARTMENT
go
alter table NEWS_DEPARTMENT add constraint PK_NEWS_DEPARTMENT primary key (NEWS_ID, TB)
go
alter table NEWS_DEPARTMENT drop column DEPARTMENT_ID
go

-- Номер ревизии: 56520
-- Номер версии: 1.18
-- Комментарий: Фасилитаторская схема Яндекс(ч.1)
alter table SERVICE_PROVIDERS add IS_FASILITATOR char(1) default'0' not null
go

-- Номер ревизии: 56548
-- Номер версии: 1.18
-- Комментарий: Тарифы ЕРМБ. Админка. 
alter table ERMB_TARIF drop column STATUS
go
alter table ERMB_TARIF add DESCRIPTION varchar2(300)
go

-- Номер ревизии: 56564
-- Номер версии: 1.18
-- Комментарий: Видимость продуктов по умолчанию (применение правил)
alter table ERMB_PROFILE add NEW_PRODUCT_SHOW_IN_SMS CHAR(1) default '0' not null
go

-- Номер ревизии: 56579
-- Номер версии: 1.18
-- Комментарий: ПФП. объединение таблиц (пенсионные продукты)
alter table PFP_PENSION_PRODUCT add NAME VARCHAR2(250)
go
alter table PFP_PENSION_PRODUCT add DESCRIPTION VARCHAR2(500)
go
alter table PFP_PENSION_PRODUCT add IMAGE_ID INTEGER
go
alter table PFP_PENSION_PRODUCT add MIN_INCOME NUMBER(7,2)
go
alter table PFP_PENSION_PRODUCT add MAX_INCOME NUMBER(7,2)
go
alter table PFP_PENSION_PRODUCT add DEFAULT_INCOME NUMBER(7,2)
go
alter table PFP_PENSION_PRODUCT add UNIVERSAL char(1)
go
alter table PFP_PENSION_PRODUCT add ENABLED char(1)
go
alter table PFP_PENSION_PRODUCT rename column PRODUCT_BASE_ID to ID
go

update PFP_PENSION_PRODUCT pp set NAME = (select NAME FROM PFP_PRODUCT_BASE WHERE PFP_PRODUCT_BASE.ID = pp.ID)
go
update PFP_PENSION_PRODUCT pp set DESCRIPTION = (select DESCRIPTION FROM PFP_PRODUCT_BASE WHERE PFP_PRODUCT_BASE.ID = pp.ID)
go
update PFP_PENSION_PRODUCT pp set IMAGE_ID = (select IMAGE_ID FROM PFP_PRODUCT_BASE WHERE PFP_PRODUCT_BASE.ID = pp.ID)
go
update PFP_PENSION_PRODUCT pp set MIN_INCOME = (select MIN_INCOME FROM PFP_PRODUCT_BASE WHERE PFP_PRODUCT_BASE.ID = pp.ID)
go
update PFP_PENSION_PRODUCT pp set MAX_INCOME = (select MAX_INCOME FROM PFP_PRODUCT_BASE WHERE PFP_PRODUCT_BASE.ID = pp.ID)
go
update PFP_PENSION_PRODUCT pp set DEFAULT_INCOME = (select DEFAULT_INCOME FROM PFP_PRODUCT_BASE WHERE PFP_PRODUCT_BASE.ID = pp.ID)
go
update PFP_PENSION_PRODUCT pp set UNIVERSAL = (select UNIVERSAL from PFP_PRODUCT_BASE WHERE PFP_PRODUCT_BASE.ID = pp.ID)
go
update PFP_PENSION_PRODUCT pp set ENABLED = (select ENABLED FROM PFP_PRODUCT_BASE WHERE PFP_PRODUCT_BASE.ID = pp.ID)
go

alter table PFP_PENSION_PRODUCT modify NAME not null
go
alter table PFP_PENSION_PRODUCT modify UNIVERSAL not null
go
alter table PFP_PENSION_PRODUCT modify ENABLED not null
go

alter table PFP_PRODUCT_TARGET_GROUPS
   drop constraint FK_PFP_TARGET_GR_TO_PRODUCT
go
alter table PFP_PRODUCT_TARGET_GROUPS
   add constraint FK_PFP_TARGET_GR_TO_P_PRODUCT foreign key (PRODUCT_ID)
      references PFP_PENSION_PRODUCT (ID)
      on delete cascade
go
alter table PFP_PENSION_PRODUCT
   drop constraint FK_PFP_PENSION_TO_PRODUCT
go
drop TABLE PFP_PRODUCT_BASE
go
drop sequence S_PFP_PENSION_PRODUCT
go
rename S_PFP_PRODUCT_BASE to S_PFP_PENSION_PRODUCT
go
RENAME PFP_PRODUCT_TARGET_GROUPS TO PFP_P_PRODUCT_TARGET_GROUPS
go
ALTER INDEX "DXFK_PFP_TARGET_GR_TO_PRODUCT" RENAME TO "DXFK_PFP_TARGET_GR_TO_P_PRODUC"
go

drop sequence S_PFP_CP_TABLE_VIEW_PARAMETERS
go
drop sequence S_PFP_CP_TARGET_GROUPS_BUNDLE
go
drop sequence S_PFP_C_FUND_PRODUCTS
go
drop sequence S_PFP_C_IMA_PRODUCTS
go
drop sequence S_PFP_C_INSURANCE_PRODUCTS
go
drop sequence S_PFP_C_PRODUCT_PARAMETERS
go
drop sequence S_PFP_DIAGRAM_STEPS
go
drop sequence S_PFP_INS_PRODUCT_TO_PERIODS
go
drop sequence S_PFP_IP_TARGET_GROUPS_BUNDLE
go
drop sequence S_PFP_PRODUCT_PARAMETERS
go
drop sequence S_PFP_PRODUCT_TARGET_GROUPS
go
drop sequence S_PFP_PT_TARGET_GROUPS
go
drop sequence S_PFP_SP_TABLE_VIEW_PARAMETERS
go
drop sequence S_PFP_SP_TARGET_GROUPS_BUNDLE
go

-- Номер ревизии: 56650
-- Номер версии: 1.18
-- Комментарий: CHG065690: Переделать привязку ПБО к тербанкам

---Информационные сообщения----
alter table MESSAGES_DEPARTMENTS add TB varchar2(4)
go
update MESSAGES_DEPARTMENTS  nd set TB = (select TB from DEPARTMENTS d where d.ID = nd.DEPARTMENT_ID)
go
alter table MESSAGES_DEPARTMENTS drop constraint PK_MESSAGES_DEPARTMENTS
go
alter table MESSAGES_DEPARTMENTS drop column DEPARTMENT_ID
go
alter table MESSAGES_DEPARTMENTS add constraint PK_MESSAGES_DEPARTMENTS primary key(MESSAGE_ID, TB)
go
--------------ПБО--------------
alter table Q_P_PANELS_DEPARTMENTS add TB varchar2(4)
go
update Q_P_PANELS_DEPARTMENTS  nd set TB = (select TB from DEPARTMENTS d where d.ID = nd.DEPARTMENT_ID)
go
alter table Q_P_PANELS_DEPARTMENTS drop constraint PK_Q_P_PANELS_DEPARTMENTS
go
alter table Q_P_PANELS_DEPARTMENTS drop column DEPARTMENT_ID
go
alter table Q_P_PANELS_DEPARTMENTS add constraint PK_Q_P_PANELS_DEPARTMENTS primary key(Q_P_PANEL_ID, TB)
go
alter table Q_P_PANELS_DEPARTMENTS drop constraint AK_DEPARTMENT_ID
go
alter table Q_P_PANELS_DEPARTMENTS add constraint AK_DEPARTMENT_ID unique (TB)
go

-------Рекламные баннеры------
alter table ADVERTISINGS_DEPARTMENTS add TB varchar2(4)
go
update ADVERTISINGS_DEPARTMENTS  nd set TB = (select TB from DEPARTMENTS d where d.ID = nd.DEPARTMENT_ID)
go
alter table ADVERTISINGS_DEPARTMENTS drop constraint PK_ADVERTISINGS_DEPARTMENTS
go
alter table ADVERTISINGS_DEPARTMENTS drop column DEPARTMENT_ID
go
alter table ADVERTISINGS_DEPARTMENTS add constraint PK_ADVERTISINGS_DEPARTMENTS primary key(ADVERTISING_ID, TB)
go

-- Номер ревизии: 56670
-- Номер версии: 1.18
-- Комментарий: Доработать структуру для хранения новых доступных подразделений

create table ALLOWED_DEPARTMENTS(
    LOGIN_ID INTEGER not NULL,
    TB varchar2(4) not NULL,
    OSB varchar2(4) not NULL,
    VSP varchar(7) not NULL,
    constraint PK_ALLOWED_DEPARTMENTS primary key(LOGIN_ID, TB, OSB, VSP)
)
go

create index "DXFK_ALLOWED_DEPS_TO_LOGINS" on ALLOWED_DEPARTMENTS
(
   LOGIN_ID
)
go
alter table ALLOWED_DEPARTMENTS
   add constraint FK_ALLOWED_DEPS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
go

-- Номер ревизии: 56730
-- Номер версии: 1.18
-- Комментарий: Доработать механизм входа клиента в многоблочном режиме

drop table ALLOWED_DEPARTMENT_TEMPORARY
go

-- Номер ревизии: 56752
-- Номер версии: 1.18
-- Комментарий: Отображение информации о максимальной сумме вклада: доработка xsd, AccountInfo и генерация веб-сервисов.

ALTER TABLE STORED_ACCOUNT ADD (CLEAR_BALANCE NUMBER);
ALTER TABLE STORED_ACCOUNT ADD (MAX_BALANCE NUMBER);

-- Номер ревизии: 56759
-- Номер версии: 1.18
-- Комментарий: Добавлены признаки для определения клиентов, которые активно пользуются АЛФ. Реализовано обновление статисти по этим признакам.

alter table PROFILE add LAST_USING_FINANCES_DATE TIMESTAMP
go

alter table PROFILE add USING_FINANCES_COUNT INTEGER
go

alter table PROFILE add USING_ALF_EVERY_THREE_DAYS_NUM INTEGER
go

-- Номер ревизии: 56783
-- Номер версии: 1.18
-- Комментарий: АЛФ. Флаг отображения операций переводов (добавить в справочник категорий поле тип, доработать функционал редактирования категорий)

alter table CARD_OPERATION_CATEGORIES add PAYMENT_TYPE VARCHAR2(8)
go

update CARD_OPERATION_CATEGORIES set PAYMENT_TYPE='PAYMENT'
go

alter table CARD_OPERATION_CATEGORIES modify PAYMENT_TYPE not null
go

alter table CARD_OPERATION_CATEGORIES add ID_IN_MAPI VARCHAR2(30)
go

-- Номер ревизии: 56790
-- Номер версии: 1.18
-- Комментарий: Подача кредитной заявки через СБОЛ, Интеграция с TSM. Загрузка пред одобренных предложений по кредитам.

alter table LOAN_OFFER add PRODUCT_CODE varchar(2)
go
alter table LOAN_OFFER add SUB_PRODUCT_CODE varchar(5)
go
alter table LOAN_OFFER add PRODUCT_TYPE_CODE varchar(5)
go

-- Номер ревизии: 56805
-- Номер версии: 1.18
-- Комментарий: Джоб загрузки данных по активным клиентам

alter table PROFILE add LAST_UPDATE_OPER_CLAIMS_DATE DATE
go

create index "DXFK_PROFILE_TO_CLAIMS_DATE" on PROFILE
(
   LAST_UPDATE_OPER_CLAIMS_DATE desc
)
go

-- Номер ревизии: 56812
-- Номер версии: 1.18
-- Комментарий: АРМ Сотрудника: Справочник видов кредитных продуктов.

create table CREDIT_PRODUCT_TYPE(
       ID integer not null,
       CODE integer not null, 
       NAME varchar2(25) not null,
       constraint PR_CREDIT_PRODUCT_TYPE primary key (ID)
)
go
create sequence S_CREDIT_PRODUCT_TYPE
go
create unique index IDX_CODE_CREDIT_PRODUCT_TYPE on CREDIT_PRODUCT_TYPE (
    CODE
)
go

-- Номер ревизии: 56817
-- Номер версии: 1.18
-- Комментарий: АРМ Сотрудник. Справочник кредитных продуктов (ч. 1)

create table CREDIT_PRODUCT(
    ID integer not null,
    NAME varchar2(25) not null,
    CODE integer,
    CODE_DESCRIPTION varchar2(100),
    ENSURING_STATUS varchar2(12) not null,
    constraint PR_CREDIT_PRODUCT primary key (ID)
)
go
create sequence S_CREDIT_PRODUCT
go 
create table CREDIT_PRODUCT_SUB_TYPE(
    ID integer not null,
    CODE integer, 
    DEPARTMENT_ID        integer              not null,
    TO_CURRENCY          varchar2(3)          not null,
    CREDIT_PRODUCT_ID    integer              not null,
    constraint PR_CREDIT_PRODUCT_SUB_TYPE primary key (ID)
)
go
create index "DXFK_CRED_PR_SUB_TYPE_TO_CRED_" on CREDIT_PRODUCT_SUB_TYPE
(
   CREDIT_PRODUCT_ID
)
go
alter table CREDIT_PRODUCT_SUB_TYPE
   add constraint FK_CRED_PR_SUB_TYPE_TO_DEP foreign key (DEPARTMENT_ID)
      references DEPARTMENT (ID)
      on delete cascade
go
create index "DXFK_CRED_PR_SUB_TYPE_TO_CURR" on CREDIT_PRODUCT_SUB_TYPE
(
   TO_CURRENCY
)
go
alter table CREDIT_PRODUCT_SUB_TYPE
   add constraint FK_CRED_PR_SUB_TYPE_TO_CURR foreign key (TO_CURRENCY)
      references CURRENCIES (ID)
      on delete cascade
go
create index "DXFK_CRED_PR_SUB_TYPE_TO_DEP" on CREDIT_PRODUCT_SUB_TYPE
(
   DEPARTMENT_ID
)
go
alter table CREDIT_PRODUCT_SUB_TYPE
   add constraint FK_CRED_PR_SUB_TYPE_TO_CRED_PR foreign key (CREDIT_PRODUCT_ID)
      references CREDIT_PRODUCT (ID)
      on delete cascade
go
create sequence S_CREDIT_PRODUCT_SUB_TYPE
go

-- Номер ревизии: 56840
-- Номер версии: 1.18
-- Комментарий: ENH059205: Привести получение списка ТБ к единому виду

create index I_DEPARTMENTS_TBS on DEPARTMENTS (
   DECODE(OFFICE||OSB||PARENT_DEPARTMENT,NULL,TB,NULL) ASC
)
go

-- Номер ревизии: 56869
-- Номер версии: 1.18
-- Комментарий: Отображение сообщения

ALTER TABLE ACCOUNT_LINKS
	ADD ( SHOWED_ARCHIVE_MESSAGE CHAR(1) NULL ) 
GO

ALTER TABLE CARD_LINKS
	ADD ( SHOWED_ARCHIVE_MESSAGE CHAR(1) NULL ) 
GO

ALTER TABLE LOAN_LINKS
	ADD ( SHOWED_ARCHIVE_MESSAGE CHAR(1) NULL ) 
GO

-- Номер ревизии: 56872
-- Номер версии: 1.18
-- Комментарий: АЛФ. Флаг отображения операций переводов.

alter table CARD_OPERATION_CATEGORIES drop column PAYMENT_TYPE
go

alter table CARD_OPERATION_CATEGORIES add IS_TRANSFER CHAR(1) default '0' not null
go

insert into CARD_OPERATION_CATEGORIES (ID, NAME, INCOME, CASH, CASHLESS, ALLOW_INCOMPATIBLE_OPERATIONS, EXTERNALID, IS_TRANSFER) 
values(S_CARD_OPERATION_CATEGORIES.NEXTVAL, 'Перевод во вне', 0, 1, 1, 1, 'TransfersOutside', 1)
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992410')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992410, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992411')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992411, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992412')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992412, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992413')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992413, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992420')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992420, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992421')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992421, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992422')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992422, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992423')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992423, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992430')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992430, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992431')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992431, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992432')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992432, null, 'TransfersOutside')
go

MERGE INTO MERCHANT_CATEGORY_CODES USING dual ON (CODE='99992433')
WHEN MATCHED THEN UPDATE SET OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
WHEN NOT MATCHED THEN INSERT (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    VALUES (99992433, null, 'TransfersOutside')
go


-- Номер ревизии: 56876
-- Номер версии: 1.18
-- Комментарий: Переделать привязку логов к департаментам
alter table CODLOG add(
    TB varchar2(4),
    OSB varchar2(4),
    VSP varchar2(7)
)
go
alter table SYSTEMLOG add(
    TB varchar2(4),
    OSB varchar2(4),
    VSP varchar2(7)
)
go
alter table USERLOG add(
    TB varchar2(4),
    OSB varchar2(4),
    VSP varchar2(7)
)
go

--Эту часть скрипта выполнять, если нужна привязка старых логов к департаментам

update CODLOG c set (TB, OSB, VSP) = (select TB, OSB, OFFICE from DEPARTMENTS d where d.ID = c.DEPARTMENT_ID) 
go
update SYSTEMLOG c set (TB, OSB, VSP) = (select TB, OSB, OFFICE from DEPARTMENTS d where d.ID = c.DEPARTMENT_ID) 
go
update USERLOG c set (TB, OSB, VSP) = (select TB, OSB, OFFICE from DEPARTMENTS d where d.ID = c.DEPARTMENT_ID) 
go

-- Номер ревизии: 56951
-- Номер версии: 1.18
-- Комментарий: CHG064204: Поменять маппинг FAVOURITE_LINKS 
alter table FAVOURITE_LINKS
    drop constraint FK_FAVOURIT_LINKS_TO_LOGINS

-- Номер ревизии: 56951
-- Номер версии: 1.18
-- Комментарий: CHG064204: Поменять маппинг FAVOURITE_LINKS 
alter table FAVOURITE_LINKS
   add constraint FK_FAVOURIT_LINKS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)

-- Номер ревизии: 56993
-- Номер версии: 1.18
-- Комментарий: Реализовать хранение справочников ПФП в базе ЦСА Админ
create table PFP_CARD_RECOMMENDATIONS 
(
   ID                     INTEGER           not null,
   RECOMMENDATION         VARCHAR2(150),
   ACCOUNT_FROM_INCOME    NUMBER(7,2),
   ACCOUNT_TO_INCOME      NUMBER(7,2),
   ACCOUNT_DEFAULT_INCOME NUMBER(7,2)       not null,
   ACCOUNT_DESCRIPTION    VARCHAR2(150)     not null,
   THANKS_FROM_INCOME     NUMBER(7,2),
   THANKS_TO_INCOME       NUMBER(7,2),
   THANKS_DEFAULT_INCOME  NUMBER(7,2)       not null,
   THANKS_DESCRIPTION     VARCHAR2(150)     not null,
   constraint PK_PFP_CARD_RECOMMENDATIONS primary key (ID)
)
go

create sequence S_PFP_CARD_RECOMMENDATIONS
go

create table PFP_CR_CARDS 
(
   RECOMMENDATION_ID INTEGER not null,
   LIST_INDEX        INTEGER not null,
   CARD_ID           INTEGER not null,
   constraint PK_PFP_CR_CARDS primary key (RECOMMENDATION_ID, LIST_INDEX)
)
go

alter table PFP_CR_CARDS
   add constraint FK_PFP_CR_CARDS_TO_CR foreign key (RECOMMENDATION_ID)
      references PFP_CARD_RECOMMENDATIONS (ID)
      on delete cascade
go

alter table PFP_CR_CARDS
   add constraint FK_PFP_CR_TO_CARDS foreign key (CARD_ID)
      references PFP_CARDS (ID)
go

create table PFP_CR_STEPS 
(
   RECOMMENDATION_ID INTEGER        not null,
   LIST_INDEX        INTEGER        not null,
   NAME              VARCHAR2(100)  not null,
   DESCRIPTION       VARCHAR2(500)  not null,
   constraint PK_PFP_CR_STEPS primary key (RECOMMENDATION_ID, LIST_INDEX)
)
go

alter table PFP_CR_STEPS
   add constraint FK_PFP_CR_STEPS_TO_CR foreign key (RECOMMENDATION_ID)
      references PFP_CARD_RECOMMENDATIONS (ID)
      on delete cascade
go

insert into PFP_CARD_RECOMMENDATIONS (ID, RECOMMENDATION, ACCOUNT_FROM_INCOME, ACCOUNT_TO_INCOME, ACCOUNT_DEFAULT_INCOME, ACCOUNT_DESCRIPTION, THANKS_FROM_INCOME, THANKS_TO_INCOME, THANKS_DEFAULT_INCOME, THANKS_DESCRIPTION)
values (S_PFP_CARD_RECOMMENDATIONS.NEXTVAL, 
        (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.recommendation'),
        (SELECT TO_NUMBER(REPLACE(PROPERTY_VALUE, '.', ',')) FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.income.from'),
        (SELECT TO_NUMBER(REPLACE(PROPERTY_VALUE, '.', ',')) FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.income.to'),
        (select TO_NUMBER(REPLACE((SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.income.default')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.income.default')
                ELSE '5'
            END
         FROM dual), '.', ',')) FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.description')
                ELSE 'Подсказка к полю вклад'
            END
         FROM dual),
        (SELECT TO_NUMBER(REPLACE(PROPERTY_VALUE, '.', ',')) FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.income.from'),
        (SELECT TO_NUMBER(REPLACE(PROPERTY_VALUE, '.', ',')) FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.income.to'),
        (select TO_NUMBER(REPLACE((SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.income.default')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.income.default')
                ELSE '0,5'
            END
         FROM dual), '.', ',')) FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.description')
                ELSE 'Подсказка к полю СПАСИБО'
            END
         FROM dual))
go

insert into PFP_CR_STEPS (RECOMMENDATION_ID, LIST_INDEX, NAME, DESCRIPTION)
values ((select ID FROM PFP_CARD_RECOMMENDATIONS), 
        0,
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step0.name')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step0.name')
                ELSE 'Получение кредитной карты'
            END
        FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step0.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step0.description')
                ELSE 'Оформите кредитную карту Сбербанка с льготным периодом.'
            END
        FROM dual))
go

insert into PFP_CR_STEPS (RECOMMENDATION_ID, LIST_INDEX, NAME, DESCRIPTION)
values ((select ID FROM PFP_CARD_RECOMMENDATIONS), 
        1,
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step1.name')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step1.name')
                ELSE 'Все расходы по кредитной карте (покупки)'
            END
        FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step1.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step1.description')
                ELSE 'Совершайте все покупки по кредитной карте в льготный период и не снимайте наличные с карты. Собственные средства разместите на вкладе.'
            END
        FROM dual))
go

insert into PFP_CR_STEPS (RECOMMENDATION_ID, LIST_INDEX, NAME, DESCRIPTION)
values ((select ID FROM PFP_CARD_RECOMMENDATIONS), 
        2,
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step2.name')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step2.name')
                ELSE 'Погашение задолженности по кредитной карте без процентов'
            END
        FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step2.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step2.description')
                ELSE 'Погашайте задолженность по карте в льготный период, а также все расходы совершайте по кредитной карте. На средства, размещенные на вкладе, будут начислены проценты, и за каждую покупку по кредитной карте Вы получите бонусные баллы СПАСИБО.'
            END
        FROM dual))
go

insert into PFP_CR_STEPS (RECOMMENDATION_ID, LIST_INDEX, NAME, DESCRIPTION)
values ((select ID FROM PFP_CARD_RECOMMENDATIONS), 
        3,
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step3.name')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step3.name')
                ELSE 'Погашение задолженности по кредитной карте без процентов'
            END
        FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step3.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step3.description')
                ELSE 'Далее продолжайте действовать таким же образом, и с каждым месяцем у Вас будет увеличиваться доход по вкладу, открытому на сохраненные средства, и количество накопленных «Спасибо».'
            END
        FROM dual))
go

create sequence S_TEMP minvalue 0 increment by 1
go

insert into PFP_CR_CARDS (RECOMMENDATION_ID, LIST_INDEX, CARD_ID)
SELECT (select ID FROM PFP_CARD_RECOMMENDATIONS), S_TEMP.NEXTVAL, ID
FROM PFP_CARDS
WHERE INSTR(','|| (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.cards') || ',', ','|| PFP_CARDS.ID || ',') > 0
go

drop sequence S_TEMP
go

delete from PROPERTIES 
where CATEGORY = 'pfpDictionary.properties' and 
INSTR(PROPERTY_KEY, 'com.rssl.pfp.settings.recommendation.cards.') > 0
go

-- Номер ревизии: 57006
-- Номер версии: 1.18
-- Комментарий: Реализовать хранение справочников ПФП в базе ЦСА Админ
alter table PFP_PRODUCTS add ERIB_PRODUCT_ID integer
go

alter table PFP_PRODUCTS add ERIB_PRODUCT_ADDITIONAL_ID integer
go

update PFP_PRODUCTS p set p.ERIB_PRODUCT_ID = (SELECT d.PRODUCT_ID from DEPOSITDESCRIPTIONS d where d.ID = p.SBOL_PRODUCT_ID) 
where TYPE = 'A' and SBOL_PRODUCT_ID is not null
go

update PFP_PRODUCTS p set p.ERIB_PRODUCT_ID = (SELECT i.TYPE from IMAPRODUCT i where i.ID = p.SBOL_PRODUCT_ID) 
where TYPE = 'M' and SBOL_PRODUCT_ID is not null
go

update PFP_PRODUCTS p set p.ERIB_PRODUCT_ADDITIONAL_ID = (SELECT i.SUBTYPE from IMAPRODUCT i where i.ID = p.SBOL_PRODUCT_ID) 
where TYPE = 'M' and SBOL_PRODUCT_ID is not null
go

alter table PFP_PRODUCTS drop column SBOL_PRODUCT_ID
go


-- Номер ревизии: 57060
-- Номер версии: 1.18
-- Комментарий: Доработать справочники ЦСА Админ для сквозной идентификации между блоками.
alter table PFP_CHANNELS add UUID VARCHAR2(32)
go

create unique index I_PFP_CHANNELS_UUID on PFP_CHANNELS(
    UUID
)
go

update PFP_CHANNELS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_CHANNELS modify UUID not null
go

alter table PFP_CHANNELS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_CHANNELS_DATE on PFP_CHANNELS(
    LAST_UPDATE_DATE
)
go

update PFP_CHANNELS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_CHANNELS modify LAST_UPDATE_DATE not null
go

alter table PFP_INVESTMENT_PERIODS add UUID VARCHAR2(32)
go

create unique index I_PFP_INVESTMENT_PERIODS_UUID on PFP_INVESTMENT_PERIODS(
    UUID
)
go

update PFP_INVESTMENT_PERIODS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_INVESTMENT_PERIODS modify UUID not null
go

alter table PFP_INVESTMENT_PERIODS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_INVESTMENT_PERIODS_DATE on PFP_INVESTMENT_PERIODS(
    LAST_UPDATE_DATE
)
go

update PFP_INVESTMENT_PERIODS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_INVESTMENT_PERIODS modify LAST_UPDATE_DATE not null
go

alter table PFP_CARD_RECOMMENDATIONS add UUID VARCHAR2(32)
go

create unique index I_PFP_CARDRECOMMENDATIONS_UUID on PFP_CARD_RECOMMENDATIONS(
    UUID
)
go

update PFP_CARD_RECOMMENDATIONS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_CARD_RECOMMENDATIONS modify UUID not null
go

alter table PFP_CARD_RECOMMENDATIONS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_CARDRECOMMENDATIONS_DATE on PFP_CARD_RECOMMENDATIONS(
    LAST_UPDATE_DATE
)
go

update PFP_CARD_RECOMMENDATIONS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_CARD_RECOMMENDATIONS modify LAST_UPDATE_DATE not null
go

alter table PFP_CARDS add UUID VARCHAR2(32)
go

create unique index I_PFP_CARDS_UUID on PFP_CARDS(
    UUID
)
go

update PFP_CARDS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_CARDS modify UUID not null
go

alter table PFP_CARDS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_CARDS_DATE on PFP_CARDS(
    LAST_UPDATE_DATE
)
go

update PFP_CARDS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_CARDS modify LAST_UPDATE_DATE not null
go

alter table PFP_COMPLEX_PRODUCTS add UUID VARCHAR2(32)
go

create unique index I_PFP_COMPLEX_PRODUCTS_UUID on PFP_COMPLEX_PRODUCTS(
    UUID
)
go

update PFP_COMPLEX_PRODUCTS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_COMPLEX_PRODUCTS modify UUID not null
go

alter table PFP_COMPLEX_PRODUCTS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_COMPLEX_PRODUCTS_DATE on PFP_COMPLEX_PRODUCTS(
    LAST_UPDATE_DATE
)
go

update PFP_COMPLEX_PRODUCTS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_COMPLEX_PRODUCTS modify LAST_UPDATE_DATE not null
go

alter table PFP_INSURANCE_COMPANIES add UUID VARCHAR2(32)
go

create unique index I_PFP_INSURANCE_COMPANIES_UUID on PFP_INSURANCE_COMPANIES(
    UUID
)
go

update PFP_INSURANCE_COMPANIES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_INSURANCE_COMPANIES modify UUID not null
go

alter table PFP_INSURANCE_COMPANIES add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_INSURANCE_COMPANIES_DATE on PFP_INSURANCE_COMPANIES(
    LAST_UPDATE_DATE
)
go

update PFP_INSURANCE_COMPANIES set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_INSURANCE_COMPANIES modify LAST_UPDATE_DATE not null
go

alter table PFP_INSURANCE_PERIOD_TYPES add UUID VARCHAR2(32)
go

create unique index I_PFP_INS_PERIOD_TYPES_UUID on PFP_INSURANCE_PERIOD_TYPES(
    UUID
)
go

update PFP_INSURANCE_PERIOD_TYPES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_INSURANCE_PERIOD_TYPES modify UUID not null
go

alter table PFP_INSURANCE_PERIOD_TYPES add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_INS_PERIOD_TYPES_DATE on PFP_INSURANCE_PERIOD_TYPES(
    LAST_UPDATE_DATE
)
go

update PFP_INSURANCE_PERIOD_TYPES set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_INSURANCE_PERIOD_TYPES modify LAST_UPDATE_DATE not null
go

alter table PFP_INSURANCE_TYPES add UUID VARCHAR2(32)
go

create unique index I_PFP_INSURANCE_TYPES_UUID on PFP_INSURANCE_TYPES(
    UUID
)
go

update PFP_INSURANCE_TYPES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_INSURANCE_TYPES modify UUID not null
go

alter table PFP_INSURANCE_TYPES add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_INSURANCE_TYPES_DATE on PFP_INSURANCE_TYPES(
    LAST_UPDATE_DATE
)
go

update PFP_INSURANCE_TYPES set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_INSURANCE_TYPES modify LAST_UPDATE_DATE not null
go

alter table PFP_INSURANCE_PRODUCTS add UUID VARCHAR2(32)
go

create unique index I_PFP_INSURANCE_PRODUCTS_UUID on PFP_INSURANCE_PRODUCTS(
    UUID
)
go

update PFP_INSURANCE_PRODUCTS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_INSURANCE_PRODUCTS modify UUID not null
go

alter table PFP_INSURANCE_PRODUCTS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_INSURANCE_PRODUCTS_DATE on PFP_INSURANCE_PRODUCTS(
    LAST_UPDATE_DATE
)
go

update PFP_INSURANCE_PRODUCTS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_INSURANCE_PRODUCTS modify LAST_UPDATE_DATE not null
go

alter table PFP_LOAN_KINDS add UUID VARCHAR2(32)
go

create unique index I_PFP_LOAN_KINDS_UUID on PFP_LOAN_KINDS(
    UUID
)
go

update PFP_LOAN_KINDS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_LOAN_KINDS modify UUID not null
go

alter table PFP_LOAN_KINDS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_LOAN_KINDS_DATE on PFP_LOAN_KINDS(
    LAST_UPDATE_DATE
)
go

update PFP_LOAN_KINDS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_LOAN_KINDS modify LAST_UPDATE_DATE not null
go

alter table PFP_PENSION_FUND add UUID VARCHAR2(32)
go

create unique index I_PFP_PENSION_FUND_UUID on PFP_PENSION_FUND(
    UUID
)
go

update PFP_PENSION_FUND set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_PENSION_FUND modify UUID not null
go

alter table PFP_PENSION_FUND add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_PENSION_FUND_DATE on PFP_PENSION_FUND(
    LAST_UPDATE_DATE
)
go

update PFP_PENSION_FUND set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_PENSION_FUND modify LAST_UPDATE_DATE not null
go

alter table PFP_PENSION_PRODUCT add UUID VARCHAR2(32)
go

create unique index I_PFP_PENSION_PRODUCT_UUID on PFP_PENSION_PRODUCT(
    UUID
)
go

update PFP_PENSION_PRODUCT set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_PENSION_PRODUCT modify UUID not null
go

alter table PFP_PENSION_PRODUCT add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_PENSION_PRODUCT_DATE on PFP_PENSION_PRODUCT(
    LAST_UPDATE_DATE
)
go

update PFP_PENSION_PRODUCT set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_PENSION_PRODUCT modify LAST_UPDATE_DATE not null
go

alter table PFP_PRODUCTS add UUID VARCHAR2(32)
go

create unique index I_PFP_PRODUCTS_UUID on PFP_PRODUCTS(
    UUID
)
go

update PFP_PRODUCTS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_PRODUCTS modify UUID not null
go

alter table PFP_PRODUCTS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_PRODUCTS_DATE on PFP_PRODUCTS(
    LAST_UPDATE_DATE
)
go

update PFP_PRODUCTS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_PRODUCTS modify LAST_UPDATE_DATE not null
go

alter table PFP_PRODUCT_TYPE_PARAMETERS add UUID VARCHAR2(32)
go

create unique index I_PFP_PRODUCT_TYPES_UUID on PFP_PRODUCT_TYPE_PARAMETERS(
    UUID
)
go

update PFP_PRODUCT_TYPE_PARAMETERS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_PRODUCT_TYPE_PARAMETERS modify UUID not null
go

alter table PFP_PRODUCT_TYPE_PARAMETERS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_PRODUCT_TYPES_DATE on PFP_PRODUCT_TYPE_PARAMETERS(
    LAST_UPDATE_DATE
)
go

update PFP_PRODUCT_TYPE_PARAMETERS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_PRODUCT_TYPE_PARAMETERS modify LAST_UPDATE_DATE not null
go

alter table PFP_TABLE_COLUMNS add UUID VARCHAR2(32)
go

create unique index I_PFP_TABLE_COLUMNS_UUID on PFP_TABLE_COLUMNS(
    UUID
)
go

update PFP_TABLE_COLUMNS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_TABLE_COLUMNS modify UUID not null
go

alter table PFP_RISKS add UUID VARCHAR2(32)
go

create unique index I_PFP_RISKS_UUID on PFP_RISKS(
    UUID
)
go

update PFP_RISKS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_RISKS modify UUID not null
go

alter table PFP_RISKS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_RISKS_DATE on PFP_RISKS(
    LAST_UPDATE_DATE
)
go

update PFP_RISKS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_RISKS modify LAST_UPDATE_DATE not null
go

alter table PFP_AGE_CATEGORIES add UUID VARCHAR2(32)
go

create unique index I_PFP_AGE_CATEGORIES_UUID on PFP_AGE_CATEGORIES(
    UUID
)
go

update PFP_AGE_CATEGORIES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_AGE_CATEGORIES modify UUID not null
go

alter table PFP_AGE_CATEGORIES add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_AGE_CATEGORIES_DATE on PFP_AGE_CATEGORIES(
    LAST_UPDATE_DATE
)
go

update PFP_AGE_CATEGORIES set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_AGE_CATEGORIES modify LAST_UPDATE_DATE not null
go

alter table PFP_ANSWERS add UUID VARCHAR2(32)
go

create unique index I_PFP_ANSWERS_UUID on PFP_ANSWERS(
    UUID
)
go

update PFP_ANSWERS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_ANSWERS modify UUID not null
go

alter table PFP_QUESTIONS add UUID VARCHAR2(32)
go

create unique index I_PFP_QUESTIONS_UUID on PFP_QUESTIONS(
    UUID
)
go

update PFP_QUESTIONS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_QUESTIONS modify UUID not null
go

alter table PFP_QUESTIONS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_QUESTIONS_DATE on PFP_QUESTIONS(
    LAST_UPDATE_DATE
)
go

update PFP_QUESTIONS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_QUESTIONS modify LAST_UPDATE_DATE not null
go

alter table PFP_RISK_PROFILES add UUID VARCHAR2(32)
go

create unique index I_PFP_RISK_PROFILES_UUID on PFP_RISK_PROFILES(
    UUID
)
go

update PFP_RISK_PROFILES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_RISK_PROFILES modify UUID not null
go

alter table PFP_RISK_PROFILES add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_RISK_PROFILES_DATE on PFP_RISK_PROFILES(
    LAST_UPDATE_DATE
)
go

update PFP_RISK_PROFILES set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_RISK_PROFILES modify LAST_UPDATE_DATE not null
go

alter table PFP_TARGETS add UUID VARCHAR2(32)
go

create unique index I_PFP_TARGETS_UUID on PFP_TARGETS(
    UUID
)
go

update PFP_TARGETS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PFP_TARGETS modify UUID not null
go

alter table PFP_TARGETS add LAST_UPDATE_DATE TIMESTAMP
go

create index I_PFP_TARGETS_DATE on PFP_TARGETS(
    LAST_UPDATE_DATE
)
go

update PFP_TARGETS set LAST_UPDATE_DATE = SYSDATE
go

alter table PFP_TARGETS modify LAST_UPDATE_DATE not null
go

-- Номер ревизии: 57057
-- Номер версии: 1.18
-- Комментарий: АЛФ. Внесение наличных трат (доработка сущности и модели БД)

alter table CARD_OPERATIONS modify LOAD_DATE null
go

alter table CARD_OPERATIONS modify CARD_NUMBER null
go

alter table CARD_OPERATIONS modify CARD_SUMM null
go

alter table CARD_OPERATIONS add OPERATION_TYPE VARCHAR2(7)
go

update CARD_OPERATIONS set OPERATION_TYPE='BY_CARD'
go

alter table CARD_OPERATIONS modify OPERATION_TYPE not null
go
-- Номер ревизии: 57089
-- Номер версии: 1.18
-- Комментарий: Арм Сотрудника. Справочник условий по кредитным продуктам. (Сущность + Бд  + Сервис.)
create table CREDIT_PRODUCT_CONDITION(
    ID                       INTEGER             not null,
    CREDIT_PRODUCT_ID        INTEGER             not null,
    CREDIT_PRODUCT_TYPE_ID   INTEGER             not null,
    TRANSACT_SM_USE          CHAR(1) default '1' not null,
    MIN_YEAR                 INTEGER,
    MIN_MONTH                INTEGER,
    MAX_YEAR                 INTEGER,
    MAX_MONTH                INTEGER,
    MAX_RANGE_INCLUDE        CHAR(1) default '1' not null,
    USE_INITIAL_FEE          CHAR(1) default '1' not null,
    MIN_INITIAL_FEE          NUMBER(15,4),
    ADDITIONAL_CONDITIONS    VARCHAR2(500),
    constraint PK_CREDIT_PRODUCT_CONDITION primary key (ID)
)
GO
create sequence S_CREDIT_PRODUCT_CONDITION 
go
alter table CREDIT_PRODUCT_CONDITION
   add constraint FK_CPC_TO_CP foreign key (CREDIT_PRODUCT_ID)
      references CREDIT_PRODUCT (ID)
go
alter table CREDIT_PRODUCT_CONDITION
   add constraint FK_CPC_TO_CPT foreign key (CREDIT_PRODUCT_TYPE_ID)
      references CREDIT_PRODUCT_TYPE (ID)
go
create index "DXFK_CPC_TO_CP" on CREDIT_PRODUCT_CONDITION
(
   CREDIT_PRODUCT_ID
)
go
/*==============================================================*/
/* Table: DEP_CRED_PROD_COND                                    */
/*==============================================================*/
create table DEP_CRED_PROD_COND 
(
   DEPARTMENT_ID        INTEGER              not null,
   CRED_PROD_CONDITION_ID INTEGER              not null,
   constraint PK_DEP_CRED_PROD_CONDITION primary key (DEPARTMENT_ID, CRED_PROD_CONDITION_ID)
)

go
create sequence S_DEP_CRED_PROD_COND
go
create index "DXFK_DCPC_TO_CPC" on DEP_CRED_PROD_COND
(
   CRED_PROD_CONDITION_ID
)
go
alter table DEP_CRED_PROD_COND
   add constraint FK_DCPC_TO_CPC foreign key (CRED_PROD_CONDITION_ID)
      references CREDIT_PRODUCT_CONDITION (ID)
      on delete cascade
go
create index "DXFK_DCPC_TO_DEP" on DEP_CRED_PROD_COND
(
   DEPARTMENT_ID
)
go
alter table DEP_CRED_PROD_COND
   add constraint FK_DCPC_TO_DEP foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
      on delete cascade
go
/*==============================================================*/
/* Table: CURR_CRED_PROD_COND                                   */
/*==============================================================*/
create table CURR_CRED_PROD_COND 
(
   ID                   INTEGER              not null,
   CRED_PROD_COND_ID    INTEGER              not null,
   CLIENT_AVALIABLE     CHAR(1)              default '1' not null,
   START_DATE           TIMESTAMP            not null,
   PERCENT_USE          CHAR(1)              default '0' not null,
   CURRENCY             CHAR(3)              not null,
   MIN_LIMIT_AMOUNT     NUMBER(15,4),
   MIN_LIMIT_PERCENT    NUMBER(15,4),
   MAX_LIMIT_AMOUNT     NUMBER(15,4),
   MAX_LIMIT_PERCENT    NUMBER(15,4),
   MAX_LIMIT_INCLUDE    CHAR(1)              default '1' not null,
   MIN_PROCENT_RATE     NUMBER(15,4)         not null,
   MAX_PROCENT_RATE     NUMBER(15,4)         not null,
   constraint PK_CURR_CRED_PROD_COND primary key (ID)
)
go
create sequence S_CURR_CRED_PROD_COND
go
create index "DXFK_CCPC_TO_CPC" on CURR_CRED_PROD_COND
(
   CRED_PROD_COND_ID
)
go
alter table CURR_CRED_PROD_COND
   add constraint FK_CCPC_TO_CPC foreign key (CRED_PROD_COND_ID)
      references CREDIT_PRODUCT_CONDITION (ID)
      on delete cascade
go


-- Номер ревизии: 57113
-- Номер версии: 1.18
-- Комментарий: Удалить старый механизм доступных подразделений

-- Выполнять только после того, как будут перенесены доступные подразделения.
drop table ALLOWED_DEPARTMENT
go


-- Номер ревизии: 57150
-- Номер версии: 1.18
-- Комментарий: Переделать привязку логов к департаментам

alter table SYSTEMLOG drop column DEPARTMENT_ID
go
alter table USERLOG drop column DEPARTMENT_ID
go
alter table CODLOG drop column DEPARTMENT_ID
go


-- Номер ревизии: 57161  

-- Номер версии: 1.18
-- Комментарий: Удалить старый механизм доступных подразделений

alter table EMPLOYEES drop column ALL_DEPARTMENTS
go

-- Номер ревизии: 57170  
-- Номер версии: 1.18
-- Комментарий: Видимость продуктов в СБОЛ

ALTER TABLE ACCOUNT_LINKS
	DROP ( SHOWED_ARCHIVE_MESSAGE ) CASCADE CONSTRAINT 
GO
ALTER TABLE CARD_LINKS
	DROP ( SHOWED_ARCHIVE_MESSAGE ) CASCADE CONSTRAINT 
GO
ALTER TABLE LOAN_LINKS
	DROP ( SHOWED_ARCHIVE_MESSAGE ) CASCADE CONSTRAINT 
GO

-- Номер ревизии: 57178
-- Номер версии: 1.18
-- Комментарий: Оповещения о персональных предложениях(АРМ Сотрудника)

/*==============================================================*/
/* Table: PERSONAL_OFFER_AREAS                                  */
/*==============================================================*/
create table PERSONAL_OFFER_AREAS
(
   ID                   INTEGER              not null,
   PERSONAL_OFFER_ID    INTEGER,
   LIST_INDEX           INTEGER,
   AREA                 VARCHAR2(10)         not null,
   ORDER_INDEX          INTEGER              not null,
   constraint PK_PERSONAL_OFFER_AREAS primary key (ID)
)

go

create sequence S_PERSONAL_OFFER_AREAS
go

/*==============================================================*/
/* Table: PERSONAL_OFFER_BUTTONS                                */
/*==============================================================*/
create table PERSONAL_OFFER_BUTTONS
(
   ID                   INTEGER              not null,
   PERSONAL_OFFER_ID    INTEGER,
   TITLE                VARCHAR2(200),
   URL                  VARCHAR2(256),
   SHOW                 CHAR(1)              not null,
   ORDER_INDEX          INTEGER              not null,
   IMAGE_ID             INTEGER,
   constraint PK_PERSONAL_OFFER_BUTTONS primary key (ID)
)

go

create sequence S_PERSONAL_OFFER_BUTTONS
go

/*==============================================================*/
/* Table: PERSONAL_OFFER_DEPARTMENTS                            */
/*==============================================================*/
create table PERSONAL_OFFER_DEPARTMENTS
(
   PERSONAL_OFFER_ID    INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_PERSONAL_OFFER_DEPARTMENTS primary key (PERSONAL_OFFER_ID, TB)
)

go

create sequence S_PERSONAL_OFFER_DEPARTMENTS
go

/*==============================================================*/
/* Table: PERSONAL_OFFER_NOTIFICATION                           */
/*==============================================================*/
create table PERSONAL_OFFER_NOTIFICATION
(
   ID                   INTEGER              not null,
   STATE                VARCHAR2(16)         not null,
   NAME                 VARCHAR2(100)        not null,
   START_DATE           TIMESTAMP,
   END_DATE             TIMESTAMP,
   TITLE                VARCHAR2(100),
   TEXT                 VARCHAR2(400),
   IMAGE_ID             INTEGER,
   SHOW_TIME            INTEGER,
   ORDER_INDEX          INTEGER,
   PRODUCT_TYPE         VARCHAR2(16)         not null,
   DISPLAY_FREQUENCY    VARCHAR2(16)         not null,
   DISPLAY_FREQUENCY_DAY INTEGER,
   constraint PK_PERSONAL_OFFER_NOTIFICATION primary key (ID)
)

go

create sequence S_PERSONAL_OFFER_NOTIFICATION
go
create index "DXFK_OFFER_AREA_TO_OFFER" on PERSONAL_OFFER_AREAS
(
   PERSONAL_OFFER_ID
)
go

alter table PERSONAL_OFFER_AREAS
   add constraint FK_OFFER_AREA_TO_OFFER foreign key (PERSONAL_OFFER_ID)
      references PERSONAL_OFFER_NOTIFICATION (ID)
      on delete cascade
go


create index "DXFK_OFFER_BUTT_TO_OFFER" on PERSONAL_OFFER_BUTTONS
(
   PERSONAL_OFFER_ID
)
go

alter table PERSONAL_OFFER_BUTTONS
   add constraint FK_OFFER_BUTT_TO_OFFER foreign key (PERSONAL_OFFER_ID)
      references PERSONAL_OFFER_NOTIFICATION (ID)
      on delete cascade
go


create index "DXFK_OFFER_DEP_TO_OFFER" on PERSONAL_OFFER_DEPARTMENTS
(
   PERSONAL_OFFER_ID
)
go

alter table PERSONAL_OFFER_DEPARTMENTS
   add constraint FK_OFFER_DEP_TO_OFFER foreign key (PERSONAL_OFFER_ID)
      references PERSONAL_OFFER_NOTIFICATION (ID)
      on delete cascade
go
-- Номер ревизии: 57180
-- Номер версии: 1.18
-- Комментарий: АРМ Сотрудника.  Справочник условий по кредитным продуктам  (ч .2)

delete from CREDIT_PRODUCT_TYPE
go
delete from CREDIT_PRODUCT_SUB_TYPE
go
delete from CREDIT_PRODUCT
go
delete from CREDIT_PRODUCT_CONDITION
go
alter table CREDIT_PRODUCT_TYPE  modify CODE char(1)
go
alter table CREDIT_PRODUCT_SUB_TYPE modify CODE varchar2(10)
go
alter table CREDIT_PRODUCT modify CODE varchar2(3)
go
alter table CREDIT_PRODUCT modify ENSURING_STATUS char(1) default '1'  
go
alter table CREDIT_PRODUCT_CONDITION add INCLUDE_MAX_INITIAL_FEE char(1) default '1' not null
go
alter table CREDIT_PRODUCT_CONDITION add MAX_INITIAL_FEE NUMBER(15,4)
go
alter table CREDIT_PRODUCT_CONDITION add DEPARTMENTS_STR varchar2(500)
go
alter table CREDIT_PRODUCT_CONDITION add IS_PUBLIC varchar2(11) not null
go
alter table CURR_CRED_PROD_COND modify CURRENCY VARCHAR2(3)
go
drop table DEP_CRED_PROD_COND
go
alter table CURR_CRED_PROD_COND
   add constraint FK_CCRC_TO_CURR foreign key (CURRENCY)
      references CURRENCIES (ID)
      on delete cascade
go

create index "DXFK_CCRC_TO_CURR" on CURR_CRED_PROD_COND
(
   CURRENCY
)
go
alter table CURR_CRED_PROD_COND add MIN_LIMIT_CURRENCY varchar2(3) 
go
alter table CURR_CRED_PROD_COND add MAX_LIMIT_CURRENCY varchar2(3) 
go

-- Номер ревизии: 57182
-- Номер версии: 1.18
-- Комментарий: Внесение наличных трат (создать операцию, экшен, форму, jsp добавления операции клиента)

alter table CARD_OPERATIONS modify LOAD_DATE not null
go


-- Номер ревизии: 57259
-- Номер версии: 1.18
-- Комментарий: Реализовать механизм синхронизации справочников в блоке со справочниками в ЦСА Админ.
create table DICTIONARY_SYNCH_STATE
(
    LAST_UPDATE_ID      INTEGER,
    LAST_UPDATE_DATE    TIMESTAMP,
    STATE               VARCHAR2(9) not null,
    ERROR_COUNT         INTEGER     not null
)
go

-- Номер ревизии: 57302
-- Номер версии: 1.18
-- Комментарий: АРМ Сотрудника.  Справочник условий по кредитным продуктам  (ч .3)

alter table  CURR_CRED_PROD_COND modify MIN_LIMIT_CURRENCY not null
go
alter table  CURR_CRED_PROD_COND modify MIN_LIMIT_AMOUNT not null
go
alter table  CURR_CRED_PROD_COND drop column  MIN_LIMIT_PERCENT   
go
alter table  CURR_CRED_PROD_COND add MAX_PROCENT_RATE_INCLUDE char(1) default '1' not null
go


-- Номер ревизии: 57330
-- Номер версии: 1.18
-- Комментарий: Перевести справочник биллинговых систем на многоблочный режим. (переделана привязка биллинговой системы к адаптеру)

alter table BILLINGS add ADAPTER_UUID VARCHAR2(64)
go

update BILLINGS billing set ADAPTER_UUID = (select UUID from ADAPTERS adapter where adapter.ID = billing.ADAPTER_ID)
go 

alter table BILLINGS modify ADAPTER_UUID not null
go

alter table BILLINGS drop column ADAPTER_ID
go

ALTER INDEX INDEX_CODE_1 RENAME TO I_PAYMENT_SERVICES_CODE
go

ALTER INDEX INDEX_CODE_4 RENAME TO I_BILLINGS_CODE
go 

alter table ADAPTERS add CONSTRAINT UK_ADAPTERS_UUID unique (UUID)
go

create index "DXFK_BILLINGS_TO_ADAPTERS" on BILLINGS
(
   ADAPTER_UUID
)
go

alter table BILLINGS
   add constraint FK_BILLINGS_TO_ADAPTERS foreign key (ADAPTER_UUID)
      references ADAPTERS (UUID)
go

-- Номер ревизии: 57337
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника подразделений. Часть 1. Переделать привязку подразделений к адаптерам.
alter table DEPARTMENTS 
add ADAPTER_UUID varchar2(64)
go

update DEPARTMENTS department 
set department.ADAPTER_UUID = (select adapter.UUID from ADAPTERS adapter where adapter.ID = department.ADAPTER_ID)
go

alter table DEPARTMENTS drop column ADAPTER_ID
go

alter table DEPARTMENTS
   add constraint FK_DEPARTMENTS_TO_ADAPTERS foreign key (ADAPTER_UUID)
      references ADAPTERS (UUID)
go

-- Номер ревизии: 57341
-- Номер версии: 1.18
-- Комментарий: Лимиты в многоблочности : Доработки ЕРИБ

create table DOCUMENT_OPERATIONS_JOURNAL 
(
   EXTERNAL_ID          VARCHAR2(100)        not null,
   DOCUMENT_EXTERNAL_ID VARCHAR2(100)        not null,
   OPERATION_DATE       TIMESTAMP            not null,
   PROFILE_ID           INTEGER              not null,
   AMOUNT               NUMBER(19,4)         not null,
   AMOUNT_CURRENCY      CHAR(3)              not null,
   OPERATION_TYPE       VARCHAR2(20)         not null,
   CHANNEL_TYPE         VARCHAR2(25)         not null,
   LIMITS_INFO          VARCHAR2(4000),
   EXTERNAL_CARD        VARCHAR2(19),
   EXTERNAL_PHONE       VARCHAR2(16),
   constraint PK_DOCUMENT_OPERATIONS_JOURNAL primary key (EXTERNAL_ID)
)
partition by range
 (OPERATION_DATE)
    interval (NUMTODSINTERVAL(7,'DAY'))
 (partition
         P_FIRST
        values less than (to_date('04-01-2014','DD-MM-YYYY')))
go


-- Номер ревизии: 57360
-- Номер версии: 1.18
-- Комментарий: Перевести справочник биллинговых систем на многоблочный режим. (Реализовать хранение справочника в ЦСА Админ.)

alter table BILLINGS add CONSTRAINT UK_BILLINGS_ADAPTER_UUID unique (ADAPTER_UUID)
go

-- Номер ревизии: 57379
-- Номер версии: 1.18
-- Комментарий: Лимиты в многоблочности : Доработки ЕРИБ(ч4 - добавление внешнего идентификатора группе риска)

alter table GROUPS_RISK add EXTERNAL_ID varchar2(35) default SYS_GUID() not null
go

-- Номер ревизии: 57399
-- Номер версии: 1.18
-- Комментарий: Реализовать механизм синхронизации справочников в блоке со справочниками в ЦСА Админ.
alter table PFP_CHANNELS drop column LAST_UPDATE_DATE
go

alter table PFP_INVESTMENT_PERIODS drop column LAST_UPDATE_DATE
go

alter table PFP_CARD_RECOMMENDATIONS drop column LAST_UPDATE_DATE
go

alter table PFP_CARDS drop column LAST_UPDATE_DATE
go

alter table PFP_COMPLEX_PRODUCTS drop column LAST_UPDATE_DATE
go

alter table PFP_INSURANCE_COMPANIES drop column LAST_UPDATE_DATE
go

alter table PFP_INSURANCE_PERIOD_TYPES drop column LAST_UPDATE_DATE
go

alter table PFP_INSURANCE_TYPES drop column LAST_UPDATE_DATE
go

alter table PFP_INSURANCE_PRODUCTS drop column LAST_UPDATE_DATE
go

alter table PFP_LOAN_KINDS drop column LAST_UPDATE_DATE
go

alter table PFP_PENSION_FUND drop column LAST_UPDATE_DATE
go

alter table PFP_PENSION_PRODUCT drop column LAST_UPDATE_DATE
go

alter table PFP_PRODUCTS drop column LAST_UPDATE_DATE
go

alter table PFP_PRODUCT_TYPE_PARAMETERS drop column LAST_UPDATE_DATE
go

alter table PFP_RISKS drop column LAST_UPDATE_DATE
go

alter table PFP_AGE_CATEGORIES drop column LAST_UPDATE_DATE
go

alter table PFP_QUESTIONS drop column LAST_UPDATE_DATE
go

alter table PFP_RISK_PROFILES drop column LAST_UPDATE_DATE
go

alter table PFP_TARGETS drop column LAST_UPDATE_DATE
go


-- Номер ревизии: 57399
-- Номер версии: 1.18
-- Комментарий: Доработать поставщиков услуг для хранения в ЦСА Админ

drop sequence S_SERV_PROVIDER_PAYMENT_SERV
go

drop sequence S_SERVICE_PROVIDER_REGIONS
go

drop sequence S_FIELD_VALUES_DESCR
go

drop sequence S_FIELD_REQUISITE_TYPES
go

-- Номер ревизии: 57412 
-- Номер версии: 1.18
-- Комментарий: Добавление нового дизайна самостоятельной регистрации

create table REGISTRATION_FAILURE_REASONS 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   ADDITION_DATE        TIMESTAMP            not null,
   REASON               VARCHAR(250)         not null,
   constraint PK_REGISTRATION_FAILURE_REASON primary key (ID)
)
go

create sequence S_REGISTRATION_FAILURE_REASONS
go


-- Номер ревизии: 57432
-- Номер версии: 1.18
-- Комментарий: В условия по продукту добавить поля
alter table CREDIT_CARD_PRODUCTS add USE_FOR_PREAPPROVED CHAR(1) default 0 not null;
alter table CREDIT_CARD_PRODUCTS add DEFAULT_FOR_PREAPPROVED CHAR(1) default 0 not null;

-- Номер ревизии: 57450
-- Номер версии: 1.18
-- Комментарий: История изменений профиля в ЕРИБ
create table USER_KEY_HISTORY 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   FIRST_NAME           VARCHAR2(120)        not null,
   SUR_NAME             VARCHAR2(120)        not null,
   PATR_NAME            VARCHAR2(120),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   DATE_CREATED         TIMESTAMP            not null,
   constraint PK_USER_KEY_HISTORY primary key (ID)
)
;

create sequence S_USER_KEY_HISTORY
;

create index "DXUSER_KEY_TO_LOGINS" on USER_KEY_HISTORY
(
   LOGIN_ID
)
;

alter table USER_KEY_HISTORY
   add constraint FK_USER_KEY_TO_LOGINS_REF foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
;


-- Номер ревизии: 57465
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника подразделений. Часть 3. Перевод календарей, справочника подразделений на многоблочный режим.
alter table CALENDARS add UUID VARCHAR2(32)
go

create unique index I_UUID_CALENDARS on CALENDARS(
    UUID
)
go

update CALENDARS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table CALENDARS modify UUID not null
go

-- Номер ревизии: 57478
-- Номер версии: 1.18
-- Комментарий: BUG067425: Ошибка при чтении пустых настроек из properties 
delete from PROPERTIES where PROPERTY_VALUE is null
go
alter table PROPERTIES modify PROPERTY_VALUE not null
go

-- Номер ревизии: 57489 
-- Номер версии: 1.18
-- Комментарий: Вывод ВСП по региону 
update regions reg1
set code_tb = (select code_tb
               from (select A.id, code_tb
                     from (SELECT id, CONNECT_BY_ROOT id as root
                     FROM regions
                     where parent_id is not null
                     START WITH parent_id is null
                     CONNECT BY PRIOR id = parent_id) A join regions B on A.root = B.id) reg2
               where reg2.id = reg1.id
               )
where parent_id is not null
go

alter table regions modify CODE_TB not null


-- Номер ревизии: 57504
-- Номер версии: 1.18
-- Комментарий: Реализовать синхронизацию справочника поставщиков в блоке с ЦСА Админ

alter table FIELD_DESCRIPTIONS add UUID VARCHAR2(32)
go

create unique index I_FIELD_DESCRIPTIONS_UUID on FIELD_DESCRIPTIONS(
    UUID
)
go

update FIELD_DESCRIPTIONS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table FIELD_DESCRIPTIONS modify UUID not null
go


-- Номер ревизии: 57518
-- Номер версии: 1.18
-- Комментарий: Доработка хранения изображений

update IMAGES set EXTEND_IMAGE = replace(EXTEND_IMAGE, 'https://stat.online.sberbank.ru/phizic-res') 
where EXTEND_IMAGE IS NOT NULL
go


-- Номер ревизии: 57540
-- Номер версии: 1.18
-- Комментарий: Лимиты в многоблочности : Доработки ЕРИБ(ч5) 

alter table DOCUMENT_OPERATIONS_JOURNAL drop constraint PK_DOCUMENT_OPERATIONS_JOURNAL
go

create index DOC_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   DOCUMENT_EXTERNAL_ID ASC,
   OPERATION_DATE ASC
)
local
go

create index PROFILE_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   OPERATION_DATE ASC,
   PROFILE_ID ASC,
   CHANNEL_TYPE ASC
)
local
go

create index PHONE_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   OPERATION_DATE ASC,
   CHANNEL_TYPE ASC,
   EXTERNAL_PHONE ASC
)
local
go

create index CARD_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   OPERATION_DATE ASC,
   CHANNEL_TYPE ASC,
   EXTERNAL_CARD ASC
)
local
go


-- Номер ревизии: 57540
-- Номер версии: 1.18
-- Комментарий: Лимиты в многоблочности : Доработки ЕРИБ(ч5) 

drop index PROFILE_CHANNEL_OPER_DATE_IDX 
go

drop index PHONE_CHANNEL_OPER_DATE_IDX 
go

drop index CARD_CHANNEL_OPER_DATE_IDX 
go

create index PROFILE_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   PROFILE_ID ASC,
   CHANNEL_TYPE ASC,
   OPERATION_DATE ASC
)
local
go

create index PHONE_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   EXTERNAL_PHONE ASC,
   CHANNEL_TYPE ASC,
   OPERATION_DATE ASC
)
local
go

create index CARD_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   EXTERNAL_CARD ASC,
   CHANNEL_TYPE ASC,
   OPERATION_DATE ASC
)
local
go

-- Номер ревизии: 57555
-- Номер версии: 1.18
-- Комментарий: Реализовать синхронизацию справочника поставщиков в блоке с ЦСА Админ 
alter table PROVIDER_SMS_ALIAS add UUID VARCHAR2(32)
go

create unique index I_PROVIDER_SMS_ALIAS_UUID on PROVIDER_SMS_ALIAS(
    UUID
)
go

update PROVIDER_SMS_ALIAS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table PROVIDER_SMS_ALIAS modify UUID not null
go

-- Номер ревизии: 57558
-- Номер версии: 1.18
-- Комментарий: BUG067297: Необходимо добавить индекс.
create index I_PFP_FIELDS_TO_PRODUCT on PFP_PRODUCT_FIELDS
(
    BASE_PRODUCT_ID ASC
)
go
-- Номер ревизии: 57574
-- Номер версии: 1.18
-- Комментарий: Удаление Deparment из типа суб продукта, и замена его на String(код тб)
delete CREDIT_PRODUCT_SUB_TYPE
go
alter table CREDIT_PRODUCT_SUB_TYPE
drop column DEPARTMENT_ID
go
alter table CREDIT_PRODUCT_SUB_TYPE
add  TB VARCHAR2(3) not null
go

-- Номер ревизии: 57581
-- Номер версии: 1.18
-- Комментарий: убрать parent из подразделений (11 - из индекса БД)
drop index I_DEPARTMENTS_TBS 
go
create index I_DEPARTMENTS_TBS on DEPARTMENTS (
   DECODE(OFFICE||OSB,NULL,TB,NULL) ASC
)
go


-- Номер ревизии: 57610
-- Номер версии: 1.18
-- Комментарий: АЛФ: переименование столбца в таблице CARD_OPERATIONS

alter table CARD_OPERATIONS rename column CASH to IO_CASH
go

-- Номер ревизии:57615  
-- Номер версии: 1.18
-- Комментарий: Переделать привязку технологических перерывов к внешним системам.

alter table TECHNOBREAKS add ADAPTER_UUID varchar2(64)
go

update TECHNOBREAKS t set ADAPTER_UUID = (select a.UUID from ADAPTERS a where a.ID = t.ADAPTER_ID)
go

alter table TECHNOBREAKS modify ADAPTER_UUID not null
go

alter table TECHNOBREAKS drop column ADAPTER_ID
go

create index "DXFK_TECHNOBREAKS_TO_ADAPTERS" on TECHNOBREAKS
(
   ADAPTER_UUID
)
go

alter table TECHNOBREAKS
   add constraint FK_TECHNOBREAKS_TO_ADAPTERS foreign key (ADAPTER_UUID)
      references ADAPTERS (UUID)
go


-- Номер ревизии:57620  
-- Номер версии: 1.18
-- Комментарий: АЛФ: Добавлен столбец в индекс

create index I_CARDOP_LCO on CARD_OPERATIONS (
   LOGIN_ID ASC,
   CARD_NUMBER ASC,
   OPERATION_DATE ASC,
   OPERATION_TYPE ASC
)
local
go


-- Номер ревизии: 57630 
-- Номер версии: 1.18
-- Комментарий: Доработка отображения сумм комиссий

/*==============================================================*/
/* Table: COMMISSIONS_SETTINGS                                  */
/*==============================================================*/
create table COMMISSIONS_SETTINGS 
(
   ID                   INTEGER              not null,
   DEPARTMENT_ID        INTEGER              not null,
   PAYMENT_TYPE         VARCHAR2(200)        not null,
   SHOW_COMMISSION      CHAR(1)              not null,
   constraint PK_COMMISSIONS_SETTINGS primary key (ID)
)

go

create sequence S_COMMISSIONS_SETTINGS
go

/*==============================================================*/
/* Index: CS_DPTMNT_PAYTYPE                                     */
/*==============================================================*/
create index CS_DPTMNT_PAYTYPE on COMMISSIONS_SETTINGS (
   DEPARTMENT_ID ASC,
   PAYMENT_TYPE ASC
)
go

/*==============================================================*/
/* Table: WRITE_DOWN_OPERATIONS                                 */
/*==============================================================*/
create table WRITE_DOWN_OPERATIONS 
(
   ID                   INTEGER              not null,
   PAYMENT_ID           INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   CUR_AMOUNT           NUMBER(19,4)         not null,
   CUR_AMOUNT_CURRENCY  CHAR(3)              not null,
   OPERATION_NAME       VARCHAR2(255)        not null,
   TURNOVER             VARCHAR2(20)         not null,
   constraint PK_WRITE_DOWN_OPERATIONS primary key (ID)
)

go

create sequence S_WRITE_DOWN_OPERATIONS
go

create index "DXFK_WDOPERATIONS_TO_PAYMENT" on WRITE_DOWN_OPERATIONS
(
   PAYMENT_ID
)
go

alter table WRITE_DOWN_OPERATIONS
   add constraint FK_WDOPERATIONS_TO_PAYMENT foreign key (PAYMENT_ID)
      references BUSINESS_DOCUMENTS (ID)
go


-- Номер ревизии: 57665
-- Номер версии: 1.18
-- Комментарий: BUG067810: Сертификаты с большим номиналом
alter table STORED_SECURITY_ACCOUNT modify NOMINAL_AMOUNT NUMBER(18,4)
go
alter table STORED_SECURITY_ACCOUNT modify INCOME_AMOUNT NUMBER(18,4)
go



-- Номер ревизии: 57668
-- Номер версии: 1.18
-- Комментарий: Доработать справочник техперерывов для редактирования в многоблочном режиме.
-- Реализовать синхронизацию справочника техперерывов в блоке с цсаАдмин

alter table TECHNOBREAKS add UUID varchar(32) default SYS_GUID() not null
go
alter table TECHNOBREAKS add constraint AK_UK_TECHNOBREAKS unique(UUID)
go

-- Номер ревизии: 57677
-- Номер версии: 1.18
-- Комментарий: MNP. Хранение настроек и проверка поставщиков

/*==============================================================*/
/* Table: MOBILE_PROVIDER_CODES                                 */
/*==============================================================*/
create table MOBILE_PROVIDER_CODES 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(20)         not null,
   NAME                 VARCHAR2(160)        not null,
   EXTERNAL_ID          VARCHAR2(128)        not null,
   constraint PK_MOBILE_PROVIDER_CODES primary key (ID)
)
go
create sequence S_MOBILE_PROVIDER_CODES
go

-- Номер ревизии:  57706
-- Номер версии: 1.18
-- Комментарий: Оповещения о персональных предложениях(ч.3)
/*==============================================================*/
/* Table: PERSONAL_OFFER_DISPLAY_DATE                           */
/*==============================================================*/
create table PERSONAL_OFFER_DISPLAY_DATE
(
   PERSONAL_OFFER_ID    INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   DISPLAY_DATE         TIMESTAMP            not null,
   constraint PK_PERSONAL_OFFER_DISPLAY_DATE primary key (PERSONAL_OFFER_ID, LOGIN_ID)
)

go
create index "DXFK_OFFER_TO_DATE" on PERSONAL_OFFER_DISPLAY_DATE
(
   PERSONAL_OFFER_ID
)
go

alter table PERSONAL_OFFER_DISPLAY_DATE
   add constraint FK_OFFER_TO_DATE foreign key (PERSONAL_OFFER_ID)
      references PERSONAL_OFFER_NOTIFICATION (ID)
      on delete cascade
go


create index "DXFK_OFFER_TO_LOGINS" on PERSONAL_OFFER_DISPLAY_DATE
(
   LOGIN_ID
)
go

alter table PERSONAL_OFFER_DISPLAY_DATE
   add constraint FK_OFFER_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
go

-- Номер ревизии: 57725 
-- Номер версии: 1.18
-- Комментарий: Индекс для таблицы MOBILE_PROVIDER_CODES 
/*==============================================================*/
/* Index: MOBILE_PROVIDER_CODES_EXT_ID                          */
/*==============================================================*/
create unique index MOBILE_PROVIDER_CODES_EXT_ID on MOBILE_PROVIDER_CODES (
   EXTERNAL_ID ASC
)
go


-- Номер ревизии: 57743
-- Номер версии: 1.18
-- Комментарий: Перенос уровня безопасности в ЦСА. часть 2

alter table USERS drop column TRUSTED
go 

-- Номер ревизии: 57760
-- Номер версии: 1.18
-- Комментарий: убрать parent из подразделений (19 - модель БД)
alter table DEPARTMENTS drop column PARENT_DEPARTMENT cascade constraint
go



-- Номер ревизии: 57765
-- Номер версии: 1.18
-- Комментарий: АРМ Сотрудника. Справочник подразделений ВСП (галка "возможность кредитования физ. лиц") 

alter table DEPARTMENTS add POSSIBLE_LOANS_OPERATION char(1)default '0' not null 
go


-- Номер ревизии: 57786
-- Номер версии: 1.18
-- Комментарий:  Оповещения о персональных предложениях (статистика)
create table OFFER_NOTIFICATIONS_LOG
(
   ID                   INTEGER              not null,
   NOTIFICATION_ID      INTEGER              not null,
   LOGIN_ID             INTEGER,
   DISPLAY_DATE         TIMESTAMP            not null,
   TYPE                 VARCHAR2(12)         not null,
   constraint PK_OFFER_NOTIFICATIONS_LOG primary key (ID)
)
go

create sequence S_OFFER_NOTIFICATIONS_LOG
go

-- Номер ревизии: 57814
-- Номер версии: 1.18
-- Комментарий: Подтверждение паролем в mAPI

alter table MOBILE_PLATFORMS add (IS_PASSWORD_CONFIRM char(1) default 0 not null)
go

-- Номер ревизии: 57842
-- Номер версии: 1.18
-- Комментарий: Оповещения о персональных предложениях(ч.6)
create index I_OFFER_NOTIF_LOG on OFFER_NOTIFICATIONS_LOG (
   DISPLAY_DATE ASC
)
go
create index "DXFK_OFFER_NOTIF_LOG" on OFFER_NOTIFICATIONS_LOG
(
   NOTIFICATION_ID
)
go
alter table OFFER_NOTIFICATIONS_LOG
   add constraint FK_OFFER_NO_FK_OFFER__PERSONAL foreign key (NOTIFICATION_ID)
      references PERSONAL_OFFER_NOTIFICATION (ID)
      on delete cascade
go


-- Номер ревизии: 57856
-- Номер версии: 1.18
-- Комментарий: Доработать аудит для возможности смены блока сотрудниками КЦ
delete FROM FILTER_PARAMETERS_FIELDS WHERE FILTER_ID IN (SELECT ID FROM FILTER_PARAMETERS WHERE TYPE = 'K')
go

delete FROM FILTER_PARAMETERS WHERE TYPE = 'K'
go

alter table FILTER_PARAMETERS drop column TYPE
go

alter table FILTER_PARAMETERS drop column ADDITIONAL_KEY
go

-- Номер ревизии: 57966
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника подразделений. Фоновая загрузка справочника подразделений.
delete from DEPARTMENTS_REPLICA_TASKS
go

drop index DXFK_DEPARTMENTS_REPL_LOGINS 
go

alter table DEPARTMENTS_REPLICA_TASKS 
drop column LOGIN_ID
go

alter table DEPARTMENTS_REPLICA_TASKS 
add OWNER_ID VARCHAR2(25) not null
go

alter table DEPARTMENTS_REPLICA_TASKS 
add OWNER_FIO VARCHAR2(256) not null
go

-- Номер ревизии: 57977
-- Номер версии: 1.18
-- Комментарий: ENH067661: Убрать Использовать настройку вышестоящего из подразделений. 
alter table DEPARTMENTS drop
    (USE_PARENT_TIME_SETTINGS, 
    USE_PARENT_CONNECTION_CHARGE, 
    USE_PARENT_MONTHLY_CHARGE, 
    USE_PARENT_TIME_ZONE, 
    USE_PARENT_ESB_SUPPORTED, 
    USE_PARENT_MDM_SUPPORTED)
go


-- Номер ревизии: 57540
-- Номер версии: 1.18
-- Комментарий: Лимиты в многоблочности : Доработки ЕРИБ(ч5) 
DECLARE
TYPE docType IS RECORD (operDate BUSINESS_DOCUMENTS.OPERATION_DATE%TYPE, operUID BUSINESS_DOCUMENTS.OPERATION_UID%TYPE, loginId BUSINESS_DOCUMENTS.LOGIN_ID%TYPE);
doc                 docType;
limits_info         VARCHAR2(4000);
groupRiskExId       VARCHAR2(35);
personId            INTEGER;
amount              NUMBER(19,4);
currency            CHAR(3);
channel             VARCHAR2(25);
externalCard        VARCHAR2(19);
externalPhone       VARCHAR2(16);
BEGIN
FOR documentId IN (select DOCUMENT_ID from USERS_LIMITS_JOURNAL where STATE = 'ALLOWED' and CREATION_DATE > SYSDATE - 1 group by DOCUMENT_ID) LOOP 
    select OPERATION_DATE, OPERATION_UID, LOGIN_ID into doc from BUSINESS_DOCUMENTS where documentId.DOCUMENT_ID = ID;
    select ID into personId from USERS where doc.loginId = LOGIN_ID;
    -- формируем информацию по лимитам для документа
    FOR record IN (select * from USERS_LIMITS_JOURNAL where documentId.DOCUMENT_ID = DOCUMENT_ID) LOOP 
        -- учитываем лимиты внешнему получателю на карту
        IF(record.EXTERNAL_CARD is not NULL) THEN
            externalCard := record.EXTERNAL_CARD;
        END IF;
        -- учитываем лимиты внешнему получателю на телефон
        IF(record.EXTERNAL_PHONE is not NULL) THEN
            externalPhone := record.EXTERNAL_PHONE;            
        END IF;
        -- собираем информацию по сработавшим лимитам
        limits_info := limits_info || record.LIMIT_TYPE || ',' || record.RESTRICTION_TYPE || ',';
        IF(record.GROUP_RISK_ID is not NULL) THEN
            select EXTERNAL_ID into groupRiskExId from GROUPS_RISK where ID = record.GROUP_RISK_ID;  
            limits_info := limits_info || groupRiskExId; 
        END IF;    
        limits_info := limits_info || ';';
        -- не усложняем скрипт лишними ифами для значений из документа, просто переприсваиваем одни и те же значения
        channel := record.CHANNEL_TYPE;     
        amount := record.AMOUNT;            
        currency := record.AMOUNT_CURRENCY; 
    END LOOP;
    -- теперь инсерт в новую таблицу
    insert into DOCUMENT_OPERATIONS_JOURNAL(EXTERNAL_ID, DOCUMENT_EXTERNAL_ID, OPERATION_DATE, PROFILE_ID, AMOUNT, AMOUNT_CURRENCY, OPERATION_TYPE, CHANNEL_TYPE, LIMITS_INFO, EXTERNAL_CARD, EXTERNAL_PHONE)
    values (SYS_GUID, doc.operUID, doc.operDate, personId, amount, currency, 'commit', channel, limits_info, externalCard, externalPhone);
    -- сбрасываем чтобы не учлись в слеюущих итерациях
    limits_info := null;
    externalPhone := null;
    externalCard := null;  
    groupRiskExId := null;
    channel := null;     
    amount := null;            
    currency := null;
END LOOP;
END;
go

drop table USERS_LIMITS_JOURNAL
go

-- Номер ревизии: 58022
-- Номер версии: 1.18
-- Комментарий: CHG068089: Добавить возможность задавать индивидуальный режим саморегистрации  
alter table USERS add (USER_REGISTRATION_MODE varchar2(10))
go


-- Номер ревизии: 58035
-- Номер версии: 1.18
-- Комментарий: Доработать механизм фоновой репликации поставщиков услуг.
delete from PROVIDER_REPLICA_TASKS
go

drop index "DXPROV_REPLICA_TASKS_LOGINS" 
go

alter table PROVIDER_REPLICA_TASKS 
drop column LOGIN_ID
go

alter table PROVIDER_REPLICA_TASKS 
add OWNER_ID VARCHAR2(25) not null
go

alter table PROVIDER_REPLICA_TASKS 
add OWNER_FIO VARCHAR2(256) not null
go


-- Номер ревизии: 58049 
-- Номер версии: 1.18
-- Комментарий: Доработка отображения сумм комиссий(перенос микроопераций в доп. поля платежа)

drop index CS_DPTMNT_PAYTYPE;
alter table COMMISSIONS_SETTINGS drop column DEPARTMENT_ID;
alter table COMMISSIONS_SETTINGS add (TB VARCHAR2(4) not null);
create index CS_TB_PAYTYPE on COMMISSIONS_SETTINGS (
   TB ASC,
   PAYMENT_TYPE ASC
);
drop table WRITE_DOWN_OPERATIONS;
drop sequence S_WRITE_DOWN_OPERATIONS;


-- Номер ревизии: 58059
-- Номер версии: 1.18
-- Комментарий: Доработка формата загрузки пред одобренных предложений.  
alter table LOAN_OFFER add END_DATE timestamp
go

-- Номер ревизии: 58119
-- Номер версии: 1.18
-- Комментарий: CHG068368: Выбор ВСП при получении предодобренной кредитной карты.  
alter table regions modify CODE_TB null 
go

-- Номер ревизии: 58144
-- Номер версии: 1.18
-- Комментарий: CHG062817: Не подменять уровень безопасности клиента 
alter table USERS add (STORE_SECURITY_TYPE varchar2(8))
go

-- Номер ревизии: 58201 
-- Номер версии: 1.18
-- Комментарий: Реализовать синхронизацию справочника запрещенных счетов

alter table BANNED_ACCOUNTS add UUID varchar(32) default SYS_GUID() not null
go
alter table BANNED_ACCOUNTS add constraint AK_UK_BANNED_ACCOUNTS unique(UUID)
go

-- Номер ревизии: 58237
-- Номер версии: 1.18
-- Комментарий: Таблицы для EInvoicing

/*==============================================================*/
/* Table: SHOP_NOTIFICATIONS                                    */
/*==============================================================*/
create table SHOP_NOTIFICATIONS 
(
   "DATE"               TIMESTAMP            not null,
   UUID                 VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   UTRRNO               VARCHAR2(99),
   STATE                VARCHAR2(30)         not null,
   NOTIF_STATUS         VARCHAR2(30)         not null,
   NOTIF_DATE           TIMESTAMP,
   NOTIF_COUNT          INTEGER              not null,
   NOTIF_STATUS_DESCRIPTION VARCHAR2(255)
)
partition by range
 ("DATE")
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST_SN
        values less than (to_date('01-01-2012','DD-MM-YYYY')))
go

create sequence S_SHOP_NOTIFICATIONS
go

/*==============================================================*/
/* Index: IDX_SHOP_NOTIF_STATE_DATE                             */
/*==============================================================*/
create index IDX_SHOP_NOTIF_STATE_DATE on SHOP_NOTIFICATIONS (
   STATE ASC,
   "DATE" ASC
)
local
go

/*==============================================================*/
/* Table: SHOP_ORDERS                                           */
/*==============================================================*/
create table SHOP_ORDERS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   TYPE                 CHAR(1)              not null,
   ORDER_DATE           TIMESTAMP            not null,
   STATE                VARCHAR2(20)         not null,
   PROFILE_ID           INTEGER,
   PHONE                VARCHAR2(10),
   RECEIVER_CODE        VARCHAR2(32)         not null,
   RECEIVER_NAME        VARCHAR2(160),
   AMOUNT               NUMBER(15,5),
   CURRENCY             CHAR(3),
   DESCRIPTION          VARCHAR2(255),
   RECEIVER_ACCOUNT     VARCHAR2(24),
   BIC                  VARCHAR2(9),
   CORR_ACCOUNT         VARCHAR2(35),
   INN                  VARCHAR2(12),
   KPP                  VARCHAR2(9),
   PRINT_DESCRIPTION    VARCHAR2(200),
   BACK_URL             VARCHAR2(1000),
   NODE_ID              INTEGER,
   UTRRNO               VARCHAR2(99),
   DETAIL_INFO          CLOB,
   TICKETS_INFO         CLOB,
   KIND                 VARCHAR2(20)         not null
)
partition by range
 (ORDER_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST_SO
        values less than (to_date('01-01-2012','DD-MM-YYYY')))
go

create sequence S_SHOP_ORDERS
go

/*==============================================================*/
/* Index: IDX_SHOP_ORDERS_ID                                    */
/*==============================================================*/
create index IDX_SHOP_ORDERS_ID on SHOP_ORDERS (
   ID ASC
)
local
go

/*==============================================================*/
/* Index: IDX_SH_ORD_EXT_ID_REC_CODE                            */
/*==============================================================*/
create unique index IDX_SH_ORD_EXT_ID_REC_CODE on SHOP_ORDERS (
   EXTERNAL_ID ASC,
   RECEIVER_CODE ASC
)
global
go

/*==============================================================*/
/* Index: IDX_SH_ORD_PROF_ID_DATE                               */
/*==============================================================*/
create index IDX_SH_ORD_PROF_ID_DATE on SHOP_ORDERS (
   PROFILE_ID ASC,
   ORDER_DATE DESC
)
local
go

/*==============================================================*/
/* Index: IDX_SHOP_ORD_UUID                                     */
/*==============================================================*/
create unique index IDX_SHOP_ORD_UUID on SHOP_ORDERS (
   UUID ASC
)
global
go

/*==============================================================*/
/* Table: SHOP_PROFILES                                         */
/*==============================================================*/
create table SHOP_PROFILES 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_SHOP_PROFILES primary key (ID)
)

go

create sequence S_SHOP_PROFILES
go

/*==============================================================*/
/* Index: IDX_SHOP_PROFILE_UNIQ                                 */
/*==============================================================*/
create unique index IDX_SHOP_PROFILE_UNIQ on SHOP_PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT, ' ', '') ASC
)
go

/*==============================================================*/
/* Table: SHOP_RECALLS                                          */
/*==============================================================*/
create table SHOP_RECALLS 
(
   UUID                 VARCHAR2(32)         not null,
   RECEIVER_CODE        VARCHAR2(32)         not null,
   "DATE"               TIMESTAMP            not null,
   ORDER_UUID           VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(255)        not null,
   UTRRNO               VARCHAR2(99),
   STATE                VARCHAR2(20)         not null,
   constraint PK_SHOP_RECALLS primary key (UUID)
)
partition by range
 ("DATE")
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST_SR
        values less than (to_date('01-01-2012','DD-MM-YYYY')))
go

create sequence S_SHOP_RECALLS
go

create index "DXREFERENCE_8" on SHOP_ORDERS
(
   PROFILE_ID
)
go

alter table SHOP_ORDERS
   add constraint FK_SHOP_ORD_REFERENCE_SHOP_PRO foreign key (PROFILE_ID)
      references SHOP_PROFILES (ID)
go

-- Номер ревизии: 58253
-- Номер версии: 1.18
-- Комментарий: CHG068388: Поменять маппинг <many-to-one name="owner" class="com.rssl.phizic.auth.CommonLogin" в АЛФ(часть 1 поменял маппинг в CardOperations + обработка null в operationType)
drop index I_CARDOP_LCO 
go

create index I_CARDOP_LCO on CARD_OPERATIONS (
   LOGIN_ID ASC,   
   OPERATION_DATE ASC   
)
local
go

alter table CARD_OPERATIONS modify OPERATION_TYPE null
go

-- Номер ревизии: 58270
-- Номер версии: 1.18
-- Комментарий: Сохранение результатов загрузки предодобренных предложений.

create table LOAN_OFFER_LOAD_RESULTS 
(
   ID                   INTEGER              not null,
   ALL_COUNT            INTEGER              not null,
   LOAD_COUNT           INTEGER              not null,
   LOAD_OFFER_ERRORS    CLOB,
   LOAD_COMMON_ERRORS   CLOB,
   constraint PK_LOAN_OFFER_LOAD_RESULTS primary key (ID)
)
go

create sequence S_LOAN_OFFER_LOAD_RESULTS
go

-- Номер ревизии: 58278
-- Номер версии: 1.18
-- Комментарий: Изменение реализации справочников TSM

 -- Справочник «Категория занимаемой должности»
CREATE TABLE LOANCLAIM_CATEGORY_OF_POSITION
(
  CODE     VARCHAR2(20 BYTE)                    NOT NULL,
  NAME     VARCHAR2(100 BYTE)                   NOT NULL,
  MAX_AGE  INTEGER                              NOT NULL
);
ALTER TABLE LOANCLAIM_CATEGORY_OF_POSITION ADD (
  CONSTRAINT LC_CATEGORY_OF_POSITION_PK
 PRIMARY KEY
 (CODE));

 -- Справочник «Образование»
CREATE TABLE LOANCLAIM_EDUCATION
(
  CODE     VARCHAR2(20 BYTE)                    NOT NULL,
  NAME     VARCHAR2(100 BYTE)                   NOT NULL
);
ALTER TABLE LOANCLAIM_EDUCATION ADD (
  CONSTRAINT LC_EDUCATION_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Родственная связь»
 CREATE TABLE LOANCLAIM_FAMILY_RELATION
(
  CODE      VARCHAR2(20 BYTE)                   NOT NULL,
  NAME      VARCHAR2(100 BYTE)                  NOT NULL,
  IS_CHILD  CHAR(1 BYTE)                        NOT NULL
);
ALTER TABLE LOANCLAIM_FAMILY_RELATION ADD (
  CONSTRAINT LC_FAMILY_RELATION_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Семейное положение»
 CREATE TABLE LOANCLAIM_FAMILY_STATUS
(
  CODE                  VARCHAR2(20 BYTE)       NOT NULL,
  NAME                  VARCHAR2(100 BYTE)      NOT NULL,
  SPOUSE_INFO_REQUIRED  CHAR(1 BYTE)            NOT NULL
);
ALTER TABLE LOANCLAIM_FAMILY_STATUS ADD (
  CONSTRAINT LC_FAMILY_STATUS_PK
 PRIMARY KEY
 (CODE));
   
 --Справочник кодов организационно - правовых форм
 CREATE TABLE LOANCLAIM_INCORPORATION_FORM
(
  CODE        VARCHAR2(20 BYTE)                 NOT NULL,
  NAME        VARCHAR2(100 BYTE)                NOT NULL,
  SHORT_NAME  VARCHAR2(50 BYTE)                 NOT NULL
);
ALTER TABLE LOANCLAIM_INCORPORATION_FORM ADD (
  CONSTRAINT LC_INCORPORATION_FORM_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Стаж на текущем месте работы»
 CREATE TABLE LOANCLAIM_JOB_EXPERIENCE
(
  CODE              VARCHAR2(20 BYTE)           NOT NULL,
  NAME              VARCHAR2(100 BYTE)          NOT NULL,
  LOAN_NOT_ALLOWED  CHAR(1 BYTE)                NOT NULL
);
ALTER TABLE LOANCLAIM_JOB_EXPERIENCE ADD (
  CONSTRAINT LC_JOB_EXPERIENCE_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Вид деятельности компании»
 CREATE TABLE LOANCLAIM_KIND_OF_ACTIVITY
(
  CODE           VARCHAR2(20 BYTE)              NOT NULL,
  NAME           VARCHAR2(100 BYTE)             NOT NULL,
  DESC_REQUIRED  CHAR(1 BYTE)                   NOT NULL
);
ALTER TABLE LOANCLAIM_KIND_OF_ACTIVITY ADD (
  CONSTRAINT LC_KIND_OF_ACTIVITY_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Способ погашения кредита»
 CREATE TABLE LOANCLAIM_PAYMENT_METHOD
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_PAYMENT_METHOD ADD (
  CONSTRAINT LC_PAYMENT_METHOD_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Периодичность погашения кредита»
 CREATE TABLE LOANCLAIM_PAYMENT_PERIOD
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_PAYMENT_PERIOD ADD (
  CONSTRAINT LC_PAYMENT_PERIOD_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Приблизительное количество сотрудников в компании»
 CREATE TABLE LOANCLAIM_NUMBER_OF_EMPLOYEES
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_NUMBER_OF_EMPLOYEES ADD (
  CONSTRAINT LC_NUMBER_OF_EMPLOYEES_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Регионы»
 CREATE TABLE LOANCLAIM_REGION
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_REGION ADD (
  CONSTRAINT LC_REGION_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Вид собственности жилья»
 CREATE TABLE LOANCLAIM_RESIDENCE_RIGHT
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_RESIDENCE_RIGHT ADD (
  CONSTRAINT LC_RESIDENCE_RIGHT_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Типы районов/округов»
 CREATE TABLE LOANCLAIM_TYPE_OF_AREA
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_TYPE_OF_AREA ADD (
  CONSTRAINT LC_TYPE_OF_AREA_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Типы городов»
 CREATE TABLE LOANCLAIM_TYPE_OF_CITY
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_TYPE_OF_CITY ADD (
  CONSTRAINT LC_TYPE_OF_CITY_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Типы обязательств»
 CREATE TABLE LOANCLAIM_TYPE_OF_DEBIT
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_TYPE_OF_DEBIT ADD (
  CONSTRAINT LC_TYPE_OF_DEBIT_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Типы населенных пунктов»
 CREATE TABLE LOANCLAIM_TYPE_OF_LOCALITY
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_TYPE_OF_LOCALITY ADD (
  CONSTRAINT LC_TYPE_OF_LOCALITY_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Вид недвижимости в собственности»
 CREATE TABLE LOANCLAIM_TYPE_OF_REALTY
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_TYPE_OF_REALTY ADD (
  CONSTRAINT LC_TYPE_OF_REALTY_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Типы улиц»
 CREATE TABLE LOANCLAIM_TYPE_OF_STREET
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_TYPE_OF_STREET ADD (
  CONSTRAINT LC_TYPE_OF_STREET_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Вид транспортного средства в собственности»
 CREATE TABLE LOANCLAIM_TYPE_OF_VEHICLE
(
  CODE  VARCHAR2(20 BYTE)                       NOT NULL,
  NAME  VARCHAR2(100 BYTE)                      NOT NULL
);
ALTER TABLE LOANCLAIM_TYPE_OF_VEHICLE ADD (
  CONSTRAINT LC_TYPE_OF_VEHICLE_PK
 PRIMARY KEY
 (CODE));
 
 -- Справочник «Работа по трудовому договору»
 CREATE TABLE LOANCLAIM_WORK_ON_CONTRACT
(
  CODE                    VARCHAR2(20 BYTE)     NOT NULL,
  NAME                    VARCHAR2(100 BYTE)    NOT NULL,
  FULL_NAME_REQUIRED      CHAR(1)               NOT NULL,
  INN_REQUIRED            CHAR(1)               NOT NULL,
  BUSINESS_DESC_REQUIRED  CHAR(1)               NOT NULL,
  UNEMPLOYED              CHAR(1)               NOT NULL
);
ALTER TABLE LOANCLAIM_WORK_ON_CONTRACT ADD (
  CONSTRAINT LC_WORK_ON_CONTRACT_PK
 PRIMARY KEY
 (CODE));

-- Справочник "Соответствие подразделений ЦАС НСИ и системы ЕТСМ"
 CREATE TABLE CASNSI_TO_ETSM_DEP
(
  ID          INTEGER,
  TB_CASNSI   VARCHAR2(12 BYTE)                 NOT NULL,
  TB_ETSM     VARCHAR2(12 BYTE)                 NOT NULL,
  OSB_CASNSI  VARCHAR2(12 BYTE)                 NOT NULL,
  OSB_ETSM    VARCHAR2(12 BYTE)                 NOT NULL
);
ALTER TABLE CASNSI_TO_ETSM_DEP ADD (
  CONSTRAINT CASNSI_TO_ETSM_DEP_PK
 PRIMARY KEY
 (ID)); 
create sequence S_CASNSI_TO_ETSM_DEP;

-- Заполнение справочников

insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('1', 'Руководитель высшего звена', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('2', 'Руководитель среднего звена', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('3', 'Руководитель начального звена', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('4', 'Владелец предприятия/ген.Директор/Главный бухгалтер', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('5', 'Высококвалифицированный специалист', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('6', 'Специалист', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('7', 'Военнослужащий', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('8', 'Рабочий', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('9', 'Служащий', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('10', 'Судья', 70); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('11', 'Нотариус', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('12', 'Государственный гражданский служащий', 65); 

insert into LOANCLAIM_EDUCATION(CODE, NAME) values ('1', 'Ученая степень / MBA'); 
insert into LOANCLAIM_EDUCATION(CODE, NAME) values ('2', 'Второе высшее'); 
insert into LOANCLAIM_EDUCATION(CODE, NAME) values ('3', 'Высшее'); 
insert into LOANCLAIM_EDUCATION(CODE, NAME) values ('4', 'Незаконченное высшее'); 
insert into LOANCLAIM_EDUCATION(CODE, NAME) values ('5', 'Среднее специальное'); 
insert into LOANCLAIM_EDUCATION(CODE, NAME) values ('6', 'Среднее'); 
insert into LOANCLAIM_EDUCATION(CODE, NAME) values ('7', 'Ниже среднего'); 

insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('1', 'Мать', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('2', 'Отец', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('3', 'Брат', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('4', 'Сестра', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('5', 'Сын', 1); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('6', 'Дочь', 1);
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('7', 'Иное', 0); 

insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('0', 'Холост/не замужем', 0); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('1', 'В разводе', 0); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('2', 'Женат/замужем', 1); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('3', 'Вдовец/вдова', 0); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('4', 'Гражданский брак', 1);

insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('005', 'Предприятие потребительской кооперации', 'Потреб.КООП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('002', 'Открытое акционерное общество', 'ОАО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('003', 'Закрытое акционерное общество', 'ЗАО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('006', 'Полное товарищество', 'Полное товарищество'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('007', 'Товарищество на вере', 'Товарищество на вере'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('008', 'Производственный кооператив', 'Произв. КООП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('009', 'Крестьянское (фермерское) хозяйство', 'КФХ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('001', 'Общество с ограниченной ответственностью', 'ООО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('012', 'Общество с дополнительной ответственностью', 'ОДО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('013', 'Унитарное предприятие на праве хозяйственного ведения', 'УПОнПХВ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('014', 'Унитарное предприятие на праве оперативного управления', 'УПОнПОУ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('015', 'Дочернее унитарное предприятие', 'ДУП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('016', 'Общественная (религиозная) организация (объединение)', 'ОРО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('017', 'Общественное движение', 'ОД'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('018', 'Фонд', 'ФОНД'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('019', 'Учреждение', 'УЧРЕЖДЕНИЕ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('020', 'Государственное корпорация', 'ГК'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('021', 'Орган общественной самодеятельности', 'ООС'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('022', 'Некоммерческое партнерство', 'НКП'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('023', 'Автономная некоммерческая организация', 'АНО'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('024', 'Объединение юридических лиц (ассоциация или союз)', 'ОЮЛ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('025', 'Ассоциация крестьянских (фермерских) хозяйств', 'АКФХ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('026', 'Территориальное общественное самоуправление', 'ТОС'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('027', 'Товарищество собственников жилья', 'ТСЖ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('028', 'Садовод., огород.или дачные некоммерческие товарищества', 'ТОВРИЩЕСТВА'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('029', 'Прочая некоммерческия организация', 'ПРОЧИЕ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('030', 'Финансово - промышленные группы', 'ФПГ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('031', 'Паевые инвестиционные фонды', 'ПИФ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('032', 'Простые товарищества', 'ПТ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('999', 'Представительства и филиалы', 'ПРЕДСТАВИТЕЛЬСТВА'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('034', 'Иные неюридические лица', 'ИНЫЕ'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('033', 'Индивидуальные предприниматели (ПБЮЛ)', 'ИП');

insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('1', 'от 6 до 12 мес', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('2', 'от 1 года до 3 лет', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('3', 'от 3 до 5 лет', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('4', 'от 5 до 10 лет', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('5', 'от 10 до 20 лет', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('6', 'более 20 лет', 0); 
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('7', 'менее 6 месяцев', 1);

insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('1', 'Финансы, банки, страхование', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('2', 'Консалтинговые услуги', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('3', 'Армия', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('4', 'Промышленность и машиностроение', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('5', 'Предприятия ТЭК', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('6', 'Металлургия', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('7', 'Оптовая / розничная торговля (уточнение)', 1); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('8', 'Услуги (уточнение)', 1); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('9', 'Транспорт', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('10', 'Охранная деятельность', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('11', 'Туризм', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('12', 'Образование', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('13', 'Медицина', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('14', 'Культура и искусство', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('15', 'Органы власти и управления', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('16', 'Социальная сфера', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('17', 'Информационные технологии / телекоммуникации', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('18', 'Строительство', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('19', 'Наука', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('20', 'Другие отрасли (уточнение)', 1);

insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('2', 'Аннуитетный'); 
insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('0', 'Дифференцированный'); 
insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('9', 'Иное'); 

insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('0', 'Ежемесячно'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('1', 'Ежеквартально'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('2', 'Индивидуальный график'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('3', 'Ежегодно'); 

insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('1', 'До 10'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('2', '11-30'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('3', '31-50'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('4', '51-100'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('5', 'Более 100'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('6', 'Затрудняюсь ответить'); 

insert into LOANCLAIM_REGION(CODE, NAME) values ('0001', 'Республика Адыгея '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0002', 'Республика Башкортостан '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0003', 'Республика Бурятия '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0004', 'Республика Алтай '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0005', 'Республика Дагестан '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0006', 'Ингушская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0007', 'Кабардино - Балкарская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0008', 'Республика Калмыкия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0009', 'Карачаево - Черкесская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0010', 'Республика Карелия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0011', 'Республика Коми'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0012', 'Республика Марий Эл'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0013', 'Республика Мордовия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0014', 'Республика Саха (Якутия)'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0015', 'Республика Северная Осетия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0016', 'Республика Татарстан (Татарстан)'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0017', 'Республика Тува '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0018', 'Удмуртская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0019', 'Республика Хакасия'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0020', 'Чеченская Республика'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0021', 'Чувашская Республика- Чаваш Республики '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0022', 'Алтайский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0023', 'Краснодарский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0024', 'Красноярский край '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0025', 'Приморский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0026', 'Ставропольский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0027', 'Хабаровский край'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0028', 'Амурская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0029', 'Архангельская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0030', 'Астраханская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0031', 'Белгородская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0032', 'Брянская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0033', 'Владимирская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0034', 'Волгоградская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0035', 'Вологодская область '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0036', 'Воронежская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0037', 'Ивановская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0038', 'Иркутская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0039', 'Калининградская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0040', 'Калужская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0041', 'Камчатская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0042', 'Кемеровская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0043', 'Кировская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0044', 'Костромская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0045', 'Курганская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0046', 'Курская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0047', 'Ленинградская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0048', 'Липецкая область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0049', 'Магаданская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0050', 'Московская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0051', 'Мурманская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0052', 'Нижегородская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0053', 'Новгородская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0054', 'Новосибирская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0055', 'Омская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0056', 'Оренбургская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0057', 'Орловская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0058', 'Пензенская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0059', 'Пермская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0060', 'Псковская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0061', 'Ростовская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0062', 'Рязанская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0063', 'Самарская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0064', 'Саратовская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0065', 'Сахалинская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0066', 'Свердловская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0067', 'Смоленская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0068', 'Тамбовская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0069', 'Тверская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0070', 'Томская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0071', 'Тульская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0072', 'Тюменская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0073', 'Ульяновская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0074', 'Челябинская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0075', 'край Забайкальский'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0076', 'Ярославская область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0077', 'г. Москва'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0078', 'г. Санкт – Петербург'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0079', 'Еврейская автономная область'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0080', 'Агинский Бурятский автономный округ'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0081', 'Коми - Пермяцкий автономный округ'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0082', 'Корякский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0083', 'Ненецкий автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0084', 'Таймырский (Долгано - Ненецкий) автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0085', 'Усть - Ордынский Бурятский автономный округ'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0086', 'Ханты - Мансийский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0087', 'Чукотский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0088', 'Эвенкийский автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0089', 'Ямало - Ненецкий автономный округ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('9901', 'город и космодром Байконур '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0099', 'Иное (только для адресов, расположенных вне Российской Федерации');

insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME) values ('1', 'Собственная квартира'); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME) values ('2', 'У родственников'); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME) values ('3', 'Соц. Найм'); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME) values ('4', 'Аренда'); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME) values ('5', 'Коммунальная квартира'); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME) values ('6', 'Общежитие'); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME) values ('7', 'Воинская часть');

insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('201', 'Р-н'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('230', 'Тер'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('202', 'У'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('204', 'Кожуун'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('205', 'АО'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('103', 'г'); 

insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('310', 'Волость'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('301', 'Г'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('305', 'Дп'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('304', 'Кп'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('311', 'п/о'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('302', 'пгт'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('303', 'рп'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('307', 'с/а'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('309', 'с/о'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('306', 'с/с'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('312', 'тер'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('313', 'сумон'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('314', 'с/п'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('315', 'с/мо'); 

insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('1', 'Автокредит'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('2', 'Ипотечный'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('3', 'Потребительский'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('4', 'Кредитная карта'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('5', 'Поручительство'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('6', 'Другое'); 

insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('401', 'аал'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('402', 'аул'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('403', 'волость'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('404', 'высел'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('405', 'г'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('436', 'городок'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('406', 'д'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('407', 'дп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('408', 'ж/д будка'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('409', 'ж/д казарм'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('410', 'ж/д оп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('411', 'ж/д пост'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('412', 'ж/д разд'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('438', 'ж/д платф'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('413', 'ж/д ст'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('414', 'заимка'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('415', 'казарма'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('416', 'кп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('417', 'м'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('418', 'мкр'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('419', 'нп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('420', 'остров'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('421', 'п'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('426', 'п/о'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('422', 'п/р'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('423', 'п/ст'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('424', 'пгт'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('425', 'починок'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('427', 'промзона'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('428', 'рзд'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('429', 'рп'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('430', 'с'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('431', 'сл'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('432', 'ст'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('433', 'ст-ца'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('437', 'тер'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('434', 'у'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('435', 'х'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('439', 'кв-л'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('440', 'арбан'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('441', 'снт'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('442', 'лпх'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('443', 'погост'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('444', 'кордон'); 

insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME) values ('1', 'Комната'); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME) values ('2', 'Квартира'); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME) values ('3', 'Дом'); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME) values ('4', 'Участок'); 

insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('529', 'Ул'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('532', 'аал'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('501', 'Аллея'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('533', 'Аул'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('502', 'б-р'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('503', 'Въезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('534', 'Высел'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('535', 'Городок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('536', 'Д'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('504', 'Дор'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('537', 'ж/д будка'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('538', 'ж/д казарм'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('539', 'ж/д оп'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('540', 'ж/д пост'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('541', 'ж/д рзд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('542', 'ж/д ст'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('559', 'ж/д платф'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('505', 'Жт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('506', 'Заезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('543', 'Казарма'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('507', 'кв-л'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('508', 'Км'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('509', 'Кольцо'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('510', 'Линия'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('544', 'М'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('545', 'Мкр'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('511', 'Наб'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('546', 'Нп'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('512', 'Остров'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('548', 'П'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('549', 'п/о'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('550', 'п/р'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('551', 'п/ст'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('513', 'Парк'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('514', 'Пер'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('515', 'Переезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('516', 'Пл'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('547', 'Платф'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('517', 'пл-ка'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('552', 'Полустанок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('553', 'Починок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('519', 'пр-кт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('518', 'Проезд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('520', 'просек'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('521', 'Проселок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('522', 'Проулок'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('554', 'Рзд'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('555', 'С'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('523', 'Сад'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('524', 'Сквер'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('556', 'Сл'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('557', 'Ст'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('525', 'Стр'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('526', 'Тер'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('527', 'Тракт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('528', 'Туп'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('530', 'уч-к'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('558', 'Х'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('531', 'Ш'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('560', 'Арбан'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('561', 'Спуск'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('562', 'Канал'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('563', 'Гск'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('564', 'Снт'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('565', 'Лпх'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('566', 'Проток'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('567', 'коса'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('568', 'вал'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('569', 'ферма'); 

insert into LOANCLAIM_TYPE_OF_VEHICLE(CODE, NAME) values ('1', 'Наземное ТС'); 
insert into LOANCLAIM_TYPE_OF_VEHICLE(CODE, NAME) values ('2', 'Водное ТС'); 

-- Работа по трудовому договору
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('1', 'Срочный контракт', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('2', 'Без срока (постоянная занятость)', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('3', 'Частная практика (уточнить)', 0, 0, 1, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('4', 'Индивидуальный предприниматель', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('5', 'Агент на комиссионном договоре', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('6', 'Пенсионер', 0, 0, 0, 1); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, BUSINESS_DESC_REQUIRED, UNEMPLOYED) values ('7', 'Исполнитель по гражданско-правовому договору', 1, 1, 0, 0); 
 
 
-- Номер ревизии: 58284
-- Номер версии: 1.18
-- Комментарий: CHG065494: Мусор в PAYMENT_EXECUTION_RECORDS 

delete from PAYMENT_EXECUTION_RECORDS
go

alter table PAYMENT_EXECUTION_RECORDS add (COUNTER INTEGER not null)
go 


-- Номер ревизии: 58323
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника ПБО в многоблочном режиме

alter table QUICK_PAYMENT_PANELS add UUID varchar2(32) default SYS_GUID() not null
go
alter table QUICK_PAYMENT_PANELS add constraint AK_UUID_UNIQUE unique(UUID)
go


alter table PANEL_BLOCKS add UUID varchar2(32) default SYS_GUID() not null
go
alter table PANEL_BLOCKS add constraint AK_UNIQUE_PANEL_BLOCKS unique(UUID)
go

-- Номер ревизии: 58284
-- Номер версии: 1.18
-- Комментарий: CHG065494: Мусор в PAYMENT_EXECUTION_RECORDS 

drop index DOC_ID_JOB_NAME_PER_IDX
go

create unique index DOC_ID_JOB_NAME_PER_IDX on PAYMENT_EXECUTION_RECORDS (
   DOCUMENT_ID ASC,
   JOB_NAME ASC
)
go
 
-- Номер ревизии: 58327
-- Номер версии: 1.18
-- Комментарий: Изменение БД заказов

alter table SHOP_RECALLS rename column "DATE" to RECALL_DATE
go

alter table SHOP_RECALLS modify UUID varchar2(300)
go


-- Номер ревизии: 58374
-- Номер версии: 1.18
-- Комментарий:  Синхронизация справочника событий в многоблочном режиме.
alter table NEWS add UUID VARCHAR2(32)
go

create unique index I_NEWS_UUID on NEWS(
    UUID
)
go

update NEWS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table NEWS modify UUID not null
go

-- Номер ревизии: 58373
-- Номер версии: 1.18
-- Комментарий: BUG068289: Некорректно отображается список доступных подразделений у сотрудника(часть 3. инициализация контекста для джоба выполнения фоновых задач, изменение AllowedDepartmentsUtil)
delete from PROVIDER_REPLICA_TASKS
go

alter table PROVIDER_REPLICA_TASKS modify(OWNER_ID INTEGER)
go

delete from DEPARTMENTS_TASKS_CONTENT
go

delete from DEPARTMENTS_REPLICA_TASKS
go

alter table DEPARTMENTS_REPLICA_TASKS modify(OWNER_ID INTEGER)
go



-- Номер ревизии: 58386
-- Номер версии: 1.18
-- Комментарий: CHG065690: Переделать привязку ПБО к тербанкам

alter table CALENDARS add TB VARCHAR2(4)
go
update CALENDARS c set TB = (select d.TB from DEPARTMENTS d where d.id = c.DEPARTMENT_ID)
go
alter table CALENDARS drop column DEPARTMENT_ID
go 


-- Номер ревизии: 58417
-- Номер версии: 1.18
-- Комментарий: fix: индексы по таблице перекодировки кодов подразделений ЦАС НСИ -> Transact SM

ALTER TABLE CASNSI_TO_ETSM_DEP
DROP PRIMARY KEY CASCADE
;

ALTER TABLE CASNSI_TO_ETSM_DEP
DROP COLUMN ID
;

ALTER TABLE CASNSI_TO_ETSM_DEP
ADD CONSTRAINT CASNSI_TO_ETSM_DEP_PK PRIMARY KEY(TB_CASNSI, OSB_CASNSI)
;

-- Номер ревизии: 58451
-- Номер версии: 1.18
-- Комментарий:  BUG068793: Невозможно добавить площадку КЦ через цса админ.

alter table C_CENTER_AREAS_DEPARTMENTS add TB varchar2(4)
go
update C_CENTER_AREAS_DEPARTMENTS c set TB = (select d.TB from DEPARTMENTS d where d.id = c.DEPARTMENT_ID)
go
alter table C_CENTER_AREAS_DEPARTMENTS drop constraint PK_C_CENTER_AREAS_DEPARTMENTS 
go
alter table C_CENTER_AREAS_DEPARTMENTS add constraint PK_C_CENTER_AREAS_DEPARTMENTS PRIMARY KEY (C_C_AREA_ID, TB)
go
alter table C_CENTER_AREAS_DEPARTMENTS drop column DEPARTMENT_ID
go 
create unique index I_C_CENTER_AREAS_DEPARTMENTS on C_CENTER_AREAS_DEPARTMENTS (
   TB
)
go

-- Номер ревизии:  58459
-- Номер версии: 1.18
-- Комментарий:  CHG065977: Необходимо информировать клиента, если операция производится по льготному курсу.

create table TARIF_PLAN_CONFIGS 
(
   ID                   INTEGER              not null,
   TARIF_PLAN_CODE      VARCHAR(50)          not null,
   NEED_SHOW_STANDART_RATE CHAR(1),
   PRIVILEGED_RATE_MESSAGE VARCHAR(2000),
   constraint PK_TARIF_PLAN_CONFIGS primary key (ID)
)
go
create sequence S_TARIF_PLAN_CONFIGS
go


-- Номер ревизии: 58475
-- Номер версии: 1.18
-- Комментарий: Перевод на многоблочный режим справочника депозитных продуктов.(часть 1)
drop sequence S_DEPOSITGLOBALS
go

drop sequence S_DEPOSIT_DEPARTMENTS
go


-- Номер ревизии: 58486
-- Номер версии: 1.18
-- Комментарий: BUG068192: Не отображаются пункты лимитов при редактировании подразделения в цса админ 
alter table LIMITS add UUID VARCHAR2(32)
go

create unique index I_LIMITS_UUID on LIMITS(
    UUID
)
go

update LIMITS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table LIMITS modify UUID not null
go


-- Номер ревизии: 58532
-- Номер версии: 1.18
-- Комментарий: CHG069020: Поле синхронизации справочника поставщиков услуг 

alter table SERVICE_PROVIDERS add UUID VARCHAR2(32)
go

create unique index I_SERVICE_PROVIDERS_UUID on SERVICE_PROVIDERS(
    UUID
)
go

--Заполнить UUID данными из ЦСААдмин

alter table SERVICE_PROVIDERS modify UUID not null
go

-- Номер ревизии: 58549
-- Номер версии: 1.18
-- Комментарий: АРМ Сотрудника. Протокол загрузки пред одобренных предложение по картам.

alter table LOAN_CARD_OFFER
add LOAD_DATE TIMESTAMP
add VIEW_DATE TIMESTAMP
add TRANSITION_DATE TIMESTAMP
add REGISTRATION_DATE TIMESTAMP
add IS_OFFER_USED CHAR(1)  default '0' not null 
go

-- Номер ревизии: 58551
-- Номер версии: 1.18
-- Комментарий: Синхронизация справочника ОМС продуктов.
alter table IMAPRODUCT add UUID VARCHAR2(32)
go

create unique index I_IMAPRODUCT_UUID on IMAPRODUCT(
    UUID ASC
)
go

update IMAPRODUCT set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table IMAPRODUCT modify UUID not null
go

-- Номер ревизии: 58575
-- Номер версии: 1.18
-- Комментарий: Синхронизация баннеров в многоблочном режиме

alter table ADVERTISINGS add UUID VARCHAR2(32)
go

create unique index I_ADVERTISINGS_UUID on ADVERTISINGS(
    UUID
)
go

update ADVERTISINGS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table ADVERTISINGS modify UUID not null
go

alter table ADVERTISING_BUTTONS add UUID VARCHAR2(32)
go

create unique index I_ADVERTISING_BUTTONS_UUID on ADVERTISING_BUTTONS(
    UUID
)
go

update ADVERTISING_BUTTONS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table ADVERTISING_BUTTONS modify UUID not null
go

-- Номер ревизии: 58610
-- Номер версии: 1.18
-- Комментарий: BUG068403: Длина поля Код типа участника расчетов в сети Банка России не соответствует РО
alter table RUSBANKS modify PARTICIPANT_CODE VARCHAR2(100);

-- Номер ревизии: 58643
-- Номер версии: 1.18
-- Комментарий: Расширенная заявка на кредит. Доработка справочника "образование".

delete from LOANCLAIM_EDUCATION;

alter table LOANCLAIM_EDUCATION add (HIGH_EDUCATION_COURSE_REQUIRED CHAR(1 BYTE) not null);

insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('1', 'Ученая степень / MBA', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('2', 'Второе высшее', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('3', 'Высшее', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('4', 'Незаконченное высшее', 1);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('5', 'Среднее специальное', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('6', 'Среднее', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('7', 'Ниже среднего', 0);


-- Номер ревизии: 58637
-- Номер версии: 1.18
-- Комментарий: EInvoicing2 (ч27. Модель БД)
alter table BUSINESS_DOCUMENTS_TO_ORDERS add TYPE varchar2(30)
go
update BUSINESS_DOCUMENTS_TO_ORDERS bdto set TYPE=
(select (case when bd.KIND = 'RO' then 'REFUND' when bd.KIND = 'RG' THEN 'PARTIAL_REFUND' else 'ORDER' end ) 
 from BUSINESS_DOCUMENTS bd where bd.id=bdto.BUSINESS_DOCUMENT_ID)
go
alter table BUSINESS_DOCUMENTS_TO_ORDERS modify TYPE not null
go
drop index BUS_DOC_ORD_UUID_IDX
go
create index BUS_DOC_ORD_UUID_IDX on BUSINESS_DOCUMENTS_TO_ORDERS (
    ORDER_UUID asc,
    TYPE asc
)
go


-- Номер ревизии: 58665
-- Номер версии: 1.18
-- Комментарий: BUG069090: Не синхронизируется видимость вклада в депозитах

alter table DEPOSIT_DEPARTMENTS add TB varchar2(4)
go
update DEPOSIT_DEPARTMENTS dd set dd.TB = (select d.TB from DEPARTMENTS d where d.ID = dd.DEPARTMENT_ID)
go
alter table DEPOSIT_DEPARTMENTS modify TB not null
go
alter table DEPOSIT_DEPARTMENTS drop constraint PK_DEPOSIT_DEPARTMENTS
go
alter table DEPOSIT_DEPARTMENTS drop column DEPARTMENT_ID
go
alter table DEPOSIT_DEPARTMENTS add constraint PK_DEPOSIT_DEPARTMENTS PRIMARY KEY (DEPOSIT_ID, TB)
go

-- Номер ревизии: 58726
-- Номер версии: 1.18
-- Комментарий: CHG069141: Убрать many-to-one class="com.rssl.phizic.business.departments.Department" из лимитов.

alter table LIMITS add TB varchar2(4)
go
update LIMITS l set TB = (select d.TB from DEPARTMENTS d where d.ID = l.DEPARTMENT_ID)
go
alter table LIMITS drop column DEPARTMENT_ID
go
alter table LIMITS modify TB not null
go

alter table PAYMENTS_GROUP_RISK add TB varchar2(4)
go
update PAYMENTS_GROUP_RISK pgr set TB = (select d.TB from DEPARTMENTS d where d.ID = pgr.DEPARTMENT_ID)
go
alter table PAYMENTS_GROUP_RISK drop column DEPARTMENT_ID
go
alter table PAYMENTS_GROUP_RISK modify TB not null
go


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

drop index NEWS_INDEX 
go

update NEWS set START_PUBLISH_DATE = current_date where START_PUBLISH_DATE is null and STATE = 'PUBLISHED'
go

alter table NEWS drop column STATE
go


-- Номер ревизии: 58839
-- Номер версии: 1.18
-- Комментарий: EInvoicing2 (ч33.1. Модель БД. PhizIC)

alter table SHOP_NOTIFICATIONS RENAME COLUMN "DATE" to CREATE_DATE
go
alter table SHOP_NOTIFICATIONS  add RECEIVER_CODE        VARCHAR2(32)         not null
go
drop index IDX_SHOP_NOTIF_STATE_DATE
go
create index IDX_SHOP_NOTIF_STATE_DATE on SHOP_NOTIFICATIONS (
   STATE ASC,
   CREATE_DATE ASC
)
go
alter table SHOP_RECALLS modify UUID varchar2(32)
go
insert into properties (ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.nextval, 'job.orders.notify.max.count', (SELECT value FROM BUSINESS_PROPERTIES where KEY='orderNotificationCount'), 'phiz')


-- Номер ревизии: 58842
-- Номер версии: 1.18
-- Комментарий: BUG069393: [ЕРМБ] необходимо реализовать CSATransportSmsResponseListener 

alter table ERMB_CHECK_IMSI_RESULTS add (PHONE_NUMBER varchar2(20))
go

-- Номер ревизии: 58865
-- Номер версии: 1.18
-- Комментарий: BUG069036: Нет проверки условия заполнения блока недвижимости при создании заявки на кредит 

ALTER TABLE LOANCLAIM_RESIDENCE_RIGHT ADD (NEED_REALTY_INFO CHAR(1 BYTE) DEFAULT 0 NOT NULL);
UPDATE LOANCLAIM_RESIDENCE_RIGHT SET NEED_REALTY_INFO = 1 WHERE CODE = '1';


-- Номер ревизии: 58876
-- Номер версии: 1.18
-- Комментарий: EInvoicing2 (ч33.2. Модель БД. PhizIC)

drop index IDX_SHOP_NOTIF_STATE_DATE
go

create index IDX_SHOP_NOTIF_STATE_DATE on SHOP_NOTIFICATIONS (
   STATE ASC,
   CREATE_DATE ASC
)
local
go

create index IDX_SH_RECALLS_ORDER_UUID on SHOP_RECALLS (
   ORDER_UUID ASC
)
local
go

insert into properties (ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) values (S_PROPERTIES.nextval, 'job.orders.clear.days.count', (SELECT value FROM BUSINESS_PROPERTIES where KEY='clearOrderDays'), 'phiz')
go

-- Номер ревизии: 58963
-- Номер версии: 1.18
-- Комментарий: BUG069264: Не отображать удаленный продукт на форме условий 

ALTER TABLE CREDIT_PRODUCT_CONDITION ADD (TMP_IS_PUBLIC CHAR(1) DEFAULT 0 NOT NULL)
go

UPDATE CREDIT_PRODUCT_CONDITION 
SET TMP_IS_PUBLIC = 0 
WHERE IS_PUBLIC = 'DELETED' or IS_PUBLIC = 'UNPUBLISHED'
go

UPDATE CREDIT_PRODUCT_CONDITION 
SET TMP_IS_PUBLIC = 1 
WHERE IS_PUBLIC = 'PUBLISHED' 
go

ALTER TABLE CREDIT_PRODUCT_CONDITION DROP COLUMN IS_PUBLIC
go

ALTER TABLE CREDIT_PRODUCT_CONDITION RENAME COLUMN TMP_IS_PUBLIC TO IS_PUBLISHED 
go

-- Номер ревизии: 58981
-- Номер версии: 1.18
-- Комментарий: CHG069292: Связка бизнес документов и интернет заказов
alter table BUSINESS_DOCUMENTS_TO_ORDERS drop column TYPE
go

-- Номер ревизии: 58990
-- Номер версии: 1.18
-- Комментарий: BUG067276: Подсчет операций в АЛФ 

drop index DXFK_CARDOP_CATEGORY
go

create index I_CARDOP_CATEGORY on CARD_OPERATIONS (
   CATEGORY_ID ASC
)
local
go

drop index DXFK_PROFILE_TO_CLAIMS_DATE
go

create index I_PROFILE_CLAIMS_DATE on PROFILE
(
    DECODE(USING_FINANCES_COUNT, 0, null, USING_ALF_EVERY_THREE_DAYS_NUM / USING_FINANCES_COUNT)
)
go

-- Номер ревизии: 58994
-- Номер версии: 1.18
-- Комментарий: BUG068155: Переделать логирование в OFFER_NOTIFICATIONS_LOG
drop table OFFER_NOTIFICATIONS_LOG
go

drop  sequence S_OFFER_NOTIFICATIONS_LOG
go

-- Номер ревизии: 59013
-- Номер версии: 1.18
-- Комментарий: EInvoicing2 (ч37 SHOP_RECALLS. поле TYPE. PhizIC)

alter table SHOP_RECALLS add TYPE varchar2(10) not null

-- Номер ревизии: 59028
-- Номер версии: 1.18
-- Комментарий: BUG069565: Расширенная заявка: Недоконца выполняется проверка в блоке недвижимость

ALTER TABLE LOANCLAIM_TYPE_OF_REALTY ADD (RESIDENCE CHAR(1 BYTE) DEFAULT 0 NOT NULL);
UPDATE LOANCLAIM_TYPE_OF_REALTY SET RESIDENCE = 1 WHERE CODE = '2';


-- Номер ревизии: 59062
-- Номер версии: 1.18
-- Комментарий: BUG069665: Исключение при переходе по ссылке Мои финансы 

drop index I_PROFILE_CLAIMS_DATE
go

create index I_PROFILE_ALF_STATISTICS on PROFILE (
   DECODE(USING_FINANCES_COUNT, 0, null, TRUNC(USING_ALF_EVERY_THREE_DAYS_NUM * 100 / USING_FINANCES_COUNT)) ASC
)
go

-- Номер версии: 1.18
-- Комментарий:  CHG069684: Некорретный справочник Вид собственности жилья по месту фактического проживания 
delete from LOANCLAIM_RESIDENCE_RIGHT;
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('0', 'Собственная квартира',1); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('1', 'У родственников',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('2', 'Соц. Найм',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('3', 'Аренда',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('4', 'Коммунальная квартира',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('5', 'Общежитие',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('6', 'Воинская часть',0);

-- Номер ревизии: 59135
-- Номер версии: 1.18
-- Комментарий: BUG069584 Долго открывается страница каталога при создании шаблона СМС
/*==============================================================*/
/* Index: IDX_SP_IS_MOBILEBANK                                  */
/*==============================================================*/
create index IDX_SP_IS_MOBILEBANK on SERVICE_PROVIDERS (
   IS_MOBILEBANK ASC
)
go

-- Номер ревизии: 59141
-- Номер версии: 1.18
-- Комментарий: CHG067950: [ЕРМБ] перевод Job-ов ЕРМБ на БД.
CREATE TABLE qrtz_ermb_job_details
  (
    JOB_NAME  VARCHAR2(80) NOT NULL,
    JOB_GROUP VARCHAR2(80) NOT NULL,
    DESCRIPTION VARCHAR2(120) NULL,
    JOB_CLASS_NAME   VARCHAR2(128) NOT NULL,
    IS_DURABLE VARCHAR2(1) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    IS_STATEFUL VARCHAR2(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (JOB_NAME,JOB_GROUP)
)
;

CREATE TABLE qrtz_ermb_job_listeners
  (
    JOB_NAME  VARCHAR2(80) NOT NULL,
    JOB_GROUP VARCHAR2(80) NOT NULL,
    JOB_LISTENER VARCHAR2(80) NOT NULL,
    PRIMARY KEY (JOB_NAME,JOB_GROUP,JOB_LISTENER),
    FOREIGN KEY (JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_ERMB_JOB_DETAILS(JOB_NAME,JOB_GROUP)
)
;

CREATE TABLE qrtz_ermb_triggers
  (
    TRIGGER_NAME VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    JOB_NAME  VARCHAR2(80) NOT NULL,
    JOB_GROUP VARCHAR2(80) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    DESCRIPTION VARCHAR2(120) NULL,
    NEXT_FIRE_TIME NUMBER(13) NULL,
    PREV_FIRE_TIME NUMBER(13) NULL,
    PRIORITY NUMBER(13) NULL,
    TRIGGER_STATE VARCHAR2(16) NOT NULL,
    TRIGGER_TYPE VARCHAR2(8) NOT NULL,
    START_TIME NUMBER(13) NOT NULL,
    END_TIME NUMBER(13) NULL,
    CALENDAR_NAME VARCHAR2(80) NULL,
    MISFIRE_INSTR NUMBER(2) NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_ERMB_JOB_DETAILS(JOB_NAME,JOB_GROUP)
)
;

CREATE TABLE qrtz_ermb_simple_triggers
  (
    TRIGGER_NAME VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    REPEAT_COUNT NUMBER(7) NOT NULL,
    REPEAT_INTERVAL NUMBER(12) NOT NULL,
    TIMES_TRIGGERED NUMBER(7) NOT NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_ERMB_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)
;

CREATE TABLE qrtz_ermb_cron_triggers
  (
    TRIGGER_NAME VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    CRON_EXPRESSION VARCHAR2(80) NOT NULL,
    TIME_ZONE_ID VARCHAR2(80),
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_ERMB_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)
;

CREATE TABLE qrtz_ermb_blob_triggers
  (
    TRIGGER_NAME VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    BLOB_DATA BLOB NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES QRTZ_ERMB_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)
;

CREATE TABLE qrtz_ermb_trigger_listeners
  (
    TRIGGER_NAME  VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    TRIGGER_LISTENER VARCHAR2(80) NOT NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_LISTENER),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_ERMB_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)
;

CREATE TABLE qrtz_ermb_calendars
  (
    CALENDAR_NAME  VARCHAR2(80) NOT NULL,
    CALENDAR BLOB NOT NULL,
    PRIMARY KEY (CALENDAR_NAME)
)
;

CREATE TABLE qrtz_ermb_paused_trigger_grps
  (
    TRIGGER_GROUP  VARCHAR2(80) NOT NULL,
    PRIMARY KEY (TRIGGER_GROUP)
)
;

CREATE TABLE qrtz_ermb_fired_triggers
  (
    ENTRY_ID VARCHAR2(95) NOT NULL,
    TRIGGER_NAME VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    INSTANCE_NAME VARCHAR2(80) NOT NULL,
    FIRED_TIME NUMBER(13) NOT NULL,
    PRIORITY NUMBER(13) NOT NULL,
    STATE VARCHAR2(16) NOT NULL,
    JOB_NAME VARCHAR2(80) NULL,
    JOB_GROUP VARCHAR2(80) NULL,
    IS_STATEFUL VARCHAR2(1) NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NULL,
    PRIMARY KEY (ENTRY_ID)
)
;

CREATE TABLE qrtz_ermb_scheduler_state
  (
    INSTANCE_NAME VARCHAR2(80) NOT NULL,
    LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
    CHECKIN_INTERVAL NUMBER(13) NOT NULL,
    PRIMARY KEY (INSTANCE_NAME)
)
;

CREATE TABLE qrtz_ermb_locks
  (
    LOCK_NAME  VARCHAR2(40) NOT NULL,
    PRIMARY KEY (LOCK_NAME)
)
;

INSERT INTO qrtz_ermb_locks values('TRIGGER_ACCESS')
;

INSERT INTO qrtz_ermb_locks values('JOB_ACCESS')
;

INSERT INTO qrtz_ermb_locks values('CALENDAR_ACCESS')
;

INSERT INTO qrtz_ermb_locks values('STATE_ACCESS')
;

INSERT INTO qrtz_ermb_locks values('MISFIRE_ACCESS')
;

create index idx_qrtz_ermb_j_req_recovery on qrtz_ermb_job_details(REQUESTS_RECOVERY)
;

create index idx_qrtz_ermb_t_next_fire_time on qrtz_ermb_triggers(NEXT_FIRE_TIME)
;

create index idx_qrtz_ermb_t_state on qrtz_ermb_triggers(TRIGGER_STATE)
;

create index idx_qrtz_ermb_t_nft_st on qrtz_ermb_triggers(NEXT_FIRE_TIME,TRIGGER_STATE)
;

create index idx_qrtz_ermb_t_volatile on qrtz_ermb_triggers(IS_VOLATILE)
;

create index idx_qrtz_ermb_ft_trig_name on qrtz_ermb_fired_triggers(TRIGGER_NAME)
;

create index idx_qrtz_ermb_ft_trig_group on qrtz_ermb_fired_triggers(TRIGGER_GROUP)
;

create index idx_qrtz_ermb_ft_trig_nm_gp on qrtz_ermb_fired_triggers(TRIGGER_NAME,TRIGGER_GROUP)
;

create index idx_qrtz_ermb_ft_trig_volatile on qrtz_ermb_fired_triggers(IS_VOLATILE)
;

create index idx_qrtz_ermb_ft_trig_inst_nm on qrtz_ermb_fired_triggers(INSTANCE_NAME)
;

create index idx_qrtz_ermb_ft_job_name on qrtz_ermb_fired_triggers(JOB_NAME)
;

create index idx_qrtz_ermb_ft_job_group on qrtz_ermb_fired_triggers(JOB_GROUP)
;

create index idx_qrtz_ermb_ft_job_stateful on qrtz_ermb_fired_triggers(IS_STATEFUL)
;

create index idx_qrtz_ermb_ft_job_req_rec on qrtz_ermb_fired_triggers(REQUESTS_RECOVERY)
;

-- Номер ревизии: 59156
-- Номер версии: 1.18
-- Комментарий: ENH066749: Поиск по пустым фильтрам в АРМ сотрудника
drop index INDEX_USER_ID
/
create index INDEX_USER_ID on LOGINS(upper(USER_ID))
/
drop index AGR_NUMB_SINGLE_INDEX
/
create index AGR_NUMB_SINGLE_INDEX on USERS(upper(AGREEMENT_NUMBER))
/
create index IO_BD_PERSON_INDEX on USERS (upper (REPLACE(REPLACE("FIRST_NAME"||"PATR_NAME",' ',''),'-','')), BIRTHDAY)
/

-- Номер ревизии: 59304
-- Номер версии: 1.18
-- Комментарий: BUG069810: Исключение при входе администратора
drop index INDEX_USER_ID
/
create index INDEX_USER_ID on LOGINS(USER_ID)
/
drop index AGR_NUMB_SINGLE_INDEX
/
create index AGR_NUMB_SINGLE_INDEX on USERS(AGREEMENT_NUMBER)
/

-- Номер ревизии: 59018
-- Номер версии: 1.18
-- Комментарий: BUG069500 Не сохрняются данные шаблона (Модель БД)
alter table PAYMENTS_DOCUMENTS_EXT add (CHANGED char(1) default '0' not null)
go


-- Номер ревизии: 59318
-- Номер версии: 1.18
-- Комментарий: Шаблоны, машина состояний для шаблонов (Модель БД)
alter table PAYMENTFORMS add (TEMPLATE_STATE_MACHINE clob)
go

-- Номер ревизии: 59525
-- Номер версии: 1.18
-- Комментарий: ИОК

alter table ORDERS drop column ORDER_TYPE
go

drop table SHOP_ADDITIONAL_FIELDS
go

drop sequence S_SHOP_ADDITIONAL_FIELDS
go

drop index MOB_CHCKOUT_STTE_IDX
go

alter table SHOP_FIELDS drop column AIRLINE_RESERV_ID
go

alter table SHOP_FIELDS drop column AIRLINE_RESERV_EXPIRATION
go

alter table SHOP_FIELDS drop column AIRLINE_RESERVATION
go

alter table SHOP_FIELDS drop column CANCELED
go

alter table SHOP_FIELDS drop column MOBILE_CHECKOUT_STATE
go

alter table SHOP_FIELDS drop column MOBILE_CHECKOUT_PHONE
go


-- Номер ревизии: 59926
-- Номер версии: 1.18
-- Комментарий: Настройка расхождение даты исполнения платежного документа в таблицах BUSSINES_DOCUNENTS, CARD_OPERATION
insert into PROPERTIES values(S_PROPERTIES.NEXTVAL, 'linking.max.date.diff', 120, 'phiz')
/


-- Номер ревизии: 59931
-- Номер версии: 1.18
-- Комментарий: Автоматическая перекатегоризация (Сохранять начальное значение DESCRIPTION из IPS, Реализовать добавление перекатегоризации клиентом)

create table ALF_RECATEGORIZATION_RULES
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   DESCRIPTION          VARCHAR2(100)        not null,
   MCC_CODE             INTEGER              not null,
   NEW_CATEGORY_ID      INTEGER              not null,
   constraint PK_ALF_RECATEGORIZATION_RULES primary key (ID)
)
go

create sequence S_ALF_RECATEGORIZATION_RULES
go

create unique index I_ALF_RECATEGORIZATION_RULES on ALF_RECATEGORIZATION_RULES (
   LOGIN_ID ASC,
   MCC_CODE ASC,
   DESCRIPTION ASC
)
go

alter table CARD_OPERATIONS ADD ORIGINAL_DESCRIPTION VARCHAR2(100)
go


-- Номер ревизии: 59963
-- Номер версии: 1.18
-- Комментарий: Автоматическая перекатегоризация (Реализовать алгоритм перекатегоризации)

create index "DXFK_RULE_TO_CATEGORY" on ALF_RECATEGORIZATION_RULES
(
   NEW_CATEGORY_ID
)
go

alter table ALF_RECATEGORIZATION_RULES
   add constraint FK_RULE_TO_CATEGORY foreign key (NEW_CATEGORY_ID)
      references CARD_OPERATION_CATEGORIES (ID)
      on delete cascade
go


-- Номер ревизии: 59978
-- Номер версии: 1.18
-- Комментарий: Автоматическая перекатегоризация (Реализовать алгоритм перекатегоризации) (докоммит)

alter table ALF_RECATEGORIZATION_RULES drop constraint FK_RULE_TO_CATEGORY
go

alter table ALF_RECATEGORIZATION_RULES
   add constraint FK_RULE_TO_CATEGORY foreign key (NEW_CATEGORY_ID)
      references CARD_OPERATION_CATEGORIES (ID)
go

alter table ALF_RECATEGORIZATION_RULES
   add constraint FK_ALF_RECA_FK_RULE_T_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
go


-- Номер ревизии: 59981
-- Номер версии: 1.18
-- Комментарий: Связь справочника видов (групп) услуг и справочника категорий в АЛФ
alter table PAYMENT_SERVICES add CARD_OPERATION_CATEGORY NUMBER NULL
/
CREATE UNIQUE INDEX DXFK_CARD_OP_CATEGORY_ID
    ON PAYMENT_SERVICES(CARD_OPERATION_CATEGORY asc)
/

-- Номер ревизии: 59990
-- Номер версии: 1.18
-- Комментарий: BUG070244: Ошибка при оформлении offline заказа
ALTER
	TABLE SHOP_ORDERS ADD MOBILE_CHECKOUT CHAR(1) DEFAULT '0' NOT NULL
go

-- Номер ревизии: 60008
-- Номер версии: 1.18
-- Комментарий: BUG069142: Сбрасываются настройки в параметрах системы
delete from PROPERTIES where PROPERTY_KEY like 'mobileProviderProperties%'
go

delete from PROPERTIES where PROPERTY_KEY like 'oldDocAdapterProperties%'
go

insert into PROPERTIES (id, property_key, property_value, category)
select (select max(id) from properties) + rownum id , 'com.rssl.business.mobileProvidersProperties_' || (rownum-1) property_key, key property_value, 'phiz' category
from BUSINESS_PROPERTIES
where kind='E'
order by id
go

insert into PROPERTIES (id, property_key, property_value, category)
select (select max(id) from properties) + rownum id , 'com.rssl.business.oldDocAdaptersProperties_' || (rownum-1) property_key, key property_value, 'phiz' category
from BUSINESS_PROPERTIES
where kind='F'
order by id
go

delete from BUSINESS_PROPERTIES where KIND in ('E', 'F')
go

-- Номер ревизии: 60172
-- Номер версии: 1.18
-- Комментарий: Реализовать выгрузку информации по совершенной в ЕРИБ операции  (BUSSINES_DOCUNENTS) в таблицу с данными,  поученными из ИПС (CARD_OPERATION).

alter table CARD_OPERATIONS MODIFY LOAD_DATE null
/
alter table card_operations Add BUSINESS_DOCUMENT_ID INTEGER NULL
/

-- Номер ревизии: 60240
-- Номер версии: 1.18
-- Комментарий: Реализовать выгрузку информации по совершенной в ЕРИБ операции  (BUSSINES_DOCUNENTS) в таблицу с данными,  поученными из ИПС (CARD_OPERATION).

drop index DXFK_PROV_PAY_SER_TO_PAY
/
drop index DXFK_PROV_PAY_SER_TO_PROV
/
create unique index SK_SERV_PROVIDER_PAYMENT_SERV on SERV_PROVIDER_PAYMENT_SERV (SERVICE_PROVIDER_ID, PAYMENT_SERVICE_ID)
/


-- Номер ревизии: 60289
-- Номер версии: 1.18
-- Комментарий: BUG070552: Наличие избыточных тех. перерывов при ошибке синхронизации блоков
create index I_TECHNOBREAKS on TECHNOBREAKS (
   ADAPTER_UUID ASC,
   STATUS ASC,
   TO_DATE ASC
)
go


-- Номер ревизии: 60453
-- Номер версии: 1.18
-- Комментарий:  BUG068530: Убрать фулсканы с таблицы ORDERS
create index ORDERS_STATUS on ORDERS (
   STATUS ASC,
   NOTIFICATION_TIME ASC,
   SYSTEM_NAME ASC
)
go

-- Номер ревизии: 60433
-- Номер версии: 1.18
-- Комментарий:  BUG069188: Не хватает кнопки репликации на части страниц с настройками
insert into PROPERTIES (id, property_key, property_value, category)
select S_PROPERTIES.nextval , 'com.rssl.business.simple.' || key property_key, value property_value, 'phiz' category
from BUSINESS_PROPERTIES
where kind='C' and value is not null
go

insert into PROPERTIES (id, property_key, property_value, category)
select S_PROPERTIES.nextval , 'com.rssl.business.PaymentRestriction.' || key property_key, value property_value, 'phiz'  category
from BUSINESS_PROPERTIES
where kind='D' and value is not null
go

insert into PROPERTIES (id, property_key, property_value, category)
select S_PROPERTIES.nextval , 'com.rssl.business.LoanReceptionTimeProperty.' || key property_key, FROM_TIME || '-' || TO_TIME || '-' || value property_value, 'phiz' category
from BUSINESS_PROPERTIES
where kind='A' and value is not null
go

delete from BUSINESS_PROPERTIES where KIND in ('A', 'C', 'D')
go

-- Номер ревизии: 60483
-- Номер версии: 1.18
-- Комментарий:  BUG070976: Не отображается баннер по предодобренной карте\кредиту
alter table PERSONAL_OFFER_NOTIFICATION modify NAME varchar2(40)
go

-- Номер ревизии: 60467
-- Номер версии: 1.18
-- Комментарий: Перевести WebAPI на использование http-сессии.
drop table WEBAPI_SESSIONS;

-- Номер ревизии: 60492
-- Номер версии: 1.18
-- Комментарий: Возможность скрыть конкретную операцию из списка расходов
alter table CARD_OPERATIONS add HIDDEN char(1)
/

-- Номер ревизии: 60509
-- Номер версии: 1.18
-- Комментарий: Линковка операций

alter table CARD_OPERATIONS modify CATEGORY_ID null
/


-- Номер ревизии: 60601
-- Номер версии: 1.18
-- Комментарий: BUG064410: Доработать отображение Логина, Даты последнего входа и Способа аутентификации
alter table LOGINS add LAST_LOGON_TYPE VARCHAR2(15);
alter table LOGINS add LAST_LOGON_PARAMETER VARCHAR2(350);

-- Номер ревизии: 60644
-- Номер версии: 1.18
-- Комментарий: Профиль: пользовательские настройки

create table USER_PROPERTIES (
    ID INTEGER NOT NULL,
    USER_ID INTEGER NOT NULL,
    PROPERTY_KEY VARCHAR2(256) NOT NULL,
    PROPERTY_VALUE VARCHAR2(256),

    constraint PK_USER_PROPERTIES primary key (ID)
)
go

create sequence S_USER_PROPERTIES
go

create unique index IDX_LOGIN_USER_PROPERTIES on USER_PROPERTIES (
    USER_ID asc,
    PROPERTY_KEY asc
)
go

insert into USER_PROPERTIES (ID, USER_ID, PROPERTY_KEY, PROPERTY_VALUE)
select
    S_USER_PROPERTIES.nextVal as ID,
    (select ID from USERS u where u.LOGIN_ID = p.LOGIN_ID) as USER_ID,
    'com.rssl.phizic.userSettings.showBanner' as PROPERTY_KEY,
    'false' as PROPERTY_VALUE
from PROFILE p where p.SHOW_BANNER = '0'
go

insert into USER_PROPERTIES (ID, USER_ID, PROPERTY_KEY, PROPERTY_VALUE)
select
    S_USER_PROPERTIES.nextVal as ID,
    (select ID from USERS u where u.LOGIN_ID = p.LOGIN_ID) as USER_ID,
    'com.rssl.phizic.userSettings.bankOfferViewed' as PROPERTY_KEY,
    'false' as PROPERTY_VALUE
from PROFILE p where p.IS_BANK_OFFER_VIEWED = '0'
go

insert into USER_PROPERTIES (ID, USER_ID, PROPERTY_KEY, PROPERTY_VALUE)
select
    S_USER_PROPERTIES.nextVal as ID,
    (select ID from USERS u where u.LOGIN_ID = p.LOGIN_ID) as USER_ID,
    'com.rssl.phizic.userSettings.showWidget' as PROPERTY_KEY,
    'false' as PROPERTY_VALUE
from PROFILE p where p.SHOW_WIDGET = '0'
go

insert into USER_PROPERTIES (ID, USER_ID, PROPERTY_KEY, PROPERTY_VALUE)
select
    S_USER_PROPERTIES.nextVal as ID,
    (select ID from USERS u where u.LOGIN_ID = p.LOGIN_ID) as USER_ID,
    'com.rssl.phizic.userSettings.hintsRead' as PROPERTY_KEY,
    'true' as PROPERTY_VALUE
from PROFILE p where p.HINT_READ = '1'
go

insert into USER_PROPERTIES (ID, USER_ID, PROPERTY_KEY, PROPERTY_VALUE)
select
    S_USER_PROPERTIES.nextVal as ID,
    p.ID as USER_ID,
    'com.rssl.phizic.userSettings.useInternetSecurity' as PROPERTY_KEY,
    'true' as PROPERTY_VALUE
from USERS p where p.USE_INTERNET_SECURITY = '1'
go

insert into USER_PROPERTIES (ID, USER_ID, PROPERTY_KEY, PROPERTY_VALUE)
select
    S_USER_PROPERTIES.nextVal as ID,
    p.ID as USER_ID,
    'com.rssl.phizic.userSettings.useOfert' as PROPERTY_KEY,
    'true' as PROPERTY_VALUE
from USERS p where p.USE_OFERT = '1'
go

alter table PROFILE drop column SHOW_BANNER
go

alter table PROFILE drop column IS_BANK_OFFER_VIEWED
go

alter table PROFILE drop column SHOW_WIDGET
go

alter table PROFILE drop column HINT_READ
go

alter table USERS drop column USE_INTERNET_SECURITY
go

alter table USERS drop column USE_OFERT
go


-- Номер ревизии: 60654
-- Номер версии: 1.18
-- Комментарий: Привязка категорий и цветов

alter table CARD_OPERATION_CATEGORIES add COLOR VARCHAR2(6)
go

-- Номер ревизии: 60666
-- Номер версии: 1.18
-- Комментарий: WebAPI. Сделать страницу переадресации на СБОЛ 3.
create table WEBAPI_TOKENS
(
   TOKEN                VARCHAR2(32)         not null,
   LOGIN_ID             INTEGER              not null,
   CREATION_DATE        TIMESTAMP            not null,
   constraint PK_WEBAPI_TOKENS primary key (TOKEN)
)
go


-- Номер ревизии: 60699
-- Номер версии: 1.18
-- Комментарий: Корзина платежей. Объект учета, подписка (услуга), счет (инвойс)

/*==============================================================*/
/* Table: ACCOUNTING_ENTITY                                     */
/*==============================================================*/
create table ACCOUNTING_ENTITY
(
   ID                   integer              not null,
   TYPE                 VARCHAR2(20)         not null,
   NAME                 VARCHAR2(100)        not null,
   LOGIN_ID             INTEGER              not null,
   CAR_STATE_NUMBER     VARCHAR2(20),
   CAR_CERTIFICATE_NUMBER VARCHAR2(10),
   CAR_CERTIFICATE_BATCH VARCHAR2(5),
   CAR_CERTIFICATE_ISSUED_SOURCE VARCHAR2(50),
   CAR_CERTIFICATE_ISSUED_DATE TIMESTAMP,
   POSTALCODE           VARCHAR2(6),
   PROVINCE             VARCHAR2(30),
   DISTRICT             VARCHAR2(30),
   CITY                 VARCHAR2(30),
   STREET               VARCHAR2(30),
   HOUSE                VARCHAR2(10),
   BUILDING             VARCHAR2(5),
   FLAT                 VARCHAR2(5),
   ADDRESS_FULL         VARCHAR2(200),
   constraint PK_ACCOUNTING_ENTITY primary key (ID)
)

go

create sequence S_ACCOUNTING_ENTITY
go

/*==============================================================*/
/* Table: INVOICES                                              */
/*==============================================================*/
create table INVOICES
(
   ID                   INTEGER              not null,
   AUTOPAY_ID           VARCHAR2(100)        not null,
   AUTOPAY_SUBSCRIPTION_ID VARCHAR2(100)        not null,
   STATE                VARCHAR2(10)         not null,
   STATE_DESC           VARCHAR2(100)        not null,
   COMMISSION           NUMBER(19,4),
   EXCE_PAYMENT_DATE    TIMESTAMP            not null,
   NON_EXEC_REASON_CODE VARCHAR2(4),
   NON_EXEC_REASON_DESC VARCHAR2(100),
   CODE_RECIPIENT_BS    VARCHAR2(20),
   REC_NAME             VARCHAR2(100),
   CODE_SERVICE         VARCHAR2(20),
   NAME_SERVICE         VARCHAR2(100),
   REC_INN              VARCHAR2(40)         not null,
   REC_COR_ACCOUNT      VARCHAR2(20),
   REC_KPP              VARCHAR2(10),
   REC_BIC              VARCHAR2(10)         not null,
   REC_ACCOUNT          VARCHAR2(20)         not null,
   REC_TB               VARCHAR2(2)          not null,
   REQUSITES            CLOB                 not null,
   constraint PK_INVOICES primary key (ID)
)

go

create sequence S_INVOICES
go

/*==============================================================*/
/* Table: INVOICE_SUBSCRIPTIONS                                 */
/*==============================================================*/
create table INVOICE_SUBSCRIPTIONS
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   ACCOUNTING_ENTITY_ID INTEGER,
   NAME                 VARCHAR2(100)        not null,
   AUTOPAY_ID           VARCHAR2(100),
   REQUEST_ID           VARCHAR2(100)        not null,
   START_DATE           TIMESTAMP            not null,
   CHANNEL_TYPE         VARCHAR2(15)         not null,
   TB                   VARCHAR2(4)          not null,
   EXECUTION_EVENT_TYPE VARCHAR2(40)         not null,
   PAY_DAY              INTEGER              not null,
   CHARGE_OFF_CARD      VARCHAR2(25)         not null,
   STATE                VARCHAR2(15)         not null,
   CODE_RECIPIENT_BS    VARCHAR2(20),
   REC_NAME             VARCHAR2(100),
   CODE_SERVICE         VARCHAR2(20),
   NAME_SERVICE         VARCHAR2(100),
   REC_INN              VARCHAR2(40)         not null,
   REC_COR_ACOUNT       VARCHAR2(20),
   REC_KPP              VARCHAR2(10),
   REC_BIC              VARCHAR2(10)         not null,
   REC_ACCOUNT          VARCHAR2(20)         not null,
   REC_NAME_ON_BILL     VARCHAR2(100),
   REC_PHONE_NUMBER     VARCHAR2(11),
   REC_TB               VARCHAR2(2)          not null,
   REQUISITES           CLOB                 not null,
   RECIPIENT_ID         INTEGER,
   ERROR_DESC           VARCHAR2(256),
   constraint PK_INVOICE_SUBSCRIPTIONS primary key (ID)
)

go

create sequence S_INVOICE_SUBSCRIPTIONS
go

/*==============================================================*/
/* Index: INDEX_INVOICE_SUB_AUTOPAY_ID                          */
/*==============================================================*/
create index INDEX_INVOICE_SUB_AUTOPAY_ID on INVOICE_SUBSCRIPTIONS (
   AUTOPAY_ID ASC
)
go

/*==============================================================*/
/* Index: INDEX_INVOICE_SUB_REQUEST_ID                          */
/*==============================================================*/
create index INDEX_INVOICE_SUB_REQUEST_ID on INVOICE_SUBSCRIPTIONS (
   REQUEST_ID ASC
)
go

create index "DXFK_ACC_ENTITY_TO_LOGIN" on ACCOUNTING_ENTITY
(
   LOGIN_ID
)
go

alter table ACCOUNTING_ENTITY
   add constraint FK_ACCOUNTI_FK_ACC_EN_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go


create index "DXFK_INVOICE_SUB_TO_LOGIN" on INVOICE_SUBSCRIPTIONS
(
   LOGIN_ID
)
go

alter table INVOICE_SUBSCRIPTIONS
   add constraint FK_INVOICE__FK_INVOIC_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go

-- Номер ревизии: 60709
-- Номер версии: 1.18
-- Комментарий: Расширенная заявка на кредит: переход от использования кодов к использованию флажков из справочников
ALTER TABLE LOANCLAIM_WORK_ON_CONTRACT RENAME COLUMN BUSINESS_DESC_REQUIRED TO PRIVATE_PRACTICE
;
ALTER TABLE LOANCLAIM_WORK_ON_CONTRACT RENAME COLUMN UNEMPLOYED TO PENSIONER
;

-- Номер ревизии: 60699
-- Номер версии: 1.18
-- Комментарий: Корзина платежей. Тип ошибки, возможной при создании подписки.

alter table INVOICE_SUBSCRIPTIONS add (ERROR_TYPE varchar2(20))
go

-- Номер ревизии: 60736
-- Номер версии: 1.18
-- Комментарий: Справочник кодов стран
create table COUNTRY_CODES
(
    ISO3 varchar2(3) not null,
    ISO2 varchar2(2) not null,
    NAME varchar2(256) not null,
    constraint PK_COUNTRY_CODES primary key(ISO3)
)
/

create unique index IDX_COUNTRY_CODES on COUNTRY_CODES( ISO2 asc)
/
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Австралия', 'AU', 'AUS') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Австрия', 'AT', 'AUT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Азербайджан', 'AZ', 'AZE') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Аландские острова', 'AX', 'ALA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Албания', 'AL', 'ALB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Алжир', 'DZ', 'DZA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Американские Виргинские острова', 'VI', 'VIR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Американское Самоа', 'AS', 'ASM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Ангилья', 'AI', 'AIA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Ангола', 'AO', 'AGO') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Андорра', 'AD', 'AND') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Антарктида', 'AQ', 'ATA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Антигуа и Барбуда', 'AG', 'ATG') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Аргентина', 'AR', 'ARG') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Армения', 'AM', 'ARM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Аруба', 'AW', 'ABW') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Афганистан', 'AF', 'AFG') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Багамы', 'BS', 'BHS') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Бангладеш', 'BD', 'BGD') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Барбадос', 'BB', 'BRB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Бахрейн', 'BH', 'BHR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Белиз', 'BZ', 'BLZ') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Белоруссия', 'BY', 'BLR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Бельгия', 'BE', 'BEL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Бенин', 'BJ', 'BEN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Бермуды', 'BM', 'BMU') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Болгария', 'BG', 'BGR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Боливия', 'BO', 'BOL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Бонэйр, Синт-Эстатиус и Саба', 'BQ', 'BES') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Босния и Герцеговина', 'BA', 'BIH') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Ботсвана', 'BW', 'BWA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Бразилия', 'BR', 'BRA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Британская территория в Индийском океане', 'IO', 'IOT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Британские Виргинские острова', 'VG', 'VGB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Бруней', 'BN', 'BRN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Буркина-Фасо', 'BF', 'BFA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Бурунди', 'BI', 'BDI') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Бутан', 'BT', 'BTN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Вануату', 'VU', 'VUT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Ватикан', 'VA', 'VAT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Великобритания', 'GB', 'GBR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Венгрия', 'HU', 'HUN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Венесуэла', 'VE', 'VEN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Внешние малые острова (США)', 'UM', 'UMI') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Восточный Тимор', 'TL', 'TLS') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Вьетнам', 'VN', 'VNM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Габон', 'GA', 'GAB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гаити', 'HT', 'HTI') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гайана', 'GY', 'GUY') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гамбия', 'GM', 'GMB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гана', 'GH', 'GHA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гваделупа', 'GP', 'GLP') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гватемала', 'GT', 'GTM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гвиана', 'GF', 'GUF') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гвинея', 'GN', 'GIN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гвинея-Бисау', 'GW', 'GNB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Германия', 'DE', 'DEU') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гернси', 'GG', 'GGY') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гибралтар', 'GI', 'GIB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гондурас', 'HN', 'HND') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гонконг', 'HK', 'HKG') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гренада', 'GD', 'GRD') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гренландия', 'GL', 'GRL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Греция', 'GR', 'GRC') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Грузия', 'GE', 'GEO') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Гуам', 'GU', 'GUM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Дания', 'DK', 'DNK') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Джерси (остров)', 'JE', 'JEY') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Джибути', 'DJ', 'DJI') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Доминика', 'DM', 'DMA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Доминиканская Республика', 'DO', 'DOM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('ДР Конго', 'CD', 'COD') 
 go    
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Египет', 'EG', 'EGY') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Замбия', 'ZM', 'ZMB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Западная Сахара', 'EH', 'ESH') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Зимбабве', 'ZW', 'ZWE') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Израиль', 'IL', 'ISR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Индия', 'IN', 'IND') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Индонезия', 'ID', 'IDN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Иордания', 'JO', 'JOR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Ирак', 'IQ', 'IRQ') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Иран', 'IR', 'IRN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Ирландия', 'IE', 'IRL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Исландия', 'IS', 'ISL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Испания', 'ES', 'ESP') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Италия', 'IT', 'ITA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Йемен', 'YE', 'YEM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Кабо-Верде', 'CV', 'CPV') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Казахстан', 'KZ', 'KAZ') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Каймановы острова', 'KY', 'CYM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Камбоджа', 'KH', 'KHM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Камерун', 'CM', 'CMR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Канада', 'CA', 'CAN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Катар', 'QA', 'QAT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Кения', 'KE', 'KEN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Кипр', 'CY', 'CYP') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Киргизия', 'KG', 'KGZ') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Кирибати', 'KI', 'KIR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Китайская Республика', 'TW', 'TWN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('КНДР', 'KP', 'PRK') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('КНР', 'CN', 'CHN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Кокосовые острова', 'CC', 'CCK') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Колумбия', 'CO', 'COL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Коморы', 'KM', 'COM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Коста-Рика', 'CR', 'CRI') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Кот-д’Ивуар', 'CI', 'CIV') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Куба', 'CU', 'CUB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Кувейт', 'KW', 'KWT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Кюрасао', 'CW', 'CUW') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Лаос', 'LA', 'LAO') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Латвия', 'LV', 'LVA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Лесото', 'LS', 'LSO') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Либерия', 'LR', 'LBR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Ливан', 'LB', 'LBN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Ливия', 'LY', 'LBY') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Литва', 'LT', 'LTU') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Лихтенштейн', 'LI', 'LIE') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Люксембург', 'LU', 'LUX') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Маврикий', 'MU', 'MUS') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Мавритания', 'MR', 'MRT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Мадагаскар', 'MG', 'MDG') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Майотта', 'YT', 'MYT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Макао', 'MO', 'MAC') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Македония', 'MK', 'MKD') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Малави', 'MW', 'MWI') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Малайзия', 'MY', 'MYS') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Мали', 'ML', 'MLI') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Мальдивы', 'MV', 'MDV') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Мальта', 'MT', 'MLT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Марокко', 'MA', 'MAR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Мартиника', 'MQ', 'MTQ') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Маршалловы Острова', 'MH', 'MHL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Мексика', 'MX', 'MEX') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Микронезия', 'FM', 'FSM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Мозамбик', 'MZ', 'MOZ') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Молдавия', 'MD', 'MDA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Монако', 'MC', 'MCO') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Монголия', 'MN', 'MNG') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Монтсеррат', 'MS', 'MSR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Мьянма', 'MM', 'MMR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Намибия', 'NA', 'NAM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Науру', 'NR', 'NRU') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Непал', 'NP', 'NPL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Нигер', 'NE', 'NER') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Нигерия', 'NG', 'NGA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Нидерланды', 'NL', 'NLD') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Никарагуа', 'NI', 'NIC') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Ниуэ', 'NU', 'NIU') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Новая Зеландия', 'NZ', 'NZL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Новая Каледония', 'NC', 'NCL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Норвегия', 'NO', 'NOR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('ОАЭ', 'AE', 'ARE') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Оман', 'OM', 'OMN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Остров Буве', 'BV', 'BVT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Остров Мэн', 'IM', 'IMN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Острова Кука', 'CK', 'COK') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Остров Норфолк', 'NF', 'NFK') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Остров Рождества', 'CX', 'CXR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Острова Питкэрн', 'PN', 'PCN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Острова Святой Елены, Вознесения и Тристан-да-Кунья', 'SH', 'SHN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Пакистан', 'PK', 'PAK') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Палау', 'PW', 'PLW') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Государство Палестина', 'PS', 'PSE') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Панама', 'PA', 'PAN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Папуа — Новая Гвинея', 'PG', 'PNG') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Парагвай', 'PY', 'PRY') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Перу', 'PE', 'PER') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Польша', 'PL', 'POL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Португалия', 'PT', 'PRT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Пуэрто-Рико', 'PR', 'PRI') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Республика Конго', 'CG', 'COG') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Республика Корея', 'KR', 'KOR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Реюньон', 'RE', 'REU') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Россия', 'RU', 'RUS') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Руанда', 'RW', 'RWA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Румыния', 'RO', 'ROU') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сальвадор', 'SV', 'SLV') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Самоа', 'WS', 'WSM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сан-Марино', 'SM', 'SMR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сан-Томе и Принсипи', 'ST', 'STP') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Саудовская Аравия', 'SA', 'SAU') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Свазиленд', 'SZ', 'SWZ') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Северные Марианские острова', 'MP', 'MNP') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сейшельские Острова', 'SC', 'SYC') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сен-Бартелеми', 'BL', 'BLM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сен-Мартен', 'MF', 'MAF') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сен-Пьер и Микелон', 'PM', 'SPM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сенегал', 'SN', 'SEN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сент-Винсент и Гренадины', 'VC', 'VCT') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сент-Китс и Невис', 'KN', 'KNA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сент-Люсия', 'LC', 'LCA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сербия', 'RS', 'SRB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сингапур', 'SG', 'SGP') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Синт-Мартен', 'SX', 'SXM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сирия', 'SY', 'SYR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Словакия', 'SK', 'SVK') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Словения', 'SI', 'SVN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Соломоновы Острова', 'SB', 'SLB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сомали', 'SO', 'SOM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Судан', 'SD', 'SDN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('СССР', 'SU', 'SUN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Суринам', 'SR', 'SUR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('США', 'US', 'USA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Сьерра-Леоне', 'SL', 'SLE') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Таджикистан', 'TJ', 'TJK') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Таиланд', 'TH', 'THA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Танзания', 'TZ', 'TZA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Тёркс и Кайкос', 'TC', 'TCA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Того', 'TG', 'TGO') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Токелау', 'TK', 'TKL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Тонга', 'TO', 'TON') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Тринидад и Тобаго', 'TT', 'TTO') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Тувалу', 'TV', 'TUV') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Тунис', 'TN', 'TUN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Туркмения', 'TM', 'TKM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Турция', 'TR', 'TUR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Уганда', 'UG', 'UGA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Узбекистан', 'UZ', 'UZB') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Украина', 'UA', 'UKR') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Уоллис и Футуна', 'WF', 'WLF') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Уругвай', 'UY', 'URY') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Фарерские острова', 'FO', 'FRO') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Фиджи', 'FJ', 'FJI') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Филиппины', 'PH', 'PHL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Финляндия', 'FI', 'FIN') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Фолклендские острова', 'FK', 'FLK') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Франция', 'FR', 'FRA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Французская Полинезия', 'PF', 'PYF') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Французские Южные и Антарктические Территории', 'TF', 'ATF') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Херд и Макдональд', 'HM', 'HMD') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Хорватия', 'HR', 'HRV') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('ЦАР', 'CF', 'CAF') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Чад', 'TD', 'TCD') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Черногория', 'ME', 'MNE') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Чехия', 'CZ', 'CZE') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Чили', 'CL', 'CHL') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Швейцария', 'CH', 'CHE') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Швеция', 'SE', 'SWE') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Шпицберген и Ян-Майен', 'SJ', 'SJM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Шри-Ланка', 'LK', 'LKA') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Эквадор', 'EC', 'ECU') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Экваториальная Гвинея', 'GQ', 'GNQ') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Эритрея', 'ER', 'ERI') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Эстония', 'EE', 'EST') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Эфиопия', 'ET', 'ETH') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('ЮАР', 'ZA', 'ZAF') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Южная Георгия и Южные Сандвичевы острова', 'GS', 'SGS') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Южный Судан', 'SS', 'SSD') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Ямайка', 'JM', 'JAM') 
 go  
insert into COUNTRY_CODES (NAME, ISO2, ISO3) VALUES('Япония', 'JP', 'JPN') 
 go
 
 
-- Номер ревизии: 60808
-- Номер версии: 1.18
-- Комментарий: Добавить поле "страна"
alter table CARD_OPERATIONS add  ORIGINAL_COUNTRY  varchar2(3)
/
alter table CARD_OPERATIONS add  CLIENT_COUNTRY  varchar2(3)
/

-- Номер ревизии: 60827
-- Номер версии: 1.18
-- Комментарий: BUG071129  Расширенная заявка: Стаж работы на текущем месте
delete from LOANCLAIM_JOB_EXPERIENCE;
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('1', 'от 6 до 12 мес', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('2', 'от 1 года до 3 лет', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('3', 'от 3 до 5 лет', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('4', 'от 5 до 10 лет', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('5', 'от 10 до 20 лет', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('6', 'более 20 лет', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('7', 'от 3 до 6 месяцев', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('8', 'менее 3 месяцев', 1);


-- Номер ревизии: 60931
-- Номер версии: 1.18
-- Комментарий: Отчет по автоматической перекатегоризации

alter table CARD_OPERATIONS add ORIGINAL_CATEGORY_NAME VARCHAR2(100)
go

alter table CARD_OPERATION_CATEGORIES add UUID VARCHAR2(32)
go

create unique index I_CARD_CATEGORIES_UUID on CARD_OPERATION_CATEGORIES(
    UUID
)
go

update CARD_OPERATION_CATEGORIES set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table CARD_OPERATION_CATEGORIES modify UUID not null
go

-- Номер ревизии: 60984
-- Номер версии: 1.18
-- Комментарий: BUG069044: Ошибка при добавлении алиаса

alter table "PROVIDER_SMS_ALIAS" modify "NAME" varchar2(20);


-- Номер ревизии: 60986
-- Номер версии: 1.18
-- Комментарий: Связь справочника видов (групп) услуг и справочника категорий в АЛФ(многоблочный режим) (ИОК)

alter table CARD_OPERATION_CATEGORIES modify UUID null
go

update CARD_OPERATION_CATEGORIES  set UUID = null
where LOGIN_ID is not null
go


-- Номер ревизии: 61045
-- Номер версии: 1.18
-- Комментарий: Профиль: Работа с аватарами, пользовательские документы
create table USER_ADDED_DOCUMENTS (
    ID INTEGER NOT NULL, 
    USER_ID INTEGER NOT NULL, 
    DOCUMENT_TYPE VARCHAR2(10) NOT NULL, 
    DOC_NAME varchar2(200),
    SERIES VARCHAR2(4), 
    DOC_NUMBER VARCHAR2(20),
    ISSUE_DATE TIMESTAMP,
    EXPIRE_DATE TIMESTAMP,
    ISSUE_BY VARCHAR2(150),
  
    constraint PK_USER_ADDED_DOCUMENTS primary key (ID)
)
go

create sequence S_USER_ADDED_DOCUMENTS
go

create unique index IDX_DOC_TYPE_USER_ID on USER_ADDED_DOCUMENTS (
    USER_ID asc,
    DOCUMENT_TYPE asc
)
go

create table USER_IMAGES (
    ID integer not null,
    IDENTIFY_BY varchar2(200) not null,
    UUID varchar2(32) not null,
    PATH varchar2(256) not null, 
    USER_ID integer not null, 
    KIND varchar2(10) not null,
	FILE_MOVED char(1) default '0' not null,

    constraint PK_USER_IMAGES primary key (ID)
)
go

create sequence S_USER_IMAGES
go

create unique index IDX_USER_IMAGES on USER_IMAGES (
    USER_ID asc, 
    IDENTIFY_BY asc
)
go

alter table USER_IMAGES add constraint FK_USER_IMAGES_TO_USERS foreign key (USER_ID) references USERS(ID)
go

create index "DXFK_USER_IMAGES_TO_USERS" on USER_IMAGES (
    USER_ID
)
go

-- Номер ревизии: 61133
-- Номер версии: 1.18
-- Комментарий: Управление признаком отправки рекламы 
update ERMB_PROFILE set ADVERTISING = '1' WHERE ADVERTISING='0'
go


-- Номер ревизии: 61153
-- Номер версии: 1.18
-- Комментарий: Управление подпиской. Сохранение данных в линке
alter table CARD_LINKS ADD USE_REPORT_DELIVERY CHAR(1)
go
alter table CARD_LINKS ADD EMAIL_ADDRESS VARCHAR2(40)
go
alter table CARD_LINKS ADD REPORT_DELIVERY_TYPE VARCHAR2(4)
go
alter table CARD_LINKS ADD REPORT_DELIVERY_LANGUAGE VARCHAR2(2)
go

-- Номер ревизии: 60654
-- Номер версии: 1.18
-- Комментарий: Привязка категорий и цветов

update CARD_OPERATION_CATEGORIES set COLOR='72bf44'
where NAME='Автомобиль' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='8967b0'
where NAME='Перевод с карты' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='47b082'
where NAME='Выдача наличных' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='f9b37b'
where NAME='Коммунальные платежи, связь, интернет' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='fff450'
where NAME='Здоровье и красота' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='009597'
where NAME='Одежда и аксессуары' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='74489d'
where NAME='Образование' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='d4711a'
where NAME='Отдых и развлечения' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='89c765'
where NAME='Супермаркеты' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='00599d'
where NAME='Прочие расходы' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='aa55a1'
where NAME='Транспорт' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='ed1c24'
where NAME='Комиссия' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='d89016'
where NAME='Погашение кредитов' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='ce181e'
where NAME='Путешествия' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='f58220'
where NAME='Все для дома' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='62a73b'
where NAME='Рестораны и кафе' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='0066b3'
where NAME='Искусство' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='f04e4c'
where NAME='Перевод на вклад' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='faa61a'
where NAME='Перевод между своими картами' and INCOME='0' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='fecd7f'
where NAME='Траты наличными' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='00a65d'
where NAME='Внесение наличных' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='1c3687'
where NAME='Зачисления' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='cf3734'
where NAME='Перевод на карту' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='e3d200'
where NAME='Возврат, отмена операций' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='00aaad'
where NAME='Прочие поступления' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='8e187c'
where NAME='Перевод со вклада' and LOGIN_ID is null
/
update CARD_OPERATION_CATEGORIES set COLOR='00b274'
where NAME='Перевод между своими картами' and INCOME='1' and LOGIN_ID is null
/

update CARD_OPERATION_CATEGORIES categories set categories.COLOR =
   (CASE mod(ROWNUM-1,30)
    WHEN 0 THEN '21409a'
    WHEN 1 THEN 'f3715a'
    WHEN 2 THEN 'fff200'
    WHEN 3 THEN '00b6be'
    WHEN 4 THEN '5c2d91'
    WHEN 5 THEN 'f79548'
    WHEN 6 THEN '009252'
    WHEN 7 THEN '1b75bc'
    WHEN 8 THEN 'a3238e'
    WHEN 9 THEN '51247f'
    WHEN 10 THEN 'fdba4d'
    WHEN 11 THEN '99d178'
    WHEN 12 THEN 'f8a55e'
    WHEN 13 THEN '4791c8'
    WHEN 14 THEN 'bd60ad'
    WHEN 15 THEN 'fff781'
    WHEN 16 THEN '47b3b4'
    WHEN 17 THEN '9b7bb8'
    WHEN 18 THEN 'e0995a'
    WHEN 19 THEN 'aad790'
    WHEN 20 THEN 'c284bb'
    WHEN 21 THEN 'f25b61'
    WHEN 22 THEN 'e3af57'
    WHEN 23 THEN '8161a3'
    WHEN 24 THEN '5b6ea8'
    WHEN 25 THEN 'dc6f6d'
    WHEN 26 THEN 'ebdf47'
    WHEN 27 THEN '47c2c4'
    WHEN 28 THEN 'dc585d'
    ELSE '5a9bcf'
    END)
where categories.LOGIN_ID is not null
/

alter table CARD_OPERATION_CATEGORIES modify COLOR not null
/

-- Номер ревизии: 61305
-- Номер версии: 1.18
-- Комментарий: Корзина платежей. Инвойс. Добавил поле "отложенная дата платежа".

alter table INVOICES add (DELAYED_PAY_DATE timestamp)
go


-- Номер ревизии: 60654
-- Номер версии: 1.18
-- Комментарий: Привязка категорий и цветов (ИОК)

alter table CARD_OPERATION_CATEGORIES modify COLOR null
/

-- Номер ревизии: 61366
-- Номер версии: 1.18
-- Комментарий: Сохранение логинов клиента из соц. сетей. Модель БД.

create table USER_SOCIAL_IDS 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   SOCIAL_NETWORK_TYPE  VARCHAR2(4)          not null,
   SOCIAL_NETWORK_ID    VARCHAR2(50)         not null,
   constraint PK_USER_SOCIAL_IDS primary key (ID)
)

go

create sequence S_USER_SOCIAL_IDS
go


create index "DXUSER_SOC_IDS_TO_LOGINS" on USER_SOCIAL_IDS
(
   LOGIN_ID
)
go

alter table USER_SOCIAL_IDS
   add constraint FK_USER_SOC_IDS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
go


-- Номер ревизии: 61370
-- Номер версии: 1.18
-- Комментарий: Профиль: Сохранение аватара. Работа с аватарами и настройками пользователя

alter table USER_IMAGES drop column USER_ID
go

alter table USER_IMAGES drop column UUID
go

alter table USER_IMAGES drop column FILE_MOVED
go

alter table USER_IMAGES drop column IDENTIFY_BY
go

alter table USER_IMAGES drop column KIND
go

alter table PROFILE add AVATAR_IMAGE_ID integer
go

alter table PROFILE add constraint FK_PROFILE_FK_AVATAR_USER_IMA foreign key (AVATAR_IMAGE_ID)
   references USER_IMAGES(ID)
go

create index "DXFK_PR_AVATAR_IMAGE_ID" on PROFILE (
    AVATAR_IMAGE_ID asc
)
go

-- Номер ревизии: 61396
-- Номер версии: 1.18
-- Комментарий: Корзина платежей

ALTER TABLE INVOICE_SUBSCRIPTIONS
	ADD ( NOT_PAID_INVOICES NUMBER(15,5) NULL, 
	DELAYED_INVOICES NUMBER(15,5) NULL, 
	DELAY_DATE TIMESTAMP NULL ) 
GO

-- Номер ревизии: 61504
-- Номер версии: 1.18
-- Комментарий: Актуализация текстов сообщений

alter table NOTIFICATIONS add DOCUMENT_STATE varchar2(25) null
go
alter table NOTIFICATIONS add DOCUMENT_TYPE varchar2(50) null
go
alter table NOTIFICATIONS add NAME_AUTO_PAYMENT varchar2(100) null
go
alter table NOTIFICATIONS add RECIPIENT_ACCOUNT_NUMBER varchar2(25) null
go
alter table NOTIFICATIONS add RECIPIENT_ACCOUNT_TYPE varchar2(32) null
go
update NOTIFICATIONS set DOCUMENT_STATE='EXECUTED' where NAME='PaymentExecuteNotification' or
NAME='AccountRestChangeLowNotification' or NAME='AccountRestChangeNotification' or NAME='LossPassbookNotification' or
NAME='RefuseLongOfferNotification' or NAME='LongOfferNotification'
go
update NOTIFICATIONS set DOCUMENT_TYPE='CardJurIntraBankTransfer' where NAME='PaymentExecuteNotification'
go
update NOTIFICATIONS set DOCUMENT_TYPE='CardJurIntraBankTransfer' where NAME='AccountRestChangeLowNotification'
go
update NOTIFICATIONS set DOCUMENT_TYPE='CardJurIntraBankTransfer' where NAME='AccountRestChangeNotification'
go
update NOTIFICATIONS set DOCUMENT_TYPE='LossPassbookApplicationClaim' where NAME='LossPassbookNotification'
go
update NOTIFICATIONS set DOCUMENT_TYPE='RefuseAutoPayment' where NAME='RefuseLongOfferNotification'
go
update NOTIFICATIONS set DOCUMENT_TYPE='CreateAutoPayment' where NAME='LongOfferNotification'
go
update NOTIFICATIONS set NAME='PaymentExecuteNotification' where
NAME='AccountRestChangeLowNotification' or NAME='AccountRestChangeNotification' or NAME='LossPassbookNotification' or
NAME='RefuseLongOfferNotification' or NAME='LongOfferNotification'
go

-- Номер ревизии: 61525
-- Номер версии: 1.18
-- Комментарий: ИОК в модели БД. Корзина платежей.

delete from INVOICE_SUBSCRIPTIONS 
go
alter table INVOICE_SUBSCRIPTIONS add (PAY_DATE timestamp not null)
go
alter table INVOICE_SUBSCRIPTIONS drop column PAY_DAY
go
alter table INVOICES rename column EXCE_PAYMENT_DATE to EXEC_PAYMENT_DATE
go
alter table INVOICES rename column REQUSITES to REQUISITES 
go
alter table INVOICES add (REC_PHONE_NUMBER VARCHAR2(11))
go
alter table INVOICES add (REC_NAME_ON_BILL VARCHAR2(100))
go

-- Номер ревизии: 61537
-- Номер версии: 1.18
-- Комментарий: Информирование клиента: статус по операциям

alter table USERS drop column MESSAGE_SERVICE
go
alter table SUBSCRIPTIONS add NOTIFICATION_TYPE varchar2(30) 
go
alter table SUBSCRIPTIONS add CHANNEL varchar2(10)  
go
alter table SUBSCRIPTIONS modify LOGIN_ID not null
go


update SUBSCRIPTIONS subs set CHANNEL=(SELECT CHANNEL FROM DISTRIBUTION_CHANNEL dcha
join SUBSCRIPTION_TEMPLATES subtem on subtem.TEMPLATE_ID=dcha.ID
where subtem.SUBSCRIPTION_ID=subs.ID)
go

update SUBSCRIPTIONS set NOTIFICATION_TYPE='operationNotification'
go
alter table SUBSCRIPTIONS modify NOTIFICATION_TYPE not null
go
alter table SUBSCRIPTIONS modify CHANNEL not null
go
alter table SUBSCRIPTIONS drop column SCHEDULE_ID
go

create unique index UNIQ_SUBSCRIPTION on SUBSCRIPTIONS (
   LOGIN_ID ASC,
   NOTIFICATION_TYPE ASC
)
go

drop index UK_3
go



CREATE TABLE PAYMENT_NOTIFICATIONS
(
   ID                   INTEGER         not null,
   LOGIN                INTEGER 	not null,
   DATE_CREATED         TIMESTAMP 	not null,
   NAME                 VARCHAR2(63)    not null,
   DOCUMENT_STATE 	VARCHAR2(25)    not null,
   ACCOUNT_NUMBER       VARCHAR2(25),
   ACCOUNT_RESOURCE_TYPE VARCHAR2(32),
   TRANSACTION_SUM      NUMBER(15,4),
   CURRENCY             VARCHAR2(5),
   NAME_OR_TYPE         VARCHAR2(56),
   DOCUMENT_TYPE 	VARCHAR2(50),
   NAME_AUTO_PAYMENT    VARCHAR2(100),
   RECIPIENT_ACCOUNT_NUMBER VARCHAR2(25),
   RECIPIENT_ACCOUNT_TYPE VARCHAR2(32)
)
partition by range
 (DATE_CREATED)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST
        values less than (to_date('15-05-2014','DD-MM-YYYY')))
go


create sequence S_PAYMENT_NOTIFICATIONS
go

create index IND_PAY_NOTIF_ID on PAYMENT_NOTIFICATIONS (
   ID ASC
)
local
go

create index IND_PAY_NOTIF1 on PAYMENT_NOTIFICATIONS (
   LOGIN ASC,
   DATE_CREATED ASC
)
local
go

create index IND_PAY_NOTIF2 on PAYMENT_NOTIFICATIONS (
   DATE_CREATED ASC,   
   LOGIN ASC
)
local
go



insert into PAYMENT_NOTIFICATIONS (ID, LOGIN,DATE_CREATED,NAME,DOCUMENT_STATE,ACCOUNT_NUMBER,ACCOUNT_RESOURCE_TYPE,TRANSACTION_SUM,
        CURRENCY,NAME_OR_TYPE,DOCUMENT_TYPE,NAME_AUTO_PAYMENT,RECIPIENT_ACCOUNT_NUMBER,RECIPIENT_ACCOUNT_TYPE)  
select S_PAYMENT_NOTIFICATIONS.nextval, LOGIN,DATE_CREATED,NAME,DOCUMENT_STATE,ACCOUNT_NUMBER,ACCOUNT_RESOURCE_TYPE,TRANSACTION_SUM,
        CURRENCY,NAME_OR_TYPE,DOCUMENT_TYPE,NAME_AUTO_PAYMENT,RECIPIENT_ACCOUNT_NUMBER,RECIPIENT_ACCOUNT_TYPE from NOTIFICATIONS

go

drop table NOTIFICATIONS
go
drop sequence S_NOTIFICATIONS
go
drop table SCHEDULE
go
drop sequence S_SCHEDULE
go
drop table DISTRIBUTIONS
go
drop sequence S_DISTRIBUTIONS
go
drop table DISTRIBUTION_CHANNEL
go
drop sequence S_DISTRIBUTION_CHANNEL
go
drop table SUBSCRIPTION_TEMPLATES
go
drop sequence S_SUBSCRIPTION_TEMPLATES
go
drop table SUBSCRIPTION_PARAMETERS
go
drop sequence S_SUBSCRIPTION_PARAMETERS
go
drop table ACCOUNT_PARAMETERS
go
drop sequence S_ACCOUNT_PARAMETERS
go
drop table CARD_PARAMETERS
go
drop sequence S_CARD_PARAMETERS

-- Номер ревизии: 61541
-- Номер версии: 1.18
-- Комментарий: Изменение тарифного плана SMS запросом

alter table ermb_tarif add STATUS varchar2(12) null;
update ermb_tarif set STATUS = 'ACTIVE';
alter table ermb_tarif modify STATUS varchar2(12) not null;


-- Номер ревизии: 61560
-- Номер версии: 1.18
-- Комментарий: ФОС. схема сотрудника, работающего с письмами
ALTER TABLE ACCESSSCHEMES ADD MAIL_MANAGEMENT char(1)
go

UPDATE ACCESSSCHEMES SET MAIL_MANAGEMENT = '1' WHERE ID IN (SELECT SCHEME_ID FROM SCHEMESSERVICES JOIN SERVICES ON SERVICES.ID = SCHEMESSERVICES.SERVICE_ID WHERE SERVICES.SERVICE_KEY = 'MailManagment')
go

UPDATE ACCESSSCHEMES SET MAIL_MANAGEMENT = '0' WHERE MAIL_MANAGEMENT IS NULL
go

ALTER TABLE ACCESSSCHEMES MODIFY MAIL_MANAGEMENT NOT NULL
go


-- Номер ревизии: 61586
-- Номер версии: 1.18
-- Комментарий: Управление подпиской. clientId
alter table CARD_LINKS ADD CLIENT_ID VARCHAR2(255)
go


-- Номер ревизии: 61606
-- Номер версии: 1.18
-- Комментарий: ФОС. отключение работы с группами клиентов
DELETE FROM RECIPIENTS WHERE MAIL_ID IN (SELECT ID FROM MAIL WHERE RECIPIENT_TYPE = 'GROUP')
go

DELETE FROM MAIL WHERE RECIPIENT_TYPE = 'GROUP'
go

-- Номер ревизии: 61610
-- Номер версии: 1.18
-- Комментарий: Перенести каналы подписок клиента из профиля в таблицу подписок

insert into SUBSCRIPTIONS (id, LOGIN_ID, NOTIFICATION_TYPE, CHANNEL)
select S_SUBSCRIPTIONS.nextval , LOGIN_ID LOGIN_ID,'loginNotification' NOTIFICATION_TYPE, LOGON_NOTIFICATION_TYPE CHANNEL
from PROFILE 
where LOGON_NOTIFICATION_TYPE is not null
go

insert into SUBSCRIPTIONS (id, LOGIN_ID, NOTIFICATION_TYPE, CHANNEL)
select S_SUBSCRIPTIONS.nextval , LOGIN_ID LOGIN_ID,'mailNotification' NOTIFICATION_TYPE, MAIL_NOTIFICATION_TYPE CHANNEL
from PROFILE 
where MAIL_NOTIFICATION_TYPE is not null
go

alter table PROFILE DROP COLUMN LOGON_NOTIFICATION_TYPE
go

alter table PROFILE DROP COLUMN MAIL_NOTIFICATION_TYPE
go

-- Номер ревизии: 61614
-- Номер версии: 1.18
-- Комментарий: Добавление изображения  для цели
alter table ACCOUNT_TARGETS add IMAGE_ID NUMBER
/

-- Номер ревизии: 61676
-- Номер версии: 1.18
-- Комментарий: Структура БД. Таблицы для адресов

/* Table: LOANCLAIM_AREA                                        */
/*==============================================================*/
create table LOANCLAIM_AREA 
(
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(20)         not null,
   TYPEOFAREA           varchar2(20)         not null,
   REGION               varchar2(20)         not null,
   constraint PK_LOANCLAIM_AREA primary key (CODE)
);
/* Table: LOANCLAIM_CITY                                        */
/*==============================================================*/
create table LOANCLAIM_CITY 
(
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(20)         not null,
   TYPEOFCITY           varchar2(20)         not null,
   AREA                 varchar2(20)         not null,
   REGION               varchar2(20)         not null,
   constraint PK_LOANCLAIM_CITY primary key (CODE)
);
/* Table: LOANCLAIM_SETTLEMENT                                  */
/*==============================================================*/
create table LOANCLAIM_SETTLEMENT 
(
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(20)         not null,
   TYPEOFLOCALITY       varchar2(20)         not null,
   CITY                 varchar2(20)         not null,
   AREA                 varchar2(20)         not null,
   REGION               varchar2(20)         not null,
   constraint PK_LOANCLAIM_SETTLEMENT primary key (CODE)
);
/* Table: LOANCLAIM_STREET                                      */
/*==============================================================*/
create table LOANCLAIM_STREET 
(
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(20)         not null,
   TYPEOFSTREET         varchar2(20)         not null,
   SETTLEMENT           varchar2(20)         not null,
   CITY                 varchar2(20)         not null,
   AREA                 varchar2(20)         not null,
   REGION               varchar2(20)         not null,
   constraint PK_LOANCLAIM_STREET primary key (CODE)
);

create index "DXLC_AREA_TYPEOFAREA_FK" on LOANCLAIM_AREA
(
   TYPEOFAREA
);

alter table LOANCLAIM_AREA
   add constraint LC_AREA_TYPEOFAREA_FK foreign key (TYPEOFAREA)
      references LOANCLAIM_TYPE_OF_AREA (CODE);


create index "DXLC_CITY_TYPEOFCITY_FK" on LOANCLAIM_CITY
(
   TYPEOFCITY
);

alter table LOANCLAIM_CITY
   add constraint LC_CITY_TYPEOFCITY_FK foreign key (TYPEOFCITY)
      references LOANCLAIM_TYPE_OF_CITY (CODE);


create index "DXLC_SETTL_TYPEOFLOCALITY_FK" on LOANCLAIM_SETTLEMENT
(
   TYPEOFLOCALITY
);

alter table LOANCLAIM_SETTLEMENT
   add constraint LC_SETTL_TYPEOFLOCALITY_FK foreign key (TYPEOFLOCALITY)
      references LOANCLAIM_TYPE_OF_LOCALITY (CODE);


create index "DXLC_STREET_TYPEOFSTREET_FK" on LOANCLAIM_STREET
(
   TYPEOFSTREET
);

alter table LOANCLAIM_STREET
   add constraint LC_STREET_TYPEOFSTREET_FK foreign key (TYPEOFSTREET)
      references LOANCLAIM_TYPE_OF_STREET (CODE);

-- Номер ревизии: 61682
-- Номер версии: 1.18
-- Комментарий: Доработки оплаты документов с помощью штрих-кодов.

alter table regions add provider_code_mapi varchar2(200) null
go
alter table regions add provider_code_atm  varchar2(200) null
go

-- Номер ревизии: 61733
-- Номер версии: 1.18
-- Комментарий: BUG068530: Убрать фулсканы с таблицы ORDERS

drop index ORDERS_UEC_STATUS 
go
create index IDX_NOT_NOTIFIED_UEC on ORDERS (
   case when SYSTEM_NAME = 'UEC' and (STATUS='NOT_SEND' or  STATUS='ERROR') then '1' else null end ASC 
)


-- Номер ревизии: 61759
-- Номер версии: 1.18
-- Комментарий: Профиль: связка услуг и пользовательских документов.
create table INVOICES_FOR_USER_DOCUMENTS (
   LOGIN_ID integer not null,
   DOCUMENT_TYPE VARCHAR2(32) not null,
   INVOICE_SUBSCRIPTION_ID integer not null,
   
   constraint PK_INV_FR_USR_DOCS primary key (LOGIN_ID, DOCUMENT_TYPE, INVOICE_SUBSCRIPTION_ID)
)
go

alter table INVOICES_FOR_USER_DOCUMENTS 
	add constraint FK_INVC_FR_USR_DOCS_LGN_ID foreign key (LOGIN_ID) references LOGINS(ID)
go

alter table INVOICES_FOR_USER_DOCUMENTS
	add constraint FK_INVC_FR_USR_DOCS_INVSUBID foreign key (INVOICE_SUBSCRIPTION_ID) references INVOICE_SUBSCRIPTIONS (ID)
go

create index IDX_FK_USR_DOCS_INVSUBID on INVOICES_FOR_USER_DOCUMENTS (
	INVOICE_SUBSCRIPTION_ID asc
)
go 


-- Номер ревизии: 61776
-- Номер версии: 1.18
-- Комментарий: CHG072541: Доработка сохранения новостных подписок

alter table NEWS_DISTRIBUTIONS DROP COLUMN LAST_PROCESSED_ID
go
DELETE FROM SUBSCRIPTIONS WHERE channel = 'none' and NOTIFICATION_TYPE ='newsNotification'
go
alter table SUBSCRIPTIONS ADD TB VARCHAR2(4) null
go
UPDATE SUBSCRIPTIONS SET TB = (select TB from DEPARTMENTS join USERS on users.DEPARTMENT_ID = DEPARTMENTS.id where SUBSCRIPTIONS.login_id = users.login_id) 
go
ALTER TABLE SUBSCRIPTIONS MODIFY TB NOT NULL
go
create index SUBSCR_NEWS_IND on SUBSCRIPTIONS
(
   NOTIFICATION_TYPE ASC,
   TB ASC,
   LOGIN_ID ASC
)
go
insert into SUBSCRIPTIONS (id, LOGIN_ID, NOTIFICATION_TYPE, CHANNEL, TB)
select S_SUBSCRIPTIONS.nextval , LOGIN_ID,'newsNotification', 'email', TB
from NEWS_SUBSCRIPTIONS 
go
drop index NEWS_SUBSCRIPTIONS_TB_INDEX
go
drop table NEWS_SUBSCRIPTIONS 
go
drop sequence S_NEWS_SUBSCRIPTIONS
go


-- Номер ревизии: 61614
-- Номер версии: 1.18
-- Комментарий: ИОК
alter table ACCOUNT_TARGETS modify IMAGE_ID INTEGER
/

-- Номер ревизии: 61832
-- Номер версии: 1.18
-- Комментарий: BUG072720: Не находится профиль клиента в блоке.
create index LAST_LOGON_CARD_NUMBER_IDX on LOGINS (
   LAST_LOGON_CARD_NUMBER ASC
)
go

-- Номер ревизии: 61878
-- Номер версии: 1.18
-- Комментарий: Профиль. Конвертация БД.
alter table USER_ADDED_DOCUMENTS rename column USER_ID to LOGIN_ID
go
insert into USER_ADDED_DOCUMENTS (ID, LOGIN_ID, DOC_NUMBER, DOCUMENT_TYPE) 
select S_USER_ADDED_DOCUMENTS.nextVal as ID, u.LOGIN_ID as LOGIN_ID, u.SNILS as DOC_NUMBER, 'SNILS' as DOCUMENT_TYPE from USERS u where u.SNILS is not null
go
insert into USER_ADDED_DOCUMENTS (ID, LOGIN_ID, DOC_NUMBER, DOCUMENT_TYPE) 
select S_USER_ADDED_DOCUMENTS.nextVal as ID, u.LOGIN_ID as LOGIN_ID, u.INN as DOC_NUMBER, 'INN' as DOCUMENT_TYPE from USERS u where u.INN is not null
go
alter table USERS drop column INN
go
alter table USERS drop column SNILS
go


-- Номер ревизии: 61916
-- Номер версии: 1.18
-- Комментарий: ФОС. Выбор сотрудника для переназначения.

alter table ACCESSSCHEMES drop column MAIL_MANAGEMENT
go
-- Номер ревизии: 61961
-- Номер версии: 1.18
-- Комментарий: Доработки ЕРМБ. Абонентская плата. Доработка структуры БД

ALTER TABLE ERMB_PROFILE ADD FPP_UNLOAD_DATE TIMESTAMP null
go
ALTER TABLE ERMB_PROFILE ADD CHARGE_NEXT_DATE TIMESTAMP  null  
go
ALTER TABLE ERMB_PROFILE ADD CHARGE_NEXT_AMOUNT NUMBER null  
go
ALTER TABLE ERMB_PROFILE ADD CHARGE_NEXT_PERIOD NUMBER null  
go
ALTER TABLE ERMB_PROFILE ADD FPP_IN_PROGRESS CHAR(1)  default('0') not null
go
CREATE SEQUENCE S_PROCES_GROUP_ID
go
ALTER TABLE ERMB_PROFILE ADD PROCES_GROUP_ID NUMBER 
go
UPDATE ERMB_PROFILE SET PROCES_GROUP_ID  = S_PROCES_GROUP_ID.NEXTVAL 
go
ALTER TABLE ERMB_PROFILE MODIFY PROCES_GROUP_ID NOT NULL
go
ALTER TABLE ERMB_PROFILE ADD SERVICE_STATUS_TMP CHAR(1)  default('0') not null
go
UPDATE ERMB_PROFILE SET SERVICE_STATUS_TMP = '1' WHERE SERVICE_STATUS = 'ACTIVE'
go
ALTER TABLE ERMB_PROFILE ADD CLIENT_BLOCKED CHAR(1)  default('0') not null
go
UPDATE ERMB_PROFILE SET CLIENT_BLOCKED = '1' WHERE SERVICE_STATUS = 'LOCK'
go
ALTER TABLE ERMB_PROFILE ADD PAYMENT_BLOCKED CHAR(1)  default('0') not null
go
UPDATE ERMB_PROFILE SET PAYMENT_BLOCKED = '1' WHERE SERVICE_STATUS = 'NOT_PAID'
go
ALTER TABLE ERMB_PROFILE drop column SERVICE_STATUS
go
ALTER TABLE ERMB_PROFILE rename column SERVICE_STATUS_TMP to SERVICE_STATUS
go


-- Номер ревизии: 61970
-- Номер версии: 1.18
-- Комментарий: Одноклассники. Доработка сущности "мобильной платформы".

alter table MOBILE_PLATFORMS 
	add (IS_SOCIAL CHAR(1) default 0 not null,
		UNALLOWED_BROWSERS VARCHAR2(100),
		USE_CAPTCHA CHAR(1) default 1 not null);
	

-- Номер ревизии: 61979
-- Номер версии: 1.18
-- Комментарий: ФОС. Синхронизация справочников площадок и тематик сообщений
	
alter table MAIL_SUBJECTS add UUID VARCHAR2(32)
go

update MAIL_SUBJECTS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table MAIL_SUBJECTS modify UUID not null
go

create unique index I_MAIL_SUBJECTS_UUID on MAIL_SUBJECTS(
    UUID
)
go

alter table CONTACT_CENTER_AREAS add UUID VARCHAR2(32)
go

update CONTACT_CENTER_AREAS set UUID = DBMS_RANDOM.STRING('X', 32)
go

alter table CONTACT_CENTER_AREAS modify UUID not null
go

create unique index I_CONTACT_CENTER_AREAS_UUID on CONTACT_CENTER_AREAS(
    UUID
)
go

DROP index "DXFK_C_C_AREA_DEP_TO_C_C_AREA"
go


-- Номер ревизии: 62014
-- Номер версии: 1.18
-- Комментарий: Корзина платежей. Код биллинга в пописках. ИОК

delete from INVOICES
go
delete from INVOICE_SUBSCRIPTIONS
go
alter table INVOICE_SUBSCRIPTIONS add (CODE_BILLING varchar2(50) not null)
go
alter table INVOICE_SUBSCRIPTIONS rename column REC_COR_ACOUNT to REC_COR_ACCOUNT 
go

-- Номер ревизии: 62011
-- Номер версии: 1.18
-- Комментарий: Форма "счета к оплате"
DROP TABLE INVOICES
go

create table INVOICES 
(
   ID                      INTEGER              not null,
   INVOICE_SUBSCRIPTION_ID INTEGER,
   LOGIN_ID                INTEGER              not null,
   AUTOPAY_ID              VARCHAR2(100)        not null,
   STATE                   VARCHAR2(10)         not null,
   STATE_DESC              VARCHAR2(100)        not null,
   COMMISSION              NUMBER(19,4),
   EXEC_PAYMENT_DATE       TIMESTAMP            not null,
   NON_EXEC_REASON_CODE    VARCHAR2(4),
   NON_EXEC_REASON_DESC    VARCHAR2(100),
   CODE_RECIPIENT_BS       VARCHAR2(20),
   REC_NAME                VARCHAR2(100),
   CODE_SERVICE            VARCHAR2(20),
   NAME_SERVICE            VARCHAR2(100),
   REC_INN                 VARCHAR2(40)         not null,
   REC_COR_ACCOUNT         VARCHAR2(20),
   REC_KPP                 VARCHAR2(10),
   REC_BIC                 VARCHAR2(10)         not null,
   REC_ACCOUNT             VARCHAR2(20)         not null,
   REC_TB                  VARCHAR2(2)          not null,
   REQUISITES              CLOB                 not null,
   DELAYED_PAY_DATE        TIMESTAMP,
   CREATING_DATE           TIMESTAMP            not null,
   REC_PHONE_NUMBER        VARCHAR2(11),
   REC_NAME_ON_BILL        VARCHAR2(100),
   CONSTRAINT FK_TO_SUBS
        FOREIGN KEY(INVOICE_SUBSCRIPTION_ID)
        REFERENCES INVOICE_SUBSCRIPTIONS(ID)
    
)
partition by RANGE
 (CREATING_DATE)
    interval (NUMTOYMINTERVAL(3,'MONTH'))
 (partition
         P_FIRST
        values less than (to_date('15-05-2014','DD-MM-YYYY')))

go

create index IDX_INVOICES_TO_ID ON INVOICES
(
ID
)
Global

go

create index IDX_INVOICES_TO_SUB ON INVOICES
(
InVOICE_SUBSCRIPTION_ID
)
global
go


create index IDX_INVOICES_TO_LOGIN_STATE ON INVOICES
(
LOGIN_ID,
STATE
)
local


-- Номер ревизии: 62057
-- Номер версии: 1.18
-- Комментарий: CHG072313: Секционировать OPERATION_CONFIRM_LOG 

drop table OPERATION_CONFIRM_LOG cascade constraints
go

create table OPERATION_CONFIRM_LOG 
(
   ID                   INTEGER              not null,
   LOG_DATE             TIMESTAMP            not null,
   STATE                VARCHAR2(16)         not null,
   SESSION_ID           VARCHAR2(64)         not null,
   OPERATION_UID        VARCHAR2(32)         not null,
   RECIPIENT            VARCHAR2(256),
   MESSAGE              CLOB,
   CARD_NUMBER          VARCHAR2(16),
   PASSW_NUMBER         VARCHAR2(2),
   TYPE                 VARCHAR2(4)          not null,
   IMSI_CHECK           CHAR(1)              not null,
   CONFIRM_CODE         VARCHAR2(32)
)
partition by range
 (LOG_DATE)  interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition P_START values less than (to_date('01-06-2014','DD-MM-YYYY')))
go

create index I_OPERATION_CONFIRM_LOG_ID ON OPERATION_CONFIRM_LOG
(
    ID
)
local
go

create index I_OPERATION_CONFIRM_UID ON OPERATION_CONFIRM_LOG
(
    OPERATION_UID
)
global
PARTITION BY HASH (OPERATION_UID) PARTITIONS 128
go

-- Номер ревизии: 62078 
-- Номер версии: 1.18
-- Комментарий: Корзина платежей.MQ(изменение модели БД Phizic) 

alter table INVOICES add (PAYMENT_ID INTEGER)
go

create index INDEX_INVOICES_AUTOPAY_ID on INVOICES (
   AUTOPAY_ID ASC
)
global
go

drop index INDEX_INVOICE_SUB_REQUEST_ID
go

create unique index INDEX_INVOICE_SUB_REQUEST_ID on INVOICE_SUBSCRIPTIONS (
   REQUEST_ID ASC
)
go

-- Номер ревизии: 62110 
-- Номер версии: 1.18
-- Комментарий: Справочник подразделений. Добавление признака активности подразделения.

ALTER TABLE DEPARTMENTS ADD (ACTIVE  CHAR(1) DEFAULT '1' NOT NULL);

-- Номер ревизии: 62137
-- Номер версии: 1.18
-- Комментарий:  BUG073215 	Не создается таблица основной БД

ALTER TABLE INVOICES ADD (AUTOPAY_SUBSCRIPTION_ID VARCHAR2(100) not null)
go


-- Номер ревизии: 62148 
-- Номер версии: 1.18
-- Комментарий: Настройки лимитов.
DELETE FROM PROPERTIES where PROPERTY_KEY='mobile.api.contact.sync.size.limit'
/

-- Номер ревизии: 62190
-- Номер версии: 1.18
-- Комментарий: Корзина платежей. Добавление номера карты в инвойсы
delete from invoices
go
alter table INVOICES add (CARD_NUMBER varchar2(19) not null)
go

-- Номер ревизии: 62202
-- Номер версии: 1.18
-- Комментарий: Корзина платежей. ИОК

alter table INVOICES modify (CODE_RECIPIENT_BS varchar2(128))
go
alter table INVOICES modify (REC_NAME varchar2(160))
go
alter table INVOICES modify (CODE_SERVICE varchar2(50))
go
alter table INVOICES modify (NAME_SERVICE varchar2(150))
go
alter table INVOICES modify (REC_INN varchar2(12))
go
alter table INVOICES modify (REC_COR_ACCOUNT varchar2(25))
go
alter table INVOICES modify (REC_ACCOUNT varchar2(25))
go
alter table INVOICES modify (REC_TB varchar2(4))
go
alter table INVOICES modify (REC_PHONE_NUMBER varchar2(15))
go
alter table INVOICES modify (REC_NAME_ON_BILL varchar2(250))
go

alter table INVOICE_SUBSCRIPTIONS modify (CODE_RECIPIENT_BS varchar2(128))
go
alter table INVOICE_SUBSCRIPTIONS modify (REC_NAME varchar2(160))
go
alter table INVOICE_SUBSCRIPTIONS modify (CODE_SERVICE varchar2(50))
go
alter table INVOICE_SUBSCRIPTIONS modify (NAME_SERVICE varchar2(150))
go
alter table INVOICE_SUBSCRIPTIONS modify (REC_INN varchar2(12))
go
alter table INVOICE_SUBSCRIPTIONS modify (REC_COR_ACCOUNT varchar2(25))
go
alter table INVOICE_SUBSCRIPTIONS modify (REC_ACCOUNT varchar2(25))
go
alter table INVOICE_SUBSCRIPTIONS modify (REC_TB varchar2(4))
go
alter table INVOICE_SUBSCRIPTIONS modify (REC_PHONE_NUMBER varchar2(15))
go
alter table INVOICE_SUBSCRIPTIONS modify (REC_NAME_ON_BILL varchar2(250))
go

-- Номер ревизии: 62207
-- Номер версии: 
-- Комментарий: Адресная информация в расширенной заявке на кредит. Добавление индексов на таблицы справочников
create index LOANCLAIM_AREA_REGION_IDX on LOANCLAIM_AREA (
   REGION ASC,
   LOWER(NAME) ASC
);

create index LOANCLAIM_CITY_REGION_IDX on LOANCLAIM_CITY (
   REGION ASC,
   LOWER(NAME) ASC
);

create index LOANCLAIM_SETTL_REGION_IDX on LOANCLAIM_SETTLEMENT (
   REGION ASC,
   LOWER(NAME) ASC
);

create index LOANCLAIM_STREET_REGION_IDX on LOANCLAIM_STREET (
   REGION ASC,
   LOWER(NAME) ASC
);

-- Номер ревизии: 62220
-- Номер версии: 1.18
-- Комментарий: BUG073185: [ISUP] Вернуть раздельное выставление тех.перерывов в блоках

ALTER TABLE TECHNOBREAKS DROP CONSTRAINT AK_UK_TECHNOBREAKS
go

ALTER TABLE TECHNOBREAKS DROP COLUMN UUID
go

-- Номер ревизии: 62229
-- Номер версии: 1.18
-- Комментарий: ENH070442: Доработать алгоритм работы элемента "добавить в личное меню" 

update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/accounts/graphic-abstract.do', '/private/accounts/operations.do'),
NAME = replace(NAME, 'Графическая выписка по', 'Последние операции по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Графическая выписка по', 'Последние операции по')
where LINK like '%/private/accounts/graphic-abstract.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/accounts/operations.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
go
delete from FAVOURITE_LINKS 
where LINK like '%/private/accounts/graphic-abstract.do%'
go

update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/account/payments.do', '/private/accounts/operations.do'),
NAME = replace(NAME, 'Шаблоны и платежи по', 'Последние операции по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Шаблоны и платежи по', 'Последние операции по')
where LINK like '%/private/account/payments.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/accounts/operations.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
go
delete from FAVOURITE_LINKS 
where LINK like '%/private/account/payments.do%'
go

update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/accounts/info.do', '/private/accounts/operations.do'),
NAME = replace(NAME, 'Детальная информация по', 'Последние операции по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Детальная информация по', 'Последние операции по')
where LINK like '%/private/accounts/info.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/accounts/operations.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
go
delete from FAVOURITE_LINKS 
where LINK like '%/private/accounts/info.do%'
go

update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/cards/graphic-abstract.do', '/private/cards/info.do'),
NAME = replace(NAME, 'Графическая выписка по', 'Последние операции по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Графическая выписка по', 'Последние операции по')
where LINK like '%/private/cards/graphic-abstract.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/cards/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
go
delete from FAVOURITE_LINKS 
where LINK like '%/private/cards/graphic-abstract.do%'
go

update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/cards/payments.do', '/private/cards/info.do'),
NAME = replace(NAME, 'Шаблоны и платежи с', 'Последние операции по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Шаблоны и платежи с', 'Последние операции по')
where LINK like '%/private/cards/payments.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/cards/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
go
delete from FAVOURITE_LINKS 
where LINK like '%/private/cards/payments.do%'
go

update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/cards/detail.do', '/private/cards/info.do'),
NAME = replace(NAME, 'Детальная информация по', 'Последние операции по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Детальная информация по', 'Последние операции по')
where LINK like '%/private/cards/detail.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/cards/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
go
delete from FAVOURITE_LINKS 
where LINK like '%/private/cards/detail.do%'
go

update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/loans/info.do', '/private/loans/detail.do'),
NAME = replace(NAME, 'График платежей по', 'Детальная информация по'),
PATTERN_NAME = replace(PATTERN_NAME, 'График платежей по', 'Детальная информация по')
where LINK like '%/private/loans/info.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/loans/detail.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
go
delete from FAVOURITE_LINKS 
where LINK like '%/private/loans/info.do%'
go

update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/depo/info/position.do', '/private/depo/documents.do'),
NAME = replace(NAME, 'Позиция по', 'Список документов по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Позиция по', 'Список документов по')
where LINK like '%/private/depo/info/position.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/depo/documents.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
go
delete from FAVOURITE_LINKS 
where LINK like '%/private/depo/info/position.do%'
go

update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/depo/info/debt.do', '/private/depo/documents.do'),
NAME = replace(NAME, 'Задолженность по', 'Список документов по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Задолженность по', 'Список документов по')
where LINK like '%/private/depo/info/debt.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/depo/documents.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
go
delete from FAVOURITE_LINKS 
where LINK like '%/private/depo/info/debt.do%'
go

update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/ima/detail.do', '/private/ima/info.do'),
NAME = replace(NAME, 'Детальная информация по', 'Последние операции по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Детальная информация по', 'Последние операции по')
where LINK like '%/private/ima/detail.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/ima/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
go
delete from FAVOURITE_LINKS 
where LINK like '%/private/ima/detail.do%'
go



-- Номер ревизии: 62273
-- Номер версии: 1.18
-- Комментарий: Доработать оплату услуг для оплаты своего телефона
CREATE INDEX IDX_RFFD_LOGIN_ID ON RECENTLY_FILLED_FIELD_DATA (login_id);

-- Номер ревизии: 62288
-- Номер версии: 1.18
-- Комментарий: Доработка логики исполнения шаблонов

alter table SERVICE_PROVIDERS add PLANING_FOR_DEACTIVATE char(1)
go

alter table BILLINGS add TEMPLATE_STATE varchar2(30)
go

-- Номер ревизии: 62313
-- Номер версии: 1.18
-- Комментарий: Управление кешированием счетчиков
create or replace procedure create_sequence(sequenceName VARCHAR, minval INTEGER, maxval INTEGER, sequenceType VARCHAR default NULL) is
begin
    case
        -- пересоздаваемые каждый день
        when sequenceType = 'EXTENDED'
        then execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle cache 2000';

        -- строго последовательные
        when sequenceType = 'STRICT'
        then execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle nocache order';

        -- сиквенс по умолчанию
        else execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle';
   end case;
end;
go


-- Номер ревизии: 62329
-- Номер версии: 1.18
-- Комментарий: Адресная книга. Добавление журнала синхронизаций

create table ADDRESS_BOOK_CONTACTS_COUNT (
    LOGIN_ID INTEGER NOT NULL,
    FIO VARCHAR2(360) NOT NULL,
    DOCUMENT VARCHAR2(32) NOT NULL,
    BIRTHDAY TIMESTAMP NOT NULL,
    TB VARCHAR2(4) NOT NULL,
    "COUNT" INTEGER NOT NULL
)
go

create unique index I_LOGIN_ID_AB_CONTACTS_COUNT on ADDRESS_BOOK_CONTACTS_COUNT (
    LOGIN_ID
)
go

create index I_COUNT_AB_CONTACTS_COUNT on ADDRESS_BOOK_CONTACTS_COUNT (
    "COUNT" DESC 
)
go

create table ADDRESS_BOOK_REQUESTS_COUNT (
    ID INTEGER NOT NULL,
    LOGIN_ID INTEGER NOT NULL,
    FIO VARCHAR2(360) NOT NULL,
    DOCUMENT VARCHAR2(32) NOT NULL,
    BIRTHDAY TIMESTAMP NOT NULL,
    TB VARCHAR2(4) NOT NULL,
    SYNCHRONIZATION_DATE TIMESTAMP NOT NULL
)
partition by range
 (SYNCHRONIZATION_DATE)  interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition P_START values less than (to_date('01-06-2014','DD-MM-YYYY')))
go

create sequence S_ADDRESS_BOOK_REQUESTS_COUNT
go

create index I_COUNT_AB_REQUESTS_COUNT_DATE on ADDRESS_BOOK_REQUESTS_COUNT (
    SYNCHRONIZATION_DATE
)
local
go

-- Номер ревизии: 62369
-- Номер версии: 1.18
-- Комментарий: ENH070442: Доработать алгоритм работы элемента "добавить в личное меню" 
alter table FAVOURITE_LINKS drop column IS_USE
go

-- Номер ревизии: 62402
-- Номер версии: 1.18
-- Комментарий: Добавление интернет-заказов в функционал "счетов к оплате"
ALTER TABLE "INVOICE_SUBSCRIPTIONS"
	DROP ( "NOT_PAID_INVOICES", "DELAYED_INVOICES" ) CASCADE CONSTRAINT 
GO

ALTER TABLE "SHOP_ORDERS"
	ADD ( "DELAYED_PAY_DATE" TIMESTAMP NULL ) 
GO


-- Номер ревизии: 62434
-- Номер версии: 1.18
-- Комментарий: Корзина платежей. Идентификатор поставщика услуг обязателен для заполнения

alter table INVOICE_SUBSCRIPTIONS modify (RECIPIENT_ID not null)
go

-- Номер ревизии: 62460
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. Переименовал признак разрешение рекламных рассылок в признак запрета. 

alter table ERMB_PROFILE
rename column  ADVERTISING  to SUPPRESS_ADVERTISING
go

alter table ERMB_PROFILE
  modify SUPPRESS_ADVERTISING default '0'
go

update ERMB_PROFILE
set SUPPRESS_ADVERTISING = decode(SUPPRESS_ADVERTISING, '0', '1', '0')
go

-- Номер ревизии: 62490
-- Номер версии: 1.18
-- Комментарий: Доработка операции синхронизации адрессной книги.

delete from ADDRESS_BOOK_REQUESTS_COUNT
go

alter table ADDRESS_BOOK_REQUESTS_COUNT add (COUNT INTEGER not null)
go

create index I_ADDRESS_BOOK_REQUESTS_COUNT on ADDRESS_BOOK_REQUESTS_COUNT (
    LOGIN_ID,
    SYNCHRONIZATION_DATE
)
local
go

-- Номер ревизии: 62496
-- Номер версии: 1.18
-- Комментарий: Активация услуги ЕРМБ - Проставление флагов при создании ЕРМБ профиля

ALTER TABLE ERMB_PROFILE ADD COD_ACTIVATED CHAR(1) DEFAULT '0' NOT NULL;
ALTER TABLE ERMB_PROFILE ADD WAY_ACTIVATED CHAR(1) DEFAULT '0' NOT NULL;
ALTER TABLE ERMB_PROFILE ADD ACTIVATION_TRY_DATE TIMESTAMP;

-- Номер ревизии: 62582
-- Номер версии: 1.18
-- Комментарий: CHG073752: Привязывать пользовательские настройки(USER_PROPERTIES) к логину, а не юзеру.

alter table USER_PROPERTIES rename column USER_ID to LOGIN_ID
go

update USER_PROPERTIES p set p.LOGIN_ID = (select u.LOGIN_ID from USERS u where u.ID = p.LOGIN_ID) 
go

create index "DXFK_LOGIN_PROPERTIES" on USER_PROPERTIES
(
   LOGIN_ID
)
go

alter table USER_PROPERTIES
   add constraint FK_LOGIN_PROPERTIES foreign key (LOGIN_ID)
      references LOGINS (ID)
go

-- Номер ревизии: 62721
-- Номер версии: 1.18
-- Комментарий: BUG073925: [Расширенная заявка: Справочники] Не грузятся справочники для ввода адресной информации 

drop index LOANCLAIM_AREA_REGION_IDX;
alter table LOANCLAIM_AREA modify name varchar2(255);
drop index LOANCLAIM_CITY_REGION_IDX; 
alter table LOANCLAIM_CITY modify name varchar2(255);
drop index LOANCLAIM_SETTL_REGION_IDX;
alter table LOANCLAIM_SETTLEMENT modify name varchar2(255);
drop index LOANCLAIM_STREET_REGION_IDX;
alter table LOANCLAIM_STREET modify name varchar2(255);

create index LOANCLAIM_AREA_REGION_IDX on LOANCLAIM_AREA (
   REGION ASC,
   LOWER(NAME) ASC
);

create index LOANCLAIM_CITY_REGION_IDX on LOANCLAIM_CITY (
   REGION ASC,
   LOWER(NAME) ASC
);

create index LOANCLAIM_SETTL_REGION_IDX on LOANCLAIM_SETTLEMENT (
   REGION ASC,
   LOWER(NAME) ASC
);

create index LOANCLAIM_STREET_REGION_IDX on LOANCLAIM_STREET (
   REGION ASC,
   LOWER(NAME) ASC
);

-- Номер ревизии: 62724
-- Номер версии: 1.18
-- Комментарий: Управление кешированием счетчиков (откат)

create or replace procedure create_sequence(sequenceName VARCHAR, maxval INTEGER) is
begin
    IF regexp_like(sequenceName,'SC_.+_\d{6}') -- если этот сиквенс - обновляемый
    then    -- ставим ему кэш 2000
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle cache 2000';
    else
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle';
    end if;
end;

-- Номер ревизии: 62739
-- Номер версии: 1.18
-- Комментарий: Переработка механизма асинхронного выбора профилей ермб для выгрузки списания абонентской платы.

drop  sequence  SC_FEE_FPP_JOB_NUMBER
go
alter table ERMB_PROFILE drop column PROCES_GROUP_ID 
go

-- Номер ревизии: 62760
-- Номер версии: 1.18
-- Комментарий: ENH070461: Уйти от использования ConnectorInfo в ЕРИБ и ЦСА 

drop table CONNECTORS_INFO
go
drop sequence S_CONNECTORS_INFO
go
drop index USR_CON_INFO_UN_IND
go

-- Номер ревизии: 62800
-- Номер версии: 1.18
-- Комментарий: Доработка идентификатции внутренних ошибок

alter table EXCEPTION_ENTRY modify HASH varchar2(277)
go
alter table EXCEPTIONS_LOG modify HASH varchar2(277)
go
alter table EXCEPTION_COUNTERS modify EXCEPTION_HASH varchar2(277)
go

-- Можно просто удалить данные либо выполнить скрипт ниже

update EXCEPTIONS_LOG el set (el.HASH) = (select ee.HASH||'|'||ee.APPLICATION from EXCEPTION_ENTRY ee where ee.HASH = el.HASH)
go
update EXCEPTION_COUNTERS el set (el.EXCEPTION_HASH) = (select ee.HASH||'|'||ee.APPLICATION from EXCEPTION_ENTRY ee where ee.HASH = el.EXCEPTION_HASH)
go
update EXCEPTION_ENTRY set HASH = HASH||'|'||APPLICATION
go

-- Номер ревизии: 62844
-- Номер версии: 1.18
-- Комментарий: Загрузка справочника DEF-кодов в АРМ сотрудника

create table DEF_CODES
(
  ID                INTEGER      not null,
  DEF_CODE_FROM     VARCHAR(10)  not null,
  DEF_CODE_TO       VARCHAR(10)  not null,
  PROVIDER_CODE     VARCHAR(20)  not null,
  constraint PK_DEF_CODES primary key (ID)
);

create sequence S_DEF_CODES;

-- Номер ревизии: 62916
-- Номер версии: 1.18
-- Комментарий: Merged revision(s) 62894 from versions/v.1.18_release_14.0_PSI:иок(2)

UPDATE ERMB_PROFILE
   SET ACTIVATION_TRY_DATE = SYSDATE
 WHERE ACTIVATION_TRY_DATE IS NULL;

ALTER TABLE ERMB_PROFILE MODIFY ACTIVATION_TRY_DATE DEFAULT SYSDATE NOT NULL;

CREATE INDEX I_ACTIVATION_TRY_DATE
   ON ERMB_PROFILE (ACTIVATION_TRY_DATE ASC);

-- Номер ревизии: 62929
-- Номер версии: 1.18
-- Комментарий: BUG073990: [ЕРМБ ] некорректная работа джобов ЕРМБ

DELETE FROM QRTZ_SIMPLE_TRIGGERS
WHERE TRIGGER_NAME IN
(
    SELECT TRIGGER_NAME FROM QRTZ_TRIGGERS
    WHERE JOB_NAME IN (
      'ErmbProfilesUpdateJob',
      'DelayedCommandExecuteJob',
      'ErmbNewClientsRegistrationJob',
      'ErmbDisconnectorPhoneJob',
      'ErmbP2PJob',
      'IncompletePaymentsRemoverJob',
      'ErmbActivationJob',
      'FPP_STARTER')
);

DELETE FROM QRTZ_CRON_TRIGGERS
WHERE TRIGGER_NAME IN
(
    SELECT TRIGGER_NAME FROM QRTZ_TRIGGERS
    WHERE JOB_NAME IN (
      'ErmbProfilesUpdateJob',
      'DelayedCommandExecuteJob',
      'ErmbNewClientsRegistrationJob',
      'ErmbDisconnectorPhoneJob',
      'ErmbP2PJob',
      'IncompletePaymentsRemoverJob',
      'ErmbActivationJob',
      'FPP_STARTER')
);

DELETE FROM QRTZ_BLOB_TRIGGERS
WHERE TRIGGER_NAME IN
(
    SELECT TRIGGER_NAME FROM QRTZ_TRIGGERS
    WHERE JOB_NAME IN (
      'ErmbProfilesUpdateJob',
      'DelayedCommandExecuteJob',
      'ErmbNewClientsRegistrationJob',
      'ErmbDisconnectorPhoneJob',
      'ErmbP2PJob',
      'IncompletePaymentsRemoverJob',
      'ErmbActivationJob',
      'FPP_STARTER')
);


DELETE FROM QRTZ_TRIGGERS
WHERE JOB_NAME IN (
  'ErmbProfilesUpdateJob',
  'DelayedCommandExecuteJob',
  'ErmbNewClientsRegistrationJob',
  'ErmbDisconnectorPhoneJob',
  'ErmbP2PJob',
  'IncompletePaymentsRemoverJob',
  'ErmbActivationJob',
  'FPP_STARTER')
;

DELETE FROM QRTZ_JOB_DETAILS
WHERE JOB_NAME IN (
  'ErmbProfilesUpdateJob',
  'DelayedCommandExecuteJob',
  'ErmbNewClientsRegistrationJob',
  'ErmbDisconnectorPhoneJob',
  'ErmbP2PJob',
  'IncompletePaymentsRemoverJob',
  'ErmbActivationJob',
  'FPP_STARTER')
;


-- Номер ревизии: 62968
-- Номер версии: 1.18
-- Комментарий: Merged revision(s) 62966 from versions/v.1.18_release_14.0_PSI:доработка профиля ЕРМБ

UPDATE ERMB_PROFILE
   SET CHARGE_NEXT_AMOUNT = 0
WHERE CHARGE_NEXT_AMOUNT IS NULL;

ALTER TABLE ERMB_PROFILE MODIFY CHARGE_NEXT_AMOUNT DEFAULT 0 NOT NULL;

-- Номер ревизии: 63018
-- Номер версии: 1.18
-- Комментарий: Маппинг ошибок: изменение поиска сообщения для ошибки
create table EXCEPTION_MAPPINGS 
(
   HASH                 VARCHAR2(277)        not null,
   GROUP_ID             INTEGER              not null,
   MESSAGE              VARCHAR2(2000)       not null,
   constraint PK_EXCEPTION_MAPPINGS primary key (HASH, GROUP_ID)
)
go

create table EXCEPTION_MAPPING_RESTRICTIONS 
(
   HASH                 VARCHAR2(277)        not null,
   GROUP_ID             INTEGER              not null,
   APPLICATION          VARCHAR(20)          not null,
   TB                   VARCHAR2(4)          not null
)
go

create unique index EXC_MAP_RESTRICTION_UK on EXCEPTION_MAPPING_RESTRICTIONS (
   HASH ASC,
   APPLICATION ASC,
   TB ASC
)
go

create index "DXEXCEPTION_MAPPING_RESTRICTIO" on EXCEPTION_MAPPING_RESTRICTIONS
(
   HASH, GROUP_ID
)
go

alter table EXCEPTION_MAPPING_RESTRICTIONS
   add constraint FK_EXCEPTIO_EXCEPTION_EXCEPTIO foreign key (HASH, GROUP_ID)
      references EXCEPTION_MAPPINGS (HASH, GROUP_ID)
      on delete cascade
go

-- Номер ревизии: 63030
-- Номер версии: 1.18
-- Комментарий: Загрузка справочника DEF-кодов в АРМ сотрудника (доработка)

delete from DEF_CODES;

alter table DEF_CODES modify DEF_CODE_FROM NUMBER;
alter table DEF_CODES modify DEF_CODE_TO NUMBER;

CREATE INDEX IDX_DEFCODES_PHONE_RANGE ON DEF_CODES
(
  "DEF_CODE_FROM" desc,
  "DEF_CODE_TO",
  "PROVIDER_CODE"
);


-- Номер ревизии: 63239
-- Номер версии: 1.18
-- Комментарий: CHG074183: Добавить возможность многопоточных запросов в ИПС 

create table PROCESSED_CARD_OP_CLAIM_LOGIN 
(
   LOGIN_ID             INTEGER              not null,
   PROCESSING_DATE      TIMESTAMP            not null,
   constraint PK_PROCESSED_CARD_OP_CLAIM_LOG primary key (LOGIN_ID)
)
go

create index PROCESSED_CARD_OP_DATE_IDX on PROCESSED_CARD_OP_CLAIM_LOGIN (
   PROCESSING_DATE ASC
)
go


-- Номер ревизии: 63245
-- Номер версии: 1.18
-- Комментарий: BUG073908: Доработать отправку смс для операций с вкладов 

drop index INDEX_SMS_CHANNEL_KEY 
go

create unique index INDEX_SMS_CHANNEL_KEY on SMS_RESOURCES (
   KEY ASC,
   CHANNEL ASC,
   SMS_TYPE ASC
)
go

-- Номер ревизии: 63251
-- Номер версии: 1.18
-- Комментарий: BUG073202: Шаблоны отдельное приложение. Добавить хранение межблочного идентификатора поставщика услуг.
alter table PAYMENTS_DOCUMENTS add  (MULTI_BLOCK_RECEIVER_CODE VARCHAR2(32))
go

-- Номер ревизии: 63271
-- Номер версии: 1.18
-- Комментарий: BUG070523: Лишнее поле в расширенной заявке на кредит
delete from LOANCLAIM_FAMILY_STATUS;
alter table LOANCLAIM_FAMILY_STATUS modify (spouse_info_required varchar2(15));
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('0', 'Холост/не замужем', 'NOT'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('1', 'В разводе', 'NOT'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('2', 'Женат/замужем', 'REQUIRED'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('3', 'Вдовец/вдова', 'NOT'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('4', 'Гражданский брак', 'WITHOUT_PRENUP');

-- Номер ревизии: 63282
-- Номер версии: 1.18
-- Комментарий: BUG071121: полное сканирование таблицы CSA_PROFILES 

create index I_INCOGNITO_PHONES on INCOGNITO_PHONES (
PHONE_NUMBER
)
go
delete from INCOGNITO_PHONES
go
alter table INCOGNITO_PHONES add (PROFILE_ID integer not null)
go
create index I_INCOGNITO_PROFILES on INCOGNITO_PHONES (
PROFILE_ID
)
go

-- Номер ревизии: 63439
-- Номер версии: 1.18
-- Комментарий: Добавление индекса для запроса выгрузки ФПП абонентской платы ЕРМБ

CREATE INDEX IDX_ERMB_SRV_FEE_CHARGE_DATE ON ERMB_PROFILE(CHARGE_NEXT_DATE)
;

-- Номер ревизии: 63593
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG074625: [ISUP] Дублирование заявок на загрузку операций из ИПС.

alter table CARD_OPERATION_CLAIMS add constraint UNIQUE_LOGIN_ID_CARD_NUMBER unique (LOGIN_ID, CARD_NUMBER) 
/


-- Номер ревизии: 63616
-- Номер версии: 1.18
-- Комментарий: BUG071121: полное сканирование таблицы CSA_PROFILES

create table INCOGNITO_PROFILES(
    PROFILE_ID integer not null,
    constraint PR_INCOGNITO_PROFILE primary key (PROFILE_ID)
)
go

create unique index I_INCOGNITO_PROFILE on INCOGNITO_PROFILES (
    PROFILE_ID
)
go

-- Номер ревизии: 63631
-- Номер версии: v.1.18
-- Комментарий: BUG074333: [Шаблоны] Не устанавливается признак подтверждать операции по шаблону СМС-паролем 
UPDATE PROPERTIES SET PROPERTY_VALUE = 'true' WHERE PROPERTY_KEY LIKE 'com.rssl.iccs.favourite.templates.needConfirm%' AND PROPERTY_VALUE = 'on'
GO


-- Номер ревизии: 63713
-- Номер версии: 1.18
-- Комментарий: Убираем default в ErmbProfile с полей добавленных в 14м релизе.

alter table ERMB_PROFILE
ADD SERVICE_STATUS_TMP  CHAR(1)
go
update ERMB_PROFILE 
set SERVICE_STATUS_TMP = SERVICE_STATUS
go
alter table ERMB_PROFILE
drop column SERVICE_STATUS
go
alter table ERMB_PROFILE
rename column SERVICE_STATUS_TMP to SERVICE_STATUS
go
alter table ERMB_PROFILE
modify SERVICE_STATUS not null
go
alter table ERMB_PROFILE
add CLIENT_BLOCKED_TMP  CHAR(1)
go
update ERMB_PROFILE 
set CLIENT_BLOCKED_TMP = CLIENT_BLOCKED
go
alter table ERMB_PROFILE
drop column CLIENT_BLOCKED
go
alter table ERMB_PROFILE
rename column CLIENT_BLOCKED_TMP to CLIENT_BLOCKED
go
alter table ERMB_PROFILE
modify CLIENT_BLOCKED not null
go
alter table ERMB_PROFILE
ADD PAYMENT_BLOCKED_TMP  CHAR(1)
go
update ERMB_PROFILE 
set PAYMENT_BLOCKED_TMP = PAYMENT_BLOCKED
go
alter table ERMB_PROFILE
drop column PAYMENT_BLOCKED
go
alter table ERMB_PROFILE
rename column PAYMENT_BLOCKED_TMP to PAYMENT_BLOCKED
go

-- Номер ревизии: 63687
-- Номер версии: 1.18
-- Комментарий: Доработать доступность поставщиков в новом канале и запросы sql с учетом этих признаков

alter table SERVICE_PROVIDERS add IS_AUTOPAYMENT_SUPPORTED_S_API CHAR(1); 
update SERVICE_PROVIDERS set IS_AUTOPAYMENT_SUPPORTED_S_API = '0';
alter table SERVICE_PROVIDERS modify IS_AUTOPAYMENT_SUPPORTED_S_API CHAR(1) default '0' not null;

alter table SERVICE_PROVIDERS add VISIBLE_AUTOPAYMENTS_FOR_S_API CHAR(1);
update SERVICE_PROVIDERS set VISIBLE_AUTOPAYMENTS_FOR_S_API = '0';
alter table SERVICE_PROVIDERS modify VISIBLE_AUTOPAYMENTS_FOR_S_API CHAR(1) default '0' not null;

alter table SERVICE_PROVIDERS add VISIBLE_PAYMENTS_FOR_S_API CHAR(1);
update SERVICE_PROVIDERS set VISIBLE_PAYMENTS_FOR_S_API = '0';
alter table SERVICE_PROVIDERS modify VISIBLE_PAYMENTS_FOR_S_API CHAR(1) default '0' not null;

alter table SERVICE_PROVIDERS add AVAILABLE_PAYMENTS_FOR_S_API CHAR(1);
update SERVICE_PROVIDERS set AVAILABLE_PAYMENTS_FOR_S_API ='1';
alter table SERVICE_PROVIDERS modify AVAILABLE_PAYMENTS_FOR_S_API CHAR(1) default '1' not null;

-- Номер ревизии: 63730
-- Номер версии: 1.18
-- Комментарий: CHG073489: Доработка отображения адресной информации в заявке на транзакт.

delete from loanclaim_city;
delete from loanclaim_settlement;
delete from loanclaim_street;
 
alter table loanclaim_city modify (area number(20,0));

alter table loanclaim_settlement modify (area number(20,0));
alter table loanclaim_settlement modify (city number(20,0));

alter table loanclaim_street modify (area number(20,0));
alter table loanclaim_street modify (city number(20,0));
alter table loanclaim_street modify (settlement number(20,0));



-- Номер ревизии: 63575
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG074625: [ISUP] Дублирование заявок на загрузку операций из ИПС. Лишний индекс
drop index DXFK_CARDOP_CLAIM_OWNER
/

-- Номер ревизии: 62966
-- Номер версии: 1.18
-- Комментарий: доработка профиля ЕРМБ
UPDATE ERMB_PROFILE
   SET CHARGE_NEXT_PERIOD = 1
 WHERE SERVICE_STATUS = '1' AND CHARGE_NEXT_PERIOD IS NULL;

UPDATE ERMB_PROFILE
   SET CHARGE_DAY_OF_MONTH = 1
 WHERE SERVICE_STATUS = '1' AND CHARGE_DAY_OF_MONTH IS NULL;

-- Номер ревизии: 64042
-- Номер версии: 1.18
-- Комментарий: Merged revision(s) 64042 from versions/v.1.18_release_14.0_PSI: ENH074735  [ЕРМБ] Удаление недовведённых платежей на обработке каждого СМС-запроса 
ALTER TABLE ERMB_PROFILE ADD INCOMPLETE_SMS_PAYMENT INTEGER;

-- Номер ревизии: 64117
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: ENH074735  [ЕРМБ] Удаление недовведённых платежей на обработке каждого СМС-запроса 
DELETE FROM QRTZ_ERMB_SIMPLE_TRIGGERS
      WHERE TRIGGER_NAME = 'IncompletePaymentsRemoverTrigger';

DELETE FROM QRTZ_ERMB_TRIGGERS
      WHERE TRIGGER_NAME = 'IncompletePaymentsRemoverTrigger';

DELETE FROM QRTZ_ERMB_JOB_DETAILS
      WHERE JOB_NAME = 'IncompletePaymentsRemoverJob';

-- Номер ревизии: 64304
-- Номер версии: 1.18
-- Комментарий: BUG072501: [ЕРМБ смс-канал] некорректно работает проверка суточного заградительного лимита при оплате ПУ
alter table SMS_RESOURCES modify DESCRIPTION VARCHAR2(4000);


-- Номер ревизии: 64318
-- Номер версии: 1.18
-- Комментарий: BUG074650: [Синхронизация справочников] Ошибка при синхронизации справочников АЛФ. 

alter table PAYMENT_SERVICES
drop column CARD_OPERATION_CATEGORY
go

create table PAYMENT_SERVICES_TO_CATEGORIES 
(
   SERVICE_CODE  VARCHAR2(50)  not null,
   CATEGORY_ID   INTEGER  not null,
   constraint PK_PAYMENT_SERV_TO_CATEGORIES primary key (SERVICE_CODE)
)
go

alter table PAYMENT_SERVICES_TO_CATEGORIES
   add constraint FK_PAYMENT_SERV_CAT_CAT_ID foreign key (CATEGORY_ID)
      references CARD_OPERATION_CATEGORIES (ID)
      on delete cascade
go

create index DXFK_PAYMENT_SERV_CAT_CAT_ID on PAYMENT_SERVICES_TO_CATEGORIES
(
   CATEGORY_ID
)
go

-- Номер ревизии: 64395
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG075192: [ЕРМБ абон плата] некорректно формируются файлы прямого потока
alter table ERMB_PROFILE add FPP_BLOCKED CHAR(1) default '0' not null;


-- Номер ревизии: 64435
-- Номер версии: v1.18
-- Комментарий: BUG074685: Не сохраняются настройки Права доступа клиента по умолчанию

ALTER TABLE DEFAULT_ACCESS_SCHEMES ADD DEPARTMENT_TB varchar2(4)
go

update DEFAULT_ACCESS_SCHEMES ds set ds.DEPARTMENT_TB = (SELECT d.TB FROM DEPARTMENTS d WHERE d.ID = ds.DEPARTMENT_ID)
go

ALTER TABLE DEFAULT_ACCESS_SCHEMES DROP CONSTRAINT AK_DEFAULT_SCHEMES_UNIQUE
go

CREATE UNIQUE INDEX I_DEFAULT_ACCESS_SCHEMES ON DEFAULT_ACCESS_SCHEMES(
    CREATION_TYPE,
    NVL(DEPARTMENT_TB, 'NULL')
)
go

alter table DEFAULT_ACCESS_SCHEMES DROP constraint FK_DEFAULT__FK_DEF_SC_DEPARTME
go

ALTER TABLE DEFAULT_ACCESS_SCHEMES DROP COLUMN DEPARTMENT_ID
go


-- Номер ревизии: 64731
-- Номер версии: v1.18
-- Комментарий: ENH072991: Почему логин в профиле неуникален?

drop index DXREFERENCE_20
go

create unique index IDX_PROFILE_LOGIN on PROFILE (
   LOGIN_ID ASC
)
go


-- Номер ревизии: 64318
-- Номер версии: 1.18
-- Комментарий: BUG074650: [Синхронизация справочников] Ошибка при синхронизации справочников АЛФ. 

create index I_MCC_INCOME_CATEGORY on MERCHANT_CATEGORY_CODES (
   INCOME_OPERATION_CATEGORY_ID ASC
)
go

create index I_MCC_OUTCOME_CATEGORY on MERCHANT_CATEGORY_CODES (
   OUTCOME_OPERATION_CATEGORY_ID ASC
)
go


-- Номер ревизии: 65154
-- Номер версии: 1.18
-- Комментарий: BUG075776: Репликация поставщиков услуг: ошибка при репликации ПУ, у которого есть алиасы ЕРМБ

create index "DXFK_SMS_ALIAS_TO_PROVIDER" on PROVIDER_SMS_ALIAS
(
   SERVICE_PROVIDER_ID
)
;

alter table PROVIDER_SMS_ALIAS
add constraint FK_SMS_ALIAS_TO_PROVIDER foreign key (SERVICE_PROVIDER_ID)
    references SERVICE_PROVIDERS (ID)
    on delete cascade
;

alter table PROVIDER_SMS_ALIAS_FIELD
drop constraint FK_PROVIDER_FK_ALIAS__PROVIDER
;

alter table PROVIDER_SMS_ALIAS_FIELD
add constraint FK_PROVIDER_FK_ALIAS__PROVIDER foreign key (PROVIDER_SMS_ALIAS_ID)
    references PROVIDER_SMS_ALIAS (ID)
    on delete cascade
;

alter table PROVIDER_SMS_ALIAS_FIELD
drop constraint FK_PROVIDER_FK_ALIAS__FIELD_DE
;

alter table PROVIDER_SMS_ALIAS_FIELD
add constraint FK_PROVIDER_FK_ALIAS__FIELD_DE foreign key (FIELD_DESCRIPTION_ID)
    references FIELD_DESCRIPTIONS (ID)
    on delete cascade
;
-- Номер ревизии: 65616
-- Номер версии: v1.18
-- Комментарий: BUG075726 Оповещение об отключении шаблонов : Возможность редактирования (Модель БД)
update SERVICE_PROVIDERS set PLANING_FOR_DEACTIVATE = 0 where PLANING_FOR_DEACTIVATE is null
go
alter table SERVICE_PROVIDERS modify PLANING_FOR_DEACTIVATE not null
go

-- Номер ревизии: 65936
-- Номер версии: v1.18
-- Комментарий: Иерархическое администрирование. Сущность группы сервисов

create table SERVICES_GROUPS 
(
   ID                   INTEGER              not null,
   KEY                  VARCHAR2(60)         not null,
   NAME                 VARCHAR2(100)        not null,
   PARENT_ID            INTEGER,
   CATEGORY             VARCHAR2(8)          not null,
   IS_ACTION            CHAR(1)              not null,
   constraint PK_SERVICES_GROUPS primary key (ID)
)
go

create sequence S_SERVICES_GROUPS
go

create unique index I_SERVICES_GROUPS_KEY on SERVICES_GROUPS (
   KEY ASC
)
go

create table SERVICES_GROUPS_EDIT_SERVICES 
(
   GROUP_ID             INTEGER              not null,
   SERVICE_ID           INTEGER              not null
)
go

create table SERVICES_GROUPS_VIEW_SERVICES 
(
   GROUP_ID             INTEGER              not null,
   SERVICE_ID           INTEGER              not null
)
go

alter table SERVICES_GROUPS
   add constraint FK_SERVICES_GROUPS_TO_PARENT foreign key (PARENT_ID)
      references SERVICES_GROUPS (ID)
      on delete cascade
go


create index "DXFK_EDIT_SERVICES_TO_GROUP" on SERVICES_GROUPS_EDIT_SERVICES
(
   GROUP_ID
)
go

alter table SERVICES_GROUPS_EDIT_SERVICES
   add constraint FK_EDIT_SERVICES_TO_GROUP foreign key (GROUP_ID)
      references SERVICES_GROUPS (ID)
      on delete cascade
go


create index "DXFK_EDIT_SERVICES_TO_SERVICE" on SERVICES_GROUPS_EDIT_SERVICES
(
   SERVICE_ID
)
go

alter table SERVICES_GROUPS_EDIT_SERVICES
   add constraint FK_EDIT_SERVICES_TO_SERVICE foreign key (SERVICE_ID)
      references SERVICES (ID)
      on delete cascade
go


create index "DXFK_VIEW_SERVICES_TO_GROUP" on SERVICES_GROUPS_VIEW_SERVICES
(
   GROUP_ID
)
go

alter table SERVICES_GROUPS_VIEW_SERVICES
   add constraint FK_VIEW_SERVICES_TO_GROUP foreign key (GROUP_ID)
      references SERVICES_GROUPS (ID)
      on delete cascade
go


create index "DXFK_VIEW_SERVICES_TO_SERVICE" on SERVICES_GROUPS_VIEW_SERVICES
(
   SERVICE_ID
)
go

alter table SERVICES_GROUPS_VIEW_SERVICES
   add constraint FK_VIEW_SERVICES_TO_SERVICE foreign key (SERVICE_ID)
      references SERVICES (ID)
      on delete cascade
go


-- Номер ревизии: 65995
-- Номер версии: v1.18
-- Комментарий: Иерархическое администрирование. Сущность группы сервисов

alter table SERVICES add constraint I_SERVICES_KEY unique (SERVICE_KEY)
go

alter table SERVICES_GROUPS add GROUP_ORDER INTEGER NOT NULL
go

drop table SERVICES_GROUPS_EDIT_SERVICES 
go

drop table SERVICES_GROUPS_VIEW_SERVICES 
go

create table SERVICES_GROUPS_SERVICES 
(
   GROUP_ID             INTEGER              not null,
   SERVICE_KEY          VARCHAR2(60)         not null,
   SERVICE_MODE         VARCHAR2(4)          not null
)
go

create index "DXFK_SERVICES_TO_GROUP" on SERVICES_GROUPS_SERVICES
(
   GROUP_ID
)
go

alter table SERVICES_GROUPS_SERVICES
   add constraint FK_SERVICES_TO_GROUP foreign key (GROUP_ID)
      references SERVICES_GROUPS (ID)
      on delete cascade
go


create index "DXFK_SERVICES_TO_SERVICE" on SERVICES_GROUPS_SERVICES
(
   SERVICE_KEY
)
go

alter table SERVICES_GROUPS_SERVICES
   add constraint FK_SERVICES_TO_SERVICE foreign key (SERVICE_KEY)
      references SERVICES (SERVICE_KEY)
      on delete cascade
go

-- Номер ревизии: 66183
-- Номер версии: v1.18
-- Комментарий: Доработать справочники платформ
alter table MOBILE_PLATFORMS modify VERSION null;
alter table MOBILE_PLATFORMS modify ERROR_TEXT null;

-- Номер ревизии: 66197
-- Номер версии: v1.18
-- Комментарий: Merged revision(s) 66196 from versions/v.1.18_release_14.0_PSI: BUG071135  [ЕРМБ смс-канал] Не срабатывает проверка для кода подтверждения операции 
DELETE FROM CONFIRM_BEANS;

ALTER TABLE CONFIRM_BEANS ADD OVERDUE_TIME TIMESTAMP NOT NULL;

DROP INDEX IDX_CONFIRM_EXPIRE;

CREATE INDEX IDX_CONFIRM_OVERDUE
   ON CONFIRM_BEANS (OVERDUE_TIME ASC);

DELETE FROM QRTZ_ERMB_SIMPLE_TRIGGERS
      WHERE TRIGGER_NAME = 'ExpiredConfirmBeanRemoverTrigger';

DELETE FROM QRTZ_ERMB_TRIGGERS
      WHERE TRIGGER_NAME = 'ExpiredConfirmBeanRemoverTrigger';

DELETE FROM QRTZ_ERMB_JOB_DETAILS
      WHERE JOB_NAME = 'ExpiredConfirmBeanRemoverJob';

-- Номер ревизии: 66288
-- Номер версии: v1.18
-- Комментарий: BUG076892: [ЕРМБ] ошибка при работе FppUnloadJob
update ERMB_TARIF set CHARGE_PERIOD = 1 where CHARGE_PERIOD = 0;


-- Номер ревизии: 66397
-- Номер версии: v1.18
-- Комментарий: Доработать видимость продуктов клиента
alter table CARD_LINKS add SHOW_IN_SOCIAL CHAR(1);
update CARD_LINKS set SHOW_IN_SOCIAL = '1';
alter table CARD_LINKS MODIFY SHOW_IN_SOCIAL not null; 

alter table ACCOUNT_LINKS add SHOW_IN_SOCIAL CHAR(1);
update ACCOUNT_LINKS set SHOW_IN_SOCIAL = '1';
alter table ACCOUNT_LINKS MODIFY SHOW_IN_SOCIAL not null;

alter table LOAN_LINKS add SHOW_IN_SOCIAL CHAR(1);
update LOAN_LINKS set SHOW_IN_SOCIAL = '1';
alter table LOAN_LINKS MODIFY SHOW_IN_SOCIAL not null;

alter table IMACCOUNT_LINKS add SHOW_IN_SOCIAL CHAR(1);
update IMACCOUNT_LINKS set SHOW_IN_SOCIAL = '1';
alter table IMACCOUNT_LINKS MODIFY SHOW_IN_SOCIAL not null;

-- Номер ревизии: 66506
-- Номер версии: v1.18
-- Комментарий: BUG069346: Не отображать ссылку Выберите отделение, если нет доступных ВСП.
create index IDX_DEP_TB_IS_CRED_CARD_OFFICE on DEPARTMENTS(IS_CREDIT_CARD_OFFICE, TB);

-- Номер ревизии: 66581
-- Номер версии: v1.18
-- Комментарий: Параметризация дополнительного соглашешения к Сберегательному счету
alter table DEPOSITGLOBALS add PERCENT_RATES_TRANSFORMATION CLOB;

-- Номер ревизии: 66619
-- Номер версии: v1.18
-- Комментарий: Справочник тарифных планов

DELETE FROM TARIF_PLAN_CONFIGS;
ALTER TABLE TARIF_PLAN_CONFIGS ADD (NAME  VARCHAR2(100 BYTE) NOT NULL);
ALTER TABLE TARIF_PLAN_CONFIGS ADD (DATE_BEGIN TIMESTAMP(6) NOT NULL);
ALTER TABLE TARIF_PLAN_CONFIGS ADD (DATE_END TIMESTAMP(6));
ALTER TABLE TARIF_PLAN_CONFIGS ADD (STATE CHAR(1 BYTE) DEFAULT '0' NOT NULL);

update PROFILE set TARIF_PLAN_CODE = '1' where TARIF_PLAN_CODE = 'PREMIER';
update PROFILE set TARIF_PLAN_CODE = '2' where TARIF_PLAN_CODE = 'GOLD';
update PROFILE set TARIF_PLAN_CODE = '3' where TARIF_PLAN_CODE = 'FIRST';
update PROFILE set TARIF_PLAN_CODE = null where TARIF_PLAN_CODE = 'UNKNOWN';

update USERS set TARIF_PLAN_CODE = '1' where TARIF_PLAN_CODE = 'PREMIER';
update USERS set TARIF_PLAN_CODE = '2' where TARIF_PLAN_CODE = 'GOLD';
update USERS set TARIF_PLAN_CODE = '3' where TARIF_PLAN_CODE = 'FIRST';
update USERS set TARIF_PLAN_CODE = null where TARIF_PLAN_CODE = 'UNKNOWN';

-- Номер ревизии: 66667
-- Номер версии: v1.18
-- Комментарий: CHG074515: Отображение новых и отложенных счетов в списке Счетов к оплате 

alter table INVOICES add (IS_NEW char(1));
update INVOICES set IS_NEW='0';
alter table INVOICES modify (IS_NEW not null);

alter table SHOP_ORDERS add (IS_NEW char(1));
update SHOP_ORDERS set IS_NEW='0';
alter table SHOP_ORDERS modify (IS_NEW not null);


-- Номер ревизии: 66672
-- Номер версии: v1.18
-- Комментарий: CHG073948  ЕРМБ. Нет проверки номера, с которого приходит подтверждение операции.
DELETE FROM CONFIRM_BEANS;

ALTER TABLE CONFIRM_BEANS ADD PHONE VARCHAR2(20) NOT NULL;

DROP INDEX UNIQ_CONFIRM_LOGIN_CODE;

CREATE UNIQUE INDEX UI_LOGIN_CONFIRM_CODE_PRIMARY
   ON CONFIRM_BEANS (LOGIN_ID ASC, PRIMARY_CONFIRM_CODE ASC);

CREATE UNIQUE INDEX UI_LOGIN_CONFIRM_CODE_SEC
   ON CONFIRM_BEANS (LOGIN_ID ASC, SECONDARY_CONFIRM_CODE ASC);

-- Номер ревизии: 66813
-- Номер версии: 14.0 ПСИ
-- Комментарий: BUG077468: [ISUP] Необходимо расширить поле.
ALTER TABLE CARD_OPERATIONS MODIFY DESCRIPTION VARCHAR2(300)
/
ALTER TABLE CARD_OPERATIONS MODIFY ORIGINAL_DESCRIPTION VARCHAR2(300)
/

-- Номер ревизии: 66839   
-- Номер версии: 1.18
-- Комментарий: Веб представление настройки ТБ и канала 
--              Убрать заполнение поля APPLICATION для внешних ошибок
alter table EXCEPTION_ENTRY drop column message
go

alter table EXCEPTION_ENTRY modify APPLICATION null
go

CREATE OR REPLACE PROCEDURE updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in clob, exceptionSystem in varchar, exceptionErrorCode in varchar) as
   PRAGMA AUTONOMOUS_TRANSACTION;
   updateExceptionInformationOFF NUMBER;
BEGIN
    SELECT count(1) into updateExceptionInformationOFF from PROPERTIES where PROPERTY_KEY='com.rssl.phizic.business.exception.updateExceptionInformation' AND PROPERTY_VALUE = 'OFF' AND CATEGORY='iccs.properties';

    IF updateExceptionInformationOFF > 0 THEN
        ROLLBACK;
        RETURN;
    END IF;

    INSERT INTO EXCEPTIONS_LOG(CREATION_DATE, HASH) values (SYSDATE, exceptionHash);

    INSERT INTO EXCEPTION_ENTRY (ID, KIND, HASH, APPLICATION, OPERATION, DETAIL, SYSTEM, ERROR_CODE) 
         SELECT S_EXCEPTION_ENTRY.nextval, exceptionKind, exceptionHash, exceptionApplication, exceptionOperation, exceptionDetail, exceptionSystem, exceptionErrorCode FROM DUAL
        WHERE NOT EXISTS (SELECT 1 FROM EXCEPTION_ENTRY WHERE EXCEPTION_ENTRY.HASH = exceptionHash);

    COMMIT;
END;
go
 

-- Номер ревизии: 66871
-- Номер версии: 1.18
-- Комментарий: Создать таблицы для хранения языка в системе.
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
   constraint PK_ERIB_LOCALES primary key (ID)
)
go


-- Номер ревизии: 66883
-- Номер версии: 1.18
-- Комментарий: Информирование клиентов о входе с нестандартного ip-адреса. Сущность оповещения
create sequence S_UNUSUAL_IP_NOTIFICATIONS
go

create table UNUSUAL_IP_NOTIFICATIONS
(
    ID              INTEGER     NOT NULL,
    DATE_CREATED    TIMESTAMP   NOT NULL,
    LOGIN_ID        NUMBER      NOT NULL,
    ATTEMPTS_COUNT  INTEGER     NOT NULL,

   constraint PK_UNUSUAL_IP_NOTIFICATIONS primary key (ID)
)
go

create index I_UNUSUAL_IP_NOTIFICATIONS on UNUSUAL_IP_NOTIFICATIONS 
(
    DATE_CREATED
)
go



-- Номер ревизии: 66935
-- Номер версии: 1.18
-- Комментарий: Реализовать таблицы для хранения текстовок в БД.
create table ERIB_STATIC_MESSAGE
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   BUNDLE               VARCHAR2(30)         not null,
   KEY                  VARCHAR2(100)        not null,
   MESSAGE              VARCHAR2(2048)       not null,
   constraint PK_ERIB_STATIC_MESSAGE primary key (ID)
)
go

create sequence S_ERIB_STATIC_MESSAGE
go
-- Номер ревизии: 66938
-- Номер версии: 1.18
-- Комментарий: CHG077624: Добавить признак видимости кредитного продкута (1.18)
alter table CREDIT_PRODUCT_CONDITION add  IS_SEL_AVAILABLE char(1)
/
update CREDIT_PRODUCT_CONDITION set IS_SEL_AVAILABLE = '1' 
/
alter table CREDIT_PRODUCT_CONDITION modify IS_SEL_AVAILABLE not null
/


-- Номер ревизии: 67074
-- Номер версии: 1.18
-- Комментарий: Информирование клиентов о входе с нестандартного ip-адреса. Сущность оповещения. Поле MESSAGE.
alter table UNUSUAL_IP_NOTIFICATIONS add MESSAGE varchar2(80) not null
go

-- Номер ревизии: 67209
-- Номер версии: 1.18
-- Комментарий: Сохранение служебной информации в профиле клиента.
create table USER_SERVICE_INFO 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   DATA                 VARCHAR2(256),
   constraint PK_USER_SERVICE_INFO primary key (ID)
);

create sequence S_USER_SERVICE_INFO;

create unique index IDX_USER_SERVICE_INFO on USER_SERVICE_INFO (
   LOGIN_ID ASC
);


-- Номер ревизии: 67255
-- Номер версии: 1.18
-- Комментарий: Stand-In. Доработка схем прав резервного блока

update DEFAULT_ACCESS_SCHEMES set CREATION_TYPE = 'CARD_TEMPORARY' where CREATION_TYPE = 'TEMPORARY'
go

insert into DEFAULT_ACCESS_SCHEMES (ID, CREATION_TYPE, SCHEME_ID, DEPARTMENT_TB)
select S_DEFAULT_ACCESS_SCHEMES.nextval, 'UDBO_TEMPORARY', SCHEME_ID, null from DEFAULT_ACCESS_SCHEMES where CREATION_TYPE = 'CARD_TEMPORARY'
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

-- Номер ревизии: 67482
-- Номер версии: 1.18
-- Комментарий: Реализовать выгрузку xml файла сообщений.
create index I_ERIB_STATIC_MESSAGE_LBK on ERIB_STATIC_MESSAGE (
   LOCALE_ID ASC,
   BUNDLE ASC,
   KEY ASC
)
go
drop index I_ERIB_STATIC_MESSAGE_LI
go
drop index I_ERIB_STATIC_MESSAGE_BK
go

-- Номер ревизии: 67499
-- Номер версии: 1.18
-- Комментарий: Удаление индекса IDX_DEF_PNV

DROP INDEX IDX_DEF_PNV 


-- Номер ревизии: 67513
-- Номер версии: 1.18
-- Комментарий: Краудгифтинг. часть 1

create table FUND_INITIATOR_REQUESTS (
ID integer not null,
EXTERNAL_ID varchar2(100) not null,
LOGIN_ID integer not null,
STATE varchar2(16) not null,
REQUIRED_SUM number(19,4),
RECCOMEND_SUM number(19,4),
MESSAGE varchar2(256) not null,
TO_RESOURCE varchar2(30) not null,
EXPECTED_CLOSED_DATE timestamp,
CLOSED_DATE timestamp,
CLOSED_REASON varchar2(16),
CREATED_DATE timestamp not null,
SENDERS_COUNT integer not null 
)
partition by range
(CREATED_DATE) interval (NUMTOYMINTERVAL(1, 'MONTH'))
(partition P_START values less than (to_date('01-09-2014','DD-MM-YYYY')))
go

create sequence S_FUND_INITIATOR_REQUESTS cache 10000
go

create unique index I_FUND_I_REQ_ID on FUND_INITIATOR_REQUESTS
(
    ID
)
global 
go

alter table FUND_INITIATOR_REQUESTS 
    add constraint PK_FUND_REQUESTS primary key (ID) using index I_FUND_I_REQ_ID
go

create index I_FUND_I_REQUESTS_LOGIN on FUND_INITIATOR_REQUESTS
(
    LOGIN_ID,
    CREATED_DATE
)
local
go

create index I_FUND_I_REQUESTS_STATE on FUND_INITIATOR_REQUESTS (
    STATE,
    CREATED_DATE
)
local
go

create table FUND_INITIATOR_RESPONSES (
ID integer not null,
EXTERNAL_ID varchar2(100) not null,
REQUEST_ID integer not null,
PHONE varchar2(15) not null,
STATE varchar2(10) not null,
SUM number(19,4),
MESSAGE varchar2(256),
CREATED_DATE timestamp not null,
ACCUMULATED char not null
)
partition by range
(CREATED_DATE) interval (NUMTOYMINTERVAL(1, 'MONTH'))
(partition P_START values less than (to_date('01-09-2014','DD-MM-YYYY')))
go

create sequence S_FUND_INITIATOR_RESPONSES
go

alter table FUND_INITIATOR_RESPONSES 
    add constraint FK_FUND_I_RESPONSES foreign key (REQUEST_ID)
        references FUND_INITIATOR_REQUESTS (ID)
go

create unique index I_FUND_I_RESPONSES_EXT_ID on FUND_INITIATOR_RESPONSES
(
    EXTERNAL_ID
)
go

create index I_FUND_I_RESPONSES_REQUEST on FUND_INITIATOR_RESPONSES
(
    REQUEST_ID,
    STATE
)
go

create index I_FUND_I_ACCUMULATED on FUND_INITIATOR_RESPONSES (
    decode(ACCUMULATED, '1', REQUEST_ID, null)
)
local
go

create table FUND_SENDER_RESPONSES (
    ID integer not null,
    FIRST_NAME varchar2(120) not null,
    SUR_NAME varchar2(120) not null,
    PATR_NAME varchar2(120),
    BIRTH_DATE timestamp not null,
    TB varchar2(4) not null,
    PASSPORT varchar2(100) not null,
    EXTERNAL_RESPONSE_ID varchar2(100) not null,
    EXTERNAL_REQUEST_ID varchar2(100) not null,
    STATE varchar2(16) not null,
    SUM number(19,4),
    MESSAGE varchar2(256),
    PAYMENT_ID integer,
    INITIATOR_FIRST_NAME varchar2(120) not null,
    INITIATOR_SUR_NAME varchar2(120) not null,
    INITIATOR_PATR_NAME varchar2(120),
    INITIATOR_PHONES varchar2(1500) not null,
    REQUEST_MESSAGE varchar2(256) not null,
    REQUEST_STATE varchar2(8) not null,
    REQUIRED_SUM number(19,4),
    RECCOMEND_SUM number(19,4),
    TO_RESOURCE varchar2(30) not null,
    CLOSED_DATE timestamp,
    EXPECTED_CLOSED_DATE timestamp,
    CREATED_DATE timestamp not null
)
partition by range
(CREATED_DATE) interval (NUMTOYMINTERVAL(1, 'MONTH'))
(partition P_START values less than (to_date('01-09-2014','DD-MM-YYYY')))
go

create sequence S_FUND_SENDER_RESPONSES cache 10000
go

create unique index I_FUND_S_RESP_ID on FUND_SENDER_RESPONSES (
    ID
)
global
go

create index I_FUND_S_RESP_UNIVERSAL on FUND_SENDER_RESPONSES (
    UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'()+',' '))) ASC,
    BIRTH_DATE ASC,
    TB ASC,
    REPLACE(PASSPORT,' ','') ASC,
    CREATED_DATE
)
local
go

create index I_FUND_S_REQ_EXT_ID on FUND_SENDER_RESPONSES (
    EXTERNAL_REQUEST_ID,
    CREATED_DATE
)
local
go

create table FUND_GROUPS (
    ID integer not null primary key,
    LOGIN_ID integer not null,
    NAME varchar2(500) not null
)
go

create sequence S_FUND_GROUPS
go

create index I_FUND_GROUP_LOGIN on FUND_GROUPS(
    LOGIN_ID
)
go

create table FUND_GROUP_PHONES (
    GROUP_ID integer not null,
    PHONE varchar2(15) not null
)
go

create unique index I_FUND_GROUP_PHONE on FUND_GROUP_PHONES (
    GROUP_ID,
    PHONE
)
go

-- Номер ревизии: 67518
-- Номер версии: 1.18
-- Комментарий: Изменение логики загрузки справочника БИК(Модель БД)
alter table RUSBANKS add DATE_CH timestamp
go

-- Номер ревизии: 67538
-- Номер версии: 1.18
-- Комментарий: Секционирование FUND_INITIATOR_REQUESTS
drop table FUND_INITIATOR_RESPONSES 
go
drop table FUND_INITIATOR_REQUESTS
go
create table FUND_INITIATOR_REQUESTS 
(
   ID                   INTEGER              not null,
   EXTERNAL_ID          VARCHAR2(100)        not null,
   LOGIN_ID             INTEGER              not null,
   STATE                VARCHAR2(16)         not null,
   REQUIRED_SUM         NUMBER(19,4),
   RECCOMEND_SUM        NUMBER(19,4),
   MESSAGE              VARCHAR2(256)        not null,
   TO_RESOURCE          VARCHAR2(30)         not null,
   EXPECTED_CLOSED_DATE TIMESTAMP,
   CLOSED_DATE          TIMESTAMP,
   CLOSED_REASON        VARCHAR2(16),
   CREATED_DATE         TIMESTAMP            not null,
   SENDERS_COUNT        INTEGER              not null,
   constraint I_PK_FUND_REQUESTS primary key (ID)
         using index global
)
partition by range
 (CREATED_DATE)
    interval (NUMTOYMINTERVAL(1, 'MONTH'))
 (partition
         P_START
        values less than (to_date('01-09-2014','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: I_FUND_I_REQUESTS_LOGIN                               */
/*==============================================================*/
create index I_FUND_I_REQUESTS_LOGIN on FUND_INITIATOR_REQUESTS (
   LOGIN_ID ASC,
   CREATED_DATE ASC
)
local
go

/*==============================================================*/
/* Index: I_FUND_I_REQUESTS_STATE                               */
/*==============================================================*/
create index I_FUND_I_REQUESTS_STATE on FUND_INITIATOR_REQUESTS (
   STATE ASC,
   CREATED_DATE ASC
)
local
go

create table FUND_INITIATOR_RESPONSES (
ID integer not null,
EXTERNAL_ID varchar2(100) not null,
REQUEST_ID integer not null,
PHONE varchar2(15) not null,
STATE varchar2(10) not null,
SUM number(19,4),
MESSAGE varchar2(256),
CREATED_DATE timestamp not null,
ACCUMULATED char not null
)
partition by range
(CREATED_DATE) interval (NUMTOYMINTERVAL(1, 'MONTH'))
(partition P_START values less than (to_date('01-09-2014','DD-MM-YYYY')))
go


alter table FUND_INITIATOR_RESPONSES 
    add constraint FK_FUND_I_RESPONSES foreign key (REQUEST_ID)
        references FUND_INITIATOR_REQUESTS (ID)
go

create unique index I_FUND_I_RESPONSES_EXT_ID on FUND_INITIATOR_RESPONSES
(
    EXTERNAL_ID
)
go

create index I_FUND_I_RESPONSES_REQUEST on FUND_INITIATOR_RESPONSES
(
    REQUEST_ID,
    STATE
)
go

create index I_FUND_I_ACCUMULATED on FUND_INITIATOR_RESPONSES (
    decode(ACCUMULATED, '1', REQUEST_ID, null)
)
local
go

-- Номер ревизии: 67549
-- Номер версии: 1.18
-- Комментарий: CHG078189: [АЛФ] Пропадают категории из управления расходами.

insert into USER_PROPERTIES(ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE)
select S_USER_PROPERTIES.nextval, budgetLogins.LOGIN_ID, 'com.rssl.phizic.userSettings.usedALFBudgets', 'true' from 
    (select budget.LOGIN_ID from CLIENTS_BUDGET budget group by LOGIN_ID) budgetLogins
/

-- Номер ревизии: 67625
-- Номер версии: 1.18
-- Комментарий: Оптимизаци загрузки кредитных предложений ч.1

delete from LOAN_OFFER
go
alter table LOAN_OFFER drop column LOGIN_ID
go
alter table LOAN_OFFER drop column DEPARTMENT_ID
go
alter table LOAN_OFFER add FIRST_NAME VARCHAR2(20) not null
go
alter table LOAN_OFFER add SUR_NAME VARCHAR2(20) not null
go
alter table LOAN_OFFER add PATR_NAME VARCHAR2(20)
go
alter table LOAN_OFFER add BIRTHDAY TIMESTAMP not null
go
create index DXFK_LOAN_OFFER on LOAN_OFFER 
(
   upper(replace(SUR_NAME, ' ', '')||replace(FIRST_NAME, ' ', '')||replace(PATR_NAME, ' ', '')) asc,
   upper(replace(PASPORT_SERIES, ' ', '')||replace(PASPORT_NUMBER, ' ', '')) asc,
   BIRTHDAY ASC,
   TB ASC
)
Go

-- Номер ревизии: 67636
-- Номер версии: 1.18
-- Комментарий: ENH078444 [ISUP] Оптимизировать загрузку предложений кредитам.

alter table LOAN_OFFER add LOGIN_ID INTEGER
go
alter table LOAN_OFFER add DEPARTMENT_ID INTEGER
go


-- Номер ревизии: 67711
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Баннеры)

CREATE TABLE ADVERTISING_BUTTONS_RES  ( 
    ID       	NUMBER NOT NULL,
    LOCALE_ID	VARCHAR2(10) NOT NULL,
    TITLE    	VARCHAR2(100) NOT NULL,
    CONSTRAINT PK_ADVERTISING_BUTTONS_RES PRIMARY KEY(ID,LOCALE_ID)
)
GO

CREATE TABLE ADVERTISINGS_RES  ( 
    ID       	NUMBER NOT NULL,
    LOCALE_ID	VARCHAR2(10) NOT NULL,
    TITLE    	VARCHAR2(100) NOT NULL,
    TEXT     	VARCHAR2(400) NOT NULL,
    CONSTRAINT PK_ADVERTISINGS_RES PRIMARY KEY(ID,LOCALE_ID)	
)
GO

-- Номер ревизии: 66935
-- Номер версии: 1.18
-- Комментарий: ИОК
alter table ERIB_STATIC_MESSAGE modify BUNDLE VARCHAR2(40)
/
alter table ERIB_STATIC_MESSAGE modify KEY VARCHAR2(200)
/

-- Номер ревизии: 67764
-- Номер версии: 1.18
-- Комментарий: Профиль. Адресная книга (хранение адресной книги)
create table ADDRESS_BOOKS (
	ID INTEGER NOT NULL,
	LOGIN_ID INTEGER NOT NULL,
	PHONE VARCHAR2(11) NOT NULL,
	FULL_NAME VARCHAR2(60) NOT NULL,
	SBERBANK_CLIENT CHAR(1) NOT NULL,
	INCOGNITO CHAR(1) NOT NULL,
	FIO VARCHAR2(100),
	ALIAS VARCHAR2(60),
	CUT_ALIAS VARCHAR2(7),
	AVATAR VARCHAR2(256),
	CARD_NUMBER VARCHAR2(20),
	CATEGORY VARCHAR2(10) NOT NULL,
	TRUSTED CHAR(1) NOT NULL,
	FREQUENCY_P2P NUMBER NOT NULL,
	FREQUENCY_PAY NUMBER NOT NULL,
	ADDED_BY VARCHAR2(10) NOT NULL,
	STATUS VARCHAR2(10) NOT NULL,
	
	constraint PK_ADDRESS_BOOK primary key(ID)
)
PARTITION BY HASH (LOGIN_ID)
PARTITIONS 128
go	

alter table ADDRESS_BOOKS add constraint FK_AB_LOGIN_ID foreign key (LOGIN_ID) references LOGINS(ID)
go

create index IDX_AB_LGID on ADDRESS_BOOKS (
      LOGIN_ID asc,
      PHONE asc
)
local
go

create index IDX_AB_PHN on ADDRESS_BOOKS (
      PHONE asc
)
global
go

create sequence S_ADDRESS_BOOK cache 10000
go

-- Номер ревизии: 67767
-- Номер версии: 1.18
-- Комментарий: Кредитный профиль клиента
create table USER_CREDIT_PROFILE 
(
   USER_ID              INTEGER              not null,
   CONNECTED            CHAR(1)              not null,
   LAST_BKI_REQUEST     TIMESTAMP            not null,
   LAST_PAYMENT         TIMESTAMP,
   LAST_REPORT          TIMESTAMP,
   REPORT_XML           CLOB
)
partition by hash
 (USER_ID)
 partitions 64
;

create sequence S_USER_CREDIT_PROFILE
;

create unique index UNIQ_USER_CREDIT_PROFILE on USER_CREDIT_PROFILE (
   USER_ID ASC
)
local
;

alter table USER_CREDIT_PROFILE add constraint PK_USER_CREDIT_PROFILE primary key (USER_ID)
         using index UNIQ_USER_CREDIT_PROFILE
; 

alter table USER_CREDIT_PROFILE
   add constraint FK_USER_CREDIT_PROFILE_USERS foreign key (USER_ID)
      references USERS (ID)
;

-- Номер ревизии: 67760
-- Номер версии: 1.18
-- Комментарий: Синхронизация локалезависимых сущностей(Баннеры, перевод на UUID)

drop table ADVERTISING_BUTTONS_RES
go

drop table ADVERTISINGS_RES
go


CREATE TABLE ADVERTISING_BUTTONS_RES  ( 
    UUID       	VARCHAR2(32) NOT NULL,
    LOCALE_ID	VARCHAR2(30) NOT NULL,
    TITLE    	VARCHAR2(100) NOT NULL,
    CONSTRAINT PK_ADVERTISING_BUTTONS_RES PRIMARY KEY(UUID,LOCALE_ID)
)
GO

CREATE TABLE ADVERTISINGS_RES  ( 
    UUID       	VARCHAR2(32) NOT NULL,
    LOCALE_ID	VARCHAR2(30) NOT NULL,
    TITLE    	VARCHAR2(100) NOT NULL,
    TEXT     	VARCHAR2(400) NOT NULL,
    CONSTRAINT PK_ADVERTISINGS_RES PRIMARY KEY(UUID,LOCALE_ID)	
)
GO

-- Номер ревизии: 67770
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Новости)
create table NEWS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   TITLE                VARCHAR2(100)        not null,
   SHORT_TEXT           VARCHAR2(150)        not null,
   TEXT                 CLOB                 not null,
   constraint PK_NEWS_RES primary key (UUID, LOCALE_ID)
)
go

-- Номер ревизии: 67779
-- Номер версии: 1.18
-- Комментарий: Удалить функционал завязанный на неиспользуемую таблицу «CURBANKS»
drop table CURBANKS
go

-- Номер ревизии: 67798
-- Номер версии: 1.18
-- Комментарий: Профиль. Адресная книга (первоначальное заполнение)
insert into ADDRESS_BOOKS 
    (
        ID, 
        LOGIN_ID, 
        FULL_NAME, 
        PHONE, 
        SBERBANK_CLIENT, 
        INCOGNITO, 
        CATEGORY, 
        TRUSTED, 
        FREQUENCY_P2P,
        FREQUENCY_PAY, 
        ADDED_BY,
        STATUS
    )
select 
    S_ADDRESS_BOOK.nextval as ID,
    LOGIN_ID as LOGIN_ID, 
    NAME as FULL_NAME,
    PHONE as PHONE, 
    0 as SBERBANK_CLIENT, 
    0 as INCOGNITO, 
    'NONE' as CATEGORY, 
    0 as TRUSTED, 
    0 as FREQUENCY_P2P, 
    0 as FREQUENCY_PAY, 
    'MOBILE' as ADDED_BY, 
    'ACTIVE' as STATUS 
from CONTACTS
go

drop table CONTACTS
go

drop table CONTACTS_SYNC
go


-- Номер ревизии: 67898
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Справочник категорий операций)

CREATE TABLE CARD_OPERATION_CATEGORIES_RES  ( 
    ID       	INTEGER NOT NULL,
    LOCALE_ID	VARCHAR2(30) NOT NULL,
    NAME    	VARCHAR2(100) NOT NULL,
    CONSTRAINT PK_CARD_OPERATION_CATEGOR_RES PRIMARY KEY(ID,LOCALE_ID)
)
GO

-- Номер ревизии: 67915
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Регионы)
CREATE TABLE REGION_RES  ( 
    UUID     	VARCHAR2(32) NOT NULL,
    LOCALE_ID	VARCHAR2(30) NOT NULL,
    NAME     	VARCHAR2(128) NOT NULL,
    CONSTRAINT PK_REGION_RES PRIMARY KEY(UUID,LOCALE_ID)
GO
alter table REGIONS add UUID VARCHAR2(32)
go
create unique index I_REGIONS_UUID on REGIONS(
    UUID
)
go
--Заполнить UUID данными из ЦСААдмин
--Если CSAAdmin не включено (не многоблочный режим) выполнить закомментированное:
--update REGIONS set UUID = DBMS_RANDOM.STRING('X', 32)
--go
alter table REGIONS modify UUID not null
go


-- Номер ревизии: 67926
-- Номер версии: 1.18
-- Комментарий: Stand-In. форма состояния миграции (ч.3 сущности модели)
CREATE TABLE MIGRATION_INFO  ( 
    ID         	INTEGER     NOT NULL,
    TOTAL_COUNT	INTEGER     NOT NULL,
    BATCH_SIZE  INTEGER,
    NEED_STOP   CHAR(1)     NOT NULL,
    CONSTRAINT PK_MIGRATION_INFO PRIMARY KEY(ID)
)
GO

create sequence S_MIGRATION_INFO 
go

CREATE TABLE MIGRATION_THREAD_INFO  ( 
    ID         	INTEGER         NOT NULL,
    PARENT_ID   INTEGER         NOT NULL,
    STATE      	VARCHAR2(16)    NOT NULL,
    GOOD_COUNT 	INTEGER         NOT NULL,
    BAD_COUNT  	INTEGER         NOT NULL,
    START_DATE 	TIMESTAMP       NOT NULL,
    END_DATE   	TIMESTAMP,
    CONSTRAINT PK_MIGRATION_THREAD_INFO PRIMARY KEY(ID)
)
GO

create sequence S_MIGRATION_THREAD_INFO
go

create unique index I_MIGRATION_INFO_BATCH_SIZE on MIGRATION_INFO
(
    decode(BATCH_SIZE, null, '1', null)
)
go 

create index "DXFK_MIGRATION_TOTAL_TO_THREAD" on MIGRATION_THREAD_INFO
(
   PARENT_ID
)
go

alter table MIGRATION_THREAD_INFO
   add constraint FK_MIGRATION_TOTAL_TO_THREAD foreign key (PARENT_ID)
      references MIGRATION_INFO (ID)
      on delete cascade
go


-- Номер ревизии: 67926
-- Номер версии: 1.18
-- Комментарий: Stand-In. форма состояния миграции (ч.3 сущности модели)
create index I_MIGRATION_THREAD_INFO_STATE on MIGRATION_THREAD_INFO (
    decode(STATE, 'WAIT', '1', null)
)
go


-- Номер ревизии: 67926
-- Номер версии: 1.18
-- Комментарий: Stand-In. форма состояния миграции (ч.3 сущности модели)
alter table MIGRATION_THREAD_INFO modify PARENT_ID null
go


-- Номер ревизии: 67998
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Банки)
create table RUSBANKS_RES 
(
   ID                   VARCHAR2(64)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(256)        not null,
   PLACE                VARCHAR2(50),
   SHORT_NAME           VARCHAR2(256),
   ADDRESS              VARCHAR2(256),
   constraint PK_RUSBANKS_RES primary key (ID, LOCALE_ID)
)
go
-- Номер ревизии: 68017
-- Номер версии: 1.18
-- Комментарий: Интеграция с БКИ. Справочники констант БКИ. 

create table BKI_BANK_CONSTANT_NAME 
(
    CODE varchar2(50) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_BANK_CONSTANT_NAME primary key(CODE)
)
go

create table BKI_FINANCE_TYPE
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_FINANCE_TYPE primary key(CODE)
)
go

create table BKI_PURPOSE_OF_FINANCE
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_PURPOSE_OF_FINANCE primary key(CODE)
)
go

create table BKI_ACCOUNT_PAYMENT_STATUS
(
   CODE varchar2(2) not null,
    NAME varchar2(60) not null,
    constraint PK_BKI_ACCOUNT_PAYMENT_STATUS primary key(CODE)
)
go

create table BKI_TYPE_OF_SECURITY
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_TYPE_OF_SECURITY primary key(CODE)
)
go

create table BKI_APPLICATION_TYPE
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_APPLICATION_TYPE primary key(CODE)
)
go

create table BKI_PRIMARY_ID_TYPE
(
    CODE varchar2(2) not null,
    NAME varchar2(100) not null,
    constraint PK_BKI_BKI_PRIMARY_ID_TYPE primary key(CODE)
)
go

create table BKI_ADRESS_TYPE
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    REGISTRATION CHAR(1) not null,
    RESIDENCE CHAR(1) not null,
    constraint PK_BKI_ADRESS_TYPE primary key(CODE)
)
go

create table BKI_REGION_CODE
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_REGION_CODE primary key(CODE)
)
go

create table BKI_COUNTRY
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_COUNTRY primary key(CODE)
)
go

create table BKI_REASON_FOR_CLOSURE
(
    CODE varchar2(2) not null,
    NAME varchar2(60) not null,
    constraint PK_BKI_REASON_FOR_CLOSURE primary key(CODE)
)
go

create table BKI_REASON_FOR_ENQUIRY
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_REASON_FOR_ENQUIRY primary key(CODE)
)
go

create table BKI_TYPE_OF_SCORE_CARD
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_TYPE_OF_SCORE_CARD primary key(CODE)
)
go


create table BKI_ACCOUNT_CLASS
(
    CODE varchar2(2) not null,
    NAME varchar2(60) not null,
    constraint PK_BKI_ACCOUNT_CLASS primary key(CODE)
)
go


create table BKI_DISPUTE_INDICATOR
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_DISPUTE_INDICATOR primary key(CODE)
)
go

create table BKI_APPLICANT_TYPE
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_APPLICANT_TYPE primary key(CODE)
)
go

create table BKI_TITTLE
(
    CODE varchar2(2) not null,
    NAME varchar2(20) not null,
    constraint PK_BKI_BKI_TITTLE primary key(CODE)
)
go

create table BKI_SEX
(
    CODE varchar2(2) not null,
    NAME varchar2(20) not null,
    constraint PK_BKI_SEX primary key(CODE)
)
go

create table BKI_CURRENT_PREVIOUS_ADDRESS
(
    CODE varchar2(2) not null,
    NAME varchar2(50) not null,
    constraint PK_BKI_CURR_PREV_ADDR primary key(CODE)
);

create table BKI_CREDIT_FACILITY_STATUS
(
    CODE varchar2(2) not null,
    NAME varchar2(20) not null,
    constraint PK_BKI_CREDIT_FACILITY_STATUS primary key(CODE)
)
go

create table BKI_ACCOUNT_SPECIAL_STATUS
(
    CODE varchar2(2) not null,
    NAME varchar2(60) not null,
    constraint PK_BKI_ACCOUNT_SPECIAL_STATUS primary key(CODE)
)
go

create table BKI_INSURED_LOAN
(
    CODE varchar2(2) not null,
    NAME varchar2(20) not null,
    constraint PK_BKI_INSURED_LOAN primary key(CODE)
)
go

create table BKI_CONSENT_INDICATOR
(
    CODE varchar2(2) not null,
    NAME varchar2(20) not null,
    constraint PK_BKI_CONSENT_INDICATOR primary key(CODE)
)
go

-- Номер ревизии: 68032
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Редактирование поставщика услуг)
create table SERVICE_PROVIDERS_RES(
  UUID                              varchar2(32) NOT NULL,
  LOCALE_ID                         varchar2(30) NOT NULL,
  NAME                              VARCHAR2(160) NOT NULL,
  LEGAL_NAME                        VARCHAR2(250) NULL,
  ALIAS                             VARCHAR2(250) NULL,
  BANK_NAME                         VARCHAR2(128) NULL,
  DESCRIPTION                       VARCHAR2(512) NULL,
  TIP_OF_PROVIDER                   VARCHAR2(255) NULL,
  COMMISSION_MESSAGE                VARCHAR2(250) NULL,
  NAME_ON_BILL                      VARCHAR2(250) NULL,

CONSTRAINT PK_SERVICE_PROVIDERS_RES PRIMARY KEY(UUID, LOCALE_ID)
)


-- Номер ревизии: 68041
-- Номер версии: 1.18
-- Комментарий: Stand-In. механизм запуска миграции.
create or replace function "SyncNode"(gStreamId in number, gNodeId number, gPackSize in number default 0, gCSAProfileId in number default null ) return number
   IS state_code INTEGER;
begin
    return 1; 
end;
go

-- Номер ревизии: 68125
-- Номер версии: 1.18
-- Комментарий: BUG074679: Признак «Предлагать по умолчанию» применяется к нескольким картам в ПФП
drop index I_PFP_CARDS_DEFAULT_CARD
go

create unique index I_PFP_CARDS_DEFAULT_CARD on PFP_CARDS(
    decode(DEFAULT_CARD, '0', null, DEFAULT_CARD)
)
go

-- Номер ревизии: 68139
-- Номер версии: 1.18
-- Комментарий: Атрибуты для пуш уведомлений

alter table PUSH_PARAMS add (ATTRIBUTES clob)
go




-- Номер ревизии: 68186
-- Номер версии: 1.18
-- Комментарий: Корзина платежей. Реализация джоба автоматического создания подписок.

create index IDX_INVSUB_STATE_CREATION on INVOICE_SUBSCRIPTIONS (
   STATE ASC,
   START_DATE ASC
)
go

alter table INVOICE_SUBSCRIPTIONS add CONFIRM_STRATEGY_TYPE VARCHAR2(18)
go

update INVOICE_SUBSCRIPTIONS set CONFIRM_STRATEGY_TYPE = 'sms' where STATE <> 'AUTO'
go

alter table INVOICE_SUBSCRIPTIONS add CREATION_TYPE CHAR(1)
go

update INVOICE_SUBSCRIPTIONS set CREATION_TYPE = '0' where STATE = 'AUTO'
go

update INVOICE_SUBSCRIPTIONS set CREATION_TYPE = '1' where STATE <> 'AUTO'
go

alter table INVOICE_SUBSCRIPTIONS modify CREATION_TYPE not null
go


-- Номер ревизии: 68191
-- Номер версии: 1.18
-- Комментарий: Stand-In. Доработка формы миграции (инициализация задачи миграци).

alter table MIGRATION_INFO modify TOTAL_COUNT null
go

-- Номер ревизии: 68193
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей. Редактирование платформы.
create table MOBILE_PLATFORM_RES
(
    ID                 INTEGER not null,
    LOCALE_ID          VARCHAR2(30) not null,
    PLATFORM_NAME      VARCHAR2(100) not null,
    ERROR_TEXT         VARCHAR2(500) not null,
    constraint PK_MOBILE_PLATFORM_RES primary key (ID, LOCALE_ID) 
)
GO

-- Номер ревизии: 68243
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей. Техперерывы.
create table TECHNOBREAKS_RES 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   MESSAGE              VARCHAR2(200)        not null,
   constraint PK_TECHNOBREAKS_RES primary key (ID, LOCALE_ID)
)
go


-- Номер ревизии: 68267
-- Номер версии: 1.18
-- Комментарий: Редактирование локалезависимых сущностей(Маппинг ошибок)

create table EXCEPTION_MAPPINGS_RES 
(
   HASH                 varchar2(277)        not null,
   GROUP_ID             INTEGER              not null,
   LOCALE_ID            varchar2(30)         not null,
   MESSAGE              VARCHAR2(2000)       not null,
   constraint PK_EXCEPTION_MAPPINGS_RES primary key (HASH, GROUP_ID, LOCALE_ID)
)
go

-- Номер ревизии: 68331
-- Номер версии: 1.18
-- Комментарий: Синхронизация УАК

create table MBK_CAST (
    PHONE VARCHAR2(11) NOT NULL
)
partition by hash(PHONE)
partitions 128
go

create index IDX_MBK_CAST_PHONE on MBK_CAST (
   PHONE asc
)
local
go

drop table INCOGNITO_PROFILES
go

alter table INCOGNITO_PHONES add LOGIN_ID integer
go

update INCOGNITO_PHONES ip set ip.login_id = (select login_id from USERS u where u.id = ip.PROFILE_ID )
go

create index IDX_LOGIN_INCOGNITO on INCOGNITO_PHONES (LOGIN_ID asc)
go

create index IDX_PH_INCOGNITO on INCOGNITO_PHONES (PHONE_NUMBER asc)
go

alter table INCOGNITO_PHONES drop column PROFILE_ID
go

-- Номер ревизии 68387
-- Номер версии 1.18
-- Комментарий: Удалён Job 'DocumentConverterJob' и триггер 'DocumentConverterJobTrigger'  за ненадобностью. Сделано в рамках ENH077739

DELETE FROM QRTZ_SIMPLE_TRIGGERS WHERE TRIGGER_NAME = 'DocumentConverterJobTrigger' AND TRIGGER_GROUP = 'DEFAULT'
go
DELETE FROM QRTZ_CRON_TRIGGERS WHERE TRIGGER_NAME = 'DocumentConverterJobTrigger' AND TRIGGER_GROUP = 'DEFAULT'
go
DELETE FROM QRTZ_BLOB_TRIGGERS WHERE TRIGGER_NAME = 'DocumentConverterJobTrigger' AND TRIGGER_GROUP = 'DEFAULT'
go
DELETE FROM QRTZ_TRIGGER_LISTENERS WHERE TRIGGER_NAME = 'DocumentConverterJobTrigger' AND TRIGGER_GROUP = 'DEFAULT'
go
DELETE FROM QRTZ_TRIGGERS WHERE TRIGGER_NAME = 'DocumentConverterJobTrigger' AND TRIGGER_GROUP = 'DEFAULT'
go
DELETE FROM QRTZ_JOB_LISTENERS WHERE JOB_NAME = 'DocumentConverterJob' AND JOB_GROUP = 'DEFAULT'
go
DELETE FROM QRTZ_JOB_DETAILS WHERE JOB_NAME = 'DocumentConverterJob' AND JOB_GROUP = 'DEFAULT'
Go

-- Номер ревизии 68396
-- Номер версии 1.18
-- Комментарий: Доработка журналов регистрации и платежей.
alter table INPUT_REGISTER_JOURNAL add DEVICE_INFO VARCHAR2(255);

-- Номер ревизии 68377
-- Номер версии 1.18
-- Комментарий: Синхронизация УАК. Настройки УАК

ALTER TABLE MOBILE_PLATFORMS
    ADD SHOW_SB_ATTRIBUTE CHAR(1) default '1' NOT NULL 
/


-- Номер ревизии 68439
-- Номер версии 1.18
-- Комментарий: Многоязычность: 1)новости 2)баннеры(добавлен мультиязычный кеш и доработан механизм генерируемых запросов)
create index ADVERTISINGS_BY_TB_IDX on ADVERTISINGS_DEPARTMENTS (
   TB ASC,
   ADVERTISING_ID ASC
)
go

-- Номер ревизии 68446
-- Номер версии 1.18
-- Комментарий: CHG078948: Многоязычность. Загрузка статических текстов для русского (базового) языка для atmAPI
create index ATM_ERIB_STATIC_MESSAGE on ERIB_STATIC_MESSAGE (
   DECODE(substr( KEY , -4), '.atm', LOCALE_ID||KEY, null)  ASC
)
go

-- Номер ревизии 68530
-- Номер версии 1.18
-- Комментарий: ЗНИ 179118 исправление ошибки по бизнес логике изменения параметров подписки отчётов по картам
ALTER TABLE CARD_LINKS ADD CONTRACT_NUMBER VARCHAR2(64);


-- Номер ревизии 68546
-- Номер версии 1.18
-- Комментарий: Корзина. Линки для напоминаний

create table REMINDER_LINKS (
    LOGIN_ID integer not null,
    REMINDER_ID integer not null,
    DELAYED_DATE timestamp,
    PROCESS_DATE timestamp
)
go

create unique index I_REMINDER_LINKS on REMINDER_LINKS (
    LOGIN_ID,
    REMINDER_ID
)
go

-- Номер ревизии 68549
-- Номер версии 1.18
-- Комментарий: Корзина. Напоминания

alter table PAYMENTS_DOCUMENTS add (REMINDER_TYPE varchar2(16))
go
alter table PAYMENTS_DOCUMENTS add (REMINDER_ONCE_DATE timestamp)
go
alter table PAYMENTS_DOCUMENTS add (REMINDER_DAY_OF_MONTH integer)
go
alter table PAYMENTS_DOCUMENTS add (REMINDER_MONTH_OF_QUARTER integer)
go
alter table PAYMENTS_DOCUMENTS add (REMINDER_CREATED_DATE timestamp)
go

-- Номер ревизии 68573
-- Номер версии 1.18
-- Комментарий: Запрос отчета из БКИ - Ожидание отчета

ALTER TABLE USER_CREDIT_PROFILE RENAME COLUMN LAST_BKI_REQUEST TO LAST_CHECK_REQUEST;
ALTER TABLE USER_CREDIT_PROFILE ADD LAST_GET_ERROR TIMESTAMP;

-- Номер ревизии 68618
-- Номер версии 1.18
-- Комментарий: Запрос отчета из БКИ - Реализация джоба проверки наличия Кредитной Истории

/*==============================================================*/
/* Index: I_USER_CREDIT_PROFILE_RQ_TIME                         */
/*==============================================================*/
create index I_USER_CREDIT_PROFILE_RQ_TIME on USER_CREDIT_PROFILE (
   CONNECTED ASC,
   LAST_CHECK_REQUEST ASC
)
go

--Номер ревизии: 68732
--Номер версии: 1.18
--Комментарий: Хранение аватаров в АК

create table PHONES_TO_AVATAR (
    PHONE varchar2(11) not null,
    AVATAR_PATH varchar2(100) not null,
    LOGIN_ID integer
)
partition by hash(PHONE)
partitions 128
go

create index IDX_PH_TO_AV_PH on PHONES_TO_AVATAR (PHONE asc)
local
go

create index IDX_PH_TO_AV_LID on PHONES_TO_AVATAR (LOGIN_ID asc)
local
go

--Номер ревизии: 68760
--Номер версии: 1.18
--Комментарий: Многоязычность. Редактирование локалезависмых данных услуги.
create table PAYMENT_SERVICES_RES
(
   UUID                 VARCHAR2(50)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(128)        not null,
   DESCRIPTION          VARCHAR2(512),
   constraint PK_PAYMENT_SERVICES_RES primary key (UUID, LOCALE_ID)
)
go

--Номер ревизии: 68767
--Номер версии: 1.18
--Комментарий: Интеграция с БКИ (отправка запроса) - доработки заполнения полей запроса
DELETE FROM BKI_SEX;

ALTER TABLE BKI_SEX ADD ESB_CODE VARCHAR2(4);
ALTER TABLE BKI_SEX ADD IS_DEFAULT CHAR(1) NOT NULL;

DELETE FROM BKI_PRIMARY_ID_TYPE;

ALTER TABLE BKI_PRIMARY_ID_TYPE ADD ESB_CODE VARCHAR2(22);
ALTER TABLE BKI_PRIMARY_ID_TYPE ADD IS_DEFAULT CHAR(1) NOT NULL;

DELETE FROM BKI_COUNTRY;

ALTER TABLE BKI_COUNTRY ADD ESB_CODE VARCHAR2(22);
ALTER TABLE BKI_COUNTRY ADD IS_DEFAULT CHAR(1) NOT NULL;

DELETE FROM BKI_ADRESS_TYPE;

ALTER TABLE BKI_ADRESS_TYPE ADD ESB_CODE VARCHAR2(22);
ALTER TABLE BKI_ADRESS_TYPE ADD IS_DEFAULT CHAR(1) NOT NULL;

--Номер ревизии: 68841
--Номер версии: 1.18
--Комментарий: Справочник БКИ - уникальность шинного кода
ALTER TABLE BKI_SEX ADD CONSTRAINT AK_ESB_BKI_SEX UNIQUE (ESB_CODE);
ALTER TABLE BKI_PRIMARY_ID_TYPE ADD CONSTRAINT AK_ESB_BKI_PRIM UNIQUE (ESB_CODE);
ALTER TABLE BKI_COUNTRY ADD CONSTRAINT AK_ESB_BKI_COUN UNIQUE (ESB_CODE);
ALTER TABLE BKI_ADRESS_TYPE ADD CONSTRAINT AK_ESB_BKI_ADRE UNIQUE (ESB_CODE);

-- Номер ревизии: 68972 
-- Номер версии: 1.18
-- Комментарий: Наполнение адресной книги. Из справочника доверенных получателей 
drop table RECENTLY_FILLED_FIELD_DATA
go 
drop sequence S_RECENTLY_FILLED_FIELD_DATA
go

-- Номер ревизии: 68982 
-- Номер версии: 1.18
-- Комментарий: Изменение отображения комиссии по операция в АС СБОЛ
alter table COMMISSIONS_SETTINGS add(SHOW_COMMISSION_RUB char(1));
update COMMISSIONS_SETTINGS set SHOW_COMMISSION_RUB='0';
alter table COMMISSIONS_SETTINGS modify(SHOW_COMMISSION_RUB char(1) not null); 

--Номер ревизии: 69002
--Номер версии: 1.18
--Комментарий: Бизнес отчет ЕРМБ «Количество клиентов с разбивкой по тарифным планам». История изменений тарифного плана
create table ERMB_CLIENT_TARIFF_HISTORY
(
    ID INTEGER not null,
    ERMB_PROFILE_ID INTEGER not null,
    ERMB_TARIFF_ID INTEGER not null,
    ERMB_TARIFF_NAME VARCHAR2(128) null,
    CHANGE_DATE TIMESTAMP not null,
    TB VARCHAR2(4) not null
)

partition by range (CHANGE_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(partition P_START values less than (timestamp' 2014-01-01 00:00:00'))
;

create sequence S_ERMB_CLIENT_TARIFF_HISTORY
;

--Номер ревизии: 69090
--Номер версии: 1.18
--Комментарий: Агрегация поставщиков услуг
/*==============================================================*/
/* Table: AGGREGATION_STATE                                     */
/*==============================================================*/
create table AGGREGATION_STATE 
(
   KEY                  VARCHAR2(64)         not null,
   TIME                 TIMESTAMP
)
go

/*==============================================================*/
/* Table: PAYMENT_SERVICES_AGGR                                 */
/*==============================================================*/
create table PAYMENT_SERVICES_AGGR 
(
   SERVICE_ID           INTEGER              not null,
   PARENT_SERVICE_ID    INTEGER,
   SERVICE_NAME         VARCHAR2(128)        not null,
   IMAGE_ID             INTEGER,
   IMAGE_NAME           VARCHAR2(128)        not null,
   GUID                 VARCHAR2(50)         not null,
   CHANEL               VARCHAR2(10)         not null,
   REGION_ID            INTEGER              not null,
   PRIORITY             INTEGER,
   AVAILABLE            VARCHAR2(32)         not null
)
go

/*==============================================================*/
/* Index: IDX_PSA                                               */
/*==============================================================*/
create index IDX_PSA on PAYMENT_SERVICES_AGGR (
   REGION_ID ASC,
   CHANEL ASC,
   AVAILABLE ASC,
   PARENT_SERVICE_ID ASC
)
go
/*==============================================================*/
/* Table: SERVICE_PROVIDERS_AGGR                                */
/*==============================================================*/
create table SERVICE_PROVIDERS_AGGR 
(
   ID                   INTEGER              not null,
   PROVIDER_NAME        VARCHAR2(160)        not null,
   ALIAS                VARCHAR2(250),
   LEGAL_NAME           VARCHAR2(250),
   CODE_RECIPIENT_SBOL  VARCHAR2(32),
   NAME_B_SERVICE       VARCHAR2(150),
   BILLING_ID           INTEGER,
   SORT_PRIORITY        INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   INN                  VARCHAR2(12),
   ACCOUNT              VARCHAR2(25),
   IMAGE_ID             INTEGER,
   IMAGE_UPDATE_TIME    TIMESTAMP,
   IMAGE_MD5            VARCHAR(32),
   H_IMAGE_ID           INTEGER,
   H_IMAGE_UPDATE_TIME  TIMESTAMP,
   H_IMAGE_MD5          VARCHAR(32),
   IS_BAR_SUPPORTED     CHAR(1),
   SUB_TYPE             VARCHAR2(10),
   CHANEL               VARCHAR2(10)         not null,
   REGION_ID            INTEGER              not null,
   AVAILABLE_PAYMENTS   CHAR(1),
   AVAILABLE_ESB_AUTOP  CHAR(1),
   AVAILABLE_IQW_AUTOP  CHAR(1),
   AVAILABLE_BASKET     CHAR(1),
   AVAILABLE_TEMPLATES  CHAR(1),
   AVAILABLE_MB_TEMPLATES CHAR(1),
   SERVICE_ID           INTEGER              not null,
   SERVICE_NAME         VARCHAR2(128),
   SERVICE_IMAGE        VARCHAR2(128),
   SERVICE_GUID         VARCHAR2(50),
   SHOW_SERVICE         CHAR(1),
   GROUP_ID             INTEGER,
   GROUP_NAME           VARCHAR2(128),
   GROUP_IMAGE          VARCHAR2(128),
   GROUP_GUID           VARCHAR2(50),
   SHOW_GROUP           CHAR(1),
   CATEGORY_ID          INTEGER,
   CATEGORY_NAME        VARCHAR2(128),
   CATEGORY_IMAGE       VARCHAR2(128),
   CATEGORY_GUID        VARCHAR2(50),
   SHOW_CATEGORY        CHAR(1)
)
go

/*==============================================================*/
/* Index: IDX_SPA                                               */
/*==============================================================*/
create index IDX_SPA on SERVICE_PROVIDERS_AGGR (
   REGION_ID ASC,
   CHANEL ASC,
   SERVICE_ID ASC,
   INN ASC,
   ACCOUNT ASC,
   PROVIDER_NAME ASC,
   LEGAL_NAME ASC,
   ALIAS ASC
)
go

--Номер ревизии: 69105
--Номер версии: 1.18
--Комментарий: Бизнес отчет по оплате услуг в ЕРМБ

create table SMS_SERVICE_PAYMENT_STATISTICS
(
    PAYMENT_ID INTEGER not null,
    SERVICE_PROVIDER_ID INTEGER not null,
    SERVICE_PROVIDER_NAME VARCHAR2(160) null,
    PAYMENT_STATE VARCHAR2(10) not null,
    AMOUNT NUMBER(19, 4) not null,
    CURRENCY CHAR(3) not null,
    TB VARCHAR2(4) not null,
    FINAL_STATUS_DATE TIMESTAMP not null
)

partition by range (FINAL_STATUS_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(partition P_START values less than (timestamp' 2014-01-01 00:00:00'))
;


--Номер ревизии: 69129
--Номер версии: 1.18
--Комментарий: Перевод сообщений об ошибках, приходящих из ВС.

create table LOCALE_EXCEPTION_MAPPINGS 
(
   ID                   INTEGER              not null,
   MESSAGE_KEY          VARCHAR2(160),
   ERROR_KEY            VARCHAR2(20),
   PATTERN              VARCHAR2(2000)       not null,
   FORMATTER            VARCHAR2(2000)       not null,
   constraint PK_LOCALE_EXCEPTION_MAPPINGS primary key (ID)
)
go


--Номер ревизии: 69302  
--Номер версии: 1.18
--Комментарий: CHG079909: Не получать персону в LimitHelper 
drop index PROFILE_CHANNEL_OPER_DATE_IDX
go

create index PROFILE_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   PROFILE_ID ASC,
   nvl(CHANNEL_TYPE, 'NULL') ASC,
   OPERATION_DATE ASC
)
local
go

--Номер ревизии: 69322  
--Номер версии: 1.18
--Комментарий: АЛФ: категоризация операций push.
create table CARD_OPERATIONS_EXT_FIELDS 
(
   ID                   INTEGER              not null,
   CARD_OPERATION_ID    INTEGER              not null,
   PUSHUID              INTEGER,
   PARENT_PUSHUID       INTEGER,
   AUTHCODE             VARCHAR2(6),
   OPERATION_TYPE_WAY   VARCHAR2(2),
   LOAD_DATE_MAPI       TIMESTAMP(6),
   CATEGORY_CHANGE      CHAR(1)              not null,
   OPERATION_DATE       TIMESTAMP            not null,
   constraint PK_CARD_OPERATIONS_EXT_FIELDS primary key (ID)
)
partition by range
 (OPERATION_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST
        values less than (to_date('01-01-2012','DD-MM-YYYY')));

create sequence S_CARD_OPERATIONS_EXT_FIELDS;

create index I_CARDOPEF_PUSHUID on CARD_OPERATIONS_EXT_FIELDS (
   PUSHUID ASC
);

create index I_CARDOPEF_PARENT_PUSHUID on CARD_OPERATIONS_EXT_FIELDS (
   PARENT_PUSHUID ASC
);


create index I_CARDOPEF_CARD_OPERATION_ID on CARD_OPERATIONS_EXT_FIELDS (
   CARD_OPERATION_ID ASC
);

create index I_CARDOPEF_CARD_OPERATION_DATE on CARD_OPERATIONS_EXT_FIELDS (
   OPERATION_DATE ASC
)
local;

--Номер ревизии: 69329
--Номер версии: 1.18
--Комментарий: CHG079909: Не получать персону в LimitHelper 
drop index PROFILE_CHANNEL_OPER_DATE_IDX
go

create index PROFILE_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   PROFILE_ID ASC,
   CHANNEL_TYPE ASC,
   OPERATION_DATE ASC
)
local
go

-- Номер ревизии: 69382
-- Номер версии: 1.18
-- Комментарий: Добавление механизма технологических перерывов в ЦСА
ALTER TABLE TECHNOBREAKS ADD UUID VARCHAR2(32);

UPDATE TECHNOBREAKS
   SET UUID = SYS_GUID ();

ALTER TABLE TECHNOBREAKS MODIFY UUID NOT NULL;
/*==============================================================*/
/* Index: UI_TECHNOBREAKS                                       */
/*==============================================================*/
create unique index UI_TECHNOBREAKS on TECHNOBREAKS (
   UUID ASC
)
-- Номер ревизии: 69405
-- Номер версии: 1.18
-- Комментарий: Секционированная таблица агрегации

drop table SERVICE_PROVIDERS_AGGR 
go
/*==============================================================*/
/* Table: SERVICE_PROVIDERS_AGGR                                */
/*==============================================================*/
create table SERVICE_PROVIDERS_AGGR 
(
   ID                   INTEGER              not null,
   PROVIDER_NAME        VARCHAR2(300)        not null,
   ALIAS                VARCHAR2(1024),
   LEGAL_NAME           VARCHAR2(264),
   CODE_RECIPIENT_SBOL  VARCHAR2(32)         not null,
   NAME_B_SERVICE       VARCHAR2(150),
   BILLING_ID           INTEGER,
   SORT_PRIORITY        INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   INN                  VARCHAR2(12),
   ACCOUNT              VARCHAR2(25),
   IMAGE_ID             INTEGER,
   IMAGE_UPDATE_TIME    TIMESTAMP,
   IMAGE_MD5            VARCHAR(32),
   H_IMAGE_ID           INTEGER,
   H_IMAGE_UPDATE_TIME  TIMESTAMP,
   H_IMAGE_MD5          VARCHAR(32),
   IS_BAR_SUPPORTED     CHAR(1),
   SUB_TYPE             VARCHAR2(10),
   CHANEL               VARCHAR2(10)         not null,
   REGION_ID            INTEGER              not null,
   AVAILABLE_PAYMENTS   CHAR(1),
   AVAILABLE_ESB_AUTOP  CHAR(1),
   AVAILABLE_IQW_AUTOP  CHAR(1),
   AVAILABLE_BASKET     CHAR(1),
   AVAILABLE_TEMPLATES  CHAR(1),
   AVAILABLE_MB_TEMPLATES CHAR(1),
   SERVICE_ID           INTEGER              not null,
   SERVICE_NAME         VARCHAR2(128),
   SERVICE_IMAGE        VARCHAR2(128),
   SERVICE_GUID         VARCHAR2(50),
   SHOW_SERVICE         CHAR(1),
   GROUP_ID             INTEGER,
   GROUP_NAME           VARCHAR2(128),
   GROUP_IMAGE          VARCHAR2(128),
   GROUP_GUID           VARCHAR2(50),
   SHOW_GROUP           CHAR(1),
   CATEGORY_ID          INTEGER,
   CATEGORY_NAME        VARCHAR2(128),
   CATEGORY_IMAGE       VARCHAR2(128),
   CATEGORY_GUID        VARCHAR2(50),
   SHOW_CATEGORY        CHAR(1),
   P_KEY                CHAR(1)              not null,
   constraint SP_AGRR_PK primary key (P_KEY, REGION_ID, CHANEL, SERVICE_ID, ID)
)
organization index
 partition by list
 (P_KEY)
    (
        partition
             P_1
            values ('1'),
        partition
             P_2
            values ('2')
    )
go

-- Номер ревизии: 69417
-- Номер версии: 1.18
-- Комментарий: Секционированная таблица агрегации каталога услуг
INSERT INTO AGGREGATION_STATE values ('catalog.last.update.timestamp', NULL)
go
insert into PROPERTIES (ID, PROPERTY_VALUE, PROPERTY_KEY, CATEGORY) values (S_PROPERTIES.nextval, '1', 'com.rssl.iccs.phizic.catalog.aggreagation.current.partition', 'phiz')
go

-- Номер ревизии: 69477
-- Номер версии: 1.18
-- Комментарий: ENH079915: [Карты] В детальной информации по карте в поле "Дата формирования отчета" использовать NextReportDate
alter table STORED_CARD add NEXT_REPORT_DATE TIMESTAMP(6);

-- Номер ревизии: 69528
-- Номер версии: 1.18
-- Комментарий:  BUG078393: [ISUP] Оптимизировать запрос лимитов. 

alter table LIMITS add (END_DATE timestamp)
go

update LIMITS up_l 
set up_l.END_DATE = 
    case 
        when up_l.START_DATE =
        (
            SELECT MAX(l.START_DATE)
            FROM LIMITS l
            WHERE   up_l.TB = l.TB and
                l.STATE = 'CONFIRMED' and
                current_date >= l.START_DATE and
                up_l.LIMIT_TYPE = l.LIMIT_TYPE and
                (up_l.GROUP_RISK_ID = l.GROUP_RISK_ID OR up_l.GROUP_RISK_ID IS NULL AND l.GROUP_RISK_ID IS NULL) and
                up_l.CHANNEL_TYPE  = l.CHANNEL_TYPE and
                up_l.RESTRICTION_TYPE = l.RESTRICTION_TYPE and
                (up_l.SECURITY_TYPE = l.SECURITY_TYPE OR up_l.GROUP_RISK_ID IS NULL AND l.GROUP_RISK_ID IS NULL)
        )
        then
            (
                select entered_l.START_DATE 
                from LIMITS entered_l 
                where 
                    entered_l.ID != up_l.ID and
                    entered_l.TB = up_l.TB and
                    entered_l.STATE = 'CONFIRMED' and
                    entered_l.LIMIT_TYPE = up_l.LIMIT_TYPE and
                    (up_l.GROUP_RISK_ID = entered_l.GROUP_RISK_ID OR up_l.GROUP_RISK_ID IS NULL AND entered_l.GROUP_RISK_ID IS NULL) and
                    up_l.CHANNEL_TYPE  = entered_l.CHANNEL_TYPE and
                    up_l.RESTRICTION_TYPE = entered_l.RESTRICTION_TYPE and
                    (up_l.SECURITY_TYPE = entered_l.SECURITY_TYPE OR up_l.GROUP_RISK_ID IS NULL AND entered_l.GROUP_RISK_ID IS NULL) and
                    entered_l.START_DATE > current_date
            )
        else
            up_l.START_DATE
    end
where 
    up_l.STATE = 'CONFIRMED' and
    up_l.START_DATE <= current_date
go

-- Номер ревизии: 69597
-- Номер версии: 1.18
-- Комментарий:  BUG079463  [ЕРМБ смс-канал] ошибка при переводе на карту другого клиента , через смс канал (1. уникальность СМС)
UPDATE CONFIRM_BEANS
   SET SECONDARY_CONFIRM_CODE = PRIMARY_CONFIRM_CODE
 WHERE SECONDARY_CONFIRM_CODE IS NULL;

ALTER TABLE CONFIRM_BEANS MODIFY SECONDARY_CONFIRM_CODE NOT NULL;

-- Номер ревизии: 69662
-- Номер версии: 1.18
-- Комментарий: Секционированная таблица агругированных групп услуг(БД)
drop table PAYMENT_SERVICES_AGGR 
go
/*==============================================================*/
/* Table: PAYMENT_SERVICES_AGGR                                 */
/*==============================================================*/
create table PAYMENT_SERVICES_AGGR 
(
   SERVICE_ID           INTEGER              not null,
   PARENT_SERVICE_ID    INTEGER,
   SERVICE_NAME         VARCHAR2(128)        not null,
   IMAGE_ID             INTEGER,
   IMAGE_NAME           VARCHAR2(128)        not null,
   GUID                 VARCHAR2(50)         not null,
   CHANEL               VARCHAR2(10)         not null,
   REGION_ID            INTEGER              not null,
   PRIORITY             INTEGER,
   AVAILABLE            VARCHAR2(32)         not null,
   P_KEY                CHAR(1)              not null
)
partition by list
 (P_KEY)
    (
        partition
             P_1
            values ('1'),
        partition
             P_2
            values ('2')
    )
go

/*==============================================================*/
/* Index: IDX_PSA                                               */
/*==============================================================*/
create index IDX_PSA on PAYMENT_SERVICES_AGGR (
   REGION_ID ASC,
   CHANEL ASC,
   AVAILABLE ASC,
   PARENT_SERVICE_ID ASC
)
local
go

-- Номер ревизии: 69732
-- Номер версии: v.1.18
-- Комментарий: !!!Важно возможно на пром этот скрипт не нужен BUG077641: Не возможно сохранить поставщика Внешних услуг (модель БД)
alter table SERVICE_PROVIDERS modify PLANING_FOR_DEACTIVATE null 
go

-- Номер ревизии: 69790
-- Номер версии: 1.18
-- Комментарий: ПРофиль адресная книга

drop index IDX_PH_TO_AV_LID
go

create index IDX_PH_TO_AV_LID on PHONES_TO_AVATAR (LOGIN_ID asc)
go


create table IDENT_TYPE_FOR_DEPS (
   ID integer not null,
   SYSTEM_ID varchar2(20) not null,
   NAME varchar2(100) not null,
   UUID varchar2(100),

   constraint PK_IDENT_TYPE_FOR_DEPS primary key (ID)
)
go

create sequence S_IDENT_TYPE_FOR_DEPS 
go

create table ATTRIBUTES_FOR_IDENT_TYPES (
   ID integer not null,
   IDENT_ID integer not null,
   SYSTEM_ID varchar2(20) not null,
   NAME varchar2(100) not null,
   DATA_TYPE varchar2(20) not null,
   REG_EXP varchar2(100),
   MANDATORY char(1) not null,
   UUID varchar2(100),

   constraint PK_ATTRIBUTE_FOR_IDENT_TYPE primary key (ID),
   constraint FK_ATTRIBUTE_TO_IDENT foreign key (IDENT_ID) references IDENT_TYPE_FOR_DEPS (ID)
)
go

create sequence S_ATTRIBUTES_FOR_IDENT_TYPES  
go


insert into IDENT_TYPE_FOR_DEPS (ID, SYSTEM_ID, NAME, UUID)
values (S_IDENT_TYPE_FOR_DEPS.nextval, 'INN', 'ИНН', '00000000000000000000000000000001')
go

insert into IDENT_TYPE_FOR_DEPS (ID, SYSTEM_ID, NAME, UUID)
values (S_IDENT_TYPE_FOR_DEPS.nextval, 'DL', 'Водительское удостоверение', '00000000000000000000000000000002')
go

insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
 select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval, 
 ID, 'NUMBER', 'ИНН', 'TEXT', '\d{12}', '0', '00000000000000000000000000000003' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'INN'
go

 insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval,
 ID, 'NUMBER', 'Номер', 'TEXT', '\d{6}', '0', '00000000000000000000000000000004' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' 
go  

 insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval,
 ID, 'SERIES', 'Серия', 'TEXT', '\d{2}[0-9A-ZА-Я]{2}', '0', '00000000000000000000000000000005' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL'
go  

 insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval,
  ID, 'ISSUE_DATE', 'Дата выдачи', 'DATE', '\d{2}.\d{2}.\d{4}', '0', '00000000000000000000000000000005' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 

'DL' 
go  

 insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval,
 ID, 'ISSUE_BY', 'Кем выдано', 'TEXT', '.{150}', '0', '00000000000000000000000000000006' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' 
go  

 insert into ATTRIBUTES_FOR_IDENT_TYPES  (ID, IDENT_ID, SYSTEM_ID, NAME, DATA_TYPE, REG_EXP, MANDATORY, UUID)
select S_ATTRIBUTES_FOR_IDENT_TYPES.nextval,
  ID, 'EXPIRE_DATE', 'Действует до', 'DATE', '\d{2}.\d{2}.\d{4}', '0', '00000000000000000000000000000007' from IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' 
go  

-- Номер ревизии: 69811
-- Номер версии: 1.18
-- Комментарий: BUG077513  Настройка услуги "Мобильный банк". Grace-период обновляется при каждой смене тарифа.

ALTER TABLE ERMB_PROFILE ADD GRACE_PERIOD_DATE TIMESTAMP;

UPDATE ERMB_PROFILE
   SET GRACE_PERIOD_DATE =
          ADD_MONTHS (CONNECTION_DATE,
                      (SELECT GRACE_PERIOD
                         FROM ERMB_TARIF
                        WHERE ID = ERMB_TARIF_ID));

ALTER TABLE ERMB_PROFILE MODIFY GRACE_PERIOD_DATE NOT NULL;


-- Номер ревизии: 69906
-- Номер версии: 1.18
-- Комментарий: BUG078978: Добавить группу настроек по новому каналу в блок "Настройка видимости в каналах" 

alter table PAYMENT_SERVICES add SHOW_IN_SOCIAL CHAR(1);

update PAYMENT_SERVICES set SHOW_IN_SOCIAL = '1';

alter table PAYMENT_SERVICES modify SHOW_IN_SOCIAL default '1' not null ;


-- Номер ревизии: 69974
-- Номер версии: 1.18
-- Комментарий: ENH078242: [ISUP] Полный просмотр таблицы CardOperationClaim

create index I_CARD_OPERATION_CLAIMS_STATUS on CARD_OPERATION_CLAIMS
(
    decode(STATUS, 'TIMEOUT', 1, 'PROCESSING', 2, 'WAITING', 3, 'AUTO_WAITING', 4)
)
go


-- Номер ревизии: 69983
-- Номер версии: 1.18
-- Комментарий: BUG080865: Ошибка при миграции клиентов из резервного блока

drop function "SyncNode"
go

-- далее следует заглушечный вариант скрипта
-- информация по реальному скрипту в файле "http://svn.softlab.ru/svn/PhizIC/support/Внедрение/Скрипты (по задачам релизов)/Переливка профилей клиентов между блоками/STAND-IN/info.txt"
create or replace package STAND_IN_PKG as
   function SyncNode (gStreamId in number,gNodeId number,gPackSize in number default 0,gCSAProfileId in number default null) return NUMBER;
end STAND_IN_PKG;
go

create or replace package body STAND_IN_PKG as
   function SyncNode (gStreamId in number,gNodeId number,gPackSize in number default 0,gCSAProfileId in number default null) return NUMBER as
   begin
       return 1;
   end;
end STAND_IN_PKG;
go

-- Номер ревизии: 70165
-- Номер версии: 1.18
-- Комментарий: BUG077756: Ошибка при поиске расширенной заявки на кредит 

update BUSINESS_DOCUMENTS
set STATE_CODE = 'EXECUTED'
where STATE_CODE = 'ADOPTED'
and KIND = 'EL'
go

-- Номер ревизии: 69995
-- Номер версии: 1.18
-- Комментарий:  BUG081456: [Лимиты] Некорректно работают лимиты. 

-- под администратором БД (подставить свои значения CSA_ADMIN_NAME, CSA_ADMIN_PASSWORD, HOST, NAME):
CREATE PUBLIC DATABASE LINK CsaAdminLimitsLink
CONNECT TO %CSA_ADMIN_NAME% IDENTIFIED BY %CSA_ADMIN_PASSWORD%
USING '(DESCRIPTION =
(ADDRESS = (PROTOCOL = TCP)(HOST = %HOST%)(PORT = 1521))
(CONNECT_DATA =
  (SERVER = DEDICATED)
  (SERVICE_NAME = %NAME%)
)
)'
go

-- для экземпляра PhizIC:
update LIMITS limits set limits.END_DATE = (
    select csaLimits.END_DATE from LIMITS@CsaAdminLimitsLink csaLimits where csaLimits.UUID = limits.UUID
)
go 

-- Номер ревизии: 70274
-- Номер версии: 1.18
-- Комментарий: BUG081648: Исправить отображение предложений по кредиту 

drop index DXFK_LOAN_OFFER
go

create index DXFK_LOAN_OFFER on LOAN_OFFER 
(
   upper(replace(SUR_NAME, ' ', '')||replace(FIRST_NAME, ' ', '')||replace(PATR_NAME, ' ', '')) asc,
   BIRTHDAY ASC,
   upper(replace(PASPORT_NUMBER, ' ', '')||replace(PASPORT_SERIES, ' ', '')) asc,
   TB ASC
)
go

alter table LOAN_OFFER drop column LOGIN_ID  
go
alter table LOAN_OFFER drop column DEPARTMENT_ID
go


-- Номер ревизии: 66619
-- Номер версии: v1.18
-- Комментарий: Справочник тарифных планов
update RATE set TARIF_PLAN_CODE = '1' where TARIF_PLAN_CODE = 'PREMIER';
update RATE set TARIF_PLAN_CODE = '2' where TARIF_PLAN_CODE = 'GOLD';
update RATE set TARIF_PLAN_CODE = '3' where TARIF_PLAN_CODE = 'FIRST';
update RATE set TARIF_PLAN_CODE = null where TARIF_PLAN_CODE = 'UNKNOWN';

-- Номер ревизии: 70425
-- Номер версии: v1.18
-- Комментарий: BUG080983: [Корзина] Отображать напоминание в корзине после удаления напоминания у шаблона.

alter table REMINDER_LINKS add RESIDUAL_DATE TIMESTAMP
go

-- Номер ревизии: 70427
-- Номер версии: v1.18
-- Комментарий: BUG081606: [БКИ] Ошибка при сохранении изменения в настройках времени отправки запроса
delete from PROPERTIES
where PROPERTY_KEY='com.rssl.iccs.loanreport.bureau.job.lastTry.period.to'
   or PROPERTY_KEY='com.rssl.iccs.loanreport.bureau.job.lastTry.period.from'
   or PROPERTY_KEY='com.rssl.iccs.loanreport.bureau.job.start.time'
;


-- Номер ревизии: 70471
-- Номер версии: 1.18
-- Комментарий:  CHG081684: [Корзина] корректировка механизма работы с приостановлением\возобновлением\удалением автопоиска 

delete from INVOICES where INVOICE_SUBSCRIPTION_ID in (select subscription.ID from INVOICE_SUBSCRIPTIONS subscription where subscription.STATE like 'WAIT%')
go
delete from INVOICE_SUBSCRIPTIONS where STATE like 'WAIT%'
go

-- Номер ревизии: 70601
-- Номер версии: 1.18
-- Комментарий:  BUG080648: Убрать логирование части сообщений mAPI
insert into properties (id, property_key, property_value, category) values  
(s_properties.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*) from PROPERTIES WHERE PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), 
    '/mobile5*', 'phiz')
go
insert into properties (id, property_key, property_value, category) values  
(s_properties.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*) from PROPERTIES WHERE PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), 
    '/mobile6*', 'phiz')
go
insert into properties (id, property_key, property_value, category) values  
(s_properties.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*) from PROPERTIES WHERE PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), 
    '/mobile7/checkPassword.do', 'phiz')
go
insert into properties (id, property_key, property_value, category) values  
(s_properties.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*) from PROPERTIES WHERE PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), 
    ' /mobile7/private/graphics/finance.do', 'phiz')
go
insert into properties (id, property_key, property_value, category) values  
(s_properties.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*) from PROPERTIES WHERE PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), 
    '/mobile7/private/permissions.do', 'phiz')
go
insert into properties (id, property_key, property_value, category) values  
(s_properties.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*) from PROPERTIES WHERE PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), 
    '/mobile7/private/rates/list.do', 'phiz')
go
insert into properties (id, property_key, property_value, category) values  
(s_properties.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*) from PROPERTIES WHERE PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), 
    '/mobile7/private/profile/loyaltyURL.do', 'phiz')
go
insert into properties (id, property_key, property_value, category) values  
(s_properties.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*) from PROPERTIES WHERE PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), 
    '/mobile7/version/control.do', 'phiz')
go


-- Номер ревизии: 70763
-- Номер версии: 1.18
-- Комментарий:  ENH081992: [БКИ] Доработать запрос взаимодействия с ОКБ

-- Если сиквенс уже есть, пропускаем
CREATE SEQUENCE S_BKI_PRIMARY_ID_TYPE;

ALTER TABLE BKI_PRIMARY_ID_TYPE
DROP CONSTRAINT PK_BKI_BKI_PRIMARY_ID_TYPE;

ALTER TABLE BKI_PRIMARY_ID_TYPE
ADD (ID INTEGER);

UPDATE BKI_PRIMARY_ID_TYPE
SET ID = S_BKI_PRIMARY_ID_TYPE.nextval;

ALTER TABLE BKI_PRIMARY_ID_TYPE
MODIFY (ID INTEGER NOT NULL);

ALTER TABLE BKI_PRIMARY_ID_TYPE
ADD CONSTRAINT PK_BKI_BKI_PRIMARY_ID_TYPE PRIMARY KEY (ID);

INSERT INTO BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT)
VALUES (S_BKI_PRIMARY_ID_TYPE.nextval, '01', 'Паспорт гражданина РФ', 'PASSPORT_WAY', '0'); -- Для БКИ паспорт WAY = паспорт РФ


-- Номер ревизии: 70790
-- Номер версии: 1.18
-- Комментарий: CHG081985: [УАК] доработать скрипт переноса доверенных получателей в адресную книгу

create or replace and compile
java source named "RecentlyFields"
as
public class RecentlyFields
{
	private static final java.lang.String NOT_PHONE = "noPhone";
	private static final java.lang.String YANDEX_CONTACT_NAME = "Яндекс-контакт ";
	private static final java.lang.String letters[] = {"Альфа", "Бета", "Гамма", "Дельта", "Эпсилон", "Дзета", "Эта", "Тета", "Йота", "Каппа"};

	public static String getPhoneForAdding(java.lang.String phone)
	{
		if (phone == null || phone.equals(""))
			return NOT_PHONE;

		if (phone.startsWith("41001"))
			return NOT_PHONE;

		if (phone.matches("\\d{10}"))
			return "7" + phone;

		if (phone.matches("7\\d{10}"))
			return phone;

		if (phone.matches("8\\d{10}"))
			return "7" + phone.substring(1);

		if (phone.matches("\\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}"))
			return "7" + phone.substring(1, 4) + phone.substring(6, 9) + phone.substring(10, 12) + phone.substring(13, 15);

		return NOT_PHONE;
	}

	public static java.lang.String getContactName(int currentYaCount)
	{
		int n = currentYaCount / 10;
	    currentYaCount = currentYaCount%10;
		java.lang.String name = YANDEX_CONTACT_NAME;

		name += letters[currentYaCount];


		if (n > 1)
            name = name + " " + n;

		return name;
	}
}
go

create or replace
function getPhoneForAdding(phone in varchar2) return varchar2
as
language java
name 'RecentlyFields.getPhoneForAdding(java.lang.String) return java.lang.String';
go

create or replace
function getYandexName(yacount in number) return varchar2
as
language java
name 'RecentlyFields.getContactName(int) return java.lang.String';
go

--p2p и mobile
insert into ADDRESS_BOOKS (
    ID,
    LOGIN_ID, 
    PHONE, 
    FULL_NAME, 
    SBERBANK_CLIENT, 
    INCOGNITO, 
    FIO,
    TRUSTED, 
    FREQUENCY_P2P,
    FREQUENCY_PAY, 
    STATUS,
    ADDED_BY,
    CATEGORY,
    CARD_NUMBER)
select 
    S_ADDRESS_BOOK.nextval,
    rffd.LOGIN_ID,
    getPhoneForAdding(rffd.PHONE_NUMBER), 
    case when rffd.OUR_CARD_NUMBER is not null then rffd.RECEIVER_FIRST_NAME || ' ' || rffd.RECEIVER_PATR_NAME || ' ' || SUBSTR

(rffd.RECEIVER_SUR_NAME, 1, 1) || '.'
        else 'Оплата мобильного телефона' end,
    0,
    0,
    rffd.RECEIVER_FIRST_NAME || ' ' || rffd.RECEIVER_PATR_NAME || ' ' || rffd.RECEIVER_SUR_NAME,
    1,
    case when rffd.OUR_CARD_NUMBER is not null then 1 else 0 end,
    case when rffd.OUR_CARD_NUMBER is not null then 0 else 1 end,
    'ACTIVE',
    'ERIB',
    'NONE',
    rffd.OUR_CARD_NUMBER
from 
    RECENTLY_FILLED_FIELD_DATA rffd
where 
    rffd.PHONE_NUMBER is not null 
    and getPhoneForAdding(rffd.PHONE_NUMBER) != 'noPhone'
    and rffd.IS_SELF_PHONE_NUMBER = '0' 
    and rffd.PROVIDER_GUID is null
    and not exists (select ID from ADDRESS_BOOKS where LOGIN_ID = rffd.LOGIN_ID and '7' || getPhoneForAdding(rffd.PHONE_NUMBER) = PHONE)
go

CREATE OR REPLACE PROCEDURE FILL_ADDRESS_BOOK_YANDEX
as
  cursor rffds is 
    select 
        rffd.*
    from 
        RECENTLY_FILLED_FIELD_DATA rffd
    where 
        rffd.IWALLET_NUMBER is not null 
        and rffd.IS_SELF_PHONE_NUMBER = '0' 
        and rffd.PROVIDER_GUID in (select PROPERTY_VALUE from PROPERTIES where PROPERTY_KEY = 'com.rssl.business.simple.yandexPaymentId')
        and not exists (select ID from ADDRESS_BOOKS where LOGIN_ID = rffd.LOGIN_ID and getPhoneForAdding(rffd.IWALLET_NUMBER) = PHONE);
  currentYaCount integer;
  currentYaName VARCHAR2(100);

begin

    for rffd in rffds loop
        if (getPhoneForAdding(rffd.IWALLET_NUMBER) != 'noPhone') then
            currentYaCount := 10;
            select TO_NUMBER(PROPERTY_VALUE) into currentYaCount 
            from USER_PROPERTIES where PROPERTY_KEY = 'com.rssl.phizic.userSettings.addressbook.nomOfYandexContacts'
            and LOGIN_ID = rffd.LOGIN_ID;

            if (currentYaCount = 10) then
                insert into USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE)
                values (S_USER_PROPERTIES.nextval, rffd.LOGIN_ID, 'com.rssl.phizic.userSettings.addressbook.nomOfYandexContacts', '11');
            else
                update USER_PROPERTIES set PROPERTY_VALUE = TO_CHAR(currentYaCount + 1)
                where LOGIN_ID = rffd.LOGIN_ID and PROPERTY_KEY = 'com.rssl.phizic.userSettings.addressbook.nomOfYandexContacts';
            end if;

            currentYaName := getYandexName(currentYaCount);

            insert into ADDRESS_BOOKS (
                ID,
                LOGIN_ID, 
                PHONE, 
                FULL_NAME, 
                SBERBANK_CLIENT, 
                INCOGNITO, 
                FIO,
                TRUSTED, 
                FREQUENCY_P2P,
                FREQUENCY_PAY, 
                STATUS,
                ADDED_BY,
                CATEGORY)
            values
                (
                    S_ADDRESS_BOOK.nextval,
                    rffd.LOGIN_ID,
                    getPhoneForAdding(rffd.IWALLET_NUMBER), 
                    currentYaName,
                    0,
                    0,
                    rffd.RECEIVER_FIRST_NAME || ' ' || rffd.RECEIVER_PATR_NAME || ' ' || rffd.RECEIVER_SUR_NAME,
                    1,
                    1,
                    0,
                    'ACTIVE',
                    'ERIB',
                    'NONE'
                );
         end if;
    end loop;

end FILL_ADDRESS_BOOK_YANDEX;
go

call FILL_ADDRESS_BOOK_YANDEX
go

--p2p
update ADDRESS_BOOKS ab set (FULL_NAME,FREQUENCY_P2P) =
(select 
    case when rffd.OUR_CARD_NUMBER is not null then rffd.RECEIVER_FIRST_NAME || ' ' || rffd.RECEIVER_PATR_NAME || ' ' || SUBSTR

(rffd.RECEIVER_SUR_NAME, 1, 1) || '.'
        else ab.FULL_NAME end as FULL_NAME,
    ab.FREQUENCY_P2P+1 as FREQUENCY_P2P
from 
    RECENTLY_FILLED_FIELD_DATA rffd
where 
    rffd.LOGIN_ID = ab.LOGIN_ID 
    and getPhoneForAdding(rffd.PHONE_NUMBER) = ab.PHONE
    and rffd.PHONE_NUMBER is not null
    and (rffd.OUR_CARD_NUMBER is not null and rffd.PROVIDER_GUID is null)) 
where exists (select 
    case when rffd.OUR_CARD_NUMBER is not null then rffd.RECEIVER_FIRST_NAME || ' ' || rffd.RECEIVER_PATR_NAME || ' ' || SUBSTR

(rffd.RECEIVER_SUR_NAME, 1, 1) || '.'
        else ab.FULL_NAME end as FULL_NAME,
    ab.FREQUENCY_P2P+1 as FREQUENCY_P2P
from 
    RECENTLY_FILLED_FIELD_DATA rffd
where 
    rffd.LOGIN_ID = ab.LOGIN_ID 
    and getPhoneForAdding(rffd.PHONE_NUMBER) = ab.PHONE
    and rffd.PHONE_NUMBER is not null
    and (rffd.OUR_CARD_NUMBER is not null and rffd.PROVIDER_GUID is null))
go

--yandex
update ADDRESS_BOOKS ab set (FULL_NAME,FREQUENCY_P2P) =
(select 
    case when rffd.OUR_CARD_NUMBER is not null then rffd.RECEIVER_FIRST_NAME || ' ' || rffd.RECEIVER_PATR_NAME || ' ' || SUBSTR

(rffd.RECEIVER_SUR_NAME, 1, 1) || '.'
        else ab.FULL_NAME end as FULL_NAME,
    ab.FREQUENCY_P2P+1 as FREQUENCY_P2P
from 
    RECENTLY_FILLED_FIELD_DATA rffd
where 
    rffd.LOGIN_ID = ab.LOGIN_ID 
    and getPhoneForAdding(rffd.IWALLET_NUMBER) = ab.PHONE
    and rffd.PHONE_NUMBER is not null
    and (rffd.PROVIDER_GUID in (select PROPERTY_VALUE from PROPERTIES where PROPERTY_KEY = 'com.rssl.business.simple.yandexPaymentId'))) 
where exists (select 
    case when rffd.OUR_CARD_NUMBER is not null then rffd.RECEIVER_FIRST_NAME || ' ' || rffd.RECEIVER_PATR_NAME || ' ' || SUBSTR

(rffd.RECEIVER_SUR_NAME, 1, 1) || '.'
        else ab.FULL_NAME end as FULL_NAME,
    ab.FREQUENCY_P2P+1 as FREQUENCY_P2P
from 
    RECENTLY_FILLED_FIELD_DATA rffd
where 
    rffd.LOGIN_ID = ab.LOGIN_ID 
    and getPhoneForAdding(rffd.IWALLET_NUMBER) = ab.PHONE
    and rffd.PHONE_NUMBER is not null
    and (rffd.PROVIDER_GUID in (select PROPERTY_VALUE from PROPERTIES where PROPERTY_KEY = 'com.rssl.business.simple.yandexPaymentId')))
go

--mobile
update ADDRESS_BOOKS ab set (FREQUENCY_PAY) =
(select 
    ab.FREQUENCY_PAY+1 as FREQUENCY_PAY
from 
    RECENTLY_FILLED_FIELD_DATA rffd
where
    rffd.LOGIN_ID = ab.LOGIN_ID and '7' || rffd.PHONE_NUMBER = ab.PHONE
    and rffd.PHONE_NUMBER is not null
    and (rffd.OUR_CARD_NUMBER is null and rffd.PROVIDER_GUID is null)
    )
where exists (select 
    ab.ID
from 
    RECENTLY_FILLED_FIELD_DATA rffd
where
    rffd.LOGIN_ID = ab.LOGIN_ID and getPhoneForAdding(rffd.PHONE_NUMBER) = ab.PHONE
    and rffd.PHONE_NUMBER is not null
    and (rffd.OUR_CARD_NUMBER is null and rffd.PROVIDER_GUID is null)
    )
go

drop procedure FILL_ADDRESS_BOOK_YANDEX
go

drop function getPhoneForAdding
go

drop function getYandexName
go

drop java source "RecentlyFields"
go


-- Номер ревизии: 70807
-- Номер версии: 1.18
-- Комментарий: Бизнес-отчеты ЕРМБ. Индексы.

create index SERV_PAY_STATISTICS_DATE_IDX on SMS_SERVICE_PAYMENT_STATISTICS (FINAL_STATUS_DATE) local
;
create index ERMB_TARIFF_CHANGE_DATE_IDX on ERMB_CLIENT_TARIFF_HISTORY(CHANGE_DATE) local
;

-- Номер ревизии: 70884
-- Номер версии: 1.18
-- Комментарий: BUG082157: [Профиль] не добавляется водительское удостоверение
update ATTRIBUTES_FOR_IDENT_TYPES set REG_EXP = '.{0,150}' 
 WHERE IDENT_ID = (SELECT id FROM IDENT_TYPE_FOR_DEPS where SYSTEM_ID = 'DL' ) and SYSTEM_ID = 'ISSUE_BY'
 

-- Номер ревизии: 70905
-- Номер версии: 1.18
-- Комментарий: Пакет агрегации каталога услуг добавлен в ПД
insert into AGGREGATION_STATE(KEY, TIME) values ('aggregation.last.update.timestamp', null)
go

create or replace package SERVICES_AGGREGATION as
   IQWAVE_CODE constant VARCHAR2(16) :='%iqwave%';
   procedure REFRESH;
end SERVICES_AGGREGATION;
go

create or replace package body SERVICES_AGGREGATION as
   procedure REFRESH as
       PKEY_CODE       number;
       aggregationTime AGGREGATION_STATE.TIME%TYPE;
       catalogTime     AGGREGATION_STATE.TIME%TYPE;
     begin
   
     
       select 
         time, ( select time from AGGREGATION_STATE where key='catalog.last.update.timestamp' ) ctime into aggregationTime, catalogTime
       from AGGREGATION_STATE 
       where 
         key='aggregation.last.update.timestamp' for update nowait;
   
       if ( catalogTime >  aggregationTime or aggregationTime is null) then
   
         update AGGREGATION_STATE set time = systimestamp where key='aggregation.last.update.timestamp';
   	  
         update PROPERTIES set PROPERTY_VALUE = 3-to_number(PROPERTY_VALUE) 
           where PROPERTY_KEY='com.rssl.iccs.phizic.catalog.aggreagation.current.partition' and CATEGORY='phiz' 
             return PROPERTY_VALUE into PKEY_CODE;
     
         delete from PAYMENT_SERVICES_AGGR where P_KEY = PKEY_CODE;
     
         insert into PAYMENT_SERVICES_AGGR( SERVICE_ID, PARENT_SERVICE_ID, SERVICE_NAME, IMAGE_ID, IMAGE_NAME, GUID, CHANEL, REGION_ID, PRIORITY, AVAILABLE, P_KEY )
         with
         AVAILABLE_MAP as (
           select 'PAYMENTS' AVAILABLE from dual union all 
           select 'ESB_AUTOPAYMENTS' AVAILABLE from dual union all
           select 'IQW_AUTOPAYMENTS' AVAILABLE from dual union all
           select 'TEMPLATES' AVAILABLE from dual union all
           select 'MB_TEMPLATES' AVAILABLE from dual union all
           select 'BASKET' AVAILABLE from dual
         ),
         CHANELS as (
           select 'MAPI' CHANEL from dual union all select 'WEB' CHANEL from dual union all select 'ATMAPI' CHANEL from dual
         ),
         ALL_REGIONS as (
           select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by r.PARENT_ID = prior r.ID
           union
           select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by prior r.PARENT_ID = r.ID
           union
           select 0, ID REL_REGION_ID from REGIONS r --федеральность
           union
         select 0, -1 REL_REGION_ID from DUAL r -- -1 для федеральных	  
         ),
         ALL_SERVICE_PROVIDERS as (
           ----регион обслуживания с учетом федеральности и группа "без учета региона" REGION_ID -1
           select sp.*, decode(sp.IS_FEDERAL, '1', 0, nvl(spr.REGION_ID, 0)) PROVIDER_REGION_ID
           from SERVICE_PROVIDERS sp 
           left join SERVICE_PROVIDER_REGIONS spr on spr.SERVICE_PROVIDER_ID=decode(sp.IS_FEDERAL, '1', 0, sp.ID)
           where sp.STATE='ACTIVE'
           union all
           select sp.*, -1 PROVIDER_REGION_ID from SERVICE_PROVIDERS sp where sp.STATE='ACTIVE' and sp.IS_FEDERAL!='1'
         ),
         AVAILABLE_PROVIDERS as (
           select * from (
             select
               CHANEL,
               spps.PAYMENT_SERVICE_ID SERVICE_ID,
               PROVIDER_REGION_ID,
               sp.ID   PROVIDER_ID,
               ----ФУНКИОНАЛЬНОСТЬ В КАНАЛАХ---------------------------------------------------
               case  
                 when CHANEL='WEB'    and AVAILABLE_PAYMENTS_FOR_IB = '1'      and VISIBLE_PAYMENTS_FOR_IB = '1'       then 1
                 when CHANEL='MAPI'   and AVAILABLE_PAYMENTS_FOR_M_API = '1'   and VISIBLE_PAYMENTS_FOR_M_API = '1'    then 1
                 when CHANEL='ATMAPI' and AVAILABLE_PAYMENTS_FOR_ATM_API = '1' and VISIBLE_PAYMENTS_FOR_ATM_API = '1'  then 1
                 else 0 
               end as AVAILABLE_PAYMENTS,
               case  
                 when CHANEL='WEB'    and IS_AUTOPAYMENT_SUPPORTED='1'     and VISIBLE_AUTOPAYMENTS_FOR_IB='1'  and EXTERNAL_ID not like IQWAVE_CODE then 1
                 when CHANEL='MAPI'   and IS_AUTOPAYMENT_SUPPORTED_API='1' and VISIBLE_AUTOPAYMENTS_FOR_API='1' and EXTERNAL_ID not like IQWAVE_CODE then 1
                 when CHANEL='ATMAPI' and IS_AUTOPAYMENT_SUPPORTED_ATM='1' and VISIBLE_AUTOPAYMENTS_FOR_ATM='1' and EXTERNAL_ID not like IQWAVE_CODE then 1
                 else 0 
               end as AVAILABLE_ESB_AUTOP,
               case 
                 when CHANEL='WEB'    and IS_AUTOPAYMENT_SUPPORTED='1'     and VISIBLE_AUTOPAYMENTS_FOR_IB='1'  and EXTERNAL_ID like IQWAVE_CODE then 1
                 when CHANEL='MAPI'   and IS_AUTOPAYMENT_SUPPORTED_API='1' and VISIBLE_AUTOPAYMENTS_FOR_API='1' and EXTERNAL_ID like IQWAVE_CODE then 1
                 when CHANEL='ATMAPI' and IS_AUTOPAYMENT_SUPPORTED_ATM='1' and VISIBLE_AUTOPAYMENTS_FOR_ATM='1' and EXTERNAL_ID like IQWAVE_CODE then 1
                 else 0 
               end as AVAILABLE_IQW_AUTOP,
               case     
                 when CHANEL='WEB' and AVAILABLE_PAYMENTS_FOR_IB='1' and VISIBLE_PAYMENTS_FOR_IB='1' and IS_TEMPLATE_SUPPORTED='1' then 1
                 when CHANEL='MAPI'   then 0
                 when CHANEL='ATMAPI' then 0
                 else 0 
               end as AVAILABLE_TEMPLATES,
               case     
                 when CHANEL='WEB' and IS_MOBILEBANK='1' and ( select count(1) from FIELD_DESCRIPTIONS where RECIPIENT_ID=sp.ID and IS_KEY=1 )=1 then 1
                 when CHANEL='MAPI'   then 0
                 when CHANEL='ATMAPI' then 0
                 else 0 
               end as AVAILABLE_MB_TEMPLATES,
               case     
                 when CHANEL='WEB' and IS_AUTOPAYMENT_SUPPORTED='1'     and VISIBLE_AUTOPAYMENTS_FOR_IB='1'  and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then 1
                 when CHANEL='MAPI'and IS_AUTOPAYMENT_SUPPORTED_API='1' and VISIBLE_AUTOPAYMENTS_FOR_API='1' and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then 1
                 when CHANEL='ATMAPI' then 0
                 else 0
               end as AVAILABLE_BASKET
             from ALL_SERVICE_PROVIDERS sp
             join SERV_PROVIDER_PAYMENT_SERV spps on spps.SERVICE_PROVIDER_ID = sp.ID     
             ----КАНАЛЫ ОБСЛУЖИВАНИЯ---------------------------------------------------------
             inner join CHANELS on 1=1
           )
           where 
             AVAILABLE_PAYMENTS     = 1 or 
             AVAILABLE_ESB_AUTOP    = 1 or 
             AVAILABLE_IQW_AUTOP    = 1 or 
             AVAILABLE_TEMPLATES    = 1 or 
             AVAILABLE_MB_TEMPLATES = 1 or 
             AVAILABLE_BASKET       = 1
         ),
         CHANEL_SERVICES as (
           select 
             CHANEL,
             ID SERVICE_ID, 
             case 
               when CHANEL='WEB' and SHOW_IN_SYSTEM='1'  then 1
               when CHANEL='MAPI' and SHOW_IN_API='1'    then 1
               when CHANEL='ATMAPI' and SHOW_IN_ATM='1'  then 1
               else 0
             end as SHOW,
             CODE GUID, 
             NAME SERVICE_NAME, 
             IMAGE_ID, 
             IMAGE_NAME,
             PRIORITY
           from PAYMENT_SERVICES s
           inner join CHANELS on 1=1
         ),
         AVAILABLE_SERVICES_TREE as (
           select * from (
             select
               s1.CHANEL CHANEL_L1, s1.SERVICE_ID SERVICE_L1, nvl(s2.SERVICE_ID, -1) PARENT_L1, s1.SHOW SHOW_L1, s1.GUID GUID_L1, s1.SERVICE_NAME SERVICE_NAME_L1, s1.IMAGE_ID IMAGE_ID_L1, s1.IMAGE_NAME IMAGE_NAME_L1, s1.PRIORITY PRIORITY_L1,
               s2.CHANEL CHANEL_L2, s2.SERVICE_ID SERVICE_L2, nvl(s3.SERVICE_ID, -1) PARENT_L2, s2.SHOW SHOW_L2, s2.GUID GUID_L2, s2.SERVICE_NAME SERVICE_NAME_L2, s2.IMAGE_ID IMAGE_ID_L2, s2.IMAGE_NAME IMAGE_NAME_L2, s2.PRIORITY PRIORITY_L2,
               s3.CHANEL CHANEL_L3, s3.SERVICE_ID SERVICE_L3, -1                     PARENT_L3, s3.SHOW SHOW_L3, s3.GUID GUID_L3, s3.SERVICE_NAME SERVICE_NAME_L3, s3.IMAGE_ID IMAGE_ID_L3, s3.IMAGE_NAME IMAGE_NAME_L3, s3.PRIORITY PRIORITY_L3
             from CHANEL_SERVICES s1
             left join PAYMENT_SERV_PARENTS p1 on p1.SERVICE_ID = s1.SERVICE_ID 
             left join CHANEL_SERVICES s2 on s2.SERVICE_ID=p1.PARENT_ID and s1.CHANEL=s2.CHANEL
             left join PAYMENT_SERV_PARENTS p2 on s2.SERVICE_ID = p2.SERVICE_ID
             left join CHANEL_SERVICES s3 on s3.SERVICE_ID=p2.PARENT_ID and s2.CHANEL=s3.CHANEL
           )
           where 
              (SHOW_L1=1 and SHOW_L2 is null and SHOW_L3 is null) 
           or (SHOW_L1=1 and SHOW_L2=1 and SHOW_L3 is null) 
           or (SHOW_L1=1 and SHOW_L2=1 and SHOW_L3=1)
         ),
         AVAILABLE_SERVICES as (
           select SERVICE_L1 LEAF_SERVICE, CHANEL_L3 CHANEL, SERVICE_L3 SERVICE_ID, PARENT_L3 PARENT_ID, SHOW_L3 SHOW, GUID_L3 GUID, SERVICE_NAME_L3 SERVICE_NAME, IMAGE_ID_L3 IMAGE_ID, IMAGE_NAME_L3 IMAGE_NAME, PRIORITY_L3 PRIORITY
             from AVAILABLE_SERVICES_TREE where SERVICE_L1 is not null and SERVICE_L2 is not null and SERVICE_L3 is not null
           union
           select SERVICE_L1 LEAF_SERVICE, CHANEL_L2 CHANEL, SERVICE_L2 SERVICE_ID, PARENT_L2 PARENT_ID, SHOW_L2 SHOW, GUID_L2 GUID, SERVICE_NAME_L2 SERVICE_NAME, IMAGE_ID_L2 IMAGE_ID, IMAGE_NAME_L2 IMAGE_NAME, PRIORITY_L2 PRIORITY
             from AVAILABLE_SERVICES_TREE where SERVICE_L1 is not null and SERVICE_L2 is not null
           union
           select SERVICE_L1 LEAF_SERVICE, CHANEL_L1 CHANEL, SERVICE_L1 SERVICE_ID, PARENT_L1 PARENT_ID, SHOW_L1 SHOW, GUID_L1 GUID, SERVICE_NAME_L1 SERVICE_NAME, IMAGE_ID_L1 IMAGE_ID, IMAGE_NAME_L1 IMAGE_NAME, PRIORITY_L1 PRIORITY
             from AVAILABLE_SERVICES_TREE where SERVICE_L1 is not null
         )
         select 
           SERVICE_ID, PARENT_ID, SERVICE_NAME, IMAGE_ID, IMAGE_NAME, GUID, CHANEL, REGION_ID, PRIORITY, AVAILABLE, PKEY_CODE 
         from (
           select 
             SERVICE_ID, PARENT_ID, SERVICE_NAME, IMAGE_ID, IMAGE_NAME, GUID, CHANEL, REGION_ID, PRIORITY, AVAILABLE
           from (
             select 
               REGION_ID, CHANEL, SERVICE_ID, PARENT_ID, GUID, SERVICE_NAME, IMAGE_ID, IMAGE_NAME, PRIORITY,
               decode(max(AVAILABLE_PAYMENTS), 1, 1, null)     AVAILABLE_PAYMENTS, 
               decode(max(AVAILABLE_ESB_AUTOP), 1, 1, null)    AVAILABLE_ESB_AUTOP,
               decode(max(AVAILABLE_IQW_AUTOP), 1, 1, null)    AVAILABLE_IQW_AUTOP, 
               decode(max(AVAILABLE_TEMPLATES), 1, 1, null)    AVAILABLE_TEMPLATES, 
               decode(max(AVAILABLE_MB_TEMPLATES), 1, 1, null) AVAILABLE_MB_TEMPLATES, 
               decode(max(AVAILABLE_BASKET), 1, 1, null)       AVAILABLE_BASKET
             from (
               select 
                 decode(providers.PROVIDER_REGION_ID, -1, -1, regions.REL_REGION_ID) REGION_ID, services.CHANEL, services.SERVICE_ID, services.PARENT_ID, services.SHOW, services.GUID, services.SERVICE_NAME, services.IMAGE_ID, services.IMAGE_NAME, services.PRIORITY,
                 ----ДОСТУПНОСТЬ---------------------------------------------------------------
                 providers.AVAILABLE_PAYMENTS, providers.AVAILABLE_ESB_AUTOP, providers.AVAILABLE_IQW_AUTOP, providers.AVAILABLE_TEMPLATES, providers.AVAILABLE_MB_TEMPLATES, providers.AVAILABLE_BASKET
               from AVAILABLE_PROVIDERS providers
               inner join AVAILABLE_SERVICES services on services.LEAF_SERVICE=providers.SERVICE_ID and services.CHANEL=providers.CHANEL 
               left join ALL_REGIONS regions on regions.ID=providers.PROVIDER_REGION_ID
             )
             group by REGION_ID, CHANEL, SERVICE_ID, PARENT_ID, GUID, SERVICE_NAME, IMAGE_ID, IMAGE_NAME, PRIORITY
           )
           inner join AVAILABLE_MAP on 1=1
           where 
             ( AVAILABLE_PAYMENTS=1     and AVAILABLE='PAYMENTS')         or
             ( AVAILABLE_ESB_AUTOP=1    and AVAILABLE='ESB_AUTOPAYMENTS') or
             ( AVAILABLE_IQW_AUTOP=1    and AVAILABLE='IQW_AUTOPAYMENTS') or 
             ( AVAILABLE_TEMPLATES=1    and AVAILABLE='TEMPLATES')        or 
             ( AVAILABLE_MB_TEMPLATES=1 and AVAILABLE='MB_TEMPLATES')     or 
             ( AVAILABLE_BASKET=1       and AVAILABLE='BASKET')
         );
     
         delete from SERVICE_PROVIDERS_AGGR where P_KEY = PKEY_CODE;
     
         insert into SERVICE_PROVIDERS_AGGR ( 
           ----ПОСТАВЩИК-----------------------------------------------------------------
           ID, REGION_ID, CHANEL, PROVIDER_NAME,  ALIAS, LEGAL_NAME, CODE_RECIPIENT_SBOL, NAME_B_SERVICE, BILLING_ID, SORT_PRIORITY, UUID, INN, ACCOUNT, 
           IMAGE_ID, IMAGE_UPDATE_TIME, IMAGE_MD5, H_IMAGE_ID, H_IMAGE_MD5, H_IMAGE_UPDATE_TIME, IS_BAR_SUPPORTED, SUB_TYPE,
           ----ДОСТУПНОСТЬ---------------------------------------------------------------
           AVAILABLE_PAYMENTS, AVAILABLE_ESB_AUTOP, AVAILABLE_IQW_AUTOP, AVAILABLE_BASKET, AVAILABLE_TEMPLATES, AVAILABLE_MB_TEMPLATES, 
           ----ХЛЕБНЫЕ КРОШКИ------------------------------------------------------------
           SERVICE_ID, SERVICE_NAME, SERVICE_IMAGE, SERVICE_GUID, SHOW_SERVICE, GROUP_ID, GROUP_NAME, GROUP_IMAGE, GROUP_GUID, SHOW_GROUP, CATEGORY_ID, CATEGORY_NAME, CATEGORY_IMAGE, CATEGORY_GUID, SHOW_CATEGORY, P_KEY 
         )
         with 
         CHANELS as (
           select 'MAPI' CHANEL from dual union all select 'WEB' CHANEL from dual union all select 'ATMAPI' CHANEL from dual
         ),
         PROVIDERS_REGIONS as (
           select id SERVICE_PROVIDER_ID, -1 PROVIDER_REGION_ID from SERVICE_PROVIDERS --все регионы
           union 
           select 
             SP.ID SERVICE_PROVIDER_ID, decode(sp.IS_FEDERAL, '1', 0, nvl(spr.REGION_ID, 0)) PROVIDER_REGION_ID 
           from SERVICE_PROVIDERS sp
           left join SERVICE_PROVIDER_REGIONS spr on spr.SERVICE_PROVIDER_ID=decode(sp.IS_FEDERAL, '1', 0, sp.ID)
         ),
         ALL_REGIONS as (
           select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by r.PARENT_ID = prior r.ID
           union
           select ID, connect_by_root ID REL_REGION_ID from REGIONS r connect by prior r.PARENT_ID = r.ID
           union
           select 0, ID REL_REGION_ID from REGIONS r --федеральность
         ), 
         REL_REGIONS as (
           select SERVICE_PROVIDER_ID, PROVIDER_REGION_ID from (
             select 
               SERVICE_PROVIDER_ID, decode(providers.PROVIDER_REGION_ID, -1, -1, regions.REL_REGION_ID) PROVIDER_REGION_ID
             from PROVIDERS_REGIONS providers
             left join ALL_REGIONS regions on regions.ID=providers.PROVIDER_REGION_ID
           ) group by SERVICE_PROVIDER_ID, PROVIDER_REGION_ID
         ),
         AVAILABLE_PROVIDERS as (
           select * from (
             select
               CHANEL,
               sp.ID   PROVIDER_ID,
               sp.UUID PROVIDER_UUID,
               sp.NAME PROVIDER_NAME,
               sp.SORT_PRIORITY,
               sp.INN,
               sp.LEGAL_NAME,
               sp.ALIAS,
               sp.ACCOUNT,
               sp.NAME_SERVICE NAME_B_SERVICE, 
               sp.IMAGE_ID,
               i.MD5,
               i.UPDATE_TIME IMAGE_UPDATE_TIME,
               sp.IMAGE_HELP_ID H_IMAGE_ID,
               ih.MD5 H_IMAGE_MD5,
               ih.UPDATE_TIME H_IMAGE_UPDATE_TIME,
               sp.CODE_RECIPIENT_SBOL,
               sp.BILLING_ID,
               sp.EXTERNAL_ID,
               sp.IS_BAR_SUPPORTED,
               sp.SUB_TYPE,  
               ----ФУНКИОНАЛЬНОСТЬ В КАНАЛАХ---------------------------------------------------
               case  
                 when CHANEL='WEB'    and AVAILABLE_PAYMENTS_FOR_IB = '1'      then decode(VISIBLE_PAYMENTS_FOR_IB, '1', 'A', 'S')
                 when CHANEL='MAPI'   and AVAILABLE_PAYMENTS_FOR_M_API = '1'   then decode(VISIBLE_PAYMENTS_FOR_M_API, '1', 'A', 'S')
                 when CHANEL='ATMAPI' and AVAILABLE_PAYMENTS_FOR_ATM_API = '1' then decode(VISIBLE_PAYMENTS_FOR_ATM_API, '1', 'A', 'S')
                 else null 
               end as AVAILABLE_PAYMENTS,
               case  
                 when CHANEL='WEB'    and IS_AUTOPAYMENT_SUPPORTED='1'     and EXTERNAL_ID not like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_IB, '1', 'A', 'S')
                 when CHANEL='MAPI'   and IS_AUTOPAYMENT_SUPPORTED_API='1' and EXTERNAL_ID not like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_API, '1', 'A', 'S')
                 when CHANEL='ATMAPI' and IS_AUTOPAYMENT_SUPPORTED_ATM='1' and EXTERNAL_ID not like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_ATM, '1', 'A', 'S')
                 else null 
               end as AVAILABLE_ESB_AUTOP,
               case 
                 when CHANEL='WEB'    and IS_AUTOPAYMENT_SUPPORTED='1'     and EXTERNAL_ID like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_IB, '1', 'A', 'S')
                 when CHANEL='MAPI'   and IS_AUTOPAYMENT_SUPPORTED_API='1' and EXTERNAL_ID like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_API, '1', 'A', 'S')
                 when CHANEL='ATMAPI' and IS_AUTOPAYMENT_SUPPORTED_ATM='1' and EXTERNAL_ID like IQWAVE_CODE then decode(VISIBLE_AUTOPAYMENTS_FOR_ATM, '1', 'A', 'S')
                 else null 
               end as AVAILABLE_IQW_AUTOP,
               case     
                 when CHANEL='WEB' and AVAILABLE_PAYMENTS_FOR_IB='1' and IS_TEMPLATE_SUPPORTED='1' then decode(VISIBLE_PAYMENTS_FOR_IB, '1', 'A', 'S')
                 when CHANEL='MAPI'   then null
                 when CHANEL='ATMAPI' then null
                 else null 
               end as AVAILABLE_TEMPLATES,
               case     
                 when CHANEL='WEB' and IS_MOBILEBANK='1' and ( select count(1) from FIELD_DESCRIPTIONS where RECIPIENT_ID=sp.ID and IS_KEY='1' )=1 then 'A'
                 when CHANEL='MAPI'   then null
                 when CHANEL='ATMAPI' then null
                 else null 
               end as AVAILABLE_MB_TEMPLATES,
               case     
                 when CHANEL='WEB' and IS_AUTOPAYMENT_SUPPORTED='1'     and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then decode(VISIBLE_AUTOPAYMENTS_FOR_IB, '1', 'A', 'S')
                 when CHANEL='MAPI'and IS_AUTOPAYMENT_SUPPORTED_API='1' and EXTERNAL_ID not like IQWAVE_CODE and exists( select 1 from AUTOPAY_SETTINGS where RECIPIENT_ID=sp.ID and type='INVOICE' ) then decode(VISIBLE_AUTOPAYMENTS_FOR_API, '1', 'A', 'S')
                 when CHANEL='ATMAPI' then null
                 else null 
               end as AVAILABLE_BASKET,
               ----ХЛЕБНЫЕ КРОШКИ--------------------------------------------------------------
               category.ID CATEGORY_ID,
               category.NAME CATEGORY_NAME,
               category.IMAGE_NAME CATEGORY_IMAGE,
               category.CODE CATEGORY_GUID,
               case 
                 when CHANEL='WEB' and category.SHOW_IN_SYSTEM='1'  then 1
                 when CHANEL='MAPI' and category.SHOW_IN_API='1'    then 1
                 when CHANEL='ATMAPI' and category.SHOW_IN_ATM='1'  then 1
                 else 0
               end as SHOW_CATEGORY,
               servGroup.ID GROUP_ID,
               servGroup.NAME GROUP_NAME,
               servGroup.IMAGE_NAME GROUP_IMAGE,
               servGroup.CODE GROUP_GUID,
               case 
                 when CHANEL='WEB' and servGroup.SHOW_IN_SYSTEM='1'  then 1
                 when CHANEL='MAPI' and servGroup.SHOW_IN_API='1'    then 1
                 when CHANEL='ATMAPI' and servGroup.SHOW_IN_ATM='1'  then 1
                 else 0
               end as SHOW_GROUP,
               services.ID SERVICE_ID,
               services.NAME SERVICE_NAME,
               services.IMAGE_NAME SERVICE_IMAGE,
               services.CODE SERVICE_GUID,
               case 
                 when CHANEL='WEB' and services.SHOW_IN_SYSTEM='1'  then 1
                 when CHANEL='MAPI' and services.SHOW_IN_API='1'    then 1
                 when CHANEL='ATMAPI' and services.SHOW_IN_ATM='1'  then 1
                 else 0
               end as SHOW_SERVICE
             from SERVICE_PROVIDERS sp 
             join SERV_PROVIDER_PAYMENT_SERV spps on spps.SERVICE_PROVIDER_ID = sp.ID 
             left join IMAGES i on i.ID=sp.IMAGE_ID
             left join IMAGES ih on ih.ID=sp.IMAGE_HELP_ID
             ----ХЛЕБНЫЕ КРОШКИ--------------------------------------------------------------
             inner join PAYMENT_SERVICES services on spps.PAYMENT_SERVICE_ID = services.ID
             left join PAYMENT_SERV_PARENTS parents on services.id = parents.SERVICE_ID
             left join PAYMENT_SERVICES servGroup on parents.PARENT_ID = servGroup.id
             left join PAYMENT_SERV_PARENTS parents2 on servGroup.id = parents2.SERVICE_ID
             left join PAYMENT_SERVICES category on parents2.PARENT_ID = category.id
             ----КАНАЛЫ ОБСЛУЖИВАНИЯ---------------------------------------------------------
             inner join CHANELS on 1=1        
             where sp.STATE='ACTIVE'
           )
           where 
             AVAILABLE_PAYMENTS     is not null or 
             AVAILABLE_ESB_AUTOP    is not null or 
             AVAILABLE_IQW_AUTOP    is not null or 
             AVAILABLE_TEMPLATES    is not null or 
             AVAILABLE_MB_TEMPLATES is not null or 
             AVAILABLE_BASKET       is not null
         )    
         select 
           ----ПОСТАВЩИК-----------------------------------------------------------------
           PROVIDER_ID, regions.PROVIDER_REGION_ID REGION_ID, CHANEL, PROVIDER_NAME, ALIAS, LEGAL_NAME, CODE_RECIPIENT_SBOL, NAME_B_SERVICE, BILLING_ID, SORT_PRIORITY, PROVIDER_UUID UUID, INN, ACCOUNT,
           IMAGE_ID, IMAGE_UPDATE_TIME,  MD5, H_IMAGE_ID, H_IMAGE_MD5, H_IMAGE_UPDATE_TIME, IS_BAR_SUPPORTED, SUB_TYPE,   
           ----ДОСТУПНОСТЬ---------------------------------------------------------------
           AVAILABLE_PAYMENTS, AVAILABLE_ESB_AUTOP, AVAILABLE_IQW_AUTOP, AVAILABLE_BASKET, AVAILABLE_TEMPLATES, AVAILABLE_MB_TEMPLATES,
           ----ХЛЕБНЫЕ КРОШКИ------------------------------------------------------------
           SERVICE_ID, SERVICE_NAME, SERVICE_IMAGE, SERVICE_GUID, SHOW_SERVICE, GROUP_ID, GROUP_NAME, GROUP_IMAGE, GROUP_GUID, decode(GROUP_ID, null, null, SHOW_GROUP) SHOW_GROUP, CATEGORY_ID, CATEGORY_NAME, CATEGORY_IMAGE, CATEGORY_GUID, decode(CATEGORY_ID, null, null, SHOW_CATEGORY) SHOW_CATEGORY, PKEY_CODE P_KEY
         from AVAILABLE_PROVIDERS providers
         left join REL_REGIONS regions on regions.SERVICE_PROVIDER_ID=providers.PROVIDER_ID
         order by REGION_ID, CHANEL, SERVICE_ID, PROVIDER_ID;
     
       end if;
   
       commit;
     end;
end SERVICES_AGGREGATION;
go


-- Номер ревизии: 71173
-- Номер версии: 1.18
-- Комментарий: CHG073314  Изменить работу выборки адреса в заявке на кредит
DROP TABLE LOANCLAIM_AREA;

DROP TABLE LOANCLAIM_CITY;

DROP TABLE LOANCLAIM_SETTLEMENT;

DROP TABLE LOANCLAIM_STREET;

/*==============================================================*/
/* Table: LOANCLAIM_AREA                                        */
/*==============================================================*/
create table LOANCLAIM_AREA 
(
   ID                   INTEGER              not null,
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(255)        not null,
   TYPEOFAREA           varchar2(20)         not null,
   REGION               varchar2(20)         not null,
   SEARCH_POSTFIX       varchar2(255)        not null,
   constraint PK_LOANCLAIM_AREA primary key (ID)
)

go

create sequence S_LOANCLAIM_AREA
go

/*==============================================================*/
/* Index: LOANCLAIM_AREA_REGION_IDX                             */
/*==============================================================*/
create index LOANCLAIM_AREA_REGION_IDX on LOANCLAIM_AREA (
   REGION ASC,
   SEARCH_POSTFIX ASC
)
go

/*==============================================================*/
/* Table: LOANCLAIM_CITY                                        */
/*==============================================================*/
create table LOANCLAIM_CITY 
(
   ID                   INTEGER              not null,
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(255)        not null,
   TYPEOFCITY           varchar2(20)         not null,
   AREA                 NUMBER(20,0)         not null,
   REGION               varchar2(20)         not null,
   SEARCH_POSTFIX       varchar2(255)        not null,
   constraint PK_LOANCLAIM_CITY primary key (ID)
)

go

create sequence S_LOANCLAIM_CITY
go

/*==============================================================*/
/* Index: LOANCLAIM_CITY_REGION_IDX                             */
/*==============================================================*/
create index LOANCLAIM_CITY_REGION_IDX on LOANCLAIM_CITY (
   REGION ASC,
   SEARCH_POSTFIX ASC
)
go

/*==============================================================*/
/* Table: LOANCLAIM_SETTLEMENT                                  */
/*==============================================================*/
create table LOANCLAIM_SETTLEMENT 
(
   ID                   INTEGER              not null,
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(255)        not null,
   TYPEOFLOCALITY       varchar2(20)         not null,
   CITY                 NUMBER(20,0)         not null,
   AREA                 NUMBER(20,0)         not null,
   REGION               varchar2(20)         not null,
   SEARCH_POSTFIX       varchar2(255)        not null,
   constraint PK_LOANCLAIM_SETTLEMENT primary key (ID)
)

go

create sequence S_LOANCLAIM_SETTLEMENT
go

/*==============================================================*/
/* Index: LOANCLAIM_SETTL_REGION_IDX                            */
/*==============================================================*/
create index LOANCLAIM_SETTL_REGION_IDX on LOANCLAIM_SETTLEMENT (
   REGION ASC,
   SEARCH_POSTFIX ASC
)
go

/*==============================================================*/
/* Table: LOANCLAIM_STREET                                      */
/*==============================================================*/
create table LOANCLAIM_STREET 
(
   ID                   INTEGER              not null,
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(255)        not null,
   TYPEOFSTREET         varchar2(20)         not null,
   SETTLEMENT           NUMBER(20,0)         not null,
   CITY                 NUMBER(20,0)         not null,
   AREA                 NUMBER(20,0)         not null,
   REGION               varchar2(20)         not null,
   SEARCH_POSTFIX       varchar2(255)        not null,
   constraint PK_LOANCLAIM_STREET primary key (ID)
)

go

create sequence S_LOANCLAIM_STREET
go

/*==============================================================*/
/* Index: LOANCLAIM_STREET_REGION_IDX                           */
/*==============================================================*/
create index LOANCLAIM_STREET_REGION_IDX on LOANCLAIM_STREET (
   REGION ASC,
   SEARCH_POSTFIX ASC
)
go

create index "DXLC_AREA_TYPEOFAREA_FK" on LOANCLAIM_AREA
(
   TYPEOFAREA
)
go

alter table LOANCLAIM_AREA
   add constraint LC_AREA_TYPEOFAREA_FK foreign key (TYPEOFAREA)
      references LOANCLAIM_TYPE_OF_AREA (CODE)
go

create index "DXLC_CITY_TYPEOFCITY_FK" on LOANCLAIM_CITY
(
   TYPEOFCITY
)
go

alter table LOANCLAIM_CITY
   add constraint LC_CITY_TYPEOFCITY_FK foreign key (TYPEOFCITY)
      references LOANCLAIM_TYPE_OF_CITY (CODE)
go

create index "DXLC_SETTL_TYPEOFLOCALITY_FK" on LOANCLAIM_SETTLEMENT
(
   TYPEOFLOCALITY
)
go

alter table LOANCLAIM_SETTLEMENT
   add constraint LC_SETTL_TYPEOFLOCALITY_FK foreign key (TYPEOFLOCALITY)
      references LOANCLAIM_TYPE_OF_LOCALITY (CODE)
go

create index "DXLC_STREET_TYPEOFSTREET_FK" on LOANCLAIM_STREET
(
   TYPEOFSTREET
)
go

alter table LOANCLAIM_STREET
   add constraint LC_STREET_TYPEOFSTREET_FK foreign key (TYPEOFSTREET)
      references LOANCLAIM_TYPE_OF_STREET (CODE)
go

-- Номер ревизии: 71187
-- Номер версии: 1.18
-- Комментарий:  CHG077428: Добавить в профиль ЕРМБ подразделение подключения 

alter table ERMB_PROFILE add CONNECT_DEP_ID number
go

-- Номер ревизии: 71334
-- Номер версии: 1.18
-- Комментарий: CHG082172: ЕРМБ. Часовые пояса. 


UPDATE  ERMB_PROFILE SET TIME_ZONE = '120' WHERE TIME_ZONE = '180'
go
UPDATE  ERMB_PROFILE SET TIME_ZONE = '180' WHERE TIME_ZONE = '240'
go
UPDATE  ERMB_PROFILE SET TIME_ZONE = '240' WHERE TIME_ZONE = '300'
go
UPDATE  ERMB_PROFILE SET TIME_ZONE = '300' WHERE TIME_ZONE = '360'
go
UPDATE  ERMB_PROFILE SET TIME_ZONE = '360' WHERE TIME_ZONE = '420'
go
UPDATE  ERMB_PROFILE SET TIME_ZONE = '420' WHERE TIME_ZONE = '480'
go
UPDATE  ERMB_PROFILE SET TIME_ZONE = '480' WHERE TIME_ZONE = '540'
go
UPDATE  ERMB_PROFILE SET TIME_ZONE = '540' WHERE TIME_ZONE = '600'
go
UPDATE  ERMB_PROFILE SET TIME_ZONE = '600' WHERE TIME_ZONE = '660'
go
UPDATE  ERMB_PROFILE SET TIME_ZONE = '660' WHERE TIME_ZONE = '720'
go

-- Номер ревизии: 71476
-- Номер версии: 1.18
-- Комментарий: Краудгифтинг. ФИО получателя в блоке инициатора

alter table FUND_INITIATOR_RESPONSES add (FIRST_NAME varchar2(120))
go
alter table FUND_INITIATOR_RESPONSES add (SUR_NAME varchar2(120))
go
alter table FUND_INITIATOR_RESPONSES add (PATR_NAME varchar2(120))
go

-- Номер ревизии: 71545
-- Номер версии: 1.18
-- Комментарий: Краудгифтинг(доработка p2p перевода)

alter table FUND_SENDER_RESPONSES drop column ID;

create unique index I_FUND_S_RES_EXT_ID on FUND_SENDER_RESPONSES (
   EXTERNAL_RESPONSE_ID ASC
)
global;

-- Номер ревизии: 71572
-- Номер версии: 1.18
-- Комментарий: Передача в ЕРИБ тега «Дата пролонгации»
alter table STORED_ACCOUNT add PROLONGATION_DATE TIMESTAMP(6);

-- Номер ревизии: 71643
-- Номер версии: 1.18
-- Комментарий: Доработать загрузку справочника мобильных операторов
ALTER TABLE MOBILE_OPERATORS ADD 
( 
	FL_AUTO CHAR(1) NOT NULL, 
	BALANCE INTEGER NULL, 
	MIN_SUMM INTEGER NULL, 
	MAX_SUMM INTEGER NULL 
) 
GO

-- Номер ревизии: 71693
-- Номер версии: 1.18
-- Комментарий: Краудгифтинг(создание запроса на сбор средств)
alter table FUND_INITIATOR_REQUESTS modify (EXPECTED_CLOSED_DATE timestamp not null);
alter table FUND_SENDER_RESPONSES modify (EXPECTED_CLOSED_DATE timestamp not null);

-- Номер ревизии: 71700
-- Номер версии: 1.18
-- Комментарий: Загрузка справочника "Возрастные ограничения клиентов для включения в сегмент"
create table AGE_REQUIREMENT 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(10 BYTE)    not null,
   DATE_BEGIN           TIMESTAMP(6)         not null,
   LOW_LIMIT_FEMALE     INTEGER,
   LOW_LIMIT_MALE       INTEGER,
   TOP_LIMIT            INTEGER,
   constraint PK_AGE_REQUIREMENT primary key (ID)
)
go

create sequence S_AGE_REQUIREMENT
go


-- Номер ревизии: 71736
-- Номер версии: 1.18
-- Комментарий: Краудгифтинг. Ответы в блоке инициатора. Глобальный индекс

drop index I_FUND_I_ACCUMULATED 
go
create index I_FUND_I_ACCUMULATED on FUND_INITIATOR_RESPONSES (
    decode(ACCUMULATED, '1', REQUEST_ID, null)
)
global
go

-- Номер ревизии: 71910
-- Номер версии: 1.18
-- Комментарий: Докомит (BKI_DURATION_UNITS)

create table BKI_DURATION_UNITS
(
    CODE varchar2(2) not null,
    NAME varchar2(20) not null,
    constraint PK_BKI_DURATION_UNITS primary key(CODE)
)
go

-- Номер ревизии: 71973
-- Номер версии: 1.18
-- Комментарий: Унификация формата телефона - вспомогательные таблицы ермб

UPDATE ERMB_SMS_INBOX
   SET PHONE = SUBSTR (PHONE, 2);

ALTER TABLE ERMB_SMS_INBOX MODIFY PHONE VARCHAR2(10);


UPDATE ERMB_REGISTRATION
   SET PHONE_NUMBER = SUBSTR (PHONE_NUMBER, 2);

ALTER TABLE ERMB_REGISTRATION MODIFY PHONE_NUMBER VARCHAR2(10);

-- Номер ревизии: 72052
-- Номер версии: 1.18
-- Комментарий: Применение промо-кодов для открытия промо-вкладов.
/*==============================================================*/
/* Table: PROMO_CODES_DEPOSIT                                   */
/*==============================================================*/
create table PROMO_CODES_DEPOSIT 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(9)          not null,
   CODE_G               VARCHAR2(9)          not null,
   MASK                 VARCHAR2(15)         not null,
   CODE_S               VARCHAR2(9)          not null,
   DATE_BEGIN           TIMESTAMP            not null,
   DATE_END             TIMESTAMP            not null,
   SROK_BEGIN           VARCHAR2(7)          not null,
   SROK_END             VARCHAR2(7)          not null,
   NUM_USE              INTEGER              default 0 not null,
   PRIORITY             CHAR(1)              not null,
   AB_REMOVE            CHAR(1)              not null,
   ACTIVE_COUNT         INTEGER              not null,
   HIST_COUNT           INTEGER              not null,
   NAME_ACT             VARCHAR2(250)        not null,
   NAME_S               VARCHAR2(150)        not null,
   NAME_F               VARCHAR2(500),
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PROMO_CODES_DEPOSIT primary key (ID)
)

go

create sequence S_PROMO_CODES_DEPOSIT
go

comment on column PROMO_CODES_DEPOSIT.CODE is
'Уникальный код «промо»'
go

comment on column PROMO_CODES_DEPOSIT.CODE_G is
'Код промо-акции'
go

comment on column PROMO_CODES_DEPOSIT.MASK is
'Маска промо- кода'
go

comment on column PROMO_CODES_DEPOSIT.CODE_S is
'Код сегмента клиента'
go

comment on column PROMO_CODES_DEPOSIT.DATE_BEGIN is
'Дата начала периода активации промо- кода'
go

comment on column PROMO_CODES_DEPOSIT.DATE_END is
'Дата окончания периода активации промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.SROK_BEGIN is
'Срок действия промо-кода, начиная со дня ввода клиентом'
go

comment on column PROMO_CODES_DEPOSIT.SROK_END is
'Срок действия промо-кода, начиная со дня окончания периода активации промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.NUM_USE is
'Возможное количество раз использования клиентом'
go

comment on column PROMO_CODES_DEPOSIT.PRIORITY is
'Приоритет условий с промо-кодом «0» - используются ставки для промо-кода; «1» - отображается как отдельный вклад
'
go

comment on column PROMO_CODES_DEPOSIT.AB_REMOVE is
'Возможность удаления введенного промо-кода:«0» - нельзя удалять;«1» - можно удалять
'
go

comment on column PROMO_CODES_DEPOSIT.ACTIVE_COUNT is
'Количество действующих промо- кодов у клиента'
go

comment on column PROMO_CODES_DEPOSIT.HIST_COUNT is
'Количество введенных промо - кодов клиентом'
go

comment on column PROMO_CODES_DEPOSIT.NAME_ACT is
'Описание промо акции'
go

comment on column PROMO_CODES_DEPOSIT.NAME_S is
'Краткое описание промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.NAME_F is
'Подробное описание промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.UUID is
'Кросблочный ID'
go

/*==============================================================*/
/* Index: IDX_PROMO_CODES_DEP_UUID                              */
/*==============================================================*/
create unique index IDX_PROMO_CODES_DEP_UUID on PROMO_CODES_DEPOSIT (
   UUID ASC
)
go

/*==============================================================*/
/* Index: IDX_PROMO_CODES_DEP_CODE                              */
/*==============================================================*/
create unique index IDX_PROMO_CODES_DEP_CODE on PROMO_CODES_DEPOSIT (
   CODE ASC
)
go

-- Номер ревизии: 72240
-- Номер версии: 1.18
-- Комментарий: Доработка профиля сотрудника в АРМ ЕРИБ
alter table EMPLOYEES add (SUDIR_LOGIN varchar2(100));

-- Номер ревизии: 72282
-- Номер версии: 1.18
-- Комментарий: BUG083283: [БКИ] Падение при переходе в детальную информацию по кред. продукту. Добавление поля в BKI_PRIMARY_ID_TYPE
delete from BKI_PRIMARY_ID_TYPE;

alter table BKI_PRIMARY_ID_TYPE add BKI_CODE VARCHAR2(2);

insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '01', 'Паспорт гражданина РФ', 'REGULAR_PASSPORT_RF', '0', '01');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '01', 'Паспорт гражданина РФ', 'PASSPORT_WAY', '0', null); -- Для БКИ паспорт WAY = паспорт РФ (ENH081992: [БКИ] Доработать запрос взаимодействия с ОКБ)
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '02', 'Международный паспорт (для не граждан РФ)', 'FOREIGN_PASSPORT', '0', '02');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '03', 'Военный билет', 'MILITARY_IDCARD', '0', '03');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '04', 'Удостоверение офицера', 'OFFICER_IDCARD', '0', '04');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '05', 'Паспорт моряка', 'SEAMEN_PASSPORT', '0', '05');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '06', 'ЕГРН', NULL, '0', '06');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '07', 'Временное удостоверение по форме № 2-П', 'TIME_IDCARD_RF', '0', '07');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '08', 'ИНН Физического лица', NULL, '0', '08');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '09', 'ИНН Юридического лица', NULL, '0', '09');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '10', 'ОКПО', NULL, '0', '10');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '11', 'Заграничный паспорт (для граждан РФ для выезда за рубеж)', 'FOREIGN_PASSPORT_RF', '0', '11');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '12', 'Свидетельство о рождение (Для граждан РФ, не достигших 14 лет)', 'BIRTH_CERTIFICATE', '0', '12');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '13', 'Дипломатический паспорт', 'DIPLOMATIC_PASSPORT_RF', '0', '13');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '14', 'Паспорт гражданина СССР', 'REGULAR_PASSPORT_USSR', '0', '14');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '15', 'Водительское удостоверение', NULL, '0', '15');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '16', 'Паспорт, выданный иностранным государством и признаваемый в Российской Федерации', 'FOREIGN_PASSPORT_LEGAL', '0', '16');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '17', 'Вид на жительство', 'RESIDENTIAL_PERMIT_RF', '0', '17');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '18', 'Удостоверение беженца', 'REFUGEE_IDENTITY', '0', '18');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '19', 'Свидетельство о регистрации ходатайства о признании иммигранта беженцем', 'IMMIGRANT_REGISTRATION', '0', '19');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '20', 'Разрешение на временное проживание лица без гражданства', 'TEMPORARY_PERMIT', '0', '20');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '21', 'Иные документы, выдаваемые уполномоченными органами (для граждан РФ)', 'OTHER_DOCUMENT_MVD', '0', '21');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '22', 'Код иностранной организации', NULL, '0', '22');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '23', 'Код причины постановки на учет в налоговой инспекции', NULL, '0', '23');
insert into BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (S_BKI_PRIMARY_ID_TYPE.nextval, '99', 'Неизвестно', 'OTHER', '1', '99');

-- Номер ревизии: 72355
-- Номер версии: 1.18
-- Комментарий: BUG083390: Краудгифтинг. Список входящих запросов. Поле <phones>. 
ALTER TABLE FUND_INITIATOR_REQUESTS ADD INITIATOR_PHONES VARCHAR2(1000);
ALTER TABLE FUND_INITIATOR_REQUESTS MODIFY INITIATOR_PHONES NOT NULL;

-- Номер ревизии: 72403
-- Номер версии: 1.18
-- Комментарий: BUG083390: Краудгифтинг. Push.

alter table FUND_SENDER_RESPONSES add (INITIATOR_BIRTH_DATE timestamp not null)
go
alter table FUND_SENDER_RESPONSES add (INITIATOR_TB varchar2(4) not null)
go
alter table FUND_SENDER_RESPONSES add (INITIATOR_PASSPORT varchar2(100) not null)
go


-- Номер ревизии: 72437
-- Номер версии: 1.18
-- Комментарий: Добавление таблицы вопросов для заявки на кредит.
/*==============================================================*/
/* Table: LOAN_CLAIM_QUESTION                                   */
/*==============================================================*/
create table LOAN_CLAIM_QUESTION 
(
   ID                   INTEGER              not null,
   QUESTION             VARCHAR2(500)        not null,
   ANSWER_TYPE          INTEGER              not null,
   STATUS               INTEGER,
   constraint PK_LOAN_CLAIM_QUESTION primary key (ID)
)
;

create sequence S_LOAN_CLAIM_QUESTION
;

/*==============================================================*/
/* Index: IDX_ID_STATUS                                         */
/*==============================================================*/
create index IDX_ID_STATUS on LOAN_CLAIM_QUESTION (
   ID ASC,
   STATUS ASC
)
;

/*==============================================================*/
/* Index: IDX_STATUS                                            */
/*==============================================================*/
create index IDX_STATUS on LOAN_CLAIM_QUESTION (
   STATUS ASC
)
;

-- Номер ревизии: 72543
-- Номер версии: 1.18
-- Комментарий: Розничный CRM. Модель БД предодобренных предложений
CREATE TABLE CRM_OFFER_LOGINS
(
  LOGIN_ID             INTEGER                  NOT NULL,
  LAST_RQ_UID          VARCHAR2(32)             NOT NULL,
  LAST_RQ_TIME         TIMESTAMP(6)             NOT NULL,
  LAST_RQ_STATUS       VARCHAR2(20)             NOT NULL,
  CONSTRAINT CRM_OFFER_LOGINS_PK PRIMARY KEY(LOGIN_ID)
)
;

ALTER TABLE CRM_OFFER_LOGINS
ADD CONSTRAINT FK_CRM_OFLOGIN_TO_LOGIN FOREIGN KEY (LOGIN_ID)
  REFERENCES LOGINS (ID)
  ON DELETE CASCADE
;

CREATE TABLE CRM_OFFERS
(
  ID                   INTEGER                  NOT NULL,
  LOGIN_ID             INTEGER                  NOT NULL,
  CAMPAIGN_ID          VARCHAR2(15)             NOT NULL,
  CAMPAIGN_NAME        VARCHAR2(255)            NULL,
  PRODUCT_TYPE         VARCHAR2(20)             NOT NULL,
  SOURCE_CODE          VARCHAR2(20)             NOT NULL,
  SOURCE_NAME          VARCHAR2(200)            NOT NULL,
  PRODUCT_AS_NAME      VARCHAR2(200)            NOT NULL,
  PRODUCT_TYPE_CODE    VARCHAR2(50)             NULL,
  PRODUCT_CODE         VARCHAR2(50)             NULL,
  PRODUCT_SUB_CODE     VARCHAR2(50)             NULL,
  CAMPAIGN_MEMBER_ID   VARCHAR2(50)             NOT NULL,
  CLIENT_ID            VARCHAR2(100)            NOT NULL,
  TB                   VARCHAR2(4)              NOT NULL,
  PRIORITY             INTEGER                  NOT NULL,
  PERSONAL_TEXT        VARCHAR2(500)            NULL,
  EXP_DATE             TIMESTAMP(6)             NOT NULL,
  CURRENCY_CODE        CHAR(3)                  NOT NULL,
  RATE_MIN             NUMBER(7,2)              NULL,
  RATE_MAX             NUMBER(7,2)              NULL,
  LIMIT_MIN            NUMBER(19,2)             NULL,
  LIMIT_MAX            NUMBER(19,2)             NULL,
  PERIOD_MIN           INTEGER                  NULL,
  PERIOD_MAX           INTEGER                  NULL,
  LOAD_DATE            DATE                     NOT NULL,
  CONSTRAINT CRM_OFFERS_PK PRIMARY KEY(ID)
)
;

ALTER TABLE CRM_OFFERS
ADD CONSTRAINT FK_CRM_OFFER_TO_LOGIN FOREIGN KEY (LOGIN_ID)
  REFERENCES LOGINS (ID)
  ON DELETE CASCADE
;

CREATE TABLE CRM_OFFER_CONDITIONS
(
  OFFER_ID             INTEGER                  NOT NULL,
  RATE                 NUMBER(7,2)              NOT NULL,
  PERIOD               INTEGER                  NOT NULL,
  AMOUNT               NUMBER(19,2)             NOT NULL
)
;

ALTER TABLE CRM_OFFER_CONDITIONS
ADD CONSTRAINT FK_CRM_OFCOND_TO_OFFER FOREIGN KEY (OFFER_ID)
  REFERENCES CRM_OFFERS (ID)
  ON DELETE CASCADE
;

CREATE TABLE CRM_OFFER_FEEDBACKS
(
  LOGIN_ID             INTEGER                  NOT NULL,
  SOURCE_CODE          VARCHAR2(20)             NOT NULL,
  CAMPAIGN_MEMBER_ID   VARCHAR2(50)             NOT NULL,
  INFORM_TIME          TIMESTAMP(6)             NULL,
  INFORM_CHANNEL       VARCHAR2(20)             NULL,
  PRESENT_TIME         TIMESTAMP(6)             NULL,
  PRESENT_CHANNEL      VARCHAR2(20)             NULL,
  OFFER_END_DATE       TIMESTAMP(6)             NOT NULL,
  CONSTRAINT CRM_OFFER_FEEDBACKS_PK PRIMARY KEY(LOGIN_ID, SOURCE_CODE, CAMPAIGN_MEMBER_ID)
)
;

ALTER TABLE CRM_OFFER_FEEDBACKS
ADD CONSTRAINT FK_CRM_OFFEEDBACK_TO_LOGIN FOREIGN KEY (LOGIN_ID)
  REFERENCES LOGINS (ID)
  ON DELETE CASCADE
;

-- Номер ревизии: 72612
-- Номер версии: 1.18
-- Комментарий: Промо коды клиента
/*==============================================================*/
/* Table: CLIENT_PROMO_CODE                                     */
/*==============================================================*/
create table CLIENT_PROMO_CODE 
(
   ID                   INTEGER                 not null,
   NAME                 VARCHAR(50)          not null,
   LOGIN_ID             INTEGER               not null,
   INPUT_DATE           TIMESTAMP(6)         not null,
   USED                 INTEGER               not null,
   CLOSE_REASON         VARCHAR(50),
   PROMO_ID             INTEGER               not null,
   constraint PK_CLIENT_PROMO_CODE primary key (ID)
)

go

create sequence S_CLIENT_PROMO_CODE
go

-- Номер ревизии: 72769
-- Номер версии: 1.18
-- Комментарий: Применение промо-кодов для открытия промо-вкладов. часть 3.
alter table USER_COUNTERS add BLOCK_UNTIL TIMESTAMP(6);

-- Номер ревизии: 72780
-- Номер версии: 1.18
-- Комментарий: ENH074646: [Кредитная Заявка] Реализовать отложенную загрузку справочников адресов.
alter table LOANCLAIM_AREA drop column id;
alter table LOANCLAIM_CITY drop column id;
alter table LOANCLAIM_SETTLEMENT drop column id;
alter table LOANCLAIM_STREET drop column id;

drop sequence S_LOANCLAIM_AREA;
drop sequence S_LOANCLAIM_CITY;
drop sequence S_LOANCLAIM_SETTLEMENT;
drop sequence S_LOANCLAIM_STREET;

-- Номер ревизии: 72796 
-- Номер версии: 1.18
-- Комментарий: Интеграция с Яндекс.Деньги. Справочник поставщиков услуг. Создание формы списка
/*==============================================================*/
/* Index: IDX_SP_IS_FASILITATOR                                 */
/*==============================================================*/
create index IDX_SP_IS_FASILITATOR on SERVICE_PROVIDERS (
   IS_FASILITATOR ASC
)
go


-- Номер ревизии: 72827
-- Номер версии: 1.18
-- Комментарий: BUG083905: [mAPI][Краудгифтинг] Ошибка при вставке в FUND_SENDER_RESPONSES 
alter table FUND_SENDER_RESPONSES rename column REQUIRES_SUM to REQUIRED_SUM
go
alter table FUND_SENDER_RESPONSES modify (MESSAGE null)
go

-- Номер ревизии: 72958
-- Номер версии: 1.18
-- Комментарий: промо коды клиента (Алгоритм проверки введенного промо кода клиента и верные запросы)
drop table CLIENT_PROMO_CODE
go

drop sequence S_CLIENT_PROMO_CODE
go

/*==============================================================*/
/* Table: CLIENT_PROMO_CODE                                     */
/*==============================================================*/
create table CLIENT_PROMO_CODE 
(
   ID                   INTEGER                not null,
   NAME                 VARCHAR2(50)          not null,
   LOGIN_ID             INTEGER              not null,
   INPUT_DATE           TIMESTAMP(6)         not null,
   USED                 INTEGER              not null,
   CLOSE_REASON         VARCHAR2(50),
   PROMO_ID             INTEGER              not null,
   END_DATE             TIMESTAMP(6)         not null
)
partition by range
 (END_DATE)
    interval (NUMTOYMINTERVAL(1,'YEAR'))
 (partition
         CPC_PART
        values less than (to_date('01-01-2015','DD-MM-YYYY')))
go

create sequence S_CLIENT_PROMO_CODE
  cache 1000
go


/*==============================================================*/
/* Index: CPC_E_DATE_LOGIN_IDX                                  */
/*==============================================================*/
create index CPC_E_DATE_LOGIN_IDX on CLIENT_PROMO_CODE (
   LOGIN_ID ASC,
   END_DATE ASC
)
local
go

/*==============================================================*/
/* Index: CPC_ID_IDX                                            */
/*==============================================================*/
create index CPC_ID_IDX on CLIENT_PROMO_CODE (
   ID ASC
)
go

-- Номер ревизии: 73347
-- Номер версии: 1.18
-- Комментарий: КУКО2 - Доработка справочников по кредитной заявке
CREATE TABLE LOANCLAIM_LOAN_ISSUE_METHOD
(
   CODE                   VARCHAR2 (20 BYTE) NOT NULL,
   NAME                   VARCHAR2 (100 BYTE) NOT NULL,
   AVAILABLE_IN_CLAIM     CHAR (1) NOT NULL,
   NEW_PRODUCT_FOR_LOAN   CHAR (1) NOT NULL,
   PRODUCT_FOR_LOAN       VARCHAR2 (15 BYTE) NOT NULL,
   CONSTRAINT PK_LOANCLAIM_LOAN_ISSUE_METHOD PRIMARY KEY (CODE)
);

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('1',
             'На имеющийся вклад',
             1,
             0,
             'DEPOSIT');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('2',
             'На новый вклад',
             1,
             1,
             'DEPOSIT');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('3',
             'На имеющуюся карту',
             1,
             0,
             'CARD');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('4',
             'На новую карту',
             1,
             1,
             'CARD');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('5',
             'На имеющийся текущий счет',
             1,
             0,
             'CURRENT_ACCOUNT');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('6',
             'На новый текущий счет',
             1,
             1,
             'CURRENT_ACCOUNT');


-- Номер ревизии: 73425
-- Номер версии: 1.18
-- Комментарий: Рефакторинг механизма предодобренных предложений (часть 4)
alter table CRM_OFFERS add (IS_OFFER_USED CHAR(1) default 0 not null);

-- Номер ревизии: 73492
-- Номер версии: 1.18
-- Комментарий: [СБНКД] лимиты
create table PHONE_LIMITS 
(
   ID                   INTEGER              not null,
   PHONE                VARCHAR(15)          not null,
   TYPE_LIMIT           VARCHAR(25)          not null,
   COUNTER              INTEGER              not null,
   LAST_DATE            TIMESTAMP            not null,
   constraint PK_PHONE_LIMITS primary key (ID)
)

go

create sequence S_PHONE_LIMITS
go

/*==============================================================*/
/* Index: IDX_UNIQUE_PHONE_LIMITS                               */
/*==============================================================*/
create unique index IDX_UNIQUE_PHONE_LIMITS on PHONE_LIMITS (
   PHONE ASC,
   TYPE_LIMIT ASC
)
go

-- Номер ревизии: 73567
-- Номер версии: 1.18
-- Комментарий: Интеграция с Яндекс.Деньги. Слепок МБК.
create table PHONE_UPDATE_JURNAL (
	UPDATE_DATE TIMESTAMP not null,
	UPDATED_ID INTEGER not null,
	NEW_ITEM char(1) not null
)
go

-- Номер ревизии: 73664 
-- Номер версии: 1.18
-- Комментарий: Загрузка предодобренных предложений по кредитам из файла (добавление параметра идентификатора участника кампании)
ALTER TABLE LOAN_OFFER ADD (CAMPAIGN_MEMBER_ID  VARCHAR2(50 BYTE));

-- Номер ревизии: 73815 
-- Номер версии: 1.18
-- Комментарий: СБНКД: Связь с базой данных 
create table ISSUE_CARD_CLAIM
(
     ID INTEGER not null,
     UUID varchar2(32),
     OWNER_ID INTEGER not null,
     PHONE varchar2(11) not null,
     IS_GUEST CHAR(1) not null,
     STATUS varchar2(30) not null,
     CREATION_DATE timestamp not null,
     EMAIL varchar2(100),

     SYSTEM_ID varchar2(20),
     STAGE_NUMBER INTEGER,
     TB varchar2(3),
     OSB varchar2(4),
     VSP varchar2(5),
     CLIENT_FOUND char(1) not null,
     VERIFIED char(1) not null,
     MESSAGE_DELIVERY_TYPE varchar2(1),
     CARD_AUTO_PAY_INFO INTEGER,

     CONTACT_TYPE_0 varchar2(4),
     CONTACT_NUMBER_0 varchar2(11),
     CONTACT_PHONE_OPERATOR_0 varchar2(3),
     CONTACT_TYPE_1 varchar2(4),
     CONTACT_NUMBER_1 varchar2(11),
     CONTACT_PHONE_OPERATOR_1 varchar2(3),
     CONTACT_TYPE_2 varchar2(4),
     CONTACT_NUMBER_2 varchar2(11),
     CONTACT_PHONE_OPERATOR_2 varchar2(3),

     ADDRESS_REGISTER_POSTAL_CODE varchar2(6),
     ADDRESS_REGISTER_COUNTRY varchar2(100),
     ADDRESS_REGISTER_REGION varchar2(100),
     ADDRESS_REGISTER_CITY varchar2(100),
     ADDRESS_REGISTER_AFTER_CITY varchar2(200),
     
     ADDRESS_LIVE_POSTAL_CODE varchar2(6),
     ADDRESS_LIVE_COUNTRY varchar2(100),
     ADDRESS_LIVE_REGION varchar2(100),
     ADDRESS_LIVE_CITY varchar2(100),
     ADDRESS_LIVE_AFTER_CITY varchar2(200),
     
     CONTRACT_BRANCH_ID varchar2(3),
     CONTRACT_AGENCY_ID varchar2(4),
     CONTRACT_REGION_ID varchar2(5),
     CONTRACT_CREDIT_CARD_OFFICE varchar2(150),
     
     EDBO_BRANCH_ID varchar2(5),
     EDDO_AGENCY_ID varchar2(4),
     EDBO_PHONE varchar2(11),
     EDBO_PHONE_OPERATOR varchar2(3),
     EDBO_ORDER_NUMBER varchar2(50),
     EDBO_TB varchar2(3),

     IDENTITY_CARD_TYPE varchar2(10),
     IDENTITY_CARD_SERIES varchar2(12),
     IDENTITY_CARD_NUMBER varchar2(25),
     IDENTITY_CARD_ISSUED_BY varchar2(255),
     IDENTITY_CARD_ISSUED_CODE varchar2(10),
     IDENTITY_CARD_ISSUE_DATE TIMESTAMP,
     IDENTITY_CARD_EXP_DATE TIMESTAMP,

     PERSON_BIRTHDAY TIMESTAMP,
     PERSON_BIRTHPLACE varchar2(255),
     PERSON_CITIZENSHIP varchar2(30),
     PERSON_GENDER char(1) not null,
     PERSON_RESIDENT char(1),
     PERSON_TAX_ID varchar2(12),
     PERSON_LAST_NAME varchar2(120) not null,
     PERSON_FIRST_NAME varchar2(120) not null,
     PERSON_MIDDLE_NAME varchar2(120),
     
     constraint PK_ISSUE_CARD_CLAIM primary key (ID)
)
partition by range
(CREATION_DATE)
    interval (numtoyminterval(1,'MONTH'))
(partition
         P_START
        values less than (to_date('01-02-2015','DD-MM-YYYY')))
go

create sequence S_ISSUE_CARD_CLAIM
cache 10000
go

create index IDX_ISSUE_CARD_UUID on ISSUE_CARD_CLAIM (
     UUID asc
)
local
go

create table ISSUE_CARD_CLAIM_CARD_INFOS
(
     UUID varchar2(32),
     CREATION_DATE timestamp not null,
     CLAIM_ID INTEGER not null,
     FIRST_CARD CHAR(1) not null,
     STATUS varchar2(10) not null,
     CARD_NUMBER varchar2(25),
     ACCOUNT_NUMBER varchar2(25),
     CONTRACT_NUMBER varchar2(25),
     CARD_END_DATE timestamp,
     CARD_TYPE INTEGER,
     CARD_CURRENCY char(3),
     PIN_PACK INTEGER,
     MBK_STATUS char(1) not null,
     MBK_PHONE varchar2(16),
     MBK_PHONE_OPERATOR varchar2(50),
     PRODUCT_CODE varchar2(50),
     CREDIT_LIMIT INTEGER,
     CARD_CONTRACT_DATE VARCHAR2(30),
     BONUS_INFO varchar2(255),
     BONUS_INFO_NUMBER varchar2(6),
     CONTRACT_CODE varchar2(50),
     RISK_FACTOR varchar2(50),
     CLIENT_CATEGORY INTEGER,
     BIOData char(1) not null,
     IS_PIN char(1) not null,
     IS_OWNER char(1) not null,
     CONTRACT_EMBOSSED_TEXT varchar2(19),
	 CLIENT_CARD_NAME varchar2(50)
)
partition by range
(CREATION_DATE)
    interval (numtoyminterval(1,'MONTH'))
(partition
         P_START
        values less than (to_date('01-02-2015','DD-MM-YYYY')))
go

create index IDX_ISSUE_CARD_CARD_UUID on ISSUE_CARD_CLAIM_CARD_INFOS (
     UUID asc
)
local
go

alter table ISSUE_CARD_CLAIM_CARD_INFOS
  add constraint FK_ISSU_CARD_CLA_CARD foreign key (CLAIM_ID) 
     references ISSUE_CARD_CLAIM (ID)
go

create index IDX_ICAR_FK_CAR_CL on ISSUE_CARD_CLAIM_CARD_INFOS (
     CLAIM_ID asc
)
local
go

-- Номер ревизии: 73819 
-- Номер версии: 1.18
-- Комментарий: Деградация.
alter table MONITORING_STATE_CONFIGS add (MONITORING_ERROR_PERCENT NUMBER default 30 not null);

-- Номер ревизии: 73860
-- Номер версии: 1.18
-- Комментарий: Единый суточный кумулятивный лимит

drop index PROFILE_CHANNEL_OPER_DATE_IDX;

create index PROFILE_CHANNEL_OPER_DATE_IDX on DOCUMENT_OPERATIONS_JOURNAL (
   PROFILE_ID ASC,
   OPERATION_DATE ASC
)
local;

-- Номер ревизии: 73918
-- Номер версии: 1.18
-- Комментарий: BUG084835: Ошибка при подтверждениии SMS-паролем заявки на дебетовую карту СБНКД
alter table ISSUE_CARD_CLAIM_CARD_INFOS add MBK_CONTRACT_TYPE INTEGER
go

-- Номер ревизии: 73947
-- Номер версии: 1.18
-- Комментарий: СБНКД: Связь с базой данных. Неверные поля
alter table ISSUE_CARD_CLAIM modify (
    CONTRACT_BRANCH_ID varchar2(5),
    CONTRACT_REGION_ID varchar2(3)
)
go

-- Номер ревизии: 74144
-- Номер версии: 1.18
-- Комментарий:  Управление лимитами кредитных карт
/*==============================================================*/
/* Table: UESI_MESSAGES                                         */
/*==============================================================*/
create table UESI_MESSAGES 
(
   ID                   INTEGER              not null,
   CREATION_DATE        TIMESTAMP(6)         not null,
   EXTERNAL_ID          VARCHAR2(80),
   PHONE                VARCHAR2(11),
   EVENT_DATE_TIME      TIMESTAMP(6),
   STATE                VARCHAR2(10)         not null
)
partition by range
 (CREATION_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST
        values less than (to_date('01-02-2015','DD-MM-YYYY'))
         compress)
;

create sequence S_UESI_MESSAGES
;

/*==============================================================*/
/* Index: UESI_EXT_ID_IDX                                       */
/*==============================================================*/
create index UESI_EXT_ID_IDX on UESI_MESSAGES (
   EXTERNAL_ID ASC
)
local
;

/*==============================================================*/
/* Index: UESI_ID_IDX                                           */
/*==============================================================*/
create index UESI_ID_IDX on UESI_MESSAGES (
   ID ASC
)
local
;

/*==============================================================*/
/* Index: UESI_STATE_IDX                                        */
/*==============================================================*/
create index UESI_STATE_IDX on UESI_MESSAGES (
   STATE ASC,
   CREATION_DATE ASC
)
local
;

-- Номер ревизии: 74328
-- Номер версии: 1.18
-- Комментарий: СБНКД. Списки. Список заявок на странице карты
alter table ISSUE_CARD_CLAIM add
( 
    CARD_COUNT integer null,
    FIRST_CARD_NAME varchar2(50) null,
    FIRST_CARD_CURRENCY CHAR(3) null
)
go

/*==============================================================*/
/* Index: IDX_ISSUE_CARD_OWNER_DATE                             */
/*==============================================================*/
create index IDX_ISSUE_CARD_OWNER_DATE on ISSUE_CARD_CLAIM (
   OWNER_ID ASC,
   CREATION_DATE ASC
)
local
go

-- Номер версии: 1.18
-- Комментарий: КУКО2 - Переименование шагов в старых кредитных заявках 
UPDATE (
  SELECT DOCUMENTS.STATE_CODE 
    FROM BUSINESS_DOCUMENTS DOCUMENTS 
   WHERE DOCUMENTS.KIND = 'EL'
) 
SET STATE_CODE =
  CASE WHEN STATE_CODE = 'INITIAL'  THEN 'INITIAL2'
       WHEN STATE_CODE = 'INITIAL2' THEN 'INITIAL3'   
       WHEN STATE_CODE = 'INITIAL3' THEN 'INITIAL4'   
       WHEN STATE_CODE = 'INITIAL4' THEN 'INITIAL5'   
       WHEN STATE_CODE = 'INITIAL5' THEN 'INITIAL6'   
       WHEN STATE_CODE = 'INITIAL6' THEN 'INITIAL7'   
       WHEN STATE_CODE = 'INITIAL7' THEN 'INITIAL8'
  ELSE STATE_CODE END;
  
 -- Номер версии: 1.18
 -- Комментарий: CHG084643: Расширить поле id в таблице incognito_phones
 
 alter table INCOGNITO_PHONES modify ID NUMBER(*,0)
 go
 
-- Номер ревизии: 74940
-- Номер версии: 1.18
-- Комментарий: BUG083955	[АРМ сотрудника. Поставщики] ошибка при сохранении изменений поставщика

update SERVICE_PROVIDERS set IMAGE_HELP_ID = null where IMAGE_ID = IMAGE_HELP_ID  

-- Номер ревизии: 75162
-- Номер версии: 1.18
-- Комментарий: BUG084689: АРМ. На странице мобильного банка в анкете клиента не отображаются счетчики последних активностей

/*==============================================================*/
/* Table: ERMB_PROFILE_STATISTIC                                */
/*==============================================================*/
create table ERMB_PROFILE_STATISTIC 
(
   PROFILE_ID           INTEGER              not null,
   LAST_REQUEST_TIME    TIMESTAMP,
   constraint PK_ERMB_PROFILE_STATISTIC primary key (PROFILE_ID)
)

go

create sequence S_ERMB_PROFILE_STATISTIC
go

alter table ERMB_PROFILE_STATISTIC
   add constraint FK_ERMB_PRO_FK_ERMB_P_ERMB_PRO foreign key (PROFILE_ID)
      references ERMB_PROFILE (PERSON_ID)
go

-- Номер ревизии: 75204
-- Номер версии: 1.18
-- Комментарий: BUG084689: АРМ. На странице мобильного банка в анкете клиента не отображаются счетчики последних активностей

alter table ERMB_PROFILE drop column last_request_time;

-- Номер ревизии: 75307
-- Номер версии: 1.18
-- Комментарий: BUG082567: [ISUP] mAPI. Долгая обработка запроса на открытие вклада
/*==============================================================*/
/* Table: DEPOSITS_DCF_TAR                                      */
/*==============================================================*/
create table DEPOSITS_DCF_TAR 
(
   ID                   INTEGER              not null,
   KOD_VKL_QDTN1        INTEGER              not null,
   KOD_VKL_QDTSUB       INTEGER              not null,
   KOD_VKL_QVAL         CHAR(1)              not null,
   KOD_VKL_CLNT         INTEGER              not null,
   DCF_SROK             INTEGER              not null,
   DATE_BEG             TIMESTAMP(6)         not null,
   SUM_BEG              NUMBER(21,4)         not null,
   SUM_END              NUMBER(21,4)         not null,
   TAR_VKL              NUMBER(21,4)         not null,
   TAR_NRUS             NUMBER(21,4),
   DCF_VAL              VARCHAR2(3)          not null,
   DCF_SEG              INTEGER              not null,
   constraint PK_DEPOSITS_DCF_TAR primary key (ID)
)

go

create sequence S_DEPOSITS_DCF_TAR
go

/*==============================================================*/
/* Index: DEPOSITS_DCF_TAR_IDX                                  */
/*==============================================================*/
create index DEPOSITS_DCF_TAR_IDX on DEPOSITS_DCF_TAR (
   KOD_VKL_QDTN1 ASC,
   KOD_VKL_QDTSUB ASC
)
go

-- Номер ревизии: 75635
-- Номер версии: 1.18
-- Комментарий: BUG084622: Ошибка при входе если появилось много закрытых вкладов.
alter table ACCOUNT_LINKS add
( 
    CLOSED_STATE  CHAR(1) null
)
go

alter table CARD_LINKS add
( 
    CLOSED_STATE  CHAR(1) null
)
go

alter table LOAN_LINKS add
( 
    CLOSED_STATE  CHAR(1) null
)
go

-- Номер ревизии: 75835
-- Номер версии: 1.18
-- Комментарий: BUG084691  ЕРМБ. При подключении услуги МБ, делать регистрацию у разных ОСС, как сейчас у мегафона (бд)
ALTER TABLE DEF_CODES ADD MNC CHAR(2) NOT NULL;
create table ERMB_MOBILE_OPERATOR 
(
   MNC                  CHAR(2)              not null,
   NAME                 VARCHAR2(20)         not null,
   USE_INTEGRATION      CHAR(1)              not null,
   SERVICE_URL          VARCHAR2(256),
   SERVICE_LOGIN        VARCHAR2(100),
   SERVICE_PASSWORD     VARCHAR2(100),
   constraint PK_ERMB_MOBILE_OPERATOR primary key (MNC)
)

-- Номер ревизии: 75873
-- Номер версии: 1.18
-- Комментарий: Перенос расширенной кредитной заявки в новые таблицы Гостевая и клиентская сессии.
CREATE TABLE LOAN_CLAIMS
(
  ID                        INTEGER             NOT NULL,
  EXTERNAL_ID               VARCHAR2(80 BYTE),
  SYSTEM_NAME               VARCHAR2(128 BYTE),
  DOC_NUMBER                VARCHAR2(121 BYTE)  NOT NULL,
  OPERATION_UID             VARCHAR2(27 BYTE),
  STATE_CODE                VARCHAR2(25 BYTE)   NOT NULL,
  STATE_DESCRIPTION         VARCHAR2(256 BYTE)  NOT NULL,
  CREATE_CHANNEL            CHAR(1 BYTE)        NOT NULL,
  CREATION_SOURCE_TYPE      CHAR(1 BYTE)        NOT NULL,
  CREATION_DATE             TIMESTAMP(6)        NOT NULL,
  DOCUMENT_DATE             TIMESTAMP(6)        NOT NULL,
  OPERATION_DATE            TIMESTAMP(6),
  ADMISSION_DATE            TIMESTAMP(6),
  EXECUTION_DATE            TIMESTAMP(6),
  CONFIRM_CHANNEL           CHAR(1 BYTE),
  CONFIRM_STRATEGY_TYPE     VARCHAR2(18 BYTE),
  ADDITION_CONFIRM          CHAR(1 BYTE),
  REFUSING_REASON           VARCHAR2(256 BYTE),
  ARCHIVE                   CHAR(1 BYTE),
  TB                        VARCHAR2(4 BYTE)    NOT NULL,
  OSB                       VARCHAR2(4 BYTE),
  VSP                       VARCHAR2(4 BYTE),
  OWNER_LOGIN_ID            INTEGER,
  OWNER_GUEST_ID            INTEGER,
  OWNER_GUEST_PHONE         VARCHAR2(15 BYTE),
  OWNER_GUEST_MBK           CHAR(1 BYTE),
  OWNER_EXTERNAL_ID         VARCHAR2(100 BYTE)  NOT NULL,
  OWNER_FIRST_NAME          VARCHAR2(42 BYTE),
  OWNER_LAST_NAME           VARCHAR2(42 BYTE),
  OWNER_MIDDLE_NAME         VARCHAR2(120 BYTE),
  OWNER_BIRTHDAY            TIMESTAMP(6)        ,
  OWNER_IDCARD_SERIES       VARCHAR2(19 BYTE),
  OWNER_IDCARD_NUMBER       VARCHAR2(19 BYTE),
  OWNER_TB                  VARCHAR2(4 BYTE)    NOT NULL,
  OWNER_OSB                 VARCHAR2(4 BYTE),
  OWNER_VSP                 VARCHAR2(4 BYTE),
  OWNER_LOGIN_TYPE          VARCHAR2(10 BYTE),
  CREATE_MANAGER_LOGIN_ID   INTEGER,
  CONFIRM_MANAGER_LOGIN_ID  INTEGER,
  CHANGED                   TIMESTAMP(6)        NOT NULL,
  ETSM_ERROR_CODE           INTEGER,

  constraint PK_LOAN_CLAIMS primary key(ID)
)
partition by range (CREATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH')) (partition P_START values less than (timestamp' 2015-01-01 00:00:00'));
alter table LOAN_CLAIMS add constraint FK_CLAIMS_OWNER_LOGIN foreign key (OWNER_LOGIN_ID) references LOGINS(ID) on delete cascade;
alter table LOAN_CLAIMS add constraint FK_CLAIMS_CR_MANAGER_LOGIN foreign key (CREATE_MANAGER_LOGIN_ID) references LOGINS(ID) on delete cascade;
alter table LOAN_CLAIMS add constraint FK_CLAIMS_CONF_MANAGER_LOGIN foreign key (CONFIRM_MANAGER_LOGIN_ID) references LOGINS(ID) on delete cascade;

create index LOAN_CLAIMS_EXTERNAL_ID_IDX on LOAN_CLAIMS (EXTERNAL_ID);
create index LOAN_CLAIMS_CR_LOGIN_ID_IDX on LOAN_CLAIMS (CREATE_MANAGER_LOGIN_ID);
create index LOAN_CLAIMS_CONF_LOGIN_ID_IDX on LOAN_CLAIMS (CONFIRM_MANAGER_LOGIN_ID);
create index LOAN_CLAIMS_LIEXD_IDX on LOAN_CLAIMS (OWNER_LOGIN_ID, EXECUTION_DATE);
create index LOAN_CLAIMS_LICD_IDX on LOAN_CLAIMS (OWNER_LOGIN_ID, CREATION_DATE) local;
create index LOAN_CLAIMS_GICD_IDX on LOAN_CLAIMS (OWNER_GUEST_ID, CREATION_DATE) local;

create table LOAN_CLAIM_FIELDS
(
    CLAIM_ID INTEGER not null,
    NAME VARCHAR2(64) not null,
    TYPE VARCHAR2(16) not null,
    VALUE VARCHAR2(4000) null,
    CREATION_DATE TIMESTAMP(6) not null
)
partition by range (CREATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH')) (partition P_START values less than (timestamp' 2015-01-01 00:00:00'));
alter table LOAN_CLAIM_FIELDS add constraint FK_CLAIM_FIELDS_TO_CLAIM foreign key (CLAIM_ID) references LOAN_CLAIMS(ID) on delete cascade;

-- Номер ревизии: 75873
-- Номер версии: 1.18
-- Комментарий: Конвертация заявок на кредит из старой таблицы (BUSINESS_DOCUMENTS) в новую (LOAN_CLAIMS).
CREATE TABLE EXTENDED_LOAN_CLAIMS_BACKUP
(
  ID                           INTEGER          NOT NULL,
  GROUND                       VARCHAR2(1024 BYTE),
  LOGIN_ID                     INTEGER,
  FORM_ID                      INTEGER,
  CHANGED                      TIMESTAMP(6)     NOT NULL,
  SIGNATURE_ID                 INTEGER,
  STATE_CODE                   VARCHAR2(25 BYTE) NOT NULL,
  STATE_DESCRIPTION            VARCHAR2(265 BYTE) NOT NULL,
  CURRENCY                     CHAR(3 BYTE),
  DEPARTMENT_ID                INTEGER          NOT NULL,
  DOC_NUMBER                   VARCHAR2(21 BYTE),
  CREATION_TYPE                CHAR(1 BYTE)     NOT NULL,
  CLIENT_OPERATION_TYPE        CHAR(1 BYTE),
  ARCHIVE                      CHAR(1 BYTE),
  KIND                         VARCHAR2(2 BYTE),
  CREATION_DATE                TIMESTAMP(6),
  COMMISSION                   NUMBER(19,4),
  COMMISSION_CURRENCY          CHAR(3 BYTE),
  PAYER_NAME                   VARCHAR2(256 BYTE),
  RECEIVER_ACCOUNT             VARCHAR2(30 BYTE),
  RECEIVER_NAME                VARCHAR2(256 BYTE),
  AMOUNT                       NUMBER(19,4),
  EXTERNAL_ID                  VARCHAR2(80 BYTE),
  EXTERNAL_OFFICE_ID           VARCHAR2(64 BYTE),
  STATE_MACHINE_NAME           VARCHAR2(50 BYTE),
  PAYER_ACCOUNT                VARCHAR2(26 BYTE),
  OPERATION_DATE               TIMESTAMP(6),
  ADMISSION_DATE               TIMESTAMP(6),
  EXECUTION_DATE               TIMESTAMP(6),
  DOCUMENT_DATE                TIMESTAMP(6),
  REFUSING_REASON              VARCHAR2(256 BYTE),
  EXTERNAL_OWNER_ID            VARCHAR2(100 BYTE),
  TEMPLATE_ID                  INTEGER,
  DESTINATION_AMOUNT           NUMBER(19,4),
  DESTINATION_CURRENCY         CHAR(3 BYTE),
  COUNT_ERROR                  INTEGER          DEFAULT 0                     NOT NULL,
  IS_LONG_OFFER                CHAR(1 BYTE)     DEFAULT '0'                   NOT NULL,
  CREATION_SOURCE_TYPE         CHAR(1 BYTE)     DEFAULT '0'                   NOT NULL,
  EXTENDED_FIELDS              CLOB,
  SYSTEM_NAME                  VARCHAR2(128 BYTE),
  SUMM_TYPE                    VARCHAR2(50 BYTE),
  CONFIRM_STRATEGY_TYPE        VARCHAR2(18 BYTE),
  ADDITION_CONFIRM             CHAR(1 BYTE),
  CONFIRM_EMPLOYEE             VARCHAR2(256 BYTE),
  OPERATION_UID                VARCHAR2(32 BYTE),
  PROMO_CODE                   VARCHAR2(10 BYTE),
  BILLING_DOCUMENT_NUMBER      VARCHAR2(100 BYTE),
  PROVIDER_EXTERNAL_ID         VARCHAR2(128 BYTE),
  RECIPIENT_ID                 INTEGER,
  ACCESS_AUTOPAY_SCHEMES       CLOB,
  SESSION_ID                   VARCHAR2(64 BYTE),
  CODE_ATM                     VARCHAR2(255 BYTE),
  CREATED_EMPLOYEE_LOGIN_ID    INTEGER,
  CONFIRMED_EMPLOYEE_LOGIN_ID  INTEGER,
  LOGIN_TYPE                   VARCHAR2(10 BYTE),
  constraint PK_LOAN_CLAIM_BACKUP primary key(ID)
)
;

CREATE TABLE EXT_LOAN_CLAIM_FIELDS_BACKUP
(
  ID          INTEGER                           NOT NULL,
  PAYMENT_ID  INTEGER                           NOT NULL,
  NAME        VARCHAR2(64 BYTE)                 NOT NULL,
  TYPE        VARCHAR2(16 BYTE)                 NOT NULL,
  VALUE       VARCHAR2(4000 BYTE),
  IS_CHANGED  CHAR(1 BYTE),
  CONSTRAINT FK_LOAN_CLAIM_BACKUP
  FOREIGN KEY (PAYMENT_ID)
  REFERENCES EXTENDED_LOAN_CLAIMS_BACKUP (ID)
);

insert into EXTENDED_LOAN_CLAIMS_BACKUP select /*+ INDEX(doc IDX_FK_BD_TO_PAYMENTFORMS) */  * from BUSINESS_DOCUMENTS where FORM_ID=(SELECT ID FROM PAYMENTFORMS WHERE NAME='ExtendedLoanClaim');

insert into EXT_LOAN_CLAIM_FIELDS_BACKUP select /*+ INDEX(doc IDX_FK_BD_TO_PAYMENTFORMS) */ f.ID,f.PAYMENT_ID, f.NAME, f.TYPE,  f.VALUE, f.IS_CHANGED from DOCUMENT_EXTENDED_FIELDS f JOIN BUSINESS_DOCUMENTS doc ON doc.ID = f.PAYMENT_ID where FORM_ID=(SELECT ID FROM PAYMENTFORMS WHERE NAME='ExtendedLoanClaim');

insert into LOAN_CLAIMS
(ID, EXTERNAL_ID, SYSTEM_NAME, DOC_NUMBER, OPERATION_UID,
STATE_CODE, STATE_DESCRIPTION, CREATE_CHANNEL, CREATION_SOURCE_TYPE, CREATION_DATE,
DOCUMENT_DATE, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, CONFIRM_CHANNEL,
CONFIRM_STRATEGY_TYPE, ADDITION_CONFIRM, REFUSING_REASON, ARCHIVE, TB,
OSB, VSP, OWNER_LOGIN_ID, OWNER_GUEST_ID, OWNER_GUEST_PHONE,
OWNER_GUEST_MBK, OWNER_EXTERNAL_ID, OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_MIDDLE_NAME,
OWNER_BIRTHDAY, OWNER_IDCARD_SERIES, OWNER_IDCARD_NUMBER, OWNER_TB, OWNER_OSB,
OWNER_VSP, OWNER_LOGIN_TYPE, CREATE_MANAGER_LOGIN_ID, CONFIRM_MANAGER_LOGIN_ID, CHANGED,
ETSM_ERROR_CODE)
select /*+ ORDERED INDEX(doc IDX_FK_BD_TO_PAYMENTFORMS) INDEX (users IDX_USR_LOGIN) */
doc.ID, EXTERNAl_ID, SYSTEM_NAME, DOC_NUMBER, NULL, STATE_CODE, STATE_DESCRIPTION,
doc.CREATION_TYPE, CREATION_SOURCE_TYPE, CREATION_DATE, DOCUMENT_DATE, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, CLIENT_OPERATION_TYPE,
CONFIRM_STRATEGY_TYPE, ADDITION_CONFIRM, REFUSING_REASON, ARCHIVE,
dep1.tb, dep1.osb, dep1.office,
doc.LOGIN_ID, NULL, NULL, NULL, USERS.CLIENT_ID, NULL, NULL, NULL, NULL, NULL, NULL, dep2.tb, dep2.osb, dep2.office, LOGIN_TYPE,
CREATED_EMPLOYEE_LOGIN_ID, CONFIRMED_EMPLOYEE_LOGIN_ID, CHANGED, null
from BUSINESS_DOCUMENTS doc
JOIN USERS users ON users.LOGIN_ID = doc.LOGIN_ID
LEFT JOIN DEPARTMENTS dep1 ON dep1.ID = doc.DEPARTMENT_ID
LEFT JOIN DEPARTMENTS dep2 ON dep2.ID = users.DEPARTMENT_ID
where FORM_ID=(SELECT ID FROM PAYMENTFORMS WHERE NAME='ExtendedLoanClaim');

update LOAN_CLAIMS set OWNER_FIRST_NAME = (
    select field.value from DOCUMENT_EXTENDED_FIELDS field
    where field.name='firstName' and field.PAYMENT_ID = LOAN_CLAIMS.ID
);

update LOAN_CLAIMS set OWNER_LAST_NAME = (
    select field.value from DOCUMENT_EXTENDED_FIELDS field
    where field.name='surName' and field.PAYMENT_ID = LOAN_CLAIMS.ID
);

update LOAN_CLAIMS set OWNER_MIDDLE_NAME = (
    select field.value from DOCUMENT_EXTENDED_FIELDS field
    where field.name='patrName' and field.PAYMENT_ID = LOAN_CLAIMS.ID
);

update LOAN_CLAIMS set OWNER_BIRTHDAY = (
    select to_timestamp(field.value, 'RRRR-MM-DD') from DOCUMENT_EXTENDED_FIELDS field
    where field.name='birthDay' and field.PAYMENT_ID = LOAN_CLAIMS.ID
);

update LOAN_CLAIMS set OWNER_IDCARD_SERIES = (
    select field.value from DOCUMENT_EXTENDED_FIELDS field
    where field.name='passportSeries' and field.PAYMENT_ID = LOAN_CLAIMS.ID
);

update LOAN_CLAIMS set OWNER_IDCARD_NUMBER = (
    select field.value from DOCUMENT_EXTENDED_FIELDS field
    where field.name='passportNumber' and field.PAYMENT_ID = LOAN_CLAIMS.ID
);

update LOAN_CLAIMS set ETSM_ERROR_CODE = (
    select field.value from DOCUMENT_EXTENDED_FIELDS field
    where field.name='etsmErrorCode' and field.PAYMENT_ID = LOAN_CLAIMS.ID
);

insert into LOAN_CLAIM_FIELDS
(select field.PAYMENT_ID, field.NAME, field.TYPE, field.VALUE, claim.CREATION_DATE from DOCUMENT_EXTENDED_FIELDS field join LOAN_CLAIMS claim on claim.ID = field.PAYMENT_ID);

delete from (select * from DOCUMENT_EXTENDED_FIELDS field JOIN BUSINESS_DOCUMENTS document ON document.ID = field.PAYMENT_ID where FORM_ID=(SELECT ID FROM PAYMENTFORMS WHERE NAME='ExtendedLoanClaim'));
delete from BUSINESS_DOCUMENTS where  FORM_ID=(SELECT ID FROM PAYMENTFORMS WHERE NAME='ExtendedLoanClaim');

-- Номер ревизии: 76108 
-- Номер версии: 1.18
-- Комментарий: BUG086924: [ISUP] Ошибка удаления поставщика из-за constraint (из первых 2х скриптов должен выполниться только один)
alter table AUTOPAY_SETTINGS
   drop constraint FK_AUTOPAY__FK_AUTOPA_SERVICE_
go

alter table AUTOPAY_SETTINGS
   drop constraint FK_AUTOPAY_SETTING_TO_PROV
go

alter table AUTOPAY_SETTINGS
   add constraint FK_AUTOPAY_SETTING_TO_PROV foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go

-- Номер ревизии: 76135
-- Номер версии: 1.18
-- Комментарий: Поиск заявки на кредит в АРМ Сотрудника
create index LOAN_CLAIMS_OPERATION_UID_IDX on LOAN_CLAIMS (OPERATION_UID) local;

-- Номер ревизии: 77018
-- Номер версии: 1.18
-- Комментарий: Перенос расширенной заявки на кредит в отдельные таблицы. История операций.
create index LOAN_CLAIM_FIELDS_CID_IDX on LOAN_CLAIM_FIELDS (CLAIM_ID) local;

-- Номер ревизии: 77137
-- Номер версии: 1.18
-- Комментарий: BUG084475: Несоответствие типа карты на форме редактирования карты в СБНКД
alter table ISSUE_CARD_CLAIM_CARD_INFOS  add CLIENT_CARD_TYPE varchar2(64)
go

-- Номер ревизии: 77156
-- Номер версии: 1.18
-- Комментарий: Маппинг ошибок.

drop table EXCEPTIONS_LOG
go

drop table EXCEPTION_COUNTERS
go

drop table EXCEPTION_ENTRY
go

drop sequence S_EXCEPTION_ENTRY
go

drop procedure aggregateExceptionInformation
go

drop procedure updateExceptionInformation
go

-- Номер ревизии: 77186
-- Номер версии: 1.18
-- Комментарий: Merged revision(s) 77181 from versions/v.1.18_release_16.0_PSI: BUG087602  АРМ. Разные суммы абонентской платы в АРМе и в ФПП. (бд)
ALTER TABLE ERMB_PROFILE DROP COLUMN CHARGE_NEXT_AMOUNT;
ALTER TABLE ERMB_PROFILE DROP COLUMN CHARGE_NEXT_PERIOD;

-- Номер ревизии: 77233
-- Номер версии: 1.18
-- Комментарий: BUG087686: МИГРАЦИЯ ИЗ АС ФИЛИАЛ. -1 ошибка при неуказании дней недели для оповещений. 
alter table ERMB_PROFILE modify DAYS_OF_WEEK null
go

-- Номер ревизии: 77735
-- Номер версии: 1.18
-- Комментарий: Загрузка справочников ЦАС НСИ (вклады)

CREATE TABLE DEPOSITS_BCH_BUX
(
  ID         INTEGER                            NOT NULL,
  BCH_VKL    INTEGER                            NOT NULL,
  BCH_PVVKL  INTEGER                            NOT NULL,
  BCH_VAL    CHAR(1 BYTE)                       NOT NULL,
  FL_REZ     CHAR(1 BYTE)                       NOT NULL,
  BSSCH      VARCHAR2(5 BYTE)                   NOT NULL,
  BEG_SROK   INTEGER,
  END_SROK   INTEGER,
  constraint DEPOSITS_BCH_BUX_PK primary key (ID)
)
;

CREATE TABLE DEPOSITS_CONTRACT
(
  ID                INTEGER                     NOT NULL,
  QDTN1             INTEGER                     NOT NULL,
  QDTSUB            INTEGER                     NOT NULL,
  QVAL              CHAR(1 BYTE)                NOT NULL,
  CONTRACTTEMPLATE  INTEGER                     NOT NULL,
  constraint DEPOSITS_CONTRACT_PK primary key (ID)
)
;

CREATE TABLE DEPOSITS_CONTRACT_TEMPLATE
(
  ID          INTEGER                           NOT NULL,
  TEMPLATEID  INTEGER,
  D_STOP      TIMESTAMP(6),
  TYPE        INTEGER,
  LABEL       VARCHAR2(250 BYTE),
  TEXT        VARCHAR2(3000 BYTE),
  CODE        VARCHAR2(12 BYTE),
  D_START     TIMESTAMP(6),
  constraint DEPOSITS_CONTRACT_TEMPLATE_PK primary key (ID)
)
;

CREATE TABLE DEPOSITS_FIELD_TDOG
(
  ID           INTEGER                          NOT NULL,
  VID          INTEGER                          NOT NULL,
  PVID         INTEGER                          NOT NULL,
  SUM_VKL      VARCHAR2(80 BYTE),
  CURR_VKL     VARCHAR2(80 BYTE),
  QSROK        VARCHAR2(80 BYTE),
  DATA_END     VARCHAR2(80 BYTE),
  PERCENT      VARCHAR2(3000 BYTE),
  PRIXOD       VARCHAR2(1000 BYTE),
  MIN_ADD      VARCHAR2(1000 BYTE),
  PER_ADD      VARCHAR2(1000 BYTE),
  RASXOD       VARCHAR2(1000 BYTE),
  SUM_NOST     VARCHAR2(1000 BYTE),
  PER_PERCENT  VARCHAR2(3000 BYTE),
  ORD_PERCENT  VARCHAR2(3000 BYTE),
  ORD_DOXOD    CLOB,
  QPROL        VARCHAR2(1000 BYTE),
  constraint DEPOSITS_FIELD_TDOG_PK primary key (ID)
)
;

CREATE TABLE DEPOSITS_QVB
(
  ID         INTEGER                            NOT NULL,
  QDTN1      INTEGER                            NOT NULL,
  QDTSUB     INTEGER                            NOT NULL,
  QVAL       CHAR(1 BYTE)                       NOT NULL,
  QDN        VARCHAR2(80 BYTE)                  NOT NULL,
  QSNAME     VARCHAR2(80 BYTE),
  QTYPE      INTEGER                            NOT NULL,
  QOPBEG     TIMESTAMP(6)                       NOT NULL,
  QOPEND     TIMESTAMP(6),
  QPFS       NUMBER(21,4),
  QMINADD    NUMBER(21,4),
  QN_RESN    CHAR(1 BYTE),
  QCAP       INTEGER,
  QCAP_NU    INTEGER,
  QCAPN      INTEGER,
  QPRC       INTEGER,
  QPROL      INTEGER,
  Q_DT_PROL  TIMESTAMP(6),
  QPRCTAR    INTEGER,
  QPRCTYPE   INTEGER,
  Q_SROK     VARCHAR2(10 BYTE),
  FL_RES     INTEGER                            NOT NULL,
  Q_PERMIT   INTEGER,
  Q_EXPENS   INTEGER,
  Q_GROUP    INTEGER,
  Q_MINSD    NUMBER(21,4)                       NOT NULL,
  Q_CDED     INTEGER                            NOT NULL,
  Q_CICET    INTEGER                            NOT NULL,
  constraint DEPOSITS_QVB_PK primary key (ID)
)
;

CREATE TABLE DEPOSITS_QVB_GROUP
(
  ID       INTEGER                              NOT NULL,
  GR_CODE  INTEGER                              NOT NULL,
  GR_NAME  VARCHAR2(250 BYTE)                   NOT NULL,
  constraint DEPOSITS_QVB_GROUP_PK primary key (ID)
)
;

CREATE TABLE DEPOSITS_QVB_PARGR
(
  ID          INTEGER                           NOT NULL,
  PG_CODGR    INTEGER                           NOT NULL,
  PG_BDATE    TIMESTAMP(6)                      NOT NULL,
  PG_FCONTR   CHAR(1 BYTE)                      NOT NULL,
  PG_PENS     CHAR(1 BYTE),
  PG_FMAXP    CHAR(1 BYTE)                      NOT NULL,
  PG_PRONPR   INTEGER                           NOT NULL,
  PG_MAXV     INTEGER                           NOT NULL,
  PG_CSTAV    INTEGER                           NOT NULL,
  PG_TYPEGR   CHAR(1 BYTE)                      NOT NULL,
  PG_OPENWPP  CHAR(1 BYTE)                      NOT NULL,
  PG_PRBC     CHAR(1 BYTE)                      NOT NULL,
  PG_PROMO    CHAR(1 BYTE)                      NOT NULL,
  constraint DEPOSITS_QVB_PARGR_PK primary key (ID)
)
;

CREATE TABLE DEPOSITS_QVB_TP
(
  ID         INTEGER                            NOT NULL,
  TP_QDTN1   INTEGER                            NOT NULL,
  TP_QDTSUB  INTEGER                            NOT NULL,
  TP_QVAL    CHAR(1 BYTE)                       NOT NULL,
  TP_CODE    INTEGER                            NOT NULL,
  TP_DBEG    TIMESTAMP(6)                       NOT NULL,
  TP_DEND    TIMESTAMP(6)                       NOT NULL,
  constraint DEPOSITS_QVB_TP primary key (ID)
)
;

CREATE TABLE DEPOSITS_QVKL_VAL
(
  ID             INTEGER                        NOT NULL,
  QVKL_T_QDTN1   INTEGER                        NOT NULL,
  QVKL_T_QDTSUB  INTEGER                        NOT NULL,
  QVKL_T_QVAL    INTEGER                        NOT NULL,
  QVKL_V         VARCHAR2(10 BYTE),
  constraint DEPOSITS_QVKL_VAL_PK primary key (ID)
)
;

CREATE TABLE DEPOSITS_V_ALG
(
  ID             INTEGER                        NOT NULL,
  V_AVKL_QDTN1   INTEGER                        NOT NULL,
  V_AVKL_QDTSUB  INTEGER                        NOT NULL,
  V_AVKL_QVAL    CHAR(1 BYTE)                   NOT NULL,
  V_TYPE_OP      INTEGER                        NOT NULL,
  V_CODE_VKL     INTEGER                        NOT NULL,
  constraint DEPOSITS_V_ALG_PK primary key (ID)
)
;

create sequence S_DEPOSITS_BCH_BUX;

create sequence S_DEPOSITS_CONTRACT;

create sequence S_DEPOSITS_CONTRACT_TEMPLATE;

create sequence S_DEPOSITS_FIELD_TDOG;

create sequence S_DEPOSITS_QVB;

create sequence S_DEPOSITS_QVB_GROUP ;

create sequence S_DEPOSITS_QVB_PARGR;

create sequence S_DEPOSITS_QVB_TP;

create sequence S_DEPOSITS_QVKL_VAL;

create sequence S_DEPOSITS_V_ALG;

-- Номер ревизии: 77996 
-- Номер версии: 1.18
-- Комментарий: СБНКД. Списки. Отображение в АРМ гостевых заявок 
alter table ISSUE_CARD_CLAIM add
( 
    LOGIN  VARCHAR2(32) null
)
go

create index IDX_ISS_CARD_LOGIN_DATE on ISSUE_CARD_CLAIM (
   LOGIN asc,
   CREATION_DATE asc
)
local
go

create index IDX_ISS_CARD_PHONE_DATE on ISSUE_CARD_CLAIM (
   PHONE asc,
   CREATION_DATE asc
)
local
go

-- Номер ревизии: 78058
-- Номер версии: 1.18
-- Комментарий: Доработка аудита

create index LOAN_CLAIMS_DOCNUM_IDX on LOAN_CLAIMS (
   DOC_NUMBER ASC
)
local
go

create index LOAN_CLAIMS_STCD_IDX on LOAN_CLAIMS (
   STATE_CODE ASC,
   CREATION_DATE ASC
)
local
go

-- Номер ревизии: 78097
-- Номер версии: 1.18
-- Комментарий: CHG088398: Изменить интеграцию в задаче СБНКД 

alter table ISSUE_CARD_CLAIM add
( 
    LAST_LOGON_CARD_NUMBER  VARCHAR2(25) null
)
go

-- Номер ревизии: 78126
-- Номер версии: 1.18
-- Комментарий: Доработка заказа обратного звонка в гостевой сессии. Отладка.
alter table LOAN_CLAIMS add BKI_CONFIRM_DATE TIMESTAMP
go

-- Номер ревизии: 78194
-- Номер версии: 1.18
-- Комментарий: Справочники вкладов ЦАС НСИ. Список вкладов и детальной информации (таблица ставок).
CREATE TABLE DEPOSITS_VISIBILITY
(
  ID          INTEGER                           NOT NULL,
  TYPE        INTEGER                           NOT NULL,
  GROUP_CODE  INTEGER                           NOT NULL,
  SUB_TYPE    INTEGER,
  constraint DEPOSITS_VISIBILITY_PK primary key (ID)
);

CREATE SEQUENCE S_DEPOSITS_VISIBILITY;

CREATE TABLE DEPOSITS_DEPARTMENTS
(
  DEPOSIT_ID  INTEGER,
  TB          VARCHAR2(10 BYTE)                 NOT NULL
);

ALTER TABLE DEPOSITS_DEPARTMENTS ADD (CONSTRAINT FK_DEPOSITS_VISIBILITY FOREIGN KEY (DEPOSIT_ID) REFERENCES DEPOSITS_VISIBILITY (ID));

CREATE INDEX IDX_D_QVB_TYPE_SUBTYPE ON DEPOSITS_QVB(QDTN1, QDTSUB); 

CREATE INDEX IDX_D_BCH_BUX_TYPE_SUBTYPE ON DEPOSITS_BCH_BUX(BCH_VKL, BCH_PVVKL);  

CREATE INDEX IDX_D_VALG_TYPE_SUBTYPE ON DEPOSITS_V_ALG(V_AVKL_QDTN1, V_AVKL_QDTSUB);

CREATE INDEX IDX_D_DEPARTMENTS ON DEPOSITS_DEPARTMENTS(TB, DEPOSIT_ID);

-- Номер ревизии: 78278
-- Номер версии: 1.18
-- Комментарий: Вклады ЦАС НСИ. Настройки видимости, детальная информация

ALTER TABLE DEPOSITS_VISIBILITY DROP COLUMN GROUP_CODE;
ALTER TABLE DEPOSITS_VISIBILITY DROP COLUMN SUB_TYPE;

ALTER TABLE DEPOSITS_VISIBILITY ADD NAME VARCHAR2(100) NULL;
ALTER TABLE DEPOSITS_VISIBILITY ADD AVAILABLE_ONLINE CHAR(1) DEFAULT 0 NULL;
UPDATE DEPOSITS_VISIBILITY SET AVAILABLE_ONLINE = 0;
ALTER TABLE DEPOSITS_VISIBILITY MODIFY (AVAILABLE_ONLINE NOT NULL);

create table DEPOSITS_SUBTYPES_VISIBILITY 
(
   DEPOSIT_ID           INTEGER,
   SUBTYPE              INTEGER              not null
);

create index IDX_D_SUBTYPES on DEPOSITS_SUBTYPES_VISIBILITY (SUBTYPE ASC,DEPOSIT_ID ASC);

alter table DEPOSITS_SUBTYPES_VISIBILITY add constraint FK_DEPOSITS_SUBTYPES foreign key (DEPOSIT_ID) references DEPOSITS_VISIBILITY (ID);

-- Номер ревизии: 78359
-- Номер версии: 1.18
-- Комментарий: Вклады ЦАС НСИ. Загрузка текста шаблона

ALTER TABLE DEPOSITS_CONTRACT_TEMPLATE DROP COLUMN TEXT;
ALTER TABLE DEPOSITS_CONTRACT_TEMPLATE ADD (TEXT  CLOB);

-- Номер ревизии: 78349
-- Номер версии: 1.18
-- Комментарий: CHG086317: СБНКД. Внесение правок по обновленным макетам
alter table ISSUE_CARD_CLAIM add AUTO_PAY_MIN_BALANCE varchar2(8)
go

-- Номер ревизии: 78359
-- Номер версии: 1.18
-- Комментарий: Вклады ЦАС НСИ. Добавление индексов

CREATE INDEX IDX_D_VISIBILITY ON DEPOSITS_VISIBILITY(TYPE);

CREATE INDEX IDX_D_DEPARTMENTS_DEPOSITS ON DEPOSITS_DEPARTMENTS(DEPOSIT_ID);
  
CREATE INDEX IDX_DEPOSITS_TDOG ON DEPOSITS_FIELD_TDOG(VID, PVID);

CREATE INDEX IDX_D_QVKL_VAL ON DEPOSITS_QVKL_VAL(QVKL_T_QDTN1, QVKL_T_QDTSUB);

-- Номер ревизии: 78457
-- Номер версии: 1.18
-- Комментарий: КУКО 2 - Заявка на кредитную карту
create table LOAN_CARD_CLAIMS
(
  ID                   INTEGER              not null,
  ADDITION_CONFIRM     CHAR(1),
  ADMISSION_DATE       TIMESTAMP,
  ARCHIVE              CHAR(1)              not null,
  CHANGED              TIMESTAMP,
  CREATE_CHANNEL       CHAR(1)              not null,
  CREATION_SOURCE_TYPE CHAR(1)              not null,
  CREATION_DATE        TIMESTAMP            not null,
  CONFIRM_CHANNEL      CHAR(1),
  CONFIRM_STRATEGY_TYPE VARCHAR2(18),
  DOC_NUMBER           VARCHAR2(121)        not null,
  DOCUMENT_DATE        TIMESTAMP            not null,
  EXECUTION_DATE       TIMESTAMP,
  EXTERNAL_ID          VARCHAR2(80),
  OPERATION_UID        VARCHAR2(27)         not null,
  OPERATION_DATE       TIMESTAMP,
  OWNER_LOGIN_ID       INTEGER,
  OWNER_GUEST_ID       INTEGER,
  OWNER_GUEST_PHONE    VARCHAR2(15),
  OWNER_GUEST_MBK      CHAR(1),
  OWNER_FIRST_NAME     VARCHAR2(42),
  OWNER_LAST_NAME      VARCHAR2(42),
  OWNER_MIDDLE_NAME    VARCHAR2(120),
  OWNER_BIRTHDAY       TIMESTAMP,
  OWNER_IDCARD_TYPE    CHAR(1),
  OWNER_IDCARD_SERIES  VARCHAR2(19),
  OWNER_IDCARD_NUMBER  VARCHAR2(19),
  OWNER_TB             VARCHAR2(4)          not null,
  OWNER_OSB            VARCHAR2(4),
  OWNER_VSP            VARCHAR2(4),
  OWNER_LOGIN_TYPE     CHAR(10),
  PREAPPROVED          CHAR(1)              not null,
  REFUSING_REASON      VARCHAR2(256),
  STATE_CODE           VARCHAR2(25)         not null,
  STATE_DESCRIPTION    VARCHAR2(256)        not null,
  SYSTEM_NAME          VARCHAR2(128)        not null,
  TB                   VARCHAR2(4)          not null,
  OSB                  VARCHAR2(4),
  VSP                  VARCHAR2(4),
  constraint PK_LOAN_CARD_CLAIMS primary key (ID)
)
partition by range
  (CREATION_DATE)
interval (NUMTOYMINTERVAL(1, 'MONTH'))
(partition
  P_START
  values less than (to_date('01-01-2015','DD-MM-YYYY')));

create index LOAN_CCLAIMS_EXTERNAL_ID_IDX on LOAN_CARD_CLAIMS (
  EXTERNAL_ID ASC
);

create index LOAN_CCLAIMS_LIEXD_IDX on LOAN_CARD_CLAIMS (
  OWNER_LOGIN_ID ASC,
  EXECUTION_DATE ASC
);

create index LOAN_CCLAIMS_LICD_IDX on LOAN_CARD_CLAIMS (
  OWNER_LOGIN_ID ASC,
  CREATION_DATE ASC
)
local;

create index LOAN_CCLAIMS_GICD_IDX on LOAN_CARD_CLAIMS (
  OWNER_GUEST_ID ASC,
  CREATION_DATE ASC
)
local;

create index LOAN_CCLAIMS_OPERATION_UID_IDX on LOAN_CARD_CLAIMS (
  OPERATION_UID ASC
);

create index LOAN_CCLAIMS_DOCNUM_IDX on LOAN_CARD_CLAIMS (
  DOC_NUMBER ASC
);

create index LOAN_CCLAIMS_STCD_IDX on LOAN_CARD_CLAIMS (
  STATE_CODE ASC,
  CREATION_DATE ASC
)
local;

create table LOAN_CARD_CLAIM_FIELDS
(
  CLAIM_ID             INTEGER              not null,
  NAME                 VARCHAR2(64)         not null,
  TYPE                 VARCHAR2(16)         not null,
  VALUE                VARCHAR2(4000),
  CREATION_DATE        TIMESTAMP            not null
)
partition by range
  (CREATION_DATE)
interval (NUMTOYMINTERVAL(1, 'MONTH'))
(partition
  P_START
  values less than (to_date('01-01-2015','DD-MM-YYYY'))
nocompress);

create index LOAN_CARD_CLAIM_FIELDS_CID_IDX on LOAN_CARD_CLAIM_FIELDS (
  CLAIM_ID ASC
)
local;

alter table LOAN_CARD_CLAIM_FIELDS
add constraint FK_LOAN_CAR_FK_CCLAIM_LOAN_CAR foreign key (CLAIM_ID)
references LOAN_CARD_CLAIMS (ID)
on delete cascade;

-- Номер ревизии: 78487
-- Номер версии: 1.18
-- Комментарий:  Вклады ЦАС НСИ. Печать договоров.
create index DEPOSITS_CONTRACT_IDX on DEPOSITS_CONTRACT (
   QDTN1 ASC,
   QDTSUB ASC
)
go

create index DEPOSITS_CT_TEMPLATEID_IDX on DEPOSITS_CONTRACT_TEMPLATE (
   TEMPLATEID ASC
)
go

create index DEPOSITS_CT_TYPE_IDX on DEPOSITS_CONTRACT_TEMPLATE (
   TYPE ASC
)
go

-- Номер ревизии: 78521
-- Номер версии: 1.18
-- Комментарий:  CHG088597 Перекодировать подразделения в интеграции СБНКД

ALTER TABLE ISSUE_CARD_CLAIM
	ADD ( CONVERTED_AGENCY_ID VARCHAR2(4) NULL, 
	CONVERTED_BRANCH_ID VARCHAR2(5) NULL,
    PERSON_REGION_ID NUMBER NULL) 
GO

-- Номер ревизии: 78613
-- Номер версии: 1.18
-- Комментарий: Справочник mnp-телефонов (модель БД)/ Временное решение загрузки mnp-справочника
create table MNP_PHONES
(
   ID                   INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(20)         not null,
   PROVIDER_CODE        VARCHAR2(20)         not null,
   MOVING_DATE          DATE                 not null,
   SOURCE_FILE_NAME     VARCHAR2(20)         not null
)
go

create sequence S_MNP_PHONES
go

create unique index MNP_PHONE_IDX on MNP_PHONES (
   PHONE_NUMBER ASC
)
go


-- Номер ревизии: 78603
-- Номер версии: 1.18
-- Комментарий: CHG088619: Промо вклад : Изменение неснижаемого остатка

alter table STORED_ACCOUNT add CLIENT_KIND number;

-- Номер ревизии: 78785
-- Номер версии: 1.18
-- Комментарий: BUG088758: [Гостевой СБОЛ] [Заявка на кредит] Ошибка при переходе ко второму шагу заявки

alter table LOAN_CLAIMS MODIFY OWNER_VSP varchar2(7);

-- Номер ревизии: 78921
-- Номер версии: 1.18
-- Комментарий: BUG086759: ЕРМБ. Код валюты при оплате своего мобильного телефона.

alter table MNP_PHONES add MNC INTEGER default 0 not null;

delete from ERMB_MOBILE_OPERATOR;
alter table ERMB_MOBILE_OPERATOR modify MNC INTEGER;

delete from DEF_CODES;
alter table DEF_CODES modify MNC INTEGER;

-- Номер ревизии: 79055
-- Номер версии: 1.18
-- Комментарий: Предодобренные предложения в гостевой сессии

-- CRM_OFFER_LOGINS
delete from CRM_OFFER_LOGINS;

alter table CRM_OFFER_LOGINS drop primary key cascade;
alter table CRM_OFFER_LOGINS drop column LOGIN_ID;

alter table CRM_OFFER_LOGINS add ID INTEGER not null;
alter table CRM_OFFER_LOGINS add SUR_NAME VARCHAR2(120) not null;
alter table CRM_OFFER_LOGINS add FIRST_NAME VARCHAR2(120) not null;
alter table CRM_OFFER_LOGINS add PATR_NAME VARCHAR2(120);
alter table CRM_OFFER_LOGINS add DOC_SERIES VARCHAR2(16) not null;
alter table CRM_OFFER_LOGINS add DOC_NUMBER VARCHAR2(16);
alter table CRM_OFFER_LOGINS add BIRTHDAY TIMESTAMP(6) not null;

create index IDX_FIO_CRM_OFFER_LOGINS on CRM_OFFER_LOGINS (
   SUR_NAME ASC,
   FIRST_NAME ASC,
   PATR_NAME ASC,
   BIRTHDAY ASC,
   DOC_SERIES ASC,
   DOC_NUMBER ASC
);

alter table CRM_OFFER_LOGINS add constraint PK_CRM_OFFER_LOGINS primary key (ID);
create sequence S_CRM_OFFER_LOGINS;


-- CRM_OFFERS
delete from CRM_OFFERS;

alter table CRM_OFFERS drop column LOGIN_ID;
alter table CRM_OFFERS add SUR_NAME VARCHAR2(120) not null;
alter table CRM_OFFERS add FIRST_NAME VARCHAR2(120) not null;
alter table CRM_OFFERS add PATR_NAME VARCHAR2(120);
alter table CRM_OFFERS add DOC_SERIES VARCHAR2(16) not null;
alter table CRM_OFFERS add DOC_NUMBER VARCHAR2(16);
alter table CRM_OFFERS add BIRTHDAY TIMESTAMP(6) not null;

create index IDX_FIO_CRM_OFFERS on CRM_OFFERS (
   SUR_NAME ASC,
   FIRST_NAME ASC,
   PATR_NAME ASC,
   BIRTHDAY ASC,
   DOC_SERIES ASC,
   DOC_NUMBER ASC
);

-- CRM_OFFER_FEEDBACKS
delete from CRM_OFFER_FEEDBACKS;

alter table CRM_OFFER_FEEDBACKS drop primary key cascade;
alter table CRM_OFFER_FEEDBACKS drop column LOGIN_ID;

alter table CRM_OFFER_FEEDBACKS add ID INTEGER not null;
alter table CRM_OFFER_FEEDBACKS add SUR_NAME VARCHAR2(120) not null;
alter table CRM_OFFER_FEEDBACKS add FIRST_NAME VARCHAR2(120) not null;
alter table CRM_OFFER_FEEDBACKS add PATR_NAME VARCHAR2(120);
alter table CRM_OFFER_FEEDBACKS add DOC_SERIES VARCHAR2(16) not null;
alter table CRM_OFFER_FEEDBACKS add DOC_NUMBER VARCHAR2(16);
alter table CRM_OFFER_FEEDBACKS add BIRTHDAY TIMESTAMP(6) not null;

create index IDX_FIO_CRM_OFFER_FEEDBACKS on CRM_OFFER_FEEDBACKS (
   SUR_NAME ASC,
   FIRST_NAME ASC,
   PATR_NAME ASC,
   BIRTHDAY ASC,
   DOC_SERIES ASC,
   DOC_NUMBER ASC
);

alter table CRM_OFFER_FEEDBACKS add constraint PK_CRM_OFFER_FEEDBACKS primary key (ID);
create sequence S_CRM_OFFER_FEEDBACKS;


-- Номер ревизии: 79174
-- Номер версии: 1.18
-- Комментарий: Предодобренные предложения в гостевой сессии - создание новых индексов

drop index IDX_FIO_CRM_OFFERS;

create index IDX_FIO_CRM_OFFERS on CRM_OFFERS (
    UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
    UPPER(REPLACE(doc_series, ' ', '')||REPLACE(doc_number, ' ', '')) ASC,
    BIRTHDAY ASC,
    PRODUCT_TYPE ASC,
    EXP_DATE ASC,
    IS_OFFER_USED ASC
);

drop index IDX_FIO_CRM_OFFER_LOGINS;

create index IDX_FIO_CRM_OFFER_LOGINS on CRM_OFFER_LOGINS (
    UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
    UPPER(REPLACE(doc_series, ' ', '')||REPLACE(doc_number, ' ', '')) ASC,
    BIRTHDAY ASC
);

drop index IDX_FIO_CRM_OFFER_FEEDBACKS;

create index IDX_FIO_CRM_OFFER_FEEDBACKS on CRM_OFFER_FEEDBACKS (
    UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
    UPPER(REPLACE(doc_series, ' ', '')||REPLACE(doc_number, ' ', '')) ASC,
    BIRTHDAY ASC,
	SOURCE_CODE ASC,
	CAMPAIGN_MEMBER_ID ASC
);

create index DXFK_CRM_OFCOND_TO_OFFER on CRM_OFFER_CONDITIONS (
    OFFER_ID ASC
);

-- Номер ревизии: 79303
-- Номер версии: 1.18
-- Комментарий: BUG088861: МИГРАЦИЯ из АС ФИЛИАЛ. Некорректно обрабатывается ситуация с конфликтным номером. 

alter table ERMB_PROFILE add CONFLICT_MIGRATION CHAR(1) default '0' 
go

-- Номер ревизии: 79348
-- Номер версии: 1.18
-- Комментарий: Вклады ЦАС НСИ. Зависимость вклада от ТП

create index IDX_DEPOSITS_QVB_TP on DEPOSITS_QVB_TP (TP_QDTN1 ASC);


-- Номер ревизии: 79469
-- Номер версии: 1.18
-- Комментарий: BUG080895: [Многоязычность] Добавить кнопку "Заполнить язык"

alter table ERIB_LOCALES add ACTUAL_DATE timestamp
go

update ERIB_LOCALES set ACTUAL_DATE = sysdate
go

alter table ERIB_LOCALES modify ACTUAL_DATE not null
go

-- Номер ревизии: 79469
-- Номер версии: 1.18
-- Комментарий: ENH088230: [ФОС] Получение черновика письма по parentId(MAIL.PARENT_ID) 

create index MAIL_PARENT_IDX on MAIL(PARENT_ID)
go

create index MAIL_DATE_IDX on MAIL(CREATION_DATE)
go

-- Номер ревизии: 79567
-- Номер версии: 1.18
-- Комментарий: Кредитная заявка. Некорретно сохраняется поле заявки

ALTER TABLE LOAN_CARD_CLAIMS MODIFY(OWNER_LOGIN_TYPE VARCHAR2(10 BYTE));


-- Номер ревизии: 79748
-- Номер версии: 1.18
-- Комментарий: BUG085977: Гостевой СБОЛ. Не открываются страницы просмотра заявок СБНКД.

alter table LOAN_CARD_CLAIMS add CREDIT_CARD VARCHAR2(100);
alter table LOAN_CARD_CLAIMS add CURRENCY CHAR(3);

-- Номер ревизии: 80102
-- Номер версии: 1.18
-- Комментарий: Заявка на кредитную карту. Справочник по кредитным карточным продуктам.

ALTER TABLE CREDIT_CARD_PRODUCTS ADD COMMON_LEAD CHAR(1) DEFAULT '0' NOT NULL;
ALTER TABLE CREDIT_CARD_PRODUCTS ADD GUEST_LEAD CHAR(1) DEFAULT '0' NOT NULL;
ALTER TABLE CREDIT_CARD_PRODUCTS ADD GUEST_PREAPPROVED CHAR(1) DEFAULT '0' NOT NULL;

-- Номер ревизии: 80143
-- Номер версии: 1.18
-- Комментарий: Доработка выгрузки заявок по кредитным картам

create index LOAN_CCLAIMS_STED_IDX on LOAN_CARD_CLAIMS (
   STATE_CODE ASC,
   EXECUTION_DATE ASC
)
go



-- Номер ревизии: 80275
-- Номер версии: 1.18
-- Комментарий: BUG080895: [Многоязычность] Добавить кнопку "Заполнить язык"(Ч7 Карты – Карточные продукты(mapi).)
CREATE TABLE CARD_PRODUCTS_RES  ( 
    ID          NUMBER NOT NULL,
    LOCALE_ID   VARCHAR2(30) NOT NULL,
    NAME        VARCHAR2(255) NULL,
    CONSTRAINT PK_CARD_PRODUCTS_RES PRIMARY KEY(ID,LOCALE_ID)   
)
GO

-- Номер ревизии: 80387
-- Номер версии: 1.18
-- Комментарий: BUG089689: [СБНКД] в запросе IssueCardRq не хватает BankInfo
alter table ISSUE_CARD_CLAIM add
(
    CONVERTED_REGION_ID VARCHAR2(3) NULL, 
    CONV_PERSON_TB VARCHAR2(3) NULL, 
    CONV_PERSON_OSB VARCHAR2(4) NULL, 
    CONV_PERSON_VSP VARCHAR2(5) NULL
)
go


-- Номер ревизии: 80566
-- Номер версии: 1.18
-- Комментарий: BUG080895: [Многоязычность] Добавить кнопку "Заполнить язык"(Ч8 Карты – Кредитные карточные продукты(mapi).)

CREATE TABLE CREDIT_CARD_PRODUCTS_RES  ( 
    ID       	NUMBER NOT NULL,
    LOCALE_ID	VARCHAR2(30) NOT NULL,
    NAME     	VARCHAR2(255) NULL,
    ADDITIONAL_TERMS       	VARCHAR2(500) NULL,
    CONSTRAINT PK_CREDIT_CARD_PRODUCTS_RES PRIMARY KEY(ID,LOCALE_ID)
)
GO
ALTER TABLE CREDIT_CARD_PRODUCTS_RES
    ADD ( CONSTRAINT U_CREDIT_CARD_PROD_NAME_RES
	UNIQUE (NAME))
GO
alter table credit_card_conditions modify PRODUCT_ID null
GO

-- Номер ревизии: 80640
-- Номер версии: 1.18
-- Комментарий: CHG086317: СБНКД. Внесение правок по обновленным макетам
alter table ISSUE_CARD_CLAIM add
(
    ALL_CARD_NAMES  VARCHAR2(512) NULL
)
go


-- Номер ревизии: 80675
-- Номер версии: 1.18
-- Комментарий: BUG080895: [Многоязычность] Добавить кнопку "Заполнить язык"

CREATE TABLE MONITORING_STATE_CONFIGS_RES  ( 
    ID                            	NUMBER NOT NULL,
    LOCALE_ID                       VARCHAR2(30) NOT NULL,  
    MESSAGE                       	VARCHAR2(500) NULL,    
    CONSTRAINT PK_MONITORING_ST_CONF_RES PRIMARY KEY(ID, LOCALE_ID)	
)
GO


-- Номер ревизии: 80721  

-- Номер версии: 1.18
-- Комментарий: BUG089755: Многоязычность : локализация справочников в МАПИ

alter table SERVICE_PROVIDERS_RES add NAME_SERVICE VARCHAR2(150)
go


-- Номер ревизии: 80792  
-- Номер версии: 1.18
-- Комментарий: BUG088880: Гостевой СБОЛ. СБНКД. Значение "Пополнять на сумму" не валидируется в соответствии с диапазоном. 

alter table issue_card_claim modify 
(
    card_auto_pay_info number (10,4) 
)
go

-- Номер ревизии: 80859  
-- Номер версии: 1.18
-- Комментарий: BUG089831: Интеграция СБНКД. В запросе CustAddRq поля не соответствуют спеке с шиной.
alter table ISSUE_CARD_CLAIM add CONVERTED_RBTBBRANCH_ID  varchar2(8)
go
alter table ISSUE_CARD_CLAIM drop  column CONV_PERSON_TB
go
alter table ISSUE_CARD_CLAIM drop  column CONV_PERSON_OSB
go
alter table ISSUE_CARD_CLAIM drop  column CONV_PERSON_VSP
go

-- Номер ревизии: 80979
-- Номер версии: 1.18
-- Комментарий: CHG084412: СБНКД. Изменение перечня загружаемых из ЦАС НСИ справочников мобильных операторов 

create table NUMBER_ARRAYS( 
	ID             	  NUMBER NOT NULL,
    NUMBERFROM        NUMBER NOT NULL,
    NUMBERTO          NUMBER NOT NULL,
    OWNERID           VARCHAR2(30) NOT NULL,
    ORGNAME           VARCHAR2(100) NOT NULL,
    MNC               VARCHAR2(2),
    REGIONCODE        VARCHAR2(2) NOT NULL,
    SERVICE_ID        VARCHAR2(8) NOT NULL,
    SERVICE_CODE      VARCHAR2(8) NOT NULL,
    CASH_SERVICE_ID   VARCHAR2(8),
    MBOPERATOR_ID     VARCHAR2(1) NOT NULL,
    MBOPERATOR_NUMBER VARCHAR2(8) NOT NULL,
    PROVIDER_ID       VARCHAR2(8),

    CONSTRAINT PK_NUMBER_ARRAYS PRIMARY KEY (ID)
)
go

create sequence S_NUMBER_ARRAYS
go

create table CELL_OPERATORS( 
	ID                  NUMBER NOT NULL,    
	ORGNAME             VARCHAR2(100) NOT NULL,
    ORGCODE             VARCHAR2(30) NOT NULL,
    MNC                 VARCHAR2(2) NOT NULL,
    TIN                 VARCHAR2(10),
    MBOPERATOR_NUMBER   NUMBER,
    FL_AUTO VARCHAR2(1),
    BALANCE NUMBER(6),
    MIN_SUMM NUMBER(10),
    MAX_SUMM NUMBER(10),
    
    CONSTRAINT PK_CELL_OPERATORS PRIMARY KEY (ID)
)
go

create sequence S_CELL_OPERATORS
go

create index IDX_NUMBER_ARRAYS_RANGE on NUMBER_ARRAYS (
    NUMBERFROM DESC,
    NUMBERTO ASC,
    ORGNAME,
    MNC    
)
go

create index IDX_CELL_OPERATORS_OM on CELL_OPERATORS (    
    ORGNAME,
    MNC    
)
go


-- Номер ревизии: 81077
-- Номер версии: 1.18
-- Комментарий: CHG084412: СБНКД. Изменение перечня загружаемых из ЦАС НСИ справочников мобильных операторов (изменение сущности заявки)
alter table ISSUE_CARD_CLAIM add (
    OPER_AUTOPAYMENT_AVAILABLE CHAR(1),
    MIN_AUTOPAYMENT_SUM_AVAILABLE NUMBER,
    MAX_AUTOPAYMENT_SUM_AVAILABLE NUMBER
) 
go


-- Номер ревизии: 81290
-- Номер версии: 1.18
-- Комментарий: BUG090067: [Многоязычность] Добавить кнопку "Заполнить язык"

create table TARIF_PLAN_CONFIGS_RES 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   PRIVILEGED_RATE_MESSAGE VARCHAR2(2000),
   constraint PK_TARIF_PLAN_CONFIGS_RES primary key (ID, LOCALE_ID)
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


-- Номер ревизии: 81452
-- Номер версии: 1.18
-- Комментарий: BUG089823: Многоязычность : Локализация справочников  (справочник валют)

create table CURRENCIES_RES (
    ID VARCHAR2(3) NOT NULL,
    LOCALE_ID VARCHAR2(30) NOT NULL,
    NAME VARCHAR2(250) NOT NULL,
    constraint PK_CURRENCIES_RES primary key (ID, LOCALE_ID)
)
go

alter table CURRENCIES_RES
   add constraint FK_CURRENCIES_RES foreign key (ID)
      references CURRENCIES (ID)
on delete cascade
go


-- Номер ревизии: 81624
-- Номер версии: 1.18
-- Комментарий: BUG090126  [ЕРМБ, справочник MNP] ошибка при репликации справочника (бд)

ALTER TABLE MNP_PHONES MODIFY SOURCE_FILE_NAME VARCHAR2(255);


-- Номер ревизии: 81661
-- Номер версии: v1.18
-- Комментарий: BUG089823: Многоязычность : Локализация справочников (ОМС)

create table IMAPRODUCT_RES (
    ID INTEGER NOT NULL,
    LOCALE_ID VARCHAR2(30) NOT NULL,
    NAME VARCHAR2(255) NOT NULL,
    constraint PK_IMAPRODUCT_RES primary key (ID, LOCALE_ID)
)
go


-- Номер ревизии: 81759
-- Номер версии: v1.18
-- Комментарий: BUG089851: [Гостевой СБОЛ][Заказ кредитной карты] Некорректно работает проверка на наличие у клиента заявок на кредитную карту
create index LOAN_CCLAIMS_GP on LOAN_CARD_CLAIMS (
   OWNER_GUEST_PHONE ASC
)
local
go

-- Номер ревизии: 81773
-- Номер версии: v1.18
-- Комментарий: BUG090419: [СБНКД] Увеличить длину поля EDBO_TB в БД 
alter table ISSUE_CARD_CLAIM modify (EDBO_TB VARCHAR2(5))
go

-- Номер ревизии: 81784
-- Номер версии: v1.18
-- Комментарий: Шаблон кредитной оферты. Доработка АРМ сотрудника. Модель БД (CSA Admin и ЕРИБ)
CREATE TABLE CREDIT_OFFER_TEMPLATE (
  ID         INTEGER      NOT NULL ,
  DATE_FROM  TIMESTAMP    NOT NULL ,
  DATE_TO    TIMESTAMP,
  OFFER      CLOB         NOT NULL ,
  UUID       VARCHAR2(32) NOT NULL
);

CREATE SEQUENCE S_CREDIT_OFFER_TEMPLATE;

CREATE UNIQUE INDEX IDX_CREDIT_OFFER_TEMPLATE_UUID ON CREDIT_OFFER_TEMPLATE (
   UUID ASC
);

-- Номер ревизии: 81839
-- Номер версии: v.1.18_release_16.1
-- Комментарий: CHG086407: [ISUP] Необходимо партиционировать таблицу IMSI_ERROR_FOR_LOGIN
drop table IMSI_ERROR_FOR_LOGIN 
go 

create table IMSI_ERROR_FOR_LOGIN 
(
   ID                   integer              not null,
   OPERATION_DATE       date                 not null,
   LOGIN_ID             integer              not null,
   GOOD_IMSI            char(1)              not null
)
partition by range
 (OPERATION_DATE)
    interval (NUMTOYMINTERVAL(1, 'MONTH'))
 (partition
         P_START
        values less than (to_date('01-01-2015','DD-MM-YYYY')))
go

create index IDX_INSI_LOGIN_LOGINID_DATE on IMSI_ERROR_FOR_LOGIN (
   LOGIN_ID ASC,
   OPERATION_DATE DESC
)
local
go

-- Номер ревизии: 81850
-- Номер версии: v1.18
-- Комментарий: BUG090447  МИГРАЦИЯ ИЗ АС ФИЛИАЛ. Некорректно мигрится клиент при финансовой блокировке. (бд)
ALTER TABLE ERMB_PROFILE ADD MBK_PAYMENT_BLOCKED CHAR(1) DEFAULT '0' NOT NULL;

-- Номер ревизии: 81904
-- Номер версии: v1.18
-- Комментарий: Управление кредитными лимитами (бд)
alter table CRM_OFFER_FEEDBACKS add (
    FEEDBACK_TIME TIMESTAMP,
    CHANNEL  VARCHAR2(20),
    TYPE VARCHAR2(20)
)
go

update CRM_OFFER_FEEDBACKS set FEEDBACK_TIME = INFORM_TIME, CHANNEL = INFORM_CHANNEL,TYPE = 'INFORM' where INFORM_CHANNEL is not null 
go
insert into CRM_OFFER_FEEDBACKS (SOURCE_CODE, CAMPAIGN_MEMBER_ID, OFFER_END_DATE, ID, SUR_NAME, FIRST_NAME, PATR_NAME, DOC_SERIES, DOC_NUMBER, BIRTHDAY, CHANNEL, TYPE, FEEDBACK_TIME)
(select SOURCE_CODE, CAMPAIGN_MEMBER_ID, OFFER_END_DATE, S_CRM_OFFER_FEEDBACKS.nextval, SUR_NAME, FIRST_NAME, PATR_NAME, DOC_SERIES, DOC_NUMBER, BIRTHDAY, PRESENT_CHANNEL, 'PRESENT', PRESENT_TIME from CRM_OFFER_FEEDBACKS where PRESENT_CHANNEL is not null)
go

alter table CRM_OFFER_FEEDBACKS drop column INFORM_TIME
go
alter table CRM_OFFER_FEEDBACKS drop column INFORM_CHANNEL
go
alter table CRM_OFFER_FEEDBACKS drop column PRESENT_TIME
go
alter table CRM_OFFER_FEEDBACKS drop column PRESENT_CHANNEL
go


-- Номер ревизии: 81964
-- Номер версии: v1.18
-- Комментарий:  ENH090023: [Многоязычность] Справочник вкладов (часть 1: локализованное отображение списка вкладов для открытия)

create table DEPOSITS_QVB_RES (
    ID INTEGER NOT NULL,
    LOCALE_ID VARCHAR2(30) NOT NULL,
    QDN VARCHAR2(80) NOT NULL,
    QSNAME VARCHAR2(80),
    constraint PK_DEPOSITS_QVB_RES primary key (ID, LOCALE_ID)
)
go

alter table DEPOSITS_QVB_RES
   add constraint FK_DEPOSITS_QVB_RES foreign key (ID)
      references DEPOSITS_QVB (ID)
on delete cascade
go

create table DEPOSITS_QVB_GROUP_RES (
    ID INTEGER NOT NULL,
    LOCALE_ID VARCHAR2(30) NOT NULL,
    GR_NAME  VARCHAR2(250) NOT NULL,
    constraint PK_DEPOSITS_QVB_GROUP_RES primary key (ID, LOCALE_ID)
)
go

alter table DEPOSITS_QVB_GROUP_RES
   add constraint FK_DEPOSITS_QVB_GROUP_RES foreign key (ID)
      references DEPOSITS_QVB_GROUP (ID)
on delete cascade
go


-- Номер ревизии: 81997
-- Номер версии: v1.18
-- Комментарий: Кредитные заявки. Расширение поля "vsp" до 5 символов.
alter table LOAN_CLAIMS modify VSP varchar2(5);

-- Номер ревизии: 82081
-- Номер версии: v1.18
-- Комментарий: BUG090037  Розничный CRM : Ошибка получения предложений для клиента с несколькими профилями (бд)
DELETE FROM CRM_OFFER_LOGINS;

DROP INDEX IDX_FIO_CRM_OFFER_LOGINS;
ALTER TABLE CRM_OFFER_LOGINS ADD TB VARCHAR2(4) NOT NULL;

CREATE INDEX IDX_FIO_CRM_OFFER_LOGINS
   ON CRM_OFFER_LOGINS (
      UPPER (
         TRIM (
            REGEXP_REPLACE (
               SUR_NAME || ' ' || FIRST_NAME || ' ' || PATR_NAME,
               '( )+',
               ' '))) ASC,
      UPPER (REPLACE (DOC_SERIES, ' ', '') || REPLACE (DOC_NUMBER, ' ', '')) ASC,
      BIRTHDAY ASC,
      TB ASC);

	  
-- Номер ревизии: 82143
-- Номер версии: v1.18
-- Комментарий: Изменить тип поля справочника FIELD_TDOG для загрузки вкладов

ALTER TABLE DEPOSITS_FIELD_TDOG DROP COLUMN ORD_DOXOD;
ALTER TABLE DEPOSITS_FIELD_TDOG ADD (ORD_DOXOD VARCHAR2(4000));	

-- Номер ревизии: 82152
-- Номер версии: v1.18
-- Комментарий: BUG090201: [СБНКД] не доступно подключение АП на форме в заявке   

drop index IDX_NUMBER_ARRAYS_RANGE
go

drop index IDX_CELL_OPERATORS_OM
go

create index IDX_NUMBER_ARRAYS_RANGE on NUMBER_ARRAYS (
    NUMBERFROM DESC,
    NUMBERTO ASC,
    OWNERID,
    MNC    
)
go

create index IDX_CELL_OPERATORS_OM on CELL_OPERATORS (    
    ORGCODE,
    MNC    
)
go

-- Номер ревизии: 82174
-- Номер версии: v1.18
-- Комментарий: Реализация асинхронного календаря
create index I_TB_CALENDARS on CALENDARS (
   TB ASC
)
go

-- Номер ревизии: 82208
-- Номер версии: v1.18
-- Комментарий: BUG090657  [ЕРМБ] Отправка уведомлений для кредита должна быть выключена 
UPDATE LOAN_LINKS
   SET ERMB_NOTIFICATION = '0'
go

-- Номер ревизии: 82267
-- Номер версии: v1.18
-- Комментарий: ENH090023: [Многоязычность] Справочник вкладов (часть 2: просмотр детальной информации по вкладу)

create table DEPOSITS_FIELD_TDOG_RES (
    ID           INTEGER      NOT NULL,
    LOCALE_ID    VARCHAR2(30) NOT NULL,
    SUM_VKL      VARCHAR2(80 BYTE),
    CURR_VKL     VARCHAR2(80 BYTE),
    QSROK        VARCHAR2(80 BYTE),
    DATA_END     VARCHAR2(80 BYTE),
    PERCENT      VARCHAR2(3000 BYTE),
    PRIXOD       VARCHAR2(1000 BYTE),
    MIN_ADD      VARCHAR2(1000 BYTE),
    PER_ADD      VARCHAR2(1000 BYTE),
    RASXOD       VARCHAR2(1000 BYTE),
    SUM_NOST     VARCHAR2(1000 BYTE),
    PER_PERCENT  VARCHAR2(3000 BYTE),
    ORD_PERCENT  VARCHAR2(3000 BYTE),
    ORD_DOXOD    VARCHAR2(4000 BYTE),
    QPROL        VARCHAR2(1000 BYTE),
    constraint PK_DEPOSITS_FIELD_TDOG_RES primary key (ID, LOCALE_ID)
)
go

alter table DEPOSITS_FIELD_TDOG_RES
   add constraint FK_DEPOSITS_FIELD_TDOG_RES foreign key (ID)
      references DEPOSITS_FIELD_TDOG (ID)
on delete cascade
go

-- Номер ревизии: 82282
-- Номер версии: v1.18
-- Комментарий: BUG085389: [Удаленное подключение УДБО] Доработать настройки в АРм сотрудника для подключения УДБО
/*==============================================================*/
/* Table: UDBO_CLAIM_RULES                                      */
/*==============================================================*/
create table UDBO_CLAIM_RULES 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   START_DATE           TIMESTAMP            not null,
   RULES_TEXT           CLOB                 not null,
   constraint PK_UDBO_CLAIM_RULES primary key (ID)
)

go

create sequence S_UDBO_CLAIM_RULES
go

/*==============================================================*/
/* Index: UDBO_CL_RU_START_DATE_IDX                             */
/*==============================================================*/
create index UDBO_CL_RU_START_DATE_IDX on UDBO_CLAIM_RULES (
   START_DATE ASC
)
go

-- Номер ревизии: 82353
-- Номер версии: v1.18
-- Комментарий: BUG090680  Не оформить заявку на кредитную карту. 
ALTER TABLE LOAN_CARD_CLAIMS MODIFY OWNER_VSP VARCHAR2(7);

-- Номер ревизии: 82387
-- Номер версии: v1.18
-- Комментарий: BUG090680  Не оформить заявку на кредитную карту. 
ALTER TABLE LOAN_CARD_CLAIMS MODIFY VSP VARCHAR2(7);

-- Номер ревизии: 82482
-- Номер версии: v1.18
-- Комментарий: Корзин платежей. Объекты учета
ALTER TABLE ACCOUNTING_ENTITY DROP 
(
   CAR_STATE_NUMBER, 
   CAR_CERTIFICATE_NUMBER,
   CAR_CERTIFICATE_BATCH,
   CAR_CERTIFICATE_ISSUED_SOURCE,
   CAR_CERTIFICATE_ISSUED_DATE,
   POSTALCODE,
   PROVINCE,
   DISTRICT,
   CITY,
   STREET,
   HOUSE,
   BUILDING,
   FLAT,
   ADDRESS_FULL
)
go

-- Номер ревизии: 82723
-- Номер версии: v1.18
-- Комментарий: [Фрод-мониторинг] Доработка взаимодействия с ActivityEngine   
   
CREATE TABLE FRAUD_NOTIFICATIONS  ( 
    "ID"            INTEGER NOT NULL,
    "CREATION_DATE" TIMESTAMP NOT NULL,
    "REQUEST_BODY"  CLOB NOT NULL,
    "WAS_SENT"      CHAR NOT NULL,
    CONSTRAINT "PK_CONSTR" PRIMARY KEY("ID")
    
)
partition by range
(CREATION_DATE) interval (NUMTODSINTERVAL(2,'DAY'))
(partition P_START values less than (to_date('03-07-2015','DD-MM-YYYY')))
go


-- Номер ревизии: 82777
-- Номер версии: 19 релиз.
-- Комментарий: merge 1.18: Асинхронная запись журнала регистации входов(ч9. Модель БД. + тип и ДУЛ; - STATE и индекс на сессию)
alter table INPUT_REGISTER_JOURNAL DROP (STATE)
go
alter table INPUT_REGISTER_JOURNAL ADD TYPE VARCHAR2(16)
go
alter table INPUT_REGISTER_JOURNAL ADD DOC_SERIES VARCHAR2(16)
go
alter table INPUT_REGISTER_JOURNAL ADD DOC_NUMBER VARCHAR2(16)
go
drop index INDEX_REGISTER_JOURNAL_SESSION
go
create index RL_DUL_DATE_INDEX on INPUT_REGISTER_JOURNAL (
   UPPER(REPLACE(doc_series, ' ', '')||REPLACE(doc_number, ' ', '')) ASC,
   LOGIN_DATE ASC
)
local
go

-- Номер ревизии: 83294 
-- Номер версии: v1.18
-- Комментарий: BUG091107: АРМ сотрудника, невозможно добавить привязку параметров документа и поставщика услуг 

/*==============================================================*/
/* Table: FIELD_FORMULAS                                        */
/*==============================================================*/
create table FIELD_FORMULAS 
(
   ID                   INTEGER              not null,
   IDENT_TYPE_ID        INTEGER              not null,
   PROVIDER_ID          INTEGER              not null,
   EXTERNAL_ID          VARCHAR2(40)         not null,
   constraint PK_FIELD_FORMULAS primary key (ID)
)

go

create sequence S_FIELD_FORMULAS
go

/*==============================================================*/
/* Index: I_FIELD_FORMULAS                                      */
/*==============================================================*/
create unique index I_FIELD_FORMULAS on FIELD_FORMULAS (
   IDENT_TYPE_ID ASC,
   PROVIDER_ID ASC,
   EXTERNAL_ID ASC
)
go

/*==============================================================*/
/* Table: FIELD_FORMULA_ATTRIBUTES                              */
/*==============================================================*/
create table FIELD_FORMULA_ATTRIBUTES 
(
   ID                   INTEGER              not null,
   FORMULA_ID           INTEGER              not null,
   SERIAL               INTEGER              not null,
   VALUE                VARCHAR2(256),
   ATTRIBUTE_SYSTEM_ID  VARCHAR2(20),
   LAST                 CHAR(1)              not null
)

go

create sequence S_FIELD_FORMULA_ATTRIBUTES
go

/*==============================================================*/
/* Index: I_FK_FORMULA_ATTRIBUTES                               */
/*==============================================================*/
create index I_FK_FORMULA_ATTRIBUTES on FIELD_FORMULA_ATTRIBUTES (
   FORMULA_ID ASC
)
go

alter table FIELD_FORMULAS
   add constraint FK_FIELD_FO_FK_FORMUL_IDENT_TY foreign key (IDENT_TYPE_ID)
      references IDENT_TYPE_FOR_DEPS (ID)
      on delete cascade
go


create index "DXFK_FORMULA_TO_PROVIDERS" on FIELD_FORMULAS
(
   PROVIDER_ID
)
go

alter table FIELD_FORMULAS
   add constraint FK_FIELD_FO_FK_FORMUL_SERVICE_ foreign key (PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go

alter table FIELD_FORMULA_ATTRIBUTES
   add constraint FK_FIELD_FO_FK_FORMUL_FIELD_FO foreign key (FORMULA_ID)
      references FIELD_FORMULAS (ID)
      on delete cascade
go

-- Номер ревизии: 83387 
-- Номер версии: 19.0
-- Комментарий: Корзина платежей. Конвертация автоплатежа в автопоиск и обратно.(ч2)

alter table INVOICE_SUBSCRIPTIONS add AUTOSUB_EXTERNAL_ID varchar2(100)
go

create index "IDX_INV_SUB_TO_AUTOSUB_EXTID" on INVOICE_SUBSCRIPTIONS
(
   AUTOSUB_EXTERNAL_ID
)
go

-- Номер ревизии: 83514 
-- Номер версии: 19.0
-- Комментарий: Top-Up. Доработка отображения кредитной заявки на шаге выбора условий кредита
create table CRM_OFFER_TOPUP
(
   ID                   INTEGER              not null,
   BLOCK_COUNT          INTEGER              not null,
   ID_SOURCE            VARCHAR2(10)         not null,
   ID_CONTRACT          VARCHAR2(22)         not null,
   AGREEMENT_NUMBER     VARCHAR2(30)         not null,
   LOAN_ACCOUNT_NUMBER  VARCHAR2(28)         not null,
   START_DATE           TIMESTAMP            not null,
   MATURITY_DATE        TIMESTAMP            not null,
   TOTAL_AMOUNT         NUMBER(17,2)         not null,
   CURRENCY             VARCHAR2(10)         not null,
   TOTAL_REPAYMENT_SUM  NUMBER(17,2),
   OFFER_ID             INTEGER              not null,
   constraint PK_CRM_OFFER_TOPUP primary key (ID)
);

create sequence S_CRM_OFFER_TOPUP;

create index "DXFK_TOP_UP_OFFER_ID" on CRM_OFFER_TOPUP
(
   OFFER_ID
);

alter table CRM_OFFER_TOPUP
   add constraint FK_TOP_UP_OFFER_ID foreign key (OFFER_ID)
      references CRM_OFFERS (ID)
      on delete cascade;
	  
-- Номер ревизии: 81832 
-- Номер версии: v1.18
-- Комментарий: Создание таблиц предварительного хранения оферт

CREATE TABLE OFFER_ERIB_PRIOR
(
  ID                     INTEGER                NOT NULL,
  RQ_UID                 VARCHAR2(32 BYTE),
  OFFER_DATE             TIMESTAMP(6),
  APPLICATION_NUMBER     VARCHAR2(20 BYTE),
  CLAIM_ID               INTEGER,
  CLIENT_LOGIN_ID        INTEGER,
  CLIENT_CATEGORY        VARCHAR2(10 BYTE),
  ALT_PERIOD             INTEGER,
  ALT_AMOUNT             NUMBER(17,2),
  ALT_INTEREST_RATE      NUMBER(6,2),
  ALT_FULL_LOAN_COST     NUMBER(16,2),
  ALT_ANNUITY_PAYMENT    NUMBER(3,3),
  ALT_CREDIT_CARD_LIMIT  NUMBER(17,2),
  STATE                  VARCHAR2(10 BYTE),
  VISIBILITY_COUNTER     INTEGER DEFAULT 0 NOT NULL
)
partition by range
(OFFER_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
(partition
         P_FIRST_OEP
        values less than (to_date('15-06-2015','DD-MM-YYYY')))
;
ALTER TABLE OFFER_ERIB_PRIOR ADD (CONSTRAINT OFFER_ERIB_PRIOR_PK PRIMARY KEY (ID));
CREATE SEQUENCE S_OFFER_ERIB_PRIOR;
CREATE INDEX IDX_OFFER_ERIB_PRIOR_CLIENT ON OFFER_ERIB_PRIOR (CLIENT_LOGIN_ID, OFFER_DATE) LOCAL;

CREATE TABLE OFFER_CONFIRMED
(
  ID                     INTEGER                NOT NULL,
  OFFER_DATE             TIMESTAMP(6),
  APPLICATION_NUMBER     VARCHAR2(20 BYTE),
  CLAIM_ID               INTEGER,
  CLIENT_LOGIN_ID        INTEGER,
  TEMPLATE_ID            INTEGER,
  FIRST_NAME             VARCHAR2(160 BYTE),
  LAST_NAME              VARCHAR2(160 BYTE),
  MIDDLE_NAME            VARCHAR2(160 BYTE),
  ID_TYPE                VARCHAR2(4 BYTE),
  ID_SERIES              VARCHAR2(12 BYTE),
  ID_NUM                 VARCHAR2(12 BYTE),
  ISSUED_BY              VARCHAR2(255 BYTE),
  ISSUE_DT               TIMESTAMP(6),
  ALT_PERIOD             INTEGER,
  ALT_AMOUNT             NUMBER(17,2),
  ALT_INTEREST_RATE      NUMBER(6,2),
  ALT_FULL_LOAN_COST     NUMBER(16,2),
  ALT_ANNUITY_PAYMENT    NUMBER(3,3),
  ALT_CREDIT_CARD_LIMIT  NUMBER(17,2),
  ACCOUNT_NUMBER         VARCHAR2(25 BYTE),
  BORROWER               VARCHAR2(160 BYTE),
  REGISTRATION_ADDRESS   VARCHAR2(500 BYTE)
)
partition by range
(OFFER_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
(partition
         P_FIRST_OC
        values less than (to_date('15-06-2015','DD-MM-YYYY')))
; 
 
ALTER TABLE OFFER_CONFIRMED ADD (CONSTRAINT OFFER_CONFIRMED_PK PRIMARY KEY (ID));
create sequence S_OFFER_CONFIRMED;

-- Номер ревизии: 83575 
-- Номер версии: v1.18
-- Комментарий:  Досылка сообщений во фрод-мониторинг. Отправка сообщений из CSA_BACK через JMS.

DROP TABLE FRAUD_NOTIFICATIONS
go
create table FRAUD_NOTIFICATIONS (
  "ID"            integer    not null,
  "CREATION_DATE" timestamp  not null,
  "REQUEST_BODY"  clob       not null,
  "WAS_SENT"      char       not null
)
partition by range (CREATION_DATE) interval (NUMTODSINTERVAL(7,'DAY')) subpartition by hash(ID) subpartitions 64 (
  partition P_START values less than (to_date('05-07-2015','DD-MM-YYYY')))
go
CREATE INDEX IDX_FN_ID_STATE_DATE ON FRAUD_NOTIFICATIONS ( "CREATION_DATE", "STATE", "ID"
) local

-- Номер ревизии: 83779 
-- Номер версии: v1.18
-- Комментарий:  Оферты. Печать оферты. Ч.2 + выгрузка в PDF
create unique index APPLICATION_NUMBER_IDX on OFFER_CONFIRMED (
   APPLICATION_NUMBER ASC
)
global partition by hash (APPLICATION_NUMBER) partitions 64;
	  
	  
-- Номер ревизии: 83816 
-- Номер версии: v1.18
-- Комментарий:  Гостевой журнал подтверждений операций

create table GUEST_OPERATION_CONFIRM_LOG 
(
   ID                   INTEGER              not null,
   STATE                VARCHAR2(16)         not null,
   SESSION_ID           VARCHAR2(64)         not null,
   OPERATION_UID        VARCHAR2(32)         not null,
   RECIPIENT            VARCHAR2(256),
   MESSAGE              CLOB,
   CONFIRM_CODE         VARCHAR2(32),
   LOG_DATE             TIMESTAMP            not null
)
partition by range
 (LOG_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-06-2015','DD-MM-YYYY')))
go

create sequence S_GUEST_OPERATION_CONFIRM_LOG cache 10000
go

create index I_G_OPERATION_CONFIRM_UID on GUEST_OPERATION_CONFIRM_LOG (
   OPERATION_UID ASC
)
global partition by hash
 (OPERATION_UID)
 partitions 128
go

alter table ISSUE_CARD_CLAIM add (OPERATION_UID varchar2(32))
go

-- Номер ревизии: 83925 
-- Номер версии: 19.0
-- Комментарий: Корзина платежей. Конвертация автоплатежа в автопоиск и обратно.(ч4)
alter table INVOICE_SUBSCRIPTIONS modify STATE VARCHAR2(20)
go

-- Номер ревизии: 83946
-- Номер версии: v1.18
-- Комментарий: mdm ч.39. Удаление старой реализации

alter table DEPARTMENTS drop column MDM_SUPPORTED
go

-- Номер ревизии: 84128 
-- Номер версии: v1.18
-- Комментарий: Шаблон заявки на ПДП. Модель БД
ALTER TABLE CREDIT_OFFER_TEMPLATE ADD (TYPE VARCHAR2(4) DEFAULT 'ERIB');

-- Номер ревизии: 84507
-- Номер версии: 19.0
-- Комментарий: Merged revision(s) 84505 from versions/v1.18: BUG090473  АРМ. Некорректно проставляется признак отправки уведомлений по кредитам BUG091934  МИГРАЦИЯ ИЗ АС ФИЛИАЛ. Не наследуется признак доступности в СМС-канале 
UPDATE LOAN_LINKS
   SET ERMB_NOTIFICATION = '0'
go

-- Номер ревизии: 84128 
-- Номер версии: v1.18
-- Комментарий: Шаблон заявки на ПДП. Модель БД. Исправление ошибок кодирования
ALTER TABLE CREDIT_OFFER_TEMPLATE DROP COLUMN TYPE;
ALTER TABLE CREDIT_OFFER_TEMPLATE ADD (TYPE VARCHAR2(4));

-- Номер ревизии: 85087
-- Номер версии: v1.18
-- Комментарий: BUG092409: [Гостевой сбол] ошибка при сохранении в crm_offer_logins
alter table CRM_OFFER_LOGINS modify DOC_SERIES null
go

-- Номер ревизии: 85157
-- Номер версии: v1.18
-- Комментарий: CHG092645: Оферты : Новый формат AltAnnuitentyPayment 
delete from OFFER_ERIB_PRIOR
/
alter table OFFER_ERIB_PRIOR modify   ALT_ANNUITY_PAYMENT  NUMBER(12,2)
/
delete from OFFER_CONFIRMED
/
alter table OFFER_CONFIRMED modify  ALT_ANNUITY_PAYMENT  NUMBER(12,2)
/


-- Номер ревизии: 85235
-- Номер версии: v1.18
-- Комментарий: BUG069830: [ISUP] Синхронизация справочника регионов
ALTER TABLE PROFILE DROP CONSTRAINT FK_PROFILE_REG_REF_REGIONS
GO
ALTER TABLE PROFILE ADD REGION_CODE VARCHAR2(20)
GO
UPDATE PROFILE PR SET PR.REGION_CODE = (SELECT CODE FROM REGIONS WHERE PR.REGION_ID = ID)
GO
ALTER TABLE PROFILE DROP COLUMN REGION_ID
GO

-- Номер ревизии 85301
-- Номер Версии: v1.18
-- Комментарий: [Фрод-мониторинг] перевод джоба рассылки оповещений в многопоточный режим.

DROP INDEX IDX_FN_ID_STATE_DATE
go

CREATE INDEX IDX_FN_NOT_SENT_ID ON FRAUD_NOTIFICATIONS ( 
    decode(STATE, 'NOT_SENT', ID, null)
) local
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


-- Номер ревизии 85567
-- Номер Версии: v1.18
-- Комментарий: BUG092317: [ISUP] ЕРМБ. В АРМ сотрудника не удается добавить номер телефона к ЕРМБ
drop table SMSPASSWORDS 
go

drop sequence S_SMSPASSWORDS
go

-- Номер ревизии 85780
-- Номер Версии: v1.18
-- Комментарий:  BUG093042: [УКО 3] Дублируются предодобренные предложения
alter table CRM_OFFERS add (IS_ACTIVE CHAR(1) default '1' not null);

-- Номер ревизии 85820
-- Номер Версии: 19.0
-- Комментарий: BUG091843  [ISUP] Неверная валидация длины поля ContactNum в CEDBO
alter table USERS modify HOME_PHONE VARCHAR2(35)
go
alter table USERS modify JOB_PHONE VARCHAR2(35)
go

-- Номер Версии: 19.0
-- Комментарий:  CHG093131: [CRM] ошибка при добавлении записи в CRM_Offers при работе в гостевом блоке 
alter table CRM_OFFERS modify DOC_SERIES null;