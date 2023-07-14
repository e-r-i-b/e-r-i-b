-- ����� �������: 47913
-- �����������: BUG051608: ����������� ������������ ������ �Twitter� ����� ��������������.
-- ����� �������: 48473
-- �����������: BUG053687: ������������ ��������� ��� ��������� ���������� ������� Twitter.
alter table WIDGET_DEFINITIONS add (
	MAX_COUNT number default 5 not null,
	MAX_COUNT_MESSAGE varchar2(256) default '�� �� ������ �������� ����� 5 ���������� ��������.' not null
)
/

-- ����� �������: 48377
-- �����������: �������������� ���-����� ��� ����-������, ����-������ � ������-������
alter table ACCOUNT_LINKS add SMS_AUTO_ALIAS varchar2(20)
/
alter table CARD_LINKS add SMS_AUTO_ALIAS varchar2(20)
/
alter table LOAN_LINKS add SMS_AUTO_ALIAS varchar2(20)
/

-- ����� �������: 48443
-- �������������� �����
alter table RATE add TARIF_PLAN_CODE varchar2(20)
/

-- ����� �������: 48518
/*--�������� � ��������� ������
update BUSINESS_DOCUMENTS 
	set OPERATION_UID = OPERATION_UUID 
		where creation_date > to_date('01.05.2013 00:00:00', 'dd.mm.yyyy hh24:mi:ss') and KIND = 'P' and OPERATION_UUID is not null 
*/

alter table BUSINESS_DOCUMENTS set unused column OPERATION_UUID
/

-- ����� �������: 48516
-- �����������: ����������� ���� ������������� � ������� �������������(��)
alter table OPERATION_CONFIRM_LOG add CONFIRM_CODE varchar2(32)
/

-- ����� �������: 48639
-- �����������: ������ ����� �� ��������� � �������� (� ����� ������ � ���).
create index DUL_INDEX on DOCUMENTS (
   replace("DOC_SERIES"||"DOC_NUMBER",' ','') asc
)
online parallel 64
/
alter index DUL_INDEX noparallel
/

-- ����� �������: 48726
-- �����������: �� �������� �������� �� ���������� ������ ����� ��� ������������� �������
alter table PROFILE add TARIF_PLAN_CODE varchar2(20)
/

-- ����� �������: 48730
-- �����������: ������� ����� ��� ����� �����
alter table GROUPS_RISK add (RANK varchar2(5)  default 'HIGH' not null)
/

-- ����� �������: 48616
-- �����������: �������������� ������ �����
alter table PAYMENT_SERVICES add ( 
	IS_CATEGORY    char(1) default '0' not null,
	SHOW_IN_API    char(1) default '1' not null,
	SHOW_IN_ATM    char(1) default '1' not null,
	SHOW_IN_SYSTEM char(1) default '1' not null
)
/

-- ����� �������: 49792
-- �����������: ���������� ����������� ���������������� ������� � ������ �������� �����
--��������� CSV �����

insert into  PAYMENT_SERVICES_OLD 
	select id, code, name, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION,SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME from PAYMENT_SERVICES
/
insert into SERV_PROV_PAYM_SERV_OLD 
	select PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID, LIST_INDEX from SERV_PROVIDER_PAYMENT_SERV;
/

alter table PAYMENT_SERVICE_CATEGORIES drop constraint FK_CATEGORY_TO_P_SERV
/

alter table PAYMENT_SERVICE_CATEGORIES
   add constraint FK_CATEGORY_TO_P_SERV foreign key (PAYMENT_SERVICES_ID)
      references PAYMENT_SERVICES_OLD (ID)
/

-- ��������� ������� ��� ������ �����������:
delete from SERV_PROVIDER_PAYMENT_SERV
/    
delete from PAYMENT_SERVICES
/

-- ��������� ����� ������:
declare
    serv_id integer; 
    prt_id integer; 

