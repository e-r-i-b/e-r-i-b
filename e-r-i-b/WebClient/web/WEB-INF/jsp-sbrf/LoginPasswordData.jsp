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
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/gost.js"></script>
<c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'p10')}"/>
<c:if test="${empty action}">
    <c:set var="action" value="/login"/>
</c:if>
<br/>

<html:form action="${action}" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">
	<html:hidden property="accessType" value="simple"/>
	<html:hidden property="clientRandom" styleId="clientRandom"/>
	<html:hidden property="password" styleId="password"/>
	<html:hidden property="serverRandom" styleId="serverRandom"/>

    <script type="text/javascript">

	function checkData()
	{
		var n = getElement("login");
		var p = getElement("passwordTxt");

		if (n.value.length == 0 || p.value.length == 0)
		{
			alert("Введите логин пользователя и пароль.");
			return false;
		}

         	var sr = getElement("serverRandom").value;
         	var cr = randomHex(16);

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


</script>
            <!-- Сообщения об ошибках -->
    <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp">
        <tiles:put name="bundle" type="string" value="securityBundle"/>
    </tiles:insert>
    <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="color" value="greenTop"/>
        <tiles:put name="title" value="Вход на личную страницу"/>
        <tiles:put name="data">
            <table class="login" onkeypress="onEnterKey(event);">
                <tr>
                    <td>
                        Идентификатор:&nbsp;
                    </td>
                    <td>
                        <html:text property="login" styleClass="login" maxlength="20" value=""/>
                    </td>
                </tr>
                <tr>
                    <td class="align-right">
                        Пароль:&nbsp;
                    </td>
                    <td>
                        <input type="password" id="passwordTxt" name="passwordTxt" maxlength="20" class="login" value=""/>
                    </td>
                </tr>
            </table>
            <div class="enterButtons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.login"/>
                    <tiles:put name="commandTextKey" value="button.validate"/>
                    <tiles:put name="commandHelpKey" value="button.validate"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="validationFunction" >checkData();</tiles:put>
                </tiles:insert>
                <div class="commandButton">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.getPassword"/>
                        <tiles:put name="commandHelpKey" value="button.changePassword.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="viewType" value="simpleLink"/>
                        <tiles:put name="onclick">openFAQ('${faqLink}');</tiles:put>
                    </tiles:insert>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>

    <div id="infoOnLogin">
        <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="color" value="gray"/>
        <tiles:put name="data">
            <div class="loginDataBlock">
                <div class="login_info">
                    <h1><bean:message bundle="commonBundle" key="text.SBOL.info"/></h1>
                    <p class="paperEnterLink">
                        <a href="https://esk.sberbank.ru/esClient/_ns/ProtectInfoPage.aspx" target="_blank"><bean:message bundle="commonBundle" key="text.SBOL.security"/></a>
                    </p>
                    <p class="paperEnterLink">
                        <a href="http://sberbank.ru/ru/person/dist_services/warning/" target="_blank">О рисках при дистанционном банковском обслуживании</a>
                    </p>
                    <p>
                        <bean:message bundle="commonBundle" key="text.SBOL.about"/>
                        Вы можете это сделать 2 способами:
                    </p>
                        <ul>
                            <li>
                                с помощью банковской карты через устройства самообслуживания Сбербанка России (<a class="sbrfLink" href="http://sberbank.ru/ru/about/today/oib/" target="_blank">список устройств</a>);
                            </li>
                            <li>
                                с помощью &laquo;Мобильного банка&raquo;, отправив SMS-сообщение на номер 900 (или на номера +7 926 2000 900, +7 916 572 3900) с
                                текстом: Parol xxxxx (где xxxxx - последние 5 цифр номера Вашей карты). В результате Вам придет пароль в SMS-сообщении.
                                Далее позвоните по телефонам Контактного центра Сбербанка России +7 (495) 500 5550, 8 800 555 5550 и получите идентификатор
                                пользователя.
                            </li>
                        </ul>
                    <p>
                        <bean:message bundle="commonBundle" key="text.SBOL.fullAccess"/>
                        <br/>
                        <bean:message bundle="commonBundle" key="text.SBOL.iPas"/>
                        Для того чтобы получить одноразовый пароль:
                    </p>
                    <ul>
                        <li>
                            распечатайте чек с паролями через банкомат, терминал Сбербанка России с помощью банковской карты. Один чек содержит 20 одноразовых паролей;
                        </li>
                        <li>
                            при входе в систему нажмите на кнопку &laquo;SMS-подтверждение&raquo; и Вы получите SMS-сообщение с паролем, если Вы подключены к услуге Мобильный банк.
                        </li>
                    </ul>
                    <p>
                        Если Вы забыли или потеряли идентификатор пользователя и пароли, то Вы можете получить новые аналогичным образом
                        (прежние пароли и идентификатор при этом будут заблокированы).
                    </p>
                    <p class="important">
                        <span class="bold">Обратите внимание:</span> <span class="italic">если Вы при входе в систему три раза подряд неправильно ввели постоянный пароль,
                        вход в систему будет автоматически блокирован на 1 час. Вы можете снова войти в систему через час либо получить новый постоянный пароль.</span>
                    </p>
                    <p class="FAQ"><a href="#" onclick="javascript:openFAQ('${faqLink}')">Часто задаваемые вопросы</a></p>
                </div>
                <div  class="login_girl"><a href="#" onclick="javascript:openFAQ('${faqLink}')"><img src="${imagePath}/login_girl.png" alt="Часто задаваемые вопросы"/></a></div>
            </div>
        </tiles:put>
    </tiles:insert>
    </div>

    <div class="clear"></div>

</html:form>