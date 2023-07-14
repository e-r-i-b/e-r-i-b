<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en-US"/>

<html:form action="/private/accounts/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="accountLink" value="${form.accountLink}" scope="request"/>
    <c:set var="account" value="${accountLink.account}"/>
    <c:set var="isImpliesChangeInterestDestService" value="${phiz:impliesServiceRigid('AccountChangeInterestDestinationClaim')}"/>
    <c:set var="availableCapitalization" value="${phiz:isAvailableCapitalizationByProductId(account.kind)}"/>
    <c:set var="isEmptyInterestCard" value="${empty account.interestTransferCard}"/>
    <c:set var="haveCardsWithTBLogined" value="${phiz:haveCardsWithTBLogined()}"/>
    <c:set var="canChangePercentDestination" value="${isImpliesChangeInterestDestService and availableCapitalization and account.kind ne 61
        and (not isEmptyInterestCard or haveCardsWithTBLogined) and (account.accountState=='OPENED' or account.accountState=='LOST_PASSBOOK')}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <detail>
                <description><c:out value="${accountLink.description}"/></description>
                <c:if test="${!empty account.period}">
                    <period>${account.period.years}-${account.period.months}-${account.period.days}</period>
                </c:if>
                <tiles:insert definition="mobileDateTimeType" flush="false">
                    <tiles:put name="name" value="open"/>
                    <tiles:put name="calendar" beanName="account" beanProperty="openDate"/>
                </tiles:insert>
                <%--Вклад срочный--%>
                <c:set var="isNotDemand" value="${(not empty account.demand) && (not account.demand)}"/>
                <%--Если вклад срочный и есть дата закрытия--%>
                <c:if test="${isNotDemand && (not empty account.closeDate)}">
                    <tiles:insert definition="mobileDateTimeType" flush="false">
                        <tiles:put name="name" value="close"/>
                        <tiles:put name="calendar" beanName="account" beanProperty="closeDate"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${!empty account.interestRate}">
                    <interestRate><fmt:formatNumber value="${account.interestRate}" pattern="0.00"/></interestRate>
                </c:if>
                <c:if test="${not empty account.maxSumWrite}">
                    <tiles:insert definition="mobileMoneyType" flush="false">
                        <tiles:put name="name" value="maxSumWrite" />
                        <tiles:put name="money" beanName="account" beanProperty="maxSumWrite"/>
                    </tiles:insert>
                </c:if>
                <c:choose>
                    <c:when test="${account.passbook}">
                        <passbook>true</passbook>
                    </c:when>
                    <c:otherwise>
                        <passbook>false</passbook>
                    </c:otherwise>
                </c:choose>

                <c:if test="${not empty account.creditCrossAgencyAllowed}">
                    <crossAgency>${account.creditCrossAgencyAllowed}</crossAgency>
                </c:if>
                <c:if test="${not empty account.prolongationAllowed}">
                    <prolongation>${account.prolongationAllowed}</prolongation>
                </c:if>
                <c:if test="${not empty account.interestTransferAccount}">
                    <percentAcc>${account.interestTransferAccount}</percentAcc>
                </c:if>
                <c:if test="${not empty account.interestTransferCard}">
                    <percentCard>${account.interestTransferCard}</percentCard>
                </c:if>
                <c:if test="${not empty account.minimumBalance}">
                    <tiles:insert definition="mobileMoneyType" flush="false">
                        <tiles:put name="name" value="irreducibleAmt" />
                        <tiles:put name="money" beanName="account" beanProperty="minimumBalance"/>
                    </tiles:insert>
                </c:if>
                <name><c:out value="${accountLink.name}"/></name>

                <c:set var="target" value="${form.target}"/>
                <c:if test="${not empty target}">
                    <target>
                        <name>${target.name}</name>

                        <c:set var="comment" value="${target.nameComment}"/>
                        <c:if test="${not empty comment}">
                            <comment>${comment}</comment>
                        </c:if>

                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name"     value="date"/>
                            <tiles:put name="pattern"  value="dd.MM.yyyy"/>
                            <tiles:put name="calendar" beanName="target" beanProperty="plannedDate"/>
                        </tiles:insert>

                        <c:set var="amount" value="${target.amount}"/>
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name"         value="amount"/>
                            <tiles:put name="decimal"      value="${amount.decimal}"/>
                            <tiles:put name="currencyCode" value="${amount.currency.code}"/>
                        </tiles:insert>
                    </target>
                </c:if>

                <canChangePercentDestination>
                    <c:out value="${canChangePercentDestination}"/>
                </canChangePercentDestination>
                <c:if test="${not empty account.maxBalance}">
                     <maxBalance>${phiz:formatDecimalToAmountWithCustomSeparatorGrouping(account.maxBalance, 2, ".", false)}</maxBalance>
                </c:if>
            </detail>
        </tiles:put>
    </tiles:insert>
</html:form>
