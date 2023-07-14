<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/private/autopayments/info/print">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="link" value="${form.link}"/>
    <c:set var="payment" value="${link.value}"/>
    <c:set var="isMock" value="${phiz:isInstance(payment, 'com.rssl.phizic.business.longoffer.autopayment.mock.MockAutoPayment')}"/>

    <tiles:insert definition="printDoc">
        <tiles:put name="data" type="string">
            <table style="width:100%">
                <tr>
                    <td class="labelAbstractPrint">
                        Сбербанк России ОАО
                    </td>
                </tr>
                <tr>
                    <td class="labelAbstractPrint">${form.topLevelDepartment.name}</td>
                </tr>
                <tr>
                    <td class="labelAbstractPrint">${form.currentDepartment.name}</td>
                </tr>
                <tr>
                    <td style="font-size:11pt;text-align:center;padding-top:20px; padding-bottom: 20px; font: bold 11pt Arial;">
                        <h3>Детальная информация по автоплатежу (регулярной операции)</h3>
                    </td>
                </tr>
                <tr>
                    <td>
                        Название автоплатежа (регулярной операции): ${link.name}
                    </td>
                </tr>
                <c:if test="${!isMock}">
                    <tr>
                        <td>
                            Вид платежа: Оплата услуг
                        </td>
                    </tr>
                    <tr>
                        <td style="padding-top:20px;">
                            Дата начала действия: ${phiz:formatDateWithStringMonth(payment.dateAccepted)}
                        </td>
                    </tr>
                    <tr>
                        <td style="padding-top:20px">
                            Тип автоплатежа (регулярной операции):&nbsp;
                                ${link.executionEventType}
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td>
                        Алгоритм расчета автоплатежа (регулярной операции): ${link.executionEventType}
                    </td>
                </tr>
                <c:if test="${!isMock}">
                    <tr>
                        <td style="padding-top:20px">
                            <c:set var="cardNumFull" value="${payment.cardNumber}"/>
                            Оплачивается с: ${phiz:getCutCardNumber(cardNumFull)}
                        </td>
                    </tr>
                    <tr>
                        <td style="padding-top:20px">
                            Наименование получателя: <bean:write name="payment" property="receiverName"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Номер телефона/счет: ${payment.requisite}
                        </td>
                    </tr>

                    <c:if test="${payment.executionEventType == 'REDUSE_OF_BALANCE'}">
                        <c:set var="amount" value="${payment.amount}"/>
                        <tr>
                            <td>
                                Сумма: <bean:write name="amount" property="decimal" format="0.00"/>&nbsp;${phiz:getCurrencySign(amount.currency.code)}
                            </td>
                        </tr>
                        <c:choose>
                            <c:when test="${payment.totalAmountPeriod != null}">
                                <c:set var="totalAmountPeriod" value="${payment.totalAmountPeriod}"/>
                                <c:set var="totalAmountLimit"  value="${payment.totalAmountLimit}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="totalAmountPeriod" value="${phiz:getProviderAutoPayTotalAmountPeriod(link)}"/>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${not empty totalAmountPeriod}">
                            <tr>
                                <td>
                                    Максимальная сумма в <bean:message key="autopay.period.max.summa.${totalAmountPeriod}" bundle="providerBundle"/>:
                                    <c:choose>
                                        <c:when test="${not empty totalAmountLimit}">
                                            <bean:write name="totalAmountLimit" property="decimal" format="0.00"/>&nbsp;${phiz:getCurrencySign(totalAmountLimit.currency.code)}
                                        </c:when>
                                        <c:otherwise>
                                            Не задана
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:if>
                    </c:if>
                </c:if>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>