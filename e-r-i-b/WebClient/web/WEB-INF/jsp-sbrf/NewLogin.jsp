<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute />
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=')}${$$helpId}"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'p11')}"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>

    <title>Единый центр аутентификации: ввод логина и пароля</title>
    <link rel="icon" type="image/x-icon" href="${skinUrl}/images/favicon.ico"/>

     <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>


    <link rel="stylesheet" type="text/css" href="${skinUrl}/style.css">
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/login.css">
    <link rel="stylesheet" type="text/css" href="${skinUrl}/login.css">
    <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css">

    <!--[if IE 6]>
        <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
	    <link type="text/css" rel="stylesheet" href="${skinUrl}/ie.css"/>
    <![endif]-->
    <script language="JavaScript" src="${initParam.resourcesRealPath}/scripts/gost.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PaymentsFormHelp.js"></script>
	<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/commandButton.js"></script>
	<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Crypto.js"></script>
	<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/builder.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/iframerequest.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.ifixpng.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/layout.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/dropdown_menu_hack.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/customPlaceholder.js"></script>
	<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/${phiz:cryptoProviderName()}Crypto.js"></script>
	<script type="text/javascript">setRSCryptoName('${phiz:cryptoProviderName()}');</script>
	<script type="text/javascript">document.webRoot='/${phiz:loginContextName()}';</script>
    <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
</head>




<c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>

<html:form action="/login" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">
	<html:hidden property="accessType" value="simple"/>
	<html:hidden property="clientRandom" styleId="clientRandom"/>
	<html:hidden property="password" styleId="password"/>
	<html:hidden property="serverRandom" styleId="serverRandom"/>

    <script language="JavaScript">

	function checkData()
	{
		var n = getElement("login");
		var p = getElement("passwordTxt");

		if (n.value.length == 0 || p.value.length == 0)
		{
			alert("Введите логин пользователя и пароль.");
			return false;
		}

		var challenge = sr + cr;
		var ph = fnStrHashLikeGOST(p.value);
		var ar = fnHmacLikeGOST(ph, challenge);
		document.getElementById("password").value = ar;
		document.getElementById("clientRandom").value = cr;
		p.value = '';
		return true;
    }

	function onEnterKey(e)
	{
		var kk = navigator.appName == 'Netscape' ? e.which : e.keyCode;
		if (kk == 13)
		{
			var butt = findDefaultCommandButton();
			if (butt)
			    butt.click();
		}
	}

   var HEX_DIGITS = "0123456789ABCDEF";
   function randomHex(n)
	{
		var res = "";

		for (var i = 0; i < n; i++)
		{
			var p = Math.floor(Math.random() * 16);
			res = res + HEX_DIGITS.charAt(p);
		}
		return res;
	}

   	var sr = getElement("serverRandom").value;
	var cr = randomHex(16);
