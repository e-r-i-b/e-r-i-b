--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

create table CHANGE_CARD_OP_CATEGORY_LOG 
(
	ID                   INTEGER              not null,
	CHANGE_DATE          TIMESTAMP            not null,
	TB                   VARCHAR2(4)          not null,
	VSP                  VARCHAR2(7),
	FIO                  VARCHAR2(100)        not null,
	OPERATION_NAME       VARCHAR2(100),
	MCC_CODE             INTEGER,
	AMOUNT               NUMBER(15,4)         not null,
	PARENT_CATEGORY      VARCHAR2(100)        not null,
	NEW_CATEGORIES       VARCHAR2(100),
	NEW_CATEGORIES_COUNT INTEGER              not null,
	constraint PK_CHANGE_CARD_OP_CATEGORY_LOG primary key (ID) using index (
		create unique index PK_CHANGE_CARD_OP_CATEGORY_LOG on CHANGE_CARD_OP_CATEGORY_LOG(ID) reverse tablespace ERIBLOGINDEXES 
	)
)
partition by range (CHANGE_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
    --Создаем 1 начальную партицию до 1 января
    partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY'))
)
/

create sequence S_CHANGE_CARD_OP_CATEGORY_LOG cache 15000
/

create index IDX_CHANGE_CARD_OP_LOG on CHANGE_CARD_OP_CATEGORY_LOG (
   CHANGE_DATE DESC,
   TB ASC,
   VSP ASC
) tablespace ERIBLOGINDEXES local
/

-- Номер ревизии: 54914
-- Комментарий: Логирование отправленных клиентам push-сообщений
create table PUSH_DEVICES_STATES_LOG 
(
	ID                   number(20)           not null,
	CREATION_DATE        DATE                 not null,
	CLIENT_ID            varchar2(64)         not null,
	MGUID                varchar2(64)         not null,
	TYPE                 varchar2(1)          not null,
	constraint PK_PUSH_DEVICES_STATES_LOG primary key (ID) using index (
		create unique index PK_PUSH_DEVICES_STATES_LOG on PUSH_DEVICES_STATES_LOG(ID) reverse tablespace ERIBLOGINDEXES 
	)
)   
)
partition by range (CREATION_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
    --Создаем 1 начальную партицию до 1 января
    partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY'))
)
/

create index I_PUSH_DEVICES_STATES_LOG_DATE on PUSH_DEVICES_STATES_LOG (
   CREATION_DATE ASC,
   TYPE ASC
) tablespace ERIBLOGINDEXES local
/


create sequence S_PUSH_DEVICES_STATES_LOG cache 15000
/

-- Номер ревизии: 54935
-- Комментарий: Сбор статистики. Сущности.
create table USER_NOTIFICATION_LOG 
(
	ID                   INTEGER              not null,
	LOGIN_ID             INTEGER              not null,
	ADDITION_DATE        TIMESTAMP            not null,
	TYPE                 VARCHAR2(25)         not null,
	VALUE                VARCHAR2(20),
	constraint PK_USER_NOTIFICATION_LOG primary key (ID) using index (
		create unique index PK_USER_NOTIFICATION_LOG on USER_NOTIFICATION_LOG(ID) reverse tablespace ERIBLOGINDEXES 
	)   
)
partition by range (ADDITION_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
    --Создаем 1 начальную партицию до 1 января
    partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY'))
)
/

create sequence S_USER_NOTIFICATION_LOG cache 15000
/

create index I_USER_NOTIFICATION_LOG_DATE on USER_NOTIFICATION_LOG (
   ADDITION_DATE ASC,
   VALUE ASC
) tablespace ERIBLOGINDEXES local
/


-- Номер ревизии: 55455
-- Комментарий: CHG063854: Сбор статисики для push и email. 
create table USER_MESSAGES_LOG 
(
	ID                   INTEGER              not null,
	ADDITION_DATE        TIMESTAMP            not null,
	TYPE                 VARCHAR2(25)         not null,
	MESSAGE_ID           VARCHAR2(64),
	LOGIN_ID             INTEGER              not null,
	TYPE_CODE            VARCHAR2(32),
   	constraint PK_USER_MESSAGES_LOG primary key (ID) using index (
		create unique index PK_USER_MESSAGES_LOG on USER_MESSAGES_LOG(ID) reverse tablespace ERIBLOGINDEXES 
	)   
)
partition by range (ADDITION_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
    --Создаем 1 начальную партицию до 1 января
    partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY'))
)
/

create sequence S_USER_MESSAGES_LOG cache 15000
/

create index I_USER_MESSAGES_LOG_DATE_TYPE on USER_MESSAGES_LOG (
   ADDITION_DATE ASC,
   TYPE ASC
) tablespace ERIBLOGINDEXES local
/

-- Комментарий: Отчет «Популярные отчеты по расходам в АЛФ».
/*==============================================================*/
/* Table: FILTER_OUTCOME_PERIOD_LOG                             */
/*==============================================================*/
create table FILTER_OUTCOME_PERIOD_LOG 
(
	ID                   INTEGER              not null,
	FILTER_DATE          TIMESTAMP            not null,
	TB                   VARCHAR2(4)          not null,
	PERIOD_TYPE          VARCHAR2(30)         not null,
	IS_DEFAULT           CHAR(1)              not null,
   	constraint PK_FILTER_OUTCOME_PERIOD_LOG primary key (ID) using index (
		create unique index PK_FILTER_OUTCOME_PERIOD_LOG on FILTER_OUTCOME_PERIOD_LOG(ID) reverse tablespace ERIBLOGINDEXES 
	)     
)
partition by range(FILTER_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
	partition P_START values less than (to_date('1-12-2013', 'DD-MM-YYYY'))
)
/

create sequence S_FILTER_OUTCOME_PERIOD_LOG cache 15000
/

/*==============================================================*/
/* Index: FILTER_OUTCOME_PERIOD_INDEX                           */
/*==============================================================*/
create index FILTER_OUTCOME_PERIOD_INDEX on FILTER_OUTCOME_PERIOD_LOG (
   FILTER_DATE ASC,
   PERIOD_TYPE ASC,
   IS_DEFAULT ASC
) tablespace ERIBLOGINDEXES local
/

-- Номер ревизии: 54980
-- Комментарий: Реализация журнала входов в ЦСА
create table CSA_ACTION_LOG 
(
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
   LOG_UID              VARCHAR2(32)         not null   
)
partition by range(START_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
	partition P_START values less than (to_date('1-12-2013', 'DD-MM-YYYY'))
)
/

create sequence S_CSA_ACTION_LOG cache 15000
/

create index CSA_ACTIONLOG_UNIVERSAL_ID on CSA_ACTION_LOG 
(
	PASSPORT ASC,
	START_DATE DESC
) tablespace ERIBLOGINDEXES local
/

