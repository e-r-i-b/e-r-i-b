<#--
   Опорный объект: I_COUNT_AB_CONTACTS_COUNT
   Предикаты доступа: -
   Кардинальность: сотрудник вводит число клиентов (строк выборки) для отображения (с учетом пагинации)
-->
SELECT
    {reportEntry.*}
FROM
    ADDRESS_BOOK_CONTACTS_COUNT reportEntry
<#if login_id?has_content>
<#--
   Опорный объект: I_LOGIN_ID_AB_CONTACTS_COUNT
   Предикаты доступа:
      access("CONTACTSCO0_"."LOGIN_ID"=TO_NUMBER(:R))
   Кардинальность: 1
-->
WHERE
    reportEntry.LOGIN_ID = :extra_login_id
</#if>
ORDER BY reportEntry.COUNT DESC