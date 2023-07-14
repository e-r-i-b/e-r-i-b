--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
-- Номер ревизии: 61711
-- Номер версии: 1.18
-- Комментарий: CHG071536: Перевод ФОС на многоблочный режим.
alter table CSA_IKFL.CSA_NODES add (
	MULTI_NODE_DATA_QUEUE_NAME     varchar2(64),
	MULTI_NODE_DATA_FACTORY_NAME   varchar2(64)
)
/
update CSA_IKFL.CSA_NODES set MULTI_NODE_DATA_QUEUE_NAME = 'jms/resources/MultiNodeResourcesQueueB1'
where id = 1
/
update CSA_IKFL.CSA_NODES set MULTI_NODE_DATA_FACTORY_NAME = 'jms/resources/MultiNodeResourcesQCFB1'
where id = 1
/
update CSA_IKFL.CSA_NODES set MULTI_NODE_DATA_QUEUE_NAME = 'jms/resources/MultiNodeResourcesQueueB2'
where id = 2
/
update CSA_IKFL.CSA_NODES set MULTI_NODE_DATA_FACTORY_NAME = 'jms/resources/MultiNodeResourcesQCFB2'
where id = 2
/
alter table CSA_IKFL.CSA_NODES modify (MULTI_NODE_DATA_QUEUE_NAME not null)
/
alter table CSA_IKFL.CSA_NODES modify (MULTI_NODE_DATA_FACTORY_NAME not null)
/

-- Номер ревизии: 62235
-- Номер версии: 1.18
-- Комментарий: Доработки ЕРМБ. Абонентская плата. Реализация многоблочности (ч2)
alter table CSA_IKFL.CSA_NODES add (
	SERVICE_FEE_RES_QUEUE_NAME varchar2(64),
	SERVICE_FEE_RES_FACTORY_NAME varchar2(64)
)
/
update CSA_IKFL.CSA_NODES set SERVICE_FEE_RES_QUEUE_NAME = 'jms/ermb/transport/ServiceFeeResultRqQueueB1'
where id = 1
/
update CSA_IKFL.CSA_NODES set SERVICE_FEE_RES_FACTORY_NAME = 'jms/ermb/transport/ServiceFeeResultRqQCFB1'
where id = 1
/
update CSA_IKFL.CSA_NODES set SERVICE_FEE_RES_QUEUE_NAME = 'jms/ermb/transport/ServiceFeeResultRqQueueB2'
where id = 2
/
update CSA_IKFL.CSA_NODES set SERVICE_FEE_RES_FACTORY_NAME = 'jms/ermb/transport/ServiceFeeResultRqQCFB2'
where id = 2
/
alter table CSA_IKFL.CSA_NODES modify (SERVICE_FEE_RES_QUEUE_NAME not null)
/
alter table CSA_IKFL.CSA_NODES modify (SERVICE_FEE_RES_FACTORY_NAME not null)
/

-- Номер ревизии: 62972
-- Номер версии: 1.18
-- Комментарий: ЕРМБ. ФПП. Доработка ЦСА. (фабрика/очередь) 
alter table CSA_IKFL.CSA_NODES add (
	MBK_REGISTRATION_QUEUE_NAME varchar2(64),
	MBK_REGISTRATION_FACTORY_NAME varchar2(64)
)
/
update CSA_IKFL.CSA_NODES set MBK_REGISTRATION_QUEUE_NAME = 'jms/ermb/mbk/RegistrationRqQueueB1'
where id = 1
/
update CSA_IKFL.CSA_NODES set MBK_REGISTRATION_FACTORY_NAME = 'jms/ermb/mbk/RegistrationRqQCFB1'
where id = 1
/
update CSA_IKFL.CSA_NODES set MBK_REGISTRATION_QUEUE_NAME = 'jms/ermb/mbk/RegistrationRqQueueB2'
where id = 2
/
update CSA_IKFL.CSA_NODES set MBK_REGISTRATION_FACTORY_NAME = 'jms/ermb/mbk/RegistrationRqQCFB2'
where id = 2
/
alter table CSA_IKFL.CSA_NODES modify (MBK_REGISTRATION_QUEUE_NAME not null)
/
alter table CSA_IKFL.CSA_NODES modify (MBK_REGISTRATION_FACTORY_NAME not null)
/

