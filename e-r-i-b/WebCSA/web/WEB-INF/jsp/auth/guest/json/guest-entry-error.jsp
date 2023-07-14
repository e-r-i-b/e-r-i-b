<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<c:set var="form" value="${GuestEntryForm}"/>
<c:set var="isFirst" value="${true}"/>
{
    "success" : false,
    "captcha" : false,
    <c:set var="token" value="${sessionScope['org.apache.struts.action.TOKEN']}"/>
    <c:if test="${not empty token}">
        "token" : "${token}",
    </c:if>
    <c:if test="${form.SMSAttemptsEnded}">
        "SMSAttemptsEnded" : true,
    </c:if>
    "errors":[
    <csa:messages id="errorMessage" field="field" message="error" bundle="commonBundle">
        <c:if test="${empty field}">
            <c:set var="field" value = "popupError"/>
        </c:if>
        <c:choose>
            <c:when test="${isFirst}">
                <c:set var="isFirst" value="${false}"/>
                {
                "field": "${field}",
                "message":"${errorMessage}"
                }
            </c:when>
            <c:otherwise>
                ,{
                "field": "${field}",
                "message":"${errorMessage}"
                }
            </c:otherwise>
        </c:choose>

    </csa:messages>
    ]
}