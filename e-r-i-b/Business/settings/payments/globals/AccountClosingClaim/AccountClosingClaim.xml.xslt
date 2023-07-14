<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<amount>
				<xsl:value-of select="amount"/>
			</amount>
			<currency>
				<xsl:value-of select="currency"/>
			</currency>
			<extra-parameters>
				<xsl:variable name="toAccount" select="toAccount"/>
				<xsl:variable name="currency" select="currency"/>
				<xsl:variable name="receiverAccount">
					<xsl:choose>
						<xsl:when test="$toAccount='AnotherBank'">
							<xsl:value-of select="receiverAccount"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$toAccount"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>

				<xsl:choose>
					<xsl:when test="$toAccount!='AnotherBank'">
						<parameter name="transferType" value="internal" type="string"/>
					</xsl:when>
					<xsl:when test="$currency='RUB'">
						<parameter name="transferType" value="ruspayment" type="string"/>
					</xsl:when>
					<xsl:otherwise>
						<parameter name="transferType" value="swiftpayment" type="string"/>
					</xsl:otherwise>
				</xsl:choose>
				<parameter name="account" value="{account}" type="string"/>
				<parameter name="state" value="{state}" type="string"/>
				<parameter name="refusing-reason" value="{refusingReason}" type="string"/>
				<parameter name="account-type" value="{accountType}" type="string"/>
				<parameter name="contract-number" value="{contractNumber}" type="string"/>
				<parameter name="opening-date" value="{openingDate}" type="string"/>
				<parameter name="closing-date" value="{closingDate}" type="date"/>
				<parameter name="to-account" value="{toAccount}" type="string"/>
				<parameter name="to-account-type" value="{toAccountType}" type="string"/>
				<parameter name="receiver-account" value="{$receiverAccount}" type="string"/>
				<parameter name="receiver-cor-account" value="{receiverCorAccount}" type="string"/>
				<parameter name="receiver-bank-name" value="{receiverBankName}" type="string"/>
				<parameter name="receiver-type" value="{receiverType}" type="string"/>
				<parameter name="receiver-name" value="{receiverName}" type="string"/>
				<parameter name="receiver-inn" value="{receiverINN}" type="string"/>
				<parameter name="receiver-kpp" value="{receiverKPP}" type="string"/>
				<parameter name="receiver-bic" value="{receiverBIC}" type="string"/>
				<parameter name="receiverCountryCode" value="{receiverCountryCode}" type="string"/>
				<parameter name="receiver-city" value="{receiverCity}" type="string"/>
				<parameter name="receiver-address" value="{receiverAddress}" type="string"/>
				<parameter name="receiver-bank-country-code" value="{receiverBankCountryCode}" type="string"/>
				<parameter name="receiver-bank-city" value="{receiverBankCity}" type="string"/>
				<parameter name="receiver-bank-address" value="{receiverBankAddress}" type="string"/>
				<parameter name="nds" value="{nds}" type="string"/>
				<parameter name="commission-amount" value="{commissionAmount}" type="string"/>
				<parameter name="ground" value="{ground}" type="string"/>
				<parameter name="payment-conditions" value="{paymentConditions}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>