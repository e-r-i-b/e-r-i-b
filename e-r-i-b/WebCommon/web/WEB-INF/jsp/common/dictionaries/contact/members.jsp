<%--
  User: Kosyakova
  Date: 16.11.2006
  Time: 11:17:30
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<c:set var="standalone" value="${empty param['action']}"/>

<html:form action="/private/dictionary/contact/members" onsubmit="return setAction();">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="dictionary">
<tiles:put name="pageTitle" type="string" value=" Справочник банков"/>

<tiles:put name="menu" type="string">

	<script type="text/javascript">
		function setAction(event)
		{
			preventDefault(event);
		<c:if test="${not standalone}">
			var frm = document.forms.item(0);
			frm.action = frm.action + "?action=getBankInfo";
		</c:if>
			return true;
		}
		function sendBankData(event)
		{
			var ids = document.getElementsByName("selectedId");
			preventDefault(event);
			for (i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					var r = ids.item(i).parentNode.parentNode;
					var a = new Array(3);
					a['code'] = trim(getElementTextContent(r.cells[1]));
					a['name'] = trim(getElementTextContent(r.cells[2]));
					a['city'] = trim(getElementTextContent(r.cells[3]));
					a['phone'] = trim(getElementTextContent(r.cells[4]));
                    a['comm'] = trim(getElementTextContent(r.cells[6]));
					a['req'] = trim(getElementTextContent(r.cells[7]));
					window.opener.setBankInfo(a);
					window.close();
					return;
				}
			}
			alert("Выберите банк.");
		}
	</script>

	<c:if test="${not standalone}">
		<tiles:insert definition="clientButton" flush='false'>
			<tiles:put name="commandTextKey" value="button.cancel"/>
			<tiles:put name="commandHelpKey" value="button.cancel"/>
			<tiles:put name="bundle" value="commonBundle"/>
			<tiles:put name="onclick" value="window.close();"/>
		</tiles:insert>
	</c:if>
</tiles:put>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.countryName"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="name" value="countryName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.city"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="name" value="city"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.title"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="name" value="name"/>
	</tiles:insert>
</tiles:put>
<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="AccountsListTable"/>
		<tiles:put name="image" value="iconMid_banksDictionary.gif"/>
		<tiles:put name="text" value="Банки"/>
		<tiles:put name="buttons">
			<c:if test="${not standalone}">
				<tiles:insert definition="clientButton" flush='false'>
					<tiles:put name="commandTextKey" value="button.choose"/>
					<tiles:put name="commandHelpKey" value="button.choose"/>
					<tiles:put name="image" value="iconSm_select.gif"/>
					<tiles:put name="bundle" value="commonBundle"/>
					<tiles:put name="onclick" value="sendBankData(event);"/>
				</tiles:insert>
			</c:if>
		</tiles:put>

		<tiles:put name="grid">
			<sl:collection id="country" property="data" model="list">
				<sl:collectionParam id="selectType" value="radio" condition="${not standalone}"/>
				<sl:collectionParam id="selectProperty" value="id" condition="${not standalone}"/>
				<sl:collectionParam id="selectName" value="selectedId" condition="${not standalone}"/>
				<sl:collectionParam id="onRowClick" value="selectRow(this);" condition="${not standalone}"/>
				<sl:collectionParam id="onRowDblClick" value="sendBankData();" condition="${not standalone}"/>

				<sl:collectionItem title="Код" property="code"/>
				<sl:collectionItem title="Наименование" property="name"/>
				<sl:collectionItem title="Адрес" property="address"/>
				<sl:collectionItem title="Телефон" property="phone"/>
                <sl:collectionItem title="Страна" property="countryId"/>
                <sl:collectionItem title="comment" property="comment"/>
                <sl:collectionItem title="regMask" property="regMask"/>
			</sl:collection>
		</tiles:put>

		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного банка,<br>соответствующего заданному фильтру!"/>
	</tiles:insert>
</tiles:put>

</tiles:insert>

</html:form>
