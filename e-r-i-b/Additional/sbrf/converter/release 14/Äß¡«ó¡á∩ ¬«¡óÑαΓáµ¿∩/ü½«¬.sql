-- Номер ревизии: 59926
-- Номер версии: 1.18
-- Комментарий: Настройка расхождение даты исполнения платежного документа в таблицах BUSSINES_DOCUNENTS, CARD_OPERATION
insert into PROPERTIES values(S_PROPERTIES.NEXTVAL, 'linking.max.date.diff', 120, 'phiz')
/
-- Номер ревизии: 60008
-- Номер версии: 1.18
-- Комментарий: BUG069142: Сбрасываются настройки в параметрах системы
delete from PROPERTIES where PROPERTY_KEY like 'mobileProviderProperties%' or PROPERTY_KEY like 'oldDocAdapterProperties%'
/
insert into PROPERTIES (id, property_key, property_value, category)
	select (select max(id) from properties) + rownum id , 'com.rssl.business.mobileProvidersProperties_' || (rownum-1) property_key, key property_value, 'phiz' category
	from BUSINESS_PROPERTIES
	where kind='E'
	order by id
/
insert into PROPERTIES (id, property_key, property_value, category)
	select (select max(id) from properties) + rownum id , 'com.rssl.business.oldDocAdaptersProperties_' || (rownum-1) property_key, key property_value, 'phiz' category
	from BUSINESS_PROPERTIES
	where kind='F'
	order by id
/
delete from BUSINESS_PROPERTIES where KIND in ('E', 'F')
/

-- Номер ревизии: 60433
-- Номер версии: 1.18
-- Комментарий:  BUG069188: Не хватает кнопки репликации на части страниц с настройками
insert into PROPERTIES (id, property_key, property_value, category)
	select S_PROPERTIES.nextval , 'com.rssl.business.simple.' || key property_key, value property_value, 'phiz' category
	from BUSINESS_PROPERTIES
	where kind='C' and value is not null
/
insert into PROPERTIES (id, property_key, property_value, category)
	select S_PROPERTIES.nextval , 'com.rssl.business.PaymentRestriction.' || key property_key, value property_value, 'phiz'  category
	from BUSINESS_PROPERTIES
	where kind='D' and value is not null
/
insert into PROPERTIES (id, property_key, property_value, category)
	select S_PROPERTIES.nextval , 'com.rssl.business.LoanReceptionTimeProperty.' || key property_key, FROM_TIME || '-' || TO_TIME || '-' || value property_value, 'phiz' category
	from BUSINESS_PROPERTIES
	where kind='A' and value is not null
/
delete from BUSINESS_PROPERTIES where KIND in ('A', 'C', 'D')
/

-- Номер ревизии: 62148 
-- Номер версии: 1.18
-- Комментарий: Настройки лимитов.
delete from PROPERTIES where PROPERTY_KEY='mobile.api.contact.sync.size.limit'
/

-- Номер ревизии: 59525
-- Номер версии: 1.18
-- Комментарий: ИОК
alter table ORDERS set unused ( ORDER_TYPE )
/
drop table SHOP_ADDITIONAL_FIELDS
/
drop sequence S_SHOP_ADDITIONAL_FIELDS
/
drop index MOB_CHCKOUT_STTE_IDX
/
alter table SHOP_FIELDS drop ( AIRLINE_RESERV_ID, AIRLINE_RESERV_EXPIRATION, AIRLINE_RESERVATION, CANCELED, MOBILE_CHECKOUT_STATE, MOBILE_CHECKOUT_PHONE )
/

-- Номер ревизии: 59931
-- Номер версии: 1.18
-- Комментарий: Автоматическая перекатегоризация (Сохранять начальное значение DESCRIPTION из IPS, Реализовать добавление перекатегоризации клиентом)
alter table CARD_OPERATIONS add ORIGINAL_DESCRIPTION varchar2(100)
/

-- Номер ревизии: 59978
-- Номер версии: 1.18
-- Комментарий: Автоматическая перекатегоризация (Реализовать алгоритм перекатегоризации) (докоммит)
alter table ALF_RECATEGORIZATION_RULES
   add constraint FK_RULE_TO_CATEGORY foreign key (NEW_CATEGORY_ID)
      references CARD_OPERATION_CATEGORIES (ID)
/
alter table ALF_RECATEGORIZATION_RULES
   add constraint FK_RULE_TO_LOGIN foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
/

-- Номер ревизии: 60172
-- Номер версии: 1.18
-- Комментарий: Реализовать выгрузку информации по совершенной в ЕРИБ операции  (BUSSINES_DOCUNENTS) в таблицу с данными,  поученными из ИПС (CARD_OPERATION).
alter table CARD_OPERATIONS modify LOAD_DATE null
/
alter table CARD_OPERATIONS add BUSINESS_DOCUMENT_ID integer null
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

-- Номер ревизии: 60483
-- Номер версии: 1.18
-- Комментарий:  BUG070976: Не отображается баннер по предодобренной карте\кредиту
alter table PERSONAL_OFFER_NOTIFICATION modify NAME varchar2(40)
/

-- Номер ревизии: 60467
-- Номер версии: 1.18
-- Комментарий: Перевести WebAPI на использование http-сессии.
drop table WEBAPI_SESSIONS
/
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
alter table LOGINS add (
	LAST_LOGON_TYPE       varchar2(15),
	LAST_LOGON_PARAMETER  varchar2(350)
)
/

-- Номер ревизии: 60644
-- Номер версии: 1.18
-- Комментарий: Профиль: пользовательские настройки
declare
	gCounter number :=0;
begin
	for p in (
		select 
			p.LOGIN_ID, p.SHOW_BANNER, p.IS_BANK_OFFER_VIEWED, p.SHOW_WIDGET, p.HINT_READ, u.USE_INTERNET_SECURITY, u.USE_OFERT
		from PROFILE p
		inner join USERS u on u.login_id=p.login_id
	) loop
		gCounter:=gCounter+1;
		if p.SHOW_BANNER = '0'          then insert into USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.showBanner', 'false'); end if;
		if p.IS_BANK_OFFER_VIEWED = '0' then insert into USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.bankOfferViewed', 'false'); end if;
		if p.SHOW_WIDGET = '0'          then insert into USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.showWidget', 'false'); end if;
		if p.HINT_READ = '1'            then insert into USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.hintsRead', 'true'); end if;
		if p.USE_INTERNET_SECURITY = '1' then insert into USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.useInternetSecurity', 'true'); end if;
		if p.USE_OFERT = '1'             then insert into USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.useOfert', 'true'); end if;

		if (mod(gCounter, 10000)=0) then
			commit;
		end if;
	end loop;
	commit;
