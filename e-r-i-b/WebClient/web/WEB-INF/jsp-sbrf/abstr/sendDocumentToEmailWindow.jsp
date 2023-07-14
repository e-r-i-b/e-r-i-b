<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<h2><bean:message bundle="sendClaimBundle" key="label.title"/></h2>
<div class="warningMessage" id="docErrors">
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="red"/>
        <tiles:put name="data">
            <div class="messageContainer">
            </div>
            <div class="clear"></div>
        </tiles:put>
    </tiles:insert>
</div>
<div id="editMailAdress">
    <div class="messageContainer">
        <bean:message bundle="sendClaimBundle" key="label.description"/>
    </div><hr/>

    <tiles:insert definition="formRow" flush="false">
        <tiles:put name="title"><bean:message bundle="sendClaimBundle" key="label.recipient.mail"/></tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="data">
            <div onkeypress="keyEnter(event, sendDocument)">
                <c:set var="personInfo" value="${phiz:getPersonInfo()}"/>
                <input type="hidden" id="hiddenEmail" value="${personInfo.email}" disabled="disabled"/>
                <input type="text" name="field(eMail)" id="eMail" value="${personInfo.email}" maxlength="70"/>
            </div>
        </tiles:put>
    </tiles:insert>

    <div class="buttonsArea">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.cancel"/>
            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
            <tiles:put name="bundle" value="sendClaimBundle"/>
            <tiles:put name="onclick" value="win.close('sendDocumentWindow');"/>
            <tiles:put name="viewType" value="buttonGrey"/>
        </tiles:insert>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.send"/>
            <tiles:put name="commandHelpKey" value="button.send.help"/>
            <tiles:put name="bundle" value="sendClaimBundle"/>
            <tiles:put name="onclick" value="sendDocument();"/>
        </tiles:insert>
        <div class="clear"></div>
    </div>
</div>
<div id="responceSended" style="display:none;">
    <div class="messageContainer">
        <bean:message bundle="sendClaimBundle" key="label.response.send"/>&nbsp;<span id="mailAdress"></span>.
        <bean:message bundle="sendClaimBundle" key="label.status.watch"/>
        <html:link action="/private/payments/common" onclick="openHistory();" styleClass="MenuItem" title="Истрория операций">
            <bean:message bundle="sendClaimBundle" key="label.operation.history"/>
        </html:link>.
        <div class="imageMouthpiece">
            <img src="${globalUrl}/commonSkin/images/mouthpiece.png">
        </div>
        <div class="responceSendedText">
            <bean:message bundle="sendClaimBundle" key="label.response.sended"/>
        </div>
    </div>
</div>

<tiles:insert page="/WEB-INF/jsp/common/layout/emailValidation.jsp"/>

<script type="text/javascript">
    var claimProcessing = false;

    var sendingDocument = {
        date : '',
        documentNumber: '' ,
        accNumber: '',
        debit: '0',
        credit: '0',
        clientName: '',
        authCode: ''
    };


    function removeValidationErrors()
    {
        $('#docErrors').hide();
        removeAllErrors('errors');
        $('#editMailAdress').show();
        $('#responceSended').hide();
    }

    $(document).ready(function(){
        $('#docErrors').hide();
    });

    function sendDocument()
    {
        if (claimProcessing)
            return;
        claimProcessing = true;

        $('#eMail').val(trim($('#eMail').val()));
        var mail = $('#eMail').val();
        <c:set var="message">
            <bean:message bundle="sendClaimBundle" key="email.empty"/>
        </c:set>
        if(mail.length == 0)
        {
            var mes = "${message}";
            removeAllErrors('docErrors');
            addError(mes, 'docErrors');
            claimProcessing = false;
            return;
        }
        var validationResult = validateEmail($('#eMail'), 'E-mail получателя');
        if(isNotEmpty(validationResult))
        {
            removeAllErrors('docErrors');
            addError(validationResult, 'docErrors');
            claimProcessing = false;
            return;
        }
        var params =      'operation=button.send&field(eMail)='+mail;
        params = params + '&field(accountNumber)='+sendingDocument.accNumber;
        params = params + '&field(credit)='+sendingDocument.credit;
        params = params + '&field(sendOperationDate)='+sendingDocument.date;
        params = params + '&field(documentNumber)='+sendingDocument.documentNumber;
        params = params + '&field(debit)='+sendingDocument.debit;
        params = params + '&field(authCode)='+sendingDocument.authCode;
        params = params + '&field(clientName)='+sendingDocument.clientName;

        ajaxQuery (params, "${phiz:calculateActionURL(pageContext, '/private/async/operation/scan/claim/send')}",function(data)
        {
            if(data != undefined && data.eMail != undefined)
            {
                removeValidationErrors();
                $('#docErrors').hide();
                $('#responceSended').find('#mailAdress').html('<b>'+data.eMail+'</b>');
                $('#editMailAdress').hide();
                $('#responceSended').show();
            }
            else if (data != undefined && data.messages != undefined)
            {
                removeAllErrors('docErrors');
                addError(data.messages, 'docErrors');
            }
            claimProcessing = false;
        }, "json", true);
    }

    function keyEnter(event, func)
    {
        event = event || window.event;
        var code = navigator.appName == 'Netscape' ? event.which : event.keyCode;
        if(code != 13)
            return;
        if  (event != null)
        {
            try
            {
                if (event.preventDefault)
                    event.preventDefault();
                else
                    event.returnValue = false;
            }
            catch(e)
            {
            }
        }
        func();
    }
    function onCloseWindow()
    {
        removeValidationErrors();
        $('#eMail').val($('#hiddenEmail').val());
        return true;
    }

    function openWindow(elem)
    {
        var line = $(elem).parents('.sendingRecord');

        sendingDocument.date = trim(line.find('input.operationDate').val());
        sendingDocument.clientName = trim(line.find('input.clientName').val());
        sendingDocument.authCode = trim(line.find('input.authCode').val());
        sendingDocument.accNumber = trim(line.find('input.cardNumber').val());

        sendingDocument.documentNumber = trim(line.find('td.documentNumber').text());
        sendingDocument.debit = trim(line.find('td.debit span').text());
        sendingDocument.credit = trim(line.find('td.credit span').text());
        win.open('sendDocumentWindow');
    }

    function openHistory()
    {
        window.opener.location = "${phiz:calculateActionURL(pageContext,'/private/payments/common')}";
        window.close();
    }
</script>
