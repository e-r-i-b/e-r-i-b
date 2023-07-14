<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/forms/GoodsAndServicesPayment">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="appointment" value="${form.appointment}"/>

<tiles:insert definition="paymentCurrent">
	<tiles:put name="submenu" type="string" value="GoodsAndServicesPayment/${appointment}"/>

	<tiles:put name="pageTitle"><bean:message key="label.payments.services" bundle="commonBundle"/></tiles:put>

	<tiles:put name="menu" type="string">

		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.close"/>
			<tiles:put name="commandHelpKey" value="button.close.help"/>
			<tiles:put name="bundle"         value="paymentsBundle"/>
			<tiles:put name="image"          value=""/>
			<tiles:put name="action"         value="/private/payments/payments.do?name=GoodsAndServicesPayment"/>
		</tiles:insert>

	</tiles:put>

<tiles:put name="data" type="string">

<input type="hidden" name="appointment" value="${appointment}"/>
<html:hidden property="template"/>
<html:hidden property="type"/>

<c:set var="styleClass" value="label120"/>
<c:set var="styleClassTitle" value="pmntInfAreaTitle"/>

<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id">${appointment}</tiles:put>
	<tiles:put name="name">${form.description}</tiles:put>
	<tiles:put name="description">${form.detailedDescription}</tiles:put>
	<tiles:put name="data">
		 <tr>
			<td colspan="2" class="${styleClassTitle}">ѕолучатель платежа</td>
		</tr>
		<tr>
			<td>
				<c:choose>
                <c:when test="${not empty form.receivers}">
					<c:forEach items="${form.receivers}" var="receiver">
						<tr onclick="document.getElementById('check${appointment}${receiver.key}').checked=true">
							<td></td>
							<td>
								<html:radio property="fields(receiverSelect)" value="${receiver.key}" style="border:0" styleId="check${appointment}${receiver.key}">
									<img id="${appointment}${receiver.key}"
										 src="${globalImagePath}/logotips/${appointment}/${receiver.key}.gif"
										 height="30"
										 width="55"
										 onerror="document.getElementById('${appointment}${receiver.key}').style.display = 'none';"
										 />
									${receiver.value}
								</html:radio>
							</td>
						</tr>
					</c:forEach>
	            </c:when>
                <c:otherwise>
	                <tr>
		                <td colspan="2">
			                <html:select property="fields(receiverSelect)">
			                    <option value="">нет получателей</option>
			                </html:select>
		                </td>
	                </tr>
                </c:otherwise>
            </c:choose>
			</td>
		</tr>
	</tiles:put>
	<tiles:put name="buttons">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey"     value="button.next"/>
			<tiles:put name="commandHelpKey" value="button.next"/>
			<tiles:put name="bundle"  value="paymentsBundle"/>
			<tiles:put name="image" value="iconSm_next.gif"/>
			<tiles:put name="isDefault" value="true"/>
		</tiles:insert>
	</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>
	
</tiles:put>
</tiles:insert>
</html:form>