end;
/
alter table PROFILE set unused (SHOW_BANNER, IS_BANK_OFFER_VIEWED, SHOW_WIDGET, HINT_READ, USE_INTERNET_SECURITY, USE_OFERT)
/

-- Номер ревизии: 62582
-- Номер версии: 1.18
-- Комментарий: CHG073752: Привязывать пользовательские настройки(USER_PROPERTIES) к логину, а не юзеру.
alter table USER_PROPERTIES
   add constraint FK_LOGIN_PROPERTIES foreign key (LOGIN_ID)
      references LOGINS (ID)
/

-- Номер ревизии: 60699
-- Номер версии: 1.18
-- Комментарий: Корзина платежей.
alter table ACCOUNTING_ENTITY
   add constraint FK_ACCOUNTI_FK_ACC_EN_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
/
alter table INVOICE_SUBSCRIPTIONS
   add constraint FK_INVOICE__FK_INVOIC_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
/

-- Номер ревизии: 60709
-- Номер версии: 1.18
-- Комментарий: Расширенная заявка на кредит: переход от использования кодов к использованию флажков из справочников
alter table LOANCLAIM_WORK_ON_CONTRACT rename column BUSINESS_DESC_REQUIRED to PRIVATE_PRACTICE
/
alter table LOANCLAIM_WORK_ON_CONTRACT rename column UNEMPLOYED to PENSIONER
/
 
-- Номер ревизии: 60808
-- Номер версии: 1.18
-- Комментарий: Добавить поле "страна"
alter table CARD_OPERATIONS add (
	ORIGINAL_COUNTRY  varchar2(3),
	CLIENT_COUNTRY    varchar2(3)
)
/

-- Номер ревизии: 60984
-- Номер версии: 1.18
-- Комментарий: BUG069044: Ошибка при добавлении алиаса
alter table PROVIDER_SMS_ALIAS modify NAME varchar2(20)
/

-- Номер ревизии: 61133
-- Номер версии: 1.18
-- Комментарий: Управление признаком отправки рекламы 
update ERMB_PROFILE set ADVERTISING = '1' WHERE ADVERTISING='0'
/

-- Номер ревизии: 61153
-- Номер версии: 1.18
-- Комментарий: Управление подпиской. Сохранение данных в линке
alter table CARD_LINKS add(
	USE_REPORT_DELIVERY      char(1),
	EMAIL_ADDRESS            varchar2(40),
	REPORT_DELIVERY_TYPE     varchar2(4),
	REPORT_DELIVERY_LANGUAGE varchar2(2)
)
/

-- Номер ревизии: 60931
-- Номер версии: 1.18
-- Комментарий: Отчет по автоматической перекатегоризации
alter table CARD_OPERATIONS add ORIGINAL_CATEGORY_NAME varchar2(100)
/
alter table CARD_OPERATION_CATEGORIES add UUID varchar2(32)
/
update CARD_OPERATION_CATEGORIES set UUID = DBMS_RANDOM.STRING('X', 32) where nvl(LOGIN_ID, -1)=-1
/
create unique index I_CARD_CATEGORIES_UUID on CARD_OPERATION_CATEGORIES(
    UUID
)
/

-- Номер ревизии: 60654
-- Номер версии: 1.18
-- Комментарий: Привязка категорий и цветов
alter table CARD_OPERATION_CATEGORIES add COLOR varchar2(6)
/

-- Номер ревизии: 60654
-- Номер версии: 1.18
-- Комментарий: Привязка категорий и цветов
update CARD_OPERATION_CATEGORIES set COLOR='72bf44' where NAME='Автомобиль' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='8967b0' where NAME='Перевод с карты' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='47b082' where NAME='Выдача наличных' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='f9b37b' where NAME='Коммунальные платежи, связь, интернет' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='fff450' where NAME='Здоровье и красота' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='009597' where NAME='Одежда и аксессуары' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='74489d' where NAME='Образование' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='d4711a' where NAME='Отдых и развлечения' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='89c765' where NAME='Супермаркеты' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='00599d' where NAME='Прочие расходы' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='aa55a1' where NAME='Транспорт' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='ed1c24' where NAME='Комиссия' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='d89016' where NAME='Погашение кредитов' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='ce181e' where NAME='Путешествия' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='f58220' where NAME='Все для дома' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='62a73b' where NAME='Рестораны и кафе' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='0066b3' where NAME='Искусство' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='f04e4c' where NAME='Перевод на вклад' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='faa61a' where NAME='Перевод между своими картами' and INCOME='0' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='fecd7f' where NAME='Траты наличными' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='00a65d' where NAME='Внесение наличных' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='1c3687' where NAME='Зачисления' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='cf3734' where NAME='Перевод на карту' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='e3d200' where NAME='Возврат, отмена операций' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='00aaad' where NAME='Прочие поступления' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='8e187c' where NAME='Перевод со вклада' and nvl(LOGIN_ID,-1)=-1
/
update CARD_OPERATION_CATEGORIES set COLOR='00b274' where NAME='Перевод между своими картами' and INCOME='1' and nvl(LOGIN_ID,-1)=-1
/

update CARD_OPERATION_CATEGORIES categories set categories.COLOR =
   (case mod(ROWNUM-1,30)
    when 0 then '21409a'
    when 1 then 'f3715a'
    when 2 then 'fff200'
    when 3 then '00b6be'
    when 4 then '5c2d91'
    when 5 then 'f79548'
    when 6 then '009252'
    when 7 then '1b75bc'
    when 8 then 'a3238e'
    when 9 then '51247f'
    when 10 then 'fdba4d'
    when 11 then '99d178'
    when 12 then 'f8a55e'
    when 13 then '4791c8'
    when 14 then 'bd60ad'
    when 15 then 'fff781'
    when 16 then '47b3b4'
    when 17 then '9b7bb8'
    when 18 then 'e0995a'
    when 19 then 'aad790'
    when 20 then 'c284bb'
    when 21 then 'f25b61'
    when 22 then 'e3af57'
    when 23 then '8161a3'
    when 24 then '5b6ea8'
    when 25 then 'dc6f6d'
    when 26 then 'ebdf47'
    when 27 then '47c2c4'
    when 28 then 'dc585d'
    ELSE '5a9bcf'
    END)
