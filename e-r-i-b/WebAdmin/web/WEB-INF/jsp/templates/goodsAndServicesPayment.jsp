<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/templates/GoodsAndServicesPayment">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="serviceMain">
	<tiles:put name="submenu" type="string" value="ListBankSmsTemplates"/>

	<tiles:put name="pageTitle"><bean:message key="category.operations.SERVICE_PAYMENT" bundle="paymentServiceBundle"/></tiles:put>

	<tiles:put name="menu" type="string">

		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.close"/>
			<tiles:put name="commandHelpKey" value="button.close.help"/>
			<tiles:put name="bundle"         value="templatesMainBundle"/>
			<tiles:put name="image"          value=""/>
			<tiles:put name="action"         value="/templates/templates"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>

	</tiles:put>

<tiles:put name="data" type="string">
<input type="hidden" name="appointment" value="${form.appointment}"/>
<html:hidden property="template"/>
<html:hidden property="type"/>
<c:set var="appointment" value="${form.appointment}"/>

<c:set var="styleClass" value="label120"/>
<c:set var="styleClassTitle" value=""/>

<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id">${form.appointment}</tiles:put>
	<tiles:put name="name">${form.description}</tiles:put>
	<tiles:put name="description">${form.detailedDescription}</tiles:put>
	<tiles:put name="data">
		 <tr>
			<td colspan="2" class="${styleClassTitle}">Получатель платежа</td>
		</tr>
		<tr>
			<td class="${styleClass}">Наименование</td>
			<td>
				<html:select property="fields(receiverSelect)">
					<c:set var="receivers" value="${form.receivers}"/>
					<c:choose>
						<c:when test="${not empty receivers}">
							<html:options collection="receivers" property="key" labelProperty="value"/>
						</c:when>
						<c:otherwise>
							<option value="">нет получателей</option>
						</c:otherwise>
					</c:choose>
				</html:select>
			</td>
		</tr>
	</tiles:put>
	<tiles:put name="buttons">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey"     value="button.next"/>
			<tiles:put name="commandHelpKey" value="button.next"/>
			<tiles:put name="bundle"  value="templatesMainBundle"/>
			<tiles:put name="isDefault" value="true"/>
		</tiles:insert>
	</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>

</tiles:put>
</tiles:insert>
</html:form>
