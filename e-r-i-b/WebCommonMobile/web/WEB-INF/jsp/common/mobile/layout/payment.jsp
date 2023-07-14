<%@ page contentType="text/xml;windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/tags/mobile" prefix="mobile" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tiles:importAttribute/>

<c:set var="transactionToken" value="${mobile:transactionToken()}"/>

<tiles:insert definition="iphone">
    <c:if test="${not empty status}">
        <tiles:put name="status" value="${status}"/>
    </c:if>
    <tiles:put name="data">
        <c:if test="${not empty transactionToken}">
            <transactionToken><c:out value="${transactionToken}"/></transactionToken>
        </c:if>
        ${data}
    </tiles:put>
    <tiles:put name="messagesBundle">${messagesBundle}</tiles:put>
</tiles:insert>