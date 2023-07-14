<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/finances/categories">
    <div>
        <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
            <c:set var="errors">${errors}${error}</c:set>
        </phiz:messages>
        <c:if test="${not empty errors}">
            <div id="messages">
                <c:out value="${errors}"/>
            </div>
        </c:if>

        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <span id="removedOperationState">${form.operationState}</span>

        <c:set var="categoryId" value="${form.id}"/>
        <c:if test="${not empty categoryId}">
            <span id="removedCategoryId">${categoryId}</span>
        </c:if>
    </div>
</html:form>