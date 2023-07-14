<%--
  Created by IntelliJ IDEA.
  User: Kosyakova
  Date: 08.02.2007
  Time: 14:25:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/audit/listBusinessDocumentPrint" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${ShowBusinessDocumentListForm}"/>


<tiles:insert definition="print">
<!-- данные -->
<tiles:put name="data" type="string">
<div style="width:160mm; font-size:9pt;" >
		<table cellpadding="0" cellspacing="0" class="textDoc" style="width:100%; font-size:10pt;">
		<!-- Шапка документа -->
			<tr>
			   <c:set var="fromDate" value="${form.filters.fromDate}"/>
			   <c:set var="toDate" value="${form.filters.toDate}"/>
               <td align="center">
	                Журнал операций по системе<br/>
	               <c:choose>
		               <c:when test="${fromDate == toDate}" >
                           за &nbsp;&ldquo;<input value="<bean:write name="toDate" format="dd"/>" type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
                           <input id='monthStr1' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value="<bean:write name="toDate" format="yy"/>" type="Text" readonly="true" class="insertInput font11" style="width:4%">г.
			               <script>
					           document.getElementById('monthStr1').value = monthToStringOnly('<bean:write name="form" property="filter(fromDate)"/>');
			               </script>				        
		               </c:when>
		               <c:otherwise>
                           за период<br/> c &nbsp;&ldquo;<input value="<bean:write name="fromDate" format="dd"/>" type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
                           <input id='monthStr6' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value="<bean:write name="fromDate" format="yy"/>" type="Text" readonly="true" class="insertInput font11" style="width:4%">г.
			               <br/>
                           по&ldquo;<input value="<bean:write name="toDate" format="dd"/>" type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
                           <input id='monthStr7' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value="<bean:write name="toDate" format="yy"/>" type="Text" readonly="true" class="insertInput font11" style="width:4%">г.
                           <script>
                               document.getElementById('monthStr6').value = monthToStringOnly('<bean:write name="fromDate" format="dd.MM.yyyy"/>');
                               document.getElementById('monthStr7').value = monthToStringOnly('<bean:write name="toDate" format="dd.MM.yyyy"/>');
			               </script>
		               </c:otherwise>
	               </c:choose>
	            </td>
			</tr>
		</table>
<br/>

<br>
<br>

<c:choose>
<c:when test="${empty form.data}">
    <table cellspacing="2" cellpadding="0" style="margin:10px;">
				<tr>
					<td class="messageTab" align="center">Не найдено ни одной операции за данный период!</td>
				</tr>
	</table>

</c:when>
<c:otherwise>

<table class="borderTable" id="tbl" cellpadding="0" cellspacing="0">
<tr>
    <th class="docTdBorder" style="width:20mm; text-align:center;"><p>Дата создания</p></th>
    <th class="docTdBorder" style="width:15mm; text-align:center;"><p>Время создания</p></th>
    <th class="docTdBorder" style="width:15mm; text-align:center;"><p>Номер</p></th>
    <th class="docTdBorder" style="width:25mm; text-align:center;"><p>Вид операции</p></th>
    <th class="docTdBorder" style="width:25mm; text-align:center;"><p>Статус</p></th>
    <th class="docTdBorder" style="width:30mm; text-align:center;"><p>Дата изменения статуса</p></th>
    <th class="docTdBorder" style="width:30mm; text-align:center;"><p>ФИО клиента</p></th>
    <th class="docTdBorder" style="width:35mm; text-align:center;"><p>Счет списания</p></th>
    <th class="docTdBorder" style="width:35mm; text-align:center;"><p>Счет зачисления</p></th>
    <th class="docTdBorder" style="width:15mm; text-align:center;"><p>Сумма</p></th>
    <th class="docTdBorder" style="width:15mm; text-align:center;"><p>Валюта</p></th>
    <th class="docTdBorder" style="width:30mm; text-align:center;"><p>Получатель</p></th>
    <th class="docTdBorder" style="width:30mm; text-align:center;"><p>Подтверждение паролем</p></th>
    <th class="docTdBorder" style="width:30mm; text-align:center;"><p>Номер операции в БС</p></th>
