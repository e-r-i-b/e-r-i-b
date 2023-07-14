<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/claims/claim">
	<c:set var="form"  value="${ViewDocumentForm}"/>

    <c:set var="isERKC" value="${phiz:impliesOperation('ViewDocumentOperation', 'ViewPaymentListUseClientForm') and ! phiz:impliesOperation('ViewDocumentOperation', 'ViewPaymentList')}"/>

    <c:choose>
        <c:when test="${not isERKC}">
            <c:set var="layoutDefinition" value="auditMain"/>
        </c:when>
        <c:otherwise>
            <c:set var="layoutDefinition" value="erkcMain"/>
        </c:otherwise>
    </c:choose>
	<tiles:insert definition="${layoutDefinition}">
        <c:set var="formName" value="${form.metadata.name}"/>
        <c:set var="isLongOffer" value="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') and form.document.longOffer}"/>
        <c:set var="isAutoSub"      value="${isLongOffer && formName == 'RurPayJurSB'}"/>
        <c:set var="isEditAutoSub"   value="${formName == 'EditAutoSubscriptionPayment'}"/>
        <c:set var="isRemoteConnectionUDBOClaim"   value="${formName == 'RemoteConnectionUDBOClaim'}"/>
        <c:set var="isDelayAutoSub"  value="${formName == 'DelayAutoSubscriptionPayment' || formName == 'RefuseMoneyBoxPayment'}"/>
        <c:set var="isCloseAutoSub"  value="${formName == 'CloseAutoSubscriptionPayment' || formName == 'CloseMoneyBoxPayment'}"/>
        <c:set var="isRecoveryAutoSub"  value="${formName == 'RecoveryAutoSubscriptionPayment' || formName == 'RecoverMoneyBoxPayment'}"/>
        <c:set var="isInvoiceSubscriptionClaim" value="${formName =='RecoveryInvoiceSubscriptionClaim'
                                                        || formName =='DelayInvoiceSubscriptionClaim'
                                                        || formName =='CreateInvoiceSubscriptionPayment'
                                                        || formName =='CloseInvoiceSubscriptionClaim'}"/>
        <c:choose>
            <c:when test="${isInvoiceSubscriptionClaim}">
                <c:set var="autoSubscriptionTitle" value="empty"/>
            </c:when>
            <c:when test="${isEditAutoSub}">
                <c:set var="autoSubscriptionTitle" value="Заявка на редактирование автоплатежа"/>
            </c:when>
            <c:when test="${isAutoSub}">
                <c:set var="autoSubscriptionTitle" value="Заявка на оформление автоплатежа"/>
            </c:when>
            <c:when test="${isDelayAutoSub}">
                <c:set var="autoSubscriptionTitle" value="Заявка на приостановку выполнения автоплатежа"/>
            </c:when>
            <c:when test="${isCloseAutoSub}">
                <c:set var="autoSubscriptionTitle" value="Заявка на отключение автоплатежа"/>
            </c:when>
            <c:when test="${isRecoveryAutoSub}">
                <c:set var="autoSubscriptionTitle" value="Заявка на возобновление выполнения автоплатежа"/>
            </c:when>
            <c:otherwise>
                <c:set var="autoSubscriptionTitle" value="empty"/>
            </c:otherwise>
        </c:choose>

        <c:set var="auditBackUrl" value="/audit/businessDocument.do?status=admin&needLoadData=true"/>
        <c:if test="${isERKC}">
            <c:set var="auditBackUrl" value="/erkc/audit/businessDocument.do?needLoadData=true"/>
        </c:if>
		<tiles:put name="menu" type="string">
			<script type="text/javascript">
                function openMessageLogForDocument()
                {
                    <c:choose>
                        <c:when test="${phiz:impliesOperation('MessageLogOperation', 'MessageLogService') or
                                        phiz:impliesOperation('MessageLogOperation', 'MessageLogServiceEmployee')}">
                            openMessageLogList(getElementValue("operationUID"));
                        </c:when>
                        <c:otherwise>
                            openMessageLogListForERKC(getElementValue("operationUID"));
                        </c:otherwise>
                    </c:choose>
                }

				function selectPrintedDocuments(event)
				{
					var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
					             ", width=400" +
					             ", height=400" +
					             ", left=0" + ((screen.width) / 2 - 100) +
					             ", top=0" + ((screen.height) / 2 - 100);
					var pwin = openWindow(event, '${phiz:calculateActionURL(pageContext,'/payments/check_print.do')}?id=${form.id}', "dialog", winpar);
					pwin.focus();
				}

                function confirmPrint(event)
                 {
                    var h = 600;
                    var w = 500;
                    var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
                              ", width=" + w + ", height=" + h +
                              ", left=" + (getClientWidth() - w) / 2 + ", top=" + (getClientHeight() - h) / 2;
                    var win = openWindow(null, "${phiz:calculateActionURL(pageContext, '/loans/claims/claim/print.do')}?id=${form.id}", "dialog", winpar);
                    win.focus();
                 }

                function openConditionsWindow() {
                    win.open('offerTextWin');
                }
			</script>
            <c:if test="${form.form=='LoanClaim'}">

                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.accept"/>
                    <tiles:put name="commandHelpKey" value="button.accept.help"/>
                    <tiles:put name="bundle"         value="loansBundle"/>
                    <tiles:put name="image"          value=""/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.completion"/>
                    <tiles:put name="commandHelpKey" value="button.completion.help"/>
                    <tiles:put name="bundle"         value="loansBundle"/>
                    <tiles:put name="confirmText"    value="Вы действительно хотите отправить на редактирование клиенту заявку?"/>
                    <tiles:put name="image"          value=""/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>

                <c:if test="${form.comment}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"     value="button.comment"/>
                        <tiles:put name="commandHelpKey" value="button.comment.help"/>
                        <tiles:put name="bundle"         value="loansBundle"/>
                        <tiles:put name="image"          value=""/>
                        <tiles:put name="action"         value="/claims/comment.do?claimIds=${form.id}"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>

                <c:if test="${form.archive}">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.archive"/>
                        <tiles:put name="commandHelpKey" value="button.archive.help"/>
                        <tiles:put name="bundle"         value="loansBundle"/>
                        <tiles:put name="image"          value=""/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>

                <c:if test="${form.printDocuments}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.print"/>
                        <tiles:put name="commandHelpKey" value="button.print.help"/>
                        <tiles:put name="bundle" value="loansBundle"/>
                        <tiles:put name="image" value=""/>
                        <tiles:put name="onclick">confirmPrint()</tiles:put>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:if>
            </c:if>

            <tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.back"/>
				<tiles:put name="commandHelpKey" value="button.back"/>
				<tiles:put name="bundle"         value="claimsBundle"/>
                <tiles:put name="action"         value="${auditBackUrl}"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

        <tiles:put name="data" type="string">
            <input type="hidden" name="id" value="${form.document.id}"/>
            <c:set var="document" value="${form.document}"/>
            <input type="hidden" name="operationUID" value="${document.operationUID}"/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="audit_document"/>
                <c:choose>
                    <c:when test="${autoSubscriptionTitle != 'empty'}">
                        <tiles:put name="name" value="${autoSubscriptionTitle}"/>
                    </c:when>
                    <c:when test="${isLongOffer && !isInvoiceSubscriptionClaim}">
                        <tiles:put name="name" value="РЕГУЛЯРНЫЙ ПЛАТЕЖ"/>
                    </c:when>
                    <c:when test="${isInvoiceSubscriptionClaim}">
                        <tiles:put name="name"><bean:message bundle="auditBundle" key="paymentform.${formName}" failIfNone="false"/></tiles:put>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="name" value="${form.metadata.form.description}"/>
                    </c:otherwise>
                </c:choose>
                <tiles:put name="data">
                    ${form.html}

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Ф.И.О. клиента:
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <b><c:out value="${form.owner.fullName}"/></b>
                        </tiles:put>
                    </tiles:insert>

                    <c:set var="employeeInfo" value="${phiz:getCreatedEmployeeInfo(document)}"/>
                    <c:if test="${employeeInfo != null and employeeInfo.personName != null}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Ф.И.О. сотрудника:
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <b><c:out value="${employeeInfo.personName.fullName}"/></b>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Идентификатор:
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <b><c:out value="${form.owner.id}"/></b>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Офис банка:
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <b><c:out value="${form.department.name}"/></b>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${not empty form.document.state.code and form.document.state.code =='REFUSED'
                                    and not empty form.document.refusingReason
                                    and (phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.LoanClaimBase')
                                    or phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.VirtualCardClaim'))}">

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Статус:
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <b><c:out value="${form.document.refusingReason}"/></b>
                            </tiles:put>
                        </tiles:insert>

                    </c:if>

                    <c:if test="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.BusinessDocumentBase')}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Дата изменения статуса:
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <b>
                                    <bean:write name="document" property="changed" format="dd.MM.yyyy"/>
                                    <bean:write name="document" property="changed" format="HH:mm:ss"/>
                                </b>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>

                    <c:if test="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.LoanCardClaim')}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">Тип заявки:</tiles:put>
                            <tiles:put name="data">
                                <c:choose>
                                    <c:when test="${document.preapproved == true}">
                                        <b>П/П</b>
                                    </c:when>
                                    <c:otherwise>
                                        <b>Lead</b>
                                    </c:otherwise>
                                </c:choose>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">Канал подачи:</tiles:put>
                            <tiles:put name="data">
                                <b>
                                </b>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                        </tiles:insert>

                        <c:if test="${not empty document.statusHistory}">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title" value="История статусов:"/>
                                <tiles:put name="data">
                                    <table border="0" cellspacing="2">
                                        <tr>
                                            <td>
                                                <b>Дата</b>
                                            </td>
                                            <td>
                                                <b>Статус</b>
                                            </td>
                                        </tr>
                                        <tbody>
                                            <c:forEach var="element" items="${document.statusHistory}">
                                            <tr>
                                                <td>
                                                    <b>${phiz:formatDateToStringOnPattern(element.statusChangedDate, 'dd.MM.yyyy')}</b>
                                                </td>
                                                <td>
                                                    <b>
                                                    <c:choose>
                                                        <c:when test="${element.statusCode eq 'DISPATCHED'}">Обрабатывается</c:when>
                                                        <c:when test="${element.statusCode eq 'REFUSED'}">Принята</c:when>
                                                        <c:when test="${element.statusCode eq 'ADOPTED'}">Отказана</c:when>
                                                        <c:when test="${element.statusCode eq 'DELETED'}">Удалена</c:when>
                                                    </c:choose>
                                                    </b>
                                                </td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </tiles:put>
                                <tiles:put name="needMargin" value="false"/>
                            </tiles:insert>
                        </c:if>
                    </c:if>

                    <c:if test="${isRemoteConnectionUDBOClaim}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                IP-адрес:
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <b><c:out value="${form.document.ipAddress}"/></b>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Тип подтверждения:
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <b><c:out value="${form.document.confirmStrategyType}"/></b>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                номер карты входа:
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <b><c:out value="${form.document.cardNumber}"/></b>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                номер УДБО:
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <b><c:out value="${form.document.EDBOOrderNumber}"/></b>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>

                    <c:if test="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.RurPayment') && document.fundPayment}">
                        <script>
                            function openAdditionalInform()
                            {
                                var winpar = "resizable=1,menubar=1,toolbar=0"+
                                        ",width=540" +
                                        ",height=440" +
                                        ",left=0" + ((screen.width) / 2 - 450) +
                                        ",top=0" + 100;
                                openWindow(null, '${phiz:calculateActionURL(pageContext,'/fund/fundRequestInformation')}?id=${document.fundResponseId}', 'fundAdditionalInform', winpar);
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="onclick" value="openAdditionalInform()"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="commandHelpKey" value="fund.button.additionalInform.help"/>
                            <tiles:put name="commandTextKey" value="fund.button.additionalInform.text"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.ExtendedLoanClaim')}">
                        <c:set var="confirmedOfferText" value="${phiz:getConfirmedOfferText(form.document)}"/>
                        <c:if test="${not empty confirmedOfferText}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey"    value="button.credit.conditions"/>
                                <tiles:put name="commandHelpKey"    value="button.credit.conditions.help"/>
                                <tiles:put name="bundle"            value="auditBundle"/>
                                <tiles:put name="onclick"           value="openConditionsWindow();"/>
                                <tiles:put name="viewType"          value="simpleLink"/>
                            </tiles:insert>
                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="offerTextWin"/>
                                <tiles:put name="data">
                                    <html:hidden property="id"/>
                                    <div class="title">
                                        <h1><bean:message bundle="claimsBundle" key="label.credit.offer.text"/></h1>
                                    </div>
                                    <br/>
                                    <div class="info">
                                        <c:out value="${confirmedOfferText}"/>
                                     </div>
                                </tiles:put>
                            </tiles:insert>
                        </c:if>
                    </c:if>
                </tiles:put>
                <tiles:put name="buttons">
                    <c:if test="${phiz:impliesOperation('MessageLogOperation', 'MessageLogService') or
                                  phiz:impliesOperation('MessageLogOperation', 'MessageLogServiceEmployee') or
                                  phiz:impliesOperation('MessageLogOperation', 'MessageLogServiceEmployeeUseClientForm')}">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey"    value="button.messages"/>
                            <tiles:put name="commandHelpKey"    value="button.messages.help"/>
                            <tiles:put name="bundle"            value="auditBundle"/>
                            <tiles:put name="onclick"           value="openMessageLogForDocument();"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${form.form != 'LoanClaim'}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.accept"/>
                            <tiles:put name="commandHelpKey" value="button.accept.help"/>
                            <tiles:put name="bundle"         value="claimsBundle"/>
                            <tiles:put name="stateObject"    value="document"/>
                        </tiles:insert>
                    </c:if>
                    <tiles:insert definition="commandButton" flush="false" service="ChangeDocumentStatus">
                        <tiles:put name="commandKey"     value="button.execute"/>
                        <tiles:put name="commandHelpKey" value="button.execute.help"/>
                        <tiles:put name="bundle"         value="claimsBundle"/>
                        <tiles:put name="stateObject"    value="document"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.send"/>
                        <tiles:put name="commandHelpKey" value="button.send.help"/>
                        <tiles:put name="bundle"         value="claimsBundle"/>
                        <tiles:put name="stateObject"    value="document"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false" service="ChangeDocumentStatus">
                        <tiles:put name="commandKey"     value="button.refuse"/>
                        <tiles:put name="commandHelpKey" value="button.refuse.help"/>
                        <tiles:put name="bundle"         value="claimsBundle"/>
                        <tiles:put name="stateObject"    value="document"/>
                    </tiles:insert>

                    <c:if test="${phiz:impliesService('RepeatPaymentService') && phiz:isCardESBBillingPayment(form.document)}">
                       <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.specify"/>
                            <tiles:put name="commandHelpKey" value="button.accept.help"/>
                            <tiles:put name="bundle"         value="claimsBundle"/>
                            <tiles:put name="stateObject"    value="document"/>
                        </tiles:insert>
                    </c:if>

                    <c:if test="${form.form != 'LoanClaim'}">
                        <tiles:insert definition="clientButton" flush="false" operation="GetPaymentFormListOperation">
                            <tiles:put name="commandTextKey" value="button.print"/>
                            <tiles:put name="commandHelpKey" value="button.print"/>
                            <tiles:put name="bundle"         value="claimsBundle"/>
                            <tiles:put name="onclick">
                                selectPrintedDocuments(event);
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
            <script>
                if (document.getElementById("headerClaim") && document.getElementById("bodyClaim"))
                {
                    width = document.getElementById("bodyClaim").offsetWidth - 36;
                    document.getElementById("headerClaim").style[width] = width;
                }
            </script>
        </tiles:put>
	</tiles:insert>

</html:form>

