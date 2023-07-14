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
    ������ ��� ������������� �������� ������� �� �����
        confirmRequest      - ������ �� �������������
        confirmStrategy     - ��������� �������������
        message             - ��������� ������������ ������������
        confirmableObject   - �������������� ������
        anotherStrategy     - �������� �� ������������� ������ ����������
        data                - ������ ������������ ������������
        confirmCommandKey   - ���� ��� ������ �������������
        useAjax             - ������� �������� ���� ����� ������������ ������ � ������� ajax (true - ������������ ajax, false - ������������ commandButton)
        ajaxUrl             - url, ���������� ����� ajax
        showCancelButton    - ���������� �� ������ "��������"
        buttonType          - ��� ����������� ����� ������ (standart -- ������ ���� ���� ����� ������, singleRow -- ������ �� ����� ������ � �����)
--%>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="bundle" value="userprofileBundle"/>

<c:if test="${not empty confirmStrategy}">
    <c:set var="anotherStrategy" value="${phiz:isContainStrategy(confirmStrategy,'sms')}"/>
</c:if>

<c:if test="${!useAjax}">
    <tiles:insert definition="commandButton" flush="false">
        <tiles:put name="commandKey" value="button.confirmCap"/>
        <tiles:put name="commandTextKey" value="button.confirmCap"/>
        <tiles:put name="commandHelpKey" value="button.confirmCap"/>
        <tiles:put name="bundle" value="securityBundle"/>
    </tiles:insert>
    <c:if test="${phiz:isContainStrategy(confirmStrategy,'card')}">
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.confirmCard"/>
            <tiles:put name="commandTextKey" value="button.confirmCard"/>
            <tiles:put name="commandHelpKey" value="button.confirmCard"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="bundle" value="securityBundle"/>
        </tiles:insert>
    </c:if>
    <c:if test="${phiz:isContainStrategy(confirmStrategy,'sms')}">
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.confirmSMS"/>
            <tiles:put name="commandTextKey" value="button.confirmSMS"/>
            <tiles:put name="commandHelpKey" value="button.confirmSMS"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="bundle" value="securityBundle"/>
        </tiles:insert>
    </c:if>
</c:if>

