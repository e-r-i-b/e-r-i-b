-- Номер ревизии: 47250
-- Механизм проводки оффлайн документов 
/*==============================================================*/
/* Table: JOB_EXECUTION_MARKER                                  */
/*==============================================================*/
create table JOB_EXECUTION_MARKER  (
   ID                   INTEGER                         not null,
   JOB_NAME             VARCHAR2(128)                   not null,
   ACTUAL_DATE          TIMESTAMP                       not null,
   constraint PK_JOB_EXECUTION_MARKER primary key (ID)
)
/

create sequence S_JOB_EXECUTION_MARKER
/

-- Номер ревизии: 47754
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
)
/

create sequence S_PAYMENT_FORM_IMPORTS
/

-- Номер ревизии: 47856
-- Комментарий: Лимиты по автоплатежам(Доработка АРМ сотрудника банка)
/*==============================================================*/
/* Table: AUTOPAY_SETTINGS                                      */
/*==============================================================*/
create table AUTOPAY_SETTINGS(
   ID                   INTEGER                         not null,
   TYPE                 VARCHAR2(20)                    not null,
   RECIPIENT_ID         INTEGER                         not null,
   PARAMETERS           CLOB                            not null,
   constraint PK_AUTOPAY_SETTING primary key (ID)
)
/

create sequence S_AUTOPAY_SETTINGS
/

alter table AUTOPAY_SETTINGS
   add constraint FK_AUTOPAY_SETTING_TO_PROV foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
/

create index "DXFK_AUTOPAY_SETTING_TO_PROV" on AUTOPAY_SETTINGS
(
   RECIPIENT_ID
)
/

DECLARE
	autopayParams CLOB; -- параметры автоплатежа
BEGIN
FOR serviceProvidersRow IN 
(SELECT 
	ID,
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
FROM SERVICE_PROVIDERS WHERE IS_AUTOPAYMENT_SUPPORTED = '1') 
	LOOP
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
/

-- Номер ревизии: 47889
-- Комментарий:  Изменения старых данных клиента в профиле
/*==============================================================*/
/* Table: PERSON_OLD_IDENTITY                                   */
/*==============================================================*/
create table PERSON_OLD_IDENTITY  (
   ID                   INTEGER                         not null,
   FIRST_NAME           VARCHAR2(120)                   not null,
   SUR_NAME             VARCHAR2(120)                   not null,
   PATR_NAME            VARCHAR2(120),
   BIRTHDAY             TIMESTAMP                       not null,
   EMPLOYEE_ID          INTEGER,
   DATE_CHANGE          TIMESTAMP                       not null,
   STATUS               VARCHAR2(12)                    not null,
   PERSON_ID            INTEGER                         not null,
   DOC_TYPE             VARCHAR2(32),
   DOC_NAME             VARCHAR2(128),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   DOC_ISSUE_DATE       TIMESTAMP,
   DOC_ISSUE_BY         VARCHAR2(255),
   DOC_ISSUE_BY_CODE    VARCHAR2(16),
   DOC_MAIN             VARCHAR2(1),
   DOC_TIME_UP_DATE     TIMESTAMP,
   DOC_IDENTIFY         VARCHAR2(1),
   constraint PK_PERSON_OLD_IDENTITY primary key (ID)
)
/

create sequence S_PERSON_OLD_IDENTITY
/

create index "DXFK_PERS_OLD_IDEN_EMPLOYEE" on PERSON_OLD_IDENTITY
(
   EMPLOYEE_ID
)
/

alter table PERSON_OLD_IDENTITY
   add constraint FK_PERS_OLD_IDEN_EMPLOYEE foreign key (EMPLOYEE_ID)
      references LOGINS (ID)
/


create index "DXFK_PERS_OLD_IDEN_USERS" on PERSON_OLD_IDENTITY
(
   PERSON_ID
)
/

alter table PERSON_OLD_IDENTITY
   add constraint FK_PERS_OLD_IDEN_USERS foreign key (PERSON_ID)
      references USERS (ID)
/

--Номер ревизии: 49180
--Комментарий: Добавлен функциональный индекс ФИО+ДУЛ+ДР на историю идентификационных данных клиентов
create index POI_FIO_DUL_DR on PERSON_OLD_IDENTITY
(
    upper(replace(SUR_NAME,' ','') || replace(FIRST_NAME,' ','') || replace(PATR_NAME,' ','')),
    replace(DOC_SERIES,' ','') || replace(DOC_NUMBER,' ',''),
    BIRTHDAY
);

