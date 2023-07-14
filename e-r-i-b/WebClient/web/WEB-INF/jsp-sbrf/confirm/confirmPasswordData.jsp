<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<script language="JavaScript" src="${initParam.resourcesRealPath}/scripts/gost.js"></script>

<c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/confirm/password" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">
<html:hidden property="accessType" value="simple"/>
<html:hidden property="clientRandom" styleId="clientRandom"/>
<html:hidden property="serverRandom" styleId="serverRandom"/>
<html:hidden property="password" styleId="password"/>
<script language="JavaScript">
    
	function center(){
        var cElement = $("#login"); // Получаем центрируемый элемент
        var cElementHeight = cElement.height(); // Высота центрируемого элемента
        var wHeight = window.innerHeight; // Высота внутренней части окна
        if($.browser.msie){
            wHeight = document.documentElement.clientHeight; // Высота для IE6,IE7,IE8
        }
        var totalHeight = Math.round((wHeight - cElementHeight) / 2);  // Поулчаем высоту на которую нужно сдвинуть центрируемый элемент

        if (totalHeight < 0) totalHeight = 0; // Если размеры окна меньше размера центрируемого элемента, то высоту в 0
        cElement.css('top', totalHeight); // Устанавливем свойство
    }

	function checkData()
	{
		var p = getElement("passwordTxt");

		if (p.value.length == 0)
		{
			alert("Введите пароль");
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

	function demo()
	{
		alert("В демо-версии функция недоступна.");
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

	$(document).ready(function(){
        center();
		$(window).resize(center);
	});
</script>

<div id="login" onkeypress="onEnterKey(event);">
	<img src="${imagePath}/login_leftCorner.gif" alt=""/>
	<img src="${imagePath}/login_logo.png" alt="Сбербанк Онлаин"/>
	<div class="login-data">
		<div class="login">
			<h1>Пароль</h1>
			<div class="login-field">
				<input type="password" id="passwordTxt" name="passwordTxt" maxlength="20" class="login"/>
			</div>
		</div>
		<div class="clear"></div>
        <div class="enter">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.confirmByPassword"/>
                <tiles:put name="commandHelpKey" value="button.confirmByPassword.help"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="bundle" value="securityBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="validationFunction">
                    checkData();
                </tiles:put>
            </tiles:insert>
        </div>
		<div class="clear"></div>
            <div class="login-help">
            <dl class="phone">
                <dt>Телефон справочной службы</dt>
                <dd>(495) 974-77-61</dd>
            </dl>
            <div class="recover">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.getPassword"/>
                    <tiles:put name="commandTextKey" value="button.getForgottenPassword"/>
                    <tiles:put name="commandHelpKey" value="button.getPassword.help"/>
                    <tiles:put name="bundle" value="securityBundle"/>
                    <tiles:put name="isDefault" value="false"/>
                </tiles:insert>
            </div>
        </div>
	</div>

	<img src="${imagePath}/login_rightCorner.gif" alt=""/>
<div class="clear"></div>

<table cellpadding="0" cellspacing="0" width="994px">
    <tr>
        <td class="tblHeaderLeftCorner"><img src="${globalImagePath}/1x1.gif" alt="" border="0" width="33px"></td>
        <td class="tblHeader" colspan="3" width="100%">&nbsp;</td>
        <td class="tblHeaderRightCorner"><img src="${globalImagePath}/1x1.gif" alt="" border="0" width="33px"></td>
    </tr>
    <tr>
        <td class="tblInfLeftSpace"></td>
        <td class="tblInf" colspan="3">
            <table cellpadding="0" cellspacing="0" border="0" width="900px">
            <tr>
                <td class="tblHelpHdrLeftCorner">&nbsp;</td>
                <td class="tblHelpHdr">&nbsp;</td>
                <td class="tblHelpHdrRightCorner">&nbsp;</td>
            </tr>
            <tr>
                <td class="tblHelpInfLeftSpace"></td>
                <td>
                    <a href="http://www.srb.ru/servicefiz/sber-online/" class="paperEnterLink" target="_blank"><bean:message bundle="commonBundle" key="text.moreInfo"/></a><br><br>
                    <a href="https://esk.sberbank.ru/esClient/_ns/ProtectInfoPage.aspx" target="_blank">
                        <bean:message bundle="commonBundle" key="text.SBOL.security"/>
                    </a><br><br>
                    <a href="http://sberbank.ru/ru/person/dist_services/warning/"
                       class="paperEnterLink" target="_blank">О рисках при дистанционном банковском обслуживании</a>
                </td>
                <td class="tblHelpInfRightSpace"></td>
            </tr>
            <tr>
                <td class="tblHelpFtrLeftCorner">&nbsp;</td>
                <td class="tblHelpFtr">&nbsp;</td>
                <td class="tblHelpFtrRightCorner">&nbsp;</td>
            </tr>
            </table>
        </td>
        <td class="tblInfRightSpace"></td>
    </tr>
    <c:set var="error">
        <html:messages id="error" bundle="securityBundle">
            <%=error%>
        </html:messages>
    </c:set>
    <c:if test="${not empty error}">
    <tr>
        <td class="tblInfLeftSpace">&nbsp;</td>
        <td class="tblInf" colspan="3">&nbsp;</td>
        <td class="tblInfRightSpace">&nbsp;</td>
    </tr>
    <tr>
        <td class="tblInfLeftSpace"></td>
        <td class="tblInf" colspan="3">
            <table cellpadding="0" cellspacing="0" border="0" width="900px">
            <tr>
                <td class="tblErrHdrLeftCorner">&nbsp;</td>
                <td class="tblErrHdr">&nbsp;</td>
                <td class="tblErrHdrRightCorner">&nbsp;</td>
            </tr>
            <tr>
                <td class="tblErrInfLeftSpace"></td>
                <td class="tblErrInfSpace">
                    <table cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td class="iconSpace">
                            <img src="${imagePath}/iconMid_errcircle.gif" alt="" border="0">
                        </td>
                        <td width="100%">${error}</td>
                    </tr>
                    </table>
                </td>
                <td class="tblErrInfRightSpace"></td>
            </tr>
            <tr>
                <td class="tblErrFtrLeftCorner">&nbsp;</td>
                <td class="tblErrFtr">&nbsp;</td>
                <td class="tblErrFtrRightCorner">&nbsp;</td>
            </tr>
            </table>
        </td>
        <td class="tblInfRightSpace"></td>
    </tr>
    </c:if>
    <tr>
        <td class="tblFooterLeftCorner"><img src="${globalImagePath}/1x1.gif" alt="" border="0"></td>
        <td class="tblFooter" colspan="3">&nbsp;</td>
        <td class="tblFooterRightCorner"><img src="${globalImagePath}/1x1.gif" alt="" border="0"></td>
    </tr>
</table>

</div>
</html:form>