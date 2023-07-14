<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--
    ������ ������������� ����������� ������� (���, ������, ...), ����������� ����� ajax
    ��� ����������� ����������� ��������� ������������� ����������� ���� anotherStrategy+hasCapButton, ���� confirmStrategy. ������������� ������������ ������.
    ���������:
    ajaxUrl             - url, �� ������� ���������� ajax-������
    confirmRequest      - ������ �� �������������
    confirmStrategy     - ��������� �������������
    anotherStrategy     - �������� �� ������������� ������ ����������
    hasCapButton        - ���� �� ������ ������������� CAP
    preConfirmCommandKey- ���� ��� ������ ������ ���� ������������� ����������� �������( + ��������� �� ����� ��������� SMS,Cap ��� Card)
    confirmCommandKey   - ���� ��� ������ ������������� (����� ������ NotConfirmStrategy, �� ��������� ����������� button.dispatch, ��� �� ������ ������)
    message             - ��������� ������������ ������������
    formName            - ��� ����� (������������ ������ ��� ������������� ��������)
    mode                - ����� ����� ������������ (������������ ������ ��� ������������� ��������)
    winConfirmName      - id div ���� �������������
    autoConfirmableObjectType - ��� ������������������� ������� (login, payment)
    stateObject         - �������� ��� �������������(������ StateObject ��� commandButton)
    showButtonHint      - ���������� �� ��������� ��� ������
    messageBundle       - ����� ���������
    needWindow          - ����� �� ���������� ���� �������������
    onclick             - ������, ����������� ����� ��������� ������� ����
    needShowAnotherStrategy - ����� �� ���������� ������ ������ �������������
    viewTypeConfirmSMS  - ��� ����������� ������. �� ��������� buttonGreen.
    imageSms            - �������� ����� � ���������, �������� ������ ��������� � ����� �������� ������� ������������
    imageSmsHover       - ���������� ��� ������ ������ � ������ ��� ���������
    imageSmsPosition    - ��������� �������� (�� ��������� ������)
--%>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="hasSMS" value="${phiz:isContainStrategy(confirmStrategy,'sms')}"/>
<c:set var="hasCard" value="${phiz:isContainStrategy(confirmStrategy,'card')}"/>
<c:set var="hasCap" value="${phiz:isContainStrategy(confirmStrategy,'cap')}"/>
<c:set var="hasPush" value="${phiz:isContainStrategy(confirmStrategy,'push')}"/>
<c:set var="showConfirmWindow" value="false"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<c:if test="${needWindow}">
    <%--����� ����� ������ ���������� � ��� ����--%>
    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="${winConfirmName == '' ? 'oneTimePasswordWindow' : winConfirmName}"/>
        <c:if test="${not empty confirmRequest and not empty form.autoConfirmRequestType}">
            <c:choose>
                <c:when test="${form.autoConfirmRequestType == 'login'}">
                    <tiles:put name="data"><tiles:insert page="/WEB-INF/jsp-sbrf/confirm/confirmAsync.jsp" flush="false"/></tiles:put>
                    <c:set var="showConfirmWindow" value="true"/>
                </c:when>
                <c:when test="${form.autoConfirmRequestType == 'payment'}">
                    <tiles:put name="data"><tiles:insert page="/WEB-INF/jsp-sbrf/payments/confirm-payment-async.jsp" flush="false"/></tiles:put>
                    <c:set var="showConfirmWindow" value="true"/>
                </c:when>
            </c:choose>
        </c:if>
        <c:if test="${formName eq 'NewRurPayment'}">
            <tiles:put name="styleClass" value="mSizeWindow"/>
        </c:if>
    </tiles:insert>
</c:if>

<c:if test="${empty preConfirmCommandKey}">
    <c:set var="preConfirmCommandKey" value="button.confirm"/>
</c:if>
<c:set var="ajaxActionUrl">${phiz:calculateActionURL(pageContext, ajaxUrl)}</c:set>

