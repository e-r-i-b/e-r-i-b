<%@ page contentType="text/javascript;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="contextName" value="${phiz:loginContextName()}"/>
<c:set var="cardInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/cards/info.do')}"/>
<c:set var="accountInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/accounts/operations.do')}"/>
<c:set var="imaInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/ima/info.do')}"/>
<c:set var="securityAccInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/security/view.do')}"/>
<c:set var="rate1" value="" scope="request"/>
<c:set var="diagramId1" value="" scope="request"/>
<c:set var="products1" value="" scope="request"/>
<c:choose>
     <c:when test="${form.isWidget}">
        <c:set var="showCreditCardAmountId" value="${form.codename}ShowCreditCardAmount"/>
    </c:when>
    <c:otherwise>
        <c:set var="showCreditCardAmountId" value="diagramIdShowCreditCardAmount"/>
    </c:otherwise>
</c:choose>

function getProducts()
{
   /**
    * Объекты данного типа предоставляют возможность хранения и получения
    * курсов валют
    *
    * @returns {{addRateByCode: Function, getRateByCode: Function}}
    * Возвращается объект позволяющий через функции взаимодействовать с private полями объекта Rates:
    *
    * addRateByCode - позволяет добавлять новый курс валюты;
    * getRateByCode - позволяет получать курс валюты по его коду, при этом, если курс по указанному коду
    * не найден будет возвращен 0.
    *
    * @constructor
    */
    var Rates = function()
    {
        var rates       = {};

        return {
            initialize : function()
            {
                rates['RUB'] = { rate: 1 };
            },
            addRateByCode : function(code, value)
            {
                if (code && value)
                {
                    rates[code] = {rate : value};
                }
            },

            getRateByCode : function(code)
            {
                if (code && rates[code] !== undefined)
                {
                    return rates[code].rate;
                }

                return 0;
            }
        };
    };

    var rate               = {};
    var products           = [];
    var errorProducts      = [];
    var ratesByRemoteBuy   = new Rates();
    var ratesByCentralBank = new Rates();
    ratesByRemoteBuy.initialize();
    ratesByCentralBank.initialize();
    <logic:iterate id="rateByCB" name="form" property="currencyRateByCB">
        <c:set var="code" value="${rateByCB.key}"   scope="request"/>
        <c:set var="rate" value="${rateByCB.value}" scope="request"/>

        ratesByCentralBank.addRateByCode('${code}', ${rate.toValue/rate.fromValue});
    </logic:iterate>
    <logic:iterate id="rateByRemote" name="form" property="currencyRateByRemote">
        <c:set var="code" value="${rateByRemote.key}"   scope="request"/>
        <c:set var="rate" value="${rateByRemote.value}" scope="request"/>

        ratesByRemoteBuy.addRateByCode('${code}', ${rate.toValue/rate.fromValue});
    </logic:iterate>

    <%-- Карты --%>
    <c:forEach items="${form.cards}" var="cardLink">
        <c:if test="${cardLink != null}">
            <c:set var="card" value="${cardLink.card}"/>
            <c:set var="isError" value="${card.availableLimit == null}"/>
            <c:set var="cardAmountText" value="${phiz:formatAmount(card.availableLimit)}"/>
            <c:out value="${isError? 'errorProducts': 'products'}"/>.push({
                title: "<bean:write name="cardLink" property="name"/>",
                number: "${phiz:getCutCardNumber(cardLink.number)}",
                type: "card",
                typeText: "${card.cardType.displayDescription}"
                <c:if test="${!isError}">
                    ,money: {
                        total: ${card.availableLimit.decimal},
                        text: "${phiz:changeWhiteSpaces(cardAmountText)}",
                        currencyISO: "${card.availableLimit.currency.code}",
                        inRUB: toRUB(${card.availableLimit.decimal},"${card.availableLimit.currency.code}", 'rate_cb'),
                        <c:choose>
                            <c:when test="${card.cardType == 'debit' or card.overdraftLimit == null}">
                                ownInRUB: toRUB(${card.availableLimit.decimal},"${card.availableLimit.currency.code}", 'rate_cb')
                            </c:when>
                            <c:otherwise>
                                ownInRUB: ownToRUB(${card.availableLimit.decimal}, ${card.overdraftLimit.decimal}, "${card.overdraftLimit.currency.code}", 'rate_cb')
                            </c:otherwise>
                        </c:choose>
                    },
                    onclick:
                        function()
                        {
                            <c:if test="${contextName != 'PhizIA'}">
                                goTo('${cardInfoUrl}?id=${cardLink.id}');
                            </c:if>
                        },
                    getProductAmount:
                        function()
                        {
                            if($('#${showCreditCardAmountId}').is(':checked'))
                                return this.money.inRUB;
                            else
                                return this.money.ownInRUB;
                        }
                </c:if>
            });
        </c:if>
    </c:forEach>
    <%-- Вклады --%>
    <c:forEach items="${form.accounts}" var="accountLink">
        <c:if test="${accountLink != null}">
            <c:set var="account" value="${accountLink.account}"/>
            <c:set var="isError" value="${account.balance == null}"/>
            <c:set var="accountAmountText" value="${phiz:formatAmount(account.balance)}"/>
            <c:out value="${isError? 'errorProducts': 'products'}"/>.push({
                title: "<bean:write name="accountLink" property="name"/>",
                number: "${phiz:getFormattedAccountNumber(account.number)}",
                type: "account"
                <c:if test="${!isError}">
                    ,money: {
                        total: ${account.balance.decimal},
                        text: "${phiz:changeWhiteSpaces(accountAmountText)}",
                        currencyISO: "${account.balance.currency.code}",
                        inRUB:    toRUB(${account.balance.decimal},"${account.balance.currency.code}", 'rate_cb'),
                        ownInRUB: toRUB(${account.balance.decimal},"${account.balance.currency.code}", 'rate_cb')
                    },
                    onclick:
                        function()
                        {
                            <c:if test="${contextName != 'PhizIA'}">
                                goTo('${accountInfoUrl}?id=${accountLink.id}');
                            </c:if>
                        },
                    getProductAmount:
                        function()
                        {
                            if($('#${showCreditCardAmountId}').is(':checked'))
                                return this.money.inRUB;
                            else
                                return this.money.ownInRUB;
                        }
                </c:if>
            });
        </c:if>
    </c:forEach>
    <%-- металлические счета --%>
    <c:forEach items="${form.imAccounts}" var="imAccountLink">
        <c:if test="${imAccountLink != null}">
            <c:set var="imAccount" value="${imAccountLink.imAccount}"/>
            <c:set var="isError" value="${imAccount.balance == null}"/>
            <c:set var="imAccountAmountText" value="${phiz:normalizeCurrencyCode(imAccount.balance.currency.code)} ${phiz:formatAmount(imAccount.balance)}"/>
            <c:out value="${isError? 'errorProducts': 'products'}"/>.push({
                title: "<bean:write name="imAccountLink" property="name"/>",
                number: "${phiz:getFormattedAccountNumber(imAccount.number)}",
                type: "imAccount"
                <c:if test="${!isError}">
                    ,money: {
                        total: ${imAccount.balance.decimal},
                        text: "${phiz:changeWhiteSpaces(imAccountAmountText)}",
                        currencyISO: "${phiz:normalizeCurrencyCode(imAccount.balance.currency.code)}",
                        inRUB:    toRUB(${imAccount.balance.decimal},"${imAccount.balance.currency.code}", 'rate_buy_remote'),
                        ownInRUB: toRUB(${imAccount.balance.decimal},"${imAccount.balance.currency.code}", 'rate_buy_remote')
                    },
                    onclick:
                        function()
                        {
                            <c:if test="${contextName != 'PhizIA'}">
                                goTo('${imaInfoUrl}?id=${imAccountLink.id}');
                            </c:if>
                        },
                    getProductAmount:
                        function()
                        {
                            if($('#${showCreditCardAmountId}').is(':checked'))
                                return this.money.inRUB;
                            else
                                return this.money.ownInRUB;
                        }
                </c:if>
            });
    </c:if>
    </c:forEach>
    <%-- Сберегательные сертификаты--%>
    <c:set var="securityAccounts" value="${form.securityAccounts}"/>
    <c:forEach items="${form.securityAccountLinks}" var="securityAccountLink">
        <c:if test="${securityAccountLink != null}">
            <c:set var="securityAccount" value="${securityAccounts[securityAccountLink]}"/>
            <c:set var="securityAmount" value="${phiz:getSecurityAmount(securityAccount)}"/>
            <c:set var="securityAmountText" value="${phiz:formatAmount(securityAmount)}"/>
            <c:set var="isError" value="${securityAmount == null}"/>
            <c:out value="${isError? 'errorProducts': 'products'}"/>.push({
                title: "<bean:write name="securityAccountLink" property="name"/>",
                number: "${securityAccount.serialNumber}",
                type: "securityAccount"
                <c:if test="${!isError}">
                    ,money: {
                        total: ${securityAmount.decimal},
                        text: "${phiz:changeWhiteSpaces(securityAmountText)}",
                        currencyISO: "${phiz:normalizeCurrencyCode(securityAmount.currency.code)}",
                        inRUB:    toRUB(${securityAmount.decimal},"${securityAmount.currency.code}", 'rate_cb'),
                        ownInRUB: toRUB(${securityAmount.decimal},"${securityAmount.currency.code}", 'rate_cb')
                    },
                    onclick:
                        function()
                        {
                            <c:if test="${contextName != 'PhizIA'}">
                                goTo('${securityAccInfoUrl}?id=${securityAccountLink.id}');
                            </c:if>
                        },
                    getProductAmount:
                        function()
                        {
                            if($('#${showCreditCardAmountId}').is(':checked'))
                                return this.money.inRUB;
                            else
                                return this.money.ownInRUB;
                        }
                </c:if>
            });
    </c:if>
    </c:forEach>

    function getRateForCode(currencyCode, rateType)
    {
        switch (rateType)
        {
            case 'rate_cb':
            {
                return ratesByCentralBank.getRateByCode(currencyCode);
            }

            case 'rate_buy_remote':
            {
                return ratesByRemoteBuy.getRateByCode(currencyCode);
            }
            default:
            {
                return 0;
            }
        }
    }

    function toRUB(amount, currencyCode, rateType)
    {
        return amount * getRateForCode(currencyCode, rateType);
    }

    function ownToRUB(amount, creditLimit, currencyCode, rateType)
    {
        var ownAmount = (amount - creditLimit) * getRateForCode(currencyCode, rateType);
        if(ownAmount < 0)
            ownAmount = 0;
        return ownAmount;
    }

    var array = new Array();
    array[0] = rate;
    array[1] = products;
    array[2] = errorProducts;
    return array;
}