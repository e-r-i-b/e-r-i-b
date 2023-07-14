--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
-- Номер ревизии: 61979
-- Номер версии: 1.18
-- Комментарий: ФОС. Синхронизация справочников площадок и тематик сообщений
create table CSAADMIN_IKFL.CONTACT_CENTER_AREAS 
(
	ID                   INTEGER              not null,
	UUID                 VARCHAR2(32)         not null,
	AREA_NAME            VARCHAR2(50)         not null,
	constraint PK_CONTACT_CENTER_AREAS primary key (ID) using index (
		create unique index CSAADMIN_IKFL.PK_CONTACT_CENTER_AREAS on CSAADMIN_IKFL.CONTACT_CENTER_AREAS (ID) tablespace CSA_ADM_IDX
	)
) tablespace CSA_ADM_DATA
/
create sequence CSAADMIN_IKFL.S_CONTACT_CENTER_AREAS start with 100
/

create unique index CSAADMIN_IKFL.I_CONTACT_CENTER_AREAS_UUID on CSAADMIN_IKFL.CONTACT_CENTER_AREAS (
   UUID ASC
) tablespace CSA_ADM_IDX
/
create unique index CSAADMIN_IKFL.I_CONTACT_CENTER_AREAS_NAME on CSAADMIN_IKFL.CONTACT_CENTER_AREAS (
   AREA_NAME ASC
) tablespace CSA_ADM_IDX
/

create table CSAADMIN_IKFL.C_CENTER_AREAS_DEPARTMENTS 
(
	C_C_AREA_ID          INTEGER              not null,
	TB                   VARCHAR2(4)          not null,
	constraint PK_C_CENTER_AREAS_DEPARTMENTS primary key (C_C_AREA_ID, TB) using index (
		create unique index CSAADMIN_IKFL.PK_C_CENTER_AREAS_DEPARTMENTS on CSAADMIN_IKFL.C_CENTER_AREAS_DEPARTMENTS (C_C_AREA_ID, TB) tablespace CSA_ADM_IDX
	)
) tablespace CSA_ADM_DATA
/
create sequence CSAADMIN_IKFL.S_C_CENTER_AREAS_DEPARTMENTS
/

create unique index CSAADMIN_IKFL.I_C_CENTER_AREAS_DEPARTMENTS on CSAADMIN_IKFL.C_CENTER_AREAS_DEPARTMENTS (
   TB ASC
) tablespace CSA_ADM_IDX
/

alter table CSAADMIN_IKFL.C_CENTER_AREAS_DEPARTMENTS
   add constraint FK_C_C_AREA_DEP_TO_C_C_AREA foreign key (C_C_AREA_ID)
      references CSAADMIN_IKFL.CONTACT_CENTER_AREAS (ID)
      on delete cascade
/

create table CSAADMIN_IKFL.MAIL_SUBJECTS 
(
	ID                   INTEGER              not null,
	UUID                 VARCHAR2(32)         not null,
	DESCRIPTION          VARCHAR2(50)         not null,
	constraint PK_MAIL_SUBJECTS primary key (ID) using index (
		create unique index CSAADMIN_IKFL.PK_MAIL_SUBJECTS on CSAADMIN_IKFL.MAIL_SUBJECTS (ID) tablespace CSA_ADM_IDX
	)
) tablespace CSA_ADM_DATA
/
create sequence CSAADMIN_IKFL.S_MAIL_SUBJECTS
/

create unique index CSAADMIN_IKFL.I_MAIL_SUBJECTS_UUID on CSAADMIN_IKFL.MAIL_SUBJECTS (
   UUID ASC
) tablespace CSA_ADM_IDX
/

/*
grant select on CSAADMIN_IKFL.CONTACT_CENTER_AREAS to OSDBO_USER;
grant select on CSAADMIN_IKFL.C_CENTER_AREAS_DEPARTMENTS  to OSDBO_USER;
grant select on CSAADMIN_IKFL.MAIL_SUBJECTS  to OSDBO_USER;
*/