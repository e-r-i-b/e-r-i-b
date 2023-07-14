<#if loginId?has_content>
<#--
   ������� ������: I_REQ_CARD_BY_PHONE_LOG_LOGIN
   ��������� �������:
      access("LOGENTRY"."BLOCK_ID"=TO_NUMBER(:EXTRA_BLOCKID) AND "LOGENTRY"."LOGIN_ID"=TO_NUMBER(:EXTRA_LOGINID) AND
      "LOGENTRY"."EVENT_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE) AND "LOGENTRY"."EVENT_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
   ��������������: �� ����� ����� ������
-->
SELECT
logEntry.LOGIN_ID LOGIN_ID,
logEntry.FIO FIO,
logEntry.DOC_TYPE DOC_TYPE,
logEntry.DOC_NUMBER DOC_NUMBER,
logEntry.BIRTHDAY BIRTHDAY
FROM
REQUEST_CARD_BY_PHONE_LOG logEntry
WHERE
logEntry.EVENT_DATE >= :extra_fromDate
AND logEntry.EVENT_DATE <= :extra_toDate
AND logEntry.BLOCK_ID = :extra_blockId
AND logEntry.LOGIN_ID = :extra_loginId
AND rownum = 1
<#else>
<#--
   ������� ������: I_REQUEST_CARD_BY_PHONE_LOG
   ��������� �������: ���� ���������� ������ � ����� �����
      access("LOGENTRY"."BLOCK_ID"=TO_NUMBER(:EXTRA_BLOCKID) AND "LOGENTRY"."EVENT_DATE">=TO_TIMESTAMP(:EXTRA_FROMDATE)
      AND "LOGENTRY"."EVENT_DATE"<=TO_TIMESTAMP(:EXTRA_TODATE))
   ��������������: ���������� ��������, ����������� ����� �������� �� ������ �������� �� ������
-->
SELECT
logEntry.LOGIN_ID LOGIN_ID,
min(logEntry.FIO) FIO,
min(logEntry.DOC_TYPe) DOC_TYPE,
min(logEntry.DOC_NUMBER) DOC_NUMBER,
min(logEntry.BIRTHDAY) BIRTHDAY
FROM
REQUEST_CARD_BY_PHONE_LOG logEntry
WHERE
logEntry.EVENT_DATE >= :extra_fromDate
AND logEntry.EVENT_DATE <= :extra_toDate
AND logEntry.BLOCK_ID = :extra_blockId
GROUP BY  logEntry.LOGIN_ID
</#if>