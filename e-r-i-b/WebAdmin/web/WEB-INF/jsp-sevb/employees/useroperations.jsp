<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/employees/access" onsubmit="return setEmptyAction(event);">
	<c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="employeesEdit">
	<tiles:put name="submenu" type="string" value="Access"/>
   <!-- заголовок страницы-->
   <tiles:put name="pageTitle" type="string"><bean:message key="edit.operations.title" bundle="employeesBundle"/></tiles:put>

   <!-- меню -->
    <tiles:put name="menu" type="string">
        <tiles:insert definition="commandButton" flush="false" operation="AssignEmployeeAccessOperation">
            <tiles:put name="commandKey" value="button.save"/>
            <tiles:put name="commandHelpKey" value="button.saveOperation.help"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="image" value="save.gif"/>
            <tiles:put name="isDefault" value="true"/>
            <tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="buttonGrey"/>
        </tiles:insert>

        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.close"/>
            <tiles:put name="commandHelpKey" value="button.closeResources.help"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="image" value="/back.gif"/>
            <tiles:put name="action" value="/employees/list.do"/>
            <tiles:put name="viewType" value="buttonGrey"/>
        </tiles:insert>

   </tiles:put>

 <tiles:put name="data" type="string">
	<table cellpadding="0" cellspacing="0" class="MaxSize">
	<tr>
	<td height="100%">
	<div class="MaxSize">

		<c:set var="isShowSaves" value="true"/>
		<%@ include file="/WEB-INF/jsp-sevb/persons/editAccessScript.jsp"%>
		<c:set var="subform" value="${form.employeeAccess}"/>
		<c:set var="policy" value="${subform.policy}"/>
		<%@ include file="/WEB-INF/jsp-sevb/persons/editAccess.jsp"%>
		
	</div>
	</td>
	</tr>
	</table>
</tiles:put>
</tiles:insert>
</html:form>
