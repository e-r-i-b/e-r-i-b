with CALENDAR as (
    select this_date, tb 
    from
    (
        select to_date ('20/10/2013', 'dd/mm/yyyy') + LEVEL - 1 this_date
        from dual
        connect by LEVEL <= to_date ('30/10/2013', 'dd/mm/yyyy') + 1 - to_date ('20/10/2013', 'dd/mm/yyyy')
    ),
    (
        select '77' tb from dual
        union select '36' tb from dual
    )
)
select 
    calendar.THIS_DATE "Дата",
    calendar.TB "ТБ",
    (
        select 
            nvl(sum(dates.CNT),0) 
        from 
            CALENDAR cal 
        left join 
            DATES_ADD_PERSONAL_FINANCE dates on cal.THIS_DATE = dates.START_USING_PERSONAL_FINANCE and cal.TB = dates.TB 
        where
            cal.THIS_DATE <= calendar.THIS_DATE and cal.TB = calendar.TB
    ) "Текущее количество подключений",
    nvl(sum(dates.CNT), 0) "Количество новых подключений"
from 
    CALENDAR calendar 
left join 
    DATES_ADD_PERSONAL_FINANCE dates 
        on dates.START_USING_PERSONAL_FINANCE = calendar.THIS_DATE and calendar.TB = dates.TB
group by 
    calendar.THIS_DATE, calendar.TB
ORDER BY 
    calendar.TB, calendar.THIS_DATE
/