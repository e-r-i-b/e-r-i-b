-- Номер ревизии: 47913
-- Комментарий: BUG051608: Некорректно отображается виджет «Twitter» после перетаскивания.
-- Номер ревизии: 48473
-- Комментарий: BUG053687: Некорректное сообщение при повторном добавлении виджета Twitter.
alter table WIDGET_DEFINITIONS add (
	MAX_COUNT number default 5 not null,
	MAX_COUNT_MESSAGE varchar2(256) default 'Вы не можете добавить более 5 одинаковых виджетов.' not null
)
/

-- Номер ревизии: 48377
-- Комментарий: автоматический СМС-алиас для счёт-линков, кард-линков и кредит-линков
alter table ACCOUNT_LINKS add SMS_AUTO_ALIAS varchar2(20)
/
alter table CARD_LINKS add SMS_AUTO_ALIAS varchar2(20)
/
alter table LOAN_LINKS add SMS_AUTO_ALIAS varchar2(20)
/

-- Номер ревизии: 48443
-- Индивидуальные курсы
alter table RATE add TARIF_PLAN_CODE varchar2(20)
/

-- Номер ревизии: 48518
/*--вынесено в переливку данных
update BUSINESS_DOCUMENTS 
	set OPERATION_UID = OPERATION_UUID 
		where creation_date > to_date('01.05.2013 00:00:00', 'dd.mm.yyyy hh24:mi:ss') and KIND = 'P' and OPERATION_UUID is not null 
*/

alter table BUSINESS_DOCUMENTS set unused column OPERATION_UUID
/

-- Номер ревизии: 48516
-- Комментарий: логирование кода подтверждения в журнале подтвреждений(БД)
alter table OPERATION_CONFIRM_LOG add CONFIRM_CODE varchar2(32)
/

-- Номер ревизии: 48639
-- Комментарий: Убрать поиск по подстроке в журналах (в части аудита и ДУЛ).
create index DUL_INDEX on DOCUMENTS (
   replace("DOC_SERIES"||"DOC_NUMBER",' ','') asc
)
online parallel 64
/
alter index DUL_INDEX noparallel
/

-- Номер ревизии: 48726
-- Комментарий: Не работает проверка на обновление курсов валют при подтверждении платежа
alter table PROFILE add TARIF_PLAN_CODE varchar2(20)
/

-- Номер ревизии: 48730
-- Комментарий: Степень риска для групп риска
alter table GROUPS_RISK add (RANK varchar2(5)  default 'HIGH' not null)
/

-- Номер ревизии: 48616
-- Комментарий: Редактирование группы услуг
alter table PAYMENT_SERVICES add ( 
	IS_CATEGORY    char(1) default '0' not null,
	SHOW_IN_API    char(1) default '1' not null,
	SHOW_IN_ATM    char(1) default '1' not null,
	SHOW_IN_SYSTEM char(1) default '1' not null
)
/

-- Номер ревизии: 49792
-- Комментарий: Необходимо реализовать рассинхронизацию старого и нового каталога услуг
--загружаем CSV файлы

insert into  PAYMENT_SERVICES_OLD 
	select id, code, name, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION,SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME from PAYMENT_SERVICES
/
insert into SERV_PROV_PAYM_SERV_OLD 
	select PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID, LIST_INDEX from SERV_PROVIDER_PAYMENT_SERV;
/

alter table PAYMENT_SERVICE_CATEGORIES drop constraint FK_CATEGORY_TO_P_SERV
/

alter table PAYMENT_SERVICE_CATEGORIES
   add constraint FK_CATEGORY_TO_P_SERV foreign key (PAYMENT_SERVICES_ID)
      references PAYMENT_SERVICES_OLD (ID)
/

-- Почистить таблицы для нового справочника:
delete from SERV_PROVIDER_PAYMENT_SERV
/    
delete from PAYMENT_SERVICES
/

-- Загрузить новые услуги:
declare
    serv_id integer; 
    prt_id integer; 

