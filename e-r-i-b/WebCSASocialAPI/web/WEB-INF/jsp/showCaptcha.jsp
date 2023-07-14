<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>

<%--@elvariable id="form" type="com.rssl.phizic.web.mapi.auth.register.RegisterAppForm"--%>
<c:set var="form" value="${RegisterAppForm}"/>

<tiles:insert definition="response" flush="false">
    <tiles:put name="data">
        <loginCompleted>false</loginCompleted>
        <c:if test="${not empty form.captchaBase64String}">
            <captchaRegistrationStage>
                <captcha>${form.captchaBase64String}</captcha>
            </captchaRegistrationStage>
        </c:if>
    </tiles:put>
</tiles:insert>