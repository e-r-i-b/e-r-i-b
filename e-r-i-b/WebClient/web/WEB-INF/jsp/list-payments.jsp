<%--
  Created by IntelliJ IDEA.
  User: Kosyakova
  Date: 31.01.2007
  Time: 14:46:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.rssl.phizic.web.client.payments.forms.ShowMetaPaymentListForm" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/common" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${ShowCommonPaymentListForm}"/>


<tiles:insert definition="operationsInfo">
<tiles:put name="pageTitle" value="Журнал операций"/>
<tiles:put name="submenu" type="string" value="PaymentList/${pageContext.request.queryString}"/>
<!-- Меню -->

<tiles:put name="menu" type="string">
	<script type="text/javascript">
        function edit(event)
		{
            checkIfOneItem("selectedIds");
			if (!checkOneSelection('selectedIds', 'Выберите один перевод'))
				return;
			var ids = document.getElementsByName("selectedIds");
			preventDefault(event);
			for (var i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					var r = ids.item(i).parentNode.parentNode;
					var state = trim(getElementTextContent(r.cells[13]));
					if (state == 'INITIAL')
					{
						<c:set var="u" value="/private/payments/payment.do?"/>
						var url = "${phiz:calculateActionURL(pageContext,u)}";
						window.location = url + "id=" + getFirstSelectedId('selectedIds');
						return;
					}
					var formName = getElementTextContent(r.cells[12]);
					if ((state != 'SAVED') || (trim(formName) != 'ContactPayment'))
					{
						alert("Документ передан на обработку в банк и не может быть отредактирован!");
						return;
					}
					<c:set var="u" value="/private/payments/add.do?form=EditContactPayment"/>
					var url = "${phiz:calculateActionURL(pageContext,u)}";
					window.location = url + "&parentId=" + getFirstSelectedId('selectedIds');
					return;
				}
			}
		}
		function addParam(params, name)
		{
			var param;
			var elemValue = getElementValue(name);
			if ( elemValue != "" )
			{
				param = name + "=" + elemValue;
				if (params != "")
				{
					params = params +'&' + param;
				}
				else
				{
					params = param;
				}
			}
			return params;
		}

		function printPaymentList(event, personId)
		{
			<c:set var="u" value="/private/payments/common/print.do"/>
			var url = "${phiz:calculateActionURL(pageContext,u)}";
			var params = "";
			params = addParam(params,"filter(number)");
			params = addParam(params,"filter(formName)");
			params = addParam(params,"filter(account)");
			params = addParam(params,"filter(state)");
			params = addParam(params,"filter(fromDate)");
			params = addParam(params,"filter(toDate)");
			params = addParam(params,"filter(fromAdmissionDate)");
			params = addParam(params,"filter(toAdmissionDate)");
			params = addParam(params,"filter(fromAmount)");
			params = addParam(params,"filter(toAmount)");
			params = addParam(params,"filter(receiverName)");
			openWindow(event,url+'?status=<c:out value="${ShowCommonPaymentListForm.status}"/>&'+params,"","resizable=1,menubar=1,toolbar=0,scrollbars=1,width=0,height=0");
		}
	</script>

	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.print"/>
		<tiles:put name="commandHelpKey" value="button.printPaymentList"/>
		<tiles:put name="bundle"         value="paymentsBundle"/>
		<tiles:put name="onclick">
		  printPaymentList(event,${empty form.person ? 'null': form.person.id})
		</tiles:put>
	</tiles:insert>

</tiles:put>


