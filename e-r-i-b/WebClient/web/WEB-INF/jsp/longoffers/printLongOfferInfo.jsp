<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/private/longOffers/info/print">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="link" value="${form.longOfferLink}"/>
    <c:set var="longOffer" value="${form.longOffer}"/>
    <tiles:insert definition="printDoc">
        <tiles:put name="data" type="string">
            <table style="width:100%;">
                <tr>
                    <td class="labelAbstractPrint">
                        Сбербанк России ОАО
                    </td>
                </tr>
                <tr>
                    <td>${form.topLevelDepartment.name}</td>
                </tr>
                <tr>
                    <td>${form.currentDepartment.name}</td>
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
                <c:if test="${not empty longOffer.type}">
                    <tr>
                        <td>
                            Вид платежа: Оплата услуг
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty longOffer.startDate}">
                    <tr>
                        <td style="padding-top:20px;">
                            Дата начала действия: ${phiz:formatDateWithStringMonth(longOffer.startDate)}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty longOffer.endDate}">
                    <tr>
                        <td>
                            Дата окончания действия: ${phiz:formatDateWithStringMonth(longOffer.endDate)}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty form.longOfferType}">
                    <tr>
                        <td style="padding-top:20px;">
                            Тип автоплатежа (регулярной операции): ${link.executionEventType}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty longOffer.executionEventType}">
                    <tr>
                        <td>
                            Алгоритм расчета автоплатежа (регулярной операции): ${longOffer.executionEventType.description}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty longOffer.sumType}">
                    <tr>
                        <td>
                            Алгоритм расчета суммы ${longOffer.sumType.description}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty link.chargeOffAccount || not empty link.chargeOffCard}">
                    <tr>
                        <td style="padding-top:20px; padding-bottom:20px;">
                            Оплачивается с&nbsp;
                            <c:choose>
                                <c:when test="${not empty link.chargeOffAccount}">
                                    ${link.chargeOffAccount}
                                </c:when>
                                <c:otherwise>
                                    <c:set var="cardNumFull" value="${link.chargeOffCard}"/>
                                    ${phiz:getCutCardNumber(cardNumFull)}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty link.receiverName}">
                    <tr>
                        <td>
                            Наименования получателя ${link.receiverName}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty link.receiverINN}">
                    <tr>
                        <td>
                            ИНН получателя ${link.receiverINN}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty link.receiverAccount || not empty link.receiverCard}">
                    <tr>
                        <td>
                            Счет получателя
                            <c:choose>
                                <c:when test="${not empty link.receiverAccount}">
                                    ${link.receiverAccount}
                                </c:when>
                                <c:otherwise>
                                    ${phiz:getCutCardNumber(link.receiverCard)}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty link.receiverBankName}">
                    <tr>
                        <td>
                            Наименование банка получателя ${link.receiverBankName}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty link.receiverBankCorAccount}">
                    <tr>
                        <td>
                            Кор. Счет банка получателя ${link.receiverBankCorAccount}
                        </td>
                    </tr>
                </c:if>
            </table>
        </tiles:put>
    </tiles:insert>
</html:form>