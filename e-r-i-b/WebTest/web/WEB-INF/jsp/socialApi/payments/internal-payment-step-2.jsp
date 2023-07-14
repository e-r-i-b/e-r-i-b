<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Перевод между своими счетами</title>
</head>

<body>
<h1>Перевод между своими счетами</h1>

<html:form action="/mobileApi/internalPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <c:set var="document" value="${form.response.document.internalPaymentDocument}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
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
                    <c:if test="${not empty document.standartCourse}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="standartCourse"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="gain"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty document.buyAmount}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="buyAmount"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty document.sellAmount}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="sellAmount"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${form.version >= 5.10 and not empty document.commission}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="commission"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${form.version >= 5.10 and not empty document.operationCode}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="operationCode"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
