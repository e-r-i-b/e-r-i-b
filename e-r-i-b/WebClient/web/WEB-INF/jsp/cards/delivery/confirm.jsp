<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html"  %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>

&nbsp;
<tiles:importAttribute/>
<html:form action="/private/async/cards/editEmailDelivery" appendToken="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:set var="document" value="${form.document}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <c:set var="ajaxActionUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/cards/editEmailDelivery')}"/>
        <tiles:insert definition="confirm_${confirmRequest.strategyType}" flush="false">
            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
            <tiles:put name="useAjax" value="true"/>
            <tiles:put name="ajaxUrl" value="/private/async/cards/editEmailDelivery"/>
            <tiles:put name="data">
                ${form.html}
            </tiles:put>
            <c:set var="buttonName"><bean:message key="button.confirm" bundle="securityBundle"/></c:set>
            <tiles:put name="validationFunction"    value="checkClientAgreesCondition('${buttonName}');"/>
            <tiles:put name="confirmableObject"     value="CardReportDeliveryClaim"/>
            <tiles:put name="byCenter"              value="Center"/>
            <tiles:put name="confirmCommandKey"     value="button.confirm"/>
            <tiles:put name="showCancelButton"      value="false"/>
            <tiles:put name="buttonType"            value="singleRow"/>
            <tiles:put name="confirmStrategy"       beanName="confirmStrategy"/>
            <c:if test="${confirmRequest.strategyType == 'card'}">
                <tiles:put name="cardNumber" value="${phiz:getCutCardNumber(form.fields.confirmCard)}"/>
            </c:if>
        </tiles:insert>
</html:form>