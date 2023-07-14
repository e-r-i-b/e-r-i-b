Настройка заглушки CSA

1.)     в файле   \WebClient\web\WEB-INF\config\sbrf-struts-config.xml
         заменяем:
<action path="/login" type="com.rssl.phizic.web.client.ext.sbrf.security.LoginClosePasswordStageAction"
            name="LoginForm" scope="request" validate="false">
        <forward name="Show" path="/WEB-INF/jsp-sbrf/login.jsp"/>
</action>
           на:
 <action path="/login" type="com.rssl.phizic.web.client.ext.sbrf.security.PostCSALoginAction"
                name="DummyForm" scope="request" validate="false">
            <forward name="Show" path="/WEB-INF/jsp-sbrf/login.jsp"/>
        <forward name="Block" path="/WEB-INF/jsp-sbrf/loginBlockCSA.jsp"/>
 </action> 

2.)  в файле \Settings\configs\sbrf\authService.properties
           редактируем:
               com.rssl.auth.useOwnAuthentication = false   

3.)  в файле \CSAWebMock\src\com\rssl\phizic\csa\web\common\LoginAction.java  
            редактируем:
                  ActionRedirect forward = new ActionRedirect("http://localhost:8888/PhizIC/login.do");
              8888 - порт указать свой

4.)     проверяем что в sbrf.PhizIC.properties     
            Следующие настройки идентичны, за исключением порта (порт указать свой) 
                  csa.webservice.url = http://localhost:8888/CSA/services/AuthServicePortTypeImpl 
                  way4.webservice.url=http://localhost:8888/CSA/services/IPASWSSoap_Impl
                  csa.login.url = http://localhost:8888/CSA/login.do
                       
               


5.) пересобираться (через clean) не обязательно. Обычной сборки хватит

6.) Под SYSDBA создать схему и выдать права:

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

7.) В созданной схеме выполнить скрипт:
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
