<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute ignore="true"/>
{
    "state": "fail",
    <c:if test="${errorType == 'error'}">
        "errors": [
        {
        "name": "${name}",
        "value": "${value}",
        "text": "${text}"
        }
        ],
    </c:if>
    <c:if test="${errorType == 'message'}">
        "showPopup": {
        <c:choose>
            <c:when test="${not empty messageId}">
                "id": "${messageId}"
            </c:when>
            <c:when test="${not empty text}">
                "text": "${text}"
            </c:when>
            <c:otherwise>
                "id": "SysError"
            </c:otherwise>
        </c:choose>
        },
    </c:if>
    <c:if test="${errorType == 'redirect'}">
        "redirect": "${redirect}",
    </c:if>
    "captcha": ${captcha}
}