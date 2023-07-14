<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/finances/cashOperationsGraph" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="financePlot">

        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">
                    <c:set var="selectedTab" value="operations"/>
                    <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>

                    <tiles:insert definition="financeContainer" flush="false">
                        <tiles:put name="title" value="История доходов и расходов"/>
                        <tiles:put name="showFavourite" value="false"/>
                        <tiles:put name="infoText">
                            На данной странице Вы можете посмотреть сумму оборотных средств, израсходованных
                            или полученных наличным и безналичным способом. Для Вашего удобства расчета по картам,
                            открытым в валюте отличной от рублей, суммы переведены в рубли, по курсу ЦБ на день операции.
                            В диаграммах учтены операции только по картам.
                        </tiles:put>
                        <tiles:put name="data">

                            <div class="filter triggerFilter">
                                <c:choose>
                                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                                        <phiz:link url="${phiz:getWebAPIUrl('operations')}">
                                            <div class="greenSelector transparent">
                                                <span>Поступления и списания</span>
                                            </div>
                                        </phiz:link>
                                    </c:when>
                                    <c:otherwise>
                                        <html:link action="/private/finances/operations">
                                            <div class="greenSelector transparent">
                                                <span>Поступления и списания</span>
                                            </div>
                                        </html:link>
                                    </c:otherwise>
                                </c:choose>
                                <div class="greenSelector">
                                    <span>Наличные и безналичные</span>
                                </div>
                            </div>

                            <%--Фильтр--%>
                            <tiles:insert definition="filterMonth" flush="false">
                                <tiles:put name="name" value="Date"/>
                                <tiles:put name="title" value="операции"/>
                                <tiles:put name="buttonKey" value="button.filter"/>
                                <tiles:put name="buttonBundle" value="newsBundle"/>
                                <tiles:put name="validationFunction" value="checkFilterDates();"/>
                            </tiles:insert>

                            <div class="filterCheckboxes">
                                <html:checkbox property="filter(showCreditCards)" onclick="findCommandButton('button.filter').click('', false);"/>операции по кредитным картам<br/>
                                <html:checkbox property="filter(showCash)" onclick="findCommandButton('button.filter').click('', false);"/>операции с наличными<br/>
                                <html:checkbox property="filter(showOtherAccounts)" onclick="findCommandButton('button.filter').click('', false);"/>операции по дополнительным картам к чужим счетам<br/>
                            </div>

                            <div class="incomeOutcomeBlock">
                                <div class="sumPart">
                                    <div class="incomeOutcomeSumDescr">Поступления:</div>
                                    <div class="incomeOutcomeSum">${phiz:getStringInNumberFormat(form.income)} руб.</div>
                                </div>
                                <div class="sumPart">
                                    <div class="incomeOutcomeSumDescr">&nbsp;</div>
                                    <div class="incomeOutcomeSum"> - </div>
                                </div>
                                <div class="sumPart">
                                    <div class="incomeOutcomeSumDescr">Списания:</div>
                                    <div class="incomeOutcomeSum">${phiz:getStringInNumberFormat(form.outcome)} руб.</div>
                                </div>
                                <div class="sumPart">
                                    <div class="incomeOutcomeSumDescr">&nbsp;</div>
                                    <div class="incomeOutcomeSum"> = </div>
                                </div>
                                <div class="sumPart">
                                    <div class="incomeOutcomeSumDescr">Итого по операциям:</div>
                                    <div class="incomeOutcomeSum">${phiz:getStringInNumberFormat(form.income - form.outcome)} руб.</div>
                                </div>
                                <div class="clear"></div>
                            </div>

                            <div class="graphLegend">
                                <img src="${commonImagePath}/legend_green.gif"> Операции с наличными
                                <img src="${commonImagePath}/legend_blue.gif"> Безналичные операции
                            </div>

                            <c:set var="maxVal" value="${form.maxVal}"/>
                            <tiles:insert definition="scaleTemplate" flush="false">
                                <tiles:put name="id" value="top"/>
                                <tiles:put name="maxVal" value="${maxVal}"/>
                            </tiles:insert>

                            <c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(typeDate)"/></c:set>
                            <c:choose>
                                <c:when test="${curType == 'month'}">
                                    <%@ include file="/WEB-INF/jsp/finances/cashOperationsOnMonth.jsp"%>
                                </c:when>
                                <c:otherwise>
                                    <%@ include file="/WEB-INF/jsp/finances/cashOperationsOnPeriod.jsp"%>
                                </c:otherwise>
                            </c:choose>

                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    <%-- Подключаем  скрипт для АЛФ--%>
    <script type="text/javascript" src="${globalUrl}/scripts/finances/financesUtils.js"></script>

    <c:set var="currDate" value="${phiz:currentDate().time}"/>

    <script type="text/javascript">
        function checkFilterDates()
        {
            removeAllErrors();
            var typeDate = $('input[name=filter(typeDate)]').val();
            var maxDate = parseMonthDate('<fmt:formatDate value="${currDate}" pattern="MM/yyyy"/>');
            var minDate = new Date(maxDate.getYear()-1,maxDate.getMonth()-1,1);

            if (typeDate == 'monthPeriod')
            {
                var fromDateStr = $('input[name=filter(fromDate)]').val();
                var toDateStr = $('input[name=filter(toDate)]').val();

                var fromDate = parseMonthDate(fromDateStr, "Введите дату в поле Период в формате ММ/ГГГГ.");
                if (fromDate == null)
                    return false;

                var toDate = parseMonthDate(toDateStr, "Введите дату в поле Период в формате ММ/ГГГГ.");
                if (toDate == null)
                    return false;

                if (fromDate > toDate)
                {
                    addError("Конечная дата должна быть больше начальной!");
                    return false;
                }

                if (toDate > maxDate || fromDate < minDate)
                {
                    addError("Вы можете просмотреть движение средств только за последний год.");
                    return false;
                }
            }
            else
            {
                var onDateStr = $('input[name=filter(onDate)]').val();

                var onDate = parseMonthDate(onDateStr, "Введите дату в поле Месяц в формате ММ/ГГГГ.");
                if (onDate == null)
                    return false;

                if (onDate > maxDate || onDate < minDate)
                {
                    addError("Вы можете просмотреть движение средств только за последний год.");
                    return false;
                }
            }
            return true;
        }

        function parseMonthDate(dateStr, msg)
        {
            var dateYear = dateStr.slice(3);
            var dateMonth = dateStr.slice(0,2)-1;
            var dateDay = 1;
            var date = new Date(dateYear, dateMonth, dateDay);

            if (date.getFullYear() != dateYear || date.getMonth() != dateMonth || date.getDate() != dateDay)
            {
                addError(msg);
                return null;
            }

            return date;
        }
    </script>
    
    <%@ include file="/WEB-INF/jsp/finances/editOperationWindow.jsp"%>
</html:form>