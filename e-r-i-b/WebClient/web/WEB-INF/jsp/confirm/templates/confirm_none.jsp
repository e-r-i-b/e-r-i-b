<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:if test="${not empty message}">
<fieldset>
<legend>Для подтверждения операции:</legend>
<table class="pmntConfirm" cellpadding="2" cellspacing="6">
	<div class="titleHelp" style="padding:4px;"><c:out value="${message}"/></div>
</table>
</fieldset>
</c:if>

