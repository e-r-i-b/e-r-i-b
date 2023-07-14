<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:spu="java://com.rssl.phizic.web.util.ServiceProviderUtil"
                xmlns:pu="java://com.rssl.phizic.web.util.PersonInfoUtil"
                xmlns:su="java://com.rssl.phizic.utils.StringUtils"
                xmlns:bh="java://com.rssl.phizic.business.basket.BasketHelper"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
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
    <xsl:param name="longOffer" select="true()"/>
    <xsl:param name="documentState" select="''"/>
    <xsl:param name="isDefaultShow" select="true()"/>
    <xsl:param name="skinUrl" select="'skinUrl'"/>
	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
	<xsl:variable name="accessAutoSubTypes" select="document('supportedAutoPays.xml')/entity-list"/>
    <xsl:key name="subService" match="Attribute[./Type != 'calendar']" use="GroupName"/>
	<xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="isInitialLongOfferState" select="$documentState = 'INITIAL_LONG_OFFER'"/>

	<xsl:variable name="styleClass" select="'form-row'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

	<xsl:template match="/">
        <xsl:choose>
			<xsl:when test="$mode = 'edit' and not($isInitialLongOfferState)">
				<xsl:apply-templates mode="edit"/>
				<xsl:apply-templates mode="edit-longOffer"/>
			</xsl:when>
            <xsl:when test="$mode = 'edit' and $isInitialLongOfferState">
                <xsl:apply-templates mode="view"/>
                <xsl:apply-templates mode="edit-longOffer"/>
            </xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
				<xsl:apply-templates mode="view-longOffer"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="bankDetails" select="bankDetails"/>
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-cards-all-visible.xml')/entity-list"/>

        <input type="hidden" name="accountingEntityId" value="{accountingEntityId}"/>
        <input type="hidden" name="bankDetails" value="{bankDetails}"/>
        <div>
            <div style="float: left;" class="size24 middleTitle" id="nameProvider"><xsl:value-of select="receiverName"/></div>
            <div style="float: right;">
                <img class="icon" src="{$skinUrl}/images/defaultProviderIcon.jpg" alt=""/></div>
            <div class="clear"></div>
        </div>

        <xsl:if test="string-length(nameService)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Услуга</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="nameService" value="{nameService}"/>
                    <b><xsl:value-of select="nameService"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <!-- РИСУЕМ EXTENED_FIELDS  -->
        <div class="extended-fields">
            <script type="text/javascript">
                function openProfileDocuments(linkElement, type)
                {
                    if(linkElement == null)
                        return;

                    var winItem = win.getWinItem('profileDocuments');
                    if(winItem == null)
                        return;

                    var formRow = $(linkElement).closest(".form-row");
                    winItem.referFormRow = formRow.get(0);
                    winItem.dataType = type;
                    win.open('profileDocuments');
                }

                doOnLoad(function(){
                    if ($('.Perioddate-pick').datePicker)
                        $('.Perioddate-pick').datePicker({displayClose: true, chooseImg: globalUrl + '/commonSkin/images/calendar.png', dateFormat:'MM/yyyy'.toLowerCase()});
                });
            </script>
            <xsl:for-each select="$extendedFields[./Type != 'calendar']">
                <xsl:call-template name="presentEditExtendedField">
                    <xsl:with-param name="field" select="."/>
                    <xsl:with-param name="form-data" select="$formData"/>
                    <xsl:with-param name="imageHelpUrl" select="spu:getImageHelpUrl(recipient, $webRoot)"/>
                </xsl:call-template>
            </xsl:for-each>

            <xsl:for-each select="$extendedFields[./Type = 'calendar']">
                <div>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">partyPeriod</xsl:with-param>
                        <xsl:with-param name="rowName">Период оплаты (месяц/год)</xsl:with-param>
                        <xsl:with-param name="description">Укажите месяц и год, за который Вы хотите внести платеж, и нажмите на ссылку «Добавить период».</xsl:with-param>
                        <xsl:with-param name="rowValue">
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
        </div>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Получатель</b></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverDescription)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Информация по получателю средств</xsl:with-param>
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

        <xsl:call-template name="standartRow">
             <xsl:with-param name="rowName">Регион оплаты</xsl:with-param>
             <xsl:with-param name="rowValue">
                <input type="hidden" name="personRegionName" value="{pu:getPersonRegionName()}"/>
                <xsl:call-template name="regionRowValue">
                    <xsl:with-param name="providerId" select="recipient"/>
                    <xsl:with-param name="personRegionName" select="pu:getPersonRegionName()"/>
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

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Детали платежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">fromResource</xsl:with-param>
            <xsl:with-param name="rowName">Счет списания</xsl:with-param>
            <xsl:with-param name="rowValue"><b>
                <xsl:if test="$personAvailable">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="linkId" select="fromResource"/>
                        <xsl:with-param name="activeCards" select="$activeCards"/>
                    </xsl:call-template>
                </xsl:if>
                <xsl:if test="not($personAvailable)">Счет клиента</xsl:if></b>
            </xsl:with-param>
		</xsl:call-template>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit-longOffer">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Название услуги</xsl:with-param>
            <xsl:with-param name="required">true</xsl:with-param>
            <xsl:with-param name="description">
                Например, «Коммуналка», «Штраф».
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

        <xsl:variable name="autoSubEventType">
             <xsl:choose>
                <xsl:when test="string-length(autoSubEventType) > 0">
                    <xsl:value-of select="autoSubEventType"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'ONCE_IN_MONTH'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="chooseDateInvoice">
            <xsl:choose>
                <xsl:when test="string-length(chooseDateInvoice) > 0">
                    <xsl:value-of select="dh:convertIfXmlDateFormat(chooseDateInvoice)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="dh:formatDateToStringWithPoint(dh:getNearDateByMonthWithoutCurrent(10))"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <div class="invoice-sub-params">
            <div class="paymentLabel"></div>
            <div class="paymentValue">
                <div class="bold">Я бы предпочел оплачивать счета:</div>
                <div class="invoiceSubParameters">
                    <div class="linkListOfOperation" onclick="showOrHideOperationBlock($('#autoSubEventTypeMenu .listOfOperation').get(0));" style="position: relative; float: left">
                        <span id="visibleAutoSubEventType"></span>
                        <input id="autoSubEventType" name="autoSubEventType" type="hidden" value="{$autoSubEventType}"/>

                        <div id="autoSubEventTypeMenu">
                            <xsl:variable name="eventTypes">
                                <item><a href="#" onclick="setElement('autoSubEventType', 'ONCE_IN_WEEK');updateDescAutoPayParameters();return false;">Еженедельно</a></item>
                                <item><a href="#" onclick="setElement('autoSubEventType', 'ONCE_IN_MONTH');updateDescAutoPayParameters();return false;">Ежемесячно</a></item>
                                <item><a href="#" onclick="setElement('autoSubEventType', 'ONCE_IN_QUARTER');updateDescAutoPayParameters();return false;">Ежеквартально</a></item>
                            </xsl:variable>
                            <xsl:call-template name="listOfOperations">
                                <xsl:with-param name="items" select="xalan:nodeset($eventTypes)"/>
                                <xsl:with-param name="id" select="'0'"/>
                            </xsl:call-template>
                        </div>
                        <script type="text/javascript">

                            var daysOfWeekDesc = ["понедельникам", "вторникам", "средам", "четвергам", "пятницам", "субботам", "воскресеньям"];

                            function updateDescAutoPayParameters()
                            {
                                var visibleNextPayDate = $('#visibleNextPayDateInvoice');
                                var visibleEventType = $('#visibleAutoSubEventType');

                                var nextPayDate = Str2Date($("#chooseDateInvoice").val());
                                switch($("#autoSubEventType").val())
                                {
                                    case 'ONCE_IN_WEEK':
                                    {
                                        visibleEventType.text('Еженедельно');
                                        visibleNextPayDate.html("по " + daysOfWeekDesc[(nextPayDate.getDay()+6)%7]);
                                        break;
                                    }
                                    case 'ONCE_IN_MONTH':
                                    {
                                        visibleEventType.text('Ежемесячно,');
                                        visibleNextPayDate.text(nextPayDate.getDate() + "-го числа");
                                        break;
                                    }
                                    case 'ONCE_IN_QUARTER':
                                    {
                                        visibleEventType.text('Ежеквартально,');
                                        visibleNextPayDate.text(nextPayDate.getDate() + "-го числа " + (nextPayDate.getMonth()%3 + 1) + "-го месяца");
                                        break;
                                    }
                                }
                            }

                            $(function(){
                                updateDescAutoPayParameters();
                                var chooseDateElem = $("#visibleNextPayDateInvoice").closest("div").get(0);
                                $("#chooseDateInvoice")
                                    .datePicker({displayClose: true, altField: chooseDateElem, dateFormat: 'dd.mm.yyyy' })
                                    .change(function(){
                                        updateDescAutoPayParameters();
                                });
                            })
                        </script>
                    </div>
                    <div style="float: left">
                         &nbsp;<span id="visibleNextPayDateInvoice"></span>
                    </div>
                    <input id="chooseDateInvoice" type="hidden" name="chooseDateInvoice" value="{$chooseDateInvoice}"/>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:variable name="bankDetails" select="bankDetails"/>
        <input type="hidden" name="bankDetails" value="{bankDetails}"/>

        <div>
            <div style="float: left;" class="size24 middleTitle" id="nameProvider"><xsl:value-of select="receiverName"/></div>
            <div style="float: right;">
                <img class="icon" src="{$skinUrl}/images/defaultProviderIcon.jpg" alt=""/></div>
            <div class="clear"></div>
        </div>

        <xsl:if test="string-length(nameService)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Услуга</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="nameService"/></b>
                    <input type="hidden" name="nameService" value="{nameService}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <div id="keyFields">
            <xsl:for-each select="$extendedFields[./Type != 'calendar' and ./IsKey = 'true']">
                <xsl:call-template name="presentViewExtendedField">
                    <xsl:with-param name="field" select="."/>
                    <xsl:with-param name="form-data" select="$formData"/>
                    <xsl:with-param name="imageHelpUrl" select="spu:getImageHelpUrl(recipient, $webRoot)"/>
                    <xsl:with-param name="mainSummCurrency" select="destinationCurrency"/>
                </xsl:call-template>
            </xsl:for-each>

            <xsl:if test="$extendedFields[./Type = 'calendar' and ./IsKey = 'true']">
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
                        <input type="hidden" name="payPeriod" value="{payPeriod}"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </div>

        <xsl:variable name="notKeyNotCalendarFields" select="$extendedFields[./Type != 'calendar' and not(./IsKey = 'true') and not(./IsVisible = 'false') and (not(./HideInConfirmation = 'true') or $isInitialLongOfferState)]"/>
        <xsl:variable name="notKeyCalendarFields" select="$extendedFields[./Type = 'calendar' and not(./IsKey = 'true') and not(./IsVisible = 'false') and (not(./HideInConfirmation = 'true') or $isInitialLongOfferState)]"/>
        <xsl:if test="$notKeyNotCalendarFields or $notKeyCalendarFields">
            <div id="notKeyFieldOpen">
                <div class="notKeyFieldShow" onclick="hideOrShowNotKeyFields(true);">
                    <span class="blueGrayLinkDotted">Показать полные реквизиты</span>
                </div>
            </div>

            <div id="notKeyFieldsHide" style="display: none">
                <div class="notKeyFieldHide" onclick="hideOrShowNotKeyFields(false);">
                    <span class="blueGrayLinkDotted">Скрыть полные реквизиты</span>
                </div>

                <xsl:for-each select="$notKeyNotCalendarFields">
                    <xsl:call-template name="presentViewExtendedField">
                        <xsl:with-param name="field" select="."/>
                        <xsl:with-param name="form-data" select="$formData"/>
                        <xsl:with-param name="imageHelpUrl" select="spu:getImageHelpUrl(recipient, $webRoot)"/>
                        <xsl:with-param name="mainSummCurrency" select="destinationCurrency"/>
                    </xsl:call-template>
                </xsl:for-each>

                <xsl:if test="$notKeyCalendarFields">
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
                            <input type="hidden" name="payPeriod" value="{payPeriod}"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </div>

            <script type="text/javascript">
                function hideOrShowNotKeyFields(display)
                {
                    if (display)
                    {
                        $('#notKeyFieldOpen').hide();
                        $('#notKeyFieldsHide').show();
                    }
                    else
                    {
                        $('#notKeyFieldOpen').show();
                        $('#notKeyFieldsHide').hide();
                    }
                }
            </script>
        </xsl:if>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Получатель</b></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverDescription)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Информация по получателю средств</xsl:with-param>
                <xsl:with-param name="edit">false</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <p align="justify">
                            <xsl:call-template name="tokenizeString">
                                <xsl:with-param name="string" select="receiverDescription"/>
                            </xsl:call-template>
                        </p>
                    </b>
                    <input type="hidden" name="receiverDescription" value="{receiverDescription}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
             <xsl:with-param name="rowName">Регион оплаты</xsl:with-param>
             <xsl:with-param name="rowValue">
                <xsl:call-template name="regionRowValue">
                    <xsl:with-param name="providerId" select="recipient"/>
                    <xsl:with-param name="personRegionName" select="pu:getPersonRegionName()"/>
                </xsl:call-template>
            </xsl:with-param>
         </xsl:call-template>

        <xsl:if test="$bankDetails = 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">ИНН</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverINN"/></b>
                    <input type="hidden" name="receiverINN" value="{receiverINN}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Счет</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverAccount"/></b>
                    <input type="hidden" name="receiverAccount" value="{receiverAccount}"/>
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
                    <input type="hidden" name="receiverBankName" value="{receiverBankName}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">БИК</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverBIC"/></b>
                    <input type="hidden" name="receiverBIC" value="{receiverBIC}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:if test="string-length(receiverCorAccount)>0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="rowName">Корсчет</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="receiverCorAccount"/></b>
                        <input type="hidden" name="receiverCorAccount" value="{receiverCorAccount}"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:if>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Детали платежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="$isInitialLongOfferState">
                        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-cards-all-visible.xml')/entity-list"/>
                        <xsl:call-template name="resources">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="linkId" select="fromResource"/>
                            <xsl:with-param name="activeCards" select="$activeCards"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <b>
                            <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                            &nbsp;[<xsl:value-of select="fromAccountName"/>]
                            &nbsp;<xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                        </b>
                    </xsl:otherwise>
                </xsl:choose>
			</xsl:with-param>
		</xsl:call-template>
    </xsl:template>

    <xsl:template match="/form-data" mode="view-longOffer">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Название услуги</xsl:with-param>
            <xsl:with-param name="required">true</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="autoSubName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <div class="invoice-sub-params">
            <div class="paymentLabel"></div>
            <div class="paymentValue">
                <div class="bold">Я бы предпочел оплачивать счета:</div>
                <div class="invoiceSubParameters">
                    <div class="linkListOfOperation" style="position: relative; float: left">
                        <span id="visibleAutoSubEventType">
                            <xsl:choose>
                                <xsl:when test="autoSubEventType = 'ONCE_IN_WEEK'">Еженедельно</xsl:when>
                                <xsl:when test="autoSubEventType = 'ONCE_IN_MONTH'">Ежемесячно,</xsl:when>
                                <xsl:when test="autoSubEventType = 'ONCE_IN_QUARTER'">Ежеквартально,</xsl:when>
                            </xsl:choose>
                        </span>
                    </div>
                    <div style="float: left">&nbsp;
                        <span id="visibleNextPayDateInvoice">
                            <xsl:choose>
                                <xsl:when test="autoSubEventType = 'ONCE_IN_WEEK'">
                                    по <xsl:value-of select="bh:getNameDayOfWeek(nextPayDayOfWeekInvoice + 1)"/>
                                </xsl:when>
                                <xsl:when test="autoSubEventType = 'ONCE_IN_MONTH'">
                                    <xsl:value-of select="nextPayDayOfMonthInvoice"/>-го числа
                                </xsl:when>
                                <xsl:when test="autoSubEventType = 'ONCE_IN_QUARTER'">
                                    <xsl:value-of select="nextPayMonthOfQuarterInvoice"/>-го месяца
                                    <xsl:value-of select="nextPayDayOfMonthInvoice"/>-го числа
                                </xsl:when>
                            </xsl:choose>
                        </span>
                    </div>
                    <input id="chooseDateInvoice" type="hidden" name="field(chooseDateInvoice)" value="{chooseDateInvoice}"/>
                </div>
            </div>
            <div class="clear"></div>
        </div>

        <xsl:if test="$app = 'PhizIA'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="state">
                        <xsl:call-template name="employeeState2text">
                            <xsl:with-param name="code">
                                <xsl:value-of select="state"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

    </xsl:template>


    <!-- //////////////////////////////////////   ВСПОМОГАТЕЛЬНЫЕ ШАБЛОНЫ  ////////////////////////////////////////////////////////////////////   -->


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
			<xsl:when test="$code='DISPATCHED' and $longOffer">Исполняется банком</xsl:when>
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
        <div class="{$styleClass}">
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
            doOnLoad(function(){
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

    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="linkId"/>
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeCards"/>

        <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>

        <xsl:if test="$activeAccounts">
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

    <xsl:template name="presentEditExtendedField">
        <xsl:param name="field"/>
        <xsl:param name="form-data"/>
        <xsl:param name="imageHelpUrl"/>

        <xsl:variable name="name" select="$field/NameVisible"/>
        <xsl:variable name="hidden" select="$field/IsVisible = 'false'"/>
        <xsl:variable name="id" select="$field/NameBS"/>
        <xsl:variable name="isKey" select="$field/IsKey = 'true'"/>
        <xsl:variable name="saveInTemplate" select="$field/SaveInTemplate = 'true'"/>
        <xsl:variable name="description" select="$field/Description"/>
        <xsl:variable name="hint" select="$field/Comment"/>
        <xsl:variable name="size" select="$field/MaxLength"/>
        <xsl:variable name="readonly" select="$field/IsEditable = 'false'"/>
        <xsl:variable name="groupName" select="$field/GroupName"/>
        <xsl:variable name="popupHint" select="$field/PopupHint"/>
        <xsl:variable name="extendedDescId" select="$field/ExtendedDescriptionId"/>
        <xsl:variable name="graphicTemplateName" select="$field/GraphicTemplateName"/>
        <xsl:variable name="isMaskedValue" select="$form-data/*[name()=$id]/@masked = 'true'"/>
        <xsl:variable name="val">
            <xsl:variable name="currentValue" select="$form-data/*[name()=$id]"/>
            <xsl:choose>
                <!--если задано значение явно, юзаем его-->
                <xsl:when test="string-length($currentValue) > 0">
                    <xsl:value-of select="$currentValue"/>
                </xsl:when>
                <!--иначе - инициализирующее значение-->
                <xsl:otherwise>
                    <xsl:value-of select="$field/DefaultValue"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="mainSum" select="$field/IsMainSum = 'true'"/>
        <xsl:variable name="sum" select="$field/IsSum = 'true'"/>
        <xsl:variable name="type" select="$field/Type"/>
        <xsl:variable name="mandatory" select="$field/IsRequired"/>
        <xsl:variable name="disabled"  select="false()"/>

        <xsl:choose>
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
                        <xsl:for-each select="$field/Menu/MenuItem">
                            <field key="{$field/Id}"><xsl:value-of select="./Value"/></field>
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
                                    <xsl:for-each select="$field/Menu/MenuItem">
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
                                <xsl:variable name="checkedValue" select="$form-data/*[name()=$id]/text()"/>
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
                                <span class="document-info" onclick="openProfileDocuments(this,'{$type}');"><span>Мои документы</span></span>
                                <input type="hidden" name="{$id}___document" value="{$form-data/*[name()=concat($id, '___document')]/text()}"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="presentViewExtendedField">
        <xsl:param name="field"/>
        <xsl:param name="form-data"/>
        <xsl:param name="imageHelpUrl"/>
        <xsl:param name="mainSummCurrency"/>

        <xsl:variable name="name" select="$field/NameVisible"/>
        <xsl:variable name="isHideInConfirmation" select="$field/HideInConfirmation = 'true'"/>
        <xsl:variable name="hidden" select="$field/IsVisible = 'false'"/>
        <xsl:variable name="type" select="$field/Type"/>
        <xsl:variable name="id" select="$field/NameBS"/>
        <xsl:variable name="isKey" select="$field/IsKey = 'true'"/>
        <xsl:variable name="hint" select="$field/Comment"/>
        <xsl:variable name="mainSum" select="$field/IsMainSum = 'true'"/>
        <xsl:variable name="readonly" select="$field/IsEditable = 'false'"/>
        <xsl:variable name="groupName" select="$field/GroupName"/>
        <xsl:variable name="currentValue" select="$form-data/*[name()=$id]"/>
        <xsl:variable name="mandatory" select="$field/IsRequired"/>
        <xsl:variable name="popupHint" select="$field/PopupHint"/>
        <xsl:variable name="mask" select="$field/Mask"/>
        <xsl:variable name="extendedDescId" select="$field/ExtendedDescriptionId"/>
        <xsl:variable name="graphicTemplateName" select="$field/GraphicTemplateName"/>
        <!--скрытые редактируемые поля - это персчитываемые биллингом поля, значения которых необходимо показывать только на форме просмотра(подтвержедения)-->
        <xsl:if test="not($hidden) and not($type='calendar')">
            <xsl:choose>
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
                        <xsl:with-param name="rowName"><xsl:value-of select="$name"/></xsl:with-param>
                        <xsl:with-param name="description"><xsl:value-of select="$hint"/></xsl:with-param>
                        <xsl:with-param name="required"><xsl:value-of select="$mandatory"/></xsl:with-param>
                        <xsl:with-param name="popupHint"><xsl:value-of select="$popupHint"/></xsl:with-param>
                        <xsl:with-param name="lineId"><xsl:value-of select="$id"/></xsl:with-param>
                        <xsl:with-param name="rowValue"><b>
                            <xsl:variable name="sum" select="$field/IsSum"/>
                            <xsl:if test="string-length($currentValue)>0 ">
                                <xsl:variable name="formatedValue">
                                    <xsl:choose>
                                        <xsl:when test="($type='string' or $type='number') and $mainSum = 'true'">
                                            <xsl:value-of select="format-number($currentValue, '### ##0,00', 'sbrf')"/>
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
                                    <xsl:choose>
                                        <xsl:when test="$mainSum = 'true'">
                                            &nbsp;<span class='summ'><xsl:value-of select="mu:getCurrencySign($mainSummCurrency)"/></span>
                                        </xsl:when>
                                         <xsl:when test="$sum = 'true'">
                                            &nbsp;руб.
                                        </xsl:when>
                                    </xsl:choose>
                                </xsl:variable>
                                <xsl:choose>
                                    <xsl:when test="$mainSum = 'true'">
                                        <span class="summ">
                                            <xsl:value-of select="$formatedValue"/>
                                        </span>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:choose>
                                            <xsl:when test="$form-data/*[name()=$id]/@changed='true'">
                                                <b><xsl:copy-of select="$formatedValue"/></b>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:copy-of select="$formatedValue"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <input type="hidden" name="{$id}" value="{$currentValue}"/>
                            </xsl:if></b>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>
    </xsl:template>

    <xsl:template name="listOfOperations">
        <xsl:param name="items"/>  <!-- в формате <item>содержимое первого пункта</item><item>содержимое второго пункта</item> -->
        <xsl:param name="id"/>

        <div class='listOfOperation productOperation' id="listOfOperation{$id}_parent"  onclick="cancelBubbling(event);">
            <div id="listOfOperation{$id}" class="moreListOfOperation" style="display:none;">
                <xsl:call-template name="roundBorder">
                    <xsl:with-param name="color" select="'hoar'"/>
                    <xsl:with-param name="data">
                        <div>
                            <table cellpadding="0" cellspacing="0" class="productOperationList">
                                <xsl:for-each select="$items/item">
                                   <tr><td onmouseover="mouseEnter(this);" onmouseout="mouseLeave(this);">
                                       <xsl:copy-of select="./node()"/>
                                   </td></tr>
                                </xsl:for-each>
                            </table>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="roundBorder">
        <xsl:param name="data"/>
        <xsl:param name="color"/>

        <div class="workspace-box hoar">
            <div class="{$color}RT r-top">
                <div class="{$color}RTL r-top-left"><div class="{$color}RTR r-top-right"><div class="{$color}RTC r-top-center">
                <div class="clear"></div>
                </div></div></div>
            </div>
            <div class="{$color}RCL r-center-left">
                <div class="{$color}RCR r-center-right">
                    <div class="{$color}RC r-content">
                        <xsl:copy-of select="$data"/>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
            <div class="{$color}RBL r-bottom-left">
                <div class="{$color}RBR r-bottom-right">
                    <div class="{$color}RBC r-bottom-center"></div>
                </div>
             </div>
        </div>
    </xsl:template>
</xsl:stylesheet>



