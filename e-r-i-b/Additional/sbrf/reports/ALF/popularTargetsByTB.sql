with CALENDAR as (
    select distinct this_date, tb, targets.name target 
    from
    (
        select to_date ('31/10/2013', 'dd/mm/yyyy') + LEVEL - 1 this_date
        from dual
        connect by LEVEL <= to_date ('06/11/2013', 'dd/mm/yyyy') + 1 - to_date ('31/10/2013', 'dd/mm/yyyy')
    ),
    (
        select '77' tb from dual
        union select '36' tb from dual
    ),
    ACCOUNT_TARGETS targets
)
select  
    to_char(calendar.this_date, 'dd.mm.yyyy') as "Дата",
    calendar.tb as "ТБ",
    calendar.target as "Цель",
    count(case when target_dates.target_date <= calendar.this_date then 1 else null end) as "Количество целей",
    TO_CHAR(
        nvl(count(case when target_dates.target_date <= calendar.this_date then 1 else null end) / 
        nullif((select count(distinct targets.client) cnt from DATES_ADD_ACCOUNT_TARGETS targets where targets.target_date <= calendar.this_date and targets.tb = calendar.tb), 0), 0), 
    '99999999990.99') as "Среднее количество"
from 
    CALENDAR calendar
left join 
    DATES_ADD_ACCOUNT_TARGETS target_dates 
on 
    calendar.tb = target_dates.tb and 
    calendar.target = target_dates.target_name
group by calendar.this_date, calendar.tb, calendar.target
order by calendar.this_date, calendar.tb, calendar.target
/