where categories.LOGIN_ID is not null
/

-- Номер ревизии: 61366
-- Номер версии: 1.18
-- Комментарий: Сохранение логинов клиента из соц. сетей. Модель БД.
alter table USER_SOCIAL_IDS
   add constraint FK_USER_SOC_IDS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
/

-- Номер ревизии: 61370
-- Номер версии: 1.18
-- Комментарий: Профиль: Сохранение аватара. Работа с аватарами и настройками пользователя
alter table PROFILE add AVATAR_IMAGE_ID integer
/
alter table PROFILE add constraint FK_PROFILE_FK_AVATAR_USER_IMA foreign key (AVATAR_IMAGE_ID)
   references USER_IMAGES(ID)
/
create index "DXFK_AVATAR" on PROFILE(
   AVATAR_IMAGE_ID
)
/

-- Номер ревизии: 61504
-- Номер версии: 1.18
-- Комментарий: Актуализация текстов сообщений
alter table NOTIFICATIONS add (
	DOCUMENT_STATE           varchar2(25)  null,
	DOCUMENT_TYPE            varchar2(50)  null,
	NAME_AUTO_PAYMENT        varchar2(100) null,
	RECIPIENT_ACCOUNT_NUMBER varchar2(25)  null,
	RECIPIENT_ACCOUNT_TYPE   varchar2(32)  null
)
/
update NOTIFICATIONS set DOCUMENT_STATE='EXECUTED' where 
    NAME='PaymentExecuteNotification' 
or  NAME='AccountRestChangeLowNotification' 
or  NAME='AccountRestChangeNotification'
or  NAME='LossPassbookNotification'
or  NAME='RefuseLongOfferNotification'
or  NAME='LongOfferNotification'
/
update NOTIFICATIONS set DOCUMENT_TYPE='CardJurIntraBankTransfer' where NAME='PaymentExecuteNotification'
/
update NOTIFICATIONS set DOCUMENT_TYPE='CardJurIntraBankTransfer' where NAME='AccountRestChangeLowNotification'
/
update NOTIFICATIONS set DOCUMENT_TYPE='CardJurIntraBankTransfer' where NAME='AccountRestChangeNotification'
/
update NOTIFICATIONS set DOCUMENT_TYPE='LossPassbookApplicationClaim' where NAME='LossPassbookNotification'
/
update NOTIFICATIONS set DOCUMENT_TYPE='RefuseAutoPayment' where NAME='RefuseLongOfferNotification'
/
update NOTIFICATIONS set DOCUMENT_TYPE='CreateAutoPayment' where NAME='LongOfferNotification'
/
update NOTIFICATIONS set NAME='PaymentExecuteNotification' where
    NAME='AccountRestChangeLowNotification'
or  NAME='AccountRestChangeNotification'
or  NAME='LossPassbookNotification'
or  NAME='RefuseLongOfferNotification'
or  NAME='LongOfferNotification'
/

-- Номер ревизии: 61537
-- Номер версии: 1.18
-- Комментарий: Информирование клиента: статус по операциям
alter table USERS set unused ( MESSAGE_SERVICE ) 
/
alter table SUBSCRIPTIONS add (
	NOTIFICATION_TYPE varchar2(30),
	CHANNEL           varchar2(10)  
)
/
alter table SUBSCRIPTIONS modify LOGIN_ID not null
/
update SUBSCRIPTIONS subs set CHANNEL=(
	select CHANNEL from DISTRIBUTION_CHANNEL dcha
	join SUBSCRIPTION_TEMPLATES subtem on subtem.TEMPLATE_ID=dcha.ID
	where subtem.SUBSCRIPTION_ID=subs.ID
)
/
update SUBSCRIPTIONS set NOTIFICATION_TYPE='operationNotification'
/
alter table SUBSCRIPTIONS modify NOTIFICATION_TYPE not null
/
alter table SUBSCRIPTIONS modify CHANNEL not null
/
alter table SUBSCRIPTIONS drop column SCHEDULE_ID
/
create unique index UNIQ_SUBSCRIPTION on SUBSCRIPTIONS (
   LOGIN_ID ASC,
   NOTIFICATION_TYPE ASC
)
/
drop index UK_3
/
insert into PAYMENT_NOTIFICATIONS (ID, LOGIN,DATE_CREATED,NAME,DOCUMENT_STATE,ACCOUNT_NUMBER,ACCOUNT_RESOURCE_TYPE,TRANSACTION_SUM, CURRENCY,NAME_OR_TYPE,DOCUMENT_TYPE,NAME_AUTO_PAYMENT,RECIPIENT_ACCOUNT_NUMBER,RECIPIENT_ACCOUNT_TYPE)  
	select S_PAYMENT_NOTIFICATIONS.nextval, LOGIN,DATE_CREATED,NAME,DOCUMENT_STATE,ACCOUNT_NUMBER,ACCOUNT_RESOURCE_TYPE,TRANSACTION_SUM, CURRENCY,NAME_OR_TYPE,DOCUMENT_TYPE,NAME_AUTO_PAYMENT,RECIPIENT_ACCOUNT_NUMBER,RECIPIENT_ACCOUNT_TYPE 
		from NOTIFICATIONS
/

drop table ACCOUNT_PARAMETERS
/
drop sequence S_ACCOUNT_PARAMETERS
/
drop table CARD_PARAMETERS
/
drop sequence S_CARD_PARAMETERS
/
drop table SUBSCRIPTION_TEMPLATES
/
drop sequence S_SUBSCRIPTION_TEMPLATES
/
drop table SUBSCRIPTION_PARAMETERS
/
drop sequence S_SUBSCRIPTION_PARAMETERS
/
drop table NOTIFICATIONS
/
drop sequence S_NOTIFICATIONS
/
drop table SCHEDULE
/
drop sequence S_SCHEDULE
/
drop table DISTRIBUTION_CHANNEL
/
drop sequence S_DISTRIBUTION_CHANNEL
/
drop table DISTRIBUTIONS
/
drop sequence S_DISTRIBUTIONS
/

