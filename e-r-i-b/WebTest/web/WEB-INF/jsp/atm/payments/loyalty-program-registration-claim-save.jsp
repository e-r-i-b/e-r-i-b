<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Создание заявки на регистрацию в программе лояльности</title>
    </head>

    <body>
        <h1>Создание заявки на регистрацию в программе лояльности: сохранение заявки</h1>

        <html:form action="/atm/loyaltyProgramRegistrationClaim" show="true">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

            <tiles:insert definition="atm" flush="false">
                <tiles:put name="address"   value="/private/payments/payment.do"/>
                <tiles:put name="formName"  value="LoyaltyProgramRegistrationClaim"/>

                <c:if test="${not empty form.response.initialData}">
                    <tiles:put name="operation" value="save"/>

                    <c:set var="claim" value="${form.response.initialData.loyaltyProgramRegistrationClaim}"/>
                </c:if>

                <c:if test="${not empty form.response.document}">
                    <c:set var="document" value="${form.response.document.loyaltyProgramRegistrationClaim}"/>
                </c:if>

                <tiles:put name="data">
                    <c:choose>
                        <c:when test="${not empty claim}">
                            <tiles:insert page="fields-table.jsp" flush="false">
                                <tiles:put name="data">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="claim" beanProperty="documentNumber"/>
                                    </tiles:insert>

                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="claim" beanProperty="phone"/>
                                    </tiles:insert>

                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="claim" beanProperty="email"/>
                                    </tiles:insert>

                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="claim" beanProperty="rulesUrl"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </c:when>

                        <c:when test="${not empty document}">
                            <c:set var="document" value="${form.response.document.loyaltyProgramRegistrationClaim}"/>

                            <tiles:insert page="fields-table.jsp" flush="false">
                                <tiles:put name="data">
                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="documentNumber"/>
                                    </tiles:insert>

                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="documentDate"/>
                                    </tiles:insert>

                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="phone"/>
                                    </tiles:insert>

                                    <tiles:insert page="field.jsp" flush="false">
                                        <tiles:put name="field" beanName="document" beanProperty="email"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                    </c:choose>
                </tiles:put>
            </tiles:insert>

        </html:form>
    </body>
</html:html>