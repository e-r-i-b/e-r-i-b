<%@ page import="javax.servlet.ServletRequest"%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/edit" onsubmit="return setEmptyAction(event);">

<c:set var="form" value="${EditPersonForm}"/>

<tiles:insert definition="personEdit">
	<tiles:put name="submenu" type="string" value="Edit"/>
	<tiles:put name="pageTitle" type="string">
		<bean:message key="edit.user.title" bundle="personsBundle"/>
	</tiles:put>

<tiles:put name="menu" type="string">
    <c:set var="isNew"      value="${empty form.activePerson.id}"/>
    <c:set var="isTemplate" value="${form.activePerson.status == 'T'}"/>
    <c:set var="isDeleted"  value="${form.activePerson.status == 'D'}"/>
	<c:set var="isShowSaves" value="true"/>

	<c:if test="${isTemplate}">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey"     value="button.partly.save.person"/>
			<tiles:put name="commandHelpKey" value="button.partly.save.person.help"/>
			<tiles:put name="bundle"  value="personsBundle"/>
			<tiles:put name="image"   value=""/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
    </c:if>

    <c:if test="${not isNew and isTemplate}">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey"     value="button.activate.person"/>
			<tiles:put name="commandHelpKey" value="button.activate.person.help"/>
			<tiles:put name="bundle"  value="personsBundle"/>
			<tiles:put name="image"   value=""/>
            <tiles:put name="validationFunction" >
                isService();
            </tiles:put >
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>

    </c:if>

    <c:if test="${not (isNew or isTemplate or isDeleted)}">
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey"     value="button.partly.save.person"/>
			<tiles:put name="commandHelpKey" value="button.partly.save.person.help"/>
			<tiles:put name="bundle"  value="personsBundle"/>
			<tiles:put name="image"   value=""/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
		<%--<tiles:insert definition="commandButton" flush="false" operation="RegisterPersonChangesOperation">
			<tiles:put name="commandKey"     value="button.register.changes.person"/>
			<tiles:put name="commandHelpKey" value="button.register.changes.person.help"/>
			<tiles:put name="bundle"  value="personsBundle"/>
			<tiles:put name="image"   value="save.gif"/>
			<tiles:put name="validationFunction">
				confirmRegistration();
			</tiles:put>
		</tiles:insert>--%>

		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey"     value="button.remove"/>
			<tiles:put name="commandHelpKey" value="button.remove"/>
			<tiles:put name="bundle"  value="personsBundle"/>
			<tiles:put name="image"   value=""/>
			<tiles:put name="confirmText"    value="Вы действительно хотите удалить клиента?"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</c:if>
	<tiles:insert definition="returnButton" flush="false">
		<tiles:put name="commandTextKey" value="button.back"/>
		<tiles:put name="commandHelpKey" value="button.back"/>
		<tiles:put name="image"          value=""/>
		<tiles:put name="bundle"         value="personsBundle"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>	
</tiles:put>

<tiles:put name="data" type="string">

	<table cellpadding="0" cellspacing="0" class="MaxSize">
	<tr>
	<td height="100%">
	<div class="MaxSize">

	<%@ include file="editFields.jsp" %>

	<script type="text/javascript">
		function confirmRegistration()
		{
			if (isDataChanged())
			{
				window.alert("Для  регистрации изменений необходимо сначала сохранить анкету клиента");
				return false;
			}
			return true;
		}

		function printClientInfo(event, personId, operation)
		{
			if (personId != null && personId != '' && !isDataChanged())
			{
				openWindow(event, 'print.do?person=' + personId);
			}
			else
			{
                window.alert("Перед печатью анкеты клиента необходимо ее сохранить");
			}
		}
        function printAdditionalContract (event, personId)
        {
            if (personId != null && personId != '' && !isDataChanged())
			{
               openWindow(event,'printContract9.do?person=' + personId,'5',null);
               openWindow(event,'printContract8_pr5.do?person=' + personId,'2',null);
              }
			else
			{
                window.alert("Перед печатью договора необходимо сохранить анкету клиента");
			}
	    }
	</script>
	</div>
	  </td>
	</tr>
	</table>
</tiles:put>
</tiles:insert>
</html:form>