-- Номер ревизии: 62760
-- Номер версии: 1.18
-- Комментарий: ENH070461: Уйти от использования ConnectorInfo в ЕРИБ и ЦСА 
alter table CSA_IKFL.CSA_CONNECTORS add (REGISTRATION_LOGIN_TYPE varchar2(20))
/
update (
  select /*+parallel(connector, 16) parallel(info, 16)*/ connector.GUID, connector.REGISTRATION_LOGIN_TYPE, info.LOGIN_TYPE from CSA_IKFL.CSA_CONNECTORS connector
  inner join CSA_IKFL.CONNECTORS_INFO info on info.GUID = connector.GUID
  where connector.TYPE = 'MAPI'
) conn set conn.REGISTRATION_LOGIN_TYPE=conn.LOGIN_TYPE
/
drop table CSA_IKFL.CONNECTORS_INFO
/
drop sequence CSA_IKFL.S_CONNECTORS_INFO
/

-- Номер ревизии: 62764
-- Номер версии: 1.18
-- Комментарий: CHG070268: Сделать ip-адрес для запросов в цса необязательным 
alter table CSA_IKFL.CSA_OPERATIONS modify (IP_ADDRESS null)
/

-- Номер ревизии: 63272
-- Номер версии: 1.18
-- Комментарий: BUG071121: полное сканирование таблицы CSA_PROFILES 
create index CSA_IKFL.CSA_PROFILES_INCOGNITO_INDEX on CSA_IKFL.CSA_PROFILES (
   decode(INCOGNITO, '1', ID, null) ASC
) parallel 64 tablespace CSAINDEXES 
/
alter index CSA_IKFL.CSA_PROFILES_INCOGNITO_INDEX noparallel
/

-- Номер ревизии: 64384
-- Номер версии: 1.18
-- Комментарий: CHG074609 	Использование CSA_PROFILES_UNIVERSAL_ID
drop index CSA_IKFL.CSA_PROFILES_UNIVERSAL_ID
/
create unique index CSA_IKFL.CSA_PROFILES_UNIVERSAL_ID on CSA_IKFL.CSA_PROFILES (
	upper (
	 trim (
		REGEXP_REPLACE (
		   SUR_NAME || ' ' || FIRST_NAME || ' ' || PATR_NAME,
		   '( )+',
		   ' '))) asc,
	BIRTHDATE asc,
	replace (PASSPORT, ' ', '') asc,
	TB asc
) parallel 64 tablespace CSAINDEXES
/
alter index CSA_IKFL.CSA_PROFILES_UNIVERSAL_ID noparallel
/

-- Номер ревизии: 64479
-- Номер версии: 1.18
-- Комментарий: BUG074237: Запрос получения списка клиентов ЦСА.
drop index CSA_IKFL.CSA_PROFILES_DUL_BIRTH_INDEX
/
create index CSA_IKFL.CSA_PROFILES_DUL_BIRTH_INDEX on CSA_IKFL.CSA_PROFILES (
   replace(replace(PASSPORT,' ',''),'-','') asc,
   BIRTHDATE desc
) parallel 64 tablespace CSAINDEXES
/
alter index CSA_IKFL.CSA_PROFILES_DUL_BIRTH_INDEX noparallel
/

update CSA_IKFL.csa_nodes set LISTENER_HOSTNAME = '10.67.254.188:9081' where id = 1
/
/* Сбор статистики
begin dbms_stats.gather_table_stats( ownname => 'CSA_IKFL', tabname => 'CSA_PROFILES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSA_IKFL', tabname => 'CSA_OPERATIONS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSA_IKFL', tabname => 'CSA_CONNECTORS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSA_IKFL', tabname => 'CSA_NODES', degree => 32, cascade => true); end;
*/