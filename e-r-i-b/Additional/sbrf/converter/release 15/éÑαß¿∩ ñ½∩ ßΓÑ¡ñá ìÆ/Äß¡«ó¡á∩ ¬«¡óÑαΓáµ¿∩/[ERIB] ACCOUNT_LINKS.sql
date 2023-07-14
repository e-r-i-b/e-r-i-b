alter table SRB_IKFL.ACCOUNT_LINKS rename to FORDEL$ACCOUNT_LINKS;

alter table SRB_IKFL.FORDEL$ACCOUNT_LINKS rename constraint PK_ACCOUNT_LINKS to FORDEL$ACCOUNT_LINKS_C_1;
alter table SRB_IKFL.FORDEL$ACCOUNT_LINKS rename constraint FK_ACCOUNTS_ACCOUNTS_TO_LOGINS to FORDEL$ACCOUNT_LINKS_C_6;

alter table SRB_IKFL.ACCOUNT_TARGETS drop constraint FK_A_TARGETS_FK_ACCOUNT_LINK;
alter table SRB_IKFL.STORED_ACCOUNT drop constraint FK_STORED_A_TO_LINK_REF;

alter index SRB_IKFL.DXFK_ACCOUNTLINKS_TO_LOGINS rename to FORDEL$ACCOUNT_LINKS_IDX_2;
alter index SRB_IKFL.UNIQUE_ACCOUNT_NUMBER rename to FORDEL$ACCOUNT_LINKS_IDX_3;
alter index SRB_IKFL.I_NUMBER_LOGIN rename to FORDEL$ACCOUNT_LINKS_IDX_4;
alter index SRB_IKFL.PK_ACCOUNT_LINKS rename to FORDEL$ACCOUNT_LINKS_IDX_5;

create table SRB_IKFL.ACCOUNT_LINKS as 
select /*+parallel(al, 32)*/
  ID,
  EXTERNAL_ID,
  PAYMENT_ABILITY,
  LOGIN_ID,
  ACCOUNT_NUMBER,
  ACCOUNT_NAME,
  SHOW_IN_MAIN,
  SHOW_IN_SYSTEM,
  SHOW_OPERATIONS,
  SHOW_IN_MOBILE,
  DESCRIPTION,
  CURRENCY,
  SHOW_IN_ATM,
  '1' SHOW_IN_SOCIAL,
  ERMB_NOTIFICATION,
  ERMB_SMS_ALIAS,
  SMS_AUTO_ALIAS,
  OFFICE_TB,
  OFFICE_OSB,
  OFFICE_VSP,
  POSITION_NUMBER,
  SHOW_IN_SMS
from SRB_IKFL.FORDEL$ACCOUNT_LINKS al;

alter table SRB_IKFL.ACCOUNT_LINKS modify SHOW_IN_SOCIAL not null novalidate;

alter table SRB_IKFL.ACCOUNT_LINKS add constraint PK_ACCOUNT_LINKS primary key (ID) using index (
	create unique index PSRB_IKFL.K_ACCOUNT_LINKS on SRB_IKFL.ACCOUNT_LINKS (ID)  parallel 32 tablespace INDX
) enable novalidate;

create index SRB_IKFL.DXFK_ACCOUNTLINKS_TO_LOGINS on SRB_IKFL.ACCOUNT_LINKS (LOGIN_ID) parallel 32 tablespace INDX;
create unique index SRB_IKFL.UNIQUE_ACCOUNT_NUMBER on SRB_IKFL.ACCOUNT_LINKS (ACCOUNT_NUMBER)  parallel 32 tablespace INDX;
create unique index SRB_IKFL.I_NUMBER_LOGIN on SRB_IKFL.ACCOUNT_LINKS (LOGIN_ID, ACCOUNT_NUMBER)  parallel 32 tablespace INDX ;

alter table SRB_IKFL.ACCOUNT_LINKS 
	add constraint FK_ACCOUNTS_ACCOUNTS_TO_LOGINS foreign key (LOGIN_ID)
		references SRB_IKFL.LOGINS (ID) enable novalidate;

alter table SRB_IKFL.ACCOUNT_TARGETS 
	add constraint FK_A_TARGETS_FK_ACCOUNT_LINK foreign key (ACCOUNT_LINK)
		references SRB_IKFL.ACCOUNT_LINKS (ID) on delete set null enable novalidate;

alter table SRB_IKFL.STORED_ACCOUNT 
	add constraint FK_STORED_A_TO_LINK_REF foreign key (RESOURCE_ID)
		references SRB_IKFL.ACCOUNT_LINKS (ID) on delete cascade enable novalidate;
		
alter index SRB_IKFL.DXFK_ACCOUNTLINKS_TO_LOGINS noparallel;
alter index SRB_IKFL.UNIQUE_ACCOUNT_NUMBER  noparallel;
alter index SRB_IKFL.I_NUMBER_LOGIN noparallel;
alter index SRB_IKFL.PK_ACCOUNT_LINKS noparallel;	