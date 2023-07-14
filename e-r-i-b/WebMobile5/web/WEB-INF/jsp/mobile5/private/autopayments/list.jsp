<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/autopayments/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.autoPayments}">
                <autoPayments>
                    <c:forEach var="regularPayment" items="${form.autoPayments}">
                        <autoPayment>
                            <id><bean:write name="regularPayment" property="id" ignore="true"/></id>
                            <name><bean:write name="regularPayment" property="name" ignore="true"/></name>
                            <executionEventDescription><bean:write name="regularPayment" property="executionEventType" ignore="true"/></executionEventDescription>
                            <c:set var="payment" value="${regularPayment.value}"/>
                            <c:set var="amountValue" value="${payment.executionEventType == 'BY_INVOICE' ? payment.floorLimit : payment.amount }"/>
                            <executionEventType><bean:write name="payment" property="executionEventType" ignore="true"/></executionEventType>
                            <c:if test="${not empty amountValue}">
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="amount"/>
                                    <tiles:put name="money" beanName="amountValue"/>
                                </tiles:insert>
                            </c:if>

                            <c:if test="${phiz:isInstance(regularPayment, 'com.rssl.phizic.business.resources.external.AutoPaymentLink')}">
                                <status><bean:write name="regularPayment" property="statusReport" ignore="true"/></status>
                            </c:if>
                        </autoPayment>
                    </c:forEach>
                </autoPayments>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>