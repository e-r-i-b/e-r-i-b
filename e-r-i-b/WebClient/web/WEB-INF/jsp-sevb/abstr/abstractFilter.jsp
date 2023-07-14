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

<html:form action="/private/accounts/abstract" onsubmit="return checkData(event);">

<tiles:insert definition="abstract">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:put name="submenu" type="string" value="Abstract/${pageContext.request.queryString}"/>
<!-- заголовок -->
<tiles:put name="pageTitle" type="string">
	ѕользователь:
	<bean:write name="ShowAccountAbstractForm" property="user.fullName"/>
	<c:choose>
		<c:when test="${!ShowAccountAbstractForm.copying}">
			. ¬ыписки по счетам
		</c:when>
		<c:otherwise>
			. »нформаци€ по счетам	
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
			<html:select property="selectedResources" multiple="true" size="3"
			             onkeydown="onTabClick(event,'fromDateString')">
				<!--список доступных счетов-->
				<c:forEach var="accountLink" items="${ShowAccountAbstractForm.accountLinks}">
					<c:set var="account" value="${accountLink.value}"/>
					<html:option value="a:${accountLink.id}"><c:out
							value="${account.number}(${account.currency.name}, ${account.description})"/></html:option>
				</c:forEach>
				<!--список доступных карт-->
				<!-- в северном банке в информации по счету не может быть карт-->
				<c:if test="${!ShowAccountAbstractForm.copying}">
					<c:forEach var="cardLink" items="${ShowAccountAbstractForm.cardLinks}">
						<c:set var="card" value="${cardLink.value}"/>
						<html:option value="c:${cardLink.id}">
							<c:set var="num" value="${card.number}"/>
							<c:set var="len" value="${fn:length(num)}"/>
							<%--<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>--%>
							 ${phiz:getCutCardNumber(card.number)}
							<c:if test="${card.main}">
								(основна€ карта)
							</c:if>
							<c:if test="${not card.main}">
								(дополнительна€ карта)
							</c:if>
						</html:option>
					</c:forEach>
				</c:if>
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
		if (!checkPeriod("fromDateString", "toDateString", "ѕериод с", "ѕериод по", true)) return false;
		var selectedAccounts = getElementValue("selectedResources");
		if (selectedAccounts == "") {
			alert("¬ыберите счета/карты");
			return false;
		}
		setEmptyAction(event);
		return true;
	}
	function callAbstractOperation(event, action, confirm)
	{
		preventDefault(event);
		res = getElementValue("selectedResources");
		if (res == "") alert("¬ыберите счета/карты");
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

  <!--данные-->
<tiles:put name="data" type="string">
	<div>
	<c:set var="form" value="${ShowAccountAbstractForm}"/>
	<c:choose>
		<c:when test="${!form.copying}">
			<%@ include file="/WEB-INF/jsp-sevb/abstr/abstract.jsp"%>
		</c:when>
		<c:otherwise>
			<%@ include file="/WEB-INF/jsp-sevb/abstr/information.jsp"%>
		</c:otherwise>
	</c:choose>
	</div>
<script>
	switchFilter(this);
</script>
</tiles:put>

</tiles:insert>
</html:form>