begin 
    for i in (select * from TEMP_SP_SERVICES where parent is null
    ) loop
        -- Если услуга явлется категорией - нет родительской       
        
            if i.ico is null then 
                INSERT INTO PAYMENT_SERVICES(id, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM) 
                VALUES(S_PAYMENT_SERVICES.nextval, i.code, i.name, null, null, 0, i.name, 0, i.priority, 1, '/payment_service/tovary_uslugi.jpg', 1, 1, 1, 1);
            else
                INSERT INTO PAYMENT_SERVICES(id, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM) 
                VALUES(S_PAYMENT_SERVICES.nextval, i.code, i.name, null, null, 0, i.name, 0, i.priority, 1, '/payment_service/'||i.ico||'.jpg', 1, 1, 1, 1);
            end if;
	end loop;
	
	for i in (select * from TEMP_SP_SERVICES where parent is not null
	) loop
        
            if i.ico is null then 
                INSERT INTO PAYMENT_SERVICES(id, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM) 
                VALUES(S_PAYMENT_SERVICES.nextval, i.code, i.name, null, null, 0, i.name, 0, i.priority, 1, '/payment_service/tovary_uslugi.jpg', 0, 1, 1, 1);
            else
                INSERT INTO PAYMENT_SERVICES(id, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM) 
                VALUES(S_PAYMENT_SERVICES.nextval, i.code, i.name, null, null, 0, i.name, 0, i.priority, 1, '/payment_service/'||i.ico||'.jpg', 0, 1, 1, 1);
            end if;    
            --тутже заполняем связку на родителя
            SELECT ID INTO SERV_ID FROM PAYMENT_SERVICES WHERE CODE = I.CODE;
            
            SELECT ID INTO PRT_ID FROM PAYMENT_SERVICES WHERE CODE = I.PARENT;
            
            INSERT INTO PAYMENT_SERV_PARENTS(SERVICE_ID, PARENT_ID) 
            VALUES(SERV_ID, PRT_ID);
			
    end loop;

end;
/
-- Скрываем неугодные услуги
update PAYMENT_SERVICES set SHOW_IN_API = 0, SHOW_IN_ATM = 0, SHOW_IN_SYSTEM = 0 where code in ('99.08','99.99','18.01','99.06','02.01')
/
--пересоздаем связки

insert into SERV_PROVIDER_PAYMENT_SERV(PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID, LIST_INDEX)     
        select ps.id as Payment_service_id, spps_o.SERVICE_PROVIDER_ID as SERVICE_PROVIDER_ID, to_number(dense_rank() over (partition by spps_o.SERVICE_PROVIDER_ID order by spps_o.PAYMENT_SERVICE_ID)) -1 as LIST_INDEX
        from (SERV_PROV_PAYM_SERV_OLD spps_o inner join PAYMENT_SERVICES_OLD ps_o on spps_o.PAYMENT_SERVICE_ID = ps_o.id) 
            inner join PAYMENT_SERVICES ps on ps_o.code = ps.code
/
--переносим поставщиков из credits в погашение кредитов др. банков
insert into SERV_PROVIDER_PAYMENT_SERV (PAYMENT_SERVICE_ID,SERVICE_PROVIDER_ID,LIST_INDEX)
    select ps.id as PAYMENT_SERVICE_ID, sp.id as SERVICE_PROVIDER_ID, '0' as LIST_INDEX 
    from SERVICE_PROVIDERS sp, payment_services ps 
    where sp.id in (select SERVICE_PROVIDER_ID 
                    from SERV_PROV_PAYM_SERV_OLD 
                    where PAYMENT_SERVICE_ID = (select id 
                                                from PAYMENT_SERVICES_OLD 
                                                where CODE = 'credits' ))
    and sp.state = 'ACTIVE' 
    and ps.code = '08.01'
/

--удаляем временную таблицу
drop table TEMP_SP_SERVICES
/

