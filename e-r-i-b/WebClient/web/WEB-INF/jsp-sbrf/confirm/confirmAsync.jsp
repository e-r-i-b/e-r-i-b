<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="thisActionUrl" value="/async/confirm"/>
<html:form action="${thisActionUrl}" updateAtributes="false">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${form.confirmRequest}"/>
    <c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
    <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
    <c:set var="message" value="Для обеспечения безопасности входа в систему необходимо ввести одноразовый пароль. Убедитесь, что пароль введен верно, и нажмите на кнопку «Подтвердить»."/>
    <tiles:insert  definition="${confirmTemplate}" flush="false">
        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
        <tiles:put name="anotherStrategy" beanName="anotherStrategy"/>
        <tiles:put name="confirmableObject" value="login"/>
        <tiles:put name="message" value="${message}"/>
        <tiles:put name="useAjax" value="true"/>
        <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
        <c:if test="${confirmRequest.strategyType == 'card'}">
            <tiles:put name="cardNumber" value="${phiz:getCutCardNumber(form.fields.confirmCard)}"/>
        </c:if>
    </tiles:insert>
</html:form>