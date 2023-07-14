<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mf="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                xmlns:pru="java://com.rssl.phizic.business.profile.Utils"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:dep="java://com.rssl.phizic.web.util.DepartmentViewUtil">

    <xsl:import href="p2p.html.template.xslt"/>
    <xsl:import href="p2p.autotransfer.html.template.xslt"/>

    <xsl:output method="html" version="1.0"  indent="yes"/>
    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>

    <xsl:param name="mode"                  select="'edit'"/>
    <xsl:param name="webRoot"               select="'webRoot'"/>
    <xsl:param name="application"           select="'application'"/>
    <xsl:param name="app">
       <xsl:value-of select="$application"/>
    </xsl:param>
    <xsl:param name="resourceRoot"          select="'resourceRoot'"/>
    <xsl:param name="longOffer"             select="true()"/>
    <xsl:param name="personAvailable"       select="true()"/>
    <xsl:param name="documentState"         select="'documentState'"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/>

    <xsl:variable name="styleClassTitle"    select="'pmntInfAreaTitle'"/>
    <xsl:variable name="styleSpecial"       select="''"/>

    <xsl:template match="/">
        <script type="text/javascript">

            function isEmpty(value)
            {
                if (value == null || value == "")
                    return true;
                return false;
            }

        </script>

        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:if test="$app = 'PhizIC'">
                    <xsl:apply-templates mode="PhizIC-edit"/>
                </xsl:if>
                <xsl:if test="$app = 'PhizIA'">
                    <xsl:apply-templates mode="PhizIA-edit"/>
                </xsl:if>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:if test="$app = 'PhizIC'">
                    <xsl:apply-templates mode="PhizIC-view"/>
                </xsl:if>
                <xsl:if test="$app = 'PhizIA'">
                    <xsl:apply-templates mode="PhizIA-view"/>
                </xsl:if>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="PhizIA-edit">
        <xsl:variable name="activeCards"            select="document('stored-active-or-arrested-cards.xml')/entity-list/*"/>
        <xsl:variable name="activeCardsNotCredit"   select="document('stored-p2p-autotransfer-cards.xml')/entity-list/*"/>

        <input type="hidden" name="exactAmount"     id="exactAmount"        value="{exactAmount}"/>
        <input type="hidden" name="receiverType"    id="receiverType"       value="{receiverType}"/>
        <input type="hidden" name="receiverSubType" id="receiverSubType"    value="{receiverSubType}"/>

        <xsl:variable name="receiverType"       select="receiverType"/>
        <xsl:variable name="receiverSubType"    select="receiverSubType"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId" select="'receiverTypeControlRow'"/>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowValue">

                <div id='receiverTypeControl'>
                    <div id="severalReceiverTypeButton" class="inner firstButton">
                        <xsl:attribute name="onclick">changeReceiverType('several');</xsl:attribute>
                        <xsl:if test="$receiverType='several'">
                            <xsl:attribute name="class">inner firstButton activeButton</xsl:attribute>
                        </xsl:if>
                        На свою карту
                    </div>
                    <div id="phReceiverTypeButton" class="inner lastButton">
                        <xsl:attribute name="onclick">changeReceiverType('ph');</xsl:attribute>
                        <xsl:if test="$receiverType='ph'">
                            <xsl:attribute name="class">inner lastButton activeButton</xsl:attribute>
                        </xsl:if>
                        Клиенту сбербанка
                    </div>
                </div>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId" select="'receiverSubTypeControlRow'"/>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowValue">

                <div id='receiverSubTypeControl'>
                    <div id="ourCardReceiverSubTypeButton" class="inner firstButton">
                        <xsl:attribute name="onclick">changeReceiverType('ph', 'ourCard');</xsl:attribute>
                        <xsl:if test="$receiverSubType='ourCard'">
                            <xsl:attribute name="class">inner firstButton activeButton</xsl:attribute>
                        </xsl:if>
                        Карта
                    </div>
                    <div id="ourPhoneReceiverSubTypeButton" class="inner lastButton">
                        <xsl:attribute name="onclick">changeReceiverType('ph', 'ourPhone');</xsl:attribute>
                        <xsl:if test="$receiverSubType='ourPhone'">
                            <xsl:attribute name="class">inner lastButton activeButton</xsl:attribute>
                        </xsl:if>
                        Карта по номеру моб. тел.
                    </div>
                </div>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="id">fromResource</xsl:with-param>
			<xsl:with-param name="rowName">Счет списания:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:if test="$personAvailable">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                        <xsl:with-param name="linkId" select="fromResource"/>
                        <xsl:with-param name="activeCards" select="$activeCards"/>
                        <xsl:with-param name="activeCardsNotCredit" select="$activeCardsNotCredit"/>
                        <xsl:with-param name="resourceView">списания</xsl:with-param>
                    </xsl:call-template>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="fromAccountSelect" name="fromAccountSelect" disabled="disabled">
					    <option selected="selected">Счет клиента</option>
				    </select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="lineId">toResourceRow</xsl:with-param>
			<xsl:with-param name="id">toResource</xsl:with-param>
			<xsl:with-param name="rowName">Счет зачисления:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:if test="$personAvailable">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name">toResource</xsl:with-param>
                        <xsl:with-param name="accountNumber" select="toAccountSelect"/>
                        <xsl:with-param name="linkId" select="toResource"/>
                        <xsl:with-param name="activeCards" select="$activeCards"/>
                        <xsl:with-param name="activeCardsNotCredit" select="$activeCardsNotCredit"/>
                        <xsl:with-param name="resourceView">зачисления</xsl:with-param>
                    </xsl:call-template>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="toResource" name="toResource" disabled="disabled"><option selected="selected">Счет клиента</option></select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id"     select="'externalCardNumber'"/>
            <xsl:with-param name="lineId" select="'externalCardNumberRow'"/>
            <xsl:with-param name="description"><cut />Введите номер карты другого клиента Сбербанка, на которую Вы хотите перевести деньги. Номер карты может содержать 15, 16, 18 или 19 цифр.</xsl:with-param>
            <xsl:with-param name="rowName">Номер карты получателя</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input name="externalCardCurrency" type="hidden" value="{buyAmountCurrency}"/>
                <input id="externalCardNumber" name="externalCardNumber" type="text" value="{externalCardNumber}" size="20" maxlength="19" onblur="checkCardNumber('ourCard');"/>
                <div style="display: none;" id="currencyErrorDiv"></div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id"     select="'externalPhoneNumber'"/>
            <xsl:with-param name="lineId" select="'externalPhoneNumberRow'"/>
            <xsl:with-param name="description">Номер телефона абонента оператора сотовых услуг<cut/>Введите номер телефона - 10 цифр. Например, (906) 555-22-33</xsl:with-param>
            <xsl:with-param name="rowName">Номер телефона</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>+7 </b>
                <input id="externalPhoneNumber" name="externalPhoneNumber" type="text" value="{externalPhoneNumber}" size="20" maxlength="10" onblur="checkCardNumber('ourPhone');" class="phone-template-p2p"/>
                <xsl:if test="pu:impliesService('ContactSyncService') and pu:impliesService('ChooseContactService')">
                    &nbsp;<span class="text-green" style="text-decoration: underline; cursor: pointer;" onclick="openContacts();">выбрать из контактной книги</span>
                    <script type="text/javascript">
                        function openContacts()
                        {
                            var win  =  window.open(document.webRoot + "/private/contacts/list.do", 'Contacts', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=800, height=500");
                            win.moveTo(screen.width / 2 - 400, screen.height / 2 - 250);
                            return false;
                        }

                        function setPhoneNumber(phone)
                        {
                            document.getElementById("externalPhoneNumber").value = phone;
                            document.getElementById("externalPhoneNumber").focus();
                            checkNumber();
                        }
                    </script>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>


        <script type="text/javascript">

            var currencies      = new Array();
            var cardAccounts    = new Array();

            var countNotEmptyAcc = 0;
            var countAcc = 0;
            var indexNotEmptyAcc = 0;

            function initPaymentData()
            {
                var USING_STORED_CARDS_RESOURCE_MESSAGE    = 'Информация по Вашим картам может быть неактуальной.';

                <xsl:for-each select="$activeCards">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    cardAccounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';

                    <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                        addMessage(USING_STORED_CARDS_RESOURCE_MESSAGE);
                    </xsl:if>
                </xsl:for-each>

                <xsl:for-each select="$activeCardsNotCredit">
                    countAcc++;
                    <xsl:if test="field[@name='amountDecimal'] > 0 ">
                        countNotEmptyAcc ++;
                        indexNotEmptyAcc = countAcc;
                    </xsl:if>
                    <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                        addMessage(USING_STORED_CARDS_RESOURCE_MESSAGE);
                    </xsl:if>
                </xsl:for-each>

                selectDefaultFromResource(countNotEmptyAcc, indexNotEmptyAcc);
                changeReceiverType('<xsl:value-of select="$receiverType"/>', '<xsl:value-of select="$receiverSubType"/>');
            }

            function changeReceiverType(type, subType)
            {
                type = type || 'several';
                subType = subType || (type == 'several' ? 'severalCard' : 'ourCard');

                if ('several' == type)
                {
                    hideOrShow(ensureElement('toResourceRow'),   false);

                    hideOrShow(ensureElement('receiverSubTypeControlRow'),  true);
                    hideOrShow(ensureElement('externalCardNumberRow'),      true);
                    hideOrShow(ensureElement('externalPhoneNumberRow'),     true);
                    hideOrShow(ensureElement('messageToReceiverRow'),       true);
                }

                if ('ph' == type)
                {
                    hideOrShow(ensureElement('toResourceRow'),   true);

                    hideOrShow(ensureElement('receiverSubTypeControlRow'),  false);
                    hideOrShow(ensureElement('externalCardNumberRow'),      subType != 'ourCard');
                    hideOrShow(ensureElement('externalPhoneNumberRow'),     subType != 'ourPhone');
                    hideOrShow(ensureElement('messageToReceiverRow'),       false);

                    $('#receiverSubTypeControl').find('div').removeClass('activeButton');
                    $('#' + subType + 'ReceiverSubTypeButton').addClass('activeButton');
                }

                $('#receiverType').val(type);
                $('#receiverSubType').val(subType);

                $('#receiverTypeControl').find('div').removeClass('activeButton');
                $('#' + type + 'ReceiverTypeButton').addClass('activeButton');
            }

            var cardNumber    = '';
            var oldCardNumber = '';
            var oldCurrencyMessage = [];

            function checkCardNumber(subType)
            {
                if ('ourCard' == subType)
                {
                    cardNumber = getElementValue("externalCardNumber").replace(/ /g,'');
                    if (new CardNumberValidator(cardNumber, oldCardNumber).validate())
                    {
                        checkCardCurrency("field(type)=card&amp;field(cardNumber)=" + cardNumber)
                    }

                    oldCardNumber = cardNumber;
                    $('#externalCardNumber').val(cardNumber);
                }
            }

            function CardNumberValidator(number, oldNumber)
            {
                this.number = number;
                this.oldNumber = oldNumber;
                this.validate = function()
                {
                    if (number == oldNumber)
                    {
                        return false;
                    }

                    if (!(cardNumber.length == 15 || cardNumber.length == 19))
                    {
                        return false;
                    }

                    return true;
                }
            }

            function checkCardCurrency(params)
            {
                var url = document.webRoot + "/private/cards/currency/code.do";
                ajaxQuery(params, url, updateReceiverCardCurrency, null, true);

                function updateReceiverCardCurrency(currencyData)
                {
                    if (trim(currencyData) == '')
                    {
                        return false;
                    }
                    var htmlData = trim(currencyData).replace(/^&nbsp;+/, "");
                    $("#currencyErrorDiv").html(htmlData);
                    for (var i=0; i &lt; oldCurrencyMessage.length; i++)
                    {
                        removeError(oldCurrencyMessage[i]);
                    }
                    for (var i=0; i &lt; currencyErrorMessageAr.length; i++)
                    {
                        addError(currencyErrorMessageAr[i]);
                    }
                    oldCurrencyMessage = currencyErrorMessageAr;

                    //обработка данных о валюте
                    var currencyVal = $("#currency").val();
                    var currency = getElement("externalCardCurrency");
                    if (currencyErrorMessageAr.length > 0)   //пришла ошибка с описанием
                    {
                        ensureElement("isErrorCurrency").value = "true";
                        currency.value = "";
                    }
                    else
                    {
                        ensureElement("isErrorCurrency").value = "false";
                        currency.value = currencyVal;
                    }
                    return true;
                }
            }

            $(document).ready(initPaymentData);

        </script>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Параметры автоплатежа (регулярной операции)</b></xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Переводить</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" id="longOfferEventType" name="longOfferEventType"  value="{longOfferEventType}"/>

                <select id="longOfferEventSelect" name="longOfferEventSelect"
                        onchange="javascript:showReport();" onkeyup="javascript:showReport();"
                        />
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата ближайшего перевода</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="longOfferStartDate">
                    <xsl:choose>
                        <xsl:when test="contains(longOfferStartDate, '-')">
                            <xsl:copy-of select="concat(substring(longOfferStartDate, 9, 2), '.', substring(longOfferStartDate, 6, 2), '.', substring(longOfferStartDate, 1, 4))"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:copy-of select="longOfferStartDate"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <input name="longOfferStartDate" id="longOfferStartDate" class="dot-date-pick" size="10" value="{$longOfferStartDate}"
                       onchange="javascript:showReport();" onkeyup="javascript:showReport();"
                        />
           </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowValue">
                Перевод будет осуществляться
                <b><div id="report"></div></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
            <xsl:with-param name="id">sellAmount</xsl:with-param>
            <xsl:with-param name="description">Введите сумму, которую необходимо перевести</xsl:with-param>
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="sellAmount" name="sellAmount" type="text" value="{sellAmount}" size="24" class="moneyField"/>&nbsp;
                <span id="sellAmountCurrency">руб.</span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">autoSubNameRow</xsl:with-param>
            <xsl:with-param name="rowName">Наименование</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="autoSubName" select="autoSubName"/>
                <span id="autoSubNameView" class="size16 bold float" style="padding-right: 10px">
                    <xsl:if test="string-length($autoSubName) > 0">
                        <xsl:value-of select="$autoSubName"/>
                    </xsl:if>
                </span>
                <span id="productNameText" name="productNameText" class="productTitleMargin word-wrap payViewPadd productTitleDetailInfoText" style="margin: 10px">
                    <a class="productTitleDetailInfoEditBullet" onclick="showEditSubscriptionName();"></a>
                </span>
                <span id="productNameEdit" name="productNameEdit" class="productTitleMargin">
                    <input type="text" id="autoSubName" name="autoSubName" value="{$autoSubName}" size="30" maxlength="50"/>&nbsp;
                    <div class="errorDiv clear" style="display:none;"></div>
                </span>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">messageToReceiver</xsl:with-param>
            <xsl:with-param name="lineId">messageToReceiverRow</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="description">Введите сообщение для получателя средств, которое будет направлено ему на мобильный телефон.</xsl:with-param>
            <xsl:with-param name="rowName">Сообщение получателю</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="messageToReceiver" name="messageToReceiver" type="text" value="{messageToReceiver}" size="45" maxlength="40"/>
            </xsl:with-param>
        </xsl:call-template>

        <script type="text/javascript">
            <![CDATA[

                function counted(eventType)
                {
                    if (isEmpty(eventType))
                    {
                        eventType = 'ONCE_IN_WEEK';
                    }

                    var longOfferEventSelect = getElement("longOfferEventSelect");
                    var longOfferEventLength = 0;
                    longOfferEventSelect.options.length = longOfferEventLength;

                    longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_WEEK"],       "ONCE_IN_WEEK");
                    longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_MONTH"],      "ONCE_IN_MONTH");
                    longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_QUARTER"],    "ONCE_IN_QUARTER");
                    longOfferEventSelect.options[longOfferEventLength++] = new Option(eventTypes["ONCE_IN_YEAR"],       "ONCE_IN_YEAR");

                    for (var i=0; i < longOfferEventSelect.length; i++)
                    {
                        if (longOfferEventSelect[i].value == eventType)
                        {
                            longOfferEventSelect[i].selected = true;
                        }
                    }

                    $('#longOfferEventType').val(eventType);
                }

                function showReport()
                {
                    $('#report').html(generateReport());
                    $('#longOfferEventType').val($("#longOfferEventSelect").val());
                }

                function generateReport()
                {
                    var eventType = $("#longOfferEventSelect").val();
                    var startDate = Str2Date($("#longOfferStartDate").val());

                    switch(eventType)
                    {
                        case 'ONCE_IN_WEEK':    return 'раз в неделю, по ' + daysOfWeekDesc[(startDate.getDay()+6)%7];
                        case 'ONCE_IN_MONTH':   return 'раз в месяц, ' + startDate.getDate() + '-го числа';
                        case 'ONCE_IN_QUARTER': return 'раз в квартал, ' + startDate.getDate() + '-го числа ' + (startDate.getMonth()%3 + 1) + '-го месяца квартала';
                        case 'ONCE_IN_YEAR':    return 'раз в год, ' + startDate.getDate() + '-го ' + monthsOfYearDesc[startDate.getMonth()];
                    }
                }

                function showEditSubscriptionName()
                {
                    $("#autoSubNameView").hide();
                    $("#productNameText").hide();
                    $("#productNameEdit").show();
                    $("#longOfferName")[0].selectionStart = $("#longOfferName")[0].selectionEnd = $("#longOfferName").val().length;
                }

                function hideEditSubscriptionName()
                {
                    $("#productNameText").show();
                    $("#productNameEdit").hide();
                }

                function showAutoTransferName()
                {
                    var name = generateAutoTransferName();
                    $('#autoSubNameView').html(name);
                    $('#autoSubName').val(name);
                }
            ]]>

            function generateAutoTransferName()
            {
                <xsl:if test="'ph' = receiverType">
                    return "Перевод для " + '<xsl:value-of select="receiverName"/>';
                </xsl:if>
                <xsl:if test="'several' = receiverType">
                    return "Перевод на " + '<xsl:value-of select="toAccountName"/>';
                </xsl:if>
            }

            var eventTypes = new Array();
            var monthsOfYearDesc = ["января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"];
            var daysOfWeekDesc   = ["понедельникам", "вторникам", "средам", "четвергам", "пятницам", "субботам", "воскресеньям"];


            function initSubscriptionData()
            {
                <xsl:for-each select="$eventTypes">
                    eventTypes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./text()"/>';
                </xsl:for-each>

                setCurrentDateToInput("longOfferStartDate");
                counted("<xsl:value-of select="longOfferEventType"/>");
                hideEditSubscriptionName();
                showReport();
                <xsl:if test="string-length(autoSubName) = 0">
                    showAutoTransferName();
                </xsl:if>
            }

            initSubscriptionData();

        </script>

    </xsl:template>

    <xsl:template match="/form-data" mode="PhizIC-edit">
        <script type="text/javascript" src="{$resourceRoot}/scripts/PaymentReceiver.js"/>

        <xsl:variable name="activeCards"            select="document('stored-active-or-arrested-cards.xml')/entity-list/*"/>
        <xsl:variable name="activeCardsNotCredit"   select="document('stored-active-not-credit-cards.xml')/entity-list/*"/>
        <xsl:variable name="availableAddressBook">
            <xsl:value-of select="pu:impliesService('AddressBook')"/>
        </xsl:variable>

        <input type="hidden" name="exactAmount"             id="exactAmount"            value="{exactAmount}"/>
        <input type="hidden" name="receiverType"            id="receiverType"           value="{receiverType}"/>
        <input type="hidden" name="receiverSubType"         id="receiverSubType"        value="{receiverSubType}"/>
        <input type="hidden" name="contactName"             id="contactName"            value="{contactName}"/>
        <input type="hidden" name="contactPhone"            id="contactPhone"           value="{contactPhone}"/>
        <input type="hidden" name="contactCard"             id="contactCard"            value="{contactCard}"/>
        <input type="hidden" name="externalContactId"       id="externalContactId"      value="{externalContactId}"/>
        <input type="hidden" name="externalCardCurrency"    id="externalCardCurrency"   value="{sellAmountCurrency}"/>
        <input type="hidden" name="isErrorCurrency"         id="isErrorCurrency"        value="false"/>
        <input type="hidden" name="avatarPath"              id="avatarPath"             value="{avatarPath}"/>
        <input type="hidden" name="contactSberbank"         id="contactSberbank"        value="{contactSberbank}"/>

        <table class="paymentHeader">
            <tbody>
                <tr>
                    <td>
                        На этой странице можно отредактировать настройки ранее подключенного автоплатежа. Внесите необходимые изменения в соответствующие поля.
                    </td>
                </tr>
            </tbody>
        </table>
        <xsl:variable name="receiverType"       select="receiverType"/>
        <xsl:variable name="receiverSubType"    select="receiverSubType"/>

        <!--выбор получателя платежа-->
        <xsl:call-template name="select-receiver-template">
            <xsl:with-param name="severalReceiverOnClick">changeReceiverType('several');</xsl:with-param>
            <xsl:with-param name="phReceiverOnClick">changeReceiverType('ph');</xsl:with-param>
        </xsl:call-template>

        <!--поиск получателя платежа-->
        <xsl:call-template name="select-contact-receiver-template">
            <xsl:with-param name="availableAddressBook" select="$availableAddressBook"/>
        </xsl:call-template>

        <div id="writedown">
        <xsl:call-template name="p2p-StandartRow">
			<xsl:with-param name="id"       select="'fromResource'"/>
			<xsl:with-param name="rowName"  select="'Карта списания'"/>
            <xsl:with-param name="spacerClass" select="'spacer'"/>
            <xsl:with-param name="spacerHint">&nbsp;</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:if test="$personAvailable">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name"                 select="'fromResource'"/>
                        <xsl:with-param name="accountNumber"        select="fromAccountSelect"/>
                        <xsl:with-param name="linkId"               select="fromResource"/>
                        <xsl:with-param name="activeCards"          select="$activeCards"/>
                        <xsl:with-param name="activeCardsNotCredit" select="$activeCardsNotCredit"/>
                        <xsl:with-param name="resourceView"         select="'списания'"/>
                        <xsl:with-param name="application-mode"     select="$app"/>
                    </xsl:call-template>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="fromAccountSelect" name="fromAccountSelect" disabled="disabled">
					    <option selected="selected">Карта клиента</option>
				    </select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

        </div>
        <xsl:call-template name="p2p-StandartRow">
			<xsl:with-param name="lineId"   select="'toResourceRow'"/>
			<xsl:with-param name="id"       select="'toResource'"/>
			<xsl:with-param name="rowName"  select="'Карта зачисления'"/>
			<xsl:with-param name="rowValue">
				<xsl:if test="$personAvailable">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name"                 select="'toResource'"/>
                        <xsl:with-param name="accountNumber"        select="toAccountSelect"/>
                        <xsl:with-param name="linkId"               select="toResource"/>
                        <xsl:with-param name="activeCards"          select="$activeCards"/>
                        <xsl:with-param name="activeCardsNotCredit" select="$activeCardsNotCredit"/>
                        <xsl:with-param name="resourceView"         select="'зачисления'"/>
                        <xsl:with-param name="application-mode"     select="$app"/>
                    </xsl:call-template>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="toResource" name="toResource" disabled="disabled">
                        <option selected="selected">Карта клиента</option>
                    </select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

        <script type="text/javascript">

            var currencies      = new Array();
            var cardAccounts    = new Array();

            var countNotEmptyAcc = 0;
            var countAcc = 0;
            var indexNotEmptyAcc = 0;

            function init()
            {
                var USING_STORED_CARDS_RESOURCE_MESSAGE    = 'Информация по Вашим картам может быть неактуальной.';

                payInput.additErrorBlock = "errorDivBase";
                payInput.ACTIVE_CLASS_NAME = "";
                payInput.ERROR_CLASS_NAME = "form-row-addition form-row-new error-row-new";

                <xsl:for-each select="$activeCards">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    cardAccounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';

                    <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                        addMessage(USING_STORED_CARDS_RESOURCE_MESSAGE);
                    </xsl:if>
                </xsl:for-each>

                <xsl:for-each select="$activeCardsNotCredit">
                    countAcc++;
                    <xsl:if test="field[@name='amountDecimal'] > 0 ">
                        countNotEmptyAcc ++;
                        indexNotEmptyAcc = countAcc;
                    </xsl:if>
                    <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                        addMessage(USING_STORED_CARDS_RESOURCE_MESSAGE);
                    </xsl:if>
                </xsl:for-each>

                selectDefaultFromResource(countNotEmptyAcc, indexNotEmptyAcc);

                var receiverSubType = '<xsl:value-of select="$receiverSubType"/>' || "ourCard";
                var initData = {
                    "contactName": '<xsl:value-of select="contactName"/>',
                    <xsl:if test="externalPhoneNumber != ''">
                        <xsl:choose>
                            <xsl:when test="receiverSubType = 'ourPhone'">
                                "externalPhoneNumber": '<xsl:value-of select="externalPhoneNumber"/>',
                                "maskedPhoneNumber": '<xsl:value-of select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/>',
                            </xsl:when>
                            <xsl:when test="receiverSubType = 'ourContact' or receiverSubType = 'ourContactToOtherCard'">
                                "externalPhoneNumber": '<xsl:value-of select="externalPhoneNumber"/>',
                                "maskedPhoneNumber": '<xsl:value-of select="externalPhoneNumber"/>',
                            </xsl:when>
                            <xsl:when test="receiverSubType = 'otherCard'">
                                "externalPhoneNumber": '<xsl:value-of select="externalPhoneNumber"/>',
                                "maskedPhoneNumber": '<xsl:value-of select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/>',
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:variable name="maskPhone" select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/>
                                "externalPhoneNumber": '<xsl:value-of select="$maskPhone"/>',
                                "maskedPhoneNumber": '<xsl:value-of select="$maskPhone"/>',
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:if>
                    <xsl:variable name="cardNum">
                        <xsl:choose>
                            <xsl:when test="externalCardNumber != ''"><xsl:value-of select="externalCardNumber"/></xsl:when>
                            <xsl:otherwise><xsl:value-of select="receiverAccount"/></xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>
                    <xsl:choose>
                        <xsl:when test="receiverSubType = 'ourCard'">
                            "externalCardNumber": "<xsl:value-of select="$cardNum"/>",
                        </xsl:when>
                        <xsl:otherwise>
                            "externalCardNumber": "<xsl:value-of select="mask:getCutCardNumber($cardNum)"/>",
                        </xsl:otherwise>
                    </xsl:choose>
                    "contactSberbank": '<xsl:value-of select="contactSberbank"/>',
                    "receiverName": '<xsl:value-of select="receiverName"/>',
                    "avatarPath": '<xsl:value-of select="avatarPath"/>'
                };

                paymentReceiver.init(receiverSubType, initData, 'true', '<xsl:value-of select="pru:getStaticImageUri()"/>', '<xsl:value-of select="$resourceRoot"/>');
                changeReceiverType('<xsl:value-of select="$receiverType"/>', receiverSubType);
            }

            function changeReceiverType(type, subType)
            {
                type = type || 'several';
                subType = subType || (type == 'several' ? 'severalCard' : 'ourCard');

                hideOrShow(ensureElement('toResourceRow'),          'ph' == type);
                hideOrShow(ensureElement("receiverIdentifierRow"),  'several' == type)
                hideOrShow(ensureElement("messageToReceiverRow"),   'several' == type)

                if(ensureElement('toResourceRow'),'ph' == type) {
                    $('#writedown .spacerHint').hide();
                    $('#writedown .spacer').hide();
                }
                if(ensureElement("receiverIdentifierRow"), 'several' == type) {
                    $('#writedown .spacerHint').show();
                    $('#writedown .spacer').show();
                }

                $('#receiverType').val(type);
                $('#receiverSubType').val(subType);

                $('#receiverTypeControl').find('div').removeClass('activeButton');
                $('#' + type + 'ReceiverTypeButton').addClass('activeButton');
                paymentReceiver.changePaymentType(subType);
            }

            function hideError()
            {
                $("#fromResourceRow .paymentValue .errorDiv").hide();
                var fromResourceRow = document.getElementById("fromResourceRow");
                fromResourceRow.error = false;
                fromResourceRow.className = "form-row form-row-new";
            }

            function updateReceiverSubType(newType)
            {
                $('#receiverSubType').val(newType);
            }

            function refreshForm()
            {

            }

            $(document).ready(init);

        </script>

        <xsl:call-template name="p2p-StandartRow">
            <xsl:with-param name="rowName">Переводить</xsl:with-param>
            <xsl:with-param name="readField">allWidth</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="longOfferEventType" select="longOfferEventType"/>
                <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity"/>

                <input type="hidden" id="longOfferEventType" name="longOfferEventType"  value="{longOfferEventType}"/>

                <select id="longOfferEventSelect" name="longOfferEventSelect" class="selectSbtM"
                        onchange="javascript:showReport();" onkeyup="javascript:showReport();"
                        >
                    <xsl:for-each select="$eventTypes">
                        <xsl:variable name="key" select="./@key"/>
                        <xsl:if test="$key = 'ONCE_IN_WEEK' or $key = 'ONCE_IN_MONTH' or $key = 'ONCE_IN_QUARTER' or $key = 'ONCE_IN_YEAR'">
                            <option>
                                <xsl:attribute name="value">
                                    <xsl:value-of select="$key"/>
                                </xsl:attribute>
                                <xsl:if test="$key = $longOfferEventType">
                                    <xsl:attribute name="selected"/>
                                </xsl:if>
                                <xsl:value-of select="./text()"/>
                            </option>
                        </xsl:if>
                    </xsl:for-each>
                </select>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="p2p-StandartRow">
            <xsl:with-param name="rowName">Дата ближайшего перевода</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="longOfferStartDate">
                    <xsl:choose>
                        <xsl:when test="contains(longOfferStartDate, '-')">
                            <xsl:copy-of select="concat(substring(longOfferStartDate, 9, 2), '.', substring(longOfferStartDate, 6, 2), '.', substring(longOfferStartDate, 1, 4))"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:copy-of select="longOfferStartDate"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <input name="longOfferStartDate" id="longOfferStartDate" class="dot-date-pick" size="10" value="{$longOfferStartDate}"
                       onchange="javascript:showReport();" onkeyup="javascript:showReport();"
                        />
           </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="p2p-StandartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowValue">
                Перевод будет осуществляться
                <b><div id="report"></div></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="p2p-StandartRow">
            <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
            <xsl:with-param name="id">sellAmount</xsl:with-param>
            <xsl:with-param name="description">Введите сумму, которую необходимо перевести</xsl:with-param>
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="sellAmount" name="sellAmount" type="text" value="{sellAmount}" size="24" class="moneyField"/>&nbsp;
                <span id="sellAmountCurrency">руб.</span>
            </xsl:with-param>
        </xsl:call-template>

        <!--наименования автоперевода-->
        <xsl:call-template name="edit-autotransfer-name-template"/>

        <!--сообщение получателю платежа-->
        <xsl:call-template name="edit-message-to-receiver-template"/>

        <script type="text/javascript">
            <![CDATA[

                function showReport()
                {
                    $('#report').html(generateReport());
                    $('#longOfferEventType').val($("#longOfferEventSelect").val());
                }

                function generateReport()
                {
                    var eventType = $("#longOfferEventSelect").val();
                    var startDate = Str2Date($("#longOfferStartDate").val());

                    switch(eventType)
                    {
                        case 'ONCE_IN_WEEK':    return 'раз в неделю, по ' + daysOfWeekDesc[(startDate.getDay()+6)%7];
                        case 'ONCE_IN_MONTH':   return 'раз в месяц, ' + startDate.getDate() + '-го числа';
                        case 'ONCE_IN_QUARTER': return 'раз в квартал, ' + startDate.getDate() + '-го числа ' + (startDate.getMonth()%3 + 1) + '-го месяца квартала';
                        case 'ONCE_IN_YEAR':    return 'раз в год, ' + startDate.getDate() + '-го ' + monthsOfYearDesc[startDate.getMonth()];
                    }
                }

                function showEditSubscriptionName()
                {
                    $("#autoSubNameView").hide();
                    $("#productNameText").hide();
                    $("#productNameEdit").show();
                    $("#longOfferName")[0].selectionStart = $("#longOfferName")[0].selectionEnd = $("#longOfferName").val().length;
                }

                function hideEditSubscriptionName()
                {
                    $("#productNameText").show();
                    $("#productNameEdit").hide();
                }

                function showAutoTransferName()
                {
                    var name = generateAutoTransferName();
                    $('#autoSubNameView').html(name);
                    $('#autoSubName').val(name);
                }
            ]]>

            function generateAutoTransferName()
            {
                <xsl:if test="'ph' = receiverType">
                    return "Перевод для " + '<xsl:value-of select="receiverName"/>';
                </xsl:if>
                <xsl:if test="'several' = receiverType">
                    return "Перевод на " + '<xsl:value-of select="toAccountName"/>';
                </xsl:if>
            }

            var monthsOfYearDesc = ["января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"];
            var daysOfWeekDesc   = ["понедельникам", "вторникам", "средам", "четвергам", "пятницам", "субботам", "воскресеньям"];

            function initSubscription()
            {
                setCurrentDateToInput("longOfferStartDate");
                hideEditSubscriptionName();
                showReport();
                $('#smsAvatarContainer').html($('#avatarIconEl').clone());
                customPlaceholder.init();
                <xsl:if test="string-length(autoSubName) = 0">
                    showAutoTransferName();
                </xsl:if>
            }

            initSubscription();

        </script>

    </xsl:template>

    <xsl:template match="/form-data" mode="PhizIA-view">

        <xsl:variable name="receiverType"       select="receiverType"/>
        <xsl:variable name="receiverSubType"    select="receiverSubType"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="lineId" select="'receiverTypeControlRow'"/>
            <xsl:with-param name="rowValue">

                <div id='receiverTypeControl'>
                    <div id="severalReceiverTypeButton" class="inner firstButton">
                        <xsl:if test="$receiverType='several'">
                            <xsl:attribute name="class">inner firstButton activeButton</xsl:attribute>
                        </xsl:if>
                        На свою карту
                    </div>
                    <div id="phReceiverTypeButton" class="inner lastButton">
                        <xsl:if test="$receiverType='ph'">
                            <xsl:attribute name="class">inner lastButton activeButton</xsl:attribute>
                        </xsl:if>
                        Клиенту сбербанка
                    </div>
                </div>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:if test="$receiverType = 'several'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Карта зачисления</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <xsl:value-of select="mask:getCutCardNumber(receiverAccount)"/>
                        &nbsp;[<xsl:value-of select="toAccountName"/>]
                        &nbsp;<xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$receiverType = 'ph'">
            <xsl:if test="$receiverSubType = 'ourCard'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Номер карты получателя</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of  select="mask:getCutCardNumber(receiverAccount)"/></b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:if test="$receiverSubType = 'ourPhone'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Номер телефона</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b>+7 <xsl:value-of  select="externalPhoneNumber"/></b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">ФИО</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="ph:getFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)" disable-output-escaping="no"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет списания</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
                <xsl:if test="string-length($fromAccountSelect) > 0">
                    <b>
                        <xsl:choose>
                            <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                            </xsl:when>
                        </xsl:choose>
                        &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                        <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                    </b>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowValue">
                Перевод будет осуществляться
                <b><div id="report"></div></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
            <xsl:with-param name="rowName">Сумма</xsl:with-param>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowValue">
                <xsl:if test="string-length(sellAmount)>0">
                    <span class="summ">
                        <xsl:value-of select="format-number(translate(sellAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                        <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                    </span>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Комиссия</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="string-length(commission) > 0">
                        <span name="commission">
                            <xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mf:getCurrencySign(commissionCurrency)"/>
                        </span>
                    </xsl:when>
                    <xsl:otherwise>
                        <span name="commission">
                            0-1% от суммы перевода. Точная сумма комиссии будет рассчитана и указана в SMS перед ближайшим переводом.
                        </span>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">autoSubNameRow</xsl:with-param>
            <xsl:with-param name="rowName">Название</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="autoSubName"/>&nbsp;
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$receiverType = 'ph'">
            <xsl:if test="string-length(messageToReceiver) > 0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">messageToReceiverRow</xsl:with-param>
                    <xsl:with-param name="rowName">Сообщение получателю</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="messageToReceiver"/></b>&nbsp;<img src="{concat($resourceRoot, '/images/ERMB_sms.png')}" alt="sms"/>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:if test="(state = 'EXECUTED' or state = 'DISPATCHED' or state = 'UNKNOW')">
                    <xsl:variable name="existMessageToReceiverStatus" select="string-length(messageToReceiverStatus) > 0"/>
                    <xsl:variable name="sentMessageToReceiverStatus" select="$existMessageToReceiverStatus and messageToReceiverStatus = 'sent'"/>
                    <xsl:variable name="notSentMessageToReceiverStatus" select="$existMessageToReceiverStatus and messageToReceiverStatus = 'not_sent'"/>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">messageToReceiverStatusRow</xsl:with-param>
                        <xsl:with-param name="rowName">Статус SMS-сообщения</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:choose>
                                <xsl:when test="$sentMessageToReceiverStatus">
                                    <div id="messageToReceiverStatus">
                                        <span class="messageToReceiverStatus"
                                              onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription');"
                                              onmouseout="hideLayer('messageToReceiverStatusDescription');">
                                            Сообщение отправлено
                                        </span>
                                    </div>
                                </xsl:when>
                                <xsl:when test="$notSentMessageToReceiverStatus">
                                    <div id="messageToReceiverStatus">
                                        <span class="messageToReceiverStatus"
                                              onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription');"
                                              onmouseout="hideLayer('messageToReceiverStatusDescription');">
                                            Сообщение не отправлено
                                        </span>
                                    </div>
                                </xsl:when>
                                <xsl:when test="$existMessageToReceiverStatus">
                                    <xsl:value-of select="messageToReceiverStatus"/>
                                </xsl:when>
                                <xsl:otherwise>сообщение будет отправлено</xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                    <div id="messageToReceiverStatusDescription"
                         onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription','default');"
                         onmouseout="hideLayer('messageToReceiverStatusDescription');" class="layerFon stateDescription">
                        <div class="floatMessageHeader"></div>
                        <div class="layerFonBlock">
                            <xsl:choose>
                                <xsl:when test="$sentMessageToReceiverStatus">
                                    Сообщение успешно отправлено получателю средств.
                                </xsl:when>
                                <xsl:when test="$notSentMessageToReceiverStatus">
                                    Сообщение не удалось отправить получателю средств.
                                </xsl:when>
                            </xsl:choose>
                        </div>
                    </div>
                </xsl:if>
            </xsl:if>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">
                Статус платежа
            </xsl:with-param>
            <xsl:with-param name="rowValue">
                <div id="state">
                    <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
                        <xsl:call-template name="employeeState2text">
                            <xsl:with-param name="code">
                                <xsl:value-of select="state"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </span>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="longOfferStartDate">
            <xsl:choose>
                <xsl:when test="contains(longOfferStartDate, '-')">
                    <xsl:copy-of select="concat(substring(longOfferStartDate, 9, 2), '.', substring(longOfferStartDate, 6, 2), '.', substring(longOfferStartDate, 1, 4))"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:copy-of select="longOfferStartDate"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <script type="text/javascript">

            var monthsOfYearDesc = ["января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"];
            var daysOfWeekDesc   = ["понедельникам", "вторникам", "средам", "четвергам", "пятницам", "субботам", "воскресеньям"];

            function showReport()
            {
                $('#report').html(generateReport());
            }

            showReport();

            function generateReport()
            {
                var eventType = '<xsl:value-of select="longOfferEventType"/>';
                var startDate = Str2Date('<xsl:value-of select="$longOfferStartDate"/>');

                switch(eventType)
                {
                    case 'ONCE_IN_WEEK':    return 'раз в неделю, по ' + daysOfWeekDesc[(startDate.getDay()+6)%7];
                    case 'ONCE_IN_MONTH':   return 'раз в месяц, ' + startDate.getDate() + '-го числа';
                    case 'ONCE_IN_QUARTER': return 'раз в квартал, ' + startDate.getDate() + '-го числа ' + (startDate.getMonth()%3 + 1) + '-го месяца квартала';
                    case 'ONCE_IN_YEAR':    return 'раз в год, ' + startDate.getDate() + '-го ' + monthsOfYearDesc[startDate.getMonth()];
                }
            }

        </script>
    </xsl:template>

    <xsl:template match="/form-data" mode="PhizIC-view">
        <div class="confirmData">
            <xsl:if test="(state = 'DISPATCHED' or state = 'EXECUTED' or state = 'DELAYED_DISPATCH' or state = 'OFFLINE_DELAYED' or state = 'WAIT_CONFIRM' or state = 'REFUSED' or state = 'RECALLED') and $app != 'PhizIA'">
                <xsl:variable name="osb" select="dep:getOsb(departmentId)"/>
                <xsl:choose>
                    <xsl:when test="$osb != 'null'">
                        <xsl:call-template name="stateStamp">
                            <xsl:with-param name="state" select="state"/>
                            <xsl:with-param name="stateData">
                                <xsl:call-template name="clientState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                            <xsl:with-param name="documentDate"  select="documentDate"/>
                            <xsl:with-param name="documentNumber"  select="documentNumber"/>
                            <xsl:with-param name="bankName" select="dep:getNameFromOsb($osb)"/>
                            <xsl:with-param name="bic"  select="dep:getNameFromOsb($osb)"/>
                            <xsl:with-param name="corrByBIC" select="dep:getDefaultBankBic()"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:variable name="bic" select="dep:getDefaultBankBic()"/>
                        <xsl:call-template name="stateStamp">
                            <xsl:with-param name="state" select="state"/>
                            <xsl:with-param name="stateData">
                                <xsl:call-template name="clientState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                            <xsl:with-param name="documentDate"  select="documentDate"/>
                            <xsl:with-param name="documentNumber"  select="documentNumber"/>
                            <xsl:with-param name="bankName" select="dep:getDefaultBankName()"/>
                            <xsl:with-param name="bic"  select="$bic"/>
                            <xsl:with-param name="corrByBIC" select="dep:getCorrByBIC($bic)"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>

            <!--информация по получателю-->
            <xsl:call-template name="view-receiver-info-template"/>

            <!--информация по платильщику-->
            <xsl:call-template name="view-payer-info-template"/>

            <!--информация по периодичности-->
            <xsl:call-template name="view-event-template"/>

            <!--информация по сумме автоперевода-->
            <xsl:call-template name="view-sell-amount-template"/>

            <!--название автоперевода-->
            <xsl:call-template name="view-autotransfer-name-template"/>

            <!--сообщение получателю платежа-->
            <xsl:if test="receiverType = 'ph'">
                <xsl:call-template name="view-message-to-receiver-template"/>
            </xsl:if>

            <xsl:variable name="longOfferStartDate">
                <xsl:choose>
                    <xsl:when test="contains(longOfferStartDate, '-')">
                        <xsl:copy-of select="concat(substring(longOfferStartDate, 9, 2), '.', substring(longOfferStartDate, 6, 2), '.', substring(longOfferStartDate, 1, 4))"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:copy-of select="longOfferStartDate"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>

            <script type="text/javascript">

                var monthsOfYearDesc = ["января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"];
                var daysOfWeekDesc   = ["понедельникам", "вторникам", "средам", "четвергам", "пятницам", "субботам", "воскресеньям"];

                function showReport()
                {
                    $('.report1Class').html(generateReport1());
                    $('.report2Class').html(generateReport2());
                }

                showReport();

                function generateReport1()
                {
                    var eventType = '<xsl:value-of select="longOfferEventType"/>';
                    var startDate = Str2Date('<xsl:value-of select="$longOfferStartDate"/>');

                    switch(eventType)
                    {
                        case 'ONCE_IN_WEEK':    return 'раз в неделю, по ' + daysOfWeekDesc[(startDate.getDay()+6)%7];
                        case 'ONCE_IN_MONTH':   return 'раз в месяц, ' + startDate.getDate() + '-го числа';
                        case 'ONCE_IN_QUARTER': return 'раз в квартал, ' + startDate.getDate() + '-го числа ' + (startDate.getMonth()%3 + 1) + '-го месяца квартала';
                        case 'ONCE_IN_YEAR':    return 'раз в год, ' + startDate.getDate() + '-го ' + monthsOfYearDesc[startDate.getMonth()];
                    }
                }

                function generateReport2()
                {
                    var startDate = Str2Date('<xsl:value-of select="$longOfferStartDate"/>');
                    return 'Ближайший: '  + startDate.getDate() + '-го ' + monthsOfYearDesc[startDate.getMonth()];
                }

                function init()
                {
                    $('#smsAvatarContainer').html($('#avatarIconEl').clone());
                    customPlaceholder.init();
                }

                $(document).ready(init);

            </script>
        </div>
    </xsl:template>

    <xsl:template name="employeeState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='WAIT_CLIENT_MESSAGE'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")<xsl:if test="checkStatusCountResult = 'true'"> (Превышение количества проверок статуса)</xsl:if></xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if>")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
            <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="clientState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Черновик</xsl:when>
            <xsl:when test="$code='WAIT_CLIENT_MESSAGE'">Черновик</xsl:when>
            <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if></xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
            <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
