--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
-- Номер ревизии: 59325
-- Номер версии: 1.18
-- Комментарий: ENH067289: Добавить информацию в CODLOG.
alter table LOG_IKFL.CODLOG_B add THREAD_INFO integer
/
alter table LOG_IKFL.CODLOG_S add THREAD_INFO integer
/
alter table LOG_IKFL.CODLOG_S3 add THREAD_INFO integer
/
alter table LOG_IKFL.CSA_CODLOG_B add THREAD_INFO integer
/
alter table LOG_IKFL.CSA_CODLOG_S add THREAD_INFO integer
/
alter table LOG_IKFL.USERLOG_B add THREAD_INFO integer
/
alter table LOG_IKFL.USERLOG_S add THREAD_INFO integer
/

create or replace force view "LOG_IKFL"."CODLOG" ("ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "LOGIN_ID", "DEPARTMENT_ID", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "OPERATION_UID", "NODE_ID", "TB", "OSB", "VSP", "THREAD_INFO") as 
	select "ID","START_DATE","EXECUTION_TIME","APPLICATION","MESSAGE_TYPE","MESSAGE_DEMAND_ID","MESSAGE_DEMAND","MESSAGE_ANSWER_ID","MESSAGE_ANSWER","SYST","LOGIN_ID","DEPARTMENT_ID","SESSION_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","OPERATION_UID","NODE_ID","TB","OSB","VSP","THREAD_INFO" from CODLOG_B
	union all
	select "ID","START_DATE","EXECUTION_TIME","APPLICATION","MESSAGE_TYPE","MESSAGE_DEMAND_ID","MESSAGE_DEMAND","MESSAGE_ANSWER_ID","MESSAGE_ANSWER","SYST","LOGIN_ID","DEPARTMENT_ID","SESSION_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","OPERATION_UID","NODE_ID","TB","OSB","VSP","THREAD_INFO" from CODLOG_S
	union all
	select "ID","START_DATE","EXECUTION_TIME","APPLICATION","MESSAGE_TYPE","MESSAGE_DEMAND_ID","MESSAGE_DEMAND","MESSAGE_ANSWER_ID","MESSAGE_ANSWER","SYST","LOGIN_ID","DEPARTMENT_ID","SESSION_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","OPERATION_UID","NODE_ID","TB","OSB","VSP","THREAD_INFO" from CODLOG_S3
/
create or replace trigger CODLOG_IOI instead of insert on CODLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into CODLOG_S3';
    insert_b_q constant varchar2(1024):='insert into CODLOG_B';
    insert_f constant varchar2(1024):='(ID, START_DATE, EXECUTION_TIME, APPLICATION, MESSAGE_TYPE, MESSAGE_DEMAND_ID, MESSAGE_DEMAND, MESSAGE_ANSWER_ID, MESSAGE_ANSWER, SYST, LOGIN_ID, DEPARTMENT_ID, SESSION_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DEPARTMENT_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDAY, OPERATION_UID, NODE_ID, TB, OSB, VSP, THREAD_INFO) values(:ID, :START_DATE, :EXECUTION_TIME, :APPLICATION, :MESSAGE_TYPE, :MESSAGE_DEMAND_ID, :MESSAGE_DEMAND, :MESSAGE_ANSWER_ID, :MESSAGE_ANSWER, :SYST, :LOGIN_ID, :DEPARTMENT_ID, :SESSION_ID, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DEPARTMENT_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDAY, :OPERATION_UID, :NODE_ID, :TB, :OSB, :VSP, :THREAD_INFO)';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='CODLOG';
    exception
        when NO_DATA_FOUND then
            flag:='S';
        when TOO_MANY_ROWS then
            flag:='S';
    end;
    if (flag='F') then
      null;
    else
        if (flag='B') then
            insert_str :=insert_b_q || insert_f;
        else
            insert_str :=insert_s_q || insert_f;
        end if;
        execute immediate insert_str using :new.ID, :new.START_DATE, :new.EXECUTION_TIME, :new.APPLICATION, :new.MESSAGE_TYPE, :new.MESSAGE_DEMAND_ID, :new.MESSAGE_DEMAND, :new.MESSAGE_ANSWER_ID, :new.MESSAGE_ANSWER, :new.SYST, :new.LOGIN_ID, :new.DEPARTMENT_ID, :new.SESSION_ID, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DEPARTMENT_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDAY, :new.OPERATION_UID, :new.NODE_ID, :new.TB, :new.OSB, :new.VSP, :new.THREAD_INFO;
    end if;
end;
/
alter trigger LOG_IKFL.CODLOG_IOI enable
/

create or replace force view "LOG_IKFL"."CSA_CODLOG" ("ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "OPERATION_UID", "LOGIN", "DEPARTMENT_CODE", "MGUID", "PROMOTER_ID", "IP_ADDRESS", "ERROR_CODE", "LOG_UID", "THREAD_INFO") as 
	select "ID","START_DATE","EXECUTION_TIME","APPLICATION","MESSAGE_TYPE","MESSAGE_DEMAND_ID","MESSAGE_DEMAND","MESSAGE_ANSWER_ID","MESSAGE_ANSWER","SYST","SESSION_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","OPERATION_UID","LOGIN","DEPARTMENT_CODE","MGUID","PROMOTER_ID","IP_ADDRESS","ERROR_CODE","LOG_UID","THREAD_INFO" from CSA_CODLOG_B
	union all
	select "ID","START_DATE","EXECUTION_TIME","APPLICATION","MESSAGE_TYPE","MESSAGE_DEMAND_ID","MESSAGE_DEMAND","MESSAGE_ANSWER_ID","MESSAGE_ANSWER","SYST","SESSION_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","OPERATION_UID","LOGIN","DEPARTMENT_CODE","MGUID","PROMOTER_ID","IP_ADDRESS","ERROR_CODE","LOG_UID","THREAD_INFO" from CSA_CODLOG_S
/
create or replace trigger CSA_CODLOG_IOI instead of insert on CSA_CODLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into CSA_CODLOG_S';
    insert_b_q constant varchar2(1024):='insert into CSA_CODLOG_B';
    insert_f constant varchar2(1024):='(ID, START_DATE, EXECUTION_TIME, APPLICATION, MESSAGE_TYPE, MESSAGE_DEMAND_ID, MESSAGE_DEMAND, MESSAGE_ANSWER_ID, MESSAGE_ANSWER, SYST, SESSION_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDAY, OPERATION_UID, LOGIN, DEPARTMENT_CODE, MGUID, PROMOTER_ID, IP_ADDRESS, ERROR_CODE, LOG_UID, THREAD_INFO) values(:ID, :START_DATE, :EXECUTION_TIME, :APPLICATION, :MESSAGE_TYPE, :MESSAGE_DEMAND_ID, :MESSAGE_DEMAND, :MESSAGE_ANSWER_ID, :MESSAGE_ANSWER, :SYST, :SESSION_ID, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDAY, :OPERATION_UID, :LOGIN, :DEPARTMENT_CODE, :MGUID, :PROMOTER_ID, :IP_ADDRESS, :ERROR_CODE, :LOG_UID, :THREAD_INFO)';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='CSA_CODLOG';
    exception
        when NO_DATA_FOUND then
            flag:='S';
        when TOO_MANY_ROWS then
            flag:='S';
    end;
    if (flag='F') then
      null;
    else
        if (flag='B') then
            insert_str :=insert_b_q || insert_f;
        else
            insert_str :=insert_s_q || insert_f;
        end if;
            execute immediate insert_str using :new.ID, :new.START_DATE, :new.EXECUTION_TIME, :new.APPLICATION, :new.MESSAGE_TYPE, :new.MESSAGE_DEMAND_ID, :new.MESSAGE_DEMAND, :new.MESSAGE_ANSWER_ID, :new.MESSAGE_ANSWER, :new.SYST, :new.SESSION_ID, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDAY, :new.OPERATION_UID, :new.LOGIN, :new.DEPARTMENT_CODE, :new.MGUID, :new.PROMOTER_ID, :new.IP_ADDRESS, :new.ERROR_CODE, :new.LOG_UID, :new.THREAD_INFO;
    end if;
end;
/
alter trigger LOG_IKFL.CSA_CODLOG_IOI enable
/

create or replace force view "LOG_IKFL"."USERLOG" ("ID", "DESCRIPTION", "DESCRIPTION_KEY", "SUCCESS", "START_DATE", "LOGIN_ID", "APPLICATION", "OPERATION_KEY", "PARAMETERS", "SESSION_ID", "IP_ADDRESS", "EXECUTION_TIME", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "DEPARTMENT_ID", "USER_ID", "NODE_ID", "TB", "OSB", "VSP", "THREAD_INFO") AS 
	select "ID","DESCRIPTION","DESCRIPTION_KEY","SUCCESS","START_DATE","LOGIN_ID","APPLICATION","OPERATION_KEY","PARAMETERS","SESSION_ID","IP_ADDRESS","EXECUTION_TIME","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","DEPARTMENT_ID","USER_ID","NODE_ID","TB","OSB","VSP","THREAD_INFO" from USERLOG_B
	union all
	select "ID","DESCRIPTION","DESCRIPTION_KEY","SUCCESS","START_DATE","LOGIN_ID","APPLICATION","OPERATION_KEY","PARAMETERS","SESSION_ID","IP_ADDRESS","EXECUTION_TIME","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","DEPARTMENT_ID","USER_ID","NODE_ID","TB","OSB","VSP","THREAD_INFO" from USERLOG_S
/
create or replace trigger USERLOG_IOI instead of insert on USERLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into USERLOG_S';
    insert_b_q constant varchar2(1024):='insert into USERLOG_B';
    insert_f constant varchar2(1024):='(ID, DESCRIPTION, DESCRIPTION_KEY, SUCCESS, START_DATE, LOGIN_ID, APPLICATION, OPERATION_KEY, PARAMETERS, SESSION_ID, IP_ADDRESS, EXECUTION_TIME, FIRST_NAME, SUR_NAME, PATR_NAME, DEPARTMENT_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDAY, DEPARTMENT_ID, USER_ID, NODE_ID, TB, OSB, VSP, THREAD_INFO) values(:ID, :DESCRIPTION, :DESCRIPTION_KEY, :SUCCESS, :START_DATE, :LOGIN_ID, :APPLICATION, :OPERATION_KEY, :PARAMETERS, :SESSION_ID, :IP_ADDRESS, :EXECUTION_TIME, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DEPARTMENT_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDAY, :DEPARTMENT_ID, :USER_ID, :NODE_ID, :TB, :OSB, :VSP, :THREAD_INFO)';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='USERLOG';
    exception
        when NO_DATA_FOUND then
            flag:='S';
        when TOO_MANY_ROWS then
            flag:='S';
    end;
    if (flag='F') then
      null;
    else
        if (flag='B') then
            insert_str :=insert_b_q || insert_f;
        else
            insert_str :=insert_s_q || insert_f;
        end if;
        execute immediate insert_str using :new.ID, :new.DESCRIPTION, :new.DESCRIPTION_KEY, :new.SUCCESS, :new.START_DATE, :new.LOGIN_ID, :new.APPLICATION, :new.OPERATION_KEY, :new.PARAMETERS, :new.SESSION_ID, :new.IP_ADDRESS, :new.EXECUTION_TIME, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DEPARTMENT_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDAY, :new.DEPARTMENT_ID, :new.USER_ID, :new.NODE_ID, :new.TB, :new.OSB, :new.VSP, :new.THREAD_INFO;
    end if;
end;
/
alter trigger LOG_IKFL.USERLOG_IOI enable
/

-- Номер ревизии: 60483
-- Номер версии: 1.18
-- Комментарий:  BUG070976: Не отображается баннер по предодобренной карте\кредиту 
alter table LOG_IKFL.OFFER_NOTIFICATIONS_LOG modify NAME varchar2(40)
/

-- Номер ревизии: 62031
-- Номер версии: 1.18
-- Комментарий: CHG072320: Секционировать ADVERTISINGS_LOG 
alter table LOG_IKFL.ADVERTISINGS_LOG rename to FORDEL$ADVERTISINGS_LOG
/
alter table LOG_IKFL.FORDEL$ADVERTISINGS_LOG drop constraint PK_ADVERTISINGS_LOG
/
alter index LOG_IKFL.I_ADVERTISING_LOG rename to FORDEL$I_ADVERTIS_LOG
/
alter index LOG_IKFL.DXFK_ADVERTISINGS_LOG rename to FORDEL$DXFK_ADVERTIS_LOG
/

create table LOG_IKFL.ADVERTISINGS_LOG 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   TYPE                 VARCHAR2(20)         not null
)
partition by range (START_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
(
	partition P_START values less than (to_date('01-06-2014','DD-MM-YYYY')) tablespace ERIBLOG
) tablespace ERIBLOG
/
create index LOG_IKFL.I_ADVERTISING_LOG on LOG_IKFL.ADVERTISINGS_LOG (
   START_DATE ASC
) local tablespace ERIBLOGINDEXES
/
create index LOG_IKFL.DXFK_ADVERTISINGS_LOG on LOG_IKFL.ADVERTISINGS_LOG (
   ADVERTISING_ID ASC
) local tablespace ERIBLOGINDEXES
/

/* Сбор статистики
begin dbms_stats.gather_table_stats( ownname => 'LOG_IKFL', tabname => 'ADVERTISINGS_LOG', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'LOG_IKFL', tabname => 'OFFER_NOTIFICATIONS_LOG', degree => 32, cascade => true); end;
*/
/*Права
grant select on LOG_IKFL.ADVERTISINGS_LOG to OSDBO_USER;
*/