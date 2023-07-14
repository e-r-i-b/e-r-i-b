select
    {client.*}
from
    CLIENT client
where
    client.MIGRATION_ERROR = '0'
    <#if document?has_content>
        and replace(client.DOCUMENT, ' ', '') like replace(:extra_like_document,' ','')
    </#if>
    <#if status?has_content>
        and client.STATUS = :extra_status
    </#if>
    <#if department?has_content>
        and upper(client.TER_BANK||client.OSB||client.VSP) like replace(:extra_like_department,' ','')
    </#if>
    <#if birthday?has_content>
        and client.BIRTH_DATE = :extra_birthday
    </#if>
    <#if segment?has_content>
        and (
        client.SEGMENT_1 = '1' and ('1' = :extra_segment)
        or client.SEGMENT_1_1 = '1' and ('1_1' = :extra_segment)
        or client.SEGMENT_1_2 = '1' and ('1_2' = :extra_segment)
        or client.SEGMENT_2_1 = '1' and ('2_1' = :extra_segment)
        or client.SEGMENT_2_2 = '1' and ('2_2' = :extra_segment)
        or client.SEGMENT_2_2_1 = '1' and ('2_2_1' = :extra_segment)
        or client.SEGMENT_3_1 = '1' and ('3_1' = :extra_segment)
        or client.SEGMENT_3_2_1 = '1' and ('3_2_1' = :extra_segment)
        or client.SEGMENT_3_2_2 = '1' and ('3_2_2' = :extra_segment)
        or client.SEGMENT_3_2_3 = '1' and ('3_2_3' = :extra_segment)
        or client.SEGMENT_4 = '1' and ('4' = :extra_segment)
        or client.SEGMENT_5_1 = '1' and ('5_1' = :extra_segment)
        or client.SEGMENT_5_2 = '1' and ('5_2' = :extra_segment)
        or client.SEGMENT_5_3 = '1' and ('5_3' = :extra_segment)
        or client.SEGMENT_5_4 = '1' and ('5_4' = :extra_segment)
        or client.SEGMENT_5_5 = '1' and ('5_5' = :extra_segment)
        )
    </#if>
    <#if fio?has_content>
        and upper(replace(replace(concat(concat(client.SUR_NAME, client.FIRST_NAME), client.PATR_NAME), ' ', ''), '-', '')) like upper(replace(replace(:extra_like_fio, ' ', ''), '-', ''))
    </#if>
    <#if phone?has_content>
        and :extra_phone in
        (
            select
                PHONE
            from
                PHONES phone
            where
                client.ID = phone.CLIENT_ID
        )
    </#if>
ORDER BY client.SUR_NAME, client.FIRST_NAME, client.PATR_NAME ASC