-- Номер ревизии: 47948
-- Комментарий: АЛФ Планирование: Создать сущность "Цель", сервис и тест.
/*==============================================================*/
/* Table: ACCOUNT_TARGETS                                       */
/*==============================================================*/
create table ACCOUNT_TARGETS  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   NAME                 VARCHAR2(35)                    not null,
   NAME_COMMENT         VARCHAR2(100),
   PLANED_DATE          TIMESTAMP                       not null,
   AMOUNT               NUMBER(20,4)                    not null,
   AMOUNT_CURRENCY      CHAR(3)                         not null,
   ACCOUNT_NUM          VARCHAR2(25),
   DICTIONARY_TARGET    VARCHAR2(15)                    not null,
   CLAIM_ID             INTEGER,
   ACCOUNT_LINK         INTEGER,
   constraint PK_ACCOUNT_TARGETS primary key (ID)
)
/

create sequence S_ACCOUNT_TARGETS
/

create unique index I_ACCOUNT_TARGETS_CLAIM on ACCOUNT_TARGETS (
   LOGIN_ID ASC,
   CLAIM_ID ASC
)
/

create index "DXFK_A_TARGETS_FK_LOGINS" on ACCOUNT_TARGETS
(
   LOGIN_ID
)
/

alter table ACCOUNT_TARGETS
   add constraint FK_A_TARGETS_FK_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
/

create index "DXFK_A_TARGETS_FK_ACCOUNT_LINK" on ACCOUNT_TARGETS
(
   ACCOUNT_LINK
)
/

alter table ACCOUNT_TARGETS
   add constraint FK_A_TARGETS_FK_ACCOUNT_LINK foreign key (ACCOUNT_LINK)
      references ACCOUNT_LINKS (ID)
      on delete set null
/

-- Номер ревизии: 47959
-- Комментарий: Страховые и НПФ продукты клиента
/*==============================================================*/
/* Table: INSURANCE_LINKS                                       */
/*==============================================================*/
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
/

create sequence S_INSURANCE_LINKS
/

create unique index UNIQ_INS_NUMBER on INSURANCE_LINKS (
   REFERENCE ASC,
   LOGIN_ID ASC
)
/

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
/

create sequence S_USER_TIMINGS
/

comment on column USER_TIMINGS.USER_ID is
'ID клиента'
/

comment on column USER_TIMINGS.ACCOUNT_LIST_LAST_UPDATE is
'Время последнего обновления списка счетов клиента данными из КСШ'
/

comment on column USER_TIMINGS.CARD_LIST_LAST_UPDATE is
'Время последнего обновления списка карт клиента данными из КСШ'
/

comment on column USER_TIMINGS.LOAN_LIST_LAST_UPDATE is
'Время последнего обновления списка кредитов клиента данными из КСШ'
/

alter table USER_TIMINGS
   add constraint FK_USER_TIMINGS foreign key (USER_ID)
      references USERS (ID)
      on delete cascade
/

-- Номер ревизии: 48338
-- Комментарий: Управление текстами смс в ЕРИБ. Модель БД.
/*==============================================================*/
/* Table: SMS_RESOURCES                                         */
/*==============================================================*/
create table SMS_RESOURCES  (
   ID                   INTEGER                         not null,
   SMS_TYPE             VARCHAR2(13)                    not null,
   KEY                  VARCHAR2(255)                   not null,
   TEXT                 CLOB                            not null,
   CUSTOM               CHAR(1),
   CHANNEL              VARCHAR2(20),
   DESCRIPTION          VARCHAR2(255),
   LAST_MODIFIED        TIMESTAMP,
   PREVIOUS_TEXT        CLOB,
   EMPLOYEE_LOGIN_ID    INTEGER,
   VARIABLES            VARCHAR2(4000),
   constraint PK_SMS_RESOURCES primary key (ID)
)
/

create sequence S_SMS_RESOURCES
/

create index INDEX_SMS_TYPE on SMS_RESOURCES (
   SMS_TYPE ASC
)
/

create index INDEX_SMS_CHANNEL_KEY on SMS_RESOURCES (
   KEY ASC,
   CHANNEL ASC
)
/

-- Номер ревизии: 48577
-- Комментарий: ЕРМБ. ErmbProfile: PK = FK на USER.ID

drop table ERMB_CLIENT_PHONE;
drop table ERMB_PROFILE;

/*==============================================================*/
/* Table: ERMB_PROFILE                                          */
/*==============================================================*/
create table ERMB_PROFILE  (
   PERSON_ID            INTEGER                         not null,
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
)
/

create index "DXFK_ERMB_PROFILE_TARIF" on ERMB_PROFILE
(
   ERMB_TARIF_ID
)
/

