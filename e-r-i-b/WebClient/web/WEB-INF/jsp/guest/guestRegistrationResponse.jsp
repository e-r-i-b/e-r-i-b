<%--
  User: usachev
  Date: 02.02.15
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


<c:set var="form" value="${CreateGuestProfileForm}"/>
<c:set var="isFirst" value="${true}"/>
{
"success": ${form.success},
"captcha": ${form.captcha},
"token": "${sessionScope["org.apache.struts.action.TOKEN"]}"
<c:if test="${form.success}">
    ,"redirect": "${phiz:calculateActionURL(pageContext, '/guest/index')}"
</c:if>
,"errors":[
<phiz:messages id="errorMessage" field="field" message="error" bundle="commonBundle">
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

</phiz:messages>
]
}