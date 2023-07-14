<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<c:set var="standalone" value="${empty param['action']}"/>

<html:form action="/private/dictionary/offices" onsubmit="return setAction();">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="dictionary">
	<tiles:put name="pageTitle" type="string" value=" Справочник банков"/>

	<tiles:put name="menu" type="string">
		<c:if test="${not standalone}">
			<script type="text/javascript">
				function setAction ( event )
				{
					preventDefault(event);
				<c:if test="${!standalone}">
					var frm = document.forms.item(0);
					frm.action = frm.action+"?action=getOfficesInfo";
				</c:if>
					return true;
				}

				function sendOfficeData ( event )
				{
					var ids = document.getElementsByName("selectedId");
					preventDefault(event);
					for (i = 0; i<ids.length; i++)
					{
						if (ids.item(i).checked)
						{
							var r = ids.item(i).parentNode.parentNode;
							var a = new Array(3);
							a['officeId'] = trim(ids.item(i).value);
							a['name'] = trim(getElementTextContent(r.cells[1]));
							a['location'] = trim(getElementTextContent(r.cells[2]));
							a['phone'] = trim(getElementTextContent(r.cells[3]));
							a['postAddress'] = trim(getElementTextContent(r.cells[2]));

							window.opener.setOfficeInfo(a);
							window.close();
							return;
						}
					}
					alert("Выберите офис.");
				}
			</script>

			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle" value="dictionariesBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="onclick" value="window.close();"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</c:if>
	</tiles:put>

	<tiles:put name="filter" type="string">
		<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.receiver.bank.name"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="name"/>
		</tiles:insert>
	</tiles:put>

	<tiles:put name="data" type="string">
		<tiles:insert definition="tableTemplate" flush="false">
<%--TODO
		   <tiles:put name="id" value="BanksListTable"/>
		   <tiles:put name="image" value="iconMid_banksDictionary.gif"/>
--%>
		   <tiles:put name="text" value="Офисы"/>
		   <tiles:put name="buttons">
			  <c:if test="${not standalone}">
				 <tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.choose"/>
					<tiles:put name="commandHelpKey" value="button.choose"/>
					<tiles:put name="bundle" value="dictionaryBundle"/>
					<tiles:put name="onclick" value="javascript:sendOfficeData(event)"/>
				 </tiles:insert>
			  </c:if>
		   </tiles:put>

		<tiles:put name="grid">
			<sl:collection id="country" property="data" model="list">
				<sl:collectionParam id="selectType" value="radio" condition="${not standalone}"/>
				<sl:collectionParam id="selectProperty" value="synchKey" condition="${not standalone}"/>
				<sl:collectionParam id="selectName" value="selectedId" condition="${not standalone}"/>
				<sl:collectionParam id="onRowClick" value="selectRow(this);" condition="${not standalone}"/>
				<sl:collectionParam id="onRowDblClick" value="sendOfficeData();" condition="${not standalone}"/>

				<sl:collectionItem title="Наименование" property="name"/>
				<sl:collectionItem title="Адрес" property="address"/>
				<sl:collectionItem title="Телефон" property="telephone"/>
			</sl:collection>
		</tiles:put>

		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного офиса, соответсвующей заданному фильтру!"/>
	</tiles:insert>
</tiles:put>

</tiles:insert>

</html:form>
