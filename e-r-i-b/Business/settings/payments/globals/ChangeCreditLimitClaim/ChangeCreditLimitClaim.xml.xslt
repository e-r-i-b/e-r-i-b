<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
            <extra-parameters>
                <parameter name="card-number"          value="{cardNumber}"          type="string"/>
                <parameter name="card-name"        value="{cardName}"        type="string"/>
                <parameter name="card-desc"        value="{cardDesc}"        type="string"/>
                <parameter name="card-id"        value="{card-id}"        type="long"/>
                <parameter name="card-currency"         value="{cardCurrency}"         type="string"/>
                <parameter name="current-limit"     value="{currentLimit}"     type="decimal"/>
                <parameter name="current-limit-currency" value="{currentLimitCurrency}"     type="string"/>
                <parameter name="new-limit"               value="{newLimit}"          type="decimal"/>
                <parameter name="new-limit-currency"      value="{newLimitCurrency}"          type="string"/>
                <parameter name="offerId"                 value="{offerId}"        type="string"/>
                <parameter name="offerExpDate"                 value="{offerExpDate}"        type="date"/>
                <parameter name="feedbackType"            value="{feedbackType}"        type="string"/>
             </extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>