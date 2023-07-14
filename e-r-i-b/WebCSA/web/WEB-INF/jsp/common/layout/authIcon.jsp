<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib prefix="html"  uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean"  uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>


<%--
    Пиктограмма входа в зависимости от режима посещения:
    - обычный вход
    - оплата платёжного поручения по технологии e-invoicing
--%>

<tiles:importAttribute/>

<c:choose>
    <c:when test="${csa:isUECPaymentSession()}">
        <img src="${skinUrl}/skins/sbrf/images/csa/uec-icon.jpg">
    </c:when>

    <c:otherwise>
        <img src="${skinUrl}/skins/sbrf/images/csa/lock.jpg">
    </c:otherwise>
</c:choose>
