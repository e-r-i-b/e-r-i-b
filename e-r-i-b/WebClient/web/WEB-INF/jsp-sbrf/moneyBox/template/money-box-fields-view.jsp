<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<c:if test="${phiz:impliesService('CreateMoneyBoxPayment')}">
    <c:set var="createMoneyBoxValue">${form.fields['createMoneyBox']}</c:set>
    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="data">
            <tiles:put name="title"> </tiles:put>
            <div id="moneyBoxTitle" style="font-size:16px;font-weight:bold;">Копилка <input type="checkbox" name="field(createMoneyBox)" id="createMoneyBox" value="true"
               disabled onchange="hideOrShowMoneyBoxFields();" <c:if test="${createMoneyBoxValue}">checked="true"</c:if>/></div>
        </tiles:put>
    </tiles:insert>
    <c:if test="${createMoneyBoxValue}">
        <c:set var="moneyBoxType" value="${form.fields['moneyBoxSumType']}"/>
        <div id="moneyBoxFieldsDiv">
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">Название:</tiles:put>
                <tiles:put name="data">
                    <bean:write name="form" property="field(moneyBoxName)"/>
                </tiles:put>
            </tiles:insert>

            <c:set var="resource" value="${form.fields['moneyBoxFromResource']}"/>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">Карта списания:</tiles:put>
                <tiles:put name="data">
                    ${phiz:getCutCardNumber(resource.number)} [${resource.name}] ${resource.rest.decimal} ${phiz:getCurrencyName(resource.currency)}
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">Тип пополнения:</tiles:put>
                <tiles:put name="data">
                    <bean:message key="moneyBox.sumType.${moneyBoxType}" bundle="moneyboxBundle"/>
                </tiles:put>
            </tiles:insert>

            <c:choose>
                <c:when test="${moneyBoxType == 'FIXED_SUMMA'}">
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title"> </tiles:put>
                        <tiles:put name="data">
                            Перевод будет осуществляться
                            <div id="awaysPeriodicDescription" style="font-weight: bold;"></div>
                        </tiles:put>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">% от суммы:</tiles:put>
                        <tiles:put name="data">
                            <bean:write name="form" property="field(moneyBoxPercent)"/>
                        </tiles:put>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="clazz" value="moneyBoxSumRow"/>
                <tiles:put name="title"><span id="moneyBoxSumLabel"><c:choose><c:when test="${moneyBoxType=='FIXED_SUMMA'}">Сумма пополнения:</c:when><c:otherwise>Максимальная сумма:</c:otherwise></c:choose></span></tiles:put>
                <tiles:put name="data">
                    <bean:write name="form" property="field(moneyBoxSellAmount)"/>
                    <div id="moneyBoxSellAmountCurrency" style="display: inline;">${phiz:getCurrencyName(resource.currency)}</div>
                </tiles:put>
            </tiles:insert>
        </div>

        <script type="text/javascript">
            var daysOfWeekDesc = ["понедельникам", "вторникам", "средам", "четвергам", "пятницам", "субботам", "воскресеньям"];
            var monthOfYearDesc = ["января", "февраля", "марта", "апреля", "мая", "июня", "июля",   "августа", "сентября", "октября","ноября", "декабря"];

            function updateFixedSummaDescription()
            {
                var descriptionElement = document.getElementById('awaysPeriodicDescription');
                var eventTypeValue = '${moneyBoxType}';
                var startDateValue = Str2Date('${form.fields['longOfferStartDate']}');
                if(eventTypeValue == 'FIXED_SUMMA')
                {
                    var periodicValue = '${form.fields['longOfferEventType']}';
                    var description = "";
                    if(periodicValue == 'ONCE_IN_WEEK')
                    {
                        description = "Раз в неделю, по " + daysOfWeekDesc[(startDateValue.getDay()+6)%7];
                    }
                    else if(periodicValue == 'ONCE_IN_MONTH')
                    {
                        description = "Раз в месяц, " + startDateValue.getDate() + "-го числа";
                    }
                    else if(periodicValue == 'ONCE_IN_QUARTER')
                    {
                        description = "Раз в квартал, " + startDateValue.getDate() + "-го числа " + (startDateValue.getMonth()%3 + 1) + "-го месяца";
                    }
                    else if(periodicValue == 'ONCE_IN_YEAR')
                    {
                        description = "Раз в год, " + startDateValue.getDate()+"-го " + monthOfYearDesc[startDateValue.getMonth()];
                    }
                    descriptionElement.innerHTML = description;
                }
            }

            doOnLoad(function()
            {
                updateFixedSummaDescription();
            });
        </script>
    </c:if>
</c:if>