<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/cards/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="cardLink" value="${form.cardLink}" scope="request"/>
    <c:set var="card"    value="${cardLink.card}"/>
    <c:set var="cardDetail" value="${card}" scope="request"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <detail>
                <c:if test="${not empty card and card.cardType=='credit'}">
                    <creditType>
                        <c:if test="${not empty card.overdraftLimit}">
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="limit" />
                                <tiles:put name="money" beanName="card" beanProperty="overdraftLimit"/>
                            </tiles:insert>
                        </c:if>
                        <c:if test="${not empty card.overdraftOwnSum}">
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="ownSum" />
                                <tiles:put name="money" beanName="card" beanProperty="overdraftOwnSum"/>
                            </tiles:insert>
                        </c:if>
                        <c:if test="${not empty card.overdraftMinimalPayment}">
                            <tiles:insert definition="mobileMoneyType" flush="false">
                                <tiles:put name="name" value="minPayment" />
                                <tiles:put name="money" beanName="card" beanProperty="overdraftMinimalPayment"/>
                            </tiles:insert>
                        </c:if>

                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="minPaymentDate"/>
                            <tiles:put name="calendar" beanName="card" beanProperty="overdraftMinimalPaymentDate"/>
                        </tiles:insert>
                    </creditType>
                </c:if>
                <c:set var="cardClient" value="${cardLink.cardClient}"/>
                <holderName>${phiz:getFormattedPersonName(cardClient.firstName,cardClient.surName,cardClient.patrName)}</holderName>
                <c:if test="${not empty cardDetail}">
                    <c:if test="${not empty cardDetail.availableCashLimit}">
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="availableCashLimit"/>
                            <tiles:put name="money" beanName="cardDetail" beanProperty="availableCashLimit"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty cardDetail.purchaseLimit}">
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="purchaseLimit"/>
                            <tiles:put name="money" beanName="cardDetail" beanProperty="purchaseLimit"/>
                        </tiles:insert>
                    </c:if>
                </c:if>
                <officeName><c:out value="${card.office.name}"/></officeName>
                <c:if test="${!empty cardLink.cardAccount}">
                    <accountNumber>${cardLink.cardAccount.number}</accountNumber>
                </c:if>
                <c:if test="${card.virtual}">
                    <virtualCard>
                        <cardNumber>${card.number}</cardNumber>
                        <expireDate>${card.displayedExpireDate}</expireDate>
                    </virtualCard>
                </c:if>
                <name><c:out value="${phiz:getCardUserName(cardLink)}"/></name>
                <c:if test="${not empty cardLink.additionalCardType}">
                    <additionalCardType>${cardLink.additionalCardType.value}</additionalCardType>
                </c:if>
                <c:if test="${not empty form.mainCardId}">
                    <mainCardId>${form.mainCardId}</mainCardId>
                </c:if>
                <c:if test="${not empty cardLink.cardPrimaryAccount}">
                    <cardAccount>${cardLink.cardPrimaryAccount}</cardAccount>
                </c:if>
                <c:set var="moneyBoxesExist" value="${not empty form.moneyBoxesTargets}"/>
                <c:set var="p2pSubscriptionsExist" value="${not empty form.p2pSubscriptions}"/>
                <c:if test="${phiz:getApiVersionNumber().solid >= 910 && phiz:impliesService('MoneyBoxManagement') && (moneyBoxesExist || p2pSubscriptionsExist)}">
                    <boundPayments>
                        <c:if test="${moneyBoxesExist}">
                            <moneyBoxes>
                                <c:forEach var="boundPaymentList" items="${form.moneyBoxesTargets}">
                                    <c:forEach var="boundPayment" items="${boundPaymentList.value}">
                                        <c:set var="item" value="${boundPayment.value}"/>
                                        <box>
                                            <id>${item.number}</id>
                                            <sumType>${item.sumType}</sumType>
                                            <c:if test="${item.sumType == 'FIXED_SUMMA'}">
                                                <amount>${phiz:formatBigDecimal(item.amount.decimal)}</amount>
                                            </c:if>
                                            <c:if test="${not empty item.percent}">
                                                <percent>${item.percent}</percent>
                                            </c:if>
                                        </box>
                                    </c:forEach>
                                </c:forEach>
                            </moneyBoxes>
                        </c:if>
                        <c:if test="${p2pSubscriptionsExist}">
                            <p2pSubscriptions>
                                <c:forEach var="p2pSubscription" items="${form.p2pSubscriptions}">
                                    <id>${p2pSubscription.autoSubscriptionInfo.number}</id>
                                </c:forEach>
                            </p2pSubscriptions>
                        </c:if>
                    </boundPayments>
                </c:if>
            </detail>
        </tiles:put>
    </tiles:insert>
</html:form>
