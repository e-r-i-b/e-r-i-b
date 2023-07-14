<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/ima/products/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="ima" property="data" model="xml-list" title="imaProductList">
                <c:set var="currency" value="${ima.currency}"/>
                <c:set var="rate" value="${form.currencyRates[currency]}"/>
                <sl:collectionItem title="imaProduct">
                    <id>${ima.id}</id>
                    <name>
                        <%--Капитализируем--%>
                        <c:set var="name" value="${ima.name}"/>
                        <c:set var="capitalizedName">${fn:toUpperCase(fn:substring(name, 0, 1))}${fn:substring(name, 1, fn:length(name))}</c:set>
                        <c:out value="${capitalizedName}"/>
                    </name>
                    <tiles:insert definition="currencyType" flush="false">
                        <tiles:put name="currency" beanName="currency"/>
                    </tiles:insert>
                    <rate>${phiz:getFormattedCurrencyRate(rate, 2)}</rate>
                    <tiles:insert definition="currencyType" flush="false">
                        <tiles:put name="currency" beanName="rate" beanProperty="toCurrency"/>
                        <tiles:put name="name" value="nationalCurrency"/>
                    </tiles:insert>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>