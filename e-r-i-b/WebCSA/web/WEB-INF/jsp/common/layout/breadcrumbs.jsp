<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="mainUrl" value="${csa:getMainUrl()}"/>
<c:if test="${empty mainUrl}">
    <c:set var="mainUrl" value="../index.do"/>
</c:if>

<c:if test="${main}">
    <c:choose>
        <c:when test="${last}">
            <li class="bcactive">
                <a href="${mainUrl}">
                    <c:out value="${csa:getAuthTitle()}"/>
                </a>
            </li>
        </c:when>
        <c:otherwise>
            <li class="unactive">
                <a href="${mainUrl}" style="width: 31px">
                    <c:out value="${csa:getAuthTitle()}"/>
                </a>
            </li>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${last}">
    <li class="bcactive">
        <a href="${action}">
            ${name}
        </a>
    </li>
</c:if>
<c:if test="${not main && not last}">
    <li class="unactive">
        <a href="${action}" style="width: 31px">
            ${name}
        </a>
    </li>
</c:if>