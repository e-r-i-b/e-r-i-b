<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:importAttribute/>
<html:form action="/private/longOffers/info" onsubmit="return setEmptyAction(event)">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="accountInfo">
    <c:set var="link" value="${form.longOfferLink}"/>
    <c:set var="refuseLongOfferUrl" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=RefuseLongOffer')}&longOfferId=${link.id}"/>
    <c:set var="longOffer" value="${link.value}"/>
    <c:set var="items" value="${form.scheduleItems}"/>
    <tiles:put name="mainmenu" value="LongOffers"/>
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Мои автоплатежи"/>
            <tiles:put name="action" value="/private/favourite/list/AutoPayments.do"/>
        </tiles:insert>
        <c:if test="${not empty link.name}">
            <c:set var="longOfferName">&laquo;<c:out value="${link.name}"/>&raquo;</c:set>
        </c:if>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Автоплатеж (регулярная операция)"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="menu" type="string"/>
    <c:if test="${not empty longOffer}">
        <tiles:put name="data" type="string">
            <div id="payment">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Автоплатеж (Регулярная операция)"/>
                <tiles:put name="data">

                    <script type="text/javascript">
                        function openPrintInfo(event)
                        {
                            var url = "${phiz:calculateActionURL(pageContext,'/private/longOffers/info/print.do')}";
                            var params = "?id=" + ${form.id};

                            openWindow(event, url + params, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
                        }

                        function openPrintScheduleReport(event)
                        {
                            var url = "${phiz:calculateActionURL(pageContext,'/private/longOffers/info/printScheduleReport.do')}";
                            var params = "?id=" + ${form.id};

                            var toDate = getElementValue("field(toDate)");
                            var fromDate = getElementValue("field(fromDate)");
                            
                            if (!validateDatePeriod(fromDate, toDate))
                            {
                                return;
                            }

                            params += "&fromDateString=" + fromDate;
                            params += "&toDateString=" + toDate;

                            openWindow(event, url + params, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
                        }

                        function refuse(event)
                        {
                            window.location="${refuseLongOfferUrl}";
                        }

                    </script>
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${imagePath}/longOfferInfo.png"/>
                        <tiles:put name="description">
                                На данной странице можно просмотреть созданный Вами автоплатеж(регулярную операцию), распечатать график платежей
                            и при необходимости отменить выполнение автоплатежа(регулярной операции).
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>
                    <fieldset>
                        <table class="additional-product-info">
                            <c:if test="${not empty longOffer.type}">
                                <tr>
                                    <td class="align-right field">Вид операции:</td>
                                    <td><span class="bold"><bean:message key="type.${longOffer.type.simpleName}" bundle="longOfferInfoBundle"/></span></td>
                                </tr>
                            </c:if>
                            <tr>
                                <td class="align-right field">Номер документа:</td>
                                <td><span class="bold">${link.number}</span></td>
                            </tr>
                            <c:if test="${not empty link.receiverAccount || not empty link.receiverCard}">
                                <tr>
                                    <td colspan="2">
                                        <h2 class="additionalInfoHeader">Получатель</h2>
                                    </td>
                                </tr>
                                <tr>
                                    <c:if test="${not empty link.receiverName}">
                                        <td class="align-right field">Фамилия Имя Отчество:</td>
                                        <td><span class="bold">${link.receiverName}</span></td>
                                    </c:if>
                                </tr>
                                <tr>
                                    <c:choose>
                                        <c:when test="${not empty link.receiverCard}">
                                                <td class="align-right field">Номер карты получателя:</td>
                                                <td><span class="bold">${phiz:getCutCardNumber(link.receiverCard)}</span></td>
                                        </c:when>
                                        <c:otherwise>
                                                <td class="align-right field">Номер счета получателя:</td>
                                                <td><span class="bold">${link.receiverAccount}</span></td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:if>
                            <tr>
                                <td colspan="2">
                                    <h2 class="additionalInfoHeader">Перевод</h2>
                                </td>
                            </tr>
                            <c:if test="${not empty form.payerAccount}">
                                <tr>
                                    <td class="align-right field">Перевести с:</td>
                                    <td><span class="bold">${form.payerAccount}</span></td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty longOffer.percent || not empty longOffer.amount}">
                                <tr>
                                    <td class="align-right field">Сумма перевода:</td>
                                    <td>
                                        <span class="bold">
                                            <c:if test="${'ON_REMAIND' == longOffer.executionEventType}">сумма сверх указанного остатка на счете списания&nbsp;</c:if>
                                        </span>
                                        <span class="bold summ">
                                            <c:choose>
                                                <c:when test="${not empty longOffer.percent}">
                                                    Процент остатка на счете <bean:write name="longOffer" property="percent"/>%
                                                </c:when>
                                                <c:when test="${longOffer.sumType == 'SUMMA_OF_RECEIPT'}">
                                                    <c:out value="${longOffer.sumType.description}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <bean:write name="longOffer" property="amount.decimal" ignore="true"/>&nbsp;${phiz:getCurrencySign(longOffer.amount.currency.code)}
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td colspan="2">
                                    <h2 class="additionalInfoHeader">Параметры автоплатежа (регулярной операции)</h2>
                                </td>
                            </tr>
                            <c:if test="${not empty longOffer.startDate}">
                                <tr>
                                    <td class="align-right field">Дата начала:</td>
                                    <td><span class="bold">${phiz:formatDateWithStringMonth(longOffer.startDate)}</span></td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty longOffer.endDate}">
                                <tr>
                                    <td class="align-right field">Дата окончания:</td>
                                    <td><span class="bold">${phiz:formatDateWithStringMonth(longOffer.endDate)}</span></td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty link.executionEventType}">
                                <tr>
                                    <td class="align-right field">Повторяется:</td>
                                    <td><span class="bold">${link.executionEventType}</span></td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty longOffer.priority}">
                                <tr>
                                    <td class="align-right field">Выполняется:</td>
                                    <td>
                                        <span class="bold"><bean:message key="priority${longOffer.priority}" bundle="longOfferInfoBundle"/></span>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty longOffer.office}">
                                <tr>
                                    <td class="align-right field">Оформлено в:</td>
                                    <td><span class="bold">${longOffer.office.name}</span></td>
                                </tr>
                            </c:if>
                            <tr>
                                <td class="align-right field">Название:</td>
                                <td>
                                    <html:text property="field(longOfferName)" size="50" maxlength="100"/>
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="commandKey" value="button.saveLongOfferName"/>
                                        <tiles:put name="commandHelpKey" value="button.saveLongOfferName.help"/>
                                        <tiles:put name="bundle" value="longOfferInfoBundle"/>
                                        <tiles:put name="viewType" value="buttonGrey"/>
                                    </tiles:insert>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div class="buttonsArea">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.abstract.print"/>
                                            <tiles:put name="commandHelpKey" value="button.abstract.print"/>
                                            <tiles:put name="bundle" value="longOfferInfoBundle"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                            <tiles:put name="onclick">openPrintInfo(event)</tiles:put>
                                        </tiles:insert>
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.refuseLongOffer"/>
                                            <tiles:put name="commandHelpKey" value="button.refuseLongOffer"/>
                                            <tiles:put name="bundle" value="longOfferInfoBundle"/>
                                            <tiles:put name="onclick">refuse(event)</tiles:put>
                                        </tiles:insert>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <h2 class="additionalInfoHeader">График исполнения</h2>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="filterAlign">
                                    <div class="fltrText">Показать график за период с&nbsp;</div>
                                    <div>
                                        <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="field(fromDate)" format="dd/MM/yyyy"/>'
                                               onkeydown="enterNumTemplateField(event, this, ENGLISH_DATE_TEMPLATE)"
                                               size="10" name="field(fromDate)" id="field(fromDate)" class="date-pick"/>
                                    </div>
                                    <div class="fltrText">&nbsp;по&nbsp;</div>
                                    <div>
                                        <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="field(toDate)" format="dd/MM/yyyy"/>'
                                               onkeydown="enterNumTemplateField(event, this, ENGLISH_DATE_TEMPLATE)"
                                               size="10" name="field(toDate)" id="field(toDate)" class="date-pick"/>
                                    </div>
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="commandKey" value="button.showScheduleReport"/>
                                        <tiles:put name="commandHelpKey" value="button.showScheduleReport.help"/>
                                        <tiles:put name="bundle" value="longOfferInfoBundle"/>
                                        <tiles:put name="viewType" value="blueGrayLinkDotted"/>
                                    </tiles:insert>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div class="regular-payment-graph">
                                        <c:choose>
                                            <c:when test="${not empty items}">
                                                <tiles:insert definition="simpleTableTemplate" flush="false">
                                                    <tiles:put name="grid">
                                                        <sl:collection id="scheduleItem" model="no-pagination" name="items">
                                                            <sl:collectionItem>
                                                                ${phiz:formatDateWithStringMonth(scheduleItem.date)}
                                                            </sl:collectionItem>
                                                            <sl:collectionItem>
                                                                <c:if test="${not empty scheduleItem.amount}">
                                                                    <bean:write name="scheduleItem" property="amount.decimal" ignore="true"/>&nbsp;${phiz:getCurrencySign(scheduleItem.amount.currency.code)}
                                                                </c:if>
                                                            </sl:collectionItem>
                                                            <sl:collectionItem>
                                                                <c:choose>
                                                                    <c:when test="${scheduleItem.state == 'SUCCESS'}">оплачен</c:when>
                                                                    <c:otherwise>не выполнен</c:otherwise>
                                                                </c:choose>
                                                            </sl:collectionItem>
                                                        </sl:collection>
                                                    </tiles:put>
                                                </tiles:insert>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="emptyText">
                                                    <tiles:insert definition="roundBorderLight" flush="false">
                                                        <tiles:put name="color" value="greenBold"/>
                                                        <tiles:put name="data">
                                                            <c:choose>
                                                                <%--Если пришла ошибка при получении операций--%>
                                                                <c:when test="${!empty form.abstractMsgError}">
                                                                    <c:out value="${form.abstractMsgError}"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    В рамках платежа не было ни одной оплаты.
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div class="buttonsArea">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.scheduleReport.print"/>
                                            <tiles:put name="commandHelpKey" value="button.scheduleReport.print.help"/>
                                            <tiles:put name="bundle" value="longOfferInfoBundle"/>
                                            <tiles:put name="viewType" value="blueGrayLink"/>
                                            <tiles:put name="onclick">openPrintScheduleReport(event)</tiles:put>
                                        </tiles:insert>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </tiles:put>
            </tiles:insert>
            </div>
        </tiles:put>
    </c:if>
</tiles:insert>

</html:form>
