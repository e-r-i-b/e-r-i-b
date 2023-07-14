/*==============================================================*/
/* Table: MESSAGE_TRANSLATE                                     */
/*==============================================================*/
create table MESSAGE_TRANSLATE  (
   ID                   integer                         not null,
   CODE                 varchar2(255)                   not null,
   TRANSLATE            varchar2(255)                   not null,
   TYPE                 char(1)                         not null,
   constraint PK_MESSAGE_TRANSLATE primary key (ID),
   constraint AK_CODE_MESSAGE_ unique (CODE)
)
/

create sequence S_MESSAGE_TRANSLATE
/

/*==============================================================*/
/* Table: ADVERTISINGS_LOG                                      */
/*==============================================================*/
create table ADVERTISINGS_LOG  (
   ID                   integer                         not null,
   ADVERTISING_ID       integer                         not null,
   START_DATE           timestamp                       not null,
   TYPE                 varchar2(20)                    not null,
   constraint PK_ADVERTISINGS_LOG primary key (ID)
)
/

create sequence S_ADVERTISINGS_LOG cache 10000
/

/*==============================================================*/
/* Index: I_ADVERTISING_LOG                                     */
/*==============================================================*/
create index I_ADVERTISING_LOG on ADVERTISINGS_LOG (
   START_DATE ASC
)
/

create index "DXFK_ADVERTISINGS_LOG" on ADVERTISINGS_LOG
(
   ADVERTISING_ID
)
/


/*==============================================================*/
/* Table: QUICK_PAYMENT_PANELS_LOG                              */
/*==============================================================*/
create table QUICK_PAYMENT_PANELS_LOG  (
   ID                   integer                         not null,
   PANEL_ID             integer                         not null,
   TB                   varchar2(4)                     not null,
   TYPE                 varchar2(16)                    not null,
   START_DATE           timestamp                       not null,
   AMOUNT               number(15,4),
   constraint PK_QUICK_PAYMENT_PANELS_LOG primary key (ID)
)
/

create sequence S_QUICK_PAYMENT_PANELS_LOG cache 1000
/
