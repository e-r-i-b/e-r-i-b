/*
	Схема: LOG_IKFL
	Табличное пространство таблиц: ERIBLOG
	Табличное пространство индексов: ERIBLOGINDEXES
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = LOG_IKFL
/

-- Номер ревизии: 73487
-- Комментарий: Доработка механизма логирования.
alter table LOG_IKFL.SYSTEMLOG_B add (ADD_INFO varchar2(128))
/
alter table LOG_IKFL.SYSTEMLOG_S add (ADD_INFO varchar2(128))
/

alter table LOG_IKFL.USERLOG_B add (ADD_INFO varchar2(128))
/
alter table LOG_IKFL.USERLOG_S add (ADD_INFO varchar2(128))
/

alter table LOG_IKFL.CODLOG_B add (ADD_INFO varchar2(128))
/
alter table LOG_IKFL.CODLOG_S add (ADD_INFO varchar2(128))
/
alter table LOG_IKFL.CODLOG_S3 add (ADD_INFO varchar2(128))
/

alter table LOG_IKFL.FINANCIAL_CODLOG_B add (ADD_INFO varchar2(128))
/
alter table LOG_IKFL.FINANCIAL_CODLOG_S add (ADD_INFO varchar2(128))
/

alter table LOG_IKFL.CSA_CODLOG_B add (ADD_INFO varchar2(128))
/
alter table LOG_IKFL.CSA_CODLOG_S add (ADD_INFO varchar2(128))
/

alter table LOG_IKFL.CSA_SYSTEMLOG_B add (ADD_INFO varchar2(128))
/
alter table LOG_IKFL.CSA_SYSTEMLOG_S add (ADD_INFO varchar2(128))
/

create or replace force view LOG_IKFL."SYSTEMLOG" ( "ID","MSG_LEVEL","START_DATE","LOGIN_ID","APPLICATION","MESSAGE","SESSION_ID","IP_ADDRESS","MESSAGE_SOURCE","DEPARTMENT_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","THREAD_INFO","USER_ID","NODE_ID","TB","OSB","VSP","ADD_INFO" ) as
  select "ID","MSG_LEVEL","START_DATE","LOGIN_ID","APPLICATION","MESSAGE","SESSION_ID","IP_ADDRESS","MESSAGE_SOURCE","DEPARTMENT_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","THREAD_INFO","USER_ID","NODE_ID","TB","OSB","VSP","ADD_INFO" from SYSTEMLOG_B
  union all
  select "ID","MSG_LEVEL","START_DATE","LOGIN_ID","APPLICATION","MESSAGE","SESSION_ID","IP_ADDRESS","MESSAGE_SOURCE","DEPARTMENT_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","THREAD_INFO","USER_ID","NODE_ID","TB","OSB","VSP","ADD_INFO" from SYSTEMLOG_S
  /

create or replace trigger LOG_IKFL.SYSTEMLOG_IOI instead of insert on LOG_IKFL.SYSTEMLOG
for each row
declare
    insert_s_q constant varchar2(1024):='insert into SYSTEMLOG_S';
    insert_b_q constant varchar2(1024):='insert into SYSTEMLOG_B';
    insert_f constant varchar2(1024):='(ID, MSG_LEVEL, START_DATE, LOGIN_ID, APPLICATION, MESSAGE, SESSION_ID, IP_ADDRESS, MESSAGE_SOURCE, DEPARTMENT_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DEPARTMENT_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDAY, THREAD_INFO, USER_ID, NODE_ID, TB, OSB, VSP, ADD_INFO) values(:ID, :MSG_LEVEL, :START_DATE, :LOGIN_ID, :APPLICATION, :MESSAGE, :SESSION_ID, :IP_ADDRESS, :MESSAGE_SOURCE, :DEPARTMENT_ID, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DEPARTMENT_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDAY, :THREAD_INFO, :USER_ID, :NODE_ID, :TB, :OSB, :VSP, :ADD_INFO)';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='SYSTEMLOG';
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
        execute immediate insert_str using :new.ID, :new.MSG_LEVEL, :new.START_DATE, :new.LOGIN_ID, :new.APPLICATION, :new.MESSAGE, :new.SESSION_ID, :new.IP_ADDRESS, :new.MESSAGE_SOURCE, :new.DEPARTMENT_ID, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DEPARTMENT_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDAY, :new.THREAD_INFO, :new.USER_ID, :new.NODE_ID, :new.TB, :new.OSB, :new.VSP, :new.ADD_INFO;
    end if;
end;
/

create or replace force view LOG_IKFL."USERLOG" ("ID", "DESCRIPTION", "DESCRIPTION_KEY", "SUCCESS", "START_DATE", "LOGIN_ID", "APPLICATION", "OPERATION_KEY", "PARAMETERS", "SESSION_ID", "IP_ADDRESS", "EXECUTION_TIME", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "DEPARTMENT_ID", "USER_ID", "NODE_ID", "TB", "OSB", "VSP", "THREAD_INFO", "ADD_INFO") AS 
  select "ID","DESCRIPTION","DESCRIPTION_KEY","SUCCESS","START_DATE","LOGIN_ID","APPLICATION","OPERATION_KEY","PARAMETERS","SESSION_ID","IP_ADDRESS","EXECUTION_TIME","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","DEPARTMENT_ID","USER_ID","NODE_ID","TB","OSB","VSP","THREAD_INFO","ADD_INFO" from USERLOG_B
  union all
  select "ID","DESCRIPTION","DESCRIPTION_KEY","SUCCESS","START_DATE","LOGIN_ID","APPLICATION","OPERATION_KEY","PARAMETERS","SESSION_ID","IP_ADDRESS","EXECUTION_TIME","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","DEPARTMENT_ID","USER_ID","NODE_ID","TB","OSB","VSP","THREAD_INFO","ADD_INFO" from USERLOG_S
  /

create or replace trigger LOG_IKFL.USERLOG_IOI instead of insert on LOG_IKFL.USERLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into USERLOG_S';
    insert_b_q constant varchar2(1024):='insert into USERLOG_B';
    insert_f constant varchar2(1024):='(ID, DESCRIPTION, DESCRIPTION_KEY, SUCCESS, START_DATE, LOGIN_ID, APPLICATION, OPERATION_KEY, PARAMETERS, SESSION_ID, IP_ADDRESS, EXECUTION_TIME, FIRST_NAME, SUR_NAME, PATR_NAME, DEPARTMENT_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDAY, DEPARTMENT_ID, USER_ID, NODE_ID, TB, OSB, VSP, THREAD_INFO, ADD_INFO) values(:ID, :DESCRIPTION, :DESCRIPTION_KEY, :SUCCESS, :START_DATE, :LOGIN_ID, :APPLICATION, :OPERATION_KEY, :PARAMETERS, :SESSION_ID, :IP_ADDRESS, :EXECUTION_TIME, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DEPARTMENT_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDAY, :DEPARTMENT_ID, :USER_ID, :NODE_ID, :TB, :OSB, :VSP, :THREAD_INFO, :ADD_INFO)';
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
        execute immediate insert_str using :new.ID, :new.DESCRIPTION, :new.DESCRIPTION_KEY, :new.SUCCESS, :new.START_DATE, :new.LOGIN_ID, :new.APPLICATION, :new.OPERATION_KEY, :new.PARAMETERS, :new.SESSION_ID, :new.IP_ADDRESS, :new.EXECUTION_TIME, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DEPARTMENT_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDAY, :new.DEPARTMENT_ID, :new.USER_ID, :new.NODE_ID, :new.TB, :new.OSB, :new.VSP, :new.THREAD_INFO, :new.ADD_INFO;
    end if;
end;
/

create or replace force view "CODLOG" ("ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "LOGIN_ID", "DEPARTMENT_ID", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "OPERATION_UID", "NODE_ID", "TB", "OSB", "VSP", "THREAD_INFO", "ADD_INFO") AS 
  select "ID","START_DATE","EXECUTION_TIME","APPLICATION","MESSAGE_TYPE","MESSAGE_DEMAND_ID","MESSAGE_DEMAND","MESSAGE_ANSWER_ID","MESSAGE_ANSWER","SYST","LOGIN_ID","DEPARTMENT_ID","SESSION_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","OPERATION_UID","NODE_ID","TB","OSB","VSP","THREAD_INFO","ADD_INFO" from CODLOG_B
  union all
  select "ID","START_DATE","EXECUTION_TIME","APPLICATION","MESSAGE_TYPE","MESSAGE_DEMAND_ID","MESSAGE_DEMAND","MESSAGE_ANSWER_ID","MESSAGE_ANSWER","SYST","LOGIN_ID","DEPARTMENT_ID","SESSION_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","OPERATION_UID","NODE_ID","TB","OSB","VSP","THREAD_INFO","ADD_INFO" from CODLOG_S
  union all
  select "ID","START_DATE","EXECUTION_TIME","APPLICATION","MESSAGE_TYPE","MESSAGE_DEMAND_ID","MESSAGE_DEMAND","MESSAGE_ANSWER_ID","MESSAGE_ANSWER","SYST","LOGIN_ID","DEPARTMENT_ID","SESSION_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","OPERATION_UID","NODE_ID","TB","OSB","VSP","THREAD_INFO","ADD_INFO" from CODLOG_S3
/

create or replace trigger LOG_IKFL.CODLOG_IOI instead of insert on LOG_IKFL.CODLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into CODLOG_S3';
    insert_b_q constant varchar2(1024):='insert into CODLOG_B';
    insert_f constant varchar2(1024):='(ID, START_DATE, EXECUTION_TIME, APPLICATION, MESSAGE_TYPE, MESSAGE_DEMAND_ID, MESSAGE_DEMAND, MESSAGE_ANSWER_ID, MESSAGE_ANSWER, SYST, LOGIN_ID, DEPARTMENT_ID, SESSION_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DEPARTMENT_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDAY, OPERATION_UID, NODE_ID, TB, OSB, VSP, THREAD_INFO, ADD_INFO) values(:ID, :START_DATE, :EXECUTION_TIME, :APPLICATION, :MESSAGE_TYPE, :MESSAGE_DEMAND_ID, :MESSAGE_DEMAND, :MESSAGE_ANSWER_ID, :MESSAGE_ANSWER, :SYST, :LOGIN_ID, :DEPARTMENT_ID, :SESSION_ID, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DEPARTMENT_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDAY, :OPERATION_UID, :NODE_ID, :TB, :OSB, :VSP, :THREAD_INFO, :ADD_INFO)';
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
        execute immediate insert_str using :new.ID, :new.START_DATE, :new.EXECUTION_TIME, :new.APPLICATION, :new.MESSAGE_TYPE, :new.MESSAGE_DEMAND_ID, :new.MESSAGE_DEMAND, :new.MESSAGE_ANSWER_ID, :new.MESSAGE_ANSWER, :new.SYST, :new.LOGIN_ID, :new.DEPARTMENT_ID, :new.SESSION_ID, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DEPARTMENT_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDAY, :new.OPERATION_UID, :new.NODE_ID, :new.TB, :new.OSB, :new.VSP, :new.THREAD_INFO, :new.ADD_INFO;
    end if;
end;
/

create or replace force view LOG_IKFL."FINANCIAL_CODLOG" ("ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "LOGIN_ID", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "OPERATION_UID", "NODE_ID", "TB", "OSB", "VSP", "THREAD_INFO", "ADD_INFO") AS 
  select "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "LOGIN_ID", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "OPERATION_UID", "NODE_ID", "TB", "OSB", "VSP", "THREAD_INFO", "ADD_INFO" from FINANCIAL_CODLOG_B
  union all
  select "ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "LOGIN_ID", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "OPERATION_UID", "NODE_ID", "TB", "OSB", "VSP", "THREAD_INFO", "ADD_INFO" from FINANCIAL_CODLOG_S
/

create or replace trigger LOG_IKFL.FINANCIAL_CODLOG_IOI instead of insert on LOG_IKFL.FINANCIAL_CODLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into FINANCIAL_CODLOG_S';
    insert_b_q constant varchar2(1024):='insert into FINANCIAL_CODLOG_B';
    insert_f constant varchar2(1024):='(ID, START_DATE, EXECUTION_TIME, APPLICATION, MESSAGE_TYPE, MESSAGE_DEMAND_ID, MESSAGE_DEMAND, MESSAGE_ANSWER_ID, MESSAGE_ANSWER, SYST, LOGIN_ID, SESSION_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DEPARTMENT_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDAY, OPERATION_UID, NODE_ID, TB, OSB, VSP, THREAD_INFO, ADD_INFO)  values ( :ID, :START_DATE, :EXECUTION_TIME, :APPLICATION, :MESSAGE_TYPE, :MESSAGE_DEMAND_ID, :MESSAGE_DEMAND, :MESSAGE_ANSWER_ID, :MESSAGE_ANSWER, :SYST, :LOGIN_ID, :SESSION_ID, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DEPARTMENT_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDAY, :OPERATION_UID, :NODE_ID, :TB, :OSB, :VSP, :THREAD_INFO, :ADD_INFO)';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='FINANCIAL_CODLOG';
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
        execute immediate insert_str using :new.ID, :new.START_DATE, :new.EXECUTION_TIME, :new.APPLICATION, :new.MESSAGE_TYPE, :new.MESSAGE_DEMAND_ID, :new.MESSAGE_DEMAND, :new.MESSAGE_ANSWER_ID, :new.MESSAGE_ANSWER, :new.SYST, :new.LOGIN_ID, :new.SESSION_ID, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DEPARTMENT_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDAY, :new.OPERATION_UID, :new.NODE_ID, :new.TB, :new.OSB, :new.VSP, :new.THREAD_INFO, :new.ADD_INFO;
    end if;
end;
/

create or replace force view LOG_IKFL."CSA_CODLOG" ("ID", "START_DATE", "EXECUTION_TIME", "APPLICATION", "MESSAGE_TYPE", "MESSAGE_DEMAND_ID", "MESSAGE_DEMAND", "MESSAGE_ANSWER_ID", "MESSAGE_ANSWER", "SYST", "SESSION_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "OPERATION_UID", "LOGIN", "DEPARTMENT_CODE", "MGUID", "PROMOTER_ID", "IP_ADDRESS", "ERROR_CODE", "LOG_UID", "THREAD_INFO", "ADD_INFO") AS 
  select "ID","START_DATE","EXECUTION_TIME","APPLICATION","MESSAGE_TYPE","MESSAGE_DEMAND_ID","MESSAGE_DEMAND","MESSAGE_ANSWER_ID","MESSAGE_ANSWER","SYST","SESSION_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","OPERATION_UID","LOGIN","DEPARTMENT_CODE","MGUID","PROMOTER_ID","IP_ADDRESS","ERROR_CODE","LOG_UID","THREAD_INFO","ADD_INFO" from CSA_CODLOG_B
  union all
  select "ID","START_DATE","EXECUTION_TIME","APPLICATION","MESSAGE_TYPE","MESSAGE_DEMAND_ID","MESSAGE_DEMAND","MESSAGE_ANSWER_ID","MESSAGE_ANSWER","SYST","SESSION_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","OPERATION_UID","LOGIN","DEPARTMENT_CODE","MGUID","PROMOTER_ID","IP_ADDRESS","ERROR_CODE","LOG_UID","THREAD_INFO","ADD_INFO" from CSA_CODLOG_S
/

create or replace trigger LOG_IKFL.CSA_CODLOG_IOI instead of insert on LOG_IKFL.CSA_CODLOG for each row
declare
    insert_s_q constant varchar2(1024):='insert into CSA_CODLOG_S';
    insert_b_q constant varchar2(1024):='insert into CSA_CODLOG_B';
    insert_f constant varchar2(1024):='(ID, START_DATE, EXECUTION_TIME, APPLICATION, MESSAGE_TYPE, MESSAGE_DEMAND_ID, MESSAGE_DEMAND, MESSAGE_ANSWER_ID, MESSAGE_ANSWER, SYST, SESSION_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDAY, OPERATION_UID, LOGIN, DEPARTMENT_CODE, MGUID, PROMOTER_ID, IP_ADDRESS, ERROR_CODE, LOG_UID, THREAD_INFO, ADD_INFO) values(:ID, :START_DATE, :EXECUTION_TIME, :APPLICATION, :MESSAGE_TYPE, :MESSAGE_DEMAND_ID, :MESSAGE_DEMAND, :MESSAGE_ANSWER_ID, :MESSAGE_ANSWER, :SYST, :SESSION_ID, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDAY, :OPERATION_UID, :LOGIN, :DEPARTMENT_CODE, :MGUID, :PROMOTER_ID, :IP_ADDRESS, :ERROR_CODE, :LOG_UID, :THREAD_INFO, :ADD_INFO)';
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
            execute immediate insert_str using :new.ID, :new.START_DATE, :new.EXECUTION_TIME, :new.APPLICATION, :new.MESSAGE_TYPE, :new.MESSAGE_DEMAND_ID, :new.MESSAGE_DEMAND, :new.MESSAGE_ANSWER_ID, :new.MESSAGE_ANSWER, :new.SYST, :new.SESSION_ID, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDAY, :new.OPERATION_UID, :new.LOGIN, :new.DEPARTMENT_CODE, :new.MGUID, :new.PROMOTER_ID, :new.IP_ADDRESS, :new.ERROR_CODE, :new.LOG_UID, :new.THREAD_INFO, :new.ADD_INFO;
    end if;
end;
/

create or replace force view LOG_IKFL."CSA_SYSTEMLOG" ("ID", "MSG_LEVEL", "START_DATE", "LOGIN_ID", "APPLICATION", "MESSAGE", "SESSION_ID", "IP_ADDRESS", "MESSAGE_SOURCE", "DEPARTMENT_ID", "FIRST_NAME", "SUR_NAME", "PATR_NAME", "DEPARTMENT_NAME", "DOC_NUMBER", "DOC_SERIES", "BIRTHDAY", "THREAD_INFO", "LOGIN", "DEPARTMENT_CODE", "LOG_UID","ADD_INFO") AS 
  select "ID","MSG_LEVEL","START_DATE","LOGIN_ID","APPLICATION","MESSAGE","SESSION_ID","IP_ADDRESS","MESSAGE_SOURCE","DEPARTMENT_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","THREAD_INFO","LOGIN","DEPARTMENT_CODE","LOG_UID","ADD_INFO" from CSA_SYSTEMLOG_B
  union all
  select "ID","MSG_LEVEL","START_DATE","LOGIN_ID","APPLICATION","MESSAGE","SESSION_ID","IP_ADDRESS","MESSAGE_SOURCE","DEPARTMENT_ID","FIRST_NAME","SUR_NAME","PATR_NAME","DEPARTMENT_NAME","DOC_NUMBER","DOC_SERIES","BIRTHDAY","THREAD_INFO","LOGIN","DEPARTMENT_CODE","LOG_UID","ADD_INFO" from CSA_SYSTEMLOG_S
/

create or replace trigger LOG_IKFL.CSA_SYSTEMLOG_IOI instead of insert on LOG_IKFL.CSA_SYSTEMLOG
for each row
declare
    insert_s_q constant varchar2(1024):='insert into CSA_SYSTEMLOG_S';
    insert_b_q constant varchar2(1024):='insert into CSA_SYSTEMLOG_B';
    insert_f constant varchar2(1024):='(ID, MSG_LEVEL, START_DATE, LOGIN_ID, APPLICATION, MESSAGE, SESSION_ID, IP_ADDRESS, MESSAGE_SOURCE, DEPARTMENT_ID, FIRST_NAME, SUR_NAME, PATR_NAME, DEPARTMENT_NAME, DOC_NUMBER, DOC_SERIES, BIRTHDAY, THREAD_INFO, LOGIN, DEPARTMENT_CODE, LOG_UID, ADD_INFO) values(:ID, :MSG_LEVEL, :START_DATE, :LOGIN_ID, :APPLICATION, :MESSAGE, :SESSION_ID, :IP_ADDRESS, :MESSAGE_SOURCE, :DEPARTMENT_ID, :FIRST_NAME, :SUR_NAME, :PATR_NAME, :DEPARTMENT_NAME, :DOC_NUMBER, :DOC_SERIES, :BIRTHDAY, :THREAD_INFO, :LOGIN, :DEPARTMENT_CODE, :LOG_UID, :ADD_INFO)';
    insert_str varchar2(1024);
    flag char(1);
begin
    begin
        select TAB_SOURCE into flag from LOG_CONFIG where TAB_NAME='CSA_SYSTEMLOG';
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
        execute immediate insert_str using :new.ID, :new.MSG_LEVEL, :new.START_DATE, :new.LOGIN_ID, :new.APPLICATION, :new.MESSAGE, :new.SESSION_ID, :new.IP_ADDRESS, :new.MESSAGE_SOURCE, :new.DEPARTMENT_ID, :new.FIRST_NAME, :new.SUR_NAME, :new.PATR_NAME, :new.DEPARTMENT_NAME, :new.DOC_NUMBER, :new.DOC_SERIES, :new.BIRTHDAY, :new.THREAD_INFO, :new.LOGIN, :new.DEPARTMENT_CODE, :new.LOG_UID, :new.ADD_INFO;
    end if;
end;
/