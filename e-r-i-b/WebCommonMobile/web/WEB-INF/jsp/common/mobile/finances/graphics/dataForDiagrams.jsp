<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp"%>

<html:form action="/private/graphics/finance">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="cardLinks" value="${form.cards}"/>
    <c:set var="accountLinks" value="${form.accounts}"/>
    <c:set var="imAccountLinks" value="${form.imAccounts}"/>
    <%--Мапа с курсами валют для ТБ=99 учитывая тарифный план клиента--%>
    <c:set var="currencyRateByCB"     value="${form.currencyRateByCB}"/>
    <c:set var="currencyRateByRemote" value="${form.currencyRateByRemote}"/>

    <%--Показывать ли копейки--%>
    <c:set var="roundDecimal" value="${!form.showKopeck}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <%--Карты--%>
            <c:if test="${! empty cardLinks}">
                <cards>
                <c:forEach var="cardLink" items="${cardLinks}">
                    <c:set var="card" value="${cardLink.card}"/>
                    <%--карта не безлимитная и это не доп.карта клиента к своей же карте--%>
                    <c:if test="${card.availableLimit != null and card.additionalCardType != 'CLIENTTOCLIENT'}">
                    <card>
                        <id>${cardLink.id}</id>
                        <balance>
                            <c:choose>
                                <%--если карта дебетовая или кредитная, но нет лимита овердрафта--%>
                                <c:when test="${card.cardType == 'debit' and card.overdraftLimit == null}">
                                    <%--Курс относительно РУБ--%>
                                    <c:set var="currencyRate" value="${currencyRateByCB[card.availableLimit.currency.code]}"/>
                                    <c:set var="rate" value="1"/>
                                    <c:if test="${currencyRate != null}">
                                        <c:set var="toValue" value="${currencyRate.toValue}"/>
                                        <c:set var="fromValue" value="${currencyRate.fromValue}"/>
                                        <c:set var="rate" value="${toValue/fromValue}"/>
                                    </c:if>
                                    <c:set var="balance" value="${card.availableLimit.decimal * rate}"/>
                                    ${phiz:formatDecimalToAmountRound(balance, roundDecimal)}
                                </c:when>
                                <c:otherwise>
                                    <%--Курс относительно РУБ--%>
                                    <c:set var="currencyRate" value="${currencyRateByCB[card.overdraftLimit.currency.code]}"/>
                                    <c:set var="rate" value="1"/>
                                    <c:if test="${currencyRate != null}">
                                        <c:set var="toValue" value="${currencyRate.toValue}"/>
                                        <c:set var="fromValue" value="${currencyRate.fromValue}"/>
                                        <c:set var="rate" value="${toValue/fromValue}"/>
                                    </c:if>
                                    <%--Для кредитных карт и карт с разрешенным овердрафтом отображается та сумма, которую выбрало мобильное приложение--%>
                                    <%--сумма с учетом кредитных средств--%>
                                    <c:set var="balanceWithOverdraftLimit" value="${card.availableLimit.decimal * rate}"/>
                                    <c:if test="${balanceWithOverdraftLimit < 0}">
                                        <c:set var="balanceWithOverdraftLimit" value="0"/>
                                    </c:if>
                                    <%--сумма без учета кредитных средств--%>
                                    <c:set var="balanceWithoutOverdraftLimit" value="${(card.availableLimit.decimal - card.overdraftLimit.decimal) * rate}"/>
                                    <c:if test="${balanceWithoutOverdraftLimit < 0}">
                                        <c:set var="balanceWithoutOverdraftLimit" value="0"/>
                                    </c:if>

                                    <c:choose>
                                        <c:when test="${form.showWithOverdraft}">
                                            ${phiz:formatDecimalToAmountRound(balanceWithOverdraftLimit, roundDecimal)}
                                        </c:when>
                                        <c:otherwise>
                                            ${phiz:formatDecimalToAmountRound(balanceWithoutOverdraftLimit, roundDecimal)}
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </balance>
                    </card>
                    </c:if>
                </c:forEach>
                </cards>
            </c:if>

            <%--Вклады--%>
            <c:if test="${! empty accountLinks}">
                <accounts>
                <c:forEach var="accountLink" items="${accountLinks}">
                    <c:set var="account" value="${accountLink.account}"/>
                    <account>
                        <id>${accountLink.id}</id>
                        <balance><%--сумма остатка--%>
                            <c:choose>
                                <c:when test="${account.balance != null}">
                                    <c:set var="currencyRate" value="${currencyRateByCB[account.balance.currency.code]}"/>
                                    <c:set var="rate" value="1"/>
                                    <c:if test="${currencyRate != null}">
                                        <c:set var="toValue" value="${currencyRate.toValue}"/>
                                        <c:set var="fromValue" value="${currencyRate.fromValue}"/>
                                        <c:set var="rate" value="${toValue/fromValue}"/>
                                    </c:if>
                                    <c:set var="balance" value="${account.balance.decimal * rate}"/>
                                    ${phiz:formatDecimalToAmountRound(balance, roundDecimal)}
                                </c:when>
                                <c:otherwise>0</c:otherwise>
                            </c:choose>
                        </balance>
                        <%--Максимальная сумма снятия--%>
                        <c:if test="${account.maxSumWrite != null}">
                            <c:set var="currencyRate" value="${currencyRateByCB[account.maxSumWrite.currency.code]}"/>
                            <c:set var="rate" value="1"/>
                            <c:if test="${currencyRate != null}">
                                <c:set var="toValue" value="${currencyRate.toValue}"/>
                                <c:set var="fromValue" value="${currencyRate.fromValue}"/>
                                <c:set var="rate" value="${toValue/fromValue}"/>
                            </c:if>
                            <c:set var="maxSumWrite" value="${account.maxSumWrite.decimal * rate}"/>
                        <maxSumWrite>
                            ${phiz:formatDecimalToAmountRound(maxSumWrite, roundDecimal)}
                        </maxSumWrite>
                        </c:if>
                    </account>
                </c:forEach>
                </accounts>
            </c:if>

            <%--Обезличенные металлические счета--%>
            <c:if test="${! empty imAccountLinks}">
                <imaccounts>
                <c:forEach items="${imAccountLinks}" var="imAccountLink">
                    <c:set var="imAccount" value="${imAccountLink.imAccount}"/>
                    <imaccount>
                        <id>${imAccountLink.id}</id>
                        <balance>
                            <c:choose>
                                <c:when test="${imAccount.balance != null}">
                                    <c:set var="currencyRate" value="${currencyRateByRemote[imAccount.balance.currency.code]}"/>
                                    <c:set var="rate" value="1"/>
                                    <c:if test="${currencyRate != null}">
                                        <c:set var="toValue" value="${currencyRate.toValue}"/>
                                        <c:set var="fromValue" value="${currencyRate.fromValue}"/>
                                        <c:set var="rate" value="${toValue/fromValue}"/>
                                    </c:if>
                                    <c:set var="balance" value="${imAccount.balance.decimal * rate}"/>
                                    ${phiz:formatDecimalToAmountRound(balance, roundDecimal)}
                                </c:when>
                                <c:otherwise>0</c:otherwise>
                            </c:choose>
                        </balance>
                    </imaccount>
                </c:forEach>
                </imaccounts>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>