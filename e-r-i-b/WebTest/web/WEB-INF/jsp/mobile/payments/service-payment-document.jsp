<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty form.response.document.rurPayJurSBDocument}">
    <c:set var="document" value="${form.response.document.rurPayJurSBDocument}"/>
    <%@include file="service-payment-document-fields.jsp"%>
</c:if>
    