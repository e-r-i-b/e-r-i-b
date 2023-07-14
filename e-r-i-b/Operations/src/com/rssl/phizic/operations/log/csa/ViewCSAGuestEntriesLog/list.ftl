
SELECT entry.*
FROM CSA_GUEST_ACTION_LOG entry
WHERE
entry.START_DATE >= :extra_fromDate and entry.START_DATE <= :extra_toDate


<#if fio?has_content>
<#--
        ������� �������:
           CSA_GAL_FIO_DATE_IDX

        �������� �������:
           UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) LIKE
               UPPER(REPLACE(REPLACE(:EXTRA_FIO,' ',''),'-','')||'%') AND
               SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_TODATE)) AND
               SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE)))

        ��������������:
            �� ��������� ����� ������ ����� � ��������� ��� � �� �� ��������� ������
-->
    and UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) like upper(replace(replace(:extra_fio, ' ', ''), '-', '')||'%')
</#if>
and (:extra_birthDay is null or entry.BIRTHDATE = :extra_birthDay)
<#if phone?has_content>
<#--
        ������� �������:
            CSA_GAL_PHONE_IDX

        �������� �������: "ENTRY"."PHONE_NUMBER"=:EXTRA_PHONE AND SYS_OP_DESCEND("START_DATE")>=SYS_OP_DESCEND(TO_TIMESTAMP(:EXT
            RA_TODATE)) AND SYS_OP_DESCEND("START_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:EXTRA_FROMDATE))

        ��������������:
        �� ��������� ����� ������ ����� � �������� ������� �� ��������� ������
-->
    and entry.PHONE_NUMBER = :extra_phone
</#if>

and (:extra_TB is null or entry.TB = :extra_TB)
and (:extra_ipAddres is null or entry.IP_ADDRESS = :extra_ipAddres)
