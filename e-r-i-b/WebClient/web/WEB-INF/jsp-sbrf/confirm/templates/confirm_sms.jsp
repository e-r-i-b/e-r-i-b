<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<%--
  ������ ��� ������������� �������� SMS �������
    confirmRequest      - ������ �� �������������
    confirmStrategy     - ��������� �������������
    commandKey          - ���� �������
    message             - ��������� ������������ ������������
    anotherStrategy     - �������� �� ������������� ������ ����������
    confirmableObject   - �������������� ������
    data                - ������ ������������ ������������ (���� �������� ������ ��� ������ � SMS �������)
    byCenter  - ����������� ������ ��� �� ������ (� ������, ���� ������ ������� � ������ ��� ������� ����)
    confirmCommandKey   - ���� ��� ������ �������������
    useAjax             - ������� �������� ���� ����� ������������ ������ � ������� ajax (true - ������������ ajax, false - ������������ commandButton)
    ajaxUrl             - url, ���������� ����� ajax
    showActionMessages  - ���������� ��������� �� ActionMessages �� ����������� ����
    onKeyPressFunction  - ���� ���������� ������� ������� �� ������� � �����. ���� �� �����, �� ��������� ����� �������� onEnterKey();
    showCancelButton    - ���������� �� ������ "��������"
    buttonType          - ��� ����������� ����� ������ (standart -- ������ ���� ���� ����� ������, singleRow -- ������ �� ����� ������ � �����)
    guest               - ������� �������� ������
    documentShortNumber - �������� ����� ������ (��� �������� ������)
--%>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="bundle" value="userprofileBundle"/>
<c:set var="hasSMS" value="${phiz:isContainStrategy(confirmStrategy, 'sms')}"/>
<c:set var="hasCard" value="${phiz:isContainStrategy(confirmStrategy,'card')}"/>
<c:set var="hasCap" value="${phiz:isContainStrategy(confirmStrategy, 'cap')}"/>
<c:set var="hasPush" value="${phiz:isContainStrategy(confirmStrategy, 'push')}"/>

<c:if test="${not empty confirmStrategy}">
    <c:set var="anotherStrategy" value="${phiz:isContainStrategy(confirmStrategy,'card')}"/>
</c:if>

<c:if test="${!useAjax}">
    <c:choose>
        <c:when test="${hasPush}">
            <tiles:insert definition="confirm_not_ajax_all" flush="false">
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="${preConfirmCommandKey}"/>
                <tiles:put name="commandTextKey" value="${preConfirmCommandKey}"/>
                <tiles:put name="commandHelpKey" value="${preConfirmCommandKey}.help"/>
                <tiles:put name="bundle" value="securityBundle"/>
                <tiles:put name="id" value="confirmSMS"/>
            </tiles:insert>
            <c:if test="${phiz:isContainStrategy(confirmStrategy,'cap')}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.confirmCap"/>
                    <tiles:put name="commandTextKey" value="button.confirmCap"/>
                    <tiles:put name="commandHelpKey" value="button.confirmCap"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="bundle" value="securityBundle"/>
                </tiles:insert>
            </c:if>
            <c:if test="${phiz:isContainStrategy(confirmStrategy,'card')}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.confirmCard"/>
                    <tiles:put name="commandTextKey" value="button.confirmCard"/>
                    <tiles:put name="commandHelpKey" value="button.confirmCard"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="bundle" value="securityBundle"/>
                </tiles:insert>
            </c:if>
        </c:otherwise>
    </c:choose>
</c:if>

<c:if test="${confirmRequest.preConfirm and (confirmRequest.strategyType=='sms' or confirmRequest.strategyType=='push')}">

