-- Номер ревизии: 60699
-- Номер версии: 1.18
-- Комментарий: Корзина платежей. Объект учета, подписка (услуга), счет (инвойс)
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
	constraint PK_ACCOUNTING_ENTITY primary key (ID) using index (
		create unique index PK_ACCOUNTING_ENTITY on ACCOUNTING_ENTITY(ID) 
	)
)
/
create sequence S_ACCOUNTING_ENTITY
/

create index "DXFK_ACC_ENTITY_TO_LOGIN" on ACCOUNTING_ENTITY (
   LOGIN_ID
) 
/

-- Комментарий: Форма "счета к оплате"
create table INVOICES 
(
	ID                   INTEGER              not null,
	AUTOPAY_ID           VARCHAR2(100)        not null,
	STATE                VARCHAR2(10)         not null,
	STATE_DESC           VARCHAR2(100)        not null,
	COMMISSION           NUMBER(19,4),
	EXEC_PAYMENT_DATE    TIMESTAMP            not null,
	NON_EXEC_REASON_CODE VARCHAR2(4),
	NON_EXEC_REASON_DESC VARCHAR2(100),
	CODE_RECIPIENT_BS    VARCHAR2(128),
	REC_NAME             VARCHAR2(160),
	CODE_SERVICE         VARCHAR2(50),
	NAME_SERVICE         VARCHAR2(150),
	REC_INN              VARCHAR2(12)         not null,
	REC_COR_ACCOUNT      VARCHAR2(25),
	REC_KPP              VARCHAR2(10),
	REC_BIC              VARCHAR2(10)         not null,
	REC_ACCOUNT          VARCHAR2(25)         not null,
	REC_TB               VARCHAR2(4)          not null,
	REQUISITES           CLOB                 not null,
	DELAYED_PAY_DATE     TIMESTAMP,
	REC_PHONE_NUMBER     VARCHAR2(15),
	REC_NAME_ON_BILL     VARCHAR2(250),
	INVOICE_SUBSCRIPTION_ID INTEGER,
	CREATING_DATE        TIMESTAMP,
	LOGIN_ID             INTEGER,
	PAYMENT_ID           INTEGER,
	AUTOPAY_SUBSCRIPTION_ID VARCHAR2(100)     not null,
	CARD_NUMBER          VARCHAR2(19)         not null
)
partition by range (CREATING_DATE) interval (NUMTOYMINTERVAL(3,'MONTH'))
(
	partition P_FIRST values less than (to_date('15-05-2014','DD-MM-YYYY'))
)
/

create sequence S_INVOICES
/

create index IDX_INVOICES_TO_ID on INVOICES (
   ID ASC
) global 
/

create index IDX_INVOICES_TO_STATE_LOGIN on INVOICES (
   LOGIN_ID ASC,
   STATE ASC
) local 
/

create index IDX_INVOICES_TO_SUB_ID on INVOICES (
   INVOICE_SUBSCRIPTION_ID ASC
) global  
/

create index INDEX_INVOICES_AUTOPAY_ID on INVOICES (
   AUTOPAY_ID ASC
) global  
/

-- Комментарий: Профиль: связка услуг и пользовательских документов.
create table INVOICES_FOR_USER_DOCUMENTS 
(
	LOGIN_ID             INTEGER              not null,
	DOCUMENT_TYPE        VARCHAR2(32)         not null,
	INVOICE_SUBSCRIPTION_ID INTEGER              not null,
	constraint PK_INV_FR_USR_DOCS primary key (LOGIN_ID, DOCUMENT_TYPE, INVOICE_SUBSCRIPTION_ID) using index (
		create unique index PK_INV_FR_USR_DOCS on INVOICES_FOR_USER_DOCUMENTS(LOGIN_ID, DOCUMENT_TYPE, INVOICE_SUBSCRIPTION_ID) 
	)   
)
/
create sequence S_INVOICES_FOR_USER_DOCUMENTS
/

