<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/forms">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="dictionary">

<tiles:put name="pageTitle" type="string" value="Платежи и заявки"/>

<tiles:put name="menu" type="string">

	<c:if test="${not standalone}">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.cancel"/>
			<tiles:put name="commandHelpKey" value="button.cancel"/>
			<tiles:put name="bundle" value="notificationsBundle"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="onclick" value="window.close()"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</c:if>
</tiles:put>

<!--данные-->
<tiles:put name="data" type="string">

	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="forms"/>
        <tiles:put name="buttons">
             <c:if test="${not standalone}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.select"/>
                    <tiles:put name="commandHelpKey" value="button.select"/>
                    <tiles:put name="bundle" value="notificationsBundle"/>
                    <tiles:put name="onclick" value="sendData()"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="name"/>

                <sl:collectionItem title="Описание" property="description"/>
            </sl:collection>
		</tiles:put>
	</tiles:insert>

	<script type="text/javascript">
		function initData()
		{
			var ids = document.getElementsByName("selectedIds");
			var str = window.opener.getPaymentsAndClaimsInfo();
			var arr = str.split(";");

			for (var i = 0; i < ids.length; i++)
				for (var j = 0; j < arr.length; j++)
					if (ids.item(i).value == arr[j])
						ids.item(i).checked = true;
		}
		function sendData()
		{
			var ids = document.getElementsByName("selectedIds");
			var a = "";
			for (i = 0; i < ids.length; i++)
				if (ids.item(i).checked)
					a += ids[i].value + ";";
			if (a != "")
			{
				window.opener.setPaymentsAndClaimsInfo(a);
				window.close();
			}
			else
				alert("Выберите форму.");
		}
		initData();
	</script>

</tiles:put>

</tiles:insert>

</html:form>