-- Номер ревизии: 61541
-- Номер версии: 1.18
-- Комментарий: Изменение тарифного плана SMS запросом
alter table ERMB_TARIF add STATUS varchar2(12) null
/
update ERMB_TARIF set STATUS = 'ACTIVE'
/
alter table ERMB_TARIF modify STATUS varchar2(12) not null
/

-- Номер ревизии: 61560
-- Номер версии: 1.18
-- Комментарий: ФОС. схема сотрудника, работающего с письмами
alter table ACCESSSCHEMES add MAIL_MANAGEMENT char(1)
/
update ACCESSSCHEMES set MAIL_MANAGEMENT = '1' where ID in (
	select SCHEME_ID from SCHEMESSERVICES 
	join SERVICES on SERVICES.ID = SCHEMESSERVICES.SERVICE_ID 
	where SERVICES.SERVICE_KEY = 'MailManagment'
)
/
update ACCESSSCHEMES set MAIL_MANAGEMENT = '0' where MAIL_MANAGEMENT is null
/
alter table ACCESSSCHEMES modify MAIL_MANAGEMENT not null
/

-- Номер ревизии: 61586
-- Номер версии: 1.18
-- Комментарий: Управление подпиской. clientId
alter table CARD_LINKS add CLIENT_ID varchar2(255)
/

-- Номер ревизии: 61606
-- Номер версии: 1.18
-- Комментарий: ФОС. отключение работы с группами клиентов
delete from RECIPIENTS where MAIL_ID in (select ID from MAIL where RECIPIENT_TYPE = 'GROUP')
/
delete from MAIL where RECIPIENT_TYPE = 'GROUP'
/

-- Номер ревизии: 61610
-- Номер версии: 1.18
-- Комментарий: Перенести каналы подписок клиента из профиля в таблицу подписок
insert into SUBSCRIPTIONS (id, LOGIN_ID, NOTIFICATION_TYPE, CHANNEL)
	select S_SUBSCRIPTIONS.nextval , LOGIN_ID LOGIN_ID,'loginNotification' NOTIFICATION_TYPE, LOGON_NOTIFICATION_TYPE CHANNEL
		from PROFILE where LOGON_NOTIFICATION_TYPE is not null
/
insert into SUBSCRIPTIONS (id, LOGIN_ID, NOTIFICATION_TYPE, CHANNEL)
	select S_SUBSCRIPTIONS.nextval , LOGIN_ID LOGIN_ID,'mailNotification' NOTIFICATION_TYPE, MAIL_NOTIFICATION_TYPE CHANNEL
		from PROFILE where MAIL_NOTIFICATION_TYPE is not null
/
alter table PROFILE set unused (
	LOGON_NOTIFICATION_TYPE,
	MAIL_NOTIFICATION_TYPE
)
/

-- Номер ревизии: 61614
-- Номер версии: 1.18
-- Комментарий: Добавление изображения  для цели
alter table ACCOUNT_TARGETS add IMAGE_ID number
/

-- Номер ревизии: 61676
-- Номер версии: 1.18
-- Комментарий: Структура БД. Таблицы для адресов
alter table LOANCLAIM_AREA
   add constraint LC_AREA_TYPEOFAREA_FK foreign key (TYPEOFAREA)
      references LOANCLAIM_TYPE_OF_AREA (CODE)
/
alter table LOANCLAIM_CITY
   add constraint LC_CITY_TYPEOFCITY_FK foreign key (TYPEOFCITY)
      references LOANCLAIM_TYPE_OF_CITY (CODE)
/
alter table LOANCLAIM_SETTLEMENT
   add constraint LC_SETTL_TYPEOFLOCALITY_FK foreign key (TYPEOFLOCALITY)
      references LOANCLAIM_TYPE_OF_LOCALITY (CODE)
/
alter table LOANCLAIM_STREET
   add constraint LC_STREET_TYPEOFSTREET_FK foreign key (TYPEOFSTREET)
      references LOANCLAIM_TYPE_OF_STREET (CODE)
/

-- Номер ревизии: 61682
-- Номер версии: 1.18
-- Комментарий: Доработки оплаты документов с помощью штрих-кодов.
alter table REGIONS add (
	PROVIDER_CODE_MAPI varchar2(200) null,
	PROVIDER_CODE_ATM  varchar2(200) null
)
/

-- Номер ревизии: 61733
-- Номер версии: 1.18
-- Комментарий: BUG068530: Убрать фулсканы с таблицы ORDERS
create index IDX_NOT_NOTIFIED_UEC on ORDERS (
	case when SYSTEM_NAME = 'UEC' and (STATUS='NOT_SEND' or  STATUS='ERROR') then '1' else null end ASC 
) parallel 32
/
alter index IDX_NOT_NOTIFIED_UEC noparallel
/

-- Номер ревизии: 61759
-- Номер версии: 1.18
-- Комментарий: Профиль: связка услуг и пользовательских документов.
alter table INVOICES_FOR_USER_DOCUMENTS 
	add constraint FK_INVC_FR_USR_DOCS_LGN_ID foreign key (LOGIN_ID) references LOGINS(ID)
/
alter table INVOICES_FOR_USER_DOCUMENTS
	add constraint FK_INVC_FR_USR_DOCS_INVSUBID foreign key (INVOICE_SUBSCRIPTION_ID) references INVOICE_SUBSCRIPTIONS (ID)
/
--добавлен по модели БД, в рассылке нет
alter table INVOICES
   add constraint FK_INVOICES_REFERENCE_INVOICE_ foreign key (INVOICE_SUBSCRIPTION_ID)
      references INVOICE_SUBSCRIPTIONS (ID)
/


-- Номер ревизии: 61776
-- Номер версии: 1.18
-- Комментарий: CHG072541: Доработка сохранения новостных подписок
alter table NEWS_DISTRIBUTIONS drop column LAST_PROCESSED_ID
/
delete from SUBSCRIPTIONS where channel = 'none' and NOTIFICATION_TYPE ='newsNotification'
/
alter table SUBSCRIPTIONS add TB varchar2(4) null
/
update SUBSCRIPTIONS set TB = (
	select TB from DEPARTMENTS 
	join USERS on users.DEPARTMENT_ID = DEPARTMENTS.id 
	where SUBSCRIPTIONS.login_id = users.login_id) 
