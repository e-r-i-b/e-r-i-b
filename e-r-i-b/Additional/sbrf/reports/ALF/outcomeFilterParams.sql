with CALENDAR as (
    select to_date ('30/10/2013', 'dd/mm/yyyy') + LEVEL - 1 filter_date
    from dual
    connect by LEVEL <= to_date ('30/11/2013', 'dd/mm/yyyy') + 1 - to_date ('30/10/2013', 'dd/mm/yyyy')    
)
select
    calendar.filter_date "����",
    count(case when (filterLog.PERIOD_TYPE='MONTH' and filterLog.IS_DEFAULT='0') then 1 else null end) 
    as "����� �� �����",
    count(case when (filterLog.PERIOD_TYPE='BEFORE_TEN_DAYS' and filterLog.IS_DEFAULT='0') then 1 else null end)
    as "�� 10 ����",
    count(case when (filterLog.PERIOD_TYPE='FROM_TEN_TO_TWENTY_DAYS' and filterLog.IS_DEFAULT='0') then 1 else null end)
    as "�� 10 �� 20 ����",
    count(case when (filterLog.PERIOD_TYPE='FROM_TWENTY_TO_THIRTY_DAYS' and filterLog.IS_DEFAULT='0') then 1 else null end)
    as "�� 20 �� 30 ����",
    count(case when (filterLog.PERIOD_TYPE='FROM_THIRTY_TO_NINETY_DAYS' and filterLog.IS_DEFAULT='0') then 1 else null end)
    as "�� 30 �� 90 ����",
    count(case when (filterLog.PERIOD_TYPE='FROM_NINETY_DAYS_TO_HALF_YEAR' and filterLog.IS_DEFAULT='0') then 1 else null end)
     as "�� 90 �� 183 ����",
    count(case when (filterLog.PERIOD_TYPE='MORE_THAN_HALF_YEAR' and filterLog.IS_DEFAULT='0') then 1 else null end)
    as "�� 183 ����",
    count(case when (filterLog.IS_DEFAULT='1') then 1 else null end)
    as "����� �� ���������"
from  
    CALENDAR calendar
left join 
    FILTER_OUTCOME_PERIOD_LOG filterLog 
        on filterLog.FILTER_DATE = calendar.FILTER_DATE
group by 
    calendar.FILTER_DATE
order by 
    calendar.FILTER_DATE
GO
