<#--
    ������� �������: I_KINDSTATE
    ��������� �������: access("DOC"."STATE_CODE"=:EXTRA_STATE)
    ��������������: 20 (���� ������� - ������������ ���������� ������� ��� ��������� ������)
-->
select doc.ID document_id
from BUSINESS_DOCUMENTS doc
<#if nextProcessDate?has_content || needESBPaymentOnly?has_content>
<#--
    ������� �������: DOC_ID_JOB_NAME_PER_IDX
    ��������� �������: access("DOC"."ID"="EXECREC"."DOCUMENT_ID"(+) AND "EXECREC"."JOB_NAME"(+)=:EXTRA_JOBNAME)
    ��������������: 20 (���� ������� - ������������ ���������� ������� ��� ��������� ������)
-->
	left join PAYMENT_EXECUTION_RECORDS execRec on doc.id = execRec.DOCUMENT_ID and execRec.JOB_NAME = :extra_jobName
</#if>
where doc.STATE_CODE = :extra_state
    and not exists(
        select processedDocument.DOCUMENT_ID
        from PROCESSED_DOCUMENT_ID processedDocument
        where (processedDocument.JOB_NAME = :extra_jobName or processedDocument.JOB_NAME = concat(:extra_jobName, '.unexpected.error')) and
            doc.ID = processedDocument.DOCUMENT_ID
        )
<#if nextProcessDate?has_content>
	and (execRec.NEXT_PROCESS_DATE is null or execRec.NEXT_PROCESS_DATE < :extra_nextProcessDate)
</#if>
<#if fromDate?has_content>
    and doc.OPERATION_DATE < :extra_fromDate
</#if>
<#if excludingProviders?has_content>
    and doc.RECIPIENT_ID not in (:extra_excludingProviders)
</#if>
<#if iqwUuid?has_content>
    and doc.EXTERNAL_ID like concat('%', :extra_iqwUuid)
</#if>
<#if needESBPaymentOnly?has_content>
<#--
    ������� �������: I_KINDSTATE
    ��������� �������: access("DOC"."STATE_CODE"=:EXTRA_STATE AND "DOC"."KIND"='P')
    ��������������: 20 (���� ������� - ������������ ���������� ������� ��� ��������� ������)
-->
    and (doc.PROVIDER_EXTERNAL_ID is null or doc.PROVIDER_EXTERNAL_ID not like '%|' || :extra_iqwaveAdapter)  --�� iqwave �������
	and	(doc.KIND = 'P' or doc.KIND = 'ER') --������� JurPayment ��� EarlyLoanRepaymentClaimImpl
    and doc.IS_LONG_OFFER = '0'
    and
        SYSDATE - :extra_expireHour/24 < doc.OPERATION_DATE and --�������� ������ ������������ ���������
        (execRec.COUNTER is null or execRec.COUNTER < :extra_numberOfResendPayment) and --���������� �������� �������� n ���
        exists (
            select def.ID from DOCUMENT_EXTENDED_FIELDS def
            where doc.ID = def.PAYMENT_ID and def.NAME = 'from-resource-type' and def.VALUE = 'com.rssl.phizic.business.resources.external.CardLink'
        ) --������ � �����
</#if>
<#if orderType == "ASC">
    order by doc.id ASC
</#if>
<#if orderType == "DESC">
    order by doc.id desc
</#if>
<#if orderType == "RANDOM">
    order by dbms_random.value
</#if>