<c:set var="fullData">
<h2>
    <c:choose>
        <c:when test="${not empty title}">
            <c:out value="${title}"/>
        </c:when>
        <c:when test="${confirmableObject == 'login'}">
            ������������� �����
        </c:when>
        <c:when test="${confirmableObject == 'securitySettings'}">
            ������������� �������� ������������
        </c:when>
        <c:when test="${confirmableObject == 'creditOffertText'}">
            ����������� ������� � ��������������� ��������� ������������
        </c:when>
        <c:when test="${confirmableObject == 'creditOffert'}">
            ������������� ������ �������
        </c:when>
        <c:when test="${confirmableObject == 'mailNotificationSettings'}">
            ������������� �������� ����������
        </c:when>
        <c:when test="${confirmableObject == 'personalSettings'}">
            ������������� ������������ ����������
        </c:when>
        <c:when test="${confirmableObject == 'viewSettings'}">
            ������������� �������� ����������
        </c:when>
        <c:when test="${confirmableObject == 'activePerson'
                                || confirmableObject == 'RecoveryInvoiceSubscriptionClaim'
                                || confirmableObject == 'DelayInvoiceSubscriptionClaim'
                                || confirmableObject == 'MakeAutoInvoiceSubscriptionService'
                                || confirmableObject == 'CloseInvoiceSubscriptionClaim'
                                || confirmableObject == 'CreateInvoiceSubscriptionPayment'}">
            ������������� ��������
        </c:when>
        <c:when test="${confirmableObject == 'SMSTemplate'}">
            ������������� �������� SMS-�������
        </c:when>
        <c:when test="${confirmableObject == 'individualLimitSettings'}">
            ������������� ��������������� ������
        </c:when>
        <c:when test="${confirmableObject == 'claim'}">
            ������������� ������
        </c:when>
        <c:when test="${confirmableObject == 'recall'}">
            ������������� ������
        </c:when>
        <c:when test="${confirmableObject == 'securitiesTransferClaim'
                                || confirmableObject == 'SecurityRegistrationClaim'
                                || confirmableObject == 'DepositorFormClaim'
                                || confirmableObject == 'RecallDepositaryClaim'}">
            ������������� ���������
        </c:when>
        <c:when test="${confirmableObject == 'RefuseAutopayment'}">
            ������ �����������
        </c:when>
        <c:when test="${confirmableObject == 'LossPassbookApplication'}">
            ������������� ���������
        </c:when>
        <c:when test="${confirmableObject == 'createAutoSubscription'}">
            ������������� ������
        </c:when>
        <c:when test="${confirmableObject == 'mobileWallet'}">
            ������������� ��������� ���������� ��������
        </c:when>
        <c:when test="${confirmableObject == 'ermbSettings'}">
            ������������� �������� ����������� ���������� �����
        </c:when>
        <c:when test="${confirmableObject == 'incognitoSetting'}">
            ������������� �������� �����������
        </c:when>
        <c:when test="${confirmableObject == 'CardReportDeliveryClaim'}">
            <bean:message bundle="cardInfoBundle" key="email.report.delivery.win.title"/>
        </c:when>
        <c:when test="${confirmableObject == 'userEmailSettings'}">
            ��������� e-mail
        </c:when>
        <c:when test="${confirmableObject == 'userDocumentRemove'}">
            �������� ���������
        </c:when>
        <c:when test="${confirmableObject == 'iTunes'}">
            ������������� ��������
        </c:when>
        <c:when test="${confirmableObject == 'contactTrustSettings'}">
            <bean:message bundle="userprofileBundle" key="message.one.confirm.title"/>
        </c:when>
        <c:when test="${confirmableObject == 'translateToExternalAccount'}">
            ������������� �������� �� ���������� ����
        </c:when>
        <c:when test="${confirmableObject == 'translateToExternalCard'}">
            ������������� �������� �� ����� ������� �����
        </c:when>
        <c:when test="${confirmableObject == 'translateYandexWallet'}">
            ������������� �������� �� ������.������
        </c:when>
        <c:when test="${confirmableObject == 'translateOurClient'}">
            ������������� �������� ������� ���������
        </c:when>
        <c:when test="${confirmableObject == 'RemoteConnectionUDBO'}">
            ����������� ���� ������������ �������� ������
        </c:when>
        <c:when test="${confirmableObject == 'EarlyLoanRepaymentClaimPartial'}">
            ������������� ���������� ���������� ���������
        </c:when>
        <c:when test="${confirmableObject == 'EarlyLoanRepaymentClaimFull'}">
            ������������� ������� ��������� �������
        </c:when>
        <c:otherwise>
            ������������� �������
        </c:otherwise>
    </c:choose>
</h2>


<c:set var="inactiveESMessages" value=""/>
<phiz:messages id="inactiveES" bundle="${bundle}" field="field" message="inactiveExternalSystem">
    <c:set var="inactiveESMessages">${inactiveESMessages}<div class = "itemDiv">${phiz:processBBCode(inactiveES)} </div></c:set>
</phiz:messages>

<c:set var="errorMessage" value=""/>
<c:set var="informMessage" value=""/>
<c:if test="${showActionMessages}">
    <phiz:messages id="errorItem" bundle="paymentsBundle" field="field" message="error">
        <c:if test="${field == null}">
            <c:set var="errorMessage">${errorMessage}<div class = "itemDiv"><bean:write name="errorItem" filter="false" ignore="true"/></div></c:set>
        </c:if>
    </phiz:messages>
    <phiz:messages id="infMessage" bundle="paymentsBundle" field="field" message="message">
        <c:if test="${field == null}">
            <c:set var="informMessage">${informMessage}<div class = "itemDiv"><bean:write name="infMessage" filter="false" ignore="true"/></div></c:set>
        </c:if>
    </phiz:messages>
</c:if>

<div class="warningMessage" id="warningMessages">
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="red"/>
        <tiles:put name="data">
            <div class="messageContainer">
                ${errorMessage}
                ${confirmRequest.errorMessage}
            </div>
        </tiles:put>
    </tiles:insert>
