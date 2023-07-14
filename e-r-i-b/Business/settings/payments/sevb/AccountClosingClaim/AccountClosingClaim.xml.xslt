<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>
			<!--<ground>-->
				<!--<xsl:value-of select="ground"/>-->
			<!--</ground>-->
			<amount-currency>
				<xsl:value-of select="currency" />
			</amount-currency>
			<extra-parameters>
				<xsl:variable name="receiverAccount" select="receiverAccount"/>
	            <parameter name="transferType" value="internal" type="string"/>
				<parameter name="account" value="{account}" type="string"/>
				<parameter name="state" value="{state}" type="string"/>
				<parameter name="refusing-reason" value="{refusingReason}" type="string"/>
				<parameter name="account-type" value="{accountType}" type="string"/>
				<parameter name="contract-number" value="{contractNumber}" type="string"/>
				<parameter name="opening-date" value="{openingDate}" type="string"/>
				<parameter name="finishing-date" value="{finishingDate}" type="string"/>
				<parameter name="closing-date" value="{closingDate}" type="date"/>
				<parameter name="receiver-account" value="{receiverAccount}" type="string"/>
				<parameter name="to-account-type" value="{toAccountType}" type="string"/>
				<parameter name="ground" value="{ground}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>