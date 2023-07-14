-- ����� �������: 47250
-- �������� �������� ������� ���������� 
/*==============================================================*/
/* Table: JOB_EXECUTION_MARKER                                  */
/*==============================================================*/
create table JOB_EXECUTION_MARKER  (
   ID                   INTEGER                         not null,
   JOB_NAME             VARCHAR2(128)                   not null,
   ACTUAL_DATE          TIMESTAMP                       not null,
   constraint PK_JOB_EXECUTION_MARKER primary key (ID)
)
/

create sequence S_JOB_EXECUTION_MARKER
/

-- ����� �������: 47754
-- �����������: ENH044725: mAPI 5. ������� ����������� ������� ���� ������� � ��������� ����.
/*==============================================================*/
/* Table: PAYMENT_FORM_IMPORTS                                  */
/*==============================================================*/
create table PAYMENT_FORM_IMPORTS  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(256)                   not null,
   BODY                 CLOB                            not null,
   constraint PK_PAYMENT_FORM_IMPORTS primary key (ID),
   constraint AK_FORM_IMPORT_NAME unique (NAME)
)
/

create sequence S_PAYMENT_FORM_IMPORTS
/

-- ����� �������: 47856
-- �����������: ������ �� ������������(��������� ��� ���������� �����)
/*==============================================================*/
/* Table: AUTOPAY_SETTINGS                                      */
/*==============================================================*/
create table AUTOPAY_SETTINGS(
   ID                   INTEGER                         not null,
   TYPE                 VARCHAR2(20)                    not null,
   RECIPIENT_ID         INTEGER                         not null,
   PARAMETERS           CLOB                            not null,
   constraint PK_AUTOPAY_SETTING primary key (ID)
)
/

create sequence S_AUTOPAY_SETTINGS
/

alter table AUTOPAY_SETTINGS
   add constraint FK_AUTOPAY_SETTING_TO_PROV foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
/

create index "DXFK_AUTOPAY_SETTING_TO_PROV" on AUTOPAY_SETTINGS
(
   RECIPIENT_ID
)
/

DECLARE
	autopayParams CLOB; -- ��������� �����������
BEGIN
FOR serviceProvidersRow IN 
(SELECT 
	ID,
	IS_THRESHOSD_AUTOPAY_SUPPORTED, 
	MIN_SUMMA_THRESHOLD, 
	MAX_SUMMA_THRESHOLD, 
	IS_INTERVAL_THRESHOLD, 
	MIN_VALUE_THRESHOLD, 
	MAX_VALUE_THRESHOLD, 
	DISCRETE_VALUE_THRESHOLD,
	CLIENT_HINT_THRESHOLD,
	IS_ALWAYS_AUTOPAY_SUPPORTED,
	MIN_SUMMA_ALWAYS,
	MAX_SUMMA_ALWAYS,
	CLIENT_HINT_ALWAYS,
	IS_INVOICE_AUTOPAY_SUPPORTED,
	CLIENT_HINT_INVOICE
FROM SERVICE_PROVIDERS WHERE IS_AUTOPAYMENT_SUPPORTED = '1') 
	LOOP
		IF(serviceProvidersRow.IS_THRESHOSD_AUTOPAY_SUPPORTED = '1') THEN   
			-- ��������� ���������
			autopayParams := '<entity key="THRESHOLD">';
			IF(serviceProvidersRow.CLIENT_HINT_THRESHOLD is not null) THEN
				autopayParams := autopayParams || '<field name="clientHint">'||serviceProvidersRow.CLIENT_HINT_THRESHOLD||'</field>';
			END IF;    
			IF(serviceProvidersRow.MAX_SUMMA_THRESHOLD is not null) THEN
				autopayParams := autopayParams || '<field name="maxSumThreshold">'||serviceProvidersRow.MAX_SUMMA_THRESHOLD||'</field>';
			END IF;    
			IF(serviceProvidersRow.MIN_SUMMA_THRESHOLD is not null) THEN
				autopayParams := autopayParams || '<field name="minSumThreshold">'||serviceProvidersRow.MIN_SUMMA_THRESHOLD||'</field>';
			END IF;    
			IF(serviceProvidersRow.MAX_VALUE_THRESHOLD is not null) THEN
				autopayParams := autopayParams || '<field name="maxValueThreshold">'||serviceProvidersRow.MAX_VALUE_THRESHOLD||'</field>';
			END IF;    
			IF(serviceProvidersRow.MIN_VALUE_THRESHOLD is not null) THEN
				autopayParams := autopayParams || '<field name="minValueThreshold">'||serviceProvidersRow.MIN_VALUE_THRESHOLD||'</field>'; 
			END IF;    
			IF(serviceProvidersRow.DISCRETE_VALUE_THRESHOLD is not null) THEN
				autopayParams := autopayParams ||'<field name="discreteValues">'||serviceProvidersRow.DISCRETE_VALUE_THRESHOLD||'</field>';     
			END IF;    
			IF(serviceProvidersRow.IS_INTERVAL_THRESHOLD = '1') THEN
				autopayParams := autopayParams || '<field name="interval">true</field>';
			ELSE     
				autopayParams := autopayParams ||'<field name="interval">false</field>';     
			END IF;
			autopayParams := autopayParams ||'</entity>';
			-- ��������� ������
			insert into AUTOPAY_SETTINGS(ID, TYPE, RECIPIENT_ID, PARAMETERS) values (S_AUTOPAY_SETTINGS.nextval, 'THRESHOLD', serviceProvidersRow.ID, autopayParams);
		END IF;
		IF(serviceProvidersRow.IS_ALWAYS_AUTOPAY_SUPPORTED = '1') THEN   
			-- ��������� ���������
			autopayParams := '<entity key="ALWAYS">'; 
			IF(serviceProvidersRow.CLIENT_HINT_ALWAYS is not null) THEN
				autopayParams := autopayParams ||'<field name="clientHint">'||serviceProvidersRow.CLIENT_HINT_ALWAYS||'</field>';
			END IF;    
			IF(serviceProvidersRow.MIN_SUMMA_ALWAYS is not null) THEN
				autopayParams := autopayParams ||'<field name="minSumAlways">'||serviceProvidersRow.MIN_SUMMA_ALWAYS||'</field>';
			END IF;    
			IF(serviceProvidersRow.MAX_SUMMA_ALWAYS is not null) THEN
				autopayParams := autopayParams ||'<field name="maxSumAlways">'||serviceProvidersRow.MAX_SUMMA_ALWAYS||'</field>';
			END IF;    
			autopayParams := autopayParams ||'</entity>';                                          
			-- ��������� ������            
			insert into AUTOPAY_SETTINGS(ID, TYPE, RECIPIENT_ID, PARAMETERS) values (S_AUTOPAY_SETTINGS.nextval, 'ALWAYS', serviceProvidersRow.ID, autopayParams);            
		END IF;
		IF(serviceProvidersRow.IS_INVOICE_AUTOPAY_SUPPORTED = '1') THEN   
			-- ��������� ���������
			autopayParams := '<entity key="INVOICE">'; 
			IF(serviceProvidersRow.CLIENT_HINT_INVOICE is not null) THEN
				autopayParams := autopayParams ||'<field name="clientHint">'||serviceProvidersRow.CLIENT_HINT_INVOICE||'</field>';
			END IF;    
			autopayParams := autopayParams ||'</entity>';
			-- ��������� ������            
			insert into AUTOPAY_SETTINGS(ID, TYPE, RECIPIENT_ID, PARAMETERS) values (S_AUTOPAY_SETTINGS.nextval, 'INVOICE', serviceProvidersRow.ID, autopayParams);                        
		END IF;   
	END LOOP;