<script type="text/javascript">
    <%--������������� ��������--%>
    function clickConfirmButton(buttonName, buttonTitle)
    {
        <c:if test="${formName eq 'AccountOpeningClaim' || formName eq 'AccountOpeningClaimWithClose' || formName eq 'CreditReportPayment'}">
            <%--������� �������� ���������� ��������� � ������������� ����������� ������� ������--%>
            removeMessageByButtonName('<bean:message key="button.confirmCard" bundle="securityBundle"/>');
            removeMessageByButtonName('<bean:message key="button.confirmCap" bundle="securityBundle"/>');
            removeMessageByButtonName('<bean:message key="button.confirmSMS" bundle="securityBundle"/>');
            removeMessageByButtonName('<bean:message key="button.confirmPush" bundle="securityBundle"/>');
            <%--�������� ��� ������ ����������� � ��������� ������ � ���� ���, �� �������� ���--%>
            if (!checkClientAgreesCondition(buttonTitle)) return;
        </c:if>

        if (confirmOperation != undefined)
        {
           <c:if test="${winConfirmName != ''}">
                confirmOperation.windowId = '${winConfirmName}';
            </c:if>
            confirmOperation.openConfirmWindow(buttonName, '${ajaxActionUrl}');
        }
    }

    <c:if test="${showConfirmWindow == 'true'}">
        $(document).ready(function(){
            win.open('oneTimePasswordWindow');
            confirmOperation.initLoadedData();
        });
    </c:if>
</script>
<c:if test="${phiz:needKasperskyScript()}">
    <script type="text/javascript" src="https://af.kaspersky-labs.com/sb/kljs"></script>
</c:if>
<c:set var="show" value="true"/>
<c:if test="${(not empty stateObject)}">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<bean:define id="object" name="form" property="${stateObject}"/>
    <c:set var="show" value="${phiz:checkEvent(object, 'button.confirm')}"/>
