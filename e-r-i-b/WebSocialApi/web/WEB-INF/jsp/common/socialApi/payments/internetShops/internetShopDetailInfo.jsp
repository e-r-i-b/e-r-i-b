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
            <sl:collection id="field" property="fields" model="xml-list" title="itemList">
                <sl:collectionItem title="item">
                    <c:if test="${not empty field.productName}">
                        <name><c:out value="${field.productName}"/></name>
                    </c:if>
                    <c:if test="${not empty field.productDescription}">
                        <description><c:out value="${field.productDescription}"/></description>
                    </c:if>
                    <c:if test="${not empty field.count}">
                        <count><c:out value="${field.count}"/></count>
                    </c:if>
                    <c:if test="${not empty field.amount}">
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="price"/>
                            <tiles:put name="money" beanName="field" beanProperty="amount"/>
                        </tiles:insert>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>