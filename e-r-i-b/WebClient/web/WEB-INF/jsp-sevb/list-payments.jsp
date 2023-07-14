<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 28.04.2008
  Time: 14:39:32
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


<tiles:insert definition="paymentCurrent">
<tiles:put name="pageTitle" value="Журнал операций"/>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<!-- Меню -->

<tiles:put name="menu" type="string">
	<script type="text/javascript">
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
					var state = trim(getElementTextContent(r.cells[13]));
					if (state == 'initial')
					{
					<c:set var="u" value="/private/payments/payment.do?"/>
						var url = "${phiz:calculateActionURL(pageContext,u)}";
						window.location = url + "id=" + getFirstSelectedId('selectedIds');
						return;
					}
					var formName = trim(getElementTextContent(r.cells[12]));
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
			openWindow(event,url+'?status=${form.status}&'+params,"","resizable=1,menubar=1,toolbar=0,scrollbars=1,width=0,height=0");
		}
	</script>


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
		<tiles:put name="image" value="iconSm_copy.gif"/>
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
	<c:set var="colCount" value="2" scope="request"/>
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
				<html:select property="filter(formName)" styleClass="filterSelect" style="width:270px">
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
			<html:select property="filter(account)" styleClass="filterSelect" style="width:200px">
				<html:option value="">Все</html:option>
				<c:forEach items="${form.accounts}" var="accountLink" varStatus="ls">
					<html:option value="${accountLink.account.number}">${accountLink.account.number}
						&nbsp;[${accountLink.account.type}
						]&nbsp;${accountLink.account.currency.name}</html:option>
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
					<html:option value="I">Введен</html:option>
					<html:option value="W">Обрабатывается</html:option>
					<c:if test="${status == 'all'}">
						<html:option value="S">Исполнен</html:option>
					</c:if>
					<html:option value="E">Отказан</html:option>
					<html:option value="F">Приостановлен</html:option>
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
	<div id="listPaymentsDiv">
	    <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="titleAcc"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="text" value=""/>
		<tiles:put name="head">
			<td width="3%">
				<input name="isSelectAll" style="border: medium none ;" onclick="switchSelection()" type="checkbox"/>
			</td>
			<td width="7%">Дата и время операции</td>
			<td width="7%">Дата приема к исполнению</td>
			<td width="5%">Номер</td>
			<td width="17%">Вид операции</td>
			<td width="13%">Счет списания</td>
			<td width="14%">Наименование получателя платежа</td>
			<td width="7%">Сумма платежа</td>
			<td width="7%">Сумма комиссии</td>
			<td width="7%">Сумма операции</td>
			<td width="6%">Валюта</td>
			<td width="7%">Статус</td>
		</tiles:put>
		<tiles:put name="data">
			<c:set var="lineNumber" value="0"/>
			<c:set var="totalDocAmount" value="0"/>
			<c:set var="totalCommissionAmount" value="0"/>
			<c:set var="totalAmount" value="0"/>

			<c:set var="paymentList" value="${form.businessDocuments}"/>
			<c:set var="listLimit" value="${form.listLimit}"/>
			<c:forEach items="${paymentList}" var="listElement" varStatus="ls">
				<c:set var="businessDocument" value="${listElement[0]}"/>
				<c:set var="form" value="${listElement[1]}"/>
				<c:set var="lineNumber" value="${lineNumber+1}"/>
				<tr>
					<c:if test="${lineNumber<=listLimit}">
						<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
							<c:set var="totalDocAmount" value="${totalDocAmount+businessDocument.chargeOffAmount.decimal}"/>
							<c:set var="totalCommissionAmount"
								   value="${totalCommissionAmount+businessDocument.commission.decimal}"/>
							<c:set var="totalAmount"
								   value="${totalAmount+businessDocument.chargeOffAmount.decimal+businessDocument.commission.decimal}"/>
						</c:if>
						<td class="listItem" align="center">
							<html:multibox property="selectedIds" style="border:none">
								<bean:write name="businessDocument" property="id"/>
							</html:multibox>
						</td>
						<td class="listItem">
							<c:if test="${! empty businessDocument.operationDate}">
								<bean:write name="businessDocument" property="operationDate.time" format="dd.MM.yyyy HH:mm:ss"/>
							</c:if>
							&nbsp;
						</td>
						<td class="listItem">
							<c:if test="${! empty businessDocument.admissionDate}">
								<bean:write name="businessDocument" property="admissionDate.time" format="dd.MM.yyyy"/>
							</c:if>
							&nbsp;
						</td>
						<td class="listItem">
                            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/payments/default-action.do')}"/>
							&nbsp;
							<a href="${url}?id=${businessDocument.id}&amp;state=${businessDocument.state.category}&objectFormName=${businessDocument.formName}&stateCode=${businessDocument.state.code}">
								<c:out value="${businessDocument.documentNumber}"/>
							</a>
							&nbsp;
						</td>
						<td class="listItem">
							<c:out value="${form.description}"/>
							&nbsp;
						</td>
						<td class="listItem">
							<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
								<c:out value="${businessDocument.chargeOffAccount}"/>
							</c:if>
							&nbsp;
						</td>
						<td class="listItem">
							<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
								<c:out value="${businessDocument.receiverName}"/>
							</c:if>
							&nbsp;
						</td>
						<td class="listItem" align="right">
							<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
								<c:if test="${businessDocument.chargeOffAmount != null}">
									<bean:write name="businessDocument" property="chargeOffAmount.decimal" format="0.00"/>
								</c:if>
							</c:if>
							&nbsp;
						</td>
						<td class="listItem" align="right">
							<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
								<c:set var="docCommissionAmount" value="0"/>
								<c:set var="docCommissionAmount" value="${docCommissionAmount + businessDocument.commission.decimal}"/>
								<bean:write name="docCommissionAmount" format="0.00"/>
							</c:if>
							&nbsp;
						</td>
						<td class="listItem" align="right">
							<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
								<c:set var="docTotalAmount" value="${businessDocument.chargeOffAmount.decimal+businessDocument.commission.decimal}"/>
								<bean:write name="docTotalAmount" format="0.00"/>
							</c:if>
							&nbsp;
						</td>
						<td class="listItem" align="right">
							<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
								<c:if test="${businessDocument.chargeOffAmount != null}">
									<bean:write name="businessDocument" property="chargeOffAmount.currency.code"/>
								</c:if>
							</c:if>
							&nbsp;
						</td>
						<td class="listItem">
							<c:set var="code" value="${businessDocument.state.code}"/>
							<c:choose>
								<c:when test="${code=='I'}">Введен</c:when>
								<c:when test="${code=='W'}">Обрабатывается</c:when>
								<c:when test="${code=='S'}">Исполнен</c:when>
								<c:when test="${code=='E'}">Отказан</c:when>
								<c:when test="${code=='D'}">Отказан</c:when>
								<c:when test="${code=='V'}">Отозван</c:when>
								<c:when test="${code=='F'}">Приостановлен</c:when>
							</c:choose>
						</td>
						<!--
						Два последних столбца не удалять!!! Нужны для определения возможности отзыва и редактирования
						-->
						<td style="display:none">
							<c:out value="${businessDocument.formName}"/>
						</td>
						<td style="display:none">
							<c:out value="${businessDocument.state.category}"/>
						</td>
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
			<td class="listItem" align="center">
				<html:multibox property="selectedIds" style="border:none">
					<bean:write name="businessDocument" property="id"/>
				</html:multibox>
			</td>
			<td class="listItem">
				<c:if test="${! empty businessDocument.operationDate}">
					<bean:write name="businessDocument" property="operationDate.time" format="dd.MM.yyyy HH:mm:ss"/>
				</c:if>
				&nbsp;
			</td>
			<td class="listItem">
				<c:if test="${! empty businessDocument.admissionDate}">
					<bean:write name="businessDocument" property="admissionDate.time" format="dd.MM.yyyy"/>
				</c:if>
				&nbsp;
			</td>
			<td class="listItem">
                <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/payments/default-action.do')}"/>
				&nbsp;
				<a href="${url}?id=${businessDocument.id}&amp;state=${businessDocument.state.category}">
					<c:out value="${businessDocument.documentNumber}"/>
				</a>
				&nbsp;
			</td>
			<td class="listItem">
				<c:out value="${form.description}"/>
				&nbsp;
			</td>
			<td class="listItem">
				<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					<c:out value="${businessDocument.chargeOffAccount}"/>
				</c:if>
				&nbsp;
			</td>
			<td class="listItem">
				<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
					<c:out value="${businessDocument.receiverName}"/>
				</c:if>
				&nbsp;
			</td>
			<td class="listItem" align="right">
				<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					<c:if test="${businessDocument.chargeOffAmount != null}">
						<bean:write name="businessDocument" property="chargeOffAmount.decimal" format="0.00"/>
					</c:if>
				</c:if>
				&nbsp;
			</td>
			<td class="listItem" align="right">
				<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					<c:set var="docCommissionAmount" value="0"/>
					<c:set var="docCommissionAmount" value="${docCommissionAmount + businessDocument.commission.decimal}"/>
					<bean:write name="docCommissionAmount" format="0.00"/>
				</c:if>
				&nbsp;
			</td>
			<td class="listItem" align="right">
				<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					<c:set var="docTotalAmount" value="${businessDocument.chargeOffAmount.decimal+businessDocument.commission.decimal}"/>
					<bean:write name="docTotalAmount" format="0.00"/>
				</c:if>
				&nbsp;
			</td>
			<td class="listItem" align="right">
				<c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					<c:if test="${businessDocument.chargeOffAmount != null}">
						<bean:write name="businessDocument" property="chargeOffAmount.currency.code"/>
					</c:if>
				</c:if>
				&nbsp;
			</td>
			<td class="listItem">
				<c:set var="code" value="${businessDocument.state.code}"/>
				<c:choose>
					<c:when test="${code=='I'}">Введен</c:when>
					<c:when test="${code=='W'}">Обрабатывается</c:when>
                    <c:when test="${code=='G'}">Обрабатывается</c:when>
					<c:when test="${code=='S'}">Исполнен</c:when>
					<c:when test="${code=='E'}">Отказан</c:when>
					<c:when test="${code=='D'}">Отказан</c:when>
					<c:when test="${code=='V'}">Отозван</c:when>
					<c:when test="${code=='F'}">Приостановлен</c:when>
				</c:choose>
			</td>
			<!--
			Два последних столбца не удалять!!! Нужны для определения возможности отзыва и редактирования
			-->
			<td style="display:none">
				<c:out value="${businessDocument.formName}"/>
			</td>
			<td style="display:none">
				<c:out value="${businessDocument.state.category}"/>
			</td>
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
</div>
<c:if test="${lineNumber==0}">
	<script type="text/javascript">hideTitle("titleAcc");</script>
	<center>
            <span class="messageTab">
                Нет операций.
            </span>
	</center>
</c:if>

		</tiles:put>
		<tiles:put name="isEmpty" value="${empty ShowCommonPaymentListForm.businessDocuments}"/>
		<tiles:put name="emptyMessage" value="Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;платежа,<br>
                 соответствующего&nbsp;заданному&nbsp;фильтру!"/>
	</tiles:insert>
	</div>
	<script type="text/javascript">
		var widthClient = getClientWidth();
		if (navigator.appName=='Microsoft Internet Explorer')
			document.getElementById('listPaymentsDiv').style.width = widthClient - leftMenuSize - 20;
	</script>
</tiles:put>

</tiles:insert>
</html:form>
