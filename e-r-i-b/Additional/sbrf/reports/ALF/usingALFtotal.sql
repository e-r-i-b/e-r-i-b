with CALENDAR as (
    select to_date ('20/10/2013', 'dd/mm/yyyy') + LEVEL - 1 this_date
    from dual
    connect by LEVEL <= to_date ('30/10/2013', 'dd/mm/yyyy') + 1 - to_date ('20/10/2013', 'dd/mm/yyyy')
)
select 
    calendar.THIS_DATE "Дата",
    (
        select 
            nvl(sum(cnt), 0) 
        from 
            CALENDAR cal 
        left join 
            DATES_ADD_PERSONAL_FINANCE dates on cal.THIS_DATE = dates.START_USING_PERSONAL_FINANCE 
        where 
            cal.THIS_DATE <= calendar.THIS_DATE
    ) "Текущее количество подключений",
    nvl(sum(CNT), 0) "Количество новых подключений"
from 
    CALENDAR calendar 
left join 
    DATES_ADD_PERSONAL_FINANCE dates on dates.START_USING_PERSONAL_FINANCE = calendar.THIS_DATE
group by 
    calendar.THIS_DATE
ORDER BY calendar.THIS_DATE
/
