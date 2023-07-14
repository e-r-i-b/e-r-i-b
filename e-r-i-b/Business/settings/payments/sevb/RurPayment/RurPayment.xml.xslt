<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
			<document-date>
				<xsl:value-of select="documentDate"/>
			</document-date>

			<payer-account>
				<xsl:value-of select="payerAccountSelect"/>
			</payer-account>

			<receiver-name>
				<xsl:value-of select="receiverName"/>
			</receiver-name>

			<receiver-account>
				<xsl:value-of select="receiverAccount"/>
			</receiver-account>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<amount-currency>
				<xsl:value-of select="amountCurrency"/>
			</amount-currency>

			<!--<amount-currency>RUB</amount-currency>-->

			<ground>
				<xsl:value-of select="ground"/>
			</ground>

			<extra-parameters>
				<parameter name="payer-account-type" value="{payerAccountSelectType}" type="string"/>
				<parameter name="receiver-alias" value="{receivPaySelect}" type="string"/>
				<parameter name="operation-code" value="{operationCode}" type="string"/>
				<parameter name="receiver-bic" value="{receiverBIC}" type="string"/>
				<parameter name="receiver-cor-account" value="{receiverCorAccount}" type="string"/>
				<parameter name="receiver-pay-type" value="{receivPayType}" type="string"/>
				<parameter name="bank" value="{bank}" type="string"/>
            </extra-parameters>
		</payment>
	</xsl:template>
</xsl:stylesheet>