</script>
    
    <%-- Блок "подождите" --%>
    <div id="loading" style="left:-3300px;">
        <div id="loadingImg"><img src="${imagePath}/ajax-loader64.gif"/></div>
        <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
    </div>
    <div id="tinted" class="opacity25"></div>
    <div id="tintedNet"></div>

    <c:set var="appName"><bean:message bundle="commonBundle" key="application.title"/></c:set>
    <div id="loginPage">
        <div id="header">
            <div class="header-box header-teaser-border-left">
                <div class="header-teaser-border-right">
                    <div class="header-teaser-box-bottom-left">
                        <div class="header-teaser-box-bottom-right">
                            <div class="header-teaser-box-top-left">
                                <div class="header-teaser-box-top-right">
                                    <!-- header group -->
                                    <img class="logo" src="${imagePath}/logo.png" alt="${appName}"/>
                                    <div class="header header-border-left">
                                        <div class="header-box-bottom-left height">
                                            <div class="header-box-bottom-right height">
                                                <div class="header-box-top-left">
                                                    <div class="header-box-top-right">
                                                        <div class="phones">
                                                            <span>Справочная служба: </span></span><span>+7 (495) 500 5550</span><span>8 (800) 555 5550</span>
                                                        </div>
                                                        <ul class="buttons">
                                                            <li class="help">
                                                                <a class="button" href="" onclick="openHelp('${helpLink}'); return false;"><img src="${imagePath}/help.png" alt="Помощь"/></a>
                                                                <a class="buttons-description" href="" onclick="openHelp('${helpLink}'); return false;">Помощь</a>
                                                            </li>
                                                            <li>
                                                                <a class="button" href="/PhizIC/logoff.do"><img src="${imagePath}/exit.png" alt="Выход"/></a>
                                                                <a class="buttons-description" href="/PhizIC/logoff.do">Выход</a>
                                                            </li>
                                                        </ul>
                                                       </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="header-info">
                                    <span class="department">
                                        &nbsp;<c:out value="${phiz:getTBName()}" default=""/>
                                    </span>
                                    </div>
                                    <!-- end header group -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="content">
        <div class="Login" id="LoginDiv">
            <!-- Сообщения об ошибках -->
            <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp">
                <tiles:put name="bundle" type="string" value="securityBundle"/>
            </tiles:insert>
        <div class="mun"  onkeypress="onEnterKey(event);">
      <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="isEnterConfirm" value="true"/>
        <tiles:put name="data">
            <h1>вход на личную страницу</h1>
        </tiles:put>
          <tiles:put name="confirmInfo">
            <div class="login">
              <table style="margin-left:300px;width:350px">
                <tr>
                    <td><h5>Логин</h5></td>
                    <td>
                        <div с="login-field">
                            <html:text property="login" styleClass="login" maxlength="20"/>
                        </div>
                    </td>
                </tr>
               </table>
	    	</div>
	    	<div class="clear"></div>
            </p>  
            <div class="login">
               <table  style="margin-left:300px;width:283px">
                  <tr>
                    <td><h5>Пароль</h5></td>
                    <td><input type="password" id="passwordTxt" name="passwordTxt" maxlength="20" class="login"/></td>
                   </tr>
               </table>
	    	</div>
	    	<div class="clear"></div>
          </tiles:put>
                  <tiles:put name="buttons">
                    <div class="buttonsArea">
                          <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.confirm"/>
                            <tiles:put name="commandTextKey" value="button.validate"/>
                            <tiles:put name="commandHelpKey" value="button.validate"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                             <tiles:put name="validationFunction" >checkData();</tiles:put>
                        </tiles:insert>
                          <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.getPassword"/>
                            <tiles:put name="commandTextKey" value="button.getPassword"/>
                            <tiles:put name="commandHelpKey" value="button.changePassword.help"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="viewType"       value="simpleLink"/>
                        </tiles:insert>
                    </div>
                </tiles:put>

      </tiles:insert>
    </div>
    <div class="mun">
          <tiles:insert definition="teaser" flush="false">
            <tiles:put name="data">
              <div class="login_info">
                  <h4><b>Информация о мерах безопасности при использовании Сбербанк</b> Онлайн</h4>
              <p><a href="https://esk.sberbank.ru/esClient/_ns/ProtectInfoPage.aspx" target="_blank">Меры безопасности при использовании системы "Сбербанк Онлайн"</a></p>
              <p><a href="http://sberbank.ru/ru/person/dist_services/warning/" class="paperEnterLink" target="_blank">О рисках при дистанционном банковском обслуживании</a></p>
              Подключить услугу «Сбербанк Онлайн» можно в отделении Сбербанка России  при наличии 	документа,
               удостоверяющего личность, и карты Сбербанка России (кроме Сберкарт, карт
              Сбербанк-Maestro, выпущенных  Северо-Западным	и Поволжским территориальными банками  и корпоративных карт).
              В системе «Сбербанк Онлайн»  Вы можете управлять своими вкладами и картами, совершать платежи и переводы через интернет
              <P> Идентификатор пользователя и постоянный пароль можно получить:Через устройства самообслуживания Сбербанка России с помощью банковских карт<a href="http://www.sberbank.ru/common/img/uploaded/cards/us_online.xls" class="paperEnterLink">(список устройств)</a>. ;</p>
              <p class="FAQ"><a href="#" onclick="openFAQ('${faqLink}')"><i>Часто задаваемые вопросы</i></a></p>
                  </div>
                <div  class="login_girl"><a href="#" onclick="openFAQ('${faqLink}')"><img src="${imagePath}/login_girl.png" alt="Часто задаваемые вопросы"/></a></div>
                <div class="clear"></div>
              </tiles:put>
              </tiles:insert>
        </div>
         <div id="footer">
            <div class="copyright">
                <div>© 1997 — 2015 ОАО «Сбербанк России»</div>
            </div>
            <div class="contacts">
                Россия, Москва, 117997, ул. Вавилова, д. 19.<br/>
                Генеральная лицензия на осуществление банковских операций от 3 октября 2002 года.<br/>
                Регистрационный номер - 1481.<br/>
                Правовая информация.
            </div>
         </div>
</div>

</html:form>