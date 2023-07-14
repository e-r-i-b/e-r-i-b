/*
	Схема: SRB_IKFL3
	Табличное пространство таблиц: - 
	Табличное пространство индексов: DOCUMENT_OPERATIONS_JOURNA_IDX, USER_METADATA_IDX, USER_DATA_IDX
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = SRB_IKFL3
/

drop table SRB_IKFL3.LOANCLAIM_AREA
/
drop table SRB_IKFL3.LOANCLAIM_CITY
/
drop table SRB_IKFL3.LOANCLAIM_SETTLEMENT
/
drop table SRB_IKFL3.LOANCLAIM_STREET
/
create table SRB_IKFL3.LOANCLAIM_AREA 
(
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(255)        not null,
   TYPEOFAREA           varchar2(20)         not null,
   REGION               varchar2(20)         not null,
   SEARCH_POSTFIX       varchar2(255)        not null
) tablespace USER_METADATA
/

create index SRB_IKFL3.LOANCLAIM_AREA_REGION_IDX on SRB_IKFL3.LOANCLAIM_AREA (
   REGION ASC,
   SEARCH_POSTFIX ASC
) tablespace USER_METADATA_IDX
/


create table SRB_IKFL3.LOANCLAIM_CITY 
(
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(255)        not null,
   TYPEOFCITY           varchar2(20)         not null,
   AREA                 NUMBER(20,0)         not null,
   REGION               varchar2(20)         not null,
   SEARCH_POSTFIX       varchar2(255)        not null
) tablespace USER_METADATA
/

create index SRB_IKFL3.LOANCLAIM_CITY_REGION_IDX on SRB_IKFL3.LOANCLAIM_CITY (
   REGION ASC,
   SEARCH_POSTFIX ASC
) tablespace USER_METADATA_IDX
/

create table SRB_IKFL3.LOANCLAIM_SETTLEMENT 
(
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(255)        not null,
   TYPEOFLOCALITY       varchar2(20)         not null,
   CITY                 NUMBER(20,0)         not null,
   AREA                 NUMBER(20,0)         not null,
   REGION               varchar2(20)         not null,
   SEARCH_POSTFIX       varchar2(255)        not null
) tablespace USER_METADATA
/

create index SRB_IKFL3.LOANCLAIM_SETTL_REGION_IDX on SRB_IKFL3.LOANCLAIM_SETTLEMENT (
   REGION ASC,
   SEARCH_POSTFIX ASC
) tablespace USER_METADATA_IDX
/

create table SRB_IKFL3.LOANCLAIM_STREET 
(
   CODE                 varchar2(20)         not null,
   NAME                 varchar2(255)        not null,
   TYPEOFSTREET         varchar2(20)         not null,
   SETTLEMENT           NUMBER(20,0)         not null,
   CITY                 NUMBER(20,0)         not null,
   AREA                 NUMBER(20,0)         not null,
   REGION               varchar2(20)         not null,
   SEARCH_POSTFIX       varchar2(255)        not null
) tablespace USER_METADATA
/

create index SRB_IKFL3.LOANCLAIM_STREET_REGION_IDX on SRB_IKFL3.LOANCLAIM_STREET (
   REGION ASC,
   SEARCH_POSTFIX ASC
) tablespace USER_METADATA_IDX
/

create index SRB_IKFL3."DXLC_AREA_TYPEOFAREA_FK" on SRB_IKFL3.LOANCLAIM_AREA
(
   TYPEOFAREA
) tablespace USER_METADATA_IDX
/

alter table SRB_IKFL3.LOANCLAIM_AREA
   add constraint LC_AREA_TYPEOFAREA_FK foreign key (TYPEOFAREA)
      references SRB_IKFL3.LOANCLAIM_TYPE_OF_AREA (CODE)
/

create index SRB_IKFL3."DXLC_CITY_TYPEOFCITY_FK" on SRB_IKFL3.LOANCLAIM_CITY
(
   TYPEOFCITY
) tablespace USER_METADATA_IDX
/

alter table SRB_IKFL3.LOANCLAIM_CITY
   add constraint LC_CITY_TYPEOFCITY_FK foreign key (TYPEOFCITY)
      references SRB_IKFL3.LOANCLAIM_TYPE_OF_CITY (CODE)
/

create index SRB_IKFL3."DXLC_SETTL_TYPEOFLOCALITY_FK" on SRB_IKFL3.LOANCLAIM_SETTLEMENT
(
   TYPEOFLOCALITY
) tablespace USER_METADATA_IDX
/

alter table SRB_IKFL3.LOANCLAIM_SETTLEMENT
   add constraint LC_SETTL_TYPEOFLOCALITY_FK foreign key (TYPEOFLOCALITY)
      references SRB_IKFL3.LOANCLAIM_TYPE_OF_LOCALITY (CODE)
/

create index SRB_IKFL3."DXLC_STREET_TYPEOFSTREET_FK" on SRB_IKFL3.LOANCLAIM_STREET
(
   TYPEOFSTREET
) tablespace USER_METADATA_IDX
/

alter table SRB_IKFL3.LOANCLAIM_STREET
   add constraint LC_STREET_TYPEOFSTREET_FK foreign key (TYPEOFSTREET)
      references SRB_IKFL3.LOANCLAIM_TYPE_OF_STREET (CODE)
/

-- Номер ревизии: 70601
-- Комментарий:  BUG080648: Убрать логирование части сообщений mAPI
insert into SRB_IKFL3.PROPERTIES (ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
	values ( SRB_IKFL3.S_PROPERTIES.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*) from SRB_IKFL3.PROPERTIES where PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), '/mobile5*', 'phiz')
/
insert into SRB_IKFL3.PROPERTIES (ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
	values ( SRB_IKFL3.S_PROPERTIES.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*)+1 from SRB_IKFL3.PROPERTIES where PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), '/mobile6*', 'phiz')
/
insert into SRB_IKFL3.PROPERTIES (ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
	values ( SRB_IKFL3.S_PROPERTIES.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*)+2 from SRB_IKFL3.PROPERTIES where PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), '/mobile7/checkPassword.do', 'phiz')
/
insert into SRB_IKFL3.PROPERTIES (ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
	values ( SRB_IKFL3.S_PROPERTIES.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*)+3 from SRB_IKFL3.PROPERTIES where PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), '/mobile7/private/graphics/finance.do', 'phiz')
/
insert into SRB_IKFL3.PROPERTIES (ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
	values ( SRB_IKFL3.S_PROPERTIES.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*)+4 from SRB_IKFL3.PROPERTIES where PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), '/mobile7/private/permissions.do', 'phiz')
/
insert into SRB_IKFL3.PROPERTIES (ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
	values  ( SRB_IKFL3.S_PROPERTIES.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*)+5 from SRB_IKFL3.PROPERTIES where PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), '/mobile7/private/rates/list.do', 'phiz')
/
insert into SRB_IKFL3.PROPERTIES (ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
	values  ( SRB_IKFL3.S_PROPERTIES.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*)+6 from SRB_IKFL3.PROPERTIES where PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), '/mobile7/private/profile/loyaltyURL.do', 'phiz')
/
insert into SRB_IKFL3.PROPERTIES (ID, PROPERTY_KEY, PROPERTY_VALUE, CATEGORY) 
	values  ( SRB_IKFL3.S_PROPERTIES.nextval, 'com.rssl.phizic.logging.messagesLog.excluded.messages'||(select count(*)+7 from SRB_IKFL3.PROPERTIES where PROPERTY_KEY like 'com.rssl.phizic.logging.messagesLog.excluded.messages%'), '/mobile7/version/control.do', 'phiz')
/

-- Номер ревизии: 70807
-- Комментарий: Бизнес-отчеты ЕРМБ. Индексы.
create index SRB_IKFL3.ERMB_TARIFF_CHANGE_DATE_IDX on SRB_IKFL3.ERMB_CLIENT_TARIFF_HISTORY(
	CHANGE_DATE
) local tablespace USER_DATA_IDX
/

-- Номер ревизии: 71187
-- Комментарий:  CHG077428: Добавить в профиль ЕРМБ подразделение подключения 
alter table SRB_IKFL3.ERMB_PROFILE add CONNECT_DEP_ID number
/

-- Номер ревизии: 71334
-- Комментарий: CHG082172: ЕРМБ. Часовые пояса. 
update SRB_IKFL3.ERMB_PROFILE set TIME_ZONE = '120' where TIME_ZONE = '180'
/
update SRB_IKFL3.ERMB_PROFILE set TIME_ZONE = '180' where TIME_ZONE = '240'
/
update SRB_IKFL3.ERMB_PROFILE set TIME_ZONE = '240' where TIME_ZONE = '300'
/
update SRB_IKFL3.ERMB_PROFILE set TIME_ZONE = '300' where TIME_ZONE = '360'
/
update SRB_IKFL3.ERMB_PROFILE set TIME_ZONE = '360' where TIME_ZONE = '420'
/
update SRB_IKFL3.ERMB_PROFILE set TIME_ZONE = '420' where TIME_ZONE = '480'
/
update SRB_IKFL3.ERMB_PROFILE set TIME_ZONE = '480' where TIME_ZONE = '540'
/
update SRB_IKFL3.ERMB_PROFILE set TIME_ZONE = '540' where TIME_ZONE = '600'
/
update SRB_IKFL3.ERMB_PROFILE set TIME_ZONE = '600' where TIME_ZONE = '660'
/
update SRB_IKFL3.ERMB_PROFILE set TIME_ZONE = '660' where TIME_ZONE = '720'
/

-- Номер ревизии: 71572
-- Комментарий: Передача в ЕРИБ тега «Дата пролонгации»
alter table SRB_IKFL3.STORED_ACCOUNT add PROLONGATION_DATE timestamp(6)
/

-- Номер ревизии: 71643
-- Комментарий: Доработать загрузку справочника мобильных операторов
delete from SRB_IKFL3.MOBILE_OPERATORS
/
--требуется загрузка справочника MobileOperatorsDictionary!

alter table SRB_IKFL3.MOBILE_OPERATORS add ( 
	FL_AUTO   char(1) not null,
	BALANCE   integer     null, 
	MIN_SUMM  integer     null, 
	MAX_SUMM  integer     null 
) 
/

-- Номер ревизии: 71973
-- Комментарий: Унификация формата телефона - вспомогательные таблицы ермб
update SRB_IKFL3.ERMB_SMS_INBOX
   set PHONE = substr (PHONE, 2)
/

alter table SRB_IKFL3.ERMB_SMS_INBOX modify PHONE varchar2(10)
/

update SRB_IKFL3.ERMB_REGISTRATION
   set PHONE_NUMBER = substr (PHONE_NUMBER, 2)
/

alter table SRB_IKFL3.ERMB_REGISTRATION modify PHONE_NUMBER varchar2(10)
/

-- Номер ревизии: 72240
-- Комментарий: Доработка профиля сотрудника в АРМ ЕРИБ
alter table SRB_IKFL3.EMPLOYEES add (SUDIR_LOGIN varchar2(100))
/

-- Номер ревизии: 72282
-- Комментарий: BUG083283: [БКИ] Падение при переходе в детальную информацию по кред. продукту. Добавление поля в BKI_PRIMARY_ID_TYPE
delete from SRB_IKFL3.BKI_PRIMARY_ID_TYPE
/

alter table SRB_IKFL3.BKI_PRIMARY_ID_TYPE add BKI_CODE VARCHAR2(2)
/

insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '01', 'Паспорт гражданина РФ', 'REGULAR_PASSPORT_RF', '0', '01')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '01', 'Паспорт гражданина РФ', 'PASSPORT_WAY', '0', null) -- Для БКИ паспорт WAY = паспорт РФ (ENH081992: [БКИ] Доработать запрос взаимодействия с ОКБ)
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '02', 'Международный паспорт (для не граждан РФ)', 'FOREIGN_PASSPORT', '0', '02')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '03', 'Военный билет', 'MILITARY_IDCARD', '0', '03')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '04', 'Удостоверение офицера', 'OFFICER_IDCARD', '0', '04')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '05', 'Паспорт моряка', 'SEAMEN_PASSPORT', '0', '05')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '06', 'ЕГРН', NULL, '0', '06')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '07', 'Временное удостоверение по форме № 2-П', 'TIME_IDCARD_RF', '0', '07')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '08', 'ИНН Физического лица', NULL, '0', '08')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '09', 'ИНН Юридического лица', NULL, '0', '09')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '10', 'ОКПО', NULL, '0', '10')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '11', 'Заграничный паспорт (для граждан РФ для выезда за рубеж)', 'FOREIGN_PASSPORT_RF', '0', '11')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '12', 'Свидетельство о рождение (Для граждан РФ, не достигших 14 лет)', 'BIRTH_CERTIFICATE', '0', '12')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '13', 'Дипломатический паспорт', 'DIPLOMATIC_PASSPORT_RF', '0', '13')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '14', 'Паспорт гражданина СССР', 'REGULAR_PASSPORT_USSR', '0', '14')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '15', 'Водительское удостоверение', NULL, '0', '15')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '16', 'Паспорт, выданный иностранным государством и признаваемый в Российской Федерации', 'FOREIGN_PASSPORT_LEGAL', '0', '16')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '17', 'Вид на жительство', 'RESIDENTIAL_PERMIT_RF', '0', '17')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '18', 'Удостоверение беженца', 'REFUGEE_IDENTITY', '0', '18')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '19', 'Свидетельство о регистрации ходатайства о признании иммигранта беженцем', 'IMMIGRANT_REGISTRATION', '0', '19')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '20', 'Разрешение на временное проживание лица без гражданства', 'TEMPORARY_PERMIT', '0', '20')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '21', 'Иные документы, выдаваемые уполномоченными органами (для граждан РФ)', 'OTHER_DOCUMENT_MVD', '0', '21')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '22', 'Код иностранной организации', NULL, '0', '22')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '23', 'Код причины постановки на учет в налоговой инспекции', NULL, '0', '23')
/
insert into SRB_IKFL3.BKI_PRIMARY_ID_TYPE(ID, CODE, NAME, ESB_CODE, IS_DEFAULT, BKI_CODE) values (SRB_IKFL3.S_BKI_PRIMARY_ID_TYPE.nextval, '99', 'Неизвестно', 'OTHER', '1', '99')
/

-- Номер ревизии: 72769
-- Комментарий: Применение промо-кодов для открытия промо-вкладов. часть 3.
alter table SRB_IKFL3.USER_COUNTERS add BLOCK_UNTIL timestamp(6)
/

-- Номер ревизии: 72796 
-- Комментарий: Интеграция с Яндекс.Деньги. Справочник поставщиков услуг. Создание формы списка
create index SRB_IKFL3.IDX_SP_IS_FASILITATOR on SRB_IKFL3.SERVICE_PROVIDERS (
   IS_FASILITATOR ASC
) tablespace USER_METADATA_IDX
/

-- Номер ревизии: 73664 
-- Комментарий: Загрузка предодобренных предложений по кредитам из файла (добавление параметра идентификатора участника кампании)
alter table SRB_IKFL3.LOAN_OFFER add (CAMPAIGN_MEMBER_ID  varchar2(50 BYTE))
/

-- Номер ревизии: 73819 
-- Комментарий: Деградация.
alter table SRB_IKFL3.MONITORING_STATE_CONFIGS add (MONITORING_ERROR_PERCENT number)
/
update SRB_IKFL3.MONITORING_STATE_CONFIGS set MONITORING_ERROR_PERCENT=0
/
alter table SRB_IKFL3.MONITORING_STATE_CONFIGS modify (MONITORING_ERROR_PERCENT number default 30 not null)
/

-- Номер ревизии: 73860
-- Комментарий: Единый суточный кумулятивный лимит
drop index SRB_IKFL3.PROFILE_CHANNEL_OPER_DATE_IDX
/

create index SRB_IKFL3.PROFILE_CHANNEL_OPER_DATE_IDX on SRB_IKFL3.DOCUMENT_OPERATIONS_JOURNAL (
   PROFILE_ID ASC,
   OPERATION_DATE ASC
) local tablespace DOCUMENT_OPERATIONS_JOURNA_IDX parallel 64 nologging
/

alter index SRB_IKFL3.PROFILE_CHANNEL_OPER_DATE_IDX noparallel logging
/
  
 -- Номер версии: 1.18
 -- Комментарий: CHG084643: Расширить поле id в таблице incognito_phones
alter table SRB_IKFL3.INCOGNITO_PHONES modify ID NUMBER(*,0)
/
 
-- Номер ревизии: 74935
-- Комментарий: BUG083955	[АРМ сотрудника. Поставщики] ошибка при сохранении изменений поставщика
update SRB_IKFL3.SERVICE_PROVIDERS set IMAGE_HELP_ID = null where IMAGE_ID = IMAGE_HELP_ID 
/

-- Номер ревизии: 75202
-- Комментарий: BUG084689: АРМ. На странице мобильного банка в анкете клиента не отображаются счетчики последних активностей
alter table SRB_IKFL3.ERMB_PROFILE drop column LAST_REQUEST_TIME
/

-- Номер ревизии: 75648
-- Комментарий: Merge 75635(v.1.18): BUG084622: Ошибка при входе если появилось много закрытых вкладов.
alter table SRB_IKFL3.ACCOUNT_LINKS add ( CLOSED_STATE  CHAR(1) null )
/
alter table SRB_IKFL3.CARD_LINKS add ( CLOSED_STATE  CHAR(1) null )
/
alter table SRB_IKFL3.LOAN_LINKS add ( CLOSED_STATE  CHAR(1) null )
/

-- Номер ревизии: 75832
-- Комментарий: BUG084691  ЕРМБ. При подключении услуги МБ, делать регистрацию у разных ОСС, как сейчас у мегафона (бд)
alter table SRB_IKFL3.DEF_CODES add MNC char(2) not null
/

-- Номер ревизии: 76100 
-- Комментарий: BUG086924: [ISUP] Ошибка удаления поставщика из-за constraint (из первых 2х скриптов должен выполниться только один)

alter table SRB_IKFL3.AUTOPAY_SETTINGS
   drop constraint FK_AUTOPAY_SETTING_TO_PROV
/

alter table SRB_IKFL3.AUTOPAY_SETTINGS
   add constraint FK_AUTOPAY_SETTING_TO_PROV foreign key (RECIPIENT_ID)
      references SRB_IKFL3.SERVICE_PROVIDERS (ID)
      on delete cascade enable novalidate
/

-- Номер версии: 1.18
-- Комментарий: КУКО2 - Переименование шагов в старых кредитных заявках  TODO  будем делать в выносе заявок
update (
  select DOCUMENTS.STATE_CODE 
    from SRB_IKFL3.BUSINESS_DOCUMENTS DOCUMENTS 
   where DOCUMENTS.FORM_ID in ( select ID from SRB_IKFL3.PAYMENTFORMS where name='ExtendedLoanClaim' )
) 
set STATE_CODE =
  case when STATE_CODE = 'INITIAL'  then 'INITIAL2'
       when STATE_CODE = 'INITIAL2' then 'INITIAL3'   
       when STATE_CODE = 'INITIAL3' then 'INITIAL4'   
       when STATE_CODE = 'INITIAL4' then 'INITIAL5'   
       when STATE_CODE = 'INITIAL5' then 'INITIAL6'   
       when STATE_CODE = 'INITIAL6' then 'INITIAL7'   
       when STATE_CODE = 'INITIAL7' then 'INITIAL8'
  else STATE_CODE end
/



-----Постконвертация
delete from SRB_IKFL3.QRTZ_SIMPLE_TRIGGERS where TRIGGER_NAME ='ConnectCreditProfileTrigger'
/
delete from SRB_IKFL3.QRTZ_TRIGGERS where TRIGGER_NAME = 'ConnectCreditProfileTrigger'
/
delete from SRB_IKFL3.QRTZ_JOB_DETAILS where JOB_NAME ='ConnectCreditProfileJob'
/

insert into SRB_IKFL3.EXTENDED_DESCRIPTION_DATA (id, name, content)
values (SRB_IKFL3.S_EXTENDED_DESCRIPTION_DATA.nextval, 'itunes-agreement-atm','Исполняя данную операцию я подтверждаю свое согласие на передачу моих персональных данных из ОАО «Сбербанк России» в ООО НКО «Яндекс.Деньги», для цели идентификации плательщика и безналичной оплаты услуг iTunes.

Условия покупки кода App Store и iTunes

Приобретайте музыку, фильмы, игры, приложения и многое другое.
Действуют только в магазине iTunes в РФ, оператором которого является iTunes S.a.r.l. Требуется наличие учетной записи iTunes и предварительное согласие с условиями лицензии и использования. Для создания учетной записи Вы должны быть старше 13 лет и находиться в РФ. Требуется наличие совместимого программного обеспечения, аппаратного обеспечения, а также доступ к Интернету. Не подлежат обмену на наличные денежные средства; возврат или обмен не осуществляются (за исключением случаев, предусмотренных законом). Пин-код не может использоваться для приобретения подарочных сертификатов, карт iTunes, каких-либо других товаров, получения скидок или подарков в магазине iTunes. Пин-код не может использоваться для совершения покупок в интернет магазинах Apple и в розничных магазинах Apple. Неиспользованные средства по пин-коду не подлежат передаче. Cбор и использование данных осуществляется в соответствии с Политикой конфиденциальности для клиентов Apple, см. www.apple.com/privacy, если не указано иное. Риск утраты и право использования пин-кода переходят к покупателю в момент передачи. Стоимость пин-кода полностью зачисляется на учетную запись. Пин-коды выпускаются, реализуются и находятся под управлением компании iTunes S.A.R.L. iTunes S.A.R.L. не несет ответственность за какой-либо убыток или ущерб в результате утраты или кражи кода, или его использования без разрешения. Компания iTunes S.A.R.L., а также ее лицензиаты, аффилированные лица и лицензиары не дают никаких гарантий, прямых или подразумеваемых, в отношении пин-кодов или магазина iTunes, и отказываются от всех гарантий в максимально возможной степени. Указанные ограничения могут не распространяться на Вас. Недействительны в тех случаях, когда действует запрет. Не предназначены для перепродажи. Действуют полные условия, которые могут периодически изменяться, см. apple.com/legal/itunes/appstore/ru/terms.html. Контент и цены определяются с учетом доступности на момент фактической загрузки. Контент, приобретаемый в магазине iTunes, предназначается только для законного личного использования. Не крадите музыку.
© 2014 г., iTunes S.A.R.L. Все права защищены.')
/

delete from SRB_IKFL3.QRTZ_SIMPLE_TRIGGERS where TRIGGER_NAME ='ActualizeSberClientTrigger'
/
delete from SRB_IKFL3.QRTZ_TRIGGERS where TRIGGER_NAME = 'ActualizeSberClientTrigger'
/
delete from SRB_IKFL3.QRTZ_JOB_DETAILS where JOB_NAME ='ActualizeSberClientJob'
/
