<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/private/autopayments/info/printScheduleReport">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="link" value="${form.link}"/>
    <c:set var="payment" value="${link.value}"/>
    <c:set var="items" value="${form.scheduleItems}"/>
    <c:set var="isMock" value="${phiz:isInstance(payment, 'com.rssl.phizic.business.longoffer.autopayment.mock.MockAutoPayment')}"/>

    <tiles:insert definition="printDoc">
        <tiles:put name="data" type="string">
            <c:if test="${link != null}">
                <table style="width:100%">
                    <tr>
                        <td class="labelAbstractPrint">
                            Сбербанк России ОАО
                        </td>
                    </tr>
                    <tr>
                        <td class="labelAbstractPrint">
                            ${form.topLevelDepartment.name}
                        </td>
                    </tr>
                    <tr>
                        <td class="labelAbstractPrint">
                            ${form.currentDepartment.name}
                        </td>
                    </tr>
                    <tr>
                        <td style="font-size:11pt;text-align:center;padding-top:20px; padding-bottom: 20px; font: bold 11pt Arial;">
                            <h3>График платежей за период с ${form.fromDateString} по ${form.toDateString}</h3>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Название автоплатежа (регулярной операции): ${link.name}
                        </td>
                    </tr>
                </table>
                <br>
                <table cellpadding="0" cellspacing="0" style="width:70%">
                    <tr class="tblInfHeaderAbstrPrint">
                        <td class="docTdTopBorder" style="text-align:center;">Дата платежа</td>
                        <td class="docTdTopBorder" style="text-align:center;">Сумма платежа</td>
                        <td class="docTdTopBorder" style="text-align:center;">Статус</td>
                    </tr>
                    <c:forEach items="${items}" var="item">
                        <tr>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                ${phiz:formatDateWithStringMonth(item.date)}
                            </td>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <c:if test="${not empty payment.amount}">
                                    ${item.amount.decimal}&nbsp;${phiz:getCurrencySign(payment.amount.currency.code)}
                                </c:if>
                            </td>
                            <td class="docTdBorderSecond textPadding" style="text-align:left;">
                                <c:choose>
                                    <c:when test="${item.state == 'SUCCESS'}">исполнен</c:when>
                                    <c:otherwise>отказан</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>