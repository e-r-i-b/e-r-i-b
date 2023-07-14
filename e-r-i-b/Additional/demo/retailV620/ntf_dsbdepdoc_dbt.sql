-- ��������-������ ��� ���. ���.
-- 20/02/2008 ������ ��
-- ������� ��� ���������� �� ��������� ������� � ��������� ������� ������������
-- ������� 
-- ����������� �������� ������ � �����������
CREATE OR REPLACE TRIGGER ntf_dsbdepdoc_dbt
before insert on dsbdepdoc_dbt
for each row
declare
        -- ��� �������
        objecttype ntfobj_dbt.objecttype%type;
        -- ������
        objectid ntfobj_dbt.objectid%type;
        -- ������� ��
        oldrest ntfntfy_dbt.oldrest%type;
        -- �����
        summ ntfntfy_dbt.summa%type;

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
        begin
        objecttype:=202;
        objectid:=concat('DA',:new.t_account);
        if (getObject(:new.t_iscur,:new.t_fncash,objecttype,objectid)) then
            if (:new.t_insum=0) then
              summ:=:new.t_outsum;
            else
              summ:=:new.t_insum;
            end if;
            if (:new.t_rest>0) then
              if (:new.t_insum=0) then
                oldrest:=:new.t_rest+summ;
              else
                oldrest:=:new.t_rest-summ;
              end if;
            end if;

            insert into ntfntfy_dbt
                 (iscur,        branch,         objecttype,     objectid,       
                  notifytype,   notifydate,     notifytime,     notifyoper,
                  oldrest,      newrest,        iapplicationkind,applicationkey,
                  summa)
            values
                 (:new.t_iscur, :new.t_fncash,  objecttype,     objectid,
                  1,            sysdate,        sysdate,        :new.t_oper,
                  oldrest,      :new.t_rest,    :new.t_iapplicationkind,:new.t_applicationkey,
                  summ);

            -- ���������� � �������� ������ ��� ���������� �� ����
            --if (bitand(:new.t_flags2,1)<>0) then
            if (bitand(:new.t_flags2,32)<>0) then
              insert into ntfntfy_dbt
                 (iscur,        branch,         objecttype,     objectid,       
                  notifytype,   notifydate,     notifytime,     notifyoper,
                  oldrest,      newrest,        iapplicationkind,applicationkey,
                  summa,        status)
              values
                 (:new.t_iscur, :new.t_fncash,  objecttype,     objectid,
                  6,            sysdate,        sysdate,        :new.t_oper,
                  oldrest,      :new.t_rest,    :new.t_iapplicationkind,:new.t_applicationkey,
                  summ,         'C');
            end if;
        end if;  
end ntf_dsbdepdoc_dbt;
/
