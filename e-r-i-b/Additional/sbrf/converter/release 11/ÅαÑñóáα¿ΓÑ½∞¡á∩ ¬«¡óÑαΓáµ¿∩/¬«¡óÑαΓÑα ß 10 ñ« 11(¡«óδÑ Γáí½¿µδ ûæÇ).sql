-- Номер ревизии:  48529
-- Комментарий: новости для страницы ЦСА.
/*==============================================================*/
/* Table: NEWS_DEPARTMENT                                       */
/*==============================================================*/
create table NEWS_DEPARTMENT  (
   NEWS_ID              INTEGER                         not null,
   DEPARTMENT_ID        INTEGER                         not null,
   TB                   VARCHAR2(2),
   NAME                 VARCHAR2(256),
   constraint PK_NEWS_DEPARTMENT primary key (NEWS_ID, DEPARTMENT_ID)
)
/

create sequence S_NEWS_DEPARTMENT
/

-- Номер ревизии:  48100
-- Комментарий: Выбор региона на странице ЦСА(БД) 
/*==============================================================*/
/* Table: REGIONS                                               */
/*==============================================================*/
create table REGIONS  (
   ID                   INTEGER                         not null,
   CODE                 VARCHAR2(20)                    not null,
   NAME                 NVARCHAR2(128)                  not null,
   CODE_TB              VARCHAR2(2),
   PARENT_ID            INTEGER,
   constraint PK_REGIONS primary key (ID)
)
/

create sequence S_REGIONS
/

/*==============================================================*/
/* Index: INDEX_CODE                                            */
/*==============================================================*/
create unique index INDEX_CODE on REGIONS (
   CODE ASC
)
/

/*==============================================================*/
/* Table: USERS_REGIONS                                         */
/*==============================================================*/
create table USERS_REGIONS  (
   ID                   INTEGER                         not null,
   CODE                 VARCHAR2(20),
   COOKIE               VARCHAR(32)                     not null,
   CSA_USER_ID          VARCHAR2(10)                    not null,
   LAST_USE_DATE        TIMESTAMP,
   constraint PK_USERS_REGIONS primary key (ID)
)
/

create sequence S_USERS_REGIONS
/

/*==============================================================*/
/* Index: INDEX_COOKIE                                          */
/*==============================================================*/
create unique index INDEX_COOKIE on USERS_REGIONS (
   COOKIE ASC
)
/

/*==============================================================*/
/* Index: INDEX_CSA_USER_ID                                     */
/*==============================================================*/
create index INDEX_CSA_USER_ID on USERS_REGIONS (
   CSA_USER_ID ASC
)
/

-- Номер ревизии:  48519
-- Комментарий: Управление текстами смс в ЕРИБ. CSA. Модель БД.
/*==============================================================*/
/* Table: SMS_RESOURCES                                         */
/*==============================================================*/
create table SMS_RESOURCES  (
   ID                   INTEGER                         not null,
   KEY                  VARCHAR2(255)                   not null,
   TEXT                 CLOB                            not null,
   DESCRIPTION          VARCHAR2(255),
   SMS_TYPE             VARCHAR2(13)                    not null,
   LAST_MODIFIED        TIMESTAMP,
   PREVIOUS_TEXT        CLOB,
   EMPLOYEE_LOGIN_ID    INTEGER,
   VARIABLES            VARCHAR2(4000),
   constraint PK_SMS_RESOURCES primary key (ID)
)
/

create sequence S_SMS_RESOURCES
/

/*==============================================================*/
/* Index: INDEX_SMS_KEY                                         */
/*==============================================================*/
create unique index INDEX_SMS_KEY on SMS_RESOURCES (
   KEY ASC
)
/

create table LIST_PROPERTIES (
   ID              INTEGER     not null,
   PROPERTY_ID     INTEGER     not null,
   VALUE           VARCHAR2(200),
   constraint PK_LIST_PROPERTIES primary key (ID)
)
/

create sequence S_LIST_PROPERTIES
/

create index "DXFK_LIST_PROPERTIES" on LIST_PROPERTIES
(
   PROPERTY_ID
)
/

alter table LIST_PROPERTIES
   add constraint FK_LIST_PROPERTIES foreign key (PROPERTY_ID)
      references PROPERTIES (ID)
      on delete cascade
/