begin 
    for i in (select * from TEMP_SP_SERVICES where parent is null
    ) loop
        -- ���� ������ ������� ���������� - ��� ������������       
        
            if i.ico is null then 
                INSERT INTO PAYMENT_SERVICES(id, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM) 
                VALUES(S_PAYMENT_SERVICES.nextval, i.code, i.name, null, null, 0, i.name, 0, i.priority, 1, '/payment_service/tovary_uslugi.jpg', 1, 1, 1, 1);
            else
                INSERT INTO PAYMENT_SERVICES(id, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM) 
                VALUES(S_PAYMENT_SERVICES.nextval, i.code, i.name, null, null, 0, i.name, 0, i.priority, 1, '/payment_service/'||i.ico||'.jpg', 1, 1, 1, 1);
            end if;
	end loop;
	
	for i in (select * from TEMP_SP_SERVICES where parent is not null
	) loop
        
            if i.ico is null then 
                INSERT INTO PAYMENT_SERVICES(id, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM) 
                VALUES(S_PAYMENT_SERVICES.nextval, i.code, i.name, null, null, 0, i.name, 0, i.priority, 1, '/payment_service/tovary_uslugi.jpg', 0, 1, 1, 1);
            else
                INSERT INTO PAYMENT_SERVICES(id, CODE, NAME, PARENT_ID, IMAGE_ID, POPULAR, DESCRIPTION, SYSTEM, PRIORITY, VISIBLE_IN_SYSTEM, IMAGE_NAME, IS_CATEGORY, SHOW_IN_API, SHOW_IN_ATM, SHOW_IN_SYSTEM) 
                VALUES(S_PAYMENT_SERVICES.nextval, i.code, i.name, null, null, 0, i.name, 0, i.priority, 1, '/payment_service/'||i.ico||'.jpg', 0, 1, 1, 1);
            end if;    
            --����� ��������� ������ �� ��������
            SELECT ID INTO SERV_ID FROM PAYMENT_SERVICES WHERE CODE = I.CODE;
            
            SELECT ID INTO PRT_ID FROM PAYMENT_SERVICES WHERE CODE = I.PARENT;
            
            INSERT INTO PAYMENT_SERV_PARENTS(SERVICE_ID, PARENT_ID) 
            VALUES(SERV_ID, PRT_ID);
			
    end loop;

end;
/
-- �������� ��������� ������
update PAYMENT_SERVICES set SHOW_IN_API = 0, SHOW_IN_ATM = 0, SHOW_IN_SYSTEM = 0 where code in ('99.08','99.99','18.01','99.06','02.01')
/
--����������� ������

insert into SERV_PROVIDER_PAYMENT_SERV(PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID, LIST_INDEX)     
        select ps.id as Payment_service_id, spps_o.SERVICE_PROVIDER_ID as SERVICE_PROVIDER_ID, to_number(dense_rank() over (partition by spps_o.SERVICE_PROVIDER_ID order by spps_o.PAYMENT_SERVICE_ID)) -1 as LIST_INDEX
        from (SERV_PROV_PAYM_SERV_OLD spps_o inner join PAYMENT_SERVICES_OLD ps_o on spps_o.PAYMENT_SERVICE_ID = ps_o.id) 
            inner join PAYMENT_SERVICES ps on ps_o.code = ps.code
/
--��������� ����������� �� credits � ��������� �������� ��. ������
insert into SERV_PROVIDER_PAYMENT_SERV (PAYMENT_SERVICE_ID,SERVICE_PROVIDER_ID,LIST_INDEX)
    select ps.id as PAYMENT_SERVICE_ID, sp.id as SERVICE_PROVIDER_ID, '0' as LIST_INDEX 
    from SERVICE_PROVIDERS sp, payment_services ps 
    where sp.id in (select SERVICE_PROVIDER_ID 
                    from SERV_PROV_PAYM_SERV_OLD 
                    where PAYMENT_SERVICE_ID = (select id 
                                                from PAYMENT_SERVICES_OLD 
                                                where CODE = 'credits' ))
    and sp.state = 'ACTIVE' 
    and ps.code = '08.01'
/

--������� ��������� �������
drop table TEMP_SP_SERVICES
/

