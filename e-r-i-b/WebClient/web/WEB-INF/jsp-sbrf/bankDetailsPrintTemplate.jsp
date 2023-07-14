<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
    Компонент для отображения формы реквизитов счета вклада или карты.
    Из формы доступны:
    - печать реквизитов
    - отправка на e-mail
    - сохранение в форматах PDF и RTF

    title - заголовок ("Реквизиты перевода на счет вклада/карты")
    accountLink - линк счета вклада
    cardLink - линк счета карты
    useService - сервис отправки сообщения
    detailsMap - мапа с данными реквизитов счета карты или вклада ("ключ" - "значение")
    emailSended - признак того что письмо было отправлено
--%>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagesPath" value="${globalUrl}/commonSkin/images"/>
<c:set var="accountLink" value="${accountLink}"/>
<c:set var="cardLink" value="${cardLink}"/>
<%--флаг реквизитов перевода на счет карты--%>
<c:set var="isCardAccountDetails" value="${not empty cardLink ? true : false}"/>
<%--флаг реквизитов перевода на счет вклада--%>
<c:set var="isAccountDetails" value="${not empty accountLink ? true : false}"/>
<c:set var="detailsMap" value="${details}"/>
<%--Шапка формы--%>

<div id="header">
    <div class="roundTL">
        <div class="roundTR">
            <div class="clear"></div>
            <div class="detailHdrController">
                <%--меню формы--%>
                <div class="detailMenu">
                    <div class="detailsMenuItem" style="width: 200px">
                        <a href="" class="menuItem">
                            <div>
                                <div style="float: right;text-decoration: underline;cursor: pointer;"
                                     onclick="window.print();" onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"
                                     title="Печать">Печать</div>
                                <div class="detailsPrintBlock"></div>
                            </div>
                        </a>
                    </div>
                    <c:if test="${useService != '' && phiz:impliesService(useService)}">
                        <div class="detailsMenuItem" style="width: 165px">
                            <a href="#" class="menuItem" >
                                <div>
                                    <div style="float: right;text-decoration: underline;cursor: pointer;" onclick="sendEmailShow(false)"
                                         onmouseout="mouseLeave(this);" onmouseover="mouseEnter(this);"
                                         title="Отправить на электронную почту">Отправить на e-mail</div>
                                    <div class="detailsMailBlock"></div>
                                </div>
                            </a>
                        </div>
                    </c:if>
                    <div id="save_props" class="detailsMenuItem">
                        <div class="relative">
                            <a href="" class="menuItem">
                                <div>Сохранить реквизиты</div>
                            </a>
                            <div id="detailsMenuHover" style="display: none;" class="detailsMenuHover">
                                <div>
                                    <div>
                                        <ul>
                                            <li>
                                                <span class="firstMenuItem">Сохранить реквизиты</span>
                                                <div class="detalsSubMenu firstItemPosition">
                                                    <div class="greenText">
                                                        <span onclick="createCommandButton('button.unloadRTF').click();"
                                                            onmouseover="saveMouseEnter(this);" title="Экспортировать в RTF">
                                                            DOC (MS Word)
                                                        </span>
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="clientRegion">
                                                <div class="detalsSubMenu">
                                                    <div class="greenText">
                                                        <span onclick="createCommandButton('button.unloadPDF').click();"
                                                           onmouseover="saveMouseEnter(this);" title="Экспортировать в PDF">
                                                            PDF (Adobe Acrobat)
                                                        </span>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>

