set pages 0 feed off
set trimspool on
set term off
set verify off

spool off

column report_start_date new_value fromDate
column report_end_date new_value toDate
select nvl('&2',to_char(sysdate-1,'dd.mm.yyyy')) as report_start_date from dual;
select nvl('&3',to_char(sysdate-1,'dd.mm.yyyy')) as report_end_date from dual;
spool &1\report-3-&fromDate..csv

SELECT COUNT(*) FROM USER_MESSAGES_LOG
WHERE
ADDITION_DATE >= to_date('&fromDate','dd.mm.yyyy')
AND ADDITION_DATE < to_date('&toDate','dd.mm.yyyy') + 1
AND TYPE = 'push'

spool off;
exit;