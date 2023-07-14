SELECT
    department.TB as TB,
    pfp.CREATION_DATE as creation_date,
    pfp.EXECUTION_DATE as execution_date,
    person.BIRTHDAY as person_birthday,
    pfp.EMPLOYEE_FIO as employee_fio,
    pfp.STATE_CODE as state_code,
    pfp.ID as pfp_id,
    person.SUR_NAME as user_sur_name,
    person.FIRST_NAME as user_first_name,
    person.PATR_NAME as user_patr_name,
    concat((case when (pfp.STATE_CODE='COMPLITE' or pfp.STATE_CODE='COMPLITE_OLD') then 'Выполнено' else 'Прервано' end), concat(': ', pfp.STATE_DESCRIPTION)) as state_descr,
    risk_profile.NAME as risk_profile_name,
    document.DOC_SERIES as document_series,
    document.DOC_NUMBER as document_number,
    document.DOC_TYPE as document_type,
    document.DOC_NAME as document_name,
    count(target.PERSON_FINANCE_PROFILE_ID) as targets,
    pfp.MANAGER_ID as manager_id,
    (nvl(pfp.SBRF_ACCOUNTS_AMOUNT, 0) + nvl(pfp.OTHER_BANKS_ACCOUNTS_AMOUNT, 0)
        + nvl(pfp.CASH_AMOUNT, 0) + nvl(pfp.INVESTMENTS_FUNDS_AMOUNT, 0)
        + nvl(pfp.INVESTMENTS_IMA_AMOUNT, 0) + nvl(pfp.INVESTMENTS_OTHER_AMOUNT, 0)) as virtual_balance,
    nvl(pfp.OTHER_BANKS_ACCOUNTS_AMOUNT, 0) as balance_other_banks,
    nvl(pfp.CASH_AMOUNT, 0) as balance_cash,
    nvl(nvl(pfp.INVESTMENTS_FUNDS_AMOUNT, 0) + nvl(pfp.INVESTMENTS_IMA_AMOUNT, 0)
        + nvl(pfp.INVESTMENTS_OTHER_AMOUNT, 0),0) as total_investments,
    pfp.MANAGER_OSB as manager_osb,
    pfp.MANAGER_VSP as manager_vsp,
    pfp.CREDIT_CARD_TYPE as user_credit_card_type,
    channel.NAME as channel_name,
    personSubData.EMAIL_ADDRESS as user_e_mail,
    personSubData.MOBILE_PHONE as user_mobile_phone
FROM
    PERSONAL_FINANCE_PROFILE pfp
    JOIN USERS person on pfp.LOGIN_ID = person.LOGIN_ID and person.STATUS <> 'D'
    JOIN DEPARTMENTS department on person.DEPARTMENT_ID = department.ID
    LEFT JOIN PFP_RISK_PROFILES risk_profile on pfp.RISK_PROFILE_ID = risk_profile.id
    LEFT JOIN PERSON_TARGET target on pfp.ID = target.PERSON_FINANCE_PROFILE_ID
    LEFT JOIN PFP_CHANNELS channel ON pfp.CHANNEL_ID = channel.ID
	LEFT JOIN PERSONAL_SUBSCRIPTION_DATA personSubData ON person.LOGIN_ID = personSubData.LOGIN_ID
    LEFT JOIN DOCUMENTS document on  person.ID = document.PERSON_ID,
    (select d.PERSON_ID p_id, max(DOC_IDENTIFY + (case when DOC_TYPE='REGULAR_PASSPORT_RF' then 10 else 0 end)) res from DOCUMENTS d group by d.person_id) docs
WHERE
    (docs.p_id=person.ID and (document.DOC_IDENTIFY + (case when document.DOC_TYPE='REGULAR_PASSPORT_RF' then 10 else 0 end)) = docs.res)
    <#if !allTbAccess>
    AND exists(SELECT 1 FROM ALLOWED_DEPARTMENTS ad
                         WHERE ad.LOGIN_ID = :extra_employeeLoginId
                         AND ad.TB||'|'||ad.OSB||'|'||ad.VSP in (department.TB||'|*|*',
                                                                 department.TB||'|'||department.OSB||'|*',
                                                                 department.TB||'|'||department.OSB||'|'||department.OFFICE,
                                                                 '*|*|*')
                  )
    </#if>
    <#if ids?has_content>
        AND pfp.ID in (:extra_ids)
    <#else>
        <#if state?has_content  && state == "INITIAL">
            AND (pfp.STATE_CODE not in ('COMPLITE','COMPLITE_OLD'))
        <#elseif state?has_content && state == "COMPLITE">
            AND (pfp.STATE_CODE in ('COMPLITE','COMPLITE_OLD'))
        </#if>
        <#if user_fio?has_content>
            AND (upper(replace(replace(concat(concat(person.SUR_NAME, person.FIRST_NAME), person.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_user_fio, ' ', ''), '-', ''), '%')))
        </#if>
        <#if employee_fio?has_content>
            AND (upper(replace(replace(pfp.EMPLOYEE_FIO, ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_employee_fio, ' ', ''), '-', ''), '%')))
        </#if>
        <#if fromDate?has_content>
            AND (pfp.CREATION_DATE >= :extra_fromDate)
        </#if>
        <#if toDate?has_content>
            AND (pfp.CREATION_DATE < :extra_toDate)
        </#if>
        <#if channel_id?has_content>
            AND (pfp.CHANNEL_ID = :extra_channel_id)
        </#if>
        <#if risk_profile?has_content>
            AND (pfp.RISK_PROFILE_ID = :extra_risk_profile)
        </#if>
        <#if documentSeries?has_content || documentNumber?has_content>
            AND person.ID in (select PERSON_ID
                              from DOCUMENTS document
                              where
                                    <#if documentSeries?has_content>
                                        (replace(document.DOC_SERIES,' ') like concat(:extra_documentSeries, '%'))
                                    </#if>
                                    <#if documentSeries?has_content && documentNumber?has_content>
                                        AND
                                    </#if>
                                    <#if documentNumber?has_content>
                                        (document.DOC_NUMBER like concat(:extra_documentNumber, '%'))
                                    </#if>
                              )
        </#if>
        <#if birthday?has_content>
            AND (person.BIRTHDAY = :extra_birthday)
        </#if>
        <#if manager_id?has_content>
            AND (pfp.MANAGER_ID = :extra_manager_id)
        </#if>
    </#if>
group by pfp.ID, CREATION_DATE, TB, EXECUTION_DATE, person.SUR_NAME, person.FIRST_NAME, person.PATR_NAME,
         person.BIRTHDAY, pfp.EMPLOYEE_FIO,  pfp.STATE_CODE, pfp.STATE_DESCRIPTION, risk_profile.NAME,
         document.DOC_SERIES, document.DOC_NUMBER, document.DOC_TYPE, document.DOC_NAME,  person.ID,
         pfp.MANAGER_ID, pfp.SBRF_ACCOUNTS_AMOUNT, pfp.OTHER_BANKS_ACCOUNTS_AMOUNT, 
         pfp.CASH_AMOUNT, pfp.INVESTMENTS_FUNDS_AMOUNT, pfp.INVESTMENTS_IMA_AMOUNT, pfp.INVESTMENTS_OTHER_AMOUNT, pfp.CREDIT_CARD_TYPE,
         personSubData.EMAIL_ADDRESS, personSubData.MOBILE_PHONE,pfp.MANAGER_VSP,pfp.MANAGER_OSB,channel.NAME
ORDER BY pfp.CREATION_DATE DESC