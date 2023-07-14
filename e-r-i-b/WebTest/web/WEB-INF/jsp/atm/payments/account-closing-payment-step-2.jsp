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

    <html:form action="/atm/accountClosingPayment" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>

            <tiles:put name="data">
                <c:set var="document" value="${form.response.document.accountClosingPaymentDocument}"/>
                <tiles:insert page="fields-table.jsp" flush="false">
                    <tiles:put name="data">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="documentNumber"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="documentDate"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="toResource"/>
                        </tiles:insert>
                        <c:if test="${not empty document.course}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="course"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="closingDate"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="chargeOffAmount"/>
                        </tiles:insert>
                        <c:if test="${not empty document.destinationAmount}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="document" beanProperty="destinationAmount"/>
                            </tiles:insert>
                        </c:if>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
