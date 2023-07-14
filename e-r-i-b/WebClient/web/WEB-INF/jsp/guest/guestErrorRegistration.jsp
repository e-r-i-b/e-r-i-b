<%--
  User: usachev
  Date: 30.01.15
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<phiz:messages id="errorMessage" field="field" message="error" bundle="commonBundle">
    <c:set var="fieldName" value="${field}"/>
    <c:set var="message" value="${errorMessage}"/>
</phiz:messages>


<c:set var="form" value="${CreateGuestProfileForm}"/>
{
    "success" : false,
    "captcha" : ${form.captcha},
    "error" : {
        "field" : "${fieldName}",
        "message" : "${message}"
    }
}