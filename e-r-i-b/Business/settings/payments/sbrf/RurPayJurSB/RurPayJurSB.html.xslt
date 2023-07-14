<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:pu="java://com.rssl.phizic.web.util.PersonInfoUtil"
                xmlns:spu="java://com.rssl.phizic.web.util.ServiceProviderUtil"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
                xmlns:su="java://com.rssl.phizic.utils.StringUtils"
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
    <xsl:param name="longOffer" select="false()"/>
    <xsl:param name="documentState" select="''"/>
    <xsl:param name="isTemplate" select="'isTemplate'"/>
    <xsl:param name="byTemplate" select="'byTemplate'"/>
    <xsl:param name="isDefaultShow" select="true()"/>
    <xsl:param name="skinUrl" select="'skinUrl'"/>
    <xsl:param name="isITunes" select="false()"/>
	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
    <xsl:key name="subService" match="Attribute[./Type != 'calendar']" use="GroupName"/>
	<xsl:variable name="formData" select="/form-data"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleClass" select="'form-row'"/>
	<xsl:variable name="styleSpecial" select="''"/>
    <xsl:variable name="isInitialLongOfferState" select="$documentState = 'INITIAL_LONG_OFFER'"/>

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
	</script>
		<xsl:choose>
			<xsl:when test="$mode = 'edit' and not($isInitialLongOfferState)">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
            <xsl:when test="$mode = 'edit' and $isInitialLongOfferState">
                <xsl:apply-templates mode="view"/>
                <xsl:apply-templates mode="edit-auto-sub-info"/>
            </xsl:when>

			<xsl:when test="$mode = 'view'  and not($longOffer)">
				<xsl:apply-templates mode="view"/>
			</xsl:when>

            <xsl:when test="$mode = 'view' and $longOffer">
                <xsl:apply-templates mode="view"/>
                <xsl:apply-templates mode="view-auto-sub-info"/>
            </xsl:when>
		</xsl:choose>
	</xsl:template>


    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="linkId"/>
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeCards"/>

        <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
        <xsl:variable name="accountNode" select="$activeAccounts/entity[$linkId=./field[@name='code']/text() or $fromAccountSelect/text() = ./@key]"/>
        <xsl:if test="$accountNode">
            <input type="hidden" value="{$accountNode/field[@name='code']/text()}" name="{$name}" id="{$name}"/>
            <xsl:value-of select="au:getFormattedAccountNumber($accountNode/@key)"/>&nbsp;
            [<xsl:value-of select="$accountNode/field[@name='name']"/>]
            <xsl:if test="$accountNode/field[@name='amountDecimal'] != ''">
                <xsl:value-of select="format-number($accountNode/field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
            </xsl:if>
            &nbsp;
            <xsl:value-of select="mu:getCurrencySign($accountNode/field[@name='currencyCode'])"/>
        </xsl:if>

        <xsl:variable name="cardNode" select="$activeCards/entity[$linkId=./field[@name='code'] or $fromAccountSelect/text() = ./@key]"/>
        <xsl:if test="$cardNode">
            <input type="hidden" value="{$cardNode/field[@name='code']/text()}" name="{$name}" id="{$name}"/>
            <xsl:value-of select="mask:getCutCardNumber($cardNode/@key)"/>&nbsp;
            [<xsl:value-of select="$cardNode/field[@name='name']"/>]
            <xsl:if test="$cardNode/field[@name='amountDecimal'] != ''">
                <xsl:value-of select="format-number($cardNode/field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
            </xsl:if>
            &nbsp;
            <xsl:value-of select="mu:getCurrencySign($cardNode/field[@name='currencyCode'])"/>
        </xsl:if>
    </xsl:template>

	<xsl:template match="/form-data" mode="edit">
        <xsl:variable name="activeAccounts" select="document('stored-active-debit-rur-allowed-external-jur-accounts-all-visible.xml')/entity-list"/>
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-cards-all-visible.xml')/entity-list"/>

		<script type="text/javascript">
            document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
        </script>
        <input name="bankDetails" type="hidden" value="{bankDetails}"/>
        <input name="recipient" type="hidden" value="{recipient}"/>
        <xsl:variable name="bankDetails" select="bankDetails"/>

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

        <xsl:if test="not ($isITunes)">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName">&nbsp;<b>Получатель</b></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(receiverDescription)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Информация по получателю средств</xsl:with-param>
                <!--<xsl:with-param name="required">false</xsl:with-param>-->
                <xsl:with-param name="edit">false</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <input name="receiverDescription" type="hidden" value="{receiverDescription}"/>
                        <p align="justify">
                            <xsl:call-template name="tokenizeString">
                                <xsl:with-param name="string" select="receiverDescription"/>
                            </xsl:call-template>
                        </p>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(receiverName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="receiverName" value="{receiverName}"/>
                    <b><xsl:value-of select="receiverName"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(nameService)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Услуга</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="nameService" value="{nameService}"/>
                    <b><xsl:value-of select="nameService"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
             <xsl:with-param name="rowName">Регион оплаты</xsl:with-param>
             <xsl:with-param name="rowValue">
                <xsl:variable name="personRegionName" select="pu:getPersonRegionName()"/>
                <input type="hidden" name="personRegionName" value="{$personRegionName}"/>
                <xsl:call-template name="regionRowValue">
                    <xsl:with-param name="providerId" select="recipient"/>
                    <xsl:with-param name="personRegionName" select="$personRegionName"/>
                    <xsl:with-param name="isLongOffer" select="$longOffer"/>
                </xsl:call-template>
            </xsl:with-param>
         </xsl:call-template>


        <xsl:if test="$bankDetails = 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">ИНН</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="receiverINN" value="{receiverINN}"/>
                    <b><xsl:value-of select="receiverINN"/></b>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Счет</xsl:with-param>
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
                <xsl:with-param name="rowName">Наименование</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="receiverBankName" value="{receiverBankName}"/>
                    <b><xsl:value-of select="receiverBankName"/></b>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">БИК</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="receiverBIC" value="{receiverBIC}"/>
                    <b><xsl:value-of select="receiverBIC"/></b>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:if test="string-length(receiverCorAccount)>0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="rowName">Корсчет</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="hidden" size="35" name="receiverCorAccount" value="{receiverCorAccount}"/>
                        <b><xsl:value-of select="receiverCorAccount"/></b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:if>

        <xsl:if test="$longOffer">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName">&nbsp;<b>Плательщик</b></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

		<xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">fromResource</xsl:with-param>
            <xsl:with-param name="rowName">Списать со счета</xsl:with-param>
            <xsl:with-param name="rowValue"><b>
                <xsl:if test="$personAvailable">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="linkId" select="fromResource"/>
                        <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                        <xsl:with-param name="activeCards" select="$activeCards"/>
                    </xsl:call-template>
                </xsl:if>
                <xsl:if test="not($personAvailable)">Счет клиента</xsl:if></b>
            </xsl:with-param>
		</xsl:call-template>

        <xsl:if test="not ($isITunes)">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName">&nbsp;<b>Детали платежа</b></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

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

        <xsl:if test="not ($isITunes)">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName">&nbsp;<b>Получатель</b></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(receiverName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverName"/></b>
                    <input name="receiverName" type="hidden" value="{receiverName}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(nameService)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Услуга</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="nameService"/></b>
                    <input name="nameService" type="hidden" value="{nameService}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="$longOffer">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Регион оплаты</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="personRegionName" value="{personRegionName}"/>
                    <xsl:call-template name="regionRowValue">
                        <xsl:with-param name="providerId" select="recipient"/>
                        <xsl:with-param name="personRegionName" select="personRegionName"/>
                        <xsl:with-param name="isLongOffer" select="true()"/>
                    </xsl:call-template>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="$bankDetails = 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">ИНН</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverINN"/></b>
                    <input name="receiverINN" type="hidden" value="{receiverINN}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Счет</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverAccount"/></b>
                    <input name="receiverAccount" type="hidden" value="{receiverAccount}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName">&nbsp;<b>Банк получателя</b></xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Наименование</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverBankName"/></b>
                    <input name="receiverBankName" type="hidden" value="{receiverBankName}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">БИК</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverBIC"/></b>
                    <input name="receiverBIC" type="hidden" value="{receiverBIC}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:if test="string-length(receiverCorAccount)>0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="rowName">Корсчет</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="receiverCorAccount"/></b>
                        <input name="receiverCorAccount" type="hidden" value="{receiverCorAccount}"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
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
                            <xsl:variable name="activeCards" select="document('stored-active-not-virtual-cards-all-visible.xml')/entity-list"/>
                            <xsl:variable name="cardNode" select="$activeCards/entity[$fromResourceLink=./field[@name='code']]"/>
                            <xsl:if test="$cardNode">
                                <xsl:value-of select="$cardNode/@key"/>
                            </xsl:if>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:variable name="activeAccounts" select="document('stored-active-debit-rur-allowed-external-jur-accounts-all-visible.xml')/entity-list"/>
                            <xsl:variable name="accountNode" select="$activeAccounts/entity[$fromResourceLink=./field[@name='code']]"/>
                            <xsl:if test="$accountNode">
                                <xsl:value-of select="$accountNode/@key"/>
                            </xsl:if>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:if test="$longOffer">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName">&nbsp;<b>Плательщик</b></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
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
                        <xsl:if test="$isInitialLongOfferState">
                            <input name="fromResource" type="hidden" value="{fromResourceLink}"/>
                            <input name="fromResourceLink" type="hidden" value="{fromResourceLink}"/>
                            <input name="fromAccountName" type="hidden" value="{fromAccountName}"/>
                            <input name="fromResourceCurrency" type="hidden" value="{fromResourceCurrency}"/>
                        </xsl:if>
                    </b>
                </xsl:if>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:if test="not($longOffer or $isTemplate = 'true')">
            <xsl:choose>
                <xsl:when test="string-length(commission)>0">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="rowName">Комиссия</xsl:with-param>
                        <xsl:with-param name="rowValue">
                                <b><xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/></b>
                                <input name="commission" type="hidden" value="{commission}"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:when>
            </xsl:choose>
        </xsl:if>

        <xsl:if test="not ($isITunes)">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName">&nbsp;<b>Детали платежа</b></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

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
                        <xsl:if test="not($isHideInConfirmation and not($isInitialLongOfferState)) and not($hidden) and not($type='calendar')">
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
                                            <xsl:choose>
                                                <xsl:when test="$isITunes and $id = 'Summ'">rowITunes</xsl:when>
                                                <xsl:otherwise></xsl:otherwise>
                                            </xsl:choose>
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

        <xsl:if test="not($longOffer)">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">
                    Статус
                    <xsl:choose>
                        <xsl:when test="$isTemplate != 'true'">
                            платежа
                        </xsl:when>
                        <xsl:otherwise>
                            шаблона
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
        </xsl:if>

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
            <xsl:when test="$code='SENT'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
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
			<xsl:when test="$code='DISPATCHED' and $longOffer">Исполняется банком</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='SEND'">Исполняется банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
			<xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
			<xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='PARTLY_EXECUTED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
            <xsl:when test="$code='SENT'">Исполняется банком</xsl:when>
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

	<div class="paymentLabel"><span class="paymentTextLabel"><xsl:copy-of select="$rowName"/>:</span>
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
    <xsl:param name="isLongOffer"/>

    <xsl:variable name="regionsNode" select="spu:getProviderRegions($providerId)"/>
    <xsl:variable name="notError" select="spu:allowedAnyRegions($providerId)"/>
    <xsl:variable name="isAllRegions" select="not($regionsNode/entity-list/*)"/>
    <xsl:variable name="regions">
        <xsl:for-each select="$regionsNode/entity-list/*"><xsl:value-of select="./field[@name = 'name']/text()"/>|</xsl:for-each>
    </xsl:variable>

    <div id="region" name="regions">
        <b>
        <xsl:choose>
            <xsl:when test="$isLongOffer">
                <xsl:choose>
                    <xsl:when test="$personRegionName!=''">
                        <xsl:choose>
                            <xsl:when test="$notError or $isAllRegions">
                                <xsl:value-of select="$personRegionName"/>
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
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:choose>
                            <xsl:when test="$isAllRegions">
                                Поставщик доступен во всех регионах.
                            </xsl:when>
                            <xsl:otherwise>
                                Поставщик доступен в нескольких регионах.
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
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
            }
        </xsl:if>
     </script>
