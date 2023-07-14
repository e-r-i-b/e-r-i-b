<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<%--
Компонент для отображения доходов и расходов за месяц
id - идентификатор блока, обязателен для заполнения, если таких компонентов на странице несколько
incomeVal - доход за месяц
outcomeVal - расход за месяц
date - дата
showCreditCard - отображать кредитные карты в выписке
--%>

<c:choose>
    <c:when test="${outcomeVal != 0.00 || incomeVal != 0.00}">
        <c:set var="formatOutgo" value="${phiz:formatDecimalToAmount(outcomeVal, 0)}"/>
        <c:set var="formatIncome" value="${phiz:formatDecimalToAmount(incomeVal, 0)}"/>
        <c:set var="formatBalance" value="${phiz:formatDecimalToAmount(incomeVal-outcomeVal, 0)}"/>
        <script type="text/javascript">
            $(document).ready(function(){
                financesUtils.setIncomeOutgoVals('${id}', ${incomeVal}, ${outcomeVal}, ${maxValue});

                financesUtils.bindEnter('outgoLine${id}', '${title}', '-${formatOutgo}');
                financesUtils.bindEnter('incomeLine${id}', '${title}', '+${formatIncome}');
                financesUtils.bindEnter('negativeBalanceLine${id}', '${title}', '${formatBalance}');
                financesUtils.bindEnter('positiveBalanceLine${id}', '${title}', '+${formatBalance}');

                financesUtils.bindLeave('outgoLine${id}');
                financesUtils.bindLeave('incomeLine${id}');
                financesUtils.bindLeave('negativeBalanceLine${id}');
                financesUtils.bindLeave('positiveBalanceLine${id}');
            });
        </script>

        <div id="incomeOutgoMonth${id}" class="incomeOutgoMonth" onclick="financesUtils.showOperations('Operations${id}', ${functionParams}, getElementValue('filter(showOtherAccounts)')); return false;">

            <div class="monthName">
                <a href="">${title}</a>
            </div>

            <div class="leftPart">
                <div class="leftGraphPart">
                    <div id="outgoLine${id}" class="outgoLine relative">
                        <div class="descrText"><nobr>-<span id="outgoLineDescr${id}">${outcomeVal}</span> руб.</nobr></div>

                        <div class="outgoLineLeft"></div>
                        <div class="lineCenter"></div>
                    </div>

                    <div class="clear"></div>

                    <div id="negativeBalanceLine${id}" class="negativeBalanceLine">
                        <div class="balanceLineLeft"></div>
                        <div class="lineCenter"></div>
                    </div>
                </div>
            </div>

            <div class="partSeparator"></div>

            <div class="rightPart">
                <div class="rightGraphPart">
                    <div id="incomeLine${id}" class="incomeLine float relative">
                        <div class="descrText"><nobr>+<span id="incomeLineDescr${id}">${incomeVal}</span> руб.</nobr></div>

                        <div class="incomeLineRight"></div>
                        <div class="lineCenter"></div>
                    </div>

                    <div class="clear"></div>

                    <div id="positiveBalanceLine${id}" class="positiveBalanceLine">
                        <div class="balanceLineRight"></div>
                        <div class="lineCenter"></div>
                    </div>
                 </div>
            </div>

            <div class="clear"></div>

            <div id="balanceDescr${id}" class="balanceDescr">
                <nobr>
                    <span id="positiveBalanceLineDescr${id}"></span>
                    <span id="negativeBalanceLineDescr${id}"></span>
                    &nbsp;руб.
                </nobr>
            </div>
        </div>

        <div id="Operations${id}" class="operationsListBlock" style="display:none;">
            <input type="hidden" id="incomeOperations${id}" value=""/>
            <input type="hidden" id="cashOperations${id}" value=""/>

            <div class="filter triggerFilter">
                <div class="greenSelector active" id="income_${id}" onclick="financesUtils.changeSearchTypeValue('${id}', 'income', '', ${functionParams}, getElementValue('filter(showOtherAccounts)')); return false;">
                   <span>Все операции</span>
                </div>
                <div class="greenSelector transparent" id="income_1${id}" onclick="financesUtils.changeSearchTypeValue('${id}', 'income', '1', ${functionParams}, getElementValue('filter(showOtherAccounts)')); return false;">
                   <span>Поступления</span>
                </div>
                <div class="greenSelector transparent" id="income_0${id}" onclick="financesUtils.changeSearchTypeValue('${id}', 'income', '0', ${functionParams}, getElementValue('filter(showOtherAccounts)')); return false;">
                   <span>Списания</span>
                </div>
            </div>

            <div id="listOperations${id}" class="operationsList">
            </div>

            <div class="align-center marginBottom5"><a onclick="closeOperations('Operations${id}'); return false;" href="#">свернуть</a></div>
        </div>
    </c:when>
    <c:otherwise>
        <div id="incomeOutgoMonth${id}" class="incomeOutgoMonth noOperations">
            <div class="monthName">
                ${title}
            </div>
            <div class="emptyText">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="greenBold"/>
                    <tiles:put name="data">
                        <span class="text-dark-gray normal relative">
                            <bean:message bundle="financesBundle" key="message.finance.operations.emptyMessage"/>
                        </span>
                    </tiles:put>
                </tiles:insert>
            </div>
        </div>
    </c:otherwise>
</c:choose>
