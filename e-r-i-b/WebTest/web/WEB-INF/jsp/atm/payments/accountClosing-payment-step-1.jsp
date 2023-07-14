<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Закрытие вкладов</title>
    </head>

    <body>
    <h1>Закрытие вкладов</h1>

    <html:form action="/atm/AccountClosingPayment" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>

            <tiles:put name="data">
                <c:choose>
                    <c:when test="${not empty form.response.initialData}">
                        <c:set var="initialData" value="${form.response.initialData.accountClosingPayment}"/>

                        <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                            <tiles:insert page="fields-table.jsp" flush="false">
                                <tiles:put name="data">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="closingDate"/>
                                    </tiles:insert>
                                     <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="documentNumber"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="documentDate"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="toResource"/>
                                    </tiles:insert>
                                     <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="chargeOffAmount"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="course"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:when>
                </c:choose>
            </tiles:put>

            <c:if test="${empty form.response.confirmStage.confirmType}">
                <tiles:put name="operation" value="save"/>
            </c:if>
            <tiles:put name="formName" value="AccountClosingPayment"/>
        </tiles:insert>

        <c:url var="AccountClosingPaymentUrl" value="/atm/AccountClosingPayment.do">
            <c:param name="url" value="${form.url}"/>
            <c:param name="cookie" value="${form.cookie}"/>
            <c:param name="proxyUrl" value="${form.proxyUrl}"/>
            <c:param name="proxyPort" value="${form.proxyPort}"/>
        </c:url>
        <html:link href="${AccountClosingPaymentUrl}">Начать сначала</html:link>
    </html:form>

    </body>
</html:html>