END;
/

-- ����� �������: 47889
-- �����������:  ��������� ������ ������ ������� � �������
/*==============================================================*/
/* Table: PERSON_OLD_IDENTITY                                   */
/*==============================================================*/
create table PERSON_OLD_IDENTITY  (
   ID                   INTEGER                         not null,
   FIRST_NAME           VARCHAR2(120)                   not null,
   SUR_NAME             VARCHAR2(120)                   not null,
   PATR_NAME            VARCHAR2(120),
   BIRTHDAY             TIMESTAMP                       not null,
   EMPLOYEE_ID          INTEGER,
   DATE_CHANGE          TIMESTAMP                       not null,
   STATUS               VARCHAR2(12)                    not null,
   PERSON_ID            INTEGER                         not null,
   DOC_TYPE             VARCHAR2(32),
   DOC_NAME             VARCHAR2(128),
   DOC_NUMBER           VARCHAR2(16),
   DOC_SERIES           VARCHAR2(16),
   DOC_ISSUE_DATE       TIMESTAMP,
   DOC_ISSUE_BY         VARCHAR2(255),
   DOC_ISSUE_BY_CODE    VARCHAR2(16),
   DOC_MAIN             VARCHAR2(1),
   DOC_TIME_UP_DATE     TIMESTAMP,
   DOC_IDENTIFY         VARCHAR2(1),
   constraint PK_PERSON_OLD_IDENTITY primary key (ID)
)
/

create sequence S_PERSON_OLD_IDENTITY
/

create index "DXFK_PERS_OLD_IDEN_EMPLOYEE" on PERSON_OLD_IDENTITY
(
   EMPLOYEE_ID
)
/

alter table PERSON_OLD_IDENTITY
   add constraint FK_PERS_OLD_IDEN_EMPLOYEE foreign key (EMPLOYEE_ID)
      references LOGINS (ID)
/


create index "DXFK_PERS_OLD_IDEN_USERS" on PERSON_OLD_IDENTITY
(
   PERSON_ID
)
/

alter table PERSON_OLD_IDENTITY
   add constraint FK_PERS_OLD_IDEN_USERS foreign key (PERSON_ID)
      references USERS (ID)
/

--����� �������: 49180
--�����������: �������� �������������� ������ ���+���+�� �� ������� ����������������� ������ ��������
create index POI_FIO_DUL_DR on PERSON_OLD_IDENTITY
(
    upper(replace(SUR_NAME,' ','') || replace(FIRST_NAME,' ','') || replace(PATR_NAME,' ','')),
    replace(DOC_SERIES,' ','') || replace(DOC_NUMBER,' ',''),
    BIRTHDAY
);

-- ����� �������: 47948
-- �����������: ��� ������������: ������� �������� "����", ������ � ����.
/*==============================================================*/
/* Table: ACCOUNT_TARGETS                                       */
/*==============================================================*/
create table ACCOUNT_TARGETS  (
   ID                   INTEGER                         not null,
   LOGIN_ID             INTEGER                         not null,
   NAME                 VARCHAR2(35)                    not null,
   NAME_COMMENT         VARCHAR2(100),
   PLANED_DATE          TIMESTAMP                       not null,
   AMOUNT               NUMBER(20,4)                    not null,
   AMOUNT_CURRENCY      CHAR(3)                         not null,
   ACCOUNT_NUM          VARCHAR2(25),
   DICTIONARY_TARGET    VARCHAR2(15)                    not null,
   CLAIM_ID             INTEGER,
   ACCOUNT_LINK         INTEGER,
   constraint PK_ACCOUNT_TARGETS primary key (ID)
)
/

create sequence S_ACCOUNT_TARGETS
/

create unique index I_ACCOUNT_TARGETS_CLAIM on ACCOUNT_TARGETS (
   LOGIN_ID ASC,
   CLAIM_ID ASC
)
/

create index "DXFK_A_TARGETS_FK_LOGINS" on ACCOUNT_TARGETS
(
   LOGIN_ID
)
/

alter table ACCOUNT_TARGETS
   add constraint FK_A_TARGETS_FK_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
/

create index "DXFK_A_TARGETS_FK_ACCOUNT_LINK" on ACCOUNT_TARGETS
(
   ACCOUNT_LINK
)
/

alter table ACCOUNT_TARGETS
   add constraint FK_A_TARGETS_FK_ACCOUNT_LINK foreign key (ACCOUNT_LINK)
      references ACCOUNT_LINKS (ID)
      on delete set null
/

-- ����� �������: 47959
-- �����������: ��������� � ��� �������� �������
/*==============================================================*/
/* Table: INSURANCE_LINKS                                       */
/*==============================================================*/
create table INSURANCE_LINKS  (
   ID                   INTEGER                         not null,
   EXTERNAL_ID          VARCHAR(70)                     not null,
   LOGIN_ID             INTEGER,
   REFERENCE            VARCHAR(32)                     not null,
   INSURANCE_NAME       VARCHAR(50)                     not null,
   SHOW_IN_SYSTEM       CHAR(1)                         not null,
   BUSINESS_PROCESS     VARCHAR(12)                     not null,
   constraint PK_INSURANCE_LINKS primary key (ID)
)
/

