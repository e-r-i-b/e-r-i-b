-- ��������-������ ��� ���. ���.
-- 20/02/2008 ������ ��
-- ������� ��� ���������� �� ��������� ������� �������
-- ����������� �������� ������ �� ����������
-- ���� ��� ���� ���� ������ � �����������, ������ ���� �������� ���->����
-- ���� ������ � ����������� ���, ������ �������� ������� �� ����������
create or replace trigger ntf_dpsdepdoc_dbt
before delete on dpsdepdoc_dbt
for each row
declare 
        -- ��� �������
        objecttype ntfobj_dbt.objecttype%type;
        -- ������
        objectid ntfobj_dbt.objectid%type;
        -- �����
        newsum ntfntfy_dbt.summa%type;

        -- ������� ������ ������� (�������� �� ������ �� ����������)
        -- true - ������ �������� �� ����������
        -- false - ������ �� �������� �� ����������
        function getObject(p_iscur in ntfobj_dbt.iscur%type,
                           p_branch in ntfobj_dbt.branch%type,
                           p_objecttype in ntfobj_dbt.objecttype%type,
                           p_objectid in ntfobj_dbt.objectid%type)
            return boolean is
            ret boolean;
            num integer;
        begin
            select count(*) into num from ntfobj_dbt where
                    iscur=p_iscur and branch=p_branch and objecttype=p_objecttype and objectid=p_objectid;
            if (num=0) then
              ret:=false;
            else
              ret:=true;
            end if;
            return ret;    
        end getObject;
        
        -- ������� ������ ��������� � �����������, ����� �� appkind/appkey
        -- � �������� appkind � ����������� ���� 1, �.�. ��� ������ ��� ������
        -- ��� �������� �� ���������� �� �������
        function getDepdoc( /*p_appkind in dsbdepdoc_dbt.t_iapplicationkind%type,*/ 
                            p_appkey in dsbdepdoc_dbt.t_applicationkey%type)
            return boolean is
            ret boolean;
            num integer;
        begin
            select count(*) into num from dsbdepdoc_dbt 
            where t_iapplicationkind=1/*p_appkind*/ and t_applicationkey=p_appkey;
            if (num=0) then
              ret:=false;
            else
              ret:=true;
            end if;
            return ret;
        end getDepdoc;
begin
        objecttype:=202;
        objectid:=concat('DA',:old.t_account);
        if (getObject(:old.t_iscur,:old.t_fncash,objecttype,objectid)) then
            if (:old.t_insum=0) then
              newsum:=:old.t_outsum;
            else
              newsum:=:old.t_insum;
            end if;
            if (getDepdoc(/*:old.t_iapplicationkind,*/:old.t_applicationkey)=false) then
               -- ���������� � �������� ������ ��� ���������� �� ����
              if (bitand(:old.t_flags2,1)<>0) then
                 insert into ntfntfy_dbt
                    (iscur,        branch,         objecttype,     objectid,       
                     notifytype,   notifydate,     notifytime,     notifyoper,
                     oldrest,      newrest,        iapplicationkind,applicationkey,
                     summa,        status)
                 values
                    (:old.t_iscur, :old.t_fncash,  objecttype,     objectid,
                     5,            sysdate,        sysdate,        :old.t_oper,
                     :old.t_rest,  :old.t_rest,    :old.t_iapplicationkind,:old.t_applicationkey,
                    newsum,       'D');
              end if;
            end if;
        end if;
end ntf_dpsdepdoc_dbt;
/