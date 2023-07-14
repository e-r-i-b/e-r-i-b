-- ���������� ������������

insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('1', '������������ ������� �����', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('2', '������������ �������� �����', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('3', '������������ ���������� �����', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('4', '�������� �����������/���.��������/������� ���������', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('5', '����������������������� ����������', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('6', '����������', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('7', '��������������', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('8', '�������', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('9', '��������', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('10', '�����', 70); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('11', '��������', 65); 
insert into LOANCLAIM_CATEGORY_OF_POSITION(CODE, NAME, MAX_AGE) values ('12', '��������������� ����������� ��������', 65); 

insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('1', '������ ������� / MBA', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('2', '������ ������', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('3', '������', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('4', '������������� ������', 1);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('5', '������� �����������', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('6', '�������', 0);
insert into LOANCLAIM_EDUCATION(CODE, NAME, HIGH_EDUCATION_COURSE_REQUIRED) values ('7', '���� ��������', 0);

insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('1', '����', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('2', '����', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('3', '����', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('4', '������', 0); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('5', '���', 1); 
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('6', '����', 1);
insert into LOANCLAIM_FAMILY_RELATION(CODE, NAME, IS_CHILD) values ('7', '����', 0); 

insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('0', '������/�� �������', 'NOT'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('1', '� �������', 'NOT'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('2', '�����/�������', 'REQUIRED'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('3', '������/�����', 'NOT'); 
insert into LOANCLAIM_FAMILY_STATUS(CODE, NAME, SPOUSE_INFO_REQUIRED) values ('4', '����������� ����', 'WITHOUT_PRENUP');

insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('005', '����������� ��������������� ����������', '������.����'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('002', '�������� ����������� ��������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('003', '�������� ����������� ��������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('006', '������ ������������', '������ ������������'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('007', '������������ �� ����', '������������ �� ����'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('008', '���������������� ����������', '������. ����'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('009', '������������ (����������) ���������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('001', '�������� � ������������ ����������������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('012', '�������� � �������������� ����������������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('013', '��������� ����������� �� ����� �������������� �������', '�������'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('014', '��������� ����������� �� ����� ������������ ����������', '�������'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('015', '�������� ��������� �����������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('016', '������������ (�����������) ����������� (�����������)', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('017', '������������ ��������', '��'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('018', '����', '����'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('019', '����������', '����������'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('020', '��������������� ����������', '��'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('021', '����� ������������ ����������������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('022', '�������������� �����������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('023', '���������� �������������� �����������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('024', '����������� ����������� ��� (���������� ��� ����)', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('025', '���������� ������������ (����������) ��������', '����'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('026', '��������������� ������������ ��������������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('027', '������������ ������������� �����', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('028', '�������., ������.��� ������ �������������� ������������', '�����������'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('029', '������ �������������� �����������', '������'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('030', '��������� - ������������ ������', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('031', '������ �������������� �����', '���'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('032', '������� ������������', '��'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('999', '����������������� � �������', '�����������������'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('034', '���� ������������� ����', '����'); 
insert into LOANCLAIM_INCORPORATION_FORM(CODE, NAME, SHORT_NAME) values ('033', '�������������� ��������������� (����)', '��');

insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('1', '�� 6 �� 12 ���', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('2', '�� 1 ���� �� 3 ���', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('3', '�� 3 �� 5 ���', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('4', '�� 5 �� 10 ���', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('5', '�� 10 �� 20 ���', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('6', '����� 20 ���', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('7', '�� 3 �� 6 �������', 0);
insert into LOANCLAIM_JOB_EXPERIENCE(CODE, NAME, LOAN_NOT_ALLOWED) values ('8', '����� 3 �������', 1);

insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('1', '�������, �����, �����������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('2', '�������������� ������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('3', '�����', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('4', '�������������� � ��������������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('5', '����������� ���', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('6', '�����������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('7', '������� / ��������� �������� (���������)', 1); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('8', '������ (���������)', 1); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('9', '���������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('10', '�������� ������������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('11', '������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('12', '�����������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('13', '��������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('14', '�������� � ���������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('15', '������ ������ � ����������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('16', '���������� �����', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('17', '�������������� ���������� / ����������������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('18', '�������������', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('19', '�����', 0); 
insert into LOANCLAIM_KIND_OF_ACTIVITY(CODE, NAME, DESC_REQUIRED) values ('20', '������ ������� (���������)', 1);

insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('2', '�����������'); 
insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('0', '������������������'); 
insert into LOANCLAIM_PAYMENT_METHOD(CODE, NAME) values ('9', '����'); 

insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('0', '����������'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('1', '�������������'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('2', '�������������� ������'); 
insert into LOANCLAIM_PAYMENT_PERIOD(CODE, NAME) values ('3', '��������'); 

insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('1', '�� 10'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('2', '11-30'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('3', '31-50'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('4', '51-100'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('5', '����� 100'); 
insert into LOANCLAIM_NUMBER_OF_EMPLOYEES(CODE, NAME) values ('6', '����������� ��������'); 

insert into LOANCLAIM_REGION(CODE, NAME) values ('0001', '���������� ������ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0002', '���������� ������������ '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0003', '���������� ������� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0004', '���������� ����� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0005', '���������� �������� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0006', '��������� ����������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0007', '��������� - ���������� ����������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0008', '���������� ��������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0009', '��������� - ���������� ����������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0010', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0011', '���������� ����'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0012', '���������� ����� ��'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0013', '���������� ��������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0014', '���������� ���� (������)'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0015', '���������� �������� ������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0016', '���������� ��������� (���������)'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0017', '���������� ���� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0018', '���������� ����������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0019', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0020', '��������� ����������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0021', '��������� ����������- ����� ���������� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0022', '��������� ����'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0023', '������������� ����'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0024', '������������ ���� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0025', '���������� ����'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0026', '�������������� ����'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0027', '����������� ����'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0028', '�������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0029', '������������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0030', '������������ �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0031', '������������ �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0032', '�������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0033', '������������ �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0034', '������������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0035', '����������� ������� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0036', '����������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0037', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0038', '��������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0039', '��������������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0040', '��������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0041', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0042', '����������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0043', '��������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0044', '����������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0045', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0046', '������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0047', '������������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0048', '�������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0049', '����������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0050', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0051', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0052', '������������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0053', '������������ �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0054', '������������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0055', '������ �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0056', '������������ �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0057', '��������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0058', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0059', '�������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0060', '��������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0061', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0062', '��������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0063', '��������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0064', '����������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0065', '����������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0066', '������������ �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0067', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0068', '���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0069', '�������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0070', '������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0071', '�������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0072', '��������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0073', '����������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0074', '����������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0075', '���� �������������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0076', '����������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0077', '�. ������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0078', '�. ����� � ���������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0079', '��������� ���������� �������'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0080', '�������� ��������� ���������� �����'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0081', '���� - ��������� ���������� �����'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0082', '��������� ���������� ����� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0083', '�������� ���������� ����� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0084', '���������� (������� - ��������) ���������� ����� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0085', '���� - ��������� ��������� ���������� �����'); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0086', '����� - ���������� ���������� ����� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0087', '��������� ���������� ����� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0088', '����������� ���������� ����� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0089', '����� - �������� ���������� ����� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('9901', '����� � ��������� �������� '); 
insert into LOANCLAIM_REGION(CODE, NAME) values ('0099', '���� (������ ��� �������, ������������� ��� ���������� ���������');

insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('0', '����������� ��������',1); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('1', '� �������������',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('2', '���. ����',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('3', '������',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('4', '������������ ��������',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('5', '���������',0); 
insert into LOANCLAIM_RESIDENCE_RIGHT(CODE, NAME, NEED_REALTY_INFO) values ('6', '�������� �����',0);

insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('201', '�-�'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('230', '���'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('202', '�'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('204', '������'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('205', '��'); 
insert into LOANCLAIM_TYPE_OF_AREA(CODE, NAME) values ('103', '�'); 

insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('310', '�������'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('301', '�'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('305', '��'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('304', '��'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('311', '�/�'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('302', '���'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('303', '��'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('307', '�/�'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('309', '�/�'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('306', '�/�'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('312', '���'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('313', '�����'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('314', '�/�'); 
insert into LOANCLAIM_TYPE_OF_CITY(CODE, NAME) values ('315', '�/��'); 

insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('1', '����������'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('2', '���������'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('3', '���������������'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('4', '��������� �����'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('5', '��������������'); 
insert into LOANCLAIM_TYPE_OF_DEBIT(CODE, NAME) values ('6', '������'); 

insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('401', '���'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('402', '���'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('403', '�������'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('404', '�����'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('405', '�'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('436', '�������'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('406', '�'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('407', '��'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('408', '�/� �����'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('409', '�/� ������'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('410', '�/� ��'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('411', '�/� ����'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('412', '�/� ����'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('438', '�/� �����'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('413', '�/� ��'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('414', '������'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('415', '�������'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('416', '��'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('417', '�'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('418', '���'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('419', '��'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('420', '������'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('421', '�'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('426', '�/�'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('422', '�/�'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('423', '�/��'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('424', '���'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('425', '�������'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('427', '��������'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('428', '���'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('429', '��'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('430', '�'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('431', '��'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('432', '��'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('433', '��-��'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('437', '���'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('434', '�'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('435', '�'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('439', '��-�'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('440', '�����'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('441', '���'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('442', '���'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('443', '������'); 
insert into LOANCLAIM_TYPE_OF_LOCALITY(CODE, NAME) values ('444', '������'); 

insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME, RESIDENCE) values ('1', '�������', 0); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME, RESIDENCE) values ('2', '��������', 1); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME, RESIDENCE) values ('3', '���', 0); 
insert into LOANCLAIM_TYPE_OF_REALTY(CODE, NAME, RESIDENCE) values ('4', '�������', 0); 

insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('529', '��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('532', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('501', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('533', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('502', '�-�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('503', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('534', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('535', '�������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('536', '�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('504', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('537', '�/� �����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('538', '�/� ������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('539', '�/� ��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('540', '�/� ����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('541', '�/� ���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('542', '�/� ��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('559', '�/� �����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('505', '��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('506', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('543', '�������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('507', '��-�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('508', '��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('509', '������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('510', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('544', '�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('545', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('511', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('546', '��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('512', '������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('548', '�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('549', '�/�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('550', '�/�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('551', '�/��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('513', '����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('514', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('515', '�������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('516', '��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('547', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('517', '��-��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('552', '����������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('553', '�������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('519', '��-��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('518', '������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('520', '������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('521', '��������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('522', '�������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('554', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('555', '�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('523', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('524', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('556', '��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('557', '��'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('525', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('526', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('527', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('528', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('530', '��-�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('558', '�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('531', '�'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('560', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('561', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('562', '�����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('563', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('564', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('565', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('566', '������'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('567', '����'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('568', '���'); 
insert into LOANCLAIM_TYPE_OF_STREET(CODE, NAME) values ('569', '�����'); 

insert into LOANCLAIM_TYPE_OF_VEHICLE(CODE, NAME) values ('1', '�������� ��'); 
insert into LOANCLAIM_TYPE_OF_VEHICLE(CODE, NAME) values ('2', '������ ��'); 

-- ������ �� ��������� ��������
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('1', '������� ��������', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('2', '��� ����� (���������� ���������)', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('3', '������� �������� (��������)', 0, 0, 1, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('4', '�������������� ���������������', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('5', '����� �� ������������ ��������', 1, 1, 0, 0); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('6', '���������', 0, 0, 0, 1); 
insert into LOANCLAIM_WORK_ON_CONTRACT(CODE, NAME, FULL_NAME_REQUIRED, INN_REQUIRED, PRIVATE_PRACTICE, PENSIONER) values ('7', '����������� �� ����������-��������� ��������', 1, 1, 0, 0); 

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('1',
             '�� ��������� �����',
             1,
             0,
             'DEPOSIT');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('2',
             '�� ����� �����',
             1,
             1,
             'DEPOSIT');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('3',
             '�� ��������� �����',
             1,
             0,
             'CARD');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('4',
             '�� ����� �����',
             1,
             1,
             'CARD');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('5',
             '�� ��������� ������� ����',
             1,
             0,
             'CURRENT_ACCOUNT');

INSERT INTO LOANCLAIM_LOAN_ISSUE_METHOD (CODE,
                                         NAME,
                                         AVAILABLE_IN_CLAIM,
                                         NEW_PRODUCT_FOR_LOAN,
                                         PRODUCT_FOR_LOAN)
     VALUES ('6',
             '�� ����� ������� ����',
             1,
             1,
             'CURRENT_ACCOUNT');