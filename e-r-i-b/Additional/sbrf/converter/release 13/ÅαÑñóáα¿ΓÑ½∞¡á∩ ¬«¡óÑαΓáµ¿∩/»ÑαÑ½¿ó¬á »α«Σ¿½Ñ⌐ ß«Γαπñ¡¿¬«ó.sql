CREATE OR REPLACE PACKAGE extract_employees is

    procedure start_extract(
        gSchemeId in number
    );

    procedure extract_default_admin;

end extract_employees;
/
CREATE OR REPLACE PACKAGE BODY extract_employees is

    --Идентификаторы исключения
    C_COMPLITE              constant number(5):=100;    -- Перенос сущности успешно завершен
    C_NOT_VALID_LOGIN       constant number(5):=101;    -- Некорректно выбрана сущность login
    C_NOT_VALID_EMPLOYEE    constant number(5):=102;    -- Некорректно выбрана сущность employees
    C_NOT_VALID_ALLDEP      constant number(5):=103;    -- Некорректно выбрана сущность allowed_departments
    C_COMMON_ERROR          constant number(5):=104;    -- Ошибка в логике выпонения

    procedure log(message in varchar2) is
    begin
        dbms_output.put_line(message);
    end;

    procedure setNewAllowed(gLoginId number) is
    begin
        insert into ALLOWED_DEPARTMENTS(LOGIN_ID, TB, OSB, VSP)
        with temp as (
            select --отсечение office
              tb, 
              osb, 
              case
                  when osb is not null and office is null and (parent_osb_allowed=2 or (level_osb_deps_allowed=level_osb_deps and level_osb_deps>0)) then '*'
                  else office
              end as office, 
              parent_tb_allowed,
              level_tb_osbs,
              total_tbs,   
              case
                when osb is not null and parent_tb_allowed = 2 then 0
                when osb is not null and office is null and (parent_osb_allowed=2 or (level_osb_deps_allowed=level_osb_deps and level_osb_deps>0)) then 1 --осб
                when office is not null and (parent_osb_allowed!=2 and level_osb_deps_allowed!=level_osb_deps) and allowed=1 then 1 --office
                when office is null and osb is null and allowed=1 then 1 --tb
                else 0 
              end as is_show 
            from (
                select 
                    decode(ald.department_id, null, 0, 1) as allowed,  
                    tb, 
                    osb, 
                    office, 
                    (sum(decode(ald.department_id, null, 0, 1)) over (partition by tb)) as level_tb_recs_allowed,
                    (count(1) over (partition by tb, osb))-1 as level_osb_deps,    
                    (sum(decode(ald.department_id, null, 0, decode(office, null, 0,1))) over (partition by tb, osb)) as level_osb_deps_allowed,
                    (sum(decode(office, null, 1, 0)) over (partition by tb))-1 as level_tb_osbs,
                    sum(decode(osb, null, 1, 0)) over () as total_tbs,
                    max((decode(ald.department_id, null, 0, 1))+(decode(office, null, 1, 0))) over (partition by tb, osb) as parent_osb_allowed,
                    max((decode(ald.department_id, null, 0, 1))+(decode(osb, null, 1, 0))) over (partition by tb ) as parent_tb_allowed
                from DEPARTMENTS d
                left join ALLOWED_DEPARTMENT ald on ald.department_id=d.id and ald.login_id=gLoginId
                where tb is not null and tb!='0'
            ) 
            where level_tb_recs_allowed>0
        )
        select 
          gLoginId, decode(all_tb_allowed, 1, '*', tb) tb, osb, office 
        from (
          select 
            tb,
            osb, 
            office, 
            is_show, 
            case sum(decode(osb, '*', 1, 0)) over () 
              when total_tbs then 1
              else 0
            end as all_tb_allowed   
          from (
            select --отсечение осб
              tb, 
              case
                  when osb is null and office is null and (parent_tb_allowed=2 or level_tb_osbs=level_tb_osbs_allowed) then '*'          
                  else osb
              end as osb,
              case
                  when osb is null and office is null and (parent_tb_allowed=2 or level_tb_osbs=level_tb_osbs_allowed) then '*'          
                  else office
              end as office,
              total_tbs, 
              case
                when office ='*' and (parent_tb_allowed=2 or level_tb_osbs=level_tb_osbs_allowed) then 0
                else is_show
              end as is_show 
            from (
              select 
                tb, 
                osb, 
                office, 
                level_tb_osbs,
                sum(decode(office, '*', 1, 0)) over (partition by tb) level_tb_osbs_allowed,
                parent_tb_allowed,
                total_tbs, 
                is_show
              from temp     
            )
          )
        )
        where 
          is_show='1' and ((all_tb_allowed=1 and rownum =1) or (all_tb_allowed=0));
    end;

    --заведение дефолтного админа
    procedure extract_default_admin is
        pragma autonomous_transaction;
        lLastSynchronizationDate date;
    begin
        lLastSynchronizationDate:=sysdate;
        insert into LOGINS@csaadmin(ID, NAME, PASSWORD, ACCESSSCHEME_ID, NODE_EXT_ID, LAST_UPDATE_DATE, DELETED, WRONG_LOGONS, PASSWORD_EXPIRE_DATE) 
            select l.ID, l.USER_ID NAME, p.VALUE PASSWORD, so.SCHEME_ID ACCESSSCHEME_ID, 21 NODE_EXT_ID, lLastSynchronizationDate LAST_UPDATE_DATE, l.DELETED, l.WRONG_LOGONS, p.EXPIRE_DATE PASSWORD_EXPIRE_DATE
            from LOGINS l
            inner join SCHEMEOWNS so on so.LOGIN_ID=l.id
            inner join PASSWORDS p on p.LOGIN_ID=l.id
            where 
                l.user_id='admin' 
            and exists (select 1 from ACCESSSCHEMES a where a.ID=so.SCHEME_ID and a.PERSONAL='0');

        if (sql%rowcount!=1) then
            rollback;
            --return C_NOT_VALID_LOGIN; 
        end if;

        --проставляем доступ к подразделениям
        insert into ALLOWED_DEPARTMENTS@csaadmin(ID, LOGIN_ID, TB, OSB, VSP) 
            select S_ALLOWED_DEPARTMENTS.nextval@csaadmin, 1, '*', '*', '*' from dual;

        if (sql%rowcount=0) then
            rollback;
            --return C_NOT_VALID_ALLDEP;
        end if;

        update LOGINS set CSA_USER_ID=USER_ID, last_synchronization_date=lLastSynchronizationDate where ID=1;

        commit; --TODO commit
        --return C_COMPLITE;
    exception
        when OTHERS then 
        rollback;
        log(dbms_utility.format_error_stack);
        log(dbms_utility.format_error_backtrace);
        --return C_COMMON_ERROR; --Ошибка в процессе выполнения, конвертация прервана
    end;

    procedure initial_export is
    begin
        --актуализируем схемы прав
        insert into ACCESSSCHEMES@CSAADMIN(ID, NAME, TYPE, FOR_CA, FOR_VSP )
        select id, NAME, CATEGORY as TYPE, CA_ADMIN_SCHEME as FOR_CA, VSP_EMPLOYEE_SCHEME as FOR_VSP 
        from ACCESSSCHEMES 
        where CATEGORY in ('A', 'E', 'S') and PERSONAL!='1'
        minus 
        select ID, NAME, TYPE, FOR_CA, FOR_VSP from ACCESSSCHEMES@CSAADMIN;   

        update ACCESSSCHEMES set EXTERNAL_ID=ID where CATEGORY in ('A', 'E', 'S') and EXTERNAL_ID is null;

        commit;
    end;

    --перенос данных по сотруднику
    function extract_employee_data(gLoginId in number, gAlltb in number) return number is
        pragma autonomous_transaction;
        lLastSynchronizationDate date;
    begin
        --переносим профиль
        lLastSynchronizationDate:=sysdate;

        insert into LOGINS@csaadmin(ID, NAME, PASSWORD, ACCESSSCHEME_ID, NODE_EXT_ID, LAST_UPDATE_DATE, DELETED, WRONG_LOGONS, PASSWORD_EXPIRE_DATE) 
            select l.ID, l.USER_ID NAME, p.VALUE PASSWORD, so.SCHEME_ID ACCESSSCHEME_ID, 21 NODE_EXT_ID, lLastSynchronizationDate, l.DELETED, l.WRONG_LOGONS, p.EXPIRE_DATE PASSWORD_EXPIRE_DATE
            from LOGINS l
            inner join SCHEMEOWNS so on so.LOGIN_ID=l.id
            inner join PASSWORDS p on p.LOGIN_ID=l.id
            where 
                l.id=gLoginId
            and exists (select 1 from ACCESSSCHEMES a where a.ID=so.SCHEME_ID and a.PERSONAL='0') and (csa_user_id is null or csa_user_id!=user_id);

        if (sql%rowcount!=1) then
            rollback;
            return C_NOT_VALID_LOGIN; 
        end if;

        insert into EMPLOYEES@csaadmin(ID, LOGIN_ID, FIRST_NAME, SUR_NAME, PATR_NAME, INFO, E_MAIL, MOBILE_PHONE, CA_ADMIN, VSP_EMPLOYEE, MANAGER_ID, MANAGER_PHONE, MANAGER_EMAIL, MANAGER_LEAD_EMAIL, MANAGER_CHANNEL, TB, OSB, VSP) 
            select 
                S_EMPLOYEES.nextval@csaadmin, e.LOGIN_ID, e.FIRST_NAME, e.SUR_NAME, e.PATR_NAME, e.INFO, e.E_MAIL, e.MOBILE_PHONE, 
                e.CA_ADMIN, e.VSP_EMPLOYEE, 
                e.MANAGER_ID, e.MANAGER_PHONE, e.MANAGER_EMAIL, e.MANAGER_LEAD_EMAIL, 
                decode(CHANNEL_ID, null, null, (select NAME from PFP_CHANNELS where ID=e.CHANNEL_ID)) as MANAGER_CHANNEL, 
                d.TB, d.OSB, d.OFFICE as VSP
            from EMPLOYEES e
            inner join DEPARTMENTS d on d.id=e.department_id
            where e.login_id=gLoginId;

        if (sql%rowcount!=1) then
            rollback;
            return C_NOT_VALID_EMPLOYEE; 
        end if;

        --проставляем доступ к подразделениям
        if gAlltb=1 then
            insert into ALLOWED_DEPARTMENTS(LOGIN_ID, TB, OSB, VSP) values (gLoginId, '*', '*', '*');
        else
            setNewAllowed(gLoginId);
        end if;

        --проставляем доступ к подразделениям
        insert into ALLOWED_DEPARTMENTS@csaadmin(ID, LOGIN_ID, TB, OSB, VSP) 
            select S_ALLOWED_DEPARTMENTS.nextval@csaadmin, LOGIN_ID, TB, OSB, VSP from ALLOWED_DEPARTMENTS where LOGIN_ID=gLoginId;

        if (sql%rowcount=0) then
            rollback;
            return C_NOT_VALID_ALLDEP; 
        end if;

        update LOGINS set CSA_USER_ID=USER_ID, last_synchronization_date=lLastSynchronizationDate where ID=gLoginId;

        commit; --TODO commit
        return C_COMPLITE;
    exception
        when OTHERS then 
        rollback;        
        log(dbms_utility.format_error_stack);
        log(dbms_utility.format_error_backtrace);
        return C_COMMON_ERROR; --Ошибка в процессе выполнения, конвертация прервана
    end;

    --запуск переноса данных
    procedure start_extract(gSchemeId in number) is
        type EmployeesListType is table of number;

        gEmployeesList           EmployeesListType;
        lResult                  number;
        gEmployeesProcessed      number:=0;
        gEmployeesAdded          number:=0;
        gAlltb                   number(1);

    begin
        dbms_output.enable(500000);
	
        log('старт: ['||systimestamp||']');

        initial_export;

        select count(1) into gAlltb from dual where exists(select 1 from SCHEMESSERVICES where SCHEME_ID=gSchemeId and SERVICE_ID in (select ID from SERVICES where SERVICE_KEY='AllTBAccess'));

        select e.login_id bulk collect into gEmployeesList 
        from EMPLOYEES e 
        inner join SCHEMEOWNS s on s.LOGIN_ID=e.LOGIN_ID
            where SCHEME_ID=gSchemeId;

        for rec in gEmployeesList.first..gEmployeesList.last 
        loop
            gEmployeesProcessed:=gEmployeesProcessed+1;

            lResult:=extract_employee_data(gEmployeesList(rec), gAllTB);

            if (lResult=C_COMPLITE) then 
                gEmployeesAdded:=gEmployeesAdded+1; 
            else
                log('Ошибка при экспорте профиля: '||gEmployeesList(rec)||' результат:'||lResult);
            end if;
        end loop;
        log('стоп:  ['||systimestamp||'], обработано профилей ['||gEmployeesProcessed||'], добавлено профилей ['||gEmployeesAdded||']');
    end;

end extract_employees;
/
