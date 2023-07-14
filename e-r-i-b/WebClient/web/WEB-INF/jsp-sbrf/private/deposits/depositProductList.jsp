<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:if test="${not empty form.depositProductEntities}">

    <script type="text/javascript">
        function selectRow(row)
        {
            row.getElementsByTagName('input')[0].checked = true;
        }
    </script>

    <div class="depositTariff">
        <c:forEach items="${form.depositProductEntities}" var="depositEntity">

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

            <c:set var="depositProductClass">
                <c:choose>
                    <c:when test="${promoDeposit}"><c:out  value="premiumDepositProductItem"/></c:when>
                    <c:otherwise><c:out value="depositProductItem"/></c:otherwise>
                </c:choose>
            </c:set>

            <div class="${depositProductClass}">
                <div class="depositProductTitle" onclick="selectRow(this);">
                    <input name="depositProductId" type="radio" value="${depositEntity.depositType}:${depositEntity.groupCode}:${promoSegmentCode}"/>
                    <h2>${depositEntity.groupName}</h2>
                    <c:if test="${promoDeposit}">
                        <span class="promoCodeLabel">промо-вклад</span>
                    </c:if>
                </div>

                <table class="depositProductInfo">
                    <tr>
                        <th class="borderBottom newProductEmptyCell"></th>
                        <th class="borderBottom clientMinFee">
                            <c:choose>
                                <c:when test="${depositEntity.withMinimumBalance}">
                                    Ќеснижаемый остаток
                                </c:when>
                                <c:otherwise>
                                    ћинимальный взнос
                                </c:otherwise>
                            </c:choose>
                        </th>
                        <th class="borderBottom clientDepositCurrency">¬алюта</th>
                        <th class="borderBottom clientDepositRate">—тавка, %</th>
                        <th class="borderBottom clientDepositTerm">—рок</th>
                        <th class="borderBottom clientOperationsAvail">ѕриходные/–асходные операции</th>
                    </tr>
                    <c:forEach items="${depositEntity.depositDescriptions}" var="row" varStatus="rowNum">
                        <tr>
                            <td <c:if test="${row.promo}">class="rowPromo"</c:if>></td>
                            <td <c:if test="${row.promo}">class="rowPromo"</c:if>>
                                ќт <c:out value="${phiz:formatBigDecimal(row.sumBegin)}"/>
                            </td>
                            <td <c:if test="${row.promo}">class="rowPromo"</c:if>>
                                <c:out value="${phiz:getISOCodeExceptingRubles(row.currency)}"/>
                            </td>
                            <td <c:if test="${row.promo}">class="rowPromo"</c:if>>
                                <c:set var="rateClass">
                                    <c:choose>
                                        <c:when test="${row.promo}"><c:out  value="bold"/></c:when>
                                        <c:otherwise><c:out value=""/></c:otherwise>
                                    </c:choose>
                                </c:set>
                                <div class="${rateClass}">
                                    <c:choose>
                                        <c:when test="${row.minRate != row.maxRate}">
                                            <c:out value="${phiz:formatBigDecimal(row.minRate)}"/>&nbsp;...&nbsp;<c:out value="${phiz:formatBigDecimal(row.maxRate)}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${phiz:formatBigDecimal(row.minRate)}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </td>
                            <c:if test="${rowNum.count == 1}">
                                <td rowspan="10">
                                    ${phiz:preparePeriods(depositEntity.periodList)}
                                </td>
                            </c:if>
                            <c:if test="${rowNum.count == 1}">
                                <td rowspan="10">
                                    <c:choose>
                                        <c:when test="${depositEntity.creditOperations}">
                                            наличными деньгами, безналичным путем
                                        </c:when>
                                        <c:otherwise>
                                            не совершаютс€
                                        </c:otherwise>
                                    </c:choose>
                                    &nbsp;/&nbsp;
                                    <c:choose>
                                        <c:when test="${depositEntity.debitOperations}">
                                            <c:set var="debitOperationsCode" value="${depositEntity.debitOperationsCode}"/>
                                            <c:choose>
                                                <c:when test="${debitOperationsCode == 0}">
                                                    не совершаютс€
                                                </c:when>
                                                <c:when test="${debitOperationsCode == 1}">
                                                    предусмотрено, в пределах причисленных процентов
                                                </c:when>
                                                <c:when test="${debitOperationsCode == 2}">
                                                    предусмотрено, в пределах неснижаемого остатка
                                                </c:when>
                                                <c:when test="${debitOperationsCode == 3}">
                                                    предусмотрено, в пределах остатка собственных средств
                                                </c:when>
                                                <c:when test="${debitOperationsCode == 4}">
                                                    предусмотрено, до нулевого остатка
                                                </c:when>
                                                <c:when test="${debitOperationsCode == 5}">
                                                    предусмотрено, в пределах максимального из двух значений: неснижаемый остаток и сумма причисленных процентов
                                                </c:when>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${not depositEntity.interestOperations}">
                                                    не совершаютс€
                                                </c:when>
                                                <c:when test="${depositEntity.interestOperations}">
                                                    предусмотрено, в пределах причисленных процентов
                                                </c:when>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>

                <c:set var="$promoSegment">
                    <c:choose>
                        <c:when test="${not empty promoSegmentCode}">
                            <c:out value=", ${promoSegmentCode}"/>
                        </c:when>
                    </c:choose>
                </c:set>

                <span class="depositProductInfoLink link blueGrayLinkDotted"
                      onclick="openDepositDetailInfo(${depositEntity.depositType}, ${depositEntity.groupCode}${$promoSegment});return false;">
                    ѕодробнее
                </span>

            </div>
        </c:forEach>
    </div>

</c:if>

