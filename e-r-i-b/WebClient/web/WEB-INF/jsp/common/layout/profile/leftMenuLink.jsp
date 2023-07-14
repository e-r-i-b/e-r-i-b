<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<c:set var="additionalSpanParameters" value=""/>
<c:if test="${newItem == 'true'}">
    <c:set var="additionalSpanParameters">class="newItem"</c:set>
</c:if>

<c:choose>
    <c:when test="${active == 'true'}">
        <li class="active">
            <c:choose>
                <c:when test="${checkEnabledLink and not fn:contains(sessionScope['curUrl'], '/private/userprofile/basket')}">
                    <phiz:link action="${action}"><span>${name}</span><span ${additionalSpanParameters}></span></phiz:link>
                </c:when>
                <c:otherwise>
                    <span>${name}</span><span ${additionalSpanParameters}></span>
                </c:otherwise>
            </c:choose>
        </li>
    </c:when>
    <c:otherwise>
        <li>
            <phiz:link action="${action}"><span>${name}</span><span ${additionalSpanParameters}></span></phiz:link>
        </li>
    </c:otherwise>
</c:choose>