<%--Блок реквизитов счета вклада или карты--%>
<div id="wrapper" style="min-height: inherit;">
    <div id="workspace" class="detailWorkspace">
        <div class="productDetailInfo">
            <tiles:insert definition="embeddedPrintDoc">
                <tiles:put name="pageTitle" type="string">
                    <style type="text/css">
                        .roundTL, .roundTR {
                            height: 81px;
                        }
                        #header {
                            background:url(${imagesPath}/header_bckg.png) repeat-x left bottom;
                            height: 81px;
                            border-top: none;
                        }
                        .roundTL {
                            background:url(${imagesPath}/roundTL.jpg) left bottom no-repeat;
                        }
                        .roundTR {
                            background:url(${imagesPath}/roundTR.jpg) right bottom no-repeat;
                        }
                        .sendEmailTitle {
                            text-align: center;
                        }

                        .sendEmailResult {
                            text-align: center;
                            display: none;
                        }

                        .sendEmailResult .sendEmailImg {
                            margin-top: 40px;
                        }
                        .sendEmailResult .sendEmailResInfo {
                            margin-top: 30px;
                            color: #99CCCC;
                            font-size: 25px;
                        }

                        .productDetailInfo {
                            margin-left: 100px;
                        }

                        .detailsRow {
                            margin-top: 10px;
                        }

                        .printSourceName {
                            color: #000;
                            font-size: 24px;
                            line-height: 32px;
                            margin-right: 10px;
                        }

                        .detailsDivider {
                            border-bottom: 1px solid #a8a8a8;
                            margin: 35px 0 0 -20px;
                        }

                        .detailsDataBlock {
                            margin-left: 20px;
                        }

                        .detailsHorizAlignment {
                            display: inline-block;
                            margin-top: 10px;
                            width: 100%;
                        }

                        .detailsTitle {
                            text-align: left;
                            float: left;
                            width: 28%;
                        }

                        .detailsValue {
                            display: block;
                            float: left;
                            line-height: 18px;
                            text-align: left;
                            margin-left: 5px;
                            width: 365px;
                        }

                        .legendBlock {
                            margin-left: -40px;
                        }

                        .headerImage {
                            margin: 40px 2px 2px 0;
                        }

                        .headerOfficeInfo {
                            font-size: 14px;
                            color: #000;
                            float:right;
                            position:relative;
                            margin-top:-90px;
                            margin-right: 10px;
                        }

                        .printWorkSpace {
                            margin-right: 100px;
                        }

                        .marginDetails div.detailsHorizAlignment {
                            margin-top: 20px;
                            font-size: 16px;
                            color: #000;
                        }

                        .printTitle {
                            text-align: left;
                            font: bold 14px Arial;
                            margin-top: 60px;
                            color: #606060;
                            text-transform: uppercase;
                        }

                        .headerButtons {
                            background: url(${imagesPath}/printbg.gif) 0 0 repeat-x;
                            height: 54px;
                        }

                        .detailsMailBlock {
                            background: url("${imagesPath}/letter-white.gif") no-repeat 0 0;
                            float: right;
                            height: 16px;
                            width: 25px;
                            cursor: pointer;
                        }
                        .detailsMailBlockHover {
                            background: url("${imagesPath}/letter-yellow.gif") no-repeat 0 0;
                            float: right;
                            height: 16px;
                            width: 25px;
                            cursor: pointer;
                        }
                        .detailsPrintBlock {
                            background: url("${imagesPath}/print-check-white.gif") no-repeat 0 -1px;
                            float: right;
                            height: 16px;
                            width: 20px;
                            cursor: pointer;
                            margin-right: 5px;
                        }
                        .detailsPrintBlockHover {
                            background: url("${imagesPath}/print-check-yellow.gif") no-repeat 0 -1px;
                            float: right;
                            height: 16px;
                            width: 20px;
                            cursor: pointer;
                            margin-right: 5px;
                        }
                        .headerButtons img {
                            margin: 7px;
                            cursor: pointer;
                        }

                        .confirmFloatMesage {
                            padding: 0;
                        }

                        .fieldDescription {
                            float: left;
                            width: 30%;
                        }

                        #errors {
                            padding: 10px 0;
                        }
                        #errors .infoMessage{
                            padding-top: 0;
                            margin-top: -10px;
                            margin-bottom: -10px;
                        }
                        .buttonsWithSeparator {
                            border-right: 1px solid #000000;
                            display: inline;
                            float: left;
                            margin-top: 4px;
                            margin-bottom: 7px;
                        }
                        .buttonsWithSeparator img{
                            margin: 3px 8px 0 8px;
                        }
                        #winErrors .workspace-box, #winErrors {
                            width: 100%;
                        }
                        .fieldDescription {
                            width: 25%;
                        }
                        .detailWorkspace {
                            height: auto !important;
                            margin: 0 auto;
                            min-height: 100%;
                        }
                        .detalsSubMenu {
                            cursor: pointer;
                        }
                    </style>
                    <div class="headerImage">
                        <div>
                            <img src="${globalImagePath}/logoSBRFNew.png"/>
                        </div>
                    </div>
                </tiles:put>
                <tiles:put name="data" type="string">
                    <div class="printWorkSpace">
                        <%--Подразделение, обслуживающее счет--%>
                        <div class="headerOfficeInfo">
                            <c:choose>
                                <c:when test="${isCardAccountDetails}">
                                    <div><c:out value="${cardLink.office.name}"/></div>
                                    <c:if test="${not empty cardLink.office.code}">
                                        <div>№<bean:write name="cardLink" property="office.code.fields(branch)"/>/<bean:write name="cardLink" property="office.code.fields(office)"/></div>
                                    </c:if>
                                </c:when>
                                <c:when test="${isAccountDetails}">
                                    <div><c:out value="${accountLink.office.name}"/></div>
                                    <c:if test="${not empty accountLink.office.code}">
                                        <div>№<bean:write name="accountLink" property="office.code.fields(branch)"/>/<bean:write name="accountLink" property="office.code.fields(office)"/></div>
                                    </c:if>
                                </c:when>
                                <c:otherwise/>
                            </c:choose>
                        </div>

                        <div class="printTitle">${title}</div>

                        <div class="printSourceName detailsRow">
                            <c:if test="${isCardAccountDetails}">
                                ${phiz:getCutCardNumber(cardLink.number)}
                                «<c:out value="${cardLink.name}"/>»&nbsp;(<c:out value="${detailsMap['DESCRIPTION']}"/>), в Сбербанк России ОАО
                            </c:if>
                            <c:if test="${isAccountDetails}">
                                <c:out value="${detailsMap['DESCRIPTION']}"/>
                            </c:if>
                        </div>

                        <div class="marginDetails">
                            <c:set var="owner" value="${detailsMap['RECEIVER']}"/>
                            <c:set var="account_number" value="${detailsMap['accountNumber']}"/>

                            <div class="detailsDataBlock">
                                <div class="detailsHorizAlignment">
                                    <div class="detailsTitle">Получатель:</div>
                                    <div class="detailsValue">${phiz:getFormattedPersonName(owner.firstName, owner.surName, owner.patrName)}</div>
                                </div>

                                <div class="detailsHorizAlignment">
                                    <div class="detailsTitle">Номер счета:</div>
                                    <div class="detailsValue">${account_number}</div>
                                </div>

                                <div class="detailsDivider"></div>

                                <div class="detailsHorizAlignment">
                                    <div class="detailsTitle"><span class="bold">Банк получателя:</span></div>
                                    <div class="detailsValue"><span class="bold">${detailsMap['bankName']}</span></div>
                                </div>

                                <div class="detailsHorizAlignment">
                                    <div class="detailsTitle">БИК:</div>
                                    <div class="detailsValue">${detailsMap['BIC']}</div>
                                </div>

                                <div class="detailsHorizAlignment">
                                    <div class="detailsTitle">Корреспондентский счет:</div>
                                    <div class="detailsValue">${detailsMap['corrAccount']}</div>
                                </div>

                                <div class="detailsHorizAlignment">
                                    <div class="detailsTitle">КПП:</div>
                                    <div class="detailsValue">${detailsMap['KPP']}</div>
                                </div>

                                <div class="detailsHorizAlignment">
                                    <div class="detailsTitle">ИНН:</div>
                                    <div class="detailsValue">${detailsMap['INN']}</div>
                                </div>

                                <div class="detailsHorizAlignment">
                                    <div class="detailsTitle">ОКПО:</div>
                                    <div class="detailsValue">${detailsMap['OKPO']}</div>
                                </div>
                                <div class="detailsHorizAlignment">
                                    <div class="detailsTitle">ОГРН:</div>
                                    <div class="detailsValue">${detailsMap['OGRN']}</div>
                                </div>

                                <div class="detailsDivider"></div>

                                <div class="detailsHorizAlignment">
                                    <div class="detailsTitle">Юридический адрес банка:</div>
                                    <div class="detailsValue">${detailsMap['caAddress']}</div>
                                </div>

                                <c:if test="${!phiz:impliesService('DontShowPostTBAddressInBankDetails')}">
                                    <div class="detailsHorizAlignment">
                                        <div class="detailsTitle">Почтовый адрес банка:</div>
                                        <div class="detailsValue">${detailsMap['tbAddress']}</div>
                                    </div>
                                </c:if>
                                <c:if test="${!phiz:impliesService('DontShowPostVSPAddressInBankDetails')}">
                                    <div class="detailsHorizAlignment">
                                        <div class="detailsTitle">Почтовый адрес доп.офиса:</div>
                                        <div class="detailsValue">${detailsMap['vspAddress']}</div>
                                    </div>
                                </c:if>
                            </div>
                            <br/>
                            <br/>
                            <br/>
                            <div class="legendBlock">
                                <span class="italic">
                                    При выполнении операций со ${isCardAccountDetails ? "счета карты" : "вклада"} необходимо указывать Вашу фамилию, имя, отчество полностью
                                </span>
                            </div>
                            <br/>
                            <br/>
                        </div>
                    </div>

                    <%--Отправка реквизитов на почту--%>
                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="sendEmail"/>
                        <tiles:put name="data">
                            <html:hidden property="id"/>
                            <div class="sendEmailTitle">
                                <h1>Отправка реквизитов на e-mail</h1>
                            </div>

                            <div class="sendEmailResult" id="sendEmailResult">
                                <div class="sendEmailImg">
                                    <img alt="Отправка детальной информации по карте на почту" src="${imagesPath}/mail.png" width="223" height="103"/>
                                </div>
                                <div class="sendEmailResInfo">
                                    Реквизиты отправлены
                                </div>
                            </div>

                            <div id="sendEmailData">
                                <tiles:insert definition="errorBlock" flush="false">
                                    <tiles:put name="regionSelector" value="errors"/>
                                    <tiles:put name="isDisplayed" value="false"/>
                                    <tiles:put name="needWarning" value="true"/>
                                </tiles:insert>

                                <div class="detailsRow">
                                    <div class="fieldDescription">
                                        E-mail получателя<span class="asterisk">*</span>
                                    </div>
                                    <html:text property="field(mailAddress)" size="40" onfocus="setFocusFix(true);" styleId="field(mailAddress)"/>
                                </div>
                                <html:hidden property="field(mailSubject)"/>
                                <div class="detailsRow">
                                    <div class="fieldDescription">Комментарий</div>
                                    <html:textarea styleId="mailText" property="field(mailText)" cols="46" rows="8" style="text-align:justify;"
                                                   onclick="setFocusFix(true);"/>
                                </div>

                                <div class="buttonsArea">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.cancel"/>
                                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                        <tiles:put name="bundle" value="commonBundle"/>
                                        <tiles:put name="onclick" value="win.close('sendEmail');"/>
                                        <tiles:put name="viewType" value="simpleLink"/>
                                    </tiles:insert>
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="btnId" value="submitSend"/>
                                        <tiles:put name="commandTextKey" value="button.send"/>
                                        <tiles:put name="commandHelpKey" value="button.send"/>
                                        <tiles:put name="bundle" value="mailBundle"/>
                                        <tiles:put name="onclick" value="sendMail();"/>
                                    </tiles:insert>
                                </div>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </div>
    </div>
