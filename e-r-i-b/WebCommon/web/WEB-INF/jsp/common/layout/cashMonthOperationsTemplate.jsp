<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--
Компонент для отображения доходов и расходов за месяц
id - идентификатор блока, обязателен для заполнения, если таких компонентов на странице несколько
cashIncome - доход по наличным операциям за месяц
cashOutcome - расход по наличным операциям за месяц
cashlessIncome - доход по безналичным операциям за месяц
cashlessOutcome - расход по безналичным операциям за месяц
showCreditCard - отображать кредитные карты в выписке
title - месяц год
--%>

<c:choose>
    <c:when test="${cashOutcome != 0.00 || cashIncome != 0.00 || cashlessOutcome != 0.00 || cashlessIncome != 0.00}">
        <c:set var="formatOutgoCash" value="${phiz:getStringInNumberFormat(cashOutcome)}"/>
        <c:set var="formatIncomeCash" value="${phiz:getStringInNumberFormat(cashIncome)}"/>
        <c:set var="formatOutgoCashless" value="${phiz:getStringInNumberFormat(cashlessOutcome)}"/>
        <c:set var="formatIncomeCashless" value="${phiz:getStringInNumberFormat(cashlessIncome)}"/>
        <c:set var="dateString" value="${day} ${month}"/>
        <script type="text/javascript">
            $(document).ready(function(){
                financesUtils.setCashOperVals('${id}', ${cashIncome}, ${cashOutcome}, ${cashlessIncome}, ${cashlessOutcome}, ${maxValue});

                financesUtils.bindEnter('outgoCashLine${id}', '${title}', '-${formatOutgoCash}');
                financesUtils.bindEnter('outgoCashlessLine${id}', '${title}', '-${formatOutgoCashless}');
                financesUtils.bindEnter('incomeCashLine${id}', '${title}', '+${formatIncomeCash}');
                financesUtils.bindEnter('incomeCashlessLine${id}', '${title}', '+${formatIncomeCashless}');

                financesUtils.bindLeave('outgoCashLine${id}');
                financesUtils.bindLeave('outgoCashlessLine${id}');
                financesUtils.bindLeave('incomeCashLine${id}');
                financesUtils.bindLeave('incomeCashlessLine${id}');
            });
        </script>

        <div id="cashMonth${id}" class="cashMonth" onclick="financesUtils.showOperations('Operations${id}', ${functionParams}); return false;">

            <div class="monthName">
                <a href="" >${title}</a>
            </div>

            <div class="leftPart">
                <div class="leftGraphPart">
                    <div class="outgoCashLine relative">
                        <div id="outgoCashLine${id}">
                            <div class="descrText"><nobr>-<span id="outgoCashLineDescr${id}">${outcomeVal}</span> руб.</nobr></div>

                            <div class="outgoCashLineLeft"></div>
                            <div class="lineCenter"></div>
                        </div>
                    </div>

                    <div class="clear"></div>

                    <div id="outgoCashlessLine${id}" class="outgoCashlessLine relative">
                        <div class="descrText"><nobr>-<span id="outgoCashlessLineDescr${id}">${outcomeVal}</span> руб.</nobr></div>

                        <div class="outgoCashlessLineLeft"></div>
                        <div class="lineCenter"></div>
                    </div>
                </div>
            </div>

            <div class="partSeparator"></div>

            <div class="rightPart">
                <div class="rightGraphPart">
                    <div class="incomeCashLine float relative">
                        <div id="incomeCashLine${id}">
                            <div class="descrText"><nobr>+<span id="incomeCashLineDescr${id}">${incomeVal}</span> руб.</nobr></div>

                            <div class="incomeCashLineRight"></div>
                            <div class="lineCenter"></div>
                        </div>
                    </div>

                    <div class="clear"></div>

                    <div id="incomeCashlessLine${id}" class="incomeCashlessLine float relative">
                        <div class="descrText"><nobr>+<span id="incomeCashlessLineDescr${id}">${incomeVal}</span> руб.</nobr></div>

                        <div class="incomeCashlessLineRight"></div>
                        <div class="lineCenter"></div>
                    </div>
                 </div>
            </div>

            <div class="clear"></div>
        </div>

        <div id="Operations${id}" class="operationsListBlock" style="display:none;">
            <input type="hidden" id="incomeOperations${id}" value=""/>
            <input type="hidden" id="cashOperations${id}" value=""/>

            <div class="filter triggerFilter">
                <div class="greenSelector active" id="cash_${id}" onclick="financesUtils.changeSearchTypeValue('${id}', 'cash', '', ${functionParams}, getElementValue('filter(showOtherAccounts)')); return false;">
                   <span>Все операции</span>
                </div>
                <div class="greenSelector transparent" id="cash_1${id}" onclick="financesUtils.changeSearchTypeValue('${id}', 'cash', '1', ${functionParams}, getElementValue('filter(showOtherAccounts)')); return false;">
                   <span>Операции с наличными</span>
                </div>
                <div class="greenSelector transparent" id="cash_0${id}" onclick="financesUtils.changeSearchTypeValue('${id}', 'cash', '0', ${functionParams}, getElementValue('filter(showOtherAccounts)')); return false;">
                   <span>Безналичные операции</span>
                </div>
            </div>

            <div id="listOperations${id}" class="operationsList">
            </div>

            <div class="align-center marginBottom5"><a onclick="closeOperations('Operations${id}'); return false;" href="#">свернуть</a></div>
        </div>
    </c:when>
    <c:otherwise>
        <div id="cashMonth${id}" class="cashMonth noOperations">
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