alter table ERMB_PROFILE
   add constraint FK_ERMB_PROFILE_TARIF foreign key (ERMB_TARIF_ID)
      references ERMB_TARIF (ID)
/

alter table ERMB_PROFILE
   add constraint FK_ERMB_PROFILE_USERS foreign key (PERSON_ID)
      references USERS (ID)
/

/*==============================================================*/
/* Table: ERMB_CLIENT_PHONE                                     */
/*==============================================================*/
create table ERMB_CLIENT_PHONE  (
   ID                   INTEGER                         not null,
   PROFILE_ID           INTEGER                         not null,
   IS_MAIN              CHAR(1)                        default '0' not null,
   PHONE_NUMBER         VARCHAR2(20)                    not null,
   MOBILE_PHONE_OPERATOR VARCHAR2(100)                  default '0' not null,
   constraint PK_ERMB_CLIENT_PHONE primary key (ID)
)
/

create unique index UNIQ_ERMB_PHONE_NUMBER on ERMB_CLIENT_PHONE (
   PHONE_NUMBER ASC
)
/

create index "DXFK_ERMB_CLIENT_PHONE_PROFILE" on ERMB_CLIENT_PHONE
(
   PROFILE_ID
)
/

alter table ERMB_CLIENT_PHONE
   add constraint FK_ERMB_CLIENT_PHONE_PROFILE foreign key (PROFILE_ID)
      references ERMB_PROFILE (PERSON_ID)
      on delete cascade
/

-- Номер ревизии: 48630
-- Комментарий: ЕРМБ (Транспортный канал): прием результата проверки IMSI
/*==============================================================*/
/* Table: ERMB_CHECK_IMSI_RESULTS                               */
/*==============================================================*/
create table ERMB_CHECK_IMSI_RESULTS  (
   SMS_UID              VARCHAR(32)                     not null,
   RESULT               CHAR(1),
   constraint PK_ERMB_CHECK_IMSI_RESULTS primary key (SMS_UID)
)
/

create sequence S_ERMB_CHECK_IMSI_RESULTS
/

-- Номер ревизии: 48710
-- Комментарий: Маппинг ошибок
/*==============================================================*/
/* Table: EXCEPTION_ENTRY                                       */
/*==============================================================*/
create table EXCEPTION_ENTRY  (
   ID                   INTEGER                         not null,
   KIND                 CHAR(1)                         not null,
   HASH                 VARCHAR2(40)                    not null,
   OPERATION            VARCHAR2(160),
   APPLICATION          VARCHAR2(20)                    not null,
   DETAIL               CLOB                            not null,
   MESSAGE              VARCHAR2(2000),
   SYSTEM               VARCHAR2(16),
   ERROR_CODE           VARCHAR2(20),
   constraint PK_EXCEPTION_ENTRY primary key (ID)
)
/

create sequence S_EXCEPTION_ENTRY
/

create unique index IND_EXCEPTION_ENTRY_HASH on EXCEPTION_ENTRY (
   HASH ASC
)
/

-- Номер ревизии: 48771
-- Комментарий: Редактирование группы услуг
/*==============================================================*/
/* Table: PAYMENT_SERV_PARENTS                                  */
/*==============================================================*/
create table PAYMENT_SERV_PARENTS  (
   SERVICE_ID           INTEGER                         not null,
   PARENT_ID            INTEGER                         not null,
   constraint PK_PAYMENT_SERV_PARENTS primary key (SERVICE_ID, PARENT_ID)
)
/

create sequence S_PAYMENT_SERV_PARENTS
/

create index "DXFK_PAY_SER_TO_PARENT" on PAYMENT_SERV_PARENTS
(
   PARENT_ID
)
/

alter table PAYMENT_SERV_PARENTS
   add constraint FK_PAY_SER_TO_PARENT foreign key (PARENT_ID)
      references PAYMENT_SERVICES (ID)
/


create index "DXFK_PAY_SER_TO_SERV" on PAYMENT_SERV_PARENTS
(
   SERVICE_ID
)
/

alter table PAYMENT_SERV_PARENTS
   add constraint FK_PAY_SER_TO_SERV foreign key (SERVICE_ID)
      references PAYMENT_SERVICES (ID)
/

-- Номер ревизии: 48892
-- Комментарий: Реализовать механизм автоматической архивации отчетов об ошибках (сущность каунтера)
/*==============================================================*/
/* Table: EXCEPTION_COUNTERS                                    */
/*==============================================================*/
create table EXCEPTION_COUNTERS  (
   EXCEPTION_HASH       VARCHAR2(40)                    not null,
   EXCEPTION_DATE       TIMESTAMP                       not null,
   EXCEPTION_COUNT      INTEGER                         not null,
   constraint PK_EXCEPTION_COUNTERS primary key (EXCEPTION_HASH, EXCEPTION_DATE)
)
/

