DECLARE
  --------------------������� ��������� ��� ��������� ����� �����������----------------------
  --���-�� �����������, ������� ����� ��������������
  c_EmployeesCount constant number := 5;
  --������������� ������������, � �������� ��������� ���������
  c_DepartmentId constant number := 87;
  --������ ��������, ������� �� ��������� ����� �������� ������������ �����������
  c_ServicesNameList constant varchar2(4000) := 'BankListManagement,ServiceProvidersDictionaryEmployeeService,KBKEmployeeManagement,PersonManagement,'||
                                                'CheckThroughStopListService,PersonAccess,AccountManagement,CardsManagement,PersonAccessManagement,'||
                                                'PaymentFormManagement,ManageEmployeePassword,UnloadJBTManagement,SynchronizeDictionaries,DepartmentsViewing,'||
                                                'PersonsViewing,PaymentFormsViewing,BankListViewing,ViewPaymentRegister,PaymentsSignatureManagment,ViewPaymentList,'||
                                                'MailManagment,NewsManagment,LimitManagment,PersonalPaymentCardManagment,RepeatPaymentService,LogsServiceEmployee,'||
                                                'MessageLogServiceEmployee,PersonGroupManagement,CommonLogServiceEmployee,ChangeDocumentStatus,PersonGroupView,'||
                                                'OffersLoad,LoanProducts,PublishLoanProducts,CreateCardsLimits,CreditCardProducts,PublishCreditCardProducts,'||
                                                'UnloadOffersProduct,MailArchiveManagment,IncomeLevelService,IMAccountRateService,LoggingJournalServiceEmployee,'||
                                                'InformationMessageService,ConfirmPaymentByCallCenter,EditGroupRiskServiceProvider,ViewMobileOperators,TechnoBreaksManagment,'||
                                                'CardProducts,MailMessagesManagment,ViewOTPRestrictionService,EmployeeCreateAutoPayment';
  l_EmplItemIndex          number:=0;
  l_EmplLoginId            number;
  l_AccessSchemes_Id       number;
  l_EmplId_Str             varchar2(4000);
  l_LoginId_Str            varchar2(4000);
  l_begin                  number;
  l_EmplId                 number;
BEGIN
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

  --������� ����� ������� ���� �� ����
  select max(a.id) into l_AccessSchemes_Id
    from accessschemes a
   where a.name = 'personal'
     and a.category = 'E'
     and a.personal = 1
     and a.ca_admin_scheme = 0;
  if (l_AccessSchemes_Id is null) then
    insert into accessschemes(id
                            , name
                            , category
                            , personal
                            , ca_admin_scheme)
           values(s_accessschemes.nextval
                , 'personal'
                , 'E' /*"E"-��������� ���������� � ����������� �����*/
                , 1
                , 0)
         returning id into l_AccessSchemes_Id;
  end if;
  
  --������� ������ ����� ������� � �������� ���� ����� (��� ���� ����������)
  execute immediate 
    'insert into schemesservices '||
         'select distinct :l_AccessSchemes_Id, s.id '||
           'from table (cast(str2tbl(:c_ServicesNameList,'','') as STRING_LIST_TYPE)) serv '||
              ', services s '||
              ', schemesservices ss '||
          'where upper(s.service_key) = upper(serv.column_value) '||
            'and ss.scheme_id (+)= :l_AccessSchemes_Id '||
            'and ss.service_id (+) = s.id '||
            'and ss.scheme_id is null ' 
     using l_AccessSchemes_Id, c_ServicesNameList, l_AccessSchemes_Id;
  
  loop
    l_EmplItemIndex := l_EmplItemIndex + 1;

    --������� ������ ���������� 
    select s_logins.nextval into l_EmplLoginId from dual;
    insert into LOGINS(ID, USER_ID, KIND, WRONG_LOGONS, DELETED)
           values(s_logins.nextval
                , substr('Empl_'||rpad(l_EmplLoginId,10,'0'),1,50)
                , 'E' /*"E" - ���������*/
                , 0
                , 0)
        returning id into l_EmplLoginId;
    
    --������� ������ ����������
    select s_employees.nextval into l_EmplId from dual;
    insert into employees
         select l_EmplId
              , l_EmplLoginId
              , '��� '||DBMS_RANDOM.string('a',8)||' generated'
              , '������� '||DBMS_RANDOM.string('a',8)||' generated'
              , '�������� '||DBMS_RANDOM.string('a',8)||' generated'
              /*��� ����������*/
              , '������ ���������� �������������� ��� ��'
              /*Email*/
              , 'sotrudnik@milo.ru'
              /*���������*/
              , '+7(903)123-45-67'
              /*�����������, � �������� ��������� ���������*/
              , c_DepartmentId
              , null
              , 0
              , 0
           from dual;
    
    --������� ������� ����������
    insert into profile(login_id,id)
          values (l_EmplLoginId,s_profile.nextval);

    --������� ������ ����������
    insert into passwords
         select s_passwords.nextval
              , 'pu'
              , l_EmplLoginId
              , 'AD39A3788F1F7F6B7DB530291C67DB20A74D8AA1CEEB110B2599DF5448C6D9B2' /*��� ������ a1d3g5j7*/
              , 1
              , cast(sysdate as timestamp)
              , cast(to_date('01.01.3000','dd.mm.yyyy') as timestamp)
              , 0
           from dual;

    --������� �������������� ������� � ����� �������
    insert into schemeowns
         select s_schemeowns.nextval
              , l_AccessSchemes_Id
              , l_EmplLoginId
              , 'employee'
           from dual;
           
    --������� ��������� �������� ������������
    insert into authentication_modes
         select s_authentication_modes.nextval
              , l_EmplLoginId
              , 'employee'
              , null
              , null
           from dual;
           
    --������� ���������� ������������ ��� ����������
    insert into allowed_department(department_id,login_id) values(c_DepartmentId,l_EmplLoginId);
    
    exit when l_EmplItemIndex >= c_EmployeesCount;
  end loop;
  --������ ��������������� ��������
  execute immediate 'drop type STRING_LIST_TYPE';
  execute immediate 'drop function str2tbl';
  commit;
END;