</div>

<tiles:insert page="/WEB-INF/jsp/common/layout/emailValidation.jsp"/>

<script type="text/javascript">
    var isClosed = true;
    var previousClassName;
    var isSenEmailWindowOpen = false;
    var isSendButtonClicked = false;

    function setFocusFix(value)
    {
        isSendEmailToFocus = value;
    }

    function mouseEnter(element, action)
    {
        $(element).parents(".detailMenu").find(".detailsMenuItemHover").removeClass("detailsMenuItemHover");
        $(element).parent().addClass("detailsMenuItemHover");
        $(element).parent().find(".detailsPrintBlock").removeClass("detailsPrintBlock").addClass("detailsPrintBlockHover");
        $(element).parent().find(".detailsMailBlock").removeClass("detailsMailBlock").addClass("detailsMailBlockHover");
        if (action != undefined)
            createCommandButton(action).click();
        return false;
    }

    function mouseLeave(element)
    {
        $(element).parent().removeClass("detailsMenuItemHover");
        $(element).parent().find(".detailsPrintBlockHover").removeClass("detailsPrintBlockHover").addClass("detailsPrintBlock");
        $(element).parent().find(".detailsMailBlockHover").removeClass("detailsMailBlockHover").addClass("detailsMailBlock");
        return false;
    }

    function saveMouseEnter(element, action)
    {
        if (action != undefined)
            createCommandButton(action).click();
        return false;
    }


    $(document).ready(function()
    {
        $('#save_props').live("mouseenter", function(){
            $(this).find('.detailsMenuHover').css('display','block');
        });

        $('#save_props').live("mouseleave", function(){
            $(this).find('.detailsMenuHover').css('display','none');
        });

        initAreaMaxLengthRestriction("mailText", 200);
        $('body')[0].onclick = function (event) {
            if(isSenEmailWindowOpen) {
                isSenEmailWindowOpen = false;
                return false;
            } else {
                event = event || window.event;
                if($('#sendEmailWin.farAway')[0]) {
                    cancelBubbling(event);
                    return;
                }
                var sendEmailWinElement = $('#sendEmailWin')[0];
                if(sendEmailWinElement && isClickOutSide(event, sendEmailWinElement)) {
                    // костыли для IE
                    if(isSendButtonClicked) {
                        // do nothing
                        isSendButtonClicked = false;
                        cancelBubbling(event);
                        return;
                    } else {
                        win.close('sendEmail');
                    }
                }
            }
        }
        <%--Для ишака нужен ОСОБЫЙ подход--%>
        if (isIE())
        {
            var mailAddressList = document.getElementsByName('field(mailAddress)');
            var mailAddressInput = mailAddressList[0];
            if (mailAddressInput != undefined)
            {
                mailAddressInput.onkeyup = function (e) { checkInputText('field(mailAddress)') };
                mailAddressInput.onchange = function (e) { checkInputText('field(mailAddress)') };
                mailAddressInput.onpaste = function (e) { checkInputText('field(mailAddress)') };
            }
        }
        else
        {
            $("input[name='field(mailAddress)']").change(function(){
                checkInputText('field(mailAddress)');
            });
            $("input[name='field(mailAddress)']").keyup(function(){
                checkInputText('field(mailAddress)');
            });
            $("input[name='field(mailAddress)']").bind('paste', function(e) {
                <%--Немного еврейский способ, но работает--%>
                setTimeout(function () {checkInputText('field(mailAddress)')}, 80);
            });
        }

        <%--Энебленость кнопки отправки реквизитов на почту--%>
        checkInputText('field(mailAddress)');
    });

    function isClickOutSide(event, element) {
        var box = getOffsetRect(element);
        if(event.clientX < box.left || event.clientX > box.right)
            return true;
        if(event.clientY < box.top || event.clientY > box.bottom)
            return true;
        return false;
    }

    function sendEmailShow(showNotification)
    {
        win.open('sendEmail');
        isSenEmailWindowOpen = true;
        $("#sendEmailWin").css({'left': ($(window).width() - $("#sendEmailWin").width())/2});
        if(showNotification) {
            $("#sendEmailData").hide();
            $("#sendEmailResult").show();
            isSenEmailWindowOpen = false;
        } else {
            $("#sendEmailWin #errors").hide();
            $("#sendEmailData").show();
            $("#sendEmailResult").hide();
        }
        setFocusFix(false);
    }

    function sendMail()
    {
        isSendButtonClicked = true;
		var address = $("input[name='field(mailAddress)']").val();
        var text = $("textarea[name='field(mailText)']").val();
        var errorDiv = $("#sendEmailWin").find("#errors");
        var sendEmailData = $("#sendEmailData");
        var sendEmailResult = $("#sendEmailResult");
        errorDiv.hide();
        sendEmailResult.hide();

        if (isEmpty(address))
        {
            errorDiv.find(".messageContainer").text("Укажите адрес электронной почты, на который следует выслать реквизиты.");
            errorDiv.show();
            return;
        }
        var textKey = validateEmail($("input[name='field(mailAddress)']"), 'E-mail получателя');
        if (isNotEmpty(textKey))
        {
            errorDiv.find(".messageContainer").text(textKey);
            errorDiv.show();
            return;
        }

        if (!isEmpty(text) && text.length > 200)
        {
            errorDiv.find(".messageContainer").text("Комментарий не должен превышать 200 символов.");
            errorDiv.show();
            return;
        }

        createCommandButton('button.send','').click('', false);
    }

    function getOffsetRect(elem) {

        var box = elem.getBoundingClientRect();

        var body = document.body;
        var docElem = document.documentElement;

        var scrollTop = window.pageYOffset || docElem.scrollTop || body.scrollTop;
        var scrollLeft = window.pageXOffset || docElem.scrollLeft || body.scrollLeft;

        var clientTop = docElem.clientTop || body.clientTop || 0;
        var clientLeft = docElem.clientLeft || body.clientLeft || 0;

        var top  = box.top +  scrollTop - clientTop;
        var left = box.left + scrollLeft - clientLeft;
        var bottom = box.bottom + scrollTop - clientTop;
        var right = box.right + scrollLeft - clientLeft;

        return {top: Math.round(top), left: Math.round(left), bottom: Math.round(bottom), right: Math.round(right)};
    }

    <c:if test="${detailsMap['relocateToDownload'] != null && detailsMap['relocateToDownload'] == 'true'}">
        doOnLoad(
            function ()
            {
                <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/download')}?fileType=${detailsMap['fileType']}&clientFileName=${detailsMap['clientFileName']}"/>
                clientBeforeUnload.showTrigger = false;
                goTo('${downloadFileURL}');
                clientBeforeUnload.showTrigger = false;
            });
    </c:if>
    <c:if test="${emailSended}">
        doOnLoad(
                function(){
                    sendEmailShow(true);
                }
        );
    </c:if>
    <%--Дополнительные проверки на активность кнопки "Отправить"--%>
    function checkInputText(inputName)
    {
        var submitSend = document.getElementById('submitSend');
        var inputList = document.getElementsByName(inputName);
        var input = inputList[0];
        if (input == undefined)
            return;
        if (input.value != '')
        {
            submitSend.className = "buttonGreen";
            submitSend.onclick = function(){ sendMail() };
        }
        else
        {
            submitSend.className = submitSend.className + " disabled";
            submitSend.onclick = null;
        }
    }
</script>