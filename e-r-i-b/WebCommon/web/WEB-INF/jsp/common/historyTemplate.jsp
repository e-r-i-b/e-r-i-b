<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>

<c:set var="viewURL" value="${phiz:calculateActionURL(pageContext, '/private/payments/view')}"/>
<c:set var="viewCardReportDeliveryClaimURL" value="${phiz:calculateActionURL(pageContext, '/private/cards/info')}"/>
<c:set var="isCardReportDeliveryClaimAvailably" value="${phiz:impliesService('CardReportDeliveryClaim')}"/>
<c:set var="scriptsRSAActive"   value="${phiz:isScriptsRSAActive()}"/>

<script type="text/javascript" src="${globalUrl}/scripts/reminder.js"></script>
<c:if test="${scriptsRSAActive}">
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

    <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
    <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>
</c:if>

<script type="text/javascript">
    function edit(event, paymentId, state, formName)
    {
        preventDefault(event);
        <c:set var="u" value="/private/payments/default-action.do"/>
        var url = "${phiz:calculateActionURL(pageContext,u)}";

        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", url + "?id=" + paymentId + "&history=true" + "&objectFormName=" + formName + "&stateCode=" + state);
        <c:if test="${scriptsRSAActive}">
            <%-- формирование основных данных для ФМ --%>
            new RSAObject().toSubmitFormParameters(form);
        </c:if>

        document.body.appendChild(form);
        form.submit();
    }

    function editCardReportDeliveryClaim(event, paymentId, cardId)
    {
        <%-- Бред, но мы должны отрисовать сообщугу отсутствия доступа --%>
        <c:choose>
            <c:when test="${isCardReportDeliveryClaimAvailably}">
                var url = "${viewCardReportDeliveryClaimURL}" + "?id=" + cardId + "&claimId=" + paymentId;
            </c:when>
            <c:otherwise>
                var url = "${viewURL}" + "?id=" + paymentId;
            </c:otherwise>
        </c:choose>
        preventDefault(event);
        window.location = url + "&history=true";
    }

    function addCopy(event, paymentId, url, isDepo)
    {
        preventDefault(event);
        var resultUrl = url;
        if(isDepo)
            resultUrl = resultUrl + '?id='+paymentId;
        else
            resultUrl = resultUrl + '?copying=true&id=' + paymentId;
        window.location = resultUrl;
    }
    var autoPaymentUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/view.do')}";
    function saveAutoPayment(paymentId)
    {
        changeFormAction(autoPaymentUrl + "?id="+paymentId); createCommandButton('button.makeLongOffer','').click('', false)
    }

    function saveAutoTransfer(paymentId)
    {
        changeFormAction(autoPaymentUrl + "?id="+paymentId + "&form=CreateP2PAutoTransferClaim"); createCommandButton('button.makeAutoTransfer','').click('', false)
    }

    function editPFP()
    {
        <c:set var="personId" value="${phiz:getPersonInfo().id}"/>
        <c:set var="u" value="/private/pfp/edit"/>
        window.location = "${phiz:calculateActionURL(pageContext,u)}";
    }


    function editDebitCardClaim(id, state)
    {
        var editUrl = "${phiz:calculateActionURL(pageContext,'/private/sberbankForEveryDay')}";
        var showUrl =  "${phiz:calculateActionURL(pageContext,'/private/sberbankForEveryDay/viewClaim')}";
        if (state == 'INIT' || state == 'INIT_NO_VIP')
            window.location = editUrl + "?id="+id+"&fromHistory=true";
        else
            window.location = showUrl + "?id="+id;
    }

    <c:set var="createTemplateUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/payments/quicklyCreateTemplate')}"/>
    function saveTemplate(paymentId, templateName)
    {
        ajaxQuery(
                "payment=" +  paymentId + "&field(templateName)=" + decodeURItoWin(templateName)<c:if test="${scriptsRSAActive}"> + new RSAObject().toRequestParameters()</c:if>,
                "${createTemplateUrl}",
                function(data)
                {
                    document.getElementById('oneTimePasswordWindow').innerHTML = data;
                    win.open('oneTimePasswordWindow');
                    confirmOperation.initLoadedData();
                }, null);
    }

    function closeTemplateForm(paymentId)
    {
        $('#create-template-' + paymentId).hide();
        $('#create-template-label-' + paymentId).hide();
        $("#createTemplateBlock"+ paymentId).hide();
    }

    function openTemplateForm(elem, paymentId)
    {
        if (elem.blocked)
            return;

        elem.blocked = true;

        ajaxQuery(
                "payment=" +  paymentId + "&operation=button.getTemplateName",
                "${createTemplateUrl}",
                function(data){
                    $("#createTemplateBlock"+ paymentId).show();
                    if(data == null)
                    {
                        $('#templateName' + paymentId).val("");
                        $('#create-template-' + paymentId).show();
                        $('#create-template-label-' + paymentId).show();
                    }
                    else if(data.errors == null || data.errors.length == 0)
                    {
                        $('#templateName' + paymentId).val(data.templateName);
                        $('#create-template-' + paymentId).show();
                        $('#create-template-label-' + paymentId).show();
                    }
                    else
                    {
                        for(var i = 0; i < data.errors.length; i++)
                            addError(data.errors[i]);
                    }
                    elem.blocked = false;
                }, "json");
    }

    function saveTemplateByEnter(e, docId)
    {
        var kk = navigator.appName == 'Netscape' ? e.which : e.keyCode;
        if(kk == 13)
        {
            $('#saveTemplateBtn' + docId).click();
            if (e.preventDefault)
                e.preventDefault();
            else
                e.returnValue = false;
        }
    }

    function openReminderForm(elem, paymentId)
    {
        $('#reminderName' + paymentId).val("");
        $("#createReminderBlock"+ paymentId).show();
        $('#create-reminder-' + paymentId).show();
        $('#create-reminder-label-' + paymentId).show();
    }

    function closeReminderForm(paymentId)
    {
        $('#create-reminder-' + paymentId).hide();
        $('#create-reminder-label-' + paymentId).hide();
        $("#createReminderBlock"+ paymentId).hide();
    }

    <c:set var="createReminderUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/payments/quicklyCreateReminder')}"/>
    function saveReminder(paymentId, templateName)
    {
        var reminderParameters = serializeDiv("createReminderBlock" + paymentId);
        ajaxQuery(
                reminderParameters + "&payment=" +  paymentId + "&field(templateName)=" + decodeURItoWin(templateName),
                "${createReminderUrl}",
                function(data)
                {
                    document.getElementById('oneTimePasswordWindow').innerHTML = data;
                    win.open('oneTimePasswordWindow');
                    confirmOperation.initLoadedData();
                }, null);
    }

    function makeInvoiceSubscription(paymentId)
    {
        ajaxQuery(
                "id=" + paymentId + "&operation=button.makeInvoiceSubscription",
                "${phiz:calculateActionURL(pageContext, '/private/async/userprofile/basket/payments/payment')}",
                function(data)
                {
                    if(data.errors != null && data.errors.length > 0)
                    {
                        for(var i = 0; i < data.errors.length; i++)
                            addError(data.errors[i]);
                    }
                    else if(!isEmpty(data.redirectUrl))
                    {
                        goTo(data.redirectUrl);
                    }
                }, "json");
    }

    $(document).ready(function(){
        $(".grid .ListLine1, .grid .ListLine0").each(function() {
            var img = $(this).find("td.star-hover img").get(0);
            if(img != undefined)
            {
                $(this).find("td.star-hover").click(img.onclick);
                img.onclick = null;
            }
            var onclickFunc = $(this).find('.onclickFunc').get(0).onclick;
            if(onclickFunc != null)
                // кроме столбца с описанием
                $(this).children().filter(":not(.payment-description)").click(onclickFunc);
        });
    });