create sequence S_EXCEPTION_COUNTERS
/


--Номер ревизии: 49031
--Комментарий:  Перекодировка справочника подразделений из СПОБК. Модель БД
/*==============================================================*/
/* Table: DEPARTMENTS_RECORDING                                 */
/*==============================================================*/
create table DEPARTMENTS_RECORDING  (
   ID                   INTEGER                         not null,
   TB_ERIB              VARCHAR2(3)                     not null,
   OSB_ERIB             VARCHAR2(4),
   OFFICE_ERIB          VARCHAR2(7),
   TB_SPOOBK2           VARCHAR2(3)                     not null,
   OSB_SPOOBK2          VARCHAR2(4),
   OFFICE_SPOOBK2       VARCHAR2(7),
   constraint PK_DEPARTMENTS_RECORDING primary key (ID)
)
/

create sequence S_DEPARTMENTS_RECORDING
/

/*==============================================================*/
/* Index: SPOOBK2_TB_OSB                                        */
/*==============================================================*/
create index SPOOBK2_TB_OSB on DEPARTMENTS_RECORDING (
   TB_SPOOBK2 ASC,
   LTRIM(OSB_SPOOBK2, '0') ASC
)
/

/*==============================================================*/
/* Table: DEPARTMENTS_RECORDING_TMP                             */
/*==============================================================*/
create table DEPARTMENTS_RECORDING_TMP  (
   ID                   INTEGER                         not null,
   TB_ERIB              VARCHAR2(3)                     not null,
   OSB_ERIB             VARCHAR2(4),
   OFFICE_ERIB          VARCHAR2(7),
   TB_SPOOBK2           VARCHAR2(3)                     not null,
   OSB_SPOOBK2          VARCHAR2(4),
   OFFICE_SPOOBK2       VARCHAR2(7),
   constraint PK_DEPARTMENTS_RECORDING_TMP primary key (ID)
)
/

create sequence S_DEPARTMENTS_RECORDING_TMP
/

--Номер ревизии: 49215
--Комментарий:  Бюджетирование
/*==============================================================*/
/* Table: CLIENTS_BUDGET                                        */
/*==============================================================*/
create table CLIENTS_BUDGET  (
   LOGIN_ID             INTEGER                         not null,
   CATEGORY_ID          INTEGER                         not null,
   BUDGET               NUMBER(15,4),
   constraint PK_CLIENTS_BUDGET primary key (LOGIN_ID, CATEGORY_ID)
)
/

create sequence S_CLIENTS_BUDGET
/

create index "DXFK_CLIENTS_BUDGET_TO_CATEGOR" on CLIENTS_BUDGET
(
   CATEGORY_ID
)
/

alter table CLIENTS_BUDGET
   add constraint FK_CLIENTS__FK_CLIENT_CARD_OPE foreign key (CATEGORY_ID)
      references CARD_OPERATION_CATEGORIES (ID)
      on delete cascade
/


create index "DXFK_CLIENTS_BUDGET_TO_LOGINS" on CLIENTS_BUDGET
(
   LOGIN_ID
)
/

alter table CLIENTS_BUDGET
   add constraint FK_CLIENTS__FK_CLIENT_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
/

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
/

create sequence S_EXTENDED_DESCRIPTION_DATA
/

create unique index UNQ_NAME_EXT_DESC on EXTENDED_DESCRIPTION_DATA (
   NAME ASC
)
/

insert into EXTENDED_DESCRIPTION_DATA(ID, NAME, CONTENT) values (S_EXTENDED_DESCRIPTION_DATA.nextval, 'aeroexpress-rule', EMPTY_CLOB())
/

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h1>
                                Правила оказания услуг по перевозкам пассажиров, ручной клади и багажа в электропоездах ООО «Аэроэкспресс»
                            </h1>
                            <br/>
                            <h2>
                                <b>1. Общие положения</b>
                            </h2>
                            1.1	Настоящие Правила разработаны в соответствии с требованиями следующих нормативных документов:
                            &'||'mdash; Федерального закона РФ от 10.01.2003 года № 18-ФЗ «Устав железнодорожного транспорта РФ»;<br/>
                            &'||'mdash; Правил оказания услуг по перевозкам на железнодорожном транспорте пассажиров, а также грузов, багажа и грузобагажа для личных, семейных, домашних и иных нужд, не связанных с осуществлением предпринимательской деятельности, утвержденных постановлением Правительства РФ от 2 марта 2005 года № 111;<br/>
                            &'||'mdash; Правил перевозок пассажиров, багажа и грузобагажа на федеральном железнодорожном транспорте, утвержденных Приказом МПС РФ от 26 июля 2002 года N 30;<br/>
                            &'||'mdash; Иных нормативно-правовых актов Российской Федерации.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
