<%--
  User: bogdanov
  Date: 08.02.2012
  Time: 14:30:10
  отображение детальной информации по подписке на автоплатеж.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<tiles:importAttribute/>
<script type="text/javascript">
    function HintBlock()
    {
       var autoPaymentHint = $('#autoPaymentStatus');
       var parentText = $('.hintText');
       autoPaymentHint.css("margin-left", parentText.width()/2 - autoPaymentHint.width()/2);
    }
</script>

<c:if test="${not empty payment}">

    <c:set var="bank" value="${payment.receiverBank}"/>
    <fieldset>
        <table class="additional-product-info">
            <tr>
                <td colspan="2">
                    <h2 class="additionalInfoHeader fontForH2">Получатель</h2>
                </td>
            </tr>

            <tr>
                <c:choose>
                    <c:when test="${autosubscriptionType == 'autoPayment'}">
                        <td class="text-align-right field">Наименование:</td>
                        <td><span class="bold"><c:out value="${payment.receiverName}"/></span></td>
                    </c:when>
                    <c:when test="${autosubscriptionType == 'autoTransfer'}">
                        <td class="text-align-right field">Получатель:</td>
                            <td><span class="bold">
                                <c:if test="${payment.type.simpleName == 'ExternalCardsTransferToOurBankLongOffer' || payment.type.simpleName == 'ExternalCardsTransferToOtherBankLongOffer'}">
                                    <c:out value="${payment.receiverName}"/>
                                    <br/>
                                </c:if>
                                <c:if test="${not empty payment.receiverCard}">
                                    <c:out value="${phiz:getCutCardNumber(payment.receiverCard)}"/>
                                </c:if>
                            </span></td>
                    </c:when>
                </c:choose>
            </tr>
            <c:if test="${autosubscriptionType == 'autoPayment'}">
                <c:if test="${not empty payment.service.name}">
                    <tr>
                        <td class="text-align-right field">Услуга:</td>
                        <td><span class="bold"><c:out value="${payment.service.name}"/></span></td>
                    </tr>
                </c:if>
                <c:if test="${not payment.notVisibleBankDetails}">
                    <tr>
                        <td class="text-align-right field">ИНН:</td>
                        <td><span class="bold"><c:out value="${payment.receiverINN}"/></span></td>
                    </tr>
                    <tr>
                        <td class="text-align-right field">Счет:</td>
                        <td><span class="bold"><c:out value="${payment.receiverAccount}"/></span></td>
                    </tr>
                    <c:if test="${not empty payment.receiverKPP}">
                        <tr>
                            <td class="text-align-right field">КПП:</td>
                            <td><span class="bold"><c:out value="${payment.receiverKPP}"/></span></td>
                        </tr>
                    </c:if>

                    <tr>
                        <td colspan="2">
                            <h2 class="additionalInfoHeader fontForH2">Банк получателя</h2>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-align-right field">Наименование:</td>
                        <td>
                            <span class="bold">
                                <c:if test="${ not empty bank}">
                                    <c:out value="${bank.name}"/>
                                </c:if>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-align-right field">БИК:</td>
                        <td>
                            <span class="bold">
                                <c:if test="${ not empty bank}">
                                    <c:out value="${bank.BIC}"/>
                                </c:if>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-align-right field">Корсчет:</td>
                        <td>
                            <span class="bold">
                                <c:if test="${ not empty bank}">
                                    <c:out value="${bank.account}"/>
                                </c:if>
                            </span>
                        </td>
                    </tr>
                </c:if>
            </c:if>
            <tr>
                <td colspan="2">
                    <h2 class="additionalInfoHeader fontForH2">Плательщик</h2>
                </td>
            </tr>
            <tr>
                <td class="text-align-right field"  width="254">Списать со счета:</td>
                <td>
                    <span class="bold word-wrap">
                        <c:if test="${ not empty cardLink}">
                            <c:out value="${phiz:getCutCardNumber(payment.chargeOffCard)}"/>&nbsp;
                            <c:out value="${cardLink.name}"/>&nbsp;<c:out value="${phiz:getCurrencySign(cardLink.rest.currency.code)}"/>
                        </c:if>
                    </span>
                </td>
            </tr>

            <c:if test="${not empty payment.extendedFields or not empty payment.executionDate}">
                <tr>
                    <td colspan="2">
                        <h2 class="additionalInfoHeader fontForH2">Детали платежа</h2>
                    </td>
                </tr>
            </c:if>

            <c:if test="${not empty payment.autopayNumber}">
                <tr>
                    <td class="text-align-right field">Номер автоплатежа:</td>
                    <td><span class="bold"><c:out value="${payment.autopayNumber}"/></span></td>
                </tr>
            </c:if>

            <c:if test="${not empty payment.executionDate}">
                <tr>
                    <td class="text-align-right field">Дата операции:</td>
                    <td><span class="bold"><c:out value="${phiz:formatDateDependsOnSysDate(payment.executionDate, true, false)}"/></span></td>
                </tr>
                <c:if test="${not empty payment.amount}">
                    <tr>
                        <td class="text-align-right field">Сумма операции:</td>
                        <td><span class="bold summ"><c:out value="${phiz:formatAmount(payment.amount)}"/></span></td>
                    </tr>
                </c:if>
                <c:if test="${not empty payment.commission}">
                    <tr>
                        <td class="text-align-right field">Комиссия:</td>
                        <td><span class="bold"><c:out value="${phiz:formatAmount(payment.commission)}"/></span></td>
                    </tr>
                </c:if>
            </c:if>

            <c:if test="${ not empty payment.extendedFields}">
                <c:forEach items="${payment.extendedFields}" var="field">
                    <c:if test="${field.visible}">
                        <tr>
                            <td class="text-align-right field" style="vertical-align:top">${field.name}:</td>
                            <td>
                            <c:choose>
                                <c:when test="${not empty field.value}">
                                    <c:set var="fieldValue" value="${field.value}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="fieldValue" value="${field.defaultValue}"/>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${field.type == 'money'}">
                                    <span class="bold"><c:out value="${fieldValue}"/>&nbsp;руб.</span>
                                </c:when>
                                <c:when test="${field.type == 'set'}">
                                    <c:forTokens delims="@" var="value" items="${fieldValue}">
                                        <span class="bold"><c:out value="${value}"/></span>
                                        <br/>
                                    </c:forTokens>
                                </c:when>
                                <c:otherwise>
                                   <span class="bold"><c:out value="${fieldValue}"/></span>
                                </c:otherwise>
                            </c:choose>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:if>

            <c:if test="${not empty payment.executionDate}">
                <tr>
                    <td class="text-align-right field">Статус:</td>
                    <td><span class="bold">Исполнен</span></td>
                </tr>
            </c:if>

            <c:if test="${empty payment.executionDate}">
                <tr>
                    <td colspan="2">
                        <h2 class="additionalInfoHeader fontForH2">Детали автоплатежа</h2>
                    </td>
                </tr>
                <tr>
                    <td class="text-align-right field">Дата регистрации:</td>
                    <td>
                        <span class="bold">
                            <c:if test="${ not empty payment.startDate }">
                                <bean:write name="payment" property="startDate.time" format="dd.MM.yyyy" filter="true"/>
                            </c:if>
                        </span>
                    </td>
                </tr>
                <c:if test="${not empty payment.updateDate}">
                    <tr>
                        <td class="text-align-right field">Дата изменения:</td>
                        <td>
                            <span class="bold">
                                <bean:write name="payment" property="updateDate.time" format="dd.MM.yyyy" filter="true"/>
                            </span>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="text-align-right field">Наименование автоплатежа:</td>
                    <td><span class="bold word-wrap"><c:out value="${payment.friendlyName}"/></span></td>
                </tr>
                <tr>
                    <td class="text-align-right field">Тип автоплатежа:</td>
                    <td>
                        <span class="bold">
                            <c:choose>
                                <c:when test="${payment.executionEventType == 'ON_REMAIND'}">
                                    <bean:message key='label.field.autopay.border' bundle='providerBundle'/>
                                </c:when>
                                <c:when test="${payment.sumType =='BY_BILLING'}">
                                    <bean:message key='label.field.autopay.invoice' bundle='providerBundle'/>
                                </c:when>
                                <c:when test="${payment.sumType =='FIXED_SUMMA_IN_RECIP_CURR' && payment.executionEventType != 'ON_REMAIND'}">
                                    <bean:message key='label.field.autopay.always' bundle='providerBundle'/>
                                </c:when>
                            </c:choose>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td class="text-align-right field">Оплачивать:</td>
                    <td>
                        <span class="bold">
                            <bean:message key='label.field.autopay.state.${payment.executionEventType}' bundle='providerBundle'/>
                        </span>
                    </td>
                </tr>

                <c:choose>
                    <c:when test="${payment.sumType=='FIXED_SUMMA_IN_RECIP_CURR' && payment.executionEventType!='ON_REMAIND'}">
                        <tr>
                            <td class="text-align-right field">Дата ближайшего платежа:</td>
                            <td>
                                <span class="bold">
                                    <c:if test="${ not empty payment.nextPayDate }">
                                        <bean:write name="payment" property="nextPayDate.time" format="dd.MM.yyyy" filter="true"/>
                                    </c:if>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-align-right field">Сумма:</td>
                            <td><span class="bold"><c:out value="${phiz:formatAmount(payment.amount)}"/></span></td>
                        </tr>
                    </c:when>
                    <c:when test="${payment.sumType=='BY_BILLING'}">
                        <tr>
                            <td class="text-align-right field">Ожидаемая дата оплаты счета:</td>
                            <td>
                                <span class="bold">
                                    <c:if test="${ not empty payment.nextPayDate }">
                                        <bean:write name="payment" property="nextPayDate.time" format="dd.MM.yyyy" filter="true"/>
                                    </c:if>
                                </span>
                            </td>
                        </tr>
                        <c:if test="${not empty payment.maxSumWritePerMonth}">
                            <tr>
                                <td class="text-align-right field">Максимальный размер платежа:</td>
                                <td><span class="bold"><c:out value="${phiz:formatAmount(payment.maxSumWritePerMonth)}"/></span></td>
                            </tr>
                        </c:if>
                    </c:when>
                </c:choose>

                <tr>
                    <td class="text-align-right field"><br><br></td>
                    <td></td>
                </tr>

                <tr>
                    <td class="text-align-right field">Статус автоплатежа:</td>
                    <td>
                        <c:if test="${ not empty payment.autoPayStatusType}">
                            <script type="text/javascript">
                                var textForHint = "<div class=\"floatMessageHeader\"></div><div class=\"layerFonBlock\">${payment.reasonDescription}</div>";
                            </script>
                            <div id="stateDescriptionAutoPayment" class="hintText" style="width:40px;"
                                 onmouseover="showHint('stateDescriptionAutoPayment', 'autoPaymentStatus', 'pointer', textForHint); HintBlock()"
                                 onmouseout="hideLayer('autoPaymentStatus');">
                                <c:out value="${payment.autoPayStatusType.description}"/>
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:if>

            <c:if test="${not empty paymentGraphicMenu}">
                ${paymentGraphicMenu}
            </c:if>
        </table>
    </fieldset>
</c:if>
<div id="autoPaymentStatus" class="layerFon hintWindow autoPaymentHint">
</div>