</div>

<%--������� ���������� ��������� ���������� �� jsp--%>
<div class="${confirmableObject == 'iTunes' ? 'confirmMessagePadding' : ''}">
    ${message}
</div>

<c:if test="${inactiveESMessages != '' || informMessage != '' || not empty confirmRequest.messages}">
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="orangeBlock"/>
        <tiles:put name="data">
            <c:set var="inactiveESMessage"><bean:write name='inactiveESMessages' filter='false'/></c:set>
            <c:if test="${not empty inactiveESMessage}">
                <div class="messageContainer">
                    ${inactiveESMessage}
                </div>
            </c:if>
            <c:if test="${not empty informMessage}">
                <div class="messageContainer">
                    ${informMessage}
                </div>
            </c:if>
            <c:forEach items="${confirmRequest.messages}" var="infoMessage">
                <div>
                    ${infoMessage}
                </div>
            </c:forEach>
        </tiles:put>
    </tiles:insert>
</c:if>

<c:set var="needNewPassword" value="${phiz:isInstance(confirmRequest, 'com.rssl.phizic.auth.modes.SmsPasswordConfirmRequest') && confirmRequest.requredNewPassword}"/>
<c:set var="buttons">
    <div class="buttonsArea <c:if test='${buttonType eq "singleRow" and not needNewPassword}'>float</c:if>
    <c:if test="${confirmableObject == 'translateToExternalAccount' ||
                            confirmableObject == 'translateToExternalCard' ||
                            confirmableObject == 'translateYandexWallet' ||
                            confirmableObject == 'translateOurClient'}"> confirmP2PButtons</c:if>">
        <c:choose>
            <c:when test="${useAjax}">
                <c:set var="ajaxActionUrl">${phiz:calculateActionURL(pageContext, ajaxUrl)}</c:set>
                <c:if test="${showCancelButton}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="securityBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="onclick" value="win.close(confirmOperation.windowId);"/>
                    </tiles:insert>
                </c:if>
                <c:choose>
                    <c:when test="${needNewPassword}">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.newSMSpassword"/>
                            <tiles:put name="commandHelpKey" value="button.newSMSpassword.help"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                            <tiles:put name="onclick">confirmOperation.openConfirmWindow('${preConfirmCommandKey}', '${ajaxActionUrl}');</tiles:put>
                        </tiles:insert>
                        <c:if test="${buttonType eq 'singleRow' and (anotherStrategy or hasCard or hasCap or hasPush)}">
                            <tiles:insert definition="additional_confirm_sms" flush="false">
                                <tiles:put name="hasCard" value="${anotherStrategy or hasCard}"/>
                                <tiles:put name="hasCap" value="${hasCap}"/>
                                <tiles:put name="hasPush" value="${hasPush}"/>
                                <tiles:put name="hasCapButton" value="false"/>
                            </tiles:insert>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <script type="text/javascript">
                            function onConfirm()
                            {
                                var onComplete = undefined;
                                <c:if test="${not empty ajaxOnCompleteCallback}">
                                onComplete = function() { ${ajaxOnCompleteCallback}; };
                                </c:if>
                                var onError = undefined;
                                <c:if test="${not empty ajaxOnErrorCallback}">
                                onError = function() { ${ajaxOnErrorCallback}; };
                                </c:if>
                                <c:if test="${phiz:isScriptsRSAActive()}">
                                    if (typeof RSAObject != "undefined")
                                    {
                                        new RSAObject().toHiddenParameters();
                                    }
                                </c:if>
                                confirmOperation.validateConfirmPassword('${confirmCommandKey}', '${ajaxActionUrl}', confirmOperation.type.sms, onComplete, '${formId}', onError);
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="${confirmCommandKey}"/>
                            <tiles:put name="commandHelpKey" value="${confirmCommandKey}.help"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="onclick">onConfirm();</tiles:put>
                        </tiles:insert>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:if test="${showCancelButton}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="securityBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="onclick" value="win.close('confirm_sms');"/>
                    </tiles:insert>
                </c:if>
                <c:choose>
                    <c:when test="${needNewPassword}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="${preConfirmCommandKey}"/>
                            <tiles:put name="commandTextKey" value="button.newSMSpassword"/>
                            <tiles:put name="commandHelpKey" value="button.newSMSpassword.help"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                        </tiles:insert>
                    </c:when>
                    <c:otherwise>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="${confirmCommandKey}"/>
                            <tiles:put name="commandHelpKey" value="${confirmCommandKey}.help"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="validationFunction" value="validatePasswordField();"/>
                        </tiles:insert>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
        <div class="clear"></div>
    </div>
</c:set>

<div id="paymentForm${byCenter}" onkeypress="
<c:choose>
    <c:when test="${onKeyPressFunction == null}">
        onEnterKey(event);
    </c:when>
    <c:otherwise>
        ${onKeyPressFunction}
    </c:otherwise>
</c:choose>
">
        ${data}
    <c:choose>
        <c:when test="${buttonType eq 'singleRow'}">
            <div class="singleRowPasswordBlock">
                <c:if test="${not needNewPassword}">
                    <div class="singleRowPassword">
                        <span class="fieldTitle" style="color: ${textColor}" id="fieldTitle">������� SMS-������</span>
                        <input type="text" class="confirmSmsInput confirmInput" name="$$confirmSmsPassword" size="10"/>
                    </div>
                </c:if>
                ${buttons}
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${confirmableObject != 'iTunes'}">
                <c:if test="${guest && mobileBankExist}">
                    <hr/>
                    ��� �������� ������, ���������� ����������� ���� �������� �� ������ ������ ���������� �� ���� ��������� �������. ��� ����� ��� �����:
                    <tiles:insert page="/WEB-INF/jsp-sbrf/payments/bki-number-message.jsp" flush="false">
                        <tiles:put name="documentShortNumber" value="${documentShortNumber}"/>
                        <tiles:put name="bankPhoneForSms" value="900"/>
                    </tiles:insert>
                </c:if>
            </c:if>
            <c:if test="${confirmableObject == 'translateToExternalAccount' ||
                            confirmableObject == 'translateToExternalCard' ||
                            confirmableObject == 'translateYandexWallet' ||
                            confirmableObject == 'translateOurClient'}"><div class="confirmP2PPasswordBlock"></c:if>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">
                    <c:set var="textColor" value="black"/>
                    <c:if test="${confirmRequest.errorFieldPassword}">
                        <c:set var="textColor" value="red"/>
                    </c:if>
                    <span class="fieldTitle" style="color: ${textColor}" id="fieldTitle">
                        �������
                        <span class="bold">
                            SMS-������ :
                        </span>
                    </span>
                </tiles:put>
                <tiles:put name="data">
                    <div class="confirm_hint">
                        <c:if test="${not empty hint}">
                            <div style="padding-left:4px">
                                <span>
                                    ${hint}
                                </span>
                            </div>
                        </c:if>
                    </div>
                    <input type="text" class="confirmSmsInput confirmInput" name="$$confirmSmsPassword" size="10"/>
                </tiles:put>
            </tiles:insert>
            <c:if test="${confirmableObject == 'translateToExternalAccount' ||
                            confirmableObject == 'translateToExternalCard' ||
                            confirmableObject == 'translateYandexWallet' ||
                            confirmableObject == 'translateOurClient'}"></div></c:if>
        </c:otherwise>
    </c:choose>
    <c:if test="${confirmableObject == 'accountOpeningClaimInLoanClaim'}">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <input type="hidden" name="confirmDocumentId" value='${form.document.id}'/>
    </c:if>
    <div class="clear"></div>
