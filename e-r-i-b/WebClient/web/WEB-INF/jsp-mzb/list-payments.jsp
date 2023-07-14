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
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/common" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${ShowCommonPaymentListForm}"/>


<tiles:insert definition="transferList">
<tiles:put name="pageTitle" value="Журнал операций"/>
<tiles:put name="submenu" type="string" value="PaymentList/${pageContext.request.queryString}"/>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<!-- Меню -->

<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function recall(event)
		{
			if (!checkOneSelection('selectedIds', 'Выберите один перевод'))
				return;
			var ids = document.getElementsByName("selectedIds");
			preventDefault(event);
			for (i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					var r = ids.item(i).parentNode.parentNode;
					var state = trim(getElementTextContent(r.cells[13]));
					if (state != 'intermediate')
					{
						alert("Отзыв перевода со статусом " + trim(getElementTextContent(r.cells[10])) + " невозможен!");
						return;
					}					
					var formName = trim(getElementTextContent(r.cells[12]));
					if ((formName=="GoodsAndServicesPayment") || (formName=="CardReplenishmentPayment"))
					{
						alert("Отзыв перевода \"" + trim(getElementTextContent(r.cells[3])) + "\" невозможен!");
						return;
					}
				<c:set var="u" value="/private/payments/add.do?form=RecallPayment"/>
					var url = "${phiz:calculateActionURL(pageContext,u)}";
					window.location = url + "&parentId=" + getFirstSelectedId('selectedIds');
					return;
				}
			}
		}

		function edit(event)
		{
			if (!checkOneSelection('selectedIds', 'Выберите один перевод'))
				return;
			var ids = document.getElementsByName("selectedIds");
			preventDefault(event);
			for (i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					var r = ids.item(i).parentNode.parentNode;
					var state = trim(getElementTextContent(r.cells[11]));
					if (state == 'initial')
					{
					<c:set var="u" value="/private/payments/payment.do?"/>
						var url = "${phiz:calculateActionURL(pageContext,u)}";
						window.location = url + "id=" + getFirstSelectedId('selectedIds');
						return;
					}
					var formName = trim(getElementTextContent(r.cells[10]));
					if ((state != 'intermediate') || (formName != 'ContactPayment'))
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
			var params = "status=" + "${form.status}";
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
			openWindow(event,url+'?'+params,"","resizable=1,menubar=1,toolbar=0,scrollbars=1,width=0,height=0");
		}
	</script>


	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.withdraw"/>
		<tiles:put name="commandHelpKey" value="button.withdraw.help"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="image" value="edit.gif"/>
		<tiles:put name="onclick" value="javascript:recall(event)"/>
	</tiles:insert>

	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.edit-payment"/>
		<tiles:put name="commandHelpKey" value="button.edit-payment.help"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="image" value="edit.gif"/>
		<tiles:put name="onclick" value="javascript:edit(event)"/>
	</tiles:insert>


	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.addCopy"/>
		<tiles:put name="commandHelpKey" value="button.addCopy"/>
		<tiles:put name="image" value="copy.gif"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="validationFunction">
			checkOneSelection('selectedIds', 'Выберите один платеж')
		</tiles:put>
	</tiles:insert>

	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.print"/>
		<tiles:put name="commandHelpKey" value="button.printPaymentList"/>
		<tiles:put name="image" value="print.gif"/>
		<tiles:put name="bundle"         value="paymentsBundle"/>
		<tiles:put name="onclick">
		  printPaymentList(event,${empty form.person ? 'null': form.person.id})
		</tiles:put>
	</tiles:insert>                         

</tiles:put>


<tiles:put name="filter" type="string">
	<c:set var="acountList" value="${form.accounts}"/>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.number"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="name" value="number"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.formName"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="data">
			<c:set var="formList" value="${forms}"/>
				<html:select property="filter(formName)" styleClass="filterSelect" style="width:200px">
					<html:option value="">Все</html:option>
					<html:optionsCollection name="form" property="forms"
					                        value="name" label="description"/>
				</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.account"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(account)"  styleClass="filterSelect"  style="width:168px">
				<html:option value="">Все</html:option>
				<c:forEach items="${form.accounts}" var="accountLink" varStatus="ls">
					<html:option value="${accountLink.account.number}">${accountLink.account.number}&nbsp;[${accountLink.account.type}]&nbsp;${accountLink.account.currency.name}</html:option>
				</c:forEach>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.state"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="data">
			<c:set var="status" value="${form.status}"/>
				<html:select property="filter(state)" styleClass="filterSelect" style="width:200px">
					<html:option value="">Все</html:option>
					 <c:if test="${status == 'all'}">
						 <html:option value="E,D">Отказан</html:option>
						 <html:option value="S">Исполнен</html:option>
						 <html:option value="V">Отозван</html:option>
					</c:if>
					<html:option value="I">Введен</html:option>
					<html:option value="Z,W">На исполнении</html:option>
				</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.date"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="name" value="Date"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.amount"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="name" value="Amount"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.admissionDate"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="name" value="AdmissionDate"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.receiverName"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="name" value="receiverName"/>
	</tiles:insert>

	<script type="text/javascript">
		document.imgPath = "${imagePath}/";

		function initTemplates()
		{
		}

		function clearMasks(event)
		{
		}
		initTemplates();
	</script>
</tiles:put>
<!-- данные -->
<tiles:put name="data" type="string">
<div id="listPaymentsDiv" class="workspaceRegion">
<table cellspacing="0" cellpadding="0" width="100%" class="userTab" id="titleAcc">
<tr class="titleTableOperations">
	<td width="3%">
		<input name="isSelectAll" style="border: medium none ;" onclick="switchSelection()" type="checkbox"/>
	</td>
	<td>Дата и время операции</td>
	<td>Номер</td>
	<td>Вид операции</td>
	<td>Счет списания</td>
	<td>Наименование получателя платежа</td>
	<td>Сумма платежа</td>
	<td>Сумма комиссии</td>
	<td>Сумма операции</td>
	<td>Валюта</td>
	<td>Статус</td>
	<td>Дата приема к исполнению</td>
</tr>
<c:set var="lineNumber" value="0"/>
<c:set var="totalDocAmount" value="0"/>
<c:set var="totalCommissionAmount" value="0"/>
<c:set var="totalAmount" value="0"/>

<c:set var="paymentList" value="${form.payments}"/>
<c:forEach items="${paymentList}" var="listElement" varStatus="ls">
	<c:set var="payment" value="${listElement[0]}"/>
	<c:set var="form" value="${listElement[1]}"/>
	<c:set var="lineNumber" value="${lineNumber+1}"/>
	<tr>
		<c:if test="${lineNumber<=listLimit}">
			<c:set var="totalDocAmount" value="${totalDocAmount + payment.chargeOffAmount.decimal}"/>
			<c:set var="totalCommissionAmount"
		           value="${totalCommissionAmount + payment.commission.decimal}"/>
			<c:set var="totalAmount"
			       value="${totalAmount + payment.chargeOffAmount.decimal + payment.commission.decimal}"/>
			<c:set var="docCurrency" value="${payment.chargeOffAmount.currency.code}"/>
			<td class="listItem" align="center">
				<html:multibox property="selectedIds" style="border:none">
					<bean:write name="payment" property="id"/>
				</html:multibox>
			</td>
			<td class="listItem">
				<c:if test="${! empty payment.operationDate}">
					<bean:write name="payment" property="operationDate.time" format="dd.MM.yyyy HH:mm:ss"/>
				</c:if>
				&nbsp;
			</td>
			<td class="listItem">
                <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/payments/default-action.do')}"/>
				&nbsp;
				<a href="${url}?id=${payment.id}&amp;state=${payment.state.category}&objectFormName=${payment.formName}&stateCode=${payment.state.code}">
					<c:out value="${payment.documentNumber}"/>
				</a>
				&nbsp;
			</td>
			<td class="listItem">
				<c:out value="${form.description}"/>&nbsp;
			</td>
			<td class="listItem">
				<c:out value="${payment.chargeOffAccount}"/>&nbsp;
			</td>
			<td class="listItem">
				<c:out value="${payment.receiverName}"/>
				&nbsp;
			</td>
			<td class="listItem" align="right">
				<c:set var="docAmount" value="${payment.chargeOffAmount.decimal}"/>
				<bean:write name="docAmount" format="0.00"/>
				&nbsp;
			</td>
			<td class="listItem" align="right">
				<c:set var="docCommissionAmount" value="0"/>
				<c:set var="docCommissionAmount" value="${docCommissionAmount + payment.commission.decimal}"/>				    
				<bean:write name="docCommissionAmount" format="0.00"/>
				&nbsp;
			</td>
			<td class="listItem" align="right">
				<c:set var="docTotalAmount" value="${payment.chargeOffAmount.decimal + payment.commission.decimal}"/>
				<bean:write name="docTotalAmount" format="0.00"/>
				&nbsp;
			</td>
			<td class="listItem" align="right">
				<bean:write name="docCurrency"/>
				&nbsp;
			</td>
			<td class="listItem">
					<a id="state${lineNumber}" onmouseover="showLayer('state${lineNumber}','stateDescription${lineNumber}','default',40);" onmouseout="hideLayer('stateDescription${lineNumber}');" style='text-decoration:underline'>
						<c:set var="code" value="${payment.state.code}"/>
						<c:choose>
							<c:when test="${code=='E'}">Отказан</c:when>
							<c:when test="${code=='D'}">Отказан</c:when>
							<c:when test="${code=='I'}">Введен</c:when>
							<c:when test="${code=='W'}">На исполнении</c:when>
							<c:when test="${code=='S'}">Исполнен</c:when>
							<c:when test="${code=='V'}">Отозван</c:when>
							<c:when test="${code=='Z'}">На исполнении</c:when>
						</c:choose>
					</a>
			</td>
			<td class="listItem">
				<c:if test="${! empty payment.admissionDate}">
					<bean:write name="payment" property="admissionDate" format="dd.MM.yyyy"/>
				</c:if>
				&nbsp;
			</td>
			<!--
			Два последних столбца не удалять!!! Нужны для определения возможности отзыва и редактирования
			-->
			<td style="display:none">
				<c:out value="${payment.formName}"/>
			</td>
			<td style="display:none">
				<c:out value="${payment.state.category}"/>
			</td>
			<c:if test="${not empty payment.state.description}">
				<div id='stateDescription${lineNumber}' onmouseover="showLayer('state${lineNumber}','stateDescription${lineNumber}','default',40);" onmouseout="hideLayer('stateDescription${lineNumber}');" class='layerFon' style='position:absolute; display:none; width:150px; height:80px;overflow:auto;'>
					${payment.state.description}
				</div>
			</c:if>
		</c:if>
	</tr>
</c:forEach>
<c:if test="${lineNumber>listLimit}">
	<tr>
		<td colspan="12" class="listItem">
			<b>
				В результате поиска найдено слишком много операций.
				На экран выведены первые
				<c:out value="${listLimit}"/>
				.
				Задайте более жесткие условия поиска.
			</b>
		</td>
	</tr>
</c:if>
</table>
<c:if test="${lineNumber==0}">
	<script type="text/javascript">hideTitle("titleAcc");</script>
	<center>
            <span class="messageTab">
                Нет операций.
            </span>
	</center>
</c:if>
</div>
	<script type="text/javascript">
		changeDivSize('listPaymentsDiv');
	</script>
</tiles:put>

</tiles:insert>
</html:form>
