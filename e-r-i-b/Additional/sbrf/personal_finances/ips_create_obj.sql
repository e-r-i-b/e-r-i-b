declare 
  /*����� ��� �������� ����������� ����*/
  c_User constant varchar2(4000) := 'IPS';
begin
  execute immediate
    'create user '||c_User||
    ' default tablespace USERS'||
    '  temporary tablespace TEMP'||
    '  profile DEFAULT'||
    '  quota unlimited on users'||
    '  identified by '||c_User||
    '  account unlock';

  execute immediate 'grant connect to '||c_User;
  execute immediate 'grant resource to '||c_User;
  execute immediate 'grant unlimited tablespace to '||c_User;

  --����� ��� ����� ���������� ������� ����������
  execute immediate 'alter session set current_schema = '||c_User;
  
  /*�������� ���������������� ����*/
  execute immediate 'CREATE OR REPLACE TYPE STRING_LIST_TYPE IS TABLE OF VARCHAR2(4000)';
  
  /*������� �������������� ������ � ������������ � �������*/
  execute immediate 
    'create or replace function str2tbl(pStr   in varchar2 /*������ ��� �������*/
                                      , pDelim in varchar2 default '','' /*�����������*/
                                       ) return STRING_LIST_TYPE is
       l_str      long default pStr || pDelim;
       l_n        number;
       l_data     STRING_LIST_TYPE := STRING_LIST_TYPE();  
     begin
       loop
         l_n := instr( l_str, pDelim );
         exit when (nvl(l_n,0) = 0);
         l_data.extend;
         l_data(l_data.count) := ltrim(rtrim(substr(l_str,1,l_n-1)));
         l_str := substr(l_str, l_n+length(pDelim));
       end loop;

       return l_data;
     end str2tbl;';
  /*�������� ������� ���������� �� ������*/
  execute immediate
    'create table IPS_TRANSACTION
    (
      id            VARCHAR2(20) not null,
      operationType INTEGER not null,
      operationDate TIMESTAMP(6) not null,
      cardId        VARCHAR2(20) not null,
      description   VARCHAR2(100),
      accSum        NUMBER(15,4) not null,
      curAccSum     CHAR(3) not null,
      sum           NUMBER(15,4) not null,
      curSum        CHAR(3) not null,
      mccCode       INTEGER not null,
      cardAccount   VARCHAR2(25) not null,
      deviceId      VARCHAR2(10),
      personDoc     VARCHAR2(40) not null,
	  authCode      VARCHAR2(6) not null
    )';
    execute immediate 'comment on table IPS_TRANSACTION is ''���������� � ���''';
    execute immediate 'comment on column IPS_TRANSACTION.id is ''������������� ���������� � ���''';
    execute immediate 'comment on column IPS_TRANSACTION.operationType is ''��� ���� ��������''';
    execute immediate 'comment on column IPS_TRANSACTION.operationDate is ''���� ���������� ��������''';
    execute immediate 'comment on column IPS_TRANSACTION.cardId is ''����� �����''';
    execute immediate 'comment on column IPS_TRANSACTION.description is ''�������� �������� � ������ �� ����������''';
    execute immediate 'comment on column IPS_TRANSACTION.accSum is ''����� � ������ �����''';
    execute immediate 'comment on column IPS_TRANSACTION.curAccSum is ''ISO ��� ������ �����''';
    execute immediate 'comment on column IPS_TRANSACTION.sum is ''����� ����������''';
    execute immediate 'comment on column IPS_TRANSACTION.curSum is ''ISO ��� ������ ����������''';
    execute immediate 'comment on column IPS_TRANSACTION.mccCode is ''MCC ���''';
    execute immediate 'comment on column IPS_TRANSACTION.cardAccount is ''����� ����� �����''';
    execute immediate 'comment on column IPS_TRANSACTION.deviceId is ''������������� ����������, � ������� ���� ��������� ��������''';
    execute immediate 'comment on column IPS_TRANSACTION.personDoc is ''����� ��������� ������������''';
	execute immediate 'comment on column IPS_TRANSACTION.authCode is ''��� ����������� ��������''';
  /*������� ��� ������� ����������*/
  execute immediate 
  'create sequence SQ_IPS_TRANSACTION
          minvalue 1
          maxvalue 9999999999999999999999999999
          start with 1
          increment by 1
          cache 20';
  /*��������� ����*/
  execute immediate 'alter table IPS_TRANSACTION add constraint PK_ID primary key (ID)';
  /*������ �� ������ �����*/
  execute immediate 'create index IDX_IPS_TRANSACTION_CARDID on IPS_TRANSACTION (CARDID)';
  /*��������������� ������� ���  ������������ ������  ���������� �� ������*/
  execute immediate 
  'create table ERIB_TAB
  (
    CARD_NBR VARCHAR2(4000)
  )';
  /*��������� ��������� ������ ���������� ��� ����� ��� ���������� ����*/
  execute immediate
  'create or replace procedure getTransactionsByCards(
          pSeparator in varchar2 default '','' /*����������� ��� ������ ����*/
        , pCards in varchar2 /*������, ���������� ������ ���� ����������� separator-��, �� ������� �������. �������� ������ ����������*/
        , pFromDate in timestamp := sysdate/*���� � ������� �������. �������� ������ ����������*/
        , pCursor out sys_refcursor) as
  c VARCHAR2(4000);
  s VARCHAR2(4000);
  i INTEGER;
  begin
    if (pSeparator is null and pCards is null and pFromDate is null) or
         (pSeparator is null and pCards is not null) or
         (pSeparator is not null and pCards is null) then
        RAISE_APPLICATION_ERROR(-20900,''������ ������� ������ ��� ������ getTransactionByCards'');
    end if;

    DELETE FROM erib_tab;
    COMMIT;
    s := pCards;
    WHILE s IS NOT NULL LOOP
        i:=INSTR(s,pSeparator,-1);
        IF NVL(i,0)=0 THEN
          c:=s;
        ELSE
          c:=SUBSTR(s,i+LENGTH(pSeparator));
        END IF;
        s:=SUBSTR(s,1,i-1);
        IF c IS NOT NULL THEN
          INSERT INTO erib_tab VALUES(c);
        END IF;
    END LOOP;
    OPEN pCursor FOR
        SELECT /*+ ORDERED DRIVING_SITE(C) USE_NL_WITH_INDEX(E)*/
               E.*
          FROM erib_tab C,ips_transaction E
         WHERE E.cardid=C.card_nbr
           AND E.operationdate>=pFromDate
    ORDER BY E.operationdate;
  end getTransactionsByCards;';  
end;
