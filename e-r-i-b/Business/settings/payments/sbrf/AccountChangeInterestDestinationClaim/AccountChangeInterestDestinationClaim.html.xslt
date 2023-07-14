<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:clh="java://com.rssl.phizic.business.resources.external.CardLinkHelper">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="resourcesCards">
        <xsl:param name="activeCards"/>
        <xsl:param name="cardNumber"/>
        <xsl:param name="name"/>
        <xsl:param name="linkId"/>

        <xsl:for-each select="$activeCards">

            <xsl:if test="./field[@name='isMain'] = 'true' or ./field[@name='additionalCardType'] = 'CLIENTTOCLIENT' or $name = 'toResource'">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                <option>
                    <xsl:if test="./field[@name='isMain'] = 'false' and $name = 'toResource'">
                        <xsl:attribute name="class">hideable</xsl:attribute>
                    </xsl:if>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$linkId=$id or $cardNumber=./@key">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="mask:getCutCardNumber(./@key)"/>
                    &nbsp;
                    [
                    <xsl:value-of select="./field[@name='name']"/>
                    ]
                    <xsl:if test="./field[@name='amountDecimal'] != ''">
                        <xsl:value-of
                                select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                    </xsl:if>
                    &nbsp;
                    <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:if>

        </xsl:for-each>
    </xsl:template>

    <xsl:template name="resourcesCardsForPercent">
        <xsl:param name="name"/>
        <xsl:param name="activeCards"/>
        <xsl:param name="cardNumber"/>
        <xsl:param name="linkId"/>
        <xsl:param name="transferToCardNotChecked"/>

        <select id="{$name}" name="{$name}">
            <xsl:choose>
                <xsl:when test="count($activeCards) = 0">
                    <xsl:attribute name="disabled"/>
                    <option value="">Нет доступных карт</option>
                </xsl:when>
                <xsl:when test="$cardNumber != '' or $linkId != ''"/>
                <xsl:when test="$transferToCardNotChecked">
                    <xsl:attribute name="disabled"/>
                    <option value="">Выберите карту</option>
                </xsl:when>
                <xsl:otherwise>
                    <option value="">Выберите карту</option>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:call-template name="resourcesCards">
                <xsl:with-param name="activeCards" select="$activeCards"/>
                <xsl:with-param name="name" select="$name"/>
                <xsl:with-param name="linkId" select="$linkId"/>
                <xsl:with-param name="cardNumber" select="$cardNumber"/>
            </xsl:call-template>
        </select>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="activeWithTBLoginedCards" select="document(concat('active-with-tblogined-without-interest-cards.xml?accountId=', accountCode))/entity-list/*"/>
        <xsl:variable name="accountCode" select="accountCode"/>
        <xsl:variable name="allAccounts" select="document('active-or-arrested-accounts-info.xml')/entity-list/*"/>
        <xsl:variable name="currentAccount" select="$allAccounts[$accountCode=./field[@name='code']]"/>
        <input type="hidden" name="accountCode" value="{accountCode}" id="accountCode"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <script>
            doOnLoad(function()
            {
                 getElement("cardLink").disabled = ($("[name=interestDestinationSource]:checked").val()=='account');
            });
            function setPercentPaymentOrderToCard(value)
            {
                var percentTransferCardSource = getElement("cardLink");
                percentTransferCardSource.disabled = !value;
                if (value)
                {
                    percentTransferCardSource.selectedIndex = 0;
                    if($('div[name=helpMessageIfCardUsing]')) {
                        $('div[name=helpMessageIfCardUsing]').show();
                    }
                }
                else
                {
                    if($('div[name=helpMessageIfCardUsing]')) {
                        $('div[name=helpMessageIfCardUsing]').hide();
                    }
                }
            }
        </script>

        <xsl:variable name="interestTransferCardNum" select="$currentAccount/field[@name='interestTransferCard']"/>
        <xsl:variable name="interestTransferAccountNum" select="$currentAccount/field[@name='interestTransferAccount']"/>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">percentPaymentOrderRow</xsl:with-param>
                <xsl:with-param name="id">percentPaymentOrderRow</xsl:with-param>
                <xsl:with-param name="required" select="'true'"/>
                <xsl:with-param name="rowName">
                    <span>Порядок&nbsp;уплаты&nbsp;процентов:</span>
                </xsl:with-param>

                <xsl:with-param name="rowValue">
                    <div>
                        <xsl:if test="string-length($interestTransferCardNum) > 0">
                            <div class="fullWidthRadioButton">
                                <b>
                                   <input type="radio" value="account" name="interestDestinationSource" onclick="setPercentPaymentOrderToCard(false);">
                                       <xsl:attribute name="checked"/>
                                   </input>
                                   &nbsp;капитализация процентов на счете по вкладу
                                </b>
                            </div>
                        </xsl:if>

                        <div class="fullWidthRadioButton">
                            <b>
                               <input type="radio" value="card" name="interestDestinationSource" id="transferOfInterest" onclick="setPercentPaymentOrderToCard(true);">
                                    <xsl:if test="string-length($interestTransferCardNum) = 0 or interestDestinationSource = 'card'">
                                        <xsl:attribute name="checked"/>
                                    </xsl:if>
                                    <xsl:if test="count($activeWithTBLoginedCards) = 0">
                                        <xsl:attribute name="disabled"/>
                                    </xsl:if>
                               </input>
                               &nbsp;перечисление процентов на счет банковской карты
                            </b>
                        </div>

                        <div>
                            <xsl:call-template name="resourcesCardsForPercent">
                                <xsl:with-param name="activeCards" select="$activeWithTBLoginedCards"/>
                                <xsl:with-param name="name">cardLink</xsl:with-param>
                                <xsl:with-param name="cardNumber" select="interestCardNumber"/>
                                <xsl:with-param name="linkId" select="cardLink"/>
                                <xsl:with-param name="transferToCardNotChecked" select="not(string-length($interestTransferCardNum) = 0)"/>
                            </xsl:call-template>
                        </div>

                        <div name="helpMessageIfCardUsing" class="payments-legend">
                            <xsl:if test="not(string-length($interestTransferCardNum) = 0)">
                                <xsl:attribute name="style">display:none</xsl:attribute>
                            </xsl:if>
                            Укажите банковскую карту для перечисления процентов по вкладу
                        </div>

                        <xsl:choose>
                            <xsl:when test="string-length($interestTransferCardNum) = 0">
                                <div class="fullWidthRadioButton" style="margin-top:1em;">
                                    <b>
                                       <input type="radio" value="account" name="interestDestinationSource"  onclick="setPercentPaymentOrderToCard(false);">
                                           <xsl:if test="string-length($interestTransferCardNum) = 0 and string-length($interestTransferAccountNum) = 0">
                                                  <xsl:attribute name="disabled"/>
                                           </xsl:if>
                                           <xsl:if test="interestDestinationSource = 'account'">
                                               <xsl:attribute name="checked" />
                                           </xsl:if>
                                       </input>
                                       &nbsp;капитализация процентов на счете по вкладу
                                    </b>
                                </div>
                            </xsl:when>
                        </xsl:choose>
                    </div>

                </xsl:with-param>
            </xsl:call-template>

    </xsl:template>
    <xsl:template match="/form-data" mode="view">
        <script type="text/javascript">
            function onClickLicenseAgreement()
            {
                var licenseAgree =ensureElement('licenseAgree');
                if(licenseAgree != null)
                {
                    licenseAgree.removeAttribute('disabled');
                }
                var win = window.open("<xsl:value-of select="$webRoot"/>/private/deposit/collateralAgreementWithCapitalization.do?id=" + getQueryStringParameter('id'), 'IMALicense', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=750, height=500");
                win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);


            };

            function checkClientAgreesCondition(buttonName)
            {
                var checked = getElement('licenseAgree').checked;
                if (checked == false)
                {
                     addError('Для того чтобы подтвердить заявку, ознакомьтесь с условиями дополнительного соглашения к договору о вкладе, щелкнув по ссылке «Просмотр условий дополнительного соглашения». Если Вы согласны с условиями, то установите флажок в поле «С условиями дополнительного соглашения согласен» и нажмите кнопку «Подтвердить».');
                     payInput.fieldError('licenseAgree');
                }

                return checked;
            };
        </script>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">
                <span>Порядок&nbsp;уплаты&nbsp;процентов:</span>
            </xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:variable name="interestCardNumber" select="interestCardNumber"/>
                    <xsl:choose>
                        <xsl:when test="string-length($interestCardNumber)>0">
                            на счет банковской карты&nbsp;
                            <xsl:choose>
                                <xsl:when test="string-length($interestCardNumber)>0">
                                    <xsl:value-of select="clh:getCardInfoByCardNumber($interestCardNumber)"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:variable name="activeWithTBLoginedCards" select="document('active-with-tblogined-cards.xml')/entity-list"/>
                                    <xsl:variable name="cardNode" select="$activeWithTBLoginedCards/entity[$interestCardNumber=./@key]"/>
                                    <xsl:if test="$cardNode">
                                        <div>
                                            <xsl:value-of select="mask:getCutCardNumber($cardNode/@key)"/>&nbsp;
                                            [<xsl:value-of select="$cardNode/field[@name='name']"/>]
                                            <xsl:value-of select="mu:getCurrencySign($cardNode/field[@name='currencyCode'])"/>
                                        </div>
                                    </xsl:if>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:when>
                        <xsl:otherwise>
                            капитализация процентов на счете по вкладу
                        </xsl:otherwise>
                    </xsl:choose>
                </b>
            </xsl:with-param>
        </xsl:call-template>

         <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Статус документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
               <div id="state">
                    <span onmouseover="showLayer('state','stateDescription');"
                          onmouseout="hideLayer('stateDescription');" class="link">
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
                </div>
            </xsl:with-param>
        </xsl:call-template>


        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName"    select="''"/>
            <xsl:with-param name="required"   select="'false'"/>
            <xsl:with-param name="isAllocate" select="'false'"/>
            <xsl:with-param name="rowValue">
                <b><a href="#" name="licenseLink" class="link" onclick="onClickLicenseAgreement(); return false;">Просмотр условий дополнительного соглашения</a></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="state = 'SAVED'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName" select="''"/>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <input type="checkbox" id="licenseAgree"  name="licenseAgree" value="" style="vertical-align: middle;" disabled="disabled"/>
                        <b>&nbsp;С условиями дополнительного соглашения согласен</b>
                    <div class="payments-legend">
                        Ознакомьтесь с условиями дополнительного соглашения к договору о вкладе, щелкнув по ссылке «Просмотр условий дополнительного соглашения». Затем установите флажок в поле «С условиями дополнительного соглашения согласен» и нажмите на кнопку «Подтвердить»
                    </div>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>
        </xsl:if>
        <script>
            payInput.fieldError('licenseAgree');
        </script>
    </xsl:template>

    <!-- шаблон формирующий div описания -->
    <xsl:template name="buildDescription">
        <xsl:param name="text"/>

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
        <xsl:param name="lineId"/>                      <!--идентификатор строки-->
        <xsl:param name="required" select="'true'"/>    <!--параметр обязатьльности заполнения-->
        <xsl:param name="rowName"/>                     <!--описание поля-->
        <xsl:param name="rowValue"/>                    <!--данные-->
        <xsl:param name="description"/>                	<!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
        <xsl:param name="rowStyle"/>                    <!-- Стиль поля -->
        <xsl:param name="isAllocate" select="'true'"/>  <!-- Выделить при нажатии -->
        <!-- Необязательный параметр -->
        <xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->

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

        <!-- Устанавливаем события onfocus поля -->
        <xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
            <script type="text/javascript">
            if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
            document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
            </script>
        </xsl:if>

    </xsl:template>


    <xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
		</xsl:choose>
	</xsl:template>
    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='ERROR'">Отклонено банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
        <!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="validatorsError.xml" srcSchemaRoot="" AssociatedInstance="file:///c:/Flash/v1.18/Business/settings/payments/SBRF/InternalPayment/validatorsError.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
