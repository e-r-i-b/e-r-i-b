<%@ page contentType="application/json;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute ignore="true"/>
{
    "state": "fail"
<c:if test="${not empty token}">
    ,"token": "${token}"
</c:if>
<c:if test="${errorType == 'error'}">
    ,"errors": [
        {
            "name": "${name}",
            "value": "${value}",
            "text": "${text}"
        }
    ]
</c:if>
<c:if test="${errorType == 'showPopup'}">
    ,"showPopup" : {
        "id" : "${popupId}"
        <c:if test='${not empty messageText}'>
        ,"text" : "${messageText}"
        </c:if>
        <c:if test='${not empty disabled}'>
        ,"disabled" : ${disabled}
        </c:if>
        <c:if test='${not empty onCloseRedirect}'>
        ,"onCloseRedirect" : "${onCloseRedirect}"
        </c:if>
    }
</c:if>
<c:if test="${errorType == 'showError'}">
    ,"showError": {
        "id": "${messageId}",
        "params": {
            "param" : "${messageText}"
        }
    }
</c:if>
<c:if test="${errorType == 'redirect'}">
    ,"redirect": "${redirect}"
</c:if>
<c:if test="${not empty captcha}">
    ,"captcha": ${captcha}
</c:if>
<c:if test="${not empty hidePopups}">
    ,"hidePopups": true
</c:if>
<c:if test="${isRegistered}">
    ,"isRegistered": true
</c:if>
<c:if test="${not empty goToStep}">
    , "goToStep" : ${goToStep}
</c:if>
}