create index IDX_FK_USR_DOCS_INVSUBID on INVOICES_FOR_USER_DOCUMENTS (
	INVOICE_SUBSCRIPTION_ID asc
) 
/

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
	PAY_DATE             TIMESTAMP            not null,
	CHARGE_OFF_CARD      VARCHAR2(25)         not null,
	STATE                VARCHAR2(15)         not null,
	CODE_RECIPIENT_BS    VARCHAR2(128),
	REC_NAME             VARCHAR2(160),
	CODE_SERVICE         VARCHAR2(50),
	NAME_SERVICE         VARCHAR2(150),
	REC_INN              VARCHAR2(12)         not null,
	REC_COR_ACCOUNT      VARCHAR2(25),
	REC_KPP              VARCHAR2(10),
	REC_BIC              VARCHAR2(10)         not null,
	REC_ACCOUNT          VARCHAR2(25)         not null,
	REC_NAME_ON_BILL     VARCHAR2(250),
	REC_PHONE_NUMBER     VARCHAR2(15),
	REC_TB               VARCHAR2(4)          not null,
	REQUISITES           CLOB                 not null,
	RECIPIENT_ID         INTEGER              not null,
	ERROR_DESC           VARCHAR2(256),
	ERROR_TYPE           VARCHAR2(20),
	DELAY_DATE           TIMESTAMP,
	CODE_BILLING         VARCHAR2(50)         not null,
	constraint PK_INVOICE_SUBSCRIPTIONS primary key (ID) using index (
		create unique index PK_INVOICE_SUBSCRIPTIONS on INVOICE_SUBSCRIPTIONS(ID) 
	)
)
/
create sequence S_INVOICE_SUBSCRIPTIONS
/

create index INDEX_INVOICE_SUB_AUTOPAY_ID on INVOICE_SUBSCRIPTIONS (
   AUTOPAY_ID ASC
) 
/
create unique index INDEX_INVOICE_SUB_REQUEST_ID on INVOICE_SUBSCRIPTIONS (
   REQUEST_ID ASC
) 
/
create index DXFK_INVOICE_SUB_TO_LOGIN on INVOICE_SUBSCRIPTIONS(
   LOGIN_ID
) 
/

-- Номер ревизии: 62329
-- Номер версии: 1.18
-- Комментарий: Адресная книга. Добавление журнала синхронизаций
create table ADDRESS_BOOK_CONTACTS_COUNT 
(
   LOGIN_ID             INTEGER              not null,
   FIO                  VARCHAR2(360)        not null,
   DOCUMENT             VARCHAR2(32)         not null,
   BIRTHDAY             TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   COUNT                INTEGER              not null
)
/

create unique index I_LOGIN_ID_AB_CONTACTS_COUNT on ADDRESS_BOOK_CONTACTS_COUNT (
   LOGIN_ID ASC
) 
/

create index I_COUNT_AB_CONTACTS_COUNT on ADDRESS_BOOK_CONTACTS_COUNT (
   COUNT ASC
) 
/

create table ADDRESS_BOOK_REQUESTS_COUNT 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   FIO                  VARCHAR2(360)        not null,
   DOCUMENT             VARCHAR2(32)         not null,
   BIRTHDAY             TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   SYNCHRONIZATION_DATE TIMESTAMP            not null,
   COUNT                INTEGER              not null
)
partition by range (SYNCHRONIZATION_DATE)  interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_START values less than (to_date('01-06-2014','DD-MM-YYYY'))
)
/
create sequence S_ADDRESS_BOOK_REQUESTS_COUNT
/

create index I_COUNT_AB_REQUESTS_COUNT_DATE on ADDRESS_BOOK_REQUESTS_COUNT (
   SYNCHRONIZATION_DATE ASC
) local 
/

