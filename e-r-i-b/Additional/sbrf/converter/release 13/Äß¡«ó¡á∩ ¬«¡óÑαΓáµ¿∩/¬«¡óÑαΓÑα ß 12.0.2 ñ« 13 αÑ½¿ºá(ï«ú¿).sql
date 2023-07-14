--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

-- Номер ревизии: 56876
-- Комментарий: Переделать привязку логов к департаментам
alter table CODLOG add(
    TB varchar2(4),
    OSB varchar2(4),
    VSP varchar2(7)
)
/
alter table SYSTEMLOG add(
    TB varchar2(4),
    OSB varchar2(4),
    VSP varchar2(7)
)
/
alter table USERLOG add(
    TB varchar2(4),
    OSB varchar2(4),
    VSP varchar2(7)
)
/

-- Номер ревизии: 57150
-- Комментарий: Переделать привязку логов к департаментам
alter table SYSTEMLOG set unused ( DEPARTMENT_ID )
/
alter table USERLOG set unused ( DEPARTMENT_ID )
/
alter table CODLOG set unused ( DEPARTMENT_ID )
/

-- Номер ревизии: 57150
-- Комментарий: Переделать привязку логов к департаментам
alter table CSA_SYSTEMLOG set unused ( DEPARTMENT_ID )
/