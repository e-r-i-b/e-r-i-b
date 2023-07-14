<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/private/accountDict" onsubmit="return setAction();">

<tiles:insert definition="dictionary">
<tiles:put name="pageTitle" type="string" value=" Справочник счетов пользователя"/>

<tiles:put name="menu" type="string">

	<c:if test="${not standalone}">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.cancel"/>
			<tiles:put name="commandHelpKey" value="button.cancel"/>
			<tiles:put name="bundle" value="notificationsBundle"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="onclick" value="window.close()"/>
		</tiles:insert>
	</c:if>
</tiles:put>

<tiles:put name="data" type="string">
	<script type="text/javascript">
		function setAction(event)
		{
			preventDefault(event);
			return true;
		}
		function sendAccountsData(event)
		{
			var ids = document.getElementsByName("selectedIds");
			preventDefault(event);
			var a = "";
			for (var i = 0; i < ids.length; i++)
			{
				if (ids.item(i).checked)
				{
					a += ids[i].value + ";";
				}
			}
            if (a == "")
            {
                alert("Выберите счет.");
                return;
            }
     		window.opener.setAccountInfo(a);
			window.close();
		}
	</script>
		<tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value="AccountsDict"/>
			<tiles:put name="image" value="iconMid_dictionary.gif"/>
			<tiles:put name="text" value="Счета пользователя"/>
			<tiles:put name="buttons">
				<c:if test="${not standalone}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.select"/>
					<tiles:put name="commandHelpKey" value="button.select"/>
					<tiles:put name="bundle" value="notificationsBundle"/>
					<tiles:put name="image" value="iconSm_select.gif"/>
					<tiles:put name="onclick" value="sendAccountsData(event)"/>
				</tiles:insert>
				</c:if>
			</tiles:put>
            <tiles:put name="grid">
                <sl:collection id="listElement" model="list" property="data">
                    <sl:collectionParam id="selectType" value="checkbox"     condition="${not standalone}"/>
                    <sl:collectionParam id="selectName" value="selectedType" condition="${not standalone}"/>
                    <sl:collectionParam id="selectProperty" value="id" condition="${not standalone}"/>

                    <sl:collectionItem title="Счет" property="value.number"/>
                </sl:collection>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty ShowAccountsListForm.data}"/>
            <tiles:put name="emptyMessage" value="Не найдено ни одного счета"/>
		</tiles:insert>
</tiles:put>

</tiles:insert>

</html:form>
