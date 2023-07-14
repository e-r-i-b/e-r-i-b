<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/private/payments/internetShops/detailInfo">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.shopOrdersInfo}">
                <itemList>
                    <c:forEach var="shopOrder" items="${form.shopOrdersInfo}">
                    <item>
                        <c:if test="${not empty shopOrder.productName}">
                            <name><c:out value="${shopOrder.productName}"/></name>
                        </c:if>
                        <c:if test="${not empty shopOrder.productDescription}">
                            <description><c:out value="${shopOrder.productDescription}"/></description>
                        </c:if>
                        <c:if test="${not empty shopOrder.shopCount}">
                            <count><c:out value="${shopOrder.shopCount}"/></count>
                        </c:if>
                        <c:if test="${not empty shopOrder.shopAmount and not empty shopOrder.shopCurrency}">
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name"         value="price"/>
                                <tiles:put name="decimal"      value="${shopOrder.shopAmount}"/>
                                <tiles:put name="currencyCode" value="${shopOrder.shopCurrency}"/>
                            </tiles:insert>
                        </c:if>
                    </item>
                    </c:forEach>
                </itemList>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>