<%@ page import="com.rssl.phizic.web.client.payments.forms.ShowMetaPaymentListForm" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/payments" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>


<tiles:insert definition="paymentMain">
<tiles:put name="pageTitle" value="${form.title}"/>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:put name="submenu" value="${form.name}"/>
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
					var state = getElementTextContent(r.cells[7]);
					if (state != 'intermediate')
					{
						alert("Отзыв перевода со статусом " + trim(getElementTextContent(r.cells[6])) + " невозможен!");
						return;
					}
					<c:set var="u" value="/private/payments/add.do?form=${form.metadata.withdrawOptions.formName}"/>
					var url = "${phiz:calculateActionURL(pageContext,u)}";
					window.location = url + "&parentId=" + getFirstSelectedId('selectedIds');
					return;
				}
			}
		}


<%--
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
					var state = getElementTextContent(r.cells[8]);
					if (state == 'initial')
					{
						<c:set var="u" value="/private/payments/payment.do?"/>
						var url = "${phiz:calculateActionURL(pageContext,u)}";
						window.location = url + "id=" + getFirstSelectedId('selectedIds');
						return;
					}
					var formName = getElementTextContent(r.cells[7]);
					if ((state != 'intermediate') || (formName != 'ContactPayment'))
					{
						alert("Документ передан на обработку в банк и не может быть отредактирован!");
						return;
					}
					<c:set var="u" value="/private/payments/add.do?form=${form.metadata.editOptions.formName}"/>
					var url = "${phiz:calculateActionURL(pageContext,u)}";
					window.location = url + "&parentId=" + getFirstSelectedId('selectedIds');
					return;
				}
			}
		}
--%>
	</script>


	<c:if test="${!empty form.metadata.withdrawOptions.formName}">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.withdraw"/>
			<tiles:put name="commandHelpKey" value="button.withdraw.help"/>
			<tiles:put name="bundle" value="paymentsBundle"/>
			<tiles:put name="image" value="edit.gif"/>
			<tiles:put name="onclick" value="javascript:recall(event)"/>
		</tiles:insert>
	</c:if>

	<c:if test="${!empty form.metadata.editOptions.formName}">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.edit-payment"/>
			<tiles:put name="commandHelpKey" value="button.edit-payment.help"/>
			<tiles:put name="bundle" value="paymentsBundle"/>
			<tiles:put name="image" value="edit.gif"/>
			<tiles:put name="onclick" value="javascript:edit(event)"/>
		</tiles:insert>
	</c:if>


	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.addNew"/>
		<tiles:put name="commandHelpKey" value="button.addNew"/>
		<tiles:put name="image" value="add.gif"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="action" value="/private/payments/add.do?form=${form.name}"/>
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
		<tiles:put name="commandTextKey" value="button.goto.forms.list"/>
		<tiles:put name="commandHelpKey" value="button.goto.forms.list.help"/>
		<tiles:put name="bundle" value="paymentsBundle"/>
		<tiles:put name="image" value="back.gif"/>
		<tiles:put name="action" value="/private/payments.do"/>
	</tiles:insert>

</tiles:put>

<!-- фильтр -->
<tiles:put name="filter" type="string">
	<%= ((ShowMetaPaymentListForm) request.getAttribute("ShowMetaPaymentListForm")).getFilterHtml() %>
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
	<c:choose>
		<c:when test="${form.listHtml != null}">
			${form.listHtml}
		</c:when>
		<c:otherwise>
			<table id="messageTab" width="100%">
				<tr>
					<td class="messageTab" align="center">
						Исправьте параметры отбора и попробуйте снова
					</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</tiles:put>

</tiles:insert>
</html:form>