</c:if>
<c:if test="${show}">
<c:set var="additionalClass"><c:if test="${showButtonHint}">showButtonHint</c:if></c:set>
<c:choose>
    <c:when test="${confirmRequest.strategyType eq 'sms'}">
        <%--����������� �� SMS--%>
        <div class="<c:if test="${anotherStrategy or hasPush or hasCapButton or hasCap or hasCard}">chooseConfirmStrategy</c:if> confirmButtonRegion ${additionalClass}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.confirmSMS"/>
                    <tiles:put name="commandHelpKey" value="button.confirmSMS"/>
                    <tiles:put name="bundle" value="${messageBundle}"/>
                    <tiles:put name="viewType" value="${viewTypeConfirmSMS}"/>
                    <tiles:put name="image" value="${imageSms}"/>
                    <tiles:put name="imageHover" value="${imageSmsHover}"/>
                    <tiles:put name="imagePosition" value="${imageSmsPosition}"/>

                    <tiles:put name="onclick">
                        ${onclick}
                        clickConfirmButton('${preConfirmCommandKey}SMS','<bean:message key="button.confirmSMS" bundle="securityBundle"/>')
                    </tiles:put>
                </tiles:insert>
            <c:if test="${showButtonHint}">
                <div class="confirmButtonHint">
                    <bean:message key="button.confirmSMS.hint" bundle="${messageBundle}"/>
                </div>
            </c:if>
            <c:if test="${needShowAnotherStrategy and (anotherStrategy or hasPush or hasCapButton or hasCap or hasCard)}">
                <tiles:insert definition="additional_confirm_sms" flush="false">
                    <tiles:put name="preConfirmCommandKey" value="${preConfirmCommandKey}"/>
                    <tiles:put name="hasCard" value="${anotherStrategy or hasCard}"/>
                    <tiles:put name="hasCap" value="${hasCap}"/>
                    <tiles:put name="hasPush" value="${hasPush}"/>
                    <tiles:put name="hasCapButton" value="${hasCapButton}"/>
                </tiles:insert>
            </c:if>
        </div>
    </c:when>
    <c:when test="${confirmRequest.strategyType eq 'card'}">
        <%--����������� �����--%>
        <table class="<c:if test="${anotherStrategy or hasSMS or hasPush or hasCapButton or hasCap}">chooseConfirmStrategy</c:if> ${additionalClass}">
            <tr>
                <td>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.confirmCard"/>
                        <tiles:put name="commandHelpKey" value="button.confirmCard"/>
                        <tiles:put name="bundle" value="${messageBundle}"/>
                        <tiles:put name="onclick">
                            ${onclick}
                            clickConfirmButton('${preConfirmCommandKey}Card','<bean:message key="button.confirmCard" bundle="securityBundle"/>')
                        </tiles:put>
                    </tiles:insert>
                    <c:if test="${showButtonHint}">
                        <div class="confirmButtonHint">
                            <bean:message key="button.confirmCard.hint" bundle="${messageBundle}"/>
                        </div>
                    </c:if>
                </td>
            </tr>
            <c:if test="${needShowAnotherStrategy and (anotherStrategy or hasSMS or hasPush or hasCapButton or hasCap)}">
                <tr>
                    <td>
                        <div class="clear"></div>
                        <div class="changeStrategy">
                            <a class="blueGrayLinkDotted">������ ������ �������������</a>
                            <div class="clear"></div>
                            <div class="anotherStrategy" style="width: 350px; z-index: 20">
                                <div class="anotherStrategyTL">
                                   <div class="anotherStrategyTR">
                                       <div class="anotherStrategyTC">
                                           <div class="anotherStrategyItem"></div>
                                       </div>
                                   </div>
                                </div>
                                <div class="anotherStrategyCL">
                                    <div class="anotherStrategyCR">
                                        <div class="anotherStrategyCC">
                                            <ul>
                                                <%--����������� �� SMS--%>
                                                <c:if test="${anotherStrategy or hasSMS}">
                                                    <li onclick="${onclick}; clickConfirmButton('${preConfirmCommandKey}SMS','<bean:message key="button.confirmSMS" bundle="securityBundle"/>')">
                                                        <span class="strategyTitle">SMS-������</span>
                                                        <span class="textStrategy">����������� ������ �������� � SMS �� ����� ��������� ��������</span>
                                                    </li>
                                                </c:if>

                                                <c:if test="${hasCapButton or hasCap}">
                                                    <li onclick="${onclick}; clickConfirmButton('${preConfirmCommandKey}Cap','<bean:message key="button.confirmCap" bundle="securityBundle"/>')">
                                                        <%--����������� �� �����--%>
                                                        <span class="strategyTitle">������ � �����</span>
                                                        <span class="textStrategy">������, ���������� � ����� ������-�����.</span>
                                                    </li>
                                                </c:if>
                                                <%--����������� �� push--%>
                                                <c:if test="${hasPush}">
                                                    <li onclick="${onclick}; clickConfirmButton('${preConfirmCommandKey}Push','<bean:message key="button.confirmPush" bundle="securityBundle"/>')">
                                                        <div class="newLabel <c:if test="${not(hasCapButton or hasCap) && not(anotherStrategy or hasSMS)}">newLabelFirst</c:if>"><img src="${globalImagePath}/newLabel.gif" width="51" height="51"/></div>
                                                        <span class="strategyTitle">Push-������ �� ����������� � ��������� ����������</span>
                                                        <span class="textStrategy">����������� ������ �������� � ���������� ��������� �� ����� ��������� ��������.</span>
                                                    </li>
                                                </c:if>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="anotherStrategyBL">
                                   <div class="anotherStrategyBR">
                                       <div class="anotherStrategyBC">
                                       </div>
                                   </div>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:if>
        </table>
    </c:when>
        <c:when test="${confirmRequest.strategyType eq 'cap'}">
            <table class="<c:if test="${anotherStrategy or hasPush or hasCard or hasSMS}">chooseConfirmStrategy</c:if> ${additionalClass}">
                <tr>
                    <td>
                        <c:if test="${hasCapButton or hasCap}">
                            <%--����������� �� �����--%>
                            <tiles:insert definition="clientButton" flush="false">
                                 <tiles:put name="commandTextKey" value="button.confirmCap"/>
                                 <tiles:put name="commandHelpKey" value="button.confirmCap"/>
                                <tiles:put name="bundle" value="${messageBundle}"/>
                                <tiles:put name="onclick">
                                    ${onclick}
                                    clickConfirmButton('${preConfirmCommandKey}Cap','<bean:message key="button.confirmCap" bundle="securityBundle"/>')
                                </tiles:put>
                            </tiles:insert>
                            <c:if test="${showButtonHint}">
                                <div class="confirmButtonHint">
                                    <bean:message key="button.confirmCap.hint" bundle="${messageBundle}"/>
                                </div>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
                <c:if test="${needShowAnotherStrategy and (anotherStrategy or hasPush or hasCard or hasSMS)}">
                    <tr>
                        <td>
                            <div class="clear"></div>
                            <div class="changeStrategy">
                                <a class="blueGrayLinkDotted">������ ������ �������������</a>
                                <div class="clear"></div>
                                <div class="anotherStrategy" style="width: 350px; z-index: 20">
                                    <div class="anotherStrategyTL">
                                        <div class="anotherStrategyTR">
                                            <div class="anotherStrategyTC">
                                               <div class="anotherStrategyItem"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="anotherStrategyCL">
                                        <div class="anotherStrategyCR">
                                            <div class="anotherStrategyCC">
                                                <ul>
                                                    <c:if test="${anotherStrategy or hasSMS}">
                                                        <li onclick="${onclick}; clickConfirmButton('${preConfirmCommandKey}SMS','<bean:message key="button.confirmSMS" bundle="securityBundle"/>')">
                                                            <%--����������� �� SMS--%>
                                                            <span class="strategyTitle">SMS-������</span>
                                                            <span class="textStrategy">����������� ������ �������� � SMS �� ����� ��������� ��������</span>
                                                        </li>
                                                    </c:if>

                                                    <c:if test="${anotherStrategy or hasCard}">
                                                        <li onclick="${onclick}; clickConfirmButton('${preConfirmCommandKey}Card','<bean:message key="button.confirmCard" bundle="securityBundle"/>')">
                                                        <%--����������� �����--%>
                                                            <span class="strategyTitle">������ � ����</span>
                                                            <span class="textStrategy">������ ������� ���������� �� ���� � ����� ��������� ��� ��������� ���������</span>
                                                        </li>
                                                    </c:if>
                                                    <%--����������� �� push--%>
                                                    <c:if test="${hasPush}">
                                                        <li onclick="${onclick}; clickConfirmButton('${preConfirmCommandKey}Push','<bean:message key="button.confirmPush" bundle="securityBundle"/>')">
                                                            <div class="newLabel <c:if test="${not(anotherStrategy or hasCard) && not(anotherStrategy or hasSMS)}">newLabelFirst</c:if>"><img src="${globalImagePath}/newLabel.gif" width="51" height="51"/></div>
                                                            <span class="strategyTitle">Push-������ �� ����������� � ��������� ����������</span>
                                                            <span class="textStrategy">����������� ������ �������� � ���������� ��������� �� ����� ��������� ��������.</span>
                                                        </li>
                                                    </c:if>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="anotherStrategyBL">
                                       <div class="anotherStrategyBR">
                                           <div class="anotherStrategyBC">
                                           </div>
                                       </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:if>
            </table>
        </c:when>
        <c:when test="${confirmRequest.strategyType eq 'push'}">
            <table class="<c:if test="${anotherStrategy or hasCapButton or hasCard or hasSMS or hasCap}">chooseConfirmStrategy</c:if> ${additionalClass}">
                <tr>
                    <td>
                        <%--����������� �� push--%>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.confirmPush"/>
                            <tiles:put name="commandHelpKey" value="button.confirmPush"/>
                            <tiles:put name="bundle" value="${messageBundle}"/>
                            <tiles:put name="onclick">
                                ${onclick}
                                clickConfirmButton('${preConfirmCommandKey}Push','<bean:message key="button.confirmPush" bundle="securityBundle"/>')
                            </tiles:put>
                        </tiles:insert>
                        <c:if test="${showButtonHint}">
                            <div class="confirmButtonHint">
                                <bean:message key="button.confirmPush.hint" bundle="${messageBundle}"/>
                            </div>
                        </c:if>
                    </td>
                </tr>
                <c:if test="${anotherStrategy or hasCapButton or hasCard or hasSMS or hasCap}">
                    <tr>
                        <td>
                            <tiles:insert definition="additional_confirm_push" flush="false">
                                <tiles:put name="preConfirmCommandKey" value="${preConfirmCommandKey}"/>
                                <tiles:put name="hasSMS" value="${anotherStrategy or hasSMS}"/>
                                <tiles:put name="hasCard" value="${anotherStrategy or hasCard}"/>
                                <tiles:put name="hasCap" value="${hasCap}"/>
                                <tiles:put name="hasCapButton" value="${hasCapButton}"/>
                            </tiles:insert>
                        </td>
                    </tr>
                </c:if>
            </table>
        </c:when>
    <c:otherwise>
        <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
        <tiles:insert definition="${confirmTemplate}" flush="false">
            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
            <tiles:put name="anotherStrategy" beanName="anotherStrategy"/>
            <tiles:put name="message" value="${message}"/>
            <c:if test="${formName == 'AccountOpeningClaim' or
                          formName eq 'AccountOpeningClaimWithClose' or
                          formName eq 'IMAOpeningClaim' or
                          formName eq 'ChangeDepositMinimumBalanceClaim' or
                          formName eq 'AccountChangeInterestDestinationClaim' or
                           formName eq 'CreditReportPayment'}">

                <c:set var="buttonName"><bean:message key="button.dispatch" bundle="securityBundle"/></c:set>
                <tiles:put name="validationFunction" value="checkClientAgreesCondition('${buttonName}');"/>
            </c:if>
            <c:choose>
                <c:when test="${formName == 'SecurityRegistrationClaim'}">
                    <tiles:put name="confirmableObject" value="claim"/>
                </c:when>
                <c:when test="${formName == 'DepositorFormClaim'}">
                    <tiles:put name="confirmableObject" value="DepositorFormClaim"/>
                </c:when>
                <c:when test="${formName == 'SecuritiesTransferClaim'}">
                    <tiles:put name="confirmableObject" value="securitiesTransferClaim"/>
                </c:when>
                <c:when test="${formName == 'RecallDepositaryClaim'}">
                    <tiles:put name="confirmableObject" value="recall"/>
                </c:when>
            </c:choose>
            <tiles:put name="byCenter" value="${mode == 'PAYORDER_PAYMENT' ? 'Center' : ''}"/>
            <c:if test="${not empty preConfirmCommandKey}">
                <tiles:put name="preConfirmCommandKey" value="${preConfirmCommandKey}"/>
            </c:if>
            <c:choose>
                <c:when test="${not empty confirmCommandKey}">
                    <tiles:put name="confirmCommandKey" value="${confirmCommandKey}"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="confirmCommandKey" value="button.dispatch"/>
                </c:otherwise>
            </c:choose>
        </tiles:insert>
    </c:otherwise>
</c:choose>
</c:if>