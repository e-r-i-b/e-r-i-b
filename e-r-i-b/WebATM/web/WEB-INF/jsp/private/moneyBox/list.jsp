<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html:form action="/private/moneyboxes/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <sl:collection id="moneyBox" property="data" model="xml-list" title="moneyBoxes">
                <c:set var="payment" value="${moneyBox.value}"/>
                <c:set var="isClaim" value="${phiz:isInstance(payment, 'com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment')}"/>
                <c:set var="typeSum" value="${payment.sumType}"/>
                <c:set var="bundle" value="moneyboxBundle"/>
                <c:set var="period" value="${payment.executionEventType}"/>
                <c:set var="cardName" value="${phiz:getCardUserName(phiz:getCardLink(payment.cardNumber))}"/>
                <c:set var="cardNumber" value="${payment.cardNumber}"/>
                <c:set var="percent" value="${payment.percent}"/>
                <c:choose>
                    <c:when test="${isClaim}">
                        <c:set var="maxSumWrite" value="${payment.amount}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="maxSumWrite" value="${moneyBox.autoSubscriptionInfo.maxSumWritePerMonth}"/>
                    </c:otherwise>
                </c:choose>
                <c:set var="amountValue" value="${typeSum == 'FIXED_SUMMA' ? payment.amount : maxSumWrite}"/>
                <%-- краткое описание копилки --%>
                <c:set var="description">
                    <bean:message bundle="${bundle}" key="template.description.status.active.account"/>
                    <c:choose>
                        <c:when test="${typeSum == 'FIXED_SUMMA'}">
                            <c:set var="longOfferPayDay" value="${payment.longOfferPayDay}"/>
                            <fmt:formatNumber value="${amountValue.decimal}" pattern="0.00"/>
                            <c:if test="${amountValue!=null}">
                                <c:out value="${amountValue.currency.code}"/>
                            </c:if>
                            <bean:message bundle="${bundle}" key="template.description.period.${period}"/>
                            <c:choose>
                                <c:when test="${period == 'ONCE_IN_WEEK'}">
                                    <bean:message bundle="${bundle}" key="template.description.period.dayOfWeek.by"/> ${phiz:dayNumberToString(longOfferPayDay.weekDay)}<bean:message bundle="${bundle}" key="template.description.typeSum.FIXED_SUMMA"/>
                                </c:when>
                                <c:when test="${period == 'ONCE_IN_MONTH'}">
                                    <c:out value="${longOfferPayDay.day}"/><bean:message bundle="${bundle}" key="template.description.period.dayOfMonth"/><bean:message bundle="${bundle}" key="template.description.typeSum.FIXED_SUMMA"/>
                                </c:when>
                                <c:when test="${period == 'ONCE_IN_QUARTER'}">
                                    <c:out value="${longOfferPayDay.day}"/><bean:message bundle="${bundle}" key="template.description.period.dayOfMonth"/>
                                    <c:out value="${longOfferPayDay.monthInQuarter}"/><bean:message bundle="${bundle}" key="template.description.period.monthOfQuarter"/><bean:message bundle="${bundle}" key="template.description.typeSum.FIXED_SUMMA"/>
                                </c:when>
                                <c:when test="${period == 'ONCE_IN_YEAR'}">
                                    <c:out value="${longOfferPayDay.day}"/> ${phiz:monthNumberToString(longOfferPayDay.monthInYear)}<bean:message bundle="${bundle}" key="template.description.typeSum.FIXED_SUMMA"/>
                                </c:when>
                            </c:choose>
                            ${cardName} <c:out value="${phiz:getCutCardNumber(cardNumber)}"/>
                        </c:when>
                        <c:when test="${typeSum == 'PERCENT_BY_ANY_RECEIPT' || typeSum == 'PERCENT_BY_DEBIT'}">
                            ${percent}%
                            <bean:message bundle="${bundle}" key="template.description.typeSum.${typeSum}"/>
                            <c:choose>
                                <c:when test="${not empty amountValue}">
                                    ${cardName} <c:out value="${phiz:getCutCardNumber(cardNumber)}"/> <bean:message bundle="${bundle}" key="template.description.maxSum"/>
                                    <fmt:formatNumber value="${amountValue.decimal}" pattern="0.00"/>
                                    <c:out value="${amountValue.currency.code}"/>
                                </c:when>
                                <c:otherwise>
                                    ${cardName} <c:out value="${phiz:getCutCardNumber(cardNumber)}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                    </c:choose>
                </c:set>
                <%-- /краткое описание копилки --%>
                <sl:collectionItem title="moneyBox">
                    <id>
                        <c:choose>
                            <c:when test="${isClaim}">
                                <c:out value="${payment.id}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${moneyBox.number}"/>
                            </c:otherwise>
                        </c:choose>
                    </id>

                    <type>
                        <c:choose>
                            <c:when test="${isClaim}">
                                claim
                            </c:when>
                            <c:otherwise>
                                subscription
                            </c:otherwise>
                        </c:choose>
                    </type>

                    <name><bean:write name="moneyBox" property="name" ignore="true"/></name>

                    <c:if test="${not empty moneyBox.executionEventType}">
                        <executionEventDescription><c:out value="${description}"/></executionEventDescription>
                    </c:if>

                    <c:if test="${not empty payment.executionEventType}">
                        <executionEventType><bean:write name="payment" property="executionEventType" ignore="true"/></executionEventType>
                    </c:if>

                    <c:if test="${not empty amountValue}">
                        <tiles:insert definition="atmMoneyType" flush="false">
                            <tiles:put name="name" value="amount"/>
                            <tiles:put name="money" beanName="amountValue"/>
                        </tiles:insert>
                    </c:if>
                    <status>
                        <c:choose>
                            <c:when test="${isClaim}">
                                <c:out value="New"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${moneyBox.autoPayStatusType}"/>
                            </c:otherwise>
                        </c:choose>
                    </status>
               </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>