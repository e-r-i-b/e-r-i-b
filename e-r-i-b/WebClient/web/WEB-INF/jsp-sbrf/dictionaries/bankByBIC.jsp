<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/dictionary/banks/national">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bank" value="${form.data[0]}"/>
    <c:if test="${bank ne null}">
        {"name": "${fn:replace(bank.name, "\"", "\\\"")}",
        "account": "${bank.account}",
        "our": "${bank.our}"}
    </c:if>
</html:form>