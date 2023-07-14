<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<html:form action="/private/cards/print">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>

    <tiles:insert definition="printDoc">
        <tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>
        <tiles:put name="data" type="string">
            <c:set var="cardClient" value="${form.cardLink.cardClient}"/>

            <table style="width:100%;">
                <tr>
                    <td style="font-size:11pt;text-align:center;padding-top:20px">
                        Информация по карте № ${phiz:getCutCardNumber(form.cardLink.number)} в «Сбербанк
                        России ОАО»
                    </td>
                </tr>
                <tr>
                    <td class="needBorder" style="padding-top:7px; padding-bottom:7px;">
                        <table cellpadding="0" cellspacing="0" style="width:100%;">
                            <tr>
                                <td class="labelAbstractPrint">
                                    ФИО клиента:&nbsp;
                                        <c:if test="${not empty cardClient}">
                                            <c:out value="${phiz:getFormattedPersonName(cardClient.firstName,cardClient.surName,cardClient.patrName)}"/>
                                        </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="labelAbstractPrint">
                                    Дата распечатки документа:&nbsp;
                                    <c:set var="date" value="${phiz:currentDate()}"/>
                                    <bean:write name="date" property="time" format="dd.MM.yyyy"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="borderedTable" style="padding:5px;">
                        <c:set var="cardLink" value="${form.cardLink}"/>
                        <c:set var="card" value="${cardLink.card}"/>

                        <c:set var="isMain" value="${cardLink.main}"/>
                        <c:set var="cardDetail" value="${cardLink.card}"/>
                        <c:set var="amountinfo" value="${phiz:formatAmount(card.availableLimit)}"/>
                        <%--Для дополнительных карт доступный расходный лимит "Avail" не приходит в GFL и CRDWI,
                            вместо него отображаем доступный лимит для оплаты "AvailPmt"--%>
                        <c:if test="${!isMain && empty card.availableLimit && not empty cardDetail && not empty cardDetail.purchaseLimit}">
                            <c:set var="amountinfo" value="${phiz:formatAmount(cardDetail.purchaseLimit)}"/>
                        </c:if>

                        <table cellpadding="0" cellspacing="0" style="width:100%;">
                            <c:if test="${not empty cardClient}">
                                <tr>
                                    <td class="docTdBorder textPadding">ФИО Держателя карты</td>
                                    <td>
                                        <span class="bold">
                                            <c:out value="${phiz:getFormattedPersonName(cardClient.firstName,cardClient.surName,cardClient.patrName)}"/>
                                        </span>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty cardLink.description || not empty card.cardType}">
                                <tr>
                                    <td class="docTdBorder textPadding">Тип карты</td>
                                    <td>
                                                    <span class="bold">
                                                            ${cardLink.description}
                                                    </span>
                                        <c:choose>
                                            <c:when test="${not card.virtual}">
                                                ${card.cardType.displayDescription}
                                            </c:when>
                                            <c:otherwise>
                                                Виртуальная
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td class="docTdBorder textPadding">Номер карты</td>
                                <td><span class="bold"> ${phiz:getCutCardNumber(cardLink.number)}</span></td>
                            </tr>
                            <tr>
                                <td class="docTdBorder textPadding">Номер счета карты</td>
                                <td>
                                    <c:set var="cardPrimaryAccount" value="${card.primaryAccountNumber}"/>
                                    <c:if test="${empty cardPrimaryAccount}">
                                        <c:choose>
                                            <c:when test="${!empty cardLink.cardPrimaryAccount}">
                                                <c:set var="cardPrimaryAccount" value="${cardLink.cardPrimaryAccount}"/>
                                            </c:when>
                                            <c:when test="${!empty cardAccount}">
                                                <c:set var="cardPrimaryAccount" value="${cardAccount.number}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="cardPrimaryAccount" value=""/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                    <span class="bold">${phiz:getFormattedAccountNumber(cardPrimaryAccount)}</span>
                                </td>
                            </tr>
                            <tr>
                                <td class="docTdBorder textPadding">Дата открытия</td>
                                <td><span
                                        class="bold">${phiz:formatDateWithStringMonth(card.issueDate)}</span>
                                </td>
                            </tr>
                            <tr>
                                <td class="docTdBorder textPadding">Срок действия по</td>
                                <td><span
                                        class="bold">${phiz:formatExpirationCardDate(cardLink)}</span>
                                </td>
                            </tr>
                            <tr>
                                <td class="docTdBorder textPadding">Доступный расходный лимит</td>
                                <td><span class="bold">${amountinfo}</span></td>
                            </tr>
                            <tr>
                                <td class="docTdBorder textPadding">Доступные средства для снятия наличных
                                </td>
                                <td><span
                                        class="bold">${phiz:formatAmount(cardLink.card.availableCashLimit)}</span>
                                </td>
                            </tr>
                            <tr>
                                <td class="docTdBorder textPadding">Доступные средства для покупок</td>
                                <td><span
                                        class="bold">${phiz:formatAmount(cardLink.card.purchaseLimit)}</span>
                                </td>
                            </tr>
                            <c:set var="cardPrimaryAccountOffice" value="${cardLink.cardAccount.office}"/>
                            <c:if test="${empty cardPrimaryAccountOffice}">
                                <c:set var="cardPrimaryAccountOffice" value="${card.office}"/>
                            </c:if>
                            <c:if test="${not empty cardPrimaryAccountOffice}">
                                <tr>
                                    <td class="docTdBorder textPadding">Подразделение, выдавшее карту:</td>
                                    <td>
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${not empty cardPrimaryAccountOffice.name}">
                                                    <c:out value="${cardPrimaryAccountOffice.name}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="codeFields" value="${cardPrimaryAccountOffice.code.fields}"/>
                                                    <c:if test="${!empty codeFields.branch && !empty codeFields.office}">
                                                        №&nbsp;
                                                            ${codeFields.branch}/
                                                        ${codeFields.office}
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${cardLink.main && not empty card && card.cardType=='credit'}">
                                <tr>
                                    <td class="docTdBorder textPadding">Лимит кредита</td>
                                    <td>
                                        <span class="bold">
                                            <c:if test="${not empty card.overdraftLimit}">
                                                ${phiz:formatAmount(card.overdraftLimit)}
                                            </c:if>
                                        </span>
                                    </td>
                                </tr>
                                <c:set var="showOwnSum" value="${not empty card.overdraftOwnSum and card.overdraftOwnSum.wholePart >= 0}"/>
                                <%--Отображение "Обязательный платеж" для овердрафтных завязывается на значение "Лимит овердрафта"--%>
                                <c:set var="showMandatoryPayment" value="${not empty card.overdraftMinimalPayment &&
                                (card.cardType == 'credit' ||
                                    (card.cardType == 'overdraft' && not empty card.overdraftLimit && card.overdraftLimit.decimal>0 && (isMain || isVirtual) ))}"/>
                                <tr>
                                    <td class="docTdBorder textPadding">Собственные средства</td>
                                    <td>
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${showOwnSum}">
                                                    ${phiz:formatAmount(card.overdraftOwnSum)}
                                                </c:when>
                                                <c:otherwise>
                                                    0,00 руб.
                                                </c:otherwise>
                                            </c:choose>
                                       </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="docTdBorder textPadding">Обязательный платеж</td>
                                    <td>
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${not empty card.overdraftMinimalPayment}">
                                                    ${phiz:formatAmount(card.overdraftMinimalPayment)}
                                                </c:when>
                                                <c:otherwise>
                                                    0,00 руб.
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="docTdBorder textPadding">Дата платежа</td>
                                    <td>
                                        <span class="bold">
                                            <c:if test="${not empty card.overdraftMinimalPaymentDate}">
                                                <fmt:formatDate
                                                        value="${card.overdraftMinimalPaymentDate.time}"
                                                        pattern="dd.MM.yyyy"/>
                                            </c:if>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="docTdBorder textPadding">Общая сумма задолженности на дату формирования отчета</td>
                                    <td>
                                        <span class="bold">
                                            <c:if test="${not empty card.overdraftTotalDebtSum}">
                                                ${phiz:formatAmount(card.overdraftTotalDebtSum)}
                                            </c:if>
                                        </span>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td class="docTdBorder textPadding">Название</td>
                                <td><span class="bold"><c:out value="${cardLink.name}"/></span></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>

        </tiles:put>
    </tiles:insert>
</html:form>