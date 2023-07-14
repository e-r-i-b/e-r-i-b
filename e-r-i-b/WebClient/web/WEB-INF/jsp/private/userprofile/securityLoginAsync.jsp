<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="thisActionUrl" value="/private/async/userprofile/accountSecurity/loginConfirm"/>
<html:form action="${thisActionUrl}">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:if test="${not empty form.confirmableObject}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    </c:if>
    <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
    <tiles:insert  definition="${confirmTemplate}" flush="false">
        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
        <tiles:put name="anotherStrategy" value="false"/>
        <tiles:put name="confirmableObject" value="securitySettings"/>
        <tiles:put name="message">
            <bean:message key="${confirmRequest.strategyType}.settings.security.message" bundle="securityBundle"/>
        </tiles:put>
        <tiles:put name="data">
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title"><bean:message bundle="userprofileBundle" key="label.login"/> : </tiles:put>
                <tiles:put name="needMark" value="false"/>
                <tiles:put name="data">
                    <span class="bold">${form.fields.newLogin}</span>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="confirmCommandKey" value="button.confirm"/>
        <tiles:put name="useAjax" value="true"/>
        <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
    </tiles:insert>
</html:form>