create index I_ADDRESS_BOOK_REQUESTS_COUNT on ADDRESS_BOOK_REQUESTS_COUNT (
   LOGIN_ID ASC,
   SYNCHRONIZATION_DATE ASC
) local 


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
	constraint PK_ALF_RECATEGORIZATION_RULES primary key (ID) using index (
		create unique index PK_ALF_RECATEGORIZATION_RULES on ALF_RECATEGORIZATION_RULES(ID) 
	)
)
/
create sequence S_ALF_RECATEGORIZATION_RULES
/

create unique index I_ALF_RECATEGORIZATION_RULES on ALF_RECATEGORIZATION_RULES (
   LOGIN_ID ASC,
   MCC_CODE ASC,
   DESCRIPTION ASC
) 
/
create index "DXFK_RULE_TO_CATEGORY" on ALF_RECATEGORIZATION_RULES
(
   NEW_CATEGORY_ID
) 
/

-- Номер ревизии: 60736
-- Номер версии: 1.18
-- Комментарий: Справочник кодов стран
create table COUNTRY_CODES 
(
	ISO3                 varchar2(3)          not null,
	ISO2                 varchar2(2)          not null,
	NAME                 varchar2(256)        not null,
	constraint PK_COUNTRY_CODES primary key (ISO3) using index (
		create unique index PK_COUNTRY_CODES on COUNTRY_CODES(ISO3) 
	)
)
/

create unique index IDX_COUNTRY_CODES on COUNTRY_CODES (
   ISO2 ASC
)
/

insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Австралия', 'AU', 'AUS') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Австрия', 'AT', 'AUT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Азербайджан', 'AZ', 'AZE') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Аландские острова', 'AX', 'ALA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Албания', 'AL', 'ALB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Алжир', 'DZ', 'DZA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Американские Виргинские острова', 'VI', 'VIR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Американское Самоа', 'AS', 'ASM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Ангилья', 'AI', 'AIA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Ангола', 'AO', 'AGO') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Андорра', 'AD', 'AND') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Антарктида', 'AQ', 'ATA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Антигуа и Барбуда', 'AG', 'ATG') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Аргентина', 'AR', 'ARG') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Армения', 'AM', 'ARM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Аруба', 'AW', 'ABW') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Афганистан', 'AF', 'AFG') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Багамы', 'BS', 'BHS') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Бангладеш', 'BD', 'BGD') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Барбадос', 'BB', 'BRB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Бахрейн', 'BH', 'BHR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Белиз', 'BZ', 'BLZ') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Белоруссия', 'BY', 'BLR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Бельгия', 'BE', 'BEL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Бенин', 'BJ', 'BEN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Бермуды', 'BM', 'BMU') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Болгария', 'BG', 'BGR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Боливия', 'BO', 'BOL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Бонэйр, Синт-Эстатиус и Саба', 'BQ', 'BES') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Босния и Герцеговина', 'BA', 'BIH') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Ботсвана', 'BW', 'BWA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Бразилия', 'BR', 'BRA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Британская территория в Индийском океане', 'IO', 'IOT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Британские Виргинские острова', 'VG', 'VGB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Бруней', 'BN', 'BRN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Буркина-Фасо', 'BF', 'BFA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Бурунди', 'BI', 'BDI') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Бутан', 'BT', 'BTN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Вануату', 'VU', 'VUT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Ватикан', 'VA', 'VAT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Великобритания', 'GB', 'GBR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Венгрия', 'HU', 'HUN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Венесуэла', 'VE', 'VEN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Внешние малые острова (США)', 'UM', 'UMI') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Восточный Тимор', 'TL', 'TLS') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Вьетнам', 'VN', 'VNM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Габон', 'GA', 'GAB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гаити', 'HT', 'HTI') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гайана', 'GY', 'GUY') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гамбия', 'GM', 'GMB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гана', 'GH', 'GHA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гваделупа', 'GP', 'GLP') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гватемала', 'GT', 'GTM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гвиана', 'GF', 'GUF') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гвинея', 'GN', 'GIN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гвинея-Бисау', 'GW', 'GNB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Германия', 'DE', 'DEU') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гернси', 'GG', 'GGY') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гибралтар', 'GI', 'GIB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гондурас', 'HN', 'HND') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гонконг', 'HK', 'HKG') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гренада', 'GD', 'GRD') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гренландия', 'GL', 'GRL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Греция', 'GR', 'GRC') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Грузия', 'GE', 'GEO') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Гуам', 'GU', 'GUM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Дания', 'DK', 'DNK') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Джерси (остров)', 'JE', 'JEY') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Джибути', 'DJ', 'DJI') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Доминика', 'DM', 'DMA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Доминиканская Республика', 'DO', 'DOM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('ДР Конго', 'CD', 'COD') 
/   
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Египет', 'EG', 'EGY') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Замбия', 'ZM', 'ZMB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Западная Сахара', 'EH', 'ESH') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Зимбабве', 'ZW', 'ZWE') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Израиль', 'IL', 'ISR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Индия', 'IN', 'IND') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Индонезия', 'ID', 'IDN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Иордания', 'JO', 'JOR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Ирак', 'IQ', 'IRQ') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Иран', 'IR', 'IRN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Ирландия', 'IE', 'IRL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Исландия', 'IS', 'ISL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Испания', 'ES', 'ESP') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Италия', 'IT', 'ITA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Йемен', 'YE', 'YEM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Кабо-Верде', 'CV', 'CPV') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Казахстан', 'KZ', 'KAZ') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Каймановы острова', 'KY', 'CYM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Камбоджа', 'KH', 'KHM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Камерун', 'CM', 'CMR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Канада', 'CA', 'CAN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Катар', 'QA', 'QAT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Кения', 'KE', 'KEN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Кипр', 'CY', 'CYP') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Киргизия', 'KG', 'KGZ') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Кирибати', 'KI', 'KIR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Китайская Республика', 'TW', 'TWN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('КНДР', 'KP', 'PRK') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('КНР', 'CN', 'CHN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Кокосовые острова', 'CC', 'CCK') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Колумбия', 'CO', 'COL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Коморы', 'KM', 'COM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Коста-Рика', 'CR', 'CRI') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Кот-д’Ивуар', 'CI', 'CIV') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Куба', 'CU', 'CUB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Кувейт', 'KW', 'KWT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Кюрасао', 'CW', 'CUW') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Лаос', 'LA', 'LAO') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Латвия', 'LV', 'LVA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Лесото', 'LS', 'LSO') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Либерия', 'LR', 'LBR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Ливан', 'LB', 'LBN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Ливия', 'LY', 'LBY') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Литва', 'LT', 'LTU') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Лихтенштейн', 'LI', 'LIE') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Люксембург', 'LU', 'LUX') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Маврикий', 'MU', 'MUS') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Мавритания', 'MR', 'MRT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Мадагаскар', 'MG', 'MDG') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Майотта', 'YT', 'MYT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Макао', 'MO', 'MAC') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Македония', 'MK', 'MKD') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Малави', 'MW', 'MWI') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Малайзия', 'MY', 'MYS') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Мали', 'ML', 'MLI') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Мальдивы', 'MV', 'MDV') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Мальта', 'MT', 'MLT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Марокко', 'MA', 'MAR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Мартиника', 'MQ', 'MTQ') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Маршалловы Острова', 'MH', 'MHL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Мексика', 'MX', 'MEX') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Микронезия', 'FM', 'FSM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Мозамбик', 'MZ', 'MOZ') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Молдавия', 'MD', 'MDA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Монако', 'MC', 'MCO') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Монголия', 'MN', 'MNG') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Монтсеррат', 'MS', 'MSR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Мьянма', 'MM', 'MMR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Намибия', 'NA', 'NAM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Науру', 'NR', 'NRU') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Непал', 'NP', 'NPL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Нигер', 'NE', 'NER') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Нигерия', 'NG', 'NGA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Нидерланды', 'NL', 'NLD') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Никарагуа', 'NI', 'NIC') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Ниуэ', 'NU', 'NIU') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Новая Зеландия', 'NZ', 'NZL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Новая Каледония', 'NC', 'NCL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Норвегия', 'NO', 'NOR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('ОАЭ', 'AE', 'ARE') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Оман', 'OM', 'OMN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Остров Буве', 'BV', 'BVT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Остров Мэн', 'IM', 'IMN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Острова Кука', 'CK', 'COK') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Остров Норфолк', 'NF', 'NFK') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Остров Рождества', 'CX', 'CXR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Острова Питкэрн', 'PN', 'PCN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Острова Святой Елены, Вознесения и Тристан-да-Кунья', 'SH', 'SHN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Пакистан', 'PK', 'PAK') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Палау', 'PW', 'PLW') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Государство Палестина', 'PS', 'PSE') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Панама', 'PA', 'PAN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Папуа — Новая Гвинея', 'PG', 'PNG') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Парагвай', 'PY', 'PRY') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Перу', 'PE', 'PER') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Польша', 'PL', 'POL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Португалия', 'PT', 'PRT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Пуэрто-Рико', 'PR', 'PRI') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Республика Конго', 'CG', 'COG') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Республика Корея', 'KR', 'KOR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Реюньон', 'RE', 'REU') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Россия', 'RU', 'RUS') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Руанда', 'RW', 'RWA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Румыния', 'RO', 'ROU') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сальвадор', 'SV', 'SLV') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Самоа', 'WS', 'WSM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сан-Марино', 'SM', 'SMR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сан-Томе и Принсипи', 'ST', 'STP') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Саудовская Аравия', 'SA', 'SAU') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Свазиленд', 'SZ', 'SWZ') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Северные Марианские острова', 'MP', 'MNP') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сейшельские Острова', 'SC', 'SYC') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сен-Бартелеми', 'BL', 'BLM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сен-Мартен', 'MF', 'MAF') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сен-Пьер и Микелон', 'PM', 'SPM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сенегал', 'SN', 'SEN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сент-Винсент и Гренадины', 'VC', 'VCT') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сент-Китс и Невис', 'KN', 'KNA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сент-Люсия', 'LC', 'LCA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сербия', 'RS', 'SRB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сингапур', 'SG', 'SGP') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Синт-Мартен', 'SX', 'SXM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сирия', 'SY', 'SYR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Словакия', 'SK', 'SVK') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Словения', 'SI', 'SVN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Соломоновы Острова', 'SB', 'SLB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сомали', 'SO', 'SOM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Судан', 'SD', 'SDN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('СССР', 'SU', 'SUN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Суринам', 'SR', 'SUR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('США', 'US', 'USA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Сьерра-Леоне', 'SL', 'SLE') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Таджикистан', 'TJ', 'TJK') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Таиланд', 'TH', 'THA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Танзания', 'TZ', 'TZA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Тёркс и Кайкос', 'TC', 'TCA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Того', 'TG', 'TGO') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Токелау', 'TK', 'TKL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Тонга', 'TO', 'TON') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Тринидад и Тобаго', 'TT', 'TTO') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Тувалу', 'TV', 'TUV') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Тунис', 'TN', 'TUN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Туркмения', 'TM', 'TKM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Турция', 'TR', 'TUR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Уганда', 'UG', 'UGA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Узбекистан', 'UZ', 'UZB') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Украина', 'UA', 'UKR') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Уоллис и Футуна', 'WF', 'WLF') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Уругвай', 'UY', 'URY') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Фарерские острова', 'FO', 'FRO') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Фиджи', 'FJ', 'FJI') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Филиппины', 'PH', 'PHL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Финляндия', 'FI', 'FIN') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Фолклендские острова', 'FK', 'FLK') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Франция', 'FR', 'FRA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Французская Полинезия', 'PF', 'PYF') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Французские Южные и Антарктические Территории', 'TF', 'ATF') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Херд и Макдональд', 'HM', 'HMD') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Хорватия', 'HR', 'HRV') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('ЦАР', 'CF', 'CAF') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Чад', 'TD', 'TCD') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Черногория', 'ME', 'MNE') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Чехия', 'CZ', 'CZE') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Чили', 'CL', 'CHL') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Швейцария', 'CH', 'CHE') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Швеция', 'SE', 'SWE') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Шпицберген и Ян-Майен', 'SJ', 'SJM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Шри-Ланка', 'LK', 'LKA') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Эквадор', 'EC', 'ECU') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Экваториальная Гвинея', 'GQ', 'GNQ') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Эритрея', 'ER', 'ERI') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Эстония', 'EE', 'EST') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Эфиопия', 'ET', 'ETH') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('ЮАР', 'ZA', 'ZAF') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Южная Георгия и Южные Сандвичевы острова', 'GS', 'SGS') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Южный Судан', 'SS', 'SSD') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Ямайка', 'JM', 'JAM') 
/ 
insert into COUNTRY_CODES (NAME, ISO2, ISO3) values('Япония', 'JP', 'JPN') 
/

