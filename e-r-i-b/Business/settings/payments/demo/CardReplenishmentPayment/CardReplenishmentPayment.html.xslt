<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="isTemplate" select="'isTemplate'"/>
	<xsl:param name="personAvailable" select="true()"/>

	<xsl:variable name="styleClass" select="'label120'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

	<xsl:variable name="formData" select="/form-data"/>
	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:call-template name="editHtml"/>
				<xsl:call-template name="editInitValues"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>
	<xsl:template name="editHtml">
		<input id="amountCurrency"  name="amountCurrency" type="hidden"/>
		<input id="receiverAccount" name="receiverAccount" type="hidden"/>
		<input id="documentNumber" name="documentNumber" type="hidden"/>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Документ №</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="$formData/documentNumber"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата</xsl:with-param>
			<xsl:with-param name="rowValue"><input type="text" id="documentDate" name="documentDate" size="10" /></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:if test="$personAvailable">
				<select id="account" name="account" onchange="javascript:updateCurrencyCodes()">
					<xsl:if test="$isTemplate = 'true'">
						<option value="">Не задан</option>
					</xsl:if>
					<xsl:for-each select="document('active-accounts.xml')/entity-list/*">
						<option>
							<xsl:attribute name="value">
								<xsl:value-of select="./@key"/>
							</xsl:attribute>
							<xsl:value-of select="./@key"/>
							&nbsp;
							[<xsl:value-of select="./field[@name='type']"/>]
							<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
							&nbsp;
							<xsl:value-of select="./field[@name='currencyCode']"/>
						</option>
					</xsl:for-each>
				</select>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="account" name="account" disabled="disabled"><option selected="selected">Счет клиента</option></select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Пластиковая карта</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:if test="$personAvailable">
				<select id="cardNumber" name="cardNumber" onchange="javascript:updateCardAccount();">
					<xsl:if test="$isTemplate = 'true'">
						<option value="">Не задан</option>
					</xsl:if>
				    <xsl:for-each select="document('active-cards.xml')/entity-list/entity">
						<option>
							<xsl:attribute name="value">
								<xsl:value-of select="field[@name='cardLinkId']/text()"/>
							</xsl:attribute>
							<xsl:value-of select="concat(substring(./@key, 1, 1), '..', substring(./@key, string-length(./@key)-3, 4))"/>
							&nbsp;
							[<xsl:value-of select="./field[@name='type']"/>]
							<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
							&nbsp;
							<xsl:value-of select="./field[@name='currencyCode']"/>
						</option>
					</xsl:for-each>
				</select>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="cardNumber" name="cardNumber" disabled="disabled"><option selected="selected">Карта клиента</option></select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="amount" name="amount" type="text" size="24"/></xsl:with-param>
		</xsl:call-template>

		<script type="text/javascript">

            var currencyCodes = new Array();
			var cards = new Array();

            function updateCurrencyCodes()
            {
                var fromAccountSelect         = document.getElementById("account");
                var code                     = currencyCodes[fromAccountSelect.value];
                var amountCurrency   = document.getElementById("amountCurrency");
                amountCurrency.value = code;
			}

			function updateCardAccount()
			{
				var cardNumber      = document.getElementById("cardNumber");
				var card            = cards[cardNumber.value];
				var cardAccount     = document.getElementById("receiverAccount");
				cardAccount.value   = card;
			}


            function init()
            {
            <xsl:variable name="allAccounts" select="document('all-accounts.xml')/entity-list/*"/>
            <xsl:for-each select="$allAccounts">
                currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
            </xsl:for-each>
			currencyCodes[""] = "";
			<xsl:for-each select="document('active-cards.xml')/entity-list/entity">
					cards[<xsl:value-of select="field[@name='cardLinkId']/text()"/>] = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';
			</xsl:for-each>

                updateCurrencyCodes();
				updateCardAccount();
            }

            init();

		<![CDATA[
		function findAccountByNumber(number)
		{
			for ( var i = 0; i < accounts.length; i++ )
			{
				if ( accounts[i].number == number )
					return accounts[i];
			}
		}

		function refreshTexts()
		{
			var cardSelect = document.getElementById("cardNumber");
			if (cardSelect.selectedIndex < 0)
				cardSelect.selectedIndex = 0;
			var cardObject = cardSelect.options[cardSelect.selectedIndex].cardObject;

			if ( cardObject == null ) {
				return;
			}

			setValue("receiverAccount", cardObject.account);
		}
		]]>
		</script>
	</xsl:template>

	<xsl:template name="editInitValues">
		<script type="text/javascript">
		function setInitValue(elementId, value)
		{
			var elem = document.getElementById(elementId);
			if (elem != null)
			{
				if (elem.value != null)
					elem.value = value;
				else if(elem.innerHTML != null)
					elem.innerHTML = value;
			}
		}

		<xsl:call-template name="init-values">
		   <xsl:with-param name="form-data" select="$formData"/>
		</xsl:call-template>

		</script>

	</xsl:template>

	<xsl:template match="/form-data" mode="view">

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
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="account"/>[<xsl:value-of select="accountType"/>]
				<xsl:value-of select="accountCurrency"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Пластиковая карта</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="concat(substring(cardNumber, 1, 1), '..', substring(cardNumber, string-length(cardNumber)-3, 4), ' ', cardType)"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Cумма</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of  select="amount"/>&nbsp;<xsl:value-of select="amountCurrency"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Комиссия</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="commissionAmount"/>&nbsp;<xsl:value-of select="amountCurrency"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Статус документа</xsl:with-param>
			<xsl:with-param name="rowValue">
				<div id="state">
					<a onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" style="text-decoration:underline">
						<xsl:call-template name="state2text">
							<xsl:with-param name="code">
								<xsl:value-of select="state"/>
							</xsl:with-param>
						</xsl:call-template>
					</a>
				</div>
			</xsl:with-param>
		</xsl:call-template>

	</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Введен</xsl:when>
			<xsl:when test="$code='INITIAL'">Введен</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Обрабатывается</xsl:when>
			<xsl:when test="$code='DELAYED_DISPATCH'">Ожидание обработки</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='RECALLED'">Отозван</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="init-values">
		<xsl:param name="form-data"/>
		<xsl:choose>
			<xsl:when test="$form-data">
				<xsl:for-each select="$form-data/*">
					<xsl:if test="string-length(text()) > 0">
				setInitValue('<xsl:value-of select="name()"/>', '<xsl:value-of select="text()"/>');
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
		</xsl:choose>
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