-- Номер ревизии: 48608
-- Комментарий: Редактирование поставщика: добавление блока "настройка видимости в каналах"
-- Номер ревизии: 48851
-- Комментарий: Редактирование поставщика: блок "настройка видимости в каналах"
-- Номер ревизии: 49174
-- Комментарий: Доработка формы настройки автоплатежа.
alter table SERVICE_PROVIDERS add ( 
	VISIBLE_PAYMENTS_FOR_IB 	    char(1) default '0' not null, 
	VISIBLE_PAYMENTS_FOR_M_API 	    char(1) default '0' not null,
	VISIBLE_PAYMENTS_FOR_ATM_API    char(1) default '0' not null, 
	AVAILABLE_PAYMENTS_FOR_IB 		char(1) default '1' not null,
	AVAILABLE_PAYMENTS_FOR_M_API 	char(1) default '1' not null,
	AVAILABLE_PAYMENTS_FOR_ATM_API 	char(1) default '1' not null,
	AVAILABLE_PAYMENTS_FOR_ERMB 	char(1) default '1' not null, 
	IS_AUTOPAYMENT_SUPPORTED_API    char(1) default '0' not null,
	IS_AUTOPAYMENT_SUPPORTED_ATM    char(1) default '0' not null,
	IS_AUTOPAYMENT_SUPPORTED_ERMB   char(1) default '0' not null,
	IS_EDIT_PAYMENT_SUPPORTED       char(1) default '1' not null,
	IS_CREDIT_CARD_SUPPORTED        char(1) default '1' not null,
	VISIBLE_AUTOPAYMENTS_FOR_IB     char(1) default '0' not null,
	VISIBLE_AUTOPAYMENTS_FOR_API    char(1) default '0' not null,
	VISIBLE_AUTOPAYMENTS_FOR_ATM    char(1) default '0' not null,
	VISIBLE_AUTOPAYMENTS_FOR_ERMB   char(1) default '0' not null
)
/

-- Ковертация поставщиков
--Доступность для оплаты ИБ проставляем для всех, у кого была доступна оплата
update SERVICE_PROVIDERS 
	set AVAILABLE_PAYMENTS_FOR_IB = 0
		where IS_ALLOW_PAYMENTS <> 1
/
--Доступность для оплаты mAPI проставляем для всех, кто был активен в мобильном апи
update SERVICE_PROVIDERS 
	set AVAILABLE_PAYMENTS_FOR_M_API = 0
		where MOBILE_SERVICE <> 1
/
--Доступность для оплаты АТМ проставляем для всех, кто был активен в АТМ
update SERVICE_PROVIDERS 
	set AVAILABLE_PAYMENTS_FOR_ATM_API = 0
		where ATM_AVAILABLE <> 1
/

--Видимость в каталоге 
--ИБ проставляем для всех, кто не был скрыт в иерархии
update SERVICE_PROVIDERS 
	set VISIBLE_PAYMENTS_FOR_IB = 1 
		where AVAILABLE_PAYMENTS_FOR_IB = 1 and NOT_VISIBLE_IN_HIERARCHY = 0
/

--АПИ - кто был открыт в иерархии
update SERVICE_PROVIDERS 
	set VISIBLE_PAYMENTS_FOR_M_API = 1 
		where AVAILABLE_PAYMENTS_FOR_M_API = 1 and NOT_VISIBLE_IN_HIERARCHY = 0
/

--АТМ - кто был открыт в иерархии
update SERVICE_PROVIDERS 
	set VISIBLE_PAYMENTS_FOR_ATM_API = 1 
		where AVAILABLE_PAYMENTS_FOR_ATM_API = 1 and NOT_VISIBLE_IN_HIERARCHY = 0
/

--Доступность АП
-- во всех каналах если был признак автоплатежа
update SERVICE_PROVIDERS 
	set IS_AUTOPAYMENT_SUPPORTED_API = 1, IS_AUTOPAYMENT_SUPPORTED_ATM = 1, IS_AUTOPAYMENT_SUPPORTED_ERMB = 1 
		where IS_AUTOPAYMENT_SUPPORTED = 1
/

--Видимость АП в каталоге
-- ИБ - так же как и для оплаты
update SERVICE_PROVIDERS 
	set VISIBLE_AUTOPAYMENTS_FOR_IB = 1
		where IS_AUTOPAYMENT_SUPPORTED = 1 and VISIBLE_PAYMENTS_FOR_IB = 1
/

--АП - отключаем
update SERVICE_PROVIDERS 
	set VISIBLE_AUTOPAYMENTS_FOR_API = 0
/

--АТМ - так же как для ИБ
update SERVICE_PROVIDERS 
	set VISIBLE_AUTOPAYMENTS_FOR_ATM = 1
		where IS_AUTOPAYMENT_SUPPORTED_ATM = 1 and VISIBLE_AUTOPAYMENTS_FOR_IB = 1
