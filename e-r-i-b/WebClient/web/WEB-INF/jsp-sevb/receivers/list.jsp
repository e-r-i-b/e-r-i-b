<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/receivers/list" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="receiversList">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:put name="submenu" type="string" value="PaymentReceivers"/>
<tiles:put name="pageTitle" type="string" value="Справочник получателей"/>

<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false" operation="EditPaymentReceiverOperationClient">
		<tiles:put name="commandTextKey" value="button.add"/>
		<tiles:put name="commandHelpKey" value="button.add"/>
		<tiles:put name="bundle" value="contactBundle"/>
		<tiles:put name="image" value="add.gif"/>
		<tiles:put name="action" value="/private/receivers/edit.do?kind=P"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false" operation="EditPaymentReceiverOperationClient">
		<tiles:put name="commandTextKey" value="button.edit"/>
		<tiles:put name="commandHelpKey" value="button.edit.help"/>
		<tiles:put name="bundle" value="contactBundle"/>
		<tiles:put name="image" value="edit.gif"/>
		<tiles:put name="onclick" value="doEdit();"/>
	</tiles:insert>
	<tiles:insert definition="commandButton" flush="false" operation="RemovePaymentReceiverOperationClient">
		<tiles:put name="commandKey" value="button.remove"/>
		<tiles:put name="commandHelpKey" value="button.remove.help"/>
		<tiles:put name="bundle" value="contactBundle"/>
		<tiles:put name="image" value="delete.gif"/>
		<tiles:put name="confirmText"
		           value="Вы действительно хотите удалить выбранных представителей?"/>
		<tiles:put name="validationFunction">
			checkSelection('selectedIds', 'Выберите получателя')
		</tiles:put>
	</tiles:insert>

</tiles:put>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.name"/>
		<tiles:put name="bundle" value="dictionaryBundle"/>
		<tiles:put name="name" value="name"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.account"/>
		<tiles:put name="bundle" value="dictionaryBundle"/>
		<tiles:put name="name" value="account"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.bank"/>
		<tiles:put name="bundle" value="dictionaryBundle"/>
		<tiles:put name="name" value="bank"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<script type="text/javascript">
		var addUrl = "${phiz:calculateActionURL(pageContext,'/private/receivers/edit')}";
		function doEdit()
		{
			if (!checkOneSelection("selectedIds", "Выберите получателя!!"))
				return;
			var id = getRadioValue(document.getElementsByName("selectedIds"));
			window.location = addUrl + "?id=" + id;
		}
	</script>
	<c:choose>
		<c:when test="${not empty form.receivers}">
			<table cellspacing="0" cellpadding="0" width="100%" class="userTab" id="tableTitle">
				<tr style="height:20px" class="titleTable">
					<td width="20px">
						<input type="checkbox" name="isSelectAll" style="border:none"
						       onclick="switchSelection()"/>
					</td>
					<td>Номер</td>
					<td nowrap="true">Условное обозначение</td>
					<td nowrap="true">Наименование получателя</td>
					<td>ИНН</td>
					<td>Номер счета получателя</td>
					<td>Наименование банка получателя</td>
					<td>БИК банка получателя</td>
					<td>Корр. счет банка получателя</td>
					<td>Статус</td>
				</tr>
				<c:forEach var="receiver" items="${form.receivers}" varStatus="lineInfo">
					<tr class="listLine${lineInfo.count % 2}">
						<td class="listItem" align="center" width="20px">
							<html:multibox property="selectedIds" style="border:none">
								${receiver.id}
							</html:multibox>
						</td>
						<td class="listItem" align="center">${lineInfo.count}&nbsp;</td>
						<td class="listItem">${receiver.alias}&nbsp;</td>
						<td class="listItem">
							<phiz:link action="/private/receivers/edit"
							           operationClass="EditPaymentReceiverOperationClient"
									>
                                <phiz:param name="id" value="${receiver.id}"/>
								${receiver.name}
							</phiz:link>
							&nbsp;
						</td>
						<td class="listItem">${receiver.INN}&nbsp;</td>
						<td class="listItem">${receiver.account}&nbsp;</td>
						<td class="listItem">${receiver.bankName}&nbsp;</td>
						<td class="listItem">${receiver.bankCode}&nbsp;</td>
						<td class="listItem">${receiver.correspondentAccount}&nbsp;</td>
						<td class="listItem">
							<c:choose>
								<c:when test="${receiver.state=='INITAL'}">
									Введен
								</c:when>
								<c:when test="${receiver.state=='ACTIVE'}">
									Активен
								</c:when>
							</c:choose>
							&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<table width="100%">
				<tr>
					<td align="center" class="messageTab"><br>
						Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;получателя&nbsp;платежа,<br>
						соответствующего&nbsp;заданному&nbsp;фильтру!
					</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</tiles:put>
</tiles:insert>

</html:form>
