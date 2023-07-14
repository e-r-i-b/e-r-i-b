<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="isGuest" value="${phiz:isGuest()}"/>
<c:choose>
    <c:when test="${isGuest}">
        <c:set var="fullPhoneMunber" value="${form.document.ownerGuestPhone}"/>
    </c:when>
    <c:otherwise>
        <c:set var="fullPhoneMunber" value="${form.document.fullMobileNumber}"/>
    </c:otherwise>
</c:choose>

<%--��������� �� ������ � ���������� ����� (��)--%>
<c:set var="isMBConnectedPerson" value="${phiz:isMBConnected()}"/>

<script type="text/javascript">
    var isCallMeWindowOpen = false;

    function showMessage(messageBlockId, messageText, messageTitle)
    {
        if (isEmpty(messageText) || isEmpty(messageBlockId))
            return;
        var messageBlock = $('#' + messageBlockId);
        if (isEmpty(messageBlock))
            return;
        <%--����� ��������� � ���������� ���, ���� ����������--%>
        var title = messageBlock.find(".title");
        <%--���� �������� ���� ��� ��������� ���������--%>
        if (isNotEmpty(messageTitle))
        {
            if (isEmpty(title.get(0)))
            {
                var infoMessage = messageBlock.find(".infoMessage");
                infoMessage.append("<div class='title'>" + messageTitle + "</div>");
            }
        }
        title.text(isEmpty(messageTitle) ? "" : messageTitle);
        messageBlock.find(".messageContainer").text(messageText);
        messageBlock.show();
    }

    function hideMessage(messageBlockId)
    {
        var messageBlock = $('#' + messageBlockId);
        if (isEmpty(messageBlock))
            return;
        messageBlock.hide();
        messageBlock.find(".messageContainer").text("");
    }
    <c:if test="${not isGuest}">
        <%--�������� �������� �� ������ �� ���� ��������� ������� (���)--%>
        function checkAccessBci()
        {
            if (!$('#acceptToCreditHistory').attr('checked'))
            {
                showMessage('callFromBankErrors','����������� ���� �������� �� ������ ������ ���������� �� ���� ��������� �������','');
                return false;
            }
            return true;
        }
    </c:if>
    <%--�������� ����� ������--%>
    function validatePasswordField()
    {
        var pwInput = $("input[name='$$confirmSmsPassword']");
        if (isEmpty(pwInput.val()))
        {
            showMessage('callFromBankErrors','������� ������, ���������� ����� sms','');
            return false;
        }
        return true;
    }

    <%--����������� ������--%>
    function checkShowErrorMessage(data)
    {
        if (isNotEmpty(data))
        {
            <%--���� ������ ������, �� ������� ��--%>
            if (isNotEmpty(data.errorMessage))
            {
                showMessage('callFromBankErrors', data.errorMessage,'');
                return true;
            }
        }
        return false;
    }
    <%--����������� �������������� ���������--%>
    function showInfoMessage(data)
    {
        if (isEmpty(data))
            return;
        if (isNotEmpty(data.informMessage))
        {
            showMessage('infoMesages', data.informMessage);
            return;
        }
        hideMessage('infoMesages');
    }

    <%--�������� ��� ������� �� �������������--%>
    function confirmBySms()
    {
        var params = {};
        params['operation'] = 'button.confirmSMS';
        params['field(acceptToCreditHistory)'] = $('#acceptToCreditHistory').attr('checked');
        params['loanClaimId'] = ${form.document.id};

        sendAjaxQuery(params,
            function (data)
            {
                if (isNotEmpty(data))
                {
                    <%--������������ ����� �������� �������������--%>
                    $("#confirmToken").val(data.token);
                    <%--������� �������� ���� ���� � ������ �������--%>
                    if (!checkShowErrorMessage(data))
                    {
                        $("#callMeButtonAreaBlock").hide();
                        $("#pswInputBlock").show();
                        hideMessage('callFromBankErrors');
                        $("input[name='$$confirmSmsPassword']").focus();
                    }
                    <%--���� ������ ������.���������, �� ������� � ��--%>
                    showInfoMessage(data);
                }
                else
                {
                    $("#callMeButtonAreaBlock").show();
                }
            }
        );
    }

    <%--������������� ������� �� ���--%>
    function dispatch()
    {
        if (validatePasswordField())
        {
            var params = {};
            params['operation'] = 'button.dispatch';
            <c:if test="${not isGuest}">
                params['field(acceptToCreditHistory)'] = $('#acceptToCreditHistory').attr('checked');
            </c:if>
            params['loanClaimId'] = ${form.document.id};
            params['org.apache.struts.taglib.html.TOKEN'] = $("#confirmToken").val();
            params['$$confirmSmsPassword'] = $("input[name='$$confirmSmsPassword']").val();

            sendAjaxQuery(params,
                function (data)
                {
                    if (isNotEmpty(data))
                    {
                        <%--���� ������ ������.���������, �� ������� ��--%>
                        showInfoMessage(data);
                        <%--���� ��������� ������ ������, ���� ������ ��� ������--%>
                        if (data.isSmsPasswordExist == 'true')
                        {
                            $("#pswInputBlock").show();
                            <%--���� �� �������� ������ ��� ��������, �� �� ��������� � ��--%>
                            <c:if test="${not isGuest || not isMBConnectedPerson}">
                                $("#callMeButtonAreaBlock").hide();
                            </c:if>
                            $("input[name='$$confirmSmsPassword']").focus();
                        }
                        <%--������� �������� ���� ���� � ������ ������--%>
                        if (checkShowErrorMessage(data))
                        {
                            <%--���� ���������� ��������� �������� ������ - ��������� ��� ������ �������� ������--%>
                            if (data.needNewPassword == 'true')
                            {
                                $("#pswInputBlock").hide();
                                $("input[name='$$confirmSmsPassword']").val("");
                                $("#confirmToken").val("");
                                <%--���� �� �������� ������ ��� ��������, �� �� ��������� � ��--%>
                                <c:if test="${not isGuest || not isMBConnectedPerson}">
                                    $("#callMeButtonAreaBlock").show();
                                </c:if>
                            }
                            return;
                        }
                    }
                    <%--���� �� �������� ������ ��� ��������, �� �� ��������� � ��--%>
                    <c:if test="${not isGuest || isMBConnectedPerson}">
                        else
                        {
                            $("#callMeButtonAreaBlock").hide();
                        }
                    </c:if>
                    <%--�� ������ ������ � ������ ���������� ���-���--%>
                    $("#confirmToken").val("");
                    $("input[name='$$confirmSmsPassword']").val("");
                    win.close('callMeWindow');
                    <c:set var="u" value="/${isGuest ? 'guest' : 'private'}/payments/view.do"/>
                    var url = "${phiz:calculateActionURL(pageContext, u)}";
                    window.location = url + "?id=${form.document.id}&needCallBack=true";
                }
            );
        }
    }

    <c:if test="${isGuest and isMBConnectedPerson}">
        function checkGuest()
        {
            var params = {};
            params['operation'] = 'button.checkGuest';
            params['loanClaimId'] = ${form.document.id};

            sendAjaxQuery(params, function(data){});
        }
    </c:if>
    function sendAjaxQuery(params, callbackFunction)
    {
        <c:set var="urlPrefix" value="private"/>
        <c:choose>
            <c:when test="${isGuest}">
                <c:set var="urlPrefix" value="guest"/>
            </c:when>
            <c:otherwise>
                <%--�������� �������� �� ������ �� ���--%>
                if (!checkAccessBci())
                    return;
            </c:otherwise>
        </c:choose>
        <c:set var="url" value="/${urlPrefix}/async/confirm/phoneCall"/>
        ajaxQuery(convertAjaxParam(params), "${phiz:calculateActionURL(pageContext, url)}", callbackFunction,
            "json", true);
    }

    function callFromBankWindowShow(showNotification)
    {
       win.open('callMeWindow');

       $("#infoMesages").hide();
       $("#callFromBankErrors").hide();
       <c:choose>
            <%--������ ��� �������� ������ � ������������ ��� � �����--%>
            <c:when test="${isGuest and isMBConnectedPerson}">
                $("#pswInputBlock").show();
            </c:when>
            <c:otherwise>
                $("#callMeButtonAreaBlock").show();
                $("#pswInputBlock").hide();
            </c:otherwise>
       </c:choose>
       <c:if test="${not isGuest}">
            $('#acceptToCreditHistory').removeAttr('checked');
       </c:if>
       isCallMeWindowOpen = true;
       $("#callMeWin").css({'left': ($(window).width() - $("#callMeWin").width())/2});
       if(showNotification) {
           $("#callMeData").hide();
           $("#infoMesages").show();
           isCallMeWindowOpen = false;
       } else {
           $("#callMeWin #callFromBankErrors").hide();
           $("#callMeData").show();
           $("#infoMesages").hide();
       }
       <c:if test="${isGuest and isMBConnectedPerson}">
           <%--��� �������� ������ � ������������ ��� � ����� ����������� ���� ������� ��������� ������--%>
           checkGuest();
       </c:if>
    }

    function bciDetailInfoWindowShow(show)
    {
       if (show != undefined && ! show)
            win.close('bciDetailInfoWindow');

       if (show != undefined && show)
       {
            win.open('bciDetailInfoWindow');
            $("#bciWin").css({'left': ($(window).width() - $("#bciWin").width())/2});
       }
    }
