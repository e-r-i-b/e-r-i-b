-- Номер ревизии: 55819
-- Комментарий: CHG063851 Механизм мапинга ошибок(ч3. вставка и агрегация отдельно)
/*==============================================================*/
/* Table: EXCEPTIONS_LOG                                        */
/*==============================================================*/
create table EXCEPTIONS_LOG 
(
   CREATION_DATE        TIMESTAMP            not null,
   HASH                 VARCHAR2(256)        not null
)
partition by range (CREATION_DATE) interval (NUMTODSINTERVAL(1,'day'))
(
    partition P_FIRST values less than (to_date('1-11-2013','DD-MM-YYYY'))
)
/

-- Номер ревизии: 55819
-- Комментарий: иок (поправил тип параметра)
create or replace procedure updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in clob, exceptionSystem in varchar, exceptionErrorCode in varchar) as
   pragma autonomous_transaction;
   updateExceptionInformationOFF number;
begin
    select count(1) into updateExceptionInformationOFF from PROPERTIES where PROPERTY_KEY='com.rssl.phizic.business.exception.updateExceptionInformation' and PROPERTY_VALUE = 'OFF' and CATEGORY='iccs.properties';

    if updateExceptionInformationOFF > 0 then
        rollback;
        return;
    end if;

    insert into EXCEPTIONS_LOG(CREATION_DATE, HASH) values (SYSDATE, exceptionHash);

    insert into EXCEPTION_ENTRY (ID, KIND, HASH, APPLICATION, OPERATION, DETAIL, MESSAGE, SYSTEM, ERROR_CODE) 
        select S_EXCEPTION_ENTRY.nextval, exceptionKind, exceptionHash, exceptionApplication, exceptionOperation, exceptionDetail, null, exceptionSystem, exceptionErrorCode from DUAL
        where not exists (select 1 from EXCEPTION_ENTRY where EXCEPTION_ENTRY.HASH = exceptionHash);

    commit;
end;
/

create or replace procedure aggregateExceptionInformation(aggregationDate timestamp) as
begin
 execute immediate 'INSERT INTO EXCEPTION_COUNTERS (EXCEPTION_HASH, EXCEPTION_DATE, EXCEPTION_COUNT) '||
    'SELECT HASH, to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' ), count(1) FROM EXCEPTIONS_LOG '||
        'PARTITION FOR (to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' )) GROUP BY HASH';

 execute immediate 'alter table EXCEPTIONS_LOG drop partition for (to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' ))';
end;
/

begin
DBMS_SCHEDULER.CREATE_JOB (
   job_name                 =>  'AGGREGATE_EXC_INFO', 
   job_type                 =>  'PLSQL_BLOCK',
   job_action               =>  'BEGIN SRB_IKFL.aggregateExceptionInformation(sysdate-1); END;',
   start_date               =>  to_date('2013-11-26 02:25','YYYY-MM-DD HH24:MI'),
   repeat_interval          =>  'FREQ=DAILY',
   enabled => TRUE
   );
end;
/


