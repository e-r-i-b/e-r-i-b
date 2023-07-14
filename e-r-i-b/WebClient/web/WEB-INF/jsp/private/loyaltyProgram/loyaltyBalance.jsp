<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html:form action="/private/async/loyalty/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="loyaltyProgramLink" value="${form.link}" scope="request"/>
    {
    <c:choose>
        <c:when test="${loyaltyProgramLink == null || (loyaltyProgramLink.balance == null && loyaltyProgramLink.state != 'UNREGISTERED')}">
            "button" : "buttonFindBalance"
        </c:when>
        <c:when test="${loyaltyProgramLink.state == 'UNREGISTERED'}">
            "button" : "buttonRegist"
        </c:when>
        <c:otherwise>
            "button" : "buttonUpdate",
            "balance" : "${phiz:formatBigDecimal(loyaltyProgramLink.balance)}",
            <c:set var="updateDate" value="${phiz:formatDateDependsOnSysDate(loyaltyProgramLink.updateTime, true, false)}"/>
            "updateDate" : "${fn:toLowerCase(updateDate)}"
        </c:otherwise>
    </c:choose>
    <c:if test="${form.backError}">
        ,"error" : "<bean:message bundle="loyaltyBundle" key="regErrorMessage"/>"
    </c:if>
    }
</html:form>