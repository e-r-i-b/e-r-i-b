<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">
                          <!ENTITY quot "&#034;">]>
<xsl:stylesheet version="1.0"
                xmlns:xsl   ="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan ="http://xml.apache.org/xalan"
                xmlns:mu    ="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:au    ="com.rssl.phizic.business.accounts.AccountsUtil">

  	<xsl:output version="1.0" encoding="windows-1251" indent="yes" method="html"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>

  	<xsl:param name="mode"    select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>

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

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="accountEntity"    select="document( concat('deposit-change-min-balance-account-info.xml?accountId=', accountId) )/entity"/>
        <xsl:variable name="ratesEntities"    select="document( concat('deposit-change-min-balance-rates.xml?accountId=',        accountId) )/entity-list"/>
        <xsl:variable name="accountCurrency"  select="string($accountEntity/field[@name='accountCurrency']/text())"/>
        <xsl:variable name="currencySign"     select="mu:getCurrencySign( $accountCurrency )"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">documentNumber</xsl:with-param>
            <xsl:with-param name="required"   select="'false'"/>
            <xsl:with-param name="isAllocate" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <b>
                    <xsl:value-of select="documentNumber"/>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id"         select="''"/>
            <xsl:with-param name="required"   select="'false'"/>
            <xsl:with-param name="isAllocate" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of select="documentDate"/>
                </b>
                <input type="hidden" name="documentDate" value="{documentDate}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id"         select="''"/>
            <xsl:with-param name="required"   select="'false'"/>
            <xsl:with-param name="isAllocate" select="'false'"/>
            <xsl:with-param name="rowName">Вклад:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="accountNumber"             select="$accountEntity/field[@name='accountNumber']/text()"/>
                <xsl:variable name="accountDescription"        select="$accountEntity/field[@name='accountDescription']/text()"/>

                <b>
                    <xsl:value-of select="au:getFormattedAccountNumber($accountNumber)"/>&nbsp;
                    &quot;<xsl:value-of select="$accountDescription"/>&quot;
                    &nbsp;<xsl:value-of select="$currencySign"/>
                </b>

                <input type="hidden" name="accountId"          value="{accountId}"/>
                <input type="hidden" name="accountNumber"      value="{$accountNumber}"/>
                <input type="hidden" name="accountDescription" value="{$accountDescription}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">minDepositBalance</xsl:with-param>
            <xsl:with-param name="lineId">minDepositBalanceRow</xsl:with-param>
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Неснижаемый остаток:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <select id="minDepositBalance" name="minDepositBalance"/>
                <input type="hidden" name="minDepositBalanceCurrency" value="{$accountCurrency}"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">interestRate</xsl:with-param>
            <xsl:with-param name="lineId">interestRateRow</xsl:with-param>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Процентная ставка:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" value="{interestRate}" name="interestRate"/>

                <b>
                    <span id="interestRateResult"/>&nbsp;%
                </b>
                <div class="payments-legend">рассчитывается автоматически</div>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <script type="text/javascript">
            var depositMinBalancesElements =
            {
                minBalanceSelElem    : document.getElementById('minDepositBalance'),
                interestRateSpanElem : document.getElementById('interestRateResult'),
                interestRateInput    : document.getElementsByName('interestRate')[0],

                init : function(depositMinBalances)
                {
                    if (depositMinBalances == null || depositMinBalances.length == 0)
                    {
                        return;
                    }

                    for (var i=0; i&lt;depositMinBalances.length; i++)
                    {
                        var item = depositMinBalances[i];

                        var option   = document.createElement('option');
                        option.text  = formatMoney(item.minBalance) + '&nbsp;<xsl:value-of select="$currencySign"/>';
                        option.value = item.minBalance;

                        if (this.interestRateInput.value == item.interestRate)
                        {
                            option.selected                     = 'selected';
                            this.interestRateSpanElem.innerHTML = item.interestRate;
                        }

                        this.minBalanceSelElem.add(option, this.minBalanceSelElem.options[null]);
                    }

                    this.minBalanceSelElem.onchange = depositMinBalancesElements.onchange;
                },

                onchange : function()
                {
                    var sel    = depositMinBalancesElements.minBalanceSelElem.selectedIndex;
                    var option = depositMinBalancesElements.minBalanceSelElem.options[ sel ];
                    var dmBal  = depositMinBalances.findDepositMinBalanceByMinBalance(option.value);

                    if (dmBal == null)
                    {
                        return;
                    }

                    depositMinBalancesElements.interestRateInput.value        = dmBal.interestRate;
                    depositMinBalancesElements.interestRateSpanElem.innerHTML = dmBal.interestRate;
                }
            };

            function depositMinBalance(minBalance, interestRate)
            {
                this.minBalance       = minBalance;
                this.interestRate     = interestRate;
            };

            var depositMinBalances =
            {
                values : [],

                add    : function (minBalance, interestRate)
                {
                    this.values.push(new depositMinBalance(minBalance, interestRate));
                },

                findDepositMinBalanceByMinBalance : function(minBalance)
                {
                    for(var i=0; i&lt;this.values.length; i++)
                    {
                        var item = this.values[i];

                        if (item.minBalance == minBalance)
                        {
                            return item;
                        }
                    }

                    return null;
                }
            };

            <xsl:call-template name="initMinBalances">
                <xsl:with-param name="source" select="$ratesEntities"/>
            </xsl:call-template>

            depositMinBalancesElements.init( depositMinBalances.values );
        </script>
    </xsl:template>

    <xsl:template mode="view" match="/form-data">
        <script type="text/javascript">
            function onClickLicenseAgreement()
            {
                var licenseAgree = document.getElementById('licenseAgree');
                if(licenseAgree.disabled != null)
                {
                    licenseAgree.removeAttribute('disabled');
                }

                showLicenseAgreement();

            };

            function checkClientAgreesCondition(buttonName)
            {
                var checked = getElement('licenseAgree').checked;
                if (checked == false)
                {
                     addError('Для того чтобы подтвердить заявку, ознакомьтесь с условиями договора, щелкнув по ссылке «Просмотр условий дополнительного соглашения». Если Вы согласны с  условиями, то установите флажок в поле «С условиями дополнительного соглашения согласен» и нажмите кнопку «Подтвердить».');
                     payInput.fieldError('licenseAgree');
                }

                return checked;
            };

            function showLicenseAgreement()
            {
                var win = window.open("<xsl:value-of select="$webRoot"/>/private/deposit/collateralAgreement.do?documentId=" + getQueryStringParameter('id'), 'IMALicense', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=750, height=500");
                win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
            }
        </script>

        <xsl:variable name="minDepositBalanceCurrency" select="mu:getCurrencySign(minDepositBalanceCurrency)"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of select="documentNumber"/>
                </b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of select="documentDate"/>
                </b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Вклад:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of select="accountNumber"/>&nbsp;
                    &quot;<xsl:value-of select="accountDescription"/>&quot;
                    &nbsp;<xsl:value-of select="$minDepositBalanceCurrency"/>
                </b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Неснижаемый остаток:</xsl:with-param>
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of select="minDepositBalance"/>&nbsp;
                    <xsl:value-of select="$minDepositBalanceCurrency"/>
                </b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Процентная ставка:</xsl:with-param>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="interestRate"/>&nbsp;%</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

		<xsl:call-template name="standartRow">
            <xsl:with-param name="isAllocate" select="'false'"/>
			<xsl:with-param name="rowName">Статус документа:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <div id="state">
                     <span onmouseover="showLayer('state','stateDescription');"
                        onmouseout="hideLayer('stateDescription');" style="text-decoration:underline" class="link">
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

        <xsl:if test="state = 'SAVED'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName"    select="''"/>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <b><a href="#" name="licenseLink" class="link" onclick="onClickLicenseAgreement(); return false;">Просмотр условий дополнительного соглашения</a></b>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName" select="''"/>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <label for="licenseAgree">
                        <input id="licenseAgree" name="licenseAgree" type="checkbox" disabled="disabled" style="vertical-align: middle;"/>&nbsp;с условиями дополнительного соглашения согласен
                    </label>
                    <div class="payments-legend">
                        Ознакомьтесь с условиями дополнительного соглашения к договору о вкладе, щелкнув по ссылке «Просмотр условий дополнительного соглашения». Затем установите флажок в поле «С условиями дополнительного соглашения согласен» и нажмите на кнопку «Подтвердить»
                    </div>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>
            <script>
                payInput.fieldError('licenseAgree');
            </script>
        </xsl:if>

        <xsl:if test="state = 'EXECUTED'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName" select="''"/>
                <xsl:with-param name="rowValue">
                    <b><a href="#" name="licenseLink" class="link" onclick="showLicenseAgreement(); return false;">Просмотр условий дополнительного соглашения</a></b>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template name="initMinBalances">
        <xsl:param name="source"/>
        <xsl:for-each select="$source/entity">
            depositMinBalances.add(<xsl:value-of select="field[@name='minDepositBalance']/text()"/>, <xsl:value-of select="field[@name='interestRate']/text()"/>);
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="simpleLink">
        <xsl:param name="url"/>
        <xsl:param name="title"/>
        <div class="form-row">
            <div class="paymentLabel"/>
            <div class="paymentValue">
                <div class="paymentInputDiv">
                    <b><span class="link" onclick="openExternalLink('{$url}'); return false;"><xsl:value-of select="$title"/></span></b>
                </div>
            </div>
            <div class="clear"/>
        </div>
    </xsl:template>

    <xsl:template name="standartRow">
        <xsl:param name="id"          select="''"/>
        <xsl:param name="rowName"/>                     <!--описание поля-->
        <xsl:param name="rowValue"/>                    <!--данные-->
        <xsl:param name="lineId"      select="''"/>     <!--идентификатор строки-->
        <xsl:param name="required"    select="'true'"/> <!--параметр обязатьльности заполнения-->
        <xsl:param name="rowStyle"    select="''"/>     <!-- Стиль поля -->
        <xsl:param name="isAllocate"/>                  <!-- Выделить при нажатии -->
        <!-- Необязательный параметр -->
        <xsl:param name="fieldName"   select="''"/>     <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->

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
                <xsl:otherwise>0</xsl:otherwise>
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
                <div class="errorDiv" style="display: none;"/>
            </div>
            <div class="clear"/>
        </div>

        <!-- Устанавливаем события onfocus поля -->
        <xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
            <script type="text/javascript">
                if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
                {
                    document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
                }
            </script>
        </xsl:if>
    </xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='ERROR'">Отклонено банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>