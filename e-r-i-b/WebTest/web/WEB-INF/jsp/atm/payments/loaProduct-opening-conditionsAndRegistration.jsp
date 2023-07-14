<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>ќформить за€вку на кредит карту</title>
    </head>

    <body>
    <h1>ќформить за€вку на кредит </h1>

    <html:form action="/atm/loanProductOpeningClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>
            <tiles:put name="formName" value="LoanProduct"/>
            <c:if test="${empty form.response.confirmStage.confirmType}">
                <tiles:put name="operation" value="next"/>
            </c:if>

            <tiles:put name="data">
                <c:choose>
                    <c:when test="${not empty form.response.initialData.loanProduct}">
                        <c:set var="initialData" value="${form.response.initialData.loanProduct}"/>
                        <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                            <tiles:insert page="fields-table.jsp" flush="false">
                                <tiles:put name="data">
                                    <tiles:insert page="field.jsp" flush="false">
                                         <tiles:put name="field" beanName="initialData" beanProperty="loan"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="changeDate"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="creditKind"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="productId"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="documentNumber"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="documentDate"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="creditType"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="sum.amount"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="sum.currency"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="duration"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="surName"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="firstName"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="patrName"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="mobilePhone"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="hirer"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="averageIncomePerMonth"/>
                                    </tiles:insert>
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="initialData" beanProperty="getPaidOnAccount"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:when>
                    <c:when test="${not empty form.response.confirmStage.confirmType}">
                        <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                            <c:set var="payment" value="${form.response.document.loanProductDocument}"/>
                            <tiles:insert page="fields-table.jsp" flush="false">
                                <tiles:put name="data">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="payment" beanProperty="documentNumber"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:when>
                </c:choose>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
