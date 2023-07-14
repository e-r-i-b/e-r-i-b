<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/accountCardsDict" onsubmit="return setAction();">

<tiles:insert definition="dictionary">
<tiles:put name="pageTitle" type="string" value=" Справочник счетов и карт пользователя"/>

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
		function sendAccountCardData(event)
		{
			var accountIds = document.getElementsByName("selectedAccountIds");
			var cardIds = document.getElementsByName("selectedCardIds");
			preventDefault(event);
			var result = '';
			for (i = 0; i < accountIds.length; i++)
			{
				if (accountIds.item(i).checked)
				{
					var number = accountIds.item(i).value;
					result += 'a'+number +  ';';
				}
			}
			result +=':';
			for (i = 0; i < cardIds.length; i++)
			{
				if (cardIds.item(i).checked)
				{
					var id = cardIds.item(i).value;
					result += 'c'+id+';';
				}
			}
			if(result == ":")
				result="";
			window.opener.setAccountCardsInfo(result);
			window.close();
			return;
		}
	</script>
	<c:set var="form" value="${ShowAccountsCardsListForm}"/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="AccountAndCards"/>
		<tiles:put name="image" value="iconMid_usersDictionary.gif"/>
		<tiles:put name="text" value="Счета и карты пользователя"/>
		<tiles:put name="buttons">
			<c:if test="${not standalone}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.select"/>
					<tiles:put name="commandHelpKey" value="button.select"/>
					<tiles:put name="bundle" value="notificationsBundle"/>
					<tiles:put name="image" value="iconSm_select.gif"/>
					<tiles:put name="onclick" value="sendAccountCardData(event)"/>
				</tiles:insert>
			</c:if>
		</tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="accountLinks">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedAccountIds"/>
                <sl:collectionParam id="selectProperty" value="number"/>

                <sl:collectionItem width="200px" title="Номер счета" property="number"/>
            </sl:collection>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="cardLinks">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedCardIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <c:set var="num" value="${listElement.number}"/>
				<c:set var="len" value="${fn:length(num)}"/>

                <sl:collectionItem width="200px" title="Номер карты">
                    <c:if test="${not empty listElement}">
                        <c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
        <tiles:put name="isEmpty" value="${empty form.accountLinks && empty form.cardLinks}"/>
        <tiles:put name="emptyMessage" value="Не найдено ни одного счета или карты."/>
	</tiles:insert>
</tiles:put>

</tiles:insert>

</html:form>
