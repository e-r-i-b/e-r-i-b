--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

-- Номер ревизии: 55915
-- Комментарий: BUG064107: Некорректное отображение динамики курсов. 
update RATE 
	set DYNAMIC_EXCHANGE = decode(DYNAMIC_EXCHANGE, 'DOWN', 'UP', 'UP', 'DOWN') 
		where DYNAMIC_EXCHANGE in ('DOWN', 'UP')
/

-- Номер ревизии: 56135
-- Комментарий:  Сохранение оферты МБК. 
alter table CONFIRM_BEANS modify (
	CONFIRMABLE_TASK_CLASS   null,
	CONFIRMABLE_TASK_BODY    null
)
/

-- Номер ревизии: 56280
-- Комментарий: BUG065314: [Служебный канал ЕРМБ] Не передаётся ТБ профиля ЕРМБ
alter table PERSON_OLD_IDENTITY add (TB varchar2(4))
/
update PERSON_OLD_IDENTITY
set TB = (
	select TB from DEPARTMENTS where id = (
            select DEPARTMENT_ID from USERS where id = PERSON_OLD_IDENTITY.PERSON_ID 
    )
)
/
alter table PERSON_OLD_IDENTITY modify (TB not null)
/

-- Номер ревизии: 56336
-- Комментарий: BUG059903: Не отображается сообщение о незаполненных персональных данных (ч.2 обновление данных редактируемого сотрудника)
alter table LOGINS add LAST_SYNCHRONIZATION_DATE timestamp(6)
/
update LOGINS 
	set LAST_SYNCHRONIZATION_DATE = LAST_LOGON_DATE 
		where DELETED ='0' and KIND='E'
/

-- Номер ревизии: 56492
-- Комментарий: ENH064911: Создание дефолтного администратора в csaAdmin. (ч.2 вход дефолтного админа)
update LOGINS set CSA_USER_ID = 'admin' where USER_ID = 'admin'
/

-- Номер ревизии: 56495
-- Комментарий: CHG065516: Переделать привязку новостей к департаментам 
alter table NEWS_DEPARTMENT add TB varchar2(4)
/
update NEWS_DEPARTMENT nd 
	set TB = (select TB from DEPARTMENTS d where d.ID = nd.DEPARTMENT_ID)
/
alter table NEWS_DEPARTMENT modify TB not null
/

alter table NEWS_DEPARTMENT drop constraint FK_NEWS_DEP_TO_DEPARTMENTS
/
alter table NEWS_DEPARTMENT drop constraint PK_NEWS_DEPARTMENT
/
alter table NEWS_DEPARTMENT add constraint PK_NEWS_DEPARTMENT primary key (NEWS_ID, TB)
/
alter table NEWS_DEPARTMENT drop column DEPARTMENT_ID
/

-- Номер ревизии: 56520
-- Комментарий: Фасилитаторская схема Яндекс(ч.1)
alter table SERVICE_PROVIDERS add IS_FASILITATOR char(1)
/
update SERVICE_PROVIDERS 
	set IS_FASILITATOR='0'
/	
alter table SERVICE_PROVIDERS modify IS_FASILITATOR default '0' not null
/

-- Номер ревизии: 56548
-- Комментарий: Тарифы ЕРМБ. Админка. 
alter table ERMB_TARIF drop column STATUS
/
alter table ERMB_TARIF add DESCRIPTION varchar2(300)
/

-- Номер ревизии: 56564
-- Комментарий: Видимость продуктов по умолчанию (применение правил)
alter table ERMB_PROFILE add NEW_PRODUCT_SHOW_IN_SMS char(1)
/
update ERMB_PROFILE set NEW_PRODUCT_SHOW_IN_SMS='0'
/
alter table ERMB_PROFILE modify NEW_PRODUCT_SHOW_IN_SMS default '0' not null
/

-- Номер ревизии: 56579
-- Комментарий: ПФП. объединение таблиц (пенсионные продукты)
alter table PFP_PENSION_PRODUCT add (
	NAME 			varchar2(250),
	DESCRIPTION 	varchar2(500),
	IMAGE_ID 		integer,
	MIN_INCOME 		number(7,2),
	MAX_INCOME 		number(7,2),
	DEFAULT_INCOME 	number(7,2),
	UNIVERSAL 		char(1),
	ENABLED 		char(1)
)
/
alter table PFP_PENSION_PRODUCT rename column PRODUCT_BASE_ID to ID
/

alter table PFP_P_PRODUCT_TARGET_GROUPS
   add constraint FK_PFP_TARGET_GR_TO_P_PRODUCT foreign key (PRODUCT_ID)
      references PFP_PENSION_PRODUCT (ID)
      on delete cascade
/
	  
update PFP_PENSION_PRODUCT pp set NAME = (select NAME from PFP_PRODUCT_BASE where PFP_PRODUCT_BASE.ID = pp.ID)
/
update PFP_PENSION_PRODUCT pp set DESCRIPTION = (select DESCRIPTION from PFP_PRODUCT_BASE where PFP_PRODUCT_BASE.ID = pp.ID)
/
update PFP_PENSION_PRODUCT pp set IMAGE_ID = (select IMAGE_ID from PFP_PRODUCT_BASE where PFP_PRODUCT_BASE.ID = pp.ID)
/
update PFP_PENSION_PRODUCT pp set MIN_INCOME = (select MIN_INCOME from PFP_PRODUCT_BASE where PFP_PRODUCT_BASE.ID = pp.ID)
/
update PFP_PENSION_PRODUCT pp set MAX_INCOME = (select MAX_INCOME from PFP_PRODUCT_BASE where PFP_PRODUCT_BASE.ID = pp.ID)
/
update PFP_PENSION_PRODUCT pp set DEFAULT_INCOME = (select DEFAULT_INCOME from PFP_PRODUCT_BASE where PFP_PRODUCT_BASE.ID = pp.ID)
/
update PFP_PENSION_PRODUCT pp set UNIVERSAL = (select UNIVERSAL from PFP_PRODUCT_BASE where PFP_PRODUCT_BASE.ID = pp.ID)
/
update PFP_PENSION_PRODUCT pp set ENABLED = (select ENABLED from PFP_PRODUCT_BASE where PFP_PRODUCT_BASE.ID = pp.ID)
/

alter table PFP_PENSION_PRODUCT modify NAME not null
/
alter table PFP_PENSION_PRODUCT modify UNIVERSAL not null
/
alter table PFP_PENSION_PRODUCT modify ENABLED not null
/

alter table PFP_PRODUCT_TARGET_GROUPS
   drop constraint FK_PFP_TARGET_GR_TO_PRODUCT
/
alter table PFP_PRODUCT_TARGET_GROUPS
   add constraint FK_PFP_TARGET_GR_TO_P_PRODUCT foreign key (PRODUCT_ID)
      references PFP_PENSION_PRODUCT (ID)
      on delete cascade
