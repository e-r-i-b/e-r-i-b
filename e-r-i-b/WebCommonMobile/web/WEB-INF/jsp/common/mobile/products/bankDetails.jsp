<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form action="/private/accounts/bankdetails">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="details" value="${form.fields}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <details>
                <c:set var="owner" value="${details['RECEIVER']}"/>
            <c:if test="${not empty owner}">
                <owner>${phiz:getFormattedPersonName(owner.firstName, owner.surName, owner.patrName)}</owner>
            </c:if>
                <account>${details['accountNumber']}</account>
                <bankName>${details['bankName']}</bankName>
            <c:if test="${not empty details['BIC']}">
                <bic>${details['BIC']}</bic>
            </c:if>
            <c:if test="${not empty details['corrAccount']}">
                <corrAccount>${details['corrAccount']}</corrAccount>
            </c:if>
            <c:if test="${not empty details['INN']}">
                <inn>${details['INN']}</inn>
            </c:if>
            <c:if test="${not empty details['KPP']}">
                <kpp>${details['KPP']}</kpp>
            </c:if>
            <c:if test="${not empty details['OKPO']}">
                <okpo>${details['OKPO']}</okpo>
            </c:if>
                <ogrn>${details['OGRN']}</ogrn>
            <c:if test="${not empty details['caAddress']}">
                <caAddress>${details['caAddress']}</caAddress>
            </c:if>
            <c:if test="${not empty details['caAddress']}">
                <tbAddress>${details['tbAddress']}</tbAddress>
            </c:if>
                <vspAddress>${details['vspAddress']}</vspAddress>
            </details>
        </tiles:put>
    </tiles:insert>
</html:form>
