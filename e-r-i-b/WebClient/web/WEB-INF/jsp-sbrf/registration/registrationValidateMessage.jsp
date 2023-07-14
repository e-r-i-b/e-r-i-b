<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="form" value="${AsyncSelfRegistrationForm}"/>
<tiles:insert definition="jsonResponseFail">
    <tiles:put name="errorType" value="message"/>
    <tiles:put name="text" value="${form.textError}"/>
    <tiles:put name="messageId" value="${form.messageId}"/>
    <tiles:put name="captcha" value="${form.needShowCaptcha}"/>
</tiles:insert>