create sequence S_INSURANCE_LINKS
/

create unique index UNIQ_INS_NUMBER on INSURANCE_LINKS (
   REFERENCE ASC,
   LOGIN_ID ASC
)
/

/*==============================================================*/
/* Table: USER_TIMINGS                                          */
/*==============================================================*/
create table USER_TIMINGS  (
   USER_ID              INTEGER                         not null,
   ACCOUNT_LIST_LAST_UPDATE TIMESTAMP,
   CARD_LIST_LAST_UPDATE TIMESTAMP,
   LOAN_LIST_LAST_UPDATE TIMESTAMP,
   constraint PK_USER_TIMINGS primary key (USER_ID)
)
/

create sequence S_USER_TIMINGS
/

comment on column USER_TIMINGS.USER_ID is
'ID �������'
/

comment on column USER_TIMINGS.ACCOUNT_LIST_LAST_UPDATE is
'����� ���������� ���������� ������ ������ ������� ������� �� ���'
/

comment on column USER_TIMINGS.CARD_LIST_LAST_UPDATE is
'����� ���������� ���������� ������ ���� ������� ������� �� ���'
/

comment on column USER_TIMINGS.LOAN_LIST_LAST_UPDATE is
'����� ���������� ���������� ������ �������� ������� ������� �� ���'
/

alter table USER_TIMINGS
   add constraint FK_USER_TIMINGS foreign key (USER_ID)
      references USERS (ID)
      on delete cascade
/

-- ����� �������: 48338
-- �����������: ���������� �������� ��� � ����. ������ ��.
/*==============================================================*/
/* Table: SMS_RESOURCES                                         */
/*==============================================================*/
create table SMS_RESOURCES  (
   ID                   INTEGER                         not null,
   SMS_TYPE             VARCHAR2(13)                    not null,
   KEY                  VARCHAR2(255)                   not null,
   TEXT                 CLOB                            not null,
   CUSTOM               CHAR(1),
   CHANNEL              VARCHAR2(20),
   DESCRIPTION          VARCHAR2(255),
   LAST_MODIFIED        TIMESTAMP,
   PREVIOUS_TEXT        CLOB,
   EMPLOYEE_LOGIN_ID    INTEGER,
   VARIABLES            VARCHAR2(4000),
   constraint PK_SMS_RESOURCES primary key (ID)
)
/

create sequence S_SMS_RESOURCES
/

create index INDEX_SMS_TYPE on SMS_RESOURCES (
   SMS_TYPE ASC
)
/

create index INDEX_SMS_CHANNEL_KEY on SMS_RESOURCES (
   KEY ASC,
   CHANNEL ASC
)
/

-- ����� �������: 48577
-- �����������: ����. ErmbProfile: PK = FK �� USER.ID

drop table ERMB_CLIENT_PHONE;
drop table ERMB_PROFILE;

/*==============================================================*/
/* Table: ERMB_PROFILE                                          */
/*==============================================================*/
create table ERMB_PROFILE  (
   PERSON_ID            INTEGER                         not null,
   SERVICE_STATUS       VARCHAR2(13)                    not null,
   END_SERVICE_DATE     TIMESTAMP,
   FOREG_PRODUCT_ID     INTEGER,
   CONNECTION_DATE      TIMESTAMP,
   NEW_PRODUCT_NOTIFICATION CHAR(1)                        default '0' not null,
   DAYS_OF_WEEK         VARCHAR2(28),
   TIME_START           VARCHAR2(10),
   TIME_END             VARCHAR2(10),
   TIME_ZONE            INTEGER                         not null,
   ERMB_TARIF_ID        INTEGER                         not null,
   CLIENT_CATEGORY      CHAR(1),
   LAST_REQUEST_TIME    TIMESTAMP,
   FAST_SERVICE         CHAR(1)                        default '0' not null,
   ADVERTISING          CHAR(1)                        default '1' not null,
   VERSION              INTEGER                        default 1 not null,
   CONFIRM_VERSION      INTEGER                        default 0 not null,
   DEPOSITS_TRANSFER    CHAR(1)                        default '1' not null,
   constraint PK_ERMB_PROFILE primary key (PERSON_ID)
)
/

create index "DXFK_ERMB_PROFILE_TARIF" on ERMB_PROFILE
(
   ERMB_TARIF_ID
)
/

alter table ERMB_PROFILE
   add constraint FK_ERMB_PROFILE_TARIF foreign key (ERMB_TARIF_ID)
      references ERMB_TARIF (ID)
/

alter table ERMB_PROFILE
   add constraint FK_ERMB_PROFILE_USERS foreign key (PERSON_ID)
      references USERS (ID)
/

/*==============================================================*/
/* Table: ERMB_CLIENT_PHONE                                     */
/*==============================================================*/
create table ERMB_CLIENT_PHONE  (
   ID                   INTEGER                         not null,
   PROFILE_ID           INTEGER                         not null,
   IS_MAIN              CHAR(1)                        default '0' not null,
   PHONE_NUMBER         VARCHAR2(20)                    not null,
   MOBILE_PHONE_OPERATOR VARCHAR2(100)                  default '0' not null,
   constraint PK_ERMB_CLIENT_PHONE primary key (ID)
)
/

create unique index UNIQ_ERMB_PHONE_NUMBER on ERMB_CLIENT_PHONE (
   PHONE_NUMBER ASC
)
/

create index "DXFK_ERMB_CLIENT_PHONE_PROFILE" on ERMB_CLIENT_PHONE
(
   PROFILE_ID
)
/

alter table ERMB_CLIENT_PHONE
   add constraint FK_ERMB_CLIENT_PHONE_PROFILE foreign key (PROFILE_ID)
      references ERMB_PROFILE (PERSON_ID)
      on delete cascade
/