-- ����� �������: 48608
-- �����������: �������������� ����������: ���������� ����� "��������� ��������� � �������"
-- ����� �������: 48851
-- �����������: �������������� ����������: ���� "��������� ��������� � �������"
-- ����� �������: 49174
-- �����������: ��������� ����� ��������� �����������.
alter table SERVICE_PROVIDERS add ( 
	VISIBLE_PAYMENTS_FOR_IB 	    char(1) default '0' not null, 
	VISIBLE_PAYMENTS_FOR_M_API 	    char(1) default '0' not null,
	VISIBLE_PAYMENTS_FOR_ATM_API    char(1) default '0' not null, 
	AVAILABLE_PAYMENTS_FOR_IB 		char(1) default '1' not null,
	AVAILABLE_PAYMENTS_FOR_M_API 	char(1) default '1' not null,
	AVAILABLE_PAYMENTS_FOR_ATM_API 	char(1) default '1' not null,
	AVAILABLE_PAYMENTS_FOR_ERMB 	char(1) default '1' not null, 
	IS_AUTOPAYMENT_SUPPORTED_API    char(1) default '0' not null,
	IS_AUTOPAYMENT_SUPPORTED_ATM    char(1) default '0' not null,
	IS_AUTOPAYMENT_SUPPORTED_ERMB   char(1) default '0' not null,
	IS_EDIT_PAYMENT_SUPPORTED       char(1) default '1' not null,
	IS_CREDIT_CARD_SUPPORTED        char(1) default '1' not null,
	VISIBLE_AUTOPAYMENTS_FOR_IB     char(1) default '0' not null,
	VISIBLE_AUTOPAYMENTS_FOR_API    char(1) default '0' not null,
	VISIBLE_AUTOPAYMENTS_FOR_ATM    char(1) default '0' not null,
	VISIBLE_AUTOPAYMENTS_FOR_ERMB   char(1) default '0' not null
)
/

-- ���������� �����������
--����������� ��� ������ �� ����������� ��� ����, � ���� ���� �������� ������
update SERVICE_PROVIDERS 
	set AVAILABLE_PAYMENTS_FOR_IB = 0
		where IS_ALLOW_PAYMENTS <> 1
/
--����������� ��� ������ mAPI ����������� ��� ����, ��� ��� ������� � ��������� ���
update SERVICE_PROVIDERS 
	set AVAILABLE_PAYMENTS_FOR_M_API = 0
		where MOBILE_SERVICE <> 1
/
--����������� ��� ������ ��� ����������� ��� ����, ��� ��� ������� � ���
update SERVICE_PROVIDERS 
	set AVAILABLE_PAYMENTS_FOR_ATM_API = 0
		where ATM_AVAILABLE <> 1
/

--��������� � �������� 
--�� ����������� ��� ����, ��� �� ��� ����� � ��������
update SERVICE_PROVIDERS 
	set VISIBLE_PAYMENTS_FOR_IB = 1 
		where AVAILABLE_PAYMENTS_FOR_IB = 1 and NOT_VISIBLE_IN_HIERARCHY = 0
/

--��� - ��� ��� ������ � ��������
update SERVICE_PROVIDERS 
	set VISIBLE_PAYMENTS_FOR_M_API = 1 
		where AVAILABLE_PAYMENTS_FOR_M_API = 1 and NOT_VISIBLE_IN_HIERARCHY = 0
/

--��� - ��� ��� ������ � ��������
update SERVICE_PROVIDERS 
	set VISIBLE_PAYMENTS_FOR_ATM_API = 1 
		where AVAILABLE_PAYMENTS_FOR_ATM_API = 1 and NOT_VISIBLE_IN_HIERARCHY = 0
/

--����������� ��
-- �� ���� ������� ���� ��� ������� �����������
update SERVICE_PROVIDERS 
	set IS_AUTOPAYMENT_SUPPORTED_API = 1, IS_AUTOPAYMENT_SUPPORTED_ATM = 1, IS_AUTOPAYMENT_SUPPORTED_ERMB = 1 
		where IS_AUTOPAYMENT_SUPPORTED = 1
/

--��������� �� � ��������
-- �� - ��� �� ��� � ��� ������
update SERVICE_PROVIDERS 
	set VISIBLE_AUTOPAYMENTS_FOR_IB = 1
		where IS_AUTOPAYMENT_SUPPORTED = 1 and VISIBLE_PAYMENTS_FOR_IB = 1
/

--�� - ���������
update SERVICE_PROVIDERS 
	set VISIBLE_AUTOPAYMENTS_FOR_API = 0
