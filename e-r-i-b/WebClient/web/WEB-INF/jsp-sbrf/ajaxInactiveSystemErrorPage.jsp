<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>
    <div id="ajaxInactiveExternalSystemResponceError">
        <jsp:useBean id="inactiveExternalSystemMessages" class="java.util.ArrayList" />
        <phiz:messages id="inactiveES" bundle="${bundle}" field="field" message="inactiveExternalSystem">
            <c:set var="prepareMessage" value="${phiz:processBBCode(inactiveES)}" scope="request"/>
            <c:if test="${!phiz:contains(inactiveExternalSystemMessages, prepareMessage)}">
                <% inactiveExternalSystemMessages.add(request.getAttribute("prepareMessage")); %>
                <div class = "itemDiv">${prepareMessage}</div>
            </c:if>
        </phiz:messages>
    </div>
</div>
