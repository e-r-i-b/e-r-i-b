SELECT 
	CASE
		WHEN TYPE = 'newsNotification'  THEN 'Рассылка новостей банка'
		WHEN TYPE = 'loginNotification'  THEN 'Уведомление о входе на личную страницу'
		WHEN TYPE = 'mailNotification'  THEN 'Оповещение из службы помощи'
		WHEN TYPE = 'operationNotification'  THEN 'Оповещение об исполнении операции'
	END "Тип оповещения", 
	count(id) "Количество клиентов" 
FROM USER_NOTIFICATION_LOG 
WHERE 
	ADDITION_DATE  BETWEEN to_date('01.11.2013 00:00:00', 'dd.mm.yyyy HH24:MI:SS')  AND to_date('07.11.2013 23:59:59', 'dd.mm.yyyy HH24:MI:SS')
	AND VALUE = 'email'
GROUP BY TYPE
/