/
alter table SUBSCRIPTIONS modify TB not null
/
create index SUBSCR_NEWS_IND on SUBSCRIPTIONS
(
   NOTIFICATION_TYPE ASC,
   TB ASC,
   LOGIN_ID ASC
)
/
insert into SUBSCRIPTIONS (id, LOGIN_ID, NOTIFICATION_TYPE, CHANNEL, TB)
	select S_SUBSCRIPTIONS.nextval , LOGIN_ID,'newsNotification', 'email', TB 
		from NEWS_SUBSCRIPTIONS 
/
drop index NEWS_SUBSCRIPTIONS_TB_INDEX
/
drop table NEWS_SUBSCRIPTIONS 
/
drop sequence S_NEWS_SUBSCRIPTIONS
/

-- Номер ревизии: 61614
-- Номер версии: 1.18
-- Комментарий: ИОК
alter table ACCOUNT_TARGETS modify IMAGE_ID integer
/

-- Номер ревизии: 61878
-- Номер версии: 1.18
-- Комментарий: Профиль. Конвертация БД.
insert into USER_ADDED_DOCUMENTS (ID, LOGIN_ID, DOC_NUMBER, DOCUMENT_TYPE) 
	select S_USER_ADDED_DOCUMENTS.nextVal as ID, u.LOGIN_ID as LOGIN_ID, u.SNILS as DOC_NUMBER, 'SNILS' as DOCUMENT_TYPE 
		from USERS u where u.SNILS is not null
/
insert into USER_ADDED_DOCUMENTS (ID, LOGIN_ID, DOC_NUMBER, DOCUMENT_TYPE) 
	select S_USER_ADDED_DOCUMENTS.nextVal as ID, u.LOGIN_ID as LOGIN_ID, u.INN as DOC_NUMBER, 'INN' as DOCUMENT_TYPE 
		from USERS u where u.INN is not null
/
alter table USERS set unused ( INN,	SNILS)
/

-- Номер ревизии: 61916
-- Номер версии: 1.18
-- Комментарий: ФОС. Выбор сотрудника для переназначения.
alter table ACCESSSCHEMES drop column MAIL_MANAGEMENT
/

-- Номер ревизии: 61961
-- Номер версии: 1.18
-- Комментарий: Доработки ЕРМБ. Абонентская плата. Доработка структуры БД
alter table ERMB_PROFILE add (
	FPP_UNLOAD_DATE    timestamp null,
	CHARGE_NEXT_DATE   timestamp null,
	CHARGE_NEXT_AMOUNT number    null,
	CHARGE_NEXT_PERIOD number    null,
	CHARGE_DAY_OF_MONTH number,
	FPP_IN_PROGRESS    char(1)
)
/
update ERMB_PROFILE set CHARGE_NEXT_DATE = SYSDATE where CHARGE_NEXT_DATE is null
/
alter table ERMB_PROFILE modify CHARGE_NEXT_DATE not null
/

update ERMB_PROFILE set FPP_IN_PROGRESS = 0
/
alter table ERMB_PROFILE modify FPP_IN_PROGRESS not null
/

alter table ERMB_PROFILE add SERVICE_STATUS_TMP char(1)
/
update ERMB_PROFILE set SERVICE_STATUS_TMP = case when SERVICE_STATUS = 'ACTIVE' then '1' else '0' end
/
alter table ERMB_PROFILE modify SERVICE_STATUS_TMP not null
/

alter table ERMB_PROFILE add CLIENT_BLOCKED char(1)
/
update ERMB_PROFILE set CLIENT_BLOCKED = case when SERVICE_STATUS = 'LOCK' then '1' else '0' end
/
alter table ERMB_PROFILE modify CLIENT_BLOCKED not null
/

alter table ERMB_PROFILE add PAYMENT_BLOCKED char(1)
/
update ERMB_PROFILE set PAYMENT_BLOCKED = case when SERVICE_STATUS = 'NOT_PAID' then '1' else '0' end
/
alter table ERMB_PROFILE modify PAYMENT_BLOCKED not null
/

alter table ERMB_PROFILE drop column SERVICE_STATUS
/
alter table ERMB_PROFILE rename column SERVICE_STATUS_TMP to SERVICE_STATUS
/

-- Номер ревизии: 61970
-- Номер версии: 1.18
-- Комментарий: Одноклассники. Доработка сущности "мобильной платформы".
alter table MOBILE_PLATFORMS add (
	IS_SOCIAL           char(1),
	UNALLOWED_BROWSERS  varchar2(100),
	USE_CAPTCHA         char(1)
)
/	
update MOBILE_PLATFORMS set IS_SOCIAL=0, USE_CAPTCHA=1
/
alter table MOBILE_PLATFORMS modify (
	IS_SOCIAL           default 0 not null,
	USE_CAPTCHA         default 1 not null
)
/

-- Номер ревизии: 61979
-- Номер версии: 1.18
-- Комментарий: ФОС. Синхронизация справочников площадок и тематик сообщений
alter table MAIL_SUBJECTS add UUID VARCHAR2(32)
/
update MAIL_SUBJECTS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table MAIL_SUBJECTS modify UUID not null
/
create unique index I_MAIL_SUBJECTS_UUID on MAIL_SUBJECTS(UUID)
/

alter table CONTACT_CENTER_AREAS add UUID varchar2(32)
/
update CONTACT_CENTER_AREAS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table CONTACT_CENTER_AREAS modify UUID not null
/
create unique index I_CONTACT_CENTER_AREAS_UUID on CONTACT_CENTER_AREAS(UUID)
/

drop index "DXFK_C_C_AREA_DEP_TO_C_C_AREA"
/

-- Номер ревизии: 62110 
-- Номер версии: 1.18
-- Комментарий: Справочник подразделений. Добавление признака активности подразделения.
alter table DEPARTMENTS add (ACTIVE  char(1))
/
update DEPARTMENTS set ACTIVE='1'
/
alter table DEPARTMENTS modify (ACTIVE  not null)
/

-- Номер ревизии: 62220
-- Номер версии: 1.18
-- Комментарий: BUG073185: [ISUP] Вернуть раздельное выставление тех.перерывов в блоках
alter table TECHNOBREAKS drop constraint AK_UK_TECHNOBREAKS
/
alter table TECHNOBREAKS drop column UUID
/

