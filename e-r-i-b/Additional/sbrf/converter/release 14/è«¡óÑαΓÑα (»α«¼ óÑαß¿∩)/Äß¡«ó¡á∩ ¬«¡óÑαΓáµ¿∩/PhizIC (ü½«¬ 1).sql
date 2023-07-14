--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
-- Номер ревизии: 59926
-- Комментарий: Настройка расхождение даты исполнения платежного документа в таблицах BUSSINES_DOCUNENTS, CARD_OPERATION
--insert into SRB_IKFL.PROPERTIES values(SRB_IKFL.S_PROPERTIES.NEXTVAL, 'linking.max.date.diff', 120, 'phiz')
/
-- Номер ревизии: 60008
-- Комментарий: BUG069142: Сбрасываются настройки в параметрах системы
delete from SRB_IKFL.PROPERTIES where PROPERTY_KEY like 'mobileProviderProperties%' or PROPERTY_KEY like 'oldDocAdapterProperties%'
/
insert into SRB_IKFL.PROPERTIES (id, property_key, property_value, category)
	select (select max(id) from SRB_IKFL.PROPERTIES) + rownum id , 'com.rssl.business.mobileProvidersProperties_' || (rownum-1) property_key, key property_value, 'phiz' category
	from SRB_IKFL.BUSINESS_PROPERTIES
	where kind='E'
	order by id
/
insert into SRB_IKFL.PROPERTIES (id, property_key, property_value, category)
	select (select max(id) from SRB_IKFL.PROPERTIES) + rownum id , 'com.rssl.business.oldDocAdaptersProperties_' || (rownum-1) property_key, key property_value, 'phiz' category
	from SRB_IKFL.BUSINESS_PROPERTIES
	where kind='F'
	order by id
/
delete from SRB_IKFL.BUSINESS_PROPERTIES where KIND in ('E', 'F')
/

-- Номер ревизии: 60433
-- Комментарий:  BUG069188: Не хватает кнопки репликации на части страниц с настройками
insert into SRB_IKFL.PROPERTIES (id, property_key, property_value, category)
	select SRB_IKFL.S_PROPERTIES.nextval , 'com.rssl.business.simple.' || key property_key, value property_value, 'phiz' category
	from SRB_IKFL.BUSINESS_PROPERTIES
	where kind='C' and value is not null
/
insert into SRB_IKFL.PROPERTIES (id, property_key, property_value, category)
	select SRB_IKFL.S_PROPERTIES.nextval , 'com.rssl.business.PaymentRestriction.' || key property_key, value property_value, 'phiz'  category
	from SRB_IKFL.BUSINESS_PROPERTIES
	where kind='D' and value is not null
/
insert into SRB_IKFL.PROPERTIES (id, property_key, property_value, category)
	select SRB_IKFL.S_PROPERTIES.nextval , 'com.rssl.business.LoanReceptionTimeProperty.' || key property_key, FROM_TIME || '-' || TO_TIME || '-' || value property_value, 'phiz' category
	from SRB_IKFL.BUSINESS_PROPERTIES
	where kind='A' and value is not null
/
delete from SRB_IKFL.BUSINESS_PROPERTIES where KIND in ('A', 'C', 'D')
/

-- Номер ревизии: 62148 
-- Комментарий: Настройки лимитов.
delete from SRB_IKFL.PROPERTIES where PROPERTY_KEY='mobile.api.contact.sync.size.limit'
/

-- Номер ревизии: 59525
-- Комментарий: ИОК
alter table SRB_IKFL.ORDERS set unused ( ORDER_TYPE )
/
drop table SRB_IKFL.SHOP_ADDITIONAL_FIELDS
/
drop sequence SRB_IKFL.S_SHOP_ADDITIONAL_FIELDS
/
drop index SRB_IKFL.MOB_CHCKOUT_STTE_IDX
/
alter table SRB_IKFL.SHOP_FIELDS drop ( AIRLINE_RESERV_ID, AIRLINE_RESERV_EXPIRATION, AIRLINE_RESERVATION, CANCELED, MOBILE_CHECKOUT_STATE, MOBILE_CHECKOUT_PHONE )
/

-- Номер ревизии: 59931
-- Комментарий: Автоматическая перекатегоризация (Сохранять начальное значение DESCRIPTION из IPS, Реализовать добавление перекатегоризации клиентом)
alter table SRB_IKFL.CARD_OPERATIONS add ORIGINAL_DESCRIPTION varchar2(100)
/

-- Номер ревизии: 59978
-- Комментарий: Автоматическая перекатегоризация (Реализовать алгоритм перекатегоризации) (докоммит)
alter table SRB_IKFL.ALF_RECATEGORIZATION_RULES
   add constraint FK_RULE_TO_CATEGORY foreign key (NEW_CATEGORY_ID)
      references SRB_IKFL.CARD_OPERATION_CATEGORIES (ID)
/
alter table SRB_IKFL.ALF_RECATEGORIZATION_RULES
   add constraint FK_RULE_TO_LOGIN foreign key (LOGIN_ID)
      references SRB_IKFL.LOGINS (ID)
      on delete cascade
/

-- Номер ревизии: 60172
-- Комментарий: Реализовать выгрузку информации по совершенной в ЕРИБ операции  (BUSSINES_DOCUNENTS) в таблицу с данными,  поученными из ИПС (CARD_OPERATION).
alter table SRB_IKFL.CARD_OPERATIONS modify LOAD_DATE null
/
alter table SRB_IKFL.CARD_OPERATIONS add BUSINESS_DOCUMENT_ID integer null
/

-- Номер ревизии: 60240
-- Комментарий: Реализовать выгрузку информации по совершенной в ЕРИБ операции  (BUSSINES_DOCUNENTS) в таблицу с данными,  поученными из ИПС (CARD_OPERATION).
drop index SRB_IKFL.DXFK_PROV_PAY_SER_TO_PAY
/
drop index SRB_IKFL.DXFK_PROV_PAY_SER_TO_PROV
/
create unique index SRB_IKFL.SK_SERV_PROVIDER_PAYMENT_SERV on SRB_IKFL.SERV_PROVIDER_PAYMENT_SERV (
	SERVICE_PROVIDER_ID, PAYMENT_SERVICE_ID
) tablespace INDX
/

-- Номер ревизии: 60483
-- Комментарий:  BUG070976: Не отображается баннер по предодобренной карте\кредиту
alter table SRB_IKFL.PERSONAL_OFFER_NOTIFICATION modify NAME varchar2(40)
/

-- Номер ревизии: 60467
-- Комментарий: Перевести WebAPI на использование http-сессии.
drop table SRB_IKFL.WEBAPI_SESSIONS
/
-- Номер ревизии: 60492
-- Комментарий: Возможность скрыть конкретную операцию из списка расходов
alter table SRB_IKFL.CARD_OPERATIONS add HIDDEN char(1)
/

-- Номер ревизии: 60509
-- Комментарий: Линковка операций
alter table SRB_IKFL.CARD_OPERATIONS modify CATEGORY_ID null
/

-- Номер ревизии: 60601
-- Комментарий: BUG064410: Доработать отображение Логина, Даты последнего входа и Способа аутентификации
alter table SRB_IKFL.LOGINS add (
	LAST_LOGON_TYPE       varchar2(15),
	LAST_LOGON_PARAMETER  varchar2(350)
)
/

-- Номер ревизии: 60644
-- Комментарий: Профиль: пользовательские настройки
declare
	gCounter number :=0;