</tr>
<c:forEach var="listElement" items="${form.data}">
    <c:set var="businessDocument" value="${listElement[0]}"/>
    <c:set var="form" value="${listElement[1]}"/>
    <c:set var="sender" value="${listElement[2]}"/>

    <tr>
        <td class="docTdBorder" style="text-align:center;">
            <p>
            <c:if test="${! empty businessDocument.dateCreated}">
                <bean:write name="businessDocument" property="dateCreated.time" format="dd.MM.yyyy"/>
            </c:if>
            </p>
        </td>
        <td class="docTdBorder" style="text-align:center;">
            <p>
            <c:if test="${! empty businessDocument.dateCreated}">
                <bean:write name="businessDocument" property="dateCreated.time" format="HH:mm:ss"/>
            </c:if>
            </p>
        </td>
        <td class="docTdBorder" style="text-align:center;">
            <p>
            ${businessDocument.documentNumber}
            </p>
        </td>

        <c:set var="isLongOffer" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') && businessDocument.longOffer}"/>
        <c:set var="isAutoSubscription" value="${businessDocument.formName == 'RurPayJurSB' && isLongOffer}"/>

        <td class="docTdBorder" style="text-align:center;">
            <p>
                <c:if test="${isLongOffer}">
                    <img src="${skinUrl}/images/calendar.gif" alt="" border="0"/>
                </c:if>
                <c:choose>
                    <c:when test="${isAutoSubscription}">
                        Создание автоплатежа (регулярной операции)
                    </c:when>
                    <c:when test="${businessDocument.formName == 'RurPayment'}">
                        <c:choose>
                            <c:when test="${businessDocument.receiverSubType == 'externalAccount'}">
                                Перевод частному лицу в другой банк по реквизитам
                            </c:when>
                            <c:when test="${businessDocument.receiverSubType == 'ourCard' or businessDocument.receiverSubType == 'ourAccount' or  businessDocument.receiverSubType == 'ourPhone' or  businessDocument.receiverSubType == 'social'}">
                                Перевод клиенту Сбербанка
                            </c:when>
                            <c:otherwise>
                                Перевод на карту в другом банке
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <bean:message bundle="auditBundle" key="paymentform.${businessDocument.formName}" failIfNone="false"/>
                    </c:otherwise>
                </c:choose>
            </p>
        </td>

        <td class="docTdBorder" style="text-align:center;">
            <p>
            <c:set var="code" value="${businessDocument.state.code}"/>
            <c:set var="isInstance" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.DefaultClaim') || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.DepositOpeningClaim') || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.LoanClaim')}"/>

            <c:choose>
                <c:when test="${code=='INITIAL' || code == 'SAVED'}">Введен</c:when>
                <c:when test="${code=='DRAFT'}">Частично заполнен</c:when>
                <c:when test="${code=='PARTLY_EXECUTED'}">Обрабатывается</c:when>
                <c:when test="${code=='WAIT_CONFIRM'}">Ожидает дополнительной обработки</c:when>
                <c:when test="${code=='ADOPTED'}">Принята</c:when>
                <c:when test="${code=='DELETED'}">Удален</c:when>
                <c:when test="${code=='EXECUTED'}">
                    Исполнен
                </c:when>
                <c:when test="${code=='REFUSED'}">
                    Отказан
                </c:when>

                <c:when test="${code=='RECALLED'}">
                    Отозван
                </c:when>
                <c:when test="${code=='ACCEPTED'}">
                    Одобрен
                </c:when>
                <c:when test="${code=='SUCCESSED'}">
                    Выдан
                </c:when>
                <c:when test="${code=='MODIFICATION'}">
                    Изменён
                </c:when>
                <c:when test="${code=='RETURNED'}">
                    Возвращен
                </c:when>
                <c:when test="${code=='APPROVED'}">
                    Утвержден
                </c:when>
                <c:when test="${isInstance && ((code=='SENDED') || (code=='DISPATCHED'||code=='STATEMENT_READY'))}">
                    Принят
                </c:when>
                <c:when test="${code=='CANCELATION'}">
                    Аннулирован
                </c:when>
                <c:when test="${code=='CANCELATION'}">
                    В рассмотрении
                </c:when>
                <c:when test="${code=='CONSIDERATION'}">
                    Аннулирован
                </c:when>
                <c:when test="${not isInstance && ((code=='SENDED') || (code=='DISPATCHED'||code=='STATEMENT_READY'))}">
                    Обрабатывается
                </c:when>
                <c:when test="${code=='COMPLETION'}">
                    Требуется доработка
                </c:when>
                <c:when test="${code=='RECEIVED'}">
                    Получен банком получателем
                </c:when>
                <c:when test="${code=='DELAYED_DISPATCH'}">
                    Ожидается обработка
                </c:when>
                <c:when test="${code=='ERROR' || code=='UNKNOW' || code=='SENT'}">
                    Приостановлен
                </c:when>
                <c:when test="${code=='BILLING_CONFIRM_TIMEOUT'}">
                    Таймаут при подтверждении в биллинге (ЕРИБ)
                </c:when>
                <c:when test="${code=='BILLING_GATE_CONFIRM_TIMEOUT'}">
                    Таймаут при подтверждении в биллинге (шлюз)
                </c:when>
                <c:when test="${code=='ABS_RECALL_TIMEOUT'}">
                    Таймаут при отзыве в АБС (ЕРИБ)
                </c:when>
                 <c:when test="${code=='ABS_GATE_RECALL_TIMEOUT'}">
                    Таймаут при отзыве в АБС (шлюз)
                </c:when>
                <c:otherwise>&nbsp;</c:otherwise>
            </c:choose>
            </p>
        </td>

        <td class="docTdBorder" style="text-align:center;">
            <p>
            <c:if test="${! empty businessDocument.changed}">
                <bean:write name="businessDocument" property="changed" format="dd.MM.yyyy"/>
                <bean:write name="businessDocument" property="changed" format="HH:mm:ss"/>
            </c:if>
            </p>
        </td>

        <td class="docTdBorder" style="text-align:center;">
            <p>
                <bean:write name="form" property="surName"/>
                <bean:write name="form" property="firstName"/>
                <bean:write name="form" property="patrName"/>
            </p>
        </td>
        <td class="docTdBorder" style="text-align:center;">
            <p>
            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
                 <c:out value="${phiz:getCutCardNumber(businessDocument.chargeOffAccount)}"/>
                    <c:if test="${not empty businessDocument.chargeOffAccount}">
                        <c:set var="paymentOperationNumber" value="${paymentOperationNumber + 1}"/>
                    </c:if>
            </c:if>
            </p>
        </td>
        <td class="docTdBorder" style="text-align:center;">
            <p>
            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                    <c:out value="${businessDocument.receiverAccount}"/>
            </c:if>
            </p>
        </td>
        <td class="docTdBorder" style="text-align:center;">
            <p>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
                    <c:choose>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.gate.longoffer.autopayment.AutoPayment') && businessDocument.executionEventType == 'BY_INVOICE'}">
                            ${phiz:formatAmount(businessDocument.floorLimit)}
                        </c:when>
                        <c:when test="${!empty (businessDocument.chargeOffAmount)}">
                            <c:set var="docTotalAmount"
                                   value="${businessDocument.chargeOffAmount.decimal+businessDocument.commission.decimal}"/>
                            <bean:write name="docTotalAmount" format="0.00"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="docTotalAmount"
                                   value="${businessDocument.destinationAmount.decimal+businessDocument.commission.decimal}"/>
                            <bean:write name="docTotalAmount" format="0.00"/>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </p>
        </td>

        <td class="docTdBorder" style="text-align:center;">
            <p>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
                    <c:choose>
                        <c:when test="${businessDocument.chargeOffAmount != null}">
                            <bean:write name="businessDocument" property="chargeOffAmount.currency.code"/>
                        </c:when>
                        <c:when test="${businessDocument.destinationAmount != null}">
                            <bean:write name="businessDocument" property="destinationAmount.currency.code"/>
                        </c:when>
                    </c:choose>
                </c:if>
            </p>
        </td>
        <td class="docTdBorder" style="text-align:center;">
            <p>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                    <c:out value="${businessDocument.receiverName}"/>
                </c:if>
            </p>
        </td>
        <td class="docTdBorder" style="text-align:center;">
            <p>
            <c:set var="strategy" value="${phiz:getConfirmStrategyType(businessDocument)}"/>
            <c:choose>
                <c:when test="${strategy == 'none'}">
                    <bean:message bundle="auditBundle" key="label.confirm.none"/>
                </c:when>
                <c:when test="${strategy == 'sms'}">
                    <bean:message bundle="auditBundle" key="label.confirm.sms"/>
                </c:when>
                <c:when test="${strategy == 'card'}">
                    <bean:message bundle="auditBundle" key="label.confirm.card"/>
                </c:when>
                <c:when test="${strategy == 'cap'}">
                    <bean:message bundle="auditBundle" key="label.confirm.cap"/>
                </c:when>
                <c:when test="${strategy == 'need'}">
                    <bean:message bundle="auditBundle" key="label.confirm.need"/>
                </c:when>
                <c:when test="${strategy == 'push'}">
                    <bean:message bundle="auditBundle" key="label.confirm.push"/>
                </c:when>
                <c:otherwise></c:otherwise>
            </c:choose>
            </p>
        </td>
        <td class="docTdBorder" style="text-align:center;">
            <p>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.JurPayment')}">
                    <c:out value="${businessDocument.billingDocumentNumber}"/>
                </c:if>
            </p>
        </td>
    </tr>

</c:forEach>

</table>
</c:otherwise>
</c:choose>         
</div>
    <script type="text/javascript">
        doOnLoad(function()
        {
            window.print();
        });
    </script>

</tiles:put>

</tiles:insert>
</html:form>