-- ����� �������: 48630
-- �����������: ���� (������������ �����): ����� ���������� �������� IMSI
/*==============================================================*/
/* Table: ERMB_CHECK_IMSI_RESULTS                               */
/*==============================================================*/
create table ERMB_CHECK_IMSI_RESULTS  (
   SMS_UID              VARCHAR(32)                     not null,
   RESULT               CHAR(1),
   constraint PK_ERMB_CHECK_IMSI_RESULTS primary key (SMS_UID)
)
/

create sequence S_ERMB_CHECK_IMSI_RESULTS
/

-- ����� �������: 48710
-- �����������: ������� ������
/*==============================================================*/
/* Table: EXCEPTION_ENTRY                                       */
/*==============================================================*/
create table EXCEPTION_ENTRY  (
   ID                   INTEGER                         not null,
   KIND                 CHAR(1)                         not null,
   HASH                 VARCHAR2(40)                    not null,
   OPERATION            VARCHAR2(160),
   APPLICATION          VARCHAR2(20)                    not null,
   DETAIL               CLOB                            not null,
   MESSAGE              VARCHAR2(2000),
   SYSTEM               VARCHAR2(16),
   ERROR_CODE           VARCHAR2(20),
   constraint PK_EXCEPTION_ENTRY primary key (ID)
)
/

create sequence S_EXCEPTION_ENTRY
/

create unique index IND_EXCEPTION_ENTRY_HASH on EXCEPTION_ENTRY (
   HASH ASC
)
/

-- ����� �������: 48771
-- �����������: �������������� ������ �����
/*==============================================================*/
/* Table: PAYMENT_SERV_PARENTS                                  */
/*==============================================================*/
create table PAYMENT_SERV_PARENTS  (
   SERVICE_ID           INTEGER                         not null,
   PARENT_ID            INTEGER                         not null,
   constraint PK_PAYMENT_SERV_PARENTS primary key (SERVICE_ID, PARENT_ID)
)
/

create sequence S_PAYMENT_SERV_PARENTS
/

create index "DXFK_PAY_SER_TO_PARENT" on PAYMENT_SERV_PARENTS
(
   PARENT_ID
)
/

alter table PAYMENT_SERV_PARENTS
   add constraint FK_PAY_SER_TO_PARENT foreign key (PARENT_ID)
      references PAYMENT_SERVICES (ID)
/


create index "DXFK_PAY_SER_TO_SERV" on PAYMENT_SERV_PARENTS
(
   SERVICE_ID
)
/

alter table PAYMENT_SERV_PARENTS
   add constraint FK_PAY_SER_TO_SERV foreign key (SERVICE_ID)
      references PAYMENT_SERVICES (ID)
/

-- ����� �������: 48892
-- �����������: ����������� �������� �������������� ��������� ������� �� ������� (�������� ��������)
/*==============================================================*/
/* Table: EXCEPTION_COUNTERS                                    */
/*==============================================================*/
create table EXCEPTION_COUNTERS  (
   EXCEPTION_HASH       VARCHAR2(40)                    not null,
   EXCEPTION_DATE       TIMESTAMP                       not null,
   EXCEPTION_COUNT      INTEGER                         not null,
   constraint PK_EXCEPTION_COUNTERS primary key (EXCEPTION_HASH, EXCEPTION_DATE)
)
/

create sequence S_EXCEPTION_COUNTERS
/


--����� �������: 49031
--�����������:  ������������� ����������� ������������� �� �����. ������ ��
/*==============================================================*/
/* Table: DEPARTMENTS_RECORDING                                 */
/*==============================================================*/
create table DEPARTMENTS_RECORDING  (
   ID                   INTEGER                         not null,
   TB_ERIB              VARCHAR2(3)                     not null,
   OSB_ERIB             VARCHAR2(4),
   OFFICE_ERIB          VARCHAR2(7),
   TB_SPOOBK2           VARCHAR2(3)                     not null,
   OSB_SPOOBK2          VARCHAR2(4),
   OFFICE_SPOOBK2       VARCHAR2(7),
   constraint PK_DEPARTMENTS_RECORDING primary key (ID)
)
/

create sequence S_DEPARTMENTS_RECORDING
/

/*==============================================================*/
/* Index: SPOOBK2_TB_OSB                                        */
/*==============================================================*/
create index SPOOBK2_TB_OSB on DEPARTMENTS_RECORDING (
   TB_SPOOBK2 ASC,
   LTRIM(OSB_SPOOBK2, '0') ASC
)
/

/*==============================================================*/
/* Table: DEPARTMENTS_RECORDING_TMP                             */
/*==============================================================*/
create table DEPARTMENTS_RECORDING_TMP  (
   ID                   INTEGER                         not null,
   TB_ERIB              VARCHAR2(3)                     not null,
   OSB_ERIB             VARCHAR2(4),
   OFFICE_ERIB          VARCHAR2(7),
   TB_SPOOBK2           VARCHAR2(3)                     not null,
   OSB_SPOOBK2          VARCHAR2(4),
   OFFICE_SPOOBK2       VARCHAR2(7),
   constraint PK_DEPARTMENTS_RECORDING_TMP primary key (ID)
)
/

create sequence S_DEPARTMENTS_RECORDING_TMP
/

--����� �������: 49215
--�����������:  ��������������
/*==============================================================*/
/* Table: CLIENTS_BUDGET                                        */
/*==============================================================*/
create table CLIENTS_BUDGET  (
   LOGIN_ID             INTEGER                         not null,
   CATEGORY_ID          INTEGER                         not null,
   BUDGET               NUMBER(15,4),
   constraint PK_CLIENTS_BUDGET primary key (LOGIN_ID, CATEGORY_ID)
)
/

create sequence S_CLIENTS_BUDGET
/

create index "DXFK_CLIENTS_BUDGET_TO_CATEGOR" on CLIENTS_BUDGET
(
   CATEGORY_ID
)
/

alter table CLIENTS_BUDGET
   add constraint FK_CLIENTS__FK_CLIENT_CARD_OPE foreign key (CATEGORY_ID)
      references CARD_OPERATION_CATEGORIES (ID)
      on delete cascade
/


create index "DXFK_CLIENTS_BUDGET_TO_LOGINS" on CLIENTS_BUDGET
(
   LOGIN_ID
)
/

alter table CLIENTS_BUDGET
   add constraint FK_CLIENTS__FK_CLIENT_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
/

