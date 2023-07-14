<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ include file="/WEB-INF/jsp/common/socialApi/types/status.jsp"%>

<html:form action="/private/accounts/abstract">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="resourceAbstract" value="${form.accountAbstract}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty resourceAbstract.transactions}">
                <sl:collection id="transaction" property="accountAbstract.transactions" model="xml-list" title="operations">
                    <sl:collectionItem title="operation">
                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="date"/>
                            <tiles:put name="calendar" beanName="transaction" beanProperty="date"/>
                        </tiles:insert>

                        <c:set var="sum" value="" />
                        <c:set var="sign" value="" />

                        <c:choose>
                            <c:when test="${!empty transaction.creditSum and transaction.creditSum.decimal != '0.00'}"><%-- из v6 приходит в случае пустой суммы зачисления приходит не null, а 0.00  --%>
                                <c:set var="sum" value="${transaction.creditSum}" />
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="sum" />
                                    <tiles:put name="money" beanName="sum"/>
                                    <tiles:put name="sign" value="+"/>
                                </tiles:insert>
                            </c:when>
                            <c:when test="${!empty transaction.debitSum and transaction.debitSum.decimal != '0.00'}">  <%-- из v6 приходит в случае пустой суммы списания приходит не null, а 0.00  --%>
                                <c:set var="sum" value="${transaction.debitSum}" />
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="sum" />
                                    <tiles:put name="money" beanName="sum"/>
                                    <tiles:put name="sign" value="-"/>
                                </tiles:insert>
                            </c:when>
                        </c:choose>

                        <c:choose>
                            <c:when test="${!empty transaction.description}">
                                <description>
                                    <c:out value="${transaction.description}"/>
                                </description>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${!empty transaction.creditSum}">
                                        <description>Зачисление</description>
                                    </c:when>
                                    <c:when test="${!empty transaction.debitSum}">
                                        <description>Списание</description>
                                    </c:when>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </sl:collectionItem>
                </sl:collection>
            </c:if>
            <%-- Остатки по балансам --%>
            <c:if test="${!empty resourceAbstract.openingBalance || !empty resourceAbstract.closingBalance}">
                <balances>
                    <tiles:insert definition="mobileMoneyType" flush="false">
                        <tiles:put name="name" value="openingBalance" />
                        <tiles:put name="money" beanName="resourceAbstract" beanProperty="openingBalance"/>
                    </tiles:insert>
                    <tiles:insert definition="mobileMoneyType" flush="false">
                        <tiles:put name="name" value="closingBalance" />
                        <tiles:put name="money" beanName="resourceAbstract" beanProperty="closingBalance"/>
                    </tiles:insert>
                </balances>
            </c:if>
        </tiles:put>
        <c:if test="${empty resourceAbstract and form.error}">
            <tiles:put name="status">${STATUS_CRITICAL_ERROR}</tiles:put>
            <tiles:put name="errorDescription">АБС не доступна. Получение информации не возможно.</tiles:put>
        </c:if>
    </tiles:insert>
</html:form>