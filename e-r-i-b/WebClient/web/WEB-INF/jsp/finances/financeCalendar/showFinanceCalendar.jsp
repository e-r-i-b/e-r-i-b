<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/finances/financeCalendar">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="extractIdFromTemplate" value="${form.extractId}"/>
    <tiles:insert definition="financePlot">
        <tiles:put name="data" type="string">
            <script type="text/javascript" src="${globalUrl}/scripts/invoices.js"></script>
            <script type="text/javascript" src="${globalUrl}/scripts/finances/financeCalendar.js"></script>
            <div id="financeCalendar">
                <tiles:insert definition="roundBorderWithoutTop" flush="false">
                    <tiles:put name="top">
                        <c:set var="selectedTab" value="financeCalendar"/>
                        <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>
                    </tiles:put>
                    <tiles:put name="data">
                        <tiles:insert definition="financeContainer" flush="false">
                            <tiles:put name="showFavourite" value="false"/>
                            <tiles:put name="data">
                                <div class="cardFilterBlock hightLevelPosition">
                                    <c:set var="node" value="${form.node}" scope="request"/>
                                    <tiles:insert page="/WEB-INF/jsp/common/layout/customSelect.jsp" flush="false">
                                        <tiles:put name="customSelectId" value="1"/>
                                        <tiles:put name="customSelectName" value="selectedId"/>
                                        <tiles:put name="customSelectValue" value="Все карты и наличные"/>
                                        <tiles:put name="customSelectOnclick" value="findCommandButton('button.filter').click('', false);"/>
                                    </tiles:insert>
                                    <div class="clear"></div>
                                </div>

                                <div class="dateFilterBlock">
                                    <tiles:insert definition="filterOnMonth" flush="false">
                                        <tiles:put name="name" value="Date"/>
                                        <tiles:put name="buttonKey" value="button.filter"/>
                                    </tiles:insert>

                                    <script type="text/javascript">
                                        createCommandButton('button.filter');
                                    </script>
                                </div>

                                <c:set var="openPageDate"><fmt:formatDate value="${form.openPageDate.time}" pattern="dd/MM/yyyy HH:mm:ss"/></c:set>
                                <c:set var="selectedCardIds" value="" scope="request"/>
                                <c:forEach items="${form.node.selectedIds}" var="cardId">
                                    <c:set var="selectedCardIds" value="${selectedCardIds}${cardId};" scope="request"/>
                                </c:forEach>

                                <script type="text/javascript">
                                    $(document).ready(function(){
                                        financeCalendar.setOpenPageDate('${openPageDate}');
                                        financeCalendar.setSelectedCardIds('${selectedCardIds}');

                                        var extractIdFromTemplate = '${extractIdFromTemplate}';
                                        if (extractIdFromTemplate != null && extractIdFromTemplate != '')
                                        {
                                            var extractId = extractIdFromTemplate.replace(new RegExp("\\.",'g'),'');
                                            financeCalendar.getDayExtract($('#calendarBox_' + extractId),extractId,extractIdFromTemplate.replace(new RegExp("\\.",'g'),'/'));
                                        }
                                    });

                                    function closeListTemplates(extractIdToTemplate)
                                    {
                                        var extractId = extractIdToTemplate.replace(new RegExp("\\.",'g'), '');

                                        financeCalendar.getDayExtract($('#calendarBox_' + extractId),extractId,extractIdToTemplate.replace(new RegExp("\\.",'g'),'/'));
                                    }

                                    function createTemplate(createTemplateUrl)
                                    {
                                        window.location = createTemplateUrl;
                                        return true;
                                    }
                                </script>

                                <div id="calendarGrid" class="calendarGrid">
                                    <c:set var="hasCurrentDate" value="false"/>
                                    <c:set var="currentDate" value="${phiz:currentDate().time}"/>
                                    <c:set var="filterDate" value="${form.filters['onDate']}"/>
                                    <c:set var="filterMonth">
                                        <fmt:formatDate value="${filterDate}" pattern="MM"/>
                                    </c:set>
                                    <c:set var="data" value="${form.calendarData}"/>
                                    <c:set var="size" value="${phiz:size(data)}"/>

                                    <c:choose>
                                        <c:when test="${phiz:isEmptyFinanceCalendar(data)}">
                                            <div class="emptyText">
                                                <tiles:insert definition="roundBorderLight" flush="false">
                                                    <tiles:put name="color" value="greenBold"/>
                                                    <tiles:put name="data">
                                                        <span class="text-dark-gray normal relative">
                                                            <bean:message bundle="financesBundle" key="message.finance.financeCalendar.emptyMessage"/>
                                                        </span>
                                                    </tiles:put>
                                                </tiles:insert>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <table class="calendarGridBody">
                                                <tr class="calendarGridHeader">
                                                    <tiles:insert page="calendarGridDayOfWeek.jsp" flush="false">
                                                        <tiles:put name="dayOfWeenName">
                                                            <bean:message bundle="financesBundle" key="day.name.Mo"/>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                    <tiles:insert page="calendarGridDayOfWeek.jsp" flush="false">
                                                        <tiles:put name="dayOfWeenName">
                                                            <bean:message bundle="financesBundle" key="day.name.Tu"/>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                    <tiles:insert page="calendarGridDayOfWeek.jsp" flush="false">
                                                        <tiles:put name="dayOfWeenName">
                                                            <bean:message bundle="financesBundle" key="day.name.We"/>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                    <tiles:insert page="calendarGridDayOfWeek.jsp" flush="false">
                                                        <tiles:put name="dayOfWeenName">
                                                            <bean:message bundle="financesBundle" key="day.name.Th"/>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                    <tiles:insert page="calendarGridDayOfWeek.jsp" flush="false">
                                                        <tiles:put name="dayOfWeenName">
                                                            <bean:message bundle="financesBundle" key="day.name.Fr"/>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                    <tiles:insert page="calendarGridDayOfWeek.jsp" flush="false">
                                                        <tiles:put name="dayOfWeenName">
                                                            <bean:message bundle="financesBundle" key="day.name.Sa"/>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                    <tiles:insert page="calendarGridDayOfWeek.jsp" flush="false">
                                                        <tiles:put name="dayOfWeenName">
                                                            <bean:message bundle="financesBundle" key="day.name.Su"/>
                                                        </tiles:put>
                                                        <tiles:put name="additionalClazz" value="lastDay"/>
                                                    </tiles:insert>
                                                </tr>

                                                <c:forEach items="${data}" var="item" varStatus="i">
                                                    <c:set var="currNum" value="${i.index + 1}"/>
                                                    <c:set var="dateDay"><fmt:formatDate value="${item.date.time}" pattern="dd"/></c:set>
                                                    <c:set var="dateMonth"><fmt:formatDate value="${item.date.time}" pattern="MM"/></c:set>
                                                    <c:set var="dateYear"><fmt:formatDate value="${item.date.time}" pattern="yyyy"/></c:set>
                                                    <c:set var="lastDay" value="${currNum mod 7 == 0}"/>
                                                    <c:set var="firstDay" value="${currNum mod 7 == 1}"/>
                                                    <c:set var="lastLine" value="${size - currNum < 7}"/>
                                                    <c:set var="weekend" value="${currNum mod 7 == 0 || currNum mod 7 == 6}"/>
                                                    <c:set var="isFilterMonth" value="${dateMonth == filterMonth}"/>
                                                    <c:set var="isCurrentDay" value="${item.dateType == 'TODAY'}"/>

                                                    <c:set var="additionalBoxClazz" value=""/>
                                                    <c:set var="additionalTextClazz" value=""/>

                                                    <c:if test="${!isFilterMonth}">
                                                        <c:set var="additionalBoxClazz" value="${additionalBoxClazz} notCurrentMonth"/>
                                                    </c:if>
                                                    <c:if test="${lastLine}">
                                                        <c:set var="additionalBoxClazz" value="${additionalBoxClazz} lastLine"/>
                                                    </c:if>
                                                    <c:if test="${isCurrentDay}">
                                                        <c:set var="additionalBoxClazz" value="${additionalBoxClazz} currentDay"/>
                                                        <c:set var="hasCurrentDate" value="true"/>
                                                    </c:if>
                                                    <c:if test="${weekend}">
                                                        <c:set var="additionalTextClazz" value="${additionalTextClazz} weekendDayNumber"/>
                                                    </c:if>

                                                    <c:if test="${firstDay}">
                                                        <tr>
                                                    </c:if>

                                                    <c:set var="onClick" value=""/>
                                                    <c:set var="extractId" value="${dateDay}${dateMonth}${dateYear}"/>
                                                    <c:set var="date"><fmt:formatDate value="${item.date.time}" pattern="dd/MM/yyyy"/></c:set>
                                                    <c:if test="${!item.isEmpty or (item.dateType != 'PAST' && phiz:impliesService('ReminderManagment'))}">
                                                        <c:set var="onClick" value="financeCalendar.getDayExtract(this, '${extractId}', '${date}');"/>
                                                    </c:if>

                                                    <c:if test="${not empty onClick}">
                                                        <c:set var="additionalBoxClazz" value="${additionalBoxClazz} pointer"/>
                                                    </c:if>

                                                    <td id="calendarBox_${extractId}" class="calendarBox ${additionalBoxClazz}" onclick="${onClick}">
                                                        <div class="calendarBoxDayNumber ${additionalTextClazz}">
                                                            <c:if test="${isCurrentDay}">
                                                                <bean:message bundle="financesBundle" key="lable.today"/>
                                                            </c:if>
                                                            ${dateDay}
                                                        </div>
                                                        <div class="calendarBoxData">
                                                            <c:if test="${not empty item.outcomeSum && item.outcomeSum != 0}">
                                                                <div class="outcomeBoxData">
                                                                    ${phiz:formatDecimalToAmountRound(item.outcomeSum, true)}
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${not empty item.incomeSum && item.incomeSum != 0}">
                                                                <div class="incomeBoxData">
                                                                    +${phiz:formatDecimalToAmountRound(item.incomeSum, true)}
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${item.paymentsAmount > 0}">
                                                                <div class="calendarBoxPaymentsAmount">
                                                                    -${phiz:formatDecimalToAmountRound(item.paymentsAmount, true)}
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${item.autoSubscriptionsCount > 0}">
                                                                <div class="calendarBoxAutopaymentsCount">
                                                                    ${item.autoSubscriptionsCount}
                                                                    ${phiz:numeralDeclension(item.autoSubscriptionsCount, 'автоплатеж', '', 'а', 'ей')}
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${item.invoiceSubscriptionsCount > 0}">
                                                                <div class="calendarBoxAutopaymentsCount">
                                                                    ${item.invoiceSubscriptionsCount}
                                                                    <c:choose>
                                                                        <c:when test="${isCurrentDay}">
                                                                            ${phiz:numeralDeclension(item.invoiceSubscriptionsCount, 'счет', '', 'а', 'ов')}
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            ${phiz:numeralDeclension(item.invoiceSubscriptionsCount, 'отложенн', 'ый', 'ых', 'ых')}
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </td>
                                                    <c:if test="${lastDay}">
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>
                                            </table>

                                            <c:if test="${hasCurrentDate && not empty form.cardsBalance}">
                                                <div id="calendarBalanceCircle" class="calendarBalanceCircle"></div>
                                                <div id="calendarBalanceAmountByCardsLine" class="calendarBalanceAmountByCardsLine"></div>
                                                <div class="calendarBalanceAmountByCards">
                                                    <div class="calendarBalanceTitle">
                                                        <bean:message bundle="financesBundle" key="lable.finance.calendar.currentBalance"/>
                                                    </div>
                                                    <div>
                                                        <span>
                                                            ${phiz:formatDecimalToAmountRound(form.cardsBalance, true)}
                                                        </span>
                                                        <input type="hidden" id="currentBalanceAmount" value="${phiz:formatDecimalToAmount(form.cardsBalance, 2)}"/>
                                                    </div>
                                                </div>

                                                <script type="text/javascript">
                                                    $(document).ready(function(){
                                                        financeCalendar.setCurrentDateBalancePosition();
                                                    });
                                                </script>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>