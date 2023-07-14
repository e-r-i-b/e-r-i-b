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

        begin
        -- ���������� � �������� ������ ��� ���������� �� ����
        if (bitand(:new.t_flags2,4)<>0) then
          objecttype:=202;
          objectid:=concat('DA',:new.t_account);
     
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
              summa,        status)
          values
             (:new.t_iscur, :new.t_fncash,  objecttype,     objectid,
              5,            sysdate,        sysdate,        :new.t_oper,
              oldrest,      :new.t_rest,    :new.t_iapplicationkind,:new.t_applicationkey,
              summ,         'C');
        end if;

          insert into ntfntfy2_dbt
             (iscur,        branch,         objecttype,     objectid,       
              notifytype,   notifydate,     notifytime,     notifyoper,
              oldrest,      newrest,        iapplicationkind,applicationkey,
              summa,        status)
          values
             (:new.t_iscur, :new.t_fncash,  objecttype,     objectid,
              5,            sysdate,        sysdate,        :new.t_oper,
              oldrest,      :new.t_rest,    :new.t_iapplicationkind,:new.t_applicationkey,
              summ,         'C');
        end if;


end ntf_dsbdepdoc_dbt;
/