-- Номер ревизии: 62229
-- Номер версии: 1.18
-- Комментарий: ENH070442: Доработать алгоритм работы элемента "добавить в личное меню" 
--TODO объединить в один скрипт, если останется время
update FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/accounts/graphic-abstract.do', '/private/accounts/operations.do'),
	NAME = replace(NAME, 'Графическая выписка по', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Графическая выписка по', 'Последние операции по')
where LINK like '%/private/accounts/graphic-abstract.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/accounts/operations.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from FAVOURITE_LINKS where LINK like '%/private/accounts/graphic-abstract.do%'
/
update FAVOURITE_LINKS links set LINK = replace(LINK, '/private/account/payments.do', '/private/accounts/operations.do'),
NAME = replace(NAME, 'Шаблоны и платежи по', 'Последние операции по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Шаблоны и платежи по', 'Последние операции по')
where LINK like '%/private/account/payments.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/accounts/operations.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from FAVOURITE_LINKS where LINK like '%/private/account/payments.do%'
/
update FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/accounts/info.do', '/private/accounts/operations.do'),
	NAME = replace(NAME, 'Детальная информация по', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Детальная информация по', 'Последние операции по')
where LINK like '%/private/accounts/info.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/accounts/operations.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from FAVOURITE_LINKS where LINK like '%/private/accounts/info.do%'
/
update FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/cards/graphic-abstract.do', '/private/cards/info.do'),
	NAME = replace(NAME, 'Графическая выписка по', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Графическая выписка по', 'Последние операции по')
where LINK like '%/private/cards/graphic-abstract.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/cards/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from FAVOURITE_LINKS where LINK like '%/private/cards/graphic-abstract.do%'
/
update FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/cards/payments.do', '/private/cards/info.do'),
	NAME = replace(NAME, 'Шаблоны и платежи с', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Шаблоны и платежи с', 'Последние операции по')
where LINK like '%/private/cards/payments.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/cards/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from FAVOURITE_LINKS where LINK like '%/private/cards/payments.do%'
/
update FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/cards/detail.do', '/private/cards/info.do'),
	NAME = replace(NAME, 'Детальная информация по', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Детальная информация по', 'Последние операции по')
where LINK like '%/private/cards/detail.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/cards/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from FAVOURITE_LINKS where LINK like '%/private/cards/detail.do%'
/
update FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/loans/info.do', '/private/loans/detail.do'),
	NAME = replace(NAME, 'График платежей по', 'Детальная информация по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'График платежей по', 'Детальная информация по')
where LINK like '%/private/loans/info.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/loans/detail.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from FAVOURITE_LINKS where LINK like '%/private/loans/info.do%'
/
update FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/depo/info/position.do', '/private/depo/documents.do'),
	NAME = replace(NAME, 'Позиция по', 'Список документов по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Позиция по', 'Список документов по')
where LINK like '%/private/depo/info/position.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/depo/documents.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from FAVOURITE_LINKS where LINK like '%/private/depo/info/position.do%'
/
update FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/depo/info/debt.do', '/private/depo/documents.do'),
	NAME = replace(NAME, 'Задолженность по', 'Список документов по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Задолженность по', 'Список документов по')
where LINK like '%/private/depo/info/debt.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/depo/documents.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from FAVOURITE_LINKS where LINK like '%/private/depo/info/debt.do%'
/
update FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/ima/detail.do', '/private/ima/info.do'),
	NAME = replace(NAME, 'Детальная информация по', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Детальная информация по', 'Последние операции по')
where LINK like '%/private/ima/detail.do%' 
and not exists(
        select favouriteLinks.ID from FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/ima/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from FAVOURITE_LINKS where LINK like '%/private/ima/detail.do%'
/

-- Номер ревизии: 62288
-- Номер версии: 1.18
-- Комментарий: Доработка логики исполнения шаблонов
alter table SERVICE_PROVIDERS add PLANING_FOR_DEACTIVATE char(1)
/
update SERVICE_PROVIDERS set PLANING_FOR_DEACTIVATE = 0 where PLANING_FOR_DEACTIVATE is null
/
alter table SERVICE_PROVIDERS modify PLANING_FOR_DEACTIVATE not null
/

alter table BILLINGS add TEMPLATE_STATE varchar2(30)
/

-- Номер ревизии: 62369
-- Номер версии: 1.18
-- Комментарий: ENH070442: Доработать алгоритм работы элемента "добавить в личное меню" 
alter table FAVOURITE_LINKS drop column IS_USE
/

-- Номер ревизии: 62402
-- Номер версии: 1.18
-- Комментарий: Добавление интернет-заказов в функционал "счетов к оплате"
alter table SHOP_ORDERS add ( "DELAYED_PAY_DATE" timestamp null ) 
/

-- Номер ревизии: 62460
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. Переименовал признак разрешение рекламных рассылок в признак запрета. 
alter table ERMB_PROFILE rename column  ADVERTISING to SUPPRESS_ADVERTISING
/
update ERMB_PROFILE set SUPPRESS_ADVERTISING = decode(SUPPRESS_ADVERTISING, null, '0', '0', '1', '0')
/

-- Номер ревизии: 62496
-- Номер версии: 1.18
-- Комментарий: Активация услуги ЕРМБ - Проставление флагов при создании ЕРМБ профиля
alter table ERMB_PROFILE add COD_ACTIVATED char(1)
/
update ERMB_PROFILE set COD_ACTIVATED = '0'
/
alter table ERMB_PROFILE modify COD_ACTIVATED not null
/

alter table ERMB_PROFILE add WAY_ACTIVATED char(1)
/
update ERMB_PROFILE set WAY_ACTIVATED = '0'
/
alter table ERMB_PROFILE modify WAY_ACTIVATED not null
/

alter table ERMB_PROFILE add ACTIVATION_TRY_DATE timestamp
/

-- Номер ревизии: 62760
-- Номер версии: 1.18
-- Комментарий: ENH070461: Уйти от использования ConnectorInfo в ЕРИБ и ЦСА 
drop table CONNECTORS_INFO
/
drop sequence S_CONNECTORS_INFO
/
drop index USR_CON_INFO_UN_IND
/

-- Номер ревизии: 62894
-- Номер версии: 1.18
-- Комментарий: иок(2)
update ERMB_PROFILE set ACTIVATION_TRY_DATE = sysdate where ACTIVATION_TRY_DATE is null
/
alter table ERMB_PROFILE modify ACTIVATION_TRY_DATE not null
/

create index I_ACTIVATION_TRY_DATE on ERMB_PROFILE (ACTIVATION_TRY_DATE ASC)
/

-- Номер ревизии: 62929
-- Номер версии: 1.18
-- Комментарий: BUG073990: [ЕРМБ ] некорректная работа джобов ЕРМБ
delete from QRTZ_SIMPLE_TRIGGERS where TRIGGER_NAME in (
    select TRIGGER_NAME from QRTZ_TRIGGERS where JOB_NAME in (
      'ErmbProfilesUpdateJob',
      'DelayedCommandExecuteJob',
      'ErmbNewClientsRegistrationJob',
      'ErmbDisconnectorPhoneJob',
      'ErmbP2PJob',
      'IncompletePaymentsRemoverJob',
      'ErmbActivationJob',
      'FPP_STARTER')
)
/

delete from QRTZ_CRON_TRIGGERS where TRIGGER_NAME in
(
    select TRIGGER_NAME from QRTZ_TRIGGERS where JOB_NAME in (
      'ErmbProfilesUpdateJob',
      'DelayedCommandExecuteJob',
      'ErmbNewClientsRegistrationJob',
      'ErmbDisconnectorPhoneJob',
      'ErmbP2PJob',
      'IncompletePaymentsRemoverJob',
      'ErmbActivationJob',
      'FPP_STARTER')
)
/

delete from QRTZ_BLOB_TRIGGERS where TRIGGER_NAME in
(
    select TRIGGER_NAME from QRTZ_TRIGGERS where JOB_NAME in (
      'ErmbProfilesUpdateJob',
      'DelayedCommandExecuteJob',
      'ErmbNewClientsRegistrationJob',
      'ErmbDisconnectorPhoneJob',
      'ErmbP2PJob',
      'IncompletePaymentsRemoverJob',
      'ErmbActivationJob',
      'FPP_STARTER')
)
/

delete from QRTZ_TRIGGERS where JOB_NAME in (
  'ErmbProfilesUpdateJob',
  'DelayedCommandExecuteJob',
  'ErmbNewClientsRegistrationJob',
  'ErmbDisconnectorPhoneJob',
  'ErmbP2PJob',
  'IncompletePaymentsRemoverJob',
  'ErmbActivationJob',
  'FPP_STARTER'
)
/

delete from QRTZ_JOB_DETAILS where JOB_NAME in (
  'ErmbProfilesUpdateJob',
  'DelayedCommandExecuteJob',
  'ErmbNewClientsRegistrationJob',
  'ErmbDisconnectorPhoneJob',
  'ErmbP2PJob',
  'IncompletePaymentsRemoverJob',
  'ErmbActivationJob',
  'FPP_STARTER'
)
/

-- Номер ревизии: 62966
-- Номер версии: 1.18
-- Комментарий: доработка профиля ЕРМБ
update ERMB_PROFILE set CHARGE_NEXT_AMOUNT = 0 where CHARGE_NEXT_AMOUNT is null
/
alter table ERMB_PROFILE modify CHARGE_NEXT_AMOUNT not null
/

-- Номер ревизии: 62057
-- Номер версии: 1.18
-- Комментарий: CHG072313: Секционировать OPERATION_CONFIRM_LOG 
alter table OPERATION_CONFIRM_LOG rename to OPERATION_CONFIRM_LOG_O
/

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
partition by range (LOG_DATE)  interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_START values less than (to_date('01-06-2014','DD-MM-YYYY'))
)
/

create index I_OPERATION_CONFIRM_LOG_ID ON OPERATION_CONFIRM_LOG(
    ID
) local 
/

create index I_OPERATION_CONFIRM_UID ON OPERATION_CONFIRM_LOG(
    OPERATION_UID
) global partition by hash (OPERATION_UID) partitions 128
/

-- Номер ревизии: 63231
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG073202: Шаблоны отдельное приложение. Добавить хранение межблочного идентификатора поставщика услуг.
alter table PAYMENTS_DOCUMENTS add ( MULTI_BLOCK_RECEIVER_CODE VARCHAR2(32) )
/

-- Номер ревизии: 63246
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG073908: Доработать отправку смс для операций с вкладов 
drop index INDEX_SMS_CHANNEL_KEY 
/

create unique index INDEX_SMS_CHANNEL_KEY on SMS_RESOURCES (
   KEY ASC,
   CHANNEL ASC,
   SMS_TYPE ASC
)
/

-- Номер ревизии: 63272
-- Номер версии: 1.18
-- Комментарий: BUG071121: полное сканирование таблицы CSA_PROFILES 

create index I_INCOGNITO_PHONES on INCOGNITO_PHONES (
	PHONE_NUMBER
)
/
delete from INCOGNITO_PHONES
/
alter table INCOGNITO_PHONES add (PROFILE_ID integer not null)
/
create index I_INCOGNITO_PROFILES on INCOGNITO_PHONES (
	PROFILE_ID
)
/

--================================================================================================================
-- Номер ревизии: 62273
-- Номер версии: 1.18
-- Комментарий: Доработать оплату услуг для оплаты своего телефона
alter index RECENTLYFILLEDDATA_LOGIN_INDX rename to IDX_RFFD_LOGIN_ID
/

-- Номер ревизии: 63276
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG070523: Лишнее поле в расширенной заявке на кредит
delete from LOANCLAIM_FAMILY_STATUS
/
alter table LOANCLAIM_FAMILY_STATUS modify (spouse_info_required varchar2(15))
/
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('0', 'Холост/не замужем', 'NOT')
/
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('1', 'В разводе', 'NOT')
/
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('2', 'Женат/замужем', 'REQUIRED')
/
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('3', 'Вдовец/вдова', 'NOT')
/
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('4', 'Гражданский брак', 'WITHOUT_PRENUP')
/

-- Номер ревизии: 63439
-- Номер версии: 1.18
-- Комментарий: Добавление индекса для запроса выгрузки ФПП абонентской платы ЕРМБ
create index IDX_ERMB_SRV_FEE_CHARGE_DATE on ERMB_PROFILE(CHARGE_NEXT_DATE)
/

-- Номер ревизии: 63575
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG074625: [ISUP] Дублирование заявок на загрузку операций из ИПС.
--TODO чистка дублей
alter table CARD_OPERATION_CLAIMS add constraint UNIQUE_LOGIN_ID_CARD_NUMBER unique (LOGIN_ID, CARD_NUMBER) using index (
	create unique index UNIQUE_LOGIN_ID_CARD_NUMBER on CARD_OPERATION_CLAIMS(LOGIN_ID, CARD_NUMBER)
)
/

-- Номер ревизии: 63575
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG074625: [ISUP] Дублирование заявок на загрузку операций из ИПС. Лишний индекс
drop index DXFK_CARDOP_CLAIM_OWNER
/

-- Номер ревизии: 63611
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG071121: полное сканирование таблицы CSA_PROFILES
create table INCOGNITO_PROFILES(
    PROFILE_ID integer not null,
    constraint PR_INCOGNITO_PROFILE primary key (PROFILE_ID) using index (
		create unique index I_INCOGNITO_PROFILE on INCOGNITO_PROFILES(PROFILE_ID)
	)
)
/

-- Номер ревизии: 63629
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG074333: [Шаблоны] Не устанавливается признак подтверждать операции по шаблону СМС-паролем 
update PROPERTIES set PROPERTY_VALUE = 'true' where PROPERTY_KEY like 'com.rssl.iccs.favourite.templates.needConfirm%' and PROPERTY_VALUE = 'on'
/

-- Номер ревизии: 62966
-- Номер версии: 1.18
-- Комментарий: доработка профиля ЕРМБ
update ERMB_PROFILE set CHARGE_NEXT_PERIOD = 1 where SERVICE_STATUS = '1' and CHARGE_NEXT_PERIOD is null
/
update ERMB_PROFILE set CHARGE_DAY_OF_MONTH = 1 where SERVICE_STATUS = '1' and CHARGE_DAY_OF_MONTH is null
/

-- Номер ревизии: 64042
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: ENH074735  [ЕРМБ] Удаление недовведённых платежей на обработке каждого СМС-запроса 
alter table ERMB_PROFILE add INCOMPLETE_SMS_PAYMENT integer
/

-- Номер ревизии: 64115
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: ENH074735  [ЕРМБ] Удаление недовведённых платежей на обработке каждого СМС-запроса 
delete from QRTZ_ERMB_SIMPLE_TRIGGERS where TRIGGER_NAME = 'IncompletePaymentsRemoverTrigger'
/
delete from QRTZ_ERMB_TRIGGERS where TRIGGER_NAME = 'IncompletePaymentsRemoverTrigger'
/
delete from QRTZ_ERMB_JOB_DETAILS where JOB_NAME = 'IncompletePaymentsRemoverJob'
/

DELETE FROM QRTZ_ERMB_SIMPLE_TRIGGERS WHERE TRIGGER_NAME = 'IncompletePaymentsRemoverTrigger';

DELETE FROM QRTZ_ERMB_TRIGGERS WHERE TRIGGER_NAME = 'IncompletePaymentsRemoverTrigger';

DELETE FROM QRTZ_ERMB_JOB_DETAILS WHERE JOB_NAME = 'IncompletePaymentsRemoverJob';

	  

-- Номер ревизии: 64304
-- Номер версии: 1.18
-- Комментарий: BUG072501: [ЕРМБ смс-канал] некорректно работает проверка суточного заградительного лимита при оплате ПУ
alter table SMS_RESOURCES modify DESCRIPTION varchar2(4000)
/

-- Номер ревизии: 64311
-- Номер версии: 1.18
-- Комментарий: BUG074650: [Синхронизация справочников] Ошибка при синхронизации справочников АЛФ. 
create table PAYMENT_SERVICES_TO_CATEGORIES 
(
   SERVICE_CODE  VARCHAR2(50)  not null,
   CATEGORY_ID   INTEGER  not null,
   constraint PK_PAYMENT_SERV_TO_CATEGORIES primary key (SERVICE_CODE)  using index (
		create unique index PK_PAYMENT_SERV_TO_CATEGORIES on PAYMENT_SERVICES_TO_CATEGORIES(SERVICE_CODE)
	)
)
/

alter table PAYMENT_SERVICES_TO_CATEGORIES
   add constraint FK_PAYMENT_SERV_CAT_CAT_ID foreign key (CATEGORY_ID)
      references CARD_OPERATION_CATEGORIES (ID)
      on delete cascade
/

create index DXFK_PAYMENT_SERV_CAT_CAT_ID on PAYMENT_SERVICES_TO_CATEGORIES(
   CATEGORY_ID
)
/

-- Номер ревизии: 64395
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG075192: [ЕРМБ абон плата] некорректно формируются файлы прямого потока
alter table ERMB_PROFILE add FPP_BLOCKED char(1)
/
update ERMB_PROFILE set FPP_BLOCKED = '0'
/
alter table ERMB_PROFILE modify FPP_BLOCKED not null
/

-- Номер ревизии: 64431
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG074685: Не сохраняются настройки Права доступа клиента по умолчанию
alter table DEFAULT_ACCESS_SCHEMES add DEPARTMENT_TB varchar2(4)
/
update DEFAULT_ACCESS_SCHEMES ds set ds.DEPARTMENT_TB = (select d.TB from DEPARTMENTS d where d.ID = ds.DEPARTMENT_ID)
/
alter table DEFAULT_ACCESS_SCHEMES drop constraint AK_DEFAULT_SCHEMES_UNIQUE
/

create unique index I_DEFAULT_ACCESS_SCHEMES on DEFAULT_ACCESS_SCHEMES(
    CREATION_TYPE,
    NVL(DEPARTMENT_TB, 'NULL')
)
/

alter table DEFAULT_ACCESS_SCHEMES drop constraint FK_DEFAULT__FK_DEF_SC_DEPARTME
/
alter table DEFAULT_ACCESS_SCHEMES drop column DEPARTMENT_ID
/


-- Номер ревизии: 64730
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: ENH072991: Почему логин в профиле неуникален?
drop index DXREFERENCE_20
/

create unique index IDX_PROFILE_LOGIN on PROFILE (
   LOGIN_ID ASC
) parallel 64
/
alter index IDX_PROFILE_LOGIN noparallel
/


-- Номер ревизии: 64318
-- Номер версии: v.1.18_release_14.0_PSI
-- Комментарий: BUG074650: [Синхронизация справочников] Ошибка при синхронизации справочников АЛФ. 
create index I_MCC_INCOME_CATEGORY on MERCHANT_CATEGORY_CODES (
   INCOME_OPERATION_CATEGORY_ID ASC
)
/

create index I_MCC_OUTCOME_CATEGORY on MERCHANT_CATEGORY_CODES (
   OUTCOME_OPERATION_CATEGORY_ID ASC
)
/




