declare
  c_PrimarySchema constant varchar2(100) := 'SBRF_118';
  c_SecondarySchema constant varchar2(100) := 'ESBERIB';
begin
   execute immediate
     'create user '||c_SecondarySchema||' profile "DEFAULT" identified by '||c_SecondarySchema||' account unlock
             default tablespace "USERS"
             temporary tablespace "TEMP"
             quota unlimited on "USERS"';
   execute immediate
   'grant connect, resource, create view to '||c_SecondarySchema;

  execute immediate 'alter session set current_schema = '||c_SecondarySchema;
  execute immediate
    'CREATE TABLE  GFL
    (
      ID          INTEGER,
      RBTBBRCHID  VARCHAR2(8 BYTE)                  NOT NULL,
      BIRTHDAY    DATE                              NOT NULL,
      LASTNAME    VARCHAR2(120 BYTE)                NOT NULL,
      FIRSTNAME   VARCHAR2(120 BYTE)                NOT NULL,
      MIDDLENAME  VARCHAR2(120 BYTE),
      IDTYPE      VARCHAR2(4 BYTE)                  NOT NULL,
      IDSERIES    VARCHAR2(12 BYTE),
      IDNUM       VARCHAR2(12 BYTE)                 NOT NULL,
      ISSUEDBY    VARCHAR2(255 BYTE),
      ISSUEDT     DATE,
      CARDNUM     VARCHAR2(20 BYTE)
    )';

  execute immediate 'COMMENT ON COLUMN  GFL.RBTBBRCHID IS ''Код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию на УС''';
  execute immediate 'COMMENT ON COLUMN  GFL.BIRTHDAY IS ''Информация о физическом лице. Дата рождения''';
  execute immediate 'COMMENT ON COLUMN  GFL.LASTNAME IS ''Фамилия физического лица''';

  execute immediate 'COMMENT ON COLUMN  GFL.FIRSTNAME IS ''Имя физического лица''';

  execute immediate 'COMMENT ON COLUMN  GFL.MIDDLENAME IS ''Отчество физического лица''';

  execute immediate 'COMMENT ON COLUMN  GFL.IDTYPE IS ''Тип документа. Типы документов, удостоверяющих личность.  Используется кодировка справочника pb_document ЦАС НСИ (поле code)''';

  execute immediate 'COMMENT ON COLUMN  GFL.IDSERIES IS ''Серия документа''';

  execute immediate 'COMMENT ON COLUMN  GFL.IDNUM IS ''Номер документа. Для паспорта way номер документа передается в этом в том виде, как пришло из ЦСА.''';

  execute immediate 'COMMENT ON COLUMN  GFL.ISSUEDBY IS ''Кем выдан''';

  execute immediate 'COMMENT ON COLUMN  GFL.ISSUEDT IS ''Дата выдачи''';

  execute immediate 'COMMENT ON COLUMN  GFL.CARDNUM IS ''Идентификатор карточного счета. Номер карты''';

  execute immediate 'CREATE UNIQUE INDEX  GFL_PK ON  GFL (ID)';

  execute immediate 'CREATE INDEX  IDX_GFL ON  GFL (IDNUM, RBTBBRCHID)';

  execute immediate 'ALTER TABLE  GFL ADD ( CONSTRAINT GFL_PK PRIMARY KEY (ID))';

  execute immediate '
  CREATE TABLE  PERSONINFO
  (
    ID                   INTEGER                  NOT NULL,
    CUSTID               VARCHAR2(255 BYTE)       NOT NULL,
    BIRTHDAY             DATE                     NOT NULL,
    BIRTHPLACE           VARCHAR2(255 BYTE),
    TAXID                VARCHAR2(15 BYTE),
    CITIZENSHIP          VARCHAR2(60 BYTE),
    LASTNAME             VARCHAR2(120 BYTE)       NOT NULL,
    FIRSTNAME            VARCHAR2(120 BYTE)       NOT NULL,
    MIDDLENAME           VARCHAR2(120 BYTE),
    IDTYPE               VARCHAR2(4 BYTE)         NOT NULL,
    IDSERIES             VARCHAR2(12 BYTE),
    IDNUM                VARCHAR2(12 BYTE)        NOT NULL,
    ISSUEDBY             VARCHAR2(255 BYTE),
    ISSUEDCODE           VARCHAR2(10 BYTE),
    ISSUEDT              DATE,
    EXPDT                DATE,
    ADDITIONALINFO       VARCHAR2(255 BYTE),
    EMAILADDR            VARCHAR2(255 BYTE),
    MESSAGEDELIVERYTYPE  VARCHAR2(1 BYTE)         NOT NULL
  )';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.TAXID IS ''ИНН''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.CITIZENSHIP IS ''Гражданство''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.LASTNAME IS ''Фамилия''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.FIRSTNAME IS ''Имя''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.MIDDLENAME IS ''Отчество''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.IDTYPE IS ''Тип документа. Типы документов, удостоверяющих личность. Используется кодировка справочника pb_document ЦАС НСИ (поле code)''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.IDSERIES IS ''Серия документа.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.IDNUM IS ''Номер документа. Для паспорта way номер документа передается в этом в том виде, как пришло из ЦСА.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.ISSUEDBY IS ''Кем выдан.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.ISSUEDCODE IS ''Код подразделения.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.ISSUEDT IS ''Дата выдачи.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.EXPDT IS ''Действителен до.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.ADDITIONALINFO IS ''Дополнительная информация.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.EMAILADDR IS ''Адрес электронной почты.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.MESSAGEDELIVERYTYPE IS ''Отправляется ли отчет на Email? E – да, N – нет.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.CUSTID IS ''Идентификатор потребителя.''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.BIRTHDAY IS ''Дата рождения''';

  execute immediate 'COMMENT ON COLUMN  PERSONINFO.BIRTHPLACE IS ''Место рождения''';


  execute immediate 'CREATE UNIQUE INDEX  PERSONINFO_PK ON  PERSONINFO (ID)';


  execute immediate 'ALTER TABLE  PERSONINFO ADD ( CONSTRAINT PERSONINFO_PK PRIMARY KEY (ID))';         

  execute immediate 'CREATE TABLE  CARDS (
    ID                  INTEGER                   NOT NULL,
    BRANCHID            VARCHAR2(5 BYTE)          NOT NULL,
    AGENCYID            VARCHAR2(5 BYTE)          NOT NULL,
    REGIONID            VARCHAR2(3 BYTE)          NOT NULL,
    RBBRCHID            VARCHAR2(6 BYTE)          NOT NULL,
    BANKACCTSTATUSCODE  VARCHAR2(16 BYTE),
    SYSTEMID            VARCHAR2(40 BYTE)         NOT NULL,
    CARDNUM             VARCHAR2(20 BYTE)         NOT NULL,
    ACCTID              VARCHAR2(24 BYTE),
    ACCTCODE            INTEGER                   NOT NULL,
    ACCTSUBCODE         INTEGER                   NOT NULL,
    CARDTYPE            VARCHAR2(2 BYTE)          NOT NULL,
    CARDNAME            VARCHAR2(50 BYTE)         NOT NULL,
    ACCTCUR             VARCHAR2(3 BYTE)          NOT NULL,
    ENDDT               DATE                      NOT NULL,
    PMTDT               DATE,
    ISSDT               DATE                      NOT NULL,
    CARDHOLDER          VARCHAR2(80 BYTE)         NOT NULL,
    MAINCARD            VARCHAR2(20 BYTE),
    ADDITIONALCARD      VARCHAR2(15 BYTE),
    ENDDTFORWAY         VARCHAR2(4 BYTE),
    ACCTBAL             VARCHAR2(255 BYTE),
    PERSONINFO          INTEGER,
    STATUSDESC          VARCHAR2(255 BYTE)
  )';

  execute immediate 'COMMENT ON COLUMN  CARDS.BRANCHID IS ''Номер филиала, в котором ведется договор. Не может быть пустым''';

  execute immediate 'COMMENT ON COLUMN  CARDS.AGENCYID IS ''Номер ОСБ''';

  execute immediate 'COMMENT ON COLUMN  CARDS.REGIONID IS ''Номер тербанка(ТБ) ''';

  execute immediate 'COMMENT ON COLUMN  CARDS.RBBRCHID IS ''Номер ОСБ, ведущего счет карты''';

  execute immediate 'COMMENT ON COLUMN  CARDS.BANKACCTSTATUSCODE IS ''Код статуса. Active – Активная, Stoped – Неактивная, Blocked – Заблокированная''';

  execute immediate 'COMMENT ON COLUMN  CARDS.SYSTEMID IS ''Идентификатор системы-источника продукта''';

  execute immediate 'COMMENT ON COLUMN  CARDS.CARDNUM IS ''Номер карты''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ACCTID IS ''Номер карточного счета ''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ACCTCODE IS ''Вид вклада''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ACCTSUBCODE IS ''Подвид вклада''';

  execute immediate 'COMMENT ON COLUMN  CARDS.CARDTYPE IS ''Тип карты. debit (дебетовая), credit (кредитная), overdraft (овердрафтная)''';

  execute immediate 'COMMENT ON COLUMN  CARDS.CARDNAME IS ''Наименование карты. Наименование типа карты, готовое для отображения пользователю.''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ACCTCUR IS ''Валюта''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ENDDT IS ''Дата окончания действия карты''';

  execute immediate 'COMMENT ON COLUMN  CARDS.PMTDT IS ''Дата платежа''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ISSDT IS ''Дата выпуска карты''';

  execute immediate 'COMMENT ON COLUMN  CARDS.CARDHOLDER IS ''Имя на карте''';

  execute immediate 'COMMENT ON COLUMN  CARDS.MAINCARD IS ''Номер основной карты. Обязателен в случае, если данная карта – дополнительная, иначе должен отсутствовать''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ADDITIONALCARD IS ''Тип дополнительной карты. Обязателен в случае, если данная карта – дополнительная, иначе должен отсутствовать. Возможные значения: «Client2Client» - карта, выпущенная на имя клиента и к его собственному счету, «Client2Other» - владелец счета карты и держатель разные лица.''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ENDDTFORWAY IS ''Срок действия карты в формате процессинга YYMM ''';

  execute immediate 'COMMENT ON COLUMN  CARDS.ACCTBAL IS ''Тип остатка: 1)доступный расходный лимит по карте (“Avail”), 2)доступный лимит для получения наличных (“AvailCash”), 3)доступный лимит для оплаты товаров/услуг (“AvailPmt”) Для кредитных карт и карт с овердрафтом: 4)Сумма задолженности («Debt») 5)Сумма минимального платежа («MinPmt») 6) сумма кредитного лимита и дополнительного лимита авторизации контракта («CR_LIMIT») 7) собственный баланс контракта без учета балансов подчиненных liability-контрактов («OWN_BALANCE»)''';

  execute immediate 'COMMENT ON COLUMN  CARDS.PERSONINFO IS ''Информация о владельце.''';

  execute immediate 'COMMENT ON COLUMN  CARDS.STATUSDESC IS ''Описание статуса карты''';


  execute immediate 'CREATE UNIQUE INDEX  CARDS_PK ON  CARDS (ID)';


  execute immediate 'ALTER TABLE  CARDS ADD ( CONSTRAINT CARDS_PK PRIMARY KEY (ID))';

  execute immediate 'ALTER TABLE  CARDS ADD ( FOREIGN KEY (PERSONINFO) REFERENCES  PERSONINFO (ID))';

  execute immediate 'CREATE TABLE  DEPOSIT
  (
    ID                          INTEGER           NOT NULL,
    BRANCHID                    VARCHAR2(5 BYTE)  NOT NULL,
    AGENCYID                    VARCHAR2(5 BYTE)  NOT NULL,
    REGIONID                    VARCHAR2(3 BYTE)  NOT NULL,
    RBBRCHID                    VARCHAR2(6 BYTE)  NOT NULL,
    SYSTEMID                    VARCHAR2(40 BYTE) NOT NULL,
    ACCTID                      VARCHAR2(24 BYTE) NOT NULL,
    ACCTCUR                     VARCHAR2(3 BYTE)  NOT NULL,
    ACCTNAME                    VARCHAR2(255 BYTE) NOT NULL,
    ACCTCODE                    INTEGER           NOT NULL,
    ACCTSUBCODE                 INTEGER           NOT NULL,
    OPENDATE                    DATE,
    STATUS                      VARCHAR2(16 BYTE),
    INTERESTONDEPOSITACCTID     VARCHAR2(24 BYTE),
    INTERESTONDEPOSITCARDNUM    VARCHAR2(20 BYTE),
    PERIOD                      INTEGER,
    RATE                        NUMBER(15,4),
    ENDDATE                     DATE,
    ISCREDITALLOWED             CHAR(1 BYTE)      DEFAULT 0,
    ISDEBITALLOWED              CHAR(1 BYTE)      DEFAULT 0,
    ISPROLONGATIONALLOWED       CHAR(1 BYTE)      DEFAULT 0,
    ISCREDITCROSSAGENCYALLOWED  CHAR(1 BYTE)      DEFAULT 0,
    ISDEBITCROSSAGENCYALLOWED   CHAR(1 BYTE)      DEFAULT 0,
    ISPASSBOOK                  CHAR(1 BYTE)      DEFAULT 0,
    BANKACCTPERMISS             VARCHAR2(255 BYTE),
    ACCTBAL                     VARCHAR2(255 BYTE) NOT NULL,
    PERSONINFO                  INTEGER,
    ISNEEDDEPACCINFO            CHAR(1 BYTE)      DEFAULT 0                     NOT NULL,
    ISNEEDBANKACCTPERMISS       CHAR(1 BYTE)      DEFAULT 0                     NOT NULL
  )';

  execute immediate 'COMMENT ON COLUMN  DEPOSIT.BRANCHID IS ''Номер филиала, в котором открыт счет''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.AGENCYID IS ''Идентификатор отделения (ОСБ), в котором открыт счет''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.REGIONID IS ''Номер тербанка(ТБ)''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.RBBRCHID IS ''Номер ОСБ, ведущего счет по вкладу''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.SYSTEMID IS ''Идентификатор системы-источника продукта''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTID IS ''Номер счета''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTCUR IS ''Валюта счета''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTNAME IS ''Краткое наименование счета из поля qsname справочника qvb ЦАС НСИ Должен приходить текст на русском языке полностью готовый для отображения пользователю как есть.''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTCODE IS ''Вид вклада''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTSUBCODE IS ''Подвид вклада''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.OPENDATE IS ''Дата открытия счета''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.STATUS IS ''Статус счета :Opened – Открыт,.Closed – Закрыт, Arrested – Арестован, Lost-passbook – Утеряна сберкнижка.''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.INTERESTONDEPOSITACCTID IS ''Информация, предназначенная для перечисления процентов. Номер счета''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.INTERESTONDEPOSITCARDNUM IS ''Информация, предназначенная для перечисления процентов. Номер карты''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.PERIOD IS ''Срок вклада в днях''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.RATE IS ''Процентная ставка по вкладу''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ENDDATE IS ''Дата закрытия счета''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISCREDITALLOWED IS ''Разрешено ли списание со счета''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISDEBITALLOWED IS ''Разрешено ли зачисление на счет''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISPROLONGATIONALLOWED IS ''Разрешена ли пролонгация на следующий срок''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISCREDITCROSSAGENCYALLOWED IS ''Разрешено ли списание со счета в других ОСБ (признак «Зеленая улица»)''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISDEBITCROSSAGENCYALLOWED IS ''Разрешено ли зачисление на счет в других ОСБ (признак «Зеленая улица»)''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISPASSBOOK IS ''Признак наличия сберкнижки''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.BANKACCTPERMISS IS ''Права доступа к счету или карте. Для ЦОД обязатльно 2. Указывается код фронтальной системы, для которой проставляется право, значения фиксированные («BP_ES» - СмартВиста, «BP_ERIB» - СБОЛ),  а также признак отсутствия указанного права(Если false – то счет виден). Записывается в виде двух пар формата [код] - [признак], перечисленных через запятую.''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ACCTBAL IS ''Тип остатка: 1)доступный расходный лимит по карте (“Avail”), 2)доступный лимит для получения наличных (“AvailCash”), 3)доступный лимит для оплаты товаров/услуг (“AvailPmt”) Для кредитных карт и карт с овердрафтом: 4)Сумма задолженности («Debt») 5)Сумма минимального платежа («MinPmt») 6) сумма кредитного лимита и дополнительного лимита авторизации контракта («CR_LIMIT») 7) собственный баланс контракта без учета балансов подчиненных liability-контрактов («OWN_BALANCE»)''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.PERSONINFO IS ''Информация о владельце.''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISNEEDDEPACCINFO IS ''Требуется ли  отображать в системе дополнительную информацию по вкладу (тег DepAccInfo выходных данных)''';
  execute immediate 'COMMENT ON COLUMN  DEPOSIT.ISNEEDBANKACCTPERMISS IS ''Требуется ли возвращать информацию о правах доступа к счету (тег BankAcctPermiss выходных данных)''';
  execute immediate 'CREATE UNIQUE INDEX  DEPOSIT_PK ON  DEPOSIT (ID)';

  execute immediate 'ALTER TABLE  DEPOSIT ADD ( CONSTRAINT DEPOSIT_PK PRIMARY KEY (ID))';
  execute immediate 'ALTER TABLE  DEPOSIT ADD ( FOREIGN KEY (PERSONINFO) REFERENCES  PERSONINFO (ID))';

  execute immediate 'CREATE TABLE  IMACCOUNT
  (
    ID               INTEGER                      NOT NULL,
    BRANCHID         VARCHAR2(5 BYTE)             NOT NULL,
    AGENCYID         VARCHAR2(5 BYTE)             NOT NULL,
    REGIONID         VARCHAR2(3 BYTE)             NOT NULL,
    RBBRCHID         VARCHAR2(6 BYTE)             NOT NULL,
    SYSTEMID         VARCHAR2(40 BYTE)            NOT NULL,
    ACCTID           VARCHAR2(22 BYTE)            NOT NULL,
    ACCTCUR          VARCHAR2(3 BYTE)             NOT NULL,
    ACCTNAME         VARCHAR2(30 BYTE)            NOT NULL,
    STARTDATE        DATE                         NOT NULL,
    ENDDATE          DATE,
    STATUS           VARCHAR2(16 BYTE),
    AGREEMENTNUMBER  VARCHAR2(64 BYTE),
    PERSONINFO       INTEGER,
    ACCTBAL          VARCHAR2(255 BYTE)           NOT NULL
  )';

  execute immediate 'comment on column IMACCOUNT.BRANCHID is ''Номер филиала(ВСП)''';
  execute immediate 'comment on column IMACCOUNT.AGENCYID is ''Номер подразделения(ОСБ)''';
  execute immediate 'comment on column IMACCOUNT.REGIONID is ''Номер тербанка(ТБ)''';
  execute immediate 'comment on column IMACCOUNT.RBBRCHID is ''Номер ОСБ, ведущего карт счет''';
  execute immediate 'comment on column IMACCOUNT.SYSTEMID is ''Идентификатор системы-источника продукта''';
  execute immediate 'comment on column IMACCOUNT.ACCTID is ''Номер счета''';
  execute immediate 'comment on column IMACCOUNT.ACCTCUR is ''Вид металла''';
  execute immediate 'comment on column IMACCOUNT.ACCTNAME is ''Краткое наименование на русском языке, готовое для отображения пользователю''';
  execute immediate 'comment on column IMACCOUNT.STARTDATE is ''Дата открытия счета''';
  execute immediate 'comment on column IMACCOUNT.ENDDATE is ''Дата закрытия счета. Обязательно для закрытых счетов.''';
  execute immediate 'comment on column IMACCOUNT.STATUS is ''Статус ОМС (Opened – Открыт, Closed – Закрыт)''';
  execute immediate 'comment on column IMACCOUNT.AGREEMENTNUMBER is ''Номер договора счета ОМС''';
  execute immediate 'comment on column IMACCOUNT.PERSONINFO is ''Информация о владельце''';
  execute immediate 'comment on column IMACCOUNT.ACCTBAL is ''Строка с записью об остатке и лимите списания. Запись вида Avail - [остаток], AvailCash - [сумма cписания]. Остаток в граммах (значение дробной части должно быть указано с точностью до второго знака)''';
  
  execute immediate 'ALTER TABLE  IMACCOUNT ADD ( CONSTRAINT IMACCOUNT_PK PRIMARY KEY (ID))';
  execute immediate 'ALTER TABLE  IMACCOUNT ADD ( CONSTRAINT IMACCOUNT_R01 FOREIGN KEY (PERSONINFO) REFERENCES  PERSONINFO (ID))';

  execute immediate 'CREATE TABLE  CRDWI
  (
    ID          INTEGER                           NOT NULL,
    RBTBBRCHID  VARCHAR2(8 BYTE)                  NOT NULL,
    SYSTEMID    VARCHAR2(40 BYTE),
    CARDNUM     VARCHAR2(20 BYTE)                 NOT NULL,
    RBBRCHID    VARCHAR2(6 BYTE),
    CARDID      INTEGER
  )';
  
  execute immediate 'create index idx_crdwi_RBTBBRCHID_CARDNUM on CRDWI (rbtbbrchid, cardnum)';

  execute immediate 'COMMENT ON COLUMN  CRDWI.RBTBBRCHID IS ''Код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию на УС''';

  execute immediate 'COMMENT ON COLUMN  CRDWI.SYSTEMID IS ''Идентификатор системы-источника продукта ''';

  execute immediate 'COMMENT ON COLUMN  CRDWI.CARDNUM IS ''Номер карты''';

  execute immediate 'COMMENT ON COLUMN  CRDWI.RBBRCHID IS ''Номер ОСБ, ведущего карту''';

  execute immediate 'COMMENT ON COLUMN  CRDWI.CARDID IS ''Идентификатор карты, возвращаемой по такому запросу''';

  execute immediate 'CREATE UNIQUE INDEX  CRDWI_PK ON  CRDWI (ID)';

  execute immediate 'ALTER TABLE  CRDWI ADD ( CONSTRAINT CRDWI_PK PRIMARY KEY (ID))';

  execute immediate 'ALTER TABLE  CRDWI ADD ( FOREIGN KEY (CARDID) REFERENCES  CARDS (ID))';

  execute immediate 'CREATE TABLE  ACC_DI
  (
    ID          INTEGER                           NOT NULL,
    RBTBBRCHID  VARCHAR2(8 BYTE)                  NOT NULL,
    SYSTEMID    VARCHAR2(40 BYTE)                 NOT NULL,
    ACCTID      VARCHAR2(24 BYTE)                 NOT NULL,
    RBBRCHID    VARCHAR2(6 BYTE),
    DEPOSITID   INTEGER
  )';

  execute immediate 'create index idx_accdi_RBTBBRCHID_ACCTID on ACC_DI (rbtbbrchid,acctid)';
  
  execute immediate 'COMMENT ON COLUMN  ACC_DI.RBTBBRCHID IS ''Код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию на УС''';

  execute immediate 'COMMENT ON COLUMN  ACC_DI.SYSTEMID IS ''Идентификатор системы-источника продукта''';

  execute immediate 'COMMENT ON COLUMN  ACC_DI.ACCTID IS ''Номер счета''';

  execute immediate 'COMMENT ON COLUMN  ACC_DI.RBBRCHID IS ''Номер ОСБ, ведущего счет по вкладу''';

  execute immediate 'COMMENT ON COLUMN  ACC_DI.DEPOSITID IS ''Идентификатор вклада, возвращаемого по такому запросу''';

  execute immediate 'CREATE UNIQUE INDEX  ACC_DI_PK ON  ACC_DI (ID)';

  execute immediate 'ALTER TABLE  ACC_DI ADD ( CONSTRAINT ACC_DI_PK PRIMARY KEY (ID))';

  execute immediate 'ALTER TABLE  ACC_DI ADD ( FOREIGN KEY (DEPOSITID) REFERENCES  DEPOSIT (ID))';

  execute immediate 'CREATE TABLE  IMA_IS
  (
    ID           INTEGER                          NOT NULL,
    RBTBBRCHID   VARCHAR2(8 BYTE)                 NOT NULL,
    SYSTEMID     VARCHAR2(40 BYTE)                NOT NULL,
    ACCTID       VARCHAR2(22 BYTE)                NOT NULL,
    RBBRCHID     VARCHAR2(6 BYTE),
    IMACCOUNTID  INTEGER
  )';

  execute immediate 'create index idx_imais_RBTBBRCHID_ACCTID on IMA_IS (rbtbbrchid,acctid)';
  
  execute immediate 'COMMENT ON COLUMN  IMA_IS.RBTBBRCHID IS ''Код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию на УС''';

  execute immediate 'COMMENT ON COLUMN  IMA_IS.SYSTEMID IS ''Идентификатор системы-источника продукта''';

  execute immediate 'COMMENT ON COLUMN  IMA_IS.ACCTID IS ''Номер счета''';

  execute immediate 'COMMENT ON COLUMN  IMA_IS.RBBRCHID IS ''Номер ОСБ, ведущего счет ОМС''';

  execute immediate 'COMMENT ON COLUMN  IMA_IS.IMACCOUNTID IS ''Идентификатор ОМС, возвращаемого по такому запросу''';
  
  execute immediate 'ALTER TABLE  IMA_IS ADD ( PRIMARY KEY (ID))';

  execute immediate 'ALTER TABLE  IMA_IS ADD ( FOREIGN KEY (IMACCOUNTID) REFERENCES  IMACCOUNT (ID) ON DELETE SET NULL)';
  
  execute immediate 'create table CREDIT
  (
    ID         INTEGER not null,
    SYSTEMID   VARCHAR2(20) not null,
	ACCTID     VARCHAR2(30) not null,
	AGREEMTNUM VARCHAR2(16) not null,
	PRODTYPE   VARCHAR2(6) not null,
	LOANTYPE   VARCHAR2(30) not null,
	ACCTCUR    VARCHAR2(3) not null,
	CURAMT     NUMBER(15,4) not null,
	BRANCHID   VARCHAR2(5),
	AGENCYID   VARCHAR2(5),
	REGIONID   VARCHAR2(3) not null,
	RBBRCHID   VARCHAR2(6) not null,
	ANN        CHAR(1) default 0 not null,
	STARTDT    DATE not null,
	EXPDT      DATE not null,
	PERSONINFO INTEGER not null,
	PERSONROLE CHAR(1) default 1 not null
  )';
  
  execute immediate 'comment on column CREDIT.SYSTEMID is ''Идентификатор системы-источника продукта''';
  execute immediate 'comment on column CREDIT.ACCTID is ''Номер ссудного счета''';
  execute immediate 'comment on column CREDIT.AGREEMTNUM is ''Номер кредитного договора''';
  execute immediate 'comment on column CREDIT.PRODTYPE is ''(ИД в ИАСК) Идентификатор банковского продукта''';
  execute immediate 'comment on column CREDIT.LOANTYPE is ''Краткое название кредита''';
  execute immediate 'comment on column CREDIT.ACCTCUR is ''Валюта''';
  execute immediate 'comment on column CREDIT.CURAMT is ''Сумма по договору''';
  execute immediate 'comment on column CREDIT.BRANCHID is ''Номер филиала, в котором ведется договор''';
  execute immediate 'comment on column CREDIT.AGENCYID is ''Номер ОСБ, где ведется кредит''';
  execute immediate 'comment on column CREDIT.REGIONID is ''Номер тербанка(ТБ)''';
  execute immediate 'comment on column CREDIT.RBBRCHID is ''Номер ОСБ, где ведется ссудный счет''';
  execute immediate 'comment on column CREDIT.ANN is ''Признак аннуитетности (True-аннуитетный, false – дифференцированный)''';
  execute immediate 'comment on column CREDIT.STARTDT is ''Дата подписания договора''';
  execute immediate 'comment on column CREDIT.EXPDT is ''Дата окончания/закрытия договора''';
  execute immediate 'comment on column CREDIT.PERSONINFO is ''Запись о заемщике''';
  execute immediate 'comment on column CREDIT.PERSONROLE is ''Роль клиента в договоре (1 – заемщик, созаемщик, 2 – поручитель, залогодатель)''';
  
  execute immediate 'alter table CREDIT add constraint CREDIT_PK primary key (ID)';
  execute immediate 'alter table CREDIT add foreign key (PERSONINFO) references PERSONINFO (ID)';

  execute immediate 'CREATE TABLE LN_CI
  (
	ID         INTEGER not null,
	RBTBBRCHID VARCHAR2(8) not null,
	SYSTEMID   VARCHAR2(20),
	ACCTID     VARCHAR2(24) not null,
	RBBRCHID   VARCHAR2(6),
	CREDITID   INTEGER
  )';
  execute immediate 'comment on column LN_CI.RBTBBRCHID is ''Код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию на УС''';
  execute immediate	'comment on column LN_CI.SYSTEMID is ''Идентификатор системы-источника продукта''';
  execute immediate 'comment on column LN_CI.ACCTID is ''Номер ссудного счета''';
  execute immediate 'comment on column LN_CI.RBBRCHID is ''Номер ОСБ, где ведется ссудный счет''';
  execute immediate 'comment on column LN_CI.CREDITID is ''Кредит, возвращаемый по такому запросу''';
  
  execute immediate 'alter table LN_CI add constraint LN_CI_PK primary key (ID)';
  execute immediate 'alter table LN_CI add foreign key (CREDITID) references CREDIT (ID)';
  execute immediate 'create index idx_lnci_RBTBBRCHID_ACCTID on ln_ci (rbtbbrchid,acctid)';
  
  execute immediate 'CREATE TABLE  GFL_PRODUCTS
  (
    ID            INTEGER                         NOT NULL,
    GFL_ID        INTEGER                         NOT NULL,
    PRODUCT_ID    INTEGER                         NOT NULL,
    PRODUCT_TYPE  VARCHAR2(10 BYTE)               NOT NULL
  )';

  execute immediate 'COMMENT ON COLUMN  GFL_PRODUCTS.GFL_ID IS ''Идентификатор запроса GFL, по которому должен быть получен продукт''';
  execute immediate 'COMMENT ON COLUMN  GFL_PRODUCTS.PRODUCT_ID IS ''Идентификатор продукта''';
  execute immediate 'COMMENT ON COLUMN  GFL_PRODUCTS.PRODUCT_TYPE IS ''Тип продукта. Возможные значения: Deposit, IMA, Card, Credit, DepoAcc, LongOrd''';
  execute immediate 'CREATE UNIQUE INDEX  GFL_PRODUCTS_PK ON  GFL_PRODUCTS (ID)';
  execute immediate 'CREATE INDEX  IDX_GFL_PRODUCTS ON  GFL_PRODUCTS (GFL_ID)';
  execute immediate 'ALTER TABLE  GFL_PRODUCTS ADD ( CONSTRAINT GFL_PRODUCTS_PK PRIMARY KEY (ID), UNIQUE (GFL_ID, PRODUCT_ID, PRODUCT_TYPE) )';

  execute immediate 'CREATE SEQUENCE S_ACCDI
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_CARDS
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_CRDWI
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
	
  execute immediate 'create sequence S_CREDIT
	START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_DEPOSIT
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_GFL
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_GFL_PRODUCT
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_IMACCOUNT
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_IMAIS
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';

  execute immediate 'CREATE SEQUENCE S_PERSONINFO
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
        
  execute immediate 'CREATE SEQUENCE S_LNCI
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
  
  execute immediate 'grant select on s_personinfo to '|| c_PrimarySchema;
  execute immediate 'grant select, insert on PERSONINFO to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_gfl to '|| c_PrimarySchema;
  execute immediate 'grant select, insert on GFL to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_cards to '|| c_PrimarySchema;
  execute immediate 'grant select, insert on CARDS to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_gfl_product to '|| c_PrimarySchema;
  execute immediate 'grant select, insert on gfl_products to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_crdwi to '|| c_PrimarySchema;
  execute immediate 'grant select, insert on crdwi to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_deposit to '|| c_PrimarySchema;  
  execute immediate 'grant select,insert on deposit to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_accdi to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on acc_di to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_imaccount to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on imaccount to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_imais to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on ima_is to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_credit to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on credit to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_lnci to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on ln_ci to '|| c_PrimarySchema;
end;
