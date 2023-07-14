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


<tiles:insert definition="paymentCurrent">
<c:set var="metadata" value="${form.metadata}"/>

<!-- заголовок -->
<tiles:put name="pageTitle" type="string">
	Платежи: <c:out value="${metadata.form.description}"/>. История операций
</tiles:put>

<tiles:put name="submenu" value="${form.name}"/>
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
			for (i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					var r = ids.item(i).parentNode.parentNode;
					var state = getElementTextContent(r.cells[0]);
					if (state == 'INITIAL')
					{
						<c:set var="u" value="/private/payments/payment.do?"/>
						var url = "${phiz:calculateActionURL(pageContext,u)}";
						window.location = url + "id=" + getFirstSelectedId('selectedIds');
						return;
					}
					var formName = getElementTextContent(r.cells[2]);
					if ((state != 'DISPATCHED' && state != 'STATEMENT_READY') || (trim(formName) != 'ContactPayment'))
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

        function addCopy()
        {
             var addUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/payment')}";

            if (!checkOneSelection("selectedIds", 'Выберите одну новость'))
                return;
            var paymentId = getRadioValue(document.getElementsByName("selectedIds"));
            window.location = addUrl + "?copying=true&id=" + paymentId;
        }
	</script>

</tiles:put>


<tiles:put name="filter" type="string">
	<%= ((ShowMetaPaymentListForm) request.getAttribute("ShowMetaPaymentListForm")).getFilterHtml() %>
	<script type="text/javascript">

		function hideTables()
		{
			if (document.getElementById("emptyList")!=null && document.getElementById("emptyList").value)
			{
				hiddenTab(0);
			}
			else
			{
			   hiddenTab(1);
			}
		}
		initTemplates();
	</script>
</tiles:put>
<!-- данные -->
<tiles:put name="data" type="string">
		<tiles:insert definition="tableTemplate" flush="false">
			<c:choose>
				<c:when test="${form.listHtml != ''}">
					    <tiles:put name="buttons">
						    <c:if test="${not empty form.metadata.withdrawOptions.formName}">
								<tiles:insert definition="commandButton" service="RecallPayment" flush="false">
									<tiles:put name="commandKey" value="button.withdraw"/>
									<tiles:put name="commandHelpKey" value="button.withdraw.help"/>
									<tiles:put name="bundle" value="paymentsBundle"/>
									<tiles:put name="image" value="iconSm_withdraw.gif"/>
									<tiles:put name="validationFunction">
										checkOneSelection('selectedIds', 'Выберите один платеж')
									</tiles:put>
								</tiles:insert>
							</c:if>
							<c:if test="${!empty form.metadata.editOptions.formName}">
								<tiles:insert definition="clientButton" flush="false">
									<tiles:put name="commandTextKey" value="button.edit"/>
									<tiles:put name="commandHelpKey" value="button.edit.help"/>
									<tiles:put name="image" value="iconSm_edit.gif"/>
									<tiles:put name="bundle" value="paymentsBundle"/>
									<tiles:put name="onclick" value="javascript:edit(event)"/>
								</tiles:insert>
							</c:if>

								<tiles:insert definition="clientButton" flush="false">
									<tiles:put name="commandTextKey" value="button.addCopy"/>
									<tiles:put name="commandHelpKey" value="button.addCopy"/>
									<tiles:put name="bundle"  value="paymentsBundle"/>
									<tiles:put name="image"   value="iconSm_copy.gif"/>
									<tiles:put name="onclick" value="addCopy();"/>
								</tiles:insert>
								<tiles:insert definition="commandButton" flush="false">
									<tiles:put name="commandKey" value="button.remove"/>
									<tiles:put name="commandHelpKey" value="button.remove.document.help"/>
									<tiles:put name="bundle" value="paymentsBundle"/>
									<tiles:put name="image" value="iconSm_delete.gif"/>
									<tiles:put name="validationFunction">
                                        function()
                                        {
                                            checkIfOneItem("selectedIds");
										    return checkSelection('selectedIds', 'Выберите платежи');
                                        }
									</tiles:put>
								</tiles:insert>
					    </tiles:put>
						<tiles:put name="id" value="${form.metadata.name}"/>
						<tiles:put name="image" value="iconMid_operationHistory.gif"/>
						<tiles:put name="text" value="История операций. ${metadata.form.description}"/>
						<tiles:put name="data">
							${form.listHtml}
						</tiles:put>
				</c:when>
				<c:otherwise>					
					<tiles:put name="isEmpty" value="true"/>
				</c:otherwise>
			</c:choose>
			</tiles:insert>
		</tiles:put>
</tiles:insert>
</html:form>
