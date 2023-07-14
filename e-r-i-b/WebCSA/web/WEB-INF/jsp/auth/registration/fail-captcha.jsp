<%@ page contentType="application/json;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
    <c:set var="form" value="${RegistrationForm}"/>
        <tiles:insert definition="jSonResponseFail">
            <tiles:put name="captcha" value="true"/>
            <tiles:put name="errorType" value="error"/>
            <tiles:put name="name" value="field(captchaCode)"/>
            <tiles:put name="value" value=""/>
            <tiles:put name="text" value="Вы неправильно ввели код с картинки"/>
        </tiles:insert>