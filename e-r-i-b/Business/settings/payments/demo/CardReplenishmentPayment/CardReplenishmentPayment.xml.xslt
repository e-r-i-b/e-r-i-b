<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<document-date>
				<xsl:value-of select="documentDate"/>
			</document-date>

			<payer-account>
				<xsl:value-of select="account"/>
			</payer-account>

			<xsl:variable name="cardId" select="cardNumber"/>
			<xsl:variable name="cards" select="document('cards.xml')/entity-list/entity[@key=$cardId]"/>
			<xsl:variable name="cardAccount" select="$cards/field[@name='cardAccount']/text()"/>

			<receiver-account>
				<xsl:value-of select="$cardAccount"/>
			</receiver-account>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<amount-currency>
				<xsl:value-of select="amountCurrency"/>
			</amount-currency>

			<extra-parameters>
				<xsl:variable name="cardType" select="$cards/field[@name='type']/text()"/>
				<xsl:variable name="accountId" select="account"/>
				<xsl:variable name="payerAccount" select="document('all-accounts.xml')/entity-list/entity[@key=$accountId]"/>
				<xsl:variable name="accountType" select="$payerAccount/field[@name='type']/text()"/>
				<xsl:variable name="accountCurrency" select="$payerAccount/field[@name='currencyCode']/text()"/>
				<parameter name="card-type" value="{$cardType}" type="string"/>
				<parameter name="cardNumber" value="{cardNumber}" type="string"/>
				<parameter name="account-type" value="{$accountType}" type="string"/>
				<parameter name="account-currency" value="{$accountCurrency}" type="string"/>
			</extra-parameters>

		</document>
	</xsl:template>
</xsl:stylesheet>