/

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>2. Перевозка пассажиров</b>
                            </h2>
                            2.1	Для проезда в электропоезде ООО «Аэроэкспресс» пассажир обязан:<br/>
                            2.2	Приобрести билет для разовой поездки, либо абонементный билет в установленной форме;<br/>
                            2.3	Сохранять приобретённый проездной документ (билет) в течение всего пути следования электропоезда до момента выхода через пункт контроля проездных документов (билетов). Пассажир, не предъявивший проездной документ (билет) при выходе через пункт контроля, считается безбилетным и обязан оплатить стоимость проезда;<br/>
                            2.4	Предъявлять проездной документ (билет) лицам, осуществляющим контроль – разъездным билетным кассирам (бортпроводникам);<br/>
                            2.5	При проверке проездных документов (билетов) в электропоезде, предъявить разъездному билетному кассиру (бортпроводнику) документы, удостоверяющие право на льготы, (если пассажир имеет на них право).<br/>
                            2.6	Пассажир вправе приобрести проездной документ (билет) в пути следования (при наличии такой услуги на направлении перевозки) с оплатой стоимости услуги по оформлению проездных документов (билетов) в электропоездах ООО «Аэроэкспресс» согласно действующему тарифу.<br/>
                            2.7	Проездной документ (билет) для разовой поездки действителен на одну поездку в одном направлении в соответствии с датой, указанной в нём. Если срок действия проездного документа (билета) заканчивается в момент нахождения пассажира в пути, проездной документ (билет) является действительным до прибытия пассажира в пункт назначения.<br/>
                            2.8	Пассажир имеет право провозить бесплатно детей в возрасте не старше 5 (пяти) лет. При следовании с пассажиром детей в возрасте от 5 (пяти) до 7 (семи) лет непосредственно в день поездки приобретаются детские билеты. При возникновении сомнения относительно возраста детей, провозимых бесплатно или по детским билетам, разъездные билетные кассиры (бортпроводники) вправе потребовать предъявления соответствующих документов, подтверждающих возраст ребенка.<br/>
                            2.9	Разовые и абонементные билеты (в т.ч. абонементный билет «Выходного дня»), оформленные иными перевозчиками пригородного сообщения, в электропоездах  ООО «Аэроэкспресс» не действительны. Пассажир, предъявивший такие билеты, считается безбилетным и с него взимается полная стоимость проезда, согласно установленному тарифу для данного вида перевозок.<br/>
                            2.10 На один билет пассажир имеет право занять только одно место. При отсутствии свободных сидячих мест в вагоне допускается проезд пассажиров стоя, при этом стоимость проезда не изменяется. <br/>
                            2.11 Пассажир может получить полную стоимость проезда в случае незапланированного перерыва в движении электропоездов более чем на 1 (один) час. При этом в других случаях возврат средств по неиспользованным билетам для разовой поездки не производится. Возврат средств по неиспользованным абонементным билетам производится в порядке и случаях, предусмотренных «Правилами перевозок пассажиров, багажа и грузобагажа на федеральном железнодорожном транспорте»,  утвержденными Приказом МПС РФ от 26 июля 2002 года N 30.<br/>
                            2.12 Оформление проездного документа (билета) в электропоезд лицу, имеющему право оплаты стоимости проезда со скидкой или бесплатного проезда, производится в Порядке, разработанном в соответствии с требованиями действующего законодательства Российской Федерации.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