--����� �������: 49220
--����� ������: 1.18
--�����������:  ������������: ������� ���������� ������ ��� ���� �� ��������(����� ������� �������� ��� ������ �������������)
/*==============================================================*/
/* Table: EXTENDED_DESCRIPTION_DATA                             */
/*==============================================================*/
create table EXTENDED_DESCRIPTION_DATA  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(50)                    not null,
   CONTENT              CLOB                            not null,
   constraint PK_EXTENDED_DESCRIPTION_DATA primary key (ID)
)
/

create sequence S_EXTENDED_DESCRIPTION_DATA
/

create unique index UNQ_NAME_EXT_DESC on EXTENDED_DESCRIPTION_DATA (
   NAME ASC
)
/

insert into EXTENDED_DESCRIPTION_DATA(ID, NAME, CONTENT) values (S_EXTENDED_DESCRIPTION_DATA.nextval, 'aeroexpress-rule', EMPTY_CLOB())
/

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h1>
                                ������� �������� ����� �� ���������� ����������, ������ ����� � ������ � �������������� ��� �������������
                            </h1>
                            <br/>
                            <h2>
                                <b>1. ����� ���������</b>
                            </h2>
                            1.1	��������� ������� ����������� � ������������ � ������������ ��������� ����������� ����������:
                            &'||'mdash; ������������ ������ �� �� 10.01.2003 ���� � 18-�� ������ ���������������� ���������� �Ի;<br/>
                            &'||'mdash; ������ �������� ����� �� ���������� �� ��������������� ���������� ����������, � ����� ������, ������ � ����������� ��� ������, ��������, �������� � ���� ����, �� ��������� � �������������� ������������������� ������������, ������������ �������������� ������������� �� �� 2 ����� 2005 ���� � 111;<br/>
                            &'||'mdash; ������ ��������� ����������, ������ � ����������� �� ����������� ��������������� ����������, ������������ �������� ��� �� �� 26 ���� 2002 ���� N 30;<br/>
                            &'||'mdash; ���� ����������-�������� ����� ���������� ���������.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
/

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>2. ��������� ����������</b>
                            </h2>
                            2.1	��� ������� � ������������� ��� ������������� �������� ������:<br/>
                            2.2	���������� ����� ��� ������� �������, ���� ������������ ����� � ������������� �����;<br/>
                            2.3	��������� ������������ ��������� �������� (�����) � ������� ����� ���� ���������� ������������� �� ������� ������ ����� ����� �������� ��������� ���������� (�������). ��������, �� ������������ ��������� �������� (�����) ��� ������ ����� ����� ��������, ��������� ����������� � ������ �������� ��������� �������;<br/>
                            2.4	����������� ��������� �������� (�����) �����, �������������� �������� � ���������� �������� �������� (���������������);<br/>
                            2.5	��� �������� ��������� ���������� (�������) � �������������, ���������� ����������� ��������� ������� (��������������) ���������, �������������� ����� �� ������, (���� �������� ����� �� ��� �����).<br/>
                            2.6	�������� ������ ���������� ��������� �������� (�����) � ���� ���������� (��� ������� ����� ������ �� ����������� ���������) � ������� ��������� ������ �� ���������� ��������� ���������� (�������) � �������������� ��� ������������� �������� ������������ ������.<br/>
                            2.7	��������� �������� (�����) ��� ������� ������� ������������ �� ���� ������� � ����� ����������� � ������������ � �����, ��������� � ��. ���� ���� �������� ���������� ��������� (������) ������������� � ������ ���������� ��������� � ����, ��������� �������� (�����) �������� �������������� �� �������� ��������� � ����� ����������.<br/>
                            2.8	�������� ����� ����� ��������� ��������� ����� � �������� �� ������ 5 (����) ���. ��� ���������� � ���������� ����� � �������� �� 5 (����) �� 7 (����) ��� ��������������� � ���� ������� ������������� ������� ������. ��� ������������� �������� ������������ �������� �����, ���������� ��������� ��� �� ������� �������, ���������� �������� ������� (��������������) ������ ����������� ������������ ��������������� ����������, �������������� ������� �������.<br/>
                            2.9	������� � ������������ ������ (� �.�. ������������ ����� ���������� ����), ����������� ����� ������������� ������������ ���������, � ��������������  ��� ������������� �� �������������. ��������, ������������ ����� ������, ��������� ����������� � � ���� ��������� ������ ��������� �������, �������� �������������� ������ ��� ������� ���� ���������.<br/>
                            2.10 �� ���� ����� �������� ����� ����� ������ ������ ���� �����. ��� ���������� ��������� ������� ���� � ������ ����������� ������ ���������� ����, ��� ���� ��������� ������� �� ����������. <br/>
                            2.11 �������� ����� �������� ������ ��������� ������� � ������ ������������������ �������� � �������� �������������� ����� ��� �� 1 (����) ���. ��� ���� � ������ ������� ������� ������� �� ���������������� ������� ��� ������� ������� �� ������������. ������� ������� �� ���������������� ������������ ������� ������������ � ������� � �������, ��������������� ���������� ��������� ����������, ������ � ����������� �� ����������� ��������������� ����������,  ������������� �������� ��� �� �� 26 ���� 2002 ���� N 30.<br/>
                            2.12 ���������� ���������� ��������� (������) � ������������ ����, �������� ����� ������ ��������� ������� �� ������� ��� ����������� �������, ������������ � �������, ������������� � ������������ � ������������ ������������ ���������������� ���������� ���������.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
