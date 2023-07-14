-- ����� �������: 62313
-- ����� ������: 1.18
-- �����������: ���������� ������������ ���������
create or replace procedure create_sequence(sequenceName VARCHAR, minval INTEGER, maxval INTEGER, sequenceType VARCHAR default NULL) is
begin
    case
        -- ��������������� ������ ����
        when sequenceType = 'EXTENDED'
        then execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle cache 2000';

        -- ������ ����������������
        when sequenceType = 'STRICT'
        then execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle nocache order';

        -- ������� �� ���������
        else execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle';
   end case;
end;
go

-- ����� �������: 62724
-- ����� ������: 1.18
-- �����������: ���������� ������������ ��������� (�����)

create or replace procedure create_sequence(sequenceName VARCHAR, maxval INTEGER) is
begin
    IF regexp_like(sequenceName,'SC_.+_\d{6}') -- ���� ���� ������� - �����������
    then    -- ������ ��� ��� 2000
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle cache 2000';
    else
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle';
    end if;
end;

-- ����� �������:
-- ����� ������: 1.18
-- �����������: �� ��������� (fix)
ALTER TABLE COD MODIFY EDBO_STATE NULL;

-- ����� �������:
-- ����� ������: 1.18
-- �����������: �� ��������� (fix)
ALTER TABLE COD MODIFY BRANCHNO_ZONA NULL;
ALTER TABLE COD MODIFY OFFICE_ZONE NULL;

-- ����� �������:
-- ����� ������: 1.18
-- �����������:
ALTER TABLE MIGRATION_INFO DROP (PROFILE_CREATED)

-- ����� �������:
-- ����� ������: 1.18
-- �����������: �� ��������� (fix)
ALTER TABLE MBK MODIFY LAST_REGISTRATION_DATE NULL;

-- ����� �������:
-- ����� ������: 1.18
-- �����������: 
DELETE FROM MIGRATION_INFO;

ALTER TABLE MIGRATION_INFO DROP COLUMN MBV_MIGRATION_ID;
ALTER TABLE MIGRATION_INFO ADD MBV_MIGRATION_ID VARCHAR2(32);

-- ����� �������:
-- ����� ������: 1.18
-- �����������: ��������� ����� ���� ������ �������� ����
ALTER TABLE COD MODIFY (EDBO_NO VARCHAR2(16));

-- ����� �������: 71126
-- ����� ������: 1.18
-- �����������: BUG082215  ����. ��������� �������. ������� ����������� �� ������. 
ALTER TABLE PHONES MODIFY REGISTRATION_DATE NULL;

-- ����� �������: 79134
-- ����� ������: 1.18
-- �����������: BUG084000  [����, �� ��������] ���������� ��� ���������� ����������� �������� � �� ��������. (��)
ALTER TABLE CONFLICTED_CLIENTS DROP COLUMN LAST_SMS_ACTIVITY
