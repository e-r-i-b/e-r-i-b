<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<style>
	.docTableBorder{border-top:1px solid #000000;border-left:1px solid #000000;}
	.docTdBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;}
	.docTdBorderFirst{border-top:1px solid #000000;border-bottom:1px solid #000000;border-right:1px solid #000000;}
	.docTdBorderFirstAngle{border-top:1px solid #000000;border-bottom:1px solid #000000;border-right:1px solid #000000;border-left:1px solid #000000;}
	.docTdBorderSecond{border-bottom:1px solid #000000;border-right:1px solid #000000;border-left:1px solid #000000;}
	.textPadding{padding-left:4;padding-right:4;}
	.font10{font-family:Times New Roman;font-size:10pt;}
	.font8{font-family:Times New Roman;font-size:8pt;}
</style>

<html:form action="/private/accounts/abstract" onsubmit="return checkData(event);">

<tiles:insert definition="accountInfo">
<tiles:put name="mainmenu" value="Info" />
<tiles:put name="submenu" type="string" value="Abstract"/>
<!-- заголовок -->
<tiles:put name="pageTitle" type="string">
	Пользователь:
	<bean:write name="ShowAccountAbstractForm" property="user.fullName"/>
	<c:choose>
		<c:when test="${!ShowAccountAbstractForm.copying}">
			. Выписки по счетам
		</c:when>
		<c:otherwise>
			. Информация по счетам	
		</c:otherwise>
	</c:choose>
</tiles:put>

<!-- меню -->
<tiles:put name="menu" type="string">
	<c:choose>
		<c:when test="${!ShowAccountAbstractForm.copying}">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.printAbstract"/>
				<tiles:put name="commandHelpKey" value="button.printAbstract.help"/>
				<tiles:put name="bundle" value="commonBundle"/>
				<tiles:put name="onclick" value="openPrint(event)"/>
				<tiles:put name="image" value="print.gif"/>
			</tiles:insert>
		</c:when>
		<c:otherwise>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.printAccountInfromation"/>
				<tiles:put name="commandHelpKey" value="button.printAbstract.help"/>
				<tiles:put name="bundle" value="commonBundle"/>
				<tiles:put name="onclick" value="openPrint(event,'information')"/>
				<tiles:put name="image" value="print.gif"/>
			</tiles:insert>
		</c:otherwise>
	</c:choose>
	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="image" value="back.gif"/>
	</tiles:insert>
</tiles:put>
<!-- фильтр -->
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.account"/>
		<tiles:put name="mandatory" value="true"/>
		<tiles:put name="data">
            <tiles:importAttribute/>
            <c:set var="globalImagePath" value="${globalUrl}/images"/>
            <c:set var="imagePath" value="${skinUrl}/images"/>

			<html:select property="selectedResources" multiple="true" size="3"
			             onkeydown="onTabClick(event,'fromDateString')">
				<!--список доступных счетов-->
				<c:forEach var="accountLink" items="${ShowAccountAbstractForm.accountLinks}">
					<html:option value="a:${accountLink.id}"><c:out
							value="${phiz:getFormattedAccountNumber(accountLink.number)}(${accountLink.currency.name}, ${accountLink.description})"/></html:option>
				</c:forEach>
				<!--список доступных карт-->
				<c:forEach var="cardLink" items="${ShowAccountAbstractForm.cardLinks}">
					<html:option value="c:${cardLink.id}">
						<c:set var="num" value="${cardLink.number}"/>
						<c:set var="len" value="${fn:length(num)}"/>
						<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
						<c:if test="${cardLink.main}">
							(основная карта)
						</c:if>
						<c:if test="${not cardLink.main}">
							(дополнительная карта)
						</c:if>
					</html:option>
				</c:forEach>
				<!--список доступных вкладов-->
				<c:forEach var="depositLink" items="${ShowAccountAbstractForm.depositLinks}">
					<c:set var="depoit" value="${depositLink.deposit}"/>
					<html:option value="d:${depoit.id}">
						<c:out value="${depoit.description}"/>
					</html:option>
	            </c:forEach>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.date"/>
		<tiles:put name="mandatory" value="true"/>
		<tiles:put name="data">
			&nbsp;c:
				<html:text property="fromDateString" styleClass="filterInput" size="10"
				           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDateString')"/>
				&nbsp;по&nbsp;
				<html:text property="toDateString" styleClass="filterInput" size="10"
				           onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		</tiles:put>
	</tiles:insert>
<script language="JavaScript">
	document.imgPath = "${imagePath}/";

	function clearMasks(event)
	{
		clearInputTemplate("fromDateString", DATE_TEMPLATE);
		clearInputTemplate("toDateString", DATE_TEMPLATE);
	}
	function checkData(event)
	{
		if (!checkPeriod("fromDateString", "toDateString", "Период с", "Период по", true)) return false;
		var selectedAccounts = getElementValue("selectedResources");
		if (selectedAccounts == "") {
			alert("Выберите счета/карты");
			return false;
		}
		setEmptyAction(event);
		return true;
	}
	function callAbstractOperation(event, action, confirm)
	{
		preventDefault(event);
		res = getElementValue("selectedResources");
		if (res == "") alert("Выберите счета/карты");
		else callOperation(event, action, confirm);
	}

	//печать выписки по счету - если printInformation не задан
	// иначе - печать информации по счету
	function openPrint(event, printInformation)
	{
		if (checkData(event))
		{   /* todo: реализовать передачу параметров через форму */
			var req;
			req = 'print.do?';
			req = req + "fromDateString=<bean:write name='ShowAccountAbstractForm' property="fromDateString"/>&";
			req = req + "toDateString=<bean:write name='ShowAccountAbstractForm' property="toDateString"/>";
		<logic:iterate id="res" name="ShowAccountAbstractForm" property="selectedResources">
			req += "&sel=<bean:write name='res'/>";
		</logic:iterate>
			if (printInformation != undefined)
			{
				req += "&copying=true";
			}
			openWindow(event, req,"","resizable=1,menubar=1,toolbar=1,scrollbars=1,width=700");
		}
	}
</script>
</tiles:put>

<tiles:put name="additionalFilterButtons" type="string">
    <c:choose>
		<c:when test="${!ShowAccountAbstractForm.copying}">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.printAbstract"/>
				<tiles:put name="commandHelpKey" value="button.printAbstract.help"/>
				<tiles:put name="bundle" value="commonBundle"/>
				<tiles:put name="onclick" value="openPrint(event)"/>
				<tiles:put name="image" value="print.gif"/>
			</tiles:insert>
		</c:when>
		<c:otherwise>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.printAccountInfromation"/>
				<tiles:put name="commandHelpKey" value="button.printAbstract.help"/>
				<tiles:put name="bundle" value="commonBundle"/>
				<tiles:put name="onclick" value="openPrint(event,'information')"/>
				<tiles:put name="image" value="print.gif"/>
			</tiles:insert>
		</c:otherwise>
	</c:choose>
</tiles:put>

<!--данные-->
<tiles:put name="data" type="string">
	<c:set var="form" value="${ShowAccountAbstractForm}"/>
    <c:set var="abstractMap" value="${form.accountAbstract}"/>

	<c:choose>
		<c:when test="${!form.copying}">
            <c:choose>
                <c:when test="${empty abstractMap}">
                    <table width="100%">
                        <tr>
                            <td align="center" class="messageTab"><br>
                                Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;счета,<br>
                                соответствующего&nbsp;заданному&nbsp;фильтру!
                            </td>
                        </tr>
                    </table>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${abstractMap}" var="listElement">
                        <c:set var="accountLink" value="${listElement.key}"/>
                        <c:set var="account" value="${accountLink.account}"/>
                        <c:set var="resourceAbstract" value="${listElement.value}"/>
                        <c:set var="accountBalans" value="${fn:substring(accountLink.number, 0,5)}"/>
                        <c:set var="codeFields" value="${accountLink.office.code.fields}"/>

                        <c:choose>
                            <c:when test="${accountBalans != '40817' && accountBalans != '40820'}">
                                <%-- Выписка из лицевого счета по вкладу --%>
                                <%@ include file="/WEB-INF/jsp-sbrf/abstr/abstractDeposit.jsp"%>
                            </c:when>
                            <c:otherwise>
                                <%-- Выписка из лицевого счета --%>
                                <%@ include file="/WEB-INF/jsp-sbrf/abstr/abstractAccount.jsp"%>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
		</c:when>
		<c:otherwise>
			<%@ include file="/WEB-INF/jsp-sbrf/abstr/information.jsp"%>
		</c:otherwise>
	</c:choose>
<script>
	switchFilter(this);
</script>
</tiles:put>

</tiles:insert>
</html:form>