</script>

<%--���� ����������� ���������� ���������� ��������� � ���� ��������� �������--%>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="bciDetailInfoWindow"/>
    <tiles:put name="notShowCloseButton" value="true"/>
    <tiles:put name="data">
        <div class="bciTitle">
            <h1><bean:message key="loan.callFromBank.bci.HeadMessage" bundle="commonBundle"/></h1>
        </div>
        <div class="bciInfoMessage">
            <bean:message key="loan.callFromBank.bci.infoMessage" bundle="commonBundle"/>
        </div>
        <div class="bciButtonAreaBlock">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.closeWindow"/>
                <tiles:put name="commandHelpKey" value="button.closeWindow.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="viewType" value="simpleLink"/>
                <tiles:put name="onclick" value="bciDetailInfoWindowShow(false);"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>

<%--���� �������� ������� �� �������� ������--%>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="callMeWindow"/>
    <tiles:put name="data">
        <span class="title">
            <%--����������� ���--%>
            <h1 class="completeTitle"><bean:message key="loan.callFromBank.headMessage" bundle="commonBundle"/></h1>
        </span>

        <%--���� ��� ����������� ������--%>
        <tiles:insert definition="errorBlock" flush="false">
            <tiles:put name="color" value="redLight"/>
            <tiles:put name="regionSelector" value="callFromBankErrors"/>
            <tiles:put name="isDisplayed" value="false"/>
            <tiles:put name="needWarning" value="false"/>
        </tiles:insert>
        <%--���� ��� ����������� ������.���������--%>
        <tiles:insert definition="informMessageBlock" flush="false">
            <tiles:put name="regionSelector" value="infoMesages"/>
            <tiles:put name="isDisplayed" value="false"/>
            <tiles:put name="needWarning" value="false"/>
        </tiles:insert>
        <%--��� � ��. ����������--%>
        <div class="messageContainer">
            <div class="messageContainer">
                <div class="separateData">
                    <div class="clientDataToCall">
                        <p class="clientDataText">
                            <span class="word-wrap"><c:out value="${phiz:getFormattedPersonFIO(person.firstName, person.surName, person.patrName)}"/></span>
                        </p>

                        <c:set var="maskPhoneNumber">
                            ${phiz:getCutPhoneNumber(fullPhoneMunber)}
                        </c:set>
                        <p class="clientDataText">${maskPhoneNumber}</p>
                    </div>

                    <div class="clientDataDesc">
                        <bean:message key="loan.callFromBank.infoMessage" bundle="commonBundle"/>
                        <%--��������� ���� ������ ����� ���������� � ����. ��������� ����� ������� ��������� ����������� ����������--%>
                    </div>
                </div>

                <div class="checkBlock">
                    <c:set var="bciLink">
                       <span class="grayLinkIcon" onclick="bciDetailInfoWindowShow(true)">
                           <i>
                               <bean:message key="loan.callFromBank.bciLink" bundle="commonBundle"/>
                           </i>
                       </span>
                    </c:set>
                    <%--��� ���������� ������ �������� ������������ �������� �������� �� �����--%>
                    <c:if test="${not isGuest}">
                        <html:checkbox property="field(acceptToCreditHistory)" name="form" styleId="acceptToCreditHistory" styleClass="float"/>
                    </c:if>
                    <%--������������ ��� ������� (�� ��������) ������ ��� ���� ������ ��������� � ��---%>
                    <c:if test="${not isGuest || isMBConnectedPerson}">
                        <%--<label class="checkInfo" for="acceptToCreditHistory">--%>
                        <label class="checkInfo">
                            <bean:message key="loan.callFromBank.creditHistoryOffice" bundle="commonBundle" arg0="${bciLink}"/>
                            <%--��� ������ ������ � ���������� ������ � ���������� �����, ���������� ����������� ���� �������� �� ������ ������ ����������--%>
                            <%--�� ���� ��������� �������--%>
                        </label>
                    </c:if>
                </div>

                <%--������������ ��� �������� ������ � ���� ����� ��������� � ���---%>
                <c:if test="${isGuest and isMBConnectedPerson}">
                    <%--TODO: ����� ���� ��� ����� ����������� ������� ��� ����� ���������, �������� ��� �����������. ����������� ��������� �������--%>
                    <tiles:insert page="/WEB-INF/jsp-sbrf/payments/bki-number-message.jsp" flush="false">
                        <tiles:put name="documentShortNumber" value="${form.document.bkiClaimNumber}"/>
                        <tiles:put name="bankPhoneForSms" value="900"/>
                    </tiles:insert>
                </c:if>

                <%--���� ����� ������ �� ���--%>
                <div class="pswInputBlock" id="pswInputBlock">
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="loan.callFromBank.password.label" bundle="commonBundle"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <input type="text" name="$$confirmSmsPassword" size="10"/>
                        </tiles:put>
                    </tiles:insert>

                    <div class="pswInputButton">
                        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
                        <c:set var="buttonCommandTextKey" value="button.confirm"/>
                        <c:if test="${isGuest and isMBConnectedPerson}">
                            <c:set var="buttonCommandTextKey" value="button.callMe"/>
                        </c:if>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="${buttonCommandTextKey}"/>
                            <tiles:put name="commandHelpKey" value="${buttonCommandTextKey}.help"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="onclick" value="dispatch();"/>
                        </tiles:insert>
                    </div>
                </div>
                <input type="hidden" id="confirmToken"/>
                <%--�������� ������--%>
                <c:if test="${not isGuest || not isMBConnectedPerson}">
                    <div class="callMeButtonAreaBlock" id="callMeButtonAreaBlock">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.callMe"/>
                            <tiles:put name="commandHelpKey" value="button.callMe.help"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="onclick" value="confirmBySms();"/>
                            <tiles:put name="enabled" value="true"/>
                        </tiles:insert>
                    </div>
                </c:if>
            </div>
        </div>
    </tiles:put>
</tiles:insert>