/

--ЕРМБ так же как для ИБ
update SERVICE_PROVIDERS 
	set VISIBLE_AUTOPAYMENTS_FOR_ERMB = 1
		where IS_AUTOPAYMENT_SUPPORTED_ERMB = 1 and VISIBLE_AUTOPAYMENTS_FOR_IB = 1
/

--Запрещен платеж с кредитных карт для оплаты кредитов в др. банках
update SERVICE_PROVIDERS 
	set IS_CREDIT_CARD_SUPPORTED = 0
		where id in (	select service_provider_id 
						from SERV_PROVIDER_PAYMENT_SERV 
						where payment_service_id in (
							select id from PAYMENT_SERVICES where code = '08.01'
						)
		)
/

-- Номер ревизии: 49404
-- Комментарий:  Привести форму редактирования поставщика в соответствие макету 
alter table SERVICE_PROVIDERS drop (
	MOBILE_SERVICE,
	ATM_AVAILABLE,
	NOT_VISIBLE_IN_HIERARCHY,
	MIN_SUMMA_THRESHOLD,
	MAX_SUMMA_THRESHOLD,
	MIN_VALUE_THRESHOLD,
	MAX_VALUE_THRESHOLD,
	MIN_SUMMA_ALWAYS,
	MAX_SUMMA_ALWAYS,
	DISCRETE_VALUE_THRESHOLD,
	IS_INTERVAL_THRESHOLD,
	IS_ALWAYS_AUTOPAY_SUPPORTED,
	IS_THRESHOSD_AUTOPAY_SUPPORTED,
	IS_INVOICE_AUTOPAY_SUPPORTED,
	CLIENT_HINT_ALWAYS,
	CLIENT_HINT_THRESHOLD,
	CLIENT_HINT_INVOICE
)
/

-- Номер ревизии: 49663
-- Комментарий: Учет полной версии mAPI в анкете ПУ
update SERVICE_PROVIDERS set VERSION_API = 400 where VERSION_API is not null and VERSION_API < 5
/
update SERVICE_PROVIDERS set VERSION_API = 500 where VERSION_API is not null and VERSION_API = 5
/
	
--убираем из каталога в поиск поставщиков, которых прислал бизнес
-- СКРЫТЬ ВСЕХ пу, У КОТОРЫХ ПО НУЛЯМ
UPDATE SERVICE_PROVIDERS SP SET VISIBLE_PAYMENTS_FOR_IB = 0, VISIBLE_PAYMENTS_FOR_M_API =0, VISIBLE_PAYMENTS_FOR_ATM_API =0
WHERE EXISTS (SELECT * FROM TEMP_SP_UNVISIBLE TSP 
                WHERE TSP.EXTERNAL_ID = SP.EXTERNAL_ID 
                AND (TSP.PAYMENTS = 0 OR TSP.PAYMENTS IS NULL))
/

--скрыть услуги, к которым никто не привязан
update PAYMENT_SERVICES set SHOW_IN_SYSTEM = 0, SHOW_IN_API = 0, SHOW_IN_ATM = 0
where id in (select ps.id from (select sps.* from SERV_PROVIDER_PAYMENT_SERV sps inner join SERVICE_PROVIDERS sp1 on sps.SERVICE_PROVIDER_ID=sp1.id where sp1.VISIBLE_PAYMENTS_FOR_IB = 1) sps1
    right join PAYMENT_SERVICES ps on sps1.PAYMENT_SERVICE_ID=ps.id 
where sps1.SERVICE_PROVIDER_ID is null and ps.is_category <> 1)
/

--Скрыть категории, в укоторых только пустые услуги
update PAYMENT_SERVICES set SHOW_IN_SYSTEM = 0, SHOW_IN_API = 0, SHOW_IN_ATM = 0
where id in (select id from PAYMENT_SERVICES ps1 
--только категории
where ps1.IS_CATEGORY=1 
    -- у которых есть потомки
    and exists (select ps.id from PAYMENT_SERVICES ps inner join PAYMENT_SERV_PARENTS psp on ps.id = psp.service_id 
                where  psp.parent_id = ps1.id) 
    -- но у которых нет видимых потомков
    and not exists (select ps.id from PAYMENT_SERVICES ps inner join PAYMENT_SERV_PARENTS psp on ps.id = psp.service_id 
                where  psp.parent_id = ps1.id and ps.SHOW_IN_SYSTEM = 1))
