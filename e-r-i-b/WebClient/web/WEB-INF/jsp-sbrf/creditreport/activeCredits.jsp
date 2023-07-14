<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
    credits - список кредитов
    cards - список карт
    isActiveCreditViewBlock - отображать в блочном режиме
    creditsCount - строка с количеством кредитов и карт
--%>
<tiles:importAttribute/>
<script type="text/javascript">
    function selectCredit(creditId)
    {
        var detailUrl = "${phiz:calculateActionURL(pageContext,'/private/credit/report')}";
        window.location = detailUrl + "?creditId=" + creditId;
        return true;
    }
</script>
<div class="credit-history-active">
    <c:choose>
        <c:when test="${isActiveCreditViewBlock}">
            <a href="${phiz:calculateActionURL(pageContext,'private/credit/report?activeCreditViewBlock=false')}" class="credit-history-active__view">Показать списком</a>
        </c:when>
        <c:otherwise>
            <a href="${phiz:calculateActionURL(pageContext,'private/credit/report?activeCreditViewBlock=true')}" class="credit-history-active__view">Показать блочно</a>
        </c:otherwise>
    </c:choose>
    <h2 class="credit-history-active__title">Активные кредиты и кредитные карты</h2>

    <c:choose>
        <c:when test="${isActiveCreditViewBlock}">

            <p class="credit-history-active__current-state">${creditsCount}</p>
            <c:set var="colors" value="${0}"/>
            <div class="credit-history-items">
                <c:forEach items="${credits}" var="credit">
                    <c:set var="colors" value="${colors+1}"/>
                    <c:choose>
                        <c:when test="${colors==1}">
                            <c:set var="color" value="blue"/>
                        </c:when>
                        <c:when test="${colors==2}">
                            <c:set var="color" value="red"/>
                        </c:when>
                        <c:when test="${colors==3}">
                            <c:set var="color" value="green"/>
                        </c:when>
                        <c:when test="${colors==4}">
                            <c:set var="color" value="orange"/>
                            <c:set var="colors" value="${0}"/>
                        </c:when>
                    </c:choose>
                    <tiles:insert page="credit.jsp" flush="false">
                        <tiles:put name="credit" beanName="credit"/>
                        <tiles:put name="isDetail" value="false"/>
                        <tiles:put name="blockColor" value="${color}"/>
                    </tiles:insert>
                </c:forEach>
                <c:forEach items="${cards}" var="card">
                    <c:set var="colors" value="${colors+1}"/>
                    <c:choose>
                        <c:when test="${colors==1}">
                            <c:set var="color" value="blue"/>
                        </c:when>
                        <c:when test="${colors==2}">
                            <c:set var="color" value="red"/>
                        </c:when>
                        <c:when test="${colors==3}">
                            <c:set var="color" value="green"/>
                        </c:when>
                        <c:when test="${colors==4}">
                            <c:set var="color" value="orange"/>
                            <c:set var="colors" value="${0}"/>
                        </c:when>
                    </c:choose>
                    <tiles:insert page="card.jsp" flush="false">
                        <tiles:put name="card" beanName="card"/>
                        <tiles:put name="isDetail" value="false"/>
                        <tiles:put name="blockColor" value="${color}"/>
                    </tiles:insert>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${phiz:size(credits) > 0}">
                <table class="credit-history-items-table credit-history-items-creditor">
                    <thead>
                    <tr>
                        <td class="cred-hist-table-col-first">КРЕДИТОР</td>
                        <td class="cred-hist-table-col-second">КРЕДИТ</td>
                        <td class="cred-hist-table-col-time">СРОК</td>
                        <td class="cred-hist-table-col4">ВСЕГО К ВЫПЛАТЕ</td>
                        <td class="cred-hist-table-col-last tal-right">ПЛАТЕЖ</td>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${credits}" var="credit">
                            <tr>
                                <c:set var="warnClass" value=""/>
                                <c:if test="${credit.arrears.value!=null and credit.arrears.value!=0}">
                                    <c:set var="warnClass" value="cred-hist-table-warn"/>
                                </c:if>
                                <td class="cred-hist-table-col-first ${warnClass}">
                                    <a class="underlined-link" onclick="selectCredit(${credit.id});"><span>${credit.bankName}</span></a>
                                </td>
                                <td class="cred-hist-table-col-second">${phiz:formatDecimalToAmountRound(credit.amount.value, true)}
                                    <span class="rouble">
                                        ${phiz:getCurrencySignForBKI(credit.amount.currency)}
                                    </span>
                                </td>
                                <td class="cred-hist-table-col-time">${credit.duration}</td>
                                <td class="cred-hist-table-col4">${phiz:formatDecimalToAmountRound(credit.balance.value, true)}
                                    <span class="rouble">
                                        ${phiz:getCurrencySignForBKI(credit.balance.currency)}
                                    </span>
                                </td>
                                <c:choose>
                                    <c:when test="${not empty credit.arrears.value and credit.arrears.value != 0}">
                                        <td class="cred-hist-table-col-last tal-right red"><span class="cred-hist-prosrochka">${phiz:formatDecimalToAmountRound(credit.instalment.value, true)}</span>
                                            <span class="rouble">
                                                ${phiz:getCurrencySignForBKI(credit.instalment.currency)}
                                            </span> <br />
                                            <div class="credit-history-items-table-prosrochka">Включая просрочку ${phiz:formatDecimalToAmountRound(credit.arrears.value, true)}
                                                <span class="rouble">
                                                    ${phiz:getCurrencySignForBKI(credit.arrears.currency)}
                                                </span>
                                            </div>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="cred-hist-table-col-last tal-right">${phiz:formatDecimalToAmountRound(credit.instalment.value, true)}
                                            <span class="rouble">
                                                ${phiz:getCurrencySignForBKI(credit.instalment.currency)}
                                            </span><br/>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${phiz:size(cards) > 0}">
                <table class="credit-history-items-table credit-history-items-credit-card">
                    <thead>
                    <tr>
                        <td class="cred-hist-table-col-first">КРЕДИТНАЯ КАРТА</td>
                        <td class="tal-right cred-hist-table2-col2">ЛИМИТ</td>
                        <td class="tal-right cred-hist-table2-col3">ДОЛГ</td>
                        <td class="tal-right cred-hist-table-col-last">МИН. ПЛАТЕЖ</td>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${cards}" var="card">
                            <tr>
                                <c:set var="warnClass" value=""/>
                                <c:if test="${not empty card.arrears.value and card.arrears.value != 0}">
                                    <c:set var="warnClass" value="cred-hist-table-warn"/>
                                </c:if>
                                <td class="cred-hist-table-col-first ${warnClass}"><a onclick="selectCredit(${card.id});" class="underlined-link"><span>${card.bankName}</span></a></td>
                                <td class="tal-right cred-hist-table2-col2">${phiz:formatDecimalToAmountRound(card.creditLimit.value, true)}
                                    <span class="rouble">
                                        ${phiz:getCurrencySignForBKI(card.creditLimit.currency)}
                                    </span>
                                </td>
                                <td class="tal-right cred-hist-table2-col3">${phiz:formatDecimalToAmountRound(card.balance.value, true)}
                                    <span class="rouble">
                                        ${phiz:getCurrencySignForBKI(card.balance.currency)}
                                    </span>
                                </td>
                                <c:choose>
                                    <c:when test="${not empty card.arrears.value and card.arrears.value != 0}">
                                        <td class="cred-hist-table-col-last tal-right red"><span class="cred-hist-prosrochka">${phiz:formatDecimalToAmountRound(card.instalment.value, true)}</span>
                                            <span class="rouble">
                                                ${phiz:getCurrencySignForBKI(card.instalment.currency)}
                                            </span> <br />
                                            <div class="credit-history-items-table-prosrochka">Включая просрочку ${phiz:formatDecimalToAmountRound(card.arrears.value, true)}
                                                <span class="rouble">
                                                    ${phiz:getCurrencySignForBKI(card.arrears.currency)}
                                                </span>
                                            </div>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="cred-hist-table-col-last tal-right">${phiz:formatDecimalToAmountRound(card.instalment.value, true)}
                                            <span class="rouble">
                                                ${phiz:getCurrencySignForBKI(card.instalment.currency)}
                                            </span><br/>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </c:otherwise>
    </c:choose>

</div>
