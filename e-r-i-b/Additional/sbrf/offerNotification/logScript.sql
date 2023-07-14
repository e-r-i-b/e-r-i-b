--В блоке SELECT проставляем даты начала и конца периода. Запускаем в среде разработки запросов.

-- Количество показов уведомления
select 
    logs.NOTIFICATION_ID as "Иденификатор уведомления", 
    logs.NAME  as "Название уведомления", 
    count(logs.ID) as "Количество показов уведомления" 
from OFFER_NOTIFICATIONS_LOG logs 
where logs.TYPE='SHOW'
AND DISPLAY_DATE  BETWEEN to_date('01.03.2014 00:00:00', 'dd.mm.yyyy HH24:MI:SS')  AND to_date('31.03.2014 23:59:59', 'dd.mm.yyyy HH24:MI:SS')
group BY logs.NOTIFICATION_ID, logs.NAME
go

-- Количество уникальных клиентов
select 
    logs.NOTIFICATION_ID as "Иденификатор уведомления", 
    logs.NAME  as "Название уведомления", 
    count(distinct logs.LOGIN_ID) as "Количество уникальных клиентов" 
from 
    OFFER_NOTIFICATIONS_LOG logs 
where logs.TYPE='CLICK'
AND DISPLAY_DATE  BETWEEN to_date('01.03.2014 00:00:00', 'dd.mm.yyyy HH24:MI:SS')  AND to_date('31.03.2014 23:59:59', 'dd.mm.yyyy HH24:MI:SS')
group BY logs.NOTIFICATION_ID, logs.NAME
go

-- Количество кликов
select 
    logs.NOTIFICATION_ID as "Иденификатор уведомления", 
    logs.NAME  as "Название уведомления", 
    count(logs.ID) as "Количество кликов" 
from OFFER_NOTIFICATIONS_LOG logs 
where logs.TYPE='CLICK'
AND DISPLAY_DATE  BETWEEN to_date('01.03.2014 00:00:00', 'dd.mm.yyyy HH24:MI:SS')  AND to_date('31.03.2014 23:59:59', 'dd.mm.yyyy HH24:MI:SS')
group BY logs.NOTIFICATION_ID, logs.NAME
go