/

--��� - ��� �� ��� ��� ��
update SERVICE_PROVIDERS 
	set VISIBLE_AUTOPAYMENTS_FOR_ATM = 1
		where IS_AUTOPAYMENT_SUPPORTED_ATM = 1 and VISIBLE_AUTOPAYMENTS_FOR_IB = 1
/

--���� ��� �� ��� ��� ��
update SERVICE_PROVIDERS 
	set VISIBLE_AUTOPAYMENTS_FOR_ERMB = 1
		where IS_AUTOPAYMENT_SUPPORTED_ERMB = 1 and VISIBLE_AUTOPAYMENTS_FOR_IB = 1
/

--�������� ������ � ��������� ���� ��� ������ �������� � ��. ������
update SERVICE_PROVIDERS 
	set IS_CREDIT_CARD_SUPPORTED = 0
		where id in (	select service_provider_id 
						from SERV_PROVIDER_PAYMENT_SERV 
						where payment_service_id in (
							select id from PAYMENT_SERVICES where code = '08.01'
						)
		)
/

-- ����� �������: 49404
-- �����������:  �������� ����� �������������� ���������� � ������������ ������ 
alter table SERVICE_PROVIDERS drop (
	MOBILE_SERVICE,
	ATM_AVAILABLE,
	NOT_VISIBLE_IN_HIERARCHY,
	MIN_SUMMA_THRESHOLD,
	MAX_SUMMA_THRESHOLD,
	MIN_VALUE_THRESHOLD,
	MAX_VALUE_THRESHOLD,
	MIN_SUMMA_ALWAYS,
	MAX_SUMMA_ALWAYS,
	DISCRETE_VALUE_THRESHOLD,
	IS_INTERVAL_THRESHOLD,
	IS_ALWAYS_AUTOPAY_SUPPORTED,
	IS_THRESHOSD_AUTOPAY_SUPPORTED,
	IS_INVOICE_AUTOPAY_SUPPORTED,
	CLIENT_HINT_ALWAYS,
	CLIENT_HINT_THRESHOLD,
	CLIENT_HINT_INVOICE
)
/

-- ����� �������: 49663
-- �����������: ���� ������ ������ mAPI � ������ ��
update SERVICE_PROVIDERS set VERSION_API = 400 where VERSION_API is not null and VERSION_API < 5
/
update SERVICE_PROVIDERS set VERSION_API = 500 where VERSION_API is not null and VERSION_API = 5
/
	
--������� �� �������� � ����� �����������, ������� ������� ������
-- ������ ���� ��, � ������� �� �����
UPDATE SERVICE_PROVIDERS SP SET VISIBLE_PAYMENTS_FOR_IB = 0, VISIBLE_PAYMENTS_FOR_M_API =0, VISIBLE_PAYMENTS_FOR_ATM_API =0
WHERE EXISTS (SELECT * FROM TEMP_SP_UNVISIBLE TSP 
                WHERE TSP.EXTERNAL_ID = SP.EXTERNAL_ID 
                AND (TSP.PAYMENTS = 0 OR TSP.PAYMENTS IS NULL))
/

--������ ������, � ������� ����� �� ��������
update PAYMENT_SERVICES set SHOW_IN_SYSTEM = 0, SHOW_IN_API = 0, SHOW_IN_ATM = 0
where id in (select ps.id from (select sps.* from SERV_PROVIDER_PAYMENT_SERV sps inner join SERVICE_PROVIDERS sp1 on sps.SERVICE_PROVIDER_ID=sp1.id where sp1.VISIBLE_PAYMENTS_FOR_IB = 1) sps1
    right join PAYMENT_SERVICES ps on sps1.PAYMENT_SERVICE_ID=ps.id 
where sps1.SERVICE_PROVIDER_ID is null and ps.is_category <> 1)
/

