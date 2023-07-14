SELECT logentry.*
        FROM   CSA_SYSTEMLOG logentry
		WHERE
        <#if logUID?has_content>
            logentry.LOG_UID = :extra_logUID
        <#else>
             (logentry.START_DATE >= :extra_fromDate )
			  AND (logentry.START_DATE <= :extra_toDate   )
              <#if application?has_content>
                AND logentry.APPLICATION in (:extra_application)
              </#if>
              <#if source?has_content>
                 AND logentry.MESSAGE_SOURCE = :extra_source
              </#if>
              <#if messageType?has_content>
                 AND logentry.MSG_LEVEL = :extra_messageType
              </#if>
              <#if fio?has_content>
              AND (
                    upper(replace(replace(concat(concat(logentry.SUR_NAME, logentry.FIRST_NAME), logentry.PATR_NAME), ' ', ''), '-', '')) like upper(concat(replace(replace(:extra_fio, ' ', ''), '-', ''), '%'))
                   )
              </#if>
              <#if number?has_content>
                and replace(logentry.DOC_NUMBER,' ','') = :extra_number
              </#if>
              <#if birthday?has_content>
                and logentry.BIRTHDAY= :extra_birthday
              </#if>
              <#if messageWord?has_content>
	            AND (upper(logentry.MESSAGE) like upper(:extra_like_messageWord))
              </#if>
              <#if ipAddres?has_content>
	            AND (logentry.IP_ADDRESS=:extra_ipAddres  )
              </#if>
              <#if errorId?has_content>
	            AND (logentry.ID = :extra_errorId)
              </#if>
              <#if login?has_content>
	            AND logentry.LOGIN = :extra_login
              </#if>
              <#if departmentCode?has_content>
                  AND logentry.DEPARTMENT_CODE = :extra_departmentCode
              </#if>
        </#if>
	    order by logentry.start_date desc