/
alter table PFP_PENSION_PRODUCT
   drop constraint FK_PFP_PENSION_TO_PRODUCT
/
drop table PFP_PRODUCT_BASE
/
drop sequence S_PFP_PENSION_PRODUCT
/
rename S_PFP_PRODUCT_BASE to S_PFP_PENSION_PRODUCT
/
rename PFP_PRODUCT_TARGET_GROUPS to PFP_P_PRODUCT_TARGET_GROUPS
/
alter index "DXFK_PFP_TARGET_GR_TO_PRODUCT" rename to "DXFK_PFP_TARGET_GR_TO_P_PRODUC"
/

drop sequence S_PFP_CP_TABLE_VIEW_PARAMETERS
/
drop sequence S_PFP_CP_TARGET_GROUPS_BUNDLE
/
drop sequence S_PFP_C_FUND_PRODUCTS
/
drop sequence S_PFP_C_IMA_PRODUCTS
/
drop sequence S_PFP_C_INSURANCE_PRODUCTS
/
drop sequence S_PFP_C_PRODUCT_PARAMETERS
/
drop sequence S_PFP_DIAGRAM_STEPS
/
drop sequence S_PFP_INS_PRODUCT_TO_PERIODS
/
drop sequence S_PFP_IP_TARGET_GROUPS_BUNDLE
/
drop sequence S_PFP_PRODUCT_PARAMETERS
/
drop sequence S_PFP_PRODUCT_TARGET_GROUPS
/
drop sequence S_PFP_PT_TARGET_GROUPS
/
drop sequence S_PFP_SP_TABLE_VIEW_PARAMETERS
/
drop sequence S_PFP_SP_TARGET_GROUPS_BUNDLE
/

-- Номер ревизии: 56650
-- Комментарий: CHG065690: Переделать привязку ПБО к тербанкам

---Информационные сообщения----
alter table MESSAGES_DEPARTMENTS add TB varchar2(4)
/
update MESSAGES_DEPARTMENTS nd set TB = (select TB from DEPARTMENTS d where d.ID = nd.DEPARTMENT_ID)
/
alter table MESSAGES_DEPARTMENTS drop constraint PK_MESSAGES_DEPARTMENTS
/
alter table MESSAGES_DEPARTMENTS drop constraint FK_MESSAGES_DEP_TO_DEPARTMENTS
/
drop index DXFK_MESSAGES_DEP_TO_DEPARTMEN
/
alter table MESSAGES_DEPARTMENTS drop column DEPARTMENT_ID
/
alter table MESSAGES_DEPARTMENTS add constraint PK_MESSAGES_DEPARTMENTS primary key(MESSAGE_ID, TB)
/
--------------ПБО--------------
alter table Q_P_PANELS_DEPARTMENTS add TB varchar2(4)
/
update Q_P_PANELS_DEPARTMENTS  nd set TB = (select TB from DEPARTMENTS d where d.ID = nd.DEPARTMENT_ID)
/
alter table Q_P_PANELS_DEPARTMENTS drop constraint PK_Q_P_PANELS_DEPARTMENTS
/
alter table Q_P_PANELS_DEPARTMENTS drop column DEPARTMENT_ID
/
alter table Q_P_PANELS_DEPARTMENTS add constraint PK_Q_P_PANELS_DEPARTMENTS primary key(Q_P_PANEL_ID, TB)
/
alter table Q_P_PANELS_DEPARTMENTS drop constraint AK_DEPARTMENT_ID
/
alter table Q_P_PANELS_DEPARTMENTS add constraint AK_DEPARTMENT_ID unique (TB)
/

-------Рекламные баннеры------
alter table ADVERTISINGS_DEPARTMENTS add TB varchar2(4)
/
update ADVERTISINGS_DEPARTMENTS  nd set TB = (select TB from DEPARTMENTS d where d.ID = nd.DEPARTMENT_ID)
/
alter table ADVERTISINGS_DEPARTMENTS drop constraint PK_ADVERTISINGS_DEPARTMENTS
/
alter table ADVERTISINGS_DEPARTMENTS drop column DEPARTMENT_ID
/
alter table ADVERTISINGS_DEPARTMENTS add constraint PK_ADVERTISINGS_DEPARTMENTS primary key(ADVERTISING_ID, TB)
/

-- Номер ревизии: 56730
-- Комментарий: Доработать механизм входа клиента в многоблочном режиме
drop table ALLOWED_DEPARTMENT_TEMPORARY
/

-- Номер ревизии: 56752
-- Комментарий: Отображение информации о максимальной сумме вклада: доработка xsd, AccountInfo и генерация веб-сервисов.
alter table STORED_ACCOUNT add (
	CLEAR_BALANCE	number,
	MAX_BALANCE 	number
)
/

-- Номер ревизии: 56759
-- Комментарий: Добавлены признаки для определения клиентов, которые активно пользуются АЛФ. Реализовано обновление статисти по этим признакам.
alter table PROFILE add (
	LAST_USING_FINANCES_DATE 		timestamp,
	USING_FINANCES_COUNT     		integer,
	USING_ALF_EVERY_THREE_DAYS_NUM  integer
)
/

-- Номер ревизии: 56783
-- Комментарий: АЛФ. Флаг отображения операций переводов (добавить в справочник категорий поле тип, доработать функционал редактирования категорий)
alter table CARD_OPERATION_CATEGORIES add PAYMENT_TYPE varchar2(8)
/
update CARD_OPERATION_CATEGORIES set PAYMENT_TYPE='PAYMENT'
/
alter table CARD_OPERATION_CATEGORIES modify PAYMENT_TYPE not null
/

alter table CARD_OPERATION_CATEGORIES add ID_IN_MAPI varchar2(30)
/

-- Номер ревизии: 56790
-- Комментарий: Подача кредитной заявки через СБОЛ, Интеграция с TSM. Загрузка пред одобренных предложений по кредитам.
alter table LOAN_OFFER add (
	PRODUCT_CODE 		varchar(2),
	SUB_PRODUCT_CODE 	varchar(5),
	PRODUCT_TYPE_CODE 	varchar(5)
)
/

-- Номер ревизии: 56872
-- Комментарий: АЛФ. Флаг отображения операций переводов.
alter table CARD_OPERATION_CATEGORIES drop column PAYMENT_TYPE
/

alter table CARD_OPERATION_CATEGORIES add IS_TRANSFER char(1) 
/
update CARD_OPERATION_CATEGORIES set IS_TRANSFER='0'
/
alter table CARD_OPERATION_CATEGORIES modify IS_TRANSFER default '0' not null
/

insert into CARD_OPERATION_CATEGORIES (ID, NAME, INCOME, CASH, CASHLESS, ALLOW_INCOMPATIBLE_OPERATIONS, EXTERNALID, IS_TRANSFER) 
	values(S_CARD_OPERATION_CATEGORIES.nextval, 'Перевод во вне', 0, 1, 1, 1, 'TransfersOutside', 1)
