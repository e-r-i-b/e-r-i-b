with CALENDAR as (
    select to_date ('30/10/2013', 'dd/mm/yyyy') + LEVEL - 1 filter_date
    from dual
    connect by LEVEL <= to_date ('30/11/2013', 'dd/mm/yyyy') + 1 - to_date ('30/10/2013', 'dd/mm/yyyy')    
)
select
    calendar.filter_date "Дата",
    count(case when (filterLog.PERIOD_TYPE='MONTH' and filterLog.IS_DEFAULT='0') then 1 else null end) 
    as "отчет за месяц",
    count(case when (filterLog.PERIOD_TYPE='BEFORE_TEN_DAYS' and filterLog.IS_DEFAULT='0') then 1 else null end)
    as "до 10 дней",
    count(case when (filterLog.PERIOD_TYPE='FROM_TEN_TO_TWENTY_DAYS' and filterLog.IS_DEFAULT='0') then 1 else null end)
    as "от 10 до 20 дней",
    count(case when (filterLog.PERIOD_TYPE='FROM_TWENTY_TO_THIRTY_DAYS' and filterLog.IS_DEFAULT='0') then 1 else null end)
    as "от 20 до 30 дней",
    count(case when (filterLog.PERIOD_TYPE='FROM_THIRTY_TO_NINETY_DAYS' and filterLog.IS_DEFAULT='0') then 1 else null end)
    as "от 30 до 90 дней",
    count(case when (filterLog.PERIOD_TYPE='FROM_NINETY_DAYS_TO_HALF_YEAR' and filterLog.IS_DEFAULT='0') then 1 else null end)
     as "от 90 до 183 дней",
    count(case when (filterLog.PERIOD_TYPE='MORE_THAN_HALF_YEAR' and filterLog.IS_DEFAULT='0') then 1 else null end)
    as "от 183 дней",
    count(case when (filterLog.IS_DEFAULT='1') then 1 else null end)
    as "отчет по умолчанию"
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