--������ ���������, � �������� ������ ������ ������
update PAYMENT_SERVICES set SHOW_IN_SYSTEM = 0, SHOW_IN_API = 0, SHOW_IN_ATM = 0
where id in (select id from PAYMENT_SERVICES ps1 
--������ ���������
where ps1.IS_CATEGORY=1 
    -- � ������� ���� �������
    and exists (select ps.id from PAYMENT_SERVICES ps inner join PAYMENT_SERV_PARENTS psp on ps.id = psp.service_id 
                where  psp.parent_id = ps1.id) 
    -- �� � ������� ��� ������� ��������
    and not exists (select ps.id from PAYMENT_SERVICES ps inner join PAYMENT_SERV_PARENTS psp on ps.id = psp.service_id 
                where  psp.parent_id = ps1.id and ps.SHOW_IN_SYSTEM = 1))
/
--����������� ���������� ��

-- ������� ��� ����������
UPDATE SERVICE_PROVIDERS SET SORT_PRIORITY = 0;
/
--������ ����������� ���������� �������� ����� �� �����������
DECLARE
    VPRIORITY NUMBER;
BEGIN
    --��������� ��� ������, �� ������� ���� ������ � ������������
    FOR SERVICE IN (SELECT * FROM PAYMENT_SERVICES PS WHERE EXISTS (SELECT * FROM SERV_PROVIDER_PAYMENT_SERV SPS 
                                                                    WHERE SPS.PAYMENT_SERVICE_ID = PS.ID)) LOOP
        VPRIORITY:= 100;
        FOR PROVIDER IN (SELECT U1.* FROM (SELECT * FROM SERVICE_PROVIDERS SP1
                           WHERE EXISTS (SELECT 1 FROM SERV_PROVIDER_PAYMENT_SERV SPS1 
                                            WHERE SP1.ID = SPS1.SERVICE_PROVIDER_ID 
                                            AND SPS1.PAYMENT_SERVICE_ID = SERVICE.ID
                            ) 
                            AND SP1.VISIBLE_PAYMENTS_FOR_IB = 1) U1 
                           INNER JOIN 
                            TEMP_SP_UNVISIBLE TSP1 ON U1.EXTERNAL_ID = TSP1.EXTERNAL_ID AND TSP1.PAYMENTS <> 0 
                            ORDER BY TSP1.PAYMENTS DESC
          ) LOOP            
          BEGIN  
            UPDATE SERVICE_PROVIDERS SET SORT_PRIORITY = VPRIORITY WHERE ID = PROVIDER.ID;
            VPRIORITY:= VPRIORITY - 1;
            IF VPRIORITY = 0 THEN EXIT;
            END IF;
          end;
          END LOOP;
     END LOOP;
END;
--������� ��������� �������			
drop table TEMP_SP_UNVISIBLE
/

-- ����� �������: 48930
-- �����������: ���������� ��������� "��������� ���" � "�������� ����������� ���".
alter table ACCESSSCHEMES add VSP_EMPLOYEE_SCHEME char(1) default '0' not null
/
alter table EMPLOYEES add VSP_EMPLOYEE char(1) default '0' not null 
/

--����� �������: 48937
--�����������:  MANAGER_ID 14 ��������     
alter table EMPLOYEES modify MANAGER_ID varchar2(14)
/
alter table PERSONAL_FINANCE_PROFILE modify MANAGER_ID varchar2(14)
/

--����� �������: 49012
--�����������:  ������� ������. ����� 5. ��������� ��������� �� ������� ��� ������� � ����������.
alter table STATIC_MESSAGES modify (KEY varchar2(64))
/
update STATIC_MESSAGES set KEY = concat('com.rssl.iccs.',KEY)
/
alter table IMAGES_MESSAGES modify (MESSAGE_KEY varchar2(64))
/
update IMAGES_MESSAGES set MESSAGE_KEY = concat('com.rssl.iccs.',MESSAGE_KEY)
/

--����� �������: 49038
--�����������:  ���������� �������� ��������� Field(�������� ���� mask) 
alter table FIELD_DESCRIPTIONS add (MASK varchar2(1024) null)
/

--����� �������: 49265
--�����������:  ������� ������������ �������
alter table LIMITS add (SECURITY_TYPE varchar2(8))
/

-- ����� �������: 48678
-- �����������: ���������� �������� ��� � ����. 
--�����������:  ���������� ��������� ������������� ������� ��� � ���� ����� � SVN. ������ ��.
alter table MESSAGE_TEMPLATES add (
   KEY                  varchar2(255),
   DESCRIPTION          varchar2(255),
   PREVIOUS_TEXT        clob,
   LAST_MODIFIED        timestamp,
   EMPLOYEE_LOGIN_ID    integer,
   VARIABLES            varchar2(4000)	
)
/

