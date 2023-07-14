<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/pfr/pfrHistoryFull">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:if test="${form.printAbstract && not form.accountHasPFRRecords}">
        emptyListPFRRecords=true
    </c:if>
</html:form>