<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY ndash "&#150;">
        <!ENTITY nbsp "&#160;">
        <!ENTITY laquo "&#171;">
        <!ENTITY raquo "&#187;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:pu="java://com.rssl.phizic.web.util.PersonInfoUtil"
                xmlns:spu="java://com.rssl.phizic.web.util.ServiceProviderUtil"
                xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
                xmlns:xalan = "http://xml.apache.org/xalan">

	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
    <xsl:param name="documentState" select="''"/>
    <xsl:param name="isTemplate" select="'isTemplate'"/>
    <xsl:param name="byTemplate" select="'byTemplate'"/>
    <xsl:param name="isDefaultShow" select="true()"/>
    <xsl:param name="skinUrl" select="'skinUrl'"/>
    <!--<xsl:param name="isITunes" select="false()"/>-->
	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
    <xsl:key name="subService" match="Attribute[./Type != 'calendar']" use="GroupName"/>
	<xsl:variable name="formData" select="/form-data"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleClass" select="'form-row'"/>
	<xsl:variable name="styleSpecial" select="''"/>

	<xsl:template match="/">
	<script type="text/javascript">
        var payPeriodArray = new Array();
        var periodCount = 0;
        var sumCount = 0;

		var WRONG_PAY_PERIOD_MESSAGE = 'Некорректный формат периода. Введите значение в формате MM/ГГГГ.';
        var BAD_PAY_PERIOD = "Вы неправильно указали период оплаты. Добавляемый период должен отличаться от периодов, уже добавленных на форму.";

		function addPayPeriod(value)
        {
            removeMessage(WRONG_PAY_PERIOD_MESSAGE);

            if (!(/(0[0-9]|1[0-2])\/[0-9]{4}/.test($.trim(value))))
            {
                addMessage(WRONG_PAY_PERIOD_MESSAGE);
                return;
            }

            if (!validatePeriods(value))
            {
                return;
            }

            var tbody = document.getElementById('details').getElementsByTagName('TBODY')[0];
            var newRow = document.createElement('tr');
            newRow.setAttribute("id", "payPeriod" + periodCount + "TR");
            var newCol = document.createElement('td');
            var payPeriodId = 'partyPayPeriod' + periodCount;
            var trashIco  = '<xsl:value-of select="concat($resourceRoot, '/images/icon_trash.gif')"/>';
            var innerHTML =
            <![CDATA[
                '<div class="form-row" id="'+payPeriodId+'Row" onclick="payInput.onClick(this)">'+
                '<div class="paymentLabel"><span class="paymentTextLabel">Период оплаты (месяц/год)</span><span class="asterisk">*</span></div>'+
                '<div class="paymentValue">'+
                '<div class="paymentInputDiv"><input type="hidden" value="'+value+'" id="'+payPeriodId+'" name="'+payPeriodId+'" />'+
                '<div class="paymentInputDiv"><b>'+ value + '</b>'+
                '<span class="text-gray" style="padding-left: 15px; cursor: pointer;" onclick="dropPayPeriod(' + periodCount + ');"><u>Удалить период</u></span>'+
                '<img class="pmtImgHint" src="'+trashIco+'" alt=""/></div>'+
                '<div style="display: none" class="errorDiv"></div>'+
                '</div>'+
                '</div><div class="clear"></div>'+
                '</div>'+
                '<script type="text/javascript">if (document.getElementsByName("'+payPeriodId+'").length == 1) {'+
                'document.getElementsByName("'+payPeriodId+'")[0].onfocus = function() {payInput.onFocus(this);}}</'+'script>';
            ]]>
            newCol.innerHTML = innerHTML;
            newRow.appendChild(newCol);
            tbody.appendChild(newRow);
            savePayPeriod(periodCount);
            ensureElement('partyPayPeriodInit').value='';
            periodCount++;
        }

        function dropPayPeriod(rownumber)
        {
            var tbody = document.getElementById('details').getElementsByTagName('TBODY')[0];
            tbody.removeChild(document.getElementById("payPeriod" + rownumber + "TR"));
            payPeriodArray[rownumber] = null;
            rewritePayPeriod();
        }

        function validatePeriods(value)
        {
            var paySet = [];
            paySet[value] = true;
            delete errorHash['payPeriod'];
            for(var i = 0; i &lt; periodCount + 1; i++)
            {
                if (payPeriodArray[i] != null &amp;&amp; payPeriodArray[i] in paySet)
                {
                    errorHash['payPeriod'] = BAD_PAY_PERIOD;
                    break;
                }
                paySet[payPeriodArray[i]] = true;
            }
            getFieldError(errorHash);
            return !('payPeriod' in errorHash);
        }

        function rewritePayPeriod()
        {
            var str = '';
            for(var i = 0; i &lt; periodCount + 1; i++)
            {
                if (payPeriodArray[i] != null)
                {
                    if(str == '')
                        str = payPeriodArray[i];
                    else
                        str = str +';'+ payPeriodArray[i];
                }

            }
            getFieldError(errorHash);
            document.getElementById('payPeriod').value=str;
        }

        function savePayPeriod(num)
        {
            var elementId = 'partyPayPeriod' + num;
            var value = document.getElementById(elementId).value;
            payPeriodArray[num] = value;
            rewritePayPeriod();
        }

        var CONTRACT_AND_CONDITIONS_AGREEMENT = "Пожалуйста, ознакомьтесь с Договором на оказание услуг по предоставлению кредитного отчета и Условиями передачи и обработки информации из кредитного отчета. После этого установите галочки в соответствующих полях и подтвердите операцию.";
        var CONTRACT_AGREEMENT = "Пожалуйста ознакомьтесь с договором на оказание услуг по предоставлению кредитного отчета. После этого установите галочку в поле «Я согласен с договором на оказание услуг по предоставлению кредитного отчета» и подтвердите документ.";
        var CONDITIONS_AGREEMENT = "Пожалуйста ознакомьтесь с условиями передачи и обработки информации из кредитного отчета, щелкнув по ссылке «с условиями». После этого установите галочку в поле «Я согласен с условиями передачи и обработки информации из кредитного отчета» и подтвердите документ";

        function checkClientAgreesCondition(buttonName)
        {
            var contractAgreeChecked = getElement('agreeWithContract').checked;
            var conditionsAgreeChecked = getElement('agreeWithConditions').checked;
            if (contractAgreeChecked == false &amp;&amp; conditionsAgreeChecked == false)
            {
                 addError(CONTRACT_AND_CONDITIONS_AGREEMENT);
                 payInput.fieldError('agreeWithContract');
                 payInput.fieldError('agreeWithConditions');
            }
            else if (contractAgreeChecked == false)
            {
                addError(CONTRACT_AGREEMENT);
                payInput.fieldError('agreeWithContract');
            }
            else if (conditionsAgreeChecked == false)
            {
                addError(CONDITIONS_AGREEMENT);
                payInput.fieldError('agreeWithConditions');
            }
            return contractAgreeChecked &amp;&amp; conditionsAgreeChecked;
        }

        <!--Удаляет сообщения по названию кнопки-->
        function removeMessageByButtonName(buttonName)
        {
            removeMessage(CONTRACT_AND_CONDITIONS_AGREEMENT, 'errors');
            removeMessage(CONTRACT_AGREEMENT, 'errors');
            removeMessage(CONDITIONS_AGREEMENT, 'errors');
            $("#payment .form-row").each(function(){
                payInput.formRowClearError(this);
            });
        }

        function getAgreementMessage(buttonName)
        {
            if (buttonName!=undefined)
            {
                return AGREEMENT_CONDITION + '«' + buttonName + '».';
            }
            else
                return AGREEMENT_CONDITION + '«Подтвердить».';
        }

         function openPopupCreditReportContractTerms()
        {
            var width   = 900;
            var height  = 600;
            var top     = (screen.height - height) /2;
            var left    = (screen.width  - width)  /2;
            var options = 'height=' + height + ', width=' + width + ',top=' + top + ', left=' + left + 'directories=0, scrollbars=1, titlebar=0, toolbar=0, location=0, status=0, menubar=0';
            var text = document.getElementById('creditReportContractTermDiv').innerHTML;
            var win = window.open('', 'terms', options, false);
            win.document.write(text);
            win.document.close();
        }

        function openPopupCreditReportConditions()
        {
            var width   = 900;
            var height  = 600;
            var top     = (screen.height - height) /2;
            var left    = (screen.width  - width)  /2;

            var options = 'height=' + height + ', width=' + width + ',top=' + top + ', left=' + left + 'directories=0, titlebar=0, toolbar=0, location=0, status=0, menubar=0';

            var text = document.getElementById('creditReportConditionsDiv').innerHTML;
            var win = window.open('', 'terms', options, false);
            win.document.write(text);
            win.document.close();
        }
        ;
	</script>

        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
        </xsl:choose>
	</xsl:template>

    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeCards"/>

        <select id="{$name}" name="{$name}">
            <xsl:choose>
                <xsl:when test="count($activeCards) = 0 and count($activeAccounts) = 0">
                    <option value="">Нет доступных карт и счетов</option>
                </xsl:when>
                <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                    <xsl:choose>
                        <xsl:when test="count($activeAccounts) + count($activeCards) > 0">
                            <option value="">Выберите счет/карту списания</option>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="count($activeCards) = 0 and count($activeAccounts) > 0">
                                    <option value="">Выберите счет списания</option>
                                </xsl:when>
                                <xsl:when test="count($activeAccounts) = 0 and count($activeCards) > 0">
                                    <option value="">Выберите карту списания</option>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
            </xsl:choose>
            <xsl:for-each select="$activeAccounts">
                <option>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$accountNumber=./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="au:getFormattedAccountNumber(./@key)"/>&nbsp;
                    [<xsl:value-of select="./field[@name='name']"/>]
                    <xsl:if test="./field[@name='amountDecimal'] != ''">
                        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                    </xsl:if>
                    <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:for-each>
            <xsl:for-each select="$activeCards">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                <option>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$accountNumber= ./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="mask:getCutCardNumber(./@key)"/>&nbsp;
                    [<xsl:value-of select="./field[@name='name']"/>]
                    <xsl:if test="./field[@name='amountDecimal'] != ''">
                        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                    </xsl:if>
                    <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:for-each>
        </select>
    </xsl:template>

	<xsl:template match="/form-data" mode="edit">

		<script type="text/javascript">
            document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
        </script>
        <input name="bankDetails" type="hidden" value="{bankDetails}"/>
        <input name="recipient" type="hidden" value="{recipient}"/>

        <xsl:variable name="imageHelpUrl" select="spu:getImageHelpUrl(recipient, $webRoot)"/>

        <!-- window для отображения картинки -->
        <div id="providerImageHelpWin" class="window farAway">
            <div class="workspace-box shadow">
                <div class="shadowRT r-top">
                    <div class="shadowRTL r-top-left"><div class="shadowRTR r-top-right"><div class="shadowRTC r-top-center"></div></div></div>
                </div>
                <div class="shadowRCL r-center-left">
                    <div class="shadowRCR r-center-right">
                        <div class="shadowRC r-content">
                            <div id="providerImageHelp">
                                <div class="imageHelp" id="imageHelpProvider"></div>
                            </div>
                            <div class="clear"></div>
                            <div class="clear"></div>
                        </div>
                    </div>
                </div>
                <div class="shadowRBL r-bottom-left">
                    <div class="shadowRBR r-bottom-right">
                        <div class="shadowRBC r-bottom-center"></div>
                    </div>
                 </div>
            </div>
            <div class="closeImg" title="закрыть" onclick="win.close('providerImageHelp');"></div>
        </div>

        <xsl:if test="string-length($imageHelpUrl) > 0">
            <div class="dashedBorder">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">imageHelpProviderHeader</xsl:with-param>
                    <xsl:with-param name="rowName"><div class="imageHelpLabel">Образец квитанции</div></xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <script type="text/javascript" src="{$resourceRoot}/scripts/providerImageHelp.js"></script>
                        <div id="imageHelpProviderHeader">
                            <input type="hidden" id="imageHelpSrc" value="{$imageHelpUrl}"/>
                            <div class="imageHelpTitleContainer">
                                <a class="imageHelpTitle imageHelpHeaderControl closed" onclick="paymentImageHelpHeaderAction(); return false;" href="#">показать</a>
                            </div>
                            <div class="imageHelp" style="display:none"></div>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>
            </div>
        </xsl:if>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Получатель</b></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="receiverName" value="{receiverName}"/>
                    <b><xsl:value-of select="receiverName"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(nameService)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Услуга:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="nameService" value="{nameService}"/>
                    <b><xsl:value-of select="nameService"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
             <xsl:with-param name="rowName">Регион оплаты:</xsl:with-param>
             <xsl:with-param name="rowValue">
                <input type="hidden" name="personRegionName" value="{pu:getPersonRegionName()}"/>
                <xsl:call-template name="regionRowValue">
                    <xsl:with-param name="providerId" select="recipient"/>
                    <xsl:with-param name="personRegionName" select="pu:getPersonRegionName()"/>
                </xsl:call-template>
            </xsl:with-param>
         </xsl:call-template>


        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">ИНН:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="receiverINN" value="{receiverINN}"/>
                <b><xsl:value-of select="receiverINN"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Счет:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="receiverAccount" value="{receiverAccount}"/>
                <b><xsl:value-of select="receiverAccount"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Банк получателя</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Наименование:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="receiverBankName" value="{receiverBankName}"/>
                <b><xsl:value-of select="receiverBankName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">БИК:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="receiverBIC" value="{receiverBIC}"/>
                <b><xsl:value-of select="receiverBIC"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverCorAccount)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Корсчет:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" size="35" name="receiverCorAccount" value="{receiverCorAccount}"/>
                    <b><xsl:value-of select="receiverCorAccount"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:variable name="activeAccounts" select="document('chargeOffAccounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('chargeOffCards.xml')/entity-list/*"/>
		<xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">fromResource</xsl:with-param>
            <xsl:with-param name="rowName">Счет списания:</xsl:with-param>
            <xsl:with-param name="rowValue">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="linkId" select="fromResource"/>
                        <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                        <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                        <xsl:with-param name="activeCards" select="$activeCards"/>
                    </xsl:call-template>
            </xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Детали платежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="editMode">
            <xsl:choose>
                <xsl:when test="string-length(recipient) > 0">
                    <xsl:value-of select="dh:getEditMode(recipient)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'dinamic'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:for-each select="$extendedFields[./Type != 'calendar']">
            <xsl:choose>
                <!-- Выводим поля по группам, необходимо для случая, если услуга сложная -->
                <!-- Если среди обработанных полей есть хотя бы одно поле с таким же GroupName, как у текущего, то ничего не делаем -->
                <xsl:when test="preceding-sibling::Attribute[./GroupName=current()/GroupName and current()/GroupName != '']"/>
                <!-- Если полей с текущим GroupName еще не было, то вытаскиваем из мапа все поля с таким GroupName и обрабатываем -->
                <xsl:otherwise>
                    <xsl:for-each select="self::node()|key('subService',current()/GroupName)[./GroupName !='']">
                        <xsl:variable name="name" select="./NameVisible"/>
                        <xsl:variable name="hidden" select="./IsVisible = 'false'"/>
                        <xsl:variable name="id" select="./NameBS"/>
                        <xsl:variable name="isKey" select="./IsKey = 'true'"/>
                        <xsl:variable name="saveInTemplate" select="./SaveInTemplate = 'true'"/>
                        <xsl:variable name="description" select="./Description"/>
                        <xsl:variable name="hint" select="./Comment"/>
                        <xsl:variable name="size" select="./MaxLength"/>
                        <xsl:variable name="readonly" select="./IsEditable = 'false'"/>
                        <xsl:variable name="groupName" select="./GroupName"/>
                        <xsl:variable name="popupHint" select="./PopupHint"/>
                        <xsl:variable name="extendedDescId" select="./ExtendedDescriptionId"/>
                        <xsl:variable name="graphicTemplateName" select="./GraphicTemplateName"/>
                        <xsl:variable name="isMaskedValue" select="$formData/*[name()=$id]/@masked = 'true'"/>
                        <xsl:variable name="val">
                            <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                            <xsl:choose>
                                <!--если задано значение явно, юзаем его-->
                                <xsl:when test="string-length($currentValue) > 0">
                                    <xsl:value-of select="$currentValue"/>
                                </xsl:when>
                                <!--иначе - инициализирующее значение-->
                                <xsl:otherwise>
                                    <xsl:value-of select="./DefaultValue"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <xsl:variable name="mainSum" select="./IsMainSum = 'true'"/>
                        <xsl:variable name="sum" select="./IsSum = 'true'"/>
                        <xsl:variable name="type" select="./Type"/>
                        <xsl:variable name="mandatory" select="./IsRequired"/>
                        <xsl:variable name="disabled"  select="boolean($isTemplate != 'true' and $byTemplate = 'true' and $editMode='static' and $mainSum != 'true' and ($isKey='true' or $saveInTemplate='true'))"/>

                        <xsl:choose>
                            <!-- Если у поля GroupName не пустой и поле является первым в списке, то это Имя услуги, отображаем как заголовок -->
                            <xsl:when test="$groupName != '' and position() = 1">
                                <xsl:call-template name="titleRow">
                                    <xsl:with-param name="rowName">
                                        <div class="subTitle">&nbsp;&nbsp;&nbsp;<b><xsl:value-of select="$val"/></b></div>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:when test="$type = 'link' and $readonly">
                                <xsl:call-template name="simpleLink">
                                    <xsl:with-param name="url" select="$val"/>
                                    <xsl:with-param name="text" select="$name"/>
                                    <xsl:with-param name="id" select="$id"/>
                                    <xsl:with-param name="name" select="$id"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:when test="$type='graphicset'">
                                <xsl:variable name="parameters">
                                    <fields>
                                        <xsl:for-each select="./Menu/MenuItem">
                                            <field key="{./Id}"><xsl:value-of select="./Value"/></field>
                                        </xsl:for-each>
                                    </fields>
                                </xsl:variable>
                                <xsl:call-template name="graphicSetField">
                                    <xsl:with-param name="id" select="position()"/>
                                    <xsl:with-param name="name" select="$graphicTemplateName"/>
                                    <xsl:with-param name="params" select="xalan:nodeset($parameters)"/>
                                    <xsl:with-param name="readonly" select="$readonly"/>
                                    <xsl:with-param name="initvalue" select="$val"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:when test="$type='choice'">
                                <xsl:call-template name="choiceField">
                                    <xsl:with-param name="id" select="$id"/>
                                    <xsl:with-param name="text" select="$name"/>
                                    <xsl:with-param name="isChecked" select="$val = 'true'"/>
                                    <xsl:with-param name="extendedDescId" select="$extendedDescId"/>
                                    <xsl:with-param name="readonly" select="$readonly"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="standartRow">
                                    <xsl:with-param name="rowName"><xsl:value-of select="$name"/></xsl:with-param>
                                    <xsl:with-param name="rowStyle"><xsl:if test="$hidden">display:none</xsl:if></xsl:with-param>
                                    <xsl:with-param name="required"><xsl:value-of select="$mandatory"/></xsl:with-param>
                                    <xsl:with-param name="lineId"><xsl:value-of select="$id"/></xsl:with-param>
                                    <xsl:with-param name="popupHint"><xsl:value-of select="$popupHint"/></xsl:with-param>
                                    <xsl:with-param name="description"><xsl:value-of select="$description"/>
                                            <xsl:if test="string-length($hint) > 0">
                                                <cut /><xsl:value-of select="$hint" />
                                            </xsl:if>
                                    </xsl:with-param>
                                    <xsl:with-param name="additionalHelpData">
                                        <xsl:if test="string-length($imageHelpUrl) > 0 and not($hidden) and not($readonly)">
                                            <div class="paymentValue" id="imageHelpProviderField{$id}">
                                                <a href="#" class="imageHelpTitle" onclick="paymentImageHelpFieldAction('{$id}');return false;">образец квитанции</a>
                                                <div class="imageHelp" style="display:none"></div>
                                            </div>
                                        </xsl:if>
                                    </xsl:with-param>

                                    <xsl:with-param name="rowValue">
                                        <xsl:choose>
                                            <xsl:when test="$type='list'">
                                                <xsl:variable name="content">
                                                    <xsl:if test="$disabled">
                                                        <xsl:attribute name="disabled"/>
                                                    </xsl:if>
                                                    <xsl:for-each select="./Menu/MenuItem">
                                                        <xsl:variable name="code" select="./Id"/>
                                                        <xsl:choose>
                                                            <xsl:when test="not($readonly)">
                                                                <option>
                                                                    <xsl:attribute name="value">
                                                                        <xsl:value-of select="$code"/>
                                                                    </xsl:attribute>
                                                                    <xsl:if test="$code = $val">
                                                                        <xsl:attribute name="selected"/>
                                                                    </xsl:if>
                                                                    <xsl:value-of select="./Value"/>
                                                                </option>
                                                            </xsl:when>
                                                            <xsl:when test="$val = $code">
                                                                <xsl:value-of select="./Value"/>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </xsl:for-each>
                                                </xsl:variable>

                                                <xsl:if test="not($readonly)">
                                                    <select id="{$id}" name="{$id}" class="detailsSelectField">
                                                        <xsl:copy-of select="$content"/>
                                                    </select>
                                                </xsl:if>
                                                <xsl:if test="$readonly">
                                                    <input type="hidden" name="{$id}" value="{$val}"/>
                                                    <b><xsl:copy-of select="$content"/></b>
                                                </xsl:if>
                                            </xsl:when>

                                            <xsl:when test="$type='set'">
                                                <xsl:variable name="checkedValue" select="$formData/*[name()=$id]/text()"/>
                                                <input type="hidden" id="{$id}" name="{$id}" value="{$checkedValue}"/>

                                                <xsl:for-each select="./Menu/MenuItem">
                                                    <xsl:variable name="checkBoxValue" select="./Value"/>
                                                    <xsl:variable name="checkBoxId"    select="concat($id, '_', $checkBoxValue)"/>

                                                    <xsl:if test="$readonly">
                                                        <xsl:for-each select="xalan:tokenize($val, '@')">
                                                            <xsl:if test="current() = $checkBoxValue">
                                                                <b><xsl:value-of select="current()"/></b><br/>
                                                            </xsl:if>
                                                        </xsl:for-each>
                                                    </xsl:if>

                                                    <xsl:if test="not($readonly)">
                                                        <input id="{$checkBoxId}" type="checkbox" onclick="{concat('javascript:changeSetValue(ensureElement(&quot;', $checkBoxId, '&quot;));')}">
                                                            <xsl:if test="$disabled">
                                                                <xsl:attribute name="disabled"/>
                                                            </xsl:if>
                                                            <xsl:choose>
                                                                <xsl:when test="contains($checkedValue, '@')">
                                                                    <xsl:for-each select="xalan:tokenize($checkedValue, '@')">
                                                                        <xsl:if test="current() = $checkBoxValue">
                                                                            <xsl:attribute name="checked"/>
                                                                        </xsl:if>
                                                                    </xsl:for-each>
                                                                </xsl:when>
                                                                <xsl:when test="$checkedValue = $checkBoxValue">
                                                                    <xsl:attribute name="checked"/>
                                                                </xsl:when>
                                                            </xsl:choose>
                                                            &nbsp;<xsl:value-of select="$checkBoxValue"/>
                                                        </input><br/>
                                                    </xsl:if>
                                                </xsl:for-each>
                                            </xsl:when>

                                            <xsl:otherwise>
                                                <xsl:variable name="inputSize">
                                                    <xsl:choose>
                                                        <xsl:when test="$size + 3 > 50">50</xsl:when>
                                                        <xsl:otherwise><xsl:value-of select="$size + 3"/></xsl:otherwise>
                                                    </xsl:choose>
                                                </xsl:variable>
                                                <xsl:variable name="className">
                                                    <xsl:if test="$type='date'">dot-date-pick</xsl:if>
                                                    <xsl:if test="$sum='true'"> moneyField</xsl:if>
                                                    <xsl:if test="$isMaskedValue"> masked-phone-number</xsl:if>
                                                </xsl:variable>
                                                <b>
                                                    <xsl:choose>
                                                        <xsl:when test="$readonly">
                                                            <input type="hidden" value="{$val}" id="{$id}" size="{$inputSize}" maxlength="{$size}" name="{$id}"/>
                                                            <xsl:value-of select="$val"/>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <input type="text" class="{$className}" value="{$val}" id="{$id}" size="{$inputSize}" maxlength="{$size}" name="{$id}">
                                                                <xsl:if test="$disabled">
                                                                    <xsl:attribute name="disabled"/>
                                                                </xsl:if>
                                                            </input>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                    <xsl:if test="$sum='true'">&nbsp;руб.</xsl:if>
                                                </b>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:with-param>
                                </xsl:call-template>
                          </xsl:otherwise>
                        </xsl:choose>
                    </xsl:for-each>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>

        <xsl:for-each select="$extendedFields[./Type = 'calendar']">
            <div>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">partyPeriod</xsl:with-param>
                    <xsl:with-param name="rowName">Период оплаты (месяц/год)</xsl:with-param>
                    <xsl:with-param name="description">Укажите месяц и год, за который Вы хотите внести платеж, и нажмите на ссылку «Добавить период».</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <script type="text/javascript">
                            doOnLoad(
                                function()
                                {
                                    $(function()
                                    {
                                        if ($('.Perioddate-pick').datePicker)
                                            $('.Perioddate-pick').datePicker({displayClose: true, chooseImg: globalUrl + '/commonSkin/images/calendar.png', dateFormat:'MM/yyyy'.toLowerCase()});
                                    })
                             }
                            );
                         </script>

                         <xsl:variable name="id" select="./NameBS"/>
                         <input type="hidden" id="{$id}" name="{$id}" value="{$formData/*[name()=$id]}"/>
                         <input name="partyPayPeriodInit" class="Perioddate-pick dp-applied" size="7" value="" id="partyPayPeriodInit" onkeydown="enterNumTemplateField(event,this, 'MM/yyyy')"/>
                         <span class="text-green" style="padding: 0 15px; cursor: pointer;" onclick="addPayPeriod(ensureElement('partyPayPeriodInit').value);"><u>Добавить период</u></span>
                    </xsl:with-param>
                </xsl:call-template>

                <table id="details">
                    <tbody><tr><td/></tr></tbody>
                </table>
            </div>
        </xsl:for-each>

		<script type="text/javascript">
            function initPeriods()
            {
                var periodsField = document.getElementById('payPeriod');
                if (periodsField.value == '')
                    return;

                var periods = periodsField.value.split(';');
                for (var i=0; i&lt;periods.length; i++)
                {
                    var value = periods[i];
                    addPayPeriod(value);
                }
            }

            function changeSetValue(checkBox)
            {
                var checkBoxId    = checkBox.id;
                var checkBoxValue = checkBoxId.substr(checkBoxId.indexOf("_") + 1, checkBoxId.length);

                var set      = ensureElement(checkBoxId.substr(0, checkBoxId.indexOf("_")));
                var setValue = set.value;
                var checked  = checkBox.checked;

                //если значение этого checkBox'а не внесено в значение set' и он сейчас выделен
                if (setValue.indexOf(checkBoxId) &lt; 0 &amp;&amp; checked)
                {
                    if (setValue != "")
                        setValue = setValue + "@";
                    setValue = setValue + checkBoxValue;
                }

                //убираем выделение
                if (!checked)
                {
                    if (setValue.indexOf(checkBoxValue) &gt; 0)
                        setValue = setValue.replace("@" + checkBoxValue, "");

                    if (setValue.indexOf(checkBoxValue) == 0)
                    {
                        var replaceValue = setValue.indexOf("@") &gt; 0 ? checkBoxValue + "@" : checkBoxValue;
                        setValue = setValue.replace(replaceValue, "");
                    }
                }

                set.value = setValue;
            }

            var accounts = new Array();
            var cardAccounts = new Array();

            function init()
            {
                var USING_STORED_CARDS_RESOURCE_MESSAGE    = 'Информация по Вашим картам может быть неактуальной.';
                var USING_STORED_ACCOUNTS_RESOURCE_MESSAGE = 'Информация по Вашим счетам может быть неактуальной.';
                <xsl:for-each select="$activeAccounts/*">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    accounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="@key"/>';
                    <xsl:if test="field[@name='isUseStoredResource'] = 'true'">
                        addMessage(USING_STORED_ACCOUNTS_RESOURCE_MESSAGE);
                    </xsl:if>
                </xsl:for-each>
                <xsl:for-each select="$activeCards/*">
                    <xsl:variable name="id" select="concat('card:',field[@name='cardLinkId']/text())"/>
                    cardAccounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';

                    <xsl:if test="field[@name='isUseStoredResource'] = 'true'">
                        addMessage(USING_STORED_CARDS_RESOURCE_MESSAGE);
                    </xsl:if>
                </xsl:for-each>
                <xsl:if test="$extendedFields/Type = 'calendar'">
                    initPeriods();
                </xsl:if>

                <xsl:variable name="recipient" select="recipient"/>
            }
            <xsl:if test="$webRoot='/PhizIC'">
            doOnLoad(function()
            {
                getFieldError(errorHash);
                getFieldMessage(messageHash);
            });
            </xsl:if>
            init();
		</script>
	</xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:variable name="bankDetails" select="bankDetails"/>
        <input type="hidden" name="bankDetails" value="{bankDetails}"/>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Получатель</b></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverName"/></b>
                    <input name="receiverName" type="hidden" value="{receiverName}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(nameService)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Услуга:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="nameService"/></b>
                    <input name="nameService" type="hidden" value="{nameService}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">ИНН:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverINN"/></b>
                <input name="receiverINN" type="hidden" value="{receiverINN}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Счет:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverAccount"/></b>
                <input name="receiverAccount" type="hidden" value="{receiverAccount}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Банк получателя:</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Наименование:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverBankName"/></b>
                <input name="receiverBankName" type="hidden" value="{receiverBankName}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">БИК:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverBIC"/></b>
                <input name="receiverBIC" type="hidden" value="{receiverBIC}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverCorAccount)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Корсчет:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverCorAccount"/></b>
                    <input name="receiverCorAccount" type="hidden" value="{receiverCorAccount}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:variable name="fromAccountSelect">
            <xsl:choose>
                <xsl:when test="string-length(fromAccountSelect)>0">
                    <xsl:value-of select="fromAccountSelect"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:variable name="fromResourceLink" select="fromResourceLink"/>
                    <xsl:choose>
                        <xsl:when test="(fromResourceType='com.rssl.phizic.business.resources.external.CardLink') or (starts-with(fromResource, 'card:'))">
                            <xsl:variable name="activeCards" select="document('chargeOffCards.xml')/entity-list"/>
                            <xsl:variable name="cardNode" select="$activeCards/entity[$fromResourceLink=./field[@name='code']]"/>
                            <xsl:if test="$cardNode">
                                <xsl:value-of select="$cardNode/@key"/>
                            </xsl:if>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:variable name="activeAccounts" select="document('chargeOffAccounts.xml')/entity-list"/>
                            <xsl:variable name="accountNode" select="$activeAccounts/entity[$fromResourceLink=./field[@name='code']]"/>
                            <xsl:if test="$accountNode">
                                <xsl:value-of select="$accountNode/@key"/>
                            </xsl:if>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <xsl:if test="not($fromAccountSelect = '')">
                    <b>
                        <xsl:choose>
                            <xsl:when test="(fromResourceType='com.rssl.phizic.business.resources.external.CardLink') or (starts-with(fromResource, 'card:'))">
                                <xsl:value-of select="mask:getCutCardNumber($fromAccountSelect)"/>
                            </xsl:when>
                            <xsl:otherwise><xsl:value-of select="au:getFormattedAccountNumber($fromAccountSelect)"/></xsl:otherwise>
                        </xsl:choose>

                        &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                        <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                    </b>
                </xsl:if>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:if test="not($isTemplate = 'true')">
            <xsl:choose>
                <xsl:when test="string-length(commission)>0">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="rowName">Комиссия:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                                <b><xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/></b>
                                <input name="commission" type="hidden" value="{commission}"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:when>
            </xsl:choose>
        </xsl:if>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Детали платежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="currency" select="destinationCurrency"/>
        <input name="destinationCurrency" type="hidden" value="{destinationCurrency}"/>

        <xsl:for-each select="$extendedFields">
            <xsl:choose>
                <xsl:when test="preceding-sibling::Attribute[./GroupName=current()/GroupName and current()/GroupName != '']"/>
                <xsl:otherwise>
                    <xsl:for-each select="self::node()|key('subService',current()/GroupName)[./GroupName !='']">
                        <xsl:variable name="name" select="./NameVisible"/>
                        <xsl:variable name="isHideInConfirmation" select="./HideInConfirmation = 'true'"/>
                        <xsl:variable name="hidden" select="./IsVisible = 'false'"/>
                        <xsl:variable name="type" select="./Type"/>
                        <xsl:variable name="id" select="./NameBS"/>
                        <xsl:variable name="isKey" select="./IsKey = 'true'"/>
                        <xsl:variable name="hint" select="./Comment"/>
                        <xsl:variable name="mainSum" select="./IsMainSum = 'true'"/>
                        <xsl:variable name="readonly" select="./IsEditable = 'false'"/>
                        <xsl:variable name="groupName" select="./GroupName"/>
                        <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                        <xsl:variable name="mandatory" select="./IsRequired"/>
                        <xsl:variable name="popupHint" select="./PopupHint"/>
                        <xsl:variable name="mask" select="./Mask"/>
                        <xsl:variable name="extendedDescId" select="./ExtendedDescriptionId"/>
                        <xsl:variable name="graphicTemplateName" select="./GraphicTemplateName"/>
            <!--скрытые редактируемые поля - это персчитываемые биллингом поля, значения которых необходимо показывать только на форме просмотра(подтвержедения)-->
                        <xsl:if test="not($isHideInConfirmation) and not($hidden) and not($type='calendar')">
                            <xsl:choose>
                                <!-- Если у поля GroupName не пустой и поле является первым в списке, то это Имя услуги, отображаем как заголовок -->
                                <xsl:when test="$groupName != '' and position() = 1">
                                    <xsl:call-template name="titleRow">
                                        <xsl:with-param name="rowName">
                                            <div class="subTitle">&nbsp;&nbsp;&nbsp;<b><xsl:value-of select="$formData/*[name()=$id]"/></b></div>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:when test="$type = 'link' and $readonly">
                                    <xsl:call-template name="simpleLink">
                                        <xsl:with-param name="url" select="$currentValue"/>
                                        <xsl:with-param name="text" select="$name"/>
                                        <xsl:with-param name="id" select="$id"/>
                                        <xsl:with-param name="name" select="$id"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:when test="$type='graphicset'">
                                    <xsl:variable name="parameters">
                                        <fields>
                                            <xsl:for-each select="./Menu/MenuItem">
                                                <field key="{./Id}"><xsl:value-of select="./Value"/></field>
                                            </xsl:for-each>
                                        </fields>
                                    </xsl:variable>
                                    <xsl:call-template name="graphicSetField">
                                        <xsl:with-param name="id" select="position()"/>
                                        <xsl:with-param name="name" select="$graphicTemplateName"/>
                                        <xsl:with-param name="params" select="xalan:nodeset($parameters)"/>
                                        <xsl:with-param name="readonly" select="true()"/>
                                        <xsl:with-param name="initvalue" select="$currentValue"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:when test="$type='choice'">
                                    <xsl:if test="not($isHideInConfirmation) and not($hidden)">
                                        <xsl:call-template name="choiceField">
                                            <xsl:with-param name="id" select="$id"/>
                                            <xsl:with-param name="text" select="$name"/>
                                            <xsl:with-param name="extendedDescId" select="$extendedDescId"/>
                                            <xsl:with-param name="isChecked" select="$currentValue = 'true'"/>
                                            <xsl:with-param name="readonly" select="true()"/>
                                        </xsl:call-template>
                                    </xsl:if>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:call-template name="standartRow">
                                        <xsl:with-param name="id"><xsl:value-of select="$id"/></xsl:with-param>
                                        <xsl:with-param name="rowName"><xsl:value-of select="$name"/></xsl:with-param>
                                        <xsl:with-param name="description"><xsl:value-of select="$hint"/></xsl:with-param>
                                        <xsl:with-param name="required"><xsl:value-of select="$mandatory"/></xsl:with-param>
                                        <xsl:with-param name="popupHint"><xsl:value-of select="$popupHint"/></xsl:with-param>
                                        <xsl:with-param name="lineId"><xsl:value-of select="$id"/></xsl:with-param>
                                        <xsl:with-param name="additionStyle">
                                        </xsl:with-param>
                                        <xsl:with-param name="rowValue"><b>
                                            <xsl:variable name="sum" select="./IsSum"/>
                                            <xsl:if test="string-length($currentValue)>0 ">
                                                <xsl:variable name="formatedValue">
                                                    <xsl:choose>
                                                        <xsl:when test="($type='string' or $type='number') and ($mainSum='true')">
                                                            <xsl:value-of select="format-number($currentValue, '### ##0,00', 'sbrf')"/><span class='summ'>&nbsp;<xsl:value-of select="mu:getCurrencySign($currency)"/></span>
                                                        </xsl:when>
                                                        <xsl:when test="$type='list'">
                                                            <xsl:for-each select="./Menu/MenuItem">
                                                                <xsl:if test="./Id = $currentValue">
                                                                    <xsl:value-of select="./Value"/>
                                                                </xsl:if>
                                                            </xsl:for-each>
                                                        </xsl:when>

                                                        <xsl:when test="$type='set'">
                                                            <xsl:choose>
                                                                <xsl:when test="contains($currentValue, '@')">
                                                                    <xsl:for-each select="xalan:tokenize($currentValue, '@')">
                                                                        <xsl:value-of select="current()"/>
                                                                        <br/>
                                                                    </xsl:for-each>
                                                                </xsl:when>
                                                                <xsl:otherwise>
                                                                    <xsl:value-of select="$currentValue"/>
                                                                </xsl:otherwise>
                                                            </xsl:choose>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <xsl:value-of select="$currentValue"/>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </xsl:variable>
                                                <xsl:choose>
                                                    <xsl:when test="$mainSum = 'true'">
                                                        <span class="summ"><xsl:value-of select="$formatedValue"/><xsl:if test="not(($type='string' or $type='number') and string-length($currency)>0)">&nbsp;руб.</xsl:if></span>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:choose>
                                                            <xsl:when test="$formData/*[name()=$id]/@changed='true'">
                                                                <b>
                                                                    <xsl:copy-of select="$formatedValue"/>
                                                                </b>
                                                            </xsl:when>
                                                            <xsl:otherwise>
                                                                <xsl:copy-of select="$formatedValue"/>
                                                            </xsl:otherwise>
                                                        </xsl:choose>
                                                        <xsl:if test="$sum='true'">&nbsp;руб.</xsl:if>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                                <input type="hidden" name="{$id}" value="{$currentValue}"/>
                                            </xsl:if></b>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>

        <xsl:if test="$extendedFields/Type = 'calendar'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Период оплаты</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:variable name="payPeriods" select="payPeriod"/>
                    <b>
                        <xsl:for-each select="xalan:tokenize($payPeriods, ';')">
                            <xsl:if test="position() > 1">;&nbsp;</xsl:if>
                            <xsl:value-of select="."/>
                        </xsl:for-each>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">
                Статус
                <xsl:choose>
                    <xsl:when test="$isTemplate != 'true'">
                        платежа:
                    </xsl:when>
                    <xsl:otherwise>
                        шаблона:
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
            <xsl:with-param name="rowValue"><b>
               <div id="state">
                    <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
                        <xsl:choose>
                            <xsl:when test="$app = 'PhizIA'">
                                <xsl:call-template name="employeeState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="clientState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </span>
                </div></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="creditReportConditionsText" select="'Я даю свое согласие ОАО «Сбербанк России», в том числе его филиалам (далее по тексту - Банк), местонахождением: 117997, г. Москва, ул. Вавилова, д.19, на обработку всех моих персональных данных, указанных в кредитных отчетах, в соответствии с Федеральным законом от 27.07.2006 г. № 152-ФЗ «О персональных данных» в виде записи и хранения информации с использованием средств автоматизации Банка или без использования таких средств в целях получения мною кредитного отчета с использованием услуги «Сбербанк Онл@йн». Согласие действительно в течение пяти лет с момента его предоставления. По истечении указанного срока действие согласия считается продленным на каждые следующие пять лет при отсутствии сведений о его отзыве. Согласие может быть отозвано мною в любой момент путем передачи Банку подписанного мною письменного уведомления.'"/>

        <div id="creditReportTermsDiv">
            <div id="creditReportContractTermDiv" style="display: none;">
                <div style="margin: 50px">
                <p align="center"><strong>Договор</strong><br/>
                  <strong>об оказании информационных услуг </strong><br/>
                  <strong>(предоставлении кредитного отчета)</strong><br/>
                  г. Москва</p>
                  <p>Настоящий документ представляет собой предложение &ndash; оферту <strong>Закрытого акционерного общества &laquo;Объединенное Кредитное Бюро&raquo;</strong>, именуемого в дальнейшем <strong>&laquo;Бюро&raquo;</strong>, физическому лицу, далее именуемому - <strong>&laquo;Клиент&raquo;</strong>, совместно с Бюро именуемые <strong>&laquo;Стороны&raquo;</strong>, а по отдельности <strong>&laquo;Сторона&raquo;</strong>, заключить через агента - <strong>Открытое акционерное общество &laquo;Сбербанк России&raquo;</strong> (далее &ndash; <strong>&laquo;Агент&raquo;</strong>), действующего от имени и по поручению Бюро, посредством использования Системы Агента договор об оказании информационных услуг (далее <strong>&laquo;Договор&raquo;</strong>) на изложенных ниже условиях.<br/>
                  <strong>1. Порядок заключения Договора</strong><br/>
                  <strong>1.1.</strong> Клиент осведомлен и согласен с тем, что Договор считается заключенным в случае совершения Клиентом в совокупности следующих действий: <br/>
                  - подтверждения ознакомления и согласия с условиями Договора путем проставления отметки в поле &laquo;Я согласен с условиями договора на оказание услуг по предоставлению кредитного отчета&raquo; и ввода одноразового пароля, <br/>
                  - оплаты Услуги после подтверждения ознакомления с условиями Договора. <br/>
                  <strong>1.2.</strong> Подтверждение ознакомления и согласия с условиями Договора путем ввода одноразового пароля в Системе и оплата Услуги Бюро означают полное и безоговорочное принятие Клиентом всех условий Договора без каких-либо изъятий и/или ограничений и равносилен заключению двухстороннего письменного договора. Электронные документы, подтвержденные вводом одноразового пароля, признаются Сторонами равнозначными документам на бумажном носителе и могут служить доказательством в суде.<br/>
                  <strong>2. Определения</strong><br/>
                  Термины, используемые в настоящем Договоре, имеют следующее значение:<br/>
                  <strong>&laquo;Закон&raquo;</strong> - Федеральный Закон № 218-ФЗ &laquo;О кредитных историях&raquo; от 30 декабря 2004 года, со всеми изменениями и дополнениями;<br/>
                  <strong>&laquo;Кредитная история&raquo;</strong> - информация, состав которой определен Законом; <br/>
                  <strong>&laquo;Запрос&raquo; или &laquo;Запросы&raquo;</strong> - сформированный Клиентом с использованием Системы запрос о предоставлении его Кредитного отчета, выраженный в согласии с условиями Договора и оплате Услуги.<br/>
                  <strong>&laquo;Кредитный отчет&raquo;</strong> - документ, который содержит информацию, входящую в состав Кредитной истории, хранящейся в Бюро, и который Бюро предоставляет по Запросу Клиента. Состав Кредитного отчета, предоставляемого в рамках настоящего Договора, указан в п. 4.3 настоящего Договора. Бюро гарантирует, что информация, содержащаяся в Кредитном отчете, соответствует такой информации, содержащейся в Кредитной истории Клиента, хранящейся в Бюро на момент формирования Кредитного отчета. В целях безопасности персональные данные титульной части Кредитного отчета маскированы. <br/>
                  <strong>&laquo;Система&raquo;</strong> - система дистанционного доступа Агента, предоставляемая Агентом Клиенту через глобальную информационно-телекоммуникационную сеть Интернет (&laquo;Сбербанк ОнЛайн&raquo;).<br/>
                  <strong>&laquo;Идентификация Клиента&raquo;</strong> - процесс подтверждения подлинности Клиента, осуществляемый Агентом в Системе по установленным в ней правилам.<br/>
                  <strong>3. Предмет Договора</strong><br/>
                  <strong>3.1.</strong> В соответствии с условиями настоящего Договора Бюро обязуется по Запросу Клиента, при условии его Идентификации, предоставить Клиенту через Систему Кредитный отчет <strong>(</strong>далее<strong> &laquo;Услуга&raquo;</strong>), а Клиент обязуется оплатить Услугу в порядке, установленном Договором. <br/>
                  <strong>3.2.</strong> Услуга оказывается в целях информирования Клиента лично об информации, содержащейся в базе Бюро, характеризующей исполнение Клиентом принятых на себя обязательств по договорам займа (кредита).<u></u><br/>
                  <strong>4. Порядок предоставления Кредитного отчета</strong><br/>
                  <strong>4.1.</strong> Кредитный отчет предоставляется Клиенту в срок не позднее трех рабочих дней с момента оплаты Услуги путем отображения Кредитного отчета в личном кабинете Клиента в Системе. Кредитный отчет доступен для печати и сохранения. При направлении нового Запроса предоставление Кредитного отчета осуществляется путем обновления информации, содержащейся в раннее предоставленном Клиенту Кредитном отчете в Системе. В таком случае ранее предоставленный кредитный отчет недоступен.<br/>
                  <strong>4.2.</strong> Кредитный отчет считается предоставленным Клиенту с момента отображения Кредитного отчета в личном кабинете Клиента в Системе.<br/>
                  <strong>4.3.</strong> Кредитный отчет, предоставляемый в рамках настоящего Договора, содержит следующую информацию, входящую в состав Кредитной истории: <br/>
                  - персональные данные субъекта кредитной истории (за исключением маскированных данных титульной части Кредитного отчета), <br/>
                  - о действующих и закрытых кредитных договорах, <br/>
                  - о запросах <br/>
                  - об истории просрочек в исполнении обязательств, <br/>
                  - об источниках кредитной истории. <br/>
                  <strong>5. Стоимость и оплата Услуг</strong><br/>
                  <strong>5.1.</strong> За Услуги, оказываемые Бюро Клиенту по настоящему Договору, Клиент уплачивает Бюро вознаграждение в размере 420 (четырехсот двадцати) рублей (включая НДС).<br/>
                  <strong>5.2.</strong> Оплата Услуг Бюро осуществляется Клиентом в Системе с выбранного Клиентом счета.<br/>
                  <strong>6. Ответственность</strong><br/>
                  <strong>6.1.</strong> Клиент понимает и согласен с тем, что содержащаяся в Кредитном отчете информация получена Бюро от источников формирования кредитных историй в соответствии с Законом. Бюро не обязано проверять достоверность информации, предоставленной ему источниками формирования кредитных историй, и не несет ответственности за ее неактуальность или недостоверность. Исходя из вышеизложенного, Клиент не имеет права на возмещение ему Бюро возможных убытков, возникших в связи с недостоверностью или неточностью информации, содержащейся в Кредитных отчетах Бюро, в случае ее соответствия кредитной истории, содержащейся в базе Бюро.<br/>
                  <strong>6.2.</strong> Бюро не несет ответственности за возможные убытки Клиента, возникшие в связи с невозможностью использования Кредитного отчета по иному назначению, кроме как указанному в пункте 3.2. настоящего Договора.<br/>
                  <strong>7. Заключительные положения </strong><br/>
                  <strong>7.1.</strong> В случае невозможности использования Услуги в связи со сбоями, перерывами и ошибками в работе Системы, а также по другим вопросам оказания Услуги Клиенту будет обращаться в Контактный центр по телефону&nbsp;- 8 (800) 555 55 50 (круглосуточно).<br/>
                  <strong>7.2.</strong> Договор вступает в силу с момента совершения Клиентом действий, указанных в п. 1.1. Договора, и действует до полного исполнения Сторонами обязательств по Договору.</p>
                  <p>Полное наименование, адрес и реквизиты Бюро:<br/>
                  Закрытое акционерное общество &laquo;Объединенное Кредитное Бюро&raquo;<br/>
                  127006, г. Москва, ул. 1-ая Тверская-Ямская, д. 2, стр. 1<br/>
                  ОГРН №1047796788819 <br/>
                  ИНН 7710561081<br/>
                  р/с №40702810938180003245 в ОАО &laquo;Сбербанк России&raquo; Московский банк, к/с №30101810400000000225 в ОПЕРУ Московского ГТУ Банка России, БИК 044525225</p>
                  <p>&nbsp;</p>
                </div>
            </div>

            <div id="creditReportConditionsDiv" style="display: none;">
                <div style="margin: 50px">
                    <h1>Условия передачи обработки информации из кредитного отчета</h1>

                    <p>
                        <xsl:value-of select="$creditReportConditionsText"/>
                    </p>
                </div>
            </div>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'true'"/>
                <xsl:with-param name="rowName">&nbsp;</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="contractBlock" class="okb-dogovor conditionality">
                        <div class="okb-dogovor-yellow">
                            <div class="okb-dogovor-top">
                                Открыть условия договора <a href="#" class="okb-dogovor-new-win" onclick="javascript:openPopupCreditReportContractTerms();return false;">в новом окне</a>
                            </div>
                            <div class="okb-dogovor-text accountOpenText">
                                <div id="contractText" name="contractText" readonly="true">Договор об оказании информационных услуг (предоставлении кредитного отчета) г. Москва
        Настоящий документ представляет собой предложение – оферту Закрытого акционерного общества «Объединенное Кредитное Бюро», именуемого в дальнейшем «Бюро», физическому лицу, далее именуемому - «Клиент», совместно с Бюро именуемые «Стороны», а по отдельности «Сторона», заключить через агента - Открытое акционерное общество «Сбербанк России» (далее – «Агент»), действующего от имени и по поручению Бюро, посредством использования Системы Агента договор об оказании информационных услуг (далее «Договор») на изложенных ниже условиях.
        1. Порядок заключения Договора
        1.1. Клиент осведомлен и согласен с тем, что Договор считается заключенным в случае совершения Клиентом в совокупности следующих действий:
        - подтверждения ознакомления и согласия с условиями Договора путем проставления отметки в поле «Я согласен с условиями договора на оказание услуг по предоставлению кредитного отчета» и ввода одноразового пароля,
        - оплаты Услуги после подтверждения ознакомления с условиями Договора.
        1.2. Подтверждение ознакомления и согласия с условиями Договора путем ввода одноразового пароля в Системе и оплата Услуги Бюро означают полное и безоговорочное принятие Клиентом всех условий Договора без каких-либо изъятий и/или ограничений и равносилен заключению двухстороннего письменного договора. Электронные документы, подтвержденные вводом одноразового пароля, признаются Сторонами равнозначными документам на бумажном носителе и могут служить доказательством в суде.
        2. Определения
        Термины, используемые в настоящем Договоре, имеют следующее значение:
        - «Закон» - Федеральный Закон № 218-ФЗ «О кредитных историях» от 30 декабря 2004 года, со всеми изменениями и дополнениями;
        - «Кредитная история» - информация, состав которой определен Законом;
        - «Запрос» или «Запросы» - сформированный Клиентом с использованием Системы запрос о предоставлении его Кредитного отчета, выраженный в согласии с условиями Договора и оплате Услуги.
        «Кредитный отчет» - документ, который содержит информацию, входящую в состав Кредитной истории, хранящейся в Бюро, и который Бюро предоставляет по Запросу Клиента.
        «Система» - система дистанционного доступа Агента, предоставляемая Агентом Клиенту через глобальную информационно-телекоммуникационную сеть Интернет («Сбербанк ОнЛайн»).
        «Идентификация Клиента» - процесс подтверждения подлинности Клиента, осуществляемый Агентом в Системе по установленным в ней правилам.
        3. Предмет Договора
        3.1. В соответствии с условиями настоящего Договора Бюро обязуется по Запросу Клиента, при условии его Идентификации, предоставить Клиенту через Систему Кредитный отчет (далее «Услуга»), а Клиент обязуется оплатить Услугу в порядке, установленном Договором.
        3.2. Услуга оказывается в целях информирования Клиента лично об информации, содержащейся в базе Бюро, характеризующей исполнение Клиентом принятых на себя обязательств по договорам займа (кредита).
        4. Порядок предоставления Кредитного отчета
        4.1. Кредитный отчет предоставляется Клиенту в срок не позднее трех рабочих дней с момента оплаты Услуги путем отображения Кредитного отчета в личном кабинете Клиента в Системе. Кредитный отчет доступен для печати и сохранения. При направлении нового Запроса предоставление Кредитного отчета осуществляется путем обновления информации, содержащейся в раннее предоставленном Клиенту Кредитном отчете в Системе. В таком случае ранее предоставленный кредитный отчет недоступен.
        4.2. Кредитный отчет считается предоставленным Клиенту с момента отображения Кредитного отчета в личном кабинете Клиента в Системе.
        4.3. Кредитный отчет, предоставляемый в рамках настоящего Договора, содержит следующую информацию, входящую в состав Кредитной истории:
        - персональные данные субъекта кредитной истории (за исключением маскированных данных титульной части Кредитного отчета),
        - о действующих и закрытых кредитных договорах,
        - о запросах
        - об истории просрочек в исполнении обязательств,
        - об источниках кредитной истории.
        5. Стоимость и оплата Услуг
        5.1. За Услуги, оказываемые Бюро Клиенту по настоящему Договору, Клиент уплачивает Бюро вознаграждение в размере 420 (четырехсот двадцати) рублей (включая НДС).
        5.2. Оплата Услуг Бюро осуществляется Клиентом в Системе с выбранного Клиентом счета.
        6. Ответственность
        6.1. Клиент понимает и согласен с тем, что содержащаяся в Кредитном отчете информация получена Бюро от источников формирования кредитных историй в соответствии с Законом. Бюро не обязано проверять достоверность информации, предоставленной ему источниками формирования кредитных историй, и не несет ответственности за ее неактуальность или недостоверность. Исходя из вышеизложенного, Клиент не имеет права на возмещение ему Бюро возможных убытков, возникших в связи с недостоверностью или неточностью информации, содержащейся в Кредитных отчетах Бюро, в случае ее соответствия кредитной истории, содержащейся в базе Бюро.
        6.2. Бюро не несет ответственности за возможные убытки Клиента, возникшие в связи с невозможностью использования Кредитного отчета по иному назначению, кроме как указанному в пункте 3.2. настоящего Договора.
        7. Заключительные положения
        7.1. В случае невозможности использования Услуги в связи со сбоями, перерывами и ошибками в работе Системы, а также по другим вопросам оказания Услуги Клиенту будет обращаться в Контактный центр по телефону - 8 (800) 555 55 50 (круглосуточно).
        7.2. Договор вступает в силу с момента совершения Клиентом действий, указанных в п. 1.1. Договора, и действует до полного исполнения Сторонами обязательств по Договору.

        Полное наименование, адрес и реквизиты Бюро:
        Закрытое акционерное общество «Объединенное Кредитное Бюро»
        127006, г. Москва, ул. 1-ая Тверская-Ямская, д. 2, стр. 1
        ОГРН №1047796788819
        ИНН 7710561081
        р/с №40702810938180003245 в ОАО «Сбербанк России» Московский банк, к/с №30101810400000000225 в ОПЕРУ Московского ГТУ Банка России, БИК 044525225
                                </div>
                            </div>
                            <div id="agreeForAllRow">
                                <input id="agreeWithContract" name="agreeWithContract" class="agreeChbx agreeWithContract" type="checkbox">
                                    <xsl:if test="state!='INITIAL' and state!='DRAFT' and state!='SAVED'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label for="agreeWithContract">&nbsp;Я согласен с договором на оказание услуг по предоставлению кредитного отчета<span class="asterisk">*</span></label>
                            </div>
                            <div id="agreeForAllRow">
                                <input id="agreeWithConditions" name="agreeWithConditions" class="agreeChbx agreeWithConditions" type="checkbox">
                                    <xsl:if test="state!='INITIAL' and state!='DRAFT' and state!='SAVED'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label for="agreeWithConditions">&nbsp;Я согласен с <a href ="#" onclick="javascript:openPopupCreditReportConditions();return false;"> условиями</a> передачи и обработки информации из кредитного отчета<span class="asterisk">*</span></label>
                            </div>
                        </div>
                    </div>

                </xsl:with-param>
            </xsl:call-template>
        </div>

        <xsl:if test="$isTemplate != 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowStyle">display:none</xsl:with-param>
                <xsl:with-param name="rowName">Плановая дата исполнения</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="admissionDate"/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>

        <xsl:if test="string-length(promoCode)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Промо-код</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="promoCode">
                        <xsl:value-of select="promoCode"/>
                        <a class="imgHintBlock" title="Часто задаваемые вопросы" onclick="javascript:openFAQ('/PhizIC/faq.do#r2');"></a>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
	</xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='SEND'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
			<xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")<xsl:if test="checkStatusCountResult = 'true'"> (Превышение количества проверок статуса)</xsl:if></xsl:when>
            <xsl:when test="$code='PARTLY_EXECUTED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if>")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
            <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='BILLING_CONFIRM_TIMEOUT'">Таймаут при подтверждении в биллинге (ЕРИБ) (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='BILLING_GATE_CONFIRM_TIMEOUT'">Таймаут при подтверждении в биллинге (шлюз) (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='ABS_RECALL_TIMEOUT'">Таймаут при отзыве в АБС (ЕРИБ) (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='ABS_GATE_RECALL_TIMEOUT'">Таймаут при отзыве в АБС (шлюз) (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='OFFLINE_SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='SEND'">Исполняется банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
			<xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
			<xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='PARTLY_EXECUTED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if></xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
            <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
            <xsl:when test="$code='BILLING_CONFIRM_TIMEOUT'">Исполняется банком</xsl:when>
            <xsl:when test="$code='BILLING_GATE_CONFIRM_TIMEOUT'">Исполняется банком</xsl:when>
            <xsl:when test="$code='ABS_RECALL_TIMEOUT'">Исполняется банком</xsl:when>
            <xsl:when test="$code='ABS_GATE_RECALL_TIMEOUT'">Исполняется банком</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='OFFLINE_SAVED'">Черновик</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="tokenizeString">
        <xsl:param name="string"/>

        <xsl:if test="string-length($string) > 0">
            <xsl:variable name="before-separator" select="substring-before($string, '&#10;')"/>
            <xsl:variable name="after-separator" select="substring-after($string, '&#10;')"/>

            <xsl:choose>
                <xsl:when test="string-length($before-separator)=0 and string-length($after-separator)=0">
                    <xsl:value-of select="$string"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="string-length($before-separator)!=0">
                        <xsl:value-of select="$before-separator"/><br/>
                    </xsl:if>
                    <xsl:call-template name="tokenizeString">
                        <xsl:with-param name="string" select="$after-separator"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>
    </xsl:template>


    <!-- шаблон формирующий div описания -->
<xsl:template name="buildDescription">
   <xsl:param name="text"/>
   <xsl:param name="additionalHelpData"/>

   <xsl:variable name="delimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее.</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="firstDelimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Как заполнить это поле?</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="end">
   <![CDATA[ </div>
   ]]>
   </xsl:variable>

   <div class="description" style="display: none">

        <xsl:variable name="nodeText" select="xalan:nodeset($text)"/>

        <xsl:for-each select="$nodeText/node()">
            <xsl:choose>
                <xsl:when test=" name() = 'cut' and position() = 1 ">
                    <xsl:value-of select="$firstDelimiter" disable-output-escaping="yes"/>
                </xsl:when>
                <xsl:when test="name() = 'cut' and position() != 1">
                    <xsl:value-of select="$delimiter" disable-output-escaping="yes"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:copy-of select="."/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>

       <xsl:if test="count($nodeText/cut) > 0">
            <xsl:value-of select="$end" disable-output-escaping="yes"/>
       </xsl:if>
       <xsl:copy-of select="$additionalHelpData"/>
	</div>

</xsl:template>

<xsl:template name="standartRow">

	<xsl:param name="id"/>
    <xsl:param name="lineId"/>                      <!--идентификатор строки-->
	<xsl:param name="required" select="'true'"/>    <!--параметр обязатьльности заполнения-->
	<xsl:param name="rowName"/>                     <!--описание поля-->
	<xsl:param name="rowValue"/>                    <!--данные-->
	<xsl:param name="description"/>                	<!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
	<xsl:param name="additionalHelpData"/>          <!-- дополнительная информацмя в подсказке -->
	<!-- Необязательный параметр -->
	<xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
    <xsl:param name="rowStyle"/>                    <!-- Стил поля -->
    <xsl:param name="additionStyle"/>               <!-- доп. стиль iTunes -->
    <xsl:param name="edit"/>
    <xsl:param name="popupHint"/>

	<xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
	<!-- Определение имени инпута или селекта передаваемого в шаблон -->
	<!-- inputName - fieldName или имя поле вытащеное из rowValue -->
	<xsl:variable name="inputName">
	<xsl:choose>
		<xsl:when test="string-length($fieldName) = 0">
				<xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
					<xsl:value-of select="$nodeRowValue/input/@name" />
					<xsl:if test="count($nodeRowValue/select[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/select/@name" />
					</xsl:if>
                    <xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/textarea/@name" />
					</xsl:if>
				</xsl:if>
		</xsl:when>
		<xsl:otherwise>
				<xsl:copy-of select="$fieldName"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>

    <xsl:variable name="readonly">
		<xsl:choose>
            <xsl:when test="$edit = 'false'">
                1
            </xsl:when>
			<xsl:when test="string-length($inputName)>0">
				<xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
			</xsl:when>
			<xsl:otherwise>
				0
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

<!--  Поиск ошибки по имени поля
В данной реализации ошибки обрабатывает javascript
                <xsl:if test="$mode = 'edit'">
                    <xsl:if test="boolean($validationErrors/entity[@key=$fieldName])">
                        <xsl:copy-of select="$validationErrors/entity[@key=$fieldName]"/>
                    </xsl:if>
                </xsl:if>
-->

<div class="{$styleClass}  {$additionStyle}">
    <xsl:if test="string-length($lineId) > 0">
        <xsl:attribute name="id"><xsl:copy-of select="$lineId"/>Row</xsl:attribute>
    </xsl:if>
    <xsl:if test="string-length($rowStyle) > 0">
        <xsl:attribute name="style">
            <xsl:copy-of select="$rowStyle"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="$readonly = 0 and $mode = 'edit'">
        <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
    </xsl:if>

	<div class="paymentLabel"><span class="paymentTextLabel"><xsl:copy-of select="$rowName"/></span>
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk">*</span>
		</xsl:if>
    </div>
	<div class="paymentValue">
        <div class="paymentInputDiv" >
            <xsl:if test="string-length($lineId) > 0">
                <xsl:attribute name="id"><xsl:copy-of select="$lineId"/>InputDiv</xsl:attribute>
                <xsl:if test="not($popupHint = '')">
                    <xsl:variable name="popupHintId" select="concat($lineId, 'Description')"/>
                    <xsl:attribute name="onmouseover">showLayer('<xsl:value-of select="concat($lineId, 'InputDiv')"/>','<xsl:value-of select="$popupHintId"/>','default');</xsl:attribute>
                    <xsl:attribute name="onmouseout">hideLayer('<xsl:value-of select="$popupHintId"/>');</xsl:attribute>
                </xsl:if>
            </xsl:if>

            <xsl:copy-of select="$rowValue"/>
        </div>

        <xsl:if test="$readonly = 0 and $mode = 'edit'">
            <xsl:call-template name="buildDescription">
                <xsl:with-param name="text" select="$description"/>
                <xsl:with-param name="additionalHelpData" select="$additionalHelpData"/>
            </xsl:call-template>
        </xsl:if>
        <div class="errorDiv" style="display: none;">
        </div>

        <xsl:if test="string-length($lineId) > 0 and not($popupHint = '')">
            <xsl:variable name="popupHintId" select="concat($lineId, 'Description')"/>
            <div id="{$popupHintId}" class="layerFon stateDescription">
                <xsl:attribute name="onmouseover">showLayer('<xsl:value-of select="concat($lineId, 'InputDiv')"/>','<xsl:value-of select="$popupHintId"/>','default');</xsl:attribute>
                <xsl:attribute name="onmouseout">hideLayer('<xsl:value-of select="$popupHintId"/>');</xsl:attribute>
                <div class="floatMessageHeader"></div>
                <div class="layerFonBlock"><xsl:value-of select="$popupHint"/></div>
            </div>
        </xsl:if>
	</div>
    <div class="clear"></div>
</div>

<!-- Устанавливаем события onfocus поля -->
	<xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
		<script type="text/javascript">
		if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
		document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
		</script>
	</xsl:if>

</xsl:template>

<xsl:template name="simpleLink">
    <xsl:param name="name"/>
    <xsl:param name="id"/>
    <xsl:param name="url"/>
    <xsl:param name="text"/>
    <div class="form-row">
        <div class="paymentLabel">
            <span id="{$id}"  name="{$name}" class="paymentTextLabel"><xsl:copy-of select="$text"/>:</span>
        </div>
        <xsl:variable name="val">
            <xsl:copy-of select="substring-after($url,'|')"/>
        </xsl:variable>
        <div class="paymentValue">
            <div class="paymentInputDiv">
                <b><span class="link" onclick="openExternalLink('{$val}'); return false;"><xsl:copy-of select="substring-before($url,'|')"/></span></b>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</xsl:template>

<xsl:template name="regionRowValue">
    <xsl:param name="providerId"/>
    <xsl:param name="personRegionName"/>

    <xsl:variable name="regionsNode" select="spu:getProviderRegions($providerId)"/>
    <xsl:variable name="notError" select="spu:allowedAnyRegions($providerId)"/>
    <xsl:variable name="isAllRegions" select="not($regionsNode/entity-list/*)"/>
    <xsl:variable name="regions">
        <xsl:for-each select="$regionsNode/entity-list/*"><xsl:value-of select="./field[@name = 'name']/text()"/>|</xsl:for-each>
    </xsl:variable>

    <div id="region" name="regions">
        <b>
        <xsl:choose>
            <xsl:when test="$isAllRegions">
                Поставщик доступен во всех регионах.
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="$notError and $personRegionName!=''">
                        <xsl:choose>
                        <xsl:when test="string-length($regions) > 0 and substring-before($regions, '|')!='' and substring-after($regions, '|')!=''">
                            <div onclick="hideOrShow('otherRegions')">
                                <xsl:value-of select="$personRegionName"/>,..
                            </div>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$personRegionName"/>
                        </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:choose>
                        <xsl:when test="string-length($regions) > 0 and substring-before($regions, '|')!='' and substring-after($regions, '|')!=''">
                            <div onclick="hideOrShow('otherRegions')">
                                <xsl:value-of select="substring-before($regions, '|')"/>,...
                            </div>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="substring-before($regions, '|')"/>
                        </xsl:otherwise>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
        </b>
    </div>
    <xsl:if test="string-length($regions) > 0 and substring-before($regions, '|')!='' and substring-after($regions, '|')!=''">
         <div id="otherRegions" style="display: none;" class="otherRegions">
            <div class="otherRegionsHeader"></div>
            <div class="otherRegionsBlock">
                <span class="otherRegionsTitle">Этот получатель предоставляет свои услуги также в: </span>
                <span class="otherRegionsText" id="otherRegionsText">
                    <xsl:choose>
                        <xsl:when test="$notError and $personRegionName!=''">
                            <xsl:variable name="first" select="$personRegionName"/>
                            <xsl:for-each select="xalan:tokenize($regions, '|')">
                                <xsl:if test="current()!=$first">
                                    <xsl:if test="position()>2">, </xsl:if>
                                    <a><xsl:value-of select="current()"/></a>
                                </xsl:if>
                            </xsl:for-each>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:variable name="first" select="substring-before($regions, '|')"/>
                            <xsl:for-each select="xalan:tokenize($regions, '|')">
                                <xsl:if test="current()!=$first">
                                    <xsl:if test="position()>2">, </xsl:if>
                                    <a><xsl:value-of select="current()"/></a>
                                </xsl:if>
                            </xsl:for-each>
                        </xsl:otherwise>
                    </xsl:choose>
                </span>
                <div class="closeOtherRegions"><img src="{$resourceRoot}/images/colorClose.gif" alt="Закрыть" title="Закрыть" border="0" onclick="hideOrShow('otherRegions')"/></div>
            </div>
        </div>
     </xsl:if>
     <script type="text/javascript">
       doOnLoad(
         function(){
            if (<xsl:value-of select="not($notError or $isAllRegions or string-length($personRegionName)=0)"/>)
               payInput.fieldError("regions", "Получатель зарегистрирован в другом регионе оплаты.");
        });

        <xsl:if test="string-length($providerId) &gt; 0">

            <!--регистрируем слушателя изменения региона в шапке-->
            $(document).ready(function()
            {
                var region = getRegionSelector('regionsDiv');
                if (!$.isEmptyObject(region))
                    region.addListener(function() {checkProvidersRegion([<xsl:value-of select="$providerId"/>], updateAccessProviderRegion);});
            });

            function updateAccessProviderRegion(map)
            {
                if(map[<xsl:value-of select="$providerId"/>])
                    payInput.fieldClearError("regions");
                else
                    payInput.fieldError("regions", "Получатель зарегистрирован в другом регионе оплаты.");
            }  ;

        </xsl:if>
     </script>
</xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <xsl:param name="rowValue"/>
        <div>
            <xsl:if test="string-length($lineId) > 0">
                <xsl:attribute name="id">
                    <xsl:copy-of select="$lineId"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:copy-of select="$rowName"/>&nbsp;<xsl:copy-of select="$rowValue"/>
        </div>
    </xsl:template>

    <!-- Поле принятия соглашения -->
    <xsl:template name="choiceField">
        <xsl:param name="text"/>
        <xsl:param name="readonly"/>
        <xsl:param name="extendedDescId"/>
        <xsl:param name="isChecked"/>
        <xsl:param name="id"/>
        <div class="form-row">
            <div class="paymentLabel">&nbsp;</div>
            <div class="paymentValue">
                <div class="paymentInputDiv" >
                    <input type="checkbox" name="{$id}" value="true">
                        <xsl:if test="$readonly">
                            <xsl:attribute name="disabled">true</xsl:attribute>
                        </xsl:if>
                        <xsl:if test="$isChecked">
                             <xsl:attribute name="checked">true</xsl:attribute>
                        </xsl:if>
                    </input>&nbsp;
                    <xsl:choose>
                        <xsl:when test="string-length($extendedDescId) > 0">
                            <xsl:value-of select="substring-before($text, '[url]')"/>
                            <xsl:variable name="after-separator" select="substring-after($text, '[url]')"/>
                            <a href="#" onclick="openExtendedDescWin('{$extendedDescId}'); return false;">
                                <xsl:value-of select="substring-before($after-separator, '[/url]')"/>
                            </a>
                            <xsl:value-of select="substring-after($after-separator, '[/url]')"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$text"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </div>
                <div class="errorDiv" style="display: none;"/>
            </div>
            <div class="clear"/>
        </div>
    </xsl:template>

    <!-- Перечисление графических представлений set'ов -->
    <xsl:template name="graphicSetField">
        <xsl:param name="id"/>
        <xsl:param name="name"/>
        <xsl:param name="params"/>
        <xsl:param name="readonly"/>
        <xsl:param name="initvalue"/>
        <script type="text/javascript" src="{$resourceRoot}/scripts/graphicPaymentFields.js"></script>
        <xsl:choose>
            <xsl:when test="$name = 'aeroexpress-car'">
                <xsl:call-template name="aeroexpress-car">
                    <xsl:with-param name="id" select="concat('aeroexpressCar', $id)"/>
                    <xsl:with-param name="params" select="$params"/>
                    <xsl:with-param name="editable" select="not($readonly)"/>
                    <xsl:with-param name="initvalue" select="$initvalue"/>
                </xsl:call-template>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="aeroexpress-car">
        <xsl:param name="id"/>
        <xsl:param name="params"/>
        <xsl:param name="editable"/>
        <xsl:param name="initvalue"/>
        <!-- вырезаем из параметров настройки мест  -->
        <xsl:variable name="placesXml">
            <xsl:for-each select="$params/fields/field[@key = 'place']">
                <seat key="{substring-before(./text(), ';')}" status="{substring-after(./text(), ';')}"/>
            </xsl:for-each>
        </xsl:variable>
        <xsl:variable name="places" select="xalan:nodeset($placesXml)"/>
        <xsl:variable name="maxCount" select="$params/fields/field[@key = 'maxCount']/text()"/>

        <div class="aeroexpress-car" id="{$id}">
            <div class="coach">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td class="carEnter"></td>
					    <td class="carInner">
                            <table cellpadding="0" cellspacing="0" border="0">
							    <tr>
                                    <xsl:for-each select="$places/seat[position() mod 4 = 1]">
                                        <xsl:variable name="col" select="position()"/>
                                        <td>
                                            <xsl:for-each select="$places/seat[(./@key > ($col - 1)*4) and (./@key &lt;= $col*4)]">
                                                <xsl:variable name="placeId" select="./@key"/>
                                                <xsl:variable name="stateClass">
                                                    <xsl:choose>
                                                        <xsl:when test="./@status = 'F'">free</xsl:when>
                                                        <xsl:when test="./@status = 'R'">reserved</xsl:when>
                                                        <xsl:when test="./@status = 'S'">sold</xsl:when>
                                                    </xsl:choose>
                                                </xsl:variable>
                                                <span id="place{$placeId}" class="{$stateClass}">
                                                    <xsl:value-of select="$placeId"/>
                                                </span>
                                                <xsl:if test="position() = 2 or position() = 4">
                                                    <i class="aisle"/>
                                                </xsl:if>
                                            </xsl:for-each>
                                        </td>
                                    </xsl:for-each>
                                </tr>
                            </table>
                            <ul class="status">
                                <li class="statusFree">Свободно (<xsl:value-of select="count($places/seat[@status = 'F'])"/>)</li>
                                <li class="statusReserved">Забронировано (<xsl:value-of select="count($places/seat[@status = 'R'])"/>)</li>
                                <li class="statusSold">Продано (<xsl:value-of select="count($places/seat[@status = 'S'])"/>)</li>
                            </ul>
                        </td>
                        <td class="carHeadEnter"></td>
                    </tr>
                </table>
            </div>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Выбраны места</xsl:with-param>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <b id="selectedVisiblePlaces"/>
                    <input type="hidden" name="aeroexpress-choice-place" id="selectedHiddenPlaces" value="{$initvalue}"/>
                </xsl:with-param>
            </xsl:call-template>
            <script type="text/javascript">
                $(document).ready(function(){
                    $(".aeroexpress-car").filter("#<xsl:value-of select='$id'/>").each(function(){
                        var item = new AeroexpressCar(this, <xsl:value-of select="$editable"/>, <xsl:value-of select="$maxCount"/>);
                        item.initialize();
                    });
                })
            </script>
        </div>
    </xsl:template>

</xsl:stylesheet>
