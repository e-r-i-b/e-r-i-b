<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--Ответ, в котором содержится статус и если был превышен лимит получателей - доступный лимит.--%>
<c:set var="form" value="${FundRequestForm}"/>
<tiles:insert definition="iphone" flush="false">
    <tiles:put name="data">
        <c:if test="${not empty form.availableLimit}">
            <limit><c:out value="${form.availableLimit}"/></limit>
        </c:if>
    </tiles:put>
</tiles:insert>