/

merge into MERCHANT_CATEGORY_CODES using dual on (CODE='99992410')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992410, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual on (CODE='99992411')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992411, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual on (CODE='99992412')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992412, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual on (CODE='99992413')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992413, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual on (CODE='99992420')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992420, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual on (CODE='99992421')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992421, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual on (CODE='99992422')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992422, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual ON (CODE='99992423')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992423, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual ON (CODE='99992430')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992430, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual ON (CODE='99992431')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992431, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual ON (CODE='99992432')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992432, null, 'TransfersOutside')
/

merge into MERCHANT_CATEGORY_CODES using dual ON (CODE='99992433')
when matched then update set OUTCOME_OPERATION_CATEGORY_ID='TransfersOutside'
when not matched then insert (CODE, INCOME_OPERATION_CATEGORY_ID, OUTCOME_OPERATION_CATEGORY_ID) 
    values (99992433, null, 'TransfersOutside')
/

-- Номер ревизии: 56993
-- Комментарий: Реализовать хранение справочников ПФП в базе ЦСА Админ
insert into PFP_CARD_RECOMMENDATIONS (ID, RECOMMENDATION, ACCOUNT_FROM_INCOME, ACCOUNT_TO_INCOME, ACCOUNT_DEFAULT_INCOME, ACCOUNT_DESCRIPTION, THANKS_FROM_INCOME, THANKS_TO_INCOME, THANKS_DEFAULT_INCOME, THANKS_DESCRIPTION)
values (S_PFP_CARD_RECOMMENDATIONS.NEXTVAL, 
        (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.recommendation'),
        (SELECT TO_NUMBER(REPLACE(PROPERTY_VALUE, '.', ',')) FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.income.from'),
        (SELECT TO_NUMBER(REPLACE(PROPERTY_VALUE, '.', ',')) FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.income.to'),
        (select TO_NUMBER(REPLACE((SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.income.default')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.income.default')
                ELSE '5'
            END
         FROM dual), '.', ',')) FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.account.description')
                ELSE 'Подсказка к полю вклад'
            END
         FROM dual),
        (SELECT TO_NUMBER(REPLACE(PROPERTY_VALUE, '.', ',')) FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.income.from'),
        (SELECT TO_NUMBER(REPLACE(PROPERTY_VALUE, '.', ',')) FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.income.to'),
        (select TO_NUMBER(REPLACE((SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.income.default')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.income.default')
                ELSE '0,5'
            END
         FROM dual), '.', ',')) FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.thanks.description')
                ELSE 'Подсказка к полю СПАСИБО'
            END
         FROM dual))
/

insert into PFP_CR_STEPS (RECOMMENDATION_ID, LIST_INDEX, NAME, DESCRIPTION)
values ((select ID FROM PFP_CARD_RECOMMENDATIONS), 
        0,
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step0.name')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step0.name')
                ELSE 'Получение кредитной карты'
            END
        FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step0.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step0.description')
                ELSE 'Оформите кредитную карту Сбербанка с льготным периодом.'
            END
        FROM dual))
/

insert into PFP_CR_STEPS (RECOMMENDATION_ID, LIST_INDEX, NAME, DESCRIPTION)
values ((select ID FROM PFP_CARD_RECOMMENDATIONS), 
        1,
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step1.name')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step1.name')
                ELSE 'Все расходы по кредитной карте (покупки)'
            END
        FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step1.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step1.description')
                ELSE 'Совершайте все покупки по кредитной карте в льготный период и не снимайте наличные с карты. Собственные средства разместите на вкладе.'
            END
        FROM dual))
/

insert into PFP_CR_STEPS (RECOMMENDATION_ID, LIST_INDEX, NAME, DESCRIPTION)
values ((select ID FROM PFP_CARD_RECOMMENDATIONS), 
        2,
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step2.name')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step2.name')
                ELSE 'Погашение задолженности по кредитной карте без процентов'
            END
        FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step2.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step2.description')
                ELSE 'Погашайте задолженность по карте в льготный период, а также все расходы совершайте по кредитной карте. На средства, размещенные на вкладе, будут начислены проценты, и за каждую покупку по кредитной карте Вы получите бонусные баллы СПАСИБО.'
            END
        FROM dual))
/

