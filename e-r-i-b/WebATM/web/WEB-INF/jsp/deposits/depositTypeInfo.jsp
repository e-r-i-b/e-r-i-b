<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<html:form action="/private/deposits/products/typeInfo">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:set var="interestRate" value="${form.fields['interest-rate']}"/>
            <c:set var="depositSubType" value="${form.fields['deposit-sub-type']}"/>
            <c:set var="minAdditionalFee" value="${form.fields['min-additional-fee']}"/>
            <c:set var="minDepositBalance" value="${form.fields['min-deposit-balance']}"/>

            <c:if test="${not empty interestRate}">
                <interestRate><c:out value="${interestRate}"/></interestRate>
            </c:if>

            <depositSubType><c:out value="${depositSubType}"/></depositSubType>

            <c:if test="${not empty minAdditionalFee}">
                <minAdditionalFee><c:out value="${minAdditionalFee}"/></minAdditionalFee>
            </c:if>

            <c:if test="${not empty minDepositBalance}">
                <minDepositBalance><c:out value="${minDepositBalance}"/></minDepositBalance>
            </c:if>

        </tiles:put>
    </tiles:insert>
</html:form>