begin
	for p in (
		select /*+parallel(p,16) parallel(u,16)*/ p.LOGIN_ID, p.SHOW_BANNER, p.IS_BANK_OFFER_VIEWED, p.SHOW_WIDGET, p.HINT_READ, u.USE_INTERNET_SECURITY, u.USE_OFERT
		from SRB_IKFL.PROFILE p
		inner join SRB_IKFL.USERS u on u.login_id=p.login_id
	) loop
		gCounter:=gCounter+1;
		if p.SHOW_BANNER = '0'          then insert into SRB_IKFL.USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( SRB_IKFL.S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.showBanner', 'false'); end if;
		if p.IS_BANK_OFFER_VIEWED = '0' then insert into SRB_IKFL.USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( SRB_IKFL.S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.bankOfferViewed', 'false'); end if;
		if p.SHOW_WIDGET = '0'          then insert into SRB_IKFL.USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( SRB_IKFL.S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.showWidget', 'false'); end if;
		if p.HINT_READ = '1'            then insert into SRB_IKFL.USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( SRB_IKFL.S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.hintsRead', 'true'); end if;
		if p.USE_INTERNET_SECURITY = '1' then insert into SRB_IKFL.USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( SRB_IKFL.S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.useInternetSecurity', 'true'); end if;
		if p.USE_OFERT = '1'             then insert into SRB_IKFL.USER_PROPERTIES (ID, LOGIN_ID, PROPERTY_KEY, PROPERTY_VALUE) values( SRB_IKFL.S_USER_PROPERTIES.nextVal, p.LOGIN_ID, 'com.rssl.phizic.userSettings.useOfert', 'true'); end if;

		if (mod(gCounter, 10000)=0) then
			commit;
		end if;
	end loop;
	commit;
end;
/
alter table SRB_IKFL.PROFILE set unused (SHOW_BANNER, IS_BANK_OFFER_VIEWED, SHOW_WIDGET, HINT_READ, USE_INTERNET_SECURITY, USE_OFERT)
/

-- Номер ревизии: 62582
-- Комментарий: CHG073752: Привязывать пользовательские настройки(USER_PROPERTIES) к логину, а не юзеру.
alter table SRB_IKFL.USER_PROPERTIES
   add constraint FK_LOGIN_PROPERTIES foreign key (LOGIN_ID)
      references SRB_IKFL.LOGINS (ID) enable novalidate
/

-- Номер ревизии: 60699
-- Комментарий: Корзина платежей.
alter table SRB_IKFL.ACCOUNTING_ENTITY
   add constraint FK_ACCOUNTI_FK_ACC_EN_LOGINS foreign key (LOGIN_ID)
      references SRB_IKFL.LOGINS (ID)
/
alter table SRB_IKFL.INVOICE_SUBSCRIPTIONS
   add constraint FK_INVOICE__FK_INVOIC_LOGINS foreign key (LOGIN_ID)
      references SRB_IKFL.LOGINS (ID)
/

-- Номер ревизии: 60709
-- Комментарий: Расширенная заявка на кредит: переход от использования кодов к использованию флажков из справочников
alter table SRB_IKFL.LOANCLAIM_WORK_ON_CONTRACT rename column BUSINESS_DESC_REQUIRED to PRIVATE_PRACTICE
/
alter table SRB_IKFL.LOANCLAIM_WORK_ON_CONTRACT rename column UNEMPLOYED to PENSIONER
/
 
-- Номер ревизии: 60808
-- Комментарий: Добавить поле "страна"
alter table SRB_IKFL.CARD_OPERATIONS add (
	ORIGINAL_COUNTRY  varchar2(3),
	CLIENT_COUNTRY    varchar2(3)
)
/

-- Номер ревизии: 60984
-- Комментарий: BUG069044: Ошибка при добавлении алиаса
alter table SRB_IKFL.PROVIDER_SMS_ALIAS modify NAME varchar2(20)
/

-- Номер ревизии: 61133
-- Комментарий: Управление признаком отправки рекламы 
update SRB_IKFL.ERMB_PROFILE set ADVERTISING = '1' WHERE ADVERTISING='0'
/

-- Номер ревизии: 61153
-- Комментарий: Управление подпиской. Сохранение данных в линке
alter table SRB_IKFL.CARD_LINKS add(
	USE_REPORT_DELIVERY      char(1),
	EMAIL_ADDRESS            varchar2(40),
	REPORT_DELIVERY_TYPE     varchar2(4),
	REPORT_DELIVERY_LANGUAGE varchar2(2)
)
/

-- Номер ревизии: 60931
-- Комментарий: Отчет по автоматической перекатегоризации
alter table SRB_IKFL.CARD_OPERATIONS add ORIGINAL_CATEGORY_NAME varchar2(100)
/
alter table SRB_IKFL.CARD_OPERATION_CATEGORIES add UUID varchar2(32)
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set UUID = DBMS_RANDOM.STRING('X', 32) where nvl(LOGIN_ID, -1)=-1
/
create unique index SRB_IKFL.I_CARD_CATEGORIES_UUID on SRB_IKFL.CARD_OPERATION_CATEGORIES(
    UUID
) tablespace INDX
/

-- Номер ревизии: 60654
-- Комментарий: Привязка категорий и цветов
alter table SRB_IKFL.CARD_OPERATION_CATEGORIES add COLOR varchar2(6)
/

-- Номер ревизии: 60654
-- Комментарий: Привязка категорий и цветов
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='72bf44' where NAME='Автомобиль' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='8967b0' where NAME='Перевод с карты' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='47b082' where NAME='Выдача наличных' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='f9b37b' where NAME='Коммунальные платежи, связь, интернет' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='fff450' where NAME='Здоровье и красота' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='009597' where NAME='Одежда и аксессуары' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='74489d' where NAME='Образование' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='d4711a' where NAME='Отдых и развлечения' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='89c765' where NAME='Супермаркеты' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='00599d' where NAME='Прочие расходы' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='aa55a1' where NAME='Транспорт' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='ed1c24' where NAME='Комиссия' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='d89016' where NAME='Погашение кредитов' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='ce181e' where NAME='Путешествия' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='f58220' where NAME='Все для дома' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='62a73b' where NAME='Рестораны и кафе' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='0066b3' where NAME='Искусство' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='f04e4c' where NAME='Перевод на вклад' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='faa61a' where NAME='Перевод между своими картами' and INCOME='0' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='fecd7f' where NAME='Перевод во вне' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='00a65d' where NAME='Внесение наличных' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='1c3687' where NAME='Зачисления' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='cf3734' where NAME='Перевод на карту' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='e3d200' where NAME='Возврат, отмена операций' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='00aaad' where NAME='Прочие поступления' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='8e187c' where NAME='Перевод со вклада' and nvl(LOGIN_ID,-1)=-1
/
update SRB_IKFL.CARD_OPERATION_CATEGORIES set COLOR='00b274' where NAME='Перевод между своими картами' and INCOME='1' and nvl(LOGIN_ID,-1)=-1
/

-- проставление цветов для АЛФ
merge into SRB_IKFL.CARD_OPERATION_CATEGORIES u
using (
    select ID, ROWNUM rn from (
        select ID
        from SRB_IKFL.CARD_OPERATION_CATEGORIES
        WHERE LOGIN_ID is not null
        ORDER BY LOGIN_ID
    )
) c
on (u.id = c.id)
when matched then update set u.COLOR = (CASE mod(c.rn-1,30)
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

/

-- Номер ревизии: 61366
-- Комментарий: Сохранение логинов клиента из соц. сетей. Модель БД.
alter table SRB_IKFL.USER_SOCIAL_IDS
   add constraint FK_USER_SOC_IDS_TO_LOGINS foreign key (LOGIN_ID)
      references SRB_IKFL.LOGINS (ID)
      on delete cascade
/

-- Номер ревизии: 61370
-- Комментарий: Профиль: Сохранение аватара. Работа с аватарами и настройками пользователя
alter table SRB_IKFL.PROFILE add AVATAR_IMAGE_ID integer
/
create index SRB_IKFL."DXFK_AVATAR" on SRB_IKFL.PROFILE(
   AVATAR_IMAGE_ID
) tablespace INDX parallel 64
/
alter index SRB_IKFL."DXFK_AVATAR" noparallel
/
alter table SRB_IKFL.PROFILE add constraint FK_PROFILE_FK_AVATAR_USER_IMA foreign key (AVATAR_IMAGE_ID)
   references SRB_IKFL.USER_IMAGES(ID) enable novalidate
/

-- Номер ревизии: 61504
-- Комментарий: Актуализация текстов сообщений
alter table SRB_IKFL.NOTIFICATIONS add (
	DOCUMENT_STATE           varchar2(25)  null,
	DOCUMENT_TYPE            varchar2(50)  null,
	NAME_AUTO_PAYMENT        varchar2(100) null,
	RECIPIENT_ACCOUNT_NUMBER varchar2(25)  null,
	RECIPIENT_ACCOUNT_TYPE   varchar2(32)  null
)
/
update SRB_IKFL.NOTIFICATIONS set DOCUMENT_STATE='EXECUTED' where 
    NAME='PaymentExecuteNotification' 
or  NAME='AccountRestChangeLowNotification' 
or  NAME='AccountRestChangeNotification'
or  NAME='LossPassbookNotification'
or  NAME='RefuseLongOfferNotification'
or  NAME='LongOfferNotification'
/
update SRB_IKFL.NOTIFICATIONS set DOCUMENT_TYPE='CardJurIntraBankTransfer' where NAME='PaymentExecuteNotification'
/
update SRB_IKFL.NOTIFICATIONS set DOCUMENT_TYPE='CardJurIntraBankTransfer' where NAME='AccountRestChangeLowNotification'
/
update SRB_IKFL.NOTIFICATIONS set DOCUMENT_TYPE='CardJurIntraBankTransfer' where NAME='AccountRestChangeNotification'
/
update SRB_IKFL.NOTIFICATIONS set DOCUMENT_TYPE='LossPassbookApplicationClaim' where NAME='LossPassbookNotification'
/
update SRB_IKFL.NOTIFICATIONS set DOCUMENT_TYPE='RefuseAutoPayment' where NAME='RefuseLongOfferNotification'
/
update SRB_IKFL.NOTIFICATIONS set DOCUMENT_TYPE='CreateAutoPayment' where NAME='LongOfferNotification'
/
update SRB_IKFL.NOTIFICATIONS set NAME='PaymentExecuteNotification' where
    NAME='AccountRestChangeLowNotification'
or  NAME='AccountRestChangeNotification'
or  NAME='LossPassbookNotification'
or  NAME='RefuseLongOfferNotification'
or  NAME='LongOfferNotification'
/

-- Номер ревизии: 61537
-- Комментарий: Информирование клиента: статус по операциям
alter table SRB_IKFL.USERS set unused ( MESSAGE_SERVICE ) 
/
alter table SRB_IKFL.SUBSCRIPTIONS add (
	NOTIFICATION_TYPE varchar2(30),
	CHANNEL           varchar2(10)  
)
/
alter table SRB_IKFL.SUBSCRIPTIONS modify LOGIN_ID not null
/
update SRB_IKFL.SUBSCRIPTIONS subs set CHANNEL=(
	select CHANNEL from SRB_IKFL.DISTRIBUTION_CHANNEL dcha
	join SRB_IKFL.SUBSCRIPTION_TEMPLATES subtem on subtem.TEMPLATE_ID=dcha.ID
	where subtem.SUBSCRIPTION_ID=subs.ID
)
/
update SRB_IKFL.SUBSCRIPTIONS set NOTIFICATION_TYPE='operationNotification'
/
alter table SRB_IKFL.SUBSCRIPTIONS modify NOTIFICATION_TYPE not null
/
alter table SRB_IKFL.SUBSCRIPTIONS modify CHANNEL not null
/
alter table SRB_IKFL.SUBSCRIPTIONS drop column SCHEDULE_ID
/
create unique index SRB_IKFL.UNIQ_SUBSCRIPTION on SRB_IKFL.SUBSCRIPTIONS (
   LOGIN_ID ASC,
   NOTIFICATION_TYPE ASC
) tablespace INDX
/
--нет в проме
--drop index SRB_IKFL.UK_3
--/
insert into SRB_IKFL.PAYMENT_NOTIFICATIONS (ID, LOGIN,DATE_CREATED,NAME,DOCUMENT_STATE,ACCOUNT_NUMBER,ACCOUNT_RESOURCE_TYPE,TRANSACTION_SUM, CURRENCY,NAME_OR_TYPE,DOCUMENT_TYPE,NAME_AUTO_PAYMENT,RECIPIENT_ACCOUNT_NUMBER,RECIPIENT_ACCOUNT_TYPE)  
	select SRB_IKFL.S_PAYMENT_NOTIFICATIONS.nextval, LOGIN,DATE_CREATED,NAME,DOCUMENT_STATE,ACCOUNT_NUMBER,ACCOUNT_RESOURCE_TYPE,TRANSACTION_SUM, CURRENCY,NAME_OR_TYPE,DOCUMENT_TYPE,NAME_AUTO_PAYMENT,RECIPIENT_ACCOUNT_NUMBER,RECIPIENT_ACCOUNT_TYPE 
		from SRB_IKFL.NOTIFICATIONS
/

drop table SRB_IKFL.ACCOUNT_PARAMETERS
/
--нет в проме
--drop sequence SRB_IKFL.S_ACCOUNT_PARAMETERS
--/
drop table SRB_IKFL.CARD_PARAMETERS
/
--нет в проме
--drop sequence SRB_IKFL.S_CARD_PARAMETERS
--/
drop table SRB_IKFL.SUBSCRIPTION_TEMPLATES
/
--нет в проме
--drop sequence SRB_IKFL.S_SUBSCRIPTION_TEMPLATES
--/
drop table SRB_IKFL.SUBSCRIPTION_PARAMETERS
/
drop sequence SRB_IKFL.S_SUBSCRIPTION_PARAMETERS
/
drop table SRB_IKFL.NOTIFICATIONS
/
drop sequence SRB_IKFL.S_NOTIFICATIONS
/
drop table SRB_IKFL.SCHEDULE
/
drop sequence SRB_IKFL.S_SCHEDULE
/
drop table SRB_IKFL.DISTRIBUTION_CHANNEL
/
drop sequence SRB_IKFL.S_DISTRIBUTION_CHANNEL
/
drop table SRB_IKFL.DISTRIBUTIONS
/
drop sequence SRB_IKFL.S_DISTRIBUTIONS
/

-- Номер ревизии: 61541
-- Комментарий: Изменение тарифного плана SMS запросом
alter table SRB_IKFL.ERMB_TARIF add STATUS varchar2(12) null
/
update SRB_IKFL.ERMB_TARIF set STATUS = 'ACTIVE'
/
alter table SRB_IKFL.ERMB_TARIF modify STATUS varchar2(12) not null
/

-- Номер ревизии: 61586
-- Комментарий: Управление подпиской. clientId
alter table SRB_IKFL.CARD_LINKS add CLIENT_ID varchar2(255)
/

-- Номер ревизии: 61606
-- Комментарий: ФОС. отключение работы с группами клиентов
delete from SRB_IKFL.RECIPIENTS where MAIL_ID in (select ID from SRB_IKFL.MAIL where RECIPIENT_TYPE = 'GROUP')
/
delete from SRB_IKFL.MAIL where RECIPIENT_TYPE = 'GROUP'
/

-- Номер ревизии: 61610
-- Комментарий: Перенести каналы подписок клиента из профиля в таблицу подписок
insert into SRB_IKFL.SUBSCRIPTIONS (id, LOGIN_ID, NOTIFICATION_TYPE, CHANNEL)
	select /*+parallel(p,16)*/ SRB_IKFL.S_SUBSCRIPTIONS.nextval , LOGIN_ID LOGIN_ID,'loginNotification' NOTIFICATION_TYPE, LOGON_NOTIFICATION_TYPE CHANNEL
		from SRB_IKFL.PROFILE where LOGON_NOTIFICATION_TYPE is not null
/
insert into SRB_IKFL.SUBSCRIPTIONS (id, LOGIN_ID, NOTIFICATION_TYPE, CHANNEL)
	select /*+parallel(p,16)*/ SRB_IKFL.S_SUBSCRIPTIONS.nextval , LOGIN_ID LOGIN_ID,'mailNotification' NOTIFICATION_TYPE, MAIL_NOTIFICATION_TYPE CHANNEL
		from SRB_IKFL.PROFILE where MAIL_NOTIFICATION_TYPE is not null
/
alter table SRB_IKFL.PROFILE set unused (
	LOGON_NOTIFICATION_TYPE,
	MAIL_NOTIFICATION_TYPE
)
/

-- Номер ревизии: 61614
-- Комментарий: Добавление изображения  для цели
alter table SRB_IKFL.ACCOUNT_TARGETS add IMAGE_ID number
/

-- Номер ревизии: 61676
-- Комментарий: Структура БД. Таблицы для адресов
alter table SRB_IKFL.LOANCLAIM_AREA
   add constraint LC_AREA_TYPEOFAREA_FK foreign key (TYPEOFAREA)
      references SRB_IKFL.LOANCLAIM_TYPE_OF_AREA (CODE)
/
alter table SRB_IKFL.LOANCLAIM_CITY
   add constraint LC_CITY_TYPEOFCITY_FK foreign key (TYPEOFCITY)
      references SRB_IKFL.LOANCLAIM_TYPE_OF_CITY (CODE)
/
alter table SRB_IKFL.LOANCLAIM_SETTLEMENT
   add constraint LC_SETTL_TYPEOFLOCALITY_FK foreign key (TYPEOFLOCALITY)
      references SRB_IKFL.LOANCLAIM_TYPE_OF_LOCALITY (CODE)
/
alter table SRB_IKFL.LOANCLAIM_STREET
   add constraint LC_STREET_TYPEOFSTREET_FK foreign key (TYPEOFSTREET)
      references SRB_IKFL.LOANCLAIM_TYPE_OF_STREET (CODE)
/

-- Номер ревизии: 61682
-- Комментарий: Доработки оплаты документов с помощью штрих-кодов.
alter table SRB_IKFL.REGIONS add (
	PROVIDER_CODE_MAPI varchar2(200) null,
	PROVIDER_CODE_ATM  varchar2(200) null
)
/

-- Номер ревизии: 61733
-- Комментарий: BUG068530: Убрать фулсканы с таблицы ORDERS
create index SRB_IKFL.IDX_NOT_NOTIFIED_UEC on SRB_IKFL.ORDERS (
	case when SYSTEM_NAME = 'UEC' and (STATUS='NOT_SEND' or  STATUS='ERROR') then '1' else null end ASC 
) parallel 32  tablespace INDX
/
alter index SRB_IKFL.IDX_NOT_NOTIFIED_UEC noparallel
/

-- Номер ревизии: 61759
-- Комментарий: Профиль: связка услуг и пользовательских документов.
alter table SRB_IKFL.INVOICES_FOR_USER_DOCUMENTS 
	add constraint FK_INVC_FR_USR_DOCS_LGN_ID foreign key (LOGIN_ID) references SRB_IKFL.LOGINS(ID)
/
alter table SRB_IKFL.INVOICES_FOR_USER_DOCUMENTS
	add constraint FK_INVC_FR_USR_DOCS_INVSUBID foreign key (INVOICE_SUBSCRIPTION_ID) references SRB_IKFL.INVOICE_SUBSCRIPTIONS (ID)
/
--добавлен по модели БД, в рассылке нет
alter table SRB_IKFL.INVOICES
   add constraint FK_INVOICES_REFERENCE_INVOICE_ foreign key (INVOICE_SUBSCRIPTION_ID)
      references SRB_IKFL.INVOICE_SUBSCRIPTIONS (ID)
/


-- Номер ревизии: 61776
-- Комментарий: CHG072541: Доработка сохранения новостных подписок
alter table SRB_IKFL.NEWS_DISTRIBUTIONS drop column LAST_PROCESSED_ID
/
delete from SRB_IKFL.SUBSCRIPTIONS where channel = 'none' and NOTIFICATION_TYPE ='newsNotification'
/
alter table SRB_IKFL.SUBSCRIPTIONS add TB varchar2(4) null
/
--TODO оптимизация по времени
update (
  select /*+parallel(s, 16) parallel(u, 16)*/s.id, s.tb, d.tb new_tb  from SRB_IKFL.SUBSCRIPTIONS s
  inner join SRB_IKFL.USERS u on u.login_id=s.login_id
  inner join SRB_IKFL.DEPARTMENTS d on d.id=u.department_id
) subscr set subscr.TB=subscr.NEW_TB
/
--чистим мусор, подписки которым нет соответствия в профилях
delete from SRB_IKFL.SUBSCRIPTIONS s where s.tb is null
/
alter table SRB_IKFL.SUBSCRIPTIONS modify TB not null
/
create index SRB_IKFL.SUBSCR_NEWS_IND on SRB_IKFL.SUBSCRIPTIONS
(
   NOTIFICATION_TYPE ASC,
   TB ASC,
   LOGIN_ID ASC
) tablespace INDX
/
insert into SRB_IKFL.SUBSCRIPTIONS (id, LOGIN_ID, NOTIFICATION_TYPE, CHANNEL, TB)
	select SRB_IKFL.S_SUBSCRIPTIONS.nextval , LOGIN_ID,'newsNotification', 'email', TB 
		from SRB_IKFL.NEWS_SUBSCRIPTIONS 
/
drop index SRB_IKFL.NEWS_SUBSCRIPTIONS_TB_INDEX
/
drop table SRB_IKFL.NEWS_SUBSCRIPTIONS 
/
drop sequence SRB_IKFL.S_NEWS_SUBSCRIPTIONS
/

-- Номер ревизии: 61614
-- Комментарий: ИОК
alter table SRB_IKFL.ACCOUNT_TARGETS modify IMAGE_ID integer
/

-- Номер ревизии: 61878
-- Комментарий: Профиль. Конвертация БД.
insert into SRB_IKFL.USER_ADDED_DOCUMENTS (ID, LOGIN_ID, DOC_NUMBER, DOCUMENT_TYPE) 
	select /*+parallel(u, 16)*/ SRB_IKFL.S_USER_ADDED_DOCUMENTS.nextVal as ID, u.LOGIN_ID as LOGIN_ID, u.SNILS as DOC_NUMBER, 'SNILS' as DOCUMENT_TYPE 
		from SRB_IKFL.USERS u where u.SNILS is not null
/
insert into SRB_IKFL.USER_ADDED_DOCUMENTS (ID, LOGIN_ID, DOC_NUMBER, DOCUMENT_TYPE) 
	select /*+parallel(u, 16)*/ SRB_IKFL.S_USER_ADDED_DOCUMENTS.nextVal as ID, u.LOGIN_ID as LOGIN_ID, u.INN as DOC_NUMBER, 'INN' as DOCUMENT_TYPE 
		from SRB_IKFL.USERS u where u.INN is not null
/
alter table SRB_IKFL.USERS set unused ( INN, SNILS)
/

-- Номер ревизии: 61961
-- Комментарий: Доработки ЕРМБ. Абонентская плата. Доработка структуры БД
alter table SRB_IKFL.ERMB_PROFILE add (
	FPP_UNLOAD_DATE    timestamp null,
	CHARGE_NEXT_DATE   timestamp null,
	CHARGE_NEXT_AMOUNT number    null,
	CHARGE_NEXT_PERIOD number    null,
	CHARGE_DAY_OF_MONTH number,
	FPP_IN_PROGRESS    char(1)
)
/
update SRB_IKFL.ERMB_PROFILE set CHARGE_NEXT_DATE = SYSDATE where CHARGE_NEXT_DATE is null
/
alter table SRB_IKFL.ERMB_PROFILE modify CHARGE_NEXT_DATE not null
/

update SRB_IKFL.ERMB_PROFILE set FPP_IN_PROGRESS = 0
/
alter table SRB_IKFL.ERMB_PROFILE modify FPP_IN_PROGRESS not null
/

alter table SRB_IKFL.ERMB_PROFILE add SERVICE_STATUS_TMP char(1)
/
update SRB_IKFL.ERMB_PROFILE set SERVICE_STATUS_TMP = case when SERVICE_STATUS = 'ACTIVE' then '1' else '0' end
/
alter table SRB_IKFL.ERMB_PROFILE modify SERVICE_STATUS_TMP not null
/

alter table SRB_IKFL.ERMB_PROFILE add CLIENT_BLOCKED char(1)
/
update SRB_IKFL.ERMB_PROFILE set CLIENT_BLOCKED = case when SERVICE_STATUS = 'LOCK' then '1' else '0' end
/
alter table SRB_IKFL.ERMB_PROFILE modify CLIENT_BLOCKED not null
/

alter table SRB_IKFL.ERMB_PROFILE add PAYMENT_BLOCKED char(1)
/
update SRB_IKFL.ERMB_PROFILE set PAYMENT_BLOCKED = case when SERVICE_STATUS = 'NOT_PAID' then '1' else '0' end
/
alter table SRB_IKFL.ERMB_PROFILE modify PAYMENT_BLOCKED not null
/

alter table SRB_IKFL.ERMB_PROFILE drop column SERVICE_STATUS
/
alter table SRB_IKFL.ERMB_PROFILE rename column SERVICE_STATUS_TMP to SERVICE_STATUS
/

-- Номер ревизии: 61970
-- Комментарий: Одноклассники. Доработка сущности "мобильной платформы".
alter table SRB_IKFL.MOBILE_PLATFORMS add (
	IS_SOCIAL           char(1),
	UNALLOWED_BROWSERS  varchar2(100),
	USE_CAPTCHA         char(1)
)
/
update SRB_IKFL.MOBILE_PLATFORMS set 
  USE_CAPTCHA=0,
  IS_SOCIAL=decode(platform_id,'Odnoklas', 1, 0 ),
  UNALLOWED_BROWSERS=decode(platform_id,'iPhone', '.*iPhone.*', 'iPad', '.*iPad.*', 'winPhone', '.*Windows Phone.*', 'android', 'AAAAAAAAAA', UNALLOWED_BROWSERS )
/
alter table SRB_IKFL.MOBILE_PLATFORMS modify (
	IS_SOCIAL           default 0 not null,
	USE_CAPTCHA         default 1 not null
)
/


-- Номер ревизии: 61979
-- Комментарий: ФОС. Синхронизация справочников площадок и тематик сообщений
alter table SRB_IKFL.MAIL_SUBJECTS add UUID VARCHAR2(32)
/
update SRB_IKFL.MAIL_SUBJECTS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table SRB_IKFL.MAIL_SUBJECTS modify UUID not null
/
create unique index SRB_IKFL.I_MAIL_SUBJECTS_UUID on SRB_IKFL.MAIL_SUBJECTS(UUID) tablespace INDX
/

alter table SRB_IKFL.CONTACT_CENTER_AREAS add UUID varchar2(32)
/
update SRB_IKFL.CONTACT_CENTER_AREAS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table SRB_IKFL.CONTACT_CENTER_AREAS modify UUID not null
/
create unique index SRB_IKFL.I_CONTACT_CENTER_AREAS_UUID on SRB_IKFL.CONTACT_CENTER_AREAS(UUID) tablespace INDX
/

drop index SRB_IKFL."DXFK_C_C_AREA_DEP_TO_C_C_AREA"
/

-- Номер ревизии: 62110 
-- Комментарий: Справочник подразделений. Добавление признака активности подразделения.
alter table SRB_IKFL.DEPARTMENTS add (ACTIVE  char(1))
/
update SRB_IKFL.DEPARTMENTS set ACTIVE='1'
/
alter table SRB_IKFL.DEPARTMENTS modify (ACTIVE  not null)
/

-- Номер ревизии: 62220
-- Комментарий: BUG073185: [ISUP] Вернуть раздельное выставление тех.перерывов в блоках
alter table SRB_IKFL.TECHNOBREAKS drop constraint AK_UK_TECHNOBREAKS_P
/
alter table SRB_IKFL.TECHNOBREAKS drop column UUID
/

-- Номер ревизии: 62229
-- Комментарий: ENH070442: Доработать алгоритм работы элемента "добавить в личное меню" 
--TODO объединить в один скрипт, если останется время
update SRB_IKFL.FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/accounts/graphic-abstract.do', '/private/accounts/operations.do'),
	NAME = replace(NAME, 'Графическая выписка по', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Графическая выписка по', 'Последние операции по')
where LINK like '%/private/accounts/graphic-abstract.do%' 
and not exists(
        select favouriteLinks.ID from SRB_IKFL.FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/accounts/operations.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from SRB_IKFL.FAVOURITE_LINKS where LINK like '%/private/accounts/graphic-abstract.do%'
/
update SRB_IKFL.FAVOURITE_LINKS links set LINK = replace(LINK, '/private/account/payments.do', '/private/accounts/operations.do'),
NAME = replace(NAME, 'Шаблоны и платежи по', 'Последние операции по'),
PATTERN_NAME = replace(PATTERN_NAME, 'Шаблоны и платежи по', 'Последние операции по')
where LINK like '%/private/account/payments.do%' 
and not exists(
        select favouriteLinks.ID from SRB_IKFL.FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/accounts/operations.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from SRB_IKFL.FAVOURITE_LINKS where LINK like '%/private/account/payments.do%'
/
update SRB_IKFL.FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/accounts/info.do', '/private/accounts/operations.do'),
	NAME = replace(NAME, 'Детальная информация по', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Детальная информация по', 'Последние операции по')
where LINK like '%/private/accounts/info.do%' 
and not exists(
        select favouriteLinks.ID from SRB_IKFL.FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/accounts/operations.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from SRB_IKFL.FAVOURITE_LINKS where LINK like '%/private/accounts/info.do%'
/
update SRB_IKFL.FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/cards/graphic-abstract.do', '/private/cards/info.do'),
	NAME = replace(NAME, 'Графическая выписка по', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Графическая выписка по', 'Последние операции по')
where LINK like '%/private/cards/graphic-abstract.do%' 
and not exists(
        select favouriteLinks.ID from SRB_IKFL.FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/cards/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from SRB_IKFL.FAVOURITE_LINKS where LINK like '%/private/cards/graphic-abstract.do%'
/
update SRB_IKFL.FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/cards/payments.do', '/private/cards/info.do'),
	NAME = replace(NAME, 'Шаблоны и платежи с', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Шаблоны и платежи с', 'Последние операции по')
where LINK like '%/private/cards/payments.do%' 
and not exists(
        select favouriteLinks.ID from SRB_IKFL.FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/cards/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from SRB_IKFL.FAVOURITE_LINKS where LINK like '%/private/cards/payments.do%'
/
update SRB_IKFL.FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/cards/detail.do', '/private/cards/info.do'),
	NAME = replace(NAME, 'Детальная информация по', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Детальная информация по', 'Последние операции по')
where LINK like '%/private/cards/detail.do%' 
and not exists(
        select favouriteLinks.ID from SRB_IKFL.FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/cards/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from SRB_IKFL.FAVOURITE_LINKS where LINK like '%/private/cards/detail.do%'
/
update SRB_IKFL.FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/loans/info.do', '/private/loans/detail.do'),
	NAME = replace(NAME, 'График платежей по', 'Детальная информация по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'График платежей по', 'Детальная информация по')
where LINK like '%/private/loans/info.do%' 
and not exists(
        select favouriteLinks.ID from SRB_IKFL.FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/loans/detail.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from SRB_IKFL.FAVOURITE_LINKS where LINK like '%/private/loans/info.do%'
/
update SRB_IKFL.FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/depo/info/position.do', '/private/depo/documents.do'),
	NAME = replace(NAME, 'Позиция по', 'Список документов по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Позиция по', 'Список документов по')
where LINK like '%/private/depo/info/position.do%' 
and not exists(
        select favouriteLinks.ID from SRB_IKFL.FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/depo/documents.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from SRB_IKFL.FAVOURITE_LINKS where LINK like '%/private/depo/info/position.do%'
/
update SRB_IKFL.FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/depo/info/debt.do', '/private/depo/documents.do'),
	NAME = replace(NAME, 'Задолженность по', 'Список документов по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Задолженность по', 'Список документов по')
where LINK like '%/private/depo/info/debt.do%' 
and not exists(
        select favouriteLinks.ID from SRB_IKFL.FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/depo/documents.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from SRB_IKFL.FAVOURITE_LINKS where LINK like '%/private/depo/info/debt.do%'
/
update SRB_IKFL.FAVOURITE_LINKS links set 
	LINK = replace(LINK, '/private/ima/detail.do', '/private/ima/info.do'),
	NAME = replace(NAME, 'Детальная информация по', 'Последние операции по'),
	PATTERN_NAME = replace(PATTERN_NAME, 'Детальная информация по', 'Последние операции по')
where LINK like '%/private/ima/detail.do%' 
and not exists(
        select favouriteLinks.ID from SRB_IKFL.FAVOURITE_LINKS favouriteLinks 
        where favouriteLinks.LINK like '%/private/ima/info.do' || substr(links.LINK, INSTR(links.LINK, '?'))
	 	and favouriteLinks.LOGIN_ID = links.LOGIN_ID
	) 
/
delete from SRB_IKFL.FAVOURITE_LINKS where LINK like '%/private/ima/detail.do%'
/

-- Номер ревизии: 62288
-- Комментарий: Доработка логики исполнения шаблонов
alter table SRB_IKFL.SERVICE_PROVIDERS add PLANING_FOR_DEACTIVATE char(1)
/
update SRB_IKFL.SERVICE_PROVIDERS set PLANING_FOR_DEACTIVATE = 0 
/
alter table SRB_IKFL.SERVICE_PROVIDERS modify PLANING_FOR_DEACTIVATE not null
/

alter table SRB_IKFL.BILLINGS add TEMPLATE_STATE varchar2(30)
/

-- Номер ревизии: 62369
-- Комментарий: ENH070442: Доработать алгоритм работы элемента "добавить в личное меню" 
alter table SRB_IKFL.FAVOURITE_LINKS drop column IS_USE
/

-- Номер ревизии: 62402
-- Комментарий: Добавление интернет-заказов в функционал "счетов к оплате"
alter table SRB_IKFL.SHOP_ORDERS add ( "DELAYED_PAY_DATE" timestamp null ) 
/

-- Номер ревизии: 62460
-- Комментарий: ЕРМБ. Переименовал признак разрешение рекламных рассылок в признак запрета. 
alter table SRB_IKFL.ERMB_PROFILE rename column ADVERTISING to SUPPRESS_ADVERTISING
/
update SRB_IKFL.ERMB_PROFILE set SUPPRESS_ADVERTISING = decode(SUPPRESS_ADVERTISING, null, '0', '0', '1', '0')
/

-- Номер ревизии: 62496
-- Комментарий: Активация услуги ЕРМБ - Проставление флагов при создании ЕРМБ профиля
alter table SRB_IKFL.ERMB_PROFILE add COD_ACTIVATED char(1)
/
update SRB_IKFL.ERMB_PROFILE set COD_ACTIVATED = '0'
/
alter table SRB_IKFL.ERMB_PROFILE modify COD_ACTIVATED not null
/

alter table SRB_IKFL.ERMB_PROFILE add WAY_ACTIVATED char(1)
/
update SRB_IKFL.ERMB_PROFILE set WAY_ACTIVATED = '0'
/
alter table SRB_IKFL.ERMB_PROFILE modify WAY_ACTIVATED not null
/

alter table SRB_IKFL.ERMB_PROFILE add ACTIVATION_TRY_DATE timestamp
/

-- Номер ревизии: 62760
-- Комментарий: ENH070461: Уйти от использования ConnectorInfo в ЕРИБ и ЦСА 
drop table SRB_IKFL.CONNECTORS_INFO
/
drop sequence SRB_IKFL.S_CONNECTORS_INFO
/

-- Номер ревизии: 62894
-- Комментарий: иок(2)
update SRB_IKFL.ERMB_PROFILE set ACTIVATION_TRY_DATE = sysdate where ACTIVATION_TRY_DATE is null
/
alter table SRB_IKFL.ERMB_PROFILE modify ACTIVATION_TRY_DATE not null
/

create index SRB_IKFL.I_ACTIVATION_TRY_DATE on SRB_IKFL.ERMB_PROFILE (ACTIVATION_TRY_DATE ASC) tablespace INDX
/

-- Номер ревизии: 62929
-- Комментарий: BUG073990: [ЕРМБ ] некорректная работа джобов ЕРМБ
delete from SRB_IKFL.QRTZ_SIMPLE_TRIGGERS where TRIGGER_NAME in (
    select TRIGGER_NAME from SRB_IKFL.QRTZ_TRIGGERS where JOB_NAME in (
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

delete from SRB_IKFL.QRTZ_CRON_TRIGGERS where TRIGGER_NAME in
(
    select TRIGGER_NAME from SRB_IKFL.QRTZ_TRIGGERS where JOB_NAME in (
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

delete from SRB_IKFL.QRTZ_BLOB_TRIGGERS where TRIGGER_NAME in
(
    select TRIGGER_NAME from SRB_IKFL.QRTZ_TRIGGERS where JOB_NAME in (
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

delete from SRB_IKFL.QRTZ_TRIGGERS where JOB_NAME in (
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

delete from SRB_IKFL.QRTZ_JOB_DETAILS where JOB_NAME in (
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
-- Комментарий: доработка профиля ЕРМБ
update SRB_IKFL.ERMB_PROFILE set CHARGE_NEXT_AMOUNT = 0 where CHARGE_NEXT_AMOUNT is null
/
alter table SRB_IKFL.ERMB_PROFILE modify CHARGE_NEXT_AMOUNT not null
/

-- Номер ревизии: 62057
-- Комментарий: CHG072313: Секционировать OPERATION_CONFIRM_LOG 
alter table SRB_IKFL.OPERATION_CONFIRM_LOG rename to OPERATION_CONFIRM_LOG_O
/

create table SRB_IKFL.OPERATION_CONFIRM_LOG 
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
	partition P_START values less than (to_date('01-06-2014','DD-MM-YYYY')) tablespace INDX
)
/

create index SRB_IKFL.I_OPERATION_CONFIRM_LOG_ID ON SRB_IKFL.OPERATION_CONFIRM_LOG(
    ID
) local  tablespace INDX
/

create index SRB_IKFL.I_OPERATION_CONFIRM_UID ON SRB_IKFL.OPERATION_CONFIRM_LOG(
    OPERATION_UID
) global partition by hash (OPERATION_UID) partitions 128 tablespace INDX
/

-- Номер ревизии: 63246
-- Комментарий: BUG073908: Доработать отправку смс для операций с вкладов 
drop index SRB_IKFL.INDEX_SMS_CHANNEL_KEY 
/

create unique index SRB_IKFL.INDEX_SMS_CHANNEL_KEY on SRB_IKFL.SMS_RESOURCES (
   KEY ASC,
   CHANNEL ASC,
   SMS_TYPE ASC
) tablespace INDX
/

-- Номер ревизии: 63272
-- Комментарий: BUG071121: полное сканирование таблицы CSA_PROFILES 
drop view SRB_IKFL.INCOGNITO_PHONES
/
alter table SRB_IKFL.INCOGNITO_PHONES_BASE rename to INCOGNITO_PHONES
/
create index SRB_IKFL.I_INCOGNITO_PHONES on SRB_IKFL.INCOGNITO_PHONES (
	PHONE_NUMBER
) tablespace INDX
/
delete from SRB_IKFL.INCOGNITO_PHONES
/
alter table SRB_IKFL.INCOGNITO_PHONES add (PROFILE_ID integer not null)
/

--================================================================================================================
-- Номер ревизии: 62273
-- Комментарий: Доработать оплату услуг для оплаты своего телефона
alter index SRB_IKFL.RECENTLYFILLEDDATA_LOGIN_INDX rename to IDX_RFFD_LOGIN_ID
/

-- Номер ревизии: 63276
-- Комментарий: BUG070523: Лишнее поле в расширенной заявке на кредит
delete from SRB_IKFL.LOANCLAIM_FAMILY_STATUS
/
alter table SRB_IKFL.LOANCLAIM_FAMILY_STATUS modify (spouse_info_required varchar2(15))
/
insert into SRB_IKFL.LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('0', 'Холост/не замужем', 'NOT')
/
insert into SRB_IKFL.LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('1', 'В разводе', 'NOT')
/
insert into SRB_IKFL.LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('2', 'Женат/замужем', 'REQUIRED')
/
insert into SRB_IKFL.LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('3', 'Вдовец/вдова', 'NOT')
/
insert into SRB_IKFL.LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('4', 'Гражданский брак', 'WITHOUT_PRENUP')
/

-- Номер ревизии: 63439
-- Комментарий: Добавление индекса для запроса выгрузки ФПП абонентской платы ЕРМБ
create index SRB_IKFL.IDX_ERMB_SRV_FEE_CHARGE_DATE on SRB_IKFL.ERMB_PROFILE(CHARGE_NEXT_DATE) tablespace INDX
/

-- Номер ревизии: 63575
-- Комментарий: BUG074625: [ISUP] Дублирование заявок на загрузку операций из ИПС.
--TODO чистка дублей
create unique index SRB_IKFL.UNIQUE_LOGIN_ID_CARD_NUMBER on SRB_IKFL.CARD_OPERATION_CLAIMS(
	LOGIN_ID, 
	CARD_NUMBER
) tablespace INDX parallel 32
/	
	
alter index SRB_IKFL.UNIQUE_LOGIN_ID_CARD_NUMBER noparallel
/
alter table SRB_IKFL.CARD_OPERATION_CLAIMS 
	add constraint UNIQUE_LOGIN_ID_CARD_NUMBER unique (LOGIN_ID, CARD_NUMBER) 
		using index SRB_IKFL.UNIQUE_LOGIN_ID_CARD_NUMBER enable novalidate
/

-- Номер ревизии: 63575
-- Комментарий: BUG074625: [ISUP] Дублирование заявок на загрузку операций из ИПС. Лишний индекс
drop index SRB_IKFL.DXFK_CARDOP_CLAIM_OWNER
/

-- Номер ревизии: 63629
-- Комментарий: BUG074333: [Шаблоны] Не устанавливается признак подтверждать операции по шаблону СМС-паролем 
update SRB_IKFL.PROPERTIES set PROPERTY_VALUE = 'true' where PROPERTY_KEY like 'com.rssl.iccs.favourite.templates.needConfirm%' and PROPERTY_VALUE = 'on'
/

-- Номер ревизии: 62966
-- Комментарий: доработка профиля ЕРМБ
update SRB_IKFL.ERMB_PROFILE set CHARGE_NEXT_PERIOD = 1 where SERVICE_STATUS = '1' and CHARGE_NEXT_PERIOD is null
/
update SRB_IKFL.ERMB_PROFILE set CHARGE_DAY_OF_MONTH = 1 where SERVICE_STATUS = '1' and CHARGE_DAY_OF_MONTH is null
/

-- Номер ревизии: 64042
-- Комментарий: ENH074735  [ЕРМБ] Удаление недовведённых платежей на обработке каждого СМС-запроса 
alter table SRB_IKFL.ERMB_PROFILE add INCOMPLETE_SMS_PAYMENT integer
/

-- Номер ревизии: 64115
-- Комментарий: ENH074735  [ЕРМБ] Удаление недовведённых платежей на обработке каждого СМС-запроса 
delete from SRB_IKFL.QRTZ_ERMB_SIMPLE_TRIGGERS where TRIGGER_NAME = 'IncompletePaymentsRemoverTrigger'
/
delete from SRB_IKFL.QRTZ_ERMB_TRIGGERS where TRIGGER_NAME = 'IncompletePaymentsRemoverTrigger'
/
delete from SRB_IKFL.QRTZ_ERMB_JOB_DETAILS where JOB_NAME = 'IncompletePaymentsRemoverJob'
/

delete from SRB_IKFL.QRTZ_ERMB_SIMPLE_TRIGGERS where TRIGGER_NAME = 'IncompletePaymentsRemoverTrigger'
/
delete from SRB_IKFL.QRTZ_ERMB_TRIGGERS where TRIGGER_NAME = 'IncompletePaymentsRemoverTrigger'
/
delete from SRB_IKFL.QRTZ_ERMB_JOB_DETAILS where JOB_NAME = 'IncompletePaymentsRemoverJob'
/	  

-- Номер ревизии: 64304
-- Комментарий: BUG072501: [ЕРМБ смс-канал] некорректно работает проверка суточного заградительного лимита при оплате ПУ
alter table SRB_IKFL.SMS_RESOURCES modify DESCRIPTION varchar2(4000)
/

-- Номер ревизии: 64395
-- Комментарий: BUG075192: [ЕРМБ абон плата] некорректно формируются файлы прямого потока
alter table SRB_IKFL.ERMB_PROFILE add FPP_BLOCKED char(1)
/
update SRB_IKFL.ERMB_PROFILE set FPP_BLOCKED = '0'
/
alter table SRB_IKFL.ERMB_PROFILE modify FPP_BLOCKED not null
/

-- Номер ревизии: 64431
-- Комментарий: BUG074685: Не сохраняются настройки Права доступа клиента по умолчанию
alter table SRB_IKFL.DEFAULT_ACCESS_SCHEMES add DEPARTMENT_TB varchar2(4)
/
update SRB_IKFL.DEFAULT_ACCESS_SCHEMES ds set ds.DEPARTMENT_TB = (select d.TB from SRB_IKFL.DEPARTMENTS d where d.ID = ds.DEPARTMENT_ID)
/
alter table SRB_IKFL.DEFAULT_ACCESS_SCHEMES drop constraint AK_DEFAULT_SCHEMES_UNIQUE
/

create unique index SRB_IKFL.I_DEFAULT_ACCESS_SCHEMES on SRB_IKFL.DEFAULT_ACCESS_SCHEMES(
    CREATION_TYPE,
    NVL(DEPARTMENT_TB, 'NULL')
) tablespace INDX
/

alter table SRB_IKFL.DEFAULT_ACCESS_SCHEMES drop constraint FK_DEFAULT__FK_DEF_SC_DEPARTME
/
alter table SRB_IKFL.DEFAULT_ACCESS_SCHEMES drop column DEPARTMENT_ID
/


-- Номер ревизии: 64730
-- Комментарий: ENH072991: Почему логин в профиле неуникален?
drop index SRB_IKFL.DXREFERENCE_20
/

create unique index SRB_IKFL.IDX_PROFILE_LOGIN on SRB_IKFL.PROFILE (
   LOGIN_ID ASC
) parallel 64 tablespace INDX
/
alter index SRB_IKFL.IDX_PROFILE_LOGIN noparallel
/


-- Номер ревизии: 64318
-- Комментарий: BUG074650: [Синхронизация справочников] Ошибка при синхронизации справочников АЛФ. 
create index SRB_IKFL.I_MCC_INCOME_CATEGORY on SRB_IKFL.MERCHANT_CATEGORY_CODES (
   INCOME_OPERATION_CATEGORY_ID ASC
) tablespace INDX
/

create index SRB_IKFL.I_MCC_OUTCOME_CATEGORY on SRB_IKFL.MERCHANT_CATEGORY_CODES (
   OUTCOME_OPERATION_CATEGORY_ID ASC
) tablespace INDX
/


-- Номер ревизии: 65154, 65156
-- Номер версии: 1.18, v.1.18_release_14.0_PSI
-- Комментарий: BUG075776: Репликация поставщиков услуг: ошибка при репликации ПУ, у которого есть алиасы ЕРМБ
create index SRB_IKFL."DXFK_SMS_ALIAS_TO_PROVIDER" on SRB_IKFL.PROVIDER_SMS_ALIAS (
   SERVICE_PROVIDER_ID
) tablespace INDX
/

alter table SRB_IKFL.PROVIDER_SMS_ALIAS
add constraint FK_SMS_ALIAS_TO_PROVIDER foreign key (SERVICE_PROVIDER_ID)
    references SRB_IKFL.SERVICE_PROVIDERS (ID)
		on delete cascade
/

alter table SRB_IKFL.PROVIDER_SMS_ALIAS_FIELD drop constraint FK_PROVIDER_FK_ALIAS__PROVIDER
/

alter table SRB_IKFL.PROVIDER_SMS_ALIAS_FIELD
add constraint FK_PROVIDER_FK_ALIAS__PROVIDER foreign key (PROVIDER_SMS_ALIAS_ID)
    references SRB_IKFL.PROVIDER_SMS_ALIAS (ID)
    on delete cascade
/

alter table SRB_IKFL.PROVIDER_SMS_ALIAS_FIELD drop constraint SRB_IKFL.FK_PROVIDER_FK_ALIAS__FIELD_DE
/

alter table SRB_IKFL.PROVIDER_SMS_ALIAS_FIELD
add constraint FK_PROVIDER_FK_ALIAS__FIELD_DE foreign key (FIELD_DESCRIPTION_ID)
    references SRB_IKFL.FIELD_DESCRIPTIONS (ID)
    on delete cascade
/

insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Мобильная связь'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Интернет'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'ТВ'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Квартплата'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'ТСЖ, ЖСК'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Электроэнергия'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Домашний телефон'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Водоснабжение'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Газ'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Теплоснабжение'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Охранные услуги, домофон'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'ГСК, автостоянки'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'ДЕЗы, ЖЭКи, ремонтные службы'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'ДСК, садовые товарищества'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'ГИБДД'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Федеральная налоговая служба'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Службы судебных приставов'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Пенсионные фонды'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Фонды социального страхования'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Услуги, оказываемые ГИБДД'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Суды'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'БТИ'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Федеральная регистрационная служба'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Федеральная миграционная служба'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Федеральные службы'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Росреестр'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Детские сады и дошкольные учреждения'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Образование' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'ВУЗы, школы, колледжи, техникумы'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Образование' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Другие образовательные учреждения'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Образование' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Погашение кредитов в другом банке'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Погашение кредитов' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Электронные деньги'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	(select ps.CODE, coc.id from SRB_IKFL.PAYMENT_SERVICES ps,SRB_IKFL.CARD_OPERATION_CATEGORIES coc where coc.NAME = 'Прочие расходы' and coc.LOGIN_ID is null and ps.NAME = 'Сетевой маркетинг')
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Здоровье'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Здоровье и красота' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Спорт и отдых'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Отдых и развлечения' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Строительные организации'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Коммунальные платежи, связь, интернет' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Онлайн игры'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Отдых и развлечения' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'ПИФы'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Брокерские счета'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Страховые компании'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Финансовые организации'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Медицинское страхование'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Прочие услуги'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Другие услуги'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Благотворительные фонды'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Социальные перечисления'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Общественные организации'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Билеты'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Путешествия' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Турагентства'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Путешествия' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Пополнение брокерских счетов'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Путешествия' and LOGIN_ID is null))
/
insert into SRB_IKFL.PAYMENT_SERVICES_TO_CATEGORIES (SERVICE_CODE, CATEGORY_ID)
	values ((select CODE from SRB_IKFL.PAYMENT_SERVICES where NAME = 'Благотворительные фонды, социальные и общественные организации'), (select id from SRB_IKFL.CARD_OPERATION_CATEGORIES where NAME = 'Прочие расходы' and LOGIN_ID is null))
/

update SRB_IKFL.EXTENDED_DESCRIPTION_DATA 
set CONTENT='<p style="font-size:16px;font-weight:bold;padding-bottom:10px;">Условия покупки кода App Store и iTunes</p><p style="font-size:12px;">Приобретайте музыку, фильмы, игры, приложения и многое другое. Действуют только в магазине iTunes в РФ, оператором которого является iTunes S.a.r.l. Требуется наличие учетной записи iTunes и предварительное согласие с условиями лицензии и использования. Для создания учетной записи Вы должны быть старше 13 лет и находиться в РФ. Требуется наличие совместимого программного обеспечения, аппаратного обеспечения, а также доступ к Интернету. Не подлежат обмену на наличные денежные средства; возврат или обмен не осуществляются (за исключением случаев, предусмотренных законом). Пин-код не может использоваться для приобретения подарочных сертификатов, карт iTunes, каких-либо других товаров, получения скидок или подарков в магазине iTunes. Пин-код не может использоваться для совершения покупок в интернет магазинах Apple и в розничных магазинах Apple. Неиспользованные средства по пин-коду не подлежат передаче. Сбор и использование данных осуществляется в соответствии с Политикой конфиденциальности для клиентов Apple, см. <u>www.apple.com/privacy</u>, если не указано иное. Риск утраты и право использования пин-кода переходят к покупателю в момент передачи. Стоимость пин-кода полностью зачисляется на учетную запись. Пин-коды выпускаются, реализуются и находятся под управлением компании iTunes S.A.R.L., а также ее лицензиаты, аффилированные лица и лицензиары не дают никаких гарантий, прямых или подразумеваемых, в отношении пин-кодов или магазина iTunes, и отказываются от всех гарантий в максимально возможной степени. Указанные ограничения могут не распространяться на Вас. Недействительны в тех случаях, когда действует запрет. Не предназначены для перепродажи. Действуют полные условия, которые могут периодически изменяться, см. <u>apple.com/legal/itunes/appstore/ru/terms.html</u>. Контент и цены определяются с учетом доступности на момент фактической загрузки. Контент, приобретаемый в магазине iTunes, предназначается только для законного личного использования. Не крадите музыку.</p><p style="font-size:12px;">© 2014 г., iTunes S.A.R.L. Все права защищены.</p>'
  where NAME='itunes-agreement'
/

/* Сбор статистики
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'ACCESSSCHEMES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'ACCOUNT_TARGETS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'ACCOUNTING_ENTITY', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'ALF_RECATEGORIZATION_RULES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'BILLINGS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'CARD_LINKS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'CARD_OPERATION_CATEGORIES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'CARD_OPERATION_CLAIMS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'CARD_OPERATIONS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'CONTACT_CENTER_AREAS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'DEFAULT_ACCESS_SCHEMES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'DEPARTMENTS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'ERMB_PROFILE', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'ERMB_TARIF', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'FAVOURITE_LINKS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'INCOGNITO_PHONES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'INCOGNITO_PHONES_BASE', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'INVOICE_SUBSCRIPTIONS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'INVOICES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'INVOICES_FOR_USER_DOCUMENTS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'LOANCLAIM_AREA', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'LOANCLAIM_CITY', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'LOANCLAIM_FAMILY_STATUS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'LOANCLAIM_SETTLEMENT', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'LOANCLAIM_STREET', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'LOANCLAIM_WORK_ON_CONTRACT', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'LOGINS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'MAIL_SUBJECTS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'MOBILE_PLATFORMS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'NEWS_DISTRIBUTIONS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'NOTIFICATIONS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'OPERATION_CONFIRM_LOG', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'ORDERS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'PERSONAL_OFFER_NOTIFICATION', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'PROFILE', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'PROVIDER_SMS_ALIAS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'REGIONS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'SERVICE_PROVIDERS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'SHOP_FIELDS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'SHOP_ORDERS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'SMS_RESOURCES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'SUBSCRIPTIONS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'TECHNOBREAKS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'USER_PROPERTIES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'USER_SOCIAL_IDS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'SRB_IKFL', tabname => 'USERS', degree => 32, cascade => true); end;
*/
/*Права
grant select on SRB_IKFL.OPERATION_CONFIRM_LOG  to OSDBO_USER;
*/