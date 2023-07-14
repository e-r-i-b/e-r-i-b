<%--
  User: IIvanova
  Date: 19.04.2006
  Time: 15:59:55
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% int i = 0; %>
<bean:define id="includes" name="ShowTemplateListForm" property="templates(${param.formName})"/>
<logic:iterate id="template" name="includes">
	<% i++; %>
	<html:multibox property="selectedIds" style="border:none">
		<c:out value="${template.id}"/>
	</html:multibox>
	&nbsp;
	<c:if test="${template.owner == null}">
         <span class="BankTemplate">
            <c:out value="${template.templateName}"/>
         </span>
	</c:if>
	<c:if test="${template.owner != null}">
		<c:out value="${template.templateName}"/>
	</c:if>
	<br/>
</logic:iterate>
<% if (i == 0)
{ %>
<br/>нет шаблонов
<%}%>

