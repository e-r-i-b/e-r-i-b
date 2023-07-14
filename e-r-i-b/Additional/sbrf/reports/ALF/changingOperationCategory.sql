select 
    to_char(CHANGE_DATE, 'dd.mm.yyyy') "Дата",
    TB "ТБ",
    VSP "ВСП",
    FIO "Клиент",
    OPERATION_NAME "Операция",
    MCC_CODE "МСС-код",
    AMOUNT "Сумма",
    PARENT_CATEGORY "Исходная категория",
    NEW_CATEGORIES "Новая категория",
    NEW_CATEGORIES_COUNT "Количество новых категорий"
from 
    CHANGE_CARD_OP_CATEGORY_LOG
where 
    CHANGE_DATE between TO_DATE('20.10.2013', 'dd.mm.yyyy') and TO_DATE('31.10.2013', 'dd.mm.yyyy')
    and TB || VSP in (774, 775, 364)
//    and TB in (77, 36)
order by TB, VSP, CHANGE_DATE
go