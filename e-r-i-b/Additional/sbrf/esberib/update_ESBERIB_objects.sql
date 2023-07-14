declare
  c_PrimarySchema constant varchar2(100) := 'SBRF_118';
  c_SecondarySchema constant varchar2(100) := 'ESBERIB';
begin
  execute immediate 'alter session set current_schema = '||c_SecondarySchema;
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
  execute immediate 'create sequence S_CREDIT
	START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
  
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
  execute immediate 'CREATE SEQUENCE S_LNCI
    START WITH 10
    MAXVALUE 999999999999999999999999999
    MINVALUE 0
    NOCYCLE
    NOCACHE
    NOORDER';
	
  execute immediate 'alter table IMACCOUNT modify ACCTNAME varchar2(30)';
  
  execute immediate 'create index idx_crdwi_RBTBBRCHID_CARDNUM on crdwi (rbtbbrchid, cardnum)';
  execute immediate 'create index idx_accdi_RBTBBRCHID_ACCTID on acc_di (rbtbbrchid,acctid)';
  execute immediate 'create index idx_imais_RBTBBRCHID_ACCTID on ima_is (rbtbbrchid,acctid)';
  
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
   
  execute immediate 'grant select on s_imaccount to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on imaccount to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_imais to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on ima_is to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_credit to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on credit to '|| c_PrimarySchema;
  
  execute immediate 'grant select on s_lnci to '|| c_PrimarySchema;
  execute immediate 'grant select,insert on ln_ci to '|| c_PrimarySchema;
end;