/

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>3. Перевозка ручной клади и багажа</b>
                            </h2>
                            3.1	Каждый пассажир имеет право бесплатно провозить с собой на один проездной документ (билет) полный или детский ручную кладь весом не более 36 кг, размер которой в сумме 3-х измерений не должен превышать 180 см. Указанная ручная кладь независимо от рода и вида упаковки должна быть размещена на багажных полках вагона электропоезда. Дополнительно к установленной норме разрешается провоз до 50 кг ручной клади за дополнительную плату, согласно установленному тарифу.<br/>
                            3.2	За провоз мелких домашних животных, собак и птиц, одного велосипеда в не разобранном виде (перевозка в тамбуре поезда) взимается дополнительная плата, согласно установленному тарифу.<br/>
                            3.3	Мелкие домашние животные, собаки и птицы должны перевозиться в ящиках, клетках, контейнерах и помещаться на местах, предназначенных для размещения ручной клади. Собаки крупных пород, в том числе служебные перевозятся в тамбуре поезда (не более двух собак) в намордниках и с поводком под наблюдением их владельцев или сопровождающих, которые должны обеспечить соблюдение санитарно-гигиенического режима в вагоне поезда. Перевозка мелких домашних животных, собак и птиц допускается сверх установленной нормы провоза ручной клади при наличии ветеринарной справки.<br/>
                            3.4	Слепые пассажиры провозят при себе собак-проводников бесплатно.<br/>
                            3.5	К перевозке ручной кладью в счет установленной нормы провоза  допускаются растения, саженцы и другие посадочные материалы с обвязанной кроной и упакованными корневищами, не превышающими по высоте 180 см.<br/>
                            3.6	Разрешается перевозка за дополнительную плату электронной, бытовой, видео- и аудиотехники, которая по сумме 3-х измерений превышает 180 см, независимо от наличия у пассажира ручной клади, не более одного предмета на проездной документ (билет). Плата за провоз указанных предметов, независимо от веса взимается согласно установленному тарифу.<br/>
                            3.7	Пассажиру разрешается бесплатно провозить с собой сверх установленной нормы провоза ручной клади портфель, дамскую сумочку, бинокль, лыжи и палки к ним, удочки, фотоаппарат, зонт, а также другие мелкие вещи, размер которых по сумме 3-х измерений не превышает 100 см.<br/>
                            3.8	Обеспечение целостности и сохранности ручной клади, перевозимой пассажиром, является обязанностью пассажира.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
/

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>4.	Пассажирам запрещено</b>
                            </h2>
                            4.1	В электропоездах ООО «Аэроэкспресс» запрещается:<br/>
                            4.2	Находиться с багажом, в одежде, с предметами, продуктами, которые могут испачкать пассажиров, вагоны, сооружения и устройства станций.<br/>
                            4.3	Размещать багаж или ручную кладь на пассажирских креслах.<br/>
                            4.4	Перевозить вещи (предметы), которые могут повредить или загрязнить вагон, а также зловонные, огнеопасные, отравляющие, легковоспламеняющиеся, взрывчатые и другие опасные вещества. Огнестрельное оружие при перевозке в качестве ручной клади должно находиться в чехле, кобуре или специальном футляре в разряженном состоянии отдельно от патронов.<br/>
                            4.5	Распивать спиртные напитки и находиться в нетрезвом состоянии.<br/>
                            4.6	Задерживать открытие или закрытие автоматических дверей на остановках, открывать двери во время движения электропоезда.<br/>
                            4.7	Повреждать внутривагонное оборудование, стекла локомотивов и вагонов.<br/>
                            4.8	Нарушать спокойствие других пассажиров, играть в азартные игры, сорить.<br/>
                            4.9	Останавливать без надобности поезд стоп-краном.<br/>
                            4.10 Курить в вагонах и тамбурах.<br/>
                            4.11 Пассажир может быть удален из электропоезда:<br/>
                            4.12 Работниками ОВД и сотрудниками охраны, сопровождающими состав, если он при посадке в поезд или в пути следования нарушает правила проезда, общественный порядок и мешает спокойствию других пассажиров;<br/>
                            4.13 Разъездными билетными кассирами (бортпроводниками), если пассажир проезжает без проездного документа (билета) или по билету, оформленному на обычный пригородный поезд и отказывается оплатить стоимость проезда согласно установленного порядка;<br/>
                            4.14 Медицинскими работниками - в случае болезни пассажира, препятствующей возможности его дальнейшей поездки или угрожающей здоровью других пассажиров, если нет возможности поместить его отдельно;<br/>
                            4.15 В иных случаях, установленных законодательством Российской Федерации.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
/

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>5.	Права билетных кассиров (бортпроводников)</b>
                            </h2>
                            5.1	Разъездные билетные кассиры (бортпроводники) имеют право:<br/>
                            &'||'mdash; Взыскивать с пассажиров, проезжающих без проездных документов (билетов), стоимость проезда согласно установленному тарифу и сбор за оказание услуги по оформлению проездных документов (билетов).<br/>
                            &'||'mdash; Взыскивать с пассажиров стоимость проезда в установленных размерах за провоз без билета детей в возрасте старше 5 (пяти) лет, за провоз излишнего веса багажа и негабаритной ручной клади, а также сбор за оказание услуги по продаже билетов.<br/>
                            &'||'mdash; При отказе пассажира от уплаты проезда, принимать меры к высадке пассажира из электропоезда.<br/>' where NAME = 'aeroexpress-rule'
/