</div>
<c:if test="${buttonType eq 'standart'}">${buttons}</c:if>
</c:set>

<c:choose>
    <c:when test="${useAjax}">
        ${fullData}

        <script type="text/javascript">
            $(document).ready(function(){
                $("input[name='$$confirmSmsPassword']").click();
            });
        </script>
    </c:when>
    <c:otherwise>
        <tiles:insert definition="window" flush="false">
            <tiles:put name="id" value="confirm_sms"/>
            <tiles:put name="data">${fullData}</tiles:put>
        </tiles:insert>

        <script type="text/javascript">
            function validatePasswordField()
            {
                var pass = getElement('$$confirmSmsPassword');
                if (pass.value == '' || pass.value == null)
                {
                    addError("������� ������, ���������� ����� sms.", 'warningMessages');
                    var errorDiv = document.getElementById('warningMessages');
                    var errorField = document.getElementById('fieldTitle');
                    errorField.style.color = 'red';
                    hideOrShow(errorDiv, false);
                    return false;
                }
                return true;
            }
            function openSmsWindow()
            {
                win.open('confirm_sms');
                if (document.getElementsByName('$$confirmSmsPassword').length == 1)
                    document.getElementsByName('$$confirmSmsPassword')[0].focus();

                var confirmHeight = $(document.getElementById('confirm_smsWin')).height();
                var browserWinHeight = screen.height;
                if (confirmHeight > browserWinHeight)
                {
                    window.scrollTo(0, document.body.scrollHeight);
                }
                ;
                var errorDiv = document.getElementById('warningMessages');
                var hide = true;
            <c:if test="${confirmRequest.error}">
                hide = false;
            </c:if>
                hideOrShow(errorDiv, hide);
            }
            doOnLoad(openSmsWindow);
        </script>
    </c:otherwise>
</c:choose>
</c:if>
