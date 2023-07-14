select code as code from
(
    select (d.TER_CODE || '|' || d.OSB || '|' || d.FOSB || ':' || d.DESPATCH) as code
      from <#if table_name?has_content>${table_name}<#else>DEPARTMENT_ACTIVE</#if>${linkName} d
     where  d.TER_CODE || '|' || d.OSB || '|' || d.FOSB in(:extra_codes)
)