-- Номер ревизии: 62844
-- Номер версии: 1.18
-- Комментарий: Загрузка справочника DEF-кодов в АРМ сотрудника
create table DEF_CODES 
(
	ID                   INTEGER              not null,
	DEF_CODE_FROM        NUMBER               not null,
	DEF_CODE_TO          NUMBER               not null,
	PROVIDER_CODE        VARCHAR(20)          not null,
	constraint PK_DEF_CODES primary key (ID) using index (
		create unique index PK_DEF_CODES on DEF_CODES(ID) 
	)
)
/

create sequence S_DEF_CODES
/

create index IDX_DEFCODES_PHONE_RANGE on DEF_CODES (
   DEF_CODE_FROM DESC,
   DEF_CODE_TO ASC,
   PROVIDER_CODE ASC
) 
/

-- Номер ревизии: 61676
-- Номер версии: 1.18
-- Комментарий: Структура БД. Таблицы для адресов
create table LOANCLAIM_AREA 
(
	CODE                 varchar2(20)         not null,
	NAME                 varchar2(255)        not null,
	TYPEOFAREA           varchar2(20)         not null,
	REGION               varchar2(20)         not null,
	constraint PK_LOANCLAIM_AREA primary key (CODE) using index (
		create unique index PK_LOANCLAIM_AREA on LOANCLAIM_AREA(CODE) 
	)
)
/
create sequence S_LOANCLAIM_AREA
/
create index DXLC_AREA_TYPEOFAREA_FK on LOANCLAIM_AREA(
   TYPEOFAREA
) 
/

