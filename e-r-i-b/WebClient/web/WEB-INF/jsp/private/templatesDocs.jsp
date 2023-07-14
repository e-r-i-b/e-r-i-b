<%--
  User: Kosyakova
  Date: 07.12.2006
  Time: 18:02:18
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>

<html:form action="/private/templates/documents/list" onsubmit="return setAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="dictionary">
  <tiles:put name="mainmenu" value="ReceiversDictionary"/>
  <tiles:put name="submenu" type="string" value="PaymentReceiverList"/>
  <tiles:put name="pageTitle" type="string" value="Документы банка"/>
<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function setAction(event)
		{
			preventDefault(event);
		<c:if test="${!standalone}">
			var frm = document.forms.item(0);
			frm.action = frm.action + "?action=Start";
		</c:if>
			return true;
		}

	</script>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.close"/>
			<tiles:put name="commandHelpKey" value="button.close.help"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="bundle" value="dictionaryBundle"/>
			<tiles:put name="onclick" value="javascript:window.close()"/>
		</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="BanksDocumentsListTable"/>
		<tiles:put name="image" value="iconMid_banksDictionary.gif"/>
		<tiles:put name="text" value="Документы банка"/>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data">
                <sl:collectionItem title="Название документа" name="listElement" property="name">                              '
                    <sl:collectionItemParam
                            id="action"
                            value="/private/templates/documents/load.do?id=${listElement.id}" 
                            condition="${phiz:impliesService('RurPayment')}"/>
                </sl:collectionItem>
                <sl:collectionItem title="Описание документа" name="listElement" property="description"/>
            </sl:collection>
        </tiles:put>
	</tiles:insert>
</tiles:put>

</tiles:insert>

</html:form>