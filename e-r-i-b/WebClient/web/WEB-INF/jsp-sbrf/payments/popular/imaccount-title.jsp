<%--
  Внешние переменные используемые в данном файле
  imAccountLink    - линк на ОМС
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:if test="${not empty imAccountLink}">
    <%--Номер ОМС--%>
    <c:set var="userIMAccountNumber" value="${phiz:getFormattedIMAccountNumber(imAccountLink.number)}"/>
    <%--Наименование ОМС--%>
    <c:set var="userIMAccountName" value="${imAccountLink.name}"/>
    <span class="word-wrap"><bean:write name="userIMAccountNumber"/></span>
    <span class="word-wrap whole-words"><bean:write name="userIMAccountName"/></span>
</c:if>