<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:param name="isTemplate" select="'isTemplate'"/>
	
	<xsl:variable name="styleClass" select="'label120'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="'width:170px'"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:call-template name="initCurrencyScript"/>
				<xsl:call-template name="initAccountsScript"/>
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit">
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<tr>
			<td colspan="2">
				<table cellpadding="0" cellspacing="0">

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
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Операция</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="type" select="type"/>
					<select id="purchaseSelect" name="type" onchange="javascript:setDiv()" onkeypress="javascript:setDiv()" onkeydown="javascript:setDiv()" onkeyup="javascript:setDiv()">
						<option>
							<xsl:attribute name="value">BUY</xsl:attribute>
							<xsl:if test="$type = 'BUY'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Купить валюту
						</option>
						<option>
							<xsl:attribute name="value">SALE</xsl:attribute>
							<xsl:if test="$type = 'SALE'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Продать валюту
						</option>
					</select>
			</xsl:with-param>
		</xsl:call-template>

				</table>
			</td>

		</tr>
		<tr>
			<td colspan="2">
				<table id="purchaseDiv" cellpadding="0" cellspacing="0" class="MaxSize">

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Вид валюты для покупки</xsl:with-param>
			<xsl:with-param name="id">currencyTypeTd</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="foreignCurrency" select="foreignCurrency"/>
				<select id="currencyCodeSelect" name="foreignCurrency"
				  onchange="javascript:setCurrencyCode()"
				  onkeypress="javascript:setCurrencyCode()"
				  onkeydown="javascript:setCurrencyCode()"
				  onkeyup="javascript:setCurrencyCode()">
					<xsl:if test="$isTemplate = 'true'">
						<option value="">Не задан</option>
					</xsl:if>
					<option>
						<xsl:attribute name="value">USD</xsl:attribute>
						<xsl:if test="$foreignCurrency = 'USD'">
							<xsl:attribute name="selected">true</xsl:attribute>
						</xsl:if>
						Доллар США
					</option>
					<option>
						<xsl:attribute name="value">EUR</xsl:attribute>
						<xsl:if test="$foreignCurrency = 'EUR'">
							<xsl:attribute name="selected">true</xsl:attribute>
						</xsl:if>
						ЕВРО
					</option>
				</select>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Сумма покупаемой валюты</xsl:with-param>
			<xsl:with-param name="id">chargeOffAmountTd</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="chargeOffAmount" size="24" name="amount" onchange="javascript:setDestinationAmount()">
					<xsl:attribute name="value">
						<xsl:choose>
							<xsl:when test="type = 'BUY'">
								<xsl:value-of select="destinationAmount"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="chargeOffAmount"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:attribute>
				</input>
				<span id="currencyChargeCode"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Курс банка продажи валюты</xsl:with-param>
			<xsl:with-param name="id">currencyRateTd</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type ="text" id="currencyRate" name="currencyRate" size="10" readonly="true">
					<xsl:attribute name="value">
						<xsl:value-of select="currencyRate"/>
					</xsl:attribute>
				</input>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Сумма для списания средств на покупку валюты</xsl:with-param>
			<xsl:with-param name="id">destinationAmountTd</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="destinationAmount" name="destinationAmount" size="10" readonly="true">
					<xsl:attribute name="value">
						<xsl:choose>
							<xsl:when test="type = 'BUY'">
								<xsl:value-of select="chargeOffAmount"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="destinationAmount"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:attribute>
				</input>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Счет списания средств на покупку валюты</xsl:with-param>
			<xsl:with-param name="id">chargeOffAccountTd</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="payerAccount" select="fromAccountSelect"/>
				<xsl:if test="$personAvailable">
					<select id = "fromAccountSelect" name="fromAccountSelect">
						<xsl:for-each select="document('active-rur-accounts.xml')/entity-list/*">
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
					</select>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id = "fromAccountSelect" name="fromAccountSelect" disabled="disabled">
						<option value="" selected="selected">Счет клиента</option>
					</select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Счет для зачисления купленной валюты</xsl:with-param>
			<xsl:with-param name="id">destinationAccountTd</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:if test="$personAvailable">
					<select id="toAccountSelect" name="toAccountSelect"/>
			    </xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id = "toAccountSelect" name="toAccountSelect" disabled="disabled">
						<option value="" selected="selected">Счет клиента</option>
					</select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

				</table>
			</td>
		</tr>

		<script type="text/javascript">
		<![CDATA[

		//Функция setDiv очищает поле сумма
		function setDiv()
		{
			selectOperationType();
			var chargeOffAmountElem = document.getElementById("chargeOffAmount");
            chargeOffAmountElem.value = "";
            setDestinationAmount();
            setCurrencyCode();
		}

		function selectOperationType()
		{
			var purchaseSelect = document.getElementById("purchaseSelect");

			if (purchaseSelect.value == "SALE")
			{
				document.getElementById("currencyTypeTd").innerHTML = "Вид валюты для продажи";
				document.getElementById("chargeOffAmountTd").innerHTML = "Сумма продаваемой валюты";
				document.getElementById("currencyRateTd").innerHTML = "Курс банка покупки валюты";
				document.getElementById("destinationAmountTd").innerHTML = "Сумма средств за проданную валюту";
				document.getElementById("chargeOffAccountTd").innerHTML = "Счет для списания средств на продажу валюты";
				document.getElementById("destinationAccountTd").innerHTML = "Счет для зачисления средств, полученных от продажи валюты";
				document.getElementById("destinationAmount").name = "destinationAmount";
				document.getElementById("chargeOffAmount").name = "chargeOffAmount";
			}
			else
			{
				document.getElementById("currencyTypeTd").innerHTML = "Вид валюты для покупки";
				document.getElementById("chargeOffAmountTd").innerHTML = "Сумма покупаемой валюты";
				document.getElementById("currencyRateTd").innerHTML = "Курс банка продажи валюты";
				document.getElementById("destinationAmountTd").innerHTML = "Сумма для списания средств на покупку валюты";
				document.getElementById("chargeOffAccountTd").innerHTML = "Счет списания средств на покупку валюты";
				document.getElementById("destinationAccountTd").innerHTML = "Счет для зачисления купленной валюты";
				document.getElementById("chargeOffAmount").name = "destinationAmount";
				document.getElementById("destinationAmount").name = "chargeOffAmount";
			}
		}

		function setDestinationAmount()
		{
			var rate   = document.getElementById("currencyRate").value;
			var amount = document.getElementById("chargeOffAmount").value;
			if (rate*amount > 600000)
			{
				alert("Сумма операции превышает сумму 600 000 рублей")
				return false;
			}
			else
			{
				var destination = document.getElementById("destinationAmount");
				destination.value = (rate*amount).toFixed(2);
			}
			return true;
		}
			
		function setCurrencyCode()
		{
			var currency = document.getElementById("currencyCodeSelect");
			var code     = currency[currency.selectedIndex].value;
			
			var currencyCodeElem       = document.getElementById("currencyChargeCode");
			currencyCodeElem.innerHTML = code;

			setCurrency();
			refreshCurrencyAccount();
		}

		function init()
		{
			var currencyTypeElem = document.getElementById("currencyCodeSelect");
			if (currencyTypeElem.value == "")
			{
				currencyTypeElem.value = "USD";
			}
			document.getElementById("currencyChargeCode").innerHTML = currencyTypeElem.value;
			selectOperationType();
			setCurrency();
			refreshCurrencyAccount();
		}

		function setCurrency()
		{
			var purSelect = document.getElementById("purchaseSelect").value;
			var operation = purSelect == 'SALE' ? 'BUY' : 'SALE';

			var selectCurr = document.getElementById("currencyCodeSelect");
			var name = selectCurr[selectCurr.selectedIndex].value;
			var field = document.getElementById("currencyRate");
			field.value = currencies[name][operation];
			setDestinationAmount();
		}

		function refreshCurrencyAccount()
		{
			if (document.getElementById("purchaseSelect").value == 'BUY')
			{
				var selectAccount = document.getElementById("toAccountSelect");
				var rurSelectAccount = document.getElementById("fromAccountSelect");
				var selectCurr = document.getElementById("currencyCodeSelect");
				var name = selectCurr[selectCurr.selectedIndex].value;
			}
			else
			{
				var selectAccount = document.getElementById("fromAccountSelect");
				var rurSelectAccount = document.getElementById("toAccountSelect");
				var selectCurr = document.getElementById("currencyCodeSelect");
				var name = selectCurr[selectCurr.selectedIndex].value;
			}

			selectAccount.options.length = 0;
			rurSelectAccount.options.length = 0;
			]]>
			<xsl:choose>
			<xsl:when test="$isTemplate = 'true'">
				payerAccount = '<xsl:value-of select="fromAccountSelect"/>';
				toAccount = '<xsl:value-of select="toAccountSelect"/>';
				<![CDATA[
			    selectAccount.options[0] = new Option("Не задан", "");

			    for ( var i = 0; i < accounts.length; i++ )
				{
					if ( accounts[i].currency == name)
					{
						var currentAccount = accounts[i].number+"["+accounts[i].type+"] "+accounts[i].amount+ " "+accounts[i].currency;
						selectAccount.options[selectAccount.options.length] = new Option(currentAccount, accounts[i].number);
						if(accounts[i].number == toAccount || accounts[i].number == payerAccount)
							selectAccount.options[selectAccount.options.length-1].selected = true;
					}
				}
				if ( selectAccount.options.length == 1 )
				{
					selectAccount.options.length = 0;
					selectAccount.options[selectAccount.options.length] = new Option("Нет доступных счетов в данной валюте", "");
				}

                rurSelectAccount.options[0] = new Option("Не задан", "");
				for ( var i = 0; i < rurAccounts.length; i++ )
				{
					var currentAccount = rurAccounts[i].number+"["+rurAccounts[i].type+"] "+rurAccounts[i].amount+ " "+rurAccounts[i].currency;
					rurSelectAccount.options[rurSelectAccount.options.length] = new Option(currentAccount, rurAccounts[i].number);
					if(rurAccounts[i].number == payerAccount || rurAccounts[i].number == toAccount)
						rurSelectAccount.options[rurSelectAccount.options.length-1].selected = true;					
				}
				if ( rurSelectAccount.options.length == 1 )
				{
					rurSelectAccount.options.length = 0;
					rurSelectAccount.options[rurSelectAccount.options.length] = new Option("Нет доступных счетов в рублях", "");
				}
				]]>
			</xsl:when>
			<xsl:otherwise>
			<![CDATA[
			for ( var i = 0; i < accounts.length; i++ )
			{
				if ( accounts[i].currency == name)
				{
					var currentAccount = accounts[i].number+"["+accounts[i].type+"] "+accounts[i].amount+ " "+accounts[i].currency;
					selectAccount.options[selectAccount.options.length] = new Option(currentAccount, accounts[i].number);
				}
			}
			if ( selectAccount.options.length == 0 )
			{
				selectAccount.options[selectAccount.options.length] = new Option("Нет доступных счетов в данной валюте", "");
			}

			for ( var i = 0; i < rurAccounts.length; i++ )
			{
				var currentAccount = rurAccounts[i].number+"["+rurAccounts[i].type+"] "+rurAccounts[i].amount+ " "+rurAccounts[i].currency;
				rurSelectAccount.options[rurSelectAccount.options.length] = new Option(currentAccount, rurAccounts[i].number);
			}
			if ( rurSelectAccount.options.length == 0 )
			{
				rurSelectAccount.options[rurSelectAccount.options.length] = new Option("Нет доступных счетов в рублях", "");
			}
			]]>
		    </xsl:otherwise>
			</xsl:choose>
		}

		init();
		</script>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">
		<input id="type" name="type" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="type"/>
	            </xsl:attribute>
		</input>
		<tr>
			<td colspan="2">
				<table cellspacing="2" cellpadding="0" border="0" class="paymentFon">

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Номер документа</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Дата документа</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="documentDate"/></xsl:with-param>
			</xsl:call-template>

			<xsl:variable name="adDate" select="admissionDate"/>
			<xsl:if test="not($adDate = '')">
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">Дата приема платежа</xsl:with-param>
					<xsl:with-param name="rowValue"><xsl:value-of select="admissionDate"/></xsl:with-param>
				</xsl:call-template>
			</xsl:if>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Сумма покупаемой валюты</xsl:with-param>
				<xsl:with-param name="id">chargeOffAmountTd</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:choose>
						<xsl:when test="type = 'SALE'">
							<xsl:value-of select="chargeOffAmount"/>&nbsp;
						</xsl:when>
						<xsl:when test="type = 'BUY'">
							<xsl:value-of select="destinationAmount"/>&nbsp;
						</xsl:when>
					</xsl:choose>
					<xsl:value-of select="foreignCurrency"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Курс банка продажи валюты</xsl:with-param>
				<xsl:with-param name="id">currencyRateTd</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of select="currencyRate"/>&nbsp;
					<xsl:value-of select="rurCurrency"/>/<xsl:value-of select="foreignCurrency"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Сумма для списания средств на покупку валюты</xsl:with-param>
				<xsl:with-param name="id">destinationAmountTd</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:choose>
						<xsl:when test="type = 'BUY'">
							<xsl:value-of select="chargeOffAmount"/>&nbsp;
						</xsl:when>
						<xsl:when test="type = 'SALE'">
							<xsl:value-of select="destinationAmount"/>&nbsp;
						</xsl:when>
					</xsl:choose>
					<xsl:value-of select="rurCurrency"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Счет списания средств на покупку валюты</xsl:with-param>
				<xsl:with-param name="id">chargeOffAccountTd</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of select="fromAccountSelect"/>[<xsl:value-of select="fromAccountType"/>]
					<xsl:value-of select="fromAccountCurrency"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Счет для зачисления купленной валюты</xsl:with-param>
				<xsl:with-param name="id">destinationAccountTd</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of select="toAccountSelect"/>[<xsl:value-of select="toAccountType"/>]
					<xsl:value-of select="toAccountCurrency"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Статус документа</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:call-template name="state2text">
						<xsl:with-param name="code">
							<xsl:value-of select="state"/>
						</xsl:with-param>
					</xsl:call-template>
				</xsl:with-param>
			</xsl:call-template>
				</table>
			</td>
		</tr>
	<xsl:if test="(state='REFUSED') and (stateDescription != '')">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Причина отказа:<xsl:value-of select="stateDescription"/>
		</div>
	</xsl:if>
		<script type="text/javascript">
		function init()
		{
			var purchaseSelect = '<xsl:value-of select="type"/>';

			if (purchaseSelect == "SALE")
			{
				document.getElementById("chargeOffAmountTd").innerHTML = "Сумма продаваемой валюты";
				document.getElementById("currencyRateTd").innerHTML = "Курс банка покупки валюты";
				document.getElementById("destinationAmountTd").innerHTML = "Сумма средств за проданную валюту";
				document.getElementById("chargeOffAccountTd").innerHTML = "Счет для списания средств на продажу валюты";
				document.getElementById("destinationAccountTd").innerHTML = "Счет для зачисления средств, полученных от продажи валюты";
			}
			else
			{
				document.getElementById("chargeOffAmountTd").innerHTML = "Сумма покупаемой валюты";
				document.getElementById("currencyRateTd").innerHTML = "Курс банка продажи валюты";
				document.getElementById("destinationAmountTd").innerHTML = "Сумма для списания средств на покупку валюты";
				document.getElementById("chargeOffAccountTd").innerHTML = "Счет списания средств на покупку валюты";
				document.getElementById("destinationAccountTd").innerHTML = "Счет для зачисления купленной валюты";
			}
		}
		init();
		</script>

	</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Введен</xsl:when>
			<xsl:when test="$code='INITIAL'">Введен</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Обрабатывается</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='RECALLED'">Отозван</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="initCurrencyScript">
		<script type="text/javascript">
			<xsl:variable name="currency" select="document('account-card-currency.xml')/entity-list/entity"/>
			var currencies = new Array();
			currencies[''] = 0;
            <xsl:for-each select="$currency">
                <xsl:variable name="optCurrency" select="@key"/>
				rate = new Array()
				<xsl:for-each select="*">

				rate['<xsl:value-of select="@name"/>'] = '<xsl:value-of select="./text()"/>';
				</xsl:for-each>
				currencies['<xsl:value-of select="@key"/>'] = rate;

			</xsl:for-each>
		</script>
	</xsl:template>

	<xsl:template name="initAccountsScript">
		<script type="text/javascript">

			var accounts = new Array();
			var account;
			<xsl:for-each select="document('active-foreign-currency-accounts.xml')/entity-list/*">
			account          = new Object()
			account.number   = '<xsl:value-of select="@key"/>';
			account.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
			account.amount   = '<xsl:value-of select="field[@name='amountDecimal']/text()"/>';
			account.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
			accounts[accounts.length] = account;
			</xsl:for-each>

			var rurAccounts = new Array();
			var rurAccount;
			<xsl:for-each select="document('active-rur-accounts.xml')/entity-list/*">
			rurAccount          = new Object()
			rurAccount.number   = '<xsl:value-of select="@key"/>';
			rurAccount.currency = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
			rurAccount.amount   = '<xsl:value-of select="field[@name='amountDecimal']/text()"/>';
			rurAccount.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
			rurAccounts[rurAccounts.length] = rurAccount;
			</xsl:for-each>
		</script>
	</xsl:template>

	<xsl:template name="standartRow">

		<xsl:param name="id"/>
		<xsl:param name="required" select="'true'"/>
		<xsl:param name="rowName"/>
		<xsl:param name="rowValue"/>

		<tr>
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