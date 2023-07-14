create or replace package Migration is

  function StartClientMigration(clientId in number) 
    return integer;
       --     -1          ��� ������� � ��������� ���������������
       --     0           ���� �������� ������� ���������� � mig_START, ����� ������
       --     1           �������� ��� ������
       --     2           �������� ��� ��������� �������
       --     3           � ������� ���� �������� ������
       --     4           ������ ��� ��������� MIG_STATE � MIG_TIME
       --     5           �������� ��������, � �������� �������� �������� ������

  function EndClientMigration(clientId in number, state in varchar2)
    return integer;
       --       -2         ������� ������ ��� ����������. ������ ���� mig_OK ��� mig_FAIL
       --       -1         ��� ������� � ��������� ���������������
       --       0          ��������� � ����� ����� �������� ������� �����������
       --       1          ������ �� � ��������� "�������� ������"
       --       2          �� ������� �������� ��������� � ����� ����� ��������

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
            return -1; --��� ������� � ��������� ���������������
        end if;
        -- ��������� �������� �� ������ � ������� (���� � ���� ���������� ������� �� ��������� 20 ���, ���� ������ ���, �������, ��� �� ��������)
        select count(LOGIN_ID) into cc from USERLOG where APPLICATION = 'PhizIC' and  START_DATE > ( sysdate-0.014 ) and LOGIN_ID = clientId ; 
        if ( cc != 0 ) then
            return 3; --� ������� ���� �������� ������        
        end if;

        begin
            select REASON_DESCRIPTION into old_state from LOGIN_BLOCK where LOGIN_ID = clientId and REASON_TYPE='system' and REASON_DESCRIPTION is not null;
        exception
            when no_data_found then old_state:=''; --��� ����������, �.�. �� �� �������� ������������ �������
        end;

        case old_state
            when mig_START then return 1; --�������� ��� ������
            when mig_OK then return 2;    --�������� ��� ��������� �������
            when mig_FAIL then return 5;  --�������� ��������, � �������� �������� �������� ������
            else null;
        end case;

        -- ��������� ���������� ��� ������ ������� � ���� ���. �����
        insert into LOGIN_BLOCK values(S_LOGIN_BLOCK.NEXTVAL, clientId, 'system', mig_START, sysdate, null, null); 

    exception
        when others then
            return 4; --������ ��� ��������� MIG_STATE
    end;
    commit;
    return 0; -- ���� �������� ������� ���������� � mig_START
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
    --������������� ���� � ����� ��������
    begin
        if state not in (mig_OK, mig_FAIL) then 
            return -2; --������� ������ ��� ����������
        end if;

        begin
            select ID, REASON_DESCRIPTION into loginId, old_state from LOGIN_BLOCK where LOGIN_ID = clientId and REASON_TYPE='system' and REASON_DESCRIPTION = mig_START;
        exception
            when no_data_found then return -1; --��� ������� � ��������� ��������������� � ��������� "�������� ������"
        end;

        if old_state is NULL or old_state <> mig_START then
            return 1; --������ �� � ��������� "�������� ������"
        end if;
        if ( state = mig_OK ) then
            update LOGIN_BLOCK LB set LB.REASON_DESCRIPTION = state, LB.DATE_UNTIL = sysdate where LB.ID = loginId;
        else
            update LOGIN_BLOCK LB set LB.REASON_DESCRIPTION = state where LB.ID = loginId;
        end if;
    exception
        when others then return 2; --�� ������� �������� ��������� � ����� ����� ��������
    end;
    commit;
    return 0; --��������� � ����� ����� �������� ������� �����������
end EndClientMigration;

end Migration;