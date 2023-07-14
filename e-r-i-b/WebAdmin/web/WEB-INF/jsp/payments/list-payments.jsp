<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 10.06.2008
  Time: 21:01:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/payments/list">
<tiles:insert definition="paymentsMain">
<tiles:put name="pageTitle" type="string" value="Список платежей"/>
<c:set var="form" value="${PaymentListForm}"/>
<%-- фильтр --%>
<tiles:put name="filter" type="string">
	<c:set var="colCount" value="2" scope="request"/>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.number"/>
		<tiles:put name="bundle" value="claimsBundle"/>
		<tiles:put name="name" value="number"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.claim.state"/>
		<tiles:put name="bundle" value="claimsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(state)" styleClass="filterSelect" style="width:200px">
				<html:option value="">Все</html:option>
				<html:option value="E,D">Отказан</html:option>
				<html:option value="S">Исполнен</html:option>
				<html:option value="Z,W">Обрабатывается</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.type"/>
		<tiles:put name="bundle" value="claimsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(type)" styleClass="filterSelect" style="width:210px">
				<html:option value="">Все</html:option>
				<html:optionsCollection name="form" property="forms" value="name" label="description"/>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.receiverName"/>
		<tiles:put name="bundle" value="claimsBundle"/>
		<tiles:put name="name" value="receiverName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.receiverName"/>
		<tiles:put name="bundle" value="claimsBundle"/>
		<tiles:put name="name" value="receiverName"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.date"/>
		<tiles:put name="bundle" value="claimsBundle"/>
		<tiles:put name="name" value="Date"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.admissionDate"/>
		<tiles:put name="bundle" value="claimsBundle"/>
		<tiles:put name="name" value="AdmissionDate"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>

	<script type="text/javascript">
		function initTemplates()
		{
		}

		function submit(event)
		{
			var frm = window.opener.document.forms.item(0);

			frm.action = '';

			frm.submit();

		}

		initTemplates();
	</script>
</tiles:put>

<%-- данные --%>
<tiles:put name="data" type="string">
	<table cellpadding="0" cellspacing="0" class="MaxSize">
    <tr>
	<td height="100%">
	<c:choose>
		<c:when test="${not empty form.payments}">
			<table cellspacing="0" cellpadding="0" width="100%" class="userTab"
			       style="margin-top:4px;" id="ServicesTable">

				<tr class="titleTable">
					<td width="40px">
						<input name="isSelectedAllItems" type="checkbox" style="border:none"
						       onclick="switchSelection('isSelectedAllItems','selectedIds')"/>
					</td>
					<td nowrap="true">
						<bean:message key="label.payment.operation.date" bundle="formsBundle"/>
					</td>
					<td nowrap="true">
						<bean:message key="label.payment.number" bundle="formsBundle"/>
					</td>
					<td nowrap="true">
						<bean:message key="label.payment.operation.type" bundle="formsBundle"/>
					</td>
					<td width="200px" nowrap="true">
						<bean:message key="label.payment.receiver.name" bundle="formsBundle"/>
					</td>
					<td nowrap="true">
						<bean:message key="label.payment.operation.sum" bundle="formsBundle"/>
					</td>
					<td nowrap="true">
						<bean:message key="label.payment.operation.currency" bundle="formsBundle"/>
					</td>
					<td nowrap="true">
						<bean:message key="label.payment.status" bundle="formsBundle"/>
					</td>
					<td nowrap="true">
						<bean:message key="label.payment.admission.date" bundle="formsBundle"/>
					</td>
				</tr>
				<c:set var="lineNumber" value="0"/>
				<c:set var="paymentList" value="${form.payments}"/>
				<c:forEach items="${paymentList}" var="listElement">
					<c:set var="payment" value="${listElement[0]}"/>
					<c:set var="form" value="${listElement[1]}"/>
					<c:set var="lineNumber" value="${lineNumber+1}"/>
					<c:choose>
						<c:when test="${lineNumber<=form.listLimit}">
						<tr class="listLine${lineNumber % 2}">
							<td class="listItem" nowrap="true" align="center">
								<html:multibox property="selectedIds" style="border:none">
									<bean:write name="payment" property="id"/>
								</html:multibox>
							</td>
							<td class="listItem" nowrap="true">
								<c:if test="${! empty payment.operationDate}">
									<bean:write name="payment" property="operationDate.time" format="dd.MM.yyyy HH:mm:ss"/>
								</c:if>
								&nbsp;
							</td>
							<td class="listItem" nowrap="true">
								&nbsp;
								<html:link action="/payments/payment.do?id=${payment.id}">
									<bean:write name="payment" property="documentNumber"/>
								</html:link>
								&nbsp;
							</td>
							<td class="listItem" nowrap="true">
								&nbsp;
								<bean:write name="form" property="description"/>
								&nbsp;
							</td>
							<td class="listItem">
								&nbsp;
								<bean:write name="payment" property="receiverName"/>
								&nbsp;
							</td>
							<td class="listItem" nowrap="true">
								<c:set var="operationAmount" value="${payment.chargeOffAmount.decimal + payment.commission.decimal}"/>				    
								<bean:write name="operationAmount" format="0.00"/>
								&nbsp;
							</td>
							<td class="listItem" nowrap="true">
								<c:if test="${chargeOffAmount.currency.name != null}"> 
									<bean:write name="payment" property="chargeOffAmount.currency.name" format="0.00"/>
								</c:if>
								&nbsp;
							</td>
							<td class="listItem" nowrap="true">
								&nbsp;
								<c:set var="code" value="${payment.state.code}"/>
								<c:choose>
									<c:when test="${code=='E'}">Отказан</c:when>
									<c:when test="${code=='D'}">Отказан</c:when>
									<c:when test="${code=='W'}">Обрабатывается</c:when>
									<c:when test="${code=='S'}">Исполнен</c:when>
									<c:when test="${code=='Z'}">Обрабатывается</c:when>
								</c:choose>
								&nbsp;
							</td>
							<td class="listItem">
								<c:if test="${! empty payment.admissionDate}">
									<bean:write name="payment" property="admissionDate" format="dd.MM.yyyy"/>
								</c:if>
								&nbsp;
							</td>
						</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="listItem" colspan="9">
									В результате поиска найдено слишком много платежей.
									На экран выведены первые
									<c:out value="${form.listLimit}"/>
									.
									Задайте более жесткие условия поиска.
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<table width="100%" cellpadding="4">
				<tr>
					<td class="messageTab" align="center">В списке платежей нет элементов!</td>
				</tr>
			</table>
			<script>hideTitle("ServicesTable");</script>
		</c:otherwise>
	</c:choose>
	</td>
	</tr>
	</table>
</tiles:put>
</tiles:insert>
</html:form>