/*==============================================================*/
/* Table: CONFIRM_BEANS                                         */
/*==============================================================*/
create table CONFIRM_BEANS  (
   ID                           INTEGER                         not null,
   LOGIN_ID                     INTEGER                         not null,
   PRIMARY_CONFIRM_CODE         VARCHAR2(1024)                  not null,
   SECONDARY_CONFIRM_CODE       VARCHAR2(1024),
   EXPIRE_TIME                  TIMESTAMP                       not null,
   CONFIRMABLE_TASK_CLASS       VARCHAR2(256)                   not null,
   CONFIRMABLE_TASK_BODY        VARCHAR2(1024)                  not null,
   constraint PK_CONFIRM_BEANS primary key (ID)
)
/

create sequence S_CONFIRM_BEANS
/

comment on table CONFIRM_BEANS is
'Данные о некоторой операции, требующей подтверждения'
/

comment on column CONFIRM_BEANS.LOGIN_ID is
'Логин-id пользователя, от лица которого производится подтверждение'
/

comment on column CONFIRM_BEANS.PRIMARY_CONFIRM_CODE is
'Код подтверждения'
/

comment on column CONFIRM_BEANS.EXPIRE_TIME is
'Срок окончания действия кода подтверждения'
/

comment on column CONFIRM_BEANS.CONFIRMABLE_TASK_CLASS is
'Класс подтверждаемой операции'
/

comment on column CONFIRM_BEANS.CONFIRMABLE_TASK_BODY is
'Данные подтверждаемой операции'
/

/*==============================================================*/
/* Index: IDX_CONFIRM_EXPIRE                                    */
/*==============================================================*/
create index IDX_CONFIRM_EXPIRE on CONFIRM_BEANS (
   EXPIRE_TIME ASC
)
/

/*==============================================================*/
/* Index: UNIQ_CONFIRM_LOGIN_CODE                               */
/*==============================================================*/
create index UNIQ_CONFIRM_LOGIN_CODE on CONFIRM_BEANS (
   LOGIN_ID ASC,
   (GREATEST(PRIMARY_CONFIRM_CODE, SECONDARY_CONFIRM_CODE) || LEAST(PRIMARY_CONFIRM_CODE, SECONDARY_CONFIRM_CODE)) ASC
)
/

-- Номер ревизии: 49352
-- Комментарий: ЕРМБ. Защита от дублирования входящих СМС.
/*==============================================================*/
/* Table: ERMB_SMS_INBOX                                        */
/*==============================================================*/
create table ERMB_SMS_INBOX  (
   RQ_UID               VARCHAR2(32)                    not null,
   RQ_TIME              TIMESTAMP                       not null,
   PHONE                VARCHAR2(20)                    not null,
   TEXT                 VARCHAR2(1024)                  not null,
   constraint PK_ERMB_SMS_INBOX primary key (RQ_UID)
)
/

create sequence S_ERMB_SMS_INBOX
/

create unique index UI_ERMB_SMS_INBOX on ERMB_SMS_INBOX (
   PHONE ASC,
   TEXT ASC
)
/

create index IDX_RQ_TIME on ERMB_SMS_INBOX (
   RQ_TIME ASC
)
/


create table CLIENT_EXTENDED_LOGGING  (
   LOGIN_ID             INTEGER not null,
   START_DATE           TIMESTAMP                       not null,
   END_DATE           TIMESTAMP,
   constraint PK_CLIENT_EXTENDED_LOGGING primary key (LOGIN_ID)
)
/

create table LIST_PROPERTIES (
   ID              INTEGER     not null,
   PROPERTY_ID     INTEGER     not null,
   VALUE              VARCHAR2(200),
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
   TEMPLATE_NAME        VARCHAR2(286)                   not null,
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
/
create sequence S_PAYMENTS_DOCUMENTS start with 400000000
/

create index IND_TEMPLATE_OWNER on PAYMENTS_DOCUMENTS (
   CLIENT_GUID ASC,
   CLIENT_CREATION_DATE ASC
)
/

create unique index IND_U_TEMPLATE_NAME on PAYMENTS_DOCUMENTS (
   CLIENT_GUID ASC,
   TEMPLATE_NAME ASC
)
/

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
/

create sequence S_PAYMENTS_DOCUMENTS_EXT start with 25000000000
/

create index "DXFK_TEMPLATE_EXT_TO_TEMPLATE" on PAYMENTS_DOCUMENTS_EXT
(
   PAYMENT_ID
)
/

alter table PAYMENTS_DOCUMENTS_EXT
   add constraint FK_TEMPLATE_EXT_TO_TEMPLATE foreign key (PAYMENT_ID)
      references PAYMENTS_DOCUMENTS (ID)
      on delete cascade
/


/*==============================================================*/
/* Table: PROVIDER_SMS_ALIAS                                    */
/*==============================================================*/
create table PROVIDER_SMS_ALIAS  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(10)                    not null,
   SERVICE_PROVIDER_ID  INTEGER                         not null,
   constraint PK_PROVIDER_SMS_ALIAS primary key (ID)
)
/
create sequence S_PROVIDER_SMS_ALIAS
/

