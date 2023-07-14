<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/async/depositProduct/details">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:set var="depositEntity" value="${form.entity}"/>
    <c:set var="entityTDog" value="${form.entityTDog}"/>

    <c:set var="promoDeposit">
        <c:choose>
            <c:when test="${not empty depositEntity.promoCodeDeposit}">
                <c:out value="${depositEntity.promoCodeDeposit.prior == 1}"/>
            </c:when>
            <c:otherwise><c:out value="false"/></c:otherwise>
        </c:choose>
    </c:set>

    <c:set var="promoSegmentCode">
        <c:if test="${not empty depositEntity.promoCodeDeposit}">
            <c:out value="${depositEntity.promoCodeDeposit.codeS}"/>
        </c:if>
    </c:set>

    <%--Заголовок вкладного продукта--%>
    <h1><c:out value="${form.entity.groupName}"/></h1>
    <c:if test="${promoDeposit}">
        <span class="promoCodeLabel">промо-вклад</span>
    </c:if>
    <br/>

    <%--Описание промо-кода--%>
    <c:if test="${promoDeposit}">
        <div class="premiumDepositProductItem">
            <div class="promoShortDescription"><c:out value="${depositEntity.promoCodeDeposit.nameS}"/></div>
            <div class="clear"></div>
            <div class="promoFullDescription"><c:out value="${depositEntity.promoCodeDeposit.nameF}"/></div>
        </div>
    </c:if>

    <%--Таблица со ставками--%>
    <div class="depositInfoTable">
        <table class='windowShadowTable simpleTable'>

            <tr class="tblInfHeader">
                <th class="titleTable first">Валюта</th>
                <th class="titleTable">Сумма</th>
                <c:choose>
                    <c:when test="${not empty depositEntity.periodList}">
                        <c:forEach items="${depositEntity.periodList}" var="period">
                            <th class="titleTable">
                                ${phiz:preparePeriod(period)}
                            </th>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <th class="titleTable">Процентная ставка</th>
                    </c:otherwise>
                </c:choose>
            </tr>

            <c:forEach items="${depositEntity.depositRates}" var="rateEntry">
                <c:set var="currency" value="${rateEntry.key}"/>

                <c:if test="${not empty currency}">
                    <%--Подсчитать количество строк с балансами (для объединения ячеек)--%>
                    <c:set var="balanceRowSpan" value="1"/>
                    <c:forEach var="t" items="${rateEntry.value}" varStatus="rowEntry">
                        <c:set var="balanceRowSpan" value="${rowEntry.count}"/>
                    </c:forEach>

                    <c:forEach items="${rateEntry.value}" var="balanceRatesEntry"  varStatus="rowNum">
                        <tr <c:if test='${promoDeposit}'> class="fullPromoRates"</c:if>>
                            <%--Валюта--%>
                            <c:if test="${rowNum.count == 1}">
                                <td class="first" rowspan="${balanceRowSpan}">${phiz:getCurrencySign(currency)}</td>
                            </c:if>
                            <%--Сумма--%>
                            <td class="textNobr">
                                от ${phiz:formatBigDecimal(balanceRatesEntry.key)}
                            </td>
                            <c:choose>
                                <c:when test="${not empty depositEntity.periodList}">
                                    <%--Для каждого из возможных периодов должна быть ячейка--%>
                                    <c:forEach items="${depositEntity.periodList}" var="period">
                                        <td nowrap="true">
                                            <c:forEach items="${balanceRatesEntry.value}" var="periodRatesEntry">
                                                <%--
                                                Нельзя просто пройтись по списку ставок.
                                                Надо убедиться, что период ставки совпадает с периодом заголовка.
                                                --%>
                                                <c:if test="${periodRatesEntry.key == period}">
                                                    <span class="<c:if test='${periodRatesEntry.value[0].segment == promoSegmentCode}'>promoRate</c:if>">${phiz:formatBigDecimal(periodRatesEntry.value[0].baseRate)}%</span>
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <td nowrap="true">
                                        <c:forEach items="${balanceRatesEntry.value}" var="periodRatesEntry">
                                            <c:if test="${periodRatesEntry.key == 0}">
                                                ${phiz:formatBigDecimal(periodRatesEntry.value[0].baseRate)}%
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </c:if>
            </c:forEach>
        </table>
    </div>

    <c:if test="${not empty entityTDog}">
        <table class="infoTable">
            <c:if test="${depositEntity.initialFee}">
                <tr>
                    <td>Процентная ставка:</td>
                    <td>
                        <c:out value="${fn:replace(entityTDog.rate,'{PROC}','Определяется в соответствии с таблицей')}"
                               escapeXml="false"/>
                    </td>
                </tr>
            </c:if>
            <c:if test="${not empty entityTDog.incomingTransactions}">
                <tr>
                    <td>Приходные операции по вкладу:</td>
                    <td><c:out value="${entityTDog.incomingTransactions}" escapeXml="false"/></td>
                </tr>
            </c:if>
            <c:if test="${not empty minAdditionalFee}">
                <tr>
                    <td>Минимальный размер дополнительного взноса, вносимого наличными деньгами:</td>
                    <td>
                        <c:forEach items="${minAdditionalFee}" var="minAdditionalFeeValue">
                            ${phiz:formatBigDecimal(minAdditionalFeeValue.value)} ${phiz:getCurrencySign(minAdditionalFeeValue.key)},
                        </c:forEach>
                    </td>
                </tr>
            </c:if>
            <c:if test="${not empty entityTDog.additionalFeePeriod}">
                <tr>
                    <td>Периодичность внесения дополнительных взносов:</td>
                    <td><c:out value="${entityTDog.additionalFeePeriod}" escapeXml="false"/></td>
                </tr>
            </c:if>
            <c:if test="${not empty entityTDog.debitTransactions}">
                <tr>
                    <td>Расходные операции по вкладу:</td>
                    <td><c:out value="${entityTDog.debitTransactions}" escapeXml="false"/></td>
                </tr>
            </c:if>
            <c:if test="${not empty entityTDog.frequencyPercent}">
                <tr>
                    <td>Периодичность выплаты процентов:</td>
                    <td><c:out value="${entityTDog.frequencyPercent}" escapeXml="false"/></td>
                </tr>
            </c:if>
            <c:if test="${not empty entityTDog.percentOrder}">
                <tr>
                    <td>Порядок уплаты процентов:</td>
                    <td><c:out value="${entityTDog.percentOrder}" escapeXml="false"/></td>
                </tr>
            </c:if>
            <c:if test="${not empty entityTDog.incomeOrder}">
                <tr>
                    <td>Порядок начисления дохода при досрочном востребовании вклада:</td>
                    <td><c:out value="${entityTDog.incomeOrder}" escapeXml="false"/></td>
                </tr>
            </c:if>
            <c:if test="${not empty entityTDog.renewals}">
                <tr>
                    <td>Количество пролонгаций Договора:</td>
                    <td><c:out value="${entityTDog.renewals}" escapeXml="false"/></td>
                </tr>
            </c:if>
        </table>
    </c:if>

    <%--Кнопка закрытия окна--%>
    <div class="text-align-center">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.info.close"/>
            <tiles:put name="commandHelpKey" value="button.info.close.help"/>
            <tiles:put name="bundle" value="depositInfoBundle"/>
            <tiles:put name="onclick" value="win.close(this);"/>
            <tiles:put name="viewType" value="buttonGrey"/>
        </tiles:insert>
    </div>

    <script type="text/javascript">
        $(document).ready(function(){
            $('.windowShadowTable tr').each(function()
            {
                $(this).find('span.promoRate').closest('tr').addClass('rowPromo');
            })
        })
    </script>

</html:form>
