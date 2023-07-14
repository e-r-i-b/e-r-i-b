<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="currencyRatesAvaliable" select="'currencyRatesAvaliable'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
    <xsl:param name="showCommission" select="'showCommission'"/>
    <xsl:param name="data-path" select="''"/>

    <xsl:variable name="styleClass" select="'Width120 LabelAll'"/>
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

   <!-- редактирование -->
    <xsl:template match="/form-data" mode="edit">

        <script type="text/javascript">
			document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
		</script>

		<input id="sellAmountCurrency" name="sellAmountCurrency" type="hidden"/>
		<input id="buyAmountCurrency"  name="buyAmountCurrency"  type="hidden"/>
        <input id="type" name="type" value="{type}" type="hidden"/>
        <input id="recalculatedAmountChanged" value="{recalculatedAmountChanged}" name="recalculatedAmountChanged" type="hidden"/>
        <!--тот курс, который мы передаем в ядро-->
        <input id="course" value="{course}" name="course" type="hidden"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа</xsl:with-param>
            <xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" id="documentDate" name="documentDate" size="10">
                    <xsl:attribute name="value">
                        <xsl:value-of select="documentDate"/>
                    </xsl:attribute>
                </input>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
            <xsl:variable name="payerAccount" select="payerAccountSelect"/>
			   <xsl:if test="$personAvailable">
					<select id="payerAccountSelect" name="payerAccountSelect" onchange="javascript:showHideOperationCode(); updateCurrencyCodes(); setRate(); setAmount();">
						<xsl:variable name="foreignCurrencyAccounts" select="document('active-accounts.xml')/entity-list/*"/>

						<xsl:choose>
							<xsl:when test="count($foreignCurrencyAccounts) = 0">
								<option value="">нет валютных счетов</option>
							</xsl:when>
							<xsl:otherwise>
								<xsl:for-each select="$foreignCurrencyAccounts">
									<option>
										<xsl:attribute name="value">
											<xsl:value-of select="./@key"/>
										</xsl:attribute>
										<xsl:if test="$payerAccount = ./@key">
											<xsl:attribute name="selected">true</xsl:attribute>
										</xsl:if>
										<xsl:value-of select="./@key"/>[<xsl:value-of select="./field[@name='type']"/>]
										<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
										&nbsp;<xsl:value-of select="./field[@name='currencyCode']"/>
									</option>
								</xsl:for-each>
							</xsl:otherwise>
						</xsl:choose>
					</select>
				</xsl:if>
            </xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет зачисления</xsl:with-param>
			<xsl:with-param name="rowValue">
            <xsl:variable name="receiverAccount" select="receiverAccountSelect"/>
				<xsl:if test="$personAvailable">
					<select id="receiverAccountSelect" name="receiverAccountSelect" onchange="javascript:showHideOperationCode();updateCurrencyCodes(); setRate(); setAmount();">
						<xsl:variable name="foreignCurrencyAccounts" select="document('active-accounts.xml')/entity-list/*"/>

						<xsl:choose>
							<xsl:when test="count($foreignCurrencyAccounts) = 0">
								<option value="">нет счетов</option>
							</xsl:when>
							<xsl:otherwise>
								<xsl:for-each select="$foreignCurrencyAccounts">
									<option>
										<xsl:attribute name="value">
											<xsl:value-of select="./@key"/>
										</xsl:attribute>
										<xsl:if test="$receiverAccount = ./@key">
											<xsl:attribute name="selected">true</xsl:attribute>
										</xsl:if>
										<xsl:value-of select="./@key"/>[<xsl:value-of select="./field[@name='type']"/>]
										<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
										&nbsp;<xsl:value-of select="./field[@name='currencyCode']"/>
									</option>
								</xsl:for-each>
							</xsl:otherwise>
						</xsl:choose>
					</select>
				</xsl:if>
            </xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сумма в валюте списания</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="sellAmount" size="24" value="{currencyRatesAbaliable}" name="sellAmount"
                         onchange="javascript:refreshBuySum();"/>&nbsp;<span id="sellCurrencyCode"></span>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:if test="$currencyRatesAvaliable = 'true'">
            <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Курс конверсии</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type ="text" id="courseForUser" value="{courseForUser}" name="courseForUser" size="10" readonly="true"/>
			</xsl:with-param>
		</xsl:call-template>
        </xsl:if>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма в валюте зачисления</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="buyAmount" size="24" value="{buyAmount}" name="buyAmount"
							onchange="javascript:refreshSellSum();"/>&nbsp;<span id="buyCurrencyCode"></span>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">operationCodeRow</xsl:with-param>
            <xsl:with-param name="rowName">Код валютной операции</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="operationCode" size="24" value="{operationCode}" name="operationCode" />&nbsp;
                <input type="button" class="buttWhite" style="height:18px;width:18;"
                   onclick="javascript:openSelectOperationTypeWindow();" value="..."/>
            </xsl:with-param>
        </xsl:call-template>
        <script type="text/javascript">
              var currencyCodes = new Array();
              var type = '<xsl:value-of select="type"/>';
              var rate;
              var accountCurrencies = new Array();
              var currencies = new Array();
              currencyRatesAvaliable = '<xsl:value-of select="$currencyRatesAvaliable"/>';

            function openSelectOperationTypeWindow()
			{
                window.open('../operationCodes.do?' + addParam2List("","operationCode","fltrCode"),
                        'OperationTypes', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
			}

            function updateCurrencyCodes()
			{
				var payerAccountElem         = document.getElementById("payerAccountSelect");
				var code                     = currencyCodes[payerAccountElem.value];

				if (code == null)
					 code = "";

				var currencyCodeElem         = document.getElementById("sellCurrencyCode");
				currencyCodeElem.innerHTML   = code;
				var sellAmountCurrencyElem   = document.getElementById("sellAmountCurrency");
				sellAmountCurrencyElem.value = code;

				var receiverAccountElem      = document.getElementById("receiverAccountSelect");
				code                         = currencyCodes[receiverAccountElem.value];

				if (code == null)
					 code = "";

				currencyCodeElem             = document.getElementById("buyCurrencyCode");
				currencyCodeElem.innerHTML   = code;

				var buyAmountCurrencyElem    = document.getElementById("buyAmountCurrency");
				buyAmountCurrencyElem.value  = code;

			}

            function setAmount(){
			    if(type == 'SALE'){
					refreshBuySum();
				}else if(type == 'BUY'){
					refreshSellSum();
				}else{
			    	return;
				}
			}

            function setAmounts()
			{
				if (type != null)
				{
					//значит, сюда мы пришли со страницы подтверждения - надо поменять суммы
					if (rate != null)
					{
						setAmount();
					}
			    }
			}

            function setRate()
            {
                if (currencyRatesAvaliable == 'false') return;

                var payCode  = currencyCodes[document.getElementById("payerAccountSelect").value];
				var buyCode  = currencyCodes[document.getElementById("receiverAccountSelect").value];
				if (payCode == buyCode)
				{
					rate = 1;
                    document.getElementById("courseForUser").value = 1;
                    document.getElementById("course").value = rate;
                    return;
                }
                else if ( payCode == 'RUB')
                {
                     document.getElementById("courseForUser").value = currencies[buyCode]['SALE'];
                }
                else if (buyCode == 'RUB')
                {
                    document.getElementById("courseForUser").value = currencies[payCode]['BUY'];
                }
                else
			    {
                    document.getElementById("courseForUser").value = rate;
				}
                var curr = document.getElementById("sellAmountCurrency").value+"|"+document.getElementById("buyAmountCurrency").value;
                rate = currencies[curr]['BUY'];
				document.getElementById("course").value = rate;
            }

            function refreshSellSum()
            {
                var amount = document.getElementById("buyAmount").value;
                var buyCurrency = document.getElementById("buyAmountCurrency").value;
                var sellCurrency = document.getElementById("sellAmountCurrency").value;
                // пользователь ввел сумму зачисления - это операция продажи банком валюты списания
                if (currencyRatesAvaliable == 'false')
                {
                    document.getElementById("sellAmount").value = '';
                }
                else if(buyCurrency == sellCurrency )
                {
                    document.getElementById("sellAmount").value = '';
                    document.getElementById("buyAmount").value = '';
                    document.getElementById("course").value = 1;
                }
                else
                {
                    var curr = buyCurrency+"|"+sellCurrency;

                    rate = currencies[curr]['SALE'];
                    document.getElementById("sellAmount").value =  Math.round((rate*amount)*100)/100;

                    //document.getElementById("courseForUser").value = rate;

                    //если что, это можно удалить.
                    if (sellCurrency == 'RUB')
                    {
                        document.getElementById("courseForUser").value =  currencies[buyCurrency]['SALE'];
                    }
                    else
                    if (buyCurrency == 'RUB')
                    {
                        document.getElementById("courseForUser").value =  currencies[sellCurrency]['BUY'];
                        //в этом случае, чтобы не шокировать пользователя странной суммой - некруглой - в валюте
                        // - установим ему ее такой, какую он ожидает.
                        document.getElementById("sellAmount").value =  Math.round((amount/document.getElementById("courseForUser").value)*100)/100;

                    }
                    else
                        document.getElementById("courseForUser").value = rate;

                }
                type = 'BUY';
				document.getElementById("type").value = type;
				return true;
            }

            /*относительно клиента - это сумма зачисления (то есть, сумма покупки)--------------------------*/
            function refreshBuySum()
            {
                var amount = document.getElementById("sellAmount").value;
                var buyCurrency = document.getElementById("buyAmountCurrency").value;
                var sellCurrency = document.getElementById("sellAmountCurrency").value;
                // пользователь ввел сумму списания - это операция покупки банком валюты списания
                if (currencyRatesAvaliable == 'false')
                {
                    document.getElementById("buyAmount").value = '';
                }
                else if (buyCurrency == sellCurrency)
                {
                    document.getElementById("buyAmount").value = '';
                    document.getElementById("sellAmount").value = '';
                    document.getElementById("course").value = 1;
                 }
                else
                {
                    var curr = sellCurrency+"|"+buyCurrency;

                    rate = currencies[curr]['BUY'];

                    document.getElementById("buyAmount").value =  Math.round((rate*amount)*100)/100;

                    //если что, это можно удалить.
                    if (sellCurrency == 'RUB')
                    {
                        document.getElementById("courseForUser").value =  currencies[buyCurrency]['SALE'];
                        //в этом случае, чтобы не шокировать пользователя странной суммой - некруглой - в валюте
                        // - установим ему ее такой, какую он ожидает.
                        document.getElementById("buyAmount").value =  Math.round((amount/document.getElementById("courseForUser").value)*100)/100;

                    }
                    else if (buyCurrency == 'RUB')
                    {
                        document.getElementById("courseForUser").value =  currencies[sellCurrency]['BUY'];
                    }
                    else
                        document.getElementById("courseForUser").value = rate;
                }

                type = 'SALE';
				document.getElementById("type").value = type;
				return true;
            }

            function showHideOperationCode()
			{
				var isRes = '<xsl:value-of select="document('currentPerson.xml')/entity-list/entity/field[@name='isResident']"/>';
                if(isRes == 'false' || isResident(document.getElementById('payerAccountSelect').value) || isResident(document.getElementById('receiverAccountSelect').value))
				{
					hideOrShow(document.getElementById("operationCodeRow"), false);
				}
				else
				{
					hideOrShow(document.getElementById("operationCodeRow"), true);
				}
			}

            function init()
			{
                <xsl:variable name="foregnCurrencyAccounts" select="document('active-accounts.xml')/entity-list/*"/>
                <xsl:for-each select="$foregnCurrencyAccounts">
                    currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
                </xsl:for-each>
                updateCurrencyCodes();
                showHideOperationCode();
            }


             <!-- получение списка счетов -->
            function initAccounts(){
                <xsl:variable name="accounts" select="document(concat($data-path,'active-accounts.xml'))"/>
                var account;
                <xsl:for-each select="$accounts/entity-list/entity">
                    account          = new Object();
                    account.number   = '<xsl:value-of select="@key"/>';
                    account.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                    accountCurrencies[account.number] = account.currency;
                </xsl:for-each>
            }

            init();
            initAccounts();

           </script>

        <xsl:if test="$currencyRatesAvaliable = 'true'">
            <script type="text/javascript">
           function initCurrencies(){
                <xsl:variable name="currency" select="document('account-card-currency.xml')/entity-list/entity"/>
                currencies[''] = 0;
                <xsl:for-each select="$currency">
                    rateAr = new Array()
                    <xsl:for-each select="*">
                        rateAr['<xsl:value-of select="@name"/>'] = '<xsl:value-of select="./text()"/>';
                    </xsl:for-each>
                    currencies['<xsl:value-of select="@key"/>'] = rateAr;
                </xsl:for-each>
            }
            initCurrencies();
            </script>
        </xsl:if>

        <script type="text/javascript">
            setRate();
            setAmounts();
        </script>

    </xsl:template>

    <!-- просмотр -->
    <xsl:template match="/form-data" mode="view">

        <xsl:variable name="amountChanged" select="recalculatedAmountChanged"/>
        <xsl:variable name="type" select="type"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Номер документа</xsl:with-param>
            <xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата документа</xsl:with-param>
            <xsl:with-param name="rowValue"><xsl:value-of select="documentDate"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет списания</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="payerAccountType"/>]
				<xsl:value-of select="payerAccountCurrency"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет зачисления</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="receiverAccountSelect"/>[<xsl:value-of select="receiverAccountType"/>]
					<xsl:value-of select="receiverAccountCurrency"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="sellAmount != '' and sellAmount != '0.00'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Сумма в валюте списания</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="$amountChanged='true' and $type='BUY'"><b><xsl:value-of select="sellAmount"/>&nbsp;</b></xsl:when>
                        <xsl:otherwise><xsl:value-of select="sellAmount"/>&nbsp;</xsl:otherwise>
                    </xsl:choose>
                    <xsl:value-of select="sellAmountCurrency"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$currencyRatesAvaliable = 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Курс конверсии</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of select="course"/>&nbsp;
                    <xsl:value-of select="sellAmountCurrency"/>/<xsl:value-of select="buyAmountCurrency"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="buyAmount != '0.00' and buyAmount != ''">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Сумма в валюте зачисления</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="$amountChanged='true' and $type='SALE'"><b><xsl:value-of select="buyAmount"/>&nbsp;</b></xsl:when>
                        <xsl:otherwise><xsl:value-of select="buyAmount"/>&nbsp;</xsl:otherwise>
				    </xsl:choose>
				<xsl:value-of select="buyAmountCurrency"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="operationCode != ''">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Код валютной операции</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="operationCode"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$showCommission = 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Комиссия</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of select="commission"/>&nbsp;<xsl:value-of select="sellAmountCurrency"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Статус платежа</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div id="state">
                    <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
                        <xsl:call-template name="state2text">
                            <xsl:with-param name="code">
                                <xsl:value-of select="state"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </span>
                </div>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Плановая дата исполнения</xsl:with-param>
            <xsl:with-param name="rowValue"><xsl:value-of  select="admissionDate"/></xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!-- шаблоны -->
    <xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='DELAYED_DISPATCH'">Исполняется банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="standartRow">
        <xsl:param name="id"/>
        <xsl:param name="lineId" />
        <xsl:param name="required" select="'true'"/>
        <xsl:param name="rowName"/>
        <xsl:param name="rowValue"/>

        <tr id="{$lineId}">
            <td class="{$styleClass}" style="{$styleSpecial}" id="{$id}">
            <xsl:copy-of select="$rowName"/>
            <xsl:if test="$required = 'true' and $mode = 'edit'">
                <span id="asterisk_{$id}" class="asterisk">*</span>
            </xsl:if>
            </td>
            <td>
                <xsl:copy-of select="$rowValue"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="rowName"/>
        <tr>
            <td colspan="2" class="{$styleClassTitle}"><xsl:copy-of select="$rowName"/></td>
        </tr>
    </xsl:template>
</xsl:stylesheet>