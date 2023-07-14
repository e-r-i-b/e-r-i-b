<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 21.01.2009
  Time: 12:56:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/chargeoff/list" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="personEdit">
<c:set var="form" value="${ChargeOffListForm}"/>
<tiles:put name="submenu" type="string" value="ChargeOffLog"/>
<tiles:put name="pageTitle" type="string" value="Журнал оплаты"/>

<tiles:put name="filter" type="string">
	<script type="text/javascript">
		addClearMasks(null,function(event)
		{
			clearInputTemplate('filter(operationDate)', DATE_TEMPLATE);
		});
	</script>

	<tiles:insert definition="filterTextField" flush="false">
	    <tiles:put name="label" value="label.operation.date"/>
	    <tiles:put name="bundle" value="personsBundle"/>
	    <tiles:put name="name" value="operationDate"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
    </tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
	    <tiles:put name="label" value="label.operation.state"/>
	   <tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="data">
		    <html:select property="filter(operationState)" styleClass="select">
				<html:option value="">Все</html:option>
				<html:option value="payed">Проведена</html:option>
				<html:option value="dept">Недостаточно средств</html:option>
			</html:select>
	    </tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.rows.max.count"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(maxRows)" styleClass="select">
					<html:option value="-1">Все</html:option>
					<html:option value="5">5</html:option>
					<html:option value="20">20</html:option>
					<html:option value="50">50</html:option>
					<html:option value="100">100</html:option>
					<html:option value="500">500</html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
</tiles:put>
 <tiles:put name="data" type="string">
	    <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="chargeOffList"/>
		<tiles:put name="head">
            <td><bean:message key="label.operation.datetime" bundle="personsBundle"/></td>
            <td><bean:message key="label.operation.document.number" bundle="personsBundle"/></td>
			<td><bean:message key="label.operation.type" bundle="personsBundle"/></td>
			<td><bean:message key="label.operation.amount" bundle="personsBundle"/></td>
			<td><bean:message key="label.operation.period" bundle="personsBundle"/></td>
			<td><bean:message key="label.operation.state" bundle="personsBundle"/></td>
			<td><bean:message key="label.operation.attempts" bundle="personsBundle"/></td>
		</tiles:put>
		<tiles:put name="data">
            <c:set var="lineNumber" value="0"/>
			<c:forEach var="chargeOffLog" items="${form.chargeOffLogs}">
				<c:set var="lineNumber" value="${lineNumber+1}"/>
					<tr class="ListLine<c:out value="${lineNumber%2}"/>">
                    <c:choose>
	                   <c:when test="${lineNumber<=form.listLimit}">
	                    <td class="ListItem">&nbsp;
		                    <c:if test="${not empty chargeOffLog.date}">
		                        <bean:write name="chargeOffLog" property="date.time" format="dd.MM.yyyy HH:mm:ss"/>
		                    </c:if>
	                    </td>
						<td class="ListItem">&nbsp;${chargeOffLog.id}</td>
						<td class="ListItem">&nbsp;
							<c:if test="${chargeOffLog.type == 'connection'}">Единовременная</c:if>
							<c:if test="${chargeOffLog.type == 'monthly'}">Абонентская</c:if>
						</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not empty chargeOffLog.amount}">
								<bean:write name="chargeOffLog"  property="amount.decimal" format="0.00"/>
								&nbsp; ${chargeOffLog.amount.currency.code}
							</c:if>
						</td>
						<td class="ListItem">&nbsp;
							<c:choose>
								<c:when test="${chargeOffLog.type == 'connection'}">
									<c:if test="${not empty chargeOffLog.periodFrom}">
										<bean:write name="chargeOffLog" property="periodFrom.time" format="dd.MM.yyyy"/>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${not empty chargeOffLog.periodFrom}">
										с&nbsp;<bean:write name="chargeOffLog" property="periodFrom.time" format="dd.MM.yyyy"/>
									</c:if>
									<c:if test="${not empty chargeOffLog.periodUntil}">
										&nbsp;по&nbsp;<bean:write name="chargeOffLog" property="periodUntil.time" format="dd.MM.yyyy"/>
									</c:if>
								</c:otherwise>
							</c:choose>
						</td>
						<td class="ListItem">&nbsp;
							<c:if test="${chargeOffLog.state=='payed'}">Проведена</c:if>
							<c:if test="${chargeOffLog.state=='dept'}">Недостаточно средств</c:if>
						</td>
						<td class="ListItem">&nbsp;
							<c:if test="${not empty chargeOffLog.attempt}">${chargeOffLog.attempt}</c:if>
						</td>
						</c:when>
	                    <c:otherwise>
							<tiles:put name="searchMessage">
							В результате поиска найдено слишком много платежей.
							На экран выведены первые <c:out value="${ChargeOffListForm.listLimit}"/>.
							Задайте более жесткие условия поиска.
							</tiles:put>
	                    </c:otherwise>
					</c:choose>
					</tr>
			</c:forEach>
		</tiles:put>
		<tiles:put name="isEmpty" value="${empty ChargeOffListForm.chargeOffLogs}"/>
		<tiles:put name="emptyMessage" value="Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;платежа.&nbsp;"/>
	    </tiles:insert>
</tiles:put>

</tiles:insert>
</html:form>
