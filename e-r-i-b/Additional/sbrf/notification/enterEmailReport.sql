SELECT 
    count(LOGIN_ID) as "���������� ��������" 
FROM
	USER_NOTIFICATION_LOG 
WHERE 
	ADDITION_DATE BETWEEN to_date('01.11.2013 00:00:00', 'dd.mm.yyyy HH24:MI:SS')  AND to_date('07.11.2013 23:59:59', 'dd.mm.yyyy HH24:MI:SS')
	AND TYPE='emailInput'
/