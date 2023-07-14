<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
&nbsp;
<tiles:importAttribute/>
<html:form action="/guest/async/sberbankForEveryDay" appendToken="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <tiles:insert definition="confirm_${confirmRequest.strategyType}" flush="false">
        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
        <tiles:put name="useAjax" value="true"/>
        <tiles:put name="ajaxUrl" value="/guest/async/sberbankForEveryDay"/>
        <tiles:put name="anotherStrategy"       value="false"/>
        <tiles:put name="confirmableObject"     value="claim"/>
        <tiles:put name="byCenter"              value="Center"/>
        <tiles:put name="confirmCommandKey"     value="button.confirm"/>
        <tiles:put name="showCancelButton"      value="false"/>
        <tiles:put name="buttonType"            value="singleRow"/>
        <tiles:put name="confirmStrategy"       beanName="confirmStrategy"/>
        <tiles:put name="message"><p class="messSize14"><bean:message key="sms.payments.security.message" bundle="securityBundle"/></p></tiles:put>
    </tiles:insert>
</html:form>
