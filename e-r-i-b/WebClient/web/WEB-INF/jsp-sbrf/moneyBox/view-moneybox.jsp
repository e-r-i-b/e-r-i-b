<%--
  Created by IntelliJ IDEA.
  User: saharnova
  Date: 29.09.14
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/MoneyBox.js"></script>
<script type="text/javascript">
    doOnLoad(function(){
        $('.listOfOperation .buttonSelect, .buttonGrayNew, .buttonSelect.productListOperation').hover(function(){
           $(this).addClass('hoverOperation');
           }, function() {
           $(this).removeClass('hoverOperation');
        });
        if (isIE(6) || isIE(7))
        {
            $(".productRight .listOfOperation").each(function(){
                var newWidth = $(this).find('.buttonSelect').width();
                $(this).width(newWidth);
            });
        }
    });
</script>
<html:form styleId="viewMoneyBoxFormId" action="/private/async/moneybox/view" onsubmit="return setEmptyAction(event);" show="true">
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="moneyBoxWinId" value="moneyBoxWinId"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="moneyboxBundle"/>

    <c:set var="moneybox" value="${form.link.value}"/>
    <c:set var="card" value="${form.cardLink}"/>
    <c:set var="account" value="${form.accountLink}"/>
    <c:set var="restAccount" value="${account.rest}"/>
    <c:set var="restCard" value="${card.rest}"/>
    <c:set var="items" value="${form.scheduleItems}"/>
    <c:set var="sumType" value="${moneybox.sumType}"/>
    <c:set var="status" value="${moneybox.autoPayStatusType}"/>
    <c:set var="moneyBoxId" value="${form.linkId != null ? form.linkId : form.claimId}"/>
    <c:set var="confirmWinId" value="confirmWindow"/>
    <c:set var="pauseWinId" value="pauseWindow"/>
    <c:set var="resumeWinId" value="resumeWindow"/>
    <c:set var="disableWinId" value="disableWindow"/>

    <%-- Сообщения --%>
    <tiles:insert definition="warningBlock" flush="false">
        <tiles:put name="regionSelector" value="warningsViewMoneyBox"/>
        <tiles:put name="needWarning" value="false"/>
        <tiles:put name="isDisplayed" value="false"/>
    </tiles:insert>

    <%-- Ошибки --%>
    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="errorsViewMoneyBox"/>
        <tiles:put name="needWarning" value="false"/>
        <tiles:put name="isDisplayed" value="false"/>
    </tiles:insert>

    <%--Заголовок и кнопка операций--%>
    <div class="moneyBoxInfoTitle">
        <div class="moneyBoxInfoTitleOperations">
            <tiles:insert definition="listOfOperationAsync" flush="false">
                <tiles:put name="productOperation" value="true"/>
                <tiles:put name="onClickList" value="showOrHideOperationBlock($('#moneyBoxViewWinDiv .listOfOperation').get(0));"/>
                <tiles:putList name="items">
                    <c:if test="${!card.arrest && phiz:impliesService('EditMoneyBoxClaim')}">
                        <tiles:add>
                            <c:set var="onclickEditAction">
                                <c:choose>
                                    <c:when test="${not empty form.linkId}">
                                        <c:out value="javascript:editMoneyBox(${form.linkId});"/>
                                    </c:when>
                                    <c:when test="${not empty form.claimId}">
                                        <c:out value="javascript:editMoneyBoxClaim(${form.claimId});"/>
                                    </c:when>
                                </c:choose>
                            </c:set>
                            <a onclick="${onclickEditAction}">
                                <bean:message bundle="${bundle}" key="button.edit"/>
                            </a>
                        </tiles:add>
                    </c:if>
                    <c:choose>
                        <c:when test="${status == 'Active' && phiz:impliesService('RefuseMoneyBoxPayment')}">
                            <tiles:add>
                                <a onclick="javascript:openMoneyBoxQuestionWindow('${pauseWinId}');">
                                    <bean:message bundle="${bundle}" key="button.pause"/>
                                </a>
                            </tiles:add>
                        </c:when>
                        <c:when test="${status == 'Paused' && phiz:impliesService('RecoverMoneyBoxPayment') && !card.arrest}">
                            <tiles:add>
                                <a onclick="javascript:openMoneyBoxQuestionWindow('${resumeWinId}');">
                                    <bean:message bundle="${bundle}" key="button.resume"/>
                                </a>
                            </tiles:add>
                        </c:when>
                        <c:when test="${phiz:isInstance(moneybox,'com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment') && phiz:impliesService('CloseMoneyBoxPayment')}">
                            <tiles:add>
                                <a onclick="javascript:openMoneyBoxQuestionWindow('${confirmWinId}');">
                                    <bean:message bundle="${bundle}" key="button.confirm"/>
                                </a>
                            </tiles:add>
                        </c:when>
                    </c:choose>

                    <tiles:add>
                        <a class="operationSeparate" onclick="javascript:openMoneyBoxQuestionWindow('${disableWinId}');">
                            <bean:message bundle="${bundle}" key="button.disable"/>
                        </a>
                    </tiles:add>
                </tiles:putList>
            </tiles:insert>
        </div>
        <div class="productTitleDetailInfoText moneyBoxInfoTitleText">
            <c:out value="${moneybox.friendlyName}"/>
        </div>
    </div>

    <div class="moneyBoxGreyLineCover">
        <div class="moneyBoxGreyLine"></div> <pre class="moneyBoxGreyText">  <bean:message bundle="${bundle}" key="conditions"/>  </pre><div class="moneyBoxGreyLine"> </div>
    </div>
    <div class="clear"></div>

    <%--Данные о копилке--%>
    <div>
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title" value="Карта списания"/>
            <tiles:put name="data">
                <span class="bold">
                    <c:choose>
                        <c:when test="${restCard != null}">
                            <c:out value="${phiz:getCutCardNumber(card.number)} [${card.name}] ${phiz:formatAmount(restCard)}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${phiz:getCutCardNumber(card.number)} [${card.name}]"/>
                            <c:choose>
                                <c:when test="${card.currency != null}">
                                    <c:out value="${phiz:getCurrencySign(card.currency.code)}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${phiz:getCurrencySign('RUB')}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </span>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title" value="Счет зачисления"/>
            <tiles:put name="data">
                <span class="bold">
                    <c:choose>
                        <c:when test="${restAccount != null}">
                            <c:out value="${phiz:getFormattedAccountNumber(account.number)} [${account.name}] ${phiz:formatAmount(restAccount)}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${phiz:getFormattedAccountNumber(account.number)} [${account.name}]"/>
                            <c:choose>
                                <c:when test="${account.currency != null}">
                                    <c:out value="${phiz:getCurrencySign(account.currency.code)}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${phiz:getCurrencySign('RUB')}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </span>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title" value="Вид копилки"/>
            <tiles:put name="data">
                <span class="bold"><c:out value="${sumType.description}"/></span>
            </tiles:put>
        </tiles:insert>

        <c:choose>
            <c:when test="${sumType == 'FIXED_SUMMA'}">
                <c:set var="period" value="${moneybox.executionEventType}"/>
                <c:set var="longOfferPayDay" value="${moneybox.longOfferPayDay}"/>

                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title" value=" "/>
                    <tiles:put name="data">
                        <bean:message bundle="${bundle}" key="view.period.description"/>
                        <span class="bold">
                            <bean:message bundle="${bundle}" key="view.period.${period}"/>
                            <c:choose>
                                <c:when test="${period == 'ONCE_IN_WEEK'}">
                                    <bean:message bundle="${bundle}" key="template.description.period.dayOfWeek.by"/> <c:out value="${phiz:dayNumberToString(longOfferPayDay.weekDay)}"/>.</br>
                                </c:when>
                                <c:when test="${period == 'ONCE_IN_MONTH'}">
                                    <c:out value="${longOfferPayDay.day}"/><bean:message bundle="${bundle}" key="template.description.period.dayOfMonth"/>.</br>
                                </c:when>
                                <c:when test="${period == 'ONCE_IN_QUARTER'}">
                                    <c:out value="${longOfferPayDay.day}"/><bean:message bundle="${bundle}" key="template.description.period.dayOfMonth"/>
                                    <c:out value="${longOfferPayDay.monthInQuarter}"/><bean:message bundle="${bundle}" key="template.description.period.monthOfQuarter"/>.</br>
                                </c:when>
                                <c:when test="${period == 'ONCE_IN_YEAR'}">
                                    <c:out value="${longOfferPayDay.day}"/> <c:out value="${phiz:monthNumberToString(longOfferPayDay.monthInYear)}"/>.</br>
                                </c:when>
                            </c:choose>
                        </span>
                        <bean:message bundle="${bundle}" key="view.period.nearestRefill"/> <c:out value="${phiz:formatDateWithStringMonth(moneybox.nextPayDate)}"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title" value="Сумма"/>
                    <tiles:put name="data">
                        <span class="bold"><c:out value="${phiz:formatAmount(moneybox.amount)}"/></span>
                    </tiles:put>
                </tiles:insert>
            </c:when>

            <c:when test="${sumType == 'PERCENT_BY_ANY_RECEIPT' || sumType == 'PERCENT_BY_DEBIT'}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title" value="% от суммы"/>
                    <tiles:put name="data">
                        <span class="bold"><c:out value="${moneybox.percent}"/></span>
                    </tiles:put>
                </tiles:insert>

                <c:choose>
                    <c:when test="${phiz:isInstance(moneybox, 'com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment')}">
                        <c:set var="maxSumWrite" value="${moneybox.amount}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="maxSumWrite" value="${form.link.autoSubscriptionInfo.maxSumWritePerMonth}"/>
                    </c:otherwise>
                </c:choose>
                <c:if test="${not empty maxSumWrite}">
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title" value="Максимальная сумма"/>
                        <tiles:put name="data">
                            <span class="bold"><c:out value="${phiz:formatAmount(maxSumWrite)}"/></span>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="${bundle}" key="view.maxAmount.Description"/>
                        </tiles:put>
                    </tiles:insert>
                </c:if>
            </c:when>
        </c:choose>
    </div>

    <c:if test="${!form.hasMbConnection}">
        <script type="text/javascript">
            var mbConnectionWarningText = "Вы не сможете получать SMS-сообщения об операциях по данной копилке, потому что к карте списания не подключена услуга «Мобильный банк». "
                    + "Для получения SMS-сообщений подключите услугу «Мобильный банк» через Контактный центр Сбербанка по телефону 8-800-500-55-50. "
                    + "Для завершения подключения копилки нажмите на кнопку «Операции», затем «Подключить»"
            addMessage(mbConnectionWarningText, "warningsViewMoneyBox", true);
        </script>
    </c:if>
    <div class="moneyBoxGreyLineCover">
        <div class="moneyBoxGreyLine"></div> <pre class="moneyBoxGreyText">  <bean:message bundle="${bundle}" key="history"/>  </pre><div class="moneyBoxGreyLine"> </div>
    </div>
    <div class="clear"></div>
    <%--График платежей копилки--%>
    <c:choose>
        <c:when test="${empty items}">
            <div class="emptyText greenBlock">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="greenBold"/>
                    <tiles:put name="data">
                        <c:choose>
                            <c:when test="${not empty form.textUpdateSheduleItemsError}">
                                <c:out value="${form.textUpdateSheduleItemsError}"/>
                            </c:when>
                            <c:otherwise>
                                <bean:message bundle="${bundle}" key="paymentsGraphicEmpty"/>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:when>
        <c:otherwise>
            <div class="moneyBoxInfoGraph">
                <tiles:insert definition="customScroll" flush="false">
                    <tiles:put name="height" value="105px"/>
                    <tiles:put name="data">
                        <table cellpadding="0" cellspacing="0" style="width:100%">
                            <tr class="moneyBoxGraphTitleTable" style="height: 30px;">
                                <td style="text-align:left;"><bean:message bundle="${bundle}" key="graphic.data"/></td>
                                <td style="text-align:left;"><bean:message bundle="${bundle}" key="graphic.summ"/></td>
                                <td style="text-align:left;"><bean:message bundle="${bundle}" key="graphic.state"/></td>
                                <td style="text-align:left;"><bean:message bundle="${bundle}" key="graphic.reasonError"/></td>
                            </tr>
                            <c:forEach items="${items}" var="item">
                                <tr class="moneyBoxGraphTable" style="height: 20px;">
                                    <td style="text-align:left;">
                                        <c:out value="${phiz:сalendarToString(item.date)}"/>
                                    </td>
                                    <td style="text-align:left;">
                                        <c:if test="${not empty item.summ}">
                                            <c:choose>
                                                <c:when test="${card.currency != null}">
                                                    <c:out value="${phiz:formatBigDecimal(item.summ)} ${phiz:getCurrencySign(card.currency.code)}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${phiz:formatBigDecimal(item.summ)} ${phiz:getCurrencySign('RUB')}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </td>
                                    <td  style="text-align:left;">
                                        <c:if test="${not empty item.status}">
                                            <bean:message bundle="${bundle}" key="payment.state.${item.status}"/>
                                        </c:if>
                                    </td>
                                    <td  class="moneyBoxGraphTitleTableRejectColumn">
                                        <c:if test="${not empty item.rejectionCause}">
                                                <c:out value="${item.rejectionCause}"/>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </tiles:put>
                </tiles:insert>
            </div>

            <hr>
            <div class="clear"></div>
            <div class="printButtonArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.print"/>
                    <tiles:put name="commandHelpKey" value="button.print"/>
                    <tiles:put name="bundle" value="${bundle}"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="image" value="print-check.gif"/>
                    <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                    <tiles:put name="imagePosition" value="left"/>
                    <tiles:put name="onclick">openPrintScheduleReport(event, ${moneyBoxId})</tiles:put>
                </tiles:insert>
            </div>
        </c:otherwise>
    </c:choose>

    <%--Всплывающие окна--%>
    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="${confirmWinId}"/>
        <tiles:put name="notCloseWindow" value="moneyBoxWinId"/>
        <tiles:put name="data">
            <div  onkeypress="onEnterKey(event);">
                <div class="moneyBoxWindowsText">
                    <bean:message bundle="${bundle}" key="window.confirm.text"/>
                </div>
                <div class="clear"></div>
                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="onclick" value="closeMoneyBoxQuestionWindow('${confirmWinId}');"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.connect"/>
                        <tiles:put name="commandHelpKey" value="button.connect"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick" value="connectMoneyBox('${confirmWinId}', ${moneyBoxId});"/>
                    </tiles:insert>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="${pauseWinId}"/>
        <tiles:put name="notCloseWindow" value="moneyBoxWinId"/>
        <tiles:put name="data">
            <div  onkeypress="onEnterKey(event);">
                <div class="moneyBoxWindowsText">
                    <bean:message bundle="${bundle}" key="window.pause.text"/>
                </div>
                <div class="clear"></div>
                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="onclick" value="closeMoneyBoxQuestionWindow('${pauseWinId}');"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.pause"/>
                        <tiles:put name="commandHelpKey" value="button.pause"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick" value="pauseMoneyBox('${pauseWinId}', '${moneyBoxId}');"/>
                    </tiles:insert>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="${resumeWinId}"/>
        <tiles:put name="notCloseWindow" value="moneyBoxWinId"/>
        <tiles:put name="data">
            <div  onkeypress="onEnterKey(event);">
                <div class="moneyBoxWindowsText">
                    <bean:message bundle="${bundle}" key="window.resume.text"/>
                </div>
                <div class="clear"></div>
                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="onclick" value="closeMoneyBoxQuestionWindow('${resumeWinId}');"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.resume"/>
                        <tiles:put name="commandHelpKey" value="button.resume"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick" value="resumeMoneyBox('${resumeWinId}', '${moneyBoxId}');"/>
                    </tiles:insert>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="${disableWinId}"/>
        <tiles:put name="notCloseWindow" value="moneyBoxWinId"/>
        <tiles:put name="data">
            <div  onkeypress="onEnterKey(event);">
                <div class="moneyBoxWindowsText">
                    <c:set var="textDisable">
                        <bean:message bundle="${bundle}" key="window.disable.text"/>
                    </c:set>
                    ${fn:replace(textDisable, '%nameMoneyBox%', moneybox.friendlyName)}
                </div>
                <div class="clear"></div>
                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="onclick" value="closeMoneyBoxQuestionWindow('${disableWinId}');"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.disable"/>
                        <tiles:put name="commandHelpKey" value="button.disable"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick" value="disableMoneyBox('${disableWinId}', '${moneyBoxId}', '${form.linkId != null ? 'LINK' : 'CLAIM'}');"/>
                    </tiles:insert>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>

</html:form>