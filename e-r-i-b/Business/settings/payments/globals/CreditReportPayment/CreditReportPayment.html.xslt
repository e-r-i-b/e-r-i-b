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

		var WRONG_PAY_PERIOD_MESSAGE = '������������ ������ �������. ������� �������� � ������� MM/����.';
        var BAD_PAY_PERIOD = "�� ����������� ������� ������ ������. ����������� ������ ������ ���������� �� ��������, ��� ����������� �� �����.";

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
                '<div class="paymentLabel"><span class="paymentTextLabel">������ ������ (�����/���)</span><span class="asterisk">*</span></div>'+
                '<div class="paymentValue">'+
                '<div class="paymentInputDiv"><input type="hidden" value="'+value+'" id="'+payPeriodId+'" name="'+payPeriodId+'" />'+
                '<div class="paymentInputDiv"><b>'+ value + '</b>'+
                '<span class="text-gray" style="padding-left: 15px; cursor: pointer;" onclick="dropPayPeriod(' + periodCount + ');"><u>������� ������</u></span>'+
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

        var CONTRACT_AND_CONDITIONS_AGREEMENT = "����������, ������������ � ��������� �� �������� ����� �� �������������� ���������� ������ � ��������� �������� � ��������� ���������� �� ���������� ������. ����� ����� ���������� ������� � ��������������� ����� � ����������� ��������.";
        var CONTRACT_AGREEMENT = "���������� ������������ � ��������� �� �������� ����� �� �������������� ���������� ������. ����� ����� ���������� ������� � ���� �� �������� � ��������� �� �������� ����� �� �������������� ���������� ������ � ����������� ��������.";
        var CONDITIONS_AGREEMENT = "���������� ������������ � ��������� �������� � ��������� ���������� �� ���������� ������, ������� �� ������ �� ���������. ����� ����� ���������� ������� � ���� �� �������� � ��������� �������� � ��������� ���������� �� ���������� ������ � ����������� ��������";

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

        <!--������� ��������� �� �������� ������-->
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
                return AGREEMENT_CONDITION + '�' + buttonName + '�.';
            }
            else
                return AGREEMENT_CONDITION + '�������������.';
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
                    <option value="">��� ��������� ���� � ������</option>
                </xsl:when>
                <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                    <xsl:choose>
                        <xsl:when test="count($activeAccounts) + count($activeCards) > 0">
                            <option value="">�������� ����/����� ��������</option>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="count($activeCards) = 0 and count($activeAccounts) > 0">
                                    <option value="">�������� ���� ��������</option>
                                </xsl:when>
                                <xsl:when test="count($activeAccounts) = 0 and count($activeCards) > 0">
                                    <option value="">�������� ����� ��������</option>
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

        <!-- window ��� ����������� �������� -->
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
            <div class="closeImg" title="�������" onclick="win.close('providerImageHelp');"></div>
        </div>

        <xsl:if test="string-length($imageHelpUrl) > 0">
            <div class="dashedBorder">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">imageHelpProviderHeader</xsl:with-param>
                    <xsl:with-param name="rowName"><div class="imageHelpLabel">������� ���������</div></xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <script type="text/javascript" src="{$resourceRoot}/scripts/providerImageHelp.js"></script>
                        <div id="imageHelpProviderHeader">
                            <input type="hidden" id="imageHelpSrc" value="{$imageHelpUrl}"/>
                            <div class="imageHelpTitleContainer">
                                <a class="imageHelpTitle imageHelpHeaderControl closed" onclick="paymentImageHelpHeaderAction(); return false;" href="#">��������</a>
                            </div>
                            <div class="imageHelp" style="display:none"></div>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>
            </div>
        </xsl:if>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>����������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">������������:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="receiverName" value="{receiverName}"/>
                    <b><xsl:value-of select="receiverName"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(nameService)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">������:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="nameService" value="{nameService}"/>
                    <b><xsl:value-of select="nameService"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
             <xsl:with-param name="rowName">������ ������:</xsl:with-param>
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
            <xsl:with-param name="rowName">���:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="receiverINN" value="{receiverINN}"/>
                <b><xsl:value-of select="receiverINN"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="receiverAccount" value="{receiverAccount}"/>
                <b><xsl:value-of select="receiverAccount"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>���� ����������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">������������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="receiverBankName" value="{receiverBankName}"/>
                <b><xsl:value-of select="receiverBankName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">���:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="receiverBIC" value="{receiverBIC}"/>
                <b><xsl:value-of select="receiverBIC"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverCorAccount)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">�������:</xsl:with-param>
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
            <xsl:with-param name="rowName">���� ��������:</xsl:with-param>
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
            <xsl:with-param name="rowName">&nbsp;<b>������ �������</b></xsl:with-param>
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
                <!-- ������� ���� �� �������, ���������� ��� ������, ���� ������ ������� -->
                <!-- ���� ����� ������������ ����� ���� ���� �� ���� ���� � ����� �� GroupName, ��� � ��������, �� ������ �� ������ -->
                <xsl:when test="preceding-sibling::Attribute[./GroupName=current()/GroupName and current()/GroupName != '']"/>
                <!-- ���� ����� � ������� GroupName ��� �� ����, �� ����������� �� ���� ��� ���� � ����� GroupName � ������������ -->
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
                                <!--���� ������ �������� ����, ����� ���-->
                                <xsl:when test="string-length($currentValue) > 0">
                                    <xsl:value-of select="$currentValue"/>
                                </xsl:when>
                                <!--����� - ���������������� ��������-->
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
                            <!-- ���� � ���� GroupName �� ������ � ���� �������� ������ � ������, �� ��� ��� ������, ���������� ��� ��������� -->
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
                                                <a href="#" class="imageHelpTitle" onclick="paymentImageHelpFieldAction('{$id}');return false;">������� ���������</a>
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
                                                    <xsl:if test="$sum='true'">&nbsp;���.</xsl:if>
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
                    <xsl:with-param name="rowName">������ ������ (�����/���)</xsl:with-param>
                    <xsl:with-param name="description">������� ����� � ���, �� ������� �� ������ ������ ������, � ������� �� ������ ��������� ������.</xsl:with-param>
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
                         <span class="text-green" style="padding: 0 15px; cursor: pointer;" onclick="addPayPeriod(ensureElement('partyPayPeriodInit').value);"><u>�������� ������</u></span>
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

                //���� �������� ����� checkBox'� �� ������� � �������� set' � �� ������ �������
                if (setValue.indexOf(checkBoxId) &lt; 0 &amp;&amp; checked)
                {
                    if (setValue != "")
                        setValue = setValue + "@";
                    setValue = setValue + checkBoxValue;
                }

                //������� ���������
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
                var USING_STORED_CARDS_RESOURCE_MESSAGE    = '���������� �� ����� ������ ����� ���� ������������.';
                var USING_STORED_ACCOUNTS_RESOURCE_MESSAGE = '���������� �� ����� ������ ����� ���� ������������.';
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
            <xsl:with-param name="rowName">&nbsp;<b>����������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">������������:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverName"/></b>
                    <input name="receiverName" type="hidden" value="{receiverName}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(nameService)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">������:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="nameService"/></b>
                    <input name="nameService" type="hidden" value="{nameService}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">���:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverINN"/></b>
                <input name="receiverINN" type="hidden" value="{receiverINN}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverAccount"/></b>
                <input name="receiverAccount" type="hidden" value="{receiverAccount}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>���� ����������:</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">������������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverBankName"/></b>
                <input name="receiverBankName" type="hidden" value="{receiverBankName}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">���:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverBIC"/></b>
                <input name="receiverBIC" type="hidden" value="{receiverBIC}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverCorAccount)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">�������:</xsl:with-param>
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
			<xsl:with-param name="rowName">���� ��������:</xsl:with-param>
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
                        <xsl:with-param name="rowName">��������:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                                <b><xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/></b>
                                <input name="commission" type="hidden" value="{commission}"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:when>
            </xsl:choose>
        </xsl:if>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>������ �������</b></xsl:with-param>
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
            <!--������� ������������� ���� - ��� �������������� ��������� ����, �������� ������� ���������� ���������� ������ �� ����� ���������(��������������)-->
                        <xsl:if test="not($isHideInConfirmation) and not($hidden) and not($type='calendar')">
                            <xsl:choose>
                                <!-- ���� � ���� GroupName �� ������ � ���� �������� ������ � ������, �� ��� ��� ������, ���������� ��� ��������� -->
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
                                                        <span class="summ"><xsl:value-of select="$formatedValue"/><xsl:if test="not(($type='string' or $type='number') and string-length($currency)>0)">&nbsp;���.</xsl:if></span>
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
                                                        <xsl:if test="$sum='true'">&nbsp;���.</xsl:if>
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
                <xsl:with-param name="rowName">������ ������</xsl:with-param>
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
                ������
                <xsl:choose>
                    <xsl:when test="$isTemplate != 'true'">
                        �������:
                    </xsl:when>
                    <xsl:otherwise>
                        �������:
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

        <xsl:variable name="creditReportConditionsText" select="'� ��� ���� �������� ��� ��������� ������, � ��� ����� ��� �������� (����� �� ������ - ����), ����������������: 117997, �. ������, ��. ��������, �.19, �� ��������� ���� ���� ������������ ������, ��������� � ��������� �������, � ������������ � ����������� ������� �� 27.07.2006 �. � 152-�� �� ������������ ������� � ���� ������ � �������� ���������� � �������������� ������� ������������� ����� ��� ��� ������������� ����� ������� � ����� ��������� ���� ���������� ������ � �������������� ������ ��������� ���@��. �������� ������������� � ������� ���� ��� � ������� ��� ��������������. �� ��������� ���������� ����� �������� �������� ��������� ���������� �� ������ ��������� ���� ��� ��� ���������� �������� � ��� ������. �������� ����� ���� �������� ���� � ����� ������ ����� �������� ����� ������������ ���� ����������� �����������.'"/>

        <div id="creditReportTermsDiv">
            <div id="creditReportContractTermDiv" style="display: none;">
                <div style="margin: 50px">
                <p align="center"><strong>�������</strong><br/>
                  <strong>�� �������� �������������� ����� </strong><br/>
                  <strong>(�������������� ���������� ������)</strong><br/>
                  �. ������</p>
                  <p>��������� �������� ������������ ����� ����������� &ndash; ������ <strong>��������� ������������ �������� &laquo;������������ ��������� ����&raquo;</strong>, ���������� � ���������� <strong>&laquo;����&raquo;</strong>, ����������� ����, ����� ���������� - <strong>&laquo;������&raquo;</strong>, ��������� � ���� ��������� <strong>&laquo;�������&raquo;</strong>, � �� ����������� <strong>&laquo;�������&raquo;</strong>, ��������� ����� ������ - <strong>�������� ����������� �������� &laquo;�������� ������&raquo;</strong> (����� &ndash; <strong>&laquo;�����&raquo;</strong>), ������������ �� ����� � �� ��������� ����, ����������� ������������� ������� ������ ������� �� �������� �������������� ����� (����� <strong>&laquo;�������&raquo;</strong>) �� ���������� ���� ��������.<br/>
                  <strong>1. ������� ���������� ��������</strong><br/>
                  <strong>1.1.</strong> ������ ���������� � �������� � ���, ��� ������� ��������� ����������� � ������ ���������� �������� � ������������ ��������� ��������: <br/>
                  - ������������� ������������ � �������� � ��������� �������� ����� ������������ ������� � ���� &laquo;� �������� � ��������� �������� �� �������� ����� �� �������������� ���������� ������&raquo; � ����� ������������ ������, <br/>
                  - ������ ������ ����� ������������� ������������ � ��������� ��������. <br/>
                  <strong>1.2.</strong> ������������� ������������ � �������� � ��������� �������� ����� ����� ������������ ������ � ������� � ������ ������ ���� �������� ������ � �������������� �������� �������� ���� ������� �������� ��� �����-���� ������� �/��� ����������� � ���������� ���������� �������������� ����������� ��������. ����������� ���������, �������������� ������ ������������ ������, ���������� ��������� ������������� ���������� �� �������� �������� � ����� ������� ��������������� � ����.<br/>
                  <strong>2. �����������</strong><br/>
                  �������, ������������ � ��������� ��������, ����� ��������� ��������:<br/>
                  <strong>&laquo;�����&raquo;</strong> - ����������� ����� � 218-�� &laquo;� ��������� ��������&raquo; �� 30 ������� 2004 ����, �� ����� ����������� � ������������;<br/>
                  <strong>&laquo;��������� �������&raquo;</strong> - ����������, ������ ������� ��������� �������; <br/>
                  <strong>&laquo;������&raquo; ��� &laquo;�������&raquo;</strong> - �������������� �������� � �������������� ������� ������ � �������������� ��� ���������� ������, ���������� � �������� � ��������� �������� � ������ ������.<br/>
                  <strong>&laquo;��������� �����&raquo;</strong> - ��������, ������� �������� ����������, �������� � ������ ��������� �������, ���������� � ����, � ������� ���� ������������� �� ������� �������. ������ ���������� ������, ���������������� � ������ ���������� ��������, ������ � �. 4.3 ���������� ��������. ���� �����������, ��� ����������, ������������ � ��������� ������, ������������� ����� ����������, ������������ � ��������� ������� �������, ���������� � ���� �� ������ ������������ ���������� ������. � ����� ������������ ������������ ������ ��������� ����� ���������� ������ �����������. <br/>
                  <strong>&laquo;�������&raquo;</strong> - ������� �������������� ������� ������, ��������������� ������� ������� ����� ���������� �������������-�������������������� ���� �������� (&laquo;�������� ������&raquo;).<br/>
                  <strong>&laquo;������������� �������&raquo;</strong> - ������� ������������� ����������� �������, �������������� ������� � ������� �� ������������� � ��� ��������.<br/>
                  <strong>3. ������� ��������</strong><br/>
                  <strong>3.1.</strong> � ������������ � ��������� ���������� �������� ���� ��������� �� ������� �������, ��� ������� ��� �������������, ������������ ������� ����� ������� ��������� ����� <strong>(</strong>�����<strong> &laquo;������&raquo;</strong>), � ������ ��������� �������� ������ � �������, ������������� ���������. <br/>
                  <strong>3.2.</strong> ������ ����������� � ����� �������������� ������� ����� �� ����������, ������������ � ���� ����, ��������������� ���������� �������� �������� �� ���� ������������ �� ��������� ����� (�������).<u></u><br/>
                  <strong>4. ������� �������������� ���������� ������</strong><br/>
                  <strong>4.1.</strong> ��������� ����� ��������������� ������� � ���� �� ������� ���� ������� ���� � ������� ������ ������ ����� ����������� ���������� ������ � ������ �������� ������� � �������. ��������� ����� �������� ��� ������ � ����������. ��� ����������� ������ ������� �������������� ���������� ������ �������������� ����� ���������� ����������, ������������ � ������ ��������������� ������� ��������� ������ � �������. � ����� ������ ����� ��������������� ��������� ����� ����������.<br/>
                  <strong>4.2.</strong> ��������� ����� ��������� ��������������� ������� � ������� ����������� ���������� ������ � ������ �������� ������� � �������.<br/>
                  <strong>4.3.</strong> ��������� �����, ��������������� � ������ ���������� ��������, �������� ��������� ����������, �������� � ������ ��������� �������: <br/>
                  - ������������ ������ �������� ��������� ������� (�� ����������� ������������� ������ ��������� ����� ���������� ������), <br/>
                  - � ����������� � �������� ��������� ���������, <br/>
                  - � �������� <br/>
                  - �� ������� ��������� � ���������� ������������, <br/>
                  - �� ���������� ��������� �������. <br/>
                  <strong>5. ��������� � ������ �����</strong><br/>
                  <strong>5.1.</strong> �� ������, ����������� ���� ������� �� ���������� ��������, ������ ���������� ���� �������������� � ������� 420 (���������� ��������) ������ (������� ���).<br/>
                  <strong>5.2.</strong> ������ ����� ���� �������������� �������� � ������� � ���������� �������� �����.<br/>
                  <strong>6. ���������������</strong><br/>
                  <strong>6.1.</strong> ������ �������� � �������� � ���, ��� ������������ � ��������� ������ ���������� �������� ���� �� ���������� ������������ ��������� ������� � ������������ � �������. ���� �� ������� ��������� ������������� ����������, ��������������� ��� ����������� ������������ ��������� �������, � �� ����� ��������������� �� �� �������������� ��� ���������������. ������ �� ���������������, ������ �� ����� ����� �� ���������� ��� ���� ��������� �������, ��������� � ����� � ���������������� ��� ����������� ����������, ������������ � ��������� ������� ����, � ������ �� ������������ ��������� �������, ������������ � ���� ����.<br/>
                  <strong>6.2.</strong> ���� �� ����� ��������������� �� ��������� ������ �������, ��������� � ����� � �������������� ������������� ���������� ������ �� ����� ����������, ����� ��� ���������� � ������ 3.2. ���������� ��������.<br/>
                  <strong>7. �������������� ��������� </strong><br/>
                  <strong>7.1.</strong> � ������ ������������� ������������� ������ � ����� �� ������, ���������� � �������� � ������ �������, � ����� �� ������ �������� �������� ������ ������� ����� ���������� � ���������� ����� �� ��������&nbsp;- 8 (800) 555 55 50 (�������������).<br/>
                  <strong>7.2.</strong> ������� �������� � ���� � ������� ���������� �������� ��������, ��������� � �. 1.1. ��������, � ��������� �� ������� ���������� ��������� ������������ �� ��������.</p>
                  <p>������ ������������, ����� � ��������� ����:<br/>
                  �������� ����������� �������� &laquo;������������ ��������� ����&raquo;<br/>
                  127006, �. ������, ��. 1-�� ��������-������, �. 2, ���. 1<br/>
                  ���� �1047796788819 <br/>
                  ��� 7710561081<br/>
                  �/� �40702810938180003245 � ��� &laquo;�������� ������&raquo; ���������� ����, �/� �30101810400000000225 � ����� ����������� ��� ����� ������, ��� 044525225</p>
                  <p>&nbsp;</p>
                </div>
            </div>

            <div id="creditReportConditionsDiv" style="display: none;">
                <div style="margin: 50px">
                    <h1>������� �������� ��������� ���������� �� ���������� ������</h1>

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
                                ������� ������� �������� <a href="#" class="okb-dogovor-new-win" onclick="javascript:openPopupCreditReportContractTerms();return false;">� ����� ����</a>
                            </div>
                            <div class="okb-dogovor-text accountOpenText">
                                <div id="contractText" name="contractText" readonly="true">������� �� �������� �������������� ����� (�������������� ���������� ������) �. ������
        ��������� �������� ������������ ����� ����������� � ������ ��������� ������������ �������� ������������� ��������� ����, ���������� � ���������� �����, ����������� ����, ����� ���������� - �������, ��������� � ���� ��������� ���������, � �� ����������� ��������, ��������� ����� ������ - �������� ����������� �������� ��������� ������ (����� � ������), ������������ �� ����� � �� ��������� ����, ����������� ������������� ������� ������ ������� �� �������� �������������� ����� (����� ��������) �� ���������� ���� ��������.
        1. ������� ���������� ��������
        1.1. ������ ���������� � �������� � ���, ��� ������� ��������� ����������� � ������ ���������� �������� � ������������ ��������� ��������:
        - ������������� ������������ � �������� � ��������� �������� ����� ������������ ������� � ���� �� �������� � ��������� �������� �� �������� ����� �� �������������� ���������� ������ � ����� ������������ ������,
        - ������ ������ ����� ������������� ������������ � ��������� ��������.
        1.2. ������������� ������������ � �������� � ��������� �������� ����� ����� ������������ ������ � ������� � ������ ������ ���� �������� ������ � �������������� �������� �������� ���� ������� �������� ��� �����-���� ������� �/��� ����������� � ���������� ���������� �������������� ����������� ��������. ����������� ���������, �������������� ������ ������������ ������, ���������� ��������� ������������� ���������� �� �������� �������� � ����� ������� ��������������� � ����.
        2. �����������
        �������, ������������ � ��������� ��������, ����� ��������� ��������:
        - ������ - ����������� ����� � 218-�� �� ��������� ��������� �� 30 ������� 2004 ����, �� ����� ����������� � ������������;
        - ���������� �������� - ����������, ������ ������� ��������� �������;
        - ������� ��� ��������� - �������������� �������� � �������������� ������� ������ � �������������� ��� ���������� ������, ���������� � �������� � ��������� �������� � ������ ������.
        ���������� ����� - ��������, ������� �������� ����������, �������� � ������ ��������� �������, ���������� � ����, � ������� ���� ������������� �� ������� �������.
        �������� - ������� �������������� ������� ������, ��������������� ������� ������� ����� ���������� �������������-�������������������� ���� �������� (��������� ������).
        �������������� ������� - ������� ������������� ����������� �������, �������������� ������� � ������� �� ������������� � ��� ��������.
        3. ������� ��������
        3.1. � ������������ � ��������� ���������� �������� ���� ��������� �� ������� �������, ��� ������� ��� �������������, ������������ ������� ����� ������� ��������� ����� (����� �������), � ������ ��������� �������� ������ � �������, ������������� ���������.
        3.2. ������ ����������� � ����� �������������� ������� ����� �� ����������, ������������ � ���� ����, ��������������� ���������� �������� �������� �� ���� ������������ �� ��������� ����� (�������).
        4. ������� �������������� ���������� ������
        4.1. ��������� ����� ��������������� ������� � ���� �� ������� ���� ������� ���� � ������� ������ ������ ����� ����������� ���������� ������ � ������ �������� ������� � �������. ��������� ����� �������� ��� ������ � ����������. ��� ����������� ������ ������� �������������� ���������� ������ �������������� ����� ���������� ����������, ������������ � ������ ��������������� ������� ��������� ������ � �������. � ����� ������ ����� ��������������� ��������� ����� ����������.
        4.2. ��������� ����� ��������� ��������������� ������� � ������� ����������� ���������� ������ � ������ �������� ������� � �������.
        4.3. ��������� �����, ��������������� � ������ ���������� ��������, �������� ��������� ����������, �������� � ������ ��������� �������:
        - ������������ ������ �������� ��������� ������� (�� ����������� ������������� ������ ��������� ����� ���������� ������),
        - � ����������� � �������� ��������� ���������,
        - � ��������
        - �� ������� ��������� � ���������� ������������,
        - �� ���������� ��������� �������.
        5. ��������� � ������ �����
        5.1. �� ������, ����������� ���� ������� �� ���������� ��������, ������ ���������� ���� �������������� � ������� 420 (���������� ��������) ������ (������� ���).
        5.2. ������ ����� ���� �������������� �������� � ������� � ���������� �������� �����.
        6. ���������������
        6.1. ������ �������� � �������� � ���, ��� ������������ � ��������� ������ ���������� �������� ���� �� ���������� ������������ ��������� ������� � ������������ � �������. ���� �� ������� ��������� ������������� ����������, ��������������� ��� ����������� ������������ ��������� �������, � �� ����� ��������������� �� �� �������������� ��� ���������������. ������ �� ���������������, ������ �� ����� ����� �� ���������� ��� ���� ��������� �������, ��������� � ����� � ���������������� ��� ����������� ����������, ������������ � ��������� ������� ����, � ������ �� ������������ ��������� �������, ������������ � ���� ����.
        6.2. ���� �� ����� ��������������� �� ��������� ������ �������, ��������� � ����� � �������������� ������������� ���������� ������ �� ����� ����������, ����� ��� ���������� � ������ 3.2. ���������� ��������.
        7. �������������� ���������
        7.1. � ������ ������������� ������������� ������ � ����� �� ������, ���������� � �������� � ������ �������, � ����� �� ������ �������� �������� ������ ������� ����� ���������� � ���������� ����� �� �������� - 8 (800) 555 55 50 (�������������).
        7.2. ������� �������� � ���� � ������� ���������� �������� ��������, ��������� � �. 1.1. ��������, � ��������� �� ������� ���������� ��������� ������������ �� ��������.

        ������ ������������, ����� � ��������� ����:
        �������� ����������� �������� ������������� ��������� ����
        127006, �. ������, ��. 1-�� ��������-������, �. 2, ���. 1
        ���� �1047796788819
        ��� 7710561081
        �/� �40702810938180003245 � ��� ��������� ������ ���������� ����, �/� �30101810400000000225 � ����� ����������� ��� ����� ������, ��� 044525225
                                </div>
                            </div>
                            <div id="agreeForAllRow">
                                <input id="agreeWithContract" name="agreeWithContract" class="agreeChbx agreeWithContract" type="checkbox">
                                    <xsl:if test="state!='INITIAL' and state!='DRAFT' and state!='SAVED'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label for="agreeWithContract">&nbsp;� �������� � ��������� �� �������� ����� �� �������������� ���������� ������<span class="asterisk">*</span></label>
                            </div>
                            <div id="agreeForAllRow">
                                <input id="agreeWithConditions" name="agreeWithConditions" class="agreeChbx agreeWithConditions" type="checkbox">
                                    <xsl:if test="state!='INITIAL' and state!='DRAFT' and state!='SAVED'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label for="agreeWithConditions">&nbsp;� �������� � <a href ="#" onclick="javascript:openPopupCreditReportConditions();return false;"> ���������</a> �������� � ��������� ���������� �� ���������� ������<span class="asterisk">*</span></label>
                            </div>
                        </div>
                    </div>

                </xsl:with-param>
            </xsl:call-template>
        </div>

        <xsl:if test="$isTemplate != 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowStyle">display:none</xsl:with-param>
                <xsl:with-param name="rowName">�������� ���� ����������</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="admissionDate"/></xsl:with-param>
            </xsl:call-template>
         </xsl:if>

        <xsl:if test="string-length(promoCode)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">�����-���</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="promoCode">
                        <xsl:value-of select="promoCode"/>
                        <a class="imgHintBlock" title="����� ���������� �������" onclick="javascript:openFAQ('/PhizIC/faq.do#r2');"></a>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
	</xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">������ (������ ��� �������: "��������")</xsl:when>
            <xsl:when test="$code='INITIAL'">������ (������ ��� �������: "��������")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">��������� ���������</xsl:when>
            <xsl:when test="$code='DISPATCHED'">�������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='SEND'">�������������� (������ ��� �������: "����������� ������")</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
            <xsl:when test="$code='REFUSED'">������� (������ ��� �������: "��������� ������")</xsl:when>
            <xsl:when test="$code='RECALLED'">������� (������ ��� �������: "������ ���� ��������")</xsl:when>
			<xsl:when test="$code='ERROR'">������������� (������ ��� �������: "����������� ������")<xsl:if test="checkStatusCountResult = 'true'"> (���������� ���������� �������� �������)</xsl:if></xsl:when>
            <xsl:when test="$code='PARTLY_EXECUTED'">�������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='UNKNOW'">������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">������� �������������� ��������� (������ ��� �������: "����������� � ���������� ������<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (����� SIM-�����)</xsl:if>")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='TEMPLATE'">�������������</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='BILLING_CONFIRM_TIMEOUT'">������� ��� ������������� � �������� (����) (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='BILLING_GATE_CONFIRM_TIMEOUT'">������� ��� ������������� � �������� (����) (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='ABS_RECALL_TIMEOUT'">������� ��� ������ � ��� (����) (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='ABS_GATE_RECALL_TIMEOUT'">������� ��� ������ � ��� (����) (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">��������� ���������</xsl:when>
            <xsl:when test="$code='OFFLINE_SAVED'">������ (������ ��� �������: "��������")</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">��������</xsl:when>
			<xsl:when test="$code='INITIAL'">��������</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">��������� ���������</xsl:when>
            <xsl:when test="$code='DISPATCHED'">����������� ������</xsl:when>
            <xsl:when test="$code='SEND'">����������� ������</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
			<xsl:when test="$code='REFUSED'">��������� ������</xsl:when>
			<xsl:when test="$code='RECALLED'">������ ���� ��������</xsl:when>
			<xsl:when test="$code='ERROR'">����������� ������</xsl:when>
            <xsl:when test="$code='PARTLY_EXECUTED'">����������� ������</xsl:when>
            <xsl:when test="$code='UNKNOW'">����������� ������</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">����������� � ���������� ������<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (����� SIM-�����)</xsl:if></xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='TEMPLATE'">�������������</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='BILLING_CONFIRM_TIMEOUT'">����������� ������</xsl:when>
            <xsl:when test="$code='BILLING_GATE_CONFIRM_TIMEOUT'">����������� ������</xsl:when>
            <xsl:when test="$code='ABS_RECALL_TIMEOUT'">����������� ������</xsl:when>
            <xsl:when test="$code='ABS_GATE_RECALL_TIMEOUT'">����������� ������</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">��������� ���������</xsl:when>
            <xsl:when test="$code='OFFLINE_SAVED'">��������</xsl:when>
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


    <!-- ������ ����������� div �������� -->
<xsl:template name="buildDescription">
   <xsl:param name="text"/>
   <xsl:param name="additionalHelpData"/>

   <xsl:variable name="delimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">���������.</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="firstDelimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">��� ��������� ��� ����?</a>
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
    <xsl:param name="lineId"/>                      <!--������������� ������-->
	<xsl:param name="required" select="'true'"/>    <!--�������� �������������� ����������-->
	<xsl:param name="rowName"/>                     <!--�������� ����-->
	<xsl:param name="rowValue"/>                    <!--������-->
	<xsl:param name="description"/>                	<!-- �������� ����, ������ ����� <cut />  ����� ��������� ��� ������ ��������� -->
	<xsl:param name="additionalHelpData"/>          <!-- �������������� ���������� � ��������� -->
	<!-- �������������� �������� -->
	<xsl:param name="fieldName"/>                   <!-- ��� ����. ���� �� ������, �� �������� �������� ��� �� rowValue -->
    <xsl:param name="rowStyle"/>                    <!-- ���� ���� -->
    <xsl:param name="additionStyle"/>               <!-- ���. ����� iTunes -->
    <xsl:param name="edit"/>
    <xsl:param name="popupHint"/>

	<xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
	<!-- ����������� ����� ������ ��� ������� ������������� � ������ -->
	<!-- inputName - fieldName ��� ��� ���� ��������� �� rowValue -->
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

<!--  ����� ������ �� ����� ����
� ������ ���������� ������ ������������ javascript
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

<!-- ������������� ������� onfocus ���� -->
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
                ��������� �������� �� ���� ��������.
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
                <span class="otherRegionsTitle">���� ���������� ������������� ���� ������ ����� �: </span>
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
                <div class="closeOtherRegions"><img src="{$resourceRoot}/images/colorClose.gif" alt="�������" title="�������" border="0" onclick="hideOrShow('otherRegions')"/></div>
            </div>
        </div>
     </xsl:if>
     <script type="text/javascript">
       doOnLoad(
         function(){
            if (<xsl:value-of select="not($notError or $isAllRegions or string-length($personRegionName)=0)"/>)
               payInput.fieldError("regions", "���������� ��������������� � ������ ������� ������.");
        });

        <xsl:if test="string-length($providerId) &gt; 0">

            <!--������������ ��������� ��������� ������� � �����-->
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
                    payInput.fieldError("regions", "���������� ��������������� � ������ ������� ������.");
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

    <!-- ���� �������� ���������� -->
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

    <!-- ������������ ����������� ������������� set'�� -->
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
        <!-- �������� �� ���������� ��������� ����  -->
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
                                <li class="statusFree">�������� (<xsl:value-of select="count($places/seat[@status = 'F'])"/>)</li>
                                <li class="statusReserved">������������� (<xsl:value-of select="count($places/seat[@status = 'R'])"/>)</li>
                                <li class="statusSold">������� (<xsl:value-of select="count($places/seat[@status = 'S'])"/>)</li>
                            </ul>
                        </td>
                        <td class="carHeadEnter"></td>
                    </tr>
                </table>
            </div>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">������� �����</xsl:with-param>
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
