SELECT 
    ROWNUM as "� ��",
    statistic.descr as "DESCRIPTION",
    statistic.mcc as "mcc-���",
    statistic.originalCategory as "�������� ���������",
    statistic.newCategory as "����� ���������",
    statistic.countApply as "���-�� ������������",
    statistic.countDiff as "���-�� ���������"
FROM
( 
    SELECT
        DESCRIPTION as descr,
        MCC_CODE as mcc,
        ORIGINAL_CATEGORY as originalCategory,
        NEW_CATEGORY as newCategory,
        SUM(DECODE(OPERATION_TYPE, 'APPLY', OPERATIONS_COUNT, 0)) as countApply,
        SUM(DECODE(OPERATION_TYPE, 'ADD', OPERATIONS_COUNT, 'REMOVE', -OPERATIONS_COUNT, 0)) as countDiff
    FROM
        ALF_RECATEGORIZATION_LOG
    WHERE
        LOG_DATE between TO_DATE('01.04.2014', 'DD.MM.YYYY') and TO_DATE('30.04.2014', 'DD.MM.YYYY')
    GROUP BY
        DESCRIPTION, MCC_CODE, ORIGINAL_CATEGORY, NEW_CATEGORY
) statistic
go