/

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>3. ��������� ������ ����� � ������</b>
                            </h2>
                            3.1	������ �������� ����� ����� ��������� ��������� � ����� �� ���� ��������� �������� (�����) ������ ��� ������� ������ ����� ����� �� ����� 36 ��, ������ ������� � ����� 3-� ��������� �� ������ ��������� 180 ��. ��������� ������ ����� ���������� �� ���� � ���� �������� ������ ���� ��������� �� �������� ������ ������ �������������. ������������� � ������������� ����� ����������� ������ �� 50 �� ������ ����� �� �������������� �����, �������� �������������� ������.<br/>
                            3.2	�� ������ ������ �������� ��������, ����� � ����, ������ ���������� � �� ����������� ���� (��������� � ������� ������) ��������� �������������� �����, �������� �������������� ������.<br/>
                            3.3	������ �������� ��������, ������ � ����� ������ ������������ � ������, �������, ����������� � ���������� �� ������, ��������������� ��� ���������� ������ �����. ������ ������� �����, � ��� ����� ��������� ����������� � ������� ������ (�� ����� ���� �����) � ����������� � � �������� ��� ����������� �� ���������� ��� ��������������, ������� ������ ���������� ���������� ���������-�������������� ������ � ������ ������. ��������� ������ �������� ��������, ����� � ���� ����������� ����� ������������� ����� ������� ������ ����� ��� ������� ������������ �������.<br/>
                            3.4	������ ��������� �������� ��� ���� �����-����������� ���������.<br/>
                            3.5	� ��������� ������ ������ � ���� ������������� ����� �������  ����������� ��������, ������� � ������ ���������� ��������� � ���������� ������ � ������������ �����������, �� ������������ �� ������ 180 ��.<br/>
                            3.6	����������� ��������� �� �������������� ����� �����������, �������, �����- � ������������, ������� �� ����� 3-� ��������� ��������� 180 ��, ���������� �� ������� � ��������� ������ �����, �� ����� ������ �������� �� ��������� �������� (�����). ����� �� ������ ��������� ���������, ���������� �� ���� ��������� �������� �������������� ������.<br/>
                            3.7	��������� ����������� ��������� ��������� � ����� ����� ������������� ����� ������� ������ ����� ��������, ������� �������, �������, ���� � ����� � ���, ������, �����������, ����, � ����� ������ ������ ����, ������ ������� �� ����� 3-� ��������� �� ��������� 100 ��.<br/>
                            3.8	����������� ����������� � ����������� ������ �����, ����������� ����������, �������� ������������ ���������.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
/

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>4.	���������� ���������</b>
                            </h2>
                            4.1	� �������������� ��� ������������� �����������:<br/>
                            4.2	���������� � �������, � ������, � ����������, ����������, ������� ����� ��������� ����������, ������, ���������� � ���������� �������.<br/>
                            4.3	��������� ����� ��� ������ ����� �� ������������ �������.<br/>
                            4.4	���������� ���� (��������), ������� ����� ��������� ��� ���������� �����, � ����� ���������, �����������, �����������, ���������������������, ���������� � ������ ������� ��������. ������������� ������ ��� ��������� � �������� ������ ����� ������ ���������� � �����, ������ ��� ����������� ������� � ����������� ��������� �������� �� ��������.<br/>
                            4.5	��������� �������� ������� � ���������� � ��������� ���������.<br/>
                            4.6	����������� �������� ��� �������� �������������� ������ �� ����������, ��������� ����� �� ����� �������� �������������.<br/>
                            4.7	���������� �������������� ������������, ������ ����������� � �������.<br/>
                            4.8	�������� ����������� ������ ����������, ������ � �������� ����, ������.<br/>
                            4.9	������������� ��� ���������� ����� ����-������.<br/>
                            4.10 ������ � ������� � ��������.<br/>
                            4.11 �������� ����� ���� ������ �� �������������:<br/>
                            4.12 ����������� ��� � ������������ ������, ��������������� ������, ���� �� ��� ������� � ����� ��� � ���� ���������� �������� ������� �������, ������������ ������� � ������ ����������� ������ ����������;<br/>
                            4.13 ����������� ��������� ��������� (����������������), ���� �������� ��������� ��� ���������� ��������� (������) ��� �� ������, ������������ �� ������� ����������� ����� � ������������ �������� ��������� ������� �������� �������������� �������;<br/>
                            4.14 ������������ ����������� - � ������ ������� ���������, �������������� ����������� ��� ���������� ������� ��� ���������� �������� ������ ����������, ���� ��� ����������� ��������� ��� ��������;<br/>
                            4.15 � ���� �������, ������������� ����������������� ���������� ���������.<br/>
                            <br/>' where NAME = 'aeroexpress-rule'
/

update EXTENDED_DESCRIPTION_DATA set CONTENT=CONTENT||'<h2>
                                <b>5.	����� �������� �������� (���������������)</b>
                            </h2>
                            5.1	���������� �������� ������� (��������������) ����� �����:<br/>
                            &'||'mdash; ���������� � ����������, ����������� ��� ��������� ���������� (�������), ��������� ������� �������� �������������� ������ � ���� �� �������� ������ �� ���������� ��������� ���������� (�������).<br/>
                            &'||'mdash; ���������� � ���������� ��������� ������� � ������������� �������� �� ������ ��� ������ ����� � �������� ������ 5 (����) ���, �� ������ ��������� ���� ������ � ������������ ������ �����, � ����� ���� �� �������� ������ �� ������� �������.<br/>
                            &'||'mdash; ��� ������ ��������� �� ������ �������, ��������� ���� � ������� ��������� �� �������������.<br/>' where NAME = 'aeroexpress-rule'
/

/*==============================================================*/
/* Table: CONFIRM_BEANS                                         */
/*==============================================================*/
create table CONFIRM_BEANS  (
   ID                           INTEGER                         not null,
   LOGIN_ID                     INTEGER                         not null,
   PRIMARY_CONFIRM_CODE         VARCHAR2(1024)                  not null,
   SECONDARY_CONFIRM_CODE       VARCHAR2(1024),
   EXPIRE_TIME                  TIMESTAMP                       not null,
   CONFIRMABLE_TASK_CLASS       VARCHAR2(256)                   not null,
   CONFIRMABLE_TASK_BODY        VARCHAR2(1024)                  not null,
   constraint PK_CONFIRM_BEANS primary key (ID)
)
/

create sequence S_CONFIRM_BEANS
/

comment on table CONFIRM_BEANS is
'������ � ��������� ��������, ��������� �������������'
/

comment on column CONFIRM_BEANS.LOGIN_ID is
'�����-id ������������, �� ���� �������� ������������ �������������'
/

comment on column CONFIRM_BEANS.PRIMARY_CONFIRM_CODE is
'��� �������������'
/

comment on column CONFIRM_BEANS.EXPIRE_TIME is
'���� ��������� �������� ���� �������������'
/

comment on column CONFIRM_BEANS.CONFIRMABLE_TASK_CLASS is
'����� �������������� ��������'
/

comment on column CONFIRM_BEANS.CONFIRMABLE_TASK_BODY is
'������ �������������� ��������'
/