<tiles:put name="filter" type="string">
	<c:set var="colCount" value="2" scope="request"/>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.amount"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="name" value="Amount"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.formName"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="data">
			<c:set var="formList" value="${forms}"/>
			<html:select property="filter(formName)" styleClass="filterSelect" style="width:200px">
				<html:option value="">Все</html:option>
				<html:optionsCollection name="form" property="forms" value="name" label="description"/>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<c:set var="acountList" value="${form.accounts}"/>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.number"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="name" value="number"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.date"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="name" value="Date"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.account"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(account)" styleClass="filterSelect" style="width:168px">
				<html:option value="">Все</html:option>
				<c:forEach items="${form.accounts}" var="accountLink" varStatus="ls">
					<html:option value="${accountLink.account.number}">${accountLink.account.number}
						&nbsp;[${accountLink.account.type}
						]&nbsp;${accountLink.account.currency.name}</html:option>
				</c:forEach>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.receiverName"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="name" value="receiverName"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.admissionDate"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="name" value="AdmissionDate"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.state"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="data">
			<c:set var="status" value="${form.status}"/>
			<html:select property="filter(state)" styleClass="filterSelect" style="width:200px">
				<html:option value="">Все</html:option>
				<html:option value="DRAFT">Черновик</html:option>
				<html:option value="INITIAL,SAVED">Введен</html:option>
				<html:option value="DISPATCHED,SENDED,STATEMENT_READY">Обрабатывается / Принят</html:option>
				<html:option value="COMPLETION">Требуется доработка</html:option>
				<html:option value="CONSIDERATION">В рассмотрении</html:option>
				<html:option value="APPROVED">Утвержден</html:option>
				<html:option value="ACCEPTED">Одобрен</html:option>
				<c:if test="${status == 'all'}">
					<html:option value="RECALLED">Отозван</html:option>
					<html:option value="EXECUTED">Исполнен</html:option>
					<html:option value="RECEIVED">Получен банком получателем</html:option>
					<html:option value="SUCCESSED">Выдан</html:option>
					<html:option value="CANCELATION">Аннулирование</html:option>
					<html:option value="MODIFICATION">Изменён</html:option>
					<html:option value="RETURNED">Возвращен</html:option>
					<html:option value="REFUSED">Отказан</html:option>
				</c:if>
			</html:select>
		</tiles:put>
	</tiles:insert>
</tiles:put>
<!-- данные -->
<tiles:put name="data" type="string">