</script>

<tiles:insert definition="simpleTableTemplate" flush="false">
    <tiles:put name="grid">
        <sl:collection id="listElement" model="simple-pagination" property="data" styleClass="rowOver">
            <c:set var="pfp_id" value="${listElement[0]}" scope="request"/>
            <c:set var="pfp_date" value="${listElement[1]}" scope="request"/>
            <c:set var="pfp_state" value="${listElement[2]}" scope="request"/>
            <c:set var="pfp_employee" value="${listElement[3]}" scope="request"/>
            <c:set var="fir_id" value="${listElement[4]}" scope="request"/>
            <c:set var="fir_sum" value="${listElement[5]}" scope="request"/>
            <c:set var="fir_card" value="${listElement[6]}" scope="request"/>
            <c:set var="fir_date" value="${listElement[7]}" scope="request"/>
            <c:set var="debit_card_claim_id" value="${listElement[8]}" scope="request"/>
            <c:set var="debit_card_claim_date" value="${listElement[1]}" scope="request"/>
            <c:set var="debit_card_claim_state" value="${listElement[2]}" scope="request"/>
            <c:set var="debit_card_claim_all_card_names" value="${listElement[3]}" scope="request"/>
            <c:set var="businessDocument" value="${listElement[9]}" scope="request"/>
            <c:set var="code" value="${businessDocument.state.code}"/>
            <c:set var="isDocument" value="${not empty businessDocument}"/>
            <c:set var="isPfp" value="${not empty pfp_id}"/>
            <c:set var="isFir" value="${not empty fir_id}"/>
            <c:set var="isDebitCardClaim" value="${not empty debit_card_claim_id}"/>
            <c:set var="faildClass" value="${businessDocument.state.code=='REFUSED'?' faild':''}"/>
            <c:set var="formName"        value="${businessDocument.formName}"/>
            <c:set var="isLongOffer"     value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') and businessDocument.longOffer}"/>
            <c:set var="isAutoSub"       value="${isLongOffer && formName == 'RurPayJurSB' || formName == 'CreateP2PAutoTransferClaim'}"/>
            <c:set var="isCreateMoneyBox" value="${formName == 'CreateMoneyBoxPayment'}"/>
            <c:set var="isModifyMoneyBox" value="${formName == 'RefuseMoneyBoxPayment' || formName == 'CloseMoneyBoxPayment' || formName == 'RecoverMoneyBoxPayment' || formName == 'EditMoneyBoxClaim'}"/>
            <c:set var="isEditInvoiceSub" value="${formName == 'EditInvoiceSubscriptionClaim'}"/>
            <c:set var="isEditAuto"   value="${formName == 'EditAutoSubscriptionPayment' || formName == 'EditP2PAutoTransferClaim' || phiz:isInstance(businessDocument, 'com.rssl.phizic.gate.payments.autopayment.EditAutoPayment')}"/>
            <c:set var="isDelayAutoSub"  value="${formName == 'DelayAutoSubscriptionPayment' || formName == 'DelayP2PAutoTransferClaim'}"/>
            <c:set var="isCloseAutoSub"  value="${formName == 'CloseAutoSubscriptionPayment' || formName == 'CloseP2PAutoTransferClaim'}"/>
            <c:set var="isRecoveryAutoSub"  value="${formName == 'RecoveryAutoSubscriptionPayment' || formName == 'RecoveryP2PAutoTransferClaim'}"/>
            <c:set var="isExtendedLoanClaim" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.ExtendedLoanClaim')}"/>
            <c:set var="isInstance" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.DefaultClaim') ||
                                             phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.DepositOpeningClaim') ||
                                             phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.LoanClaim') ||
                                             isExtendedLoanClaim ||
                                             phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.ShortLoanClaim') ||
                                             phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.EarlyLoanRepaymentClaimImpl')}"/>
            <%--Для заявок на изменение неснижаемого остатка и порядка уплаты процентов--%>
            <c:set var="isAccountAgreement" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.ChangeDepositMinimumBalanceClaim') ||
                                                     phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim')}"/>

            <c:set var="isVirtualCardClaim" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.VirtualCardClaim')}"/>
            <c:set var="isCardReportDeliveryClaim" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.CardReportDeliveryClaim')}"/>
            <c:set var="isReportByCardClaim" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.ReportByCardClaim')}"/>
            <c:set var="isChangeCreditLimitClaim" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.ChangeCreditLimitClaim')}"/>

            <c:set var="click">
                <c:choose>
                    <c:when test="${isCardReportDeliveryClaim}">
                        editCardReportDeliveryClaim(event, '${businessDocument.id}', '${businessDocument.cardIdReportDelivery}');
                    </c:when>
                    <c:when test="${isDocument}">
                        edit(event, '${businessDocument.id}', '${businessDocument.state.code}','${businessDocument.formName}');
                    </c:when>
                    <c:when test="${isPfp}">
                        editPFP();
                    </c:when>
                    <c:when test="${isDebitCardClaim}">
                        editDebitCardClaim('${debit_card_claim_id}', '${debit_card_claim_state}');
                    </c:when>
                </c:choose>
            </c:set>

            <c:set var="supportedActions"  value="${phiz:getDocumentSupportedActions(businessDocument)}"/>
            <%-- флажок, говорящий о том, можно ли создать по документу шаблон --%>
            <c:set var="templateSupported" value="${supportedActions['isTemplateSupported']}"/>
            <c:set var="invoiceSubscriptionSupported" value="${supportedActions['isSupportCreateInvoiceSubscription']}"/>
            <c:set var="isCopyAllowed" value="${supportedActions['isCopyAllowed']}"/>
            <c:set var="isAutoPaymentAllowed" value="${supportedActions['isAutoPaymentAllowed']}"/>

            <c:set var="isDocInstanceOfJurPayment" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.JurPayment')}"/>
            <c:set var="isEnableCreateTemplate" value="${businessDocument.state.code=='EXECUTED' &&
                                                                    phiz:impliesTemplateOperation(businessDocument.formName) &&
                                                                    templateSupported}" scope="page"/>
            <c:set var="isShowButtonOperation" value="${not isEditInvoiceSub && (isDocInstanceOfJurPayment ||
                                                                    isExtendedLoanClaim ||
            													    phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RurPayment') ||
            														phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanPayment') ||
            														phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.InternalTransfer'))}"/>
            <c:choose>
                <c:when test="${code=='DISPATCHED' && (isAutoSub || isEditAuto || isDelayAutoSub || isCloseAutoSub || isRecoveryAutoSub || isModifyMoneyBox || isEditInvoiceSub)}">
                    <c:set var="state"><bean:message bundle="paymentsBundle" key="payment.autosub.state.DISPATCHED"/></c:set>
                    <c:set var="stateHint"><bean:message bundle="paymentsBundle" key="payment.autosub.state.hint.DISPATCHED"/></c:set>
                    <c:set var="stateDesc" value="waiting" />
                </c:when>
                <c:when test="${isAccountAgreement && (code=='ERROR')}">
                    <c:set var="state"><bean:message bundle="paymentsBundle" key="payment.state.REFUSED"/></c:set>
                    <c:set var="stateHint"><bean:message bundle="paymentsBundle" key="payment.state.hint.REFUSED"/></c:set>
                    <c:set var="stateDesc" value="cancel" />
                </c:when>
                <c:when test="${not isInstance && ((code=='SENDED') || (code=='DISPATCHED' || code=='STATEMENT_READY') || (code=='PARTLY_EXECUTED') || (code=='ERROR') || code == 'ADOPTED')}">
                    <c:set var="state"><bean:message bundle="paymentsBundle" key="payment.state.DISPATCH"/></c:set>
                    <c:set var="stateHint"><bean:message bundle="paymentsBundle" key="payment.state.hint.DISPATCH"/></c:set>
                    <c:set var="stateDesc" value="waiting" />
                </c:when>
                <c:when test="${isChangeCreditLimitClaim}">
                    <c:set var="state"><bean:message bundle="loanOfferBundle" key="payment.state.${code}"/></c:set>
                    <c:set var="stateHint"><bean:message bundle="loanOfferBundle" key="payment.state.hint.${code}"/></c:set>
                    <c:set var="stateDesc" value="executed" />
                </c:when>
                <c:when test="${businessDocument.formName == 'EarlyLoanRepaymentClaim'}">
                    <c:set var="state"><bean:message bundle="loansBundle" key="claim.earlyRepayment.state.${code}"/></c:set>
                    <c:set var="stateHint"><bean:message bundle="loansBundle" key="claim.earlyRepayment.state.${code}"/></c:set>
                    <c:choose>
                        <c:when test="${code=='INITIAL' || code=='SAVED'}">
                            <c:set var="stateDesc" value="draft"/>
                        </c:when>
                        <c:when test="${code == 'DISPATCHED' || code == 'UNKNOWN'}">
                            <c:set var="stateDesc" value="waiting"/>
                        </c:when>
                        <c:when test="${code == 'ACCEPTED'}">
                            <c:set var="stateDesc" value="executed"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="stateDesc" value="waiting"/>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${not empty code}">
                    <c:set var="state"><bean:message bundle="paymentsBundle" key="payment.state.${code}"/>
                    <c:if test="${'WAIT_CONFIRM' == code && 'IMSI' == businessDocument.reasonForAdditionalConfirm}"> (Смена SIM-карты)</c:if></c:set>
                    <c:set var="stateHint"><bean:message bundle="paymentsBundle" key="payment.state.hint.${code}"/></c:set>

                    <c:choose>
                        <%--Для кредитных заявок--%>
                        <c:when test="${isExtendedLoanClaim}">
                            <c:choose>
                                <c:when test="${code == 'EXECUTED'}">
                                    <c:set var="state"><bean:message bundle="paymentsBundle" key="payment.state.EXECUTED"/></c:set>
                                    <c:set var="stateHint"><bean:message bundle="paymentsBundle" key="payment.state.hint.EXECUTED"/></c:set>
                                    <c:set var="stateDesc" value="executed"/>
                                </c:when>
                                <%--В состояниии "Одобрено, требуется подтверждение для выдачи" или "Необходим визит в отделение"--%>
                                <c:when test="${code == 'APPROVED_MUST_CONFIRM' || code == 'VISIT_REQUIRED'}">
                                    <c:set var="stateDesc" value="orange-attention"/>
                                </c:when>
                                <%--В состоянии "Отказ"--%>
                                <c:when test="${code == 'REFUSED'}">
                                    <c:set var="stateDesc" value="red-attention"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="stateDesc" value="waiting"/>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${code == 'WAIT_CONFIRM'}">
                            <c:set var="stateDesc" value="waitConfirm"/>
                        </c:when>
                        <c:when test="${code == 'REFUSED' || code == 'RECALLED' }">
                            <c:set var="stateDesc" value="cancel"/>
                        </c:when>
                        <c:when test="${code == 'EXECUTED' || code == 'SUCCESSED' || code == 'TICKETS_WAITING'}">
                            <c:set var="stateDesc" value="executed"/>
                        </c:when>
                        <c:when test="${code=='INITIAL'||code=='INITIAL2'||code=='INITIAL3'||code=='INITIAL4'||code=='INITIAL5'||code=='INITIAL6'||code=='INITIAL7'||code=='INITIAL8'
                            || code == 'SAVED' || code == 'DRAFT' || code == 'INITIAL_LONG_OFFER' || code == 'WAIT_CLIENT_MESSAGE'}">
                            <c:set var="stateDesc" value="draft"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="stateDesc" value="waiting"/>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${isPfp}">
                    <c:choose>
                        <c:when test="${pfp_state != 'COMPLITE'}">
                            <c:set var="state"><bean:message bundle="paymentsBundle" key="pfp.state.NOT_COMPLITE"/></c:set>
                            <c:set var="stateHint"><bean:message bundle="paymentsBundle" key="pfp.state.hint.NOT_COMPLITE"/></c:set>
                            <c:set var="stateDesc" value="cancel"/>
                        </c:when>
                        <c:when test="${not empty pfp_employee}">
                            <c:set var="state"><bean:message bundle="paymentsBundle" key="pfp.state.COMPLITE_EMPLOYEE"/>&nbsp;${pfp_employee} </c:set>
                            <c:set var="stateHint"><bean:message bundle="paymentsBundle" key="pfp.state.hint.COMPLITE_EMPLOYEE"/>&nbsp;${pfp_employee} </c:set>
                            <c:set var="stateDesc" value="executed"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="state"><bean:message bundle="paymentsBundle" key="pfp.state.COMPLITE"/></c:set>
                            <c:set var="stateHint"><bean:message bundle="paymentsBundle" key="pfp.state.hint.COMPLITE"/></c:set>
                            <c:set var="stateDesc" value="executed"/>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <%--<c:when test="${isFir}">
                    <c:set var="state"></c:set>
                    <c:set var="stateHint"></c:set>
                    <c:set var="stateDesc" value="draft"/>
                </c:when>--%>
            </c:choose>

            <%--описание операции--%>
            <sl:collectionItem styleClass="payment-description ${stateDesc}" width="305">
                <sl:collectionItemParam id="title">
                    <div class = "operationCell">
                        <span class="word-wrap">
                            <bean:message bundle="paymentsBundle" key="label.history.description"/>
                        </span>
                    </div>
                </sl:collectionItemParam>
                <table class="paymentDescription">
                    <tr>
                        <td width=20px></td>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <div class="payment-description-body ${stateDesc}" onclick="${click}">
                                            <%--<c:if test="${businessDocument.creationType == 'mobile'}">
                                                <img class="apiAutopayment" src="${skinUrl}/images/icon-mobile.png" alt="Оплата через мобильное приложение" border="0"/>
                                            </c:if>
                                            <c:if test="${businessDocument.creationType == 'atm'}">
                                                <img class="atmAutopayment" src="${globalUrl}/commonSkin/images/icon-atm.gif" alt="Оплата через устройство самообслуживания" border="0"/>
                                            </c:if>--%>
                                            <span class="word-wrap">
                                                <c:set var="paymentFormDescriptionBundle" value="paymentform.${businessDocument.formName}"/>
                                                <c:choose>
                                                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractLongOfferDocument') && businessDocument.longOffer}">
                                                        <img src="${skinUrl}/images/calendar.gif" alt="" border="0"/>
                                                        <c:choose>
                                                            <c:when test="${isAutoSub}">
                                                                Создание автоплатежа:
                                                            </c:when>
                                                            <c:when test="${isEditInvoiceSub}">
                                                                Редактирование автопоиска:
                                                            </c:when>
                                                            <c:when test="${isEditAuto}">
                                                                Редактирование автоплатежа:
                                                            </c:when>
                                                            <c:when test="${isDelayAutoSub}">
                                                                Приостановка автоплатежа:
                                                            </c:when>
                                                            <c:when test="${isCloseAutoSub or phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RefuseAutoPaymentImpl')}">
                                                                Отмена автоплатежа:
                                                            </c:when>
                                                            <c:when test="${isRecoveryAutoSub}">
                                                                Возобновление автоплатежа:
                                                            </c:when>
                                                            <c:when test="${isCreateMoneyBox || isModifyMoneyBox}"/>
                                                            <c:otherwise>
                                                                Создание автоплатежа:
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RefuseAutoPaymentImpl') or phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.CreateAutoPaymentImpl')}">
                                                                <c:out value="${businessDocument.friendlyName}"/>
                                                            </c:when>
                                                            <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.AutoPaymentBase') and not empty businessDocument.nameService}">
                                                                <c:out value="${businessDocument.nameService}"/>
                                                            </c:when>
                                                            <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.JurPayment') and not empty businessDocument.serviceName}">
                                                                <c:out value="${businessDocument.serviceName}"/>
                                                            </c:when>
                                                            <c:when test="${not empty businessDocument}">
                                                                <bean:message bundle="paymentsBundle" key="${paymentFormDescriptionBundle}" failIfNone="false"/>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:when test="${businessDocument.formName == 'RefuseLongOffer'}">
                                                        <img src="${skinUrl}/images/calendar.gif" alt="" border="0"/>
                                                        <bean:message bundle="paymentsBundle" key="paymentform.RefuseLongOffer" failIfNone="false"/>
                                                        <c:out value="${businessDocument.name}"/>
                                                    </c:when>
                                                    <c:when test="${businessDocument.formName == 'RefuseAutoPaymentPayment'}">
                                                        <img src="${skinUrl}/images/calendar.gif" alt="" border="0"/>
                                                        <bean:message bundle="paymentsBundle" key="paymentform.RefuseAutoPayment" failIfNone="false"/>
                                                    </c:when>
                                                    <c:when test="${not empty businessDocument}">
                                                        <c:choose>
                                                            <c:when test="${isReportByCardClaim}">
                                                                <span><bean:message bundle="paymentsBundle" key="${paymentFormDescriptionBundle}" failIfNone="false"/> <br/>
                                                                <c:out value="${businessDocument.cardName}"/><c:out value="${phiz:getCutCardNumber(businessDocument.cardNumber)}"/>
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${isChangeCreditLimitClaim}">
                                                                <span><bean:message bundle="paymentsBundle" key="${paymentFormDescriptionBundle}" failIfNone="false"/> <br/>
                                                                <c:out value="${businessDocument.cardName}"/><c:out value="${phiz:getCutCardNumber(businessDocument.cardNumber)}"/>
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${isCardReportDeliveryClaim}">
                                                                <span><bean:message bundle="paymentsBundle" key="paymentform.CardReportDeliveryClaim" failIfNone="false"/></span>
                                                            </c:when>
                                                            <c:when test="${businessDocument.formName == 'RurPayment'}">
                                                                <c:choose>
                                                                    <c:when test="${businessDocument.receiverSubType == 'externalAccount'}">
                                                                        <span>Перевод частному лицу в другой банк по реквизитам</span>
                                                                    </c:when>
                                                                    <c:when test="${businessDocument.receiverSubType == 'ourCard' or businessDocument.receiverSubType == 'ourAccount' or  businessDocument.receiverSubType == 'ourPhone' or  businessDocument.receiverSubType == 'ourContact'}">
                                                                        <span>Перевод клиенту Сбербанка</span>
                                                                    </c:when>
                                                                    <c:when test="${businessDocument.receiverSubType == 'yandexWallet' || document.receiverSubType == 'yandexWalletOurContact' || document.receiverSubType == 'yandexWalletByPhone'}">
                                                                        <bean:message key="label.translate.yandex" bundle="paymentsBundle"/>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span>Перевод на карту в другом банке</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:when test="${businessDocument.formName == 'NewRurPayment'}">
                                                                <c:choose>
                                                                    <c:when test="${businessDocument.receiverSubType == 'ourAccount' || businessDocument.receiverSubType == 'externalAccount'}">
                                                                        <bean:message key="label.translate.bankAccount" bundle="paymentsBundle"/>
                                                                    </c:when>
                                                                    <c:when test="${businessDocument.receiverSubType == 'visaExternalCard' || document.receiverSubType == 'masterCardExternalCard' || document.receiverSubType == 'ourContactToOtherCard'}">
                                                                        <bean:message key="label.translate.card.otherbank" bundle="paymentsBundle"/>
                                                                    </c:when>
                                                                    <c:when test="${businessDocument.receiverSubType == 'yandexWallet' || document.receiverSubType == 'yandexWalletOurContact' || document.receiverSubType == 'yandexWalletByPhone'}">
                                                                        <bean:message key="label.translate.yandex" bundle="paymentsBundle"/>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <bean:message key="label.translate.ourClient" bundle="paymentsBundle"/>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:when test="${businessDocument.formName == 'AccountOpeningClaim' && businessDocument.fromPersonalFinance}">
                                                                <span><bean:message bundle="paymentsBundle" key="paymentform.AccountOpeningClaim.fromPersonalFinance"/></span>
                                                            </c:when>
                                                            <c:when test="${businessDocument.formName == 'AccountClosingPayment' && businessDocument.fromPersonalFinance}">
                                                                <span><bean:message bundle="paymentsBundle" key="paymentform.AccountClosingPayment.fromPersonalFinance"/></span>
                                                            </c:when>
                                                            <c:when test="${businessDocument.formName == 'EarlyLoanRepaymentClaim'}">
                                                                <c:choose>
                                                                    <c:when test="${businessDocument.partial}">
                                                                        <span>Частичное досрочное погашение</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span>Полное досрочное погашение</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span><bean:message bundle="paymentsBundle" key="${paymentFormDescriptionBundle}" failIfNone="false"/></span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:when test="${isPfp}">
                                                        <span >Проведение финансового планирования</span>
                                                    </c:when>
                                                    <c:when test="${isFir}">
                                                        <span>Запрос на сбор средств</span>
                                                    </c:when>
                                                    <c:when test="${isDebitCardClaim}">
                                                        <span>Заказ дебетовых карт</span>
                                                    </c:when>
                                                </c:choose>
                                            </span>
                                        </div>
                                    </td>
                                    <c:if test="${isEnableCreateTemplate && not showOperationsButton}">
                                        <td class="star-hover"> <img src="${globalUrl}/commonSkin/images/star-lit-hover4.png" border="0" onclick="openTemplateForm(this, ${businessDocument.id});" title="Нажмите для создания шаблона платежа."/></td>
                                    </c:if>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <c:if test="${not isFir && not isDebitCardClaim}">
                        <tr  onclick="${click}">
                            <td>
                                <c:if test="${stateDesc != 'draft' && stateDesc != 'waiting' && !isDebitCardClaim}">
                                    <img src="${globalUrl}/commonSkin/images/payment/state_${stateDesc}.png"/>
                                </c:if>
                            </td>
                            <td>
                                <span class="state-${stateDesc}" title="${stateHint}">
                                    ${state}
                                </span>
                            </td>
                        </tr>
                    </c:if>
                </table>

                <c:if test="${isEnableCreateTemplate}">
                    <table id="createTemplateBlock${businessDocument.id}" style="display: none" class="createTemplateBlock">
                    <tr class="exceptionCell">
                        <td class="dateCell">
                            <div id="create-template-label-${businessDocument.id}" class="create-template-label">
                                <bean:message bundle="paymentsBundle" key="label.history.template.name"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="operationCell">
                            <div id="create-template-${businessDocument.id}" class="create-template" onkeypress="saveTemplateByEnter(event, ${businessDocument.id});">
                                <div class="create-template-body">
                                    <input type="text" size="31" id="templateName${businessDocument.id}">
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="buttonsArea buttonsAreaTemplate">
                               <tiles:insert definition="clientButton" flush="false">
                                   <tiles:put name="commandTextKey" value="button.cancel"/>
                                   <tiles:put name="commandHelpKey" value="button.cancel"/>
                                   <tiles:put name="bundle"         value="commonBundle"/>
                                   <tiles:put name="onclick"        value="closeTemplateForm(${businessDocument.id});"/>
                                   <tiles:put name="viewType"       value="buttonGrey"/>
                               </tiles:insert>
                                <tiles:insert definition="createTemplateConfirmButton" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.save"/>
                                    <tiles:put name="templateNameId"    value="#templateName${businessDocument.id}"/>
                                    <tiles:put name="bundle"            value="commonBundle"/>
                                    <tiles:put name="onSaveFunction"    value="saveTemplate(${businessDocument.id}, $('#templateName${businessDocument.id}').val());"/>
                                    <tiles:put name="winId"             value="win${businessDocument.id}"/>
                                    <tiles:put name="btnId"             value="saveTemplateBtn${businessDocument.id}"/>
                                </tiles:insert>
                               <tiles:insert definition="window" flush="false">
                                   <tiles:put name="id" value="oneTimePasswordWindow"/>
                               </tiles:insert>
                           </div>
                        </td>
                    </tr>
                    </table>
                    <c:if test="${phiz:impliesService('ReminderManagment')}">
                        <tiles:insert page="/WEB-INF/jsp-sbrf/reminder/reminderHistoryRow.jsp" flush="false">
                            <tiles:put name="id"          value="${businessDocument.id}"/>
                        </tiles:insert>
                    </c:if>
                </c:if>

            </sl:collectionItem>

            <%--счет списания--%>
            <sl:collectionItem styleClass="${stateDesc}" styleTitleClass=" " width="180">
                <sl:collectionItemParam id="title">
                    <span>
                        <bean:message bundle="paymentsBundle" key="label.history.CAD"/>
                    </span>
                </sl:collectionItemParam>

                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
                    <c:if test="${not empty businessDocument.chargeOffAccount}">
                        <c:choose>
                            <%--Счет списания КАРТА--%>
                            <c:when test="${businessDocument.chargeOffResourceType == 'CARD'}">
                                <tiles:insert page="history-card-title.jsp" flush="false">
                                    <tiles:put name="cardNumber" value="${businessDocument.chargeOffAccount}"/>
                                    <tiles:put name="cardName" value="${businessDocument.fromAccountName}"/>
                                </tiles:insert>
                            </c:when>
                            <%--Счет списания ОМС--%>
                            <c:when test="${businessDocument.chargeOffResourceType == 'IM_ACCOUNT'}">
                                <c:if test="${not empty businessDocument.chargeOffResourceLink}">
                                    <tiles:insert page="history-imaccount-title.jsp" flush="false">
                                        <tiles:put name="imAccountLink" beanName="businessDocument" beanProperty="chargeOffResourceLink"/>
                                    </tiles:insert>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert page="history-account-title.jsp" flush="false">
                                    <tiles:put name="accountNumber" value="${businessDocument.chargeOffAccount}"/>
                                    <tiles:put name="accountName" value="${businessDocument.fromAccountName}"/>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:if>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.BlockingCardClaim')}">
                    <c:if test="${not empty businessDocument.cardNumber}">
                        <tiles:insert page="history-card-title.jsp" flush="false">
                            <tiles:put name="cardNumber" value="${businessDocument.cardNumber}"/>
                            <tiles:put name="cardName" value="${businessDocument.fromAccountName}"/>
                         </tiles:insert>
                    </c:if>
                </c:if>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.gate.payments.ReIssueCardClaim')}">
                    <c:if test="${not empty businessDocument.cardNumber}">
                        <tiles:insert page="history-card-title.jsp" flush="false">
                            <tiles:put name="cardNumber" value="${businessDocument.cardNumber}"/>
                         </tiles:insert>
                    </c:if>
                </c:if>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.LossPassbookApplication')}">
                    <c:if test="${not empty businessDocument.depositAccount}">
                        <tiles:insert page="history-account-title.jsp" flush="false">
                            <tiles:put name="accountNumber" value="${businessDocument.depositAccount}"/>
                            <tiles:put name="accountName" value="${businessDocument.fromAccountName}"/>
                        </tiles:insert>
                    </c:if>
                </c:if>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RefuseLongOfferClaim')}">
                    <c:choose>
                        <c:when test="${businessDocument.chargeOffResourceType == 'CARD'}">
                            <tiles:insert page="history-card-title.jsp" flush="false">
                                <tiles:put name="cardNumber" value="${businessDocument.payerAccount}"/>
                                <tiles:put name="cardName" value="${businessDocument.payerAccountName}"/>
                              </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert page="history-account-title.jsp" flush="false">
                                <tiles:put name="accountNumber" value="${businessDocument.payerAccount}"/>
                                <tiles:put name="accountName" value="${businessDocument.payerAccountName}"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.ChangeDepositMinimumBalanceClaim')}">
                    <tiles:insert page="history-account-title.jsp" flush="false">
                        <tiles:put name="accountNumber" value="${businessDocument.accountNumber}"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim')}">
                    <tiles:insert page="history-account-title.jsp" flush="false">
                        <tiles:put name="accountNumber" value="${phiz:getAccountLinkById(businessDocument.accountId, businessDocument.owner.login).number}"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.GetPrivateOperationScanClaim')}">
                    <tiles:insert page="history-account-title.jsp" flush="false">
                        <tiles:put name="accountNumber" value="${businessDocument.accountNumber}"/>
                    </tiles:insert>
                </c:if>
                <c:if test="${isCardReportDeliveryClaim}">
                    <tiles:insert page="history-card-title.jsp" flush="false">
                        <tiles:put name="cardNumber" value="${businessDocument.cardNumberReportDelivery}"/>
                        <tiles:put name="cardName" value="${businessDocument.cardNameReportDelivery}"/>
                    </tiles:insert>
                </c:if>
            </sl:collectionItem>
            <%--Получатель--%>
            <sl:collectionItem styleClass="${stateDesc}" styleTitleClass=" "  width="180">
                <sl:collectionItemParam id="title">
                    <span>
                        <bean:message bundle="paymentsBundle" key="label.history.receiver"/>
                    </span>
                </sl:collectionItemParam>

                <c:choose>
                    <c:when test="${isDebitCardClaim}">
                        <c:out value="${debit_card_claim_all_card_names}"/>
                    </c:when>
                    <c:when test="${phiz:isInstance(businessDocument,'com.rssl.phizic.business.documents.P2PAutoTransferClaimBase')}">
                        <c:set var="receiverType" value="${businessDocument.receiverType}"/>
                        <c:if test="${receiverType == 'ph'}">
                            <div class="linkName">
                                <c:set var="receiverFirstName" value="${businessDocument.receiverFirstName}"/>
                                <c:set var="receiverSurName"   value="${businessDocument.receiverSurName}"/>
                                <c:set var="receiverPatrName"  value="${businessDocument.receiverPatrName}"/>

                                <c:out value="${phiz:getFormattedPersonName(receiverFirstName, receiverSurName, receiverPatrName)}"/>
                            </div>
                            <tiles:insert page="history-card-title.jsp" flush="false">
                                <tiles:put name="cardNumber" value="${businessDocument.receiverAccount}"/>
                                <tiles:put name="showCardName" value="false"/>
                            </tiles:insert>
                        </c:if>
                        <c:if test="${receiverType == 'several'}">
                            <tiles:insert page="history-card-title.jsp" flush="false">
                                <tiles:put name="cardNumber" value="${businessDocument.receiverAccount}"/>
                                <tiles:put name="cardName" value="${businessDocument.toAccountName}"/>
                            </tiles:insert>
                        </c:if>
                    </c:when>
                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.InternalTransfer')}">
                        <c:choose>
                            <%--Счет зачисления КАРТА--%>
                            <c:when test="${businessDocument.destinationResourceType == 'CARD'}">
                                <tiles:insert page="history-card-title.jsp" flush="false">
                                    <tiles:put name="cardNumber" value="${businessDocument.receiverAccount}"/>
                                    <tiles:put name="cardName" value="${businessDocument.toAccountName}"/>
                                </tiles:insert>
                            </c:when>
                            <%--Счет зачисления ОМС--%>
                            <c:when test="${businessDocument.destinationResourceType == 'IM_ACCOUNT'}">
                                <c:if test="${not empty businessDocument.destinationResourceLink}">
                                    <tiles:insert page="history-imaccount-title.jsp" flush="false">
                                        <tiles:put name="imAccountLink" beanName="businessDocument" beanProperty="destinationResourceLink"/>
                                    </tiles:insert>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert page="history-account-title.jsp" flush="false">
                                    <tiles:put name="accountNumber" value="${businessDocument.receiverAccount}"/>
                                    <tiles:put name="accountName" value="${businessDocument.toAccountName}"/>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AccountOpeningClaim')}">
                        <c:choose>
                            <c:when test="${businessDocument.destinationResourceType == 'CARD'}">
                                <div class="linkName">
                                    <span class="word-wrap"><c:out value="${businessDocument.depositName}"/></span>
                                </div>
                                <div  class="grayText  noWrap">
                                    ${phiz:getCutCardNumber(businessDocument.receiverAccount)}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="linkName">
                                    <span class="word-wrap"><c:out value="${businessDocument.depositName}"/></span>
                                </div>
                                <div class="grayText  noWrap">
                                    ${phiz:getFormattedAccountNumber(businessDocument.receiverAccount)}
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.IMAOpeningClaim')}">
                        <div class="grayText">
                             ${phiz:cutStringByLastSevenSymbols(businessDocument.receiverAccount)}
                        </div>
                        <div>
                            <span class="word-wrap"><c:out value="${businessDocument.IMAProductName}"/></span>
                        </div>
                        <div>
                            <span class="word-wrap"><fmt:formatNumber value="${businessDocument.destinationAmount.decimal}" pattern="0.0"/></span>
                            <span class="word-wrap"><c:out value="${phiz:getCurrencySign(businessDocument.IMACurrency)}"/></span>
                            <span class="word-wrap"><c:out value="${phiz:normalizeMetalCode(businessDocument.IMACurrency)}"/></span>
                        </div>
                    </c:when>
                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                        <c:set var="isRurPayment" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RurPayment')}"/>
                        <div  class="linkName">
                            <span class="word-wrap">
                                <c:choose>
                                    <c:when test="${isRurPayment and businessDocument.receiverType eq 'ph'}">
                                        <c:set var="receiverFirstName" value="${businessDocument.receiverFirstName}"/>
                                        <c:set var="receiverSurName"   value="${businessDocument.receiverSurName}"/>
                                        <c:set var="receiverPatrName"  value="${businessDocument.receiverPatrName}"/>

                                        <c:out value="${phiz:getFormattedPersonName(receiverFirstName, receiverSurName, receiverPatrName)}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${businessDocument.receiverName}"/>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <div class="grayText noWrap">
                            <c:choose>
                                <c:when test="${businessDocument.destinationResourceType == 'EXTERNAL_CARD'}">
                                    ${phiz:getCutCardNumber(businessDocument.receiverAccount)}
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${businessDocument.destinationResourceType == 'CARD'}">
                                            ${phiz:getCutCardNumber(businessDocument.receiverAccount)}
                                        </c:when>
                                        <c:otherwise>
                                            ${phiz:getFormattedAccountNumber(businessDocument.receiverAccount)}
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:when>
                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RefuseLongOfferClaim')}">
                        <c:choose>
                            <c:when test="${businessDocument.receiverResourceType == 'CARD'}">
                                <tiles:insert page="history-card-title.jsp" flush="false">
                                    <tiles:put name="cardNumber" value="${businessDocument.receiverAccount}"/>
                                  </tiles:insert>
                            </c:when>
                            <c:when test="${businessDocument.receiverResourceType == 'ACCOUNT'}">
                                <tiles:insert page="history-account-title.jsp" flush="false">
                                    <tiles:put name="accountNumber" value="${businessDocument.receiverAccount}"/>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert page="history-loan-title.jsp" flush="false">
                                    <tiles:put name="accountNumber" value="${businessDocument.receiverAccount}"/>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanClaimBase')
                                    ||isVirtualCardClaim
                                    ||phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanOfferClaim')
                                    ||phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanCardOfferClaim')
                                    ||phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.PFRStatementClaim')}">
                        <c:out value="${businessDocument.receiverName}"/>
                    </c:when>
                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanPayment')}">
                        <span class="word-wrap">
                            <c:out value="${phiz:getLoanLink(businessDocument.accountNumber).name}"/>
                        </span>
                        <c:out value="${businessDocument.accountNumber}"/>
                    </c:when>
                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim')}">
                        <c:choose>
                            <c:when test="${businessDocument.interestDestinationSource == 'card'}">
                                <div class="linkName">
                                    <span class="word-wrap"><c:out value="${phiz:getCardUserName(phiz:getCardLink(businessDocument.percentCardNumber))}"/></span>
                                </div>
                                <div  class="grayText  noWrap">
                                    ${phiz:getCutCardNumber(businessDocument.percentCardNumber)}
                                </div>
                            </c:when>
                            <c:when test="${businessDocument.interestDestinationSource == 'account'}">
                                <div class="linkName">
                                    <span class="word-wrap"><c:out value="${phiz:getAccountLink(businessDocument.changePercentDestinationAccountNumber).name}"/></span>
                                </div>
                                <div class="grayText  noWrap">
                                    ${phiz:getFormattedAccountNumber(businessDocument.changePercentDestinationAccountNumber)}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="linkName">
                                    <span class="word-wrap"><c:out value="${phiz:getAccountLinkById(businessDocument.accountId, businessDocument.owner.login).name}"/></span>
                                </div>
                                <div class="grayText  noWrap">
                                    ${phiz:getFormattedAccountNumber(phiz:getAccountLinkById(businessDocument.accountId, businessDocument.owner.login).number)}
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${isExtendedLoanClaim
                                    || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.ShortLoanClaim')
                                    || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanCardClaim')
                                    || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.EarlyLoanRepaymentClaimImpl') }">
                        <c:out value="Сбербанк России"/>
                    </c:when>
                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.GetPrivateOperationScanClaim')}">
                        <c:set var="firstName" value="${phiz:substringSplit(businessDocument.FIO,' ' , 1)}"/>
                        <c:set var="surname" value="${phiz:substringSplit(businessDocument.FIO,' ' , 0)}"/>
                        <c:set var="patrName" value="${phiz:substringSplit(businessDocument.FIO,' ' , 2)}"/>
                        <c:out value="${phiz:getFormattedPersonFIO(firstName, surname, patrName)}"/>
                        ${phiz:getMaskedEMail(businessDocument.EMail)}
                    </c:when>
                    <c:when test="${isFir}">
                        <tiles:insert page="history-card-title.jsp" flush="false">
                            <tiles:put name="cardNumber" value="${fir_card}"/>
                        </tiles:insert>
                    </c:when>
                </c:choose>

            </sl:collectionItem>

            <%--дата--%>
            <sl:collectionItem styleClass="align-center ${stateDesc}" styleTitleClass=" "  width="135">
                <sl:collectionItemParam id="title" >
                    <div class="align-center">
                        <bean:message bundle="paymentsBundle" key="label.history.dateCreate"/>
                    </div>
                </sl:collectionItemParam>
                <div align="center">
                    <c:choose>
                        <c:when test="${isDocument}">
                            ${phiz:formatDateDependsOnSysDate(businessDocument.dateCreated, true, false)}
                        </c:when>
                        <c:when test="${isPfp}">
                            ${phiz:formatDateDependsOnSysDate(pfp_date, true, false)}
                        </c:when>
                        <c:when test="${isFir}">
                            ${phiz:formatDateDependsOnSysDate(fir_date, true, false)}
                        </c:when>
                        <c:when test="${isDebitCardClaim}">
                            ${phiz:formatDateDependsOnSysDate(debit_card_claim_date, true, false)}
                        </c:when>
                    </c:choose>
                </div>
            </sl:collectionItem>
            <%--Сумма--%>
            <sl:collectionItem styleClass="align-right ${stateDesc}" styleTitleClass=" "  width="135">
                <sl:collectionItemParam id="title">
                    <div class="align-center">
                        <bean:message bundle="paymentsBundle" key="label.history.amount"/>
                    </div>
                </sl:collectionItemParam>

                <div class="textNobr amount">
                    <c:choose>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.gate.longoffer.autopayment.AutoPayment') && businessDocument.executionEventType == 'BY_INVOICE'}">
                            ${phiz:formatAmount(businessDocument.floorLimit)}
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RefuseAutoPaymentImpl')}">
                            <c:choose>
                                <c:when test="${not empty businessDocument.percent}">
                                    ${businessDocument.percent} % от остатка
                                </c:when>
                                <c:otherwise>
                                    ${phiz:formatAmount(businessDocument.amount)}
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RefuseLongOfferClaim')}">
                            <c:choose>
                                <c:when test="${not empty businessDocument.percent}">
                                    ${businessDocument.percent} % от остатка
                                </c:when>
                                <c:otherwise>
                                    ${phiz:formatAmount(businessDocument.amount)}
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.gate.payments.CurrencyExchangeTransfer') and (empty businessDocument.chargeOffAmount or businessDocument.chargeOffAmount.decimal == '0.00')}"> <!-- Это обмен валюты и не была указана сумма списания-->
                            +${phiz:formatAmount(businessDocument.destinationAmount)} <%-- сумма зачисления для обемена валют --%>
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanClaimBase') && not empty businessDocument.destinationAmount}">
                            ${phiz:formatAmount(businessDocument.destinationAmount)}
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AccountOpeningClaim')}">
                            <c:choose>
                                <c:when test="${not empty businessDocument.destinationAmount}">
                                    ${phiz:formatAmount(businessDocument.destinationAmount)}
                                </c:when>
                                <c:otherwise>
                                    &minus;${phiz:formatAmount(businessDocument.chargeOffAmount)}
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument') && not empty businessDocument.chargeOffAmount}">
                            <c:choose>
                                <c:when test="${businessDocument.chargeOffAmount.decimal=='0.00'}">
                                    ${phiz:formatAmount(businessDocument.chargeOffAmount)}
                                </c:when>
                                <c:otherwise>
                                    &minus;${phiz:formatAmount(businessDocument.chargeOffAmount)}
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanCardClaim')}">
                            ${phiz:formatAmount(businessDocument.destinationAmount)}
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.RefundGoodsClaim')}">
                            ${phiz:formatAmount(businessDocument.chargeOffAmount)}
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.RollbackOrderClaim')}">
                            ${phiz:formatAmount(businessDocument.chargeOffAmount)}
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.ChangeDepositMinimumBalanceClaim') && not empty businessDocument.minimumBalance}">
                            ${phiz:formatDecimalToAmount(businessDocument.minimumBalance, 2)} ${phiz:getCurrencySign(businessDocument.minDepositBalanceCurrency)}
                        </c:when>
                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.ShortLoanClaim') && not empty businessDocument.loanAmount}">
                            ${phiz:formatAmount(businessDocument.loanAmount)}
                        </c:when>
                        <c:when test="${isExtendedLoanClaim && not empty businessDocument.loanAmount}">
                            ${phiz:formatAmount(businessDocument.loanAmount)}
                        </c:when>
                        <c:when test ="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument') && not empty businessDocument.destinationAmount}">
                            <c:choose>
                                <c:when test="${(businessDocument.destinationAmount.decimal != '0.00')}">
                                    &minus;${phiz:formatAmount(businessDocument.destinationAmount)} <%-- для операции перевода с карты на счет --%>
                                </c:when>
                                <c:otherwise>
                                    ${phiz:formatAmount(businessDocument.destinationAmount)} <%-- для операции перевода с карты на счет --%>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${isFir}">
                            <c:if test="${not empty fir_sum}">
                                <c:out value="${phiz:formatBigDecimal(fir_sum)}"/>
                                <c:out value="${phiz:getCurrencySign('RUB')}"/>
                            </c:if>
                        </c:when>
                    </c:choose>
                </div>
            </sl:collectionItem>
            <c:if test="${showOperationsButton}">
                <sl:collectionItem styleClass="align-center ${stateDesc} operationWidth rightPaddingCell" styleTitleClass="rightRoundCell" width="180">
                    <sl:collectionItemParam id="title">
                       &nbsp;
                    </sl:collectionItemParam>
                    <c:if test="${isShowButtonOperation && not empty businessDocument && not phiz:isInternetShopOrAeroflotPayment(businessDocument)}">
                            <tiles:insert definition="listOfOperation" flush="false">
                                <tiles:put name="isLock" value="false"/>
                                <c:set var="url">'#'</c:set>
                                <tiles:putList name="items">
                                    <c:if test="${isCopyAllowed && (stateDesc == 'executed' || stateDesc=='waiting')}">
                                        <c:set var="isDepo" value="${(businessDocument.formName == 'JurPayment') && (not empty businessDocument.depoLinkId)}"/>
                                        <c:set var="documentId" value="${businessDocument.id}"/>
                                        <c:set var="linkPayment" value="${phiz:getLinkPaymentByDocument(businessDocument)}"/>
                                        <c:set var="copyUrl" value="${phiz:calculateActionURL(pageContext, linkPayment)}"/>
                                        <c:if test="${isDepo}">
                                            <c:set var="documentId" value="${businessDocument.depoLinkId}"/>
                                            <c:set var="copyUrl" value="${phiz:calculateActionURL(pageContext, '/private/depo/info/debt.do')}"/>
                                        </c:if>
                                        <tiles:add>
                                            <a href='#'
                                               onclick='addCopy(event, "${documentId}", "${copyUrl}","${idDepo}")'>
                                                <bean:message key="button.addCopy" bundle="paymentsBundle"/>
                                            </a>
                                        </tiles:add>
                                    </c:if>
                                    <c:if test="${isEnableCreateTemplate}">
                                        <tiles:add><a onclick="javascript:openTemplateForm(this, ${businessDocument.id});">Создать шаблон</a></tiles:add>
                                    </c:if>
                                    <c:if test="${isEnableCreateTemplate && phiz:impliesService('ReminderManagment')}">
                                        <tiles:add>
                                            <a onclick="javascript:openReminderForm(this, ${businessDocument.id});">
                                                <bean:message key="button.${formName == 'RurPayJurSB' ? 'payment' : 'transfer'}.saveAsReminder" bundle="reminderBundle"/>
                                            </a>
                                        </tiles:add>
                                    </c:if>
                                    <c:if test="${invoiceSubscriptionSupported && phiz:impliesOperation('CreateInvoiceSubscriptionDocumentOperation', 'CreateInvoiceSubscriptionPayment')}">
                                        <tiles:add><a onclick="javascript:makeInvoiceSubscription(${businessDocument.id});"><bean:message key="button.makeInvoiceSubscription" bundle="paymentsBundle"/></a></tiles:add>
                                    </c:if>
                                    <c:if test="${isAutoPaymentAllowed}">
                                        <tiles:add><a onclick="saveAutoPayment(${businessDocument.id});"><bean:message key="button.addAutoPayment" bundle="paymentsBundle"/></a></tiles:add>
                                    </c:if>
                                    <c:if test="${supportedActions['isAutoTransferAllowed']}">
                                        <tiles:add><a onclick="saveAutoTransfer(${businessDocument.id});"><bean:message key="button.addAutoPayment" bundle="paymentsBundle"/></a></tiles:add>
                                    </c:if>
                                    <%--Для кредитных заявок--%>
                                    <c:if test="${isExtendedLoanClaim}">
                                        <c:set var="loanOffer" value="${phiz:getLoanOffer(false)}"/>
                                        <%--Если есть неакцептованные оферты--%>
                                        <c:if test="${loanOffer['claimId'] == businessDocument.id}">
                                            <div class="relative noticeBtn" onclick="cancelBubbling(event);">
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.confirmEnter"/>
                                                    <tiles:put name="commandHelpKey" value="button.confirmEnter"/>
                                                    <tiles:put name="bundle" value="offertBundle"/>
                                                    <tiles:put name="viewType" value="greenBorder css3"/>
                                                    <tiles:put name="action" value="/private/credit/offert/accept.do?appNum=${loanOffer['applicationNumber']}&claimId=${businessDocument.id}"/>
                                                </tiles:insert>
                                            </div>
                                        </c:if>
                                    </c:if>
                                </tiles:putList>
                            </tiles:insert>
                    </c:if>
                </sl:collectionItem>
            </c:if>
            <sl:collectionItem hidden="true" >
                <input type="hidden" value="${businessDocument.formName}"/>
                <input type="hidden" value="${businessDocument.state.code}"/>
                <span onclick="${click}" class="onclickFunc"></span>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>
    <tiles:put name="isEmpty" value="${empty form.data}"/>
    <tiles:put name="emptyMessage">
        <bean:message bundle="paymentsBundle" key="label.history.emptyMessage"/>
    </tiles:put>
</tiles:insert>