create index LOANCLAIM_AREA_REGION_IDX on LOANCLAIM_AREA (
   REGION ASC,
   LOWER(NAME) ASC
) 
/

create table LOANCLAIM_CITY 
(
	CODE                 varchar2(20)         not null,
	NAME                 varchar2(255)        not null,
	TYPEOFCITY           varchar2(20)         not null,
	AREA                 NUMBER(20,0)         not null,
	REGION               varchar2(20)         not null,
	constraint PK_LOANCLAIM_CITY primary key (CODE) using index (
		create unique index PK_LOANCLAIM_CITY on LOANCLAIM_CITY(CODE) 
	)
)
/
create sequence S_LOANCLAIM_CITY
/

create index "DXLC_CITY_TYPEOFCITY_FK" on LOANCLAIM_CITY(
   TYPEOFCITY
) 
/

create index LOANCLAIM_CITY_REGION_IDX on LOANCLAIM_CITY (
   REGION ASC,
   LOWER(NAME) ASC
) 
/

create table LOANCLAIM_SETTLEMENT 
(
	CODE                 varchar2(20)         not null,
	NAME                 varchar2(255)        not null,
	TYPEOFLOCALITY       varchar2(20)         not null,
	CITY                 NUMBER(20,0)         not null,
	AREA                 NUMBER(20,0)         not null,
	REGION               varchar2(20)         not null,
	constraint PK_LOANCLAIM_SETTLEMENT primary key (CODE) using index (
		create unique index PK_LOANCLAIM_SETTLEMENT on LOANCLAIM_SETTLEMENT(CODE) 
	)	
)
/
create sequence S_LOANCLAIM_SETTLEMENT
/

