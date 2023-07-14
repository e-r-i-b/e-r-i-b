create or replace procedure SPLIT_LOG_TABLE(tableName varchar, splitDate timestamp) is begin
    -- им€ секции на 1 мес€ц меньше, чем дата разбиени€
    execute immediate 'alter table '||tableName||' split partition P_MAXVALUE AT (to_date('''||to_char(splitDate, 'YYYYMMDD')||''',''YYYYMMDD'' )) into (partition P_'||to_char(ADD_MONTHS(splitDate, -1), 'YYYY_MM')||', partition P_MAXVALUE)'; end; /

begin
DBMS_SCHEDULER.CREATE_JOB (
   job_name                 =>  'USERLOG_SPLIT_P', 
   job_type                 =>  'PLSQL_BLOCK',
   job_action               =>  'BEGIN SPLIT_LOG_TABLE(''USERLOG'', ADD_MONTHS(trunc(sysdate, ''mon''),2)); END;',
   start_date               =>  to_date('2013-02-22 03:00','YYYY-MM-DD HH24:MI'),
   repeat_interval          =>  'FREQ=MONTHLY',
   enabled => TRUE
   );
end;
/

begin
DBMS_SCHEDULER.CREATE_JOB (
   job_name                 =>  'SYSTEMLOG_SPLIT_P', 
   job_type                 =>  'PLSQL_BLOCK',
   job_action               =>  'BEGIN SPLIT_LOG_TABLE(''SYSTEMLOG'', ADD_MONTHS(trunc(sysdate, ''mon''),2)); END;',
   start_date               =>  to_date('2013-02-22 03:00','YYYY-MM-DD HH24:MI'),
   repeat_interval          => 	'FREQ=MONTHLY',
   enabled => TRUE
   );
end;
/

begin
DBMS_SCHEDULER.CREATE_JOB (
   job_name                 =>  'CODLOG_SPLIT_P', 
   job_type                 =>  'PLSQL_BLOCK',
   job_action               =>  'BEGIN SPLIT_LOG_TABLE(''CODLOG'', ADD_MONTHS(trunc(sysdate, ''mon''),2)); END;',
   start_date               =>  to_date('2013-02-22 03:00','YYYY-MM-DD HH24:MI'),
   repeat_interval          => 	'FREQ=MONTHLY',
   enabled => TRUE
   );
end;
/
