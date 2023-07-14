<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Открытие вклада</title>
</head>

<body>
<h1>Открытие вклада</h1>

<html:form action="/atm/accountOpeningClaim" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/payment.do"/>

        <tiles:put name="data">
            <c:set var="document" value="${form.response.document.accountOpeningClaimDocument}"/>
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="depositSubType"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="documentNumber"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="documentDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="depositName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="openDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="closingDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="toResourceCurrency"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="needInitialFee"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="withMinimumBalance"/>
                    </tiles:insert>
                    <c:if test="${document.withMinimumBalance.booleanType.value eq true}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="minDepositBalance"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${document.fromResource != null}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${document.buyAmount != null}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="buyAmount"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${document.course != null}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="course"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${document.sellAmount != null}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="sellAmount"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${document.exactAmount != null}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="exactAmount"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${document.interestRate != null}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="interestRate"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${document.minAdditionalFee != null}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="minAdditionalFee"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty document.percentTransferSource}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="percentTransferSource"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty document.percentTransferCardSource}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="percentTransferCardSource"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
