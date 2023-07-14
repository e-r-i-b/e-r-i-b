--� ����� SELECT ����������� ���� ������ � ����� �������. ��������� � ����� ���������� ��������.

-- ���������� ������� �����������
select 
    logs.NOTIFICATION_ID as "������������ �����������", 
    logs.NAME  as "�������� �����������", 
    count(logs.ID) as "���������� ������� �����������" 
from OFFER_NOTIFICATIONS_LOG logs 
where logs.TYPE='SHOW'
AND DISPLAY_DATE  BETWEEN to_date('01.03.2014 00:00:00', 'dd.mm.yyyy HH24:MI:SS')  AND to_date('31.03.2014 23:59:59', 'dd.mm.yyyy HH24:MI:SS')
group BY logs.NOTIFICATION_ID, logs.NAME
go

-- ���������� ���������� ��������
select 
    logs.NOTIFICATION_ID as "������������ �����������", 
    logs.NAME  as "�������� �����������", 
    count(distinct logs.LOGIN_ID) as "���������� ���������� ��������" 
from 
    OFFER_NOTIFICATIONS_LOG logs 
where logs.TYPE='CLICK'
AND DISPLAY_DATE  BETWEEN to_date('01.03.2014 00:00:00', 'dd.mm.yyyy HH24:MI:SS')  AND to_date('31.03.2014 23:59:59', 'dd.mm.yyyy HH24:MI:SS')
group BY logs.NOTIFICATION_ID, logs.NAME
go

-- ���������� ������
select 
    logs.NOTIFICATION_ID as "������������ �����������", 
    logs.NAME  as "�������� �����������", 
    count(logs.ID) as "���������� ������" 
from OFFER_NOTIFICATIONS_LOG logs 
where logs.TYPE='CLICK'
AND DISPLAY_DATE  BETWEEN to_date('01.03.2014 00:00:00', 'dd.mm.yyyy HH24:MI:SS')  AND to_date('31.03.2014 23:59:59', 'dd.mm.yyyy HH24:MI:SS')
group BY logs.NOTIFICATION_ID, logs.NAME
go

