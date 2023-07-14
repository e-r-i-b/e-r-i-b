<#--
   ������� ������: I_COUNT_AB_CONTACTS_COUNT
   ��������� �������: -
   ��������������: ��������� ������ ����� �������� (����� �������) ��� ����������� (� ������ ���������)
-->
SELECT
    {reportEntry.*}
FROM
    ADDRESS_BOOK_CONTACTS_COUNT reportEntry
<#if login_id?has_content>
<#--
   ������� ������: I_LOGIN_ID_AB_CONTACTS_COUNT
   ��������� �������:
      access("CONTACTSCO0_"."LOGIN_ID"=TO_NUMBER(:R))
   ��������������: 1
-->
WHERE
    reportEntry.LOGIN_ID = :extra_login_id
</#if>
ORDER BY reportEntry.COUNT DESC