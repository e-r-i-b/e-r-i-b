<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/deposits/products/info">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:choose>
                <c:when test="${phiz:isUseCasNsiDictionaries()}">
                    <c:set var="depositEntity" value="${form.entity}"/>
                    <c:set var="entityTDog" value="${form.entityTDog}"/>

                    <conditionsList>
                        <c:forEach items="${depositEntity.depositRates}" var="rateEntry">
                            <c:set var="currency" value="${rateEntry.key}"/>

                            <c:if test="${not empty currency}">
                                <c:forEach items="${rateEntry.value}" var="balanceRatesEntry"  varStatus="rowNum">
                                    <c:choose>
                                        <c:when test="${not empty depositEntity.periodList}">
                                            <c:forEach items="${depositEntity.periodList}" var="period">
                                                <c:forEach items="${balanceRatesEntry.value}" var="periodRatesEntry">
                                                    <c:if test="${periodRatesEntry.key == period}">
                                                            <condition>
                                                                <currency>
                                                                    <code>${currency}</code>
                                                                    <name>${phiz:getCurrencySign(currency)}</name>
                                                                </currency>
                                                                <amountOf>
                                                                    <fmt:formatNumber value="${balanceRatesEntry.key}" pattern="0.00"/>
                                                                </amountOf>
                                                                <period>
                                                                    <c:set var="periodFrom" value="${fn:substringBefore(period, 'U')}"/>
                                                                    <c:set var="periodTo" value="${fn:substringAfter(period, 'U')}"/>
                                                                    <from>
                                                                        <c:choose>
                                                                            <c:when test="${empty periodFrom}">
                                                                                ${period}
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                ${periodFrom}
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </from>
                                                                    <c:if test="${not empty periodTo}">
                                                                        <to>${periodTo}</to>
                                                                    </c:if>
                                                                </period>
                                                                <interestRate>
                                                                    <fmt:formatNumber value="${periodRatesEntry.value[0].baseRate}" pattern="0.00"/>
                                                                </interestRate>
                                                            </condition>
                                                    </c:if>
                                                </c:forEach>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${balanceRatesEntry.value}" var="periodRatesEntry">
                                                <c:if test="${periodRatesEntry.key == 0}">
                                                    <condition>
                                                        <currency>
                                                            <code>${currency}</code>
                                                            <name>${phiz:getCurrencySign(currency)}</name>
                                                        </currency>
                                                        <amountOf>
                                                            <fmt:formatNumber value="${balanceRatesEntry.key}" pattern="0.00"/>
                                                        </amountOf>
                                                        <interestRate>
                                                            <fmt:formatNumber value="${periodRatesEntry.value[0].baseRate}" pattern="0.00"/>
                                                        </interestRate>
                                                    </condition>
                                                </c:if>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </conditionsList>

                    <c:if test="${not empty entityTDog.incomingTransactions}">
                        <incomingTransactions><c:out value="${entityTDog.incomingTransactions}"/></incomingTransactions>
                    </c:if>
                    <c:if test="${not empty form.minAdditionalFee}">
                        <minAditionalFee>
                            <c:forEach items="${form.minAdditionalFee}" var="minAdditionalFeeValue">
                                <currencyList>
                                    <currency>
                                        <code>${minAdditionalFeeValue.key}</code>
                                        <name>${phiz:getCurrencySign(minAdditionalFeeValue.key)}</name>
                                    </currency>
                                    <amount>
                                        <fmt:formatNumber value="${minAdditionalFeeValue.value}" pattern="0.00"/>
                                    </amount>
                                </currencyList>
                            </c:forEach>
                        </minAditionalFee>
                    </c:if>

                    <c:if test="${not empty entityTDog.additionalFeePeriod}">
                        <frequencyAdd><c:out value="${entityTDog.additionalFeePeriod}"/></frequencyAdd>
                    </c:if>

                    <c:if test="${not empty entityTDog.debitTransactions}">
                        <debitTransactions><c:out value="${entityTDog.debitTransactions}"/></debitTransactions>
                    </c:if>

                    <c:if test="${not empty entityTDog.frequencyPercent}">
                        <frequencyPercent><c:out value="${entityTDog.frequencyPercent}"/></frequencyPercent>
                    </c:if>

                    <c:if test="${not empty entityTDog.percentOrder}">
                        <percentOrder><c:out value="${entityTDog.percentOrder}"/></percentOrder>
                    </c:if>

                    <c:if test="${not empty entityTDog.incomeOrder}">
                        <incomeOrder><c:out value="${entityTDog.incomeOrder}"/></incomeOrder>
                    </c:if>

                    <c:if test="${not empty entityTDog.renewals}">
                        <renewals><c:out value="${entityTDog.renewals}"/></renewals>
                    </c:if>
                </c:when>
                <c:otherwise>
            ${form.html}
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>

</html:form>