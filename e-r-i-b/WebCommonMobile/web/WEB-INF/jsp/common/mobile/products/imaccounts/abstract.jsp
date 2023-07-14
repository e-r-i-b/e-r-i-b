<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp"%>
<html:form action="/private/ima/abstract">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imAccountAbstract" value="${form.imAccountAbstract}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="transaction" property="imAccountAbstract.transactions" model="xml-list" title="elements">
                <sl:collectionItem title="element">

                    <tiles:insert definition="mobileDateTimeType" flush="false">
                        <tiles:put name="name" value="date"/>
                        <tiles:put name="calendar" beanName="transaction" beanProperty="date"/>
                    </tiles:insert>

                    <c:set var="credit" value="${transaction.creditSum}"/>
                    <c:set var="debit" value="${transaction.debitSum}"/>

                    <c:choose>
                        <c:when test="${not empty debit and debit.decimal > 0.00}">
                            <currency>${debit.currency.name} (${phiz:normalizeMetalCode(debit.currency.id)})</currency>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="debit"/>
                                <tiles:put name="money" beanName="debit"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${not empty credit and credit.decimal > 0.00}">
                            <currency>${credit.currency.name} (${phiz:normalizeMetalCode(credit.currency.id)})</currency>
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="credit"/>
                                <tiles:put name="money" beanName="credit"/>
                            </tiles:insert>
                        </c:when>
                    </c:choose>
                    
                </sl:collectionItem>
           </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>