select 
    to_char(CHANGE_DATE, 'dd.mm.yyyy') "����",
    TB "��",
    VSP "���",
    FIO "������",
    OPERATION_NAME "��������",
    MCC_CODE "���-���",
    AMOUNT "�����",
    PARENT_CATEGORY "�������� ���������",
    NEW_CATEGORIES "����� ���������",
    NEW_CATEGORIES_COUNT "���������� ����� ���������"
from 
    CHANGE_CARD_OP_CATEGORY_LOG
where 
    CHANGE_DATE between TO_DATE('20.10.2013', 'dd.mm.yyyy') and TO_DATE('31.10.2013', 'dd.mm.yyyy')
    and TB || VSP in (774, 775, 364)
//    and TB in (77, 36)
order by TB, VSP, CHANGE_DATE
go