-- ����� �������: 49336
-- �����������: ��������� ������������ ��� �������
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityStartDate','C','')
/
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityDaysNumber','C','')
/
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInATM','C','')
/
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInERIB','C','')
/
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInVSP','C','')
/
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values (S_BUSINESS_PROPERTIES.nextval, 'securityInCC','C','')
/

-- ����� �������: 49388
-- �����������: ��������� ��� ������� "�������" (��� �������): ��������������
alter table PROFILE add (HINT_READ CHAR(1) default '0' not null)
/

-- ����� �������: 49391
-- �����������: ����������� �������������� ����� ����������� �������� � ������� "����������"
create index DOC_ID_JOB_NAME_PER_IDX on PAYMENT_EXECUTION_RECORDS
(
    DOCUMENT_ID asc,
    JOB_NAME asc
)
/

-- ����� �������: 49329
delete from LOAN_CARD_OFFER
/
alter table LOAN_CARD_OFFER drop column LOGIN_ID
/
alter table LOAN_CARD_OFFER drop column DEPARTMENT_ID
/

alter table LOAN_CARD_OFFER add (
	FIRST_NAME    varchar2(20) not null, 
	SUR_NAME      varchar2(20) not null, 
	PATR_NAME     varchar2(20),
	BIRTHDAY      timestamp not null 
)
/

create index LCO_FIO_DUL_TB_V_DR on LOAN_CARD_OFFER
(
   upper(replace(SUR_NAME,' ','') || replace(FIRST_NAME,' ','') || replace(PATR_NAME,' ','')),
   upper(replace(SERIES_NUMBER, ' ', '')),
   TB,
   IS_VIEWED,
   BIRTHDAY
)
/

alter table USERS add (SECURITY_TYPE varchar2(8))
/

-- ����� �������: 49688 
alter table RATE add EXPIRE_DATE timestamp
/

--������� ������������ ��������, �� ������������ �� ��������� ������
alter session enable parallel DML
--����� ���������� 2:49
update /*+parallel (USERS, 32)*/ USERS set SECURITY_TYPE = 'LOW' 
/

alter table SHOP_FIELDS add CANCELED char(1) default '0' not null
/

create index ORDERS_USER_ID_DATE on ORDERS 
(
    USER_ID asc,
    ORDER_DATE desc
)
parallel 32
/
alter index ORDERS_USER_ID_DATE noparallel
/

alter table ACCOUNT_LINKS set unused column SHOW_IN_ES
/
alter table ACCOUNT_LINKS modify SHOW_IN_ATM default '1'
/
alter table CARD_LINKS modify SHOW_IN_ATM default '1'
/
alter table LOAN_LINKS modify SHOW_IN_ATM default '1'
/
alter table IMACCOUNT_LINKS modify SHOW_IN_ATM default '1'
/


alter table STORED_LONG_OFFER drop constraint FK_STORED_LO_DEPARTMENTS_REF
/
alter table STORED_DEPO_ACCOUNT drop constraint STORED_DA_TO_DEPARTMENTS_REF
/
alter table STORED_IMACCOUNT drop constraint FK_STORED_I_TO_DEPARTMENTS_REF
/
alter table STORED_LOAN drop constraint STORED_L_TO_DEPARTMENTS_REF
/
alter table STORED_CARD drop constraint FK_STORED_C_TO_DEPARTMENT_REF
/
alter table STORED_ACCOUNT drop constraint FK_STORED_A_TO_DEPARTMENT_REF
/

-- ����� �������: 50428
alter table STORED_CARD drop column ADDITIONAL_CARD_TYPE
/
alter table CARD_LINKS add ADDITIONAL_CARD_TYPE varchar2(20)
/
alter table ACCOUNT_LINKS add (
	OFFICE_TB   varchar2(5),
	OFFICE_OSB  varchar2(5),
	OFFICE_VSP  varchar2(5)
)
/

-- ����� �������: 50297
alter table BUSINESS_DOCUMENTS modify (CONFIRM_EMPLOYEE varchar2(256))
/