insert into PFP_CR_STEPS (RECOMMENDATION_ID, LIST_INDEX, NAME, DESCRIPTION)
values ((select ID FROM PFP_CARD_RECOMMENDATIONS), 
        3,
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step3.name')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step3.name')
                ELSE 'Погашение задолженности по кредитной карте без процентов'
            END
        FROM dual),
        (SELECT
            CASE WHEN EXISTS (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step3.description')
                THEN (SELECT PROPERTY_VALUE FROM PROPERTIES WHERE CATEGORY = 'pfpDictionary.properties' AND PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.step3.description')
                ELSE 'Далее продолжайте действовать таким же образом, и с каждым месяцем у Вас будет увеличиваться доход по вкладу, открытому на сохраненные средства, и количество накопленных «Спасибо».'
            END
        FROM dual))
/

create sequence S_TEMP minvalue 0 increment by 1
/

insert into PFP_CR_CARDS (RECOMMENDATION_ID, LIST_INDEX, CARD_ID)
select (select ID from PFP_CARD_RECOMMENDATIONS), S_TEMP.nextval, ID from PFP_CARDS
where instr(','|| (select PROPERTY_VALUE from PROPERTIES where CATEGORY = 'pfpDictionary.properties' and PROPERTY_KEY='com.rssl.pfp.settings.recommendation.cards.cards') || ',', ','|| PFP_CARDS.ID || ',') > 0
/

drop sequence S_TEMP
/

-- Номер ревизии: 57006
-- Комментарий: Реализовать хранение справочников ПФП в базе ЦСА Админ
alter table PFP_PRODUCTS add (
	ERIB_PRODUCT_ID 			integer,
	ERIB_PRODUCT_ADDITIONAL_ID 	integer
)
/

update PFP_PRODUCTS p set p.ERIB_PRODUCT_ID = (SELECT d.PRODUCT_ID from DEPOSITDESCRIPTIONS d where d.ID = p.SBOL_PRODUCT_ID) 
	where TYPE = 'A' and SBOL_PRODUCT_ID is not null
/

update PFP_PRODUCTS p set p.ERIB_PRODUCT_ID = (SELECT i.TYPE from IMAPRODUCT i where i.ID = p.SBOL_PRODUCT_ID) 
	where TYPE = 'M' and SBOL_PRODUCT_ID is not null
/

update PFP_PRODUCTS p set p.ERIB_PRODUCT_ADDITIONAL_ID = (SELECT i.SUBTYPE from IMAPRODUCT i where i.ID = p.SBOL_PRODUCT_ID) 
	where TYPE = 'M' and SBOL_PRODUCT_ID is not null
/

alter table PFP_PRODUCTS drop column SBOL_PRODUCT_ID
/


-- Номер ревизии: 57060
-- Комментарий: Доработать справочники ЦСА Админ для сквозной идентификации между блоками.
alter table PFP_CHANNELS add UUID varchar2(32)
/
update PFP_CHANNELS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_CHANNELS modify UUID not null
/
create unique index I_PFP_CHANNELS_UUID on PFP_CHANNELS(
    UUID
) tablespace INDX
/

alter table PFP_INVESTMENT_PERIODS add UUID varchar2(32)
/
update PFP_INVESTMENT_PERIODS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_INVESTMENT_PERIODS modify UUID not null
/
create unique index I_PFP_INVESTMENT_PERIODS_UUID on PFP_INVESTMENT_PERIODS(
    UUID
) tablespace INDX
/

alter table PFP_CARD_RECOMMENDATIONS add UUID varchar2(32)
/
update PFP_CARD_RECOMMENDATIONS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_CARD_RECOMMENDATIONS modify UUID not null
/
create unique index I_PFP_CARDRECOMMENDATIONS_UUID on PFP_CARD_RECOMMENDATIONS(
    UUID
) tablespace INDX
/

alter table PFP_CARDS add UUID varchar2(32)
/
update PFP_CARDS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_CARDS modify UUID not null
/
create unique index I_PFP_CARDS_UUID on PFP_CARDS(
    UUID
) tablespace INDX
/

alter table PFP_COMPLEX_PRODUCTS add UUID varchar2(32)
/
update PFP_COMPLEX_PRODUCTS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_COMPLEX_PRODUCTS modify UUID not null
/
create unique index I_PFP_COMPLEX_PRODUCTS_UUID on PFP_COMPLEX_PRODUCTS(
    UUID
) tablespace INDX
/

alter table PFP_INSURANCE_COMPANIES add UUID varchar2(32)
/
update PFP_INSURANCE_COMPANIES set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_INSURANCE_COMPANIES modify UUID not null
/
create unique index I_PFP_INSURANCE_COMPANIES_UUID on PFP_INSURANCE_COMPANIES(
    UUID
) tablespace INDX
/

alter table PFP_INSURANCE_PERIOD_TYPES add UUID varchar2(32)
/
update PFP_INSURANCE_PERIOD_TYPES set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_INSURANCE_PERIOD_TYPES modify UUID not null
/
create unique index I_PFP_INS_PERIOD_TYPES_UUID on PFP_INSURANCE_PERIOD_TYPES(
    UUID
) tablespace INDX
/

alter table PFP_INSURANCE_TYPES add UUID varchar2(32)
/
update PFP_INSURANCE_TYPES set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_INSURANCE_TYPES modify UUID not null
/
create unique index I_PFP_INSURANCE_TYPES_UUID on PFP_INSURANCE_TYPES(
    UUID
) tablespace INDX
/

alter table PFP_INSURANCE_PRODUCTS add UUID varchar2(32)
/
update PFP_INSURANCE_PRODUCTS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_INSURANCE_PRODUCTS modify UUID not null
/
create unique index I_PFP_INSURANCE_PRODUCTS_UUID on PFP_INSURANCE_PRODUCTS(
    UUID
) tablespace INDX
/

alter table PFP_LOAN_KINDS add UUID varchar2(32)
/
update PFP_LOAN_KINDS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_LOAN_KINDS modify UUID not null
/
create unique index I_PFP_LOAN_KINDS_UUID on PFP_LOAN_KINDS(
    UUID
) tablespace INDX
/

alter table PFP_PENSION_FUND add UUID varchar2(32)
/
update PFP_PENSION_FUND set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_PENSION_FUND modify UUID not null
/
create unique index I_PFP_PENSION_FUND_UUID on PFP_PENSION_FUND(
    UUID
) tablespace INDX
/

alter table PFP_PENSION_PRODUCT add UUID varchar2(32)
/
update PFP_PENSION_PRODUCT set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_PENSION_PRODUCT modify UUID not null
/
create unique index I_PFP_PENSION_PRODUCT_UUID on PFP_PENSION_PRODUCT(
    UUID
) tablespace INDX
/

alter table PFP_PRODUCTS add UUID varchar2(32)
/
update PFP_PRODUCTS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_PRODUCTS modify UUID not null
/
create unique index I_PFP_PRODUCTS_UUID on PFP_PRODUCTS(
    UUID
) tablespace INDX
/

alter table PFP_PRODUCT_TYPE_PARAMETERS add UUID varchar2(32)
/
update PFP_PRODUCT_TYPE_PARAMETERS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_PRODUCT_TYPE_PARAMETERS modify UUID not null
/
create unique index I_PFP_PRODUCT_TYPES_UUID on PFP_PRODUCT_TYPE_PARAMETERS(
    UUID
) tablespace INDX
/

alter table PFP_TABLE_COLUMNS add UUID varchar2(32)
/
update PFP_TABLE_COLUMNS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_TABLE_COLUMNS modify UUID not null
/
create unique index I_PFP_TABLE_COLUMNS_UUID on PFP_TABLE_COLUMNS(
    UUID
) tablespace INDX
/

alter table PFP_RISKS add UUID varchar2(32)
/
update PFP_RISKS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_RISKS modify UUID not null
/
create unique index I_PFP_RISKS_UUID on PFP_RISKS(
    UUID
) tablespace INDX
/

alter table PFP_AGE_CATEGORIES add UUID varchar2(32)
/
update PFP_AGE_CATEGORIES set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_AGE_CATEGORIES modify UUID not null
/
create unique index I_PFP_AGE_CATEGORIES_UUID on PFP_AGE_CATEGORIES(
    UUID
) tablespace INDX
/

alter table PFP_ANSWERS add UUID varchar2(32)
/
update PFP_ANSWERS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_ANSWERS modify UUID not null
/
create unique index I_PFP_ANSWERS_UUID on PFP_ANSWERS(
    UUID
) tablespace INDX
/

alter table PFP_QUESTIONS add UUID varchar2(32)
/
update PFP_QUESTIONS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_QUESTIONS modify UUID not null
/
create unique index I_PFP_QUESTIONS_UUID on PFP_QUESTIONS(
    UUID
) tablespace INDX
/

alter table PFP_RISK_PROFILES add UUID varchar2(32)
/
update PFP_RISK_PROFILES set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_RISK_PROFILES modify UUID not null
/
create unique index I_PFP_RISK_PROFILES_UUID on PFP_RISK_PROFILES(
    UUID
) tablespace INDX
/

alter table PFP_TARGETS add UUID varchar2(32)
/
update PFP_TARGETS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PFP_TARGETS modify UUID not null
/
create unique index I_PFP_TARGETS_UUID on PFP_TARGETS(
    UUID
) tablespace INDX
/


-- Номер ревизии: 57057
-- Комментарий: АЛФ. Внесение наличных трат (доработка сущности и модели БД)
alter table CARD_OPERATIONS modify CARD_NUMBER null
/
alter table CARD_OPERATIONS modify CARD_SUMM null
/
alter table CARD_OPERATIONS add OPERATION_TYPE varchar2(7)
/

-- Номер ревизии: 57161  
-- Комментарий: Удалить старый механизм доступных подразделений
alter table EMPLOYEES set unused ( ALL_DEPARTMENTS )
/

-- Номер ревизии: 57330
-- Комментарий: Перевести справочник биллинговых систем на многоблочный режим. (переделана привязка биллинговой системы к адаптеру)

alter table BILLINGS add ADAPTER_UUID varchar2(64)
/
update BILLINGS billing set ADAPTER_UUID = (select UUID from ADAPTERS adapter where adapter.ID = billing.ADAPTER_ID)
/ 
alter table BILLINGS modify ADAPTER_UUID not null
/
create index "DXFK_BILLINGS_TO_ADAPTERS" on BILLINGS
(
   ADAPTER_UUID
) tablespace INDX
/

alter table BILLINGS drop column ADAPTER_ID
/
alter index INDEX_CODE_1 rename to I_PAYMENT_SERVICES_CODE
/
alter index INDEX_CODE_4 rename to I_BILLINGS_CODE
/ 
alter table ADAPTERS add constraint UK_ADAPTERS_UUID unique (UUID)
/

alter table BILLINGS
   add constraint FK_BILLINGS_TO_ADAPTERS foreign key (ADAPTER_UUID)
      references ADAPTERS (UUID)
/

-- Номер ревизии: 57337
-- Комментарий: Синхронизация справочника подразделений. Часть 1. Переделать привязку подразделений к адаптерам.
alter table DEPARTMENTS add ADAPTER_UUID varchar2(64)
/
update DEPARTMENTS department 
	set department.ADAPTER_UUID = (select adapter.UUID from ADAPTERS adapter where adapter.ID = department.ADAPTER_ID)
/
alter table DEPARTMENTS drop column ADAPTER_ID
/
alter table DEPARTMENTS
   add constraint FK_DEPARTMENTS_TO_ADAPTERS foreign key (ADAPTER_UUID)
      references ADAPTERS (UUID)
/

-- Номер ревизии: 57360
-- Комментарий: Перевести справочник биллинговых систем на многоблочный режим. (Реализовать хранение справочника в ЦСА Админ.)
alter table BILLINGS add constraint UK_BILLINGS_ADAPTER_UUID unique (ADAPTER_UUID)
/

-- Номер ревизии: 57379
-- Комментарий: Лимиты в многоблочности : Доработки ЕРИБ(ч4 - добавление внешнего идентификатора группе риска)
alter table GROUPS_RISK add EXTERNAL_ID varchar2(35) 
/
update GROUPS_RISK set EXTERNAL_ID = SYS_GUID()
/
alter table GROUPS_RISK modify EXTERNAL_ID default SYS_GUID() not null
/

-- Номер ревизии: 57399
-- Комментарий: Доработать поставщиков услуг для хранения в ЦСА Админ
drop sequence S_SERV_PROVIDER_PAYMENT_SERV
/
drop sequence S_SERVICE_PROVIDER_REGIONS
/
drop sequence S_FIELD_VALUES_DESCR
/
drop sequence S_FIELD_REQUISITE_TYPES
/

-- Номер ревизии: 57432
-- Комментарий: В условия по продукту добавить поля
alter table CREDIT_CARD_PRODUCTS add (
	USE_FOR_PREAPPROVED 		char(1),
	DEFAULT_FOR_PREAPPROVED 	char(1)
)
/
update CREDIT_CARD_PRODUCTS 
	set USE_FOR_PREAPPROVED=0, DEFAULT_FOR_PREAPPROVED=0
/	

alter table CREDIT_CARD_PRODUCTS modify (
	USE_FOR_PREAPPROVED 		default 0 not null,
	DEFAULT_FOR_PREAPPROVED 	default 0 not null
)
/

-- Номер ревизии: 57450
-- Комментарий: История изменений профиля в ЕРИБ
alter table USER_KEY_HISTORY
   add constraint FK_USER_KEY_TO_LOGINS_REF foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
/

-- Номер ревизии: 57465
-- Комментарий: Синхронизация справочника подразделений. Часть 3. Перевод календарей, справочника подразделений на многоблочный режим.
alter table CALENDARS add UUID varchar2(32)
/
update CALENDARS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table CALENDARS modify UUID not null
/
create unique index I_UUID_CALENDARS on CALENDARS(
    UUID
) tablespace INDX
/

-- Номер ревизии: 57489 
-- Комментарий: Вывод ВСП по региону 
update regions reg1
set code_tb = (select code_tb
               from (select A.id, code_tb
                     from (SELECT id, CONNECT_BY_ROOT id as root
                     FROM regions
                     where parent_id is not null
                     START WITH parent_id is null
                     CONNECT BY PRIOR id = parent_id) A join regions B on A.root = B.id) reg2
               where reg2.id = reg1.id
               )
where parent_id is not null
/

-- Номер ревизии: 57504
-- Комментарий: Реализовать синхронизацию справочника поставщиков в блоке с ЦСА Админ
alter table FIELD_DESCRIPTIONS add UUID varchar2(32)
/
update FIELD_DESCRIPTIONS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table FIELD_DESCRIPTIONS modify UUID not null
/
create unique index I_FIELD_DESCRIPTIONS_UUID on FIELD_DESCRIPTIONS(
    UUID
) tablespace INDX
/

-- Номер ревизии: 57518
-- Комментарий: Доработка хранения изображений
update IMAGES 
	set EXTEND_IMAGE = replace(EXTEND_IMAGE, 'https://stat.online.sberbank.ru/phizic-res') 
where EXTEND_IMAGE is not null
/

-- Номер ревизии: 57555
-- Комментарий: Реализовать синхронизацию справочника поставщиков в блоке с ЦСА Админ 
alter table PROVIDER_SMS_ALIAS add UUID varchar2(32)
/
update PROVIDER_SMS_ALIAS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table PROVIDER_SMS_ALIAS modify UUID not null
/
create unique index I_PROVIDER_SMS_ALIAS_UUID on PROVIDER_SMS_ALIAS(
    UUID
) tablespace INDX
/

-- Номер ревизии: 57558
-- Комментарий: BUG067297: Необходимо добавить индекс.
create index I_PFP_FIELDS_TO_PRODUCT on PFP_PRODUCT_FIELDS
(
    BASE_PRODUCT_ID ASC
) tablespace INDX
/

-- Номер ревизии: 57581
-- Комментарий: убрать parent из подразделений (11 - из индекса БД)
create index I_DEPARTMENTS_TBS on DEPARTMENTS (
   decode(OFFICE||OSB,null,TB,null) asc
) tablespace INDX
/

-- Номер ревизии: 57610
-- Комментарий: АЛФ: переименование столбца в таблице CARD_OPERATIONS
alter table CARD_OPERATIONS rename column CASH to IO_CASH
/

-- Номер ревизии:57615  
-- Комментарий: Переделать привязку технологических перерывов к внешним системам.
alter table TECHNOBREAKS add ADAPTER_UUID varchar2(64)
/
update TECHNOBREAKS t set ADAPTER_UUID = (select a.UUID from ADAPTERS a where a.ID = t.ADAPTER_ID)
/
alter table TECHNOBREAKS modify ADAPTER_UUID not null
/

alter table TECHNOBREAKS drop column ADAPTER_ID
/

create index "DXFK_TECHNOBREAKS_TO_ADAPTERS" on TECHNOBREAKS
(
   ADAPTER_UUID
) tablespace INDX
/
alter table TECHNOBREAKS
   add constraint FK_TECHNOBREAKS_TO_ADAPTERS foreign key (ADAPTER_UUID)
      references ADAPTERS (UUID)
/

-- Номер ревизии: 57665
-- Комментарий: BUG067810: Сертификаты с большим номиналом
alter table STORED_SECURITY_ACCOUNT modify NOMINAL_AMOUNT number(18,4)
/
alter table STORED_SECURITY_ACCOUNT modify INCOME_AMOUNT number(18,4)
/

-- Номер ревизии: 57668
-- Комментарий: Доработать справочник техперерывов для редактирования в многоблочном режиме.
-- Реализовать синхронизацию справочника техперерывов в блоке с цсаАдмин
alter table TECHNOBREAKS add UUID varchar(32)
/
update TECHNOBREAKS set UUID = SYS_GUID()
/
alter table TECHNOBREAKS modify UUID default SYS_GUID() not null
/
alter table TECHNOBREAKS add constraint AK_UK_TECHNOBREAKS unique(UUID)
/

-- Номер ревизии:  57706
-- Комментарий: Оповещения о персональных предложениях(ч.3)
alter table PERSONAL_OFFER_DISPLAY_DATE
   add constraint FK_OFFER_TO_DATE foreign key (PERSONAL_OFFER_ID)
      references PERSONAL_OFFER_NOTIFICATION (ID)
      on delete cascade
/

alter table PERSONAL_OFFER_DISPLAY_DATE
   add constraint FK_OFFER_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
/

-- Номер ревизии: 57743
-- Комментарий: Перенос уровня безопасности в ЦСА. часть 2
alter table USERS set unused ( TRUSTED )
/ 

-- Номер ревизии: 57760
-- Комментарий: убрать parent из подразделений (19 - модель БД)
alter table DEPARTMENTS drop column PARENT_DEPARTMENT cascade constraint
/

-- Номер ревизии: 57765
-- Комментарий: АРМ Сотрудника. Справочник подразделений ВСП (галка "возможность кредитования физ. лиц") 
alter table DEPARTMENTS add POSSIBLE_LOANS_OPERATION char(1) 
/
update DEPARTMENTS set POSSIBLE_LOANS_OPERATION='0' 
/
alter table DEPARTMENTS modify POSSIBLE_LOANS_OPERATION default '0' not null 
/

-- Номер ревизии: 57814
-- Комментарий: Подтверждение паролем в mAPI
alter table MOBILE_PLATFORMS add ( IS_PASSWORD_CONFIRM char(1) )
/
update MOBILE_PLATFORMS set IS_PASSWORD_CONFIRM=0
/
alter table MOBILE_PLATFORMS modify ( IS_PASSWORD_CONFIRM default 0 not null )
/


-- Номер ревизии: 57842
-- Комментарий: Оповещения о персональных предложениях(ч.6)
alter table OFFER_NOTIFICATIONS_LOG
   add constraint FK_OFFER_NO_FK_OFFER__PERSONAL foreign key (NOTIFICATION_ID)
      references PERSONAL_OFFER_NOTIFICATION (ID)
      on delete cascade
/

-- Номер ревизии: 57856
-- Комментарий: Доработать аудит для возможности смены блока сотрудниками КЦ
delete from FILTER_PARAMETERS where type = 'K'
/
alter table FILTER_PARAMETERS drop ( TYPE, ADDITIONAL_KEY)
/

-- Номер ревизии: 57966
-- Комментарий: Синхронизация справочника подразделений. Фоновая загрузка справочника подразделений.
delete from DEPARTMENTS_TASKS_CONTENT
/
delete from DEPARTMENTS_REPLICA_TASKS
/
drop index DXFK_DEPARTMENTS_REPL_LOGINS 
/
alter table DEPARTMENTS_REPLICA_TASKS drop column LOGIN_ID
/
alter table DEPARTMENTS_REPLICA_TASKS add (
	OWNER_ID 	integer         not null,
	OWNER_FIO 	varchar2(256) 	not null
)
/

-- Номер ревизии: 57977
-- Комментарий: ENH067661: Убрать Использовать настройку вышестоящего из подразделений. 
alter table DEPARTMENTS drop (
	USE_PARENT_TIME_SETTINGS, 
    USE_PARENT_CONNECTION_CHARGE, 
    USE_PARENT_MONTHLY_CHARGE, 
    USE_PARENT_TIME_ZONE, 
    USE_PARENT_ESB_SUPPORTED, 
    USE_PARENT_MDM_SUPPORTED
)
/

--От внедрения:
delete from CONFIGS
/
alter table NOTIFICATIONS drop constraint SYS_C005432
/
alter table NOTIFICATIONS drop constraint SYS_C005434
/

-- Номер ревизии: 58022  
alter table USERS add (USER_REGISTRATION_MODE varchar2(10))
/

-- Номер ревизии: 58035
-- Комментарий: Доработать механизм фоновой репликации поставщиков услуг.
delete from PROVIDER_REPLICA_TASKS
/

drop index "DXPROV_REPLICA_TASKS_LOGINS" 
/

alter table PROVIDER_REPLICA_TASKS drop column LOGIN_ID
/

alter table PROVIDER_REPLICA_TASKS add( 
	OWNER_ID INTEGER        not null,
	OWNER_FIO VARCHAR2(256) not null
)
/

-- Номер ревизии: 58059
-- Комментарий: Доработка формата загрузки пред одобренных предложений.  
alter table LOAN_OFFER add END_DATE timestamp
/

-- Номер ревизии: 58144
-- Комментарий: CHG062817: Не подменять уровень безопасности клиента 
alter table USERS add (STORE_SECURITY_TYPE varchar2(8))
/

-- Номер ревизии: 58201 
-- Комментарий: Реализовать синхронизацию справочника запрещенных счетов
alter table BANNED_ACCOUNTS add UUID varchar(32) 
/
update BANNED_ACCOUNTS set UUID = SYS_GUID()
/
alter table BANNED_ACCOUNTS modify UUID default SYS_GUID() not null
/
alter table BANNED_ACCOUNTS add constraint AK_UK_BANNED_ACCOUNTS unique(UUID)
/

-- Номер ревизии: 58284
-- Комментарий: CHG065494: Мусор в PAYMENT_EXECUTION_RECORDS 
delete from PAYMENT_EXECUTION_RECORDS
/
alter table PAYMENT_EXECUTION_RECORDS add (COUNTER INTEGER not null)
/ 
drop index DOC_ID_JOB_NAME_PER_IDX
/
create unique index DOC_ID_JOB_NAME_PER_IDX on PAYMENT_EXECUTION_RECORDS (
   DOCUMENT_ID ASC,
   JOB_NAME ASC
) tablespace INDX
/

-- Номер ревизии: 58323
-- Комментарий: Синхронизация справочника ПБО в многоблочном режиме
alter table QUICK_PAYMENT_PANELS add UUID varchar2(32)
/
update QUICK_PAYMENT_PANELS set UUID=SYS_GUID()
/
alter table QUICK_PAYMENT_PANELS modify UUID default SYS_GUID() not null
/
alter table QUICK_PAYMENT_PANELS add constraint AK_UUID_UNIQUE unique(UUID)
/
alter table PANEL_BLOCKS add UUID varchar2(32)
/
update PANEL_BLOCKS set UUID=SYS_GUID()
/
alter table PANEL_BLOCKS modify UUID default SYS_GUID() not null
/
alter table PANEL_BLOCKS add constraint AK_UNIQUE_PANEL_BLOCKS unique(UUID)
/

-- Номер ревизии: 58374
-- Комментарий:  Синхронизация справочника событий в многоблочном режиме.
alter table NEWS add UUID VARCHAR2(32)
/
create unique index I_NEWS_UUID on NEWS(
    UUID
) tablespace INDX
/
update NEWS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table NEWS modify UUID not null
/ 

-- Номер ревизии: 58386
-- Комментарий: CHG065690: Переделать привязку ПБО к тербанкам
alter table CALENDARS add TB VARCHAR2(4)
/
update CALENDARS c set TB = (select d.TB from DEPARTMENTS d where d.id = c.DEPARTMENT_ID)
/
alter table CALENDARS drop column DEPARTMENT_ID
/ 

-- Номер ревизии: 58451
-- Комментарий:  BUG068793: Невозможно добавить площадку КЦ через цса админ.

alter table C_CENTER_AREAS_DEPARTMENTS add TB varchar2(4)
/
update C_CENTER_AREAS_DEPARTMENTS c set TB = (select d.TB from DEPARTMENTS d where d.id = c.DEPARTMENT_ID)
/
alter table C_CENTER_AREAS_DEPARTMENTS drop constraint PK_C_CENTER_AREAS_DEPARTMENTS 
/
alter table C_CENTER_AREAS_DEPARTMENTS add constraint PK_C_CENTER_AREAS_DEPARTMENTS PRIMARY KEY (C_C_AREA_ID, TB)
/
alter table C_CENTER_AREAS_DEPARTMENTS drop column DEPARTMENT_ID
/ 
create unique index I_C_CENTER_AREAS_DEPARTMENTS on C_CENTER_AREAS_DEPARTMENTS (
   TB
) tablespace INDX  
/

-- Номер ревизии: 58475
-- Комментарий: Перевод на многоблочный режим справочника депозитных продуктов.(часть 1)
drop sequence S_DEPOSITGLOBALS
/
drop sequence S_DEPOSIT_DEPARTMENTS
/

-- Номер ревизии: 58486
-- Комментарий: BUG068192: Не отображаются пункты лимитов при редактировании подразделения в цса админ 
alter table LIMITS add UUID VARCHAR2(32)
/
create unique index I_LIMITS_UUID on LIMITS(
    UUID
) tablespace INDX  
/
update LIMITS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table LIMITS modify UUID not null
/

-- Номер ревизии: 58532
-- Комментарий: CHG069020: Поле синхронизации справочника поставщиков услуг 

alter table SERVICE_PROVIDERS add UUID VARCHAR2(32)
/
update SERVICE_PROVIDERS set UUID = DBMS_RANDOM.STRING('X', 32)
/
create unique index I_SERVICE_PROVIDERS_UUID on SERVICE_PROVIDERS(
    UUID
) tablespace INDX
/
alter table SERVICE_PROVIDERS modify UUID not null
/

-- Номер ревизии: 58549
-- Комментарий: АРМ Сотрудника. Протокол загрузки предодобренных предложение по картам.
alter table LOAN_CARD_OFFER add ( 
	LOAD_DATE           timestamp,
	VIEW_DATE           timestamp,
	TRANSITION_DATE     timestamp,
	REGISTRATION_DATE   timestamp,
	IS_OFFER_USED       char(1)  default '0' not null 
)
/

-- Номер ревизии: 58551
-- Комментарий: Синхронизация справочника ОМС продуктов.
alter table IMAPRODUCT add UUID VARCHAR2(32)
/
update IMAPRODUCT set UUID = DBMS_RANDOM.STRING('X', 32)
/
create unique index I_IMAPRODUCT_UUID on IMAPRODUCT(
    UUID ASC
) tablespace INDX
/
alter table IMAPRODUCT modify UUID not null
/

-- Номер ревизии: 58575
-- Комментарий: Синхронизация баннеров в многоблочном режиме
alter table ADVERTISINGS add UUID VARCHAR2(32)
/
update ADVERTISINGS set UUID = DBMS_RANDOM.STRING('X', 32)
/
create unique index I_ADVERTISINGS_UUID on ADVERTISINGS(
    UUID
) tablespace INDX
/
alter table ADVERTISINGS modify UUID not null
/

alter table ADVERTISING_BUTTONS add UUID VARCHAR2(32)
/
update ADVERTISING_BUTTONS set UUID = DBMS_RANDOM.STRING('X', 32)
/
create unique index I_ADVERTISING_BUTTONS_UUID on ADVERTISING_BUTTONS(
    UUID
) tablespace INDX
/
alter table ADVERTISING_BUTTONS modify UUID not null
/

-- Номер ревизии: 58610
-- Комментарий: BUG068403: Длина поля Код типа участника расчетов в сети Банка России не соответствует РО
alter table RUSBANKS modify PARTICIPANT_CODE VARCHAR2(100)
/

-- Номер ревизии: 58665
-- Комментарий: BUG069090: Не синхронизируется видимость вклада в депозитах
alter table DEPOSIT_DEPARTMENTS add TB varchar2(4)
/
update DEPOSIT_DEPARTMENTS dd set dd.TB = (select d.TB from DEPARTMENTS d where d.ID = dd.DEPARTMENT_ID)
/
alter table DEPOSIT_DEPARTMENTS modify TB not null
/
alter table DEPOSIT_DEPARTMENTS drop constraint PK_DEPOSIT_DEPARTMENTS
/
alter table DEPOSIT_DEPARTMENTS drop column DEPARTMENT_ID
/
alter table DEPOSIT_DEPARTMENTS add constraint PK_DEPOSIT_DEPARTMENTS PRIMARY KEY (DEPOSIT_ID, TB)
/

-- Номер ревизии: 58726
-- Комментарий: CHG069141: Убрать many-to-one class="com.rssl.phizic.business.departments.Department" из лимитов.
alter table LIMITS add TB varchar2(4)
/
update LIMITS l set TB = (select d.TB from DEPARTMENTS d where d.ID = l.DEPARTMENT_ID)
/
alter table LIMITS drop column DEPARTMENT_ID
/
alter table LIMITS modify TB not null
/

alter table PAYMENTS_GROUP_RISK add TB varchar2(4)
/
update PAYMENTS_GROUP_RISK pgr set TB = (select d.TB from DEPARTMENTS d where d.ID = pgr.DEPARTMENT_ID)
/
alter table PAYMENTS_GROUP_RISK drop column DEPARTMENT_ID
/
alter table PAYMENTS_GROUP_RISK modify TB not null
/

-- Номер ревизии: 58784
-- Комментарий: BUG023097: Переделать автоматическую публикацию новостей.
alter table NEWS RENAME COLUMN AUTOMATIC_PUBLISH_DATE TO START_PUBLISH_DATE
/
alter table NEWS RENAME COLUMN CANCEL_PUBLISH_DATE TO END_PUBLISH_DATE
/
create index I_NEWS_DATE  ON NEWS(
	START_PUBLISH_DATE, 
	END_PUBLISH_DATE
) tablespace INDX
/

drop index NEWS_INDEX 
/
update NEWS set START_PUBLISH_DATE = current_date where START_PUBLISH_DATE is null and STATE = 'PUBLISHED'
/
alter table NEWS drop column STATE
/

-- Номер ревизии: 58842
-- Комментарий: BUG069393: [ЕРМБ] необходимо реализовать CSATransportSmsResponseListener 
alter table ERMB_CHECK_IMSI_RESULTS add (PHONE_NUMBER varchar2(20))
/

-- Номер ревизии: 58865
-- Комментарий: BUG069036: Нет проверки условия заполнения блока недвижимости при создании заявки на кредит 
alter table LOANCLAIM_RESIDENCE_RIGHT add (NEED_REALTY_INFO char(1))
/
update LOANCLAIM_RESIDENCE_RIGHT set NEED_REALTY_INFO = 1 where CODE = '1'
/
alter table LOANCLAIM_RESIDENCE_RIGHT modify (NEED_REALTY_INFO default 0 not null)
/

-- Номер ревизии: 56805
-- Комментарий: Джоб загрузки данных по активным клиентам
alter table PROFILE add LAST_UPDATE_OPER_CLAIMS_DATE date
/
create index I_PROFILE_ALF_STATISTICS on PROFILE (
   DECODE(USING_FINANCES_COUNT, 0, null, TRUNC(USING_ALF_EVERY_THREE_DAYS_NUM * 100 / USING_FINANCES_COUNT)) ASC
) parallel 32 tablespace INDX
/
alter index I_PROFILE_ALF_STATISTICS noparallel
/

-- Номер ревизии: 58990
-- Комментарий: BUG067276: Подсчет операций в АЛФ 
drop index IDX_CARDOP_LCO
/
create index I_CARDOP_LCO on CARD_OPERATIONS (
   LOGIN_ID ASC,   
   OPERATION_DATE ASC   
)
local parallel 32 tablespace INDX
/
alter index I_CARDOP_LCO noparallel
/

drop index DXFK_CARDOP_CATEGORY
/
create index I_CARDOP_CATEGORY on CARD_OPERATIONS (
   CATEGORY_ID ASC
)
local parallel 32 tablespace INDX
/
alter index I_CARDOP_CATEGORY noparallel
/

-- Номер ревизии: 59028
-- Комментарий: BUG069565: Расширенная заявка: Недоконца выполняется проверка в блоке недвижимость
alter table LOANCLAIM_TYPE_OF_REALTY add (RESIDENCE char(1))
/
update LOANCLAIM_TYPE_OF_REALTY set RESIDENCE = 1 where CODE = '2'
/
alter table LOANCLAIM_TYPE_OF_REALTY add (RESIDENCE default 0 not null)
/

-- Номер ревизии: 59176
-- Комментарий: ENH066749: Поиск по пустым фильтрам в АРМ сотрудника
create index IO_BD_PERSON_INDEX on USERS (upper (REPLACE(REPLACE("FIRST_NAME"||"PATR_NAME",' ',''),'-','')), BIRTHDAY)  parallel 32 tablespace INDX
/
alter index IO_BD_PERSON_INDEX noparallel
/

update /*+parallel (d, 64)*/ table PAYMENTS_DOCUMENTS d SET d.KIND = 'F' WHERE d.FORM_TYPE = 'IMA_PAYMENT'
/
update /*+parallel (d, 64)*/ table PAYMENTS_DOCUMENTS d SET d.KIND = 'G' WHERE d.FORM_TYPE = 'CONVERT_CURRENCY_TRANSFER'
/

-- Номер ревизии: 59135
-- Комментарий: BUG069584 Долго открывается страница каталога при создании шаблона СМС
create index IDX_SP_IS_MOBILEBANK on SERVICE_PROVIDERS (
   IS_MOBILEBANK ASC
) parallel 4 tablespace INDX

alter index IDX_SP_IS_MOBILEBANK noparallel
/
------------------
-- Номер ревизии: 58441 
alter table PAYMENTFORMS add (TEMPLATE_STATE_MACHINE clob)
/
update OPERATIONDESCRIPTORS set OPERATIONCLASS = 'com.rssl.phizic.operations.documents.templates.EditTemplateOperation' where OPERATIONKEY = 'EditTemplateOperation'
/
update OPERATIONDESCRIPTORS set OPERATIONCLASS = 'com.rssl.phizic.operations.documents.templates.RemoveBankTemplateOperation' where OPERATIONKEY = 'RemoveBankTemplateOperation'
/
update OPERATIONDESCRIPTORS set OPERATIONCLASS = 'ccom.rssl.phizic.operations.documents.templates.RemoveClientTemplateOperation' where OPERATIONKEY = 'RemoveClientTemplateOperation'
/
/* !!!
Далее нужно выполнить загрузку платежных форм.
*/

