<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%-- добавляем пустой див (костыль для ie6)
если нет каких нибудь данных перед формой, IFrameRequest не видит форму --%>
<div>&nbsp;</div>
<c:set var="form" value="${LoginForm}"/>
<html:form action="${form.fields.payOrder?'/payOrderLogin':'/login'}">
    <c:set var="operationInfo" value="${form.operationInfo}"/>
    <c:choose>
        <c:when test="${form.fields.payOrder}">
            <input type="hidden" name="$$POSTRedirect" value="${operationInfo.redirect}">
        </c:when>
        <c:otherwise>
            <input type="hidden" name="$$redirect" value="${operationInfo.redirect}">
        </c:otherwise>
    </c:choose>
</html:form>
