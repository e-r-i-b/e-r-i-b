<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:if test="${main}">
    <li class="home">
        <%-- ¬сегда считаем, что "хлебные крошки" - это область веб-приложени€ (а не веб-модул€) --%>
        <html:link contextPathName="application" action="${action}" onclick="return redirectResolved();"></html:link>
    </li>
</c:if>
<%-- <c:if test="${last}">
    <li class="bcactive">
        ${name}
    </li>
</c:if> --%>
<c:if test="${not main && not last}">
    <li class="unactive">
        <c:choose>
            <c:when test="${not empty action}">
                <%-- ¬сегда считаем, что "хлебные крошки" - это область веб-приложени€ (а не веб-модул€) --%>
                <html:link contextPathName="application" action="${action}" onclick="return redirectResolved();">
                    <span>${name}</span>
                </html:link>
            </c:when>
            <c:when test="${not empty url}">
                <phiz:link url="${fn:trim(url)}" onclick="return redirectResolved();"><span>${name}</span></phiz:link>
            </c:when>
        </c:choose>
    </li>
</c:if>