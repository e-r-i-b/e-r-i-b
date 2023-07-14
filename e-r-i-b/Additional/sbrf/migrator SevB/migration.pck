create or replace package Migration is

  function StartClientMigration(clientId in number) 
    return integer;
       --     -1          нет клиента с указанным идентификатором
       --     0           флаг миграции успешно установлен в mig_START, время начала
       --     1           миграция уже начата
       --     2           миграция уже проведена успешно
       --     3           у клиента есть активная сессия
       --     4           ошибка при установке MIG_STATE и MIG_TIME
       --     5           миграция отменена, в процессе миграции возникли ошибки

  function EndClientMigration(clientId in number, state in varchar2)
    return integer;
       --       -2         неверно указан код результата. Должно быть mig_OK или mig_FAIL
       --       -1         нет клиента с указанным идентификатором
       --       0          состояние и время конца миграции успешно установлены
       --       1          клиент не в состоянии "миграция начата"
       --       2          не удалось обновить состояние и время конца миграции

  mig_START     constant char(1) := '1';
  mig_OK        constant char(3) := '100';
  mig_FAIL      constant char(2) := '-1';

end Migration;
/
CREATE OR REPLACE PACKAGE BODY Migration is

function StartClientMigration
(
       clientId in number
) 
       return integer
is
       old_state LOGIN_BLOCK.REASON_DESCRIPTION%TYPE;
       cc number;
begin
    begin
        select count(ID) into cc from LOGINS where ID = clientId;
        if (cc = '0') then
            return -1; --нет клиента с указанным идентификатором
        end if;
        -- проверяем работает ли клиент в системе (ищем в логе активность клиента за последние 20 мин, если ничего нет, считаем, что не работает)
        select count(LOGIN_ID) into cc from USERLOG where APPLICATION = 'PhizIC' and  START_DATE > ( sysdate-0.014 ) and LOGIN_ID = clientId ; 
        if ( cc != 0 ) then
            return 3; --у клиента есть активная сессия        
        end if;

        begin
            select REASON_DESCRIPTION into old_state from LOGIN_BLOCK where LOGIN_ID = clientId and REASON_TYPE='system' and REASON_DESCRIPTION is not null;
        exception
            when no_data_found then old_state:=''; --нет блокировок, т.е. мы не пытались смигрировать клиента
        end;

        case old_state
            when mig_START then return 1; --миграция уже начата
            when mig_OK then return 2;    --миграция уже проведена успешно
            when mig_FAIL then return 5;  --миграция отменена, в процессе миграции возникли ошибки
            else null;
        end case;

        -- добавляем блокировку для логина клиента в базу Сев. Банка
        insert into LOGIN_BLOCK values(S_LOGIN_BLOCK.NEXTVAL, clientId, 'system', mig_START, sysdate, null, null); 

    exception
        when others then
            return 4; --ошибка при установке MIG_STATE
    end;
    commit;
    return 0; -- флаг миграции успешно установлен в mig_START
end StartClientMigration;

function EndClientMigration
(
       clientId in number, 
       state in varchar2
)
       return integer
is
    old_state LOGIN_BLOCK.REASON_DESCRIPTION%TYPE;
    loginId number;
begin
    --устанавливаем флаг и время миграции
    begin
        if state not in (mig_OK, mig_FAIL) then 
            return -2; --неверно указан код результата
        end if;

        begin
            select ID, REASON_DESCRIPTION into loginId, old_state from LOGIN_BLOCK where LOGIN_ID = clientId and REASON_TYPE='system' and REASON_DESCRIPTION = mig_START;
        exception
            when no_data_found then return -1; --нет клиента с указанным идентификатором в состоянии "миграция начата"
        end;

        if old_state is NULL or old_state <> mig_START then
            return 1; --клиент не в состоянии "миграция начата"
        end if;
        if ( state = mig_OK ) then
            update LOGIN_BLOCK LB set LB.REASON_DESCRIPTION = state, LB.DATE_UNTIL = sysdate where LB.ID = loginId;
        else
            update LOGIN_BLOCK LB set LB.REASON_DESCRIPTION = state where LB.ID = loginId;
        end if;
    exception
        when others then return 2; --не удалось обновить состояние и время конца миграции
    end;
    commit;
    return 0; --состояние и время конца миграции успешно установлены
end EndClientMigration;

end Migration;