create index "DXLC_SETTL_TYPEOFLOCALITY_FK" on LOANCLAIM_SETTLEMENT(
   TYPEOFLOCALITY
) 
/

create index LOANCLAIM_SETTL_REGION_IDX on LOANCLAIM_SETTLEMENT (
   REGION ASC,
   LOWER(NAME) ASC
) 
/

create table LOANCLAIM_STREET 
(
	CODE                 varchar2(20)         not null,
	NAME                 varchar2(255)        not null,
	TYPEOFSTREET         varchar2(20)         not null,
	SETTLEMENT           NUMBER(20,0)         not null,
	CITY                 NUMBER(20,0)         not null,
	AREA                 NUMBER(20,0)         not null,
	REGION               varchar2(20)         not null,
	constraint PK_LOANCLAIM_STREET primary key (CODE) using index (
		create unique index PK_LOANCLAIM_STREET on LOANCLAIM_STREET(CODE) 
	)	
)
/
create sequence S_LOANCLAIM_STREET
/

create index "DXLC_STREET_TYPEOFSTREET_FK" on LOANCLAIM_STREET
(
   TYPEOFSTREET
) 
/
create index LOANCLAIM_STREET_REGION_IDX on LOANCLAIM_STREET (
   REGION ASC,
   LOWER(NAME) ASC
) 
/

-- Номер ревизии: 61537
-- Номер версии: 1.18
-- Комментарий: Информирование клиента: статус по операциям
create table PAYMENT_NOTIFICATIONS 
(
   ID                   INTEGER              not null,
   LOGIN                INTEGER              not null,
   DATE_CREATED         TIMESTAMP            not null,
   NAME                 VARCHAR2(63)         not null,
   DOCUMENT_STATE       VARCHAR2(25)         not null,
   ACCOUNT_NUMBER       VARCHAR2(25),
   ACCOUNT_RESOURCE_TYPE VARCHAR2(32),
   TRANSACTION_SUM      NUMBER(15,4),
   CURRENCY             VARCHAR2(5),
   NAME_OR_TYPE         VARCHAR2(56),
   DOCUMENT_TYPE        VARCHAR2(50),
   NAME_AUTO_PAYMENT    VARCHAR2(100),
   RECIPIENT_ACCOUNT_NUMBER VARCHAR2(25),
   RECIPIENT_ACCOUNT_TYPE VARCHAR2(32)
)
partition by range (DATE_CREATED) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_FIRST values less than (to_date('15-05-2014','DD-MM-YYYY'))
)
/
create sequence S_PAYMENT_NOTIFICATIONS cache 10000
/

create index IND_PAY_NOTIF_ID on PAYMENT_NOTIFICATIONS (
   ID ASC
) local 
/
create index IND_PAY_NOTIF1 on PAYMENT_NOTIFICATIONS (
   LOGIN ASC,
   DATE_CREATED ASC
) local 
/
create index IND_PAY_NOTIF2 on PAYMENT_NOTIFICATIONS (
   DATE_CREATED ASC,
   LOGIN ASC
) local 
/

