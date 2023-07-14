<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="form" value="${GuestEntryForm}"/>
{
    "success": ${form.successNextStage},
    "captcha" : false,
    <c:if test="${not empty form.operationInfo.confirmParams['Timeout']}">
        "timer" : ${form.operationInfo.confirmParams['Timeout']},
    </c:if>
    "token" : "${sessionScope['org.apache.struts.action.TOKEN']}"
}