</xsl:template>

    <xsl:template match="/form-data" mode="edit-auto-sub-info">
        <xsl:variable name="autoSubTypes" select="document('auto-sub-payment-types.xml')/entity-list"/>
        <xsl:variable name="accessAutoSubTypes" select="document('supportedAutoPays.xml')/entity-list"/>
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Настройки автоплатежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="isAutoSubTypesEmpty" select="count($accessAutoSubTypes/entity) = 0"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">autoSubType</xsl:with-param>
            <xsl:with-param name="rowName">Тип</xsl:with-param>
            <xsl:with-param name="required">true</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="autoSubType" select="autoSubType"/>
                <select id="autoSubType" onchange="changeType()" name="autoSubType">
                    <xsl:choose>
                        <xsl:when test="$isAutoSubTypesEmpty">
                            <xsl:attribute name="disabled"/>
                            <option value="">Не  задан тип автоплатежа</option>
                            <script type="text/javascript">
                                $(document).ready(function() {
                                    payInput.fieldError("autoSubType", "В адрес данного поставщика услуг невозможно оформить автоплатеж.");
                                });
                            </script>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:for-each select="$accessAutoSubTypes/entity">
                                <xsl:variable name="key" select="./@key"/>
                                <xsl:if test="$autoSubTypes/entity[@key=$key]">
                                   <option value="{$key}">
                                       <xsl:if test="$key = $autoSubType">
                                           <xsl:attribute name="selected"/>
                                       </xsl:if>
                                       <xsl:value-of select="$autoSubTypes/entity[@key=$key]/@description"/>
                                   </option>
                                </xsl:if>
                            </xsl:for-each>
                        </xsl:otherwise>
                    </xsl:choose>
                </select>
            </xsl:with-param>
        </xsl:call-template>

       <input id="autoSubEventType" name="autoSubEventType" type="hidden">
            <xsl:attribute name="value">
                <xsl:value-of select="autoPaymentEventType"/>
            </xsl:attribute>
		</input>

        <div id="changeContent">
            <div id="ALWAYS">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">autoSubEventType</xsl:with-param>
                    <xsl:with-param name="rowName">Оплачивать</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:call-template name="autoSubTypeEvent">
                            <xsl:with-param name="events" select="$autoSubTypes/entity[@key='ALWAYS']"/>
                            <xsl:with-param name="name">autoSubEventType_ALWAYS</xsl:with-param>
                            <xsl:with-param name="value" select="autoSubEventType"/>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Дата ближайшего платежа</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="nextPayDateAlways">
                            <xsl:choose>
                                <xsl:when test="contains(nextPayDateAlways, '-')">
                                    <xsl:copy-of select="concat(substring(nextPayDateAlways, 9, 2), '.', substring(nextPayDateAlways, 6, 2), '.', substring(nextPayDateAlways, 1, 4))"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:copy-of select="nextPayDateAlways"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <input name="nextPayDateAlways" id="nextPayDateAlways" class="dot-date-pick" size="10" value="{$nextPayDateAlways}"
                        onchange="javascript:changeType(false);" onkeyup="javascript:changeType();"
                        />
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Сумма</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input name="alwaysAmount" id="alwaysAmount" size="10" value="{alwaysAmount}" class="moneyField"/> руб.
                    </xsl:with-param>
                </xsl:call-template>
            </div>

            <div id="INVOICE">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">autoSubEventType</xsl:with-param>
                    <xsl:with-param name="rowName">Оплачивать</xsl:with-param>
                    <xsl:with-param name="description">Оплата счета произойдет в случае его наличия в указанную дату.</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:call-template name="autoSubTypeEvent">
                            <xsl:with-param name="events" select="$autoSubTypes/entity[@key='INVOICE']"/>
                            <xsl:with-param name="name">autoSubEventType_INVOICE</xsl:with-param>
                            <xsl:with-param name="value" select="autoSubEventType"/>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="description">За сутки до указанной Вами даты при наличии выставленных счетов Вам будет направлено SMS с информацией и возможностью отказа от платежа.<cut/>Начиная с указанной даты раз в три дня будет осуществляться проверка наличия выставленного счета. Оплата счета произойдет по факту его выставления.</xsl:with-param>
                    <xsl:with-param name="rowName">Ожидаемая дата оплаты счета</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="nextPayDateInvoice">
                            <xsl:choose>
                                <xsl:when test="contains(nextPayDateInvoice, '-')">
                                    <xsl:copy-of select="concat(substring(nextPayDateInvoice, 9, 2), '.', substring(nextPayDateInvoice, 6, 2), '.', substring(nextPayDateInvoice, 1, 4))"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:copy-of select="nextPayDateInvoice"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <input name="nextPayDateInvoice" id="nextPayDateInvoice" class="dot-date-pick" size="10" value="{$nextPayDateInvoice}"
                        onchange="javascript:changeType(false);" onkeyup="javascript:changeType();"
                        />
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Максимальный размер платежа</xsl:with-param>
                    <xsl:with-param name="description">Автоматическая оплата счета не произойдет, если сумма выставленного счета превышает указанную сумму.<cut/>Например, если в поле «Максимальная сума платежа» установлено значение «100 руб.», а сумма счета к оплате будет 103 руб., оплата не произойдет. </xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input name="invoiceMaxAmount" id="invoiceMaxAmount" size="10" value="{invoiceMaxAmount}" class="moneyField"/> руб.
                    </xsl:with-param>
                </xsl:call-template>
            </div>
        </div>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Название</xsl:with-param>
            <xsl:with-param name="required">true</xsl:with-param>
            <xsl:with-param name="description">
                Укажите название, которое будет отображаться в списке Ваших автоплатежей и в SMS-оповещениях по услуге. Например, <i>квартплата</i> или <i>интернет</i>.
            </xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="string-length(autoSubName)>0">
                        <input type="text" name="autoSubName" size="20" value="{autoSubName}"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:choose>
                            <xsl:when test="20>=string-length(receiverName)">
                                <input type="text" name="autoSubName" size="20" value="{su:removeNotLetter(nameService)}"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <input type="text" name="autoSubName" size="20" value=""/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

        <script type="text/javascript">

            <!-- setCurrentDateToInput("autoPaymentStartDate"); -->
            <!-- изменнение типа автоплатежа -->
            function changeType()
            {
                var event = ensureElement("autoSubType").value;

                $("#changeContent>div").each(function(index) {
                    hideOrShow(this, this.id != event);
                  });
                changeEventType();
                changeDescription(event);
            }

            function changeEventType()
            {
                var type = ensureElement('autoSubType').value;
                if(type == 'ALWAYS')
                    ensureElement('autoSubEventType').value=ensureElement('autoSubEventType_ALWAYS').value;
                if(type == 'INVOICE')
                    ensureElement('autoSubEventType').value=ensureElement('autoSubEventType_INVOICE').value;
            }
            function changeDescription(event)
            {
                var fieldDateName;
                if(event == 'ALWAYS')
                    fieldDateName = '«Дата ближайшего платежа»';
                if(event == 'INVOICE')
                    fieldDateName = '«Начать после»';
                $("#executeNowRow>div").each(function(index) {
                    if(this.className == 'paymentValue')
                    {
                        <![CDATA[
                        for(var i=0; i<this.children.length; i++)
                        {
                            if(this.children[i].className == 'description')
                            {
                                this.children[i].textContent = 'Если  Вы заполните данное поле, то автоплатеж исполнится в день его создания,  независимо от значения поля'+fieldDateName;
                                //IE 8+ не держит textContent
                                this.children[i].innerText = 'Если  Вы заполните данное поле, то автоплатеж исполнится в день его создания,  независимо от значения поля'+fieldDateName;
                                break;
                            }
                        }
                        ]]>
                    }
                  });
            }

            changeType(true);

        </script>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="not($isInitialLongOfferState)"/>
            <xsl:with-param name="rowName">Комиссия</xsl:with-param>
            <xsl:with-param name="rowValue"><b>
                <input name="commission" type="hidden" value="{commission}"/>
                <input name="isWithCommission" type="hidden" value="{isWithCommission}"/>
                <xsl:choose>
                    <xsl:when test="string-length(commission)>0 and format-number(commission, '0,00', 'sbrf') != '0,00'">
                        <xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:if test="isWithCommission = 'true'">
                            При осуществлении платежей взимается комиссия согласно тарифам банка
                        </xsl:if>
                        <xsl:if test="not(isWithCommission = 'true')">
                            0,00&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/>
                        </xsl:if>
                    </xsl:otherwise>
                </xsl:choose></b>
            </xsl:with-param>
        </xsl:call-template>

    </xsl:template>


    <xsl:template match="/form-data" mode="view-auto-sub-info">
        <xsl:variable name="autoSubTypes" select="document('auto-sub-payment-types.xml')/entity-list"/>
        <xsl:variable name="autoSubEventType" select="autoSubEventType"/>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Настройки автоплатежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="key" select="autoSubType"/>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">autoSubType</xsl:with-param>
            <xsl:with-param name="rowName">Тип</xsl:with-param>
            <xsl:with-param name="required">true</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$autoSubTypes/entity[@key=$key]/@description"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="autoSubType = 'ALWAYS'">
            <div id="ALWAYS">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Оплачивать</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="$autoSubTypes/entity[@key='ALWAYS']/field[@name=$autoSubEventType]/text()"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Дата ближайшего платежа</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="nextPayDateAlways">
                            <xsl:choose>
                                <xsl:when test="contains(nextPayDateAlways, '-')">
                                    <xsl:copy-of select="concat(substring(nextPayDateAlways, 9, 2), '.', substring(nextPayDateAlways, 6, 2), '.', substring(nextPayDateAlways, 1, 4))"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:copy-of select="nextPayDateAlways"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <b><xsl:value-of select="$nextPayDateAlways"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Сумма</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="alwaysAmount"/> руб.</b>
                    </xsl:with-param>
                </xsl:call-template>
            </div>
        </xsl:if>

        <xsl:if test="autoSubType = 'INVOICE'">
            <div id="INVOICE">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Оплачивать</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="$autoSubTypes/entity[@key='INVOICE']/field[@name=$autoSubEventType]/text()"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Ожидаемая дата оплаты счета</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="nextPayDateInvoice">
                            <xsl:choose>
                                <xsl:when test="contains(nextPayDateInvoice, '-')">
                                    <xsl:copy-of select="concat(substring(nextPayDateInvoice, 9, 2), '.', substring(nextPayDateInvoice, 6, 2), '.', substring(nextPayDateInvoice, 1, 4))"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:copy-of select="nextPayDateInvoice"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <b><xsl:value-of select="$nextPayDateInvoice"/></b>
                    </xsl:with-param>
                    <xsl:with-param name="description">
                        За сутки до указанной Вами даты при наличии выставленных счетов Вам будет направлено SMS c информацией и возможностью отказа от платежа. <cut/> Начиная с указанной даты раз в три дня будет осуществляться проверка наличия выставленного счета. Оплата счета произойдет по факту его выставления.
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Максимальный размер платежа</xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:choose>
                            <xsl:when test="string-length(invoiceMaxAmount)>0">
                                <b><xsl:value-of select="invoiceMaxAmount"/> руб.</b>
                            </xsl:when>
                            <xsl:otherwise>
                                <b><xsl:value-of select="invoiceMaxAmount"/></b>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>
            </div>
        </xsl:if>

         <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Название</xsl:with-param>
            <xsl:with-param name="required">true</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of select="autoSubName"/>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Комиссия</xsl:with-param>
            <xsl:with-param name="rowValue"><b>
                <xsl:choose>
                    <xsl:when test="string-length(commission)>0 and format-number(commission, '0,00', 'sbrf') != '0,00'">
                        <xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:if test="isWithCommission = 'true'">
                            При осуществлении платежей взимается комиссия согласно тарифам банка
                        </xsl:if>
                        <xsl:if test="not(isWithCommission = 'true')">
                            0,00&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/>
                        </xsl:if>
                    </xsl:otherwise>
                </xsl:choose></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Статус</xsl:with-param>
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

    <xsl:template name="autoSubTypeEvent">
        <xsl:param name="events"/>
        <xsl:param name="name"/>
        <xsl:param name="value"/>

        <select name="{$name}" id="{$name}" onchange="changeEventType();">
            <xsl:for-each select="$events/*">
                <xsl:variable name="nameEvent" select="./@name"/>
                <option value="{$nameEvent}">
                    <xsl:choose>
                        <xsl:when test="string-length($value) > 0 and $nameEvent = $value">
                            <xsl:attribute name="selected"/>
                        </xsl:when>
                        <xsl:when test="string-length($value) = 0 and 'ONCE_IN_MONTH' = $nameEvent">
                            <xsl:attribute name="selected"/>
                        </xsl:when>
                    </xsl:choose>
                    <xsl:value-of select="./text()"/>
                </option>
            </xsl:for-each>
        </select>
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