<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="AccountsListTable"/>
	<tiles:put name="image" value=""/>
	<tiles:put name="text" value="Список операций"/>
	<tiles:put name="buttons">
		<script type="text/javascript">
		    var addUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/payment')}";
		    function addCopy()
		    {
			    if (!checkOneSelection("selectedIds", 'Выберите одну новость'))
				    return;
			    var paymentId = getRadioValue(document.getElementsByName("selectedIds"));
			    window.location = addUrl + "?copying=true&id=" + paymentId;
		    }
	    </script>

        <tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.withdraw"/>
			<tiles:put name="commandHelpKey" value="button.withdraw.help"/>
			<tiles:put name="bundle" value="paymentsBundle"/>
			<tiles:put name="image" value="iconSm_withdraw.gif"/>
            <tiles:put name="validationFunction">
                function()
                {
                    checkIfOneItem("selectedIds");
                    return checkSelection('selectedIds', 'Выберите заявки');
                }
            </tiles:put>
		</tiles:insert>

		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.edit"/>
			<tiles:put name="commandHelpKey" value="button.edit.help"/>
			<tiles:put name="bundle" value="paymentsBundle"/>
			<tiles:put name="image" value="iconSm_edit.gif"/>
			<tiles:put name="onclick" value="javascript:edit(event)"/>
		</tiles:insert>

		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.addCopy"/>
			<tiles:put name="commandHelpKey" value="button.addCopy"/>
			<tiles:put name="bundle" value="paymentsBundle"/>
			<tiles:put name="image"  value=""/>
			<tiles:put name="onclick" value="addCopy();"/>
		</tiles:insert>
	</tiles:put>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="list" property="data" selectBean="businessDocument">
            <sl:collectionParam id="selectType" value="checkbox"/>
            <sl:collectionParam id="selectName" value="selectedIds"/>
            <sl:collectionParam id="selectProperty" value="id"/>

            <c:set var="docCommissionAmount" value="0"/>
            <c:set var="form" value="${listElement[1]}"/>
            <c:set var="businessDocument" value="${listElement[0]}"/>

            <c:choose>
                <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
                    <c:set var="totalAmount"
                           value="${totalAmount+businessDocument.chargeOffAmount.decimal+businessDocument.commission.decimal}"/>
                    <c:set var="totalDocAmount" value="${totalDocAmount+businessDocument.chargeOffAmount.decimal}"/>
                    <c:set var="totalCommissionAmount"
                           value="${totalCommissionAmount+businessDocument.commission.decimal}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="totalAmount" value="0"/>
                    <c:set var="totalDocAmount" value="0"/>
                    <c:set var="totalCommissionAmount" value="0"/>
                </c:otherwise>
            </c:choose>

            <sl:collectionItem width="8%" title="Дата и время операции">
                <c:if test="${! empty businessDocument.operationDate}">
					<bean:write name="businessDocument" property="operationDate.time" format="dd.MM.yyyy HH:mm:ss"/>
				</c:if>
				&nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="8%" title="Дата приема к исполнению">
                <c:if test="${! empty businessDocument.admissionDate}">
					<bean:write name="businessDocument" property="admissionDate.time" format="dd.MM.yyyy"/>
				</c:if>
				&nbsp;
            </sl:collectionItem>
            <sl:collectionItem
                    width="5%"
                    title="Номер"
                    value="${businessDocument.documentNumber}"
                    action="/private/payments/default-action.do?id=${businessDocument.id}&objectFormName=${businessDocument.formName}&stateCode=${businessDocument.state.code}"
                    />
            <sl:collectionItem width="12%" title="Вид операции" value="${form.description}"/>
            <sl:collectionItem width="13%" title="Счет списания">
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
			        <c:if test="${not empty businessDocument.chargeOffAccount}">
                        <bean:write name="businessDocument" property="chargeOffAccount"/>
                    </c:if>
		        </c:if>
                &nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="14%" title="Наименование получателя платежа">
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
			        <c:if test="${not empty businessDocument.receiverName}">
                        <bean:write name="businessDocument" property="receiverName"/>
                    </c:if>
		        </c:if>
                &nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="8%" title="Сумма платежа">
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					<c:if test="${businessDocument.chargeOffAmount != null}">
						<bean:write name="businessDocument" property="chargeOffAmount.decimal" format="0.00"/>
					</c:if>
				</c:if>
				&nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="8%" title="Сумма комиссии">
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
				    <c:set var="docCommissionAmount" value="0"/>
					<c:set var="docCommissionAmount" value="${docCommissionAmount + businessDocument.commission.decimal}"/>
				    <bean:write name="docCommissionAmount" format="0.00"/>
				</c:if>
				&nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="8%" title="Сумма операции">
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					<c:set var="docTotalAmount" value="${businessDocument.chargeOffAmount.decimal+businessDocument.commission.decimal}"/>
					<bean:write name="docTotalAmount" format="0.00"/>
				</c:if>
				&nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="6%" title="Валюта">
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
                    <c:out value="${businessDocument.chargeOffAmount.currency.code}"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem width="7%" title="Статус">
                <c:set var="code" value="${businessDocument.state.code}"/>
				<c:set var="isInstance"
                       value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.DefaultClaim') || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.DepositOpeningClaim') || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.LoanClaim')}"/>

                <sl:collectionItemParam id="value" value="Черновик"  condition="${code=='DRAFT'}"/>
                <sl:collectionItemParam id="value" value="Введен"    condition="${code=='SAVED'}"/>
                <sl:collectionItemParam id="value" value="Введен"    condition="${code=='INITIAL'}"/>
                <sl:collectionItemParam id="value" value="Исполнен"  condition="${code=='EXECUTED'}"/>
                <sl:collectionItemParam id="value" value="Отказан"   condition="${code=='REFUSED'}"/>
                <sl:collectionItemParam id="value" value="Отозван"   condition="${code=='RECALLED'}"/>
                <sl:collectionItemParam id="value" value="Одобрен"   condition="${code=='ACCEPTED'}"/>
                <sl:collectionItemParam id="value" value="Выдан"     condition="${code=='SUCCESSED'}"/>
                <sl:collectionItemParam id="value" value="Изменён"   condition="${code=='MODIFICATION'}"/>
                <sl:collectionItemParam id="value" value="Возвращен" condition="${code=='RETURNED'}"/>
                <sl:collectionItemParam id="value" value="Утвержден" condition="${code=='APPROVED'}"/>
                <sl:collectionItemParam id="value" value="Принят"    condition="${isInstance && ((code=='SENDED') || (code=='DISPATCHED' || code=='STATEMENT_READY'))}"/>
                <sl:collectionItemParam id="value" value="Аннулирован" condition="${code=='CANCELATION'}"/>
                <sl:collectionItemParam id="value" value="В рассмотрении"  condition="${code=='CONSIDERATION'}"/>
                <sl:collectionItemParam id="value" value="Обрабатывается"  condition="${not isInstance && ((code=='SENDED') || (code=='DISPATCHED' || code=='STATEMENT_READY'))}"/>
                <sl:collectionItemParam id="value" value="Требуется доработка" condition="${code=='COMPLETION'}"/>
                <sl:collectionItemParam id="value" value="Получен банком получателем" condition="${code=='RECEIVED'}"/>
            </sl:collectionItem>
            <!--
			Два последних столбца не удалять!!! Нужны для определения возможности отзыва и редактирования
			-->
            <sl:collectionItem hidden="true" value="${businessDocument.formName}"/>
            <sl:collectionItem hidden="true" value="${businessDocument.state.code}"/>
        </sl:collection>

	</tiles:put>
	<tiles:put name="isEmpty" value="${empty ShowCommonPaymentListForm.data}"/>
	<tiles:put name="emptyMessage" value="Нет операций"/>
</tiles:insert>
</tiles:put>

</tiles:insert>
</html:form>
