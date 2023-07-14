<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="guestEntryType" value="${form.request}"/>
<tiles:importAttribute/>
<c:set var="hasAccount" value="${phiz:hasGuestAnyAccount()}"/>
<c:set var="hasPhoneInMobileBank" value="${phiz:hasGuestPhoneInMB()}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>�������� ������</title>
    <link rel="icon" type="image/x-icon" href="${globalUrl}/commonSkin/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/guest.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/style.css"/>
    <link rel="stylesheet" type="text/css" href="${skinUrl}/style.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/guest.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/roundBorder.css"/>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/sbnkd.css"/>

    <script type="text/javascript">
        window.resourceRoot = '${globalUrl}';
    </script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/inf.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.number_format.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/DivFloat.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Regions.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Array.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/tableProperties.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/select.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/select-sbt.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/customPlaceholder.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/dragdealer.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/formatedInput.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/imageInput.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/validators.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/PaymentsFormHelp.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/commandButton.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/clientButton.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/clientButtonUtil.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/builder.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/iframerequest.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/cookies.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/layout.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/windows.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/longOffer.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/TextEditor.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/Moment.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/json2.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/serializeToWin.js"></script>
    <%--���������� ��� ���������� ����� �����(��������, ��� ���: __.__._____)--%>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.maskedinput.js"></script>
    <%--���������� ��� "������" ������--%>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.autocomplete.js"></script>
    <%-- ����������  ������ ��� ������ � ����������� ���������--%>
    <script type="text/javascript" src="${globalUrl}/scripts/Thermometer.js"></script>
    <%-- ���������� ������ ��� ������ �� ���������--%>
    <script type="text/javascript" src="${globalUrl}/scripts/valueSlider.js"></script>
    <%-- ������ ��� ������ select`a � �������� ������ --%>
    <script type="text/javascript" src="${globalUrl}/scripts/liveSearchComponent.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/liveSearchComponentScrolls.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.mousewheel.min.js"></script>
    <%--��������� ��������� ���������� �� �������--%>
    <script type="text/javascript" src="${globalUrl}/scripts/credit.detail.scroller.js"></script>
    <%-- ������� ��� ���� � ����� ������� --%>
    <script type="text/javascript" src="${globalUrl}/scripts/mixinObjects.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/liveSearchInput.js"></script>

    <script type="text/javascript" src="${globalUrl}/scripts/switchery.js"></script>
    <!--[if IE 8]>
    <script type="text/javascript" src="${globalUrl}/scripts/html5.js"></script>
    <style type="text/css">
        .css3 {
            behavior: url(${globalUrl}/commonSkin/PIE.htc);
            position: relative;
        }
    </style>
    <![endif]-->
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.datePicker.js"></script>
    <!--[if IE 6]>
    <script type="text/javascript" src="${globalUrl}/scripts/iepngfix_tilebg.js"></script>
    <link rel="stylesheet" type="text/css" href="${globalUrl}/commonSkin/ie.css"/>
    <link type="text/css" rel="stylesheet" href="${skinUrl}/ie.css"/>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.bgiframe.min.js"></script>
    <![endif]-->
    <c:if test="${!(hasAccount || hasPhoneInMobileBank)}">
        <script type="text/javascript">
            var guestPathRegistration = "${phiz:calculateActionURL(pageContext, "guest/registration.do")}";
            var needCaptcha = ${phiz:needShowCaptchaGuestRegistration(pageContext.request)};
            var urlCaptcha = "/${phiz:loginContextName()}/registration/captcha.png";
            var token = "${phiz:getTokenForGuestRegistration(pageContext)}";
        </script>
        <script type="text/javascript" src="${globalUrl}/scripts/guest/guestRegistration.js"></script>
    </c:if>

    <c:if test="${!empty aditionalHeaderData}">
        <tiles:insert attribute="aditionalHeaderData"></tiles:insert>
    </c:if>
    <script type="text/javascript" src="${globalUrl}/scripts/KeyboardUtils.js"></script>
</head>
<body class="gPage">
<html:form action="/private/payments/view" show="true" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();" styleId="guestPersonData">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="metadata" value="${form.metadata}"/>
<c:set var="isLoanCardClaim" value="${metadata.form.name == 'LoanCardClaim'}"/>
<c:set var="isLoanCardClaimAvailable" value="${phiz:isLoanCardClaimAvailable(false)}"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <%--���� ������ �� ���������������, ���������� ����� �����������--%>
    <tiles:insert definition="guestAfterClaimRegistration">
        <tiles:put name="contentHeader">
            <bean:message key="label.title.${metadata.form.name}" bundle="claimsBundle"/>
        </tiles:put>
        <tiles:put name="showRegBtn" value="${hasPhoneInMobileBank}"/>
        <tiles:put name="showRegistration" value="${hasAccount}"/>
        <tiles:put name="topMessage">

            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="orangeLight"/>
                <tiles:put name="data">
                    <div class="notice ">
                        <c:choose>
                            <c:when test="${isLoanCardClaim && hasPhoneInMobileBank && not empty form.document.offerId}">
                                <div class="noticeTitle">�������! ������ ������� ���������� � ����</div>
                                ����� ����� �������� � ���������� � ��������� ���������.
                                ��� ��������� ����������� ������� ��� ������ ��������, �������������� ��������.
                                ��������, ���������� �� �����, ����� �� �������� ��������� ${phiz:getDepartmentPhoneNumber(form.document)}
                            </c:when>
                            <c:otherwise>
                                <div class="noticeTitle">�������! �� ������� ���� ������</div>
                                ��� ��������� �������� � ���� � ��������� ����� � ���������������� �� ���������� �����
                            </c:otherwise>
                        </c:choose>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="documentID" value="${form.id}"/>
        <tiles:put name="documentType" value="${metadata.form.name}"/>
        <tiles:put name="content">
            <div class="page_content">
                ${form.html}
            </div>
            <%--�����--%>
            <c:set var="state" value="${form.document.state.code}"/>
            <c:set var="stamp" value=""/>
            <c:choose>
                <c:when test="${state=='DELAYED_DISPATCH' || state=='OFFLINE_DELAYED'}">
                    <c:set var="stamp" value="delayed"/>
                </c:when>
                <c:when test="${(state=='DISPATCHED') || (form.metadata.name != 'ExtendedLoanClaim' && (state == 'WAIT_TM' || state == 'PREADOPTED'))}">
                    <c:set var="stamp" value="processed"/>
                </c:when>
                <c:when test="${state=='DISPATCHED' || state=='STATEMENT_READY' || state=='UNKNOW' || state=='SENT' || state=='BILLING_CONFIRM_TIMEOUT'
                                    || state=='BILLING_GATE_CONFIRM_TIMEOUT' || state=='ABS_RECALL_TIMEOUT' || state=='ABS_GATE_RECALL_TIMEOUT'}">
                    <c:set var="stamp" value="received"/>
                </c:when>
                <c:when test="${state == 'ADOPTED'}">
                    <c:set var="stamp" value="accepted"/>
                </c:when>
                <c:when test="${state=='EXECUTED' || state=='SUCCESSED' || state=='TICKETS_WAITING'}">
                    <c:set var="stamp" value="executed"/>
                </c:when>
                <c:when test="${state=='REFUSED'}">
                    <c:set var="stamp" value="refused"/>
                </c:when>
                <c:when test="${state=='WAIT_CONFIRM'}">
                    <c:set var="stamp" value="waitconfirm"/>
                </c:when>
            </c:choose>

            <c:if test="${not empty stamp}">
                <c:set var="id" value="${metadata.form.name}"/>
                <c:set var="OSB" value="${phiz:getOSB(form.department)}"/>
                <c:set var="CorrAcc" value="${phiz:getCorrByBIC(OSB.BIC)}"/>
                <c:set var="isITunes" value="${phiz:isITunesDocument(form.document)}"/>
                <div class="stamp ${isITunes ? 'stampITunes' : ''}" style="position:relative;">
                    <div class="forBankStamp rightBankStamp
                            <c:if test="${id == 'ExtendedLoanClaim'}"> newLoanClaim</c:if><c:if test="${id == 'AccountOpeningClaim' || id == 'AccountOpeningClaimWithClose'}"> afterRulesStamp</c:if> <c:if test="${id == 'RemoteConnectionUDBOClaim'}"> remoteUdboStamp</c:if>">
                        <c:set var="stampDetails">
                            <c:if test="${stamp == 'processed'}">
                                <div style="text-align:center;">
                                    <img src="${imagePath}/stampProcessed_noBorder.gif" width="150px" height="40px"/>
                                </div>
                            </c:if>
                            <span class="stampTitle"><c:out value="${OSB.name}" default="${bankName}"/></span><br>
                            <span class="stampText">���:<c:out value="${OSB.BIC}" default="${bankBIC}"/></span><br>
                            <span class="stampText">����.����: <c:out value="${phiz:getCorrByBIC(OSB.BIC)}" default="${phiz:getCorrByBIC(bankBIC)}"/></span><br>
                        </c:set>
                        <c:if test="${!isITunes}">${stampDetails}</c:if>
                        <div class="${isITunes ? 'stampStateITunes' : ''}" style="text-align:center;">

                            <c:if test="${stamp == 'waitconfirm'}">
                                <img src="${imagePath}/stampWaitConfirm_noBorder.gif"  width="128px" height="45px"/>
                            </c:if>
                            <c:if test="${stamp == 'delayed'}">
                                <img src="${imagePath}/stampDelayed_noBorder.gif"  width="128px" height="25px"/>
                            </c:if>
                            <c:if test="${stamp == 'received'}">
                                <img src="${imagePath}/stampReceived_noBorder.gif" width="128px" height="25px"/>
                            </c:if>
                            <c:if test="${stamp == 'executed'}">
                                <img src="${imagePath}/stampExecuted_noBorder.gif" width="137px" height="18px"/>
                            </c:if>
                            <c:if test="${stamp == 'refused'}">
                                <img src="${imagePath}/stampRefused_noBorder.gif" width="107px" height="17px"/>
                            </c:if>
                            <c:if test="${stamp == 'accepted'}">
                                <img src="${imagePath}/stampAccepted_blue.gif" width="150px" height="30px"/>
                            </c:if>

                        </div>
                        <span style="color:#5d417b;font:bold Arial;font-size:10;white-space: nowrap;"> ${docDate}</span>
                        <c:if test="${isITunes}">${stampDetails}</c:if>
                        <c:if test="${not empty message}">
                            <script type="text/javascript">
                                if (document.getElementById("titleHelp"))
                                    document.getElementById("titleHelp").innerHTML = ${message}
                            </script>
                        </c:if>
                    </div>
                </div>
            </c:if>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.print"/>
                <tiles:put name="commandHelpKey" value="button.printCheck.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="printCheck(${form.id}, event);"/>
                <tiles:put name="image" value="print-check-gray.gif"/>
                <tiles:put name="imageHover" value="print-check-hover.gif"/>
                <tiles:put name="imagePosition" value="left"/>
            </tiles:insert>
            <%--�����--%>
            <c:set var="stateDescr">
                <c:choose>
                    <c:when test="${state=='UNKNOW' or state=='SENT'}">${form.document.defaultStateDescription}</c:when>
                    <c:when test="${state=='EXECUTED' && form.metadata.name == 'GetPrivateOperationScanClaim'}"><bean:message key="payment.state.hint.GetPrivateOperationScanClaim.EXECUTED" bundle="paymentsBundle"/></c:when>
                    <c:otherwise><bean:message key="payment.state.hint.${state}" bundle="paymentsBundle"/></c:otherwise>
                </c:choose>
            </c:set>
            <div id="stateDescription" onmouseover="showLayer('state','stateDescription','default',0,646);" onmouseout="hideLayer('stateDescription');" class="layerFon stateDescription">
                <div class="floatMessageHeader"></div>
                <div class="layerFonBlock">
                    ${stateDescr}
                </div>
            </div>
        </tiles:put>
    </tiles:insert>

    <script type="text/javascript">
        var skinUrl = '${skinUrl}';
        var globalUrl = '${globalUrl}';
        document.webRoot = '/PhizIC';

        function printCheck(paymentId, event)
        {
            var printCheckUrl = "";
            <c:choose>
                <c:when test="${phiz:getUserVisitingMode() == 'PAYORDER_PAYMENT'}">
                    printCheckUrl = "${phiz:calculateActionURL(pageContext,'/external/payments/check_print')}";
                </c:when>
                <c:otherwise>
                    printCheckUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/check_print')}";
                </c:otherwise>
            </c:choose>

            openWindow(event, printCheckUrl + "?id=" + paymentId, "", "resizable=1,menubar=1,toolbar=0,scrollbars=1,width=300,height=700");
        }
    </script>
</html:form>
</body>
</html>