/
--РАССТАВЛЯЕМ ПРИОРИТЕТЫ ПУ

-- Сначала все сбрасываем
UPDATE SERVICE_PROVIDERS SET SORT_PRIORITY = 0;
/
--Теперь расставляем приоритеты согласно файлу со статистикой
DECLARE
    VPRIORITY NUMBER;
BEGIN
    --ПРОБЕГАЕМ ВСЕ УСЛУГИ, ПО КОТОРЫМ ЕСТЬ СВЯЗКИ С ПОСТАВЩИКАМИ
    FOR SERVICE IN (SELECT * FROM PAYMENT_SERVICES PS WHERE EXISTS (SELECT * FROM SERV_PROVIDER_PAYMENT_SERV SPS 
                                                                    WHERE SPS.PAYMENT_SERVICE_ID = PS.ID)) LOOP
        VPRIORITY:= 100;
        FOR PROVIDER IN (SELECT U1.* FROM (SELECT * FROM SERVICE_PROVIDERS SP1
                           WHERE EXISTS (SELECT 1 FROM SERV_PROVIDER_PAYMENT_SERV SPS1 
                                            WHERE SP1.ID = SPS1.SERVICE_PROVIDER_ID 
                                            AND SPS1.PAYMENT_SERVICE_ID = SERVICE.ID
                            ) 
                            AND SP1.VISIBLE_PAYMENTS_FOR_IB = 1) U1 
                           INNER JOIN 
                            TEMP_SP_UNVISIBLE TSP1 ON U1.EXTERNAL_ID = TSP1.EXTERNAL_ID AND TSP1.PAYMENTS <> 0 
                            ORDER BY TSP1.PAYMENTS DESC
          ) LOOP            
          BEGIN  
            UPDATE SERVICE_PROVIDERS SET SORT_PRIORITY = VPRIORITY WHERE ID = PROVIDER.ID;
            VPRIORITY:= VPRIORITY - 1;
            IF VPRIORITY = 0 THEN EXIT;
            END IF;
          end;
          END LOOP;
     END LOOP;
END;
--удаляем временную таблицу			
drop table TEMP_SP_UNVISIBLE
/

-- Номер ревизии: 48930
-- Комментарий: Добавление признаков "Сотрудник ВСП" и "Доступно сотрудникам ВСП".
alter table ACCESSSCHEMES add VSP_EMPLOYEE_SCHEME char(1) default '0' not null
/
alter table EMPLOYEES add VSP_EMPLOYEE char(1) default '0' not null 
/

--Номер ревизии: 48937
--Комментарий:  MANAGER_ID 14 символов     
alter table EMPLOYEES modify MANAGER_ID varchar2(14)
/
alter table PERSONAL_FINANCE_PROFILE modify MANAGER_ID varchar2(14)
/

--Номер ревизии: 49012
--Комментарий:  Маппинг ошибок. часть 5. Настройка сообщений об ошибках для клиента и сотрудника.
alter table STATIC_MESSAGES modify (KEY varchar2(64))
/
update STATIC_MESSAGES set KEY = concat('com.rssl.iccs.',KEY)
/
alter table IMAGES_MESSAGES modify (MESSAGE_KEY varchar2(64))
/
update IMAGES_MESSAGES set MESSAGE_KEY = concat('com.rssl.iccs.',MESSAGE_KEY)
/

--Номер ревизии: 49038
--Комментарий:  Доработать гейтовый интерфейс Field(добавить поле mask) 
alter table FIELD_DESCRIPTIONS add (MASK varchar2(1024) null)
/

--Номер ревизии: 49265
--Комментарий:  уровень безопасности лимитов
alter table LIMITS add (SECURITY_TYPE varchar2(8))
/

-- Номер ревизии: 48678
-- Комментарий: Управление текстами смс в ЕРИБ. 
--Комментарий:  Реализация процедуры синхронизации текстов смс в базе ПРОМа и SVN. Модель БД.
alter table MESSAGE_TEMPLATES add (
   KEY                  varchar2(255),
   DESCRIPTION          varchar2(255),
   PREVIOUS_TEXT        clob,
   LAST_MODIFIED        timestamp,
   EMPLOYEE_LOGIN_ID    integer,
   VARIABLES            varchar2(4000)	
)
/

