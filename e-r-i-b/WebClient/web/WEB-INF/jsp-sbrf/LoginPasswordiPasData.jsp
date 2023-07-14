<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<script language="JavaScript" src="${initParam.resourcesRealPath}/scripts/gost.js"></script>
<c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'p11')}"/>

<c:if test="${empty action}">
    <c:set var="action" value="/login"/>
</c:if>

<html:form action="${action}" show="true"
           onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">

    <script language="JavaScript">

        function checkData()
        {
            var n = getElement("login");
            var p = getElement("password");

            if (n.value.length == 0 || p.value.length == 0)
            {
                alert("Введите логин пользователя и пароль.");
                return false;
            }

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

    </script>
    <!-- Сообщения об ошибках -->
    <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp">
        <tiles:put name="bundle" type="string" value="securityBundle"/>
    </tiles:insert>
    <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="title" value="Вход на личную страницу"/>
        <tiles:put name="data">
            <table class="login">
                <tr>
                    <td>Идентификатор:&nbsp;</td>
                    <td>
                        <html:text property="login" styleClass="login" maxlength="20"/>
                    </td>
                </tr>
                <tr>
                    <td>Пароль&nbsp;</td>
                    <td>
                        <input type="password" id="passwordTxt" name="password" maxlength="20" class="login"/>
                    </td>
                </tr>
            </table>
            <div class="clear"></div>
            <div class="enterButtons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.login"/>
                    <tiles:put name="commandTextKey" value="button.validate"/>
                    <tiles:put name="commandHelpKey" value="button.validate"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="validationFunction">checkData();</tiles:put>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.getPassword"/>
                    <tiles:put name="commandTextKey" value="button.getPassword"/>
                    <tiles:put name="commandHelpKey" value="button.changePassword.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="viewType" value="simpleLink"/>
                </tiles:insert>
            </div>

        </tiles:put>
    </tiles:insert>
    <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="data">
            <div class="FNSlogin_girl login_girl"><a href="#" onclick="openFAQ('${faqLink}')"><img
                    src="${imagePath}/login_girl.png" alt="Часто задаваемые вопросы"/></a></div>
            <div class="FNSlogin_info login_info">
                <h4><b><bean:message bundle="commonBundle" key="text.SBOL.security.info"/></b></h4>
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
        </tiles:put>
    </tiles:insert>

</html:form>