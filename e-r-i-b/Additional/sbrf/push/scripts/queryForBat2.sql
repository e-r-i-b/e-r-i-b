set pages 0 feed off
set trimspool on
set term off
set verify off

spool off

column report_start_date new_value fromDate
column report_end_date new_value toDate
select nvl('&2',to_char(sysdate-1,'dd.mm.yyyy')) as report_start_date from dual;
select nvl('&3',to_char(sysdate-1,'dd.mm.yyyy')) as report_end_date from dual;
spool &1\report-2-&fromDate..csv

SELECT LOGIN_NOTIFICATION || &4 ||MAIL_NOTIFICATION || &4 || OPERATION_NOTIFICATION || &4 || OPERATION_CONFIRM AS COUNTS FROM  
	(SELECT count(distinct login_ID) as LOGIN_NOTIFICATION FROM USER_NOTIFICATION_LOG 
		WHERE value = 'push'
		AND ADDITION_DATE >= to_date('&fromDate','dd.mm.yyyy') AND ADDITION_DATE < to_date('&toDate','dd.mm.yyyy') + 1 
		AND type = 'loginNotification') count1, 
	(SELECT count(distinct login_ID) as MAIL_NOTIFICATION FROM USER_NOTIFICATION_LOG 
		where value = 'push'
		AND ADDITION_DATE >= to_date('&fromDate','dd.mm.yyyy') AND ADDITION_DATE < to_date('&toDate','dd.mm.yyyy') + 1
		AND type = 'mailNotification') count2, 
	(SELECT count(distinct login_ID) as OPERATION_NOTIFICATION FROM USER_NOTIFICATION_LOG 
		where value = 'push'
		AND ADDITION_DATE >= to_date('&fromDate','dd.mm.yyyy') AND ADDITION_DATE < to_date('&toDate','dd.mm.yyyy') + 1 
		AND type = 'operationNotification') count3, 
	(SELECT count(distinct login_ID) as OPERATION_CONFIRM FROM USER_NOTIFICATION_LOG 
		where value = 'push'
		AND ADDITION_DATE >= to_date('&fromDate','dd.mm.yyyy') AND ADDITION_DATE < to_date('&toDate','dd.mm.yyyy') + 1 
		AND type = 'operationConfirm') count45

spool off;
exit;