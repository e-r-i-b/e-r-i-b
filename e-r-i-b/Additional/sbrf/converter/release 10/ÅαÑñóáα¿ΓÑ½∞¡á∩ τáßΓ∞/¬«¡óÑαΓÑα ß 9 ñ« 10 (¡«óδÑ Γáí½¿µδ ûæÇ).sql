-- ����� �������:  44910
-- ��� ����������.
create table CSA_PROMOCLIENT_LOG  (
   ID                   integer                         not null,
   PROMO_SESSION_ID     integer                         not null,
   PROFILE_ID           integer                         not null,
   CREATION_DATE        timestamp                       not null,
   BEFORE_CREATION_DATE timestamp,
   constraint PK_CSA_PROMOCLIENT_LOG primary key (ID)
)
/

create sequence S_CSA_PROMOCLIENT_LOG
/
comment on table CSA_PROMOCLIENT_LOG is '������� ����������� ������ ��������, ������������ ������������'
/
comment on column CSA_PROMOCLIENT_LOG.ID is 'ID ������'
/
comment on column CSA_PROMOCLIENT_LOG.PROMO_SESSION_ID is 'ID ����� ����������'
/
comment on column CSA_PROMOCLIENT_LOG.PROFILE_ID is 'ID ������� �������'
/
comment on column CSA_PROMOCLIENT_LOG.CREATION_DATE is '���� ����� �������'
/
comment on column CSA_PROMOCLIENT_LOG.BEFORE_CREATION_DATE is '���� ���������� ����� ����� ������'
/

create table CSA_PROMOTER_SESSIONS  (
   SESSION_ID           integer                         not null,
   CREATION_DATE        timestamp                       not null,
   CLOSE_DATE           timestamp,
   CHANNEL              varchar2(10)                    not null,
   TB                   varchar2(4)                     not null,
   OSB                  varchar2(4)                     not null,
   OFFICE               varchar(7),
   PROMOTER             varchar2(100)                   not null,
   constraint PK_CSA_PROMOTER_SESSIONS primary key (SESSION_ID)
)
/

create sequence S_CSA_PROMOTER_SESSIONS
/
comment on table CSA_PROMOTER_SESSIONS is '������� ������ ������� ��������/�������� ���� �����������'
/
comment on column CSA_PROMOTER_SESSIONS.SESSION_ID is '������������� ����� ���������'
/
comment on column CSA_PROMOTER_SESSIONS.CREATION_DATE is '����� ������ �����'
/
comment on column CSA_PROMOTER_SESSIONS.CLOSE_DATE is '����� ��������� �����'
/
comment on column CSA_PROMOTER_SESSIONS.CHANNEL is '�����'
/
comment on column CSA_PROMOTER_SESSIONS.TB is '��������'
/
comment on column CSA_PROMOTER_SESSIONS.OSB is '���'
/
comment on column CSA_PROMOTER_SESSIONS.OFFICE is '���'
/
comment on column CSA_PROMOTER_SESSIONS.PROMOTER is '������������� ����������'
/
create index CSA_PROMOTER_SESSIONS_INDEX on CSA_PROMOTER_SESSIONS (TB ASC, OSB ASC, OFFICE ASC)
/

-- ����� �������:  44977
-- ��� ����������. ����� �� ���������� �����������.
create index CSA_PROMCLNT_PROM_SESID_INDX on CSA_PROMOCLIENT_LOG (
   PROMO_SESSION_ID ASC
)
/
create index CSA_PROMO_SES_CREATE_DATE_INDX on CSA_PROMOTER_SESSIONS (
   CREATION_DATE ASC
)
/

-- ����� �������:  44578
-- ������������ ���������� ��������� ��� ������� �� ������� ��������

create table CSA_TABS  (
   ID                   integer                         not null,
   NAME                 varchar2(32),
   TEXT                 varchar2(1000),
   constraint PK_CSA_TABS primary key (ID)
)
/
create sequence S_CSA_TABS
/