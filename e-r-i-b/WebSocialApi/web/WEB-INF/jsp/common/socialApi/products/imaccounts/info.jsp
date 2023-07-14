<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:form action="/private/ima/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imAccountLink" value="${form.imAccountLink}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty imAccountLink}">
                <c:set var="imAccount" value="${imAccountLink.imAccount}"/>
                <detail>
                    <id>${imAccountLink.id}</id>
                    <number>${imAccountLink.number}</number>
                    <c:if test="${not empty imAccount.currency}">
                        <currency><c:out value="${imAccount.currency.name} (${phiz:normalizeMetalCode(imAccount.currency.code)})"/></currency>
                    </c:if>
                    <c:if test="${not empty imAccount.balance}">
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="balance"/>
                            <tiles:put name="money" beanName="imAccount" beanProperty="balance"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty imAccount.agreementNumber}">
                        <agreementNumber>${imAccount.agreementNumber}</agreementNumber>
                    </c:if>
                    <c:if test="${not empty imAccount.openDate}">
                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="openDate"/>
                            <tiles:put name="calendar" beanName="imAccount" beanProperty="openDate"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty imAccount.closingDate}">
                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="closeDate"/>
                            <tiles:put name="calendar" beanName="imAccount" beanProperty="closingDate"/>
                        </tiles:insert>
                    </c:if>
                    <state>${imAccount.state}</state>
                    <name><c:out value="${imAccountLink.name}"/></name>
                </detail>
            </c:if>
        </tiles:put>
    </tiles:insert>

</html:form>