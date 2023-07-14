<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="confirmType" value="${form.response.confirmStage.confirmType}"/>

<c:if test="${not empty form.response.document.rurPayJurSBDocument}">
    <c:set var="document" value="${form.response.document.rurPayJurSBDocument}"/>
    <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <%@include file="service-payment-document-fields.jsp"%>
    </div>
</c:if>    