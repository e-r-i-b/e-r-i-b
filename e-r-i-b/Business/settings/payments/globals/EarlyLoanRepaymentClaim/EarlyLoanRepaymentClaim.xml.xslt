<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
            <payer-account>
				<xsl:value-of select="fromAccountSelect"/>
			</payer-account>
            <destination-amount>
                <xsl:value-of select="buyAmount"/>
            </destination-amount>
            <destination-amount-currency>
                <xsl:value-of select="buyAmountCurrency"/>
            </destination-amount-currency>
            <exact-amount>
                <xsl:value-of select="exactAmount"/>
            </exact-amount>
            <amount>
                <xsl:value-of select="amount"/>
            </amount>
            <document-date>
				<xsl:value-of select="documentDate"/>
			</document-date>
            <state>
                <xsl:value-of select="state"/>
            </state>
            <departmentId>
                 <xsl:value-of select="departmentId"/>
            </departmentId>
            <extra-parameters>
                <parameter name="partial"  value="{partial}"  type="boolean"/>
                <parameter name="loanLinkId" value="{loanLinkId}" type="string"/>
                <parameter name="from-resource-type"    value="{fromResourceType}"  type="string"/>
                <parameter name="from-resource"    value="{fromResource}"  type="string"/>
                <parameter name="from-account-name" value="{fromAccountName}"   type="string"/>
                <parameter name="selected-resource-rest" value="{selectedResourceRest}" type="string"/>
            </extra-parameters>
		</document>
    </xsl:template>
</xsl:stylesheet>