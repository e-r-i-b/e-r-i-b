<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    ���������� �������� �� ���������� �������� �� �������/�����
    value - ��������
    emptyValue - ������, ���� �������� null
--%>
<tiles:importAttribute/>
<c:choose>
    <c:when test="${not empty value}">
        <c:out value="${value}"/>
    </c:when>
    <c:otherwise>
        <span class="gray"><c:out value="${emptyValue}"/></span>
    </c:otherwise>
</c:choose>