/*==============================================================*/
/* Index: IDX_CONFIRM_EXPIRE                                    */
/*==============================================================*/
create index IDX_CONFIRM_EXPIRE on CONFIRM_BEANS (
   EXPIRE_TIME ASC
)
/

/*==============================================================*/
/* Index: UNIQ_CONFIRM_LOGIN_CODE                               */
/*==============================================================*/
create index UNIQ_CONFIRM_LOGIN_CODE on CONFIRM_BEANS (
   LOGIN_ID ASC,
   (GREATEST(PRIMARY_CONFIRM_CODE, SECONDARY_CONFIRM_CODE) || LEAST(PRIMARY_CONFIRM_CODE, SECONDARY_CONFIRM_CODE)) ASC
)
/

-- ����� �������: 49352
-- �����������: ����. ������ �� ������������ �������� ���.
/*==============================================================*/
/* Table: ERMB_SMS_INBOX                                        */
/*==============================================================*/
create table ERMB_SMS_INBOX  (
   RQ_UID               VARCHAR2(32)                    not null,
   RQ_TIME              TIMESTAMP                       not null,
   PHONE                VARCHAR2(20)                    not null,
   TEXT                 VARCHAR2(1024)                  not null,
   constraint PK_ERMB_SMS_INBOX primary key (RQ_UID)
)
/

create sequence S_ERMB_SMS_INBOX
/

create unique index UI_ERMB_SMS_INBOX on ERMB_SMS_INBOX (
   PHONE ASC,
   TEXT ASC
)
/

create index IDX_RQ_TIME on ERMB_SMS_INBOX (
   RQ_TIME ASC
)
/


create table CLIENT_EXTENDED_LOGGING  (
   LOGIN_ID             INTEGER not null,
   START_DATE           TIMESTAMP                       not null,
   END_DATE           TIMESTAMP,
   constraint PK_CLIENT_EXTENDED_LOGGING primary key (LOGIN_ID)
)
/

create table LIST_PROPERTIES (
   ID              INTEGER     not null,
   PROPERTY_ID     INTEGER     not null,
   VALUE              VARCHAR2(200),
   constraint PK_LIST_PROPERTIES primary key (ID)
)
/

create sequence S_LIST_PROPERTIES
/

create index "DXFK_LIST_PROPERTIES" on LIST_PROPERTIES
(
   PROPERTY_ID
)
/

alter table LIST_PROPERTIES
   add constraint FK_LIST_PROPERTIES foreign key (PROPERTY_ID)
      references PROPERTIES (ID)
      on delete cascade
/


/*==============================================================*/
/* Table: PAYMENTS_DOCUMENTS                                    */
/*==============================================================*/
create table PAYMENTS_DOCUMENTS  (
   ID                   INTEGER                         not null,
   KIND                 CHAR(1)                         not null,
   CHANGED              TIMESTAMP                       not null,
   EXTERNAL_ID          VARCHAR(80),
   DOCUMENT_NUMBER      INTEGER,
   CLIENT_CREATION_DATE TIMESTAMP                       not null,
   CLIENT_CREATION_CHANNEL CHAR(1)                         not null,
   CLIENT_OPERATION_DATE TIMESTAMP,
   CLIENT_OPERATION_CHANNEL CHAR(1),
   EMPLOYEE_OPERATION_DATE TIMESTAMP,
   EMPLOYEE_OPERATION_CHANNEL CHAR(1),
   CLIENT_GUID          VARCHAR(24)                     not null,
   CREATED_EMPLOYEE_GUID VARCHAR(24),
   CREATED_EMPLOYEE_FULL_NAME VARCHAR2(250),
   CONFIRMED_EMPLOYEE_GUID VARCHAR(24),
   CONFIRMED_EMPLOYEE_FULL_NAME VARCHAR2(250),
   OPERATION_UID        VARCHAR2(32),
   STATE_CODE           VARCHAR2(25)                    not null,
   STATE_DESCRIPTION    VARCHAR2(265),
   FORM_TYPE            VARCHAR(100)                    not null,
   CHARGEOFF_RESOURCE   VARCHAR(30),
   DESTINATION_RESOURCE VARCHAR(30),
   GROUND               VARCHAR2(1024),
   CHARGEOFF_AMOUNT     NUMBER(19,4),
   CHARGEOFF_CURRENCY   CHAR(3),
   DESTINATION_AMOUNT   NUMBER(19,4),
   DESTINATION_CURRENCY CHAR(3),
   SUMM_TYPE            VARCHAR2(50),
   RECEIVER_NAME        VARCHAR2(256),
   INTERNAL_RECEIVER_ID INTEGER,
   RECEIVER_POINT_CODE  VARCHAR2(128),
   EXTENDED_FIELDS      CLOB,
   REGION_ID            VARCHAR2(4),
   AGENCY_ID            VARCHAR2(4),
   BRANCH_ID            VARCHAR2(6),
   CLASS_TYPE           VARCHAR2(200),
   TEMPLATE_NAME        VARCHAR2(286)                   not null,
   TEMPLATE_USE_IN_MAPI CHAR(1)                         not null,
   TEMPLATE_USE_IN_ATM  CHAR(1)                         not null,
   TEMPLATE_USE_IN_ERMB CHAR(1)                         not null,
   TEMPLATE_USE_IN_ERIB CHAR(1)                         not null,
   TEMPLATE_IS_VISIBLE  CHAR(1)                         not null,
   TEMPLATE_ORDER_IND   INTEGER                         not null,
   TEMPLATE_STATE_CODE  VARCHAR2(50)                    not null,
   TEMPLATE_STATE_DESCRIPTION VARCHAR2(128),
   constraint PK_PAYMENTS_DOCUMENTS primary key (ID)
)
/
create sequence S_PAYMENTS_DOCUMENTS start with 400000000
/

create index IND_TEMPLATE_OWNER on PAYMENTS_DOCUMENTS (
   CLIENT_GUID ASC,
   CLIENT_CREATION_DATE ASC
)
/

create unique index IND_U_TEMPLATE_NAME on PAYMENTS_DOCUMENTS (
   CLIENT_GUID ASC,
   TEMPLATE_NAME ASC
)
/