create unique index PROVIDER_SMS_ALIAS_NAME on PROVIDER_SMS_ALIAS (
   NAME ASC
)
/

/*==============================================================*/
/* Table: PROVIDER_SMS_ALIAS_FIELD                              */
/*==============================================================*/
create table PROVIDER_SMS_ALIAS_FIELD  (
   ID                   INTEGER                         not null,
   EDITABLE             CHAR(1)                         not null,
   VALUE                NVARCHAR2(2000),
   PROVIDER_SMS_ALIAS_ID INTEGER                         not null,
   FIELD_DESCRIPTION_ID INTEGER                         not null,
   constraint PK_PROVIDER_SMS_ALIAS_FIELD primary key (ID)
)
/

create sequence S_PROVIDER_SMS_ALIAS_FIELD
/

create index "DXFK_ALIAS_FI_TO_ALIAS" on PROVIDER_SMS_ALIAS_FIELD
(
   PROVIDER_SMS_ALIAS_ID
)
/

alter table PROVIDER_SMS_ALIAS_FIELD
   add constraint FK_PROVIDER_FK_ALIAS__PROVIDER foreign key (PROVIDER_SMS_ALIAS_ID)
      references PROVIDER_SMS_ALIAS (ID)
/


create index "DXFK_ALIAS_FI_TO_FIELD" on PROVIDER_SMS_ALIAS_FIELD
(
   FIELD_DESCRIPTION_ID
)
/

alter table PROVIDER_SMS_ALIAS_FIELD
   add constraint FK_PROVIDER_FK_ALIAS__FIELD_DE foreign key (FIELD_DESCRIPTION_ID)
      references FIELD_DESCRIPTIONS (ID)
/


/*==============================================================*/
/* Table: PAYMENT_SERVICES_OLD                                  */
/*==============================================================*/
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
/

create sequence S_PAYMENT_SERVICES_OLD
/

create index "DXFK_P_SERVICE_TO_P_SERVICE_OL" on PAYMENT_SERVICES_OLD
(
   PARENT_ID
)
/

alter table PAYMENT_SERVICES_OLD
   add constraint FK_P_SERVICE_TO_P_SERVICE_OLD foreign key (PARENT_ID)
      references PAYMENT_SERVICES_OLD (ID)
/


/*==============================================================*/
/* Table: SERV_PROV_PAYM_SERV_OLD                               */
/*==============================================================*/
create table SERV_PROV_PAYM_SERV_OLD  (
   PAYMENT_SERVICE_ID   INTEGER                         not null,
   SERVICE_PROVIDER_ID  INTEGER                         not null,
   LIST_INDEX           INTEGER,
   constraint PK_SERV_PROV_PAYM_SERV_OLD primary key (PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID)
)
/
create sequence S_SERV_PROV_PAYM_SERV_OLD
/

create index "DXFK_PROV_PAY_SER_TO_PAY_OLD" on SERV_PROV_PAYM_SERV_OLD
(
   PAYMENT_SERVICE_ID
)
/

alter table SERV_PROV_PAYM_SERV_OLD
   add constraint FK_PROV_PAY_SER_TO_PAY_OLD foreign key (PAYMENT_SERVICE_ID)
      references PAYMENT_SERVICES_OLD (ID)
/


create index "DXFK_PROV_PAY_SER_TO_PROV_OLD" on SERV_PROV_PAYM_SERV_OLD
(
   SERVICE_PROVIDER_ID
)
/

alter table SERV_PROV_PAYM_SERV_OLD
   add constraint FK_PROV_PAY_SER_TO_PROV_OLD foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
/


create table TEMP_SP_SERVICES
(
    CODE        VARCHAR2(30) NULL,
    NAME        VARCHAR2(300) NULL,
    ICO         VARCHAR2(100) NULL,
    PARENT      VARCHAR2(30) NULL,
    PRIORITY    VARCHAR2(30) NULL,
	PRIMARY KEY(CODE)
)
/

create table TEMP_SP_UNVISIBLE
(    
    ID          VARCHAR2(30) NOT NULL,
    EXTERNAL_ID  VARCHAR2(300) NOT NULL,
    PAYMENTS    NUMBER(10) NULL,    
    PRIMARY KEY(ID)
)
/
