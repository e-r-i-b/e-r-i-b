<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
				<xsl:variable name="cardId" select="card"/>
				<xsl:variable name="cards" select="document('cards.xml')/entity-list/entity[@key=$cardId]"/>
				<xsl:variable name="cardType" select="$cards/field[@name='type']/text()"/>
				<parameter name="full-name"         value="{fullName}"        type="string"/>
				<parameter name="phone"             value="{phone}"           type="string"/>
				<parameter name="card"              value="{card}"            type="string"/>
				<parameter name="card-type"         value="{$cardType}"       type="string"/>
				<parameter name="amount-card"       value="{amountCard}"      type="string"/>
				<parameter name="amount-payment"    value="{amountPayment}"   type="string"/>
				<parameter name="card-currency"     value="{cardCurrency}"    type="string"/>
				<parameter name="payment-currency"  value="{paymentCurrency}" type="string"/>
				<parameter name="pay-date"          value="{payDate}"         type="string"/>
				<parameter name="pay-place"         value="{payPlace}"        type="string"/>
				<parameter name="end-value"         value="{endValue}"        type="string"/>
				<parameter name="return-date"       value="{returnDate}"      type="string"/>
				<parameter name="other"             value="{other}"           type="string"/>
				<parameter name="item1"             value="{item1}"           type="boolean"/>
				<parameter name="item2"             value="{item2}"           type="boolean"/>
				<parameter name="item3"             value="{item3}"           type="boolean"/>
				<parameter name="item4"             value="{item4}"           type="boolean"/>
				<parameter name="item5"             value="{item5}"           type="boolean"/>
				<parameter name="item6"             value="{item6}"           type="boolean"/>
				<parameter name="item7"             value="{item7}"           type="boolean"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>