-- Номер ревизии: 49336
-- Комментарий: настройки безопасности для клиента
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityStartDate','C','')
/
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityDaysNumber','C','')
/
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInATM','C','')
/
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInERIB','C','')
/
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInVSP','C','')
/
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInCC','C','')
/

-- Номер ревизии: 49388
-- Комментарий: Подсказки для вкладки "Расходы" (мои финансы): бюджетирование
alter table PROFILE add (HINT_READ CHAR(1) default '0' not null)
/

-- Номер ревизии: 49391
-- Комментарий: Реализовать автоматический докат биллинговых операций в статусе "неизвестен"
create index DOC_ID_JOB_NAME_PER_IDX on PAYMENT_EXECUTION_RECORDS
(
    DOCUMENT_ID asc,
    JOB_NAME asc
)
/

-- Номер ревизии: 49329
delete from LOAN_CARD_OFFER
/
alter table LOAN_CARD_OFFER drop column LOGIN_ID
/
alter table LOAN_CARD_OFFER drop column DEPARTMENT_ID
/

alter table LOAN_CARD_OFFER add (
	FIRST_NAME    varchar2(20) not null, 
	SUR_NAME      varchar2(20) not null, 
	PATR_NAME     varchar2(20),
	BIRTHDAY      timestamp not null 
)
/

create index LCO_FIO_DUL_TB_V_DR on LOAN_CARD_OFFER
(
   upper(replace(SUR_NAME,' ','') || replace(FIRST_NAME,' ','') || replace(PATR_NAME,' ','')),
   upper(replace(SERIES_NUMBER, ' ', '')),
   TB,
   IS_VIEWED,
   BIRTHDAY
)
/

alter table USERS add (SECURITY_TYPE varchar2(8))
/

-- Номер ревизии: 49688 
alter table RATE add EXPIRE_DATE timestamp
/

--Уровень безопасности клиентов, по согласованию со Смирновым Иваном
alter session enable parallel DML
--время исполнения 2:49
update /*+parallel (USERS, 32)*/ USERS set SECURITY_TYPE = 'LOW' 
/

alter table SHOP_FIELDS add CANCELED char(1) default '0' not null
/

create index ORDERS_USER_ID_DATE on ORDERS 
(
    USER_ID asc,
    ORDER_DATE desc
)
parallel 32
/
alter index ORDERS_USER_ID_DATE noparallel
/

alter table ACCOUNT_LINKS set unused column SHOW_IN_ES
/
alter table ACCOUNT_LINKS modify SHOW_IN_ATM default '1'
/
alter table CARD_LINKS modify SHOW_IN_ATM default '1'
/
alter table LOAN_LINKS modify SHOW_IN_ATM default '1'
/
alter table IMACCOUNT_LINKS modify SHOW_IN_ATM default '1'
/


alter table STORED_LONG_OFFER drop constraint FK_STORED_LO_DEPARTMENTS_REF
/
alter table STORED_DEPO_ACCOUNT drop constraint STORED_DA_TO_DEPARTMENTS_REF
/
alter table STORED_IMACCOUNT drop constraint FK_STORED_I_TO_DEPARTMENTS_REF
/
alter table STORED_LOAN drop constraint STORED_L_TO_DEPARTMENTS_REF
/
alter table STORED_CARD drop constraint FK_STORED_C_TO_DEPARTMENT_REF
/
alter table STORED_ACCOUNT drop constraint FK_STORED_A_TO_DEPARTMENT_REF
/

-- Номер ревизии: 50428
alter table STORED_CARD drop column ADDITIONAL_CARD_TYPE
/
alter table CARD_LINKS add ADDITIONAL_CARD_TYPE varchar2(20)
/
alter table ACCOUNT_LINKS add (
	OFFICE_TB   varchar2(5),
	OFFICE_OSB  varchar2(5),
	OFFICE_VSP  varchar2(5)
)
/

-- Номер ревизии: 50297
alter table BUSINESS_DOCUMENTS modify (CONFIRM_EMPLOYEE varchar2(256))
/
