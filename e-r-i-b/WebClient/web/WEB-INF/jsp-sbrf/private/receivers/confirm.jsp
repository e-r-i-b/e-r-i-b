<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/receivers/confirm" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="paymentMain">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.receiver)}"/>
<tiles:put name="submenu" type="string" value="PaymentReceivers"/>
<tiles:put name="pageTitle" type="string" value="Справочник получателей. Подтверждение получателя"/>

<!-- меню -->
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.cancel"/>
		<tiles:put name="commandHelpKey" value="button.cancel"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="action" value="/private/receivers/list.do"/>
	</tiles:insert>
</tiles:put>

<!-- собственно данные -->
<tiles:put name="data" type="string">
<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id" value="EditPaymentReceiver"/>
	<tiles:put name="name" value="Подтверждение получателя"/>
	<tiles:put name="description" value="Подтверждение получателя"/>
	<tiles:put name="buttons">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.confirm"/>
			<tiles:put name="commandHelpKey" value="button.confirm"/>
			<tiles:put name="bundle" value="commonBundle"/>
			<tiles:put name="image" value="iconSm_save.gif"/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
		</tiles:insert>
	</tiles:put>
	<tiles:put name="data">
		<tr>
			<td class="Width120 Label">Тип получателя</td>
			<td>
				<c:choose>
					<c:when test="${form.receiver.kind=='J' || form.receiver.kind =='B'}">
						Юридическое лицо
					</c:when>
					<c:when test="${form.receiver.kind=='P'}">
						Физическое лицо
					</c:when>
				</c:choose>
				&nbsp;
			</td>
		</tr>
		<tr style="height:20px">
			<td class="Width120 Label">
				<bean:message key="label.receiver.alias" bundle="paymentsBundle"/>
			</td>
			<td>
				<c:out value="${form.receiver.alias}"/>
			</td>
		</tr>
		<tr style="height:20px">
			<td class="Width120 Label">
				<bean:message key="label.receiver.name" bundle="paymentsBundle"/>
			</td>
			<td>
				<c:out value="${form.receiver.name}"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 Label">
				<bean:message key="label.receiver.inn" bundle="paymentsBundle"/>
			</td>
			<td>
				<c:out value="${form.receiver.INN}"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 Label">
				<bean:message key="label.receiver.account" bundle="paymentsBundle"/>
			</td>
			<td>
				<c:out value="${form.receiver.account}"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:left;" class="Width120 Label">
				&nbsp;
				<bean:message key="label.receiver.bank.title" bundle="paymentsBundle"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 Label">
				<bean:message key="label.receiver.bank.name" bundle="paymentsBundle"/>
			</td>
			<td>
				<c:out value="${form.receiver.bankName}"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 Label">
				<bean:message key="label.receiver.bank.bic" bundle="paymentsBundle"/>
			</td>
			<td>
				<c:out value="${form.receiver.bankCode}"/>
			</td>
		</tr>
		<tr>
			<td class="Width120 Label">
				<bean:message key="label.receiver.bank.corAccount" bundle="paymentsBundle"/>
			</td>
			<td>
				<c:out value="${form.receiver.correspondentAccount}"/>
			</td>
		</tr>
	</tiles:put>
	<tiles:put name="confirmInfo">
		<c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
		<tiles:insert definition="${confirmTemplate}" flush="false">
			<tiles:put name="confirmRequest" beanName="confirmRequest"/>
			<tiles:put name="message"
			           value="Проверьте правильность заполнения полей документа и нажмите кнопку \"Подтвердить\", чтобы передать документ в банк на исполнение."/>
		</tiles:insert>
	</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>
</tiles:put>

</tiles:insert>
</html:form>