<c:if test="${confirmRequest.preConfirm}">
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
                <c:when test="${confirmableObject == 'mailNotificationSettings'}">
                    ������������� �������� ����������
                </c:when>
                <c:when test="${confirmableObject == 'personalSettings'}">
                    ������������� ������������ ����������
                </c:when>
                <c:when test="${confirmableObject == 'viewSettings'}">
                    ������������� �������� ����������
                </c:when>
                <c:when test="${confirmableObject == 'activePerson'}">
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
                <c:when test="${confirmableObject == 'CardReportDeliveryClaim'}">
                    <bean:message bundle="cardInfoBundle" key="email.report.delivery.win.title"/>
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
                <c:otherwise>
                    ������������� �������
                </c:otherwise>
            </c:choose>
        </h2>
        <br/>

        <div class="warningMessage" id="warningMessages">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="color" value="red"/>
                <tiles:put name="data">
                    <div class="messageContainer">
                            <c:out value="${confirmRequest.errorMessage}"/>
                    </div>
                    <div class="clear"></div>
                </tiles:put>
            </tiles:insert>
        </div>

        <%--������� ���������� ��������� ���������� �� jsp--%>
        <div>
                ${message}
        </div>
        <c:if test="${not empty confirmRequest.messages}">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="orangeBlock"/>
                <tiles:put name="data">
                    <%--����� ���������� �������������� ��������� �� ��������--%>
                    <c:forEach items="${confirmRequest.messages}" var="infoMessage">
                        <div>
                                ${infoMessage}
                        </div>
                    </c:forEach>

                </tiles:put>
            </tiles:insert>
        </c:if>
    
        <c:set var="inactiveinactiveESMessagesSMessages" value=""/>
        <phiz:messages id="inactiveES" bundle="${bundle}" field="field" message="inactiveExternalSystem">
            <c:set var="inactiveESMessages">${inactiveESMessages}<div class = "itemDiv">${phiz:processBBCode(inactiveES)} </div></c:set>
        </phiz:messages>
    
        <c:if test="${fn:length(inactiveESMessages) gt 0}">
            <div class="warningMessage" id="warningESMessages">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="orangeBlock"/>
                    <tiles:put name="data">
                        <div class="messageContainer">
                            <bean:write name='inactiveESMessages' filter='false'/>
                        </div>
                        <div class="clear"></div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:if>

        <c:set var="buttons">
            <div class="buttonsArea <c:if test='${buttonType eq "singleRow"}'>float</c:if>">
                <script type="text/javascript">
                    function onConfirm()
                    {
                        <c:if test="${phiz:isScriptsRSAActive()}">
                            if (typeof RSAObject != "undefined")
                            {
                                new RSAObject().toHiddenParameters();
                            }
                        </c:if>
                        confirmOperation.validateConfirmPassword('${confirmCommandKey}', '${ajaxActionUrl}', confirmOperation.type.cap);
                    }
                </script>
                <c:choose>
                    <c:when test="${useAjax}">
                        <c:set var="ajaxActionUrl">${phiz:calculateActionURL(pageContext, ajaxUrl)}</c:set>
                        <c:if test="${showCancelButton}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="win.close(confirmOperation.windowId);"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="${confirmCommandKey}"/>
                            <tiles:put name="commandHelpKey" value="${confirmCommandKey}.help"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="onclick">onConfirm();</tiles:put>
                        </tiles:insert>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${showCancelButton}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="win.close('confirm_cap');"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="${confirmCommandKey}"/>
                            <tiles:put name="commandHelpKey" value="${confirmCommandKey}.help"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="validationFunction" value="validatePasswordField();"/>
                        </tiles:insert>
                    </c:otherwise>
                </c:choose>
                <div class="clear"></div>
            </div>
        </c:set>
        <div id="paymentForm" onkeypress="onEnterKey(event);">
                ${data}
                <c:choose>
                    <c:when test="${buttonType eq 'singleRow'}">
                        <div class="singleRowPasswordBlock">
                            <div class="singleRowPassword">
                                <span class="fieldTitle" style="color: ${textColor}" id="fieldTitle">������� ������������ �� ����� �����</span>
                                <input type="text" name="$$confirmCapPassword" size="10" class="confirmInput"/>
                            </div>
                            ${buttons}
                        </div>
                    </c:when>
                    <c:otherwise>
                        <hr/>
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title">
                                <c:set var="textColor" value="black"/>
                                <c:if test="${confirmRequest.errorFieldPassword}">
                                    <c:set var="textColor" value="red"/>
                                </c:if>
                                    <span class="fieldTitle" style="color: ${textColor}" id="fieldTitle">
                                        ������� ������������ �� ����� �����:
                                    </span>
                            </tiles:put>
                            <tiles:put name="data">
                                <div class="confirm_hint">

                                </div>
                                <input type="text" name="$$confirmCapPassword" size="10" class="confirmInput"/>
                                <div class="clear"></div>
                            </tiles:put>
                        </tiles:insert>
                    </c:otherwise>
                </c:choose>
            <div class="clear"></div>
        </div>
        <c:if test="${buttonType eq 'standart'}">${buttons}</c:if>
    </c:set>

    <c:choose>
        <c:when test="${useAjax}">
            ${fullData}
        </c:when>
        <c:otherwise>
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="confirm_cap"/>
                <tiles:put name="data">${fullData}</tiles:put>
            </tiles:insert>

            <script type="text/javascript">
                function validatePasswordField()
                {
                    var pass = getElement('$$confirmCapPassword');
                    if (pass.value == '' || pass.value == null)
                    {
                        addError("������� ������������ �� ����� �����.", 'warningMessages');
                        var errorDiv = document.getElementById('warningMessages');
                        var errorField = document.getElementById('fieldTitle');
                        errorField.style.color = 'red';
                        hideOrShow(errorDiv, false);
                        return false;
                    }
                    return true;
                }
                function openCapWindow()
                {
                    win.open('confirm_cap');
                    if (document.getElementsByName('$$confirmCapPassword').length == 1)
                        document.getElementsByName('$$confirmCapPassword')[0].focus();

                    var confirmHeight = $(document.getElementById('confirm_cardWin')).height();
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
                doOnLoad(openCapWindow);
            </script>
        </c:otherwise>
    </c:choose>
</c:if>
