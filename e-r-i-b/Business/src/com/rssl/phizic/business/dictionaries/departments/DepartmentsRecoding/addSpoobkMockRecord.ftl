insert into <#if table_name?has_content>${table_name}<#else>DEPARTMENT_ACTIVE</#if> (ID, TER_CODE, OSB, FOSB, DESPATCH, DATE_SUC)
values(S_<#if table_name?has_content>${table_name}<#else>DEPARTMENT_ACTIVE</#if>.nextval, :extra_terbank, :extra_branch, :extra_subbranch, :extra_despatch, :extra_date_suc)
