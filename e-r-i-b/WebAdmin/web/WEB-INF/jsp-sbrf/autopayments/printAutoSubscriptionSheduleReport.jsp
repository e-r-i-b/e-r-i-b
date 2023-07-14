<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<html:form action="/autopayment/info/printScheduleReport">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="items" value="${form.scheduleItems}"/>
    <c:set var="payment" value="${form.autoSubscriptionInfo}"/>
	<body>   
        <table style="width:100%">
            <tr>
                <td style="font-size:11pt;text-align:center;padding-top:20px; padding-bottom: 20px; font: bold 11pt Arial;">
                    <h3>Список платежей, прошедших по подписке
                         <c:if test="${form.showPaymentForPeriod}">
                            за период с ${form.fromDateString} по ${form.toDateString}
                        </c:if>
                    </h3>
                </td>
            </tr>
            <tr>
                <td>
                    Название подписки: ${payment.friendlyName}
                </td>
            </tr>
            <tr>
                <td>
                    Получатель: ${payment.receiverName}
                </td>
            </tr>
            <%--платеж регулярный. надо отобразить переодичность --%>
             <c:if test="${payment.sumType =='FIXED_SUMMA_IN_RECIP_CURR' && payment.executionEventType != 'ON_REMAIND'}">
                <tr>
                    <td>
                        Периодичность: <bean:message key='label.field.autopay.state.${payment.executionEventType}' bundle="providerBundle"/>
                    </td>
                </tr>
            </c:if>
        </table>
        <table style="width:100%;height:400px" cellspacing="0" cellpadding="0">
        <tr height="20px"><td>&nbsp;<td/></tr>
        <tr><td align="center" valign="top">
            <table cellspacing="0" cellpadding="0" border="1px" width="100%" class="tbl">
                <tr align="center" class="titleDoc">
                    <td width="16%">Дата и время</td>
                    <td width="16%">Сумма платежа</td>
                    <td width="16%">Сумма комиссии</td>
                    <td width="16%">Общая сумма списанных средств</td>
                    <td width="16%">Статус платежа</td>
                    <td width="20%">Причина ошибки<br>(для непрошедших платежей)</td>
                </tr>
                <c:forEach items="${items}" var="item">
                    <tr align="center" class="listItem">
                        <td><c:out value="${phiz:formatDateToStringWithSlash2(item.date)}"/></td>

                        <td>
                            &nbsp;
                            <c:if test="${not empty item.summ}">
                                <bean:write name="item" property="summ" format="0.00"/>
                                <bean:message key="currency.translate.RUB" bundle="autoPaymentInfoBundle"/>
                            </c:if>
                        </td>
                        <td>
                           &nbsp;
                           <c:if test="${not empty item.commission}">
                                <bean:write name="item" property="commission" format="0.00"/>
                                <bean:message key="currency.translate.RUB" bundle="autoPaymentInfoBundle"/>
                           </c:if>
                        </td>
                        <td>
                            &nbsp;
                            <c:set var="totalSumm" value="${item.summ + item.commission}"/>
							<c:if test="${not(totalSumm == null)}">
								<bean:write name="totalSumm" format="0.00"/>
                                <bean:message key="currency.translate.RUB" bundle="autoPaymentInfoBundle"/>
							</c:if>
                        </td>
                        <td>
                            &nbsp;
                            <c:if test="${not empty item}">
                                <bean:message key="payment.autoSub.payment.state.${item.status}" bundle="paymentsBundle"/>
                            </c:if>
                        </td>
                        <td>
                            &nbsp;
                            <c:if test="${not empty item.rejectionCause}">
                                <bean:write name="item" property="rejectionCause"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td></tr>
        </table>
    </body>
</html:form>
