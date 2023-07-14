<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/blockingrules/list/message" onsubmit="return setAction();">
<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string">
	Причина блокировки
</tiles:put>

<tiles:put name="menu" type="string">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.close"/>
        <tiles:put name="commandHelpKey" value="button.close"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="image" value=""/>
        <tiles:put name="onclick" value="window.close();"/>
        <tiles:put name="viewType" value="blueBorder"/>
    </tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="head">
				<td class="listItem" align="center" valign="top">Причина блокировки</td>
        </tiles:put>
        <tiles:put name="data">
			<tr class="listLine1">
				<td class="listItem" align="center" valign="top"><c:out value="${form.selectedMessage}"/></td>
			</tr>
		</tiles:put>
    </tiles:insert>

</tiles:put>

</tiles:insert>
</html:form>