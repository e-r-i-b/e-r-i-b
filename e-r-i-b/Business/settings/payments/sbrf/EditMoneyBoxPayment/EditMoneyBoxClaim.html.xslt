<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mf="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
                xmlns:du="java://org.apache.commons.lang.time.DateUtils"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:sbnkd="java://com.rssl.phizic.business.sbnkd.SberbankForEveryDayHelper"
                xmlns:java="http://xml.apache.org/xslt/java" xmlns:xls="http://www.w3.org/1999/XSL/Transform" exclude-result-prefixes="java"
        >
    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>

	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">
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

        <xsl:variable name="defaultFunctions" select="'fillCurrency();'"/>
        <xsl:choose>
            <xsl:when test="$name='toResource' and count($activeAccounts)=0">
                <xsl:choose>
                    <xsl:when test="$app = 'PhizIA'">
                        ��� ����������� ������� ������� ������� ���������� ������� �������������� ���� ��� �����
                    </xsl:when>
                    <xsl:otherwise>
                        ��� ����������� ������� ������� ��� ���������� ������� <a href="{concat($webRoot,'/private/deposits/products/list.do?form=AccountOpeningClaim')}">�������������� ���� ��� �����</a>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$name='fromResource' and count($activeCards)=0">
                <xsl:choose>
                    <xsl:when test="$app = 'PhizIA'">
                        ��� ����������� ������� ������� ������� ���������� ������� �����
                    </xsl:when>
                    <xsl:otherwise>
                        ��� ����������� ������� ������� ��� ����������
                        <xsl:choose>
                            <xsl:when test="sbnkd:availableSBNKD()">
                                    <a href="{concat($webRoot,'/private/sberbankForEveryDay.do')}">������� �����</a>
                            </xsl:when>
                            <xsl:otherwise>
                                    ������� �����
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <select id="{$name}" name="{$name}">
                    <xsl:attribute name="onchange">
                        <xsl:value-of select="$defaultFunctions"/>
                    </xsl:attribute>
                    <xsl:choose>
                        <xsl:when test="$name = 'fromResource'">
                            <xsl:call-template name="resourcesCards">
                                <xsl:with-param name="activeCards" select="$activeCards"/>
                                <xsl:with-param name="accountNumber" select="$accountNumber"/>
                                <xsl:with-param name="linkId" select="$linkId"/>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:call-template name="resourcesAccounts">
                                <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                                <xsl:with-param name="accountNumber" select="$accountNumber"/>
                                <xsl:with-param name="linkId" select="$linkId"/>
                            </xsl:call-template>
                        </xsl:otherwise>
                    </xsl:choose>
                </select>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="resourcesAccounts">
        <xsl:param name="activeAccounts"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:for-each select="$activeAccounts">
            <option>
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                <xsl:attribute name="value">
                    <xsl:value-of select="$id"/>
                </xsl:attribute>
                <xsl:if test="$accountNumber=./@key or $linkId=$id">
                    <xsl:attribute name="selected"/>
                </xsl:if>
                <xsl:value-of select="au:getShortAccountNumber(./@key)"/>&nbsp;
                [<xsl:value-of select="./field[@name='name']"/>]
                <xsl:if test="./field[@name='amountDecimal'] != ''">
                    <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                </xsl:if>
                <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
            </option>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="resourcesCards">
        <xsl:param name="activeCards"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:choose>
            <xsl:when test="string-length($activeCards) = 0">
                <option value="">��� ��������� ����</option>
            </xsl:when>
            <xsl:otherwise>
                <xsl:for-each select="$activeCards">
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <option>
                        <xsl:attribute name="value">
                            <xsl:value-of select="$id"/>
                        </xsl:attribute>
                        <xsl:if test="$accountNumber= ./@key or $linkId=$id">
                            <xsl:attribute name="selected"/>
                        </xsl:if>
                        <xsl:value-of select="mask:getCutCardNumber(./@key)"/>
                        &nbsp;
                        [
                        <xsl:value-of select="./field[@name='name']"/>
                        ]
                        <xsl:if test="./field[@name='amountDecimal'] != ''">
                            <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                        </xsl:if>
                        &nbsp;
                        <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
                    </option>
                </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="activeAccounts"    select="document('active-money-box-available-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards"       select="document('active-not-credit-main-cards-with-scs.xml')/entity-list/*"/>

        <input type="hidden" name="exactAmount" value="{exactAmount}" id="exactAmount"/>
        <input type="hidden" name="autoSubNumber" value="{autoSubNumber}"/>

        <div class="productTitleDetailInfo" style="border-bottom:none;">
            <div id="MBNameText" name="MBNameText" class="productTitleMargin word-wrap" style="margin-left: 10px;">
                <span class="productTitleDetailInfoText">
                    <span id="moneyBoxName" name="moneyBoxNameTitle"><xsl:value-of select="moneyBoxName"/></span>
                    <a class="productTitleDetailInfoEditBullet" onclick="showEditProductName(); setClientNameFlag();"></a>
                </span>
            </div>
        </div>
        <div id="MBNameEdit" name="MBNameEdit" style="display:none;">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">moneyBoxName</xsl:with-param>
                <xsl:with-param name="id">moneyBoxName</xsl:with-param>
                <xsl:with-param name="rowName">�������� �������</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input id="moneyBoxName" name="moneyBoxName" type="text" value="{moneyBoxName}" size="24"/>
                </xsl:with-param>
            </xsl:call-template>
            <div class="errorDiv clear" style="display:none;"></div>
        </div>
        <div style="float:none;"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">toResourceRow</xsl:with-param>
            <xsl:with-param name="id">toResource</xsl:with-param>
            <xsl:with-param name="rowName">���� ����������</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="resources">
                    <xsl:with-param name="name">toResource</xsl:with-param>
                    <xsl:with-param name="accountNumber" select="toAccountSelect"/>
                    <xsl:with-param name="linkId" select="toResource"/>
                    <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                    <xsl:with-param name="resourceView">����������</xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="id">fromResource</xsl:with-param>
			<xsl:with-param name="rowName">����� ��������</xsl:with-param>
			<xsl:with-param name="rowValue">
                <xsl:call-template name="resources">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                    <xsl:with-param name="linkId" select="fromResource"/>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                    <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                    <xsl:with-param name="resourceView">��������</xsl:with-param>
                </xsl:call-template>
			</xsl:with-param>
		</xsl:call-template>

        <!--  ���� ����������� -->
        <div id="autoPayBlock">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">moneyBoxSumType</xsl:with-param>
                <xsl:with-param name="rowName">��� �������</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <select name="moneyBoxSumType" id="moneyBoxSumType" onchange="changeEventType('');">
                        <option value="FIXED_SUMMA">
                            <xsl:if test="moneyBoxSumType = 'FIXED_SUMMA'">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            ������������� �����
                        </option>
                        <option value="PERCENT_BY_ANY_RECEIPT">
                            <xsl:if test="moneyBoxSumType = 'PERCENT_BY_ANY_RECEIPT'">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            ������� �� ����������
                        </option>
                        <option value="PERCENT_BY_DEBIT">
                            <xsl:if test="moneyBoxSumType = 'PERCENT_BY_DEBIT'">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            ������� �� ��������
                        </option>
                    </select>
                </xsl:with-param>
            </xsl:call-template>

            <div id="FIXED_SUMMA_DIV">
                <xsl:variable name="eventTypes" select="document('money-box-events-types.xml')/entity-list/entity"/>
                <xsl:variable name="eventType"  select="longOfferEventType"/>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="id">longOfferEventType</xsl:with-param>
                    <xsl:with-param name="rowName">�������������</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <select name="longOfferEventType" id="longOfferEventType" onchange="updateFixedSummaDescription(); updateMoneyBoxName();">
                            <xsl:for-each select="$eventTypes">
                                <xsl:if test="./@key = 'ONCE_IN_MONTH' or ./@key = 'ONCE_IN_QUARTER' or ./@key = 'ONCE_IN_WEEK' or ./@key = 'ONCE_IN_YEAR'">
                                    <option>
                                        <xsl:if test="$eventType = ./@key">
                                            <xsl:attribute name="selected"/>
                                        </xsl:if>
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="./@key"/>
                                        </xsl:attribute>
                                        <xsl:value-of select="./text()"/>
                                    </option>
                                </xsl:if>
                            </xsl:for-each>
                        </select>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                   <xsl:with-param name="rowName">���� ���������� ����������</xsl:with-param>
                   <xsl:with-param name="rowValue">
                       <xsl:variable name="longOfferStartDate">
                            <xsl:choose>
                                <xsl:when test="contains(longOfferStartDate, '-')">
                                    <xsl:copy-of select="concat(substring(longOfferStartDate, 9, 2), '.', substring(longOfferStartDate, 6, 2), '.', substring(longOfferStartDate, 1, 4))"/>
                                </xsl:when>
                                <xsl:when test="string-length(longOfferStartDate) = 0">
                                    <xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new('dd.MM.yyyy'), du:addDays(java:java.util.Date.new(),1))"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:copy-of select="longOfferStartDate"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <input name="longOfferStartDate" id="longOfferStartDate" class="dot-date-pick" size="10" value="{$longOfferStartDate}"
                              onchange="updateFixedSummaDescription();"/>
                   </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName"></xsl:with-param>
                    <xsl:with-param name="rowValue">
                        ���������� ����� ��������������
                        <div id="awaysPeriodicDescription" style="font-weight:bold;"></div>
                    </xsl:with-param>
                </xsl:call-template>

            </div>

            <div id="BY_PERCENT_DIV">
                <xsl:variable name="percentValue">
                    <xsl:choose>
                        <xsl:when test="string-length(percent)>0">
                            <xsl:value-of select="percent"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="dh:getMoneyBoxDefaultPercent()"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">percentRow</xsl:with-param>
                    <xsl:with-param name="id">percent</xsl:with-param>
                    <xsl:with-param name="rowName">% �� �����</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input id="percent" name="percent" type="text" value="{$percentValue}" size="24" onchange="updateMoneyBoxName();" onkeyup="updateMoneyBoxName();"/>&nbsp;
                    </xsl:with-param>
                </xsl:call-template>
            </div>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                <xsl:with-param name="id">sellAmount</xsl:with-param>
                <xsl:with-param name="rowName">����� ����������</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input id="sellAmount" name="sellAmount" type="text" value="{sellAmount}" size="24" class="moneyField"/>&nbsp;
                    <span id="sellAmountCurrency"></span>
                </xsl:with-param>
                <xsl:with-param name="description"></xsl:with-param>
            </xsl:call-template>
        </div>
        <!-- /���� ����������� -->

        <script type="text/javascript">
            var currencies = new Array();
            var cardMBConnectArray = new Array();
            <xsl:for-each select="$activeAccounts">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
            </xsl:for-each>

            <xsl:for-each select="$activeCards">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                currencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                cardMBConnectArray['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='hasMBRegistration']/text()"/>';
            </xsl:for-each>

            var clientNameFlag = <xsl:choose><xsl:when test="string-length(moneyBoxName)>0">true</xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose>;

            function setClientNameFlag()
            {
                clientNameFlag = true;
            }

            function isEmpty(value)
            {
                if (value == null || value == "")
                    return true;
                return false;
            }

            function fillCurrency()
            {
                var fromResourceValue = getElementValue("fromResource");
                if(fromResourceValue != "")
                {
                    $('#sellAmountCurrency').html(currencySignMap.get(currencies[fromResourceValue]));
                    hideOrShowMBConnectionMessage();
                }
            }

            var cardHasNoConnectToMBMessage = "�� �� ������� �������� SMS-��������� �� ��������� �� ������ �������, ������ ��� � ����� �������� �� ���������� ������ ���������� ����. ��� ��������� SMS-��������� ���������� ������ ���������� ���� ����� ���������� ����� ��������� �� �������� 8-800-500-55-50. ��� ���������� ����������� ������� ������� �� ������ �����������.";

            function hideOrShowMBConnectionMessage()
            {
                removeMessage(cardHasNoConnectToMBMessage,'warningsMoneyBox');
                var fromResourceValue = getElementValue("fromResource");
                if(cardMBConnectArray[fromResourceValue] == 'false')
                    addMessage(cardHasNoConnectToMBMessage,'warningsMoneyBox', true);
            }

            function showEditProductName()
            {
                $("#MBNameText").hide();
                $("#MBNameEdit").show();
                $("#moneyBoxName")[0].selectionStart = $("#moneyBoxName")[0].selectionEnd = $("#moneyBoxName").val().length;
            }

            function changeEventType(value)
            {
                var eventTypeValue = document.getElementById('moneyBoxSumType').value;
                var byPercentDiv = document.getElementById('BY_PERCENT_DIV');
                var fixedSummaDiv = document.getElementById('FIXED_SUMMA_DIV');
                if(eventTypeValue == 'FIXED_SUMMA')
                {
                    hideOrShow(fixedSummaDiv, false);
                    hideOrShow(byPercentDiv, true);
                    updateFixedSummaDescription();
                    $("#sellAmountTextLabel").text('����� ����������');
                    $("#sellAmount").val(value);
                    changeDescriptionAmountField('');
                }
                else
                {
                    hideOrShow(byPercentDiv, false);
                    hideOrShow(fixedSummaDiv, true);
                    $("#sellAmountTextLabel").text('������������ �����');
                    $("#sellAmount").val(value);
                    changeDescriptionAmountField('���� ������������ ����� ��� ���������� �������� ��������, ��������� � ������ ����, ���� �������� ��� ����������� � ������������ ��������� ������� �����. �� ������� ����������� ���������� �� ������� (����������) ����� �������� �������� ���������, ���� ��������������� ��� - ����� ���������� ���������� �� ����� ��������� ���� ������������ �����');
                }
                updateMoneyBoxName();
            }

            function changeDescriptionAmountField(text)
            {
                $("#sellAmountRow>div").each(function(index) {
                if(this.className == 'paymentValue')
                {
                    <![CDATA[
                    for(var i=0; i<this.children.length; i++)
                    {
                        if(this.children[i].className == 'description')
                        {
                            $(this.children[i]).html(text);
                            break;
                        }
                    }
                    ]]>
                }
              });
            }

            var daysOfWeekDesc = ["�������������", "���������", "������", "���������", "��������", "��������", "������������"];
            var monthOfYearDesc = ["������", "�������", "�����", "������", "���", "����", "����",   "�������", "��������", "�������","������", "�������"]

            function updateFixedSummaDescription()
            {
                var descriptionElement = document.getElementById('awaysPeriodicDescription');
                var eventTypeValue = document.getElementById('moneyBoxSumType').value;
                var startDateValue = Str2Date(document.getElementById('longOfferStartDate').value);
                if(eventTypeValue == 'FIXED_SUMMA')
                {
                    var periodicValue = document.getElementById('longOfferEventType').value;
                    var description = "";
                    if(periodicValue == 'ONCE_IN_WEEK')
                    {
                        description = "��� � ������, �� " + daysOfWeekDesc[(startDateValue.getDay()+6)%7];
                    }
                    else if(periodicValue == 'ONCE_IN_MONTH')
                    {
                        description = "��� � �����, " + startDateValue.getDate() + "-�� �����";
                    }
                    else if(periodicValue == 'ONCE_IN_QUARTER')
                    {
                        description = "��� � �������, " + startDateValue.getDate() + "-�� ����� " + (startDateValue.getMonth()%3 + 1) + "-�� ������";
                    }
                    else if(periodicValue == 'ONCE_IN_YEAR')
                    {
                        description = "��� � ���, " + startDateValue.getDate()+"-�� " + monthOfYearDesc[startDateValue.getMonth()];
                    }
                    $(descriptionElement).html(description);
                }
            }

            var periodicDesc = ["������������", "�����������", "��������������", "���������"]

            function updateMoneyBoxName()
            {
                if(clientNameFlag)
                    return;
                var eventTypeValue = document.getElementById('moneyBoxSumType').value;
                if(eventTypeValue == 'FIXED_SUMMA')
                {
                    var periodicValue = document.getElementById('longOfferEventType').value;
                    var name = "";
                    if(periodicValue == 'ONCE_IN_WEEK')
                    {
                        name = periodicDesc[0]+" ����������";
                    }
                    else if(periodicValue == 'ONCE_IN_MONTH')
                    {
                        name = periodicDesc[1]+" ����������";
                    }
                    else if(periodicValue == 'ONCE_IN_QUARTER')
                    {
                        name = periodicDesc[2]+" ����������";
                    }
                    else if(periodicValue == 'ONCE_IN_YEAR')
                    {
                        name = periodicDesc[3]+" ����������";
                    }
                }
                else if(eventTypeValue == 'PERCENT_BY_ANY_RECEIPT')
                {
                    var percentValue = document.getElementById('percent').value;
                    name = percentValue + "% �� �����������"
                }
                else
                {
                    var percentValue = document.getElementById('percent').value;
                    name = percentValue + "% �� ��������"
                }
                $('span#moneyBoxName').html(name);
                $('input#moneyBoxName').val(name);
            }

            doOnLoad(function()
            {
                changeEventType(<xsl:value-of select="sellAmount"/>);
                fillCurrency();
                showErrors();
            });

        </script>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <div class="productTitleDetailInfo" style="border-bottom:none;">
            <div id="MBNameText" name="MBNameText" class="productTitleMargin word-wrap" style="margin-left: 10px;">
                <span class="productTitleDetailInfoText">
                    <span id="moneyBoxName" name="moneyBoxNameTitle"><xsl:value-of select="moneyBoxName"/></span>
                </span>
            </div>
        </div>
        <div style="float:none;"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���� ����������</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="string-length(toAccountSelect)>0">
                    <xsl:value-of select="au:getFormattedAccountNumber(toAccountSelect)"/>
                    &nbsp;[<xsl:value-of select="toAccountName"/>]&nbsp;
                    <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">����� ��������</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="string-length(fromAccountSelect)>0">
                    <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                    &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                    <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">moneyBoxSumType</xsl:with-param>
            <xsl:with-param name="rowName">��� �������</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="moneyBoxSumType = 'FIXED_SUMMA'">
                        ������������� �����
                    </xsl:when>
                    <xsl:when test="moneyBoxSumType = 'PERCENT_BY_ANY_RECEIPT'">
                        ������� �� ����������
                    </xsl:when>
                    <xsl:when test="moneyBoxSumType = 'PERCENT_BY_DEBIT'">
                        ������� �� ��������
                    </xsl:when>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:choose>
            <xsl:when test="moneyBoxSumType = 'FIXED_SUMMA'">
                <xsl:variable name="eventTypes" select="document('money-box-events-types.xml')/entity-list/entity"/>
                <xsl:variable name="eventType"  select="longOfferEventType"/>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="id">longOfferEventType</xsl:with-param>
                    <xsl:with-param name="rowName">�������������</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="eventTypeDescription">
                            <xsl:for-each select="$eventTypes">
                                <xsl:if test="./@key = 'ONCE_IN_MONTH' or ./@key = 'ONCE_IN_QUARTER' or ./@key = 'ONCE_IN_WEEK' or ./@key = 'ONCE_IN_YEAR'">
                                    <xsl:if test="$eventType = ./@key">
                                        <xsl:value-of select="./text()"/>
                                    </xsl:if>
                                </xsl:if>
                            </xsl:for-each>
                        </xsl:variable>
                        <xsl:value-of select="$eventTypeDescription"/>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                   <xsl:with-param name="rowName">���� ���������� ����������</xsl:with-param>
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
                        <xsl:value-of select="$longOfferStartDate"/>
                   </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName"></xsl:with-param>
                    <xsl:with-param name="rowValue">
                        ���������� ����� ��������������
                        <div id="awaysPeriodicDescription" style="font-weight:bold;"></div>
                    </xsl:with-param>
                </xsl:call-template>

                <script type="text/javascript">
                    var daysOfWeekDesc = ["�������������", "���������", "������", "���������", "��������", "��������", "������������"];
                    var monthOfYearDesc = ["������", "�������", "�����", "������", "���", "����", "����",   "�������", "��������", "�������","������", "�������"]
                    var descriptionElement = document.getElementById('awaysPeriodicDescription');
                    var eventTypeValue = '<xsl:value-of select="moneyBoxSumType"/>';
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
                    var startDateValue = Str2Date('<xsl:value-of select="$longOfferStartDate"/>');
                    if(eventTypeValue == 'FIXED_SUMMA')
                    {
                        var periodicValue = '<xsl:value-of select="longOfferEventType"/>';
                        var description = "";
                        if(periodicValue == 'ONCE_IN_WEEK')
                        {
                            description = "��� � ������, �� " + daysOfWeekDesc[(startDateValue.getDay()+6)%7];
                        }
                        else if(periodicValue == 'ONCE_IN_MONTH')
                        {
                            description = "��� � �����, " + startDateValue.getDate() + "-�� �����";
                        }
                        else if(periodicValue == 'ONCE_IN_QUARTER')
                        {
                            description = "��� � �������, " + startDateValue.getDate() + "-�� ����� " + (startDateValue.getMonth()%3 + 1) + "-�� ������";
                        }
                        else if(periodicValue == 'ONCE_IN_YEAR')
                        {
                            description = "��� � ���, " + startDateValue.getDate()+"-�� " + monthOfYearDesc[startDateValue.getMonth()];
                        }
                        $(descriptionElement).html(description);
                    }
                </script>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                    <xsl:with-param name="id">sellAmount</xsl:with-param>
                    <xsl:with-param name="rowName">����� ����������</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:if test="string-length(sellAmount)>0">
                            <span class="summ">
                                <xsl:value-of select="format-number(translate(sellAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                            </span>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="moneyBoxSumType = 'PERCENT_BY_ANY_RECEIPT' or moneyBoxSumType = 'PERCENT_BY_DEBIT'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">percentRow</xsl:with-param>
                    <xsl:with-param name="id">percent</xsl:with-param>
                    <xsl:with-param name="rowName">% �� �����</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:value-of select="percent"/>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="string-length(sellAmount)>0">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">maxSumWriteRow</xsl:with-param>
                        <xsl:with-param name="id">maxSumWrite</xsl:with-param>
                        <xsl:with-param name="description">������� �����, ������� ���������� ���������</xsl:with-param>
                        <xsl:with-param name="rowName">������������ �����</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:if test="string-length(sellAmount)>0">
                                <span class="summ">
                                    <xsl:value-of select="format-number(translate(sellAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                    <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                                </span>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:when>
        </xsl:choose>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">������</xsl:with-param>
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

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">������ (������ ��� �������: "��������")</xsl:when>
			<xsl:when test="$code='INITIAL'">������ (������ ��� �������: "��������")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">��������� ���������</xsl:when>
			<xsl:when test="$code='DISPATCHED'">�������������� (������ ��� �������: "����������� ������")</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
			<xsl:when test="$code='RECALLED'">������� (������ ��� �������: "������ ���� ��������")</xsl:when>
			<xsl:when test="$code='REFUSED'">������� (������ ��� �������: "��������� ������")</xsl:when>
            <xsl:when test="$code='ERROR'">������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='UNKNOW'">������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">������� �������������� ��������� (������ ��� �������: "����������� � ���������� ������<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (����� SIM-�����)</xsl:if>")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE' or $code='TEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">��������� ���������</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">��������</xsl:when>
			<xsl:when test="$code='INITIAL'">��������</xsl:when>
			<xsl:when test="$code='DISPATCHED'">������������</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
			<xsl:when test="$code='RECALLED'">������ ���� ��������</xsl:when>
			<xsl:when test="$code='REFUSED'">��������� ������</xsl:when>
            <xsl:when test="$code='ERROR'">����������� ������</xsl:when>
            <xsl:when test="$code='UNKNOW'">����������� ������</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">����������� � ���������� ������<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (����� SIM-�����)</xsl:if></xsl:when>
		</xsl:choose>
	</xsl:template>

	<!--���������� option ��� select'a-->
	<xsl:template name="addOption">
	    <xsl:param name="value"/>
	    <xsl:param name="selected"/>
	    <xsl:param name="source"/>

        <option>
            <xsl:attribute name="value">
                <xsl:value-of select="$value"/>
            </xsl:attribute>
            <xsl:if test="contains($value, $selected)">
                <xsl:attribute name="selected"/>
            </xsl:if>
            <xsl:call-template name="monthsToString">
                <xsl:with-param name="value"  select="$value"/>
                <xsl:with-param name="source" select="$source"/>
            </xsl:call-template>
        </option>
	</xsl:template>

	<!--��������� ������ ������� � ������-->
	<xsl:template name="monthsToString">
	    <xsl:param name="value"/>
	    <xsl:param name="source"/>

        <xsl:variable name="delimiter" select="'|'"/>

        <xsl:choose>
            <xsl:when test="contains($value, $delimiter)">
                <xsl:for-each select="xalan:tokenize($value, $delimiter)">
                    <xsl:value-of select="concat($source[@key = current()]/text(), ' ')"/>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat($source[@key = $value]/text(), ' ')"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- ������ ����������� div �������� -->
<xsl:template name="buildDescription">
   <xsl:param name="text"/>

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
		<xsl:copy />
		</xsl:otherwise>
   </xsl:choose>
   </xsl:for-each>

   <xsl:if test="count($nodeText/cut) > 0">
   <xsl:value-of select="$end" disable-output-escaping="yes"/>
   </xsl:if>
	</div>

</xsl:template>

<xsl:template name="standartRow">

	<xsl:param name="id"/>
    <xsl:param name="lineId"/>                      <!--������������� ������-->
	<xsl:param name="required" select="'false'"/>    <!--�������� �������������� ����������-->
	<xsl:param name="rowName"/>                     <!--�������� ����-->
	<xsl:param name="rowValue"/>                    <!--������-->
	<xsl:param name="description"/>                	<!-- �������� ����, ������ ����� <cut />  ����� ��������� ��� ������ ��������� -->
	<xsl:param name="rowStyle"/>                    <!-- ����� ���� -->
    <xsl:param name="isAllocate" select="'true'"/>  <!-- �������� ��� ������� -->
    <!-- �������������� �������� -->
	<xsl:param name="fieldName"/>                   <!-- ��� ����. ���� �� ������, �� �������� �������� ��� �� rowValue -->

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
			<xsl:when test="string-length($inputName)>0">
				<xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
			</xsl:when>
			<xsl:otherwise>
				0
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

 <xsl:variable name="styleClass">
		<xsl:choose>
			<xsl:when test="$isAllocate = 'true'">
				<xsl:value-of select="'form-row'"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'form-row-addition'"/>
			</xsl:otherwise>
		</xsl:choose>
 </xsl:variable>

<div class="{$styleClass}">
    <xsl:if test="string-length($lineId) > 0">
        <xsl:attribute name="id">
            <xsl:copy-of select="$lineId"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="string-length($rowStyle) > 0">
        <xsl:attribute name="style">
            <xsl:copy-of select="$rowStyle"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
        <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
    </xsl:if>

	<div class="paymentLabel">
	    <span class="paymentTextLabel">
	        <xsl:if test="string-length($id) > 0">
	            <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
            </xsl:if>
	        <xsl:copy-of select="$rowName"/>
	    </span>
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk">*</span>
		</xsl:if>
    </div>
	<div class="paymentValue">
                <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>

				<xsl:if test="$readonly = 0 and $mode = 'edit'">
					<xsl:call-template name="buildDescription">
						<xsl:with-param name="text" select="$description"/>
	    			</xsl:call-template>
				</xsl:if>
                <div class="errorDiv" style="display: none;">
				</div>
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

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>
        </div>
    </xsl:template>

</xsl:stylesheet>