-- Номер ревизии: 61878
-- Номер версии: 1.18
-- Комментарий: Профиль. пользовательские документы
create table USER_ADDED_DOCUMENTS 
(
	ID                   INTEGER              not null,
	LOGIN_ID             INTEGER              not null,
	DOCUMENT_TYPE        VARCHAR2(10)         not null,
	DOC_NAME             VARCHAR2(200),
	SERIES               VARCHAR2(4),
	DOC_NUMBER           VARCHAR2(20),
	ISSUE_DATE           TIMESTAMP,
	EXPIRE_DATE          TIMESTAMP,
	ISSUE_BY             VARCHAR2(150),
	constraint PK_USER_ADDED_DOCUMENTS primary key (ID) using index (
		create unique index PK_USER_ADDED_DOCUMENTS on USER_ADDED_DOCUMENTS(ID) 
	)   
)
/
create sequence S_USER_ADDED_DOCUMENTS
/
create unique index IDX_DOC_TYPE_USER_ID on USER_ADDED_DOCUMENTS (
   LOGIN_ID ASC,
   DOCUMENT_TYPE ASC
) 
/

-- Номер ревизии: 61045
-- Номер версии: 1.18
-- Комментарий: Профиль: Работа с аватарами
create table USER_IMAGES 
(
	ID                   INTEGER              not null,
	PATH                 VARCHAR2(256)        not null,
	constraint PK_USER_IMAGES primary key (ID) using index (
		create unique index PK_USER_IMAGES on USER_IMAGES(ID) 
	)  
)
/
create sequence S_USER_IMAGES
/

-- Номер ревизии: 60644
-- Номер версии: 1.18
-- Комментарий: Профиль: пользовательские настройки
create table USER_PROPERTIES 
(
	ID                   INTEGER              not null,
	LOGIN_ID             INTEGER              not null,
	PROPERTY_KEY         VARCHAR2(256)        not null,
	PROPERTY_VALUE       VARCHAR2(256),
	constraint PK_USER_PROPERTIES primary key (ID) using index (
		create unique index PK_USER_PROPERTIES on USER_PROPERTIES(ID) 
	)     
)
/
create sequence S_USER_PROPERTIES
/
create unique index IDX_LOGIN_USER_PROPERTIES on USER_PROPERTIES (
   LOGIN_ID ASC,
   PROPERTY_KEY ASC
) 
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
	constraint PK_USER_SOCIAL_IDS primary key (ID) using index (
		create unique index PK_USER_SOCIAL_IDS on USER_SOCIAL_IDS(ID) 
	)     
)
/
create sequence S_USER_SOCIAL_IDS
/
create index "DXUSER_SOC_IDS_TO_LOGINS" on USER_SOCIAL_IDS
(
   LOGIN_ID
) 
/

-- Номер ревизии: 60666
-- Номер версии: 1.18
-- Комментарий: WebAPI. Сделать страницу переадресации на СБОЛ 3.
create table WEBAPI_TOKENS 
(
	TOKEN                VARCHAR2(32)         not null,
	LOGIN_ID             INTEGER              not null,
	CREATION_DATE        TIMESTAMP            not null,
	constraint PK_WEBAPI_TOKENS primary key (TOKEN) using index (
		create unique index PK_WEBAPI_TOKENS on WEBAPI_TOKENS(TOKEN) 
	)   	
)
/

-- Номер ревизии: 63239
-- Номер версии: 1.18
-- Комментарий: CHG074183: Добавить возможность многопоточных запросов в ИПС
create table PROCESSED_CARD_OP_CLAIM_LOGIN 
(
	LOGIN_ID             INTEGER              not null,
	PROCESSING_DATE      TIMESTAMP            not null,
	constraint PK_PROCESSED_CARD_OP_CLAIM_LOG primary key (LOGIN_ID) using index (
		create index PK_PROCESSED_CARD_OP_CLAIM_LOG on PROCESSED_CARD_OP_CLAIM_LOGIN(LOGIN_ID) 
 	)
)
/

create index PROCESSED_CARD_OP_DATE_IDX on PROCESSED_CARD_OP_CLAIM_LOGIN (
   PROCESSING_DATE ASC
) 
/


