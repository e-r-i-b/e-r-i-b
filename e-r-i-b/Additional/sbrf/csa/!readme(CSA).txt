��������� �������� CSA

1.)     � �����   \WebClient\web\WEB-INF\config\sbrf-struts-config.xml
         ��������:
<action path="/login" type="com.rssl.phizic.web.client.ext.sbrf.security.LoginClosePasswordStageAction"
            name="LoginForm" scope="request" validate="false">
        <forward name="Show" path="/WEB-INF/jsp-sbrf/login.jsp"/>
</action>
           ��:
 <action path="/login" type="com.rssl.phizic.web.client.ext.sbrf.security.PostCSALoginAction"
                name="DummyForm" scope="request" validate="false">
            <forward name="Show" path="/WEB-INF/jsp-sbrf/login.jsp"/>
        <forward name="Block" path="/WEB-INF/jsp-sbrf/loginBlockCSA.jsp"/>
 </action> 

2.)  � ����� \Settings\configs\sbrf\authService.properties
           �����������:
               com.rssl.auth.useOwnAuthentication = false   

3.)  � ����� \CSAWebMock\src\com\rssl\phizic\csa\web\common\LoginAction.java  
            �����������:
                  ActionRedirect forward = new ActionRedirect("http://localhost:8888/PhizIC/login.do");
              8888 - ���� ������� ����

4.)     ��������� ��� � sbrf.PhizIC.properties     
            ��������� ��������� ���������, �� ����������� ����� (���� ������� ����) 
                  csa.webservice.url = http://localhost:8888/CSA/services/AuthServicePortTypeImpl 
                  way4.webservice.url=http://localhost:8888/CSA/services/IPASWSSoap_Impl
                  csa.login.url = http://localhost:8888/CSA/login.do
                       
               


5.) �������������� (����� clean) �� �����������. ������� ������ ������

6.) ��� SYSDBA ������� ����� � ������ �����:

CREATE USER CSA IDENTIFIED BY CSA
    DEFAULT TABLESPACE USERS
    TEMPORARY TABLESPACE TEMP
    QUOTA UNLIMITED ON USERS;
GRANT CREATE SESSION TO CSA;
GRANT CREATE TABLE TO CSA;
GRANT CREATE PROCEDURE TO CSA;
GRANT ALTER ANY TABLE TO CSA;
GRANT ALTER ANY PROCEDURE TO CSA;
GRANT DELETE ANY TABLE TO CSA;
GRANT DROP ANY TABLE TO CSA;
GRANT DROP ANY PROCEDURE TO CSA;

7.) � ��������� ����� ��������� ������:
create table LOGIN_ALIAS (
    als     varchar2(40) not null,
    loginId varchar2(10) not null,
    res     integer      not null,
    constraint PK_LOGIN_ALIAS primary key (als)
);
CREATE OR REPLACE PROCEDURE CSA.GetLoginIdByAlias (
   pAls       IN     VARCHAR2,
   pLoginid      OUT VARCHAR2,
   pRes          OUT INT
)
IS
BEGIN
   SELECT   MAX (loginId), MAX (res)
     INTO   pLoginid, pRes
     FROM   LOGIN_ALIAS l
    WHERE   als = pAls;
END;
/