/*==============================================================*/
/* Table: PAYMENTS_DOCUMENTS_EXT                                */
/*==============================================================*/
create table PAYMENTS_DOCUMENTS_EXT  (
   ID                   INTEGER                         not null,
   KIND                 VARCHAR(50)                     not null,
   NAME                 VARCHAR2(64)                    not null,
   VALUE                VARCHAR2(4000),
   PAYMENT_ID           INTEGER                         not null,
   constraint PK_PAYMENTS_DOCUMENTS_EXT primary key (ID)
)
/

create sequence S_PAYMENTS_DOCUMENTS_EXT start with 25000000000
/

create index "DXFK_TEMPLATE_EXT_TO_TEMPLATE" on PAYMENTS_DOCUMENTS_EXT
(
   PAYMENT_ID
)
/

alter table PAYMENTS_DOCUMENTS_EXT
   add constraint FK_TEMPLATE_EXT_TO_TEMPLATE foreign key (PAYMENT_ID)
      references PAYMENTS_DOCUMENTS (ID)
      on delete cascade
/


/*==============================================================*/
/* Table: PROVIDER_SMS_ALIAS                                    */
/*==============================================================*/
create table PROVIDER_SMS_ALIAS  (
   ID                   INTEGER                         not null,
   NAME                 VARCHAR2(10)                    not null,
   SERVICE_PROVIDER_ID  INTEGER                         not null,
   constraint PK_PROVIDER_SMS_ALIAS primary key (ID)
)
/
create sequence S_PROVIDER_SMS_ALIAS
/

create unique index PROVIDER_SMS_ALIAS_NAME on PROVIDER_SMS_ALIAS (
   NAME ASC
)
/

/*==============================================================*/
/* Table: PROVIDER_SMS_ALIAS_FIELD                              */
/*==============================================================*/
create table PROVIDER_SMS_ALIAS_FIELD  (
   ID                   INTEGER                         not null,
   EDITABLE             CHAR(1)                         not null,
   VALUE                NVARCHAR2(2000),
   PROVIDER_SMS_ALIAS_ID INTEGER                         not null,
   FIELD_DESCRIPTION_ID INTEGER                         not null,
   constraint PK_PROVIDER_SMS_ALIAS_FIELD primary key (ID)
)
/

create sequence S_PROVIDER_SMS_ALIAS_FIELD
/

create index "DXFK_ALIAS_FI_TO_ALIAS" on PROVIDER_SMS_ALIAS_FIELD
(
   PROVIDER_SMS_ALIAS_ID
)
/

alter table PROVIDER_SMS_ALIAS_FIELD
   add constraint FK_PROVIDER_FK_ALIAS__PROVIDER foreign key (PROVIDER_SMS_ALIAS_ID)
      references PROVIDER_SMS_ALIAS (ID)
/


create index "DXFK_ALIAS_FI_TO_FIELD" on PROVIDER_SMS_ALIAS_FIELD
(
   FIELD_DESCRIPTION_ID
)
/

alter table PROVIDER_SMS_ALIAS_FIELD
   add constraint FK_PROVIDER_FK_ALIAS__FIELD_DE foreign key (FIELD_DESCRIPTION_ID)
      references FIELD_DESCRIPTIONS (ID)
/


/*==============================================================*/
/* Table: PAYMENT_SERVICES_OLD                                  */
/*==============================================================*/
create table PAYMENT_SERVICES_OLD  (
   ID                   INTEGER                         not null,
   CODE                 VARCHAR2(50)                    not null,
   NAME                 nvarchar2(128)                  not null,
   PARENT_ID            INTEGER,
   IMAGE_ID             INTEGER,
   POPULAR              CHAR(1)                         not null,
   DESCRIPTION          VARCHAR2(512),
   SYSTEM               CHAR(1)                         not null,
   PRIORITY             INTEGER,
   VISIBLE_IN_SYSTEM    CHAR(1)                         not null,
   IMAGE_NAME           VARCHAR2(128)                   not null,
   constraint PK_PAYMENT_SERVICES_OLD primary key (ID)
)
/

create sequence S_PAYMENT_SERVICES_OLD
/

create index "DXFK_P_SERVICE_TO_P_SERVICE_OL" on PAYMENT_SERVICES_OLD
(
   PARENT_ID
)
/

alter table PAYMENT_SERVICES_OLD
   add constraint FK_P_SERVICE_TO_P_SERVICE_OLD foreign key (PARENT_ID)
      references PAYMENT_SERVICES_OLD (ID)
/


/*==============================================================*/
/* Table: SERV_PROV_PAYM_SERV_OLD                               */
/*==============================================================*/
create table SERV_PROV_PAYM_SERV_OLD  (
   PAYMENT_SERVICE_ID   INTEGER                         not null,
   SERVICE_PROVIDER_ID  INTEGER                         not null,
   LIST_INDEX           INTEGER,
   constraint PK_SERV_PROV_PAYM_SERV_OLD primary key (PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID)
)
/
create sequence S_SERV_PROV_PAYM_SERV_OLD
/

create index "DXFK_PROV_PAY_SER_TO_PAY_OLD" on SERV_PROV_PAYM_SERV_OLD
(
   PAYMENT_SERVICE_ID
)
/

alter table SERV_PROV_PAYM_SERV_OLD
   add constraint FK_PROV_PAY_SER_TO_PAY_OLD foreign key (PAYMENT_SERVICE_ID)
      references PAYMENT_SERVICES_OLD (ID)
/


create index "DXFK_PROV_PAY_SER_TO_PROV_OLD" on SERV_PROV_PAYM_SERV_OLD
(
   SERVICE_PROVIDER_ID
)
/

alter table SERV_PROV_PAYM_SERV_OLD
   add constraint FK_PROV_PAY_SER_TO_PROV_OLD foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
/


create table TEMP_SP_SERVICES
(
    CODE        VARCHAR2(30) NULL,
    NAME        VARCHAR2(300) NULL,
    ICO         VARCHAR2(100) NULL,
    PARENT      VARCHAR2(30) NULL,
    PRIORITY    VARCHAR2(30) NULL,
	PRIMARY KEY(CODE)
)
/

create table TEMP_SP_UNVISIBLE
(    
    ID          VARCHAR2(30) NOT NULL,
    EXTERNAL_ID  VARCHAR2(300) NOT NULL,
    PAYMENTS    NUMBER(10) NULL,    
    PRIMARY KEY(ID)
)
/
